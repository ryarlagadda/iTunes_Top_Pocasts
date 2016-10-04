package com.example.raviteja.inclassassignment7;

import java.io.Serializable;

/**
 * Created by RAVITEJA on 10/3/2016.
 */
public class Items implements Serializable {
    String title;
    String summary;
    String releaseDate;
    String smallImageUrl;
    String LargeImageUrl;

    @Override
    public String toString() {
        return "Items{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", LargeImageUrl='" + LargeImageUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return LargeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        LargeImageUrl = largeImageUrl;
    }



}
