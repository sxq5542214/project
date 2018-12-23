package com.yd.business.wechat.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatExtendBean;
import com.yd.business.wechat.bean.ArticleBean;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.EventBean;
import com.yd.business.wechat.bean.ImageBean;
import com.yd.business.wechat.bean.LocationBean;
import com.yd.business.wechat.bean.MaterialBean;
import com.yd.business.wechat.bean.NewsBean;
import com.yd.business.wechat.bean.QrCodeBean;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.bean.TokenBean;
import com.yd.business.wechat.bean.WechatJsonBean;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatWaitSendBean;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;
  
/** 
 * 消息工具类 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class WechatUtil {  
	private static Logger log = Logger.getLogger(WechatUtil.class);
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图文 
     */  
    public static final String MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String MESSAGE_TYPE_LOCATION = "location";  

    /** 
     * 请求消息类型：音频 
     */  
    public static final String MESSAGE_TYPE_VOICE = "voice";  
    /** 
     * 请求消息类型：视频 
     */  
    public static final String MESSAGE_TYPE_VIDEO = "video";  

    /** 
     * 请求消息类型：推送 
     */  
    public static final String MESSAGE_TYPE_EVENT = "event";  

    /** 
     * 请求消息类型：推送 
     */  
    public static final String MESSAGE_TYPE_MPNEWS = "mpnews";  

    /** 
     * 请求消息类型：推送 
     */  
    public static final String MESSAGE_TYPE_TEMPLATEMSG = "templatemsg";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
    
    /**
     * 事件类型：已关注的继续关注
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";
    
    /**
     * 事件类型：位置信息
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
  
    /**
     * 事件类型，URL跳转
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";
    
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    @Deprecated 
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();  
  
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
  
        // 释放资源  
        inputStream.close();  
        inputStream = null;  
  
        return map;  
    }  
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
	public static <E extends BaseMessage> E parseXMLToBean(Document doc){
    	Element root = doc.getRootElement();
//    	List<Element> list = root.elements();
    	String msgType = root.element("MsgType").getText();
    	
    	BaseMessage result = parseXMLToBaseMessage(doc);
    	
		if(MESSAGE_TYPE_TEXT.equals(msgType)){
    		TextBean textBean = new TextBean(result);
    		
    		String content = root.element("Content").getText();
    		textBean.setContent(content);
    		result = textBean;
    	}
    	if(MESSAGE_TYPE_IMAGE.equals(msgType)){
    		ImageBean imgBean = new ImageBean(result);
    		
    		String PicUrl = root.element("PicUrl").getText();
    		imgBean.setPicUrl(PicUrl);
    		
    		String MediaId = root.element("MediaId").getText();
    		imgBean.setMediaId(MediaId);
    		
    		result = imgBean;
    	}
    	if(MESSAGE_TYPE_LOCATION.equals(msgType)){
    		LocationBean locationBean = new LocationBean(result);
    		
    		String Location_X = root.element("Location_X").getText();
    		locationBean.setLocation_X(Location_X);
    		
    		String Location_Y = root.element("Location_Y").getText();
    		locationBean.setLocation_Y(Location_Y);
    		
    		String Scale = root.element("Scale").getText();
    		locationBean.setScale(Scale);
    		
    		String Label = root.element("Label").getText();
    		locationBean.setLabel(Label);
    		
    		result = locationBean;
    	}
    	if(MESSAGE_TYPE_VOICE.equals(msgType)){
    		
    	}
    	if(MESSAGE_TYPE_EVENT.equals(msgType)){
    		EventBean bean = parseXMLToEventBean(doc, result);
    		result = bean;
//        	String eventType = root.element("Event").getText();
//        	
//        	if(EVENT_TYPE_SUBSCRIBE.equalsIgnoreCase(eventType)){
//        		
//        	}
//        	if(EVENT_TYPE_UNSUBSCRIBE.equalsIgnoreCase(eventType)){
//        		
//        	}
//        	if(EVENT_TYPE_LOCATION.equalsIgnoreCase(eventType)){
//        		
//        	}
//			if(EVENT_TYPE_SCAN.equalsIgnoreCase(eventType)){
//				
//			}
//			if(EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)){
//				
//			}
    	}
    	
    	return (E) result;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
	public static BaseMessage parseXMLToBaseMessage(Document doc){
    	BaseMessage bean = new BaseMessage();
    	Element root = doc.getRootElement();
    	List<Element> list = root.elements();
    	for(Element e : list){
    		if(e.getName().equalsIgnoreCase("ToUserName")){
    			bean.setToUserName(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("FromUserName")){
    			bean.setFromUserName(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("CreateTime")){
    			bean.setCreateTime(Long.parseLong(e.getText()));
    		}
    		if(e.getName().equalsIgnoreCase("MsgType")){
    			bean.setMsgType(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("MsgId")){
    			bean.setMsgId(Long.parseLong(e.getText()));
    		}
    	}
    	
    	return bean;
    }
    
    @SuppressWarnings("unchecked")
	public static EventBean parseXMLToEventBean(Document doc,BaseMessage base){
    	EventBean bean = new EventBean(base);
    	Element root = doc.getRootElement();
    	List<Element> list = root.elements();
    	for(Element e : list){
    		if(e.getName().equalsIgnoreCase("Event")){
    			bean.setEvent(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("EventKey")){
    			bean.setEventKey(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("Latitude")){
    			bean.setLatitude(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("Longitude")){
    			bean.setLongitude(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("Precision")){
    			bean.setPrecision(e.getText());
    		}
    		if(e.getName().equalsIgnoreCase("Status")){
    			bean.setStatus(e.getText());
    		}
    	}
    	
    	return bean;
    }
    
    
    
    /**
	 * 解析token接口的json
	 * @param jso
	 * @return
	 * @throws JSONException
	 */
	public static TokenBean parseJsonToTokenBean(JSONObject jso) throws JSONException{

		TokenBean tokenBean = new TokenBean();
		tokenBean.setAccess_token(jso.getString("access_token"));
		tokenBean.setExpires_in(jso.getInt("expires_in"));
		
		return tokenBean;
	}
	
	/**
	 * 解析二维码接口的json
	 * @param jso
	 * @return
	 * @throws JSONException
	 */
	public static QrCodeBean parseJsonToQrCodeBean(JSONObject jso) throws JSONException{

		QrCodeBean qrCodeBean = new QrCodeBean();
		qrCodeBean.setTicket(jso.getString("ticket"));
		qrCodeBean.setExpire_seconds(jso.optInt("expire_seconds")+"");
		qrCodeBean.setUrl(jso.getString("url"));
		return qrCodeBean;
	}
	
	/**
	 * 解析获取用户基本信息接口的json
	 * @param jso
	 * @return
	 * @throws JSONException
	 */
	public static UserWechatExtendBean parseJsonToUserWechatBean(JSONObject jso) throws JSONException{
		UserWechatExtendBean userBean = new UserWechatExtendBean();
		userBean.setSubscribe(jso.optInt("subscribe"));
		
		if(userBean.getSubscribe() == UserWechatExtendBean.SUBSCRIBE_YES) //为1时才能取到其它数据
		{
			userBean.setNick_name(jso.optString("nickname"));
			userBean.setSex(jso.optInt("sex")+"");
			userBean.setCity(jso.optString("city"));
			userBean.setProvince(jso.optString("province"));
			userBean.setHead_img(jso.optString("headimgurl"));
			userBean.setUnionid(jso.optString("unionid"));
		}
//		System.out.print("province-------------------"+jso.getString("nickname"));
		return userBean;
	}
	
	/**
	 * 解析微信返回素材总数的JSON
	 * @param json
	 * @return
	 */
	public static MaterialBean parseJsonToMaterialCount(String json){
		MaterialBean bean = new MaterialBean();
		JSONObject jso = new JSONObject(json);
		
		int voice_count = jso.optInt("voice_count",0);
		int video_count = jso.optInt("video_count",0);
		int image_count = jso.optInt("image_count",0);
		int news_count = jso.optInt("news_count",0);
		
		bean.setVideo_count(video_count);
		bean.setVoice_count(voice_count);
		bean.setImage_count(image_count);
		bean.setNews_count(news_count);
		
		return bean;
	}
	
	/**
	 * 解析查询微信素材列表后返回的数据
	 * @param json
	 * @param type
	 * @return
	 */
	public static List<WechatMaterialBean> parseJsonToMaterialList(String json,String type){
		List<WechatMaterialBean> list = new ArrayList<WechatMaterialBean>();
		
		JSONObject jso = new JSONObject(json);
		
		JSONArray array = jso.optJSONArray("item");
		
		if(array != null ){
			for(int i = 0 ; i < array.length() ; i++){
				WechatMaterialBean bean = new WechatMaterialBean();
				bean.setSucai_type(type);
				
				JSONObject item = array.getJSONObject(i);
				
				String media_id = item.getString("media_id");
				String name = item.optString("name");
				int update_time = item.optInt("update_time");
				String url = item.optString("url");
				
				String modify_time = DateUtil.php2javaDateStr(update_time);
				bean.setSucai_media_id(media_id);
				bean.setSucai_name(name);
				bean.setModify_time(modify_time);
				bean.setSucai_url(url);
				
				list.add(bean);
				
				JSONObject content = item.optJSONObject("content");
				if(content != null){
					
					JSONArray newsArray = content.optJSONArray("news_item");
					if(newsArray != null){
						
						for(int x = 0 ; x < newsArray.length(); x++){
							JSONObject news = newsArray.getJSONObject(x);
							
							WechatMaterialBean subMaterial = new WechatMaterialBean();
							subMaterial.setParent_media_id(media_id);
							subMaterial.setSucai_type(type);
							
							String title = news.optString("title");
							String thumb_media_id = news.optString("thumb_media_id");
							String thumb_url = news.optString("thumb_url");
							int show_cover_pic = news.optInt("show_cover_pic");
							String author = news.optString("author");
							String digest = news.optString("digest");
							String newsContent = news.optString("content");
							String newsUrl = news.optString("url");
							String content_source_url = news.optString("content_source_url");
							
							subMaterial.setSucai_title(title);
							subMaterial.setSucai_thumb_media_id(thumb_media_id);
							subMaterial.setSucai_thumb_url(thumb_url);
							subMaterial.setSucai_show_cover_pic(show_cover_pic);
							subMaterial.setSucai_author(author);
							subMaterial.setSucai_digest(digest);
							subMaterial.setSucai_content(newsContent);
							subMaterial.setSucai_url(newsUrl);
							subMaterial.setSucai_content_source_url(content_source_url);
							
							list.add(subMaterial);
						}
						
						
					}
					
				}
				
			}
			
		}
		
		
		
		
		return list;
	}
	
	
	/**
	 * 将消息转换为string串
	 * 解析token接口的json
	 * @param jso
	 * @return
	 * @throws JSONException
	 */
	public static BaseMessage parseJsonResponse(String jsonStr,BaseMessage base) throws JSONException{
		JSONObject jso = new JSONObject(jsonStr);

		base.setErrcode(jso.optInt("errcode"));
		base.setErrmsg(jso.optString("errmsg"));
		
		return base;
	}
	
	/**
	 * 将消息转换为string串，自动回复时用
	 * @param base
	 * @return
	 */
	public static String MessageToXml(BaseMessage base){
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element xml = doc.addElement("xml");
		//基础信息
		Element toUserName = xml.addElement("ToUserName");
		toUserName.addCDATA(base.getToUserName());
		
		Element fromUserName = xml.addElement("FromUserName");
		fromUserName.addCDATA(base.getFromUserName());
		
		Element createTime = xml.addElement("CreateTime");
		createTime.setText((String.valueOf(base.getCreateTime())));
		
		Element msgType = xml.addElement("MsgType");
		msgType.addCDATA(base.getMsgType());
		
		//文本消息
		if(base instanceof TextBean){
			TextBean text = (TextBean) base;
			Element content = xml.addElement("Content");
			content.addCDATA(text.getContent());
		}
		//图文消息
		if(base instanceof NewsBean ){
			NewsBean bean = (NewsBean) base;
			List<ArticleBean> list = bean.getArticles();
			
			Element articleCount = xml.addElement("ArticleCount");
			articleCount.setText(String.valueOf(bean.getArticleCount()));
			
			Element artcles = xml.addElement("Articles");
			for(ArticleBean a : list){
				Element item = artcles.addElement("item");
				Element title = item.addElement("Title");
				title.addCDATA(a.getTitle());
				
				Element description = item.addElement("Description");
				description.addCDATA(a.getDescription());
				
				Element picUrl = item.addElement("PicUrl");
				picUrl.addCDATA(a.getPicurl());
				
				Element url = item.addElement("Url");
				url.addCDATA(a.getUrl());
			}
		}
		//图片消息
		if(base instanceof ImageBean){
			ImageBean bean = (ImageBean) base;
			Element image = xml.addElement("Image");
			
			Element mediaId = image.addElement("MediaId");
			mediaId.addCDATA(bean.getMediaId());
		}
		
		return doc.asXML();
	}
	
	/**
	 * 转换数据库查出的MAP为各类BEAN
	 * @param map
	 * @return
	 */
	public static BaseMessage convertMapToBaseMessage(Map<String,Object> map){
		BaseMessage base = null;
		try {
			String msgType = (String) map.get("MSGTYPE");
			if(WechatUtil.MESSAGE_TYPE_TEXT.equals(msgType)){
				TextBean bean = new TextBean();
				AutoInvokeGetSetMethod.autoInvoke(map, bean);
				base = bean;
			}
			if(WechatUtil.MESSAGE_TYPE_EVENT.equals(msgType)){
				EventBean bean = new EventBean();
				AutoInvokeGetSetMethod.autoInvoke(map, bean);
				base = bean;
			}
			if(WechatUtil.MESSAGE_TYPE_NEWS.equals(msgType)){
				ArticleBean bean = new ArticleBean();
				AutoInvokeGetSetMethod.autoInvoke(map, bean);
				base = bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		
		return base;
	}
	
	
	/**
	 * 转换数据库查出的waitSend类 为各类BEAN
	 * @param map
	 * @return
	 */
	public static BaseMessage convertWaitSendToBaseMessage(WechatWaitSendBean wait){
		BaseMessage base = null;
		try {
			String text = wait.getText_msg();
			String media_id = wait.getMedia_id();
			String news = wait.getNews_url();
			if(StringUtil.isNotNull(text)){
				TextBean bean = new TextBean();
				bean.setContent(text);
				base = bean;
			}
			
			if(StringUtil.isNotNull(media_id)){
				MaterialBean bean = new MaterialBean();
				bean.setMedia_id(media_id);
				base = bean;
			}
			
			if(StringUtil.isNotNull(news)){
				ArticleBean bean = new ArticleBean();
				bean.setUrl(wait.getNews_url());
				bean.setTitle(wait.getNews_title());
				bean.setPicurl(wait.getNews_picurl());
				bean.setDescription(wait.getNews_description());
				base = bean;
			}

			base.setToUserName(wait.getOpenid());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		
		return base;
	}
	
	/**
	 * 将消息转换为string串,主动调用微信时用
	 * @param base
	 * @return
	 */
	public static String MessageToJson(BaseMessage base){
		if(base == null) return null;
		WechatJsonBean jsonBean = new WechatJsonBean();
		
		jsonBean.setMsgtype(base.getMsgType());
		jsonBean.setTouser(base.getToUserName());
		
		if(base instanceof TextBean){
			TextBean bean = (TextBean) base;
			jsonBean.setText(bean.getContent());
		}
		if(base instanceof ImageBean){
			ImageBean bean = (ImageBean) base;
			jsonBean.setImage(bean.getMediaId());
		}
		if(base instanceof NewsBean){
			NewsBean bean = (NewsBean) base;
			jsonBean.setNews(bean.getArticles());
		}
		if(base instanceof ArticleBean){
			ArticleBean bean = (ArticleBean) base;
			List<ArticleBean> list = new ArrayList<ArticleBean>(1);
			list.add(bean);
			jsonBean.setNews(list);
		}
		if(base instanceof MaterialBean){
			MaterialBean bean = (MaterialBean) base;
			jsonBean.setMpnews(bean.getMedia_id());
		}
		
		
		JSONObject jso = new JSONObject(jsonBean);
		
		return jso.toString();
	}
	
	/**
	 * 微信二维码的值中取senceType
	 * @param value
	 * @return
	 */
	public static int getSenceType(long value ){

		long senceType = 0xE0000000;
		int realValue = (int) ((value & senceType ) >> 29);
		return realValue;
	}
	/**
	 * 微信二维码的值中取senceId
	 * @param value
	 * @return
	 */
	public static int getSenceId(long value ){

		long senceId = 0x1FF00000;
		int realValue = (int) ((value & senceId ) >> 20);
		return realValue;
	}
	/**
	 * 微信二维码的值中取userId
	 * @param value
	 * @return
	 */
	public static int getUserId(long value ){

		long userId = 0x000FFFFF;
		int realValue = (int) (value & userId );
		return realValue;
	}
	
	
	/**
	 * 微信二维码共32个位， 前3位用于表示senceType，中9位表示senceId，后20位表示userId
	 * @param senceType 不能大于7
	 * @param senceId	不能大于511
	 * @param userId	不能大于1048575
	 * @return
	 */
	public static long getScenceStr(int senceType,int senceId,int userId ){
		
		if(senceType > 7 ){
			throw new RuntimeException("getScenceStr senceType > 7 !");
		}
		if(senceId > 511 ){
			throw new RuntimeException("getScenceStr senceId > 511 !");
		}
		if(userId > 1048575 ){
			throw new RuntimeException("getScenceStr userId > 1048575 !");
		}
		
		long value = (senceType & 0x7l) << 29;
		long value1 = (senceId & 0x1FF) << 20;
		long value2 = userId & 0xFFFFF ;
		value = value | value1 | value2;
		return value;
	}
	
	
	public static void main(String[] args) {
		
		long l = getScenceStr(3, 7, 6);
		System.out.println( l );
//System.out.println(l > (Integer.MAX_VALUE * 2l));
		System.out.println( getSenceId(l));
		System.out.println( getSenceType(l));
		System.out.println( getUserId(l));
		
//		TextBean t = new TextBean();
//		t.setFromUserName("123123");
//		t.setToUserName("adsfdsf");
//		t.setContent("椒我啊");
//		t.setCreateTime(DateUtil.java2phpDate(System.currentTimeMillis()));
//		t.setMsgType("text");
		WechatJsonBean b = new WechatJsonBean();
		b.setMsgtype("image");
		b.setTouser("123");
		b.setImage("123123123321");
		
		String str = new JSONObject(b).toString();
		System.out.println(str);
	}
}  