package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import model.DOMExample;
import model.Lecturer;
import model.SAXExample;
import model.Uni;

public class WindowUserCom {

	private static final String PENCIL_PATH = "src/arrow.png";

	public JFrame main;
	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };

	public String FileName;

	Uni uni;
	public TableModel mytable;

	public WindowUserCom() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		main = new JFrame();
		String[] rows = { "src/controller/BSUIR.xml", "src/controller/BSU.xml" };
		JPanel curr = new JPanel();
		JComboBox<String> comboBoxD = new JComboBox<String>(rows);
		curr.add(comboBoxD);
		JOptionPane.showConfirmDialog(null, curr, "Lalala ", JOptionPane.OK_CANCEL_OPTION);
		FileName = (String) comboBoxD.getSelectedItem();
		SAXExample sax = new SAXExample(FileName);
		uni = sax.uni;

		JButton searchButton = new JButton("Add");
		JButton search1Button = new JButton("Search");
		JButton deleteButton = new JButton("Delete");
		/*JButton firstButton = new JButton("Go to head");
		JButton lastButton = new JButton("Go to tail");
		JButton changeRowsButton = new JButton("Change rows");
		JButton search2Button = new JButton(new ImageIcon(PENCIL_PATH));
		JButton search3Button = new JButton(new ImageIcon(PENCIL_PATH));*/
		JPanel mainPanel = new JPanel();
		
		
		main.add(mainPanel, BorderLayout.NORTH);
		TableModel mytable = new TableModel(uni, mainPanel);
		main.add(mytable.scroll, BorderLayout.BEFORE_FIRST_LINE);
		mainPanel.setBounds(50, 550, 1800, 500);
		mainPanel.setLayout(null);
		mainPanel.add(searchButton);
		searchButton.setBounds(0, 100, 100, 70);
		mainPanel.add(search1Button);
		search1Button.setBounds(150, 100, 100, 70);
		/*mainPanel.add(search2Button);
		search2Button.setBounds(1600, 100, 70, 70);
		search3Button.setBounds(1500, 100, 70, 70);
		mainPanel.add(firstButton);
		firstButton.setBounds(1700, 100, 100, 70);
		mainPanel.add(lastButton);
		lastButton.setBounds(1370, 100, 100, 70);
		mainPanel.add(changeRowsButton);
		changeRowsButton.setBounds(1200, 100, 150, 70);
		mainPanel.add(search3Button);*/
		mainPanel.add(deleteButton);
		deleteButton.setBounds(300, 100, 100, 70);
		JLabel labl = new JLabel("Number of elements: " + mytable.rowList.size());
		mainPanel.add(labl);
		labl.setBounds(750, 100, 200, 70);
		JLabel labl1 = new JLabel("Number of elements on page: " + mytable.numOfRows);
		mainPanel.add(labl1);
		labl1.setBounds(550, 100, 200, 70);
		listenerAdd(searchButton); 
		ChooserForSearch chooser = new ChooserForSearch();
		chooser.listenerSearchChooser(search1Button, uni);
		ChoosedForDelete delete = new ChoosedForDelete(this);
		delete.listenerSearchChooser(deleteButton, uni);
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
				myPanel.add(new JLabel("Имя"));
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

				int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите информацию о новом преподавателе",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (uni.getFacultyByName((String) comboBoxF.getSelectedItem())
							.getDepartmentByName((String) comboBoxD.getSelectedItem())
							.getLectureByInfo(nameField.getText(), surnameField.getText(), secondNameField.getText(),
									(String) comboBoxDn.getSelectedItem(), (String) comboBoxDg.getSelectedItem(),
									yearField.getText()) == null) {
						Lecturer lect = new Lecturer(nameField.getText(), surnameField.getText(),
								secondNameField.getText(), (String) comboBoxDn.getSelectedItem(),
								(String) comboBoxDg.getSelectedItem(), yearField.getText());
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
		//mytable.updateTable(uni);
		button.addActionListener(actionListener);
	}
}