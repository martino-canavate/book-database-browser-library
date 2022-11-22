import java.nio.file.Path;
import java.util.Arrays;

/**
 * This class handles the command LIST used provide book information
 */
public class ListCmd extends LibraryCommand {

    /**
     * Constructor which calls the library constructor
     * @param argumentInput argument input as expected by the extending subclass.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Saves the parsed argument to distinguish between long and short
     */
    public String parsedList;

    /**
     * Checks if the given argument is either nothing, long or short
     *
     * It saves the choice in parsedList
     *
     * @param argumentInput argument given with the command LIST from the terminal
     * @return true if the given argument is either nothing, long or short
     * or false if otherwise
     */

    @Override
    protected boolean parseArguments(String argumentInput) {
        //'Cleans' the argumentInput
        argumentInput = BookEntry.argumentInputNoUnnecessarySpaces(argumentInput);
        if((argumentInput.equals("short") || (argumentInput.equals("")))){
            parsedList = "short";
            return true;
        }else if(argumentInput.equals("long")) {
            parsedList = "long";
            return true;
        }else{
            System.out.println("Invalid argument");
            return false;
        }
    }

    /**
     * Checks that the library data provided isn't empty
     *
     * Print either the title of books if parsedList is short or all the information
     * if parsedList is long
     *
     * @param data book data to check
     * @throws NullPointerException if the given library data is empty
     */

    @Override
    public void execute(LibraryData data) {
        if(data == null){
            throw new NullPointerException("The library data is empty");
        }
        System.out.println(data.getBookData().size() + " books in library:");
        for(BookEntry bookInformation : data.getBookData()){
            if (parsedList == "short"){
                System.out.println(bookInformation.getTitle());
            }else{
                System.out.println(bookInformation.toString()+ "\n");
            }
        }
    }
}
