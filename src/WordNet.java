import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import java.util.Map;
import java.util.HashMap;


public class WordNet {
    // Digraph G;
    private Digraph G;
    // Vertex of G
    private Map<Integer, String[]> synsetMap;

    // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
        if (synsets == null) {
            throw new IllegalArgumentException("synsets is null");
        }
        if (hypernyms == null) {
            throw new IllegalArgumentException("hypernyms is null");
        }
        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);

        // Map<Integer, String[]> synsetMap = new HashMap<>();
        synsetMap = new HashMap<>();

        String line = synsetsIn.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String[] synset = fields[1].split(" ");
            synsetMap.put(id, synset);
        }

        G = new Digraph(synsetMap.size());

        line = hypernymsIn.readLine();
        while (line != null) {
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);
                G.addEdge(v, w);
            }
        }

        // Check if G is a rooted DAG
        int rootCount = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                rootCount++;
            }
        }
        if (rootCount != 1) {
            throw new IllegalArgumentException("G is not a rooted DAG");
        }

        // Check if G is a DAG
        DirectedCycle dc = new DirectedCycle(G);
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()

   // is the word a WordNet noun?
   public boolean isNoun(String word)

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)

   // do unit testing of this class
   public static void main(String[] args)
}
