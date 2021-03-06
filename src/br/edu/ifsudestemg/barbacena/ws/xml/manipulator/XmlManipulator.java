package br.edu.ifsudestemg.barbacena.ws.xml.manipulator;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

public class XmlManipulator {

    private XmlManipulator() {
        // prevents instantiation
    }

    public static Document getContent(String xmlPath) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();

            return builder.parse(xmlPath);
        } catch (Exception ignored) {
            return null;
        }
    }
}
