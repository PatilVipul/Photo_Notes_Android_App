package com.example.vipul.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by Vipul on 05-02-2015.
 */
public class TableData extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "VipulDatabase";
    public static final String TABLE_NAME = "TestNotesTable";
    public static final String ID_COLUMN = "id";
    public static final String CAPTION_COLUMN = "caption";
    public static final String FILE_PATH_COLUMN = "filepath";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAPTION_COLUMN+" VARCHAR(255), "+FILE_PATH_COLUMN+" VARCHAR(255));";
    public static final  String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public TableData(Context context)
    {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

}