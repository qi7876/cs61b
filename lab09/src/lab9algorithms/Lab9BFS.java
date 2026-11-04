package lab9algorithms;

import lab9lib.Lab9Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** Breadth first search on a Lab9Graph from a single start node.
 *
 *  All of the work happens in the constructor, which runs the search and
 *  records what it finds. The query methods then just read off the answers.
 *  Unlike Lab9DFS, no instance variables are provided: decide what you need
 *  to record and add it yourself. Add the neighbors of each node to the
 *  queue in the order given by g.neighbors(). */
public class Lab9BFS {
    private final Lab9Graph g;

    private final Set<String> marked = new HashSet<>();
    private final Map<String, Integer> distTo = new HashMap<>();
    private final List<String> order = new ArrayList<>();

    public Lab9BFS(Lab9Graph g, String start) {
        if (!g.nodes().contains(start)) {
            throw new IllegalArgumentException("no such node: " + start);
        }
        this.g = g;
        Queue<String> queue = new ArrayDeque<>();
        this.marked.add(start);
        this.distTo.put(start, 0);
        this.order.add(start);
        for (String neighbor : this.g.neighbors(start)) {
            this.marked.add(neighbor);
            this.distTo.put(neighbor, this.distTo(start) + 1);
            queue.add(neighbor);
        }

        while (!queue.isEmpty()) {
            String node = queue.poll();
            this.marked.add(node);
            this.order.add(node);
            for (String neighbor : this.g.neighbors(node)) {
                if (!this.marked.contains(neighbor)) {
                    this.marked.add(neighbor);
                    this.distTo.put(neighbor, this.distTo(node) + 1);
                    queue.add(neighbor);
                }
            }
        }
    }

    /** Returns true if s was reached by the search, i.e. there is a path
     *  from start to s. Throws IllegalArgumentException if s is not a node
     *  of the graph. */
    public boolean reachable(String s) {
        if (!g.nodes().contains(s)) {
            throw new IllegalArgumentException("no such node: " + s);
        }
        return marked.contains(s);
    }

    /** Returns the distance from start to s, i.e. the number of edges on a
     *  shortest path from start to s, or -1 if s is not reachable from
     *  start. The distance to start itself is 0. Throws
     *  IllegalArgumentException if s is not a node of the graph. */
    public int distTo(String s) {
        if (!g.nodes().contains(s)) {
            throw new IllegalArgumentException("no such node: " + s);
        }
        if (reachable(s)) {
            return distTo.get(s);
        }
        return -1;
    }

    /** Returns the BFS order of the nodes reachable from start: the order
     *  in which nodes were removed from the queue. The first node in the
     *  list is always start. */
    public List<String> order() {
        return new ArrayList<>(this.order);
    }
}
