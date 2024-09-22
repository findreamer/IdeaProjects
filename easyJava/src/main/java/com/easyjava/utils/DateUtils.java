package com.easyjava.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYYMMDD1 = "yyyyMMdd";
  public static final String YYYYMMDD2 = "yyyy/MM/dd";

  public static String formate(Date date, String pattern) {
    return new SimpleDateFormat(pattern).format(date);
  }

  public static String parse(String date, String pattern) throws ParseException {
    try {
      new SimpleDateFormat(pattern).parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
