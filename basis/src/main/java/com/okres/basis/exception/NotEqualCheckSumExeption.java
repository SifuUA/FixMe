package com.okres.basis.exception;

public class NotEqualCheckSumExeption extends Exception {
    public NotEqualCheckSumExeption(String message) {
        super("The checksum is not equal!");
    }
}
