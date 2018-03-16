package com.dsc.adf.notesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mTitle, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        mTitle = findViewById(R.id.title);
        mDescription = findViewById(R.id.description);
        Button submit = findViewById(R.id.submit_button);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.submit_button){
            String title = mTitle.getEditableText().toString();
            String desc  = mDescription.getEditableText().toString();

            if (!title.isEmpty() && !desc.isEmpty()){
                writeToFile(title,desc);
                finish();
            }
            else{
                Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeToFile(String title, String desc) {
        Log.d("AddNotes","Entered here");
        Gson gson = new Gson();
        NotesModel note = new NotesModel(title, desc);
        String jsonNote = gson.toJson(note);

        String filename = "notes.txt";
        try {
            File file = new File(getApplicationContext().getFilesDir(), filename);
            FileWriter fw = new FileWriter(file, true);
            fw.write(jsonNote + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
