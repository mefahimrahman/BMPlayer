package com.mefahimrahman.bmplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton play, pause, stop;
    MediaPlayer mediaPlayer;
    int CurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (ImageButton) findViewById(R.id.playButton);
        pause = (ImageButton) findViewById(R.id.pauseButton);
        stop = (ImageButton) findViewById(R.id.stopButton);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playButton:
                if(mediaPlayer == null) {
                    mediaPlayer  = MediaPlayer.create(getApplicationContext(), R.raw.got);
                    mediaPlayer.start();
                }
                else if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(CurrentPosition);
                    mediaPlayer.start();
                }
                break;
            case R.id.pauseButton:
                if(mediaPlayer != null) {
                    mediaPlayer.pause();
                    CurrentPosition = mediaPlayer.getCurrentPosition();
                }
                break;
            case R.id.stopButton:
                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                break;

        }
    }
}