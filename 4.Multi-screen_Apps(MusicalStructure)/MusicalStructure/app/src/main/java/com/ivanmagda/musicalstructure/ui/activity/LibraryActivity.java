package com.ivanmagda.musicalstructure.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivanmagda.musicalstructure.R;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.la_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        Button playlistsButton = (Button) findViewById(R.id.playlists_button);
        playlistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, PlaylistsActivity.class);
                startActivity(intent);
            }
        });

        Button artistsButton = (Button) findViewById(R.id.artists_button);
        artistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, ArtistsActivity.class);
                startActivity(intent);
            }
        });

        Button albumsButton = (Button) findViewById(R.id.albums_button);
        albumsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, AlbumsActivity.class);
                startActivity(intent);
            }
        });

        Button songsButton = (Button) findViewById(R.id.songs_button);
        songsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, SongsActivity.class);
                startActivity(intent);
            }
        });

        Button nowPlayingButton = (Button) findViewById(R.id.now_playing_button);
        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, NowPlayingActivity.class);
                startActivity(intent);
            }
        });
    }

}
