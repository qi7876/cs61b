package main;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.In;

public class WorldNet {
    private class Node {
        HashSet<String> synset;
        HashSet<Node> hyponymNodes = new HashSet<>();

        public Node(HashSet<String> synset) {
            this.synset = synset;
        }
    }

    HashMap<Integer, Node> idMap = new HashMap<>();
    HashMap<String, HashSet<Node>> wordMap = new HashMap<>();

    public WorldNet(String synsetsFilename, String hyponymsFilename) {
        In synsetsIn = new In(synsetsFilename);
        while (!synsetsIn.isEmpty()) {
            String nextLine = synsetsIn.readLine();
            String[] splitLine = nextLine.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String rawSynset = splitLine[1];
            HashSet<String> synset = new HashSet<>(Arrays.asList(rawSynset.split(" ")));
            Node node = new Node(synset);
            idMap.put(id, node);
            for (String word : synset) {
                if (!wordMap.containsKey(word)) {
                    wordMap.put(word, new HashSet<>());
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
                idMap.get(id).hyponymNodes.add(idMap.get(hyponymID));
            }
        }
    }

    public HashSet<String> getWordHyponyms(String word) {
        Queue<Node> hyponymNodes = new ArrayDeque<>();
        hyponymNodes.addAll(wordMap.get(word));
        HashSet<String> result = new HashSet<>();
        while (!hyponymNodes.isEmpty()) {
            Node hyponymNode = hyponymNodes.poll();
            result.addAll(hyponymNode.synset);
            hyponymNodes.addAll(hyponymNode.hyponymNodes);
        }
        return result;
    }

    public List<String> getWordsHyponyms(List<String> words) {
        HashSet<String> result = getWordHyponyms(words.get(0));
        for (int i = 1; i < words.size(); i++) {
            result.retainAll(getWordHyponyms(words.get(i)));
        }
        return result.stream().sorted().toList();
    }
}
