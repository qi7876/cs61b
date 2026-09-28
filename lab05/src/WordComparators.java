import java.util.Comparator;
import java.util.List;

public class WordComparators {

    /**
     * Returns a comparator that orders strings by the number of lowercase 'x'
     * characters (ascending).
     */
    public static Comparator<String> getXComparator() {
        // TODO: Implement this.
        class xComparator implements Comparator<String> {
            @Override
            public int compare(String a, String b) {
                int num_x_in_a = 0;
                for (int i = 0; i < a.length(); i++) {
                    if (a.charAt(i) == 'x') {
                        num_x_in_a += 1;
                    }
                }

                int num_x_in_b = 0;
                for (int i = 0; i < b.length(); i++) {
                    if (b.charAt(i) == 'x') {
                        num_x_in_b += 1;
                    }
                }

                return num_x_in_a - num_x_in_b;
            }
        }

        return new xComparator();
    }

    /**
     * Returns a comparator that orders strings by the count of the given character
     * (ascending).
     */
    public static Comparator<String> getCharComparator(char c) {
        class charComparator implements Comparator<String> {
            @Override
            public int compare(String a, String b) {
                int num_x_in_a = 0;
                for (int i = 0; i < a.length(); i++) {
                    if (a.charAt(i) == c) {
                        num_x_in_a += 1;
                    }
                }

                int num_x_in_b = 0;
                for (int i = 0; i < b.length(); i++) {
                    if (b.charAt(i) == c) {
                        num_x_in_b += 1;
                    }
                }

                return num_x_in_a - num_x_in_b;
            }
        }

        return new charComparator();
    }

    /**
     * Returns a comparator that orders strings by the total count of the given
     * characters (ascending).
     */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        class charListComparator implements Comparator<String> {
            @Override
            public int compare(String a, String b) {
                int num_x_in_a = 0;
                for (int i = 0; i < a.length(); i++) {
                    if (chars.contains(a.charAt(i))) {
                        num_x_in_a += 1;
                    }
                }

                int num_x_in_b = 0;
                for (int i = 0; i < b.length(); i++) {
                    if (chars.contains(b.charAt(i))) {
                        num_x_in_b += 1;
                    }
                }

                return num_x_in_a - num_x_in_b;
            }
        }

        return new charListComparator();
    }
}
