package com.okres.basis.model;

import org.apache.commons.codec.digest.DigestUtils;

public class AcceptMsg extends Message {
    private long id;

    public AcceptMsg() {
    }

    public AcceptMsg(String msgType, long msgId, long id) {
        super(msgType, msgId);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
