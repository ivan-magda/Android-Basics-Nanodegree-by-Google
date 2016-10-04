package com.ivanmagda.musicalstructure.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ivanmagda.musicalstructure.R;
import com.ivanmagda.musicalstructure.ui.activity.detail.AlbumDetailActivity;

public class AlbumsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        Button albumDetailButton = (Button) findViewById(R.id.album_detail_button);
        albumDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumsActivity.this, AlbumDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
