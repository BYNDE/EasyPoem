package com.example.easypoem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;


    public MyDbManager(Context context){
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }

    public void openDb(){
        db = myDbHelper.getWritableDatabase();
    }
    public void insertToDb(String title,String author,String text){
        ContentValues cv = new ContentValues();
        cv.put(MyConstant.TITLE,title);
        cv.put(MyConstant.AUTHOR,author);
        cv.put(MyConstant.TEXT,text);

        db.insert(MyConstant.TABLE_NAME,null,cv);
    }
    public String[][] getFromDb(){
        Cursor cursor = db.query(MyConstant.TABLE_NAME,null,null,null,null,
        null, null);
        String [][] mass = new String[cursor.getCount()][3];

        int a = 0;
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(MyConstant.TITLE));
            String author = cursor.getString(cursor.getColumnIndex(MyConstant.AUTHOR));
            String text = cursor.getString(cursor.getColumnIndex(MyConstant.TEXT));
            mass[a][0] = title;
            mass[a][1] = author;
            mass[a][2] = text;
            a++;
        }
        cursor.close();
        return mass;
    }
    public void closeDb(){
        myDbHelper.close();
    }
}
