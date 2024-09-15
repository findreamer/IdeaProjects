package com.easyjava.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取配置文件
 */
public class PropertiesUtils {
  private static Properties props = new Properties();
  private static Map<String, String> PROPS_MAP = new ConcurrentHashMap();

  static {
    InputStream is = null;
    try {
      is = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
      props.load(is);

      Iterator<Object> iterator = props.keySet().iterator();
      while (iterator.hasNext()) {
        String key = (String) iterator.next();
        PROPS_MAP.put(key, props.getProperty(key));
      }

    } catch (Exception e) {

    } finally {
      if (is!=null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static String getString(String key) {
    return PROPS_MAP.get(key);
  }

  public static void main(String[] args) {
    System.out.println(getString("db.driver.name"));
  }
}
