package com.easyjava.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {
  /**
   * 表名
   */
  private String tableName;

  /**
   * bean 名称
   */
  private String beanName;

  /**
   * 参数名称
   */
  private String beanParamName;

  /**
   * 表注释
   */
  private String comment;

  /**
   * 字段信息
   */
  private List<FieldInfo> fieldList;

  /**
   * 唯一索引集合
   */
  private Map<String, List<FieldInfo>> keyIdexMap = new LinkedHashMap();

  /**
   * 是否有date类型
   */
  private Boolean haveDate = false;

  /**
   * 是否有datetime类型
   */
  private Boolean haveDateTime = false;

  /**
   * 是否有 bigdecimal 类型
   */
  private Boolean haveBigDecimal = false;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getBeanName() {
    return beanName;
  }

  public void setbeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getBeanParamName() {
    return beanParamName;
  }

  public void setBeanParamName(String beanParamName) {
    this.beanParamName = beanParamName;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<FieldInfo> getFieldList() {
    return fieldList;
  }

  public void setFieldList(List<FieldInfo> fieldList) {
    this.fieldList = fieldList;
  }

  public Boolean getHaveDateTime() {
    return haveDateTime;
  }

  public void setHavaDateTime(Boolean havaDateTime) {
    this.haveDateTime = havaDateTime;
  }

  public Boolean getHaveDate() {
    return haveDate;
  }

  public void setHaveDate(Boolean haveDate) {
    this.haveDate = haveDate;
  }

  public Boolean getHaveBigDecimal() {
    return haveBigDecimal;
  }

  public void setHaveBigDecimal(Boolean haveBigDecimal) {
    this.haveBigDecimal = haveBigDecimal;
  }

  public Map<String, List<FieldInfo>> getKeyIdexMap() {
    return keyIdexMap;
  }

  public void setKeyIdexMap(Map<String, List<FieldInfo>> keyIdexMap) {
    this.keyIdexMap = keyIdexMap;
  }
}
