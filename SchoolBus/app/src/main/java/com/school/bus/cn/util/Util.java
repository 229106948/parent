package com.school.bus.cn.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.bus.cn.data.Leave;
import com.school.bus.cn.data.Notice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2017/4/16.
 */

public class Util {
    public static List<Notice> changeLeaveToNotice(List<Leave> leaves) {
       List<Notice> notices=new ArrayList<>();
            for(int i=0;i<leaves.size();i++) {
                Notice notice = new Notice(leaves.get(i).getLeaveReason(), leaves.get(i).getLeaveType(), leaves.get(i).getApplyTime());
                notices.add(notice);
            }
        return notices;
    }
}
