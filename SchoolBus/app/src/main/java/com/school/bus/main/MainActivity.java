package com.school.bus.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.school.bus.R;
import com.school.bus.cn.data.Parent;
import com.school.bus.cn.data.Student;

import java.util.List;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost fragmentTabHost;
    String tabs[] = TabDb.getTabsTxt();
    int id[] = TabDb.getParentTabsImg();
    int idSelected[] = TabDb.getParentTabsImgSelected();
    String mTabId[] = {"1", "2", "3", "4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.content_layout);
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        fragmentTabHost.setOnTabChangedListener(this);
        initTab();
    }

    private void initTab() {
        for (int i = 0; i < tabs.length; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(mTabId[i]).setIndicator(getView(i));
            fragmentTabHost.addTab(tabSpec, TabDb.getFragments()[i], null);
            fragmentTabHost.setTag(i);
        }
    }

    private View getView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.main_footer, null);
        TextView tabContent = (TextView) view.findViewById(R.id.tab_content);
        ImageView tabImg = (ImageView) view.findViewById(R.id.tab_img);
        tabContent.setText(tabs[index]);
        if (index == 0) {
            tabImg.setImageResource(idSelected[index]);
        } else {
            tabImg.setImageResource(id[index]);
        }
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        updateTab(fragmentTabHost);
    }

    private void updateTab(FragmentTabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) view.findViewById(R.id.tab_content);
            ImageView img = (ImageView) view.findViewById(R.id.tab_img);
            if (tabHost.getCurrentTab() == i) {//选中
                tv.setTextColor(Color.BLACK);
                img.setImageResource(idSelected[i]);
            } else {//不选中
                tv.setTextColor(Color.GRAY);
                img.setImageResource(id[i]);
            }
        }
    }

}
