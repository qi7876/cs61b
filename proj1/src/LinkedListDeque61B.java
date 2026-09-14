import java.util.ArrayList;
import java.util.List;

import org.junit.platform.engine.support.hierarchical.Node;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private final Node<T> sentinel;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node<T>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node<T> new_node = new Node<>(x, sentinel, sentinel.next);
        sentinel.next.prev = new_node;
        sentinel.next = new_node;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node<T> new_node = new Node<>(x, sentinel.prev, sentinel);
        sentinel.prev.next = new_node;
        sentinel.prev = new_node;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> p = sentinel.next;

        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0) ? true : false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        return sentinel.next.item;
    }

    @Override
    public T getLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        return sentinel.prev.item;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return item;
    }

    @Override
    public T get(int index) {
        Node<T> p = sentinel;
        while (index >= 0) {
            if (p.next == sentinel) {
                return null;
            }
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        Node<T> p = sentinel;
        if (p.next == sentinel || index < 0) {
            return null;
        }
        return getRecursiveHelper(p.next, index);
    }

    private T getRecursiveHelper(Node<T> node, int index) {
        if (node == sentinel) {
            return null;
        }
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    public void main() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0); // [0]
        lld.addLast(1); // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
