package com.example.tim.gentleresolve.models;

import org.parceler.Parcel;

@Parcel
public class Meetup {
    String name, description, photoLink, organizer, city, link;

    public Meetup(){};
    public Meetup(String name, String description, String photoLink, String mOrganizer, String city, String mLink) {
        this.name = name;
        this.description = description;
        this.photoLink = photoLink;
        this.organizer = organizer;
        this.city = city;
        this.link = link;
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
}
