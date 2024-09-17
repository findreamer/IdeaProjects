package com.easyjava.entity.po;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * @Description: 产品信息表
 * @auther: 钱多多
 * @date: 2024/09/17
 */
public class ProductInfo implements Serializable {

  /**
   * 自增ID
   */
  private Integer id;

  /**
   * 公司ID
   */
  private String companyId;

  /**
   * 商品编号
   */
  private String code;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 价格
   */
  private BigDecimal proce;

  /**
   * sku类型
   */
  private Integer skuType;

  /**
   * 颜色类型
   */
  private Integer colorType;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 创建日期
   */
  private Date createDate;

  /**
   * 库存
   */
  private Long stock;

  /**
   * 状态
   */
  private Integer status;

}