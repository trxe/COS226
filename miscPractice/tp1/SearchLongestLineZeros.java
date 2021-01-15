import edu.princeton.cs.algs4.StdRandom;
import java.util.stream.IntStream;

class SearchLongestLineZeros {

    public static int mostZeroes(int[][] map) {
        int arrayAccs = 0;
        int height = map.length;
        int length = map[0].length, line = 0, longest = 0;
        int ones = 0;
        while (ones < length - 1 && map[line][ones + 1] == 1) { ones++; arrayAccs++; }
        // now ones is the index of the last "1"
        while (++line < height) {
            arrayAccs++;
            if (map[line][ones] == 0) {
                longest = line;
                int i;
                for (i = ones - 1; i >= 0; i--) {
                    arrayAccs++;
                    if (map[line][i] == 1 || i == 0) {
                        ones = i;
                        break;
                    }
                }
            }
        }
        System.out.println("array accesses: " + arrayAccs);
        return longest;
    }

    public static void main(String[] args) {
        int n = 10;
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            int ones = StdRandom.uniform(1, n);
            map[i] = IntStream.iterate(1, x -> x + 1)
                                .limit(10)
                                .map(x -> { if (x < ones) return 1; else return 0; })
                                .toArray();
        }
        for (int i = 0; i < n; i++) {
            String line = "";
            for (int j = 0; j < n; j++) {
                line += map[i][j] + " ";
            }
            System.out.println(line);
        }

        System.out.println(SearchLongestLineZeros.mostZeroes(map));
    }
}
