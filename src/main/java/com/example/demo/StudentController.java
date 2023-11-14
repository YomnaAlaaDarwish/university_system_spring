package com.example.demo;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
@RequestMapping("/")
public class StudentController {
    private static final String XML_FILE_PATH = "students.xml";
    private Document document;

    @PostConstruct
    public void initialize() {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            if (xmlFile.exists()) {
                document = dBuilder.parse(xmlFile);
            } else {
                document = dBuilder.newDocument();
                Element rootElement = document.createElement("University");
                document.appendChild(rootElement);
            }
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public ModelAndView showForm() {
        return new ModelAndView("Home");
    }
    @GetMapping("/Home")
    public ModelAndView showhome() {
        return new ModelAndView("Home");
    }

    @GetMapping("/Add")
    public ModelAndView Add() {
        return new ModelAndView("index");
    }

    @GetMapping("/SearchName")
    public ModelAndView SearchID() {
        return new ModelAndView("SearchName");
    }

    @GetMapping("/SearchGPA")
    public ModelAndView SearchGPA() {
        return new ModelAndView("SearchGPA");
    }

    @GetMapping("/Delete")
    public ModelAndView DeletePage() {
        return new ModelAndView("Delete");
    }

    public void saveXmldocument() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/addStudent", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addStudent(@RequestBody Student student) {
        Element studentElement = document.createElement("Student");
        studentElement.setAttribute("ID", student.getStudentId());

        Element firstNameElement = document.createElement("FirstName");
        firstNameElement.appendChild(document.createTextNode(student.getFirstName()));
        studentElement.appendChild(firstNameElement);

        Element lastNameElement = document.createElement("LastName");
        lastNameElement.appendChild(document.createTextNode(student.getLastName()));
        studentElement.appendChild(lastNameElement);

        Element genderElement = document.createElement("Gender");
        genderElement.appendChild(document.createTextNode(student.getGender()));
        studentElement.appendChild(genderElement);

        Element gpaElement = document.createElement("GPA");
        gpaElement.appendChild(document.createTextNode(Double.toString(student.getGpa())));
        studentElement.appendChild(gpaElement);

        Element levelElement = document.createElement("Level");
        levelElement.appendChild(document.createTextNode(Integer.toString(student.getLevel())));
        studentElement.appendChild(levelElement);

        Element addressElement = document.createElement("Address");
        addressElement.appendChild(document.createTextNode(student.getAddress()));
        studentElement.appendChild(addressElement);

        Node universityNode = document.getFirstChild();
        universityNode.appendChild(studentElement);

        saveXmldocument();

        //return new ModelAndView("index");
    }

    @GetMapping("/getStudentInfo")
    public ResponseEntity<String> getStudentInfo(@RequestParam String studentName) {
        String studentInfo = searchStudentInXML(studentName);
        return ResponseEntity.ok(studentInfo);
    }

    private String searchStudentInXML(String studentName) {
        try {
            // البحث عن عناصر الطلاب
            NodeList nodeList = document.getElementsByTagName("Student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String n = element.getElementsByTagName("FirstName").item(0).getTextContent();
                    // element.getAttribute("ID")
                    if (n.equals(studentName)) {

                        // استخراج وإرجاع تفاصيل الطالب
                        String firsttName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                        String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                        String gender = element.getElementsByTagName("Gender").item(0).getTextContent();
                        String gpa = element.getElementsByTagName("GPA").item(0).getTextContent();
                        String level = element.getElementsByTagName("Level").item(0).getTextContent();
                        String address = element.getElementsByTagName("Address").item(0).getTextContent();

                        return "Student Info " + "\n" +
                                "First Name  : " + firsttName + "\n" +
                                "Last Name  : " + lastName + "\n" +
                                "Gender     : " + gender + "\n" +
                                "GPA        : " + gpa + "\n" +
                                "Level      : " + level + "\n" +
                                "Address    : " + address + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Student not found";
    }

    @GetMapping("/getStudentInfo2")
    public ResponseEntity<String> getStudentInfo2(@RequestParam String gpa) {
        String studentInfo = searchStudentInXMLGPA(gpa);
        return ResponseEntity.ok(studentInfo);
    }

    private String searchStudentInXMLGPA(String gpaa) {
        try {
            // البحث عن عناصر الطلاب
            NodeList nodeList = document.getElementsByTagName("Student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String g = element.getElementsByTagName("GPA").item(0).getTextContent();

                    if (g.equals(gpaa)) {

                        // استخراج وإرجاع تفاصيل الطالب
                        String firsttName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                        String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
                        String gender = element.getElementsByTagName("Gender").item(0).getTextContent();
                        String gpa = element.getElementsByTagName("GPA").item(0).getTextContent();
                        String level = element.getElementsByTagName("Level").item(0).getTextContent();
                        String address = element.getElementsByTagName("Address").item(0).getTextContent();

                        return "Student Info " + "\n" +
                                "First Name  : " + firsttName + "\n" +
                                "Last Name  : " + lastName + "\n" +
                                "Gender     : " + gender + "\n" +
                                "GPA        : " + gpa + "\n" +
                                "Level      : " + level + "\n" +
                                "Address    : " + address + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Student not found";
    }

    @PostMapping("/deleteStudent")
    @ResponseBody
    public String deleteStudent(@RequestParam("studentId") String studentId) {
        NodeList studentList = document.getElementsByTagName("Student");
        for (int i = 0; i < studentList.getLength(); i++) {
            Element studentElement = (Element) studentList.item(i);
            String idAttribute = studentElement.getAttribute("ID");

            if (idAttribute.equals(studentId)) {
                Node universityNode = studentElement.getParentNode();
                universityNode.removeChild(studentElement);

                saveXmldocument();
                return ("Student deleted");
            }
        }

        return ("Student not found");
    }
}