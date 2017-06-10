package com.hayate.wechat.oa.pojo.receive;

/**
 * 扫描带参数二维码事件
 * 未关注：事件类型，subscribe
 * 关注后：事件类型，SCAN
 *
 */
public class ReceiveEventScan extends BaseReceiveEvent{
	
	/**
	 * 未关注：事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 * 关注后：事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 */
	private String eventKey;
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	private String ticket;
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


}
