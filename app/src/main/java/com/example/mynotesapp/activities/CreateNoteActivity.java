package com.example.mynotesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.loader.content.AsyncTaskLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotesapp.R;
import com.example.mynotesapp.database.NotesDatabase;
import com.example.mynotesapp.entities.Note;
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
                if(inputNoteTitle.getText().toString().trim().isEmpty()){
                    closeKeyboard();
                    Snackbar.make(newNoteLayout,"Title Cannot be Empty",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                else if(inputNoteSubtitle.getText().toString().trim().isEmpty() && inputNoteText.getText().toString().trim().isEmpty()){
                    closeKeyboard();
                    Snackbar.make(newNoteLayout,"Contents Cannot be Empty",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                final Note note = new Note();
                note.setTitle(inputNoteTitle.getText().toString());
                note.setSubtitle(inputNoteSubtitle.getText().toString());
                note.setNoteText(inputNoteText.getText().toString());
                note.setDateTime(textDateTime.getText().toString());

                @SuppressLint("StaticFieldLeak")
                class SaveNoteTask extends AsyncTask<Void, Void, Void> {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note );
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void unused) {
                        super.onPostExecute(unused);
                        Intent intent = new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
                new SaveNoteTask().execute();
                Snackbar.make(newNoteLayout,"Saved Note",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}