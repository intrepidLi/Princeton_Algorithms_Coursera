import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // System.out.println(StdIn.readString());
        String survival = "";
        int cnt = 0;
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            // System.out.println(str + "\n");
            cnt++;

            if (StdRandom.bernoulli(1.0/cnt)) {
                survival = str;
            }
            // System.out.println("fuck!!");
        }

        StdOut.println(survival);
    }

}
