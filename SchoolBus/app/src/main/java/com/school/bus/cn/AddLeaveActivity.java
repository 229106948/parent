package com.school.bus.cn;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.R;
import com.school.bus.cn.data.Leave;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnAddLeaveListener;
import com.school.bus.cn.listener.OnLeaveaResponseListener;
import com.school.bus.cn.listener.OnStudentResponseListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;
import com.school.bus.cn.view.DateTimePickDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2017/4/16.
 */

public class AddLeaveActivity extends Activity implements View.OnClickListener {
    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;
    int parentId;
    int studentId;
    List<Student> students;
    RelativeLayout childLayout;
    TextView childTv;
    EditText startTimeText;
    EditText endTimeText;
    RelativeLayout typeLayout;
    TextView leaveTypeTv;
    EditText telephoneText;
    EditText reasonText;
    Button confirm;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_leave_layout);
        getArgment();
        initView();
    }

    private void initView() {
        initTitle();
        childLayout = (RelativeLayout) findViewById(R.id.child_layout);
        childTv = (TextView) findViewById(R.id.child_tv);
        startTimeText = (EditText) findViewById(R.id.start_time);
        endTimeText = (EditText) findViewById(R.id.end_time);
        typeLayout = (RelativeLayout) findViewById(R.id.type_layout);
        leaveTypeTv = (TextView) findViewById(R.id.type_tv);
        telephoneText = (EditText) findViewById(R.id.telephone);
        reasonText = (EditText) findViewById(R.id.leave_reason);
        confirm = (Button) findViewById(R.id.confirm_button);
        childLayout.setOnClickListener(this);
        typeLayout.setOnClickListener(this);
        confirm.setOnClickListener(this);
        startTimeText.setOnClickListener(this);
        endTimeText.setOnClickListener(this);
    }

    private void initTitle() {
        title = (TextView) findViewById(R.id.title_content);
        back = (ImageView) findViewById(R.id.back);
        add = (ImageView) findViewById(R.id.add);
        message = (ImageView) findViewById(R.id.message);
        title.setText("填写请假条");
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
        parentId = getIntent().getIntExtra("parentId", 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.child_layout:
                getStudentList();
                break;
            case R.id.start_time:
                showTime(startTimeText);
                break;
            case R.id.end_time:
                showTime(endTimeText);
                break;
            case R.id.type_layout:
                type();
                break;
            case R.id.confirm_button:
                confirm();
                break;
            default:
                break;
        }
    }
    private void showTime(EditText et){
        DateTimePickDialog dateTimePickDialog=new DateTimePickDialog(this);
        dateTimePickDialog.dateTimePicKDialog(et);
    }

    private void getStudentList() {
        NetworkProtocol.getStudentByParent(parentId);
        NetworkProtocol.setOnResposeListener(new OnStudentResponseListener() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    students = GsonUtil.changeJsonToStudent(response);
                    List<String> dataList = new ArrayList<>();
                    for (int i = 0; i < students.size(); i++) {
                        dataList.add(students.get(i).getName());
                    }
                    initPop(dataList, childTv);
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void type() {
        List<String> datas = new ArrayList<>();
        datas.add("事假");
        datas.add("病假");
        initPop(datas, leaveTypeTv);
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
                if (v == childTv) {
                    studentId = students.get(position).getId();
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

    private void confirm() {
        if (TextUtils.isEmpty(childTv.getText())) {
            Toast.makeText(this, "孩子不能为空哦！", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(startTimeText.getText())) {
            Toast.makeText(this, "开始时间不能为空哦！", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(endTimeText.getText())) {
            Toast.makeText(this, "结束时间不能为空哦！", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(leaveTypeTv.getText())) {
            Toast.makeText(this, "请假类型不能为空哦！", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(telephoneText.getText())) {
            Toast.makeText(this, "联系方式不能为空哦！", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(reasonText.getText())) {
            Toast.makeText(this, "请假原因不能为空哦！", Toast.LENGTH_LONG).show();
        } else {
            Leave leave = new Leave(reasonText.getText().toString(), parentId, studentId,
                    startTimeText.getText().toString(), String.valueOf(System.currentTimeMillis()),
                    leaveTypeTv.getText().toString(), endTimeText.getText().toString(),
                    telephoneText.getText().toString());
            String leaveJson = GsonUtil.changeLeaveToJson(leave);
            NetworkProtocol.addLeave(leaveJson);
            NetworkProtocol.setOnResposeListener(new OnAddLeaveListener() {
                @Override
                public void onSuccess(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        boolean result = Boolean.valueOf(response);
                        if (result) {
                            finish();
                            Toast.makeText(AddLeaveActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFail(String message) {

                }
            });

        }
    }
}
