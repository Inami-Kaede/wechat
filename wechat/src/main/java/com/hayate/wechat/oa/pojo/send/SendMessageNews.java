package com.hayate.wechat.oa.pojo.send;


/**
 * 回复图文消息
 * MsgType:news
 */
public class SendMessageNews extends BaseSendMessage{
	
	public SendMessageNews(String toUserName, String fromUserName,
			int createTime,String articleCount, Item[] articles) {
		super(toUserName, fromUserName, createTime, "news");
		ArticleCount = articleCount;
		Articles = articles;
	}

	/**
	 * 图文消息个数，限制为8条以内
	 */
	private String ArticleCount;
	
	/**
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
	 */
	private Item[] Articles;


	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public Item[] getArticles() {
		return Articles;
	}

	public void setArticles(Item[] articles) {
		Articles = articles;
	}





	



}
