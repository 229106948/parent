package com.school.bus.cn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.school.bus.cn.adapter.NoticeAdapter;
import com.school.bus.cn.data.Notice;
import com.school.bus.cn.data.Student;
import com.school.bus.cn.listener.OnNoticeResposeListener;
import com.school.bus.cn.listener.OnRefreshListener;
import com.school.bus.cn.net.MyRequest;
import com.school.bus.cn.net.NetworkProtocol;
import com.school.bus.cn.util.GsonUtil;
import com.school.bus.cn.view.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import com.school.bus.R;

/**
 * Created by zhangxingpei on 2017/3/7.
 */

public class MainFragment extends Fragment implements OnRefreshListener {
    private PullToRefreshLayout pullToRefreshLayout;
    private ListView listView;
    private Animation rotateAnimation;
    private NoticeAdapter noticeAdapter;
    private List<Notice> noticeList;
    private View view;
    private TextView noChildText;
    private List<Student>students;
    TextView title;
    ImageView back;
    ImageView add;
    ImageView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, null);
            init(view);
        }
        //缓存的view需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    public void init(View view) {
        initTitle();
        pullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pull_refresh);
        listView = (ListView) view.findViewById(R.id.content_view);
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
        title.setText("首页");
        back.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
    }
    private void childVisibility() {
        pullToRefreshLayout.setVisibility(View.GONE);
        noChildText.setVisibility(View.VISIBLE);
    }
    private void getChildArgument() {
        students = (List<Student>) getActivity().getIntent().getSerializableExtra("student");
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

    public void requestForData(final boolean isPull) {
        NetworkProtocol.getNoticeData(String.valueOf(students.get(0).getParentId()));
        NetworkProtocol.setOnResposeListener(new OnNoticeResposeListener() {
            @Override
            public void onSuccess(String response) {
                noticeList = GsonUtil.changeJsonToNotice(response);
                if (noticeList != null && noticeList.size() > 0) {
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

    public void initListView() {
        noticeAdapter = new NoticeAdapter(getActivity());
        listView.setAdapter(noticeAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        requestForData(true);
    }

}
