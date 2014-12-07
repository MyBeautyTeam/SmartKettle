package com.beautyteam.smartkettle.Mechanics;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 26.10.2014.
 */
public class Device implements Parcelable{
    private String title;
    private String summary;
    private String description;
    private int type;
    private int id;

    public Device(){}

    public Device(String _name, String _shortDescription, String _longDescription, int _type, int _id) {
        title = _name;
        description = _longDescription;
        summary = _shortDescription;
        type = _type;
        id = _id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public int getTypeId() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(summary);
        parcel.writeString(description);
        parcel.writeInt(id);
        parcel.writeInt(type);
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {

        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source.readString(),source.readString(),source.readString(),source.readInt(), source.readInt());
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
