package com.example.easypoem;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypoem.learn.Text;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Collections;

public class drag_and_drop extends AppCompatActivity implements word_item_adapter.OnLongClickItem{
    TextView word_textview;
    String word_entered_text;
    RecyclerView words_recycler;
    word_item_adapter adapter;
    View entered_word;
    ArrayList<String> words = new ArrayList<>();

    FlexboxLayout flexboxLayout;

     String defText = "— Скажи-ка, дядя, ведь не даром\n" +
            "Москва, спаленная пожаром,\n" +
            "Французу отдана?\n" +
            "Ведь были ж схватки боевые,\n" +
            "Да, говорят, еще какие!\n" +
            "Недаром помнит вся Россия\n" +
            "Про день Бородина!\n" +
            "\n" +
            "— Да, были люди в наше время,\n" +
            "Не то, что нынешнее племя:\n" +
            "Богатыри — не вы!\n" +
            "Плохая им досталась доля:\n" +
            "Немногие вернулись с поля…\n" +
            "Не будь на то господня воля,\n" +
            "Не отдали б Москвы!\n" +
            "\n" +
            "Мы долго молча отступали,\n" +
            "Досадно было, боя ждали,\n" +
            "Ворчали старики:\n" +
            "«Что ж мы? на зимние квартиры?\n" +
            "Не смеют, что ли, командиры\n" +
            "Чужие изорвать мундиры\n" +
            "О русские штыки?»\n" +
            "\n" +
            "И вот нашли большое поле:\n" +
            "Есть разгуляться где на воле!\n" +
            "Построили редут.\n" +
            "У наших ушки на макушке!\n" +
            "Чуть утро осветило пушки\n" +
            "И леса синие верхушки —\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);
        words_recycler = findViewById(R.id.words_recycler);
        flexboxLayout = findViewById(R.id.flex_layout);

        Text text = new Text(defText);

        int selectidWord = text.randomWord();
        text.selectWord(selectidWord);
        for (int i = 0; i < text.words.length; i++) {
            if (i != selectidWord) {
                TextView textView = new TextView(this);
                textView.setText(text.words[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                flexboxLayout.addView(textView);
            } else {
                TextView textView = new TextView(this);
                textView.setText("         ");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                textView.setBackground(Drawable.createFromPath("@drawable/border_word"));
                textView.setId(R.id.word);
                flexboxLayout.addView(textView);
            }
        }

        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setJustifyContent(JustifyContent.CENTER);
        manager.setAlignItems(AlignItems.CENTER);

        words.add(text.selectedWord);
        words.add(text.words[text.randomWord()]);
        words.add(text.words[text.randomWord()]);
        Collections.shuffle(words);

        adapter = new word_item_adapter(this, words,drag_and_drop.this);
        words_recycler.setLayoutManager(manager);

        words_recycler.setAdapter(adapter);

        word_textview = findViewById(R.id.word);

        word_textview.setOnDragListener(dragListener);
    }

    @Override
    public void onLongClickItem(View view,String word) {
            ClipData clipData = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(clipData,myShadowBuilder,view,0);
            view.setVisibility(View.INVISIBLE);
            entered_word = view;
            word_entered_text = word;


    }



    View.OnDragListener dragListener = new View.OnDragListener() {
        boolean word_correct = false;
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int drag_ivent = event.getAction();
            switch (drag_ivent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    word_textview.setText(word_entered_text);
                    word_correct = true;
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (word_correct){
                        words.remove(word_entered_text);
                        words_recycler.setAdapter(adapter);
                    }else{
                        entered_word.setVisibility(View.VISIBLE);
                    }
                    word_correct = false;
                    break;
            }
            return true;
        }
    };
}