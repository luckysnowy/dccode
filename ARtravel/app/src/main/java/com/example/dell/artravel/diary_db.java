package com.example.dell.artravel;

import cn.bmob.v3.BmobObject;

public class diary_db extends BmobObject{

    private String spot;
    private String mood;
    private String username;
    private String content;

    public String getSpot() {
        return spot;
    }
    public String getMood() {
        return mood;
    }
    public String getUsername(){
        return username;
    }
    public String getContent(){
        return content;
    }

    public void setSpot(String spot) {
        this.spot=spot;
    }
    public void setMood(String mood) {
        this.mood=mood;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
