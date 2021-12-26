package cp3.ass01.suffixtrie;

import java.util.ArrayList;

public class SuffixTrieData {

    private ArrayList<SuffixIndex> startIndexes = new ArrayList<>();

    /**
     * Add a new SuffixIndex to this SuffixTrieData.
     * A SuffixTrieData can (will likely!) have multiple SuffixIndex's if multiple substrings follow that route
     *
     * @param startIndex the SuffixIndex containing the starting parameters of this substring
     */
    public void addStartIndex(SuffixIndex startIndex) {
        startIndexes.add(startIndex);
    }

    /**
     * Removes the last added SuffixIndex from this SuffixTrieData.
     *
     */
    public void removeLastIndex() {
        startIndexes.remove(startIndexes.size()-1);
    }

    /**
     * The toString method for the SuffixTrieData.
     * Outputs in the format specified in the SuffixIndex toString method.
     *
     * @return a string representing the indices in this SuffixTrieData.
     */
    public String toString() {
        return startIndexes.toString();
    }

    public ArrayList<SuffixIndex> getArray(){
        return startIndexes;
    }
}
