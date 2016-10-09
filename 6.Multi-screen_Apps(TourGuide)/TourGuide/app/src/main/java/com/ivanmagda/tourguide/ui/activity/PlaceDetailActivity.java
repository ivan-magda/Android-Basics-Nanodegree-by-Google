package com.ivanmagda.tourguide.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ivanmagda.tourguide.R;
import com.ivanmagda.tourguide.ui.fragment.PlaceDetailFragment;

public class PlaceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new PlaceDetailFragment())
                    .commit();
        }
    }

}
