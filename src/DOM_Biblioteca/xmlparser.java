package DOM_Biblioteca;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Created by sergi on 3/10/15.
 */
public class xmlparser {

    public static void main(String argv[]) {

        try {
            File archivoXML = new File("/home/46465442z/IdeaProjects/Examples_DOM/src/DOM_Biblioteca/biblio.xml"); // Ruta donde está localizado el archivo XML

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //Hacemos los documentBuilders
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXML);
            doc.getDocumentElement().normalize();   //Normalitzamos el arxiu

           /* System.out.println("Elemnto padre del que descienden todos los objetos :" + doc.getDocumentElement().getNodeName()); //Scamos el elemnto raíz y lo imprimimos */

            NodeList nList = doc.getElementsByTagName("libro"); // A partir de libro es cuando empieza un nuevo nodo y debes volver a leer

            System.out.println("----------------------------");

            for (int iterador = 0; iterador < nList.getLength(); iterador++) {      // Para cada nodo imprimimos en pantalla su información correspondiente

                Node nNode = nList.item(iterador);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.print("'" + eElement.getElementsByTagName("titulo").item(0).getTextContent() + "' te com a titol '");
                    System.out.print(eElement.getElementsByTagName("autor").item(0).getTextContent() + "' i va ser publicat l'any ");
                    System.out.print(eElement.getElementsByTagName("fecha").item(0).getTextContent());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
