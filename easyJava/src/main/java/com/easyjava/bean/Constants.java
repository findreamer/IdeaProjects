package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
  public static final Logger logger = LoggerFactory.getLogger(Constants.class);
  public static String AUTHER_COMMENT;
  /**
   * 是否忽略表前缀
   */
  public static Boolean IGNORE_TABLE_PREFIX;
  public static String SUFFIX_BEAN_PARAM;

  // 需要忽略掉属性
  public static String IGNORE_BEAN_TOJSON_FIELD;
  public static String IGNORE_BEAN_TOJSON_EXPRESSION;
  public static String IGNORE_BEAN_TOJSON_CLASS;


  // 日期序列化
  public static String BEAN_DATE_FORMAT_EXPRESSION;
  public static String BEAN_DATE_FORMAT_CLASS;
  // 日期反序列化
  public static String BEAN_DATE_PARSE_EXPRESSION;
  public static String BEAN_DATE_PARSE_CLASS;


  public static String PATH_JAVA = "java";
  public static String PATH_RESOURCE = "resource";
  public static String PATH_BASE;
  public static String PATH_PO;
  public static String PACKAGE_BASE;
  public static String PACKAGE_PO;

  public static final String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
  public static final String[] SQL_DATE_TYPES = new String[]{"date"};
  public static final String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "dounble", "float"};
  public static final String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
  public static final String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint",};
  public static final String[] SQL_BOOLEAN_TYPE = new String[]{"boolean"};
  public static final String[] SQL_LONG_TYPE = new String[]{"bigint"};


  static {
    AUTHER_COMMENT = PropertiesUtils.getString("auther.comment");
    IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
    SUFFIX_BEAN_PARAM = PropertiesUtils.getString("suffix.bean.param");
    PACKAGE_BASE = PropertiesUtils.getString("package.base");
    // 包路径
    PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");

    PATH_BASE = PropertiesUtils.getString("path.base");
    PATH_BASE = PATH_BASE + PATH_JAVA;

    PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");

    IGNORE_BEAN_TOJSON_FIELD = PropertiesUtils.getString("ignore.bean.tojson.field");
    IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
    IGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");

    BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
    BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
    BEAN_DATE_PARSE_EXPRESSION = PropertiesUtils.getString("bean.date.parse.expression");
    BEAN_DATE_PARSE_CLASS = PropertiesUtils.getString("bean.date.parse.class");

  }

  public static void main(String[] args) {
    System.out.println(PACKAGE_PO);
  }

}
