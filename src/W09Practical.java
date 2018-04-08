import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import java.io.File;
import java.net.URL;

public class W09Practical {

    public static void main (String[] args){
        Queries query = new Queries();
        //query.venueSearch("international school");
        //query.publicationSearch("computers");
        //query.authorSearch("John");

        String search="";
        String queryTerm="";
        String cache="";

        for(int i =0; i<args.length; i++){
            if (args[i].equals("--search")){
                search=args[i+1];
            }
            if (args[i].equals("--query")){
                queryTerm = args[i+1];
                int j=i+1;
                while (j<args.length-1&&!args[j+1].equals("--search")&&!args[j+1].equals("--cache")){
                    queryTerm+=" "+args[j+1];
                    j++;
                }
            }
            if (args[i].equals("--cache")){
                cache=args[i+1];
            }
        }
        File folder = new File(cache);
        if(!queryTerm.equals(null)){
            System.out.println("Missing value for --query\n" +
                    "Malformed command line arguments.");
        }
        if(!search.equals(null)){
            System.out.println("Missing value for --search\n" +
                    "Malformed command line arguments.");
        }
        else if(!search.equals("author")&&!search.equals("publication")&&!search.equals("venue")){
            System.out.println("Invalid value for --search: "+search);
            System.out.println("Malformed command line arguments.");
        }
        if (!folder.isDirectory()){
            System.out.println("Cache directory doesn't exist: "+queryTerm);
        }
        else{
            if (search.equals("author")){
                query.authorSearch(queryTerm, folder);
            }
            else if (search.equals("venue")){
                query.venueSearch(queryTerm, folder);
            }
            else if (search.equals("publication")){
                query.publicationSearch(queryTerm, folder);
            }
        }

    }


}
