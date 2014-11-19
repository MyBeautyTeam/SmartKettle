package com.beautyteam.smartkettle.Mechanics;

/**
 * Created by Admin on 26.10.2014.
 */
public class Device {
    private int id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private int image;

    public Device(int _id,String _name, String _shortDescription, String _longDescription, int _image) {
        id = _id;
        name = _name;
        longDescription = _longDescription;
        shortDescription = _shortDescription;
        image = _image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getImageId() {
        return image;
    }

    public String getLongDescription() {
        return longDescription;
    }
}
