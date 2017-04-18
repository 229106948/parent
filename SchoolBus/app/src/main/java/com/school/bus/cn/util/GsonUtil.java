package com.school.bus.cn.util;

import android.util.Log;

import com.baidu.mapapi.map.MyLocationData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.bus.cn.data.Driver;
import com.school.bus.cn.data.Leave;
import com.school.bus.cn.data.Line;
import com.school.bus.cn.data.LineShow;
import com.school.bus.cn.data.Notice;
import com.school.bus.cn.data.Parent;
import com.school.bus.cn.data.Student;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhangxingpei on 2017/3/29.
 */

public class GsonUtil {

    private static String TAG = "GsonUtil";

    public static List<Notice> changeJsonToNotice(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Notice>>() {
        }.getType();
        List<Notice> noticeList = gson.fromJson(json, type);
        return noticeList;
    }
    public static List<Leave> changeJsonToLeave(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Leave>>() {
        }.getType();
        List<Leave> leaves = gson.fromJson(json, type);
        return leaves;
    }
    public static List<Student> changeJsonToStudent(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>() {
        }.getType();
        List<Student> studentList = gson.fromJson(json, type);
        return studentList;
    }
    public static List<Driver> changeJsonToDriver(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Driver>>() {
        }.getType();
        List<Driver> driverList = gson.fromJson(json, type);
        return driverList;
    }
    public static MyLocationData changeJsonToLocation(String json) {
        Gson gson = new Gson();
        Driver driver = gson.fromJson(json, Driver.class);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(driver.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(driver.getLatitude())
                .longitude(driver.getLongitude()).build();
        return locData;
    }

    public static Boolean changeJsonToResult(String json) {
        Gson gson = new Gson();
        boolean result = gson.fromJson(json, Boolean.class);
        Log.d(TAG, "" + result);
        return result;
    }
    public static Parent changeJsonToParent(String json) {
        Gson gson = new Gson();
        Parent parent = gson.fromJson(json, Parent.class);
        return parent;
    }
    public static String changeParentToJson(Parent parent) {
        Gson gson = new Gson();
        String json= gson.toJson(parent, Parent.class);
        return json;
    }
    public static String changeLeaveToJson(Leave leave) {
        Gson gson = new Gson();
        String json= gson.toJson(leave, Leave.class);
        return json;
    }
    public static String changeStudentToJson(Student student) {
        Gson gson = new Gson();
        String json= gson.toJson(student, Student.class);
        return json;
    }
    public static List<Line> changeJsonToLine(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Line>>() {
        }.getType();
        List<Line> lineList = gson.fromJson(json, type);
        return lineList;
    }
    public static List<LineShow> changeJsonToLineToShow(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LineShow>>() {
        }.getType();
        List<LineShow> lineList = gson.fromJson(json, type);
        return lineList;
    }
    public static List<String> changeJsonToSite(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> lineList = gson.fromJson(json, type);
        return lineList;
    }
}
