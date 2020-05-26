package com.datcent.project.mq;


import lombok.*;
import lombok.experimental.Accessors;


public class UserContent {

    private String username;
    private String pwd;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public  UserContent(){

    }


    public UserContent(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }
}
