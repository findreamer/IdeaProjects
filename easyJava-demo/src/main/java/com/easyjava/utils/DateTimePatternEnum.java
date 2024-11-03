package com.easyjava.utils;

public enum DateTimePatternEnum {
  YYYY_MM_DD_HH_MM_SS("yyyy-MM-DD HH:mm:ss"), YYYY_MM_DD("yyyy_MM_dd");

  private String pattern;

  DateTimePatternEnum(String pattern) {
    this.pattern = pattern;
  }

  public String getPattern() {
    return pattern;
  }
}

