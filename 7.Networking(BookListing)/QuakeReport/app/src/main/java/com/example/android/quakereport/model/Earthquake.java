package com.example.android.quakereport.model;

import java.util.Date;

public class Earthquake {

    private static final String LOCATION_SEPARATOR = " of ";

    private String mLocation;
    private Date mDate;
    private double mMagnitude;
    private String mUrl;

    public Earthquake(String location, Date date, double magnitude, String url) {
        this.mLocation = location;
        this.mDate = date;
        this.mMagnitude = magnitude;
        this.mUrl = url;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public void setMagnitude(double magnitude) {
        this.mMagnitude = magnitude;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getPrimaryLocation() {
        return (containsLocationSeparator() ? mLocation.split(LOCATION_SEPARATOR)[1] : mLocation);
    }

    public String getLocationOffset() {
        return (containsLocationSeparator()
                ? String.format("%s%s", mLocation.split(LOCATION_SEPARATOR)[0], LOCATION_SEPARATOR)
                : "Near of");
    }

    private boolean containsLocationSeparator() {
        return mLocation.contains(LOCATION_SEPARATOR);
    }

}
