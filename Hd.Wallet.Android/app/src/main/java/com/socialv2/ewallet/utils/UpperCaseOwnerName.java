package com.socialv2.ewallet.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class UpperCaseOwnerName {
    public static String apply(String name) {
        String ownerName = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(ownerName).replaceAll("");
        return result.toUpperCase();
    }
}
