package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXExample {
	public static boolean isFound;
	public static ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();

	SAXExample() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		XMLHandler handler = new XMLHandler("faculties");
		parser.parse(new File("src/controller/Data.xml"), handler);
		/*for (Lecturer lecturer : lecturers) {
			System.out.println(lecturer.getFaculty());
			System.out.println(lecturer.getDepartment());
			System.out.println(lecturer.getDegreeName());
			System.out.println(lecturer.getName().getName() + " " + lecturer.getName().getSecondName() + " "
					+ lecturer.getName().getSurname());
			System.out.println(lecturer.getYear());
		}
		if (!isFound)
			System.out.println("Ёлемент не был найден.");*/

	}

	private static class XMLHandler extends DefaultHandler {

		private String element;
		boolean isEntered;

		String name;
		String surname;
		String secondName;
		FullName fullName;
		String faculty;
		String department;
		String degree;
		String degreeName;
		String year;

		public XMLHandler(String element) {
			this.element = element;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (isEntered) {
				int length = attributes.getLength();
				for (int i = 0; i < length; i++) {
					if (qName.equals("faculty")) {
						faculty = attributes.getValue(i);
					}
					if (qName.equals("department")) {
						department = attributes.getValue(i);
					} else if (qName.equalsIgnoreCase("employee")) {
						name = attributes.getValue("name");
						surname = attributes.getValue("surname");
						secondName = attributes.getValue("secondName");
						fullName = new FullName(name, surname, secondName);
						degreeName = attributes.getValue("degreeT");
						degree = attributes.getValue("degree");
						year = attributes.getValue("year");
					}
				}
			}

			if (qName.equals(element)) {
				isEntered = true;
				isFound = true;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals(element))
				isEntered = false;
			if (qName.equals("employee")) {
				lecturers.add(new Lecturer(fullName, faculty, department, degreeName, degree, year));
			}
		}
	}
}