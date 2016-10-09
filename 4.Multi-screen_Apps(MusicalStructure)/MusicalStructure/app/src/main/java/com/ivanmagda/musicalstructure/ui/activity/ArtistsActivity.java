package com.ivanmagda.musicalstructure.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivanmagda.musicalstructure.R;
import com.ivanmagda.musicalstructure.ui.activity.detail.ArtistDetailActivity;

public class ArtistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        Button artistDetailButton = (Button) findViewById(R.id.artist_detail_button);
        artistDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistsActivity.this, ArtistDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
