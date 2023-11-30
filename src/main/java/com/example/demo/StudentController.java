package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/search")
    public ModelAndView Search() {
        return new ModelAndView("search");
    }
    @GetMapping("/SearchFname")
    public ModelAndView SearchFName() {
        return new ModelAndView("SearchFname");
    }
    @GetMapping("/SearchLname")
    public ModelAndView SearchLName() {
        return new ModelAndView("SearchLname");
    }
    @GetMapping("/SearchAddress")
    public ModelAndView SearchAddress() {
        return new ModelAndView("SearchAddress");
    }
    @GetMapping("/SearchLevel")
    public ModelAndView Searchlevel() {
        return new ModelAndView("SearchLevel");
    }
    @GetMapping("/SearchGender")
    public ModelAndView SearchGender() {
        return new ModelAndView("SearchGender");
    }
    @GetMapping("/Searchid")
    public ModelAndView Searchid() {
        return new ModelAndView("Searchid");
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
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        if(IsduplicateID(Integer.parseInt(student.getStudentId())))
        {
            return ResponseEntity.badRequest().body("Duplicate student ID. Please use a different ID.");
        }
        else
        {
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
        return ResponseEntity.ok("Student added successfully");
        }
        

        //return new ModelAndView("index");
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

private boolean IsduplicateID(int id) {
        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getAttribute("ID");
                if (id==Integer.parseInt(g)) {
                return true;
                }
            }
        }
    return false;
}

@GetMapping("/StudentGpa")
public ResponseEntity<List<Student>> StudentGpa(@RequestParam String gpa) {
    List<Student> students = searchStudentInXMLGPA(gpa);
    return ResponseEntity.ok(students);
}

private List<Student> searchStudentInXMLGPA(String gpaa) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getElementsByTagName("GPA").item(0).getTextContent();

                if (g.equals(gpaa)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}

@GetMapping("/StudentFname")
public ResponseEntity<List<Student>> StudentFname(@RequestParam String Fname) {
    List<Student> students = searchStudentInXMLFname(Fname);
    return ResponseEntity.ok(students);
}
private List<Student> searchStudentInXMLFname(String Fname) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getElementsByTagName("FirstName").item(0).getTextContent();

                if (g.equals(Fname)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}
    
@GetMapping("/Studentlname")
public ResponseEntity<List<Student>> Studentlname(@RequestParam String lname) {
    List<Student> students = searchStudentInXMLlname(lname);
    return ResponseEntity.ok(students);
}

private List<Student> searchStudentInXMLlname(String lname) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getElementsByTagName("LastName").item(0).getTextContent();

                if (g.equals(lname)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}
    
@GetMapping("/Studentlevel")
public ResponseEntity<List<Student>> Studentlevel(@RequestParam String level) {
    List<Student> students = searchStudentInXMLlevel(level);
    return ResponseEntity.ok(students);
}

private List<Student> searchStudentInXMLlevel(String level) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getElementsByTagName("Level").item(0).getTextContent();

                if (g.equals(level)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}
    
@GetMapping("/id")
public ResponseEntity<List<Student>> id(@RequestParam String id) {
    List<Student> students = searchStudentInXMLid(id);
    return ResponseEntity.ok(students);
}

private List<Student> searchStudentInXMLid(String value) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getAttribute("ID");
                if (g.equals(value)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}
    
@GetMapping("/gender")
public ResponseEntity<List<Student>> gender(@RequestParam String gender) {
    List<Student> students = searchStudentInXMLgender(gender);
    return ResponseEntity.ok(students);
}

private List<Student> searchStudentInXMLgender(String value) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getElementsByTagName("Gender").item(0).getTextContent();

                if (g.equals(value)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}
    
@GetMapping("/address")
public ResponseEntity<List<Student>> address(@RequestParam String address) {
    List<Student> students = searchStudentInXMLaddress(address);
    return ResponseEntity.ok(students);
}

private List<Student> searchStudentInXMLaddress(String value) {
    List<Student> students = new ArrayList<>();

    try {
        // Your existing code to retrieve XML data

        NodeList nodeList = document.getElementsByTagName("Student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String g = element.getElementsByTagName("Address").item(0).getTextContent();

                if (g.equals(value)) {
                    // Extract student details and add to the list
                    Student student = new Student();
                    student.setFirstName(element.getElementsByTagName("FirstName").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("LastName").item(0).getTextContent());
                    student.setGender(element.getElementsByTagName("Gender").item(0).getTextContent());
                    student.setGpa (Double.parseDouble(element.getElementsByTagName("GPA").item(0).getTextContent()));
                    student.setLevel(Integer.parseInt(element.getElementsByTagName("Level").item(0).getTextContent()));
                    student.setAddress(element.getElementsByTagName("Address").item(0).getTextContent());

                    students.add(student);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return students;
}





}