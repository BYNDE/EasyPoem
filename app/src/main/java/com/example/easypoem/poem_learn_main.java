package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easypoem.learn.Text;
import com.example.easypoem.sqlite.MyDbManager;

public class poem_learn_main extends AppCompatActivity implements View.OnClickListener{
    private FrameLayout fragment_layout;
    private static poem_learn_main instance;
    private TextView current_level_tv;
    private ProgressBar progressBar;
    private Fragment last_fragment;
    private ImageButton skip_button,back_button;
    private String text,title;
    private Text text_all;
    private int[] current_mass;
    private Bundle bundle;
    private int progress;
    private MyDbManager myDbManager;
    private boolean button_is_skip;


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
        current_level_tv = findViewById(R.id.current_poem_text_view);

        skip_button.setOnClickListener(this);
        back_button.setOnClickListener(this);



        myDbManager = new MyDbManager(this);

        get_values();

        current_level_tv.setText(getIntent().getExtras().getString("title"));
        progressBar.setVisibility(View.INVISIBLE);
        skip_button.setImageResource(R.drawable.ic_button_again_24);
        button_is_skip = false;


        bundle = new Bundle();
        bundle.putString("text", text);
        bundle.putInt("progress",progress);

        check_progress_fragment check_progress_fragment= new check_progress_fragment();
        check_progress_fragment.setArguments(bundle);
        replace_fragment(check_progress_fragment);

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
                    if (button_is_skip){
                        go_to_check_progress();
                    }else{
                       restart_study();
                    }
                break;

            case R.id.imageButton_back:
                super.onBackPressed();
                overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private  void get_values(){
        myDbManager.openDb();
        current_mass = myDbManager.getParagraph_and_level(getIntent().getExtras().getString("title")
                ,getIntent().getExtras().getString("author"));
        text_all = new Text(getIntent().getExtras().getString("text"));
        if (current_mass[1] >= current_mass[2]){
            text = text_all.paragraph[current_mass[1]-1].text;
        }else{
            text = text_all.paragraph[current_mass[1]].text;
        }

        progress = (int) Math.ceil(((Double.valueOf(current_mass[0]) + Double.valueOf(current_mass[1] * 3)) / (Double.valueOf(current_mass[2])*3))*100);
        myDbManager.closeDb();
    }


    public void setName(String name){
        current_level_tv.setText(name);
    }

    public void go_to_check_progress(){
        MyDbManager myDbManager = new MyDbManager(this);
        myDbManager.openDb();

        myDbManager.update_level(getIntent().getExtras().getString("title"),getIntent().getExtras().getString("author"),
                current_mass[0],current_mass[1],current_mass[2]);

        myDbManager.closeDb();

        get_values();

        bundle = new Bundle();
        bundle.putString("text", text);
        bundle.putInt("progress",progress);

        current_level_tv.setText(getIntent().getExtras().getString("title"));
        progressBar.setVisibility(View.INVISIBLE);
        skip_button.setImageResource(R.drawable.ic_button_again_24);
        button_is_skip = false;

        check_progress_fragment check_progress_fragment= new check_progress_fragment();
        check_progress_fragment.setArguments(bundle);
        replace_fragment(check_progress_fragment);

    }
    public void restart_study(){
        myDbManager.openDb();
        myDbManager.reset(getIntent().getExtras().getString("title")
                ,getIntent().getExtras().getString("author"));
        get_values();
        bundle = new Bundle();
        bundle.putString("text", text);
        bundle.putInt("progress",0);
        check_progress_fragment check_progress_fragment= new check_progress_fragment();
        check_progress_fragment.setArguments(bundle);
        replace_fragment(check_progress_fragment);
        myDbManager.closeDb();
    }
    public void go_to_level(){
        progressBar.setVisibility(View.VISIBLE);
        skip_button.setImageResource(R.drawable.ic_button_skip_24);
        button_is_skip = true;
        int current_level = current_mass[0];

        if (current_level == 0){
            fragment_mixed_strings fragment_mixed_strings= new fragment_mixed_strings();
            fragment_mixed_strings.setArguments(bundle);
            replace_fragment(fragment_mixed_strings);
        }else if (current_level == 1){
            fragment_learn_record fragment_learn_record= new fragment_learn_record();
            fragment_learn_record.setArguments(bundle);
            replace_fragment(fragment_learn_record);
        }else{
            fragment_drag_and_drop fragment_drag_and_drop = new fragment_drag_and_drop();
            fragment_drag_and_drop.setArguments(bundle);
            replace_fragment(fragment_drag_and_drop);
        }
    }
}