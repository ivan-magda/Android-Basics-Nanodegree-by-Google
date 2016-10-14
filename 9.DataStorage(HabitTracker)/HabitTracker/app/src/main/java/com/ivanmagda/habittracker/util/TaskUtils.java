package com.ivanmagda.habittracker.util;

import android.database.Cursor;

import com.ivanmagda.habittracker.model.data.HabitContract;
import com.ivanmagda.habittracker.model.object.Task;

import java.util.ArrayList;

public class TaskUtils {

    private TaskUtils() {
    }

    public static ArrayList<Task> extractTasksFromCursor(Cursor cursor) {
        ArrayList<Task> tasks = null;
        if (cursor.getCount() > 0) {
            tasks = new ArrayList<>(cursor.getCount());

            int idColumnIdx = cursor.getColumnIndex(HabitContract.HabitTask._ID);
            int nameColumnIdx = cursor.getColumnIndex(HabitContract.HabitTask.COLUMN_TASK_NAME);
            int perDayColumnIdx = cursor.getColumnIndex(HabitContract.HabitTask.COLUMN_TASK_PER_DAY);
            int imgResIdColumnIdx = cursor.getColumnIndex(HabitContract.HabitTask.COLUMN_TASK_IMG_RES_ID);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIdx);
                String name = cursor.getString(nameColumnIdx);
                int perDay = cursor.getInt(perDayColumnIdx);
                int imgResId = cursor.getInt(imgResIdColumnIdx);

                tasks.add(new Task(id, name, perDay, imgResId));
            }
        }

        return tasks;
    }

}
