package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easypoem.sqlite.MyDbManager;

import java.util.ArrayList;

public class see_all_my_poems extends AppCompatActivity implements search_output_item_adapter.OnNoteListener, View.OnClickListener {
    private MyDbManager myDbManager = new MyDbManager(this);
    ArrayList<search_output> states = new ArrayList<search_output>();
    private Button button_all,button_added,button_created,button_learned,current_button;
    private RecyclerView recyclerView;
    private search_output_item_adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_my_poems);
        getSupportActionBar().hide();

        button_all = findViewById(R.id.button_all);
        button_created = findViewById(R.id.button_created);
        button_added = findViewById(R.id.button_added);
        button_learned = findViewById(R.id.button_learned);

        current_button = button_all;
        recyclerView = (RecyclerView) findViewById(R.id.list);

        button_all.setOnClickListener(this);
        button_created.setOnClickListener(this);
        button_added.setOnClickListener(this);
        button_learned.setOnClickListener(this);

        myDbManager.openDb();
        String [][] mass = myDbManager.getFromDb();
        if (mass.length>0){
            for (int i = 0; i < mass.length; i++) {
                states.add(new search_output(mass[i][0], mass[i][1], mass[i][2], Long.valueOf(mass[i][3])));
            }
        }
        myDbManager.closeDb();

        adapter = new search_output_item_adapter(see_all_my_poems.this, states, this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, PoemRead.class);
        intent.putExtra("title", states.get(position).title);
        intent.putExtra("text", states.get(position).text);
        intent.putExtra("author", states.get(position).author);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        states.clear();
        String [][] mass;
        myDbManager.openDb();
        switch (v.getId()) {
            case R.id.button_all:
                current_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray_hint));
                button_all.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                current_button = button_all;
                mass = myDbManager.getFromDb();
                if (mass.length>0){
                    for (int i = 0; i < mass.length; i++) {
                        states.add(new search_output(mass[i][0], mass[i][1], mass[i][2], Long.valueOf(mass[i][3])));
                    }
                }
            break;

            case R.id.button_added:
                current_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray_hint));
                button_added.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                current_button = button_added;
                mass = myDbManager.getAddedOrCreatedFromDb(0);
                if (mass.length>0){
                    for (int i = 0; i < mass.length; i++) {
                        states.add(new search_output(mass[i][0], mass[i][1], mass[i][2], Long.valueOf(mass[i][3])));
                    }
                }
            break;

            case R.id.button_created:
                current_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray_hint));
                button_created.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                current_button = button_created;
                mass = myDbManager.getAddedOrCreatedFromDb(1);
                if (mass.length>0){
                    for (int i = 0; i < mass.length; i++) {
                        states.add(new search_output(mass[i][0], mass[i][1], mass[i][2], Long.valueOf(mass[i][3])));
                    }
                }
            break;

            case R.id.button_learned:
                current_button.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray_hint));
                button_learned.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                current_button = button_learned;
                mass = myDbManager.getLearnedFromDb();
                if (mass.length>0){
                    for (int i = 0; i < mass.length; i++) {
                        states.add(new search_output(mass[i][0], mass[i][1], mass[i][2], Long.valueOf(mass[i][3])));
                    }
                }
            break;
        }
        adapter.notifyDataSetChanged();
        myDbManager.closeDb();
    }

}