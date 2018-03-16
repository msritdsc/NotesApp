package com.dsc.adf.notesapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mNotesListView;
    private ArrayList<NotesModel> mNotesList;
    private NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton fab = findViewById(R.id.fab);
        mNotesListView = findViewById(R.id.notes_list);
        TextView noText = findViewById(R.id.no_notes_text);

        fab.setOnClickListener(this);

        mNotesList = readFromFile();
        mAdapter = new NotesAdapter(this, mNotesList);
        if (mNotesList.size() != 0){
            Log.d("HomeAc",String.valueOf(mNotesList.size()));
            noText.setVisibility(View.GONE);
            mNotesListView.setAdapter(mAdapter);
            mNotesListView.setDividerHeight(1);
            mAdapter.notifyDataSetChanged();
        }else{
            noText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Activity Lifecycle method
     * This is called when the activity is stopped i.e u go to a different activity and come back
     */
    @Override
    protected void onStart() {
        super.onStart();
        mNotesList = readFromFile();
        Log.d("onStart",String.valueOf(mNotesList.size()));
        mAdapter.refreshNotesList(mNotesList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNotesList = readFromFile();
        Log.d("onStart",String.valueOf(mNotesList.size()));
        mAdapter.refreshNotesList(mNotesList);
    }

    /**
     * To avoid going back from HomeActivity to LoginActivity
     */
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab){
            startActivity(new Intent(HomeActivity.this, AddNotesActivity.class));
        }
    }

    /**
     * Returns a list of notes from the file saved in your device
     * @return ArrayList<NotesModel> i.e a list of notes
     */
    private ArrayList<NotesModel> readFromFile() {

        Gson gson = new Gson();
        ArrayList<NotesModel> noteList = new ArrayList<>();

        String filename = "notes.txt";
        try {
            File file = new File(getApplicationContext().getFilesDir(), filename);
            String line;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                NotesModel note = gson.fromJson(line, NotesModel.class);
                noteList.add(note);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteList;
    }

    private class NotesAdapter extends ArrayAdapter<NotesModel>{
        private ArrayList<NotesModel> mNotesList;

        NotesAdapter(Context context, ArrayList<NotesModel> notesList){
            super(context, 0 , notesList);
            mNotesList = notesList;
        }

        void refreshNotesList(ArrayList<NotesModel> notesList){
            this.mNotesList.clear();
            this.mNotesList.addAll(notesList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            NotesModel notes = mNotesList.get(position);

            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_item, parent,false);

            TextView title = convertView.findViewById(R.id.title);
            TextView desc  = convertView.findViewById(R.id.desc);

            title.setText(notes.getTitle());
            desc.setText(notes.getDesc());

            return convertView;
        }
    }
}
