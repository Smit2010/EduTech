package com.example.smit.edutech;

import android.net.Uri;
import android.widget.TextView;

public class user_details
{
   private String user_name,name,emailid;
   private Uri imageid;

    user_details(String personName,String personGivenName,Uri image, String personEmail)
    {
        this.name=personName;
        this.emailid=personEmail;
        this.user_name=personGivenName;
        imageid=image;

    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setImageid(Uri imageid) {
        this.imageid = imageid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getName() {
        return name;
    }

    public Uri getImageid() {
        return imageid;
    }
}
