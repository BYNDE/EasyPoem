package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
<<<<<<< HEAD
        BTN_variant3.setOnClickListener(this);
        buttons = new Button[]{BTN_variant1, BTN_variant2, BTN_variant3};

        //LL_Learn2
        BTN_send = findViewById(R.id.BTN_send);
        BTN_send.setTag("ET");
        BTN_send.setOnClickListener(this);
=======
>>>>>>> parent of 1761f3a (f)

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

<<<<<<< HEAD
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
=======



>>>>>>> parent of 1761f3a (f)
    }

}