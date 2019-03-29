package com.okres.basis.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

    private static Logger logger = Logger.getLogger(Util.class);
    public static final Properties property = new Properties();

    public static void loadProp() {
        try {
            FileInputStream fileInputStream = new FileInputStream("prop.properties");
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
