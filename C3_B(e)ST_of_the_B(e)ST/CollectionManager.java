// Joe Lin
// 03/13/2025
// CSE 123
// C3: B(e)ST of the B(e)ST
// TA: Benoit Le

import java.io.*;
import java.util.*;

/**
 * Manages a collection of EarthquakeReport objects.
 * Provides operations to add, search, save, and filter earthquake reports.
 */
public class CollectionManager {
    private Node root;

    /**
     * Inner node class to represent each node in the binary search tree.
     */
    private static class Node {
        public final EarthquakeReport report;
        public Node left;
        public Node right;

        /**
         * Constructs an Node with the specified report and null children.
         *
         * @param report - the EarthquakeReport to store in this node
         */
        public Node(EarthquakeReport report) {
            this(report, null, null);
        }

        /**
         * Constructs an Node with the specified report and children.
         *
         * @param report - the EarthquakeReport to store in this node
         * @param left - the left child node
         * @param right - the right child node
         */
        public Node(EarthquakeReport report, Node left, Node right) {
            this.report = report;
            this.left = left;
            this.right = right;
        }

        /**
         * Returns a string representation of this node.
         *
         * @return the string representation of the contained EarthquakeReport
         */
        @Override
        public String toString() {
            return report.toString();
        }
    }

    /**
     * Constructs an empty CollectionManager.
     */
    public CollectionManager() {
        this.root = null;
    }

    /**
     * Constructs a CollectionManager and loads earthquake reports from a file.
     *
     * @param input - Scanner connected to the input file
     * @throws IllegalArgumentException if input is null
     */
    public CollectionManager(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner input cannot be null");
        }

        this.root = null;

        while (input.hasNextLine()) {
            String location = input.nextLine();
            double magnitude = Double.parseDouble(input.nextLine());
            double depth = Double.parseDouble(input.nextLine());
            int timestamp = Integer.parseInt(input.nextLine());

            EarthquakeReport report = new EarthquakeReport(location, magnitude, depth, timestamp);
            add(report);
        }
    }

    /**
     * Adds an earthquake report to the collection.
     *
     * @param report - the EarthquakeReport to add
     * @throws IllegalArgumentException if report is null
     */
    public void add(EarthquakeReport report) {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }

        root = add(root, report);
    }

    /**
     * Recursive helper method to add a report to the binary search tree.
     *
     * @param current - the current node being examined
     * @param report - the EarthquakeReport to add
     * @return the updated node (may be a new node or the same node)
     */
    private Node add(Node current, EarthquakeReport report) {
        if (current == null) {
            return new Node(report);
        }

        int compareResult = report.compareTo(current.report);

        if (compareResult < 0) {
            current.left = add(current.left, report);
        } else if (compareResult > 0) {
            current.right = add(current.right, report);
        }
        // If compareResult == 0, the report is already in the tree, so we don't add it again

        return current;
    }

    /**
     * Checks if the collection contains a specific earthquake report.
     *
     * @param report - the EarthquakeReport to search for
     * @return true if the report is in the collection, false otherwise
     * @throws IllegalArgumentException if report is null
     */
    public boolean contains(EarthquakeReport report) {
        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }

        return contains(root, report);
    }

    /**
     * Recursive helper method to check if a report exists in the binary search tree.
     *
     * @param current - the current node being examined
     * @param report - the EarthquakeReport to search for
     * @return true if the report is found, false otherwise
     */
    private boolean contains(Node current, EarthquakeReport report) {
        if (current == null) {
            return false;
        }

        int compareResult = report.compareTo(current.report);

        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return contains(current.left, report);
        } else {
            return contains(current.right, report);
        }
    }

    /**
     * Returns a string representation of the collection with reports in sorted order.
     *
     * @return a string representation of the collection
     */
    @Override
    public String toString() {
        if (root == null) {
            return "Empty earthquake collection";
        }

        String result = "Earthquake Collection:\n";
        result = toString(root, result);
        return result;
    }

    /**
     * Recursive helper method to build a string representation of the tree in-order.
     *
     * @param current - the current node being examined
     * @param result - the String to append to
     */
    private String toString(Node current, String result) {
        if (current != null) {
            result = toString(current.left, result);
            result += "- " + current.report + "\n";
            result = toString(current.right, result);
        }
        return result;
    }

    /**
     * Saves the collection to a file in pre-order traversal.
     *
     * @param output - the PrintStream to write to
     * @throws IllegalArgumentException if output is null
     */
    public void save(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException("PrintStream output cannot be null");
        }

        save(root, output);
    }

    /**
     * Recursive helper method to save the tree in pre-order traversal.
     *
     * @param current - the current node being examined
     * @param output - the PrintStream to write to
     */
    private void save(Node current, PrintStream output) {
        if (current != null) {
            // Save the current node's data
            EarthquakeReport report = current.report;
            output.println(report.getLocation());
            output.println(report.getMagnitude());
            output.println(report.getDepth());
            output.println(report.getTimestamp());
            // Save left and right subtrees
            save(current.left, output);
            save(current.right, output);
        }
    }

    /**
     * Filter earthquakes by magnitude range.
     * Creative extension method that returns a list of earthquake reports with
     * magnitude within the specified range (inclusive).
     *
     * @param minMagnitude - the minimum magnitude to filter by
     * @param maxMagnitude - the maximum magnitude to filter by
     * @return a list of earthquake reports meeting the criteria
     */
    public List<EarthquakeReport> filter(double minMagnitude, double maxMagnitude) {
        List<EarthquakeReport> result = new ArrayList<>();
        filter(root, minMagnitude, maxMagnitude, result);
        return result;
    }

    /**
     * Recursive helper method to filter earthquakes by magnitude range.
     *
     * @param current - the current node being examined
     * @param minMagnitude - the minimum magnitude to filter by
     * @param maxMagnitude - the maximum magnitude to filter by
     * @param result - the list to store matching reports
     */
    private void filter(Node current, double minMagnitude, double maxMagnitude,
                                         List<EarthquakeReport> result) {
        if (current == null) {
            return;
        }

        // Get magnitude directly from the report
        double magnitude = current.report.getMagnitude();

        // Check if this node meets the criteria
        if (magnitude >= minMagnitude && magnitude <= maxMagnitude) {
            result.add(current.report);
        }

        // Since we're sorting by magnitude in descending order in our compareTo,
        // we can optimize the search by checking if we should continue down certain paths

        // Check left subtree (higher magnitudes) if max bound allows
        if (magnitude <= maxMagnitude) {
            filter(current.left, minMagnitude, maxMagnitude, result);
        }

        // Check right subtree (lower magnitudes) if min bound allows
        if (magnitude >= minMagnitude) {
            filter(current.right, minMagnitude, maxMagnitude, result);
        }
    }
}