package com.easyjava.builder;

import com.easyjava.bean.TableInfo;

import java.util.List;

public class RunApplication {
  public static void main(String[] args) {
    List<TableInfo> tableInfoList = BuildTable.getTables();
     
  }
}
