package com.example.pawfinder;

public class ItemModel {
    private String image, name, age, gender;

    public ItemModel() {
    }

    public ItemModel(String image, String name, String age, String gender) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getImage() {
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

