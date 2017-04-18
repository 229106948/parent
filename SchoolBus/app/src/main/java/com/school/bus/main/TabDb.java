package com.school.bus.main;

import com.school.bus.cn.DriverFragment;
import com.school.bus.cn.LeaveFragment;
import com.school.bus.cn.MainFragment;
import com.school.bus.cn.MineFragment;
import com.school.bus.R;

/**
 * Created by zhangxingpei on 2017/3/7.
 */

public class TabDb {
    public static String[] getTabsTxt() {
        String[] tabs = {"首页", "定位", "请假", " 我的"};
        return tabs;
    }

    public static int[] getParentTabsImg() {
        int[] ids = {R.drawable.main, R.drawable.location, R.drawable.leave, R.drawable.mine};
        return ids;
    }
    public static int[] getParentTabsImgSelected() {
        int[] ids = {R.drawable.main_selected, R.drawable.location_selected, R.drawable.leave_selected, R.drawable.mine_selected};
        return ids;
    }

    public static Class[] getFragments() {
        Class[] clz = {MainFragment.class,DriverFragment.class,LeaveFragment.class,MineFragment.class};
        return clz;
    }
}
