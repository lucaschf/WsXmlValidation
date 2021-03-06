package br.edu.ifsudestemg.barbacena.ws.xml;

import br.edu.ifsudestemg.barbacena.ws.xml.manipulator.XmlManipulator;
import br.edu.ifsudestemg.barbacena.ws.xml.validator.XmlValidator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
    private static final String xmlPath = "resources/library.xml";
    private static final String xsdPath = "resources/library.xsd";

    public static void main(String[] args) {
        testValidation();
        testManipulation();
    }

    private static void testValidation() {
        System.out.println("DTD validation:\n");
        var result = XmlValidator.validate(xmlPath);
        System.out.println("Xml " + (result.isValid() ? "valid" : "invalid"));

        if (!result.getMessages().isEmpty()) {
            System.out.println("Messages: ");
            System.out.print("\t");
            System.out.println(String.join("\n\t", result.getMessages()));
        }

        System.out.println("\n\nXSD validation:\n");
        System.out.println("Xml " + (XmlValidator.isValid(xmlPath, xsdPath) ? "valid" : "invalid"));
    }

    private static void testManipulation() {

        var document = XmlManipulator.getContent(xmlPath);
        if (document == null) {
            System.out.println("Failed to read file");
            return;
        }

        Element root;
        NodeList child;
        NodeList books;

        System.out.println("Getting root(document.getDocumentElement())");
        root = document.getDocumentElement();

        System.out.printf("Root tag (node.getTagName()): %s\n%n", root.getTagName());

        System.out.printf("Root content (node.getTextContent()) :%s\n%n", root.getTextContent());

        System.out.println("Get root children");
        child = root.getChildNodes();
        System.out.printf("Root children count: %d\n%n", child.getLength());

        System.out.println("Child - " + child.item(0).getLocalName());

        books = document.getElementsByTagName("livro");
        System.out.printf("Search for a specific child:%s\n%n", books.item(0));

        System.out.println("Get a child index");
        System.out.println(document.compareDocumentPosition(books.item(0)) + "\n");

        System.out.println("List books names");

        for (int i = 0; i < books.getLength(); i++)
            System.out.println(books.item(i));
    }
}
