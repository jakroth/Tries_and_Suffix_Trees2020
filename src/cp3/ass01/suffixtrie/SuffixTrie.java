package cp3.ass01.suffixtrie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SuffixTrie {

    private SuffixTrieNode root = new SuffixTrieNode();

    /**
     * Insert a String into the suffix trie.
     * For this assignment, the string <code>str</code> is a sentence from the given text file.
     * For this assignment, a sentence is delimited by one of these three characters: ?!.
     *
     * @param str the sentence to insert
     * @param startPosition can be any starting index, but is commonly the sentence number in an input file
     * @return the final node inserted
     */
    public SuffixTrieNode insert(String str, int startPosition) {
        str = str.toLowerCase();
        char[] sentence = str.toCharArray();
        SuffixTrieNode itNode = root;                                           // instantiate iterative node
        for(int i = 0; i < sentence.length; i++) {                              // iterate over every substring in the sentence
            itNode = root;                                                      // reset itNode to root before each substring
            for (int j = i; j < sentence.length; j++) {                         // iterate over every character in each substring
                if (itNode.getChild(sentence[j]) == null) {                     // see if the character already exists
                    itNode.addChild(sentence[j], new SuffixTrieNode());         // add new node if not
                }
                itNode = itNode.getChild(sentence[j]);                          // change itNode to the child node
                itNode.addData(startPosition, i);                               // add the substring starting index data to all child nodes on the path
            }
        }
        return itNode;
    }

    /**
     * Get the suffix trie node associated with the given (sub)string.
     *
     * @param str the (sub)string to search for
     * @return the final node in the (sub)string
     */
    public SuffixTrieNode get(String str) {
        str = str.toLowerCase();
        char[] sentence = str.toCharArray();
        SuffixTrieNode itNode = root;                       // instantiate iterative node
        for (char ch : sentence) {                          // keep iterating down the ST for the length of the sentence
            if (itNode.getChild(ch) == null) {
                return null;
            }
            itNode = itNode.getChild(ch);                   // change itNode to the child
        }
        return itNode;                                      // return the final node, at the end of the sentence
    }

    /**
     * Helper/Factory static method to build a SuffixTrie object from the text in the given file.
     * The text file is broken up into sentences and each sentence is inserted into the suffix trie.
     * 
     * It is called in the following way:
     * <code>SuffixTrie st = SuffixTrie.readInFromFile("Frank01e.txt");</code>
     *
     * @param fileName the file to read sentences from
     * @return the SuffixTrie with all the sentences added
     */
    public static SuffixTrie readInFromFile(String fileName) {
        SuffixTrie st = new SuffixTrie();
        Scanner fileScanner;
        int count = 0;
        try {
            fileScanner = new Scanner(new FileInputStream("data" + File.separator + fileName));
            while (fileScanner.hasNextLine()) {                                                         // this could contain many sentences
                String[] lineParts = fileScanner.nextLine().split("[!?.]+");                      // split this line into sentences using these characters
                if(lineParts[0].isEmpty() && lineParts.length == 1) continue;                           // check to see if the line is empty
                for (String s : lineParts){
                    st.insert(s.trim(), count);                                                         // add each sentence to the ST
                    count++;                                                                            // count of sentences added, used to index each sentence/substring
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ " in the data directory!");
            return null;
        }
        return st;
    }
}