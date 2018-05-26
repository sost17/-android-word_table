package com.col.commo.word_table;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by commo on 2017/4/30.
 */

public class List_Word_Activity extends AppCompatActivity {

    private TextView word,mean;
    private ListView list_word;
    private ImageView add_view;
    private WordSqlite  sql_word;
    MyAdapter adapter;
//    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> map_word_list;
    HashMap<String, Object> map;
    private static List<Word> words_list = new ArrayList<>();
    String Str_user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worde_list);

        word = (TextView) findViewById(R.id.word_view);
        mean = (TextView) findViewById(R.id.word_mean_view);
        list_word = (ListView) findViewById(R.id.wordlist_view);
        add_view = (ImageView) findViewById(R.id.add_word_view);

        Intent getintent = getIntent();
        Str_user = getintent.getStringExtra("user");

        sql_word = new WordSqlite(this,"word_table");

        add_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.add_word_layout,null);
                final EditText word = (EditText) view.findViewById(R.id.editText);
                final EditText mean = (EditText) view.findViewById(R.id.editText2);
                new AlertDialog.Builder(List_Word_Activity.this)
                        .setTitle("添加生词")
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String Str_word = null;
                                String Str_mean = null;

                                Str_word = word.getText().toString();
                                Str_mean = mean.getText().toString();
                                if(Str_word.equals("") || Str_word.equals("")){
                                    Toast.makeText(List_Word_Activity.this,"单词或释义不能为空",Toast.LENGTH_LONG).show();
                                }else {

                                    if(getlist_word(Str_user,Str_word) == 1){
                                        Toast.makeText(List_Word_Activity.this,"单词已存在",Toast.LENGTH_LONG).show();
                                    }else {
                                        sql_word.insert(Str_word,Str_mean,Str_user);
                                        Toast.makeText(List_Word_Activity.this,"插入成功",Toast.LENGTH_LONG).show();
                                        getaddWord(Str_user);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .create()
                        .show();
            }
        });

        list_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delInfo(position);
            }
        });

        list_word.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                updateInfo(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        map_word_list = new ArrayList<HashMap<String,Object>>();

        getWordInformation(Str_user);

        adapter = new MyAdapter(this,map_word_list);

//        adapter = new SimpleAdapter(List_Word_Activity.this,map_word_list,R.layout.simple_word,
//                new String[]{"word","mean"},new int[]{R.id.word,R.id.word_mean});

        list_word.setAdapter(adapter);
    }

    private int getlist_word(String user,String word){
        words_list = sql_word.list_word(user);
        int j = 0;

        for (int i = 0 ; i < words_list.size() ; i ++ ){
            if ((words_list.get(i).getWord()).equals(word) == true){
                j = 1;
                break;
            }
        }
        return j;
    }

    private void getWordInformation(String user){
        words_list = sql_word.list_word(user);

        for (int i = 0 ; i < words_list.size() ; i ++ ){
            map = new HashMap<String ,Object>();
            map.put("word",words_list.get(i).getWord());
            map.put("mean",words_list.get(i).getMean());
            map_word_list.add(map);
        }
    }

    private void getaddWord(String user){
        words_list = sql_word.list_word(user);

        for (int i = 0 ; i < words_list.size() ; i ++ ){
            if ( i == words_list.size() - 1){
                map = new HashMap<String ,Object>();
                map.put("word",words_list.get(i).getWord());
                map.put("mean",words_list.get(i).getMean());
                map_word_list.add(map);
            }
        }
    }

    public void delInfo(final int clickID){
        final String del_word = map_word_list.get(clickID).get("word").toString();

        new android.app.AlertDialog.Builder(this)

                .setTitle("删除单词")

                .setMessage("确认删除"+ del_word+"单词信息")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        boolean del = sql_word.delete(Str_user,del_word);

                        if(del == true){
                            map_word_list.remove(clickID);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(List_Word_Activity.this, del_word+"单词删除成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(List_Word_Activity.this, del_word+"单词删除失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                })
                .show();
    }

    public void updateInfo(final int clickID){
        final String old_word = map_word_list.get(clickID).get("word").toString();
        final String old_mean = map_word_list.get(clickID).get("mean").toString();

        LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.add_word_layout,null);
        final EditText up_word = (EditText) view.findViewById(R.id.editText);
        final EditText up_mean = (EditText) view.findViewById(R.id.editText2);
        up_word.setText(old_word);
        up_mean.setText(old_mean);
        new AlertDialog.Builder(List_Word_Activity.this)
                .setTitle("更改单词")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String Str_word = null;
                        String Str_mean = null;

                        Str_word = up_word.getText().toString();
                        Str_mean = up_mean.getText().toString();
                        if(Str_word.equals("") || Str_word.equals("")){
                            Toast.makeText(List_Word_Activity.this,"单词或释义不能为空",Toast.LENGTH_LONG).show();
                        }else {
                            if(sql_word.update(Str_user,Str_word,Str_mean) != -1 ){
                                map_word_list.get(clickID).put("word",Str_word);
                                map_word_list.get(clickID).put("mean",Str_mean);
                                Toast.makeText(List_Word_Activity.this,Str_word+"更改成功",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(List_Word_Activity.this,Str_word+"更改失败",Toast.LENGTH_LONG).show();
                            }
//                            if(getlist_word(Str_user,Str_word) == 1){
//                                Toast.makeText(List_Word_Activity.this,"单词已存在",Toast.LENGTH_LONG).show();
//                            }else {
//
//
//                            }
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();

    }


}
