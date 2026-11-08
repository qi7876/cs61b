package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            TimeSeries st = this.ngm.weightHistory(word, startYear, endYear);
            sb.append(word + ": {");
            for (Integer year : st.keySet()) {
                sb.append(year + "=" + st.get(year) + ", ");
            }
            if (!st.keySet().isEmpty()) {
                sb.setLength(sb.length() - 2);
            }
            sb.append("}\n");
        }
        return sb.toString();
    }
}
