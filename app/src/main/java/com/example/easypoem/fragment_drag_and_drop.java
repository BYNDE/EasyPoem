package com.example.easypoem;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easypoem.learn.Paragraph;
import com.example.easypoem.learn.Text;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;


public class fragment_drag_and_drop extends Fragment implements word_item_adapter.OnLongClickItem{

    TextView word_textview;
    String word_entered_text;
    RecyclerView words_recycler;
    word_item_adapter adapter;
    View entered_word;
    Text text;
    Paragraph paragraph;
    ArrayList<String> words = new ArrayList<>();
    TextView selectTextView;
    FlexboxLayout flexboxLayout;
    View view;
    int level = 0;
    int countLevels = 5;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drag_and_drop, container, false);

        words_recycler = view.findViewById(R.id.words_recycler);
        flexboxLayout = view.findViewById(R.id.flex_layout);
        text = new Text(this.getArguments().getString("text"));
        paragraph = text.getParagraph();
        if (paragraph == null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        selectword();


        return view;
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

    public void selectword() {
        if (level == countLevels) {
            poem_learn_main poem_learn_main= new poem_learn_main();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    poem_learn_main.getInstance().go_to_check_progress();
                }
            }, 1000);
            return;
        }
        level++;
        flexboxLayout.removeAllViews();
        words.clear();
        int selectidWord = paragraph.randomWord();
        paragraph.selectWord(selectidWord);
        for (int i = 0; i < paragraph.words.length; i++) {
            if (i != selectidWord) {
                TextView textView = new TextView(getActivity());
                textView.setText(paragraph.words[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(25);
                textView.setTextColor(getResources().getColor(R.color.gray_hint));
                flexboxLayout.addView(textView);
            } else {
                selectTextView = new TextView(getActivity());
                selectTextView.setText("_______");
                selectTextView.setGravity(Gravity.CENTER);
                selectTextView.setTextSize(25);
                selectTextView.setTextColor(getResources().getColor(R.color.gray_hint));
                selectTextView.setBackground(ContextCompat.getDrawable(getContext() ,R.drawable.border_word));
                selectTextView.setId(R.id.word);
                flexboxLayout.addView(selectTextView);
            }
        }

        words.add(paragraph.selectedWord);
        words.add(paragraph.words[paragraph.randomWord()]);
        words.add(paragraph.words[paragraph.randomWord()]);
        Collections.shuffle(words);


        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setJustifyContent(JustifyContent.CENTER);
        manager.setAlignItems(AlignItems.CENTER);

        adapter = new word_item_adapter(getActivity(), words,this);
        words_recycler.setLayoutManager(manager);

        words_recycler.setAdapter(adapter);

        word_textview = view.findViewById(R.id.word);

        word_textview.setOnDragListener(dragListener);
    }

    View.OnDragListener dragListener = new View.OnDragListener() {
        boolean word_correct = false;
        @Override
        public boolean onDrag(View v, @NotNull DragEvent event) {
            int drag_event = event.getAction();
            switch (drag_event){
                case DragEvent.ACTION_DRAG_ENTERED:
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
                    if (word_textview.getText().toString().equals(paragraph.selectedWord)){
                        poem_learn_main poem_learn_main= new poem_learn_main();
                        int progress = poem_learn_main.getInstance().get_current_progress();
                        poem_learn_main.getInstance().set_progress(progress + 20);
                        selectword();
                    }

                    break;
            }
            return true;
        }
    };
}