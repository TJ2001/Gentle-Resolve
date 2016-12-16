package com.example.tim.gentleresolve.models;

import org.parceler.Parcel;

@Parcel
public class Meetup {
    String name, description, photoLink, organizer, city, link, pushId, index;

    public Meetup(){};

    public Meetup(String name, String description, String photoLink, String organizer, String city, String link) {
        this.name = name;
        this.description = description;
        this.photoLink = photoLink;
        this.organizer = organizer;
        this.city = city;
        this.link = link;
        this.index = "not_specified";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getCity() {
        return city;
    }

    public String getLink() {
        return link;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
