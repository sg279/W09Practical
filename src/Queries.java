import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class contains all of the queries that the user may call as well as methods that are used by them
 */
public class Queries {

    /**
     *This method prints out all of the venues that are returned from the query
     *
     * @param query The term to search for
     * @param folder The cache location
     */
    public void venueSearch(String query, File folder) {
        //Try the following
        try {
            //Instantiate an array list of strings as the query term split by spaces
            String[] queryTerms = query.split(" ");
            //Instantiate a string called urlString as the start of the url and the first item in the queryTerms array
            String urlString = "http://dblp.org/search/venue/api?q=" + queryTerms[0];
            //For each item in the query array after the first, add a + and the item to the end of the url string
            for (int i = 1; i < queryTerms.length; i++) {
                urlString += "+" + queryTerms[i];
            }
            //Add the rest of the required formatting to the urlString and parse it to a URL object called url
            urlString += "&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            //Instantiate a string called encodedURL from the urlString object and the java URLEncoder class's encode method, with UTF-8 encoding
            String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            //Create a new Document object called doc, a new instance of a DocumentBuilderFactory object, and a new DocumentBuilder using the factory
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Create a new File object from the isCached method using the encodedURL string and folder as parameters
            File cachedQuery = isCached(encodedURL, folder);
            //If the cachedQuery file is null (because there is no cached file for that query), run the following
            if (cachedQuery == null) {
                //Create the contents of the doc variable by parsing an opened URL stream (from the url variable) to the document builder
                doc = db.parse(url.openStream());
                //Call the writeFile method with the doc, folder, and encodedURL as parameters
                writeFile(doc, folder, encodedURL);
            }
            //If not, set the contents of the doc to the cachedQuery file parsed to the document builder
            else {
                doc = db.parse(cachedQuery);
            }
            //Create a node list called venues of all nodes in the document with the tag venue
            NodeList venues = doc.getElementsByTagName("venue");
            //For each item in the node list, print the text content of the tags
            for (int i = 0; i < venues.getLength(); i++) {
                System.out.println(venues.item(i).getTextContent());
            }

        }
        //If an exception is thrown, print the stack trace
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method prints out the names of all publications that are returned from the query along with how many authors worked on it
     *
     * @param query The term to search for
     * @param folder The cache location
     */
    public void publicationSearch(String query, File folder) {
        //Try the following
        try {
            //Instantiate an array list of strings as the query term split by spaces
            String[] queryTerms = query.split(" ");
            //Instantiate a string called urlString as the start of the url and the first item in the queryTerms array
            String urlString = "http://dblp.org/search/publ/api?q=" + queryTerms[0];
            //For each item in the query array after the first, add a + and the item to the end of the url string
            for (int i = 1; i < queryTerms.length; i++) {
                urlString += "+" + queryTerms[i];
            }
            //Add the rest of the required formatting to the urlString and parse it to a URL object called url
            urlString += "&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            //Instantiate a string called encodedURL from the urlString object and the java URLEncoder class's encode method, with UTF-8 encoding
            String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            //Create a new Document object called doc, a new instance of a DocumentBuilderFactory object, and a new DocumentBuilder using the factory
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Create a new File object from the isCached method using the encodedURL string and folder as parameters
            File cachedQuery = isCached(encodedURL, folder);
            //If the cachedQuery file is null (because there is no cached file for that query), run the following
            if (cachedQuery == null) {
                //Create the contents of the doc variable by parsing an opened URL stream (from the url variable) to the document builder
                doc = db.parse(url.openStream());
                //Call the writeFile method with the doc, folder, and encodedURL as parameters
                writeFile(doc, folder, encodedURL);
            }
            //If not, set the contents of the doc to the cachedQuery file parsed to the document builder
            else {
                doc = db.parse(cachedQuery);
            }
            //Create node lists called titles and info, containing all nodes in the document with the tags title and info respectively
            NodeList titles = doc.getElementsByTagName("title");
            NodeList info = doc.getElementsByTagName("info");
            //Create an array list of strings called numberOfAuthors
            ArrayList<String> numberOfAuthors = new ArrayList<>();
            //For each item in the info node list, do the following
            for (int i = 0; i < info.getLength(); i++) {
                //Check if the item is an instance of an Element object so it can use element object methods
                if (info.item(i) instanceof Element) {
                    //Create a node list called authors from all nodes with the tag author which are children of the i info node
                    NodeList authors = ((Element) info.item(i)).getElementsByTagName("author");
                    //If there are no items in the authors node list, add 0 to the numberOfAuthors array list
                    if (authors.getLength() == 0) {
                        numberOfAuthors.add("0");
                    }
                    //Otherwise, add the number of nodes in the authors node list parsed to a string to the numberOfAuthors array list
                    else {
                        numberOfAuthors.add(Integer.toString(authors.getLength()));
                    }
                }
            }
            //For each item in the titles list, print the text content of the item and the number of authors
            for (int i = 0; i < titles.getLength(); i++) {
                System.out.println(titles.item(i).getTextContent() + " (number of authors: " + numberOfAuthors.get(i) + ")");
            }
        }
        //If an exception is thrown print the stack trace
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method prints out the name of every author returned from the query along with the number of publications and co authors they have
     *
     * @param query The term to search for
     * @param folder The cache location
     */
    public void authorSearch(String query, File folder) {
        //Try the following
        try {
            //Instantiate an array list of strings as the query term split by spaces
            String[] queryTerms = query.split(" ");
            //Instantiate a string called urlString as the start of the url and the first item in the queryTerms array
            String urlString = "http://dblp.org/search/author/api?q=" + queryTerms[0];
            //For each item in the query array after the first, add a + and the item to the end of the url string
            for (int i = 1; i < queryTerms.length; i++) {
                urlString += "+" + queryTerms[i];
            }
            //Add the rest of the required formatting to the urlString and parse it to a URL object called url
            urlString += "&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            //Instantiate a string called encodedURL from the urlString object and the java URLEncoder class's encode method, with UTF-8 encoding
            String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            //Create a new Document object called doc, a new instance of a DocumentBuilderFactory object, and a new DocumentBuilder using the factory
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Create a new File object from the isCached method using the encodedURL string and folder as parameters
            File cachedQuery = isCached(encodedURL, folder);
            //If the cachedQuery file is null (because there is no cached file for that query), run the following
            if (cachedQuery == null) {
                //Create the contents of the doc variable by parsing an opened URL stream (from the url variable) to the document builder
                doc = db.parse(url.openStream());
                //Call the writeFile method with the doc, folder, and encodedURL as parameters
                writeFile(doc, folder, encodedURL);
            }
            //If not, set the contents of the doc to the cachedQuery file parsed to the document builder
            else {
                doc = db.parse(cachedQuery);
            }
            //Create a node list called venues of all nodes in the document with the tag info
            NodeList info = doc.getElementsByTagName("info");
            //For each item in the info list do the following
            for (int i = 0; i < info.getLength(); i++) {
                //Create a node list from the child nodes of the info item
                NodeList children = info.item(i).getChildNodes();
                //Instantiate an empty string called name
                String name = "";
                //Define strings called publications and coauthors
                String publications;
                String coauthors;
                //Instantiate an empty string called authorURLString
                String authorURLString = "";
                //For each item 
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j) instanceof Element && ((Element) children.item(j)).getTagName().equals("author")) {
                        name = children.item(j).getTextContent();
                    }
                    if (children.item(j) instanceof Element && ((Element) children.item(j)).getTagName().equals("url")) {
                        authorURLString = children.item(j).getTextContent();
                    }
                }
                URL authorURL = new URL(authorURLString + ".xml");
                DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
                DocumentBuilder db1 = dbf1.newDocumentBuilder();
                Document doc1 = db1.parse(authorURL.openStream());
                NodeList coauthorsNodes = doc1.getElementsByTagName("co");
                NodeList publicationsNodes = doc1.getElementsByTagName("r");
                publications = Integer.toString(publicationsNodes.getLength());
                coauthors = Integer.toString(coauthorsNodes.getLength());
                System.out.println(name + " - " + publications + " publications with " + coauthors + " co-authors.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    private File isCached(String encodedUrl, File cacheFolder) {
        File[] files = cacheFolder.listFiles();
        File cachedFile = null;
        for (File file: files
             ) {
            String n = file.getName();
            if (n.equals(encodedUrl)) {
                cachedFile = file;
            }
        }
        return cachedFile;
    }

    private void writeFile(Document doc, File folder, String fileName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(folder.getAbsolutePath() + "/" + fileName));
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
