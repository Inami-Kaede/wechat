package com.hayate.wechat.oa.pojo.menu;


public class ViewButton extends Button{
	
	public ViewButton() {
		super();
	}

	public ViewButton(String url) {
		super();
		this.url = url;
	}

	private String url;

	public ViewButton(String type, String name, String url) {
		super(type, name, null);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
