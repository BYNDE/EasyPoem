package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.easypoem.sqlite.MyDbManager;

import java.util.ArrayList;

public class see_all_my_poems extends AppCompatActivity implements search_output_item_adapter.OnNoteListener {
    private MyDbManager myDbManager;
    ArrayList<search_output> states = new ArrayList<search_output>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_my_poems);
        getSupportActionBar().hide();

        myDbManager = new MyDbManager(this);
        myDbManager.openDb();
        String [][] mass = myDbManager.getFromDb();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        String[] mass_text =new String[mass.length];
        if (mass.length>0){
            for (int i = 0; i < mass.length; i++) {
                states.add(new search_output(mass[i][0], mass[i][2], mass[i][1], Long.valueOf(mass[i][3])));
            }
        }

        search_output_item_adapter adapter = new search_output_item_adapter(see_all_my_poems.this, states, this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, PoemRead.class);
        intent.putExtra("title", states.get(position).getTitle());
        intent.putExtra("text", states.get(position).getText());
        startActivity(intent);
    }
}