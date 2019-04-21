package model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableModel {

	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };

	Object[][] data;

	JTable table;
	public JScrollPane scroll;
	List<String[]> rowList;

	int numOfRows = 10;
	int numOfRowsEnd = numOfRows;
	int numOfRowsStart =0;

	Uni uni;
	
	public TableModel(Uni uni){
		this.uni = uni;
		rowList = new ArrayList<String[]>();

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

		data = rowList.toArray(new String[0][]);
		ArrayList<String[]> dataCurr = new ArrayList<String[]>();
		for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
			dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
					(String) data[i][3], (String) data[i][4], (String) data[i][5] });
		}
		String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
		table = new JTable(dataCurr1, headers);
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
	}
	
	public void updateTable(Uni uni) {
		data = new String[60][headers.length];
		int l = 0;
		for (int i = 0; i < uni.getLenght(); i++) {
			for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
				for (int k = 0; k < uni.getFaculty(i).getDepartment(j).getLenght(); k++) {
					data[l][0] = uni.getFaculty(i).getTitle();
					data[l][1] = uni.getFaculty(i).getDepartment(j).getTitle();
					data[l][2] = uni.getFaculty(i).getDepartment(j).getlecturer(k).getName() + " "
							+ uni.getFaculty(i).getDepartment(j).getlecturer(k).getSurname() + " "
							+ uni.getFaculty(i).getDepartment(j).getlecturer(k).getSecondName();
					data[l][3] = uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegreeName();
					data[l][4] = uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegree();
					data[l][5] = uni.getFaculty(i).getDepartment(j).getlecturer(k).getYear();
					l++;
				}
			}
		}
		table = new JTable(data, headers);
		table.repaint();
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		scroll.setViewportView(table);
	}
	
	public void listenerTurnLeft(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				if (numOfRowsEnd <= rowList.size() - 10) {
					numOfRowsEnd += numOfRows;
					numOfRowsStart += numOfRows;
					System.out.println(numOfRowsEnd);
				} else {
					numOfRowsEnd = numOfRows;
					numOfRowsStart = 0;
				}
				ArrayList<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerTurnRight(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				if (numOfRowsEnd >= 20) {
					numOfRowsEnd -= numOfRows;
					numOfRowsStart -= numOfRows;
					System.out.println(numOfRowsEnd);
				} else {
					numOfRowsEnd = 50;
					numOfRowsStart = 40;
				}
				ArrayList<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerToHead(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				numOfRowsEnd = numOfRows;
				numOfRowsStart = 0;

				ArrayList<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerToTail(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				numOfRowsEnd = 50;
				numOfRowsStart = 40;

				ArrayList<String[]> dataCurr = new ArrayList<String[]>();
				for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
					dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
							(String) data[i][3], (String) data[i][4], (String) data[i][5] });
				}
				String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
				table = new JTable(dataCurr1, headers);
				table.repaint();
				table.setPreferredScrollableViewportSize(new Dimension(400, 500));
				table.setRowHeight(50);
				scroll.setViewportView(table);
			}
		};
		button.addActionListener(actionListener);
	}

	public void listenerChangeNum(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");
				String[] rows = { "5", "10", "15", "20" };
				JPanel curr = new JPanel();
				JComboBox<String> comboBoxD = new JComboBox<String>(rows);
				curr.add(comboBoxD);
				int result = JOptionPane.showConfirmDialog(null, curr, "Введите данные ", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					numOfRows = Integer.parseInt((String) comboBoxD.getSelectedItem());
					numOfRowsEnd = numOfRows;
					numOfRowsStart = 0;
				}
			}
		};
		button.addActionListener(actionListener);
	}


}
