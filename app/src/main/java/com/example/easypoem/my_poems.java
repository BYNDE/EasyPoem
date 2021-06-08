package com.example.easypoem;

import androidx.lifecycle.ViewModelProvider;

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

    private MyPoemsViewModel mViewModel;
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
                startActivity(intent);
            }
        });


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),add_my_poem.class);
                startActivity(intent);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyPoemsViewModel.class);
        // TODO: Use the ViewModel
    }

//    private void onAddButtonClicked(FloatingActionButton button_add,FloatingActionButton button_new_folder,FloatingActionButton button_new_poem) {
//        setVisibility(clicked,button_new_folder,button_new_poem);
//        setAnimation(clicked,button_add,button_new_folder,button_new_poem);
//        setClickable(clicked,button_new_folder,button_new_poem);
//        clicked = !clicked;
//    }

//    private void setVisibility(boolean clicked,FloatingActionButton button_new_folder,FloatingActionButton button_new_poem) {
//        if(!clicked){
//            button_new_folder.setVisibility(View.VISIBLE);
//            button_new_poem.setVisibility(View.VISIBLE);
//
//        }else{
//            button_new_folder.setVisibility(View.INVISIBLE);
//            button_new_poem.setVisibility(View.INVISIBLE);
//        }
//    }

//    private void setAnimation(boolean clicked,FloatingActionButton button_add,FloatingActionButton button_new_folder,FloatingActionButton button_new_poem) {
//
//        Animation rotate_open = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_open_anim);
//        Animation rotate_close = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_close_anim);
//        Animation to_bottom = AnimationUtils.loadAnimation(getActivity(),R.anim.to_bottom_anim);
//        Animation from_bottom = AnimationUtils.loadAnimation(getActivity(),R.anim.from_bottom_anim);
//        if(!clicked){
//            button_new_folder.startAnimation(from_bottom);
//            button_new_poem.startAnimation(from_bottom);
//            button_add.startAnimation(rotate_open);
//        }else{
//            button_new_folder.startAnimation(to_bottom);
//            button_new_poem.startAnimation(to_bottom);
//            button_add.startAnimation(rotate_close);
//        }
//    }
//
//    private void setClickable(boolean clicked,FloatingActionButton button_new_folder,FloatingActionButton button_new_poem){
//
//        if(!clicked) {
//            button_new_folder.setClickable(true);
//            button_new_poem.setClickable(true);
//        }else{
//            button_new_folder.setClickable(false);
//            button_new_poem.setClickable(false);
//        }
//    }

}