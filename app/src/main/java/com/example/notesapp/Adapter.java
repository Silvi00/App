package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    LayoutInflater inflater;
    List<Note> notes;
    ItemClickListener listener;

    Adapter(Context context,List<Note> notes,ItemClickListener listener){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_list_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(position, listener);
    }

    @Override
    public int getItemCount() {

        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle,nDate,nTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.nTitle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);
        }

        public void bind(int position,ItemClickListener listener){
            String title = notes.get(position).getTitle();
            String date = notes.get(position).getDate();
            String time = notes.get(position).getTime();
            this.nTitle.setText(title);
            this.nDate.setText(date);
            this.nTime.setText(time);
            itemView.setOnClickListener(v -> listener.onClick(v,position));
        }
    }



}
