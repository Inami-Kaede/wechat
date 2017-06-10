package com.hayate.wechat.oa.pojo.push;

public class Articles {

	public Articles(Article[] articles) {
		super();
		this.articles = articles;
	}

	private Article[] articles;

	public Article[] getArticles() {
		return articles;
	}

	public void setArticles(Article[] articles) {
		this.articles = articles;
	}
}
