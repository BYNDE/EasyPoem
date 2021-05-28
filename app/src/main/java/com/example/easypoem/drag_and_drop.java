package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class drag_and_drop extends AppCompatActivity implements word_item_adapter.OnLongClickItem{
    TextView word_textview;
    String word_entered_text;
    RecyclerView words_recycler;
    word_item_adapter adapter;
    View entered_word;
    ArrayList<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);
        words_recycler = findViewById(R.id.words_recycler);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setJustifyContent(JustifyContent.CENTER);
        manager.setAlignItems(AlignItems.CENTER);

        words.add("яблоко");
        words.add("привет");
        words.add("улица");
        words.add("дом");
        words.add("телефон");
        words.add("лампа");



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