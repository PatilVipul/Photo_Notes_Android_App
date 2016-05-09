package com.example.vipul.photonotes;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPhotoActivity extends ActionBarActivity{

    private  EditText caption_EditText;
    private ImageButton photo_Button;
    private Button save_Button;
//    String caption;
    String filepath;
    private  File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        caption_EditText = (EditText) findViewById(R.id.captionEditText);
        photo_Button = (ImageButton) findViewById(R.id.photoButton);
        save_Button = (Button) findViewById(R.id.saveButton);
    }

    public void ClickPhotoMethod(View view)
    {
        Intent clickPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (clickPictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                clickPictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(clickPictureIntent, 5);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        filepath = image.getAbsolutePath();
        return image;
    }

    public void SaveMethod(View view)
    {
        String caption = caption_EditText.getText().toString();
        insertData(caption,filepath);
        finish();
    }

    public void insertData(String caption, String filepath)
    {
        TableData object = new TableData(this);
        SQLiteDatabase db = object.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.CAPTION_COLUMN,caption);
        contentValues.put(TableData.FILE_PATH_COLUMN,filepath);
        long id = db.insert(TableData.TABLE_NAME,null,contentValues);
        if(id<0)
        {
            Toast.makeText(this,"Not successful",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Successfully inserted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uninstall)
        {
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
            uninstallIntent.setData(Uri.parse("package:" + "com.example.vipul.photonotes"));
            startActivity(uninstallIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
