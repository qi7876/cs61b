import edu.princeton.cs.algs4.In;

import java.util.Comparator;
import java.util.List;

public class WordFinder {
    /**
     *  Returns the maximum string according to the provider comparator.
     *  If multiple strings are considered equal by c, return the first in
     *  the array.
     *  Use loops. Don't use Collections.max or similar.
     */
    public static String findMax(String[] strings, Comparator<String> c) {
        // TODO: Implement this.
        String max_string = strings[0];

        for (int i = 1; i < strings.length; i++) {
            if (c.compare(max_string, strings[i]) < 0) {
                max_string = strings[i];
            }
        }

        return max_string;
    }

    public static void main(String[] args) {
        In in = new In("data/mobydick.txt");
        String[] words = in.readAllStrings();

        // TODO: Print only the word with the most lower case vowels.
        //       Use your findMax method!
        //
        //       Start by creating a Comparator that compares based on lower case vowels.
        Comparator<String> vowelComparator = WordComparators.getCharListComparator(List.of('a', 'e', 'i', 'o', 'u'));
        IO.println("the word with the most lower case vowels: " + findMax(words, vowelComparator));
        

        // Optional task: Play around with lists of words from Wikipedia articles.
        // String[] zebraWords = ParseUtils.fetchWords("https://en.wikipedia.org/wiki/zebra");
        // System.out.println(findMax(zebraWords, vowelComparator));
    }
}
