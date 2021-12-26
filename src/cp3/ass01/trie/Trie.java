package cp3.ass01.trie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Trie {

    private TrieNode root = new TrieNode();     // a new TrieNode is instantiated when a new Trie is instantiated
    List<String> wordList = null;               // a list of the words in this Trie meeting some prefix search criteria
    TrieNode prefixNode = null;                 // used for prefix searches
    String prefixString = null;                 // used for prefix searches
    String mostFreq = null;                     // used for most frequent suffix searches
    int maxFreq = 0;                            // used for most frequent suffix searches

    /**
     * Inserts a string into the trie and returns the last node that was
     * inserted.
     *
     * @param str The string to insert into the trie
     * @param data	The data associated with the string
     * @return The last node that was inserted into the trie
     */
    public TrieNode insert(String str, TrieData data) {
        char[] word = str.toCharArray();
        TrieNode itNode = root;                        // instantiate iterative node
        for (char ch: word){
            if(itNode.getChild(ch) == null){
                itNode.addChild(ch, new TrieNode());
            }
            itNode = itNode.getChild(ch);
        }
        itNode.setTerminal(true);
        itNode.addData(data);
        return itNode;
    }

    /**
     * Search for a particular prefix in the trie, and return the final node in
     * the path from root to the end of the string, i.e. the node corresponding
     * to the final character. getNode() differs from get() in that getNode()
     * searches for any prefix starting from the root, and returns the node
     * corresponding to the final character of the prefix, whereas get() will
     * search for a whole word only and will return null if it finds the pattern
     * in the trie, but not as a whole word.  A "whole word" is a path in the
     * trie that has an ending node that is a terminal node.
     *
     * @param str The string to search for
     * @return the final node in the path from root to the end of the prefix, or
     * null if prefix is not found
     */
    public TrieNode getNode(String str) {
        char[] prefix = str.toCharArray();
        TrieNode itNode = root;                        // instantiate iterative node
        for (char ch: prefix){
            if(itNode.getChild(ch) == null){
                return null;
            }
            itNode = itNode.getChild(ch);
        }
        return itNode;
    }

    /**
     * Searches for a word in the trie, and returns the final node in the search
     * sequence from the root, i.e. the node corresponding to the final
     * character in the word.
     *
     * getNode() differs from get() in that getNode() searches for any prefix
     * starting from the root, and returns the node corresponding to the final
     * character of the prefix, whereas get() will search for a whole word only
     * and will return null if it finds the pattern in the trie, but not as a
     * whole word. A "whole word" is a path in the
     * trie that has an ending node that is a terminal node.
     *
     * @param str The word to search for
     * @return The node corresponding to the final character in the word, or
     * null if word is not found
     */
    public TrieNode get(String str) {
        TrieNode temp = getNode(str);
        if(temp == null || !temp.isTerminal()){
            return null;
        }
        return temp;
    }

    /**
     * Retrieve from the trie an alphabetically sorted list of all words
     * beginning with a particular prefix.
     *
     * @param prefix The prefix with which all words start.
     * @return The list of words beginning with the prefix, or an empty list if
     * the prefix was not found.
     */
    public List<String> getAlphabeticalListWithPrefix(String prefix) {
        prefixNode = getNode(prefix);                   // initialise/reassign these instance variables each time
        prefixString = prefix;                          // ditto
        wordList = new ArrayList<>();                   // ditto, makes sure we use a fresh ArrayList each time (thanks Java garbage collector!)
        if(prefixNode == null) return wordList;         // return an empty arrayList if the prefix is not in the Trie
        Stack<Character> charStack = new Stack<>();     // this is the persistent character stack that will be used in the recursive search
        addWords(prefixNode,charStack);                 // run the recursive method to fill the wordList array
        return wordList;                                // no need to sort, because my TrieNode character arrays are naturally sorted, and hence my recursion is naturally sorted
    }

    /**
     * Helper method, to recursively add all the words, or word suffixes, in the trie from the initial node down
     *
     * @param testNode is the initial node passed in, could be root or a prefix node, and then the node passed down recursively
     * @param charStack is a reference to a persistent stack that characters are pushed onto on the way down and popped off on the way up
     * the prefix was not found.
     * References for this part:
     *      https://en.wikipedia.org/wiki/Depth-first_search
     *      https://www.quora.com/How-can-I-convert-an-element-of-a-character-stack-into-a-string-in-Java
     *      https://www.geeksforgeeks.org/stack-tostring-method-in-java-with-example/
     *      https://stackoverflow.com/questions/2794381/getting-a-list-of-words-from-a-trie
     */
    private void addWords(TrieNode testNode, Stack<Character> charStack){
        if(testNode.isTerminal()){                                  // NOTE: this doesn't stop the recursion continuing down. Just cause this node is terminal, there could still be more characters below.
            Iterator<Character> itr = charStack.iterator();         // iterator to get the characters out of the stack
            StringBuilder sb = new StringBuilder();                 // StringBuilder to temporarily save the word
            while(itr.hasNext()){
                sb.append(itr.next().toString());                   // append each character in the stack to the StringBuilder (this should still work even if my trie becomes PATRICIA'd)
            }
            wordList.add(prefixString + sb.toString());             // add the StringBuilder word to the wordList if the current node is a terminal node
        }
        int i = 0;                                                  // this counter identifies which character is associated with each loop
        for(TrieNode tn : testNode.children){                       // cycle through the TrieNode child array in each TrieNode
            if(tn != null) {                                        // don't go down paths with no children
                charStack.push((char) (i + 32));                    // converts the loop counter into a character, because I'm using a 26 element array for each TrieNode
                addWords(tn, charStack);                            // recursive call down each path
            }
            i++;                                                    // increment for the next letter
        }
        if(!charStack.empty()){
            charStack.pop();                                        // pop characters on the way back up, so the stack is ready to head down the next path
        }
    }

    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Finds the most frequently
     * occurring word represented in the trie (according to the dictionary file)
     * that begins with the provided prefix.
     *
     * @param prefix The prefix to search for
     * @return The most frequent word that starts with prefix
     */
    public String getMostFrequentWordWithPrefix(String prefix) {
        prefixNode = getNode(prefix);                   // initialise/reassign these instance variables each time
        maxFreq = 0;                                    // ditto
        mostFreq = null;                                // ditto
        if(prefixNode == null) return null;             // check that the prefix exists
        getFreqWord(prefixNode,prefix);                 // call the recursive method
        return mostFreq;
    }

    /**
     * Helper method, to recursively check all the words in the trie for the most frequent, from the prefix node down
     *
     * @param testNode is the initial node passed in, could be root or a prefix node, and then the node passed down recursively
     * @param word is the string that will be built up as the recursive calls are made
     */
    private void getFreqWord(TrieNode testNode, String word){
        if(testNode.isTerminal()){
            int freq = testNode.getData().getFrequency();
            if(freq > maxFreq){
                maxFreq = freq;
                mostFreq = word;
            }
        }
        int i = 0;                                              // this counter identifies which character is associated with each loop
        for(TrieNode tn : testNode.children){                   // cycle through the TrieNode child array in each TrieNode
            if(tn != null) {                                    // don't go down paths with no children
                getFreqWord(tn, word + (char)(i + 32));   // recursive call down each path; concatenation will make a new string
            }
            i++;                                                // increment for the next letter/child
        }
    }



    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Reads in a dictionary from file
     * and places all words into the trie.
     *
     * @param fileName the file to read from
     * @return the trie containing all the words
     */
    public static Trie readInDictionary(String fileName) {
        Trie t = new Trie();
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new FileInputStream(fileName));
            while (fileScanner.hasNextLine()) {
                String[] lineParts = fileScanner.nextLine().split(" +");
                t.insert(lineParts[1].trim(),new TrieData(Integer.parseInt(lineParts[2].trim())));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ " in the data directory!");
            return null;
        }
        return t;
    }
}
