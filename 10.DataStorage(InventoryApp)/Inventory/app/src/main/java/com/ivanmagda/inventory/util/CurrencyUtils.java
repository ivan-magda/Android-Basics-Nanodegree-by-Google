package com.ivanmagda.inventory.util;

import java.text.NumberFormat;

public class CurrencyUtils {

    private static NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    private CurrencyUtils() {
    }

    public static String currencyString(double value) {
        return numberFormat.format(value);
    }

}
