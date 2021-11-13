package com.example.mynotesapp.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo; 
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {

    //Primary Key//
    @PrimaryKey(autoGenerate = true)
    private int id;

    //To store Title
    @ColumnInfo(name = "title")
    private String title;

    //entity to store Date and time
    @ColumnInfo(name = "date_time")
    private String dateTime;

    //to store subtitle
    @ColumnInfo(name = "subtitle")
    private String subtitle;

    //to Store note text
    @ColumnInfo(name = "note_text")
    private String noteText;

    //to store weblink
    @ColumnInfo(name = "web_link")
    private String webLink;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @NonNull
    @Override
    public String  toString() {
        return title + " : " + dateTime;
    }
}
