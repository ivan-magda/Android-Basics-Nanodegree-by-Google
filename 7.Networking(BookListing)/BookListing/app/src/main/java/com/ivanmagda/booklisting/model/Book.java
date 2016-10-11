package com.ivanmagda.booklisting.model;

public class Book {

    private String mTitle;
    private String mDescription;
    private String[] mAuthors;

    public Book(String title, String description, String[] authors) {
        this.mTitle = title;
        this.mDescription = description;
        this.mAuthors = authors;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String[] getAuthors() {
        return mAuthors;
    }

    public void setAuthors(String[] authors) {
        this.mAuthors = authors;
    }
}
