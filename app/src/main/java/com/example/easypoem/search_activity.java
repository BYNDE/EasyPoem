package com.example.easypoem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class search_activity extends AppCompatActivity {
    ArrayList<search_output> states = new ArrayList<search_output>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);

        getSupportActionBar().hide();

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        search_output_item_adapter adapter = new search_output_item_adapter(search_activity.this, states);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                states.clear();
                setInitialData();
                recyclerView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                states.clear();
                setInitialData();
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("poems");
        
    }

    private void setInitialData(){

        states.add(new search_output("Россия в 1918 году", "Михаил Зенкевич"));
        states.add(new search_output ("Еще одно забывчивое слово", "Афанасий Фет"));
        states.add(new search_output ("Афиши", "Николай Доризо"));

    }
}