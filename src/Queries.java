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
     *
     * @param query
     * @param folder
     */
    public void venueSearch(String query, File folder) {
        try {
            String[] queryTerms = query.split(" ");
            String urlString = "http://dblp.org/search/venue/api?q=" + queryTerms[0];
            for (int i = 1; i < queryTerms.length; i++) {
                urlString += "+" + queryTerms[i];
            }
            urlString += "&format=xml&h=40&c=0";
            URL url = new URL(urlString);

            String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File cachedQuery = isCached(encodedURL, folder);

            if (cachedQuery == null) {
                doc = db.parse(url.openStream());
                writeFile(doc, folder, encodedURL);
            }
            else {
                doc = db.parse(cachedQuery);
            }
            NodeList venues = doc.getElementsByTagName("venue");
            for (int i = 0; i < venues.getLength(); i++) {
                System.out.println(venues.item(i).getTextContent());
            }

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void publicationSearch(String query, File folder) {
        try {
            String[] queryTerms = query.split(" ");
            String urlString = "http://dblp.org/search/publ/api?q=" + queryTerms[0];
            for (int i = 1; i < queryTerms.length; i++) {
                urlString += "+" + queryTerms[i];
            }
            urlString += "&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File cachedQuery = isCached(encodedURL, folder);

            if (cachedQuery == null) {
                doc = db.parse(url.openStream());
                writeFile(doc, folder, encodedURL);
            }
            else {
                doc = db.parse(cachedQuery);
            }

            NodeList titles = doc.getElementsByTagName("title");
            NodeList info = doc.getElementsByTagName("info");
            ArrayList<String> numberOfAuthors = new ArrayList<>();

            for (int i = 0; i < info.getLength(); i++) {
                if (info.item(i) instanceof Element) {
                    NodeList authors = ((Element) info.item(i)).getElementsByTagName("author");
                    if (authors.getLength() == 0) {
                        numberOfAuthors.add("0");
                    }
                    else {
                        numberOfAuthors.add(Integer.toString(authors.getLength()));
                    }
                }
            }

            for (int i = 0; i < titles.getLength(); i++) {
                System.out.println(titles.item(i).getTextContent() + " (number of authors: " + numberOfAuthors.get(i) + ")");
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void authorSearch(String query, File folder) {
        try {
            String[] queryTerms = query.split(" ");
            String urlString = "http://dblp.org/search/author/api?q=" + queryTerms[0];
            for (int i = 1; i < queryTerms.length; i++) {
                urlString += "+" + queryTerms[i];
            }
            urlString += "&format=xml&h=40&c=0";
            URL url = new URL(urlString);
            String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File cachedQuery = isCached(encodedURL, folder);

            if (cachedQuery == null) {
                doc = db.parse(url.openStream());
                writeFile(doc, folder, encodedURL);
            }
            else {
                doc = db.parse(cachedQuery);
            }
            NodeList info = doc.getElementsByTagName("info");
            for (int i = 0; i < info.getLength(); i++) {
                NodeList children = info.item(i).getChildNodes();
                String name = "";
                String publications;
                String coauthors;
                String authorURLString = "";
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
