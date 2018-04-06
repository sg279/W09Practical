import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;

public class Queries {

    public void venueSearch(String query){
        try{
            String[] queryTerms = query.split(" ");
            String urlString = "http://dblp.org/search/venue/api?q="+queryTerms[0];
            for (int i = 1; i<queryTerms.length; i++){
                urlString+="+"+queryTerms[i];
            }
            urlString+="&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            NodeList venues = doc.getElementsByTagName("venue");
            for(int i = 0; i<venues.getLength(); i++){
                System.out.println(venues.item(i).getTextContent());
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }

    public void publicationSearch(String query){
        try{
            String[] queryTerms = query.split(" ");
            String urlString = "http://dblp.org/search/publ/api?q="+queryTerms[0];
            for (int i = 1; i<queryTerms.length; i++){
                urlString+="+"+queryTerms[i];
            }
            urlString+="&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            NodeList authors = doc.getElementsByTagName("authors");
            NodeList titles = doc.getElementsByTagName("title");
            for(int i = 0; i<titles.getLength(); i++){
                System.out.println(titles.item(i).getTextContent()+", "+authors.item(i).getChildNodes().getLength()+" authors");
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }

    public void authorSearch(String query){
        try{
            String[] queryTerms = query.split(" ");
            String urlString = "http://dblp.org/search/author/api?q="+queryTerms[0];
            for (int i = 1; i<queryTerms.length; i++){
                urlString+="+"+queryTerms[i];
            }
            urlString+="&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            NodeList info = doc.getElementsByTagName("info");

            for(int i = 0; i<info.getLength(); i++){
                NodeList children = info.item(i).getChildNodes();
                URL authorURL = new URL(info.item(i).getChildNodes().item(1).getTextContent()+".xml");
                DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
                DocumentBuilder db1 = dbf1.newDocumentBuilder();
                Document doc1 = db1.parse(authorURL.openStream());
                NodeList coauthorsNodes = doc1.getElementsByTagName("co");
                NodeList publicationsNodes = doc1.getElementsByTagName("r");

                String name = info.item(i).getChildNodes().item(0).getTextContent();
                String publications = Integer.toString(publicationsNodes.getLength());
                String coauthors = Integer.toString(coauthorsNodes.getLength());

                System.out.println(name+" "+publications+" publications, "+coauthors+" coauthors");
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
}
