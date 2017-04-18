package com.school.bus.cn.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/7.
 */

public class Site implements Serializable{
    private int id;
    private int lineId;
    private String name;
    private String aboardTime;
    private String debusTime;
    private String stayTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAboardTime() {
        return aboardTime;
    }

    public void setAboardTime(String aboardTime) {
        this.aboardTime = aboardTime;
    }

    public String getDebusTime() {
        return debusTime;
    }

    public void setDebusTime(String debusTime) {
        this.debusTime = debusTime;
    }

    public String getStayTime() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
    }

    public Site(int id, int lineId, String name, String aboardTime, String debusTime, String stayTime) {

        this.id = id;
        this.lineId = lineId;
        this.name = name;
        this.aboardTime = aboardTime;
        this.debusTime = debusTime;
        this.stayTime = stayTime;
    }
}
