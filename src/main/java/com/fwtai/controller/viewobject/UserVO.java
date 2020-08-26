package com.fwtai.controller.viewobject;

import java.io.Serializable;

/**
 * 前端UI仅仅关心的字段显示,所以要含有显示层的数据,UI仅需用到的字段即可
*/
public class UserVO implements Serializable{

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Byte getGender(){
        return gender;
    }

    public void setGender(Byte gender){
        this.gender = gender;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public String getTelphone(){
        return telphone;
    }

    public void setTelphone(String telphone){
        this.telphone = telphone;
    }
}