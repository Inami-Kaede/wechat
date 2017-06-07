package com.hayate.wechat.oa.pojo.send;

/**
 * 回复音乐消息
 * MsgType:music
 */
public class SendMessageMusic extends BaseSendMessage{
	
	public SendMessageMusic(String toUserName, String fromUserName,
			int createTime,Music music) {
		super(toUserName, fromUserName, createTime, "music");
		Music = music;
	}

	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}


}
