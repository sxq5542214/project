/**
 * 
 */
package com.yd.business.isp.bean;

import org.apache.ibatis.type.Alias;

import com.yd.business.channel.bean.ChannelBean;

/**
 * @author ice
 *
 */
@Alias("ispInterface")
public class ISPInterfaceBean extends InterfaceBean {
	

	public static final int TYPE_AHYD_SZ = ChannelBean.CODENUM_YD_SUZHOU;
	public static final int TYPE_AHDX_HF = ChannelBean.CODENUM_DX_HEFEI;
	public static final int TYPE_AHDX_FY = ChannelBean.CODENUM_DX_FUYANG;
	public static final int TYPE_THIRDPARTY_YUNZHANGTONG = ChannelBean.CODENUM_THIRDPARTY_YUNZHANGTONG;
	public static final int TYPE_THIRDPARTY_DAHANSANTONG = ChannelBean.CODENUM_THIRDPARTY_DAHANSANTONG;
	public static final int TYPE_THIRDPARTY_WANLIUKEJI = ChannelBean.CODENUM_THIRDPARTY_WANLIUKEJI;
	public static final int TYPE_THIRDPARTY_ZHUOWEI = ChannelBean.CODENUM_THIRDPARTY_ZHUOWEI;
	public static final int TYPE_THIRDPARTY_NANJINGSHENGSHI = ChannelBean.CODENUM_THIRDPARTY_NANJINGSHENGSHI;
	public static final int TYPE_THIRDPARTY_JIETUO = ChannelBean.CODENUM_THIRDPARTY_JIETUO;

	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_FAILD = -1;
	public static final int STATUS_UNFIND_CHANNEL = -2; //未找到通道

	public static final String RESCODE_SUCCESS = "SUCCESS";
	public static final String RESCODE_INTERNAL_SERVER_ERROR = "9500";
	public static final String RESCODE_PHONE_UNSUPPORT = "9400";
	public static final String RESCODE_CHANNEL_NOTFOUND = "9300";
	public static final String RESCODE_CHANNEL_ACCESSERROR = "9301";
	public static final String RESCODE_CHANNEL_SERVERNOTFOUND = "9302";
	public static final String RESCODE_CHANNEL_MAINTAIN = "9303";
	
	public static final String TABLE_NAME_INTERFACE_DX_FUYANG_LOG = "ll_interface_dx_fuyang_log";
	public static final String TABLE_NAME_INTERFACE_DX_HEFEI_LOG = "ll_interface_dx_log";
	public static final String TABLE_NAME_INTERFACE_YD_SUZHOU_LOG = "ll_interface_yd_log";
	public static final String TABLE_NAME_INTERFACE_NANJINGSHENGSHI_LOG = "ll_interface_nanjingshengshi_log";
	public static final String TABLE_NAME_INTERFACE_JIETUO_LOG = "ll_interface_jietuo_log";
	
	
	//公共
	private Integer id;
	private String order_code;
	private String server_name;
	private Integer channel_id;
	private Integer channel_type;
	private int type;
	private String interface_url;
	private String mothod;
	private String appKey;
	private String timeStamp;
	private String version;
	private String sign;
	private String format;
	private String access_token;
	private Integer product_id;
	private String product_name;

	private String resCode;
	private String resMsg;
	private String phoneNo;
	private String table_name;
	private String discount;
	private String costMoney;
	private String create_time;
	private String report_time;
	private String callBack_time;
	
	
	
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReport_time() {
		return report_time;
	}
	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCostMoney() {
		return costMoney;
	}
	public void setCostMoney(String costMoney) {
		this.costMoney = costMoney;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCallBack_time() {
		return callBack_time;
	}
	public void setCallBack_time(String callBack_time) {
		this.callBack_time = callBack_time;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getInterface_url() {
		return interface_url;
	}
	public void setInterface_url(String interface_url) {
		this.interface_url = interface_url;
	}
	public String getMothod() {
		return mothod;
	}
	public void setMothod(String mothod) {
		this.mothod = mothod;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public Integer getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(Integer channel_type) {
		this.channel_type = channel_type;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	
}
