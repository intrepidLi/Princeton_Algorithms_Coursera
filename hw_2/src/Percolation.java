import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private byte[] bytes; // 3位数组代表site的状态
    private int num;
    private int openNum;
    // private int[] path;
    private WeightedQuickUnionUF uf;
    private boolean doPercolate;
    // private WeightedQuickUnionUF uf_full;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();  
        }
        num = n;
        bytes = new byte[n * n + 2];
        bytes[0] = 2;
        bytes[n * n + 1] = 1;
        // for (int i = 0; i < 3; i++) {
        //     bytes[0][i] = 1;
        //     bytes[n * n + 1][i] = 1;
        // }
        openNum = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        // uf_full = new WeightedQuickUnionUF(n * n + 1);
        doPercolate = false;
        // path = new int[n * n];
        // for (int i = 1; i <= n; i++) {
        //     uf.union(0, i);
        // }
        // int k = 0;
    }

    private int idxOf(int row, int col) {
        return  (row - 1) * num + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row, col);
        int cur = idxOf(row, col);
        int neighbor = 0;
        if (bytes[cur] == 0) { // 表示该site没有open
            // StdOut.println("opening " + cur + " ......");
            byte status = 4;
            bytes[cur] = 4;
            openNum += 1;
            if (row == 1) {
                // uf.union(cur, 0);
                bytes[cur] |= 2; // 110 与0相连
                status |= 6;
                // uf_full.union(cur, 0);
            }
            if (row == num) {
                // uf.union(cur, num * num + 1);
                bytes[cur] |= 1; // 101 与n * n + 1相连
                status |= 5;
            }

            neighbor = cur + num;
            status = numJudge(row, cur, neighbor, num, status);
            neighbor = cur - num;
            status = numJudge(row, cur, neighbor, 1, status);
            neighbor = cur + 1;
            status = numJudge(col, cur, neighbor, num, status);
            neighbor = cur - 1;
            status = numJudge(col, cur, neighbor, 1, status);
            int rootPrevious = uf.find(cur);
            if (rootPrevious != 0 && rootPrevious != num * num + 1) {
                bytes[rootPrevious] = status;
            }
            bytes[cur] = status;
            // .println("bytes[cur] = " + bytes[cur] + "\n");
            if (bytes[cur] == 7) {
                doPercolate = true;
            }
        }
    }

    private byte numJudge(int line, int cur, int neighbor, int number, byte status) {
        byte status1 = status;
        if (line != number && ((bytes[neighbor] & 4) == 4)) { // 右 和 下
            int rootAdjacent = uf.find(neighbor);
            status1 |= bytes[rootAdjacent];
            uf.union(cur, neighbor);
            /* if (bytes[neighbor][1] == 1) {
                bytes[cur][1] = 1;
            }
            if (bytes[neighbor][2] == 1) {
                bytes[cur][2] = 1;
            }*/
            // uf_full.union(cur, cur + 1);
        }
        return status1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row, col);
        return (bytes[idxOf(row, col)] & 4) == 4;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int cur = idxOf(row, col);
        int root = uf.find(cur);

        return ((bytes[root] & 2) == 2);
        // int root1 = uf_full.find(idxOf(row, col));
        // int root2 = uf_full.find(0);
        // return root1 == root2;
    }

    // for debug private -> public
    private void printFull() {
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= num; j++) {
                StdOut.println("row is " + i + " col is " + j);
                int root = uf.find(idxOf(i, j));
                StdOut.println("root is " + root);
            }
            StdOut.println("");
        }

        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= num; j++) {
                if (isFull(i, j)) {
                    StdOut.print("True ");
                } else {
                    StdOut.print("False ");
                }
            }
            StdOut.println("");
        }
    }

    private void check(int row, int col) {
        if (row < 1 || row > num || col < 1 || col > num) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
       /* for (int i = 1; i <= num; i++) {
            if (isFull(num, i)) {
                return true;
            }
        }

        return false;*/
        // return uf.find(0) == uf.find(num * num + 1);
        return doPercolate;
    }
}
