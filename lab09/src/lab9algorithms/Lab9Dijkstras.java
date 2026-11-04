package lab9algorithms;

import lab9lib.ExtrinsicMinPQ;
import lab9lib.Lab9Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Computes shortest paths from a single source node of a Lab9Graph using
 *  Dijkstra's algorithm, treating each edge weight as a distance.
 *
 *  All of the work happens in the constructor, which builds the shortest
 *  paths tree and records it in the instance variables below. The three
 *  query methods then just read off the answers. ExtrinsicMinPQ (provided)
 *  is the priority queue you'll want for the fringe: it supports
 *  changePriority, which Dijkstra's relies on. */
public class Lab9Dijkstras {
    private final String source;
    /** The best known distance from source to each node reached so far. */
    private final Map<String, Integer> distTo = new HashMap<>();
    /** The node we came from on the best known path to each node. */
    private final Map<String, String> edgeTo = new HashMap<>();

    /** Runs Dijkstra's algorithm on g starting from source.
     *  Throws IllegalArgumentException if source is not a node of g. */
    public Lab9Dijkstras(Lab9Graph g, String source) {
        if (!g.nodes().contains(source)) {
            throw new IllegalArgumentException("no such node: " + source);
        }
        this.source = source;
        ExtrinsicMinPQ pq = new ExtrinsicMinPQ();
        this.distTo.put(this.source, 0);
        for (String neighbor : g.neighbors(this.source)) {
            this.edgeTo.put(neighbor, source);
            pq.add(neighbor, g.edgeWeight(source, neighbor));
        }
        while (pq.size() != 0) {
            String node = pq.removeSmallest();
            String prev = this.edgeTo.get(node);
            int d = this.distTo.get(prev) + g.edgeWeight(prev, node);
            this.distTo.put(node, d);
            for (String neighbor : g.neighbors(node)) {
                if (this.distTo.containsKey(neighbor)) {
                    continue;
                }
                int candidateDistance = d + g.edgeWeight(node, neighbor);
                if (pq.contains(neighbor)) {
                    String currentPrev = this.edgeTo.get(neighbor);
                    int currentDistance = this.distTo.get(currentPrev) + g.edgeWeight(currentPrev, neighbor);
                    if (candidateDistance < currentDistance) {
                        pq.changePriority(neighbor, candidateDistance);
                        this.edgeTo.put(neighbor, node);
                    }
                    continue;
                }
                this.edgeTo.put(neighbor, node);
                pq.add(neighbor, candidateDistance);
            }
        }
    }

    /** Returns true if there is a path from the source to v. The source
     *  always has a path to itself. */
    public boolean hasPathTo(String v) {
        return this.distTo.containsKey(v);
    }

    /** Returns the total weight of the shortest path from the source to v.
     *  Throws IllegalArgumentException if there is no path to v. */
    public int distTo(String v) {
        if (!this.hasPathTo(v)) {
            throw new IllegalArgumentException("no path to: " + v);
        }
        return this.distTo.get(v);
    }

    /** Returns the shortest path from the source to v as a list of nodes,
     *  starting with the source and ending with v. The path to the source
     *  itself is the one-element list [source].
     *  Throws IllegalArgumentException if there is no path to v. */
    public List<String> shortestPathTo(String v) {
        if (!this.hasPathTo(v)) {
            throw new IllegalArgumentException("no path to: " + v);
        }
        List<String> reversedPath = new ArrayList<>();
        while (this.edgeTo.get(v) != null) {
            reversedPath.add(v);
            v = this.edgeTo.get(v);
        }
        reversedPath.add(v);
        return new ArrayList<>(reversedPath.reversed());
    }
}
