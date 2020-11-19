package com.example.pawfinder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemModelTest {
    String imageUrl = "https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80";
    Pet pet = new Pet("1", imageUrl, "Lucky", "3 years", "Male");

    @Test
    public void getId() {
        assertEquals("Id should be 1","1", pet.getId());
    }

    @Test
    public void getImage() {
        assertEquals("Image should be imageUrl",imageUrl, pet.getImage());
    }

    @Test
    public void getName() {
        assertEquals("Name should be Lucky","Lucky", pet.getName());
    }

    @Test
    public void getAge() {
        assertEquals("Age should be 3 years","3 years", pet.getAge());
    }

    @Test
    public void getGender() {
        assertEquals("Gender should be Male","Male", pet.getGender());
    }
}