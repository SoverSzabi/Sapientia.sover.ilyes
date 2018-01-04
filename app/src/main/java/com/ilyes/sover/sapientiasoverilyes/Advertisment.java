package com.ilyes.sover.sapientiasoverilyes;

import android.net.Uri;

/**
 * Created by Hunor on 12/2/2017.
 */

public class Advertisment
{
    String id;
    String advertiserId;
    String title;
    String description;
    String location;
    String imageURL;
    int isDeleted;

    public Advertisment(){}

    public Advertisment(String advertiserId, String title, String description, String location, String imageURL)
    {
        this.advertiserId   = advertiserId;
        this.title          = title;
        this.description    = description;
        this.location       = location;
        this.imageURL       = imageURL;
        this.isDeleted      = 0;
    }

    public Advertisment(String id, String advertiserId, String title, String description, String location, String imageURL)
    {
        this.id             = id;
        this.advertiserId   = advertiserId;
        this.title          = title;
        this.description    = description;
        this.location       = location;
        this.imageURL       = imageURL;
        this.isDeleted      = 0;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
