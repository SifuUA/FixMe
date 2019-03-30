package com.okres.basis.model;

public class Message {
    private String checkSum;
    private String msgType;
    private long msgId;

    public Message() {
    }

    public Message(String msgType, long msgId) {
        this.msgType = msgType;
        this.msgId = msgId;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "checkSum='" + checkSum + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgId=" + msgId +
                '}';
    }
}
