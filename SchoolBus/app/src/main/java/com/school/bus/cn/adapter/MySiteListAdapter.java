package com.school.bus.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.bus.R;
import com.school.bus.cn.data.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class MySiteListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Site> sites=new ArrayList<>();

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites.clear();
        this.sites.addAll(sites);
        notifyDataSetChanged();
    }

    public MySiteListAdapter(Context context) {
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sites.size();
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
            convertView = layoutInflater.inflate(R.layout.site_list_item, null);
            viewHolder.siteName = (TextView) convertView.findViewById(R.id.site_name);
            viewHolder.stayTime = (TextView) convertView.findViewById(R.id.stay_time);
            viewHolder.aboardTime = (TextView) convertView.findViewById(R.id.aboard_time);
            viewHolder.debusTime = (TextView) convertView.findViewById(R.id.debus_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.siteName.setText(sites.get(position).getName());
        viewHolder.stayTime.setText(sites.get(position).getStayTime()+"min");
        viewHolder.aboardTime.setText(sites.get(position).getAboardTime());
        viewHolder.debusTime.setText(sites.get(position).getDebusTime());
        return convertView;
    }
    class ViewHolder {
        TextView siteName;
        TextView stayTime;
        TextView aboardTime;
        TextView debusTime;
    }
}
