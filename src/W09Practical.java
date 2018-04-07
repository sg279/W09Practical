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
        File[] listOfFiles;

        for(int i =0; i<args.length; i++){
            if (args[i].equals("--search")){
                search=args[i+1];
            }
            if (args[i].equals("--query")){
                queryTerm=args[i+1];
            }
            if (args[i].equals("--cache")){
                cache=args[i+1];
            }
        }
        File folder = new File(cache);
        if(search!="author"&&search!="publication"&&search!="venue"){
            System.out.println("Invalid search type!");
        }
        if (!folder.isDirectory()){
            System.out.println("Invalid cache location!");
        }
        else if (search.equals("author")){
            query.authorSearch(queryTerm);
        }
        else if (search.equals("venue")){
            query.venueSearch(queryTerm);
        }

    }


}
