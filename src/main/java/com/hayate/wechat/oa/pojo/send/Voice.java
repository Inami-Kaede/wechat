package com.hayate.wechat.oa.pojo.send;

public class Voice {
	public Voice(String mediaId) {
		super();
		MediaId = mediaId;
	}

	/**
	 * 通过素材管理中的接口上传多媒体文件，得到的id
	 */
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
