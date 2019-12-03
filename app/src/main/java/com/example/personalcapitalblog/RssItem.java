package com.example.personalcapitalblog;

import java.net.MalformedURLException;
import java.net.URL;

public class RssItem {
    private String title;
    private String link;
    private String description;
    private URL imageUrl;
    private String pubDate;

    public RssItem(String title, String description, String link, String imageUrl, String pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        try {
            this.imageUrl = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public String getPubDate() {
        return pubDate;
    }


    @Override
    public String toString() {
        return "RssItem [title=" + title + "]";
    }

}

