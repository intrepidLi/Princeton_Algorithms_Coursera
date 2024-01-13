import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private double minDistance;
    private Point2D nearestPoint;

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private boolean isVertical;
        // number of nodes in subtree
        private int size;
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;

        public void makeRect(double xmin, double ymin, double xmax, double ymax) {
            rect = new RectHV(xmin, ymin, xmax, ymax);
        }

        public Node(Point2D point, boolean isVertical, int size) {
            this.point = point;
            this.size = size;
            this.isVertical = isVertical;
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument to insert() is null");
        if (root == null) {
            root = new Node(p, true, 1);
            root.makeRect(0, 0, 1, 1);
        }
        else {
            root = put(root, p, root.isVertical, null, true);
        }
    }

    private Node put(Node x, Point2D p, boolean isVertical, Node parent, boolean isLeftChild) {
        if (x == null) {
            Node res = new Node(p, isVertical, 1);
            if (isLeftChild) {
                if (!isVertical) {
                    res.makeRect(parent.rect.xmin(), parent.rect.ymin(),
                            parent.point.x(), parent.rect.ymax());
                } else {
                    res.makeRect(parent.rect.xmin(), parent.rect.ymin(),
                            parent.rect.xmax(), parent.point.y());
                }
            } else {
                if (!isVertical) {
                    res.makeRect(parent.point.x(), parent.rect.ymin(),
                            parent.rect.xmax(), parent.rect.ymax());
                } else {
                    res.makeRect(parent.rect.xmin(), parent.point.y(),
                            parent.rect.xmax(), parent.rect.ymax());
                }
            }
            return res;
        }
        int cmp = compare(p, x.point, isVertical);
        if (cmp < 0) x.left = put(x.left, p, !isVertical, x, true);
        else if (cmp > 0) x.right = put(x.right, p, !isVertical, x, false);
        else x.point = p;
        // StdOut.println("x is " + toString(x));
        // 用size函数为了防止空指针异常
        x.size = 1 + size(x.left) + size(x.right);

        return x;
    }

    private int compare(Point2D p, Point2D q, boolean isVertical) {
        if (isVertical) {
            if (p.x() < q.x()) return -1;
            else if (p.x() > q.x() ||
                    (p.x() == q.x() && p.y() != q.y())) return 1;
            else return 0;
        } else {
            if (p.y() < q.y()) return -1;
            else if (p.y() > q.y() ||
                    (p.x() != q.x() && p.y() == q.y())) return 1;
            else return 0;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(root, p, true) != null;
    }

    private Node get(Node x, Point2D p, boolean isVertical) {
        if (x == null) return null;
        int cmp = compare(p, x.point, isVertical);
        if (cmp < 0) return get(x.left, p, !isVertical);
        else if (cmp > 0) return get(x.right, p, !isVertical);
        else return x;
    }

    private String toString(Node x) {
        if (x == null) return "";
        String res = "";
        res += x.point.toString();
        return res;
    }

    // draw all points to standard draw
    // all the points to the left of the root go in the left subtree;
    // all those to the right go in the right subtree;
    // and so forth, recursively.
    // Your draw() method should draw all of the points to standard draw in black
    // and the subdivisions in red (for vertical splits) and blue (for horizontal splits).
    public void draw() {
        draw(root, true);
    }

    private void draw(Node cur, boolean isVertical) {
        if (cur == null) {
            return;
        }
        if (compare(cur.point, root.point, true) == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(cur.point.x(), 0, cur.point.x(), 1);
        } else if (cur.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(cur.point.x(), cur.rect.ymin(), cur.point.x(), cur.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(cur.rect.xmin(), cur.point.y(), cur.rect.xmax(), cur.point.y());
        }

        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(cur.point.x(), cur.point.y());
        draw(cur.left, !isVertical);
        draw(cur.right, !isVertical);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument to range() is null");
        List<Point2D> pointsInRange = new ArrayList<>();
        range(rect, pointsInRange, root);
        return pointsInRange;
    }

    // cursive range
    private void range(RectHV rect, List<Point2D> points, Node cur) {
//        if (cur == null) {
//            return;
//        }
//        if (cur.rect.intersects(rect)) {
//            if (rect.contains(cur.point)) {
//                points.add(cur.point);
//            }
//            range(rect, points, cur.left);
//            range(rect, points, cur.right);
//        }
//        return;
        // 注意这里剪枝，不要两个子树无脑找
        if (cur == null) {
            return;
        }
        if (rect.contains(cur.point)) {
            points.add(cur.point);
        }
        if (cur.isVertical) {
            if (cur.point.x() > rect.xmax()) {
                range(rect, points, cur.left);
            } else if (cur.point.x() < rect.xmin()) {
                range(rect, points, cur.right);
            } else {
                range(rect, points, cur.left);
                range(rect, points, cur.right);
            }
        } else {
            if (cur.point.y() > rect.ymax()) {
                range(rect, points, cur.left);
            } else if (cur.point.y() < rect.ymin()) {
                range(rect, points, cur.right);
            } else {
                range(rect, points, cur.left);
                range(rect, points, cur.right);
            }
        }
    }



    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("nearest() has null parameter!!!");
        }
        if (root == null) {
            return null;
        }
        minDistance = Double.POSITIVE_INFINITY;
        nearestPoint = null;
        nearest(p, root);
        return nearestPoint;
    }

    private void nearest(Point2D p, Node node) {
        if (node == null) {
            return;
        }
        if (node.rect.distanceSquaredTo(p) > minDistance) {
            return;
        }
        if (node.point.distanceSquaredTo(p) < minDistance) {
            minDistance = node.point.distanceSquaredTo(p);
            nearestPoint = node.point;
        }
        if (node.isVertical) {
            if (p.x() < node.point.x()) {
                nearest(p, node.left);
                nearest(p, node.right);
            } else {
                nearest(p, node.right);
                nearest(p, node.left);
            }
        } else {
            if (p.y() < node.point.y()) {
                nearest(p, node.left);
                nearest(p, node.right);
            } else {
                nearest(p, node.right);
                nearest(p, node.left);
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
