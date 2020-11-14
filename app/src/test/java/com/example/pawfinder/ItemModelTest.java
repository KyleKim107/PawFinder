package com.example.pawfinder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemModelTest {

    ItemModel itemModel = new ItemModel(3, "Henry", "10", "Male" );

    @Test
    public void getImage() {
        assertEquals("Image should be number 3",3, itemModel.getImage());
    }

    @Test
    public void getName() {
        assertEquals("Name should be Henry","Henry", itemModel.getName());
    }

    @Test
    public void getAge() {
        assertEquals("Age should be 10","10", itemModel.getAge());
    }

    @Test
    public void getGender() {
        assertEquals("Gender should be Male","Male", itemModel.getGender());
    }
}