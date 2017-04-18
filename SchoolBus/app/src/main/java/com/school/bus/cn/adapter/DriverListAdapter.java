package com.school.bus.cn.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.school.bus.R;
import com.school.bus.cn.data.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class DriverListAdapter extends BaseAdapter {
    List<Driver> drivers=new ArrayList<>();
    LayoutInflater layoutInflater;
    Context mContext;

    public DriverListAdapter(Context context) {
        mContext=context;
        layoutInflater =LayoutInflater.from(context);
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers.clear();
        this.drivers.addAll(drivers);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return drivers.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.driver_list, null);
            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
            viewHolder.telephone= (TextView) convertView.findViewById(R.id.telephone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(drivers.get(position).getName());
        viewHolder.telephone.setText(drivers.get(position).getTelephone());
        viewHolder.telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(drivers.get(position).getTelephone()));
                try {
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
    class ViewHolder {
        TextView name;
        TextView telephone;
    }
}
