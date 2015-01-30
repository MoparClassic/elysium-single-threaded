package org.moparscape.elysium.util;

import org.moparscape.elysium.entity.Indexable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by daniel on 17/01/2015.
 */
public final class EntityList<E extends Indexable> implements Iterable<E> {

    private final PriorityQueue<Integer> availableIndicies;
    private final Object[] entities;
    private int highestIndexInUse = 0;
    private int modCount = 0;
    private int size = 0;

    public EntityList(int capacity) {
        this.availableIndicies = new PriorityQueue<>(capacity);
        this.entities = new Object[capacity];

        for (int i = 0; i < capacity; i++) {
            this.availableIndicies.add(i);
        }
    }

    public boolean add(E e) {
        boolean canAdd = availableIndicies.size() > 0;
        if (!canAdd) return false;

        int index = availableIndicies.poll();
        entities[index] = e;
        e.setIndex(index);
        size++;
        modCount++;

        if (index > highestIndexInUse) highestIndexInUse = index;

        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= size) return null;

        return (E) entities[index];
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    public E remove(int index) {
        Object value = entities[index];
        entities[index] = null;
        availableIndicies.add(index);
        size--;
        modCount++;

        return (E) value;
    }

    public void remove(E e) {
        int index = e.getIndex();
        entities[index] = null;
        availableIndicies.add(index);
        size--;
        modCount++;
    }

    public int size() {
        return size;
    }

    private class Itr<E extends Indexable> implements Iterator<E> {

        private final int creationModCount;
        private final int highestIndexExclusive = highestIndexInUse + 1;
        private int curIndex = 0;
        private Object value;

        public Itr() {
            this.creationModCount = modCount;
        }

        private void checkConcurrentModification() {
            if (creationModCount != modCount) throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            while (curIndex != highestIndexExclusive) {
                value = entities[curIndex++];
                if (value != null) return true;
            }

            return false;
        }

        public E next() {
            return (E) value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
