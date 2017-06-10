package com.hayate.wechat.oa.pojo.receive;

/**
 * 关注/取消关注事件
 * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
 *
 */
public class BaseReceiveEvent {

	/**
	 * 开发者微信号
	 */
	private String toUserName;
	
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String fromUserName;
	
	/**
	 * 消息创建时间 （整型）
	 */
	private int createTime;
	
	/**
	 * 消息类型，event
	 */
	private String msgType;
	
	private String event;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}



	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}




}
