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
	//НЕ ЗАБУДЬ ЕЎЕ РАЗ ПОЧИСТИТЬ САМ ХМЛ ДОКУМЕНТ!!!
	// Список для сотрудников из XML файла
	public static void main(String[] args) throws SAXException, IOException, TransformerException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse("src/controller/Data.xml");

			// Получаем корневой элемент
			Node root = document.getFirstChild();

			
			//changeFacultyName(document, "HAHA", 3);
			changeName(document, "OXXI", 4);
			changeSurname(document, "Mirpn", 10);
			FullName name = new FullName("Алеся", "Витальевна", "Кротая");
			createEmployee(document, "ФТК", "Личная кафедра", name, "профессор", "кандидат мемосных наук", "1000500");
			/*
			 * // Добавим новый элемент Element site = document.createElement("site");
			 * site.appendChild(document.createTextNode("http://javism.blogspot.com/"));
			 * blog.appendChild(site);
			 * 
			 * // Пройдемся по элементу blog и удалим элементы language // и изменим имя
			 * Pavel на Pavel O NodeList list = blog.getChildNodes(); for (int i = 0; i <
			 * list.getLength(); i++) { Node node = list.item(i); if
			 * ("author".equals(node.getNodeName())) node.setTextContent("Pavel O"); if
			 * ("language".equals(node.getNodeName())) blog.removeChild(node); }
			 */
			// Запишем содержимое в xml файл
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("src/controller/newData.xml"));
			transformer.transform(domSource, streamResult);
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getLocalizedMessage());
			pce.printStackTrace();
		} /*
			 * catch (TransformerException te) {
			 * System.out.println(te.getLocalizedMessage()); te.printStackTrace(); } catch
			 * (IOException ioe) { System.out.println(ioe.getLocalizedMessage());
			 * ioe.printStackTrace(); } catch (SAXException sae) {
			 * System.out.println(sae.getLocalizedMessage()); sae.printStackTrace(); }
			 */
	}
	public static void changeName(Document document, String name, int index) {
		Node blog = document.getElementsByTagName("employee").item(index);
		NamedNodeMap namedNodeMap = blog.getAttributes();
		Node nodeAttr = namedNodeMap.getNamedItem("surname");
		nodeAttr.setTextContent(name);
	}
	public static void changeSecondName(Document document, String name, int index) {
		Node blog = document.getElementsByTagName("employee").item(index);
		NamedNodeMap namedNodeMap = blog.getAttributes();
		Node nodeAttr = namedNodeMap.getNamedItem("secondName");
		nodeAttr.setTextContent(name);
	}
	public static void changeSurname(Document document, String name, int index) {
		Node blog = document.getElementsByTagName("employee").item(index);
		NamedNodeMap namedNodeMap = blog.getAttributes();
		Node nodeAttr = namedNodeMap.getNamedItem("name");
		nodeAttr.setTextContent(name);
	}
	
	public static void createEmployee(Document document, String faculty, String department, FullName name, String degreeName, String degree, String year) {
		NodeList fac = document.getElementsByTagName("faculty");
		for(int i=0; i<fac.getLength(); i++) {
			//faculty.equals(fac.item(i));
			NamedNodeMap namedNodeMap = fac.item(i).getAttributes();
			Node nodeAttr = namedNodeMap.getNamedItem("titleF");
			System.out.println(nodeAttr.getTextContent());
			if(faculty.equals(nodeAttr.getTextContent())) {
				Element blog0 = document.createElement("departments");
				//Node blog0 = document.getElementsByTagName("departments").item(0);
				fac.item(i).appendChild(blog0);
				Element blog = document.createElement("department");
				blog0.appendChild(blog);
	            //Установка атрибута элемента blog
	            Attr attr = document.createAttribute("titleK");
	            attr.setValue(department);
	            blog.setAttributeNode(attr);
	 
	            //Элемент "Автор"
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
	            blog.appendChild(employee);
	            
			}
		}
		
	}
}
