package me.lonelee.droidlove.bean;

import cn.bmob.v3.BmobObject;

public class Chat extends BmobObject {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
