import java.util.Scanner;

class SearchMonotone2D { 


    public static boolean search(int[][] map, int find) {
        int arrayAccs = 0;
        if (find < map[0][0]) {
            System.out.println("array accesses: " + arrayAccs);
            return false;
        }
        int height = map.length;
        int length = map[0].length;
        int row = 0, col = 0;
        while (col < length && map[row][col] < find) { col++; arrayAccs++; }
        if (col == length) col--;
        if (map[row][col] == find) {
            System.out.println("array accesses: " + arrayAccs);
            return true;
        }
        else {
            while (++row < height) {
                while (map[row][col] >= find) {
                    arrayAccs++;
                    if (map[row][col] == find) {
                        System.out.println("array accesses: " + arrayAccs);
                        return true;
                    }
                    col--;
                }
                if (map[row][col] < find && col < length - 1) col++;
            }
            System.out.println("array accesses: " + arrayAccs);
            return false;
        }
    }

    public static void main(String[] args) {
        int n = 4;
        int[][] map = new int[][] {{1,3,5,7},{2,5,10,17},{4,9,19,36},{6,15,34,70}};
        for (int i = 0; i < n; i++) {
            String line = "";
            for (int j = 0; j < n; j++) {
                line += map[i][j] + "\t";
            }
            System.out.println(line);
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("search for: ");
        int find = sc.nextInt();
        boolean found = SearchMonotone2D.search(map, find);
        if (found) System.out.println("found!");
        else System.out.println("not found");
    }
}
