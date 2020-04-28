package com.yd.business.wechat.bean;

/**
 * 语音消息.
 * @author Administrator
 *
 */
public class VoiceBean extends BaseMessage{
	public VoiceBean(){}
	public VoiceBean(BaseMessage base){
		baseInfo(base);
	}
	// 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}
