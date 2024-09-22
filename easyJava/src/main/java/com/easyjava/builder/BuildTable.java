package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.JsonUtils;
import com.easyjava.utils.PropertiesUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTable {
  private static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
  private static Connection conn = null;
  // 获取到表信息
  private static final String SQL_SHOW_TABLE_STATUS = "show table status";
  private static final String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";
  private static final String SQL_SHOW_TABLE_INDEX = "show index from %s";


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

  public static List<TableInfo> getTables() {
    PreparedStatement ps = null;
    ResultSet tableResult = null;

    List<TableInfo> tableInfoList = new ArrayList();
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
        readFieldInfo(tableInfo);

        getKeyIndexInfo(tableInfo);

        tableInfoList.add(tableInfo);
        logger.info("tableInfo:{}", JsonUtils.convertObj2Json(tableInfo));

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
    return tableInfoList;
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

  private static void readFieldInfo(TableInfo tableInfo) {
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

        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setFieldName(field);
        fieldInfo.setComment(comment);
        fieldInfo.setSqlType(type);
        fieldInfo.setIsAutoincrement("auto_increment".equalsIgnoreCase(extra) ? true:false);
        fieldInfo.setPropertyName(propertyName);
        fieldInfo.setJavaType(processJavaType(type));
        // logger.info("javaType:{}", type);
        fieldInfoList.add(fieldInfo);

        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
          tableInfo.setHavaDateTime(true);
        }
        if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
          tableInfo.setHaveDate(true);
        }
        if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
          tableInfo.setHaveBigDecimal(true);
        }
      }

      tableInfo.setFieldList(fieldInfoList);


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

  private static void getKeyIndexInfo(TableInfo tableInfo) {
    PreparedStatement ps = null;
    ResultSet fieldResult = null;

    Map<String, FieldInfo> tempMap = new HashMap();
    for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
      tempMap.put(fieldInfo.getFieldName(), fieldInfo);
    }
    try {
      ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX, tableInfo.getTableName()));
      fieldResult = ps.executeQuery();
      while (fieldResult.next()) {
        String keyName = fieldResult.getString("key_name");
        int nonUnique = fieldResult.getInt("non_unique");
        String columnName = fieldResult.getString("column_name");
        // logger.info("keyName:{},nonUnique:{},columnName:{}", keyName, nonUnique, columnName);

        if (nonUnique==1) {
          continue;
        }

        List<FieldInfo> keyFieldList = tableInfo.getKeyIdexMap().computeIfAbsent(keyName, k -> new ArrayList<FieldInfo>());

        keyFieldList.add(tempMap.get(columnName));
      }


    } catch (Exception e) {
      logger.error("读取索引信息失败", e);
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
  }
}
