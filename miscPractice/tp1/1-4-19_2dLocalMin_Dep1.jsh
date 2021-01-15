// problematic
int k = 0;

int min(int[][] a, int hic, int loc, int hir, int lor) {
    int c = (hic + loc) / 2;
    int r = (hir + lor) / 2;
    boolean cont = false;
    System.out.println(a[r][c] + " " + String.format("%d-%d-%d-%d [%d, %d]",
    loc, lor, hic, hir, c, r));
    if (hir <= lor && hic <= loc) return a[r][c];
    if (hic > loc) {
        if (!(a[r][c] < a[r][c+1] && a[r][c] < a[r][c-1])) {
            cont = true;
            if (c+1 <= hic && c-1 >= 0 && a[r][c+1] < a[r][c-1])
                loc = c;
            else if (c-1 >= 0)
                hic = c;
        }
    }
    if (hir > lor) {
        if (!(a[r][c] < a[r+1][c] && a[r][c] < a[r-1][c])) {
            cont = true;
            if (r+1 <= hir && r-1 >= 0 && a[r+1][r] < a[r-1][r])
                lor = r;
            else if (r-1 >= 0)
                hir = r;
        }
    }
    if (++k > 10) return a[r][c];
    if (cont) { return min(a, hic, loc, hir, lor); }
    return a[r][c];
}

int[][] test = {
 {20, 100, 12, 11, 10, 100, 2} , 
 {19, 100, 13, 100, 9, 100, 3} , 
 {18, 100, 14, 100, 8, 100, 4} , 
 {17, 16, 15, 100, 7, 6, 5} };

int[][] matrix= {
{ 1,-2, 4,-6, 1, 8}, 
{-3,-6,-8, 8, 1, 3}, 
{ 1, 2, 6, 9, 0, 6},
{-2, 9, 6,-1,-1, 9}, 
{ 9,-1,-7, 1, 2,-6}, 
{-9, 0, 8, 7,-6, 9} };

System.out.printf("val \t loc, lor, hic, hir, [c, r]\n");
int res =  min(test, test[0].length - 1, 0, test.length - 1, 0);

System.out.printf("val \t loc, lor, hic, hir, [c, r]\n");
int mt =  min(matrix, matrix[0].length - 1, 0, matrix.length - 1, 0);
