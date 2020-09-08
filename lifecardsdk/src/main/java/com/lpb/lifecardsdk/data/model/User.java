package com.lpb.lifecardsdk.data.model;

/**
 * Created by vannh.lvt on 27/07/2020
 */
public class User {
    private String name,phoneNumber,date,image;

    public User(String name, String phoneNumber, String date, String image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
