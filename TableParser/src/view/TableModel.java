package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Uni;

public class TableModel {

	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };

	private static final String PENCIL_PATH = "src/arrow.png";
	Object[][] data;

	JTable table;
	public JScrollPane scroll;
	public List<String[]> rowList;

	JButton search2Button;
	JButton search3Button;
	JButton firstButton;
	JButton lastButton;
	JButton changeRowsButton;

	public int numOfRows = 10;
	int numOfRowsEnd = numOfRows;
	int numOfRowsStart = 0;

	Uni uni;
	JPanel window;

	public TableModel(Uni uni, JPanel window) {

		this.uni = uni;
		this.window = window;
		rowList = new ArrayList<String[]>();
		search2Button = new JButton(new ImageIcon(PENCIL_PATH));
		search3Button = new JButton(new ImageIcon(PENCIL_PATH));
		firstButton = new JButton("Go to head");
		lastButton = new JButton("Go to tail");
		changeRowsButton = new JButton("Change rows");
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
		List<String[]> dataCurr = new ArrayList<String[]>();
		if (rowList.size() < numOfRows) {
			numOfRowsEnd = rowList.size();
		} 
			for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
				dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
						(String) data[i][3], (String) data[i][4], (String) data[i][5] });
			}
		
		String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
		table = new JTable(dataCurr1, headers);
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		window.add(search2Button);
		window.add(search3Button);
		search2Button.setBounds(1600, 100, 70, 70);
		search3Button.setBounds(1500, 100, 70, 70);
		window.add(firstButton);
		firstButton.setBounds(1700, 100, 100, 70);
		window.add(lastButton);
		lastButton.setBounds(1370, 100, 100, 70);
		window.add(changeRowsButton);
		changeRowsButton.setBounds(1200, 100, 150, 70);

		listenerTurnLeft(search3Button);
		listenerTurnRight(search2Button);
		listenerToHead(firstButton);
		listenerToTail(lastButton);
		listenerChangeNum(changeRowsButton);
	}

	public TableModel(Uni uni, List<String[]> rowList, JPanel window) {
		this.uni = uni;
		this.rowList = rowList;
		window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
		search2Button = new JButton(new ImageIcon(PENCIL_PATH));
		search3Button = new JButton(new ImageIcon(PENCIL_PATH));
		firstButton = new JButton("Go to head");
		lastButton = new JButton("Go to tail");
		changeRowsButton = new JButton("Change rows");
		data = rowList.toArray(new String[0][]);
		table = new JTable(data, headers);
		scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		window.add(search2Button);
		window.add(search3Button);
		// search2Button.setBounds(1600, 100, 70, 70);
		// search3Button.setBounds(1500, 100, 70, 70);
		window.add(firstButton);
		// firstButton.setBounds(1700, 100, 100, 70);
		window.add(lastButton);
		// lastButton.setBounds(1370, 100, 100, 70);
		window.add(changeRowsButton);
		// changeRowsButton.setBounds(1200, 100, 150, 70);

		listenerTurnLeft(search3Button);
		listenerTurnRight(search2Button);
		listenerToHead(firstButton);
		listenerToTail(lastButton);
		listenerChangeNum(changeRowsButton);
	}

	public void updateTable(Uni uni) {
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
		List<String[]> dataCurr = new ArrayList<String[]>();
		for (int i = numOfRowsStart; i < numOfRowsEnd; i++) {
			dataCurr.add(new String[] { (String) data[i][0], (String) data[i][1], (String) data[i][2],
					(String) data[i][3], (String) data[i][4], (String) data[i][5] });
		}
		String[][] dataCurr1 = dataCurr.toArray(new String[0][]);
		table = new JTable(dataCurr1, headers);
		table.repaint();
		table.setRowHeight(50);
		scroll.setViewportView(table);
	}

	public void listenerTurnLeft(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Pressed");

				// int mod = rowList.size()%numOfRows;
				// System.out.println(mod);
				if (rowList.size() <= numOfRows) {
					numOfRowsEnd = rowList.size();
					numOfRowsStart = 0;
				} else {
					if (numOfRowsEnd != rowList.size()) {
						if (numOfRowsEnd <= rowList.size() - numOfRows) {
							numOfRowsEnd += numOfRows;
							numOfRowsStart += numOfRows;
						} else {
							numOfRowsStart = numOfRowsEnd;
							numOfRowsEnd = rowList.size();

							/*
							 * numOfRowsEnd = numOfRows; numOfRowsStart = 0;
							 */
						}
					} else {
						numOfRowsEnd = numOfRows;
						numOfRowsStart = 0;
					}
				}

				List<String[]> dataCurr = new ArrayList<String[]>();
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
				if (rowList.size() <= numOfRows) {
					numOfRowsEnd = rowList.size();
					numOfRowsStart = 0;
				} else {
					if (numOfRowsEnd == numOfRows) {
						numOfRowsEnd = rowList.size();
						numOfRowsStart = numOfRowsEnd - rowList.size() % numOfRows;
					} else {
						if (numOfRowsEnd != rowList.size()) {
							if (numOfRowsEnd >= 2 * numOfRows) {
								numOfRowsEnd -= numOfRows;
								numOfRowsStart -= numOfRows;
							} else {
								numOfRowsStart = 0;
								numOfRowsEnd = numOfRows;
							}
						} else {
							numOfRowsEnd = rowList.size() - rowList.size() % numOfRows;
							numOfRowsStart = numOfRowsEnd - numOfRows;
						}
					}
				}
				List<String[]> dataCurr = new ArrayList<String[]>();
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

				List<String[]> dataCurr = new ArrayList<String[]>();
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
				numOfRowsEnd = rowList.size();
				numOfRowsStart = rowList.size() - rowList.size() % numOfRows;

				List<String[]> dataCurr = new ArrayList<String[]>();
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
				String[] rows = { "5", "10", "20" };
				JPanel curr = new JPanel();
				JComboBox<String> comboBoxD = new JComboBox<String>(rows);
				curr.add(comboBoxD);
				int result = JOptionPane.showConfirmDialog(null, curr, "Выберите количество элементов на странице",
						JOptionPane.OK_CANCEL_OPTION);
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
