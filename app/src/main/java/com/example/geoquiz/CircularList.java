package com.example.geoquiz;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CircularList<E> extends LinkedList<E> {

    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return new CircularIterator(this, index);
    }

    public class CircularIterator implements ListIterator<E> {

        private final CircularList<E> mList;
        private int mIndex;

        CircularIterator(@NonNull final CircularList<E> list, int index) {
            mList = list;
            mIndex = index;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public E next() {
            if (mIndex + 1 == mList.size()) {
                mIndex = 0;
                return mList.get(0);
            }

            return mList.get(mIndex++);
        }

        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public E previous() {
            if (mIndex == 0) {
                mIndex = mList.size() - 1;
                return mList.get(mIndex);
            }

            return mList.get(mIndex--);
        }

        @Override
        public int nextIndex() {
            return (mIndex + 1) % mList.size();
        }

        @Override
        public int previousIndex() {
            return Math.abs((mIndex - 1) % mList.size());
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {
            mList.set(mIndex, e);
        }

        @Override
        public void add(E e) {

        }
    }
}
