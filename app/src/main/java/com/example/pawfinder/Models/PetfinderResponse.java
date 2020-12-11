package com.example.pawfinder.Models;

import com.example.pawfinder.Models.PetfinderPet;

import java.util.ArrayList;
import java.util.List;

public class PetfinderResponse {

    private ArrayList<PetfinderPet> animals;
    private PetfinderPagination pagination;

    public ArrayList<PetfinderPet> getAnimals() {
        return animals;
    }

    public PetfinderPagination getPagination() {
        return pagination;
    }

    public static class PetfinderPagination {

        private String count_per_page;
        private String total_count;
        private String current_page;
        private String total_pages;

        public String getCount_per_page() {
            return count_per_page;
        }

        public String getTotal_count() {
            return total_count;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public String getTotal_pages() {
            return total_pages;
        }

    }
}
