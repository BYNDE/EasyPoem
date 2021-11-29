package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.easypoem.HttpClient.HttpClient;
import com.example.easypoem.HttpClient.PoemModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class all_poems_in_author extends AppCompatActivity implements search_output_item_adapter.OnNoteListener, View.OnClickListener {
    ArrayList<PoemModel> states = new ArrayList<PoemModel>();
    private RecyclerView recyclerView;
    private search_output_item_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_poems_in_author);

        getSupportActionBar().hide();

        findViewById(R.id.BTN_back).setOnClickListener(v -> back());
        TextView name = findViewById(R.id.name);
        name.setText(getIntent().getExtras().getString("name"));


        search_output_item_adapter adapter = new search_output_item_adapter(this, states, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(adapter);

        HttpClient.getApi().GetAllAuthorPoems(getIntent().getExtras().getInt("id")).enqueue(new Callback<List<PoemModel>>() {
            @Override
            public void onResponse(Call<List<PoemModel>> call, Response<List<PoemModel>> response) {
                if (response.body() != null) {
                    states.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PoemModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, PoemRead.class);
        intent.putExtra("title", states.get(position).getTitle());
        intent.putExtra("text", states.get(position).getText());
        intent.putExtra("author", states.get(position).getAuthor());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    @Override
    public void onBackPressed() {
        back();
    }

    public void back(){
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {

    }
}