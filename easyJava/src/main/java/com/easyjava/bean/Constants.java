package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {
  /**
   * 是否忽略表前缀
   */
  public static Boolean IGNORE_TABLE_PREFIX;

  public static String SUFFIX_BEAN_PARAM;

  public static final String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
  public static final String[] SQL_DATE_TYPES = new String[]{"date"};
  public static final String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "dounble", "float"};
  public static final String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
  public static final String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint",};
  public static final String[] SQL_BOOLEAN_TYPE = new String[]{"boolean"};
  public static final String[] SQL_LONG_TYPE = new String[]{"bigint"};

  static {
    IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
    SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
  }

}
