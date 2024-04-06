package com.example.mybook;

import java.util.Date;

public class Message {
    public String userName;
    public String fileMessage;
    private long MessageTime;
    public Message(){}
    public Message(String userName,String fileMessage){
        this.userName = userName;
        this.fileMessage = fileMessage;
        this.MessageTime = new Date().getTime();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileMessage() {
        return fileMessage;
    }

    public void setFileMessage(String fileMessage) {
        this.fileMessage = fileMessage;
    }

    public long getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(long messageTime) {
        MessageTime = messageTime;
    }
}
