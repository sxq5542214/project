package com.yd.business.wechat.bean;

public class WechatSendAllJsonBean {

	public static final String JSONBEAN_MSGTYPE_MPNEWS = "mpnews";
	/**
	 * 用于设定图文消息的接收者
	 */
	private WechatFilter filter;
	
	private Mpnews mpnews;
	
	/**
	 * 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
	 */
	private String msgtype;
	
	public class WechatFilter{
		/**
		 * 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户
		 */
		private boolean is_to_all;
		/**
		 * 	群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id
		 */
		private Integer tag_id;
		public boolean isIs_to_all() {
			return is_to_all;
		}
		public void setIs_to_all(boolean is_to_all) {
			this.is_to_all = is_to_all;
		}
		public Integer getTag_id() {
			return tag_id;
		}
		public void setTag_id(Integer tag_id) {
			this.tag_id = tag_id;
		}
		
		public WechatFilter(boolean is_to_all,Integer tag_id){
			this.is_to_all = is_to_all;
			this.tag_id = tag_id;
		}
		
	}
	
	public class Mpnews{
		/**
		 * 用于设定即将发送的图文消息
		 */
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
		
		public Mpnews(String media_id){
			this.media_id = media_id;
		}
		
	}

	public WechatFilter getFilter() {
		return filter;
	}

	public void setFilter(boolean is_to_all,Integer tag_id) {
		this.filter = new WechatFilter(is_to_all,tag_id);
	}

	public Mpnews getMpnews() {
		return mpnews;
	}

	public void setMpnews(String media_id) {
		this.mpnews = new Mpnews(media_id);
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
}
