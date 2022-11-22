import java.util.ArrayList;
import java.util.List;

public class GroupCmd extends LibraryCommand {

    /**
     * Constructor which calls the library constructor
     * @param argumentInput argument input as expected by the extending subclass.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    public String parsedGroup;

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
        if((argumentInput.equals("TITLE"))){
            parsedGroup = "TITLE";
            return true;
        }else if(argumentInput.equals("AUTHOR")) {
            parsedGroup = "AUTHOR";
            return true;
        }else{
            System.out.println("Invalid argument");
            return false;
        }
    }

    /**
     * Function that prints the books by alphabetical order of their first letter and
     * finally prints the books whose title starts by a number
     *
     * @param data book data
     */

    public void titleIterator(LibraryData data){
        //for each letter
        for(int z = 10; z <36; z++){
            boolean firstBook = true;
            //checks every book
            for(BookEntry bookInformation : data.getBookData()){
                String firstLetter = bookInformation.getTitle().substring(0,1).toLowerCase();
                boolean isMatch = firstLetter.equals(Character.toString(Character.forDigit(z, 36)));
                //prints the book if it matches the letter z with its first letter
                if(isMatch && (firstBook)) {
                    System.out.println("## "+ Character.toUpperCase(Character.forDigit(z, 36)));
                    System.out.println("    " + bookInformation.getTitle());
                    firstBook = false;
                }else if(isMatch){
                    System.out.println("    " + bookInformation.getTitle());
                }
            }
        }

        for(int m = 0; m<10; m++){
            boolean firstBook = true;
            for(BookEntry bookInformation : data.getBookData()){
                boolean isMatch = bookInformation.getTitle().substring(0,1).toLowerCase().equals(String.valueOf(m));
                if(isMatch && (firstBook)){
                    System.out.println("## [0-9]");
                    System.out.println("    " + bookInformation.getTitle());
                    firstBook = false;
                }else if(isMatch){
                    System.out.println("    " + bookInformation.getTitle());
                }
            }
        }

    }

    /**
     * Helper function that creates a list of the authors by alphabetical order of their
     * first letter, and then prints the book of each author in order
     *
     * @param data book data
     */

    public void authorIterator(LibraryData data){
        //Ordered list of authors by alphabetical order of first letter
        List<String> orderedAuthors = new ArrayList<String>();
        //Cheks for each letter in the alphabet
        for(int z = 10; z <36; z++){
            //Checks for each book
            for(BookEntry bookInformation : data.getBookData()){
                //Checks for each author
                for (String bookAuthor : bookInformation.getAuthors()){
                    //First letter of author
                String firstLetter = bookAuthor.substring(0,1).toLowerCase();
                boolean isMatch = firstLetter.equals(Character.toString(Character.forDigit(z, 36)));
                    if(isMatch && (!orderedAuthors.contains(bookAuthor))) {
                        orderedAuthors.add(bookAuthor);
                    }
                }
            }
        }
        //prints every book of each author
        for (String orderedAuthor : orderedAuthors) {
            System.out.println("## " + orderedAuthor);
            for (BookEntry bookInformation : data.getBookData()) {
                for (String bookAuthor : bookInformation.getAuthors()) {
                    if (bookAuthor.equals(orderedAuthor)) {
                        System.out.println("    " + bookInformation.getTitle());
                    }
                }
            }
        }
    }

    /**
     * Checks that the library data provided isn't empty
     *
     * Print the title of books either by letters in alphabetical order, or by authors
     * in alphabetical order, depending in the value of parsedGroup
     *
     * @param data book data to check
     * @throws NullPointerException if the given library data is empty
     */

    @Override
    public void execute(LibraryData data) {
        if(data == null){
            throw new NullPointerException("The library data is empty");
        }

        if(data.getBookData().size()<1){
            System.out.println("The library has no book entries.");
        }else{
            System.out.println("Grouped data by " + parsedGroup);
        }

        if (parsedGroup.equals("TITLE")){
            //Helper function to print the titles by first letter of title
            titleIterator(data);
        }else if(parsedGroup.equals("AUTHOR")){
            //Helper function to print the titles by first letter of author
            authorIterator(data);
        }
    }
}
