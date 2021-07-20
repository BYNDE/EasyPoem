package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easypoem.sqlite.MyDbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PoemRead extends AppCompatActivity {
    private MyDbManager myDbManager;
    private boolean if_added = false;
    private boolean delete_active = false;

    public void change_add_button(Button btn_add){
        Drawable img_added = ContextCompat.getDrawable(this, R.drawable.ic_button_delete_24);
        btn_add.setCompoundDrawablesWithIntrinsicBounds(img_added,null,null,null);
        btn_add.setText("Удалить");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_read);
        getSupportActionBar().hide();

        myDbManager = new MyDbManager(this);
        myDbManager.openDb();


        TextView T_title = findViewById(R.id.TV_title);
        TextView T_title1 = findViewById(R.id.TV_title1);
        TextView T_text = findViewById(R.id.TV_text);
        TextView T_author = findViewById(R.id.TV_author);

        Button btn_add = findViewById(R.id.BTN_add);


       if( myDbManager.check_availability(getIntent().getExtras().getString("title"),getIntent().getExtras().getString("author"))){
           if_added = true;
           change_add_button(btn_add);
       }

        T_title.setText(getIntent().getExtras().getString("title"));
        T_title1.setText(getIntent().getExtras().getString("title"));
        T_text.setText(getIntent().getExtras().getString("text"));
        T_author.setText(getIntent().getExtras().getString("author"));

        findViewById(R.id.BTN_back).setOnClickListener(v -> {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_down, 0);
        });

        findViewById(R.id.BTN_learn).setOnClickListener(v -> {
            Intent intent = new Intent(this, check_learn_progress.class);
            intent.putExtra("title", T_title.getText().toString());
            intent.putExtra("text", T_text.getText().toString());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, 0);
        });
        btn_add.setOnClickListener(v -> {
            if (if_added){
                myDbManager.deleteFromDb(getIntent().getExtras().getString("title"),
                        getIntent().getExtras().getString("author"));
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_down, 0);

            }
            else{
                myDbManager.insertToDb(getIntent().getExtras().getString("title"),
                        getIntent().getExtras().getString("author"),
                        getIntent().getExtras().getString("text"),0);
                change_add_button(btn_add);
                if_added = true;
            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbManager.closeDb();
    }
}