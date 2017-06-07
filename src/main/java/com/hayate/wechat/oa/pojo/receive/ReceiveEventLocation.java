package com.hayate.wechat.oa.pojo.receive;

/**
 * 上报地理位置事件
 * 事件类型，LOCATION
 *
 */
public class ReceiveEventLocation extends BaseReceiveEvent{
	
	/**
	 * 地理位置纬度
	 */
	private String latitude;
	/**
	 * 地理位置经度
	 */
	private String longitude;
	/**
	 * 地理位置精度
	 */
	private String precision;
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	
	
}
