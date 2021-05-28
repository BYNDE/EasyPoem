package com.example.easypoem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class word_item_adapter extends RecyclerView.Adapter<word_item_adapter.ViewHolder> {
    List<String> words;
    private final LayoutInflater inflater;
    OnLongClickItem onLongClickItem;



    public word_item_adapter(Context context, List<String> words, OnLongClickItem onLongClickItem){
        this.words = words;
        this.inflater = LayoutInflater.from(context);
        this.onLongClickItem = onLongClickItem;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView word;
        OnLongClickItem onLongClickItem;

        public ViewHolder(@NonNull View itemView, OnLongClickItem onLongClickItem) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            itemView.setOnLongClickListener(this);
            this.onLongClickItem = onLongClickItem;
        }

        @Override
        public boolean onLongClick(View v) {
            onLongClickItem.onLongClickItem(itemView,word.getText().toString());
            return true;
        }
    }
    @NonNull
    @Override
    public word_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_item, parent, false);
        return new word_item_adapter.ViewHolder(view, onLongClickItem);
    }

    @Override
    public void onBindViewHolder(@NonNull word_item_adapter.ViewHolder holder, int position) {
        holder.word.setText( words.get(position));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public interface OnLongClickItem{
        void onLongClickItem(View view,String text);
    }
}
