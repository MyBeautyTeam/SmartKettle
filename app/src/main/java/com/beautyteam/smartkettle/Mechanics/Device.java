package com.beautyteam.smartkettle.Mechanics;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 26.10.2014.
 */
public class Device implements Parcelable{
    private String name;
    private String shortDescription;
    private String longDescription;
    private int image;
    private int id;

    public Device(String _name, String _shortDescription, String _longDescription, int _image, int _id) {
        name = _name;
        longDescription = _longDescription;
        shortDescription = _shortDescription;
        image = _image;
        id = _id;
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

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(shortDescription);
        parcel.writeString(longDescription);
        parcel.writeInt(id);
        parcel.writeInt(image);
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {

        @Override
        public Device createFromParcel(Parcel source) {
            Device device = new Device(source.readString(),source.readString(),source.readString(),source.readInt(), source.readInt());
            return device;
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
