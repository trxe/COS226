import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private static final double TOLERANCE = Math.pow(10, -15);
    private Point[] points;
    private LineSegment[] segs = null;

    public FastCollinearPoints(Point[] points) {
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
        
    public int numberOfSegments() {
        if (segs == null)
            segments();
        return segs.length;
    }

    private void merge(double[] d, double[] dux, Point[][] a, Point[][] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            dux[k] = d[k];
            aux[k][0] = a[k][0];
            aux[k][1] = a[k][1];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k][0] = aux[j][0];
                a[k][1] = aux[j][1];
                d[k] = dux[j++];
            } else if (j > hi) {
                a[k][0] = aux[i][0];
                a[k][1] = aux[i][1];
                d[k] = dux[i++];
            } else if (dux[i] > dux[j]) {
                a[k][0] = aux[j][0];
                a[k][1] = aux[j][1];
                d[k] = dux[j++];
            } else {
                a[k][0] = aux[i][0];
                a[k][1] = aux[i][1];
                d[k] = dux[i++];
            }
        }
    }

    private boolean isSorted(Point[][] a, double[] d) {
        int total = d.length;
        for (int k = 0; k < total - 1; k++) {
            if (d[k] > d[k + 1]) {
                return false;
            } else if (d[k] == d[k + 1]) {
                int compx = a[k][0].compareTo(a[k+1][0]);
                if (compx > 0) {
                    return false;
                } else if (compx == 0) {
                    int compy = a[k][1].compareTo(a[k+1][1]);
                    if (compy > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void sort(double[] d, Point[][] a) {
        int total = d.length;
        double[] dux = new double[total];
        Point[][] aux = new Point[total][2];

        for (int sz = 1; sz < total; sz *= 2) {
            for (int lo = 0; lo < total - sz; lo += 2*sz) {
                merge(d, dux, a, aux, lo, lo + sz - 1, Math.min(total - 1, lo + 2 *sz - 1));
            }
        }
    }

    private void filter(Point[][] ps, double[] slopes) {
        int total = slopes.length;
        int k = 0;
        int count = 0;
        int ofThree = 1;
        int compx = 0;
        LineSegment[] ls = new LineSegment[total];
        double[] dt = new double[total];
        Point[] end = new Point[total];
        while (k < total - 1) {
            int l = k + 1;
            compx = ps[k][0].compareTo(ps[l][0]);
            while (l < total && slopes[k] == slopes[l] && compx == 0) {
                ofThree++;
                if (ofThree > 2) {
                    ls[count] = new LineSegment(ps[l][0], ps[l][1]);
                    dt[count] = slopes[l];
                    end[count] = ps[l][1];
                }
                l++;
                if (l < total)
                    compx = ps[k][0].compareTo(ps[l][0]);
            }
            if (ls[count] != null) {
                count++;
            }
            ofThree = 1;
            k = l;
        }

        k = 0;
        total = count;
        int compy = 0;
        while (k < total - 1) {
            if (ls[k] == null) {
                k++;
            }
            int l = k + 1;
            while (l < total && dt[k] == dt[l]) {
                if (ls[l] == null) {
                    l++;
                } else {
                    compy = end[k].compareTo(end[l]);
                    //System.out.println(end[k] + " " + end[l]);
                    if (compy == 0) {
                        ls[l] = null;
                    }
                    l++;
                }
            }
            k++;
        }

        count = 0;
        for (int i = 0; i < total; i++) {
            if (ls[i] != null) {
                count++;
            }
        }

        k = 0;
        //System.out.println(count);
        this.segs = new LineSegment[count];
        for (int i = 0; i < total; i++) {
            if (ls[i] != null) {
                //System.out.println(i + ": " + ls[i] + " | " + dt[i]);
                this.segs[k++] = ls[i];
            }
        }
    }


    public LineSegment[] segments() {
        if (segs != null) {
            return transfer(this.segs, this.segs.length);
        }
        int count = 0;
        int n = points.length;
        int total = n*(n - 1) / 2;
        Point[][] ps = new Point[total][2];
        double[] slopes = new double[total];
        LineSegment[] ls = new LineSegment[total];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                ps[count][0] = points[i];
                ps[count][1] = points[j];
                slopes[count] = points[i].slopeTo(points[j]);
                count++;
            }
        }
        this.sort(slopes, ps);
        assert isSorted(ps, slopes);
        filter(ps, slopes);
        
        return transfer(this.segs, this.segs.length);
    }

    private LineSegment[] transfer(LineSegment[] orig, int count) {
        LineSegment[] segments = new LineSegment[count];
        for (int k = 0; k < count; k++) {
            segments[k] = orig[k];
        }
        return segments;
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

            Arrays.sort(points, Point::compareTo);

            // draw the points
            StdDraw.enableDoubleBuffering();
            //StdDraw.setXscale(0, 20);
            StdDraw.setXscale(0, 32767);
            //StdDraw.setYscale(0, 20);
            StdDraw.setYscale(0, 32767);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show(); // print and draw the line segments

            FastCollinearPoints collinear = new FastCollinearPoints(points);
            collinear.segments();
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
