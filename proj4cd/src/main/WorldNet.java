package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;

public class WorldNet {
    private class Node {
        ArrayList<String> synset;
        ArrayList<Node> hyponyms;

        public Node(ArrayList<String> synset) {
            this.synset = synset;
        }
    }

    TreeMap<Integer, Node> idMap;
    TreeMap<String, ArrayList<Node>> wordMap;

    public WorldNet(String synsetsFilename, String hyponymsFilename) {
        In synsetsIn = new In(synsetsFilename);
        while (!synsetsIn.isEmpty()) {
            String nextLine = synsetsIn.readLine();
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String rawSynset = splitLine[1];
            ArrayList<String> synset = new ArrayList<>(Arrays.asList(rawSynset.split(" ")));
            Node node = new Node(synset);
            idMap.put(id, node);
            for (String word : synset) {
                if (!wordMap.containsKey(word)) {
                    wordMap.put(word, new ArrayList<>());
                }
                wordMap.get(word).add(node);
            }
        }

        In hyponymsIn = new In(hyponymsFilename);
        while (!hyponymsIn.isEmpty()) {
            String nextLine = hyponymsIn.readLine();
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                int hyponymID = Integer.parseInt(splitLine[i]);
                idMap.get(id).hyponyms.add(idMap.get(hyponymID));
            }
        }
    }
}
