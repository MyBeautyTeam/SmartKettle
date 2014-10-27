package com.beautyteam.smartkettle.Mechanics;

import android.os.Parcelable;

/**
 * Created by Admin on 25.10.2014.
 */
public class News{
    private String shortNewsText;
    private String longNewsText;
    private String date;
    private int image;

    public News(String _shortNewsText, String _longNewsText, String _date, int _image) {
        shortNewsText = _shortNewsText;
        longNewsText = _longNewsText;
        date = _date;
        image = _image;
    }

    public String getShortNewsText() {
        return shortNewsText;
    }

    public int getImageId() {
        return image;
    }

    public String getDateInfo() {
        return date;
    }

    public String getLongNewsText() {
        return longNewsText;
    }
}
