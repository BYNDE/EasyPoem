package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PoemRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_read);

        TextView T_title = findViewById(R.id.TV_title);
        TextView T_text = findViewById(R.id.TV_text);

        T_title.setText(getIntent().getExtras().getString("title"));
        T_text.setText(getIntent().getExtras().getString("text"));

        findViewById(R.id.BTN_back).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.BTN_next).setOnClickListener(v -> {
            Intent intent = new Intent(this, PoemLearn.class);
            intent.putExtra("title", T_title.getText().toString());
            intent.putExtra("text", T_title.getText().toString());
            startActivity(intent);
        });
    }
}