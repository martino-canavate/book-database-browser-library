
/**
 * This class handles the command SEARCH used to search for books
 */
public class SearchCmd extends LibraryCommand {

    /**
     * Constructor which calls the library constructor
     * @param argumentInput argument input as expected by the extending subclass.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    /**
     * Saves the word that is to be searched
     */
    public String parsedSearch;

    /**
     * Checks that a word and only one word is provided to search
     *
     * @param argumentInput word given with the command SEARCH from terminal
     * @return true if one word is provided
     * or false if its none or more than one word
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput == null){
            System.out.println("Word must be provided in order to search");
            return false;
        }else if (argumentInput.length()< 1){
            System.out.println("Word must be provided in order to search");
            return false;
        }

        //'cleans' the argumentInput
        parsedSearch = BookEntry.argumentInputNoUnnecessarySpaces(argumentInput);
        boolean atLeastOneLetter = false;
        //Checks that there are no spaces so only one word
        for(int x = 0; x < parsedSearch.length(); x++){
            if (parsedSearch.charAt(x) == ' '){
                System.out.println("Please enter a single word to search");
                return false;
            }else if(Character.isLetter(parsedSearch.charAt(x))){
                atLeastOneLetter = true;
            }
        }
        //Checks that word is composed of letters.
        if (!atLeastOneLetter){
            System.out.println("Word must be provided in order to search");
            return false;
        }

        return true;
    }

    /**
     * This functions removes the symbols in front or after words that aren't letters
     * such as (), !, :, ?, ... in order to better match the searched word with the title
     *
     * @param unnormalizedWord word given in order to remove non letter symbols
     * @return the word properly written in letters only
     */

    public String normalizeWord(String unnormalizedWord){
        //Checks that unnormalizedWord isn't null
        if (unnormalizedWord.equals("")){
            return unnormalizedWord;
        }
        //Checks for the beginning of the actual letters, to usually remove "(" or " from words.
        int normalWorldBeginning;
        if (!Character.isLetter(unnormalizedWord.charAt(0))){
            normalWorldBeginning = 1;
        }else{
            normalWorldBeginning = 0;
        }
        //Checks for the end of the word's letters, to usually remove '!', '?', ':', ...
        for (int k = 1; k< unnormalizedWord.length(); k++){
            if (!Character.isLetter(unnormalizedWord.charAt(k))){
                return unnormalizedWord.substring(normalWorldBeginning, k);
            }
        }
        return unnormalizedWord.substring(normalWorldBeginning);
    }

    /**
     * Checks that the library data provided isn't empty
     *
     * Prints the title of the books which have a match with the searched word
     *
     * @param data book data to check
     * @throws NullPointerException if the given library data is empty
     */

    @Override
    public void execute(LibraryData data) {
        if(data == null){
            throw new NullPointerException("The library data is empty");
        }
        //self explanatory
        int booksFound = 0;

        //Iterates through the title words in each BookData entry to check for matches
        for(BookEntry bookInformation : data.getBookData()){
            //Gets title using getter
            String bookTitle = bookInformation.getTitle();
            //Gets the number of words in the title using the previously created function
            int sizeTitle = LibraryFileLoader.numberOfWords(bookTitle, ' ');
            String[] titleWords;
            //Creates a string array of each different word in the title using another previously created function
            titleWords = LibraryFileLoader.stringSeparator(bookTitle, ' ', sizeTitle);
            //Iterates through each word, if match found the title is printed
            for ( int j = 0; j< sizeTitle; j++){
                if (normalizeWord(titleWords[j].toLowerCase()).equals(parsedSearch.toLowerCase())){
                    booksFound = booksFound +1;
                    System.out.println(bookTitle);
                    break;
                }
            }
        }
        //self explanatory
        if(booksFound == 0){
            System.out.println("No hits found for search term: " + parsedSearch);
        }
    }
}
