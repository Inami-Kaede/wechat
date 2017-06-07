package com.hayate.wechat.oa.pojo.send;

public class BaseSendMessage {

	public BaseSendMessage(String toUserName, String fromUserName,
			int createTime, String msgType) {
		super();
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
	}

	/**
	 * 接收方帐号（收到的OpenID）
	 */
	private String ToUserName;
	
	/**
	 * 开发者微信号
	 */
	private String FromUserName;
	
	/**
	 * 消息创建时间 （整型）
	 */
	private int CreateTime;
		
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public int getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(int createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
}
