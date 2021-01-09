import java.util.Iterator;
import java.util.function.Supplier;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTree {

    private static final boolean VERT = true;
    private static final boolean HORI = false;
    private static final int CANV = 1;
    private Node root;

    private class Node {
        double x;
        double y;
        Node left;
        Node right;
        private final int height;
        int size;

        Node(double x, double y, int height, int size) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.size = size;
        }

        private int doubleCmp(double a, double b) {
            double c = a - b;
            if (c < 0) return 1;
            if (c > 0) return -1;
            return 0;
        }

        int compareTo(Node that) {
            int dx = doubleCmp(this.x, that.x);
            int dy = doubleCmp(this.y, that.y);
            if (this.direction() == VERT) {
               if (dx == 0) return dy;
               return dx;
            } else {
                if (dy == 0) return dx;
                return dy;
            }
        }

        int compareCoord(double thatx, double thaty) {
            int dx = doubleCmp(this.x, thatx);
            int dy = doubleCmp(this.y, thaty);
            if (this.direction() == VERT) {
               if (dx == 0) return dy;
               return dx;
            } else {
                if (dy == 0) return dx;
                return dy;
            }
        }

        boolean direction() {
            return height % 2 == 0;
        }

        public String toString() { return "(" + x + " " + y + ")"; }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (root == null) return 0;
        return root.size;
    }

    public void insert(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();
        if (this.isEmpty()) {
            this.root = new Node(p.x(), p.y(), 0, 1);
            return;
        }

        Node node = root;
        double x = p.x();
        double y = p.y();
        while (node != null) {
            int cmp = node.compareCoord(x, y);
            node.size++;
            if (cmp > 0) {
                if (node.right != null) {
                    node = node.right;
                } else {
                    node.right = new Node(x, y, node.height + 1, 1);
                    break;
                }
            } else if (cmp < 0) {
                if (node.left != null) {
                    node = node.left;
                } else {
                    node.left = new Node(x, y, node.height + 1, 1);
                    break;
                }
            } else {
                node.x = x;
                node.y = y;
                break;
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();

        Node node = root;
        double x = p.x();
        double y = p.y();
        while (node != null) {
            int cmp = node.compareCoord(x, y);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                return true;
            }
        }
        return false;
    }

    private void draw(Node node, double lox, double hix, double loy, double hiy) {
        if (node == null)
            return;
        double xcoord = node.x;
        double ycoord = node.y;
        toPoint(node).draw();
        // StdDraw.filledCircle(xcoord, ycoord, 0.01);
        if (node.direction() == VERT) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(xcoord, loy, xcoord, hiy);
            StdDraw.setPenColor(StdDraw.BLACK);
            draw(node.left, lox, xcoord, loy, hiy);
            draw(node.right, xcoord, hix, loy, hiy);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(lox, ycoord, hix, ycoord);
            StdDraw.setPenColor(StdDraw.BLACK);
            draw(node.left, lox, hix, loy, ycoord);
            draw(node.right, lox, hix, ycoord, hiy);
        }
    }

    public void draw() {
        this.draw(root, 0.0, (double) CANV, 0.0, (double) CANV);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) 
            throw new IllegalArgumentException();
        if (this.isEmpty()) return null;
        Queue<Point2D> range = new Queue<>();

        /*
        this.draw();
        rect.draw();
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        */

        update(rect, range, root, 0.0, CANV, 0.0, CANV);

        return range;
    }

    private void update(RectHV rect, Queue<Point2D> pq, Node node, double lox,
            double hix, double loy, double hiy) {
        if (node == null)
            return;
        double xcoord = node.x;
        double ycoord = node.y;
        Point2D p = toPoint(node);
        if (rect.contains(p))
            pq.enqueue(p);
        // System.out.printf("[%.2f, %.2f] x [%.2f, %.2f]\n", lox, hix, loy, hiy);

        if (node.direction() == VERT) {
            RectHV leftBox = new RectHV(lox, loy, xcoord, hiy);
            RectHV rightBox = new RectHV(xcoord, loy, hix, hiy);
            if (rect.intersects(leftBox))
                update(rect, pq, node.left, lox, xcoord, loy, hiy);
            if (rect.intersects(rightBox))
                update(rect, pq, node.right, xcoord, hix, loy, hiy);
        } else {
            RectHV leftBox = new RectHV(lox, loy, hix, ycoord);
            RectHV rightBox = new RectHV(lox, ycoord, hix, hiy);
            if (rect.intersects(leftBox))
                update(rect, pq, node.left, lox, hix, loy, ycoord);
            if (rect.intersects(rightBox))
                update(rect, pq, node.right, lox, hix, ycoord, hiy);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();
        if (this.isEmpty()) return null;
        Point2D closest = new Point2D(root.x, root.y);
        closest = neighbour(p, closest, root, 0, (double) CANV, 0.0, (double) CANV);
        return closest;
    }

    private Point2D neighbour(Point2D p, Point2D closest, Node node, double lox,
            double hix, double loy, double hiy) {
        if (node == null)
            return closest;
        double sd = p.distanceSquaredTo(closest);
        double xcoord = node.x;
        double ycoord = node.y;
        Point2D pn = toPoint(node);
        if (p.distanceSquaredTo(pn) < sd)
            closest = pn;

        System.out.println(node);

        /*
        try {
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.line(p.x(), p.y(), pn.x(), pn.y());
            StdDraw.setPenColor(StdDraw.BLACK);
            Thread.sleep(200);
            StdDraw.clear();
            this.draw();
        } catch (InterruptedException e) {}
        */

        sd = p.distanceSquaredTo(closest);
        RectHV leftBox, rightBox;
        Supplier<Point2D> left, right;
        if (node.direction() == VERT) {
            leftBox = new RectHV(lox, loy, xcoord, hiy);
            rightBox = new RectHV(xcoord, loy, hix, hiy);
            double leftd = (leftBox.contains(p) ? 0 : leftBox.distanceSquaredTo(p));
            double rightd = (rightBox.contains(p) ? 0 : rightBox.distanceSquaredTo(p));
            if (leftd < sd && leftd < rightd)
                closest = neighbour(p, closest, node.left, lox, xcoord, loy, hiy);
            if (rightd < sd)
                closest = neighbour(p, closest, node.right, xcoord, hix, loy, hiy);
            if (leftd < sd && leftd >= rightd) 
                closest = neighbour(p, closest, node.left, lox, xcoord, loy, hiy);
        } else {
            leftBox = new RectHV(lox, loy, hix, ycoord);
            rightBox = new RectHV(lox, ycoord, hix, hiy);
            double leftd = (leftBox.contains(p) ? 0 : leftBox.distanceSquaredTo(p));
            double rightd = (rightBox.contains(p) ? 0 : rightBox.distanceSquaredTo(p));
            if (leftd < sd && leftd < rightd)
                closest = neighbour(p, closest, node.left, lox, hix, loy, ycoord);
            if (rightd < sd && rightd < leftd)
                closest = neighbour(p, closest, node.right, lox, hix, ycoord, hiy);
            if (leftd < sd && leftd >= rightd) 
                closest = neighbour(p, closest, node.left, lox, xcoord, loy, hiy);
        }
        return closest;
    }

    private Point2D toPoint(Node node) {
        return new Point2D(node.x, node.y);
    }

    public static void main(String[] args) {
        try {
            StdDraw.setXscale(0, CANV);
            StdDraw.setYscale(0, CANV);

            In in = new In(args[0]);
            KdTree pset = new KdTree();
            int num = in.readInt();
            for (int i = 0; i < num; i++) {
                pset.insert(new Point2D(in.readDouble(), in.readDouble()));
                System.out.println(pset.size());
            }

            if (pset.size() < 1000)
                pset.draw();

            // range
            Stopwatch watch = new Stopwatch();
            RectHV r = new RectHV(0.20, 0.15, 0.9, 0.46);
            Iterable<Point2D> range = pset.range(r);
            System.out.printf("range() time: %.5f\n", watch.elapsedTime());

            Iterator<Point2D> it = range.iterator();
            StdDraw.setPenColor(StdDraw.GREEN);
            while (it.hasNext()) {
                Point2D next = it.next();
                next.draw();
                // StdDraw.filledCircle(next.x(), next.y(), 0.01);
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            
            // nearest
            watch = new Stopwatch();
            Point2D help = new Point2D(0.34375, 0.125);
            Point2D me = pset.nearest(help);
            System.out.printf("nearest() time: %.5f\n", watch.elapsedTime());
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.line(help.x(), help.y(), me.x(), me.y());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
