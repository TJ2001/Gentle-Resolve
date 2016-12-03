package com.example.tim.gentleresolve.models;

/**
 * Created by Guest on 12/2/16.
 */
public class Meetup {
    private String mName;
    private String mDescription;
    private String mPhotoLink;
    private String mOrganizer;
    private String mCity;
    private String mLink;

    public Meetup(String mName, String mDescription, String mPhotoLink, String mOrganizer, String mCity, String mLink) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mPhotoLink = mPhotoLink;
        this.mOrganizer = mOrganizer;
        this.mCity = mCity;
        this.mLink = mLink;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPhotoLink() {
        return mPhotoLink;
    }

    public String getOrganizer() {
        return mOrganizer;
    }

    public String getCity() {
        return mCity;
    }

    public String getLink() {
        return mLink;
    }
}
