package com.beautyteam.smartkettle.Mechanics;

/**
 * Created by Admin on 25.10.2014.
 */
public class News {
    private String mainText;
    private String date;
    private int image;

    public News(String _mainText, String _date, int _image) {
        mainText = _mainText;
        date = _date;
        image = _image;
    }

    public String getNewsText() {
        return mainText;
    }

    public int getImageId() {
        return image;
    }

    public String getDateInfo() {
        return date;
    }

}
