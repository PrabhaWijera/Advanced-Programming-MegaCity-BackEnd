package com.prabhash.megacity.validations;

public class ValidateCar {

    // Validate Car Name: should be at least 2 characters long
    public static boolean isValidCarName(String carName) {
        return carName != null && carName.length() >= 2;
    }

    // Validate Car Model: should be at least 2 characters long
    public static boolean isValidCarModel(String model) {
        return model != null && model.length() >= 2;
    }

    // Validate Plate Number: should match the pattern (letters, numbers, dashes)
    public static boolean isValidPlateNumber(String plateNumber) {
        if (plateNumber == null) return false;
        String platePattern = " /^[A-Z0-9-]+$/i"; // Allows letters, numbers, and dashes
        return plateNumber.matches(platePattern);
    }

    // Validate Year: should be between 1900 and the current year
    public static boolean isValidYear(int year) {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return year >= 1900 && year <= currentYear;
    }

    // Validate Car Status: should not be empty
    public static boolean isValidStatus(String status) {
        return status != null && !status.isEmpty();
    }
}
