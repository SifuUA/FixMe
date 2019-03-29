package com.okres.basis.model;

public class OperationMessage extends Message {

    private String instrument;
    private String operation;
    private int operationLen;
    private int quantity;
    private int price;
    private long id;
    private int instrumentLen;

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

        public Builder operationLen(int operationLen) {
            operationMessage.operationLen = operationLen;
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

        public Builder instrumentLen(int instrumentLen) {
            operationMessage.instrumentLen = instrumentLen;
            return this;
        }

        public OperationMessage build() {
            return operationMessage;
        }
    }


    public String getInstrument() {
        return instrument;
    }

    public String getOperation() {
        return operation;
    }

    public int getOperationLen() {
        return operationLen;
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

    public int getInstrumentLen() {
        return instrumentLen;
    }

    @Override
    public String toString() {
        return "OperationMessage{" +
                "instrument='" + instrument + '\'' +
                ", operation='" + operation + '\'' +
                ", operationLen=" + operationLen +
                ", quantity=" + quantity +
                ", price=" + price +
                ", id=" + id +
                ", instrumentLen=" + instrumentLen +
                '}';
    }
}
