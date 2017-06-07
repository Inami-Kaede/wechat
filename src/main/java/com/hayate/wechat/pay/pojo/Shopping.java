package com.hayate.wechat.pay.pojo;

import java.util.List;

public class Shopping {
	
	/**
	 * 购买商品信息
	 */
	private List<Goods> goods;
	
	/**
	 * 抵扣信息
	 */
	private Discount discount;
	
	/**
	 * 支付方式（0：微信、1：支付宝）
	 */
	private int payType;


	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
}
