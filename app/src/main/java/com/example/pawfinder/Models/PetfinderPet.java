
package com.example.pawfinder.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PetfinderPet implements Parcelable {
    private String id;
    private String organization_id;
    private String type;
    private PetfinderPetBreeds breeds;
    private PetfinderPetColors colors;
    private String age;
    private String gender;
    private String size;
    private String coat;
    private PetfinderPetAttributes attributes;
    private List<String> tags;
    private String name;
    private String description;
    private List<PetfinderPetPhotos> photos;
    private String status;
    private String published_at;

    public PetfinderPet() {
    }

    public static class PetfinderPetBreeds {
        private String primary;
        private String secondary;
        private String mixed;
        private String unknown;

        public String getPrimary() {
            return primary;
        }

        public String getSecondary() {
            return secondary;
        }

        public String getMixed() {
            return mixed;
        }

        public String getUnknown() {
            return unknown;
        }
    }

    public static class PetfinderPetColors {
        private String primary;
        private String secondary;
        private String tertiary;

        public String getPrimary() {
            return primary;
        }

            public String getSecondary() {
                return secondary;
            }

        public String getTertiary() {
            return tertiary;
        }
    }

    public static class PetfinderPetAttributes {
        private String spayed_neutered;
        private String house_trained;
        private String declawed;
        private String special_needs;
        private String shots_current;

        public String getSpayed_neutered() {
            return spayed_neutered;
        }

        public String getHouse_trained() {
            return house_trained;
        }

        public String getDeclawed() {
            return declawed;
        }

        public String getSpecial_needs() {
            return special_needs;
        }

        public String getShots_current() {
            return shots_current;
        }
    }

    public static class PetfinderPetPhotos implements Parcelable{
        private String small;
        private String medium;
        private String large;
        private String full;

        public String getSmall() {
            return small;
        }

        public String getMedium() {
            return medium;
        }

        public String getLarge() {
            return large;
        }

        public String getFull() {
            return full;
        }

        public PetfinderPetPhotos() {
        }

        protected PetfinderPetPhotos(Parcel in) {
            small = in.readString();
            medium = in.readString();
            large = in.readString();
            full = in.readString();
        }
        public static final Creator<PetfinderPetPhotos> CREATOR = new Creator<PetfinderPetPhotos>() {
            @Override
            public PetfinderPetPhotos createFromParcel(Parcel in) {
                return new PetfinderPetPhotos(in);
            }
            @Override
            public PetfinderPetPhotos[] newArray(int size) {
                return new PetfinderPetPhotos[size];
            }
        };
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(small);
            parcel.writeString(medium);
            parcel.writeString(large);
            parcel.writeString(full);
        }
    }

    public String getId() {
        return id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public String getType() {
        return type;
    }

    public PetfinderPetBreeds getBreeds() {
        return breeds;
    }

    public PetfinderPetColors getColors() {
        return colors;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSize() {
        return size;
    }

    public String getCoat() {
        return coat;
    }

    public PetfinderPetAttributes getAttributes() {
        return attributes;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<PetfinderPetPhotos> getPhotos() {
        return photos;
    }

    public String getStatus() {
        return status;
    }

    public String getPublished_at() {
        return published_at;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(organization_id);
        parcel.writeString(type);
        parcel.writeString(age);
        parcel.writeString(gender);
        parcel.writeString(size);
        parcel.writeString(coat);
        parcel.writeStringList(tags);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(status);
        parcel.writeString(published_at);
    }

    protected PetfinderPet(Parcel in) {
        id = in.readString();
        organization_id = in.readString();
        type = in.readString();
        age = in.readString();
        gender = in.readString();
        size = in.readString();
        coat = in.readString();
        tags = in.createStringArrayList();
        name = in.readString();
        description = in.readString();
        status = in.readString();
        published_at = in.readString();
    }

    public static final Creator<PetfinderPet> CREATOR = new Creator<PetfinderPet>() {
        @Override
        public PetfinderPet createFromParcel(Parcel in) {
            return new PetfinderPet(in);
        }

        @Override
        public PetfinderPet[] newArray(int size) {
            return new PetfinderPet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
