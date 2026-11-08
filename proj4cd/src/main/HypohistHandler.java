package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.XYChart;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;

/**
 * HypohistHandler
 */
public class HypohistHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    WorldNet wn;

    public HypohistHandler(NGramMap ngm, WorldNet wn) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = wn.getWordsHyponyms(q.words());

        if (!(q.k() == 0)) {
            HashMap<String, Double> wordCountMap = new HashMap<>();
            for (String word : words) {
                double totalCount = 0;
                for (Double count : ngm.countHistory(word, q.startYear(), q.endYear()).values()) {
                    totalCount += count;
                }
                if (totalCount == 0) {
                    continue;
                }
                wordCountMap.put(word, totalCount);
            }
            words = wordCountMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .limit(q.k())
                    .map(Map.Entry::getKey)
                    .sorted()
                    .toList();
        }

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            labels.add(word);
            lts.add(this.ngm.weightHistory(word, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }

}
