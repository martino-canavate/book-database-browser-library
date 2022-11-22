import java.awt.print.Book;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * This is used to separate the title from the authors and so on from each fileContent entry
     * and to separate the different authors.
     *
     * @param wannabeSeparated is the string which we want to divide
     * @param separator is the character which indicates a separation
     * @param separationsNumber is the number of different strings wi will end up with
     * @return it returns a string array with each different separated string.
     *
     * no exceptions are to be expected as this function is not to be called anywhere else
     */

    public static String[] stringSeparator(String wannabeSeparated, Character separator, int separationsNumber){
        String[] returningArray = new String[separationsNumber];
        int separatorCount = 0;
        int previousPosition = -1;
        for (int v =0; v < wannabeSeparated.length(); v++){
            if (wannabeSeparated.charAt(v) == (separator)){
                returningArray[separatorCount] = wannabeSeparated.substring((previousPosition + 1), v);
                separatorCount++;
                previousPosition = v;
            }
        }
        returningArray[separationsNumber-1] = wannabeSeparated.substring((previousPosition + 1));
        return returningArray;
    }

    /**
     * Used to count number of different words in a string
     * in this case number of authors
     *
     * @param authorsString  string of words
     * @return number of different words
     */
    public static int numberOfWords(String authorsString, Character charCounter){
        int charCount = 0;
        for (int j = 0; j < authorsString.length(); j++){
            if (authorsString.charAt(j) == charCounter){
                charCount++;
            }
        }
        return(charCount +1);
    }

    /**
     * Number of arguments BookEntry needs
     */
    public final int BookEntryArguments = 5;

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     *@return array of BookEntry gotten from each entry of the fileContent instance field or
     *  an empty list if no book data has been loaded yet empty.
     */

    public List<BookEntry> parseFileContent() {
        ArrayList<BookEntry> newBookEntries = new ArrayList<BookEntry>();
        ArrayList<String> nullFileContent = new ArrayList<>();

        if (fileContent == null){
            System.err.println("ERROR: No content loaded before parsing.");
            return newBookEntries;
        }else if(fileContent.size() < 1){
            System.err.println("ERROR: No content loaded before parsing.");
            return newBookEntries;
        }
        
        for(int i =1; i < fileContent.size(); i++){
            //gets each BookEntry class variable
            String[] bookData;
            bookData = stringSeparator(fileContent.get(i), ',', BookEntryArguments);
          
            //separates the different authors from bookData[1]
            String[] listAuthors;
            listAuthors = stringSeparator(bookData[1], '-', numberOfWords(bookData[1], '-'));

            newBookEntries.add(new BookEntry(bookData[0], listAuthors, Float.parseFloat( bookData[2]), bookData[3], Integer.valueOf(bookData[4])));
        }
        return newBookEntries;
    }
}

