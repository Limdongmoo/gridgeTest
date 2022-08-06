package com.example.GTC.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexPhoneNum(String target) {
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{4})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}


