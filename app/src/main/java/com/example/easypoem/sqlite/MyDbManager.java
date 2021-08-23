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
    public void insertToDb(String title,String author,String text,int added_or_created, int paragraphs_number){
        ContentValues cv = new ContentValues();
        cv.put(MyConstant.TITLE,title);
        cv.put(MyConstant.AUTHOR,author);
        cv.put(MyConstant.TEXT,text);
        cv.put(MyConstant.ADDED_OR_CREATED,added_or_created);
        cv.put(MyConstant.LEARNED,0);
        cv.put(MyConstant.PARAGRAPHS_NUMBER,paragraphs_number);
        cv.put(MyConstant.CURRENT_PARAGRAPH,0);
        cv.put(MyConstant.CURRENT_LEVEL,0);

        db.insert(MyConstant.TABLE_NAME,null,cv);
    }
    public void update_level(String title, String author, int current_level, int current_paragraph,int paragraphs_number){
        ContentValues newValues = new ContentValues();
        if (current_level == 2 ){
            newValues.put(MyConstant.CURRENT_LEVEL, 0);
            if(paragraphs_number == current_paragraph + 1) {
                newValues.put(MyConstant.LEARNED, 1);
            }
            newValues.put(MyConstant.CURRENT_PARAGRAPH, current_paragraph + 1);
        }else{
            newValues.put(MyConstant.CURRENT_LEVEL, current_level + 1);
        }

        db.update(MyConstant.TABLE_NAME, newValues, "title = '"+title+"' and author = '"+author+"'", null);
    }

    public void reset (String title,String author){
        ContentValues newValues = new ContentValues();
        newValues.put(MyConstant.CURRENT_LEVEL, 0);
        newValues.put(MyConstant.CURRENT_PARAGRAPH, 0);
        newValues.put(MyConstant.LEARNED, 0);
        db.update(MyConstant.TABLE_NAME, newValues, "title = '"+title+"' and author = '"+author+"'", null);
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

    public int[] getParagraph_and_level(String title,String author){
        int mass[] = new int[3];
        Cursor cursor = db.query(MyConstant.TABLE_NAME,null,"title = ? and author = ?",new String[] {title,author},null,
                null, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            return  null;
        } else {
            while(cursor.moveToNext()){
                int current_level = cursor.getInt(cursor.getColumnIndex(MyConstant.CURRENT_LEVEL));
                int current_paragraph = cursor.getInt(cursor.getColumnIndex(MyConstant.CURRENT_PARAGRAPH));
                int paragraphs_number = cursor.getInt(cursor.getColumnIndex(MyConstant.PARAGRAPHS_NUMBER));
                mass[0] = current_level;
                mass[1] = current_paragraph;
                mass[2] = paragraphs_number;
            }
            cursor.close();
            return mass;
        }
    }
    public void closeDb(){
        myDbHelper.close();
    }
}
