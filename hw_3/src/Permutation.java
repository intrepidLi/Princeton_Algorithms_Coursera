import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int n = 0; // the number of input elements
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            n++;
            if (n <= k) { // if the queue is not full, just enqueue the input
                rq.enqueue(s);
            } else { // if the queue is full, decide whether to keep or junk the input
                int r = StdRandom.uniformInt(n); // generate a random number in [0, n)
                if (r < k) { // if the random number is less than k, keep the input and replace a random element in the queue
                    rq.dequeue(); // remove a random element from the queue
                    rq.enqueue(s); // add the input to the queue
                } // otherwise, just junk the input and do nothing
            }
        }
        for (int i = 0; i < k; i++) { // print out k elements from the queue in random order
            StdOut.println(rq.dequeue());
        }
    }

    // 法二
//    public static void main(String[] args) {
//        int k = Integer.parseInt(args[0]);
//        RandomizedQueue<String> rq = new RandomizedQueue<>();
//        String[] strs = StdIn.readAllStrings();
//        StdRandom.shuffle(strs);
//        for (int i = 0; i < k; i++) {
//            rq.enqueue(strs[i]);
//        }
//        for (int i = 0; i < k; i++) {
//            StdOut.println(rq.dequeue());
//        }
//    }
}

