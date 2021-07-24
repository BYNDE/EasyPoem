package com.example.easypoem;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easypoem.learn.Text;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Collections;


public class fragment_drag_and_drop extends Fragment implements word_item_adapter.OnLongClickItem{

    TextView word_textview;
    String word_entered_text;
    RecyclerView words_recycler;
    word_item_adapter adapter;
    View entered_word;
    Text text;
    ArrayList<String> words = new ArrayList<>();
    TextView selectTextView;
    FlexboxLayout flexboxLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drag_and_drop, container, false);

        words_recycler = view.findViewById(R.id.words_recycler);
        flexboxLayout = view.findViewById(R.id.flex_layout);
        text = new Text(this.getArguments().getString("text"));


        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setJustifyContent(JustifyContent.CENTER);
        manager.setAlignItems(AlignItems.CENTER);

        selectword();

        adapter = new word_item_adapter(getActivity(), words,this);
        words_recycler.setLayoutManager(manager);

        words_recycler.setAdapter(adapter);

        word_textview = view.findViewById(R.id.word);

        word_textview.setOnDragListener(dragListener);

        poem_learn_main poem_learn_main= new poem_learn_main();
        poem_learn_main.getInstance().setName("Drag&Drop");

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
        flexboxLayout.removeAllViews();
        words.clear();
        int selectidWord = text.randomWord();
        text.selectWord(selectidWord);
        for (int i = 0; i < text.words.length; i++) {
            if (i != selectidWord) {
                TextView textView = new TextView(getActivity());
                textView.setText(text.words[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(25);
                flexboxLayout.addView(textView);
            } else {
                selectTextView = new TextView(getActivity());
                selectTextView.setText("_______");
                selectTextView.setGravity(Gravity.CENTER);
                selectTextView.setTextSize(25);
                selectTextView.setBackground(getActivity().getDrawable(R.drawable.border_word));
                selectTextView.setId(R.id.word);
                flexboxLayout.addView(selectTextView);
            }
        }

        words.add(text.selectedWord);
        words.add(text.words[text.randomWord()]);
        words.add(text.words[text.randomWord()]);
        Collections.shuffle(words);
    }

    View.OnDragListener dragListener = new View.OnDragListener() {
        boolean word_correct = false;
        @Override
        public boolean onDrag(View v, DragEvent event) {
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
                    if (!word_textview.getText().toString().equals("_______")) {
                        words.add(word_textview.getText().toString());
                    }
                    if (word_correct){
                        words.remove(word_entered_text);
                        words_recycler.setAdapter(adapter);
                    }else{
                        entered_word.setVisibility(View.VISIBLE);
                    }
                    word_correct = false;
                    if (word_textview.getText().toString().equals(text.selectedWord))
                        selectword();
                    break;
            }
            return true;
        }
    };
}