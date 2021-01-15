class Permutation {

    public static void permute(int[] arr, int[] fixed) {
        
    }

    public static void main(String[] args) {
        int[] arr = StdRandom.permutation(1, 10);
        int[] fixed = StdRandom.permutation(1, 10);

        Permutation.permute(arr, fixed);

        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
        for (int i = 0; i < arr.length; i++)
            System.out.print(fixed[i] + " ");
        System.out.println();
    }
}

