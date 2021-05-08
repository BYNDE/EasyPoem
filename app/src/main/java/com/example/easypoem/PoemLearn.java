package com.example.easypoem;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PoemLearn extends AppCompatActivity implements View.OnClickListener {
    String[] mass;
    Button[] buttons;
    Button BTN_send;
    String word;
    int wordid;
    TextView T_text;
    TextView T_title;
    EditText ET;
    int waring = 0;

    int countPhase = 3;
    int phase = 0;
    LinearLayout LL_Start;
    LinearLayout LL_Learn;
    LinearLayout LL_Learn2;
    LinearLayout LL_Finish;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_learn);
        getSupportActionBar().hide();

        String default_title = getIntent().getExtras().getString("title");
        String default_text = getIntent().getExtras().getString("text");

        T_title = findViewById(R.id.TV_title);
        T_text = findViewById(R.id.TV_text);
        T_text.setText(getIntent().getExtras().getString("text"));

        LL_Start = findViewById(R.id.LL_Start);
        LL_Learn = findViewById(R.id.LL_Learn);
        LL_Learn2 = findViewById(R.id.LL_Learn2);
        LL_Finish = findViewById(R.id.LL_Finish);

        ET = findViewById(R.id.ET);

        // LL_Learn
        Button BTN_variant1 = findViewById(R.id.BTN_variant1);
        BTN_variant1.setOnClickListener(this);
        Button BTN_variant2 = findViewById(R.id.BTN_variant2);
        BTN_variant2.setOnClickListener(this);
        Button BTN_variant3 = findViewById(R.id.BTN_variant3);
        BTN_variant3.setOnClickListener(this);
        buttons = new Button[]{BTN_variant1, BTN_variant2, BTN_variant3};

        //LL_Learn2
        BTN_send = findViewById(R.id.BTN_send);
        BTN_send.setTag("ET");
        BTN_send.setOnClickListener(this);

        // LL_Start
        findViewById(R.id.BTN_next).setOnClickListener(v -> {
            if (countPhase <= 5) {
                LL_Start.setVisibility(View.INVISIBLE);
                LL_Learn.setVisibility(View.VISIBLE);
                LL_Learn2.setVisibility(View.INVISIBLE);
                LL_Finish.setVisibility(View.INVISIBLE);
            } else {
                LL_Start.setVisibility(View.INVISIBLE);
                LL_Learn.setVisibility(View.INVISIBLE);
                LL_Learn2.setVisibility(View.VISIBLE);
                LL_Finish.setVisibility(View.INVISIBLE);
            }
            T_title.setText(default_title);

            mass = default_text.split(" ");

            SelectWord();
        });
        findViewById(R.id.BTN_back).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        //LL_Finish
        findViewById(R.id.BTN_finish).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        Log.println(Log.DEBUG, "ET", v.getTag().toString());
        if (v.getTag().toString().equals("ET")) {
            v.setTag(ET.getText().toString());
            Log.println(Log.DEBUG, "ET", "ddd");
        }
        if (v.getTag().toString().toLowerCase().equals(word.toLowerCase())) {
            phase++;
            if (phase < countPhase) {
                mass[wordid] = word;
                SelectWord();
            }
            else if (countPhase < 10) {
                LL_Start.setVisibility(View.VISIBLE);
                LL_Learn.setVisibility(View.INVISIBLE);
                LL_Learn2.setVisibility(View.INVISIBLE);
                LL_Finish.setVisibility(View.INVISIBLE);
                T_title.setText("Прочитайте текст несколько раз");
                countPhase++;
                phase = 0;
                mass[wordid] = word;
                T_text.setText(String.join(" ", mass));
            } else {
                LL_Start.setVisibility(View.INVISIBLE);
                LL_Learn.setVisibility(View.INVISIBLE);
                LL_Learn2.setVisibility(View.INVISIBLE);
                LL_Finish.setVisibility(View.VISIBLE);
                phase = 0;
                mass[wordid] = word;
                T_text.setText(String.join(" ", mass));
                T_title.setText("У вас " + waring + " ошибок.");
            }
    } else {
            if (LL_Learn.getVisibility() == View.VISIBLE) {
                for (Button button : buttons) {
                    if (button.getTag().equals(v.getTag())) {
                        button.setText("Неправельно");
                    }
                }
            }
            else {
                ET.setHint("Неправильно");
                ET.setText("");
            }
            waring++;
        }
        BTN_send.setTag("ET");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    void SelectWord() {
        if (countPhase <= 5) {
            Random random = new Random();
            int r = random.nextInt(mass.length);
            if (wordid != r) wordid = r;
            word = mass[wordid];
            mass[wordid] = "____";
            Collections.shuffle(Arrays.asList(buttons));
            for (int i = 0; i < buttons.length; i++) {
                if (i==0) {
                    buttons[i].setText(word);
                    buttons[i].setTag(word);
                }else{
                    String wordtemp = mass[random.nextInt(mass.length)];
                    buttons[i].setText(wordtemp);
                    buttons[i].setTag(wordtemp);
                }
            }
        } else {
            Random random = new Random();
            int r = random.nextInt(mass.length);
            if (wordid != r) wordid = r;
            word = mass[wordid];
            mass[wordid] = "____";
            ET.setHint("Ведите слово");
            ET.setText("");

        }
        T_text.setText(String.join(" ", mass));
    }
}