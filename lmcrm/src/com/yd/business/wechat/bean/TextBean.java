package com.yd.business.wechat.bean;

import com.yd.business.wechat.util.WechatUtil;

/**
 * 文本消息.
 * @author Administrator
 *
 */
public class TextBean extends BaseMessage{
	public TextBean(){
		setMsgType(WechatUtil.MESSAGE_TYPE_TEXT);
	}
	public TextBean(BaseMessage base){
		baseInfo(base);
		}
	 // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}
