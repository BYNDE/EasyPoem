package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

public class PoemLearn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_learn);

        String title = getIntent().getExtras().getString("title");
        String text = getIntent().getExtras().getString("text");

        TextView T_title = findViewById(R.id.TV_title);
        TextView T_text = findViewById(R.id.TV_text);
        T_text.setText(text);

        LinearLayout LL_Start = findViewById(R.id.LL_Start);
        LinearLayout LL_Learn = findViewById(R.id.LL_Learn);

        // LL_Learn
        Button BTN_variant1 = findViewById(R.id.BTN_variant1);
        Button BTN_variant2 = findViewById(R.id.BTN_variant2);
        Button BTN_variant3 = findViewById(R.id.BTN_variant3);

        // LL_Start
        findViewById(R.id.BTN_next).setOnClickListener(v -> {
            LL_Start.setVisibility(View.INVISIBLE);
            LL_Learn.setVisibility(View.VISIBLE);

            String[] mass = text.split(" ");
            Random random = new Random();
            int var1 = random.nextInt(mass.length);
            int var2 = random.nextInt(mass.length);
            int var3 = random.nextInt(mass.length);

            BTN_variant1.setText(mass[var1]);
            BTN_variant2.setText(mass[var2]);
            BTN_variant3.setText(mass[var3]);
        });




    }

}