package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class record_activity extends AppCompatActivity implements View.OnClickListener{
    private static final int PERMISSION_CODE = 1;
    private boolean is_recording = false;
    private ImageButton record_button,record_again_button,correct_button;
    private LinearLayout layout;
    private MediaRecorder mediaRecorder;
    private Chronometer record_timer;
    private String record_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_activity);
        record_timer = findViewById(R.id.chronometer);
        record_button = findViewById(R.id.start_or_stop_recording);
        record_again_button = findViewById(R.id.record_again);
        correct_button = findViewById(R.id.correct);
        layout = findViewById(R.id.layout);
        record_button.setOnClickListener(this);
        record_again_button.setOnClickListener(this);
        correct_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_or_stop_recording:
                if(is_recording){
                    stopRecording();
                    record_button.setVisibility(View.INVISIBLE);
                    layout.setVisibility(View.VISIBLE);
                    is_recording = !is_recording;
                }
                else{
                    if(check_permission()){
                        startRecording();
                        record_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_button_stop_40));
                        is_recording = !is_recording;
                    }
                }
            break;

            case R.id.record_again:
                startRecording();
                layout.setVisibility(View.INVISIBLE);
                record_button.setVisibility(View.VISIBLE);
                is_recording = !is_recording;
            break;

            case R.id.correct:
                Intent intent = new Intent(this, listen_activity.class);
                startActivity(intent);
            break;
        }
    }

    private void startRecording() {
        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        record_file = "filename.3gp";

        record_timer.setBase(SystemClock.elapsedRealtime());
        record_timer.start();


        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + record_file);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mediaRecorder.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void stopRecording() {
        record_timer.stop();
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private boolean check_permission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},PERMISSION_CODE);
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (is_recording){
            stopRecording();
        }
    }
}