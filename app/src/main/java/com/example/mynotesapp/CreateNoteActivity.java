package com.example.mynotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateNoteActivity extends AppCompatActivity {

    ImageView backButton,saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initialise();

        backClick(backButton);
        saveClick(saveButton);
    }
    void initialise(){
        backButton = findViewById(R.id.imageBack);
        saveButton = findViewById(R.id.imageSave);
    }
    void backClick(ImageView backButton){
        backButton.setOnClickListener(v->{
            Intent backIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(backIntent);
        });
    }

    void saveClick(ImageView saveButton){
        saveButton.setOnClickListener(v->{
            Toast.makeText(getApplicationContext(),"Saved Note", Toast.LENGTH_SHORT).show();
        });
    }
}