import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.MathContext;
import edu.princeton.cs.algs4.StdRandom;

public class PointGen {

    public static void generate(int n) {
        double x = 1.01, y = 1.01;
        for (int i = 0; i < n; i++) {
            do { x = Math.abs(StdRandom.gaussian()); } while (x > 1);
            do { y = Math.abs(StdRandom.gaussian()); } while (y > 1);
            System.out.printf("%.6f %.6f\n", x, y);
        }
    }

    public static double round(double val) {
        BigDecimal bd = new BigDecimal(val);
        bd = bd.round(new MathContext(5));
        return bd.doubleValue();
    }

    public static void generate(int n, int low, int upp) {
        double x, y;
        if (low < upp) {
            for (int i = 0; i < n; i++) {
                x = round(StdRandom.uniform(low, upp) + StdRandom.gaussian());
                y = round(StdRandom.uniform(low, upp) + StdRandom.gaussian());
                System.out.println(x + " " + y);
            }
        } else {
            for (int i = 0; i < n; i++) {
                x = round(low - Math.abs(StdRandom.gaussian()));
                y = round(low - Math.abs(StdRandom.gaussian()));
                System.out.println(x + " " + y);
            }
        }

    }

    public static void main(String[] args) {
        if (args.length != 0) {
            int n = Integer.parseInt(args[0]);
            System.out.println(n);
            if (args.length == 3)  {
                int low = Integer.parseInt(args[1]) + 1;
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
