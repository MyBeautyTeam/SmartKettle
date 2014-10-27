package com.beautyteam.smartkettle.Mechanics;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 25.10.2014.
 */
public class News implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(shortNewsText);
        parcel.writeString(longNewsText);
        parcel.writeString(date);
        parcel.writeInt(image);
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {

        @Override
        public News createFromParcel(Parcel source) {
            News news = new News(source.readString(),source.readString(),source.readString(),source.readInt());
            return news;
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
