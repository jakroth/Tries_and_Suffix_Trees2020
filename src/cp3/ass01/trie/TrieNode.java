package cp3.ass01.trie;


public class TrieNode {

    private TrieData data = null;
    private boolean terminal = false;
    private int numChildren = 0;
    private final int ASCII = 32;   // this is the adjustment for ascii characters so that 'a' == 0
    TrieNode[] children = new TrieNode[224]; // exclude the first 32 control characters of the extended ASCII codes

    /**
     * Lookup a child node of the current node that is associated with a
     * particular character label.
     *
     * @param label The label to search for
     * @return The child node associated with the provided label
     */
    public TrieNode getChild(char label) {
        return children[label - ASCII];
    }

    /**
     * Add a child node to the current node, and associate it with the provided
     * label.
     *
     * @param label The character label to associate the new child node with
     * @param node The new child node that is to be attached to the current node
     */
    public void addChild(char label, TrieNode node) {
        children[label - ASCII] = node;
        numChildren++;
    }

    /**
     * Add a new data object to the node.
     *
     * @param dataObject The data object to be added to the node.
     */
    public void addData(TrieData dataObject) {
        this.data = dataObject;
    }

    /**
     * Gets the data object for this node.
     *
     * @return the TrieData object associated with this node (or null if it hasn't got one)
     */
    public TrieData getData() {
        return this.data;
    }

    /**
     * Checks whether this node is a terminal node or not
     *
     * @return whether this node is a terminal node
     */
    public boolean isTerminal() {
        return terminal;
    }

    /**
     * Sets this node as a terminal node (or not)
     *
     * @param terminal is the boolean value for terminal
     */
    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    /**
     * The toString() method for the TrieNode that outputs in the format
     *   TrieNode; isTerminal=[true|false], data={toString of data}, #children={number of children}
     * for example,
     *   TrieNode; isTerminal=true, data=3, #children=1
     * @return a string representing this TrieNode
     */
    @Override
    public String toString() {
        return "TrieNode; isTerminal=" + terminal + ", data=" + data + ", #children=" + numChildren;
    }
}

