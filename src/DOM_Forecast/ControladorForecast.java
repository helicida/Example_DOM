package DOM_Forecast;

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
import java.util.Scanner;

/**
 * Created by sergi on 3/11/15.
 */
public class ControladorForecast {

    public static String rutaInput = "/home/sergi/IdeaProjects/Example_DOM/src/DOM_Forecast/forecast.xml";
    public static String rutaOutput = "/home/sergi/IdeaProjects/Example_DOM/src/DOM_Forecast/forecast2.xml";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Scanner teclat = new Scanner(System.in);    //Fem scanner

        System.out.println("Introdueix la ruta del arixu d'on vols llegir");
            teclat.next();

        System.out.println("Introdueix la ruta del arxiu de sortida");
            teclat.next();

        teclat.close(); //Tanquem el scanner

        File outputFile = new File(rutaOutput); //Arxiu on escirurem
        File inputFile = new File(rutaInput);   //Ruta donde está localizado el archivo XML

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //Hacemos els documentBuilders
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(inputFile);
        doc.getDocumentElement().normalize();    //Normalitzamos el archivo

            NodeList nodeCiudad = doc.getElementsByTagName("location"); //Recibimos todos los elementos que pertenecen a la etiqueta "weather"
            Element raiz = (Element) nodeCiudad.item(0);   // hacemos un elemento carrer con nuestro nodeList y el iterador del for
        
            System.out.println("Lugar del tiempo: " + raiz.getElementsByTagName("name").item(0).getTextContent() + ", " + raiz.getElementsByTagName("country").item(0).getTextContent()); // Imprimimos el nombre del lugar
            System.out.println("---------------------------");

        NodeList nodeTiempo =  doc.getElementsByTagName("time");    //Listamos todos los elementos que pertenezcan a time

        for(int iterador = 0; iterador < nodeTiempo.getLength(); iterador ++){
            Element time = (Element) nodeTiempo.item(iterador);
            double ventMPS = Double.parseDouble(time.getElementsByTagName("windSpeed").item(0).getAttributes().getNamedItem("mps").getNodeValue().toString());  //conseguimos el windSpeed y lo pasamos a double
            double ventKPH = ventMPS * 3.6;
            System.out.println("----------------------------------------------------------------");
            System.out.println("Fecha: " + time.getAttributes().getNamedItem("from").getNodeValue() + " a " + time.getAttributes().getNamedItem("to").getNodeValue());
            System.out.println("Temperatura: " + time.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue() + "º");
            System.out.println("Humedad: " + time.getElementsByTagName("humidity").item(0).getAttributes().getNamedItem("value").getNodeValue() + "%");
            System.out.println("Reporte: " + toSpanish(time.getElementsByTagName("clouds").item(0).getAttributes().getNamedItem("value").getNodeValue()));
            System.out.println("Velocidad del viento: " + ventKPH + " kmh | " + ventMPS + " mps");

            //Usamos el mismo for porque la longitud de nodeTiempo y nodeVelocidad es la misma ya que hay una velocidad del viento para cada fecha
            NodeList nodeVelocidad = doc.getElementsByTagName("windSpeed");
            Element windSpeed = (Element) nodeVelocidad.item(iterador);
            windSpeed.setAttribute("kph", String.valueOf(ventKPH)); //En cada elemento windSpeed se añadira un atributo "kph" con el valor del viento en KPH que calculamos antes
        }

        escriureDocumentATextXml(doc, outputFile);
    }

    public static void escriureDocumentATextXml(Document doc, File outputFile) throws TransformerException {

        Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(outputFile);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
    }

    public static String toSpanish (String ingles) {
        if (ingles.equals("scattered clouds")) return "Pocas nubes";
        else if (ingles.equals("broken clouds")) return "Medianamente nublado";
        else if (ingles.equals("light rain")) return "lluvia ligera";
        else if (ingles.equals("overcast clouds")) return "Cielo encapotado";
        else if (ingles.equals("few clouds")) return "Nublado";
        else if (ingles.equals("clear sky")) return "Cielo despejado";
        else return "string no reconocido";
    }
}
