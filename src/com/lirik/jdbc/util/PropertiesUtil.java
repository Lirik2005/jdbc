package com.lirik.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * В этом классе мы обращаемся к нашему файлу application.properties, где хранятся наши креды для подключения к базе данных.
 * В статическом блоке инициализации вызывается метод, который читает наш properties-файл и загружает его.
 * Статический блок инициализации вызывается ТОЛЬКО ОДИН РАЗ за время жизни программы!!!
 */

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
