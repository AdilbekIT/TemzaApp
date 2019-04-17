package com.example.asus.mobiletracker.entities;

import java.util.List;
import java.util.Map;

public class ApiValidation {
    String message;

    Map<String,List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
