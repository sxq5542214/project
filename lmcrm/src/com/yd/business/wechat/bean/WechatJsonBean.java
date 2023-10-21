package com.yd.business.wechat.bean;

import java.util.List;


public class WechatJsonBean {
	private String touser;
	private String msgtype;
	private Text text;
	private Image image;
	private News news;
	private Mpnews mpnews;

	public String getTouser() {
		return touser;
	}

	public Text getText() {
		return text;
	}
	public News getNews() {
		return news;
	}

	public Mpnews getMpnews() {
		return mpnews;
	}

	public void setNews(List<ArticleBean> articles) {
		this.news = new News(articles);
	}

	public void setMpnews(String media_id) {
		this.mpnews = new Mpnews(media_id);
	}

	//	public void setText(TextJsonBean text) {
//		this.text = text;
//	}
	public void setText(String text){
		this.text = new Text(text);
	}

	public void setImage(String media_id) {
		this.image = new Image(media_id);
	}

	public Image getImage() {
		return image;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public class Text {
		private String content;
		public Text(String content) {
			this.content = content;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	public class Image{
		private String media_id;
		public Image(String media_id) {
			this.media_id = media_id;
		}
		public String getMedia_id() {
			return media_id;
		}
		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
	}
	public class News{
		private List<ArticleBean> articles;
		public News(List<ArticleBean> articles){
			this.articles = articles;
		}
		public List<ArticleBean> getArticles() {
			return articles;
		}
		public void setArticles(List<ArticleBean> articles) {
			this.articles = articles;
		}
	}

	public class Mpnews{
		private String media_id;
		
		public Mpnews(String media_id){
			this.media_id = media_id;
		}
		
		public String getMedia_id() {
			return media_id;
		}
		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
	}
//	public class Articles{
//		private String title;
//		private String description;
//		private String url;
//		private String picurl;
//		public String getTitle() {
//			return title;
//		}
//		public void setTitle(String title) {
//			this.title = title;
//		}
//		public String getDescription() {
//			return description;
//		}
//		public void setDescription(String description) {
//			this.description = description;
//		}
//		public String getUrl() {
//			return url;
//		}
//		public void setUrl(String url) {
//			this.url = url;
//		}
//		public String getPicurl() {
//			return picurl;
//		}
//		public void setPicurl(String picurl) {
//			this.picurl = picurl;
//		}
//	}
	
}
