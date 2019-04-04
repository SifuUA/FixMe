package com.okres.market;

import com.okres.basis.controller.Client;
import com.okres.basis.util.Util;

public class Market {
    public static void main(String[] args) {
        Util.loadProp();
        Client marketClient = new Client("Market");
        Thread marketThread = new Thread(marketClient);
        marketThread.start();
        try {
            marketThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Util.readInputData(marketClient);
    }
}
