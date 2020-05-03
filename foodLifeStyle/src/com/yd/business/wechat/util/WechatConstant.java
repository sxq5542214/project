package com.yd.business.wechat.util;

public interface WechatConstant {
	public static final String TOKEN = "token";
	public static final String QRCODE = "qrcode/create";
	public static final String USERINFO = "user/info";
	public static final String GRANT_TYPE_CLIENT_CREDENTIAL= "client_credential";
	public static final String JSAPI_TICKET_URL = "ticket/getticket?";
	public static final String GET_MATERIALCOUNT = "material/get_materialcount?";
	public static final String BATCHGET_MATERIAL = "material/batchget_material?";
	public static final String CUSTOMER_SEND = "message/custom/send";
	public static final String MATERIAL_SEND_ALL = "message/mass/sendall";
	public static final String TEMPLATE_SEND = "message/template/send";
	public static final String GET_NEWS = "material/get_material?";
	public static final String ADD_NEWS = "material/add_news?";
	public static final String UPDATE_NEWS = "material/update_news?";
	public static final String DEL_NEWS = "material/del_material?";
	public static final String ADD_MATERIAL = "material/add_material";
	public static final String UPLOAD_IMG = "media/uploadimg";
	
	public static final String DOWNLOAD_IMG = "media/get";
	
	/**
	 * 获取菜单
	 */
	public static final String GET_MENU = "menu/get";
	/**
	 * 创建菜单
	 */
	public static final String CREATE_MENU = "menu/create";
	
	
	public static final String ACCESS_TOKEN = "access_token";
	
	public static final String SHOWQR= "https://mp.weixin.qq.com/cgi-bin/showqrcode?";
	//基础支持
	public static final int TYPE_TOKEN = 1;
	public static final int TYPE_MEDIAUPLOAD = 2;
	public static final int TYPE_MEDIAGET = 3;
	public static final int TYPE_LOGOUPLOAD = 4;

	//向用户发消息
	public static final int TYPE_CUSTOMSEND_TEXT = 5;
	public static final int TYPE_CUSTOMSEND_MUSIC = 6;
	public static final int TYPE_CUSTOMSEND_IMAGE = 7;
	public static final int TYPE_CUSTOMSEND_LINK = 8;
//	public static final int TYPE_CUSTOMSEND_LOCATION = 9;
	public static final int TYPE_CUSTOMSEND_VOICE = 9;
	public static final int TYPE_EVENT_SUBSCRIBE = 10;
	public static final int TYPE_EVENT_UNSUBSCRIBE = 11;
	public static final int TYPE_EVENT_LOCATION = 12;
	
	public static final int TYPE_QRCODE = 13;
	
	public static final int TYPE_USERINFO = 14;

	public static final int TICKET_SENCE_CODE_WXMENU = -1;
	public static final int TICKET_SENCE_CODE_UNDEFINE = 1;
	public static final int TICKET_SENCE_CODE_LOTTERY = 2;
	public static final int TICKET_SENCE_CODE_SUPPLIEREVENT = 3;
	public static final int TICKET_SENCE_CODE_INVITEFRIENDS = 4;
	public static final int TICKET_SENCE_CODE_ACTIVITY = 5;
	public static final int TICKET_SENCE_CODE_SUPPLIERTOPIC = 6;
//	public static final int TICKET_SENCE_CODE_ACTIVITY_VOLTE = 6;//8月活动，volte语音主叫免费
//	public static final int TICKET_SENCE_CODE_ACTIVITY_OLYMPIC= 7;//8月活动，奥运会
//	public static final int TICKET_SENCE_CODE_ACTIVITY_FREEORDER = 5;//8月活动，免单
	public static final int TICKET_SENCE_CODE_SUPPLIERSHOP = 7;

	public static final int TICKET_SENCE_ID_ACTIVITY_LIULIANG1G = 1;
	public static final int TICKET_SENCE_ID_ACTIVITY_FREEORDER = 2;
	public static final int TICKET_SENCE_ID_ACTIVITY_VOLTE = 3;
	public static final int TICKET_SENCE_ID_ACTIVITY_OLYMPIC = 4;
	public static final int TICKET_SENCE_ID_ACTIVITY_QIANG1G = 5;
	
	
	public static final String MENU_KEY_USERSIGN = "userSign";
	public static final String MENU_KEY_SUPPLIEREVENT = "supplierEvent";
	public static final String MENU_KEY_WECHAT = "wechat";
}
