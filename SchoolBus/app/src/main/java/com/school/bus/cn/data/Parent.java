package com.school.bus.cn.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/31.
 */

public class Parent implements Serializable{
    private int id;
    private String name;
    private String telephone;
    private String password;

    public Parent(String password, int id, String name, String telephone) {
        this.password = password;
        this.id = id;
        this.name = name;
        this.telephone = telephone;
    }


    @Override
    public String toString() {
        return "Parent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
