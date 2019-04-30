package model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXExample {
	public static boolean isFound;
	public Uni uni = new Uni("BSU");

	public SAXExample(String FileName) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		XMLHandler handler = new XMLHandler("faculties", uni);
		if(handler != null) {
			parser.parse(new File(FileName), handler);
		}
		else {/*
			try {
				DOMExample dom = new DOMExample(uni, FileName);
			} catch (TransformerException e) {
				e.printStackTrace();
			}*/
		}

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
		private Uni uni;

		
		
		public XMLHandler(String element, Uni uni) {
			this.element = element;
			this.uni = uni;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (isEntered) {
				int length = attributes.getLength();
				for (int i = 0; i < length; i++) {
					if (qName.equals("faculty")) {
						faculty = attributes.getValue(i);
						currfac = new Faculty(faculty);
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
				Lecturer lecturer = new Lecturer(name, surname, secondName, degreeName, degree, year);
				currdep.addLecturer(lecturer);
			}
		}

	}
}