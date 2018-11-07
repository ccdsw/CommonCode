package com.shw.commoncode;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by shiwei on 18-11-7.
 */

public class OrderSet<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] DEFAULT_DATA = {};
    private int mSize;
    Object[] mData;

    public OrderSet() {
        mData = DEFAULT_DATA;
    }

    public OrderSet(int size) {
        if (size > 0) {
            mData = new Object[size];
        } else if (size == 0) {
            mData = DEFAULT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + mData);
        }
    }

    public OrderSet(Collection<? extends E> c) {
        mData = c.toArray();
        if ((mSize = mData.length) != 0) {
            if (mData.getClass() != Object[].class)
                mData = Arrays.copyOf(mData, mSize, Object[].class);
        } else {
            mData = DEFAULT_DATA;
        }
    }

    public boolean add(E e) {
        ensureCapacity(mSize + 1);  // Increments modCount!!
        mData[mSize++] = e;
        return true;
    }

    public void add(int index, E element) {
        if (index > mSize || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mSize);

        ensureCapacity(mSize + 1);  // Increments modCount!!
        System.arraycopy(mData, index, mData, index + 1,
                mSize - index);
        mData[index] = element;
        mSize++;
    }

    public E remove(int index) {
        if (index >= mSize)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mSize);

        E oldValue = (E) mData[index];

        int numMoved = mSize - index - 1;
        if (numMoved > 0)
            System.arraycopy(mData, index + 1, mData, index,
                    numMoved);
        mData[--mSize] = null; // clear to let GC do its work

        return oldValue;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < mSize; index++)
                if (mData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < mSize; index++)
                if (o.equals(mData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = mSize - index - 1;
        if (numMoved > 0)
            System.arraycopy(mData, index + 1, mData, index,
                    numMoved);
        mData[--mSize] = null;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > mData.length) {
            if (mData == DEFAULT_DATA) {
                capacity = Math.max(DEFAULT_CAPACITY, capacity);
            }
            int oldCapacity = mData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - capacity < 0)
                newCapacity = capacity;
            if (newCapacity - Integer.MAX_VALUE > 0)
                newCapacity = Integer.MAX_VALUE;
            mData = Arrays.copyOf(mData, newCapacity);
        }
    }
    public E get(int index) {
        if (index >= mSize)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mSize);

        return (E) mData[index];
    }

    public int size() {
        return mSize;
    }
}