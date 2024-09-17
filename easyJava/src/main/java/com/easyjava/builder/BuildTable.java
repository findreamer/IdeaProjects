package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildTable {
  private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
  private static Connection conn = null;
  // 获取到表信息
  private static final String SQL_SHOW_TABLE_STATUS = "show table status";
  private static final String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";


  static {
    String driverName = PropertiesUtils.getString("db.driver.name");
    String url = PropertiesUtils.getString("db.url");
    String user = PropertiesUtils.getString("db.username");
    String password = PropertiesUtils.getString("db.password");
    try {
      Class.forName(driverName);
      conn = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      logger.error("数据库连接失败", e);
    }
  }

  public static void getTables() {
    PreparedStatement ps = null;
    ResultSet tableResult = null;

    List<TableInfo> tableINfoList = new ArrayList();
    try {
      ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
      tableResult = ps.executeQuery();
      while (tableResult.next()) {
        String tableName = tableResult.getString("name");
        String comment = tableResult.getString("comment");
        // logger.info("tableName:{},comment:{}", tableName, comment);


        String beanName = tableName;
        if (Constants.IGNORE_TABLE_PREFIX) {
          beanName = tableName.substring(tableName.indexOf("_") + 1);
        }
        beanName = processField(beanName, true);

        TableInfo tableInfo = new TableInfo();
        // 1、读取表字段
        tableInfo.setTableName(tableName);
        tableInfo.setbeanName(beanName);
        tableInfo.setComment(comment);
        tableInfo.setBeanParamName(beanName + Constants.SUFFIX_BEAN_PARAM);
        // logger.info("table:{},备注:{},JavaBean:{},JavaBeanQuery:{}", tableInfo.getTableName(), tableInfo.getComment(), tableInfo.getBeanName(), tableInfo.getBeanParamName());
        List<FieldInfo> fieldInfoList = readFieldInfo(tableInfo);
      }

    } catch (Exception e) {
      logger.error("读取表信息失败", e);
    } finally {
      if (tableResult!=null) {
        try {
          tableResult.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
      if (ps!=null) {
        try {
          ps.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
      if (conn!=null) {
        try {
          conn.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  /**
   * 驼峰字段名字
   *
   * @param field
   * @param upperCaseFirstletter
   * @return
   */
  public static String processField(String field, Boolean upperCaseFirstletter) {
    StringBuffer sb = new StringBuffer();
    String[] fields = field.split("_");
    sb.append(upperCaseFirstletter ? StringUtils.upperCaseFirstLetter(fields[0]):fields[0]);
    for (int i = 1, len = fields.length; i < len; i++) {
      sb.append(StringUtils.upperCaseFirstLetter(fields[i]));
    }
    return sb.toString();
  }

  private static List<FieldInfo> readFieldInfo(TableInfo tableInfo) {
    PreparedStatement ps = null;
    ResultSet fieldResult = null;

    List<FieldInfo> fieldInfoList = new ArrayList();
    try {
      ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
      fieldResult = ps.executeQuery();
      while (fieldResult.next()) {
        String field = fieldResult.getString("field");
        String type = fieldResult.getString("type");
        String extra = fieldResult.getString("extra");
        String comment = fieldResult.getString("comment");

        if (type.indexOf("(") > 0) {
          type = type.substring(0, type.indexOf("("));
        }

        String propertyName = processField(field, false);
        // logger.info("field:{},type:{},extra:{},comment:{}", field, type, extra, comment);
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setFieldName(field);
        fieldInfo.setComment(comment);
        fieldInfo.setSqlType(type);
        fieldInfo.setIsAutoincrement("auto_increment".equalsIgnoreCase(extra) ? true:false);
        fieldInfo.setPropertyName(propertyName);
        fieldInfo.setJavaType(processJavaType(type));
        logger.info("javaType:{}", processJavaType(type));
        // fieldInfoList.add(fieldInfo);

        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
          fieldInfo.setHavaDateTime(true);
        }
        if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
          fieldInfo.setHaveDate(true);
        }
        if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
          fieldInfo.setHaveBigDecimal(true);
        }
      }


    } catch (Exception e) {
      logger.error("读字段信息失败", e);
    } finally {
      if (fieldResult!=null) {
        try {
          fieldResult.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
      if (ps!=null) {
        try {
          ps.close();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    }

    return fieldInfoList;
  }

  private static String processJavaType(String type) {
    if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPE, type)) {
      return "Integer";
    } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPE, type)) {
      return "Long";
    } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)) {
      return "String";
    } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
      return "Date";
    } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
      return "BigDecimal";
    } else {
      throw new RuntimeException("无法识别的类型:" + type);
    }

  }
}
