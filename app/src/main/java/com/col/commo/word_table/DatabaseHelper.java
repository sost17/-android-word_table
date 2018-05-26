package com.col.commo.word_table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by commo on 2017/4/30.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "word_database.db";
    public static final String TABLE_NAME_USER = "users";
    public static final String COLUMN_USER_USER ="user";
    public static final String COLUMN_PWD_USER = "password";

    public static final String TABLE_NAME_WD = "word_table";
    public static final String COLUMN_WORD_WD ="word";
    public static final String COLUMN_MEAN_WD = "mean";
    public static final String COLUMN_USER_WD = "user";

    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase dbs){

       String sql_user = "CREATE TABLE " +TABLE_NAME_USER
                +"(" +COLUMN_USER_USER+
                " VARCHAR, " +COLUMN_PWD_USER+
                " VARCHAR);";
        String sql_word = "CREATE TABLE " +TABLE_NAME_WD
                +"(" +COLUMN_WORD_WD+
                " VARCHAR, " +COLUMN_MEAN_WD+
                " VARCHAR, " +COLUMN_USER_WD+
                " VARCHAR);";
        dbs.execSQL(sql_user);
        dbs.execSQL(sql_word);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade method");
        String sql_user = "DROP TABLE IF EXISTS users";
        db.execSQL(sql_user);
        onCreate(db);
        String sql_word = "DROP TABLE IF EXISTS word_table";
        db.execSQL(sql_word);
        onCreate(db);
    }

    public void onOpen(SQLiteDatabase db){
        System.out.println("onOpen method");
        super.onOpen(db);
    }

}
