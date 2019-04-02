package com.okres.basis;

import org.apache.commons.codec.digest.DigestUtils;

public class TestMain {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex(String.valueOf(0)));
    }
}
