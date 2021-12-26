package cp3.ass01.suffixtree;

import cp3.ass01.suffixtrie.SuffixTrieNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SuffixTrie_FullText {

    private SuffixTrieNode root = new SuffixTrieNode();
    private String input;

    /**
     * Insert a String into the suffix trie.
     *
     * @param startPosition can be any starting index
     * @return the final node inserted
     */
    public SuffixTrieNode insert(int startPosition) {
        input = input.toLowerCase();
        SuffixTrieNode itNode = root;                                           // instantiate iterative node
        int sentence = 0;                                                           // instantiate sentence number
        int ch = 0;                                                                 // instantiate character number
        for(int i = 0; i < input.length(); i++) {                              // iterate over every substring in the sentence
            itNode = root;                                                      // reset itNode to root before each substring
            for (int j = i; j < input.length(); j++) {                         // iterate over every character in each substring
                if(itNode.getChild(input.charAt(j)) == null) {                     // see if the character already exists
                    itNode.addChild(input.charAt(j), new SuffixTrieNode());         // add new node if not
                }
                itNode = itNode.getChild(input.charAt(j));                          // change itNode to the child node
                itNode.addData(sentence, ch);                               // add the substring starting index data to all child nodes on the path
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
     * Get the suffix trie node associated with the given (sub)string.
     *
     * @param str the (sub)string to search for
     * @return  the final node in the (sub)string
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
     * The text file is read in as a single String
     * 
     * It is called in the following way:
     * <code>SuffixTrie st = SuffixTrie.readInFromFile("Frank01e.txt");</code>
     *
     * @param fileName the file to read  from
     * @return the SuffixTrie with all the text added
     */
    public static SuffixTrie_FullText readInFromFile(String fileName) {
        SuffixTrie_FullText st = new SuffixTrie_FullText();
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new FileInputStream("data" + File.separator + fileName));
            StringBuilder sb = new StringBuilder();
            while(fileScanner.hasNextLine()){
                sb.append(fileScanner.nextLine());
            }
            st.input = sb.toString();
            st.insert(0);
        } catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +fileName+ " in the data directory!");
            return null;
        }
        return st;
    }
}
