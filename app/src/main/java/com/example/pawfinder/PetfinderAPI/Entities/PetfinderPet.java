
package com.example.pawfinder.PetfinderAPI.Entities;

import java.util.List;

public class PetfinderPet {
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
    private List<String> videos;
    private String status;
    private String published_at;

    private static class PetfinderPetBreeds {
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

    private static class PetfinderPetColors {
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

    private static class PetfinderPetAttributes {
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

    private static class PetfinderPetPhotos {
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

    public List<String> getVideos() {
        return videos;
    }

    public String getStatus() {
        return status;
    }

    public String getPublished_at() {
        return published_at;
    }
}
