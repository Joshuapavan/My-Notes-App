package com.example.mynotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mynotesapp.R;
import com.example.mynotesapp.database.NotesDatabase;
import com.example.mynotesapp.entities.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView newNote,addNote,addImage,addLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
        newNoteClick(newNote);
    }

    void initialise(){
        newNote = findViewById(R.id.imageAddNoteMain);
        addNote = findViewById(R.id.imageAddNote);
        addImage = findViewById(R.id.imageAddImage);
        addLink = findViewById(R.id.imageAddLink);
    }

    void newNoteClick(ImageView newNote){
        newNote.setOnClickListener(v -> {
            Intent newNoteIntent = new Intent(getApplicationContext(), CreateNoteActivity.class);
            startActivity(newNoteIntent);
        });
    }

    private void getNotes(){
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void,Void, List<Note>>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
            }
        }
        new GetNotesTask().execute();
    }
}