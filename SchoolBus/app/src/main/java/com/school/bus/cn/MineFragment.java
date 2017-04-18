package com.school.bus.cn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.R;
import com.school.bus.cn.adapter.MyListAdapter;
import com.school.bus.cn.data.Line;
import com.school.bus.cn.data.LineShow;
import com.school.bus.cn.data.Parent;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnLineResponseListener;
import com.school.bus.cn.listener.OnStudentResponseListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class MineFragment extends Fragment {
    View view;
    TextView name;
    TextView telephone;
    LinearLayout bindStudent;
    ListView myList;
    Parent parent;
    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;
    String function;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.my_layout, null);
            initView();
        }//缓存的view需要判断是否已经被加过parent,如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initView() {
        getParent();
        initTitle();
        name = (TextView) view.findViewById(R.id.name);
        telephone = (TextView) view.findViewById(R.id.telephone);
        name.setText(parent.getName());
        telephone.setText(parent.getTelephone());
        bindStudent = (LinearLayout) view.findViewById(R.id.bind_student);
        myList = (ListView) view.findViewById(R.id.list_view);
        initList();
        bindStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddChildActivity.class);
                intent.putExtra("parentId", parent.getId());
                getActivity().startActivity(intent);
            }
        });
    }

    private void initTitle() {
        title = (TextView) view.findViewById(R.id.title_content);
        back = (ImageView) view.findViewById(R.id.back);
        add = (ImageView) view.findViewById(R.id.add);
        message = (ImageView) view.findViewById(R.id.message);
        title.setText("我的");
        back.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        message.setVisibility(View.VISIBLE);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getParent() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            parent = (Parent) bundle.getSerializable("parent");
        }
    }

    private void initList() {
        MyListAdapter myListAdapter = new MyListAdapter(getActivity());
        myList.setAdapter(myListAdapter);

        List<String> myContents = new ArrayList<>();
        List<Integer> redIds = new ArrayList<>();
        myContents.add("我的孩子");
        myContents.add("我关注的线路");
        myContents.add("重新设置线路");
        myContents.add("设置上车站点");
        myContents.add("设置下车站点");
        myContents.add("设置提前站点");

        redIds.add(R.drawable.student);
        redIds.add(R.drawable.line);
        redIds.add(R.drawable.line);
        redIds.add(R.drawable.site);
        redIds.add(R.drawable.site);
        redIds.add(R.drawable.site);
        myListAdapter.setMyContents(myContents, redIds);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getStudentInfo("child");
                        break;
                    case 1:
                        getLineInfo();
                        break;
                    case 2:
                        function="lineId";
                        getStudentInfo("line");
                        break;
                    case 3:
                        function="aboardSite";
                        getStudentInfo("aboardSite");
                        break;
                    case 4:
                        function="debusSite";
                        getStudentInfo("debusSite");
                        break;
                    case 5:
                        function="advanceSite";
                        getStudentInfo("advanceSite");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getStudentInfo(final String fromWhere) {
        NetworkProtocol.getStudentByParent(parent.getId());
        NetworkProtocol.setOnResposeListener(new OnStudentResponseListener() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    List<Student> students = GsonUtil.changeJsonToStudent(response);
                    if ("child".equals(fromWhere)) {
                        toMyChild(students);
                    } else {
                        setLine(students);
                    }
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void getLineInfo() {
        NetworkProtocol.getLineByParent(parent.getId());
        NetworkProtocol.setOnResposeListener(new OnLineResponseListener() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    List<LineShow> lines = GsonUtil.changeJsonToLineToShow(response);
                    toMyLine(lines);
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void toMyChild(List<Student> students) {
        Intent intent = new Intent(getActivity(), MyChildActivity.class);
        intent.putExtra("student", (Serializable) students);
        getActivity().startActivity(intent);
    }

    private void toMyLine(List<LineShow> lines) {
        Intent intent = new Intent(getActivity(), MyLineActivity.class);
        intent.putExtra("line", (Serializable) lines);
        getActivity().startActivity(intent);
    }

    private void setLine(final List<Student> students) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.select_child, null);
        final RadioGroup childGroup = (RadioGroup) view.findViewById(R.id.child_list);
        TextView childTv=(TextView) view.findViewById(R.id.child_tv);
        ImageView closeButton = (ImageView) view.findViewById(R.id.close);
        Button confirm=(Button) view.findViewById(R.id.confirm_button);
        LinearLayout root=(LinearLayout)view.findViewById(R.id.root_view);
        RelativeLayout childContent=(RelativeLayout)view.findViewById(R.id.child_content);
        if (students != null && students.size() > 0) {
            childTv.setVisibility(View.GONE);
            childGroup.setVisibility(View.VISIBLE);
            for (int i = 0; i < students.size(); i++) {
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setChecked(false);
                radioButton.setText(students.get(i).getName());
                childGroup.addView(radioButton);
            }
        }else {
            childTv.setVisibility(View.VISIBLE);
            childGroup.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
        }
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(this.view, Gravity.CENTER, 0, 0);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        childContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childGroup.getCheckedRadioButtonId()!=-1) {
                    int index=childGroup.indexOfChild(childGroup.findViewById(childGroup.getCheckedRadioButtonId()));
                    Intent intent = new Intent(getActivity(), AddChildActivity.class);
                    intent.putExtra("student", students.get(index));
                    intent.putExtra("function", function);
                    getActivity().startActivity(intent);
                    popupWindow.dismiss();
                }else {
                    Toast.makeText(getActivity(),"请至少选择一个孩子",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
