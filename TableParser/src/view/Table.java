package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import controller.ChoosedForDelete;
import controller.Chooser;
import controller.DOMExample;
import controller.SAXExample;
import javafx.scene.paint.Color;
import model.Lecturer;
import model.TableModel;
import model.Uni;

public class Table {

	private static final int AREA_WIDTH = 1920;
	private static final int AREA_HIGHT = 1080;
	private static final String PENCIL_PATH = "src/arrow.png";

	JFrame main;
	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };

	Object[][] data;

	JTable table;
	JScrollPane scroll;
	List<String[]> rowList;
	public String FileName;

	int numOfRows;
	int numOfRowsEnd;
	int numOfRowsStart;

	Uni uni;
	public TableModel mytable;

	public Table() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		main = new JFrame();
		String[] rows = { "src/controller/BSUIR.xml",  "src/controller/BSU.xml" };
		JPanel curr = new JPanel();
		JComboBox<String> comboBoxD = new JComboBox<String>(rows);
		curr.add(comboBoxD);
		JOptionPane.showConfirmDialog(null, curr, "Введите данные ", JOptionPane.OK_CANCEL_OPTION);
		FileName = (String) comboBoxD.getSelectedItem();
		SAXExample sax = new SAXExample(FileName);
		uni = sax.uni;
		// data = new String[60][headers.length];
		mytable = new TableModel(uni);
		main.add(mytable.scroll, BorderLayout.BEFORE_FIRST_LINE);

		JButton searchButton = new JButton("Add");
		JButton search1Button = new JButton("Search");
		JButton deleteButton = new JButton("Delete");
		JButton firstButton = new JButton("Go to head");
		JButton lastButton = new JButton("Go to tail");
		JButton changeRowsButton = new JButton("Change rows");
		JButton search2Button = new JButton(new ImageIcon(PENCIL_PATH));
		JButton search3Button = new JButton(new ImageIcon(PENCIL_PATH));
		JPanel mainPanel = new JPanel();
		main.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setBounds(50, 550, 1800, 500);
		java.awt.Color color = new java.awt.Color(14, 45, 67, 90);
		mainPanel.setBackground(color);
		// mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setLayout(null);
		mainPanel.add(searchButton);
		searchButton.setBounds(100, 100, 100, 70);
		mainPanel.add(search1Button);
		search1Button.setBounds(250, 100, 100, 70);
		mainPanel.add(search2Button);
		search2Button.setBounds(1600, 100, 70, 70);
		mainPanel.add(search3Button);
		search3Button.setBounds(1500, 100, 70, 70);
		mainPanel.add(firstButton);
		firstButton.setBounds(1700, 100, 70, 70);
		mainPanel.add(lastButton);
		lastButton.setBounds(1400, 100, 70, 70);
		mainPanel.add(changeRowsButton);
		changeRowsButton.setBounds(1300, 100, 70, 70);
		mainPanel.add(deleteButton);
		deleteButton.setBounds(400, 100, 100, 70);
		// searchButton.addActionListener(listener);
		listenerAdd(searchButton); //that should be in code!!!!
		Chooser chooser = new Chooser();
		chooser.listenerSearchChooser(search1Button, uni);
		ChoosedForDelete delete = new ChoosedForDelete(this);
		delete.listenerSearchChooser(deleteButton, uni);
		mytable.listenerTurnLeft(search3Button);
		mytable.listenerTurnRight(search2Button);
		mytable.listenerToHead(firstButton);
		mytable.listenerToTail(lastButton);
		mytable.listenerChangeNum(changeRowsButton);
		main.setVisible(true);
	}
	
	public void listenerAdd(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				String[] faculties = new String[uni.getLenght()];
				int lengthF = 0;
				for (int i = 0; i < uni.getLenght(); i++) {
					faculties[i] = uni.getFaculty(i).getTitle();
					lengthF += uni.getFaculty(i).getLenght();
				}
				String[] departments = new String[lengthF];
				Set<String> degreeT = new HashSet<>();
				Set<String> degree = new HashSet<>();
				int m = 0;
				for (int i = 0; i < uni.getLenght(); i++) {
					for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
						departments[m++] = uni.getFaculty(i).getDepartment(j).getTitle();
						for (int k = 0; k < uni.getFaculty(i).getDepartment(j).getLenght(); k++) {
							degreeT.add(uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegreeName());
							degree.add(uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegree());
						}
					}
				}
				String[] degreeTs = degreeT.toArray(new String[0]);
				String[] degrees = degree.toArray(new String[0]);

				JComboBox<String> comboBoxF = new JComboBox<String>(faculties);
				JComboBox<String> comboBoxD = new JComboBox<String>(departments);
				JComboBox<String> comboBoxDg = new JComboBox<String>(degrees);
				JComboBox<String> comboBoxDn = new JComboBox<String>(degreeTs);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				myPanel.add(new JLabel("Фамилия:"));
				myPanel.add(nameField);
				myPanel.add(new JLabel("Имя:"));
				myPanel.add(surnameField);
				myPanel.add(new JLabel("Отчество:"));
				myPanel.add(secondNameField);
				myPanel.add(new JLabel("Факультет:"));
				myPanel.add(comboBoxF);
				myPanel.add(new JLabel("Кафедра:"));
				myPanel.add(comboBoxD);
				myPanel.add(new JLabel("Ученое звание:"));
				myPanel.add(comboBoxDn);
				myPanel.add(new JLabel("Ученая степень:"));
				myPanel.add(comboBoxDg);
				myPanel.add(new JLabel("Стаж работы:"));
				myPanel.add(yearField);

				int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные о новом преподавателе",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (uni.getFacultyByName((String) comboBoxF.getSelectedItem())
							.getDepartmentByName((String) comboBoxD.getSelectedItem())
							.getLectureByInfo(nameField.getText(), surnameField.getText(), secondNameField.getText(),
									 (String) comboBoxDn.getSelectedItem(), (String) comboBoxDg.getSelectedItem(),
									yearField.getText()) == null) {

						// adding
						Lecturer lect = new Lecturer(nameField.getText(), surnameField.getText(),
								secondNameField.getText(), 
								(String) comboBoxDn.getSelectedItem(), (String) comboBoxDg.getSelectedItem(), yearField.getText());
						uni.getFacultyByName((String) comboBoxF.getSelectedItem())
								.getDepartmentByName((String) comboBoxD.getSelectedItem()).addLecturer(lect);
						try {
							DOMExample dom = new DOMExample(uni, FileName);
							mytable.updateTable(uni);
						} catch (ParserConfigurationException | TransformerException e1) {
							e1.printStackTrace();
						}
					}
				}

			}
		};
		mytable.updateTable(uni);
		button.addActionListener(actionListener);
	}

	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, TransformerException {
		run(new Table(), AREA_WIDTH, AREA_HIGHT);
	}

	public static void run(final Table frame, final int wigth, final int hight) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.main.setTitle(frame.getClass().getSimpleName());
				frame.main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.main.setSize(wigth, hight);
				frame.main.setVisible(true);
			}
		});
	}
}
