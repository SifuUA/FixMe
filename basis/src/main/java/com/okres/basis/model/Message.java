package com.okres.basis.model;

public class Message {
    private String checkSum;
    private String msgType;
    private long checkSumLength;
    private int msgTypeLength;
    private long id;

    public Message() {
    }

    public Message(String msgType, long id) {
        this.msgType = msgType;
        this.id = id;
        this.msgTypeLength = msgType.length();
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSumLength = checkSum.length();
        this.checkSum = checkSum;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgTypeLength = msgType.length();
        this.msgType = msgType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCheckSumLength() {
        return checkSumLength;
    }

    public int getMsgTypeLength() {
        return msgTypeLength;
    }

    @Override
    public String toString() {
        return "Message{" +
                "checkSum='" + checkSum + '\'' +
                ", msgType='" + msgType + '\'' +
                ", checkSumLength=" + checkSumLength +
                ", msgTypeLength=" + msgTypeLength +
                ", id=" + id +
                '}';
    }
}
