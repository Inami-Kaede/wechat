package com.hayate.wechat.oa.pojo.receive;

/**
 * 视频消息
 * 视频MsgType:video
 * 小视频MsgType:shortvideo
 * 视频和小视频成员变量相同
 *
 */
public class ReceiveMessageVideo extends BaseReceiveMessage{
	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String thumbMediaId;
	/**
	 * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	private String mediaId;
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
