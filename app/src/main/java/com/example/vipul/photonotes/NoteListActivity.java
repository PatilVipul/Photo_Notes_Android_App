package com.example.vipul.photonotes;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.makeText;


public class NoteListActivity extends ActionBarActivity {

    ListView photoNotes_ListView;
    ArrayList<String> result = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        SelectFromTable();
        photoNotes_ListView = (ListView) findViewById(R.id.PhotoNotesListView);
        CustomListActivity notesAdapter;
        notesAdapter = new CustomListActivity(NoteListActivity.this, result);
        photoNotes_ListView.setAdapter(notesAdapter);


        photoNotes_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewPhotoIntent = new Intent(NoteListActivity.this,ViewPhotoActivity.class);
                viewPhotoIntent.putExtra("value1",position);
                startActivity(viewPhotoIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        SelectFromTable();
        photoNotes_ListView = (ListView) findViewById(R.id.PhotoNotesListView);
        CustomListActivity notesAdapter;
        notesAdapter = new CustomListActivity(NoteListActivity.this, result);
        photoNotes_ListView.setAdapter(notesAdapter);
        photoNotes_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewPhotoIntent = new Intent(NoteListActivity.this,ViewPhotoActivity.class);
                viewPhotoIntent.putExtra("value1",position);
                startActivity(viewPhotoIntent);
            }
        });
        super.onResume();
    }

    private void SelectFromTable()
    {
        SQLiteDatabase db = new TableData(this).getWritableDatabase();
        String[] columns = {TableData.CAPTION_COLUMN, TableData.FILE_PATH_COLUMN};
        Cursor cursor = db.query(TableData.TABLE_NAME, columns, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            String column1 = cursor.getString(0);
            if(!result.contains(column1))
                result.add(column1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.addButton)
        {
            //Toast.makeText(this,"Launching AddPhotoActivity....",Toast.LENGTH_SHORT).show();
            Intent addButtonIntent = new Intent(NoteListActivity.this,AddPhotoActivity.class);
            startActivity(addButtonIntent);
        }

        if (id == R.id.uninstall)
        {
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
            uninstallIntent.setData(Uri.parse("package:" + "com.example.vipul.photonotes"));
            startActivity(uninstallIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}