package com.example.myalarm;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
                COLUMN_ACTIVE + " INTEGER);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addData(String title, int hour, int minute, int active){

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

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }

        return cursor;
    }

    void updateData(String rowId,String title, int hour, int minute, int active){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE,title);
        contentValues.put(COLUMN_HOUR,hour);
        contentValues.put(COLUMN_MINUTE,minute);
        contentValues.put(COLUMN_ACTIVE,active);

        long result = db.update(TABLE_NAME,contentValues,"_id=?",new String[]{rowId});

        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        }

    }
}
