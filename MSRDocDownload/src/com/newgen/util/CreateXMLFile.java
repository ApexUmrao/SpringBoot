
package com.newgen.util;

import com.newgen.beans.DocumentDetails;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class CreateXMLFile {
    public static void createXMLFile(DocumentDetails objDocDetails,String DocXMLLocation){
        try {
           // Create an Employee object
//           DocumentDetails emp = new DocumentDetails(12312, "Passport", "123456");
           // Create JAXB context for the Employee class
           JAXBContext context = JAXBContext.newInstance(DocumentDetails.class);
           // Create Marshaller (for converting Java object to XML)
           Marshaller marshaller = context.createMarshaller();
           // Format XML output for readability
           marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           // Write to XML file
           File file = new File(DocXMLLocation);
           marshaller.marshal(objDocDetails, file);
           // Print XML to console
           marshaller.marshal(objDocDetails, System.out);
           System.out.println("XML file '"+DocXMLLocation+"' created successfully.");
       } catch (JAXBException e) {
           e.printStackTrace();
       }
    }
}
