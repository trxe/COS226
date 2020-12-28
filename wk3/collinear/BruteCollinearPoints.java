import java.util.Comparator;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segs = null;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        Point[] temp = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            temp[i] = points[i];
        }
        
        Arrays.sort(temp, Point::compareTo);

        for (int i = 0; i < points.length - 1; i++) {
            if (temp[i].compareTo(temp[i+1]) == 0)
                throw new IllegalArgumentException();
        }

        this.points = temp;
    }

    private LineSegment[] transfer(LineSegment[] orig, int count) {
        LineSegment[] segments = new LineSegment[count];
        for (int k = 0; k < count; k++) {
            segments[k] = orig[k];
        }
        return segments;
    }

    public int numberOfSegments() {
        if (segs == null)
            segments();
        return segs.length;
    }

    public LineSegment[] segments() { 
        if (segs != null)
            return transfer(this.segs, this.segs.length);

        int n = points.length;
        int count = 0;
        Comparator<Point> compPoints = Point::compareTo;
        Arrays.sort(points, compPoints);
        LineSegment[] segsOrig = new LineSegment[n*n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point a = points[i];
                        Point b = points[j];
                        Point c = points[k];
                        Point d = points[l];
                        Comparator<Point> compI = a.slopeOrder();
                        if (compI.compare(b, c) == 0 && compI.compare(c, d) == 0) {
                            segsOrig[count] = new LineSegment(a, d);
                            assert a.compareTo(d) <= 0;
                        }
                    }
                }
                if (segsOrig[count] != null) {
                    count++;
                }
            }
        }
        this.segs = transfer(segsOrig, count);
        return transfer(this.segs, this.segs.length);
    }

    public static void main(String[] args) {
        try {
            In in = new In(args[0]);
            int n = in.readInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }

            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32760);
            StdDraw.setYscale(0, 32760);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show(); // print and draw the line segments

            BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdOut.println("Total segments: " + collinear.numberOfSegments());
            StdDraw.show();
        } catch (IllegalArgumentException e) {
            StdOut.println(e);
        }
    }

}
