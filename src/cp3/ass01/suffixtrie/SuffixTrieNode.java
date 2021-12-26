package cp3.ass01.suffixtrie;

import java.util.Map;
import java.util.TreeMap;

public class SuffixTrieNode {

    private SuffixTrieData data = new SuffixTrieData();
    private Map<Character,SuffixTrieNode> children = new TreeMap<>();

    /**
     * Lookup a child node of the current node that is associated with the character label.
     *
     * @param label The label to search for
     * @return The child node associated with the provided label
     */
    public SuffixTrieNode getChild(char label) {
        return children.get(label);
    }

    /**
     * Add a child node to the current node, at the position associated with the provided label.
     *
     * @param label The character label to associate the new child node with
     * @param node The new child node that is to be attached to the current node
     */
    public void addChild(char label, SuffixTrieNode node) {
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
