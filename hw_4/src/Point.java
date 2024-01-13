import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point


    public Point(int x, int y) { // constructs the point (x, y)
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }


    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1; // y坐标小的排在前面
        }
        if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        }
        if (this.x > that.x) {
            return 1;
        }
        return 0;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        double numer = that.y - this.y;
        double denom = that.x - this.x;
        if (numer == 0 && denom == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (denom == 0) {
            return Double.POSITIVE_INFINITY;
        }
        if (numer == 0) {
            return 0;
        }
        return numer / denom;
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double slope1 = slopeTo(o1);
                double slope2 = slopeTo(o2);
                if (slope1 < slope2) return -1; //斜率小的在前
                if (slope1 > slope2) return 1;
                return 0;
            }
        };
    }
}