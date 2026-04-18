package org.truje.jflix.util;

import java.util.Locale;

public class UtilClass {

    private UtilClass() {
        // Private constructor to prevent instantiation
    }

    public static String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
