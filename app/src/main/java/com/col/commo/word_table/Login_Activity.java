package com.col.commo.word_table;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by commo on 2017/4/30.
 */

public class Login_Activity extends Activity{

    private Button login,registered;
    private EditText user,passwd;
    private UsersSQLite  sql_user;
    private static List<Users> users_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.login);
        registered = (Button) findViewById(R.id.reg);
        user = (EditText) findViewById(R.id.user);
        passwd = (EditText) findViewById(R.id.passwd);

        sql_user = new UsersSQLite(this,"users");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select() == 1){
                    String Str_user = user.getText().toString();
                    Word word = new Word();
                    word.setUser(Str_user);

                    Intent intent = new Intent();
                    intent.setClass(Login_Activity.this,List_Word_Activity.class);
                    intent.putExtra("user",Str_user);
                    startActivity(intent);
                }

            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login_Activity.this,Sign_in_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private int  select(){
        String Str_user = user.getText().toString();
        String Str_pwd = passwd.getText().toString();
        if(Str_user.equals("") || Str_pwd.equals("")){
            Toast.makeText(Login_Activity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
            return 0;
        }else if(getUserInformation(Str_user) == 1 ){
            if(sql_user.query_pwd(Str_user).equals(Str_pwd) == true){
                Toast.makeText(Login_Activity.this,"登录成功",Toast.LENGTH_LONG).show();
                return 1;
            }else{
                Toast.makeText(Login_Activity.this,"密码错误",Toast.LENGTH_LONG).show();
                return 0;
            }

        }else if(getUserInformation(Str_user) == 0 ){
            Toast.makeText(Login_Activity.this,"用户不存在",Toast.LENGTH_LONG).show();
            return 0;
        }

        return 0;
    }

    private int getUserInformation(String user){
        users_list = sql_user.queryAll();
        int j = 0 ;

        for (int i = 0 ; i < users_list.size() ; i ++ ){
            if ((users_list.get(i).getUser()).equals(user) == true){
                j = 1;
                break;
            }
        }
        return j;
    }


}
