// C0: Search Engine
// Name: Joe Lin
// Date: Jan 13, 2025
// CSE 123 BK
// TA: Benoit Le

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Book class represents a digital book with a title, authors, content, and ratings.
 * It supports adding ratings, retrieving information, calculating averages,
 * and comparing books by rating and title.
 */
public class Book implements Media, Comparable<Book>{
    private String title;
    private List<String> author;
    private List<String> content;
    private List<Integer> rating;

    /**
     * Constructor for the Book class.
     *
     * Initializes a Book with the given title, authors, and content from a Scanner input.
     * The content is read from the provided Scanner, where each token is added as a
     * string to the content list. The list of ratings is initialized as an empty list.
     *
     * @param title The title of the book.
     * @param authors A list of authors associated with the book.
     * @param contentScanner A Scanner object containing the content of the book,
     *                       with each token added to the book's content.
     */
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
     * Gets the title of this Book.
     *
     * @return The title of this Book.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Gets all authors associated with this Book.
     *
     * @return A list of artists for this Book.
     */
    @Override
    public List<String> getArtists() {
        return author;
    }

    /**
     * Adds a rating to this Book.
     *
     * @param score The score for the new rating. Should be non-negative.
     */
    @Override
    public void addRating(int score) {
        rating.add(score);
    }

    /**
     * Gets the number of times this Book has been rated.
     *
     * @return The number of ratings for this Book.
     */
    @Override
    public int getNumRatings() {
        return rating.size();
    }

    /**
     * Gets the average (mean) of all ratings for this Book.
     *
     * @return The average (mean) of all ratings for this Book.
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
     * Gets all of the content contained in this Book.
     *
     * @ returns    The content stored in a List of strings. If there is no content, an empty list
     */
    @Override
    public List<String> getContent() {
        return new ArrayList<>(content);
    }

    /**
     * Gets a String description of this Book
     *
     * @ returns    The String description of this Book in the format `<title> by [<authors>]`
     * if ratings is empty or `<title> by [<authors>]: <average rating> (<num ratings> ratings)`
     * if ratings is not empty.
     */
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
