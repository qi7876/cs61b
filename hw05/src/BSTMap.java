import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode leftNode = null;
        BSTNode rightNode = null;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root = null;
    private int size = 0;

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public void put(K key, V value) {
        this.root = putHelper(key, value, this.root);
    }

    @Override
    public V get(K key) {
        return getHelper(key, this.root);
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(key, this.root);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>();
        keySetHelper(keys, this.root);
        return keys;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        return removeHelper(key, this.root);
    }

    private BSTNode putHelper(K key, V value, BSTNode node) {
        if (node == null) {
            size += 1;
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
            node.leftNode = putHelper(key, value, node.leftNode);
        } else {
            node.rightNode = putHelper(key, value, node.rightNode);
        }
        return node;
    }

    private V getHelper(K key, BSTNode node) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp < 0) {
            return getHelper(key, node.leftNode);
        } else {
            return getHelper(key, node.rightNode);
        }
    }

    private boolean containsKeyHelper(K key, BSTNode node) {
        if (node == null) {
            return false;
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsKeyHelper(key, node.leftNode);
        } else {
            return containsKeyHelper(key, node.rightNode);
        }
    }

    private void keySetHelper(Set<K> keys, BSTNode node) {
        if (node == null) {
            return;
        }
        keySetHelper(keys, node.leftNode);
        keys.add(node.key);
        keySetHelper(keys, node.rightNode);
    }

    private V removeHelper(K key, BSTNode node) {
        // find the parent node of the target node
        // left or right
        // // get the arbitrary biggest or smallest node
        // // substitute
        BSTNode removedNodeParent = findNodeParent(key, null, node);

        // root node
        if (removedNodeParent == null) {
            V value = this.root.value;
            BSTNode newRoot = chooseNode(this.root);
            if (newRoot != null) {
                newRoot.leftNode = this.root.leftNode;
                newRoot.rightNode = this.root.rightNode;
            }
            this.root = newRoot;
            size -= 1;
            return value;
        }

        if (removedNodeParent.leftNode != null && key.compareTo(removedNodeParent.leftNode.key) == 0) {
            // left
            BSTNode removedNode = removedNodeParent.leftNode;
            V value = removedNode.value;
            BSTNode newNode = chooseNode(removedNode);
            if (newNode != null) {
                newNode.leftNode = removedNode.leftNode;
                newNode.rightNode = removedNode.rightNode;
            }
            removedNodeParent.leftNode = newNode;
            size -= 1;
            return value;
        } else {
            // right
            BSTNode removedNode = removedNodeParent.rightNode;
            V value = removedNode.value;
            BSTNode newNode = chooseNode(removedNode);
            if (newNode != null) {
                newNode.leftNode = removedNode.leftNode;
                newNode.rightNode = removedNode.rightNode;
            }
            removedNodeParent.rightNode = newNode;
            size -= 1;
            return value;
        }
    }

    private BSTNode findNodeParent(K key, BSTNode parentNode, BSTNode node) {
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return parentNode;
        } else if (cmp < 0) {
            return findNodeParent(key, node, node.leftNode);
        } else {
            return findNodeParent(key, node, node.rightNode);
        }
    }

    private BSTNode chooseNode(BSTNode node) {
        if (node.leftNode == null && node.rightNode == null) {
            return null;
        } else if (node.leftNode == null) {
            return getRightSmallestNode(node);
        } else if (node.rightNode == null) {
            return getLeftBiggestNode(node);
        } else {
            if (ThreadLocalRandom.current().nextBoolean()) {
                return getLeftBiggestNode(node);
            } else {
                return getRightSmallestNode(node);
            }
        }
    }

    private BSTNode getLeftBiggestNode(BSTNode node) {
        BSTNode currNode = node.leftNode;
        BSTNode parentNode = node;

        while (currNode.rightNode != null) {
            parentNode = currNode;
            currNode = currNode.rightNode;
        }

        if (parentNode == node) {
            parentNode.leftNode = currNode.leftNode;
        } else {
            parentNode.rightNode = currNode.leftNode;
        }
        currNode.leftNode = null;
        return currNode;
    }

    private BSTNode getRightSmallestNode(BSTNode node) {
        BSTNode currNode = node.rightNode;
        BSTNode parentNode = node;

        while (currNode.leftNode != null) {
            parentNode = currNode;
            currNode = currNode.leftNode;
        }

        if (parentNode == node) {
            parentNode.rightNode = currNode.rightNode;
        } else {
            parentNode.leftNode = currNode.rightNode;
        }
        currNode.rightNode = null;
        return currNode;
    }
}
