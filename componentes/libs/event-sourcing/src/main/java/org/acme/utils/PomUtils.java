package org.acme.utils;

import lombok.Getter;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class PomUtils {

    @Getter
    private static String artifactId;

    static {
        try {
            File pomFile = new File("pom.xml");
            if (pomFile.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(pomFile);
                artifactId = document.getElementsByTagName("artifactId").item(0).getTextContent();
            } else {
                artifactId = "nome-nao-encontrado";
            }
        } catch (Exception e) {
            artifactId = "nome-nao-encontrado";
        }
    }

}