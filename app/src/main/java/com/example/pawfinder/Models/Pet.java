package com.example.pawfinder.Models;

public class Pet {
    private String id, image, name, age, gender;

    public Pet() {
    }

    public Pet(String id, String image, String name, String age, String gender) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
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

