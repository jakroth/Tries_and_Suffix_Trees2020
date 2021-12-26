package cp3.ass01.suffixtrie;

public class SuffixTrieNode_Arr {

    private SuffixTrieData data = new SuffixTrieData();
    private int numChildren = 0;
    private final int ASCII = 32;                           // this is the adjustment for ascii characters so that 'a' == 0
    SuffixTrieNode_Arr[] children = new SuffixTrieNode_Arr[199];    // exclude the first 32 control characters of the extended ASCII codes

    /**
     * Lookup a child node of the current node that is associated with the character label.
     *
     * @param label The label to search for
     * @return The child node associated with the provided label
     */
    public SuffixTrieNode_Arr getChild(char label) {
        if(label == 8212) label = 151;              // convert unicode em-dash to ascii em-dash
        if(label == 8217) label = 146;              // convert unicode right single quote to ascii
        if(label == 8216) label = 145;              // convert unicode left single quote to ascii
        if(label == 65533) label = 256;             // handle the unicode replacement character
        if(label >64 && label < 91) label+=32;      // convert uppercase to lowercase
        if(label > 90) label -= 26;                 // use up the space left by uppercase
        return children[label - ASCII];
    }

    /**
     * Add a child node to the current node, at the position associated with the provided label.
     *
     * @param label The character label to associate the new child node with
     * @param node The new child node that is to be attached to the current node
     */
    public void addChild(char label, SuffixTrieNode_Arr node) {
        if(label == 8212) label = 151;              // convert unicode em-dash to ascii em-dash
        if(label == 8217) label = 146;              // convert unicode right single quote to ascii
        if(label == 8216) label = 145;              // convert unicode left single quote to ascii
        if(label == 65533) label = 256;             // handle the unicode replacement character
        if(label >64 && label < 91) label+=32;      // convert uppercase to lowercase
        if(label > 90) label -= 26;                 // use up the space left by uppercase
        children[label - ASCII] = node;
        numChildren++;
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
     * The toString method for the SuffixTrieNode.
     * Outputs in the format specified in the SuffixTrieData toString method.
     *
     * @return a string representing the data in this SuffixTrieNode.
     */
    @Override
    public String toString() {
        return data.toString();
    }
}
