/**
 * 
 */
package com.yd.business.wechat.bean;

import com.yd.business.wechat.util.WechatUtil;

/**
 * 微信客服消息，发送微信后台的图文素材
 * @author ice
 *
 */
public class MaterialBean extends BaseMessage {
	
	/**
	 * 默认查询数量
	 */
	public static final int COUNT_DEFAULT = 20;
	
	private String media_id;
	
	//获取素材总量时用
	private Integer voice_count;
	private Integer video_count;
	private Integer image_count;
	private Integer news_count;
	//获取素材列表的参数
	private String type;
	private Integer offset;
	private Integer count;
	

	public MaterialBean(){
		setMsgType(WechatUtil.MESSAGE_TYPE_MPNEWS);
	}
	
	public MaterialBean(BaseMessage base){
		setMsgType(WechatUtil.MESSAGE_TYPE_MPNEWS);
		baseInfo(base);
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public Integer getVoice_count() {
		return voice_count;
	}

	public void setVoice_count(Integer voice_count) {
		this.voice_count = voice_count;
	}

	public Integer getVideo_count() {
		return video_count;
	}

	public void setVideo_count(Integer video_count) {
		this.video_count = video_count;
	}

	public Integer getImage_count() {
		return image_count;
	}

	public void setImage_count(Integer image_count) {
		this.image_count = image_count;
	}

	public Integer getNews_count() {
		return news_count;
	}

	public void setNews_count(Integer news_count) {
		this.news_count = news_count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	
}
