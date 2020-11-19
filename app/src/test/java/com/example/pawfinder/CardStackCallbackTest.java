package com.example.pawfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import static org.junit.Assert.*;

public class CardStackCallbackTest {

    Pet testModel1 = new Pet("1", "https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80", "Lucky", "3 years", "Male" );
    Pet testModel2 = new Pet("2", "https://images.unsplash.com/photo-1570018143038-6f4c428f6e3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=698&q=80", "Pumpkin", "1 month", "Female");
    Pet testModel3 = new Pet("3", "https://images.unsplash.com/photo-1598739871560-29dfcd95b823?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80", "Chase", "5 years", "Male");

    List<Pet> list1;
    List<Pet> list2;

    CardStackCallback test;

    @Test
    public void getOldListSize() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

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
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

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
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        list1.add(testModel1);
        list1.add(testModel2);

        list2.add(testModel1);
        list2.add(testModel2);
        list2.add(testModel3);


        test = new CardStackCallback(list1, list2);


        assertEquals(true, test.areItemsTheSame(1,1));
    }

    @Test
    public void areContentsTheSame() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        list1.add(testModel1);
        list1.add(testModel2);

        list2.add(testModel1);
        list2.add(testModel2);
        list2.add(testModel3);

        test = new CardStackCallback(list1, list2);

        assertEquals(true, test.areContentsTheSame(1,1));
    }
}