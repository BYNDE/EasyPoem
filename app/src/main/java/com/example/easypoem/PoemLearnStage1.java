package com.example.easypoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easypoem.learn.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PoemLearnStage1 extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<String> movelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_learn_stage1);
        getSupportActionBar().hide();

        TextView T_title = findViewById(R.id.TV_title);
        T_title.setText(getIntent().getExtras().getString("title"));

        findViewById(R.id.BTN_back).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Text text = new Text(getIntent().getExtras().getString("text"));
        movelist = new ArrayList<>();
        movelist.addAll(Arrays.asList(text.lines));
//        Collections.shuffle(movelist); Debug

        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(movelist);
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        findViewById(R.id.BTN_check).setOnClickListener(v -> {
            if (recyclerAdapter.moviesList.equals(Arrays.asList(text.lines))) {
                Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PoemLearnStage2.class);
                intent.putExtra("text", getIntent().getExtras().getString("text"));
                intent.putExtra("title", getIntent().getExtras().getString("title"));
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START  | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            if (movelist.get(viewHolder.getAdapterPosition()).equals(" "))
                return false;

            if (movelist.get(target.getAdapterPosition()).equals(" "))
                return false;


            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(movelist, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}