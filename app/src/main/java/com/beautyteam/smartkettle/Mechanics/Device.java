package com.beautyteam.smartkettle.Mechanics;

/**
 * Created by Admin on 26.10.2014.
 */
public class Device {
    private String name;
    private String description;
    private int image;

    public Device(String _name, String _description, int _image) {
        name = _name;
        description = _description;
        image = _image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return image;
    }
}
