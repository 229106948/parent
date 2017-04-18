package com.school.bus.cn.data;

import java.io.Serializable;

/**
 * Created by zhangxingpei on 2017/4/1.
 */

public class Student implements Serializable{
    private int id;
    private int parentId;
    private int lineId;
    private String name;
    private String grade;

    public Student(int id, int parentId, int lineId, String name, String grade, String classes, String aboardSite, String debusSite, int advanceSite) {
        this.id = id;
        this.parentId = parentId;
        this.lineId = lineId;
        this.name = name;
        this.grade = grade;
        this.classes = classes;
        this.aboardSite = aboardSite;
        this.debusSite = debusSite;
        this.advanceSite = advanceSite;
    }

    private String classes;
    private String aboardSite;
    private String debusSite;
    private int advanceSite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getAboardSite() {
        return aboardSite;
    }

    public void setAboardSite(String aboardSite) {
        this.aboardSite = aboardSite;
    }

    public String getDebusSite() {
        return debusSite;
    }

    public void setDebusSite(String debusSite) {
        this.debusSite = debusSite;
    }

    public int getAdvanceSite() {
        return advanceSite;
    }

    public void setAdvanceSite(int advanceSite) {
        this.advanceSite = advanceSite;
    }
}
