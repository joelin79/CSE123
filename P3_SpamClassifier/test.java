// Burton Tsai
// 03/05/2025
// CSE 123
// P3: Spam Classifier
// TA: Benoit Le

import java.io.*;
import java.util.*;

// This class represents a text classifier that can categorize text data into different labels.
// It can be constructed either from a pre-existing model file or by training on labeled
// text examples. Once built, the classifier can predict labels for new text inputs,
// evaluate its accuracy on test data, and save its trained model to a file for later use.
// The class provides functionality for classifying emails, documents, or other text content
// into user-defined categories based on word frequencies in the text.
public class test {
    private ClassifierNode overallRoot;

    // An internal structure that represents components of the classification model.
    // This class stores either classification decision points or final category labels,
    // along with associated data needed for the classification process.
    private static class ClassifierNode {
        public final String feature;
        public final double threshold;
        public final String label;
        public final TextBlock originalData;
        public ClassifierNode left;
        public ClassifierNode right;

        // Constructs a decision node with the specified feature and threshold.
        // Parameters:
        // - feature: The word feature to compare in the decision.
        // - threshold: The probability threshold for the feature.
        public ClassifierNode(String feature, double threshold) {
            this.feature = feature;
            this.threshold = threshold;
            this.label = null;
            this.originalData = null;
        }

        // Constructs a label node with the specified label and original data.
        // Parameters:
        // - label: The classification label for this node.
        // - data: The TextBlock used to create this node.
        public ClassifierNode(String label, TextBlock data) {
            this.feature = null;
            this.threshold = 0.0;
            this.label = label;
            this.originalData = data;
        }

        // Constructs a label node with only the specified label.
        // Used primarily when loading a model from a file.
        // Parameters:
        // - label: The classification label for this node.
        public ClassifierNode(String label) {
            this.feature = null;
            this.threshold = 0.0;
            this.label = label;
            this.originalData = null;
        }
    }

    // Constructs a classifier by loading a pre-built classification tree from the
    // provided Scanner input.
    // Parameters:
    // - input: A Scanner connected to a file containing classification tree data.
    // Throws:
    // - IllegalArgumentException: If input is null.
    public Classifier(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        overallRoot = buildTree(input);
    }

    // Helper method that builds the classification model from the provided Scanner input.
    // Parameters:
    // - input: A Scanner connected to a file containing classification tree data.
    // Returns:
    // - The root node of the constructed classification tree.
    private ClassifierNode buildTree(Scanner input) {
        if (!input.hasNextLine()) {
            return null;
        }
        String line = input.nextLine();
        if (line.contains("Feature: ")) {
            String feature = line.substring(9);
            String thresholdLine = input.nextLine();
            double threshold = Double.parseDouble(thresholdLine.substring(11));
            ClassifierNode node = new ClassifierNode(feature, threshold);
            node.left = buildTree(input);
            node.right = buildTree(input);
            return node;
        }
        return new ClassifierNode(line);
    }

    // Constructs a classifier by training a classification tree on the provided
    // data and corresponding labels.
    // Parameters:
    // - data: A list of TextBlock objects to be classified.
    // - labels: A list of labels corresponding to each TextBlock in data.
    // Throws:
    // - IllegalArgumentException: If data or labels is null, if they have different
    //			sizes, or if they are empty.
    public Classifier(List<TextBlock> data, List<String> labels) {
        if (data == null || labels == null || data.size() != labels.size() || data.isEmpty()) {
            throw new IllegalArgumentException();
        }
        overallRoot = new ClassifierNode(labels.get(0), data.get(0));
        for (int i = 1; i < data.size(); i++) {
            addDataPoint(data.get(i), labels.get(i));
        }
    }

    // Helper method that incorporates a new training example into the classification model.
    // Updates the model when the current structure would incorrectly classify the new example.
    // Parameters:
    // - newData: The TextBlock to add to the classification tree.
    // - newLabel: The label corresponding to newData.
    private void addDataPoint(TextBlock newData, String newLabel) {
        ClassifierNode current = overallRoot;
        ClassifierNode parent = null;
        boolean isLeft = false;
        while (current.label == null) {
            parent = current;
            if (newData.get(current.feature) < current.threshold) {
                isLeft = true;
                current = current.left;
            } else {
                isLeft = false;
                current = current.right;
            }
        }
        if (!current.label.equals(newLabel)) {
            String diffFeature = current.originalData.findBiggestDifference(newData);
            double threshold = midpoint(current.originalData.get(diffFeature),
                    newData.get(diffFeature));
            ClassifierNode decisionNode = new ClassifierNode(diffFeature, threshold);
            if (current.originalData.get(diffFeature) < threshold) {
                decisionNode.left = current;
                decisionNode.right = new ClassifierNode(newLabel, newData);
            } else {
                decisionNode.left = new ClassifierNode(newLabel, newData);
                decisionNode.right = current;
            }
            if (parent == null) {
                overallRoot = decisionNode;
            } else if (isLeft) {
                parent.left = decisionNode;
            } else {
                parent.right = decisionNode;
            }
        }
    }

    // Classifies the given TextBlock based on the current classification tree.
    // Parameters:
    // - input: The TextBlock to classify.
    // Returns:
    // - The predicted label for the input.
    // Throws:
    // - IllegalArgumentException: If input is null.
    public String classify(TextBlock input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return classify(overallRoot, input);
    }

    // Helper method that determines the appropriate label for the given TextBlock.
    // Parameters:
    // - node: The current node in the classification tree.
    // - input: The TextBlock to classify.
    // Returns:
    // - The predicted label for the input.
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

    // Saves the current classification tree to the provided PrintStream.
    // The tree is saved in pre-order traversal, with each decision node represented
    // by its feature and threshold, and each label node represented by its label.
    // Parameters:
    // - output: The PrintStream to save the classification tree to.
    // Throws:
    // - IllegalArgumentException: If output is null.
    public void save(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException();
        }
        save(overallRoot, output);
    }

    // Helper method that saves the classification model to the output stream.
    // Parameters:
    // - root: The current node in the classification tree.
    // - output: The PrintStream to save the classification tree to.
    private void save(ClassifierNode root, PrintStream output) {
        if (root == null) {
        } else if (root.label != null) {
            output.println(root.label);
        } else {
            output.println("Feature: " + root.feature);
            output.println("Threshold: " + root.threshold);
            save(root.left, output);
            save(root.right, output);
        }
    }
    ////////////////////////////////////////////////////////////////////
    // PROVIDED METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ////////////////////////////////////////////////////////////////////

    // Helper method to calculate the midpoint of two provided doubles.
    private static double midpoint(double one, double two) {
        return Math.min(one, two) + (Math.abs(one - two) / 2.0);
    }

    // Behavior: Calculates the accuracy of this model on provided Lists of
    //											testing 'data' and corresponding 'labels'. The label for a
    //											datapoint at an index within 'data' should be found at the
    //											same index within 'labels'.
    // Exceptions: IllegalArgumentException if the number of datapoints doesn't match the number
    //													of provided labels
    // Returns: a map storing the classification accuracy for each of the encountered labels when
    //										classifying
    // Parameters: data - the list of TextBlock objects to classify. Should be non-null.
    //													labels - the list of expected labels for each TextBlock object.
    //													Should be non-null.
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