package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class add_my_poem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_poem);
        getSupportActionBar().hide();

        EditText edit_author = findViewById(R.id.edit_author);
        EditText edit_title = findViewById(R.id.edit_title);
        EditText edit_text = findViewById(R.id.edit_text);
        Button add_button = findViewById(R.id.add_button);


    }
}