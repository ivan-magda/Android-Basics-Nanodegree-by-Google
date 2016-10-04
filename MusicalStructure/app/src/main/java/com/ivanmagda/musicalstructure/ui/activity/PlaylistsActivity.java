package com.ivanmagda.musicalstructure.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivanmagda.musicalstructure.R;
import com.ivanmagda.musicalstructure.ui.activity.detail.PlaylistDetailActivity;

public class PlaylistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        Button detailButton = (Button) findViewById(R.id.playlist_detail);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistsActivity.this, PlaylistDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
