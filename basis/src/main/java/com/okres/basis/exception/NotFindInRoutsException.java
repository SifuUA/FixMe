package com.okres.basis.exception;

public class NotFindInRoutsException extends Exception {
    public NotFindInRoutsException() {
        super("There is no such id in Hash map!");
    }
}
