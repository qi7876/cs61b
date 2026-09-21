import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private int capacity;
    private T[] list;
    private int front;
    private int rear;

    public ArrayDeque61B() {
        capacity = 8;
        size = 0;
        list = (T[]) new Object[capacity];
        rear = 0;
        front = capacity - 1;
    }

    private void grow_if_needed() {
        if (size == capacity) {
            T[] new_list = (T[]) new Object[capacity * 2];
            for (int i = 0; i < size; i++) {
                new_list[i] = get(i);
            }
            list = new_list;
            capacity *= 2;
            rear = size;
            front = capacity - 1;

        }
    }

    private void shrink_if_needed() {
        if (capacity > 8 && size < 0.25 * capacity) {
            T[] new_list = (T[]) new Object[capacity / 2];
            for (int i = 0; i < size; i++) {
                new_list[i] = get(i);
            }
            list = new_list;
            capacity /= 2;
            rear = size;
            front = capacity - 1;
        }
    }

    @Override
    public void addFirst(T x) {
        grow_if_needed();
        list[front] = x;
        front = Math.floorMod(front - 1, capacity);
        size += 1;
    }

    @Override
    public void addLast(T x) {
        grow_if_needed();
        list[rear] = x;
        rear = Math.floorMod(rear + 1, capacity);
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(list[Math.floorMod(front + i + 1, capacity)]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        return list[Math.floorMod(front + 1, capacity)];
    }

    @Override
    public T getLast() {
        return list[Math.floorMod(rear - 1, capacity)];
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        front = Math.floorMod(front + 1, capacity);
        T value = list[front];
        list[front] = null;
        size -= 1;
        shrink_if_needed();
        return value;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        rear = Math.floorMod(rear - 1, capacity);
        T value = list[rear];
        list[rear] = null;
        size -= 1;
        shrink_if_needed();
        return value;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return list[Math.floorMod(front + index + 1, capacity)];
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(Math.floorMod(front + 1, capacity), index);
    }

    private T getRecursiveHelper(int pointer, int index) {
        if (index == 0) {
            return list[pointer];
        }
        return getRecursiveHelper(Math.floorMod(pointer + 1, capacity), index - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ArrayDeque61B<T> o = (ArrayDeque61B<T>) obj;
        if (o.size() != this.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (o.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDeque61BIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }
}
