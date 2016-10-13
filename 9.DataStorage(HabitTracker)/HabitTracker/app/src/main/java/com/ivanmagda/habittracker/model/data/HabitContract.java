package com.ivanmagda.habittracker.model.data;

import android.provider.BaseColumns;

public final class HabitContract {

    private HabitContract() {
    }

    public static abstract class HabitTask implements BaseColumns {

        /**
         * Table name.
         */
        public static final String TABLE_NAME = "habit_task";

        /**
         * Task name.
         */
        public static final String COLUMN_TASK_NAME = "name";

        /**
         * Number of times necessary for successful task completion per day.
         */
        public static final String COLUMN_TASK_PER_DAY = "per_day";

        /**
         * Image resource id of task.
         */
        public static final String COLUMN_TASK_IMG_RES_ID = "img_res_id";
    }

}
