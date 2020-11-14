package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class CardStackAdapterTest {

    ItemModel testModel1;
    ItemModel testModel2;
    ItemModel testModel3;

    List<ItemModel> testItems = new List<ItemModel>() {
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


    CardStackAdapter test;

    @Test
    public void onCreateViewHolder() {
    }

    @Test
    public void onBindViewHolder() {
    }

    @Test
    public void getItemCount() {

        testModel1 = new ItemModel(3, "Henry", "10", "Male" );
        testModel2 = new ItemModel(4, "Mary", "5", "Female" );
        testModel3 = new ItemModel(7, "Rover", "2", "Male" );

        testItems.add(testModel1);
        testItems.add(testModel2);
        testItems.add(testModel3);

        test = new CardStackAdapter(testItems);

        assertEquals("Item count should be 3", testItems.size(), test.getItemCount());

        testItems.clear();
    }

    @Test
    public void getItems() {

        testModel1 = new ItemModel(3, "Henry", "10", "Male" );
        testItems.add(testModel1);
        test = new CardStackAdapter(testItems);

        assertEquals("get items should be equal",testItems, test.getItems());
        testItems.clear();
    }

    @Test
    public void setItems() {
        testModel1 = new ItemModel(3, "Henry", "10", "Male" );
        testItems.add(testModel1);
        test = new CardStackAdapter(null);
        test.setItems(testItems);
        assertEquals("set items should match",testItems, test.getItems());


    }
}