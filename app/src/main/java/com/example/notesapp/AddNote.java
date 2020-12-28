package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {


    EditText noteTitle;
    EditText noteText;
    Calendar c;
    String todaysDate;
    String currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setTitle("New Note");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        noteTitle = findViewById(R.id.noteTitle);
        noteText = findViewById(R.id.noteText);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  if(s.length() != 0)
                  {
                      getSupportActionBar().setTitle(s);
                  }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
        currentTime = pad(c.get(Calendar.HOUR_OF_DAY))+":"+pad(c.get(Calendar.MINUTE));

    }

    private String pad(int i) {
        if(i<10)
            return "0"+i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete)
        {
            Toast.makeText(this,"Note Deleted",Toast.LENGTH_SHORT).show();
            finish();
            onBackPressed();
        }
        if(item.getItemId() == R.id.save)
        {
            String PREFS_SETTINGS = "prefs_settings";
            SharedPreferences prefUser = getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE);
            Note note = new Note(noteTitle.getText().toString(),noteText.getText().toString(),todaysDate,currentTime,prefUser.getInt("ID_USER",0));
            NoteDatabase db = new NoteDatabase(this);
            db.addNote(note);
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show();
            finish();
            goToHome();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToHome() {
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}