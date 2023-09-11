/**
 * 
 */
package com.yd.business.wechat.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 重要类！千万不要暴露到页面上，不要ajax查这个类
 * @author ice
 *
 */
@Alias("wechatOriginalInfo")
public class WechatOriginalInfoBean extends BaseBean {
	
	private Integer id;
	private String originalid;
	private String original_name;
	private String method_name;
	private String appid;
	private String secret;
	private String server_domain;
	private Integer weight;
	private String access_token;
	private String expires_in;
	private String token;
	private String mch_name;
	private String mch_id;
	private String pay_cert_file_path;
	private String server_url;
	private String server_url2;
	private String jsapi_ticket;
	private String pay_wechat_sign_key;
	private String modify_time;
	
	private String bonus_mch_id;
	private String bonus_mch_name;
	private String bonus_pay_cert_file_path;
	private String bonus_pay_wechat_sign_key;
	
	private String from_originalid;
	

	//非数据库字段
	private Integer weight_start;
	private Integer weight_end;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOriginalid() {
		return originalid;
	}
	public void setOriginalid(String originalid) {
		this.originalid = originalid;
	}
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getServer_domain() {
		return server_domain;
	}
	public void setServer_domain(String server_domain) {
		this.server_domain = server_domain;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getWeight_start() {
		return weight_start;
	}
	public void setWeight_start(Integer weight_start) {
		this.weight_start = weight_start;
	}
	public Integer getWeight_end() {
		return weight_end;
	}
	public void setWeight_end(Integer weight_end) {
		this.weight_end = weight_end;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getPay_cert_file_path() {
		return pay_cert_file_path;
	}
	public void setPay_cert_file_path(String pay_cert_file_path) {
		this.pay_cert_file_path = pay_cert_file_path;
	}
	public String getServer_url() {
		return server_url;
	}
	public void setServer_url(String server_url) {
		this.server_url = server_url;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
	public String getPay_wechat_sign_key() {
		return pay_wechat_sign_key;
	}
	public void setPay_wechat_sign_key(String pay_wechat_sign_key) {
		this.pay_wechat_sign_key = pay_wechat_sign_key;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public String getFrom_originalid() {
		return from_originalid;
	}
	public void setFrom_originalid(String from_originalid) {
		this.from_originalid = from_originalid;
	}
	public String getServer_url2() {
		return server_url2;
	}
	public void setServer_url2(String server_url2) {
		this.server_url2 = server_url2;
	}
	public String getBonus_mch_id() {
		return bonus_mch_id;
	}
	public void setBonus_mch_id(String bonus_mch_id) {
		this.bonus_mch_id = bonus_mch_id;
	}
	public String getBonus_mch_name() {
		return bonus_mch_name;
	}
	public void setBonus_mch_name(String bonus_mch_name) {
		this.bonus_mch_name = bonus_mch_name;
	}
	public String getBonus_pay_cert_file_path() {
		return bonus_pay_cert_file_path;
	}
	public void setBonus_pay_cert_file_path(String bonus_pay_cert_file_path) {
		this.bonus_pay_cert_file_path = bonus_pay_cert_file_path;
	}
	public String getBonus_pay_wechat_sign_key() {
		return bonus_pay_wechat_sign_key;
	}
	public void setBonus_pay_wechat_sign_key(String bonus_pay_wechat_sign_key) {
		this.bonus_pay_wechat_sign_key = bonus_pay_wechat_sign_key;
	}
	
	
}
