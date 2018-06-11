package com.example.irmin.userapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class eventAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<eventItem> data;
    private int layout;

    public eventAdapter(Context context, int layout, ArrayList<eventItem> data){
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getTitle2();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }
        eventItem item = data.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.title2);
        title.setText(item.getTitle2());

        TextView content = (TextView) convertView.findViewById(R.id.content2);
        content.setText(item.getContent2());

        TextView amount = (TextView) convertView.findViewById(R.id.amount2);
        amount.setText(item.getAmount2());

        TextView cate = (TextView) convertView.findViewById(R.id.category2);
        cate.setText(item.getCategory2());

        TextView tel = (TextView) convertView.findViewById(R.id.tel2);
        tel.setText(item.getTel2());

        TextView add = (TextView) convertView.findViewById(R.id.add2);
        add.setText(item.getAdd2());

        return convertView;
    }
}
