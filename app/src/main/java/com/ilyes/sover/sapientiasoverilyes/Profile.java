package com.ilyes.sover.sapientiasoverilyes;

import android.net.Uri;

/**
 * Created by Szabolcs on 1/3/2018.
 */

public class Profile
{
    String id;
    String profileId;
    String firstName;
    String lastName;
    String email;
    String phonenr;
    String pass;
    String confirmpass;

    public Profile(){}

    public Profile(String profileId, String firstName, String lastName, String email, String phonenr)
    {
        this.profileId   = profileId;
        this.firstName          = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.phonenr       = phonenr;
        this.pass      = pass;
        this.confirmpass = confirmpass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getprofileId() {
        return profileId;
    }

    public void setprofileId(String profileId) {
        this.profileId = profileId;
    }

    public String getfirstName()
    {
        return firstName;
    }

    public void setfirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getlastName()
    {
        return lastName;
    }

    public void setlastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getemail()
    {
        return email;
    }

    public void setemail(String email)
    {
        this.email = email;
    }

    public String getpass() {
        return pass;
    }

    public void setpass(String pass) {
        this.pass = pass;
    }

    public String getphonenr() {
        return phonenr;
    }

    public void setphonenr(String phonenr) {
        this.phonenr = phonenr;
    }

    public String getconfirmpass() {
        return confirmpass;
    }

    public void setconfirmpass(String confirmpass) {
        this.confirmpass = confirmpass;
    }
}
