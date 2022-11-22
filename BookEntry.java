import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 *
 */

public class BookEntry {
    private final String title;
    private final String[] authors;
    private final float rating;
    private final String ISBN;
    private final int pages;

    /**
     * Constructor for the book entry class.
     * All the following parameters are parsed from fileContent by parseFileContent
     * @param title is a string representing the title
     * @param authors is a string array representing the authors
     * @param rating is a float representing the rating
     * @param isbn is a string representing the isbn code
     * @param pages is an int representing the number of pages
     * @throws NullPointerException if one of the string values is empty
     * @throws IllegalArgumentException if the rating isn't inside the bounds set or if the number of pages isn't positive
     */
    public BookEntry(String title, String[] authors, float rating, String isbn, int pages) {
        for(String author : authors){
            if(author == null){
                throw new NullPointerException("One of the authors is empty");
            }
        }
        if (title == null||isbn == null){
            throw new NullPointerException("One of the entries is empty");
        }else if(!(rating<=5 && rating >= 0)){
            throw new IllegalArgumentException("The rating isn't between 0 and 5");
        }else if(!(pages>=0)){
            throw new IllegalArgumentException("The number of pages is negative");

        }else{
            this.title = title;
            this.authors = authors;
            this.rating = rating;
            this.ISBN = isbn;
            this.pages = pages;
        }
    }

    /**
     * This are the getters
     * @return gives you the requested class variable
     */
    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public float getRating() {
        return rating;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPages(){
        return pages;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return (title + "\n" + "by " + Arrays.toString(authors).substring(1, Arrays.toString(authors).length()-1) +"\n" + "Rating: " + decimalFormat.format(rating) + "\n" + "ISBN: " + ISBN + "\n" + pages + " pages");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntry bookEntry = (BookEntry) o;
        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                Objects.equals(title, bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                Objects.equals(ISBN, bookEntry.ISBN);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }

    /**
     * Function that removes all unnecessary spaces from the argument for numerous commands in order to make
     * it readable for parseArguments in several commands
     *
     * @param argumentInput argument input to be 'cleaned'
     * @return the 'clean' readable argument input
     */

    public static String argumentInputNoUnnecessarySpaces(String argumentInput){
        int argumentInputBeginning = 0;
        int argumentInputEnd = argumentInput.length();
        //it fixates the actual beginning and end end of the 'cleaned' argument
        for (int i =0; i< argumentInput.length(); i++){
            if (!(argumentInput.charAt(i) == ' ')){
                argumentInputBeginning = i;
                for (int z = i; z< argumentInput.length(); z++){
                    if ((argumentInput.charAt(z) == ' ')){
                        argumentInputEnd = z;
                        for (int j = z; j< argumentInput.length(); j++ ){
                            //makes sure only one word is in the argument
                            if (!(argumentInput.charAt(j) == ' ')){
                                return("Not appropriate!");
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        //makes sure there is at least a word in the argument
        boolean isLetter = false;
        for (int k =0; k< argumentInput.length(); k++){
            if (!(argumentInput.charAt(k) == ' ')){
                isLetter = true;
            }
        }
        if (!isLetter){
            return("");
        }
        //makes sure the argument is written in a single line
        for (int p =0; p< argumentInput.length(); p++){
            if(argumentInput.charAt(p) == '\n'){
                return ("Not appropriate!");
            }
        }

        return argumentInput.substring(argumentInputBeginning, argumentInputEnd);
    }

}
