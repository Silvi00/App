package com.example.notesapp;

public class Note {
    private long ID;
    private String title;
    private String content;
    private String date;
    private String time;
    private int user_id;

    Note(){}

    Note(String title, String content, String date, String time,int user_id) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.user_id = user_id;
    }

    Note(long id, String title, String content, String date, String time,int user_id){
        this.ID = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.user_id = user_id;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUser_id(){
        return user_id;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

}
