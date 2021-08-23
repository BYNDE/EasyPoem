package com.example.easypoem;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easypoem.sqlite.MyDbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class my_poems extends Fragment {

    private boolean clicked = false;
    private MyDbManager myDbManager;
    private String [] last_poem_mass;
    private FloatingActionButton button_add;
    private Button see_all_poems_button;
    private TextView last_poem_title_tv,last_poem_author_tv;
    private LinearLayout add_poem_layout,last_poem_layout;

    public static my_poems newInstance() {
        return new my_poems();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.my_poems_fragment, container, false);
        check_added();


        button_add = view.findViewById(R.id.fab_add);
        see_all_poems_button = view.findViewById(R.id.see_all_poems);
        last_poem_author_tv = view.findViewById(R.id.last_author);
        last_poem_title_tv = view.findViewById(R.id.last_poem);
        add_poem_layout = view.findViewById(R.id.add_poem_layout);
        last_poem_layout = view.findViewById(R.id.last_poem_layout);

        myDbManager = new MyDbManager(getActivity());
        myDbManager.openDb();
        if (myDbManager.getLast() != null){
            last_poem_mass = myDbManager.getLast();
            setLastPoem();
        }
        myDbManager.closeDb();



        see_all_poems_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),see_all_my_poems.class);
                Activity activity = (Activity) getContext();
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),add_my_poem.class);
                Activity activity = (Activity) getContext();
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });

        last_poem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PoemRead.class);
                intent.putExtra("title", last_poem_mass[0]);
                intent.putExtra("author", last_poem_mass[1]);
                intent.putExtra("text", last_poem_mass[2]);
                Activity activity = (Activity) getContext();
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });

        return view;
    }

    private void setLastPoem() {
        last_poem_title_tv.setText(last_poem_mass[0]);
        last_poem_author_tv.setText(last_poem_mass[1]);
        add_poem_layout.setVisibility(View.INVISIBLE);
        last_poem_layout.setVisibility(View.VISIBLE);
    }

    private void check_added() {

    }
}