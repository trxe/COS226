import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

public class PointGen {

    public static void generate(int n) {
        PointGen.generate(n, 0, 32768);
    }

    public static void generate(int n, int low, int upp) {
        List<Point> p = new ArrayList<>();
        int x, y;
        for (int i = 0; i < n; i++) {
            x = StdRandom.uniform(low, upp);
            y = StdRandom.uniform(low, upp);
            p.add(new Point(x, y));
        }

        int i = 0;
        while (i < p.size()) {
            int j = i + 1;
            while (j < p.size()) {
                if (p.get(j).equals(p.get(i))) {
                    p.remove(j);
                } else {
                    j++;
                }
            }
            i++;
        }
        System.out.println(p.size());
        for (Point pt : p) {
            System.out.println(pt.rough());
        }
    }

    public static void main(String[] args) {
        if (args.length != 0) {
            int n = Integer.parseInt(args[0]);
            if (args.length == 3)  {
                int low = Integer.parseInt(args[1]);
                int upp = Integer.parseInt(args[2]);
                PointGen.generate(n, low, upp);
            } else {
                PointGen.generate(n);
            }
        } else {
            System.out.println("format required: <number of points> [lower bound] [upper bound]");
        }
    }
}
