package com.example.vipul.photonotes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vipul on 04-02-2015.
 */
public class CustomListActivity extends ArrayAdapter<String>{

    private final Activity context;
    ArrayList<String> notes = new ArrayList<String>();
    private TextView txtTitle;

    public CustomListActivity(Activity context, ArrayList notes) {
        super(context, R.layout.custom_single, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_single, null, true);
        txtTitle = (TextView) rowView.findViewById(R.id.singleTextView);
        txtTitle.setText(notes.get(position));
        return rowView;
    }
}