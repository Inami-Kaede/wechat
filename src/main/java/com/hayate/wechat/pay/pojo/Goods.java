package com.hayate.wechat.pay.pojo;

public class Goods {
	
	/**
	 * 产品ID
	 */
	private String prodId;	
	/**
	 * 产品类型0:VIP;1：课程；2：文章
	 */
	private Integer prodType;
	/**
	 * 数量
	 */
	private Integer quantity;
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Integer getProdType() {
		return prodType;
	}
	public void setProdType(Integer prodType) {
		this.prodType = prodType;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	} 
}
