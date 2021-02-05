package com.koko.thetodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TASKS = "TASKS";

    public DataBaseHelper(@Nullable Context context) {
        super(context,"task.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable;
        createtable= "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TASKS + " TEXT)";
        db.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addingtask(tasks taskobj){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASKS, taskobj.getTask());

        long insert = db.insert(TASK_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deletetask(tasks taskobj){

        SQLiteDatabase db=this.getWritableDatabase();
        String deletequery="DELETE FROM "+TASK_TABLE+" WHERE "+COLUMN_ID+" = "+taskobj.getId();
        Cursor cursor = db.rawQuery(deletequery, null);

        if(cursor.moveToFirst())
        {
            return true;
        }
        else{
            return false;
        }
    }

    public List<tasks> geteverytask() {
        List<tasks> returnList = new ArrayList<>();
        String querystring = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querystring, null);
        if (cursor.moveToFirst()) {
            do {
                int taskid = cursor.getInt(0);
                String thetask = cursor.getString(1);
                tasks newtask = new tasks(taskid, thetask);
                returnList.add(newtask);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

}
