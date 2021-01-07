package com.mefahimrahman.bmplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton play, pause, stop;
    MediaPlayer mediaPlayer;
    int CurrentPosition;
    TextView tvTime, tvDuration;
    SeekBar seekBarTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (ImageButton) findViewById(R.id.playButton);
        pause = (ImageButton) findViewById(R.id.pauseButton);
        stop = (ImageButton) findViewById(R.id.stopButton);
        tvTime = (TextView) findViewById(R.id.leftTime);
        tvDuration = (TextView) findViewById(R.id.rightTime);
        seekBarTime = (SeekBar) findViewById(R.id.seekBarTime);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

        mediaPlayer  = MediaPlayer.create(getApplicationContext(), R.raw.got);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);

//      Set Right Side Time
        String Duration = millisecondsToString(mediaPlayer.getDuration());
        tvDuration.setText(Duration);

        seekBarTime.setMax(mediaPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    CurrentPosition = progress;
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    if(mediaPlayer.isPlaying()) {
                        try {
                            final double current = mediaPlayer.getCurrentPosition();
                            String elapsedTime = millisecondsToString((int) current);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvTime.setText(elapsedTime);
                                    seekBarTime.setProgress((int) current);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.e("Exception","In Catch Thread");
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playButton:
                if(mediaPlayer == null) {
                    mediaPlayer  = MediaPlayer.create(getApplicationContext(), R.raw.got);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (mediaPlayer != null) {
                                if(mediaPlayer.isPlaying()) {
                                    try {
                                        final double current = mediaPlayer.getCurrentPosition();
                                        String elapsedTime = millisecondsToString((int) current);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tvTime.setText(elapsedTime);
                                                seekBarTime.setProgress((int) current);
                                            }
                                        });
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Log.e("Exception","In Catch Thread");
                                    }
                                }
                            }
                        }
                    }).start();

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
                    tvTime.setText("0:00");
                    seekBarTime.setProgress(0);
                    mediaPlayer = null;
                }
                break;

        }
    }

    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes+":";
        if(seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return  elapsedTime;
    }
}