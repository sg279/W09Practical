import java.io.File;

public class W09Practical {

    public static void main(String[] args) {
        //Instantiate a new instance of the Queries class called query
        Queries query = new Queries();
        //Instantiate empty strings called search, queryTerm, and cache
        String search = "";
        String queryTerm = "";
        String cache = "";

        //For each item in the arguments array
        for (int i = 0; i < args.length; i++) {
            //If the item is --search, set the search string to the next argument
            if (args[i].equals("--search")) {
                if (i == args.length - 1) {
                    search = null;
                }
                else {
                    search = args[i + 1];
                }
            }
            //If the item is --query run the following
            if (args[i].equals("--query")) {
                if (i == args.length - 1) {
                    queryTerm = null;
                }
                else {
                    //Add the next item to the query string
                    queryTerm = args[i + 1];
                    //Instantiate an integer called j as i+1
                    int j = i + 1;
                    //While j is less than the number of arguments -1, and the argument
                    //after j isn't --search or --cache, add the j+1 argument to the queryTerm string
                    while (j < args.length - 1 && !args[j + 1].equals("--search") && !args[j + 1].equals("--cache")) {
                        queryTerm += " " + args[j + 1];
                        j++;
                    }
                }
            }
            //If the item is --cache, set the cache string to the next item
            if (args[i].equals("--cache")) {
                if (i == args.length - 1) {
                    cache = null;
                }
                else {
                    cache = args[i + 1];
                }
            }
        }
        //Create a new file object from the cache
        File folder = new File(cache);

        Boolean noErrors = true;

        if (queryTerm == null || queryTerm.equals("--cache") || queryTerm.equals("--search")) {
            System.out.println("Missing value for --query\n"
                    + "Malformed command line arguments.");
            noErrors = false;
        }
        else if (search == null || search.equals("--cache") || search.equals("--query")) {
            System.out.println("Missing value for --search\n"
                    + "Malformed command line arguments.");
            noErrors = false;
        }
        else if (!search.equals("author") && !search.equals("publication") && !search.equals("venue")) {
            System.out.println("Invalid value for --search: " + search);
            System.out.println("Malformed command line arguments.");
            noErrors = false;
        }
        else if (!folder.isDirectory()) {
            System.out.println("Cache directory doesn't exist: " + cache);
            noErrors = false;
        }
        if (noErrors) {
            if (search.equals("author")) {
                query.authorSearch(queryTerm, folder);
            }
            else if (search.equals("venue")) {
                query.venueSearch(queryTerm, folder);
            }
            else if (search.equals("publication")) {
                query.publicationSearch(queryTerm, folder);
            }
        }

    }


}
