package com.school.bus.cn;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.school.bus.R;
import com.school.bus.cn.adapter.MySiteListAdapter;
import com.school.bus.cn.data.Line;
import com.school.bus.cn.data.LineShow;
import com.school.bus.cn.data.Site;
import com.school.bus.cn.data.Student;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class MyLineActivity extends Activity {
    List<LineShow> lineShows;
    LinearLayout rootLayout;
    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;
    MySiteListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_child);
        getArgment();
        initView();
    }

    private void initView() {
        initTitle();
        rootLayout = (LinearLayout) findViewById(R.id.child_layout);
        if (lineShows != null) {
            adapter = new MySiteListAdapter(this);
            for (int i = 0; i < lineShows.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.line_layout, null);
                View v = LayoutInflater.from(this).inflate(R.layout.site_list_item, null);
                TextView lineName = (TextView) view.findViewById(R.id.line_name);
                ListView siteLayout = (ListView) view.findViewById(R.id.site_list);
                lineName.setText(lineShows.get(i).getId() + "号线");
                List<Site> sites = lineShows.get(i).getAllSite();
                siteLayout.addHeaderView(v);
                siteLayout.setAdapter(adapter);
                adapter.setSites(sites);
                rootLayout.addView(view);
            }
        }
    }

    private void initTitle() {
        title = (TextView) findViewById(R.id.title_content);
        back = (ImageView) findViewById(R.id.back);
        add = (ImageView) findViewById(R.id.add);
        message = (ImageView) findViewById(R.id.message);
        title.setText("我关注的线路");
        back.setVisibility(View.VISIBLE);
        add.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getArgment() {
        lineShows = (List<LineShow>) getIntent().getSerializableExtra("line");
    }

}
