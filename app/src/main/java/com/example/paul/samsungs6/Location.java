package com.example.paul.samsungs6;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Paul on 2/4/2016.
 */
public class Location  implements Serializable {
    private String title;
    private String description;
    private String address;
    @SerializedName("hours")
    private HashMap<String,String> operatingHours;
    @SerializedName("image")
    private String imageUrl;
    private String url;

    public Location(String title, String description, String address,
                    HashMap<String,String> operatingHours, String imageUrl, String url) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.operatingHours = operatingHours;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public HashMap<String, String> getOperatingHours() {
        return operatingHours;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }
}
