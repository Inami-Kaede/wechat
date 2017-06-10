package com.hayate.wechat.oa.pojo.send;

public class Item {
	public Item(String url, String picUrl, String title, String description) {
		super();
		Url = url;
		PicUrl = picUrl;
		Title = title;
		Description = description;
	}

	/**
	 * 点击图文消息跳转链接(不必须)
	 */
	private String Url;
	
	/**
	 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200(不必须)
	 */
	private String PicUrl;
	
	/**
	 * 图文消息标题(不必须)
	 */
	private String Title;
	
	/**
	 * 图文消息描述(不必须)
	 */
	private String Description;
	
	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
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
