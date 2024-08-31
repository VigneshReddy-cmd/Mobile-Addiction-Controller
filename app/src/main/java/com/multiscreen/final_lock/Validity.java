package com.multiscreen.final_lock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validity {
    public static boolean isValidUsername(String username) {
        String USERNAME_REGEX =
                "^(?!.*__)[a-zA-Z][a-zA-Z0-9_]{4,14}$";
        Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
        if (username == null) {
            return false;
        }
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    public static  boolean isValidPassword(String password) {
        if (password.isEmpty()) {
            return false;
        }

        // Check if password starts with a number
        if (Character.isDigit(password.charAt(0))) {
            return false;
        }

        int digitCount = 0;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasSpecialChar = false;
        String specialChars = "@#$&_!";

        // Check the rest of the constraints
        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitCount++;
            } else if (Character.isUpperCase(ch)) {
                hasUpperCase=true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase=true;
            } else if (specialChars.indexOf(ch) >= 0) {
                hasSpecialChar = true;
            }
        }

        return digitCount >= 2 && hasUpperCase && hasLowerCase && hasSpecialChar;
    }
}
