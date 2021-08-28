package com.example.easypoem;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easypoem.learn.Text;
import com.example.easypoem.sqlite.MyDbManager;


public class check_progress_fragment extends Fragment {

    private ProgressBar progressBar;
    private TextView text_tv,progress_tv, text_blank;
    private int progress;
    private Button continue_button;
    private boolean progress_is_100 = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_check_progress_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        text_tv = view.findViewById(R.id.TV_text);
        text_blank = view.findViewById(R.id.current_poem_text_view);
        progress_tv = view.findViewById(R.id.myTextProgress);
        continue_button = view.findViewById(R.id.button_continue);

        progressBar.setMax(10000);


        progress = this.getArguments().getInt("progress");
        progress_tv.setText(Integer.toString(progress));
        updateRunnable(progress*100);

        if(progress == 100){
            text_blank.setText("Вы успешно изучили стих");
            text_tv.setText("Можете пройти его снова");
            continue_button.setText("Перезапустить");
            progress_is_100 = true;
        } else
            text_tv.setText(this.getArguments().getString("text"));






        continue_button.setOnClickListener(v -> {
            if(progress_is_100){
                poem_learn_main poem_learn_main= new poem_learn_main();
                poem_learn_main.getInstance().restart_study();
            }else{
                poem_learn_main poem_learn_main= new poem_learn_main();
                poem_learn_main.getInstance().go_to_level();
            }
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