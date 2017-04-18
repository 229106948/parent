package com.school.bus.cn;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.R;
import com.school.bus.cn.data.Line;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnAddStudentListener;
import com.school.bus.cn.listener.OnLineResponseListener;
import com.school.bus.cn.listener.OnSiteResponseListener;
import com.school.bus.cn.listener.OnUpdateStudentListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class AddChildActivity extends Activity implements View.OnClickListener {
    EditText childName;
    EditText childGrade;
    EditText childClass;
    TextView lineTv;
    RelativeLayout lineLayout;
    TextView aboardSiteTv;
    RelativeLayout aboardSiteLayout;
    TextView debusSiteTv;
    RelativeLayout debusSiteLayout;
    TextView advanceSiteTv;
    RelativeLayout advanceSiteLayout;
    PopupWindow popupWindow;
    Button confirm;
    private int mParentId;
    private int lineId = -1;
    List<Line> lines;

    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;
    Student myChild;
    String function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_child);
        getArgment();
        initView();
    }

    private void initTitle(String function) {
        title = (TextView) findViewById(R.id.title_content);
        back = (ImageView) findViewById(R.id.back);
        add = (ImageView) findViewById(R.id.add);
        message = (ImageView) findViewById(R.id.message);
        title.setText(function);
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

    private void initView() {
        if(myChild==null){
            initTitle("添加孩子");
        }else {
            initTitle("更改孩子的信息");
        }
        childName = (EditText) findViewById(R.id.child_name);
        childGrade = (EditText) findViewById(R.id.child_grade);
        childClass = (EditText) findViewById(R.id.child_class);
        lineLayout = (RelativeLayout) findViewById(R.id.line_layout);
        lineTv = (TextView) findViewById(R.id.line_tv);
        aboardSiteLayout = (RelativeLayout) findViewById(R.id.aboardSite_layout);
        aboardSiteTv = (TextView) findViewById(R.id.aboardSite_tv);
        debusSiteLayout = (RelativeLayout) findViewById(R.id.debusSite_layout);
        debusSiteTv = (TextView) findViewById(R.id.debusSite_tv);
        advanceSiteLayout = (RelativeLayout) findViewById(R.id.advanceSite_layout);
        advanceSiteTv = (TextView) findViewById(R.id.advanceSite_tv);
        confirm = (Button) findViewById(R.id.confirm_button);
        if (function!=null&&myChild!=null) {
            childName.setText(myChild.getName());
            childGrade.setText(myChild.getGrade());
            childClass.setText(myChild.getClasses());
            lineTv.setText(myChild.getLineId()+"号线");
            aboardSiteTv.setText(myChild.getAboardSite());
            debusSiteTv.setText(myChild.getDebusSite());
            advanceSiteTv.setText(""+myChild.getAdvanceSite());
            childName.setEnabled(false);
            childGrade.setEnabled(false);
            childClass.setEnabled(false);
            if("lineId".equals(function)){
                setEnable(null,null,advanceSiteLayout);
            }else if("aboardSite".equals(function)){
                setEnable(lineLayout,debusSiteLayout,advanceSiteLayout);
            }else if("debusSite".equals(function)){
                setEnable(lineLayout,aboardSiteLayout,advanceSiteLayout);
            }else if("advanceSite".equals(function)){
                setEnable(lineLayout,aboardSiteLayout,debusSiteLayout);
            }
        }
        lineLayout.setOnClickListener(this);
        aboardSiteLayout.setOnClickListener(this);
        debusSiteLayout.setOnClickListener(this);
        advanceSiteLayout.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }
    private void setEnable(RelativeLayout param,RelativeLayout param1,RelativeLayout param2){
        if(param!=null){
            param.setClickable(false);
            param.setEnabled(false);
        }
        if(param1!=null){
            param1.setClickable(false);
            param1.setEnabled(false);
        }
        if(param2!=null){
            param2.setClickable(false);
            param2.setEnabled(false);
        }
    }

    private void initPop(final List<String> dataList, final TextView v) {
        ListView lv = new ListView(this);
        /** 数据适配器 */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.pop_list_item, dataList);
        // 根据数据，设置下拉框显示
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里获取item数据
                String value = dataList.get(position);
                // 把选择的数据展示对应的TextView上
                v.setText(value);
                if (v == lineTv) {
                    lineId = lines.get(position).getId();
                }
                // 选择完后关闭popup窗口
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(lv, v.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);  //能够获得焦点
        popupWindow.setOutsideTouchable(true);  //外部点击关闭
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();  //更新位置
        popupWindow.showAsDropDown(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_layout:
                getLine();
                break;
            case R.id.aboardSite_layout:
                getSite(aboardSiteTv);
                break;
            case R.id.debusSite_layout:
                getSite(debusSiteTv);
                break;
            case R.id.advanceSite_layout:
                advanceSite();
                break;
            case R.id.confirm_button:
                confirmSubmit();
                break;
            default:
                break;
        }
    }

    private void getLine() {
        aboardSiteTv.setText("请选择上车站点");
        debusSiteTv.setText("请选择下车站点");
        NetworkProtocol.getLine();
        NetworkProtocol.setOnResposeListener(new OnLineResponseListener() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    lines = GsonUtil.changeJsonToLine(response);
                    List<String> data = new ArrayList<>();
                    for (int i = 0; i < lines.size(); i++) {
                        data.add(lines.get(i).getId() + "号线站点:" + lines.get(i).getAllSite());
                    }
                    initPop(data, lineTv);
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void getSite(final TextView v) {
        if (lineId == -1) {
            Toast.makeText(this, "你还未选择线路，无法获取站点信息", Toast.LENGTH_LONG).show();
        } else {
            NetworkProtocol.getSite(lineId);
            NetworkProtocol.setOnResposeListener(new OnSiteResponseListener() {
                @Override
                public void onSuccess(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        List<String> dataList = GsonUtil.changeJsonToSite(response);
                        initPop(dataList, v);
                    }
                }

                @Override
                public void onFail(String message) {

                }
            });
        }
    }

    private void advanceSite() {
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("2");
        datas.add("3");
        datas.add("4");
        datas.add("5");
        initPop(datas, advanceSiteTv);
    }

    private void confirmSubmit() {

        if ("请选择线路".equals(lineTv.getText().toString())) {
            lineTv.setError("线路不能为空");
        } else {
            if(function==null){
                addChild();
            }else if("lineId".equals(function)){
                String aboardSite;
                String debusSite;
                if ((!("请选择上车站点").equals(aboardSiteTv.getText().toString()))&&(!("请选择下车站点".equals(debusSiteTv.getText().toString()))))
                {
                    aboardSite = aboardSiteTv.getText().toString();
                    debusSite = debusSiteTv.getText().toString();
                    update(String.valueOf(lineId),aboardSite,debusSite);
                }
            }else {
                update(getText(),null,null);
            }
        }
    }
    private String getText(){
        if(aboardSiteTv.isEnabled()){
            return aboardSiteTv.getText().toString();
        } else if (debusSiteTv.isEnabled()) {
            return debusSiteTv.getText().toString();
        }else {
            return advanceSiteTv.getText().toString();
        }
    }
    private void update(String value,String aboardSite,String debusSite){
        NetworkProtocol.updateChild(function,value,myChild.getId(),aboardSite,debusSite);
        NetworkProtocol.setOnResposeListener(new OnUpdateStudentListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(AddChildActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void addChild(){
        mParentId = getIntent().getIntExtra("parentId", 0);
        int id = 0;
        String name = childName.getText().toString();
        String grade = childGrade.getText().toString();
        String classes = childClass.getText().toString();
        String aboardSite = "";
        String debusSite = "";
        if (!("请选择上车站点".equals(aboardSiteTv.getText().toString()))) {
            aboardSite = aboardSiteTv.getText().toString();
        }
        if (!("请选择下车站点".equals(debusSiteTv.getText().toString()))) {
            debusSite = debusSiteTv.getText().toString();
        }
        int advanceSite = 0;
        if (!("请选择提前通知的站点".equals(advanceSiteTv.getText().toString()))) {
            advanceSite = Integer.valueOf(advanceSiteTv.getText().toString());
        }
        Student student = new Student(id, mParentId, lineId, name, grade, classes, aboardSite, debusSite, advanceSite);
        NetworkProtocol.addChild(student);
        NetworkProtocol.setOnResposeListener(new OnAddStudentListener() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    boolean result = Boolean.valueOf(response);
                    if (result) {
                        Toast.makeText(AddChildActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(AddChildActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
    private void getArgment(){
        Intent intent=getIntent();
        if(intent!=null){
            myChild=(Student) intent.getSerializableExtra("student");
            function=intent.getStringExtra("function");
        }
    }
}
