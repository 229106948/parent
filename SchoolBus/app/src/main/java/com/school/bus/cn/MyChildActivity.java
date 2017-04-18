package com.school.bus.cn;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.school.bus.R;
import com.school.bus.cn.data.Student;

import java.util.List;

/**
 * Created by zhangxingpei on 2017/4/7.
 */

public class MyChildActivity extends Activity {
    LinearLayout childLayout;
    List<Student> students;

    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_child);
        getArgment();
        initView();
    }
    private void initView(){
        initTitle();
        childLayout=(LinearLayout) findViewById(R.id.child_layout);
        if(students!=null&&students.size()>0){
            for(int i=0;i<students.size();i++){
                View view= LayoutInflater.from(this).inflate(R.layout.child_list_item,null);
                TextView studentId=(TextView) view.findViewById(R.id.student_id);
                TextView studentName=(TextView) view.findViewById(R.id.student_name);
                TextView studentGrade=(TextView) view.findViewById(R.id.student_grade);
                TextView studentClass=(TextView) view.findViewById(R.id.student_class);
                TextView studentLine=(TextView) view.findViewById(R.id.student_line);
                studentId.setText("学号："+students.get(i).getId());
                studentName.setText("姓名："+students.get(i).getName());
                studentGrade.setText("年级："+students.get(i).getGrade());
                studentClass.setText("班级："+students.get(i).getClasses());
                studentLine.setText("线路："+students.get(i).getLineId()+"号线");
                childLayout.addView(view);
                addLine();
            }
        }
    }
    private void addLine(){
        View v=new View(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.parseColor("#BFBFBF"));
        childLayout.addView(v);
    }
    private void initTitle() {
        title = (TextView) findViewById(R.id.title_content);
        back = (ImageView) findViewById(R.id.back);
        add = (ImageView) findViewById(R.id.add);
        message = (ImageView) findViewById(R.id.message);
        title.setText("我的孩子");
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
    private void getArgment(){
        students=(List<Student>) getIntent().getSerializableExtra("student");
    }
}
