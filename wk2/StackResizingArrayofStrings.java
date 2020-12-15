class StackResizingArrayofStrings {

    private String[] array = new String[1];
    private int n = 0;
    private final static int RESIZING_FACTOR = 2;

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int vals) {
        String[] newArray = new String[vals];
        for (int i = 0; i < n; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public void push(String t) {
        if (++n > array.length / RESIZING_FACTOR) {
            resize(array.length * 2);
        }
        array[n - 1] = t;
    }

    public String pop() {
        if (isEmpty()) {
            throw new NullPointerException("Stack is empty.");
        }
        String out = array[n - 1];
        if (--n < array.length / (RESIZING_FACTOR * 2)) {
            resize(array.length / RESIZING_FACTOR);
        }
        array[n] = null;
        return out;
    }

    public static void main(String[] args) {
        try {
            StackResizingArrayofStrings stack = new StackResizingArrayofStrings();
            stack.push("help");
            stack.push("you");
            stack.push("save");
            stack.push("me");
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
}
