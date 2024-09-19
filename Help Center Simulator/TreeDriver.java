/**
 * Author: Alan John
 * Student ID: 170236456
 * Recitation: 214.30 Sec 4
 */
import java.util.*;
import java.io.*;

/**
 * Represents a node in the tree.
 */
class TreeNode {
    private String label;
    private String prompt;
    private String message;
    private TreeNode left;
    private TreeNode middle;
    private TreeNode right;
    
    /**
     * @return the label 
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     * @param label the new label 
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the prompt 
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Sets the prompt.
     * @param prompt the new prompt 
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * @return the message 
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param message the new message 
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the left child 
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Sets the left child.
     * @param left the new left child 
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * @return the middle child 
     */
    public TreeNode getMiddle() {
        return middle;
    }

    /**
     * Sets the middle child.
     *
     * @param middle the new middle child 
     */
    public void setMiddle(TreeNode middle) {
        this.middle = middle;
    }

    /**
     * @return the right child 
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Sets the right child.
     *
     * @param right the new right child 
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * Constructs a TreeNode with a label, prompt, and message.
     *
     * @param label the label of the node
     * @param prompt the prompt of the node
     * @param message the message of the node
     */
    public TreeNode(String label, String prompt, String message) {
        this.label = label;
        this.prompt = prompt;
        this.message = message;
        this.left = null;
        this.middle = null;
        this.right = null;
    }

    /**
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return left == null && middle == null && right == null;
    }
}

/**
 * Custom exception indicating that the tree has not been loaded.
 */
class TreeNotLoadedException extends Exception {
    public TreeNotLoadedException(String message) {
        super(message);
    }
}

/**
 * Custom exception indicating an invalid node operation.
 */
class InvalidNodeException extends Exception {
    public InvalidNodeException(String message) {
        super(message);
    }
}

/**
 * Custom exception indicating an invalid tree operation.
 */
class TreeOperationException extends Exception {
    public TreeOperationException(String message) {
        super(message);
    }
}

/**
 * Custom exception for file-related errors.
 */
class MyFileException extends Exception {
    public MyFileException(String message) {
        super(message);
    }
}

/**
 * Represents a decision tree .
 */
class Tree {
    private TreeNode root;

    public Tree() {
        this.root = null;
    }

    /**
     * Adds a node to the tree.
     *
     * @param nodeLabel the label of the node
     * @param nodePrompt the prompt of the node
     * @param nodeMessage the message of the node
     * @param parentIdentifier the label of the parent node
     * @return true if the node was successfully added, false otherwise
     * @throws InvalidNodeException if the node cannot be added
     */
    public boolean addNode(String nodeLabel, String nodePrompt, String nodeMessage, String parentLabel) throws InvalidNodeException {
        TreeNode newNode = new TreeNode(nodeLabel, nodePrompt, nodeMessage);

        if (root == null) {
            if (parentLabel == null || parentLabel.equals("root")) {
                root = newNode;
                return true;
            } else {
                throw new InvalidNodeException("Cannot add a node to a non-existent tree");
            }
        }

        TreeNode parentNode = getNodeReference(root, parentLabel);
        if (parentNode == null) {
            throw new InvalidNodeException("Parent node not found: " + parentLabel);
        }

        if (addNodeToLeft(parentNode, newNode)) {
            return true;
        } else if (addNodeToMiddle(parentNode, newNode)) {
            return true;
        } else if (addNodeToRight(parentNode, newNode)) {
            return true;
        } else {
            throw new InvalidNodeException("No available position to add the node: " + nodeLabel);
        }
    }

    private boolean addNodeToLeft(TreeNode parent, TreeNode child) {
        if (parent.getLeft() == null) {
            parent.setLeft(child);
            return true;
        }
        return false;
    }

    private boolean addNodeToMiddle(TreeNode parent, TreeNode child) {
        if (parent.getMiddle() == null) {
            parent.setMiddle(child);
            return true;
        }
        return false;
    }

    private boolean addNodeToRight(TreeNode parent, TreeNode child) {
        if (parent.getRight() == null) {
            parent.setRight(child);
            return true;
        }
        return false;
    }
    /**
     * Recursively searches for a node with the specified label in the tree.
     *
     * @param node the current node being searched
     * @param searchLabel the label of the node to search for
     * @return the TreeNode with the specified label, or null if not found
     */
    private TreeNode getNodeReference(TreeNode node, String searchLabel) {
        if (node == null) {
            return null;
        }

        if (node.getLabel().equals(searchLabel)) {
            return node;
        }

        TreeNode result = getNodeReference(node.getLeft(), searchLabel);
        if (result != null) {
            return result;
        }

        result = getNodeReference(node.getMiddle(), searchLabel);
        if (result != null) {
            return result;
        }

        return getNodeReference(node.getRight(), searchLabel);
    }

    /**
     * Performs a pre-order traversal of the tree and prints the nodes.
     */
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(TreeNode node) {
        if (node != null) {
            System.out.println("Label: " + node.getLabel());
            System.out.println("Prompt: " + node.getPrompt());
            System.out.println("Message: " + node.getMessage());
            preOrder(node.getLeft());
            preOrder(node.getMiddle());
            preOrder(node.getRight());
        }
    }

    /**
     * Begins an interactive help session with the user.
     *
     * @throws TreeNotLoadedException if the tree is not loaded
     */
    public void beginSession() throws TreeNotLoadedException {
        if (root == null) {
            throw new TreeNotLoadedException("No decision tree available. Please load the tree first.");
        }

        TreeNode activeNode = root;
        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println(activeNode.getMessage());


            if (activeNode.getLeft() != null) {
                System.out.println("1: " + activeNode.getLeft().getPrompt());
            }
            if (activeNode.getMiddle() != null) {
                System.out.println("2: " + activeNode.getMiddle().getPrompt());
            }
            if (activeNode.getRight() != null) {
                System.out.println("3: " + activeNode.getRight().getPrompt());
            }
            System.out.println("0: Exit session");

            System.out.print("Enter your choice: ");
            int userChoice = userInput.nextInt();

            if (userChoice == 0) {
                System.out.println("Exiting session. Thank you!");
                break;
            }

            switch (userChoice) {
                case 1:
                    if (activeNode.getLeft() != null) {
                        activeNode = activeNode.getLeft();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 2:
                    if (activeNode.getMiddle() != null) {
                        activeNode = activeNode.getMiddle();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 3:
                    if (activeNode.getRight() != null) {
                        activeNode = activeNode.getRight();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Builds the tree from a file.
     *
     * @param filePath the path to the file
     * @throws TreeOperationException if an error occurs during tree operations
     * @throws MyFileException if an error occurs during file operations
     */
    public void buildTreeFromFile(String filePath) throws TreeOperationException, MyFileException {
        MyBufferedReader reader = null;
        try {
            reader = new MyBufferedReader(filePath);
            String line;

            if ((line = reader.readLine()) != null) {
                String rootLabel = line.trim();
                String rootPrompt = reader.readLine().trim();
                String rootMessage = reader.readLine().trim();
                insertNode(rootLabel, rootPrompt, rootMessage, null);
            }

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] nodeInfo = line.split(" ");
                String parentNodeLabel = nodeInfo[0];
                int childCount = Integer.parseInt(nodeInfo[1]);

                for (int j = 0; j < childCount; j++) {
                    String childLabel = reader.readLine().trim();
                    String childPrompt = reader.readLine().trim();
                    String childMessage = reader.readLine().trim();
                    insertNode(childLabel, childPrompt, childMessage, parentNodeLabel);
                }
            }
            System.out.println("Tree successfully loaded from file!");
        } catch (Exception e) {
            throw new TreeOperationException("Error building tree: " + e.getMessage());
        } 
    }

    private void insertNode(String label, String prompt, String message, String parentLabel) throws InvalidNodeException {
        boolean success = addNode(label, prompt, message, parentLabel == null ? "root" : parentLabel);
        if (!success) {
            throw new InvalidNodeException("Failed to add node: " + label);
        }
    }
}

/**
 * Custom BufferedReader to handle file reading without using java.io.BufferedReader.
 */
class MyBufferedReader {
    private Scanner scanner;
    /**
     * Constructs a MyBufferedReader for the specified file path.
     *
     * @param filePath the path to the file
     * @throws MyFileException if an error occurs opening the file
     */
    public MyBufferedReader(String filePath) throws MyFileException {
        try {
            scanner = new Scanner(new File(filePath));
        } catch (Exception e) {
            throw new MyFileException("Error opening the file: " + e.getMessage());
        }
    }
    /**
     * Reads a line of text from the file.
     *
     * @return a String containing the contents of the line, or null if the end of the file has been reached
     */
    public String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
}

/**
 * The driver class for the Tree application.
 */
public class TreeDriver {
    public static void main(String[] args) throws InvalidNodeException {
        Tree tree = new Tree();
        Scanner input = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
        	System.out.println("Welcome to the help center");
            System.out.println("L - Load a Tree.");
            System.out.println("H - Begin a Help Session.");
            System.out.println("T - Traverse the Tree in preorder.");
            System.out.println("Q - Quit");
            System.out.println("Select a menu option:");
            String choice = input.nextLine().toUpperCase();
            try {
                switch (choice) {
                    case "L" -> {
                        System.out.print("Enter the file name: ");
                        String filename = input.nextLine().trim();
                        tree.buildTreeFromFile(filename);
                    }
                    case "H" -> {
                        tree.beginSession();
                    }
                    case "T" -> {
                        tree.preOrder();
                    }
                    case "Q" -> {
                        System.out.println("Thank you for using our automated help services!");
                        quit = true;
                    }
                    default -> {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } catch (TreeNotLoadedException e) {
                System.err.println("Session Error: " + e.getMessage());
            } catch (TreeOperationException e) {
                System.err.println("Operation Error: " + e.getMessage());
            } catch (MyFileException e) {
                System.err.println("File Error: " + e.getMessage());
            }
        }
    }
}
