package com.example.pawfinder.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {
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

    protected Pet(Parcel in) {
        id = in.readString();
        image = in.readString();
        name = in.readString();
        age = in.readString();
        gender = in.readString();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(gender);
    }
}

