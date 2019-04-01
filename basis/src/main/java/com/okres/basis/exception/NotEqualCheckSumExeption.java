package com.okres.basis.exception;

public class NotEqualCheckSumExeption extends Exception {
    public NotEqualCheckSumExeption() {
        super("The checksum is not equal!");
    }
}
