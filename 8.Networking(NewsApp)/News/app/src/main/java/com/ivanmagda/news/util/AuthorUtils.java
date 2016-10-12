package com.ivanmagda.news.util;

import android.text.TextUtils;

public class AuthorUtils {

    private AuthorUtils() {
    }

    public static String verifiedPersonalInfo(String personalInfo) {
        if (TextUtils.isEmpty(personalInfo)) return null;
        return personalInfo.substring(0, 1).toUpperCase() + personalInfo.substring(1);
    }

}
