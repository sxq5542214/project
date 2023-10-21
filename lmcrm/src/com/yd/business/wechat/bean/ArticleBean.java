package com.yd.business.wechat.bean;

import com.yd.business.wechat.util.WechatUtil;

/**
 * 图文model.
 * @author Administrator
 *
 */
public class ArticleBean extends BaseMessage {
	public ArticleBean(){
		setMsgType(WechatUtil.MESSAGE_TYPE_NEWS);
	}
	
	public ArticleBean(BaseMessage base){
		setMsgType(WechatUtil.MESSAGE_TYPE_NEWS);
		baseInfo(base);
	}
	
	// 图文消息名称  
    private String Title;  
    // 图文消息描述  
    private String Description;  
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致  
    private String Picurl;  
    // 点击图文消息跳转链接  
    private String Url;  
  
    public String getTitle() {  
        return Title;  
    }  
  
    public void setTitle(String title) {  
        Title = title;  
    }  
  
    public String getDescription() {  
        return null == Description ? "" : Description;  
    }  
  
    public void setDescription(String description) {  
        Description = description;  
    }  
  
    public String getPicurl() {  
        return null == Picurl ? "" : Picurl;  
    }  
  
    public void setPicurl(String picUrl) {  
    	Picurl = picUrl;  
    }  
  
    public String getUrl() {  
        return null == Url ? "" : Url;  
    }  
  
    public void setUrl(String url) {  
        Url = url;  
    }  
}
