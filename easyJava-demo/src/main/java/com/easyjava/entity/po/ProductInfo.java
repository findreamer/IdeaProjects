package com.easyjava.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

/**
 * @Description: 产品信息表
 *
 * @auther: 钱多多
 * @date: 2024/09/18
 */
public class ProductInfo implements Serializable {
	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 公司ID
	 */
	@JsonIgnore
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 创建日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	/**
	 * 库存
	 */
	private Long stock;

	/**
	 * 状态
	 */
	@JsonIgnore
	private Integer status;

}