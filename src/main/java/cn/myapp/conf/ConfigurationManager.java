package cn.myapp.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ua28 on 3/13/20.
 */
public class ConfigurationManager {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream("my.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Integer getInterger(String key) {
        String value = getProperty(key);
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Long getLong(String key) {
        String value = getProperty(key);
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static boolean getBoolean(String key) {
        String value = getProperty(key);
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
