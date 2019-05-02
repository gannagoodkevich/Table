package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMExample {
	public DOMExample(Uni uni, String fileName) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Node root = document.createElement("university");
		document.appendChild(root);
		Element nameUni = document.createElement("name");
		nameUni.setTextContent(uni.getTitle());
		root.appendChild(nameUni);
		Element faculties = document.createElement("faculties");
		root.appendChild(faculties);
		for (int i = 0; i < uni.getLenght(); i++) {
			Element faculty = document.createElement("faculty");
			faculty.setAttribute("titleF", uni.getFaculty(i).getTitle());
			faculties.appendChild(faculty);
			Element departments = document.createElement("departments");
			faculty.appendChild(departments);
			for (int j = 0; j < uni.getFaculty(i).getLenght(); j++) {
				Element department = document.createElement("department");
				department.setAttribute("titleK", uni.getFaculty(i).getDepartment(j).getTitle());
				departments.appendChild(department);
				for (int k = 0; k < uni.getFaculty(i).getDepartment(j).getLenght(); k++) {
					Element lecture = document.createElement("employee");
					lecture.setAttribute("name", uni.getFaculty(i).getDepartment(j).getlecturer(k).getName());
					lecture.setAttribute("surname", uni.getFaculty(i).getDepartment(j).getlecturer(k).getSurname());
					lecture.setAttribute("secondName",
							uni.getFaculty(i).getDepartment(j).getlecturer(k).getSecondName());
					lecture.setAttribute("degreeT", uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegreeName());
					lecture.setAttribute("degree", uni.getFaculty(i).getDepartment(j).getlecturer(k).getDegree());
					lecture.setAttribute("year", uni.getFaculty(i).getDepartment(j).getlecturer(k).getYear());
					department.appendChild(lecture);
				}
			}
		}

		TransformerFactory transform = TransformerFactory.newInstance();
		Transformer trans = transform.newTransformer();
		DOMSource domSource = new DOMSource(document);

		StreamResult res = new StreamResult(new File(fileName));

		trans.transform(domSource, res);
	}

	public static boolean findDepartment(Document document, String department, Node fin) {
		NodeList dep = document.getElementsByTagName("department");
		boolean f = false;
		for (int i = 0; i < dep.getLength(); i++) {
			NamedNodeMap namedNodeMap = dep.item(i).getAttributes();
			Node nodeAttr = namedNodeMap.getNamedItem("titleK");
			if (department.equals(nodeAttr.getTextContent())) {
				f = true;
				fin = nodeAttr;
				System.out.println(nodeAttr.getTextContent());
			}
		}
		return f;
	}
}
