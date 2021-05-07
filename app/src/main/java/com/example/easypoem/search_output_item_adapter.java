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

    search_output_item_adapter(Context context, List<search_output> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public search_output_item_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_output_item, parent, false);
        return new ViewHolder(view);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView, authorView;
        ViewHolder(View view){
            super(view);
            titleView = (TextView) view.findViewById(R.id.title);
            authorView = (TextView) view.findViewById(R.id.author);
        }
    }
}
