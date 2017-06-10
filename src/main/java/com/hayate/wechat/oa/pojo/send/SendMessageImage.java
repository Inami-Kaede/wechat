package com.hayate.wechat.oa.pojo.send;

/**
 * 回复图片消息
 * MsgType:image
 */
public class SendMessageImage extends BaseSendMessage{

	public SendMessageImage(String toUserName, String fromUserName,
			int createTime,Image image) {
		super(toUserName, fromUserName, createTime, "image");
		Image = image;
	}

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}


}
