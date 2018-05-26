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

public class Sign_in_Activity extends Activity {

    private Button reg;
    private EditText user,passwd;
    private UsersSQLite  sql_user;
    private static List<Users> users_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        reg = (Button) findViewById(R.id.registered);
        user = (EditText) findViewById(R.id.user);
        passwd = (EditText) findViewById(R.id.passwd);

        sql_user = new UsersSQLite(this,"users");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add() == 2){
                    Intent intent = new Intent();
                    intent.setClass(Sign_in_Activity.this,Login_Activity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private int  add(){
        String Str_user = user.getText().toString();
        String Str_passwd = passwd.getText().toString();

        if(Str_user.equals("") || Str_passwd.equals("")){
            Toast.makeText(Sign_in_Activity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
            return 0;
        }else if(getUserInformation(Str_user) == 1){
            Toast.makeText(Sign_in_Activity.this,"用户已存在",Toast.LENGTH_LONG).show();
            return 1;
        }else if(getUserInformation(Str_user) == 0){
            sql_user.insert(Str_user,Str_passwd);
            Toast.makeText(Sign_in_Activity.this,"注册成功",Toast.LENGTH_LONG).show();
            return 2;
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
