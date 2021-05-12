/**
 * 
 */
package com.yd.business.wechat.bean;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.yd.business.wechat.util.WechatUtil;

/**
 * @author ice
 *
 */
public class TemplateMessage extends BaseMessage {
	private String touser;
	private String template_id;
	private String url;
	private String topcolor = "#FF0000";
	private String content ;
	private Map<String,TemplateMessageValue> data = new HashMap<String,TemplateMessageValue>();
	
	public TemplateMessage() {
		setMsgType(WechatUtil.MESSAGE_TYPE_TEMPLATEMSG);
	}
	/**
	 * 设置模板参数的值
	 * @param key
	 * @param value
	 */
	public void setParam(String key,String value){
		TemplateMessageValue tm = new TemplateMessageValue();
		tm.setValue(value);
		data.put(key, tm);
	}
	
	public void setParam(String key,String value,String color){
		TemplateMessageValue tm = new TemplateMessageValue();
		tm.setValue(value);
		tm.setColor(color);
		data.put(key, tm);
	}
	
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, TemplateMessageValue> getData() {
		return data;
	}

	public void setData(Map<String, TemplateMessageValue> data) {
		this.data = data;
	}

	public class TemplateMessageValue{
		private String value ;
		private String color = "#173177";
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
	}
	
	public static void main(String[] args) {
		TemplateMessage t = new TemplateMessage();
		t.setTouser("123");
		t.setUrl("http://www.baidu.com/");
		t.setTemplate_id("aaabbb");
		t.setParam("User", "黄先生");
		t.setParam("Date", "06月07日 19时24分");
		
		System.out.println(new JSONObject(t).toString());
	}
}
