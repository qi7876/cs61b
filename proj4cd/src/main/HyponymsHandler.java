package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {
    WorldNet wn;

    public HyponymsHandler(WorldNet wn) {
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        return wn.getWordsHyponyms(q.words()).toString();
    }
}
