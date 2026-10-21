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
        V value = get(key);
        this.root = removeHelper(key, this.root);
        size -= 1;
        return value;
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

    private BSTNode removeHelper(K key, BSTNode node) {
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.leftNode = removeHelper(key, node.leftNode);
        } else if (cmp > 0) {
            node.rightNode = removeHelper(key, node.rightNode);
        } else {
            if (node.leftNode == null) {
                return node.rightNode;
            }
            if (node.rightNode == null) {
                return node.leftNode;
            }

            if (ThreadLocalRandom.current().nextBoolean()) {
                BSTNode successor = node.leftNode;
                while (successor.rightNode != null) {
                    successor = successor.rightNode;
                }
                node.key = successor.key;
                node.value = successor.value;
                node.leftNode = removeHelper(node.key, node.leftNode);
            } else {
                BSTNode successor = node.rightNode;
                while (successor.leftNode != null) {
                    successor = successor.leftNode;
                }
                node.key = successor.key;
                node.value = successor.value;
                node.rightNode = removeHelper(node.key, node.rightNode);
            }
        }
        return node;
    }
}
