import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class WordNet {

     private SeparateChainingHashST<String,List<Integer>> wordToNum;
     private SeparateChainingHashST<Integer,List<String>> numToWord;
     private Digraph g;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
         if (synsets == null || hypernyms == null)
              throw new IllegalArgumentException();
         this.wordToNum = new SeparateChainingHashST<>();
         this.numToWord = new SeparateChainingHashST<>();
         In syn = new In(synsets);
         In hyper = new In(hypernyms);
         String[] lines = syn.readAllLines();
         Arrays.stream(lines)
                .map(x -> x.split(",", 3))
                .forEach(x -> {
                    int v = Integer.parseInt(x[0]);
                    String[] synonyms = x[1].split(" ");
                    List<String> synonymsList = new ArrayList<>();
                    for (int i = 0; i < synonyms.length; i++) {
                        if (!wordToNum.contains(synonyms[i]))
                            wordToNum.put(synonyms[i], new ArrayList<>());
                        wordToNum.get(synonyms[i]).add(v);
                        synonymsList.add(synonyms[i]);
                    }
                    numToWord.put(v, synonymsList);
                });
         /*
         Iterator<String> keys = wordToNum.keys().iterator();
         while (keys.hasNext()) {
             String k = keys.next();
             System.out.println(k + " | " + wordToNum.get(k));
         }
         Iterator<Integer> verts = numToWord.keys().iterator();
         while (verts.hasNext()) {
             int k = verts.next();
             System.out.println(k + " | " + numToWord.get(k));
         }
         */
         String[] graphData = hyper.readAllLines();
         this.g = new Digraph(graphData.length);
         Arrays.stream(graphData)
                .map(x -> x.split(","))
                .map(x -> Arrays.stream(x).map(y -> Integer.parseInt(y)).toArray(Integer[]::new))
                .forEach(x -> {
                    for (int i = 1; i < x.length; i++) {
                        System.out.println(x[0] + " " + x[1]);
                        g.addEdge(x[0], x[i]);
                    }
                });
         int noV = g.V();
         boolean rooted = false;
         for (int i = 0; i < noV && !rooted; i++) {
             if (g.indegree(i) == 0 && g.outdegree(i) > 0)
                 rooted = true;
         }
         if (!rooted) throw new IllegalArgumentException();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordToNum.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
         if (word == null)
              throw new IllegalArgumentException();
         return wordToNum.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
         if (nounA == null || nounB == null)
              throw new IllegalArgumentException();
         return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
         if (nounA == null || nounB == null)
              throw new IllegalArgumentException();
         SAP sap = new SAP(g);
         int key = sap.ancestor(wordToNum.get(nounA), wordToNum.get(nounB));
         System.out.println(key);
         if (key < 0) throw new IllegalStateException();
         List<String> synset = numToWord.get(key);
         String out = "";
         for (int i = 0; i < synset.size(); i++) out+= synset.get(i) + " ";
         return out;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        try {
            WordNet wn = new WordNet(args[0], args[1]);
            String out = wn.sap("1840s", "A-line");
            System.out.println(out);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }
    }
}

