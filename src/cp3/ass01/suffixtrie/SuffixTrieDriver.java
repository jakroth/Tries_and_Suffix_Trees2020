package cp3.ass01.suffixtrie;

import java.util.*;

/**
 * java cp3.ass01.suffixtrie.SuffixTrieDriver
 *
 * This is an example class to drive the Suffix Trie project.  You can use this a starting point
 * to test your Suffix Trie implementation.
 *
 * It expects user input of the file to process as the first line and then the subsequent lines are
 * the words/phrases/suffixes to search for with an empty line terminating the user input.
 *
 * For example:
 *
Frankenstien.txt
and
the
, the
onster
monst
ing? This
 *
 * @author lewi0146
 */
public class SuffixTrieDriver {
    public static void main(String[] args) {

        // TESTING
        String file = "FrankChap02.txt";
        int runs = 10;

        // TESTING the SuffixTrie
        runTime(file, runs);
        //runMem(file);
        //runGetTime(file,runs);

        // TESTING the SuffixTrie_Arr
        //runTime_Arr(file, runs);
        //runMem_Arr(file);
        //runGetTime_Arr(file,runs);


        // TESTING - the original driver program

/*        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        Queue<String> ss = new ArrayDeque<>();
        String suffix = in.nextLine();

        while (!suffix.equals(""))
        {
            ss.offer(suffix);
            suffix = in.nextLine();
        }

        SuffixTrie st = SuffixTrie.readInFromFile(fileName);

        while (!ss.isEmpty()) {
            String s = ss.poll();
            SuffixTrieNode sn = st.get(s);
            System.out.println("[" + s + "]: " + sn);
        }*/



    }


    public static void runTime(String filename, int n) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method " + n + " times...");

        // run the task once to get the JVM warmed up
        SuffixTrie st = SuffixTrie.readInFromFile(filename);
        st = null;
        profiler.takeOutGarbage();

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            st = SuffixTrie.readInFromFile(filename);
            profiler.avgToc();
            st = null;
            profiler.takeOutGarbage();
        }

        System.out.println("Average time to construct SuffixTrie (ns): " + profiler.avgCalc());
        System.out.println("Average time to construct SuffixTrie (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

    public static void runTime_Arr(String filename, int n) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method " + n + " times...");

        // run the task once to get the JVM warmed up
        SuffixTrie_Arr st = SuffixTrie_Arr.readInFromFile(filename);
        st = null;
        profiler.takeOutGarbage();

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            st = SuffixTrie_Arr.readInFromFile(filename);
            profiler.avgToc();
            st = null;
            profiler.takeOutGarbage();
        }

        System.out.println("Average time (ns): " + profiler.avgCalc());
        System.out.println("Average time (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

    public static void runMem(String filename) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method...");
        System.out.println();

        profiler.ticMem();
        SuffixTrie st = SuffixTrie.readInFromFile(filename);
        long memory = profiler.tocMem();

        System.out.println("Used memory (bytes): " + memory);
        System.out.println("Used memory (megabytes): " + SuffixProfiler.bytesToMegabytes(memory));
        System.out.println();

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

    public static void runMem_Arr(String filename) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method...");
        System.out.println();

        profiler.ticMem();
        SuffixTrie_Arr st = SuffixTrie_Arr.readInFromFile(filename);
        long memory = profiler.tocMem();

        System.out.println("Used memory (bytes): " + memory);
        System.out.println("Used memory (megabytes): " + SuffixProfiler.bytesToMegabytes(memory));
        System.out.println();
        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

    public static void runGetTime(String filename, int n) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        System.out.println("Reading in the suffix trie...");
        SuffixTrie st = SuffixTrie.readInFromFile(filename);
        System.out.println("Done!");
        System.out.println();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the get() method on 10 substrings " + n + " times...");

        // here is a ten substring array to test
        String[] substr = {"and", "the",  ", the", "onster", "monst", "fault", "my", ", spring", "loath", "away"};

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            for(String s : substr) {
                st.get(substr[i]);
            }
            profiler.avgToc();
        }

        System.out.println("Average time to find 10 substrings (ns): " + profiler.avgCalc());
        System.out.println("Average time to find 10 substrings (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

    public static void runGetTime_Arr(String filename, int n) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        System.out.println("Reading in the suffix trie...");
        SuffixTrie st = SuffixTrie.readInFromFile(filename);
        System.out.println("Done!");
        System.out.println();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the get() method on 10 substrings " + n + " times...");

        // here is a ten substring array to test
        String[] substr = {"and", "the",  ", the", "onster", "monst", "fault", "my", ", spring", "loath", "away"};

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            for(String s : substr) {
                st.get(substr[i]);
            }
            profiler.avgToc();
        }

        System.out.println("Average time to find 10 substrings (ns): " + profiler.avgCalc());
        System.out.println("Average time to find 10 substrings (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

}