package com.dsc.adf.notesapp;

/**
 * Created by HemantJ on 14/03/18.
 */

public class NotesModel {
    private String title,desc;

    public NotesModel(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
