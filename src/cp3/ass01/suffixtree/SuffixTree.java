package cp3.ass01.suffixtree;

import cp3.ass01.suffixtrie.SuffixIndex;
import cp3.ass01.suffixtrie.SuffixTrieData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class SuffixTree {

    private Node root = new Node(-1, -1);
    private String input;
    private int length;
    private Node printNode = root;
    private boolean verbose;

    /**
     * Helper/Factory static method to readIn a string from a text file and create a SuffixTree
     * The text file is read in as a single String
     *
     * It is called in the following way:
     * <code>SuffixTree st = SuffixTree.readInFromFile("Frank01e.txt");</code>
     *
     * @param fileName the file to read from
     * @return the SuffixTree with all the text added
     */
    public static SuffixTree readInFromFile(String fileName) {
        SuffixTree st = new SuffixTree();
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new FileInputStream("data" + File.separator + fileName));
            StringBuilder sb = new StringBuilder();
            while(fileScanner.hasNextLine()){
                sb.append(fileScanner.nextLine());
            }
            st.input = sb.toString();                           // set the input string variable
            st.length = st.input.length();                      // set the length of the input string variable
            st.insert();
        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ " in the data directory!");
            return null;
        }
        return st;
    }

    /**
     * Method to insert a string into my SuffixTree
     *
     * @return the last Node inserted
     */
    public Node insert() {
        input = input.toLowerCase();
        Node itNode = root;                                                         // instantiate iterative node
        int sentence = 0;                                                           // instantiate sentence number
        int ch = 0;                                                                 // instantiate character number
        int offset = 1;                                                             // instantiate offset inside node
        for(int i = 0; i < input.length(); i++) {                                   // iterate over every substring in the input string
            itNode = root;                                                          // reset itNode to root before each substring
            for (int j = i; j < input.length(); j++) {                              // iterate over every character in each substring
                while(j < itNode.end){                                              // iterate through nodes as long as all characters match
                    char oldChar = input.charAt(itNode.begin + offset);             // to save rewriting/recalculating, this is the char in the current node at this offset
                    if(input.charAt(j) != oldChar){                                 // see if the current character = the current offset in the node
                        // split the nodes
                            Node splitA = new Node(itNode.begin + offset, itNode.end);      // new node to continue the pre-existing path
                            splitA.children = itNode.children;                              // link splitA's children to itNode's children
                            itNode.children = new TreeMap<>();                              // create new TreeMap for itNode's children
                            itNode.addChild(input.charAt(itNode.begin + offset),splitA);    // add splitA to itNode's children, with Node offset character
                            itNode.end = itNode.begin + offset;                             // set new end for itNode
                        // copy suffix index data across, except most recent one added
                            for(SuffixIndex si : itNode.data.getArray()){
                                splitA.data.addStartIndex(si);                      // this actually just links the same si's to the two arrays
                            }
                            splitA.data.removeLastIndex();                          // the last index was added when we entered this node, but not relevant for this continuing pathway
                            break;
                    }
                    offset++;                                                       // increment offset
                    j++;                                                            // increment j
                }
                // after this while loop, we will be at the end of the substring for this node
                if(j != length && itNode.getChild(input.charAt(j)) == null) {                      // see if the character edge exists in this node
                    itNode.addChild(input.charAt(j), new Node(j));                  // add new node if not, with beginning index "j"
                    itNode = itNode.getChild(input.charAt(j));                      // change itNode to the child node
                    itNode.addData(sentence, ch);                                   // add the substring starting index data to the new node
                    break;                                                          // this is an external node, so break to save a lot of loops
                }
                // if we get here, we just need to continue down the path
                if(j != length) itNode = itNode.getChild(input.charAt(j));          // change itNode to the child node
                offset = 1;                                                         // reset the offset to 1 in the new node
                itNode.addData(sentence, ch);                                       // add the substring starting index data to all child nodes on the path
            }
            if(input.substring(i,i+1).matches("[!?.]")) {                     // check for these sentence characters
                sentence++;
                ch = 0;
            }
            else{
                ch++;
            }
        }
        return itNode;
    }

    /**
     * gets the last node in the substring str
     *
     * @param str the substring to search for
     * @return the final Node in the substring
     */
    public Node get(String str){
        str = str.toLowerCase();
        Node itNode = root;
        for(int i = 0; i < str.length(); i++){
            if(verbose) System.out.println("loop: " + i);
            if(verbose) System.out.println(itNode.children);
            if(itNode.getChild(str.charAt(i)) == null){             // check to see if the first character is in the child map
                if(verbose) System.out.println("exited here");
                return null;                                        // substr not found, return null
            }
            itNode = itNode.getChild(str.charAt(i));                // go down the next path
            if(verbose) System.out.println(itNode.substr());
            int min = Math.min(str.length() - i, itNode.length());  // calculate the minimum of the remaining string length and the substring length in this node
            if(str.substring(i,i+min).equals(itNode.substr(min))){  // if a match, we can keep going
                if(min == str.length() - i){                        // if we're at the end of str, return that node
                    return itNode;
                }
                else { i += min -1; }                               // otherwise advance i by min - 1 (cause it will advance 1 when the loop starts again)
            }
            else { return null; }                                   // if there's no match, the substring doesn't exist
        }
        return null;                                                // shouldn't ever get here
    }

    /**
     * prints a crude display of the suffix tree
     * uses a recursive structure to print through the tree
     *
     */
    public void printTree(){
        System.out.println("Node string: \"" + printNode.substr() + "\": ");
        System.out.println("Starting indexes: " + printNode.data);
        System.out.print("Children: ");
        System.out.println(printNode.getChildren().keySet());
        System.out.println();
        for(Map.Entry<Character, Node> child : printNode.getChildren().entrySet()){
            printNode = child.getValue();
            printTree();
        }
        if(verbose) System.out.println("...going up...");
    }


    /**
     * Getter for the input string
     *
     *@return the input string
     */
    public String getInput() {
        return input;
    }

    /**
     * Setter for the input string
     *
     *@param input the new input string
     */
    public void setInput(String input) {
        this.input = input;
        length = input.length();
    }

    /**
     * Setter for verbose boolean, prints additional comments
     *
     *@param value the new verbose value
     */
    public void setVerbose(boolean value){
        this.verbose = value;
    }


    /**
     * Node is a nested class
     * It is nested so that it can directly access the <code>input</code> string
     *
     */
    class Node {
        protected int begin;                                                  // beginning index in input string
        protected int end;                                                    // ending index in input string
        private SuffixTrieData data = new SuffixTrieData();                 // for storing the starting indices of the substrings
        private Map<Character, Node> children = new TreeMap<>();            // the TreeMap that will link to the child node

        //constructor for nodes that run to the end of input
        Node(int begin){
            this.begin = begin;
            this.end = length;                                                     // length will be 1 past the end of the string;
        }

        //constructor for internal nodes that run for a subset of input
        Node(int begin, int end){
            this.begin = begin;
            this.end = end;                                                       // these internal nodes don't go to end of input
        }

        /**
         * Lookup a child node of the current node that is associated with the character label.
         *
         * @param label The label to search for
         * @return The child node associated with the provided label
         */
        public Node getChild(char label) {
            return children.get(label);
        }

        /**
         * Add a child node to the current node, at the position associated with the provided label.
         *
         * @param label The character label to associate the new child node with
         * @param node The new child node that is to be attached to the current node
         */
        public void addChild(char label, Node node) {
            children.put(label, node);
        }

        /**
         * Add a new SuffixIndex to the SuffixTrieData associated with this node.
         * A node can (will likely!) have multiple SuffixIndex's if multiple substrings follow that route
         *
         * @param sentencePos The starting sentence of this substring.
         * @param characterPos The starting character of this substring.
         */
        public void addData(int sentencePos, int characterPos) {
            SuffixIndex si = new SuffixIndex(sentencePos, characterPos);
            data.addStartIndex(si);
        }

        /**
         * Get the TreeMap holding all the children of this node
         *
         * @return The TreeMap holding all the children of this node
         */
        public Map<Character, Node> getChildren(){
            return children;
        }

        /**
         * Get the number of characters this node represents
         *
         * @return the number of characters this node represents
         */
        public int length(){
            return end - begin;
        }

        /**
         * Outputs the string associated with this node
         *
         * @return a string representing the substring in this Node.
         */
        public String substr() {
            if(end == -1) return "root";
            return input.substring(begin,end);
        }

        /**
         * Outputs the string associated with this node, for the number of chars given or till end if it's less
         *
         * @return a string representing the substring in this Node.
         */
        public String substr(int i) {
            if(end == -1) return "root";
            return input.substring(begin,Math.min(end,begin+i));
        }

        /**
         * The toString method for the Node.
         *
         * @return the suffix starting index data for this node in the format specified in the SuffixTrieData toString method.
         */
        @Override
        public String toString() {
            return data.toString();
        }
    }
}
