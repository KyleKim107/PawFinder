package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class CardStackAdapterTest {

    ItemModel testModel1 = new ItemModel(3, "Henry", "10", "Male" );
    ItemModel testModel2 = new ItemModel(4, "Mary", "5", "Female" );
    ItemModel testModel3 = new ItemModel(7, "Rover", "2", "Male" );

    List<ItemModel> testItems;

    CardStackAdapter test;

    @Test
    public void getItemCount() {

        testItems = new ArrayList<>();
        testItems.add(testModel1);
        testItems.add(testModel2);
        testItems.add(testModel3);

        test = new CardStackAdapter(testItems);

        assertEquals("Item count should be 3", testItems.size(), test.getItemCount());

        testItems.clear();
    }

    @Test
    public void getItems() {

        testItems = new ArrayList<>();
        testItems.add(testModel1);
        test = new CardStackAdapter(testItems);

        assertEquals("get items should be equal",testItems, test.getItems());
        testItems.clear();
    }

    @Test
    public void setItems() {

        testItems = new ArrayList<>();
        testItems.add(testModel1);
        test = new CardStackAdapter(null);
        test.setItems(testItems);
        assertEquals("set items should match",testItems, test.getItems());


    }
}