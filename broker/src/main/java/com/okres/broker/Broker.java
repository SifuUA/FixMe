package com.okres.broker;

import com.okres.basis.controller.Client;
import com.okres.basis.util.Util;

public class Broker {
    public static void main(String[] args) {
        Util.loadProp();
        Client brokerClient = new Client("Broker");
        Thread brokerThread = new Thread(brokerClient);
        brokerThread.start();
        try {
            brokerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Util.readInputData(brokerClient);
    }
}
