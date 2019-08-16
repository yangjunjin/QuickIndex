package com.example.quickindex;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quickindexdemo.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * author : yangjunjin
 * date : 2019/8/17 0:11
 */
public class MyAdapter extends BaseAdapter {
    private ArrayList<Friend> friends;

    public MyAdapter(ArrayList<Friend> friends) {
        this.friends = friends;
        Collections.sort(this.friends);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.adapter_list, null);
            holder = new ViewHolder();
            holder.tv_first = (TextView) convertView.findViewById(R.id.tv_first);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String firstChar = friends.get(position).pinyin.charAt(0) + "";
        if (position > 0) {
            String lastChar = friends.get(position - 1).pinyin.charAt(0) + "";
            if (firstChar.equalsIgnoreCase(lastChar)) {
                holder.tv_first.setVisibility(View.GONE);
            } else {
                holder.tv_first.setVisibility(View.VISIBLE);
                holder.tv_first.setText(firstChar);
            }
        } else {
            holder.tv_first.setVisibility(View.VISIBLE);
            holder.tv_first.setText(firstChar);
        }


        holder.tv_name.setText(friends.get(position).getName());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        private TextView tv_first;
        private TextView tv_name;

    }
}
