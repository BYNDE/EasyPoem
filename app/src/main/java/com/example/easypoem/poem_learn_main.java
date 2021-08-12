package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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


        text = getIntent().getExtras().getString("text");
        Bundle bundle = new Bundle();
        bundle.putString("text", text );

        int current_level = getIntent().getExtras().getInt("level");

        if (current_level == 0){
            fragment_mixed_strings fragment_mixed_strings= new fragment_mixed_strings();
            fragment_mixed_strings.setArguments(bundle);
            replace_fragment(fragment_mixed_strings);
        }else if (current_level == 1){
            fragment_learn_record fragment_learn_record= new fragment_learn_record();
            fragment_learn_record.setArguments(bundle);
            replace_fragment(fragment_learn_record);
        }else{
//            fragment_drag_and_drop fragment_drag_and_drop= new fragment_drag_and_drop();
//            fragment_drag_and_drop.setArguments(bundle);
//            replace_fragment(fragment_drag_and_drop);
            go_to_check_progress();
        }




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
                    go_to_check_progress();
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
    public void setName(String name){
        current_level_tv.setText(name);
    }

    public void go_to_check_progress(){
        MyDbManager myDbManager = new MyDbManager(this);
        myDbManager.openDb();

        myDbManager.update_level(getIntent().getExtras().getString("title"),getIntent().getExtras().getString("author"),
                getIntent().getExtras().getInt("level"),getIntent().getExtras().getInt("paragraph"));

        myDbManager.closeDb();


        Intent intent = new Intent(this, check_learn_progress.class);
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        intent.putExtra("text", getIntent().getExtras().getString("text_all"));
        intent.putExtra("author", getIntent().getExtras().getString("author"));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, 0);
    }
}