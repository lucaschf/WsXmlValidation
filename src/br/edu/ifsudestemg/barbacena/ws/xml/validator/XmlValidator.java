package br.edu.ifsudestemg.barbacena.ws.xml.validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlValidator {

    private XmlValidator() {
        // prevent instantiation
    }

    public static ValidationResult validate(String xmlPath) {

        ValidationResult result = new ValidationResult(true);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        try {
            var builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new ErrorHandler() {

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    result.addMessage(generateErrorMessage("Warning", exception));
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    result.addMessage(generateErrorMessage("Error", exception));
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    result.addMessage(generateErrorMessage("Fatal error", exception));
                }
            });

            builder.parse(new File(xmlPath));

        } catch (Exception e) {
            e.printStackTrace();
            result.addMessage("Error validating");
            result.setValid(false);
        }

        return result;
    }

    private static String generateErrorMessage(String type, SAXParseException exception) {
        return String.format("%s at line %d - %s", type, exception.getLineNumber(), exception.getLocalizedMessage());
    }

    public static boolean isValid(String xmlPath, String xsdPath){
        try {

            File schemaFile = new File(xsdPath) ;
            Source xmlFile = new StreamSource(new File(xmlPath));

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();

            validator.validate(xmlFile);

            return true;

        } catch (SAXException| IOException e) {
            return  false;
        }
    }

    public static class ValidationResult {
        boolean valid;

        ArrayList<String> messages = new ArrayList<>();

        public ValidationResult() {

        }

        public ValidationResult(boolean valid) {
            this.valid = valid;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public void addMessage(String message) {
            messages.add(message);
        }

        public ArrayList<String> getMessages() {
            return messages;
        }
    }
}
