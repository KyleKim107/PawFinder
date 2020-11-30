package com.example.pawfinder.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by User on 7/29/2017.
 */

public class LostPet implements Parcelable {

    private String date_posted;
    private String image_path;
    private String lost_pet_id;
    private String user_id;
    private String pet_name;
    private String pet_gender;
    private String date_missing;
    private String area_missing;
    private String message;
    private String email;
    private String phone;

    public LostPet() {
    }

    public LostPet(String date_posted, String image_path, String lost_pet_id, String user_id,
                   String pet_name, String pet_gender, String date_missing, String area_missing,
                   String message, String email, String phone) {
        this.date_posted = date_posted;
        this.image_path = image_path;
        this.lost_pet_id = lost_pet_id;
        this.user_id = user_id;
        this.pet_name = pet_name;
        this.pet_gender = pet_gender;
        this.date_missing = date_missing;
        this.area_missing = area_missing;
        this.message = message;
        this.email = email;
        this.phone = phone;
    }

    protected LostPet(Parcel in) {
        date_posted = in.readString();
        image_path = in.readString();
        lost_pet_id = in.readString();
        user_id = in.readString();
        pet_name = in.readString();
        pet_gender = in.readString();
        date_missing = in.readString();
        area_missing = in.readString();
        message = in.readString();
        email = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_posted);
        dest.writeString(image_path);
        dest.writeString(lost_pet_id);
        dest.writeString(user_id);
        dest.writeString(pet_name);
        dest.writeString(pet_gender);
        dest.writeString(date_missing);
        dest.writeString(area_missing);
        dest.writeString(message);
        dest.writeString(email);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LostPet> CREATOR = new Creator<LostPet>() {
        @Override
        public LostPet createFromParcel(Parcel in) {
            return new LostPet(in);
        }

        @Override
        public LostPet[] newArray(int size) {
            return new LostPet[size];
        }
    };

    public static Creator<LostPet> getCREATOR() {
        return CREATOR;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(String date_posted) {
        this.date_posted = date_posted;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getLost_pet_id() {
        return lost_pet_id;
    }

    public void setLost_pet_id(String lost_pet_id) {
        this.lost_pet_id = lost_pet_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_gender() {
        return pet_gender;
    }

    public void setPet_gender(String pet_gender) {
        this.pet_gender = pet_gender;
    }

    public String getDate_missing() {
        return date_missing;
    }

    public void setDate_missing(String date_missing) {
        this.date_missing = date_missing;
    }

    public String getArea_missing() {
        return area_missing;
    }

    public void setArea_missing(String area_missing) {
        this.area_missing = area_missing;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "date_posted='" + date_posted + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + lost_pet_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", pet_name='" + pet_name + '\'' +
                ", pet_gender='" + pet_gender + '\'' +
                ", date_missing='" + date_missing + '\'' +
                ", area_missing='" + area_missing + '\'' +
                ", message='" + message + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
