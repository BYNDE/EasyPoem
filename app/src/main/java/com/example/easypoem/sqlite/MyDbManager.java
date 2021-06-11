package com.example.easypoem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void deleteFromDb(String title,String author){
        db.delete(MyConstant.TABLE_NAME,"title = ? and author = ?",new String[] {title,author});
    }

    public void openDb(){
        db = myDbHelper.getWritableDatabase();
    }
    public void insertToDb(String title,String author,String text,int added_or_created){
        ContentValues cv = new ContentValues();
        cv.put(MyConstant.TITLE,title);
        cv.put(MyConstant.AUTHOR,author);
        cv.put(MyConstant.TEXT,text);
        cv.put(MyConstant.ADDED_OR_CREATED,added_or_created);
        cv.put(MyConstant.LEARNED,0);

        db.insert(MyConstant.TABLE_NAME,null,cv);
    }
    public String[][] getFromDb(){
        Cursor cursor = db.query(MyConstant.TABLE_NAME,null,null,null,null,
        null, null);
        String [][] mass = new String[cursor.getCount()][4];

        int a = 0;
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(MyConstant.TITLE));
            String author = cursor.getString(cursor.getColumnIndex(MyConstant.AUTHOR));
            String text = cursor.getString(cursor.getColumnIndex(MyConstant.TEXT));
            mass[a][0] = title;
            mass[a][1] = author;
            mass[a][2] = text;
            mass[a][3] = "3";

            a++;
        }
        cursor.close();
        return mass;
    }

    public String[][] getAddedOrCreatedFromDb(int added_or_created){
            Cursor cursor = db.query(MyConstant.TABLE_NAME,null,"added_or_created = ?",new String[] {Integer.toHexString(added_or_created)},null,
                    null, null);
            String [][] mass = new String[cursor.getCount()][4];

            int a = 0;
            while(cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex(MyConstant.TITLE));
                String author = cursor.getString(cursor.getColumnIndex(MyConstant.AUTHOR));
                String text = cursor.getString(cursor.getColumnIndex(MyConstant.TEXT));
                mass[a][0] = title;
                mass[a][1] = author;
                mass[a][2] = text;
                mass[a][3] = "3";

                a++;
            }
            cursor.close();
            return mass;
        }

    public String[][] getLearnedFromDb(){
        Cursor cursor = db.query(MyConstant.TABLE_NAME,null,"learned = ?",new String[] {"1"},null,
                null, null);
        String [][] mass = new String[cursor.getCount()][4];

        int a = 0;
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(MyConstant.TITLE));
            String author = cursor.getString(cursor.getColumnIndex(MyConstant.AUTHOR));
            String text = cursor.getString(cursor.getColumnIndex(MyConstant.TEXT));
            mass[a][0] = title;
            mass[a][1] = author;
            mass[a][2] = text;
            mass[a][3] = "3";

            a++;
        }
        cursor.close();
        return mass;
    }

    public String[] getLast(){
        String[] mass = new String[4];
        Cursor cursor = db.query(MyConstant.TABLE_NAME, null, null, null, null, null, MyConstant._ID +" DESC", "1");

        if (cursor.getCount() == 0) {
            cursor.close();
            return  null;
        }
        else {
            while(cursor.moveToNext()){
                String title = cursor.getString(cursor.getColumnIndex(MyConstant.TITLE));
                String author = cursor.getString(cursor.getColumnIndex(MyConstant.AUTHOR));
                String text = cursor.getString(cursor.getColumnIndex(MyConstant.TEXT));
                mass[0] = title;
                mass[1] = author;
                mass[2] = text;
                mass[3] = "3";
            }
            cursor.close();
            return mass;
        }
    }

    public boolean check_availability(String title,String author){
        Cursor cursor = db.query(MyConstant.TABLE_NAME,null,"title = ? and author = ?",new String[] {title,author},null,
                null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return  false;
        }
        else {
            cursor.close();
            return true;
        }
    }
    public void closeDb(){
        myDbHelper.close();
    }
}
