package com.okres.basis.model;

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

    @Override
    public String toString() {
        return "AcceptMsg{" +
                "id=" + id +
                '}';
    }
}
