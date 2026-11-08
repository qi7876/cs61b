package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    WorldNet wn;

    public HyponymsHandler(NGramMap ngm, WorldNet wn) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        if (q.k() == 0) {
            return wn.getWordsHyponyms(q.words()).toString();
        }
        List<String> words = wn.getWordsHyponyms(q.words());
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
        return wordCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(q.k())
                .map(Map.Entry::getKey)
                .sorted()
                .toList()
                .toString();
    }
}
