package com.ivanmagda.reportcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String student = "Ivan Magda";
        int year = 2016;
        ReportCard.Subject[] subjects = new ReportCard.Subject[]{
                new ReportCard.Subject("English", 5, "Need more practice."),
                new ReportCard.Subject("History", 4, ""),
                new ReportCard.Subject("Science", 5, ""),
                new ReportCard.Subject("Math", 5, ""),
                new ReportCard.Subject("Art", 4, ""),
                new ReportCard.Subject("Gym", 5, "Strong athlete.")
        };

        ReportCard reportCard = new ReportCard(student, year, subjects);
        Log.v(LOG_TAG, "" + reportCard);
    }

}
