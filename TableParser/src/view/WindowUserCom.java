package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import model.DOMExample;
import model.Department;
import model.Faculty;
import model.Lecturer;
import model.SAXExample;
import model.Uni;

public class WindowUserCom {

	public JFrame mainFrame;
	public String FileName = "BSUIR.xml";
	Uni currentUniversity;
	public TableModel currentTableWithLecturers;

	public WindowUserCom() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		mainFrame = new JFrame();
		ArrayList<JMenu> menus = new ArrayList<JMenu>();
		menus.add(new JMenu("File"));
		ArrayList<JMenuItem> itemsFile = new ArrayList<JMenuItem>();
		itemsFile.add(new JMenuItem("Create new"));
		itemsFile.add(new JMenuItem("Open"));
		for (JMenuItem item : itemsFile) {
			menus.get(0).add(item);
		}
		JMenuBar menuBar = new JMenuBar();
		for (JMenu jm : menus) {
			menuBar.add(jm);
		}
		mainFrame.setJMenuBar(menuBar);
		openFile(itemsFile.get(1));
		createNewFile(itemsFile.get(0));
	}

	public static void run(final WindowUserCom frame, final int wigth, final int hight) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.mainFrame.setTitle(frame.getClass().getSimpleName());
				frame.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.mainFrame.setSize(wigth, hight);
				frame.mainFrame.setVisible(true);
			}
		});
	}

	public void paintGUI() {
		JButton addButton = new JButton("Add");
		JButton searchButton = new JButton("Search");
		JButton deleteButton = new JButton("Delete");
		JPanel mainPanel = new JPanel();
		mainFrame.add(mainPanel, BorderLayout.NORTH);
		currentTableWithLecturers = new TableModel(currentUniversity, mainPanel);
		mainFrame.add(currentTableWithLecturers.scroll, BorderLayout.BEFORE_FIRST_LINE);
		mainPanel.setBounds(50, 550, 1800, 500);
		mainPanel.setLayout(null);
		mainPanel.add(addButton);
		addButton.setBounds(0, 100, 100, 70);
		mainPanel.add(searchButton);
		searchButton.setBounds(150, 100, 100, 70);
		mainPanel.add(deleteButton);
		deleteButton.setBounds(300, 100, 100, 70);
		JLabel labl = new JLabel("Number of elements: " + currentTableWithLecturers.rowList.size());
		mainPanel.add(labl);
		labl.setBounds(750, 100, 200, 70);
		JLabel labl1 = new JLabel("Number of elements on page: " + currentTableWithLecturers.numOfRows);
		mainPanel.add(labl1);
		labl1.setBounds(550, 100, 200, 70);
		listenerAdd(addButton);
		ChooserForSearch chooser = new ChooserForSearch();
		chooser.listenerSearchChooser(searchButton, currentUniversity);
		ChoosedForDelete delete = new ChoosedForDelete(this);
		delete.listenerSearchChooser(deleteButton, currentUniversity);
		mainFrame.setVisible(true);
	}

	public void createNewFile(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileSeparator = System.getProperty("file.separator");
				JTextField nameOfFile = new JTextField(10);
				

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				
				myPanel.add(new JLabel("Стаж работы с:"));
				myPanel.add(nameOfFile);
				JPanel pan = new JPanel();
				int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите данные для поиска и удаления",
						JOptionPane.OK_CANCEL_OPTION);
				String relativePath = System.getProperty("user.dir") + fileSeparator  + nameOfFile.getText()+".xml";
				File newFile = new File(relativePath);
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder;
				try {
					documentBuilder = documentBuilderFactory.newDocumentBuilder();
					Document document = documentBuilder.newDocument();
				} catch (ParserConfigurationException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				FileName =  nameOfFile.getText() + ".xml";
				
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(newFile,true));
				bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
				bw.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				JTextField faculty = new JTextField(20);
				JTextField department = new JTextField(10);
				JTextField degreeTitle = new JTextField(10);
				JTextField degreeScience = new JTextField(10);
				JPanel myPanel1 = new JPanel();
				myPanel1.setLayout(new BoxLayout(myPanel1, BoxLayout.Y_AXIS));
				myPanel1.add(new JLabel("Фамилия:"));
				myPanel1.add(nameField);
				myPanel1.add(new JLabel("Имя"));
				myPanel1.add(surnameField);
				myPanel1.add(new JLabel("Отчество:"));
				myPanel1.add(secondNameField);
				myPanel1.add(new JLabel("Факультет:"));
				myPanel1.add(faculty);
				myPanel1.add(new JLabel("Кафедра:"));
				myPanel1.add(department);
				myPanel1.add(new JLabel("Ученое звание:"));
				myPanel1.add(degreeScience);
				myPanel1.add(new JLabel("Ученая степень:"));
				myPanel1.add(degreeTitle);
				myPanel1.add(new JLabel("Стаж работы:"));
				myPanel1.add(yearField);

				int result1 = JOptionPane.showConfirmDialog(null, myPanel1, "Введите информацию о новом преподавателе",
						JOptionPane.OK_CANCEL_OPTION);
				if (result1 == JOptionPane.OK_OPTION) {
					 
						currentUniversity = new Uni("New Uni");
						Faculty fac = new Faculty(faculty.getText());
						Department dep  = new Department(department.getText());
						Lecturer lect = new Lecturer(nameField.getText(), surnameField.getText(),
								secondNameField.getText(), (String) degreeScience.getText(),
								(String) degreeTitle.getText(), yearField.getText());
						currentUniversity.addFaculty(fac);
						fac.addDepartment(dep);
						dep.addLecturer(lect);
						System.out.println(currentUniversity.getFaculty(0).getTitle());
						try {
							DOMExample dom = new DOMExample(currentUniversity, FileName);
							//currentTableWithLecturers.updateTable(currentUniversity);
						} catch (ParserConfigurationException | TransformerException e1) {
							e1.printStackTrace();
						}
					
				}
				paintGUI();

			}
		};
	// mytable.updateTable(uni);
	menu.addActionListener(actionListener);

	}

	public void openFile(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser j = new JFileChooser(System.getProperty("user.dir"));
				j.showSaveDialog(null);
				FileName = j.getSelectedFile().getAbsolutePath();
				// Open the save dialog
				SAXExample sax;
				try {
					sax = new SAXExample(FileName);
					currentUniversity = sax.uni;
					// currentTableWithLecturers.updateTable(currentUniversity);
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				paintGUI();

			}
		};
		// mytable.updateTable(uni);
		menu.addActionListener(actionListener);
	}

	public void listenerAdd(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				String[] faculties = new String[currentUniversity.getLenght()];
				int numberOfDepartments = 0;
				for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < currentUniversity
						.getLenght(); indexOfCurrentFaculty++) {
					faculties[indexOfCurrentFaculty] = currentUniversity.getFaculty(indexOfCurrentFaculty).getTitle();
					numberOfDepartments += currentUniversity.getFaculty(indexOfCurrentFaculty).getLenght();
				}
				String[] departments = new String[numberOfDepartments];
				Set<String> degreeTitle = new HashSet<>();
				Set<String> degreeScience = new HashSet<>();
				int indexOfDepartments = 0;
				for (int indexOfCurrentFaculty = 0; indexOfCurrentFaculty < currentUniversity
						.getLenght(); indexOfCurrentFaculty++) {
					for (int indexOfCurrentDepartment = 0; indexOfCurrentDepartment < currentUniversity
							.getFaculty(indexOfCurrentFaculty).getLenght(); indexOfCurrentDepartment++) {
						departments[indexOfDepartments++] = currentUniversity.getFaculty(indexOfCurrentFaculty)
								.getDepartment(indexOfCurrentDepartment).getTitle();
						for (int k = 0; k < currentUniversity.getFaculty(indexOfCurrentFaculty)
								.getDepartment(indexOfCurrentDepartment).getLenght(); k++) {
							degreeTitle.add(currentUniversity.getFaculty(indexOfCurrentFaculty)
									.getDepartment(indexOfCurrentDepartment).getlecturer(k).getDegreeName());
							degreeScience.add(currentUniversity.getFaculty(indexOfCurrentFaculty)
									.getDepartment(indexOfCurrentDepartment).getlecturer(k).getDegree());
						}
					}
				}
				String[] degreeTitles = degreeTitle.toArray(new String[0]);
				String[] degreeSciences = degreeScience.toArray(new String[0]);

				JComboBox<String> comboBoxFacultes = new JComboBox<String>(faculties);
				JComboBox<String> comboBoxDepartments = new JComboBox<String>(departments);
				JComboBox<String> comboBoxdegreeTitle = new JComboBox<String>(degreeTitles);
				JComboBox<String> comboBoxDegreeScience = new JComboBox<String>(degreeSciences);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				myPanel.add(new JLabel("Фамилия:"));
				myPanel.add(nameField);
				myPanel.add(new JLabel("Имя"));
				myPanel.add(surnameField);
				myPanel.add(new JLabel("Отчество:"));
				myPanel.add(secondNameField);
				myPanel.add(new JLabel("Факультет:"));
				myPanel.add(comboBoxFacultes);
				myPanel.add(new JLabel("Кафедра:"));
				myPanel.add(comboBoxDepartments);
				myPanel.add(new JLabel("Ученое звание:"));
				myPanel.add(comboBoxDegreeScience);
				myPanel.add(new JLabel("Ученая степень:"));
				myPanel.add(comboBoxdegreeTitle);
				myPanel.add(new JLabel("Стаж работы:"));
				myPanel.add(yearField);

				int result = JOptionPane.showConfirmDialog(null, myPanel, "Введите информацию о новом преподавателе",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (currentUniversity.getFacultyByName((String) comboBoxFacultes.getSelectedItem())
							.getDepartmentByName((String) comboBoxDepartments.getSelectedItem())
							.getLectureByInfo(nameField.getText(), surnameField.getText(), secondNameField.getText(),
									(String) comboBoxDegreeScience.getSelectedItem(),
									(String) comboBoxdegreeTitle.getSelectedItem(), yearField.getText()) == null) {
						Lecturer lect = new Lecturer(nameField.getText(), surnameField.getText(),
								secondNameField.getText(), (String) comboBoxDegreeScience.getSelectedItem(),
								(String) comboBoxdegreeTitle.getSelectedItem(), yearField.getText());
						currentUniversity.getFacultyByName((String) comboBoxFacultes.getSelectedItem())
								.getDepartmentByName((String) comboBoxDepartments.getSelectedItem()).addLecturer(lect);
						try {
							DOMExample dom = new DOMExample(currentUniversity, FileName);
							currentTableWithLecturers.updateTable(currentUniversity);
						} catch (ParserConfigurationException | TransformerException e1) {
							e1.printStackTrace();
						}
					}
				}

			}
		};
		// mytable.updateTable(uni);
		button.addActionListener(actionListener);
	}

}