package com.ivanmagda.habittracker.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ivanmagda.habittracker.model.data.HabitContract.HabitTask;

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habits.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL_ATR = " NOT NULL";
    private static final String DEFAULT_ATR = " DEFAULT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitTask.TABLE_NAME + " (" +
            HabitTask._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
            HabitTask.COLUMN_TASK_NAME + TEXT_TYPE + NOT_NULL_ATR + COMMA_SEP +
            HabitTask.COLUMN_TASK_PER_DAY + INTEGER_TYPE + DEFAULT_ATR + " 1" + COMMA_SEP +
            HabitTask.COLUMN_TASK_IMG_RES_ID + INTEGER_TYPE + DEFAULT_ATR + " 0)";

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
