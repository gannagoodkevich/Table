package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
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

import controller.DOMExample;
import controller.DeleteController;
import controller.SAXExample;
import controller.UniversityController;
import model.Department;
import model.Faculty;
import model.Lecturer;
import model.Uni;

public class WindowUserCom {

	public JFrame mainFrame;
	public String FileName;
	Uni currentUniversity;
	public TableWithPages currentTableWithLecturers;
	public List<JMenuItem> itemsSearch;
	public List<JMenuItem> itemsDelete;
	DeleteController deletecontr;

	public WindowUserCom() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		mainFrame = new JFrame();
		List<JMenu> menusFile = new ArrayList<JMenu>();
		menusFile.add(new JMenu("File"));
		List<JMenuItem> itemsFile = new ArrayList<JMenuItem>();
		itemsFile.add(new JMenuItem("Create new"));
		itemsFile.add(new JMenuItem("Open"));
		List<JMenu> menusSearch = new ArrayList<JMenu>();
		menusSearch.add(new JMenu("Search"));
		itemsSearch = new ArrayList<JMenuItem>();
		itemsSearch.add(new JMenuItem("by faculty && degree"));
		itemsSearch.add(new JMenuItem("by name && department"));
		itemsSearch.add(new JMenuItem("by year"));
		for (JMenuItem item : itemsFile) {
			menusFile.get(0).add(item);
		}
		for (JMenuItem item : itemsSearch) {
			menusSearch.get(0).add(item);
		}
		JMenuBar menuBar = new JMenuBar();
		for (JMenu jmemu : menusFile) {
			menuBar.add(jmemu);
		}
		for (JMenu jmemu : menusSearch) {
			menuBar.add(jmemu);
		}
		List<JMenu> menusDelete = new ArrayList<JMenu>();
		menusDelete.add(new JMenu("Delete"));
		itemsDelete = new ArrayList<JMenuItem>();
		itemsDelete.add(new JMenuItem("by faculty && degree"));
		itemsDelete.add(new JMenuItem("by name && department"));
		itemsDelete.add(new JMenuItem("by year"));
		for (JMenuItem item : itemsDelete) {
			menusDelete.get(0).add(item);
		}
		for (JMenu jmemu : menusDelete) {
			menuBar.add(jmemu);
		}
		List<JMenu> menusAdd = new ArrayList<JMenu>();
		menusAdd.add(new JMenu("Add"));
		List<JMenuItem> itemsAdd = new ArrayList<JMenuItem>();
		itemsAdd.add(new JMenuItem("add new lecturer"));
		for (JMenuItem item : itemsAdd) {
			menusAdd.get(0).add(item);
		}
		for (JMenu jmemu : menusAdd) {
			menuBar.add(jmemu);
		}
		mainFrame.setJMenuBar(menuBar);

		openFile(itemsFile.get(1));
		createNewFile(itemsFile.get(0));
		listenerAdd(itemsAdd.get(0));
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
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		System.out.println(currentUniversity.getFaculty(0).getTitle() + "paintGuiBefore");
		currentTableWithLecturers = new TableWithPages(this, currentUniversity, mainPanel);
		System.out.println(currentUniversity.getFaculty(0).getTitle() + "paintGuiAfter");
		// mainPanel.setLayout(null);
		// mainFrame.add(currentTableWithLecturers.scroll,
		// BorderLayout.BEFORE_FIRST_LINE);
		// mainPanel.setBounds(50, 550, 1800, 500);
		// mainPanel.setLayout(null);
		JPanel sPane = new JPanel();
		mainPanel.add(sPane);
		sPane.setLayout(new BoxLayout(sPane, BoxLayout.X_AXIS));
		sPane.add(Box.createRigidArea(new Dimension(0, 50)));
		sPane.add(addButton);
		sPane.add(Box.createRigidArea(new Dimension(10, 0)));
		// addButton.setBounds(0, 100, 100, 70);
		sPane.add(searchButton);
		sPane.add(Box.createRigidArea(new Dimension(10, 0)));
		// searchButton.setBounds(150, 100, 100, 70);
		sPane.add(deleteButton);
		sPane.add(Box.createRigidArea(new Dimension(10, 0)));
		// deleteButton.setBounds(300, 100, 100, 70);
		// lableNumberOnPage.setBounds(550, 100, 200, 70);
		listenerAdd(addButton);
		ChooserForSearch chooser = new ChooserForSearch(this);
		chooser.listenerSearchChooser(searchButton, currentUniversity);
		chooser.listenerSearchChooser(itemsSearch.get(0), currentUniversity, 0);
		chooser.listenerSearchChooser(itemsSearch.get(1), currentUniversity, 1);
		chooser.listenerSearchChooser(itemsSearch.get(2), currentUniversity, 2);
		ChoosedForDelete delete = new ChoosedForDelete(this);
		delete.listenerSearchChooser(deleteButton, currentUniversity);
		delete.listenerSearchChooser(itemsDelete.get(0), currentUniversity, 0);
		delete.listenerSearchChooser(itemsDelete.get(1), currentUniversity, 1);
		delete.listenerSearchChooser(itemsDelete.get(2), currentUniversity, 2);
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
				String relativePath = System.getProperty("user.dir") + fileSeparator + nameOfFile.getText() + ".xml";
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

				FileName = nameOfFile.getText() + ".xml";

				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true));
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
				String[] degreeN = { "доктор физико-математических наук", "доктор технических наук",
						"доктор гуманитарных наук", "кандидат физико-математических наук", "кандидат технических наук",
						"кандидат гуманитарных наук", "магистр физико-математических наук" };
				String[] degreeT = { "профессор", "доцент", "преподаватель", "старший преподаватель", "ассистент",
						"-" };
				JComboBox<String> comboBoxD = new JComboBox<String>(degreeN);
				JComboBox<String> comboBoxDn = new JComboBox<String>(degreeT);
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
				myPanel1.add(comboBoxDn);
				myPanel1.add(new JLabel("Ученая степень:"));
				myPanel1.add(comboBoxD);
				myPanel1.add(new JLabel("Стаж работы:"));
				myPanel1.add(yearField);

				int result1 = JOptionPane.showConfirmDialog(null, myPanel1, "Введите информацию о новом преподавателе",
						JOptionPane.OK_CANCEL_OPTION);
				if (result1 == JOptionPane.OK_OPTION) {

					currentUniversity = new Uni("New Uni");
					Faculty fac = new Faculty(faculty.getText());
					Department dep = new Department(department.getText());
					Lecturer lect = new Lecturer(nameField.getText(), surnameField.getText(), secondNameField.getText(),
							(String) comboBoxDn.getSelectedItem(), (String) comboBoxD.getSelectedItem(),
							yearField.getText());
					currentUniversity.addFaculty(fac);
					fac.addDepartment(dep);
					dep.addLecturer(lect);
					System.out.println(currentUniversity.getFaculty(0).getTitle());
					try {
						DOMExample dom = new DOMExample(currentUniversity, FileName);
					} catch (ParserConfigurationException | TransformerException e1) {
						e1.printStackTrace();
					}

				}
				paintGUI();

			}
		};
		menu.addActionListener(actionListener);

	}

	public void openFile(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
				fileChooser.showSaveDialog(null);
				FileName = fileChooser.getSelectedFile().getAbsolutePath();
				// Open the save dialog
				SAXExample sax;
				try {
					sax = new SAXExample(FileName);
					currentUniversity = sax.uni;
					// there
					System.out.println(currentUniversity.getFaculty(0).getTitle() + "open");
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
				System.out.println("End of opening File");
			}
		};
		menu.addActionListener(actionListener);
	}

	public void listenerAdd(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				UniversityController uniContr = new UniversityController();
				String[] faculties = uniContr.getFaculties(currentUniversity).toArray(new String[0]);
				String[] departments = uniContr.getDepartments(currentUniversity).toArray(new String[0]);
				String[] degreeTitles = uniContr.getDegrees(currentUniversity).toArray(new String[0]);
				String[] degreeSciences = uniContr.getDegreesSc(currentUniversity).toArray(new String[0]);

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
						Lecturer lecturer = new Lecturer(nameField.getText(), surnameField.getText(),
								secondNameField.getText(), (String) comboBoxDegreeScience.getSelectedItem(),
								(String) comboBoxdegreeTitle.getSelectedItem(), yearField.getText());
						currentUniversity.getFacultyByName((String) comboBoxFacultes.getSelectedItem())
								.getDepartmentByName((String) comboBoxDepartments.getSelectedItem())
								.addLecturer(lecturer);
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

	public void listenerAdd(JMenuItem menu) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField nameField = new JTextField(20);
				JTextField surnameField = new JTextField(10);
				JTextField secondNameField = new JTextField(10);
				JTextField yearField = new JTextField(10);
				UniversityController uniContr = new UniversityController();
				String[] faculties = uniContr.getFaculties(currentUniversity).toArray(new String[0]);
				String[] departments = uniContr.getDepartments(currentUniversity).toArray(new String[0]);
				String[] degreeTitles = uniContr.getDegrees(currentUniversity).toArray(new String[0]);
				String[] degreeSciences = uniContr.getDegreesSc(currentUniversity).toArray(new String[0]);

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
						Lecturer lecturer = new Lecturer(nameField.getText(), surnameField.getText(),
								secondNameField.getText(), (String) comboBoxDegreeScience.getSelectedItem(),
								(String) comboBoxdegreeTitle.getSelectedItem(), yearField.getText());
						currentUniversity.getFacultyByName((String) comboBoxFacultes.getSelectedItem())
								.getDepartmentByName((String) comboBoxDepartments.getSelectedItem())
								.addLecturer(lecturer);
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
		menu.addActionListener(actionListener);
	}

}