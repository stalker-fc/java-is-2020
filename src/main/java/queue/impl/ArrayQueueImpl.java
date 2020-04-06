package queue.impl;

import java.util.*;

public class ArrayQueueImpl<T> extends AbstractQueue<T> {

    private final static int CAPACITY = 20000;

    private int size = 0;
    private int head = 0;
    private final Object[] queue = new Object[CAPACITY];

    @Override
    public Iterator<T> iterator() {
        return new ArrayQueueIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(T t) {
        if (size == CAPACITY) {
            return false;
        }
        queue[getIndex(head + size)] = t;
        size++;
        return true;
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }

        size--;
        var oldHead = head;
        head = getIndex(head + 1);
        return (T) queue[oldHead];
    }

    @Override
    public T peek() {
        return size == 0 ? null : (T) queue[head];
    }

    private int getIndex(int x) {
        return x % CAPACITY;
    }

    private class ArrayQueueIterator implements Iterator<T> {
        int current = head;

        @Override
        public boolean hasNext() {
            return getIndex(current + 1) < getIndex(head + size);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var value = (T) queue[current];
            current = getIndex(current + 1);
            return value;
        }
    }
}