package com.easyjava.entity.po;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * @Description: 产品信息表
 * @Date: 时间
 */
public class ProductInfo implements Serializable {

  private Integer id;

  private String companyId;

  private String code;

  private String productName;

  private BigDecimal proce;

  private Integer skuType;

  private Integer colorType;

  private Date createTime;

  private Date createDate;

  private Long stock;

  private Integer status;

}