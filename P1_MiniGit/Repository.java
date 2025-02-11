import java.util.*;
import java.text.SimpleDateFormat;

public class Repository {
    private String name;
    private Commit head;
    private int size;

    public Repository(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Repository name null or empty");
        }
        this.name = name;
        this.head = null;
        this.size = 0;
    }

    public String getRepoHead() {
        return head == null ? null : head.id;
    }

    public int getRepoSize() {
        return size;
    }

    @Override
    public String toString() {
        if (head != null) {
            return name + " - Current head: " + head.toString();
        }
        return name + " - No commits";
    }

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

    public String commit(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Commit message null");
        }
        head = new Commit(message, head);
        size++;
        return head.id;
    }

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

    public void synchronize(Repository other) {
        if (other == null) {
            throw new IllegalArgumentException("other null");
        } else if (this.size == 0) {
            this.head = other.head;
            this.size = other.size;
            other.head = null;
            other.size = 0;
        } else if (other.size != 0) {
            Commit curr = this.head;

            while(curr != null && other.head != null){
                if(other.head.timeStamp < curr.timeStamp){
                    Commit temp = other.head;
                    temp.past = curr.past;
                    curr.past = temp;
                    other.head = other.head.past;
                } else {
                    Commit temp = other.head;
                    other.head = other.head.past;
                    temp.past = curr;
                }
                curr = curr.past;
            }

            if(curr == null){
                curr.past = other.head;
                other.head = null;
            }
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
