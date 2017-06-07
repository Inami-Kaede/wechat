package com.hayate.wechat.oa.pojo.menu;

public class Menu {
	
	public Menu() {
		
	}

	public Menu(Button[] button) {
		super();
		this.button = button;
	}

	private Button[] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
}
