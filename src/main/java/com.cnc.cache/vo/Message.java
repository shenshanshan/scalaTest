package com.cnc.cache.vo;

//import lombok.Data;

import java.util.Date;

/**
 * @author shenss
 * @create 2021-06-17 16:36
 **/
//@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override public String toString() {
        return "Message{" + "id=" + id + ", msg='" + msg + '\'' + ", sendTime=" + sendTime + '}';
    }
}

