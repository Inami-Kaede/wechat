package com.hayate.wechat.oa.pojo.send;

/**
 * 回复文本消息
 * MsgType:text
 */
public class SendMessageText extends BaseSendMessage{

	public SendMessageText(String toUserName, String fromUserName,
			int createTime, String content) {
		super(toUserName, fromUserName, createTime, "text");
		Content = content;
	}

	/**
	 * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
