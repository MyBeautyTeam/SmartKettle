package com.beautyteam.smartkettle.Mechanics;

/**
 * Created by Admin on 26.10.2014.
 */
public class Device {
    private String name;
    private String shortDescription;
    private String longDescription;
    private int image;

    public Device(String _name, String _shortDescription, String _longDescription, int _image) {
        name = _name;
        longDescription = _longDescription;
        shortDescription = _shortDescription;
        image = _image;
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
