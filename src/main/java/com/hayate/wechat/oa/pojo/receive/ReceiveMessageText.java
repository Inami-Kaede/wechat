package com.hayate.wechat.oa.pojo.receive;

/**
 * 文本消息
 * MsgType:text
 *
 */
public class ReceiveMessageText extends BaseReceiveMessage{
	/**
	 * 文本消息内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
