package com.example.vipul.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class ViewPhotoActivity extends ActionBarActivity {

    private TextView caption_TextView;
    private ImageView photo_ImageView;
    String caption, file;
    int value1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        caption_TextView = (TextView) findViewById(R.id.captionTextView);
        photo_ImageView = (ImageView) findViewById(R.id.photoImageView);
        value1 = getIntent().getExtras().getInt("value1");

        SelectFromTable();
        Log.d("CAPTION2: ", caption);
        Log.d("FILE2: ",file);
        caption_TextView.setText(caption);
        File imageFile = new File(file);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;  // Experiment with different sizes
        Bitmap b = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        photo_ImageView.setImageBitmap(b);
        Log.d("logs","At the end of onCreate Method");
    }

    private void SelectFromTable()
    {
        TableData object = new TableData(this);
        SQLiteDatabase db = object.getWritableDatabase();
        String[] columns = {TableData.ID_COLUMN, TableData.CAPTION_COLUMN, TableData.FILE_PATH_COLUMN};
        Cursor cursor = db.query(TableData.TABLE_NAME, columns, null, null, null ,null ,null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(TableData.ID_COLUMN));
            if(value1+1==id){
                caption = cursor.getString(1);
                file = cursor.getString(2);
                Log.d("CAPTION1: ", caption);
                Log.d("FILE1: ",file);
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uninstall) {
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
            uninstallIntent.setData(Uri.parse("package:" + "com.example.vipul.photonotes"));
            startActivity(uninstallIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
