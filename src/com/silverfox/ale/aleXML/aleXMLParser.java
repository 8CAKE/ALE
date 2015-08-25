package com.silverfox.ale.aleXML;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class aleXMLParser {

    //User Info
    static String superUser;
    static String password;
    static String email;
    static String age;
    static String school;

    static String width;

    public static void generateUserInfo(){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(aleXMLParser.class.getResourceAsStream("userInfo.xml"));
            document.normalize();

            NodeList userInfoNodeList = document.getElementsByTagName("userInfo");
            Node userInfoNode = userInfoNodeList.item(0);
            Element userInfoElement = (Element)userInfoNode;

            System.out.println("<----- XML Parser Output ----->");

            Node usernameNode = userInfoElement.getElementsByTagName("username").item(0);
            Element usernameElement = (Element)usernameNode;

            superUser = usernameElement.getTextContent();
            System.out.println(superUser);

            Node passwordNode = userInfoElement.getElementsByTagName("password").item(0);
            Element passwordElement = (Element)passwordNode;

            password = passwordElement.getTextContent();
            System.out.println(password);

            Node emailNode = userInfoElement.getElementsByTagName("email").item(0);
            Element emailElement = (Element)emailNode;

            email = emailElement.getTextContent();
            System.out.println(email);

            Node ageNode = userInfoElement.getElementsByTagName("age").item(0);
            Element ageElement = (Element)ageNode;

            age = ageElement.getTextContent();
            System.out.println(age);

            Node schoolNode = userInfoElement.getElementsByTagName("school").item(0);
            Element schoolElement = (Element)schoolNode;

            school = schoolElement.getTextContent();
            System.out.println(school);

            Node widthNode = userInfoElement.getElementsByTagName("width").item(0);
            Element widthElement = (Element)widthNode;

            width = schoolElement.getTextContent();
            System.out.println(width);

            System.out.println("<---------->\n");

        }catch (ParserConfigurationException parserConfigExcep ){
            System.out.println("<----- Parser Configuration Exception in Parse XML ----->");
            parserConfigExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (IOException ioExcep){
            System.out.println("<----- IO Exception in Parse XML ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }catch (SAXException saxExcep){
            System.out.println("<----- SAX Exception in Parse XML ----->");
            saxExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

    }

    public aleXMLParser() {
    }

    public static String getSuperUser() {
        return superUser;
    }

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }

    public static String getAge() {
        return age;
    }

    public static String getSchool() {
        return school;
    }

    public static String getWidth() { return width; }
}
