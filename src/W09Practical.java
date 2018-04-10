import java.io.File;

/**
 * This class contains the code to interpret the user's command line arguments and call the appropriate query or handle the appropriate error
 */
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
            //If the item is --search, run the following
            if (args[i].equals("--search")) {
                //If --search is the last argument, set the search string to null
                if (i == args.length - 1) {
                    search = null;
                }
                //Otherwise set the search string to the next argument
                else {
                    search = args[i + 1];
                }
            }
            //If the item is --query run the following
            if (args[i].equals("--query")) {
                //If --query is the last argument, set the queryTerm string to null
                if (i == args.length - 1) {
                    queryTerm = null;
                }
                else {
                    //Add the next item to the query string
                    queryTerm = args[i + 1];
                    //Instantiate an integer called j as i+1
                    int j = i + 1;
                    //While j is less than the number of arguments -1, and the argument
                    //after j isn't --search or --cache and queryTerm isn't --search or --cache, add the j+1 argument to the queryTerm string
                    while (j < args.length - 1 && !args[j + 1].equals("--search") && !args[j + 1].equals("--cache") && !queryTerm.equals("--search") && !queryTerm.equals("--cache")) {
                        queryTerm += " " + args[j + 1];
                        j++;
                    }
                }
            }
            //If the item is --cache, run the following
            if (args[i].equals("--cache")) {
                //If --cache is the last argument, set the cache string to null
                if (i == args.length - 1) {
                    cache = null;
                }
                //Otherwise, set the cache string to the next argument
                else {
                    cache = args[i + 1];
                }
            }
        }
        //Create a new file object from the cache
        File folder = new File(cache);
        //Instantiate a boolean variable called noErrors to true
        Boolean noErrors = true;
        //If the queryTerm string is null, --cache, or --search (indicating a missing argument), print
        //the appropriate error message and set noErrors to false
        if (queryTerm == null || queryTerm.equals("--cache") || queryTerm.equals("--search")) {
            System.out.println("Missing value for --query\n"
                    + "Malformed command line arguments.");
            noErrors = false;
        }
        //If the search string is null, --cache, or --query (indicating a missing argument), print
        //the appropriate error message and set noErrors to false
        else if (search == null || search.equals("--cache") || search.equals("--query")) {
            System.out.println("Missing value for --search\n"
                    + "Malformed command line arguments.");
            noErrors = false;
        }
        //If the search string isn't any of the valid search options, print the appropriate error messages and set noErrors to false
        else if (!search.equals("author") && !search.equals("publication") && !search.equals("venue")) {
            System.out.println("Invalid value for --search: " + search);
            System.out.println("Malformed command line arguments.");
            noErrors = false;
        }
        //If the folder created from the cache string isn't a directory, print the appropriate error messages and set noErrors to false
        else if (!folder.isDirectory()) {
            System.out.println("Cache directory doesn't exist: " + cache);
            noErrors = false;
        }
        //If noErrors is true, call the appropriate query with the queryTerm string and folder as parameters
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
