package com.example.easypoem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class search_activity extends AppCompatActivity implements search_output_item_adapter.OnNoteListener{
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
//                states.clear();
//                setInitialData();
//                recyclerView.setAdapter(adapter);
                firebase_poems_search();
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
//                states.clear();
//                setInitialData();
//                recyclerView.setAdapter(adapter);
                firebase_poems_search();
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
      Query query = FirebaseDatabase.getInstance().getReference("poems").orderByChild("title").equalTo("Шашки");
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
    System.out.print(query);
    search_output_item_adapter adapter = new search_output_item_adapter(search_activity.this, states, this);
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (states.size()>0)states.clear();
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        search_output data = ds.getValue(search_output.class);
                        states.add(data);
                    }
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.print("3kljg;lkdj");
            }
        };
        query.addListenerForSingleValueEvent(vListener);

    }

    private void setInitialData(){

        states.add(new search_output("Россия в 1918 году", "Михаил Зенкевич"));
        states.add(new search_output ("Еще одно забывчивое слово", "Афанасий Фет"));
        states.add(new search_output ("Афиши", "Николай Доризо"));
        System.out.println(states);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, PoemRead.class);
        intent.putExtra("title", states.get(position).getTitle());
        intent.putExtra("text", states.get(position).getTitle());
        startActivity(intent);
    }
}