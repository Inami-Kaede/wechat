package com.hayate.wechat.pay.pojo;

public class Discount {
	
	/**
	 * 是否使用折扣券
	 */
	private Boolean useDiscount;
	
	/**
	 * 抵扣券ID(不使用时为0)
	 */
	private Integer voucherId;
	
	public Boolean getUseDiscount() {
		return useDiscount;
	}
	public void setUseDiscount(Boolean useDiscount) {
		this.useDiscount = useDiscount;
	}
	public Integer getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(Integer voucherId) {
		this.voucherId = voucherId;
	}
}
