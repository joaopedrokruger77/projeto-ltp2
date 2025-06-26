package com.agenciaturismo.util;

import java.util.regex.Pattern;

public class ValidadorEmail {
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validar(String email) {
        if (email == null) return false;
        return pattern.matcher(email).matches();
    }
} 