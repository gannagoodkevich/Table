package controller;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMExample {
	//НЕ ЗАБУДЬ ЕЩЕ РАЗ ПОЧИСТИТЬ САМ ХМЛ ДОКУМЕНТ!!!
	// Список для сотрудников из XML файла
	public static void main(String[] args) throws SAXException, IOException, TransformerException, ParserConfigurationException {
		
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// Получаем корневой элемент
			Node root = document.createElement("university");
			document.appendChild(root);
			Element nameUni = document.createElement("name");
			nameUni.setTextContent("BSU");
			root.appendChild(nameUni);
			Element faculties = document.createElement("faculties");
			root.appendChild(faculties);
			Element faculty = document.createElement("faculty");
			faculty.setAttribute("titleF", "IIT");
			faculties.appendChild(faculty);
			Element department = document.createElement("department");
			faculties.appendChild(department);
			TransformerFactory transform = TransformerFactory.newInstance();
			Transformer trans =  transform.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult res = new StreamResult(new File("src/controller/new.xml"));
						
			trans.transform(domSource, res);
	}
	//пока так: не проверяю на родителя, чтобы знать, что ета кафедра есть.
	public static void createEmployee(Document document, String faculty, String department, FullName name, String degreeName, String degree, String year) {
		NodeList fac = document.getElementsByTagName("faculty");
		NodeList dep1 = document.getElementsByTagName("department");
		System.out.println(dep1.getLength());
		for(int i=0; i<fac.getLength(); i++) {
			//faculty.equals(fac.item(i));
			NamedNodeMap namedNodeMap = fac.item(i).getAttributes();
			Node nodeAttr = namedNodeMap.getNamedItem("titleF");
			System.out.println(nodeAttr.getTextContent());
			if(faculty.equals(nodeAttr.getTextContent())) {
				NodeList dep = document.getElementsByTagName("department");
				for(int k=0; k<dep.getLength(); k++) {
					//if(dep.item(k).getParentNode().equals(fac.item(0))) {
						NamedNodeMap depMap = dep.item(k).getAttributes();
						Node depAttr = depMap.getNamedItem("titleK");
						if(department.equals(depAttr.getTextContent())) {
							System.out.println(depMap.getNamedItem("titleK"));
							Element employee = document.createElement("employee");
							Attr name1 = document.createAttribute("name");
							name1.setValue(name.getName());
							employee.setAttributeNode(name1);
							Attr name2 = document.createAttribute("surname");
							name2.setValue(name.getSurname());
							employee.setAttributeNode(name2);
							Attr name3 = document.createAttribute("secondName");
							name3.setValue(name.getSecondName());
							employee.setAttributeNode(name3);
							Attr degreen = document.createAttribute("degreeT");
							degreen.setValue(degreeName);
							employee.setAttributeNode(degreen);
							Attr degree1 = document.createAttribute("degree");
							degree1.setValue(degree);
							employee.setAttributeNode(degree1);
							Attr year1 = document.createAttribute("year");
							year1.setValue(year);
							employee.setAttributeNode(year1);
							dep.item(k).appendChild(employee);
						}
					//}
					/*else {
						System.out.println(dep.getLength());
						System.out.println("Fail");
					}*/
				}
				
				/*for(int k=0; k<dep.getLength(); k++) {
					//faculty.equals(fac.item(i));
					//NamedNodeMap depMap = dep.item(i).getAttributes();
					//Node depAttr = depMap.getNamedItem("titleK");
					if(department.equals(depAttr.getTextContent())) {
						Node depFin = depAttr;
						System.out.println(depAttr.getTextContent());
						
						Element employee = document.createElement("employee");
			            Attr name1 = document.createAttribute("name");
			            name1.setValue(name.getName());
			            employee.setAttributeNode(name1);
			            Attr name2 = document.createAttribute("surname");
			            name2.setValue(name.getSurname());
			            employee.setAttributeNode(name2);
			            Attr name3 = document.createAttribute("secondName");
			            name3.setValue(name.getSecondName());
			            employee.setAttributeNode(name3);
			            Attr degreen = document.createAttribute("degreeT");
			            degreen.setValue(degreeName);
			            employee.setAttributeNode(degreen);
			            Attr degree1 = document.createAttribute("degree");
			            degree1.setValue(degree);
			            employee.setAttributeNode(degree1);
			            Attr year1 = document.createAttribute("year");
			            year1.setValue(year);
			            employee.setAttributeNode(year1);
			            depFin.appendChild(employee);
						
					}*/
				}	
				
				/*Element blog0 = document.createElement("departments");
				//Node blog0 = document.getElementsByTagName("departments").item(0);
				fac.item(i).appendChild(blog0);
				Element blog = document.createElement("department");
				blog0.appendChild(blog);
	            //Установка атрибута элемента blog
	            Attr attr = document.createAttribute("titleK");
	            attr.setValue(department);
	            blog.setAttributeNode(attr);*/
	 
	            //Элемент "Автор"
	            
	            
			//}
		}
		
		
	}
	public static boolean findDepartment(Document document, String department, Node fin) {
		NodeList dep = document.getElementsByTagName("department");
		boolean f = false;
		for(int i=0; i<dep.getLength(); i++) {
			//faculty.equals(fac.item(i));
			NamedNodeMap namedNodeMap = dep.item(i).getAttributes();
			Node nodeAttr = namedNodeMap.getNamedItem("titleK");
			if(department.equals(nodeAttr.getTextContent())) {
				f = true;
				fin = nodeAttr;
				System.out.println(nodeAttr.getTextContent());
			}
		}
		return f;		
	}
}
