package com.okres.basis.model;

import org.apache.commons.codec.digest.DigestUtils;

public class AcceptMsg extends Message {
    private int id;

    public AcceptMsg() {
    }

    public AcceptMsg(String msgType, int msgId, int id) {
        super(msgType, msgId);
        this.id = id;
        setCheckSum(getMd5());
    }

    @Override
    public void updateChecksum() {
        setCheckSum(getMd5());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMd5() {
        String s = DigestUtils.md5Hex(String.valueOf(id));
        return s;
    }

    @Override
    public String toString() {
        return "AcceptMsg{" +
                "id=" + id +
                '}';
    }
}
