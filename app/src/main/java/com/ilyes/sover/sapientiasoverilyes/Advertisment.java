package com.ilyes.sover.sapientiasoverilyes;

/**
 * Created by Hunor on 12/2/2017.
 */

public class Advertisment
{
    String advertismentId;
    String advertiserId;
    String title;
    String description;
    String location;
    int isDeleted;

    public Advertisment(String advertismentId, String advertiserId, String title, String description, String location)
    {
        this.advertismentId = advertismentId;
        this.advertiserId   = advertiserId;
        this.title          = title;
        this.description    = description;
        this.location       = location;
        this.isDeleted      = 0;
    }

    public String getAdvertismentId()
    {
        return advertismentId;
    }

    public void setAdvertismentId(String advertismentId)
    {
        this.advertismentId = advertismentId;
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
}
