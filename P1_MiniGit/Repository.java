// P1: Mini-Git
// Name: Joe Lin
// Date: Feb 12, 2025
// CSE 123 BK
// TA: Benoit Le

import java.util.*;
import java.text.SimpleDateFormat;


/**
 * The Repository class represents a version control system that allows users to create,
 * store, and manage commits. Users can commit changes, check repository history,
 * remove commits, and synchronize with other repositories.
 *
 * This class provides methods to interact with a repository, including retrieving the
 * latest commit, checking for commit existence, retrieving commit history, and merging
 * changes from another repository.
 *
 */
public class Repository {
    private String name;
    private Commit head;
    private int size;

    /**
     * Constructor to initialize a repository with a given name.
     * Throws an exception if the name is null or empty.
     *
     * @param name Name of the repository
     */
    public Repository(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Repository name null or empty");
        }
        this.name = name;
        this.head = null;
        this.size = 0;
    }

    /**
     * Retrieves the ID of the latest commit.
     *
     * @return ID of the latest commit or null if no commits exist.
     */
    public String getRepoHead() {
        return head == null ? null : head.id;
    }

    /**
     * Returns the number of commits in the repository.
     *
     * @return Number of commits.
     */
    public int getRepoSize() {
        return size;
    }

    /**
     * Returns a string representation of the repository, showing the current head commit.
     * If repo empty, would return "<name> - No commits"
     *
     * @return String describing the repository's current state.
     */
    @Override
    public String toString() {
        if (head != null) {
            return name + " - Current head: " + head.toString();
        }
        return name + " - No commits";
    }

    /**
     * Checks if a commit with the given ID exists in the repository.
     *
     * @param targetId The commit ID to check for.
     * @return True if the commit exists, false otherwise.
     * @throws IllegalArgumentException if the target ID is null.
     */
    public boolean contains(String targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target id null");
        }
        Commit curr = head;
        while (curr != null) {
            if (targetId.equals(curr.id)) {
                return true;
            }
            curr = curr.past;
        }
        return false;
    }

    /**
     * Retrieves the history of commits up to a specified number.
     * Will give entire history of commits if n > size of repo.
     *
     * @param n The maximum number of commits to return.
     * @return A string containing commit details.
     * @throws IllegalArgumentException if n is less than or equal to 0.
     */
    public String getHistory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("History limit less than 0");
        }
        Commit curr = head;
        String result = "";
        int count = 0;
        while(curr != null && count < n) {
            result += curr + "\n";
            curr = curr.past;
            count++;
        }
        return result;
    }

    /**
     * Creates a new commit with the given message and adds it to the repository.
     *
     * @param message The commit message.
     * @return The ID of the new commit.
     * @throws IllegalArgumentException if the message is null.
     */
    public String commit(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Commit message null");
        }
        head = new Commit(message, head);
        size++;
        return head.id;
    }

    /**
     * Removes a commit with the specified ID from the repository.
     *
     * @param targetId The ID of the commit to remove.
     * @return True if the commit was successfully removed, false otherwise.
     * @throws IllegalArgumentException if the target ID is null.
     */
    public boolean drop(String targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target id null");
        }

        if (head == null){
            return false;
        }

        if (head.id.equals(targetId)) {
            head = head.past;
            size--;
            return true;
        }

        Commit curr = head;
        while (curr.past != null) {
            if (targetId.equals(curr.past.id)) {
                curr.past = curr.past.past;
                size--;
                return true;
            }
            curr = curr.past;
        }
        return false;
    }

    /**
     * Synchronizes this repository with another repository by merging commits in
     * chronological order, and sets `other` to an empty repo.
     *
     * @param other The repository to synchronize with.
     * @throws IllegalArgumentException if the other repository is null.
     */
    public void synchronize(Repository other) {
        if (other == null) {
            throw new IllegalArgumentException("other null");
        }

        if (this.size == 0) {
            this.head = other.head;
            this.size = other.size;
            other.head = null;
            other.size = 0;
        } else if (other.size != 0) {

            // front case
            if(other.head.timeStamp >= this.head.timeStamp) {
                Commit temp = other.head;
                other.head = other.head.past;
                temp.past = this.head;
                this.head = temp;
            }

            Commit curr = this.head;

            // mid case
            while(curr.past != null && other.head != null){
                if(other.head.timeStamp >= curr.past.timeStamp){
                    Commit temp = other.head;
                    other.head = other.head.past;
                    temp.past = curr.past;
                    curr.past = temp;
                }
                curr = curr.past;
            }

            // end
            if(curr.past == null){
                curr.past = other.head;
            }
            this.size += other.size;
            other.head = null;
            other.size = 0;
        }
    }

    /**
     * DO NOT MODIFY
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     * <p>
     * Staff Note: You may notice that the comments in this
     * class openly mention the fields of the class. This is fine
     * because the fields of the Commit class are public. In general,
     * be careful about revealing implementation details!
     */
    public static class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         *
         * @param message A message describing the changes made in this commit. Should be non-null.
         * @param past    A reference to the commit made immediately before this
         *                commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         *
         * @param message A message describing the changes made in this commit. Should be non-null.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         * "[identifier] at [timestamp]: [message]"
         *
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
         * Resets the IDs of the commit nodes such that they reset to 0.
         * Primarily for testing purposes.
         */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
