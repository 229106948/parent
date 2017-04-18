package com.school.bus.cn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.R;
import com.school.bus.cn.adapter.NoticeAdapter;
import com.school.bus.cn.data.Leave;
import com.school.bus.cn.data.Notice;
import com.school.bus.cn.data.Parent;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnLeaveaResponseListener;
import com.school.bus.cn.listener.OnNoticeResposeListener;
import com.school.bus.cn.listener.OnRefreshListener;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;
import com.school.bus.cn.util.Util;
import com.school.bus.cn.view.PullToRefreshLayout;

import java.util.List;

/**
 * Created by zhangxingpei on 2017/3/7.
 */

public class LeaveFragment extends Fragment implements OnRefreshListener{
    private View view;
    private PullToRefreshLayout pullToRefreshLayout;
    private ListView listView;
    private TextView noChildText;
    private Animation rotateAnimation;
    private NoticeAdapter noticeAdapter;
    private List<Notice> noticeList;
    private List<Student>students;
    private List<Leave> leaveList;
    private Parent parent;
    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, null);
            initView();
        }//缓存的view需要判断是否已经被加过parent,如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getChildArgument();
        if(students==null){
            childVisibility();
        }else {
            requestForData(false);
        }
    }
    private void childVisibility() {
        pullToRefreshLayout.setVisibility(View.GONE);
        noChildText.setVisibility(View.VISIBLE);
    }
    private void initView(){
        initTitle();
        pullToRefreshLayout=(PullToRefreshLayout) view.findViewById(R.id.pull_refresh);
        pullToRefreshLayout.setOnRefreshListener(this);
        listView=(ListView) view.findViewById(R.id.content_view);
        noChildText = (TextView) view.findViewById(R.id.no_child);
        initListView();
        pullToRefreshLayout.setOnRefreshListener(this);
        rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_animation);
        rotateAnimation.setInterpolator(new LinearInterpolator());
    }
    private void initTitle() {
        title = (TextView) view.findViewById(R.id.title_content);
        back = (ImageView) view.findViewById(R.id.back);
        add = (ImageView) view.findViewById(R.id.add);
        message = (ImageView) view.findViewById(R.id.message);
        title.setText("请假");
        back.setVisibility(View.GONE);
        add.setVisibility(View.VISIBLE);
        message.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getActivity(),AddLeaveActivity.class);
               getParentArgument();
               intent.putExtra("parentId",parent.getId());
               getActivity().startActivity(intent);
           }
       });
    }
    public void initListView() {
        noticeAdapter = new NoticeAdapter(getActivity());
        listView.setAdapter(noticeAdapter);
    }
    private void getChildArgument() {
        students = (List<Student>) getActivity().getIntent().getSerializableExtra("student");
    }
    private void getParentArgument() {
        parent = (Parent) getActivity().getIntent().getSerializableExtra("parent");
    }
    public void requestForData(final boolean isPull) {
        NetworkProtocol.getLeaveData(String.valueOf(students.get(0).getParentId()));
        NetworkProtocol.setOnResposeListener(new OnLeaveaResponseListener() {
            @Override
            public void onSuccess(String response) {
                leaveList = GsonUtil.changeJsonToLeave(response);
                if (leaveList != null && leaveList.size() > 0) {
                    noticeList= Util.changeLeaveToNotice(leaveList);
                    noticeAdapter.setNoticeList(noticeList);
                }
                if (isPull) {
                    // 下拉刷新成功操作
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.REFRESH_SUCCEED);
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                if (isPull) {
                    // 下拉刷新失败操作
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.REFRESH_FAIL);
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        requestForData(true);
    }

}
