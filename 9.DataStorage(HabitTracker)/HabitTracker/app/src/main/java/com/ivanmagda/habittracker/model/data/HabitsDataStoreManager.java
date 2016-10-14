package com.ivanmagda.habittracker.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ivanmagda.habittracker.model.data.HabitContract.HabitTask;

public class HabitsDataStoreManager {

    private HabitDbHelper mHabitDbHelper;

    public HabitsDataStoreManager(Context context) {
        mHabitDbHelper = new HabitDbHelper(context);
    }

    public HabitDbHelper getHabitDbHelper() {
        return mHabitDbHelper;
    }

    public SQLiteDatabase getReadableDatabase() {
        return mHabitDbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return mHabitDbHelper.getWritableDatabase();
    }

    public long insertDummyTask() {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitTask.COLUMN_TASK_NAME, "Read for 10 Minutes");
        values.put(HabitTask.COLUMN_TASK_PER_DAY, 2);
        values.put(HabitTask.COLUMN_TASK_IMG_RES_ID, 0);

        return database.insert(HabitTask.TABLE_NAME, null, values);
    }

    public Cursor allTasksCursor() {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                HabitTask._ID,
                HabitTask.COLUMN_TASK_NAME,
                HabitTask.COLUMN_TASK_PER_DAY,
                HabitTask.COLUMN_TASK_IMG_RES_ID
        };

        String sortOrder = HabitTask._ID + " DESC";

        return database.query(HabitTask.TABLE_NAME, projection, null, null, null, null, sortOrder);
    }

}
