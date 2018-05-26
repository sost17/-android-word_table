package com.col.commo.word_table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by commo on 2017/4/30.
 */

public class UsersSQLite {

    private SQLiteOpenHelper DBO;
    private SQLiteDatabase db;
    private Context mcontext;
    private String tableName;

    public UsersSQLite(Context context,String tableName){
        this.mcontext = context;
        DBO = new DatabaseHelper(context);
        this.tableName = tableName;

    }

    public long insert (String user,String password){
        long i = -1;
        db = DBO.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("user",user);
            values.put("password",password);
            i = db.insert(tableName,null,values);
            db.close();
        }
        return i;
    }

    public String query_pwd(String user){
        db = DBO.getReadableDatabase();
        String passwd = null;

        if(db.isOpen()){
            Cursor cursor = db.query(tableName,null,"user=?",new String[]{user},null,null,null);
            if(cursor.moveToFirst()){
                int user_Index = cursor.getColumnIndex("password");
                String Str_pwd = cursor.getString(user_Index);
                passwd = Str_pwd;
                cursor.close();
            }
            db.close();
        }
        return passwd;
    }

    public List<Users> queryAll(){
        db = DBO.getReadableDatabase();
        List<Users> users_list = null;

        if(db.isOpen()){
            users_list = new ArrayList<>();
            Cursor cursor = db.query(tableName,null,null,null,null,null,null);
            while(cursor.moveToNext()){
                Users  users = new Users();
                int user_Index = cursor.getColumnIndex("user");
                String Str_user = cursor.getString(user_Index);
                users.setUser(Str_user);
                users_list.add(users);
            }
            cursor.close();
            db.close();
        }
        return users_list;
    }

}
