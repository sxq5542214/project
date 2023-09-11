package com.yd.business.wechat.bean;

/**
 * 图片消息.
 * @author Administrator
 *
 */
public class ImageBean extends BaseMessage{
	
	public ImageBean(){}
	
	public ImageBean(BaseMessage base){
		baseInfo(base);
	}
	
	// 图片链接  
    private String PicUrl;  
    private String MediaId;
  
    public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}
