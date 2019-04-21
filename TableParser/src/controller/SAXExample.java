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

import model.Department;
import model.Faculty;
import model.Lecturer;
import model.Uni;

public class SAXExample {
	public static boolean isFound;
	//public static ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();
	public static Uni uni = new Uni("BSU");
	
	public SAXExample(String FileName) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		XMLHandler handler = new XMLHandler("faculties");
		parser.parse(new File(FileName), handler);//"src/controller/Data.xml"
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

		
		
		Faculty currfac;
		Department currdep;
		String name;
		String surname;
		String secondName;
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
						currfac  = new Faculty(faculty);
						uni.addFaculty(currfac);
					}
					if (qName.equals("department")) {
						department = attributes.getValue(i);
						currdep = new Department(department);
						currfac.addDepartment(currdep);
					} else if (qName.equalsIgnoreCase("employee")) {
						name = attributes.getValue("name");
						surname = attributes.getValue("surname");
						secondName = attributes.getValue("secondName");
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
				Lecturer lecturer  = new Lecturer(name, surname, secondName, degreeName, degree, year);
				currdep.addLecturer(lecturer);
			}
		}
		
		
	}
}