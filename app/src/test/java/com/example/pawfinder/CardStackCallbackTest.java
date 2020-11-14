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

    ItemModel testModel1 = new ItemModel(3, "Henry", "10", "Male" );
    ItemModel testModel2 = new ItemModel(4, "Mary", "5", "Female" );
    ItemModel testModel3 = new ItemModel(7, "Rover", "2", "Male" );

    List<ItemModel> list1;
    List<ItemModel> list2;

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