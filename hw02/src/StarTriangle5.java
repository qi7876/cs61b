public class StarTriangle5 {
    /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on.
     */
    public static void starTriangle5() {
        int length = 5;
        for (int i = 1; i <= length; i++) {
            IO.println(" ".repeat(length - i) + "*".repeat(i));
        }
    }

    void main() {
        starTriangle5();
    }
}
