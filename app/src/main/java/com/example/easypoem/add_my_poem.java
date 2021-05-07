package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class add_my_poem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_poem);
        getSupportActionBar().hide();
    }
}