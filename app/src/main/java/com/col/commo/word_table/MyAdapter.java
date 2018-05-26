package com.col.commo.word_table;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by commo on 2017/4/30.
 */

public class MyAdapter extends BaseAdapter{
    private ArrayList<HashMap<String, Object>> data;

    private LayoutInflater layoutInflater;
    private Context context;


    public MyAdapter(Context context,ArrayList<HashMap<String, Object>> data) {

        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }


    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        zujian zuJian = null;
        final int selectID = position;
        if (convertView == null){
            zuJian = new zujian();

            convertView = layoutInflater.inflate(R.layout.simple_word,null);
            zuJian.word_view = (TextView) convertView.findViewById(R.id.word);
            zuJian.mean_view = (TextView) convertView.findViewById(R.id.word_mean);

            convertView.setTag(zuJian);
        }else {
            zuJian = (zujian) convertView.getTag();
        }
        zuJian.word_view.setText((CharSequence) data.get(position).get("word"));
        zuJian.mean_view.setText((CharSequence) data.get(position).get("mean"));
//        zuJian.word_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showInfo(position);
//            }
//        });

        return convertView;
    }

    public void showInfo(int clickID){

        new AlertDialog.Builder(context)

                .setTitle("列车信息")

                .setMessage("暂无"+ data.get(clickID).get("word").toString()+"列车信息")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }

                })

                .show();

    }
}
