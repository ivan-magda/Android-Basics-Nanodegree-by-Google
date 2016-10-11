package com.ivanmagda.booklisting.util;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static String mapToString(String[] arr) {
        StringBuilder stringBuilder = new StringBuilder();

        if (arr != null && arr.length > 0) {
            for (String item : arr) {
                stringBuilder.append(item);
            }
        }

        return stringBuilder.toString();
    }

}
