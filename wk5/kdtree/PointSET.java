import java.util.TreeSet;
import java.util.Iterator;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();
        return points.contains((Object) p);
    }

    public void draw() {
        Iterator<Point2D> iter = points.iterator();
        while (iter.hasNext()) {
            Point2D next = iter.next();
            next.draw();
            //StdDraw.filledCircle(next.x(), next.y(), 0.01);
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) 
            throw new IllegalArgumentException();
        Iterator<Point2D> iter = points.iterator();
        Queue<Point2D> range = new Queue<>();
        this.draw();
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        rect.draw();
        while (iter.hasNext()) {
            Point2D next = iter.next();
            if (rect.contains(next))
                range.enqueue(next);
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();
        Point2D closest = points.first();
        Iterator<Point2D> iter = points.iterator();
        while (iter.hasNext()) {
            Point2D next = iter.next();
            assert next.distanceTo(p) >= 0;
            if (closest.distanceTo(p) > next.distanceTo(p))
                closest = next;
        }
        return closest;
    }

    public static void main(String[] args) {
        try {
            In in = new In(args[0]);
            PointSET pset = new PointSET();
            int num = in.readInt();
            for (int i = 0; i < num; i++) {
                pset.insert(new Point2D(in.readDouble(), in.readDouble()));
            }

            StdDraw.setXscale(0, 1);
            StdDraw.setYscale(0, 1);

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
                //StdDraw.filledCircle(next.x(), next.y(), 0.01);
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            
            // nearest
            watch = new Stopwatch();
            Point2D help = new Point2D(0.3566, 0.7235);
            Point2D me = pset.nearest(help);
            System.out.printf("nearest() time: %.5f\n", watch.elapsedTime());
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.line(help.x(), help.y(), me.x(), me.y());

        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
