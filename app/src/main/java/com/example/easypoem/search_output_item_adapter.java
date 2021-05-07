package com.example.easypoem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class search_output_item_adapter  extends RecyclerView.Adapter<search_output_item_adapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<search_output> states;
    private OnNoteListener onNoteListener;

    search_output_item_adapter(Context context, List<search_output> states, OnNoteListener onNoteListener) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
        this.onNoteListener = onNoteListener;
    }
    @Override
    public search_output_item_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_output_item, parent, false);
        return new ViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(search_output_item_adapter.ViewHolder holder, int position) {
        search_output state = states.get(position);
        holder.titleView.setText(state.getTitle());
        holder.authorView.setText(state.getAuthor());

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView titleView, authorView;

        OnNoteListener onNoteListener;

        ViewHolder(View view, OnNoteListener onNoteListener){
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

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
