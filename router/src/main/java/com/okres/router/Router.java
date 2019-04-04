package com.okres.router;


import com.okres.basis.controller.*;
import com.okres.basis.util.*;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Router {
    private static Logger logger = Logger.getLogger(Router.class);

    public static void main(String[] args) {
//        BasicConfigurator.configure();
        Util.loadProp();
        Server marketServer = new Server(Integer.parseInt(Util.property.getProperty("MARKET_PORT")));
        Thread marketThread = new Thread(marketServer);
        marketThread.start();
        Server brokerServer = new Server(Integer.parseInt(Util.property.getProperty("BROKER_PORT")));
        Thread brokerThread = new Thread(brokerServer);
        brokerThread.start();

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line;
            while ((line = buf.readLine()) != null) {
                if (line.toLowerCase().equals(Util.property.getProperty("EXIT"))) {
                    System.out.println("A");
                }
            }
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
