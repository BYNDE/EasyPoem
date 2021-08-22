package com.example.easypoem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class fragment_learn_record extends Fragment implements View.OnClickListener{
    private static final int PERMISSION_CODE = 1;
    private boolean is_recording = false;
    private ImageButton record_button,record_again_button,correct_button;
    private MediaRecorder mediaRecorder;
    private String record_file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learn_record, container, false);


        record_button = view.findViewById(R.id.start_or_stop_recording);
        record_again_button = view.findViewById(R.id.record_again);
        correct_button = view.findViewById(R.id.correct);
        TextView tv_text = view.findViewById(R.id.TV_text);

        poem_learn_main poem_learn_main= new poem_learn_main();
        poem_learn_main.getInstance().setName("Аудиосуфлёр");


        tv_text.setText(this.getArguments().getString("text"));


        record_button.setOnClickListener(this);
        record_again_button.setOnClickListener(this);
        correct_button.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_or_stop_recording:
                if(is_recording){
                    stopRecording();
                    record_button.setVisibility(View.INVISIBLE);
                    record_again_button.setVisibility(View.VISIBLE);
                    correct_button.setVisibility(View.VISIBLE);
                    is_recording = !is_recording;
                }
                else{
                    if(check_permission()){
                        startRecording();
                        record_button.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_button_stop_40));
                        is_recording = !is_recording;
                    }
                }
                break;

            case R.id.record_again:
                startRecording();
                record_again_button.setVisibility(View.INVISIBLE);
                correct_button.setVisibility(View.INVISIBLE);
                record_button.setVisibility(View.VISIBLE);
                is_recording = !is_recording;
                break;

            case R.id.correct:
                Bundle bundle = new Bundle();
                bundle.putString("text", this.getArguments().getString("text"));
                poem_learn_main poem_learn_main= new poem_learn_main();
                fragment_learn_listening fragment_learn_listening = new fragment_learn_listening();
                fragment_learn_listening.setArguments(bundle);
                poem_learn_main.getInstance().replace_fragment(fragment_learn_listening);
                poem_learn_main.getInstance().set_progress(100);
                break;
        }
    }

    private void startRecording() {
        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        record_file = "filename.3gp";



        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
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
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private boolean check_permission() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},PERMISSION_CODE);
            return false;
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (is_recording){
            stopRecording();
        }
    }
}
