package com.ivanmagda.inventory.ui;

import java.lang.reflect.Array;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static <T> T[] concatenate(T[] lhs, T[] rhs) {
        int lhsLen = lhs.length;
        int rhsLen = rhs.length;

        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(lhs.getClass().getComponentType(), lhsLen + rhsLen);
        System.arraycopy(lhs, 0, result, 0, lhsLen);
        System.arraycopy(rhs, 0, result, lhsLen, rhsLen);

        return result;
    }

}
