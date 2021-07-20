package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easypoem.learn.Text;

public class check_learn_progress extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView text_tv, title_tv;
    private ImageButton reset_button, back_button;
    private Text text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_learn_progress);
        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar);
        text_tv = findViewById(R.id.TV_text);
        title_tv = findViewById(R.id.title);
        back_button = findViewById(R.id.imageButton_back);
        reset_button = findViewById(R.id.imageButton_reset);

        title_tv.setText(getIntent().getExtras().getString("title"));

        text = new Text(getIntent().getExtras().getString("text"));
        text_tv.setText(text.paragraph[0].text);


        progressBar.setMax(10000);

        updateRunnable(7000);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });




        findViewById(R.id.button_continue).setOnClickListener(v -> {
            Intent intent = new Intent(this, poem_learn_main.class);
            intent.putExtra("text",text.paragraph[0].text);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, 0);
        });

    }
    private void updateRunnable(int progress) {

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",0,  progress);
        animation.setDuration(1100);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void back(){
        check_learn_progress.super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, 0);
    }
}