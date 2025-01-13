import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book implements Media, Comparable<Book>{
    private String title;
    private List<String> author;
    private List<String> content;
    private List<Integer> rating;


    public Book(String title, List<String> authors, Scanner contentScanner){
        this.title = title;
        this.author = authors;
        this.content = new ArrayList<>();
        this.rating = new ArrayList<>();
        while(contentScanner.hasNext()){
            this.content.add(contentScanner.next());
        }
    }

    /**
     * Gets the title of this media.
     *
     * @return The title of this media.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Gets all artists associated with this media.
     *
     * @return A list of artists for this media.
     */
    @Override
    public List<String> getArtists() {
        return author;
    }

    /**
     * Adds a rating to this media.
     *
     * @param score The score for the new rating. Should be non-negative.
     */
    @Override
    public void addRating(int score) {
        rating.add(score);
    }

    /**
     * Gets the number of times this media has been rated.
     *
     * @return The number of ratings for this media.
     */
    @Override
    public int getNumRatings() {
        return rating.size();
    }

    /**
     * Gets the average (mean) of all ratings for this media.
     *
     * @return The average (mean) of all ratings for this media.
     * If no ratings exist, returns 0.
     */
    @Override
    public double getAverageRating() {
        if (rating.size() == 0) { return 0; }
        int sum = 0;
        for(int i: rating){
            sum += i;
        }
        return (double) sum /rating.size();
    }

    /**
     * Gets all of the content contained in this media.
     *
     * @ returns    The content stored in a List of strings. If there is no content, an empty list
     */
    @Override
    public List<String> getContent() {
        return new ArrayList<>(content);
    }

    @Override
    public String toString() {
        if(rating.isEmpty()){
            return title + " by " + author;
        }
        double rounded = (double) Math.round(getAverageRating() * 100) / 100;
        return title + " by " + author + ": "+ rounded + " (" + rating.size() + " ratings)";
    }

    /**
     * Compares this book with the specified book for order based on the title.
     * The comparison is done lexicographically in ascending order.
     *
     * @param o the book to be compared.
     * @return a negative integer, zero, or a positive integer as this book's title is
     * lexicographically less than, equal to, or greater than the specified book's title.
     */
    @Override
    public int compareTo(Book o) {
        int ratingComparison = Double.compare(o.getAverageRating(), this.getAverageRating());
        if (ratingComparison != 0) {
            return ratingComparison; // Higher ratings come first
        }
        return this.title.compareTo(o.title);
    }
}
