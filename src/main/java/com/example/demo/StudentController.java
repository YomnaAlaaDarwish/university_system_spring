package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

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
        return new ModelAndView("index");
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
    @PostMapping("/addStudent")
    public ModelAndView addStudent(
            @RequestParam("studentId") String studentId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("gender") String gender,
            @RequestParam("gpa") double gpa,
            @RequestParam("level") int level,
            @RequestParam("address") String address
    ) {
        Element studentElement = document.createElement("Student");
        studentElement.setAttribute("ID", studentId);

        Element firstNameElement = document.createElement("FirstName");
        firstNameElement.appendChild(document.createTextNode(firstName));
        studentElement.appendChild(firstNameElement);

        Element lastNameElement = document.createElement("LastName");
        lastNameElement.appendChild(document.createTextNode(lastName));
        studentElement.appendChild(lastNameElement);

        Element genderElement = document.createElement("Gender");
        genderElement.appendChild(document.createTextNode(gender));
        studentElement.appendChild(genderElement);

        Element gpaElement = document.createElement("GPA");
        gpaElement.appendChild(document.createTextNode(Double.toString(gpa)));
        studentElement.appendChild(gpaElement);

        Element levelElement = document.createElement("Level");
        levelElement.appendChild(document.createTextNode(Integer.toString(level)));
        studentElement.appendChild(levelElement);

        Element addressElement = document.createElement("Address");
        addressElement.appendChild(document.createTextNode(address));
        studentElement.appendChild(addressElement);

        Node universityNode = document.getFirstChild();
        universityNode.appendChild(studentElement);

        saveXmldocument();

        return new ModelAndView("index");
    }

    
}