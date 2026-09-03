public class PrintIndexed {
    /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
    public static void printIndexed(String s) {
        int s_length = s.length();
        for (int i = 0; i < s_length; i++) {
            char letter = s.charAt(i);
            IO.print(letter);
            IO.print(s_length - i - 1);
        }
        IO.println();
    }

    void main() {
        printIndexed("hello");
        printIndexed("cat"); // should print c2a1t0
    }
}
