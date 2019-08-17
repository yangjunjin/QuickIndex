package com.example.quickindexdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quickindex.Friend;
import com.example.quickindex.MyAdapter;
import com.example.quickindex.QuickView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ListView listView;
    private QuickView quickBar;
    private TextView tvShow;

    private ArrayList<Friend> friends = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        prepareData();
        listView.setAdapter(new MyAdapter(friends));
        quickBar.setData(tvShow, friends, new QuickView.OnSelectListener() {
            @Override
            public void select(int position) {
//                listView.setSelection(position);
                listView.smoothScrollToPositionFromTop(position, 0, 100);//滑动到position  距离top的偏移量  滑动所用的时间
            }
        });
    }

    private void init() {
        listView = (ListView) findViewById(R.id.lv_view);
        quickBar = (QuickView) findViewById(R.id.quick_bar);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }

    /**
     * 准备数据
     */
    private void prepareData() {
        // 虚拟数据
        friends.add(new Friend("6李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
        friends.add(new Friend("3李伟3"));
    }
}