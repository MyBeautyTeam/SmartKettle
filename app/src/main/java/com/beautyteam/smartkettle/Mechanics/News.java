package com.beautyteam.smartkettle.Mechanics;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Admin on 25.10.2014.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class News implements Parcelable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("short_news")
    private String short_news;
    @JsonProperty("long_news")
    private String long_news;
    @JsonProperty("event_date_begin")
    private String event_date_begin;
    @JsonProperty("event_date")
    private String event_date;
    @JsonProperty("event_date_end")
    private String event_date_end;
    private long dateLong;
    private String dateInfo;
    private static final long SECONDS_IN_MONTH=2629743;
    private static final long SECONDS_IN_DAY=86400;
    private static final long SECONDS_IN_HOUR=3600;
    private static final long SECONDS_IN_MINUTE=60;

    public News() {

    }

    public News(String _shortNewsText, String _longNewsText, String _dateInfo, int _image) {
        short_news = _shortNewsText;
        long_news = _longNewsText;
        dateInfo = _dateInfo;
        id = _image;

        try {
            Date date = new SimpleDateFormat("d MMMM yyyy, HH:mm:ss", Locale.ENGLISH).parse(_dateInfo);
            dateLong = date.getTime();
        } catch (ParseException e) {
        }
    }

    public String getShort_news() {
        return short_news;
    }

    public int getImageId() {
        return id;
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

    public String getLong_news() {
        return long_news;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(short_news);
        parcel.writeString(long_news);
        parcel.writeString(dateInfo);
        parcel.writeLong(dateLong);
        parcel.writeInt(id);
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

    public String getEvent_date_begin() {
        return event_date_begin;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_date_end() {
        return event_date_end;
    }


    public long getDateLong(){
        return dateLong;
    }

}
