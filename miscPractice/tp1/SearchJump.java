import edu.princeton.cs.algs4.StdRandom;
import java.util.stream.IntStream;

class SearchJump {

    private int memes;  

    public void memeMaker() {
        int[] list = new int[] {1, 2, 5, 6};
        memes = SearchJump.jumped(list);
    }

    public int getMemes() { return memes; }
    
    public static int jumped(int[] array) {
        return jumped(array, 0, array.length - 1);
    }

    private static int jumped(int[] array, int lo, int hi) {
        int mid = (lo + hi) / 2;
        int k = mid - lo;
        if (hi - lo == 1) {
            if (array[hi] - array[lo] > 1) return array[lo] + 1;
            else return -1;
        }
        if (array[mid] > array[lo] + k) return jumped(array, lo, mid);
        else {
            assert array[mid] == array[lo] + k;
            return jumped(array, mid, hi);
        }
    }

    public static void main(String[] args) {
        int n = StdRandom.uniform(1, 100);
        int j = StdRandom.uniform(1, 20);
        int[] list = IntStream.iterate(0, x -> x + 1)
                                .limit(100)
                                .map(x -> {if (x >= n) return x + j; else return x;})
                                .toArray();
        for (int i = 0; i < 100; i++)
            System.out.print(list[i] + " ");
        System.out.println("\n------------");
        System.out.println("jumped at " + n + " by " + j);
        System.out.println("jumped(list) returns " + SearchJump.jumped(list));
        SearchJump test = new SearchJump();
        test.memeMaker();
        System.out.println(test.getMemes());
    }
}
