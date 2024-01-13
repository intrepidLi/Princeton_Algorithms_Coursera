import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument to insert() is null");
        if (!points.contains(p))
            points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points)
            p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument to range() is null");
        SET<Point2D> pointsInRange = new SET<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p))
                pointsInRange.add(p);
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
            if (p == null)
                throw new IllegalArgumentException("argument to nearest() is null");
            if (points.isEmpty())
                return null;

            Point2D nearestPoint = null;
            double nearestDistance = Double.POSITIVE_INFINITY;
            for (Point2D point : points) {
                double distance = p.distanceSquaredTo(point);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPoint = point;
                }
            }
            return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
