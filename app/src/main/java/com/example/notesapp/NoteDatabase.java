package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "NoteDB";
    private static final String DATABASE_TABLE = "Notes";

    //Coloanele bazei de date
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_USERID = "userID";


    NoteDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER NOT NULL PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_CONTENT + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT," +
                KEY_USERID +" INTEGER" + ")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion >= newVersion )
            return;
        db.execSQL( "DROP TABLE IF EXISTS " + DATABASE_TABLE );
        onCreate(db);

    }
    public void addNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        c.put(KEY_USERID,note.getUser_id());

        int ID = (int) db.insert(DATABASE_TABLE,null,c);

        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,ID);

        db.update(DATABASE_TABLE, cv, KEY_DATE + " = ? AND " + KEY_TIME + " = ? AND " + KEY_USERID+ " = ?", new String[]{note.getDate(),note.getTime(), String.valueOf(note.getUser_id())});

        Log.i(this.getClass().getName(),"New Note id: " + ID);

    }

    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE,new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME,KEY_USERID},KEY_ID + "=?",
                        new String[]{String.valueOf(id)},null,null,null );
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(cursor.getInt(0),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5));
    }
    public List<Note> getNotes(int user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE userID=? " + "ORDER BY " + KEY_ID + " DESC";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(user_id)});
        if(cursor.moveToFirst())
            do {
                Note note = new Note();
                note.setID(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                note.setUser_id(cursor.getInt(5));
                
                allNotes.add(note);

            }while(cursor.moveToNext());
        return allNotes;
    }

    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        c.put(KEY_USERID,note.getUser_id());

        return db.update(DATABASE_TABLE,c,KEY_ID + "=?",new String[]{String.valueOf(note.getID())});
    }

    void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
}
