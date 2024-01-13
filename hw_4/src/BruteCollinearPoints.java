import edu.princeton.cs.algs4.MergeX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    // private Point[] points;
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        Point[] tmp = Arrays.copyOf(points, points.length); // 注意不能对参数进行改变
        MergeX.sort(tmp);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        double slopeij = tmp[i].slopeTo(tmp[j]);
                        double slopeik = tmp[i].slopeTo(tmp[k]);
                        double slopeim = tmp[i].slopeTo(tmp[m]);
                        if (slopeim == slopeij && slopeik == slopeij) {
                            Point p = tmp[i];
                            Point q = tmp[m];
                            lineSegments.add(new LineSegment(p, q));
                        }
                    }
                }
            }
        }
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