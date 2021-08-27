package com.example.easypoem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easypoem.learn.Paragraph;
import com.example.easypoem.learn.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class fragment_mixed_strings extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Paragraph text;
    List<String> movelist;
    String string_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mixed_strings, container, false);

        string_text = this.getArguments().getString("text");
        text = new Paragraph(string_text);

        movelist = new ArrayList<>();
        movelist.addAll(Arrays.asList(text.lines));
        Collections.shuffle(movelist);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(movelist);
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START  | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//
//            if (movelist.get(viewHolder.getAdapterPosition()).equals(" "))
//                return false;
//
//            if (movelist.get(target.getAdapterPosition()).equals(" "))
//                return false;


            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(movelist, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            if (movelist.equals(Arrays.asList(text.lines))) {
                poem_learn_main poem_learn_main= new poem_learn_main();
                poem_learn_main.getInstance().set_progress(100);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        poem_learn_main.getInstance().go_to_check_progress();
                    }
                }, 1000);

            }

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}