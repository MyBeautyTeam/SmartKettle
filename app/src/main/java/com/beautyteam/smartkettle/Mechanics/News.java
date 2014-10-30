package com.beautyteam.smartkettle.Mechanics;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 25.10.2014.
 */
public class News implements Parcelable {
    private String shortNewsText;
    private String longNewsText;
    private int image;
    private long dateLong;
    private String dateInfo;
    private static final long SECONDS_IN_MONTH=2629743;
    private static final long SECONDS_IN_DAY=86400;
    private static final long SECONDS_IN_HOUR=3600;
    private static final long SECONDS_IN_MINUTE=60;


    public News(String _shortNewsText, String _longNewsText, String _dateInfo, int _image) {
        shortNewsText = _shortNewsText;
        longNewsText = _longNewsText;
        dateInfo = _dateInfo;
        image = _image;

        try {
            Date date = new SimpleDateFormat("d MMMM yyyy, HH:mm:ss", Locale.ENGLISH).parse(_dateInfo);
            dateLong = date.getTime();
        } catch (ParseException e) {
        }
    }

    public String getShortNewsText() {
        return shortNewsText;
    }

    public int getImageId() {
        return image;
    }

    public String getDateInfo() {
        long diffSecond = dateDiffer();
        if ((int)(diffSecond/SECONDS_IN_MONTH) > 0) {
            return "" + (int)(diffSecond/SECONDS_IN_MONTH) + " months ago";
        }
        if ((int)(diffSecond/SECONDS_IN_DAY) > 0) {
            return "" + (int)(diffSecond/SECONDS_IN_DAY) + " days ago";
        }
        if ((int)(diffSecond/SECONDS_IN_HOUR) > 0) {
            return "" + (int)(diffSecond/SECONDS_IN_HOUR) + " hours ago";
        }
        if ((int)(diffSecond/SECONDS_IN_MINUTE) > 0) {
            return "" + (int)(diffSecond/SECONDS_IN_MINUTE) + " minutes ago";
        }
        return "" + diffSecond + " секунд назад";
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
        parcel.writeString(dateInfo);
        parcel.writeLong(dateLong);
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

    private long dateDiffer() {
        Calendar calendar = Calendar.getInstance();
        long diffTime = calendar.getTime().getTime() - dateLong;
        return diffTime/1000;
    }

    public long getDateLong(){
        return dateLong;
    }
}
