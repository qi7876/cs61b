package main;

import java.util.Collection;
import java.util.HashMap;

import edu.princeton.cs.algs4.In;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    HashMap<String, TimeSeries> wordHistory = new HashMap<>();
    TimeSeries yearHistory = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        In wordIn = new In(wordHistoryFilename);
        while (!wordIn.isEmpty()) {
            String nextLine = wordIn.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double frequency = Double.parseDouble(splitLine[2]);
            if (this.wordHistory.containsKey(word)) {
                this.wordHistory.get(word).put(year, frequency);
            } else {
                TimeSeries ts = new TimeSeries();
                ts.put(year, frequency);
                this.wordHistory.put(word, ts);
            }
        }
        In yearIn = new In(yearHistoryFilename);
        while (!yearIn.isEmpty()) {
            String nextLine = yearIn.readLine();
            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double numWords = Double.parseDouble(splitLine[1]);
            this.yearHistory.put(year, numWords);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = this.wordHistory.get(word);
        if (ts == null) {
            return new TimeSeries();
        }
        TimeSeries newTS = new TimeSeries();
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                newTS.put(year, ts.get(year));
            }
        }
        return newTS;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return this.countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return (TimeSeries) this.yearHistory.clone();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordTS = this.wordHistory.get(word);
        if (wordTS == null) {
            return new TimeSeries();
        }
        TimeSeries relativeFrequecyTS = new TimeSeries();
        for (Integer year : wordTS.keySet()) {
            if (year >= startYear && year <= endYear) {
                relativeFrequecyTS.put(year, wordTS.get(year) / this.yearHistory.get(year));
            }
        }
        return relativeFrequecyTS;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return this.weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
            int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            TimeSeries ts = countHistory(word, startYear, endYear);
            sum = sum.plus(ts);
        }

        TimeSeries relativeFrequecyTS = new TimeSeries();
        for (Integer year : sum.keySet()) {
            relativeFrequecyTS.put(year, sum.get(year) / this.yearHistory.get(year));
        }
        return relativeFrequecyTS;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
