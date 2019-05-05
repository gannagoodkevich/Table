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

import controller.DeleteController;
import controller.SearchController;
import model.Lecturer;
import model.Uni;

public class ChooserForSearch {

	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };
	JScrollPane scroll;
	JTable table;
	WindowUserCom t;

	ChooserForSearch(WindowUserCom t){
		this.t = t;
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

	public void listenerSearchChooser(JButton button, Uni currentuni) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				System.out.println(t.currentUniversity.getFaculty(0).getTitle());
				Object[] options = { "Faculty && DegreeName", "Name && Department", "Year" };
				int result = JOptionPane.showOptionDialog(null, myPanel, "Выберите способ поиска для удаления",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (result == 0) {
					listenerSearchByFaculty(t.currentUniversity);
				}
				if (result == 1) {
					listenerSearchByName(t.currentUniversity);
				}
				if (result == 2) {
					listenerSearchByYear(t.currentUniversity);
				}
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerSearchByFaculty(Uni uni) {

		String[] faculties = new String[uni.getLenght()];
		int numberOfFaculties = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			faculties[indexOfCurrentFaculty] = uni.getFaculty(indexOfCurrentFaculty).getTitle();
			numberOfFaculties += uni.getFaculty(indexOfCurrentFaculty).getLenght();
		}
		String[] departments = new String[numberOfFaculties];
		Set<String> degreeT = new HashSet<>();
		int numberOfCurrentDepartments = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < uni.getFaculty(indexOfCurrentFaculty)
					.getLenght(); indexOfCurrentDepartment++) {
				departments[numberOfCurrentDepartments++] = uni.getFaculty(indexOfCurrentFaculty)
						.getDepartment(indexOfCurrentDepartment).getTitle();
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

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска и удаления",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			SearchController searchcontr = new SearchController();
			List<String[]> rowList = searchcontr.listenerSearchByFaculty(uni, (String) comboBoxF.getSelectedItem(), (String) comboBoxDn.getSelectedItem());
			// String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			/*
			 * JTable table1 = new JTable(data, headers); scroll = new JScrollPane(table1);
			 * table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			 * table1.setRowHeight(50);
			 */
			//pan.setLayout(null);
			TableWithPages currTable = new TableWithPages(t, uni, rowList, pan);
			//pan.add(currTable.scroll);

			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

		}

	}

	public void listenerSearchByName(Uni uni) {

		JTextField nameField = new JTextField();

		String[] faculties = new String[uni.getLenght()];
		int lengthF = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			faculties[indexOfCurrentFaculty] = uni.getFaculty(indexOfCurrentFaculty).getTitle();
			lengthF += uni.getFaculty(indexOfCurrentFaculty).getLenght();
		}
		String[] departments = new String[lengthF];

		int m = 0;
		for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < uni.getLenght(); indexOfCurrentFaculty++) {
			for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < uni.getFaculty(indexOfCurrentFaculty)
					.getLenght(); indexOfCurrentDepartment++) {
				departments[m++] = uni.getFaculty(indexOfCurrentFaculty).getDepartment(indexOfCurrentDepartment)
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
			SearchController searchcontr = new SearchController();
			List<String[]> rowList = searchcontr.listenerSearchByName(uni, (String) comboBoxD.getSelectedItem(), nameField.getText());
			
			// String[][] data = rowList.toArray(new String[0][]);
			JPanel pan = new JPanel();

			/*
			 * JTable table1 = new JTable(data, headers); scroll = new JScrollPane(table1);
			 * table1.setPreferredScrollableViewportSize(new Dimension(1800, 500));
			 * table1.setRowHeight(50); pan.add(scroll);
			 */
			TableWithPages currTable = new TableWithPages(t, uni, rowList, pan);
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));
			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);

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
		JPanel pan = new JPanel();
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска и удаления",
				JOptionPane.OK_CANCEL_OPTION);
		String uare1 = yearFieldFrom.getText();
		String uare2 = yearFieldTo.getText();
		if (result == JOptionPane.OK_OPTION) {
			SearchController searchcontr = new SearchController();
			List<String[]> rowList = searchcontr.listenerSearchByYear(uni, uare1, uare2);
			TableWithPages currTable = new TableWithPages(t, uni, rowList, pan);
			pan.add(currTable.scroll);
			UIManager.put("OptionPane.minimumSize", new Dimension(1800, 500));

			JOptionPane.showMessageDialog(null, pan, "Table", JOptionPane.OK_CANCEL_OPTION);
		}
	}

}
