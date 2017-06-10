package com.hayate.wechat.oa.pojo.send;

public class Video {
	public Video(String mediaId, String title, String description) {
		super();
		MediaId = mediaId;
		Title = title;
		Description = description;
	}

	/**
	 * 通过素材管理中的接口上传多媒体文件，得到的id
	 */
	private String MediaId;
	
	/**
	 * 视频消息的标题(不必须)
	 */
	private String Title;
	
	/**
	 * 视频消息的描述(不必须)
	 */
	private String Description;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
