package com.example.easypoem;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easypoem.learn.Text;
import com.example.easypoem.sqlite.MyDbManager;


public class check_progress_fragment extends Fragment {

    private ProgressBar progressBar;
    private TextView text_tv, title_tv,progress_tv;
    private ImageButton reset_button, back_button;
    private int[] current_mass;
    private Text text;
    private int progress;
    private MyDbManager myDbManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_check_progress_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        text_tv = view.findViewById(R.id.TV_text);
        progress_tv = view.findViewById(R.id.myTextProgress);

        progressBar.setMax(10000);

        text_tv.setText(this.getArguments().getString("text"));
        progress = this.getArguments().getInt("progress");
        progress_tv.setText(Integer.toString(progress));
        updateRunnable(progress*100);






        view.findViewById(R.id.button_continue).setOnClickListener(v -> {
            poem_learn_main poem_learn_main= new poem_learn_main();
            poem_learn_main.getInstance().go_to_level();
        });

       return view;
    }
    private void updateRunnable(int progress) {

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",0,  progress);
        animation.setDuration(1100);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
}