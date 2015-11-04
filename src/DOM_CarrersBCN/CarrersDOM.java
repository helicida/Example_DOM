package DOM_CarrersBCN;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;

/**
 * Created by 46465442z on 03/11/15.
 */
public class CarrersDOM {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        //Hay que ir añadiendo las excepciones al método

        File inputFile = new File("/home/46465442z/IdeaProjects/Examples_DOM/src/DOM_CarrersBCN/carrer.xml");     //Ruta donde está localizado el archivo XML
        File outputFile = new File("/home/46465442z/IdeaProjects/Examples_DOM/src/DOM_CarrersBCN/carrer2.xml");     //Arxiu on escirurem

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //Hacemos els documentBuilders
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputFile);
        doc.getDocumentElement().normalize();    //Normalitzamos el archivo

        NodeList nl = doc.getElementsByTagName("ROW"); //Recibimos todos los elementos con la etiqueta "ROW"

        System.out.println("Nombre de carrers: "+ nl.getLength()); // Imprimrá elnumero de las calles
        System.out.println("---------------------------");

        /*

        for(int iterador = 0; iterador < nl.getLength(); iterador++){
            Element carrer = (Element) nl.item(iterador);   // hacemos un elemento carrer con nuestro nodeList y el iterador del for
            System.out.println("Els carrers son: " + carrer.getElementsByTagName("NOM_OFICIAL").item(0).getTextContent()); // Imprimiremos cada element del node list.
            // NOM_OFICIAL es uno de los elementos que leemos. Item 0 equivale a NOM_OFICIAL. Item1 equivaldría al primer elemento de NOM_OFICIAL.
        }

        */

        for(int iterador = 0; iterador < nl.getLength(); iterador ++){
            Element carrer = (Element) nl.item(iterador);
            System.out.println("Els carrers són : " + carrer.getElementsByTagName("NOM_OFICIAL").item(0).getTextContent());
            carrer.setAttribute("AtributProva", "valor");  // Amb això escribim elements. AtributProva es el nom del atribut i "valor" es el valor que li donem al atribut
        }
        escriureDocumentATextXml(doc, outputFile);
    }

    public static void escriureDocumentATextXml(Document doc, File file) throws TransformerException{
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(file);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
    }
}
