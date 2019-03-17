package com.example.dell.artravel;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
public class User extends BmobObject{
    private String username;
    private String password;

    public String getUsername(){
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
