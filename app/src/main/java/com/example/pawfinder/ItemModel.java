package com.example.pawfinder;

public class ItemModel {
    private int image;
    private String name, age, gender;

    public ItemModel() {
    }

    public ItemModel(int image, String name, String age, String gender) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}

