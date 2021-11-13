package com.example.mynotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mynotesapp.R;
import com.example.mynotesapp.adapters.NotesAdapter;
import com.example.mynotesapp.database.NotesDatabase;
import com.example.mynotesapp.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView newNote,addNote,addImage,addLink;

    RecyclerView notesRecyclerView;
    List<Note> noteList;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
        newNoteClick(newNote);
        recyclerView();
    }



    void initialise(){
        newNote = findViewById(R.id.imageAddNoteMain);
        addNote = findViewById(R.id.imageAddNote);
        addImage = findViewById(R.id.imageAddImage);
        addLink = findViewById(R.id.imageAddLink);

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
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

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if(noteList.size() == 0){
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                }
                else{
                    noteList.add(0,notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                }
                notesRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }

    void recyclerView(){
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);
        notesRecyclerView.setAdapter(notesAdapter);

        getNotes();
    }
}