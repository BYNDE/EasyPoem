package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.easypoem.HttpClient.HttpClient;
import com.example.easypoem.HttpClient.PoemModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class search_activity extends AppCompatActivity implements search_output_item_adapter.OnNoteListener{
    ArrayList<PoemModel> states = new ArrayList<PoemModel>();
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);

//        HttpClient.getApi().Search("Привет").enqueue(new Callback<List<PoemModel>>() {
//            @Override
//            public void onResponse(Call<List<PoemModel>> call, Response<List<PoemModel>> response) {
//                Log.println(Log.ERROR, "HTTPCLIENT", response.body().get(0).getTitle());
//            }
//
//            @Override
//            public void onFailure(Call<List<PoemModel>> call, Throwable t) {
//                Log.println(Log.ERROR, "HTTPCLIENT", t.getMessage());
//            }
//        });

        getSupportActionBar().hide();

        search_output_item_adapter adapter = new search_output_item_adapter(search_activity.this, states, search_activity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(adapter);


        SearchView searchView = findViewById(R.id.search_view);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor (Color.TRANSPARENT);
        }
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        int searchPlateId2 = appBarLayout.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate2 = searchView.findViewById(searchPlateId2);
        if (searchPlate!=null) {
            searchPlate2.setBackgroundColor (Color.TRANSPARENT);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String newText) {
                search(newText, adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                search(newText,adapter);
                return true;
            }
        });


        searchView.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager =
                        (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        searchView.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
                searchView.requestFocus();
            }
        });


        findViewById(R.id.imageButton_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void back(){
        search_activity.super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void search(String newText,search_output_item_adapter adapter){
        firebase_poems_search(newText, adapter);
    }

    private void server_poems_search(String str, search_output_item_adapter adapter) {
        states.clear();

        HttpClient.getApi().Search(str).enqueue(new Callback<List<PoemModel>>() {
            @Override
            public void onResponse(Call<List<PoemModel>> call, Response<List<PoemModel>> response) {
                states.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PoemModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void firebase_poems_search(String str, search_output_item_adapter adapter){
        states.clear();
        // Obtain the FirebaseAnalytics instance.

        if (str.length() >= 3) {
            Query query = FirebaseDatabase.getInstance().getReference("poems").orderByChild("title").startAt(str).endAt(str +"\uf8ff").limitToFirst(5);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        PoemModel value = ds.getValue(PoemModel.class);
                        states.add(value);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    throw  error.toException();
                }
            });
        }
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
}