package com.example.easypoem.sqlite;

public class MyConstant {
    public static final String TABLE_NAME = "users_poems";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String TEXT = "text";
    public static final String ADDED_OR_CREATED = "added_or_created";
    public static final String LEARNED = "learned";
    public static final String PARAGRAPHS_NUMBER = "paragraphs_number";
    public static final String CURRENT_PARAGRAPH = "current_paragraph";
    public static final String CURRENT_LEVEL= "current_level";
    public static final String DB_NAME = "users_poems.db";
    public static final int VERSION = 1;

    public static  final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "+
            TABLE_NAME+" ("+_ID+" INTEGER PRIMARY KEY,"+TITLE+" TEXT,"+AUTHOR+" TEXT,"+
            TEXT+" TEXT," + ADDED_OR_CREATED+" INTEGER," + LEARNED+" INTEGER," + PARAGRAPHS_NUMBER + " INTEGER,"
            + CURRENT_PARAGRAPH + " INTEGER," + CURRENT_LEVEL + " INTEGER)";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS "+TABLE_NAME;

}
