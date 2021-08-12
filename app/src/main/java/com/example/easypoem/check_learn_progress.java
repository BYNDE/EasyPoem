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
import com.example.easypoem.sqlite.MyDbManager;

public class check_learn_progress extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView text_tv, title_tv,progress_tv;
    private ImageButton reset_button, back_button;
    private int[] current_mass;
    private Text text;
    private int progress;
    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_learn_progress);
        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar);
        text_tv = findViewById(R.id.TV_text);
        title_tv = findViewById(R.id.title);
        progress_tv = findViewById(R.id.myTextProgress);
        back_button = findViewById(R.id.imageButton_back);
        reset_button = findViewById(R.id.imageButton_reset);

        title_tv.setText(getIntent().getExtras().getString("title"));

        progressBar.setMax(10000);


        myDbManager = new MyDbManager(this);
        myDbManager.openDb();

        set_values();

        myDbManager.closeDb();



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbManager.openDb();
                myDbManager.reset(getIntent().getExtras().getString("title")
                        ,getIntent().getExtras().getString("author"));
                set_values();
                myDbManager.closeDb();
            }
        });




        findViewById(R.id.button_continue).setOnClickListener(v -> {
            Intent intent = new Intent(this, poem_learn_main.class);
            intent.putExtra("text",text.paragraph[current_mass[1]].text);
            intent.putExtra("level",current_mass[0]);
            intent.putExtra("paragraph",current_mass[1]);
            intent.putExtra("title",getIntent().getExtras().getString("title"));
            intent.putExtra("author",getIntent().getExtras().getString("author"));
            intent.putExtra("text_all",getIntent().getExtras().getString("text"));
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

    private  void set_values(){
        current_mass = myDbManager.getParagraph_and_level(getIntent().getExtras().getString("title")
                ,getIntent().getExtras().getString("author"));
        text = new Text(getIntent().getExtras().getString("text"));
        text_tv.setText(text.paragraph[current_mass[1]].text);
        progress = (int) Math.ceil(((Double.valueOf(current_mass[0]) + Double.valueOf(current_mass[1] * 3)) / (Double.valueOf(current_mass[2])*3))*100);
        progress_tv.setText(Integer.toString(progress));
        updateRunnable(progress*100);
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