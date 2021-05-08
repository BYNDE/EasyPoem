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

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class search_activity extends AppCompatActivity implements search_output_item_adapter.OnNoteListener{
    ArrayList<search_output> states = new ArrayList<search_output>();
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);

        getSupportActionBar().hide();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        search_output_item_adapter adapter = new search_output_item_adapter(search_activity.this, states, this);

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
            public boolean onQueryTextSubmit(final String query) {
                states.clear();
                firebase_poems_search();
                recyclerView.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                states.clear();
                firebase_poems_search();
                recyclerView.setAdapter(adapter);

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
        Intent intent = new Intent(search_activity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down, 0);
    }

    private void firebase_poems_search(){
        // Obtain the FirebaseAnalytics instance.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for (int i = 0; i < 300; i++) {
            DatabaseReference myRef = database.getReference("poems/" + i);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    search_output value = dataSnapshot.getValue(search_output.class);
                    states.add(value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("fff", "Failed to read value.", error.toException());
                }
            });
        }
    }

    private void setInitialData(){
//
//        states.add(new search_output("Россия в 1918 году", "Михаил Зенкевич"));
//        states.add(new search_output ("Еще одно забывчивое слово", "Афанасий Фет"));
//        states.add(new search_output ("Афиши", "Николай Доризо"));
//        System.out.println(states);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, PoemRead.class);
        intent.putExtra("title", states.get(position).getTitle());
        intent.putExtra("text", states.get(position).getText());
        startActivity(intent);
    }
}