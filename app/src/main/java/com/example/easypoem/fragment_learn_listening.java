package com.example.easypoem;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

public class fragment_learn_listening extends Fragment implements View.OnClickListener{
    private File file;
    private ImageButton play_button;
    private SeekBar seekBar;
    private Handler seekBarHandler;
    private Runnable updateSeekBar;
    private String current_text;
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private boolean audio_is_launched = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learn_listening, container, false);

        String path = getActivity().getExternalFilesDir("/").getAbsolutePath();
        file = new File(path+"/filename.3gp");

        play_button = view.findViewById(R.id.play);
        seekBar = view.findViewById(R.id.seekBar);

        play_button.setOnClickListener(this);

        TextView tv_text = view.findViewById(R.id.TV_text);

        current_text = this.getArguments().getString("text");

        tv_text.setText(current_text);

        return view;
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
        mediaPlayer.setVolume(1.0f,1.0f);
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
                Bundle bundle = new Bundle();
                bundle.putString("text", current_text);
                poem_learn_main poem_learn_main= new poem_learn_main();
                fragment_drag_and_drop fragment_drag_and_drop = new fragment_drag_and_drop();
                fragment_drag_and_drop.setArguments(bundle);
                poem_learn_main.getInstance().replace_fragment(fragment_drag_and_drop);
                poem_learn_main.getInstance().set_progress(0);
            }
        });
    }

    private void updateRunnable() {
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                seekBarHandler.postDelayed(this,0);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isPlaying) {
            mediaPlayer.stop();
        }
    }
}