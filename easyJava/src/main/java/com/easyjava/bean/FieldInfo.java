package com.easyjava.bean;

public class FieldInfo {

    /**
     * 字段名称
     */
    private String FieldName;

    /**
     * bean 属性名称
     */
    private String propertyName;

    private String sqlType;

    /**
     * 字段类型
     */
    private String javaType;

    /**
     * 字段备注
     */
    private String comment;

    /**
     * 是否是自增长
     */
    private Boolean isAutoincrement;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fielName) {
        this.FieldName = fielName;
    }

    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsAutoincrement() {
        return isAutoincrement;
    }
    public void setIsAutoincrement(Boolean isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public String getSqlType() {
        return sqlType;
    }
    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getJavaType() {
        return javaType;
    }
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
