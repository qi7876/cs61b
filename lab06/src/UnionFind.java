public class UnionFind {
    // TODO: Instance variables
    private int[] arr;

    /*
     * Creates a UnionFind data structure holding N items. Initially, all
     * items are in disjoint sets.
     */
    public UnionFind(int N) {
        arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int root = find(v);
        return -this.arr[root];
    }

    /*
     * Returns the parent of V. If V is the root of a tree, returns the
     * negative size of the tree for which V is the root.
     */
    public int parent(int v) {
        return this.arr[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (this.find(v1) == this.find(v2)) {
            return true;
        }
        return false;
    }

    /*
     * Returns the root of the set V belongs to. Path-compression is employed
     * allowing for fast search-time. If invalid items are passed into this
     * function, throw an IllegalArgumentException.
     */
    public int find(int v) {
        if (v >= this.arr.length) {
            throw new IllegalArgumentException("Can't find this element!");
        }

        int curr = v;
        while (this.arr[curr] >= 0) {
            curr = this.arr[curr];
        }
        int root = curr;

        curr = v;
        int next = v;
        while (this.arr[curr] >= 0) {
            next = this.arr[curr];
            this.arr[curr] = root;
            curr = next;
        }

        return root;
    }

    /*
     * Connects two items V1 and V2 together by connecting their respective
     * sets. V1 and V2 can be any element, and a union-by-size heuristic is
     * used. If the sizes of the sets are equal, tie break by connecting V1's
     * root to V2's root. Union-ing an item with itself or items that are
     * already connected should not change the structure.
     */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if (this.connected(v1, v2)) {
            return;
        }
        int v1_root = this.find(v1);
        int v2_root = this.find(v2);

        int v1_size = this.sizeOf(v1_root);
        int v2_size = this.sizeOf(v2_root);

        if (v1_size > v2_size) {
            this.arr[v2_root] = v1_root;
            this.arr[v1_root] = -(v1_size + v2_size);
        } else {
            this.arr[v1_root] = v2_root;
            this.arr[v2_root] = -(v1_size + v2_size);
        }
    }

}
