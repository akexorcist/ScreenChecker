package com.akexorcist.screenchecker;

import android.os.Parcel;
import android.os.Parcelable;

public class Resolution implements Parcelable {
    private float x;
    private float y;

    public Resolution() {
    }

    public Resolution(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(x);
        dest.writeFloat(y);
    }

    protected Resolution(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
    }

    public static final Creator<Resolution> CREATOR = new Creator<Resolution>() {
        @Override
        public Resolution createFromParcel(Parcel in) {
            return new Resolution(in);
        }

        @Override
        public Resolution[] newArray(int size) {
            return new Resolution[size];
        }
    };
}
