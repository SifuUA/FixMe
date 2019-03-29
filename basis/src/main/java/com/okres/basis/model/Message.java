package com.okres.basis.model;

public class Message {
    private String checkSum;
    private String msgType;
    private long checkSumLength;
    private int msgTypeLength;
    private long msgId;

    public Message() {
    }

    public Message(String msgType, long msgId) {
        this.msgType = msgType;
        this.msgId = msgId;
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

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
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
                ", msgId=" + msgId +
                '}';
    }
}
