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

    Pet testModel1 = new Pet("1", "https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80", "Lucky", "3 years", "Male" );
    Pet testModel2 = new Pet("2", "https://images.unsplash.com/photo-1570018143038-6f4c428f6e3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=698&q=80", "Pumpkin", "1 month", "Female");
    Pet testModel3 = new Pet("3", "https://images.unsplash.com/photo-1598739871560-29dfcd95b823?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80", "Chase", "5 years", "Male");

    List<Pet> testItems;

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