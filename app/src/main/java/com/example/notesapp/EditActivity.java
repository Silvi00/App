package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditActivity extends AppCompatActivity {
    EditText noteTitle;
    EditText noteText;
    Calendar c;
    String todaysDate;
    String currentTime;
    Note note;
    NoteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        note = (Note) i.getSerializableExtra("note");
        db = new NoteDatabase(this);

        getSupportActionBar().setTitle(note.getTitle());
        noteTitle = findViewById(R.id.noteTitle);
        noteText = findViewById(R.id.noteText);
        noteTitle.setText(note.getTitle());
        noteText.setText(note.getContent());

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
        if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }
        if (item.getItemId() == R.id.save) {
            if (noteTitle.getText().length() != 0) {

                note.setTitle(noteTitle.getText().toString());
                note.setContent(noteText.getText().toString());
                note.setTime(currentTime);
                note.setDate(todaysDate);

                int id = db.editNote(note);

                if (id == 1)
                {
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update Error", Toast.LENGTH_SHORT).show();

                }
                Intent i = new Intent(getApplicationContext(), NoteDetails.class);
                i.putExtra("note", note);
                finish();
                startActivity(i);


            } else noteTitle.setError("Title can't be blank.");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
