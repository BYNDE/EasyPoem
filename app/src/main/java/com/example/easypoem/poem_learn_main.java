package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class poem_learn_main extends AppCompatActivity implements View.OnClickListener{
    private FrameLayout fragment_layout;
    private static poem_learn_main instance;
    private ProgressBar progressBar;
    private Fragment last_fragment;
    private ImageButton skip_button,back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_learn_main);
        getSupportActionBar().hide();

        instance = this;
        fragment_layout = findViewById(R.id.fragment);
        progressBar = findViewById(R.id.progressBar);
        skip_button = findViewById(R.id.imageButton_skip);
        back_button = findViewById(R.id.imageButton_back);

        skip_button.setOnClickListener(this);
        back_button.setOnClickListener(this);


        fragment_learn_record fragment_learn_record = new fragment_learn_record();
        replace_fragment(fragment_learn_record);
        progressBar.setProgress(50);


    }
    public static poem_learn_main getInstance() {
        return instance;
    }

    public void replace_fragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(fragment_layout.getId(), fragment).commit();
        last_fragment = fragment;
    }
    public void set_progress(int progress){
        progressBar.setProgress(progress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_skip:

                break;

            case R.id.imageButton_back:
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_down, 0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, 0);
    }
}