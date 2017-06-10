package com.hayate.wechat.oa.pojo.send;

/**
 * 回复视频消息
 * MsgType:video
 */
public class SendMessageVideo extends BaseSendMessage{

	public SendMessageVideo(String toUserName, String fromUserName,
			int createTime,Video video) {
		super(toUserName, fromUserName, createTime, "video");
		Video = video;
	}

	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}


}
