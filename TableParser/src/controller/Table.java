package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.scene.paint.Color;

public class Table {

	private static final int AREA_WIDTH = 1920;
	private static final int AREA_HIGHT = 1080;
	private static final String PENCIL_PATH = "src/arrow.png";

	JFrame main;
	Object[] headers = { "Факультет", "Название кафедры", "ФИО преподавателя", "Ученое звание", "Ученая степень",
			"Стаж работы" };

	Object[][] data;

	ArrayList<Lecturer> lecturer;

	public Table() throws ParserConfigurationException, SAXException, IOException {
		main = new JFrame();
		SAXExample sax = new SAXExample();
		lecturer = sax.lecturers;
		data = new String[lecturer.size()][headers.length];
		int i = 0;
		for (Lecturer lecturer : lecturer) {
			data[i][0] = lecturer.getFaculty();
			data[i][1] = lecturer.getDepartment();
			data[i][2] = lecturer.getName().getName() + " " + lecturer.getName().getSurname() + ""
					+ lecturer.getName().getSecondName();
			data[i][3] = lecturer.getDegreeName();
			data[i][4] = lecturer.getDegree();
			data[i][5] = lecturer.getYear();
			i++;

		}

		JTable table = new JTable(data, headers);
		JScrollPane scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(400, 500));
		table.setRowHeight(50);
		main.add(scroll, BorderLayout.BEFORE_FIRST_LINE);
		JButton searchButton = new JButton("Search");
		JButton search1Button = new JButton("Search1");
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
		search2Button.setBounds(1700, 100, 70, 70);
		mainPanel.add(search3Button);
		search3Button.setBounds(1400, 100, 70, 70);
		// searchButton.addActionListener(listener);
		lictener(searchButton);
		main.setVisible(true);
	}

	public void lictener(JButton button) {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "1X", "2X" };
				int a = JOptionPane.showOptionDialog(null, "Choose size", "Choose size", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			}
		};
		button.addActionListener(actionListener);
	}

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

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
