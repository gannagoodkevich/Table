package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.Lecturer;
import model.Uni;
import view.Table;

public class ChoosedForDelete {
	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };
	JScrollPane scroll;
	JTable table;
	Table t;

	public ChoosedForDelete(Table t) {
		this.t = t;
	}

	public void listenerSearchChooser(JButton button, Uni uni) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

				Object[] options = { "Faculty && DegreeName", "Name && Department", "Year" };
				int result = JOptionPane.showOptionDialog(null, myPanel, "Введите данные о новом преподавателе",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (result == 0) {
					listenerSearchByFaculty(uni);
				}
				if (result == 1) {
					listenerSearchByName(uni);
				}
				if (result == 2) {
					listenerSearchByYear(uni);
				}
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerSearchByFaculty(Uni uni) {

		String[] faculties = new String[uni.getLenght()];
		int lengthF = 0;
		for (int i = 0; i < uni.getLenght(); i++) {
			faculties[i] = uni.getFaculty(i).getTitle();
			lengthF += uni.getFaculty(i).getLenght();
		}
		String[] departments = new String[lengthF];
		Set<String> degreeT = new HashSet<>();
		int m = 0;
		for (int i = 0; i < uni.getLenght(); i++) {
			for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
				departments[m++] = uni.getFaculty(i).getDepartment(j).getTitle();
				for (int k = 0; k < uni.getFaculty(i).getDepartment(j).getLenght(); k++) {
					degreeT.add(uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegreeName());
				}
			}
		}
		String[] degreeTs = degreeT.toArray(new String[0]);

		JComboBox<String> comboBoxF = new JComboBox<String>(faculties);
		JComboBox<String> comboBoxDn = new JComboBox<String>(degreeTs);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("Факультет:"));
		myPanel.add(comboBoxF);

		myPanel.add(new JLabel("Ученое звание:"));
		myPanel.add(comboBoxDn);
		int numberOfDelete = 0;
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные о новом преподавателе",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			List<String[]> rowList = new ArrayList<String[]>();

			for (int j = 0; j < uni.getFacultyByName((String) comboBoxF.getSelectedItem()).getLenght(); j++) {
				if (uni.getFacultyByName((String) comboBoxF.getSelectedItem()).getDepartment(j)
						.getLectureByDegreeName((String) comboBoxDn.getSelectedItem()) != null) {
					for (Lecturer lecturer : uni.getFacultyByName((String) comboBoxF.getSelectedItem()).getDepartment(j)
							.getLectureByDegreeName((String) comboBoxDn.getSelectedItem())) {
						rowList.add(new String[] {
								uni.getFacultyByName((String) comboBoxF.getSelectedItem()).getTitle(),
								uni.getFacultyByName((String) comboBoxF.getSelectedItem()).getDepartment(j).getTitle(),
								lecturer.getName() + " " + lecturer.getSurname() + " " + lecturer.getSecondName(),
								lecturer.getDegreeName(), lecturer.getDegree(), lecturer.getYear() });
						uni.getFacultyByName((String) comboBoxF.getSelectedItem()).getDepartment(j)
								.deleteLecture(lecturer);
						numberOfDelete++;

					}
				}

			}
			String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			JTable table1 = new JTable(data, headers);
			scroll = new JScrollPane(table1);
			table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			table1.setRowHeight(50);

			pan.add(scroll);
			pan.add(new JLabel("Факультет:"+ numberOfDelete));
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

			try {
				DOMExample dom = new DOMExample(uni, t.FileName);
				//t.updateTable(uni);
				t.mytable.updateTable(uni);
			} catch (ParserConfigurationException | TransformerException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void listenerSearchByName(Uni uni) {

		JTextField nameField = new JTextField(20);

		String[] faculties = new String[uni.getLenght()];
		int lengthF = 0;
		for (int i = 0; i < uni.getLenght(); i++) {
			faculties[i] = uni.getFaculty(i).getTitle();
			lengthF += uni.getFaculty(i).getLenght();
		}
		String[] departments = new String[lengthF];

		int m = 0;
		for (int i = 0; i < uni.getLenght(); i++) {
			for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
				departments[m++] = uni.getFaculty(i).getDepartment(j).getTitle();
			}
		}

		JComboBox<String> comboBoxD = new JComboBox<String>(departments);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(new JLabel("Фамилия:"));
		myPanel.add(nameField);

		myPanel.add(new JLabel("Кафедра:"));
		myPanel.add(comboBoxD);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные ", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			int numberOfDelete = 0;
			// searching tamplate
			List<String[]> rowList = new ArrayList<String[]>();
			for (int i = 0; i < uni.getLenght(); i++) {
				if (uni.getFaculty(i).getDepartmentByName((String) comboBoxD.getSelectedItem()) != null) {
					if (uni.getFaculty(i).getDepartmentByName((String) comboBoxD.getSelectedItem())
							.getLectureByName(nameField.getText()) != null) {
						for (Lecturer lecturer : uni.getFaculty(i)
								.getDepartmentByName((String) comboBoxD.getSelectedItem())
								.getLectureByName(nameField.getText())) {
							rowList.add(new String[] { uni.getFaculty(i).getTitle(),
									uni.getFaculty(i).getDepartmentByName((String) comboBoxD.getSelectedItem())
											.getTitle(),
									lecturer.getName() + " " + lecturer.getSurname() + " " + lecturer.getSecondName(),
									lecturer.getDegreeName(), lecturer.getDegree(), lecturer.getYear() });
							uni.getFaculty(i).getDepartmentByName((String) comboBoxD.getSelectedItem())
									.deleteLecture(lecturer);
							numberOfDelete++;
						}
					}
				}
			}
			String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			JTable table1 = new JTable(data, headers);
			scroll = new JScrollPane(table1);
			table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			table1.setRowHeight(50);
			pan.add(new JLabel("Факультет:"+ numberOfDelete));

			pan.add(scroll);
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
			
			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

			try {
				DOMExample dom = new DOMExample(uni, t.FileName);
				t.mytable.updateTable(uni);
				updateTable(uni);
			} catch (ParserConfigurationException | TransformerException e1) {
				e1.printStackTrace();
			}

		}

	}

	public void listenerSearchByYear(Uni uni) {

		JTextField yearFieldFrom = new JTextField(10);
		JTextField yearFieldTo = new JTextField(10);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("Стаж работы from:"));
		myPanel.add(yearFieldFrom);
		myPanel.add(new JLabel("Стаж работы to:"));
		myPanel.add(yearFieldTo);
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные о новом преподавателе",
				JOptionPane.OK_CANCEL_OPTION);
		int numberOfDelete=0;
		if (result == JOptionPane.OK_OPTION) {
			List<String[]> rowList = new ArrayList<String[]>();
			for (int i = 0; i < uni.getLenght(); i++) {
				for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
					if (uni.getFaculty(i).getDepartment(j).getLectureByYear(yearFieldFrom.getText(),
							yearFieldTo.getText()) != null) {
						for (Lecturer lecturer : uni.getFaculty(i).getDepartment(j)
								.getLectureByYear(yearFieldFrom.getText(), yearFieldTo.getText())) {
							rowList.add(new String[] { uni.getFaculty(i).getTitle(),
									uni.getFaculty(i).getDepartment(j).getTitle(),
									lecturer.getName() + " " + lecturer.getSurname() + " " + lecturer.getSecondName(),
									lecturer.getDegreeName(), lecturer.getDegree(), lecturer.getYear() });
							uni.getFaculty(i).getDepartment(j).deleteLecture(lecturer);
							numberOfDelete++;
						}
					}
				}
			}
			String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			JTable table1 = new JTable(data, headers);
			scroll = new JScrollPane(table1);
			table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			table1.setRowHeight(50);

			pan.add(scroll);
			pan.add(new JLabel("Факультет:"+ numberOfDelete));
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);
			try {
				DOMExample dom = new DOMExample(uni, t.FileName);
				//t.updateTable(uni);
				t.mytable.updateTable(uni);
			} catch (ParserConfigurationException | TransformerException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void updateTable(Uni uni) {
		List<String[]> rowList = new ArrayList<String[]>();
		for (int i = 0; i < uni.getLenght(); i++) {
			for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
				for (int k = 0; k < uni.getFaculty(i).getDepartment(j).getLenght(); k++) {
					rowList.add(
							new String[] { uni.getFaculty(i).getTitle(), uni.getFaculty(i).getDepartment(j).getTitle(),
									uni.getFaculty(i).getDepartment(j).getlecturer(k).getName() + " "
											+ uni.getFaculty(i).getDepartment(j).getlecturer(k).getSurname() + " "
											+ uni.getFaculty(i).getDepartment(j).getlecturer(k).getSecondName(),
									uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegreeName(),
									uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegree(),
									uni.getFaculty(i).getDepartment(j).getlecturer(k).getYear() });
				}
			}
		}
		String[][] data = rowList.toArray(new String[0][]);
		table = new JTable(data, headers);
		table.repaint();
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		scroll.setViewportView(table);
	}

}
