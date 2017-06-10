package com.hayate.wechat.oa.pojo.receive;

/**
 * 自定义菜单事件(点击菜单弹出子菜单，不会产生上报)
 * 事件类型，CLICK或者VIEW
 *
 */
public class ReceiveEventCustom extends BaseReceiveEvent{
	
	/**
	 * 与Event对应
	 * Event：事件类型，CLICK		EventKey：事件KEY值，与自定义菜单接口中KEY值对应
	 * Event：事件类型，VIEW		EventKey：事件KEY值，设置的跳转URL
	 */
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
