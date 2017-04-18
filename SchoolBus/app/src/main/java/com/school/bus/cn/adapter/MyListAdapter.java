package com.school.bus.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.bus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */

public class MyListAdapter extends BaseAdapter {
    List<String> myContents = new ArrayList<>();
    List<Integer> myImgs = new ArrayList<>();
    LayoutInflater layoutInflater;

    public void setMyContents(List<String> myContents,List<Integer> myImgs) {
        this.myContents.clear();
        this.myImgs.clear();
        this.myContents.addAll(myContents);
        this.myImgs.addAll(myImgs);
        notifyDataSetChanged();
    }

    public MyListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return myContents.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.my_list_item, null);
            viewHolder.mmyText = (TextView) convertView.findViewById(R.id.my_content);
            viewHolder.myImg = (ImageView) convertView.findViewById(R.id.my_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mmyText.setText(myContents.get(position));
        viewHolder.myImg.setBackgroundResource(myImgs.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView myImg;
        TextView mmyText;
    }
}
