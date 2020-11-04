package com.company;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        task1();
        task21();
        task22();
        task3();
    }

    /**
     * Write a Java program using JDOM to load the recipes.xml and print
     * only the Recipe ID’s and the Recipe Titles.
     * Files to use: recipes.xml
     */
    public static void task1() {
        System.out.println("Task 1");

        //Create a SAX Builder
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            //Build the DOM document from the XML file
            Document doc = saxBuilder.build(new File("recipes.xml"));
            //Create a namspace
            Namespace ns = Namespace.getNamespace("http://www.brics.dk/ixwt/recipes");
            //return the collections element (root)
            Element collections = doc.getRootElement();
            List<Element> recipes = collections.getChildren("recipe", ns);
            if (null != recipes) {
                for (Element recipe : recipes) {
                    System.out.println("Recipe ID : " + recipe.getAttribute("id").getValue());
                    System.out.println("Recipe Title: " +
                            recipe.getChildTextNormalize("title", ns));
                }
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    /**
     * Write a Java program using JDOM to load the while.xml and validate it
     * internally using the while.xsd
     */
    public static void task21() {
        System.out.println("Task 2-1");
        //Create a SAX Builder
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        try {
            Document xmldoc = saxBuilder.build(new File("while.xml"));
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(xmldoc, System.out);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


    /**
     * Write a Java program using JDOM to load the while.xml and validate it
     * externally using the while.xsd
     */
    public static void task22() {
        System.out.println("Task 2-2");
        try {

            XMLReaderJDOMFactory schemaFac = new XMLReaderXSDFactory("while.xsd");
            //Create a SAX Builder
            SAXBuilder saxBuilder = new SAXBuilder(schemaFac);
            Document xmldoc = saxBuilder.build(new File("while.xml"));
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(xmldoc, System.out);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Write a Java program using JAXP to transform the courses.xml to an HTML using
     * any of the XSL you have written in Lab. You can either save the resulting
     * transformed content to a html file or print it on screen.
     */
    public static void task3() {
        System.out.println("Task 3");
        try {
            //XML + XSL -> Processing --> Output
            StreamSource xmlsource = new StreamSource(new File("courses.xml"));
            StreamSource xslsource = new StreamSource(new File("ex3.xsl"));

            TransformerFactory transformerFac = TransformerFactory.newInstance();
            Transformer transformer = transformerFac.newTransformer(xslsource);

            StreamResult resultingXML = new StreamResult(new File("result.xml"));
            transformer.transform(xmlsource, resultingXML);


        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}