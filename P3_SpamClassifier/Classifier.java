// Joe Lin
// 03/05/2025
// CSE 123
// P3: Spam Classifier
// TA: Benoit Le

import java.io.*;
import java.util.*;

/**
 * A classifier that constructs a binary decision tree for text classification.
 * It supports training with labeled text data, classifying new inputs,
 * and saving/loading the classifier model to/from a file.
 */
public class Classifier {
    private ClassifierNode overallRoot;

    /**
     * Constructs a classifier by loading a tree from a file.
     * File format: pre-order format, where decision nodes are stored
     * in two lines: "Feature: <feature_name>" next line followed by "Threshold: <threshold_value>"
     * and label nodes are stored as a single line containing only the classification label.
     *
     * @param input The scanner containing the tree data.
     * @throws IllegalArgumentException if the input scanner is null.
     */
    public Classifier(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        this.overallRoot = buildTree(input);
    }

    /**
     * Constructs a classifier by training a classification model on the labeled text data.
     *
     * @param data The list of text data.
     * @param labels The corresponding labels for classification.
     * @throws IllegalArgumentException if data or labels are null, empty, or mismatched in size.
     */
    public Classifier(List<TextBlock> data, List<String> labels) {

        if (data == null || labels == null || data.size() != labels.size() || data.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < data.size(); i++) {
            this.overallRoot = insertNode(this.overallRoot, data.get(i), labels.get(i));
        }
    }

    /**
     * Recursively constructs a classification tree from input data.
     * This method reads from a file following a pre-order traversal format.
     *
     * @param input The scanner containing tree data.
     * @return The root node of the constructed tree.
     */
    private ClassifierNode buildTree(Scanner input) {
        // base case
        if (!input.hasNextLine()) {
            return null;
        }

        // go next
        String line = input.nextLine();

        // decision
        if (line.startsWith("Feature: ")) {
            String feature = line.substring(9);
            double threshold = Double.parseDouble(input.nextLine().substring(11));
            ClassifierNode node = new ClassifierNode(feature, threshold);
            node.left = buildTree(input);
            node.right = buildTree(input);
            return node;
        }

        // label
        return new ClassifierNode(line, null);
    }

    /**
     * Recursively inserts a new labeled data point into the classification tree.
     * If a label mismatch occurs at a leaf node, a new decision node is created based
     * on the most differentiating feature between the existing and new data points.
     *
     * @param node The current node in traversal.
     * @param data The text data to insert.
     * @param label The classification label.
     * @return The updated tree node after insertion.
     */
    private ClassifierNode insertNode(ClassifierNode node, TextBlock data, String label) {
        if (node == null) {
            return new ClassifierNode(label, data);
        }

        if (node.label != null) {
            if (node.label.equals(label)) {
                return node;
            }

            String feature = node.data.findBiggestDifference(data);
            double threshold = midpoint(node.data.get(feature), data.get(feature));
            ClassifierNode decisionNode = new ClassifierNode(feature, threshold);

            if (data.get(feature) < threshold) {
                decisionNode.left = new ClassifierNode(label, data);
                decisionNode.right = node;
            } else {
                decisionNode.left = node;
                decisionNode.right = new ClassifierNode(label, data);
            }
            return decisionNode;
        }

        if (data.get(node.feature) < node.threshold) {
            node.left = insertNode(node.left, data, label);
        } else {
            node.right = insertNode(node.right, data, label);
        }
        return node;
    }

    /**
     * Determines the classification label for the given text input
     * based on the decision model constructed from training data.
     *
     * @param input The text block to classify.
     * @return The predicted label for the input.
     * @throws IllegalArgumentException if the input is null.
     */
    public String classify(TextBlock input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return classify(overallRoot, input);
    }

    /**
     * Recursively traverses the classification tree to determine the label.
     * At each decision node, it checks if the input's feature value is less than the threshold,
     * deciding whether to continue left or right in the tree.
     *
     * @param node The current node being examined.
     * @param input The text block to classify.
     * @return The classification label found at the corresponding leaf node.
     */
    private String classify(ClassifierNode node, TextBlock input) {
        if (node.label != null) {
            return node.label;
        }
        if (input.get(node.feature) < node.threshold) {
            return classify(node.left, input);
        } else {
            return classify(node.right, input);
        }
    }

    /**
     * Saves the classifier tree to a file in pre-order format.
     * The output format:
     * - Decision nodes: "Feature: <feature>" next line followed by "Threshold: <threshold>".
     * - Label nodes: A single line containing the label.
     *
     * @param output The output stream to save the tree.
     * @throws IllegalArgumentException if the output is null.
     */
    public void save(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException();
        }
        save(overallRoot, output);
    }

    /**
     * Recursively writes the classification tree to an output stream in pre-order format.
     * The output format:
     * - Decision nodes: "Feature: <feature>" next line followed by "Threshold: <threshold>".
     * - Label nodes: A single line containing the label.
     *
     * @param node The current node being written.
     * @param output The output stream to save the tree.
     */
    private void save(ClassifierNode node, PrintStream output) {
        if (node.label != null) {
            output.println(node.label);
        } else {
            output.println("Feature: " + node.feature);
            output.println("Threshold: " + node.threshold);
            save(node.left, output);
            save(node.right, output);
        }
    }

    /**
     * Represents a node in the classification tree.
     * Nodes can either be decision nodes containing a feature and a threshold,
     * or leaf nodes containing a classification label.
     */
    private static class ClassifierNode {
        // for decision
        public final String feature;
        public final double threshold;
        // for lable
        public final String label;
        public final TextBlock data;

        public ClassifierNode left, right;

        /**
         * Constructs a decision node.
         * @param feature The feature used for decision-making.
         * @param threshold The threshold value for branching.
         */
        public ClassifierNode(String feature, double threshold) {
            this.feature = feature;
            this.threshold = threshold;
            this.label = null;
            this.data = null;
            this.left = null;
            this.right = null;
        }

        /**
         * Constructs a label node.
         * @param label The classification label.
         * @param data The text data associated with the label.
         */
        public ClassifierNode(String label, TextBlock data) {
            this.label = label;
            this.data = data;
            this.threshold = 0;
            this.left = null;
            this.right = null;
            this.feature = null;
        }
    }


    ////////////////////////////////////////////////////////////////////
    // PROVIDED METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ////////////////////////////////////////////////////////////////////

    // Helper method to calcualte the midpoint of two provided doubles.
    private static double midpoint(double one, double two) {
        return Math.min(one, two) + (Math.abs(one - two) / 2.0);
    }    

    // Behavior: Calculates the accuracy of this model on provided Lists of 
    //           testing 'data' and corresponding 'labels'. The label for a 
    //           datapoint at an index within 'data' should be found at the 
    //           same index within 'labels'.
    // Exceptions: IllegalArgumentException if the number of datapoints doesn't match the number 
    //             of provided labels
    // Returns: a map storing the classification accuracy for each of the encountered labels when
    //          classifying
    // Parameters: data - the list of TextBlock objects to classify. Should be non-null.
    //             labels - the list of expected labels for each TextBlock object. 
    //             Should be non-null.
    public Map<String, Double> calculateAccuracy(List<TextBlock> data, List<String> labels) {
        // Check to make sure the lists have the same size (each datapoint has an expected label)
        if (data.size() != labels.size()) {
            throw new IllegalArgumentException(
                    String.format("Length of provided data [%d] doesn't match provided labels [%d]",
                                  data.size(), labels.size()));
        }
        
        // Create our total and correct maps for average calculation
        Map<String, Integer> labelToTotal = new HashMap<>();
        Map<String, Double> labelToCorrect = new HashMap<>();
        labelToTotal.put("Overall", 0);
        labelToCorrect.put("Overall", 0.0);
        
        for (int i = 0; i < data.size(); i++) {
            String result = classify(data.get(i));
            String label = labels.get(i);

            // Increment totals depending on resultant label
            labelToTotal.put(label, labelToTotal.getOrDefault(label, 0) + 1);
            labelToTotal.put("Overall", labelToTotal.get("Overall") + 1);
            if (result.equals(label)) {
                labelToCorrect.put(result, labelToCorrect.getOrDefault(result, 0.0) + 1);
                labelToCorrect.put("Overall", labelToCorrect.get("Overall") + 1);
            }
        }

        // Turn totals into accuracy percentage
        for (String label : labelToCorrect.keySet()) {
            labelToCorrect.put(label, labelToCorrect.get(label) / labelToTotal.get(label));
        }
        return labelToCorrect;
    }
}
