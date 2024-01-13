import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Board {
    private final int dim;
    private int[][] tiles;
    private int manhattan_dis;
    private int hamming_dis;

    private int cal_manhattanDis(int i, int j, int[][] tiles, int dim) {
        int num = tiles[i][j];
        int tar_y = (num - 1) % dim;
        int tar_x = (num - 1) / dim;
        return Math.abs(tar_x - i) + Math.abs(tar_y - j);
    }


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dim = tiles.length;
        manhattan_dis = 0;
        hamming_dis = 0;
        this.tiles = new int[dim][dim];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * dim + j + 1) {
                    hamming_dis++;
                    manhattan_dis += cal_manhattanDis(i, j, tiles, dim);
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder finstr = new StringBuilder(String.valueOf(dim));
        finstr.append("\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                finstr.append(" ").append(tiles[i][j]);
            }
            finstr.append("\n");
        }
        return finstr.toString();
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming_dis;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan_dis;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (tiles[i][j] == 0) continue;
                else if (tiles[i][j] != i * dim + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        return Objects.equals(y.toString(), this.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborBoards = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (tiles[i][j] == 0) {
                    if (i != 0) {
                        Board board2 = new Board(tiles);
                        board2.swap(i, j, i - 1, j);
                        neighborBoards.add(board2);
                    }
                    if (i != dim - 1) {
                        Board board3 = new Board(tiles);
                        board3.swap(i, j, i + 1, j);
                        neighborBoards.add(board3);
                    }
                    if (j != 0) {
                        Board board4 = new Board(tiles);
                        board4.swap(i, j, i, j - 1);
                        neighborBoards.add(board4);
                    }
                    if (j != dim - 1) {
                        Board board5 = new Board(tiles);
                        board5.swap(i, j, i, j + 1);
                        neighborBoards.add(board5);
                    }
                }
            }
        }
        return neighborBoards;
    }

    private void swap(int row, int col, int row1, int col1) {
        if (tiles[row1][col1] != 0) {
            if (tiles[row1][col1] == row1 * dim + col1 + 1) {
                hamming_dis += 1;
            }
            else if (tiles[row1][col1] == row * dim + col + 1) {
                hamming_dis -= 1;
            }
            manhattan_dis -= cal_manhattanDis(row1, col1, tiles, dim);
        }
        if (tiles[row][col] != 0) {
            if (tiles[row][col] == row1 * dim + col1 + 1) {
                hamming_dis -= 1;
            }
            else if (tiles[row][col] == row * dim + col + 1) {
                hamming_dis += 1;
            }
            manhattan_dis -= cal_manhattanDis(row, col, tiles, dim);
        }
        int t = this.tiles[row1][col1];
        tiles[row1][col1] = tiles[row][col];
        tiles[row][col] = t;
        if (tiles[row][col] != 0) manhattan_dis += cal_manhattanDis(row, col, tiles, dim);
        if (tiles[row1][col1] != 0) manhattan_dis += cal_manhattanDis(row1, col1, tiles, dim);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int row = 0;
        int col = 0;
        Board twinBoard = new Board(tiles);
        if (tiles[row][col] == 0) col++;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (tiles[i][j] != 0 && tiles[row][col] != tiles[i][j]) {
                    twinBoard.swap(row, col, i, j);
                    return twinBoard;
                }
            }
        }
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}