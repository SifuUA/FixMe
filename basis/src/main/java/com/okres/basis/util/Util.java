package com.okres.basis.util;

import com.okres.basis.model.Message;
import com.okres.basis.model.TypeMsg;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

public class Util {

    private static Logger logger = Logger.getLogger(Util.class);
    public static final Properties property = new Properties();
    public static Charset charSet = Charset.forName(Util.property.getProperty("CHARSET"));


    private Util() {
        throw new IllegalAccessError();
    }

    public static void loadProp() {
        try {
            FileInputStream fileInputStream = new FileInputStream("prop.properties");
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    public static boolean isOperation(Message message) {
        return message.getMsgType().equals(TypeMsg.SELL.toString())
                || message.getMsgType().equals(TypeMsg.BUY.toString());
    }
}
