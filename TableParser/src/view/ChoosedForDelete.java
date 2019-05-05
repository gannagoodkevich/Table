package view;

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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.DOMExample;
import controller.DeleteController;
import model.Lecturer;
import model.Uni;

public class ChoosedForDelete {
	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };
	JScrollPane scroll;
	JTable table;
	WindowUserCom currentWindow;

	public ChoosedForDelete(WindowUserCom currentWindow) {
		this.currentWindow = currentWindow;
	}

	public void listenerSearchChooser(JMenuItem menu, Uni uni, int index) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				if (index == 0) {
					listenerSearchByFaculty(uni);				
				}
				if (index == 1) {
					listenerSearchByName(uni);
				}
				if (index == 2) {
					listenerSearchByYear(uni);
				}
				
			}
			
		};
		menu.addActionListener(actionListener);
		
	}
	
	public void listenerSearchChooser(JButton button, Uni uni) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				System.out.println(currentWindow.currentUniversity.getFaculty(0).getTitle());
				Object[] options = { "Faculty && DegreeName", "Name && Department", "Year" };
				int result = JOptionPane.showOptionDialog(null, myPanel, "Введите данные о новом преподавателе",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (result == 0) {
					listenerSearchByFaculty(currentWindow.currentUniversity);				
				}
				if (result == 1) {
					listenerSearchByName(currentWindow.currentUniversity);
				}
				if (result == 2) {
					listenerSearchByYear(currentWindow.currentUniversity);
				}
			
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerSearchByFaculty(Uni uni) {

		String[] faculties = new String[uni.getLenght()];
		int lengthF = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			faculties[indexOfCurrentFaculty] = uni.getFaculty(indexOfCurrentFaculty).getTitle();
			lengthF += uni.getFaculty(indexOfCurrentFaculty).getLenght();
		}
		String[] departments = new String[lengthF];
		Set<String> degreeT = new HashSet<>();
		int indexOfDep = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < uni.getFaculty(indexOfCurrentFaculty)
					.getLenght(); indexOfCurrentDepartment++) {
				departments[indexOfDep++] = uni.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
						.getTitle();
				for (int indexOfCurrentLecturer = 0; indexOfCurrentLecturer < uni.getFaculty(indexOfCurrentFaculty)
						.getDepartment(indexOfCurrentDepartment).getLenght(); indexOfCurrentLecturer++) {
					degreeT.add(uni.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
							.getlecturer(indexOfCurrentLecturer).getDegreeName());
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
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Выберите способ поиска",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			DeleteController deletecontr = new DeleteController();
			int numberOfDelete = deletecontr.listenerSearchByFaculty(uni, (String) comboBoxF.getSelectedItem(), (String) comboBoxDn.getSelectedItem());
			JPanel pan = new JPanel();
			pan.add(new JLabel(" " + numberOfDelete));
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

			try {
				DOMExample dom = new DOMExample(uni, currentWindow.FileName);
				currentWindow.currentTableWithLecturers.updateTable(uni);
			} catch (ParserConfigurationException | TransformerException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void listenerSearchByName(Uni uni) {

		JTextField nameField = new JTextField();

		String[] faculties = new String[uni.getLenght()];
		int numberOfFaculty = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			faculties[indexOfCurrentFaculty] = uni.getFaculty(indexOfCurrentFaculty).getTitle();
			numberOfFaculty += uni.getFaculty(indexOfCurrentFaculty).getLenght();
		}
		String[] departments = new String[numberOfFaculty];

		int numberOfDepartments = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < uni.getFaculty(indexOfCurrentFaculty)
					.getLenght(); indexOfCurrentDepartment++) {
				departments[numberOfDepartments++] = uni.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
						.getTitle();
			}
		}

		JComboBox<String> comboBoxD = new JComboBox<String>(departments);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(new JLabel("Фамилия:"));
		myPanel.add(nameField);

		myPanel.add(new JLabel("Кафедра:"));
		myPanel.add(comboBoxD);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			DeleteController deletecontr = new DeleteController();
			int numberOfDelete = deletecontr.listenerSearchByName(uni, (String) comboBoxD.getSelectedItem(), nameField.getText());
			JPanel pan = new JPanel();
			pan.add(new JLabel(" " + numberOfDelete));
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);
			try {
				DOMExample dom = new DOMExample(uni, currentWindow.FileName);
				currentWindow.currentTableWithLecturers.updateTable(uni);
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

		myPanel.add(new JLabel("Стаж работы с:"));
		myPanel.add(yearFieldFrom);
		myPanel.add(new JLabel("Стаж работы по:"));
		myPanel.add(yearFieldTo);
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска",
				JOptionPane.OK_CANCEL_OPTION);
		String year1 = yearFieldFrom.getText();
		String year2 = yearFieldTo.getText();
		DeleteController deletecontr = new DeleteController();
		int numberOfDelete = deletecontr.listenerSearchByYear(uni, year1, year2);
		JPanel pan = new JPanel();
		pan.add(new JLabel(" " + numberOfDelete));
		UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
		JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);
		try {
			DOMExample dom = new DOMExample(uni, currentWindow.FileName);
			currentWindow.currentTableWithLecturers.updateTable(uni);
		} catch (ParserConfigurationException | TransformerException e1) {
			e1.printStackTrace();
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
