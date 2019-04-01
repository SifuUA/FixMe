package com.okres.basis.util;

import com.okres.basis.controller.Client;
import com.okres.basis.model.Message;
import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Util {

    private static Logger logger = Logger.getLogger(Util.class);
    public static final Properties property = new Properties();
    public static Charset charSet;


    private Util() {
        throw new IllegalAccessError();
    }

    public static void loadProp() {
        try {
            property.load(Util.class.getResourceAsStream("/prop.properties"));
            charSet = Charset.forName(Util.property.getProperty("CHARSET"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    public static boolean isOperation(Message message) {
        return message.getMsgType().equals(TypeMsg.SELL.toString())
                || message.getMsgType().equals(TypeMsg.BUY.toString());
    }

    public static boolean isSendOrReject(OperationMessage message) {
        return message.getOperation().equals(TypeMsg.SEND.toString()) ||
                message.getOperation().equals(TypeMsg.REJECT.toString());
    }

    public static void readInputData(Client client) {
        String str;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {

            while ((str = bufferedReader.readLine()) != null) {
                if (str.toLowerCase().equals(Util.property.getProperty("EXIT"))) {
                    client.closeWorkerGroup();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
