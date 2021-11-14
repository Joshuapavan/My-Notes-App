package com.example.mynotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynotesapp.R;
import com.example.mynotesapp.database.NotesDatabase;
import com.example.mynotesapp.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {


    CoordinatorLayout newNoteLayout;

    ImageView backButton,saveButton;
    EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    TextView textDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initialise();

        backClick(backButton);
        saveClick(saveButton);
        setDate(textDateTime);
    }

    private void setDate(TextView textDateTime) {
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd  MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date())
        );

        initialiseToolsMenu();
    }

    void initialise(){
        newNoteLayout = findViewById(R.id.newNoteLayout);
        backButton = findViewById(R.id.imageBack);
        saveButton = findViewById(R.id.imageSave);
        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
    }
    void backClick(ImageView backButton){
        backButton.setOnClickListener(v->{
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
        });
    }

    void saveClick(ImageView saveButton){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNoteTitle.getText().toString().trim().isEmpty()) {
                    Snackbar.make(newNoteLayout, "Title Cannot be Empty", Snackbar.LENGTH_SHORT).show();
                } else if (inputNoteSubtitle.getText().toString().trim().isEmpty() || inputNoteText.getText().toString().trim().isEmpty()) {
                    Snackbar.make(newNoteLayout, "Contents Cannot be Empty", Snackbar.LENGTH_SHORT).show();
                } else {

                    final Note note = new Note();
                    note.setTitle(inputNoteTitle.getText().toString());
                    note.setSubtitle(inputNoteSubtitle.getText().toString());
                    note.setNoteText(inputNoteText.getText().toString());
                    note.setDateTime(textDateTime.getText().toString());

                    @SuppressLint("StaticFieldLeak")
                    class SaveNoteTask extends AsyncTask<Void, Void, Void> {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute(unused);
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                    new SaveNoteTask().execute();
                }
            }
        });
    }
    private void initialiseToolsMenu(){
        final LinearLayout layoutTools = findViewById(R.id.layoutTools);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutTools);
        layoutTools.findViewById(R.id.layoutTools).setOnClickListener(v ->{
            if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }else{
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}