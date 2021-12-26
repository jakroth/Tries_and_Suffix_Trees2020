package cp3.ass01.suffixtree;

import cp3.ass01.suffixtrie.SuffixProfiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SuffixTreeDriver {
    public static void main(String[] args) {

        //TESTING the readIn function of the SuffixTree
        //SuffixTree st = SuffixTree.readInFromFile("test_data.txt");
        //System.out.println(st.getInput());

        //TESTING the insert() function of the SuffixTree
        //SuffixTree st = new SuffixTree();
        //st.setInput("abcd.cde");
        //st.insert();
        //st.printTree();
        //System.out.println(st.get("cd"));

        //TESTING the SuffixTrie_FullText readIn, insert() and get()
        test1();

        //TESTING the  SuffixTree readIn(), insert() and get() function of
        test2();

        //TESTING the UkkonenTree insert() and get()
        //test3();

        //TESTING the UkkonenTree readIn() and get()
        //test4();

        //TESTING the Ukonnen vs the SuffixTrie
        String input = "FrankChap02.txt";
        int runs = 10;


        //TESTING the time taken
        //trieTimeTest(input,runs);
        //treeTimeTest(input,runs);
        //ukeTimeTest(input,runs);
        //exampleUkeTimeTest(input, runs);

        //TESTING the memory usage
        //trieMemTest(input);
        //treeMemTest(input);
        //ukeMemTest(input);
        //exampleUkeMemTest(input);
    }



    // **********READ IN TESTS********** //

    public static void test1(){
        System.out.println("Testing the SuffixTrie_FullText");
        SuffixTrie_FullText st = SuffixTrie_FullText.readInFromFile("Frank02.txt");
        System.out.println("Reading in \"Frank02.txt\"...");
        System.out.println();

        String s1 = "accompan";
        System.out.println("\"" + s1 + "\" " + "found");
        System.out.println("Index in input string: " + st.get(s1));
        System.out.println();

        String s2 = "disaster";
        System.out.println("\"" + s2 + "\" " + "found");
        System.out.println("Index in input string: " + st.get(s2));
        System.out.println();

        String s3 = "and";
        System.out.println("\"" + s3 + "\" " + "found");
        System.out.println("Index in input string: " + st.get(s3));
        System.out.println();
    }

    public static void test2(){
        System.out.println("Testing the SuffixTree");
        SuffixTree st = SuffixTree.readInFromFile("Frank02.txt");
        System.out.println("Reading in \"Frank02.txt\"...");
        System.out.println();

        String s1 = "accompan";
        System.out.println("\"" + s1 + "\" " + "found");
        System.out.println("Index in input string: " + st.get(s1));
        System.out.println();

        String s2 = "disaster";
        System.out.println("\"" + s2 + "\" " + "found");
        System.out.println("Index in input string: " + st.get(s2));
        System.out.println();

        String s3 = "and";
        System.out.println("\"" + s3 + "\" " + "found");
        System.out.println("Index in input string: " + st.get(s3));
        System.out.println();
    }


    public static void test3() {
        UkkonenTree uke = new UkkonenTree(false);
        uke.setInput("minimize$");
        uke.insert();
        uke.printTree();
        System.out.println();

        String s1 = "ize";
        System.out.println("\"" + s1 + "\" " + "found, finished in node: " + uke.get(s1));
        System.out.println("Index in input string: " + uke.get(s1).getData());
        System.out.println();

        String s2 = "mi";
        System.out.println("\"" + s2 + "\" " + "found, finished in node: " + uke.get(s2));
        System.out.println("Index in input string: " + uke.get(s2).getData());
        System.out.println();

    }

    public static void test4() {
        UkkonenTree uke = UkkonenTree.readInFromFile("minimize.txt", false);
        uke.printTree();
        System.out.println();

        String s1 = "ize";
        System.out.println("\"" + s1 + "\" " + "found, finished in node: " + uke.get(s1));
        System.out.println("Index in input string: " + uke.get(s1).getData());
        System.out.println();

        String s2 = "mi";
        System.out.println("\"" + s2 + "\" " + "found, finished in node: " + uke.get(s2));
        System.out.println("Index in input string: " + uke.get(s2).getData());
        System.out.println();
    }



// **********PROFILING - TIME********** //

    public static void trieTimeTest(String filename, int n) {
        System.out.println("Running the profiler on the SuffixTrie_FullText");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method " + n + " times...");

        // run the task once to get the JVM warmed up
        SuffixTrie_FullText st = SuffixTrie_FullText.readInFromFile(filename);
        st = null;
        profiler.takeOutGarbage();

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            st = SuffixTrie_FullText.readInFromFile(filename);
            profiler.avgToc();
            st = null;
            profiler.takeOutGarbage();
        }

        System.out.println("Average time to construct SuffixTrie_FullText (ns): " + profiler.avgCalc());
        System.out.println("Average time to construct SuffixTrie_FullText (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }


    public static void treeTimeTest(String filename, int n) {
        System.out.println("Running the profiler on the SuffixTree");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method " + n + " times...");

        // run the task once to get the JVM warmed up
        SuffixTree st = SuffixTree.readInFromFile(filename);
        st = null;
        profiler.takeOutGarbage();

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            st = SuffixTree.readInFromFile(filename);
            profiler.avgToc();
            st = null;
            profiler.takeOutGarbage();
        }

        System.out.println("Average time to construct SuffixTree (ns): " + profiler.avgCalc());
        System.out.println("Average time to construct SuffixTree (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }


    public static void ukeTimeTest(String filename, int n) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method " + n + " times...");

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            UkkonenTree uke = UkkonenTree.readInFromFile(filename, false);
            profiler.avgToc();
            uke = null;
            profiler.takeOutGarbage();
        }

        System.out.println("Average time to construct UkonnenTree (ns): " + profiler.avgCalc());
        System.out.println("Average time to construct UkonnenTree (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }


    public static void exampleUkeTimeTest(String filename, int n) {
        System.out.println("Running the profiler");
        System.out.println("Setting up the string...");
        Scanner fileScanner;
        String input = "";
        try {
            fileScanner = new Scanner(new FileInputStream("data" + File.separator + filename));
            StringBuilder sb = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                sb.append(fileScanner.nextLine());
            }
            sb.append("$");                                 // this character marks the end of the input string.
            input = sb.toString();                   // set the input instance variable to the string, it will be needed in other methods
        }catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +filename+ " in the data directory!");
        }

        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();


        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method " + n + " times...");

        // run the task once to get the JVM warmed up
        ExampleUke ex = new ExampleUke();
        ex.process(input);
        ex = null;
        profiler.takeOutGarbage();

        // run the task n times
        profiler.avgReset(n);
        for (int i = 0; i < n; i++) {
            profiler.avgTic();
            ex = new ExampleUke();
            ex.process(input);
            profiler.avgToc();
            ex = null;
            profiler.takeOutGarbage();
        }

        System.out.println("Average time to construct Ukkonnen suffix tree (ns): " + profiler.avgCalc());
        System.out.println("Average time to construct Ukkonnen suffix tree (ms): " + SuffixProfiler.nsToms(profiler.avgCalc()));

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }




    // **********PROFILING - MEMORY********** //


    public static void trieMemTest(String filename) {
        System.out.println("Running the profiler on SuffixTrie_FullText");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method...");
        System.out.println();

        profiler.ticMem();
        SuffixTrie_FullText st = SuffixTrie_FullText.readInFromFile(filename);
        long memory = profiler.tocMem();

        System.out.println("Used memory (bytes): " + memory);
        System.out.println("Used memory (megabytes): " + SuffixProfiler.bytesToMegabytes(memory));
        System.out.println();

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }


    public static void treeMemTest(String filename) {
        System.out.println("Running the profiler on SuffixTree");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method...");
        System.out.println();

        profiler.ticMem();
        SuffixTree st = SuffixTree.readInFromFile(filename);
        long memory = profiler.tocMem();

        System.out.println("Used memory (bytes): " + memory);
        System.out.println("Used memory (megabytes): " + SuffixProfiler.bytesToMegabytes(memory));
        System.out.println();

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }


    public static void ukeMemTest(String filename) {
        System.out.println("Running the profiler");
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method...");
        System.out.println();

        profiler.ticMem();
        UkkonenTree uke = UkkonenTree.readInFromFile(filename, false);
        long memory = profiler.tocMem();

        System.out.println("Used memory (bytes): " + memory);
        System.out.println("Used memory (megabytes): " + SuffixProfiler.bytesToMegabytes(memory));
        System.out.println();

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }

    public static void exampleUkeMemTest(String filename) {
        System.out.println("Running the profiler");
        System.out.println("Setting up the string...");
        Scanner fileScanner;
        String input = "";
        try {
            fileScanner = new Scanner(new FileInputStream("data" + File.separator + filename));
            StringBuilder sb = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                sb.append(fileScanner.nextLine());
            }
            sb.append("$");                                 // this character marks the end of the input string.
            input = sb.toString();                   // set the input instance variable to the string, it will be needed in other methods
        }catch (FileNotFoundException ex) {
            System.out.println("could not find the file " +filename+ " in the data directory!");
        }
        System.out.println("Press enter to start profiling...");
        Scanner in = new Scanner(System.in);
        in.nextLine();

        // instantiate the profiler object
        SuffixProfiler profiler = new SuffixProfiler();
        System.out.println("Running the readInFromFile() method...");
        System.out.println();

        // run the task once to get the JVM warmed up
        ExampleUke ex = new ExampleUke();
        ex.process(input);
        ex = null;
        profiler.takeOutGarbage();

        // run the task once
        profiler.ticMem();
        ex = new ExampleUke();
        ex.process(input);
        long memory = profiler.tocMem();

        System.out.println("Used memory (bytes): " + memory);
        System.out.println("Used memory (megabytes): " + SuffixProfiler.bytesToMegabytes(memory));
        System.out.println();

        System.out.println("Press enter to stop profiling...");
        in.nextLine();
    }
}











