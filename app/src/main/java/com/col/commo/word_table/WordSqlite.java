package com.col.commo.word_table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by commo on 2017/5/1.
 */

public class WordSqlite {

    private SQLiteOpenHelper DBO;
    private SQLiteDatabase db;
    private Context mcontext;
    private String tableName;

    public WordSqlite(Context context,String tableName){
        this.mcontext = context;
        DBO = new DatabaseHelper(context);
        this.tableName = tableName;

    }

    public long insert (String word,String mean,String user){
        long i = -1;
        db = DBO.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("word",word);
            values.put("mean",mean);
            values.put("user",user);
            i = db.insert(tableName,null,values);
            db.close();
        }
        return i;
    }

    public List<Word> list_word(String user){
        db = DBO.getReadableDatabase();
        List<Word> words_list = null;

        if(db.isOpen()){
            words_list = new ArrayList<>();
            Cursor cursor = db.query(tableName,null,"user=?",new String[]{user},null,null,null);
            while(cursor.moveToNext()){
                Word  words = new Word();
                int word_Index = cursor.getColumnIndex("word");
                int mean_Index = cursor.getColumnIndex("mean");
                String Str_word = cursor.getString(word_Index);
                String Str_mean = cursor.getString(mean_Index);
                words.setWord(Str_word);
                words.setMean(Str_mean);
                words_list.add(words);
            }
            cursor.close();
            db.close();
        }
        return words_list;
    }

    public boolean delete(String user,String word){
        db = DBO.getReadableDatabase();

        if(db.isOpen()){
            db.delete(tableName,"user=?"+"and word=?",new String[]{user,word});
            db.close();
            return true;
        }

        return false;
    }

    public int update(String user,String word ,String mean){
        db = DBO.getReadableDatabase();
        int i = -1;
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("mean",mean);
            i = db.update(tableName,values,"user=? and word=?",new String[]{user,word});
            db.close();
        }
        return i;
    }
}
