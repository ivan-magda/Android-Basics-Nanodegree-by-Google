package com.ivanmagda.news.model.object;

public class NewsItem {

    private String title;
    private String sectionName;
    private String url;
    private Author[] authors;

    public NewsItem(String title, String sectionName, String url, Author[] authors) {
        this.title = title;
        this.sectionName = sectionName;
        this.url = url;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getUrl() {
        return url;
    }

    public Author[] getAuthors() {
        return authors;
    }

}
