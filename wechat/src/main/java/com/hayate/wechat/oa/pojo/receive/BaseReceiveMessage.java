package com.hayate.wechat.oa.pojo.receive;

public class BaseReceiveMessage {
	
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
	
	
	private String msgType;
	
	/**
	 * 消息id，64位整型
	 */
	private String msgId;

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

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}


	
}
