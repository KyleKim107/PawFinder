package com.example.pawfinder.PetfinderAPI.Entities;

import java.util.List;
import java.util.Map;

public class ApiError {

    String message;
    Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
