public class DoubleUp {
    /**
     * Returns a new string where each character of the given string is repeated
     * twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
    public static String doubleUp(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            result.append(c);
            result.append(c);
        }
        return result.toString();
    }

    void main() {
        String s = doubleUp("hello");
        IO.println(s);

        IO.println(doubleUp("cat"));
    }
}
