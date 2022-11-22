import java.nio.file.Path;

/**
 * This class handles the command ADD used to add books
 */
public class AddCmd extends LibraryCommand {

    /**
     * Constructor which calls the library constructor
     * @param argumentInput argument input as expected by the extending subclass.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /**
     * Saves the path parsed from the string by parseArguments
     */
    public Path parsedData;

    /**
     * Checks if the given path from which the books will be added has a 'path shape'
     *
     * It parses the given string into a path
     *
     * @param argumentInput path given with the command ADD from the terminal
     * @return true if the given path ends in .csv
     * or false if otherwise
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        //'Cleans' the argumentInput
        argumentInput = BookEntry.argumentInputNoUnnecessarySpaces(argumentInput);
        if(argumentInput.length() < 4){
            System.out.println("Invalid path");
            return false;
        }

        if((argumentInput.substring(argumentInput.length()-4)).equals(".csv")){
            //converts the string into a path
            parsedData = Path.of(argumentInput);
            return true;
        }else{
             System.out.println("Invalid path");
            return false;
        }
    }

    /**
     * Checks that the library data provided isn't empty
     *
     * loads the data from the path parsed by parseArguments
     *
     * @param data book data to check
     * @throws NullPointerException if the given library data is empty
     */
    @Override
    public void execute(LibraryData data) {
        if(data == null){
            throw new NullPointerException("The library data is empty");
        }
        data.loadData(parsedData);
    }
}
