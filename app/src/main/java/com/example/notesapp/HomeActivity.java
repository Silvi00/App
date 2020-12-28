package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements ItemClickListener{
    private static final String PREFS_SETTINGS = "prefs_settings";
    //Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        SharedPreferences prefUser = getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE);
        NoteDatabase db = new NoteDatabase(this);
        notes = db.getNotes(prefUser.getInt("ID_USER",0));
        recyclerView = findViewById(R.id.listOfNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,notes,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add)
        {
            Intent i = new Intent(this,AddNote.class);
            startActivity(i);

            Toast.makeText(this,"Add button is Clicked",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        Intent i = new Intent(this,NoteDetails.class);
        i.putExtra("note",notes.get(position));
        startActivity(i);
        //Toast.makeText(this,"note: " + notes.get(position).getTitle(),Toast.LENGTH_SHORT).show();
    }
}