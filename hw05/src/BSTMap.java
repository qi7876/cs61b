import java.util.Iterator;
import java.util.Set;

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
        if (this.root == null) {
            root = new BSTNode(key, value);
            size += 1;
            return;
        }
        putHelper(key, value, this.root);
    }

    @Override
    public V get(K key) {
        if (this.root == null) {
            return null;
        }
        return getHelper(key, this.root);
    }

    @Override
    public boolean containsKey(K key) {
        if (this.root == null) {
            return false;
        }
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
        throw new UnsupportedOperationException("Unimplemented method 'keySet'");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    private void putHelper(K key, V value, BSTNode node) {
        if (key.compareTo(node.key) == 0) {
            node.value = value;
            return;
        }

        if (key.compareTo(node.key) < 0) {
            if (node.leftNode == null) {
                node.leftNode = new BSTNode(key, value);
                size += 1;
                return;
            }
            putHelper(key, value, node.leftNode);
        } else {
            if (node.rightNode == null) {
                node.rightNode = new BSTNode(key, value);
                size += 1;
                return;
            }
            putHelper(key, value, node.rightNode);
        }
    }

    private V getHelper(K key, BSTNode node) {
        if (key.compareTo(node.key) == 0) {
            return node.value;
        }

        if (key.compareTo(node.key) < 0) {
            if (node.leftNode == null) {
                return null;
            }
            return getHelper(key, node.leftNode);
        } else {
            if (node.rightNode == null) {
                return null;
            }
            return getHelper(key, node.rightNode);
        }
    }

    private boolean containsKeyHelper(K key, BSTNode node) {
        if (key.compareTo(node.key) == 0) {
            return true;
        }

        if (key.compareTo(node.key) < 0) {
            if (node.leftNode == null) {
                return false;
            }
            return containsKeyHelper(key, node.leftNode);
        } else {
            if (node.rightNode == null) {
                return false;
            }
            return containsKeyHelper(key, node.rightNode);
        }
    }
}
