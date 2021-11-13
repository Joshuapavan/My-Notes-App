package com.example.mynotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mynotesapp.R;

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
}