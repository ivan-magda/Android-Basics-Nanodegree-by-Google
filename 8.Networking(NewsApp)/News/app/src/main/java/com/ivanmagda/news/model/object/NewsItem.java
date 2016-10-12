package com.ivanmagda.news.model.object;

import java.net.MalformedURLException;
import java.net.URL;

public class NewsItem {

    private String title;
    private String sectionName;
    private String url;

    public NewsItem(String title, String sectionName, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.url = url;
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

    public URL getWebUrl() throws MalformedURLException {
        return new URL(url);
    }

}
