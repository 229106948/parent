package com.school.bus.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.school.bus.cn.data.Notice;
import com.school.bus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxingpei on 2017/3/28.
 */

public class NoticeAdapter extends BaseAdapter {
    List<Notice> noticeList=new ArrayList<>();
    Context mContext;
    LayoutInflater layoutInflater;


    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList.clear();
        this.noticeList.addAll(noticeList);
        notifyDataSetChanged();
    }

    public NoticeAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return noticeList.size();
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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.main_list_item,null);
            holder.title=(TextView) convertView.findViewById(R.id.title_tv);
            holder.time=(TextView) convertView.findViewById(R.id.time_tv);
            holder.content=(TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.title.setText(noticeList.get(position).getTitle());
        holder.time.setText(noticeList.get(position).getTime());
        holder.content.setText(noticeList.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView time;
        TextView content;
    }
}
