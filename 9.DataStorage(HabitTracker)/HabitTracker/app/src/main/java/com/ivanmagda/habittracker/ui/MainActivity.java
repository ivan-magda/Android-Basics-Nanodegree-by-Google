package com.ivanmagda.habittracker.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ivanmagda.habittracker.R;
import com.ivanmagda.habittracker.model.data.HabitsDataStoreManager;
import com.ivanmagda.habittracker.model.object.Task;
import com.ivanmagda.habittracker.util.TaskUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitsDataStoreManager dataStoreManager = new HabitsDataStoreManager(this);
        Log.v(LOG_TAG, "Inserted task new row id: " + dataStoreManager.insertDummyTask());

        Log.v(LOG_TAG, "Queried tasks: ");
        Cursor cursor = dataStoreManager.allTasksCursor();
        ArrayList<Task> tasks = TaskUtils.extractTasksFromCursor(cursor);
        for (Task task : tasks) Log.v(LOG_TAG, task.toString());

        cursor.close();
    }

}
