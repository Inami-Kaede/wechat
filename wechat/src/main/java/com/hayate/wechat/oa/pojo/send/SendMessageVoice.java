package com.hayate.wechat.oa.pojo.send;

/**
 * 回复语音消息
 * MsgType:voice
 */
public class SendMessageVoice extends BaseSendMessage{

	public SendMessageVoice(String toUserName, String fromUserName,
			int createTime,Voice voice) {
		super(toUserName, fromUserName, createTime, "voice");
		Voice = voice;
	}

	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}


}
