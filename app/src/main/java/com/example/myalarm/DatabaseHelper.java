package com.example.myalarm;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MyAlarm.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "table1";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "label";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";
    private static final String COLUMN_ACTIVE =  "active";

    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_HOUR + " INTEGER, " +
                COLUMN_MINUTE + " INTEGER, " +
                COLUMN_ACTIVE + " BOOLEAN);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addData(String title, int hour, int minute, boolean active){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE,title);
        contentValues.put(COLUMN_HOUR,hour);
        contentValues.put(COLUMN_MINUTE,minute);
        contentValues.put(COLUMN_ACTIVE,active);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            Toast.makeText(context, "Failed to upload", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Uploaded successfully", Toast.LENGTH_SHORT).show();
        }

    }
}
