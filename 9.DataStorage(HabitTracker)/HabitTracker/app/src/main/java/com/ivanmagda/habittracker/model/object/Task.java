package com.ivanmagda.habittracker.model.object;

public class Task {

    private int mId;
    private String mName;
    private int mPerDay;
    private int mImageResourceId;

    public Task(int id, String name, int perDay, int imageResourceId) {
        this.mId = id;
        this.mName = name;
        this.mPerDay = perDay;
        this.mImageResourceId = imageResourceId;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getPerDay() {
        return mPerDay;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPerDay=" + mPerDay +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }

}
