import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points cannot be None!!!");
        }

        // this.points = new Point[points.length];
        this.lineSegments = new ArrayList<>();

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Point cannot be None!!!");
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Point cannot be Repeated!!!");
                }
            }
            // this.points[i] = points[i];
        }

        int pointsLen = points.length;
        if (pointsLen < 4) {
            return;
        }

        // Arrays.sort(points);
        Point[] tmp = Arrays.copyOf(points, pointsLen);

        for (Point p : points) {
            Arrays.sort(tmp, p.slopeOrder());
            // StdOut.println("p is " + p);
            for (int i = 1; i < pointsLen;) {
                int j = i + 1;
                // StdOut.println("Tmp[i] is " + tmp[i]);
                // StdOut.println("slopei is " + p.slopeTo(tmp[i]));
                while ((j < pointsLen) && (p.slopeTo(tmp[j]) == p.slopeTo(tmp[i]))) {
                    // StdOut.println("Tmp[j] is " + tmp[j]);
                    // StdOut.println("slopej is " + p.slopeTo(tmp[j]));
                    j++;
                }
                if (j - i >= 3 && (p.compareTo(minPoint(tmp, i, j - 1, p)) == 0)) { // 注意去重方式，只加入最小的
                    LineSegment lineSegment = new LineSegment(p, maxPoint(tmp, i, j - 1, p));
                    // StdOut.println("lineSegment is " + lineSegment);
                    lineSegments.add(lineSegment);
                }
                // 优化
                if (j == pointsLen) {
                    break;
                }
                i = j;
            }
            // StdOut.println("##########################");
        }

    }

    private Point minPoint(Point[] points, int lo, int hi, Point p) {
        Point pointLo = points[lo];

        for (int i = lo; i <= hi; i++) {
            if (pointLo.compareTo(points[i]) > 0) {
                pointLo = points[i];
            }
        }
        if (pointLo.compareTo(p) > 0) {
            pointLo = p;
        }
        return pointLo;
    }

    private Point maxPoint(Point[] points, int lo, int hi, Point p) {
        Point pointHi = points[hi];
        for (int i = lo; i <= hi; i++) {
            if (pointHi.compareTo(points[i]) < 0) {
                pointHi = points[i];
            }
        }
        if (pointHi.compareTo(p) < 0) {
            pointHi = p;
        }
        return pointHi;

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[lineSegments.size()];
        int i = 0;
        for (LineSegment l : lineSegments) {
            res[i++] = l;
        }
        return res;
    }
}