import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode curNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // pq = new MinPQ<>();
        if (initial == null) {
            throw new IllegalArgumentException("Initial Board is null!!!");
        }
        curNode = new SearchNode(initial, null);
        SearchNode curTwinNode = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();

        pq.insert(curNode);
        twinPq.insert(curTwinNode);
        // StdOut.println("twin is");
        // StdOut.println(curTwinNode.getBoard());
        while (true) {
            curNode = pq.delMin();
            if (curNode.board.isGoal()) {
                break;
            }
            putNeighborIntoPq(curNode, pq);

            curTwinNode = twinPq.delMin();
            // StdOut.println("moves is");
            // StdOut.println(curTwinNode.getMoves());
            if (curTwinNode.board.isGoal()) {
                break;
            }
            // StdOut.println("board is");
            // StdOut.println(curTwinNode.getBoard());
            putNeighborIntoPq(curTwinNode, twinPq);
        }
    }

    // private MinPQ<Board> pq;
    // 注意 MinPQ对象必须是一个Comparable对象，因此不能设为Board，用SearchNode记录
    private class SearchNode implements Comparable<SearchNode> {
        public Board board;
        public int moves;
        public SearchNode preSearchNode;
        public int priority;

        public SearchNode(Board curBoard, SearchNode preSearchNode) {
            this.board = curBoard;
            this.preSearchNode = preSearchNode;
            if (preSearchNode == null) moves = 0;
            else moves = preSearchNode.moves + 1;
            priority = moves + curBoard.manhattan();
        }

        @Override
        public int compareTo(SearchNode s) {
            return Integer.compare(this.priority, s.priority);
        }
    }


    private void putNeighborIntoPq(SearchNode searchNode, MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = searchNode.board.neighbors();
        // StdOut.println(searchNode.getBoard());
        for (Board neighbor : neighbors) {
            // StdOut.println("this neighbor is");
            // StdOut.println(neighbor);
            // 注意这里的剪枝操作
            if (searchNode.preSearchNode == null || !searchNode.preSearchNode.board.equals(neighbor)) {
                pq.insert(new SearchNode(neighbor, searchNode));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // StdOut.println(curNode.getBoard());
        return curNode.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.isSolvable()) {
            return curNode.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.isSolvable()) {
            Stack<Board> solutions = new Stack<>();
            SearchNode node = curNode;
            while (node != null) {
                solutions.push(node.board);
                node = node.preSearchNode;
            }
            return solutions;
        } else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // StdOut.println(initial.manhattan());
        // StdOut.println("initial is");
        // StdOut.println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println(board.hamming());
            }
        }
    }

}