package com.example.easypoem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easypoem.HttpClient.AuthorModel;
import com.example.easypoem.HttpClient.PoemModel;

import java.util.ArrayList;
import java.util.List;

public class search_output_item_adapter_any extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LayoutInflater inflater;
    private List<Object> items;
    private static final int ITEM_TYPE_POEM = 0;
    private static final int ITEM_TYPE_AUTHOR = 1;
    private OnNoteListener onNoteListener;

    search_output_item_adapter_any(Context context, List<Object> items, OnNoteListener onNoteListener) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
        this.onNoteListener = onNoteListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_POEM) {
            View view = inflater.inflate(R.layout.search_output_item, parent, false);
            return new PoemViewHolder(view, onNoteListener);
        } else {
            View view = inflater.inflate(R.layout.search_output_item_author, parent, false);
            return new AuthorViewHolder(view, onNoteListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);

        if (holder instanceof PoemViewHolder) {
            PoemModel poem = (PoemModel) item;
            ((PoemViewHolder) holder).titleView.setText(poem.getTitle());
            ((PoemViewHolder) holder).authorView.setText(poem.getAuthor());
        } else if (holder instanceof AuthorViewHolder) {
            AuthorModel author = (AuthorModel) item;
            ((AuthorViewHolder) holder).nameView.setText(author.getName());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PoemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView titleView, authorView;

        OnNoteListener onNoteListener;

        PoemViewHolder(View view, OnNoteListener onNoteListener){
            super(view);
            titleView = (TextView) view.findViewById(R.id.title);
            authorView = (TextView) view.findViewById(R.id.author);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameView;

        OnNoteListener onNoteListener;

        AuthorViewHolder(View view, OnNoteListener onNoteListener){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);

            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof PoemModel) {
            return ITEM_TYPE_POEM;
        } else {
            return ITEM_TYPE_AUTHOR;
        }
    }
}
