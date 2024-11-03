package com.easyjava.utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class DateUtils {
  private static final Object lockObj = new Object();
  private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

  public static SimpleDateFormat getSdf(String pattern) {
    ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
    if (tl==null) {
      synchronized (lockObj) {
        tl = sdfMap.get(pattern);
        if (tl==null) {
          tl = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
              return new SimpleDateFormat(pattern);
            }
          };

          sdfMap.put(pattern, tl);
        }
      }
    }
    return tl.get();
  }

  public static String format(Date date, String pattern) {
    return getSdf(pattern).format(date);
  }

  public static Date parse(String dateStr, String pattern) throws ParseException {
    try {
      return getSdf(pattern).parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}

