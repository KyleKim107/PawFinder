package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import static org.junit.Assert.*;

public class CardStackCallbackTest {

    ItemModel testModel1 = new ItemModel(3, "Henry", "10", "Male" );
    ItemModel testModel2 = new ItemModel(4, "Mary", "5", "Female" );
    ItemModel testModel3 = new ItemModel(7, "Rover", "2", "Male" );

    List<ItemModel> list1 = new List<ItemModel>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<ItemModel> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(ItemModel itemModel) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends ItemModel> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends ItemModel> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public ItemModel get(int index) {
            return null;
        }

        @Override
        public ItemModel set(int index, ItemModel element) {
            return null;
        }

        @Override
        public void add(int index, ItemModel element) {

        }

        @Override
        public ItemModel remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<ItemModel> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<ItemModel> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<ItemModel> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    List<ItemModel> list2 = new List<ItemModel>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<ItemModel> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(ItemModel itemModel) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends ItemModel> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends ItemModel> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public ItemModel get(int index) {
            return null;
        }

        @Override
        public ItemModel set(int index, ItemModel element) {
            return null;
        }

        @Override
        public void add(int index, ItemModel element) {

        }

        @Override
        public ItemModel remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<ItemModel> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<ItemModel> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<ItemModel> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    List<ItemModel> im;

    CardStackCallback test;

    @Test
    public void getOldListSize() {
        list1.add(testModel1);
        list1.add(testModel2);

        list2.add(testModel1);
        list2.add(testModel2);
        list2.add(testModel3);

        test = new CardStackCallback(list1, list2);

        assertEquals("old list does not match list1 ", list1.size(), test.getOldListSize());

    }

    @Test
    public void getNewListSize() {
        list1.add(testModel1);
        list1.add(testModel2);

        list2.add(testModel1);
        list2.add(testModel2);
        list2.add(testModel3);

        test = new CardStackCallback(list1, list2);

        assertEquals("new list does not match list2 ", list2.size(), test.getNewListSize());
    }

    @Test
    public void areItemsTheSame() {
        list1.add(1, testModel1);
        list1.add(2, testModel2);

        list2.add(1, testModel1);
        list2.add(2, testModel2);
        list2.add(3, testModel3);

        test = new CardStackCallback(list1, list2);

        //lists will not add items

        assertEquals(true, test.areItemsTheSame(1,1));
    }

    @Test
    public void areContentsTheSame() {
        list1.add(testModel1);
        list1.add(testModel2);

        list2.add(testModel1);
        list2.add(testModel2);
        list2.add(testModel3);

        test = new CardStackCallback(list1, list2);

        assertEquals(true, test.areContentsTheSame(1,1));
    }
}