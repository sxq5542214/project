/**
 * 
 */
package com.yd.business.channel.bean;
import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("channel")
public class ChannelBean extends BaseBean {

	/**
	 * 同步
	 */
	public static final int TYPE_SYNC = 1;
	/**
	 * 异步
	 */
	public static final int TYPE_ASYNC = 2;
	/**
	 * 启用
	 */
	public static final int STATUS_ENABLE = 1;
	/**
	 * 停用
	 */
	public static final int STATUS_DISABLE = 0;
	
	/**
	 * 宿州移动
	 */
	public static final int CODENUM_YD_SUZHOU = 1;  
	/**
	 * 合肥电信
	 */
	public static final int CODENUM_DX_HEFEI = 2;	
	/**
	 * 阜阳电信
	 */
	public static final int CODENUM_DX_FUYANG = 3;
	/**
	 * 云掌通 第三方
	 */
	public static final int CODENUM_THIRDPARTY_YUNZHANGTONG = 4;
	/**
	 * 大汉三通 第三方
	 */
	public static final int CODENUM_THIRDPARTY_DAHANSANTONG = 5; 
	/**
	 * 弯流科技 第三方
	 */
	public static final int CODENUM_THIRDPARTY_WANLIUKEJI = 6; 
	/**
	 * 卓威 第三方
	 */
	public static final int CODENUM_THIRDPARTY_ZHUOWEI = 7; 
	/**
	 * 南京盛世无限 第三方
	 */
	public static final int CODENUM_THIRDPARTY_NANJINGSHENGSHI = 8; 
	/**
	 * 杰拓通信 第三方
	 */
	public static final int CODENUM_THIRDPARTY_JIETUO = 9; 
	
	
	
	private Integer id;
	private String name;
	private String class_name;
	private String method_name;
	private Integer channel_type;
	private String channel_ip;
	private String channel_url;
	private String white_list;
	private String balance;
	private String server_name;
	private Integer code_num;
	private Integer calc_balance;
	private Integer alarm_balance;
	private Integer status;
	private Integer discount;
	private String table_name;
	
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getCalc_balance() {
		return calc_balance;
	}
	public void setCalc_balance(Integer calc_balance) {
		this.calc_balance = calc_balance;
	}
	public Integer getAlarm_balance() {
		return alarm_balance;
	}
	public void setAlarm_balance(Integer alarm_balance) {
		this.alarm_balance = alarm_balance;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getChannel_ip() {
		return channel_ip;
	}
	public void setChannel_ip(String channel_ip) {
		this.channel_ip = channel_ip;
	}
	public String getChannel_url() {
		return channel_url;
	}
	public void setChannel_url(String channel_url) {
		this.channel_url = channel_url;
	}
	public String getWhite_list() {
		return white_list;
	}
	public void setWhite_list(String white_list) {
		this.white_list = white_list;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public Integer getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(Integer channel_type) {
		this.channel_type = channel_type;
	}
	public Integer getCode_num() {
		return code_num;
	}
	public void setCode_num(Integer code_num) {
		this.code_num = code_num;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	
}
