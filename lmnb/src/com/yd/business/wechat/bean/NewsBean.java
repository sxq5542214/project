package com.yd.business.wechat.bean;

import java.util.List;

public class NewsBean extends BaseMessage{
	public NewsBean(){}
	
	public NewsBean(BaseMessage base){
		baseInfo(base);
	}
	
	// 图文消息个数，限制为8条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<ArticleBean> Articles;  
  
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<ArticleBean> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<ArticleBean> articles) {  
        Articles = articles;  
    }  
}
