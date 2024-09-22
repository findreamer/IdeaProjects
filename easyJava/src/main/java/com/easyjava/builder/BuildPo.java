package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.DateUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class BuildPo {
  private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

  public static void execute(TableInfo tableInfo) {
    File folder = new File(Constants.PATH_PO);
    // 判断文件路径是否存在
    if (!folder.exists()) {
      folder.mkdirs();
    }
    File poFile = new File(folder, tableInfo.getBeanName() + ".java");

    OutputStream out = null;
    OutputStreamWriter outw = null;
    BufferedWriter bw = null;
    try {
      out = new FileOutputStream(poFile);
      outw = new OutputStreamWriter(out, "UTF-8");
      bw = new BufferedWriter(outw);
      bw.write("package " + Constants.PACKAGE_PO + ";");
      bw.newLine();
      bw.newLine();


      bw.write("import java.io.Serializable;");
      bw.newLine();

      // 导入包
      if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
        bw.write("import java.util.Date;");
        bw.newLine();

        bw.write(Constants.BEAN_DATE_FORMAT_CLASS);
        bw.newLine();
        bw.write(Constants.BEAN_DATE_PARSE_CLASS);
        bw.newLine();

      }

      Boolean haveIgnoreBean = false;
      for (FieldInfo field : tableInfo.getFieldList()) {
        if (ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), field.getPropertyName())) {
          haveIgnoreBean = true;
          break;
        }
      }
      if (haveIgnoreBean) {
        bw.write(Constants.IGNORE_BEAN_TOJSON_CLASS);
        bw.newLine();
      }

      if (tableInfo.getHaveBigDecimal()) {
        bw.write("import java.math.BigDecimal;");
        bw.newLine();

      }
      bw.newLine();

      // 开始写入类的注解
      BuildComment.createClassComment(bw, tableInfo.getComment());
      bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
      bw.newLine();

      // 开始写入属性配置
      for (FieldInfo field : tableInfo.getFieldList()) {
        BuildComment.createFieldComment(bw, field.getComment());

        // 对日期做注解
        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, field.getSqlType())) {
          bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YYYY_MM_DD_HH_MM_SS));
          bw.newLine();
          bw.write("\t" + String.format(Constants.BEAN_DATE_PARSE_EXPRESSION, DateUtils.YYYY_MM_DD_HH_MM_SS));
          bw.newLine();
        }

        // 对日期做注解
        if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, field.getSqlType())) {
          bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YYYY_MM_DD));
          bw.newLine();
          bw.write("\t" + String.format(Constants.BEAN_DATE_PARSE_EXPRESSION, DateUtils.YYYY_MM_DD));
          bw.newLine();
        }

        // 对忽略字段做注解
        if (ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), field.getPropertyName())) {
          bw.write("\t" + String.format(Constants.IGNORE_BEAN_TOJSON_EXPRESSION, field.getPropertyName()));
          bw.newLine();
        }

        bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
        bw.newLine();
        bw.newLine();
      }

      // 写入 属性 setter\getter 方法
      for (FieldInfo field : tableInfo.getFieldList()) {
        String tempField = StringUtils.upperCaseFirstLetter(field.getPropertyName());
        bw.write("\tpublic void set" + tempField + "(" + field.getJavaType() + " " + field.getPropertyName() + ") {");
        bw.newLine();
        bw.write("\t\tthis." + field.getPropertyName() + " = " + field.getPropertyName() + ";");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();

        bw.write("\tpublic " + field.getJavaType() + " get" + tempField + "() {");
        bw.newLine();
        bw.write("\t\treturn this." + field.getPropertyName() + ";");
        bw.newLine();
        bw.write("\t}");
        bw.newLine();
        bw.newLine();
      }

      StringBuffer sb = new StringBuffer();
      for (FieldInfo field : tableInfo.getFieldList()) {
        sb.append("\"" + field.getComment() + ": \" + " + field.getPropertyName());
        sb.append(",");

      }
      sb.substring(0, sb.lastIndexOf(","));
      // 重写  toString 方法
      bw.write("\t@Override");
      bw.newLine();
      bw.write("\tpublic String toString() {");
      bw.newLine();

      bw.write("\t\t return " + sb + ";");

      bw.newLine();
      bw.write("\t}");

      bw.write("}");
      bw.flush();

    } catch (Exception e) {
      logger.error("创建PO文件失败！");
      logger.info(e.getMessage());
    } finally {
      if (bw!=null) {
        try {
          bw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (outw!=null) {
        try {
          outw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (out!=null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
