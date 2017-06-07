package com.hayate.wechat.oa.pojo.send;

public class Music {

	public Music(String title, String description, String musicUrl,
			String hQMusicUrl, String thumbMediaId) {
		super();
		Title = title;
		Description = description;
		MusicUrl = musicUrl;
		HQMusicUrl = hQMusicUrl;
		ThumbMediaId = thumbMediaId;
	}

	/**
	 * 音乐标题(不必须)
	 */
	private String Title;
	
	/**
	 * 音乐描述(不必须)
	 */
	private String Description;

	/**
	 * 音乐链接(不必须)
	 */
	private String MusicUrl;
	
	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐(不必须)
	 */
	private String HQMusicUrl;
	
	/**
	 * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
	 */
	private String ThumbMediaId;


	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
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

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}
}
