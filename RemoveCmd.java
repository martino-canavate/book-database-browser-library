/**
 * This class handles the command REMOVE used to remove books
 */
public class RemoveCmd extends LibraryCommand {
    /**
     * Constructor which calls the library constructor
     * @param argumentInput argument input as expected by the extending subclass.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    /**
     * Saves the first parsed part of the argumentInput
     */
    public int parsedRemove;
    /**
     * Saves the second parsed part of the argumentInput
     */
    public String parsedRemoveTwo;

    /**
     * Helper function of fixedArgumentInputR, used to identify either the title or the author after
     * it's been determined what the user wants to remove.
     *
     * @param beDefined word that needs to get rid of extra spaces at the beginning or end
     * @param starter 6 or 5, it depends on the length of "author" or "title", it's used to start looking for the word afterwards
     * @param argument1Beginning beginning of the word "title" or "author" in the argument input of the command REMOVE
     * @param argumentEnd int that represents the character position of the last letter in the argument input,
     *                    it's original value is the maximum it can be,it is defined properly in this function
     * @param returner either "AUTHOR" or "TITLE", used to form the fixed argument input
     * @return
     */
    public String titleOrAuthorDefiner(String beDefined, int starter, int argument1Beginning, int argumentEnd, String returner){
        //Iterates through each character after the word "TITLE" or "AUTHOR"(argument1Beginning + starter is the last character of either "TITLE" or "AUTHOR")
        for (int z =argument1Beginning + starter; z< beDefined.length(); z++){
            //WHen first letter that's not space is found
            if (!(beDefined.charAt(z) == ' ')){
                //Check for the proper end of sentence
                if ((beDefined.charAt(beDefined.length()-2) == ' ')){
                    argumentEnd = beDefined.length()-2;
                }else if(beDefined.length()-1 == ' '){
                    argumentEnd = beDefined.length()-1;
                }
                //Check for the proper end of sentence
                for (int k = z; k< beDefined.length()-2; k++ ) {
                    if ((beDefined.charAt(k) == ' ') && (beDefined.charAt(k + 1) == ' ') && (beDefined.charAt(k + 2) == ' ')) {
                        argumentEnd = k;
                    }
                }
                //Returns the fixed argument input if title or author is proper, if not it returns a sentence that won't be accepted in parseArguments
                if (z < argumentEnd){
                    return(returner+ " " + beDefined.substring(z, argumentEnd));
                }else{
                    return ("Not a correct argument");
                }
            }
        }
        return ("Not a correct argument");
    }

    /**
     * It fixes the argument input in order to make it readable to parseArguments
     *
     * @param parsedInput the argument of the command REMOVE
     * @return the fixed argument, removed all unnecessary spaces
     */

    public String fixedArgumentInputR(String parsedInput){
        int argument1Beginning = 0;
        int argument2End = parsedInput.length();
        //Iterates through the argument
        for (int i = 0; i < (parsedInput.length() - 6); i++){
            //finds if the user wants to remove by title or by author
            if (parsedInput.substring(i, i+5).equals("TITLE")){
                argument1Beginning = i;
                return titleOrAuthorDefiner(parsedInput,5, argument1Beginning, argument2End, "TITLE");
            }else if(parsedInput.substring(i, i+6).equals("AUTHOR")){
                argument1Beginning = i;
                return titleOrAuthorDefiner(parsedInput, 6, argument1Beginning, argument2End, "AUTHOR");
            }
        }
        //if argument is not fixable it returns a sentence that won't be accepted in parseArguments
        return ("Not a correct argument");
    }

    /**
     * Checks that either TITLE or AUTHOR is part of the argumentInput.
     *
     * @param argumentInput argument given with the command REMOVE from the terminal from terminal
     * @return true if either TITLE or AUTHOR is in the argumentInput
     * or false if it isn't
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput == null){
            System.out.println("Word must be provided in order to search");
            return false;
        }else if(argumentInput.length() < 8){
            System.out.println("Word must be provided in order to search");
            return false;
        }

        //'Cleans' the argumentInput
        argumentInput = fixedArgumentInputR(argumentInput);

        if(argumentInput.substring(0,5).equals("TITLE")){
            parsedRemove = 1;
            parsedRemoveTwo = argumentInput.substring(6);
            return true;
        }else if(argumentInput.substring(0,6).equals("AUTHOR")){
            parsedRemove = 2;
            parsedRemoveTwo = argumentInput.substring(7);
            return true;
        }else{
            System.out.println("Invalid argument");
            return false;
        }
    }

    /**
     * Checks that the library data provided isn't empty
     *
     * Removes the book whose title or author, depending on parsedRemove, matches
     * parsedRemoveTwo
     *
     * @param data book data to check
     * @throws NullPointerException if the given library data is empty
     */

    @Override
    public void execute(LibraryData data) {
        if(data == null){
            throw new NullPointerException("The library data is empty");
        }
        if (parsedRemove == 1){
            //Iterates through all the book's titles until a match is found and removed
            boolean foundBook = false;
            for(int i = 0; i< data.getBookData().size(); i++) {
                if (data.getBookData().get(i).getTitle().equals(parsedRemoveTwo)) {
                    data.getBookData().remove(i);
                    System.out.println(parsedRemoveTwo + ": removed successfully.");
                    foundBook = true;
                    break;
                }
            }
            if (!foundBook){
                System.out.println(parsedRemoveTwo + ": not found.");
            }
        }else if(parsedRemove == 2){
            //number of books by the author
            int numberBooks =0;
            //Iterates through all the book's authors removing the ones whose author is
            //the mentioned in parseRemoveTwo
            for(int i = 0; i< data.getBookData().size(); i++) {
                for(int k = 0; k< data.getBookData().get(i).getAuthors().length; k++){
                    if (data.getBookData().get(i).getAuthors()[k].equals(parsedRemoveTwo)) {
                        data.getBookData().remove(i);
                        numberBooks++;
                    }
                }
            }
            System.out.println(numberBooks + " books removed for author: " + parsedRemoveTwo);
        }
    }
}
