package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.easypoem.learn.Text;
import com.example.easypoem.sqlite.MyDbManager;

public class add_my_poem extends AppCompatActivity {
    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_poem);
        getSupportActionBar().hide();

        EditText edit_author = findViewById(R.id.edit_author);
        EditText edit_title = findViewById(R.id.edit_title);
        EditText edit_text = findViewById(R.id.edit_text);
        Button add_button = findViewById(R.id.add_button);
        ImageButton back_button = findViewById(R.id.BTN_back);
        myDbManager = new MyDbManager(this);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edit_title.getText().toString();
                String author = edit_author.getText().toString();
                String text = edit_text.getText().toString();
                Text text1 = new Text(text);
                int paragraphs_number = text1.getLengthParagraphs();
                myDbManager.insertToDb(title,author,text,1,paragraphs_number);

                Intent intent = new Intent(add_my_poem.this, MainActivity.class);
                startActivity(intent);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
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