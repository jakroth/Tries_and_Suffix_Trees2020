package cp3.ass01.suffixtree;

import cp3.ass01.suffixtrie.SuffixIndex;
import cp3.ass01.suffixtrie.SuffixTrieData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class UkkonenTree {

    private boolean verbose;                                            // how much info to give
    private String input;                                               // the input string to be parsed into a Suffix Tree
    private UkkonenNode root;                                           // the root node of the tree
    private int step;                                                   // the current position index in the input string, used to update all strings when they need to be printed
    private UkkonenNode activeNode;                                     // ActivePoint - where new nodes are inserted
    private char activeChar;                                            // ActivePoint - which char path to go down next
    private int activeLength;                                           // ActivePoint - how many chars into a path we are
    private int remainder;                                              // remaining suffixes to add each loop
    private UkkonenNode internal;                                       // used for nodes that don't last till the end of input
    UkkonenNode printNode;                                              // for printing the Tree

    public UkkonenTree(boolean verbose){
        root = new UkkonenNode(-1, -1);
        step = -1;
        activeNode = root;
        activeChar = '\0';
        activeLength = 0;
        remainder = 0;
        this.verbose = verbose;
        printNode = root;
    }

    /**
     * Reads from a file into a string and then calls the insert() function on it.
     *
     * @param fileName is the file to read from
     */
    public static UkkonenTree readInFromFile(String fileName, boolean verbose){
        UkkonenTree uke = new UkkonenTree(verbose);
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new FileInputStream("data" + File.separator + fileName));
            StringBuilder sb = new StringBuilder();
            while(fileScanner.hasNextLine()){
                sb.append(fileScanner.nextLine());
            }
            sb.append("$");                             // this character marks the end of the input string.
            uke.input = sb.toString();                  // set the input instance variable to the string, it will be needed in other methods
            uke.insert();
        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ " in the data directory!");
        }
        return uke;
    }

    /**
     * Inserts the String <code>input</code> into the Ukkonen suffix tree.
     *
     */
    public void insert(){
        activeNode = root;                                                  // instantiate the iterative node
        for(int i = 0; i < input.length(); i++) {                           // iterate over the input string
            step = i;
            if(verbose) System.out.println("step = " + step);
            if(verbose) System.out.println(input.charAt(i));
            remainder++;
            internal = null;

            while (remainder > 0) {                                         // while there are still suffixes to add (1 is the current character)

                // this part says if activeLength is 0, then the active char is the current char in input
                if (activeLength == 0) {
                    activeChar = input.charAt(i);
                }

                // if the child nodes of the activeNode don't have the activeChar, make a new node for that path
                if (!activeNode.contains(activeChar)) {
                    activeNode.addChild((activeChar), new UkkonenNode(i));
                    activeNode.getChild(activeChar).addData(i - remainder + 1);
                    if(activeNode != root) activeNode.addData(i - remainder + 1);  // add relevant starting index

                    // create suffixLink from last internal node to activeNode
                    if (internal != null) {
                        internal.setSuffix(activeNode);
                        internal = null;
                    }
                }

                // if the child nodes of the activeNode do have the activeChar, keep going down the paths
                else {
                    // move down the path
                    UkkonenNode next = activeNode.getChild(activeChar);

                    // if the length of suffix we need to get to is longer than this node substring we need to move to the next node and go to next while loop
                    if (activeLength >= next.length()) {
                        activeNode = next;
                        //activeChar = ???;                                 // doesn't need to change cause is a char
                        activeLength -= next.length();
                        continue;                                           // go to next while loop
                    }

                    // if the current char matches the activeLength char in this node, increment activeLength and stop processing this char
                    if (input.charAt(next.begin + activeLength) == input.charAt(i)) {

                        // create suffixLink from last internal node to activeNode
                        if (internal != null && activeNode != root) {
                            internal.setSuffix(activeNode);
                            internal = null;
                        }

                        // if we get here, we found the char already in the Tree, so just need to increment activeLength and go to next char
                        activeLength++;
                        break;
                    }

                    // we are in the right node and the index char is not there, so need to split
                    // splitting nodes to make internal node
                    int splitEnd = next.begin + activeLength - 1;                       // create ending index for internal node
                    UkkonenNode split = new UkkonenNode(splitEnd + 1);             // create internal node
                    next.end = splitEnd;                                                // increase the next node beginning index by activeLength
                    activeNode.addChild(activeChar, next);                                 // add internal node to active node children
                    next.addData(i - remainder + 1);                                // add the new starting index
                    next.addChild(input.charAt(split.begin), split);                     // add next node to the internal node children
                    split.addData(next.begin);                                             // add the right starting index
                    next.addChild(input.charAt(i), new UkkonenNode(i));                   // this child wasn't being created before, need two children in a split
                    next.getChild(input.charAt(i)).addData(i - remainder + 1);     // add the right starting index


                    // create suffixLink from internal to activeNode
                    if (internal != null) {
                        internal.setSuffix(split);
                    }
                    internal = split;
                }

                // added a suffix for this while loop, decrement the suffixes remaining to be added
                remainder--;

                // rules for updating the activePoint
                if (activeNode == root && activeLength > 0) {
                    activeLength--;
                    activeChar = input.charAt(i - remainder + 1);
                } else if (activeNode != root) {
                    activeNode = activeNode.getSuffix();
                }
            }
            if(verbose) System.out.println(root.children);
        }
    }

    /**
     * gets the last node in the substring str
     *
     * @param str the (sub)string to search for
     * @return the final UkkonenNode in the (sub)string
     */
    public UkkonenNode get(String str){
        UkkonenNode itNode = root;
        for(int i = 0; i < str.length(); i++){
            if(verbose) System.out.println("loop: " + i);
            if(verbose) System.out.println(itNode.children);
            if(itNode.getChild(str.charAt(i)) == null){             // check to see if the first character is in the child map
                if(verbose) System.out.println("exited here");
                return null;
            }
            itNode = itNode.getChild(str.charAt(i));                // go down the next path
            if(verbose) System.out.println(itNode);
            int min = Math.min(str.length() - i, itNode.length());  // calculate the minimum of the remaining string length and the substring length in this node
            if(str.substring(i,i+min).equals(itNode.subStr(min))){    // if a match, we can keep going
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
     *
     */
    public void printTree(){
        if(!printNode.getChildren().isEmpty()){
            System.out.println("\"" + printNode.toString() + "\": ");
            System.out.println(printNode.getChildren());
        }
        for(Map.Entry<Character,UkkonenNode> child : printNode.getChildren().entrySet()){
            printNode = child.getValue();
            printTree();
        }
    }

    /**
     * set the value of the input variable
     *
     * @param input the value to change input to
     */
    public void setInput(String input){
        this.input = input;
    }



    /**
     * UkkonnnNode is a nested class
     * It is nested so that it can directly access the <code>input</code> string
     *
     */
    class UkkonenNode {
        private int begin;                                                  // beginning index in input string
        private int end;                                                    // ending index in input string
        private SuffixTrieData data = new SuffixTrieData();                 // for storing the starting indices of the substrings
        private Map<Character, UkkonenNode> children = new TreeMap<>();     // the TreeMap that will link to the child node
        UkkonenNode suffixLink;                                             // used to jump to remaining suffix

        //constructor for nodes;
        UkkonenNode(int begin){
            this.begin = begin;
            this.end = -1;                                                  // end is set to -1 cause this will be updated to current when needed
            this.suffixLink = root;
        }

        //constructor for internal nodes;
        UkkonenNode(int begin, int end){
            this.begin = begin;
            this.end = end;                                                       // these internal nodes don't go to end of input
            this.suffixLink = root;
        }

        public void addChild(char label, UkkonenNode node){
            children.put(label, node);
        }

        public UkkonenNode getChild(char label){
            return children.get(label);
        }

        public Map<Character,UkkonenNode> getChildren(){
            return children;
        }

        public void addData(int index){
            SuffixIndex si = new SuffixIndex(0, index);
            data.addStartIndex(si);
        }

        public String getData(){
            return data.toString();
        }

        public UkkonenNode getSuffix(){
            return suffixLink;
        }

        public void setSuffix(UkkonenNode sf){
            this.suffixLink = sf;
        }

        public int length(){
            return end == -1 ? step - begin + 1 : end - begin + 1;
        }

        public boolean contains(char ch){
            return children.containsKey(ch);
        }

        // return the substring defined by the begin and end variables, or i if it's less than step+1
        public String subStr(int i){
            return input.substring(begin,Math.min(step,begin+i));
        }

        @Override
        public String toString() {
            if(begin == -1) return "root";
            return end == -1 ? input.substring(begin,step+1) : input.substring(begin,end+1);
        }
    }
}
