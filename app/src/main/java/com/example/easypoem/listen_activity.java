package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

public class listen_activity extends AppCompatActivity  implements View.OnClickListener{
    private File file;
    private ImageButton play_button;
    private SeekBar seekBar;
    private Handler seekBarHandler;
    private Runnable updateSeekBar;
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private boolean audio_is_launched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_activity);

        String path = this.getExternalFilesDir("/").getAbsolutePath();
        file = new File(path+"/filename.3gp");

        play_button = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);

        play_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (isPlaying){
            play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_play_40));
            pauseAudio();
        }
        else {
            play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_pause_40));
            if (audio_is_launched){
                resumeAudio();
            }
            else {
                playAudio();
                audio_is_launched = true;
            }
        }
        isPlaying = !isPlaying;
    }

    private void pauseAudio(){
        mediaPlayer.pause();
        seekBarHandler.removeCallbacks(updateSeekBar);
    }

    private void resumeAudio(){
        mediaPlayer.start();
        updateRunnable();
        seekBar.postDelayed(updateSeekBar,0);
    }

    private void stopAudio() {
        mediaPlayer.stop();
        seekBarHandler.removeCallbacks(updateSeekBar);
    }

    public void playAudio(){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){

        }

        seekBar.setMax(mediaPlayer.getDuration());
        seekBarHandler = new Handler();
        updateRunnable();
        seekBar.postDelayed(updateSeekBar,0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
                play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_play_40));

                isPlaying = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayer.seekTo(progress);
                resumeAudio();
                play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_pause_40));
                isPlaying = true;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_play_40));
                isPlaying = false;
                stopAudio();
                audio_is_launched = false;
            }
        });
    }

    private void updateRunnable() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                seekBarHandler.postDelayed(this,500);
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlaying){
            mediaPlayer.stop();
        }
    }
}