package com.okres.basis.model;

import org.apache.commons.codec.digest.DigestUtils;

public class OperationMessage extends Message {

    private String instrument;
    private String operation;
    private int quantity;
    private int price;
    private long id;

    public static class Builder {

        private OperationMessage operationMessage;

        public Builder() {
            operationMessage = new OperationMessage();
        }

        public Builder instrument(String instrument) {
            operationMessage.instrument = instrument;
            return this;
        }

        public Builder operation(String operation) {
            operationMessage.operation = operation;
            return this;
        }

        public Builder quantity(int quantity) {
            operationMessage.quantity = quantity;
            return this;
        }

        public Builder price(int price) {
            operationMessage.price = price;
            return this;
        }

        public Builder id(long id) {
            operationMessage.id = id;
            return this;
        }

        public Builder msgType(String msgType) {
            operationMessage.setMsgType(msgType);
            return this;
        }


        public Builder msgId(long msgId) {
            operationMessage.setMsgId(msgId);
            return this;
        }

        public Builder checkSum() {
            operationMessage.setCheckSum(operationMessage.getMd5());
            return this;
        }

        public OperationMessage build() {
            //operationMessage.setCheckSum(operationMessage.getMd5());
            return operationMessage;
        }


    }

    public String getMd5() {
        return DigestUtils.md5Hex(String.valueOf(id)
                .concat(instrument)
                .concat(String.valueOf(quantity).
                        concat(operation)));
    }


    public String getInstrument() {
        return instrument;
    }

    public String getOperation() {
        return operation;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }


    @Override
    public String toString() {
        return "OperationMessage{" +
                "instrument='" + instrument + '\'' +
                ", operation='" + operation + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
