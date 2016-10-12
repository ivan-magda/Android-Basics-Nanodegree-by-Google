package com.ivanmagda.news.model.object;

import android.text.TextUtils;

public class Author {

    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        if (TextUtils.isEmpty(firstName)) return lastName;
        else if (TextUtils.isEmpty(lastName)) return firstName;
        else
            return firstName + " " + lastName;
    }

}
