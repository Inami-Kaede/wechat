package com.hayate.wechat.oa.pojo.receive;

/**
 * 语音消息
 * MsgType:voice
 *
 */
public class ReceiveMessageVoice extends BaseReceiveMessage{
	/**
	 * 语音格式，如amr，speex等
	 */
	private String format;
	/**
	 * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String mediaId;
	
	/**
	 * 开通语音识别后生效：语音识别结果，UTF8编码
	 */
	private String recognition;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	
}
