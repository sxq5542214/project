package com.yd.business.isp.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("wanliuInterface")
public class WanLiuInterfaceBean extends ISPInterfaceBean {
	
	public static final String RESULT_CODE_SUCCESS = "0000";
	public static final int STATUS_CALLBACK_SUCCESS = 0;
	public static final String RETURNCODE_CALLBACK_SUCCESS = "true";
	public static final String RETURNCODE_CALLBACK_FAIL = "false";
	
	public static final String RESULTCODE_CALLBACK_SUCCESS = "9999";
	
	//接口调用的 API KEY
	private String apikey;
	//认证签名
	private String sign;
	//
	
	private String timestamp;
	private String createdate;
	
	private Tx_info info;
	//状态
	private int rep_status;
	// 序列号
	private String req_sn;
	// 序列号集合
	private List<String> req_sn_list;
	// 处理的手机号码
	private String rep_mob_no;
	// 产品编码
	private String rep_prod_code;
	// 订单创建时间
	private String created;
	// 订单流水号
	private String order_sn;
	// 订单状态，0：正在充值，1：充值失败，99：充值成功
	private String order_stat;
	// 错误码：0000 表示提交成功,正在充值 ,其它：表示失败
	private String err_code;
	// 错误信息
	private String err_msg;
	//
	private List<Tx_info> tx_info;
	

	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Tx_info getInfo() {
		return info;
	}
	public void setInfo(Tx_info info) {
		this.info = info;
	}
	public List<Tx_info> getTx_info() {
		return tx_info;
	}
	public void setTx_info(List<Tx_info> tx_info) {
		this.tx_info = tx_info;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	public List<String> getReq_sn_list() {
		return req_sn_list;
	}
	public void setReq_sn_list(List<String> req_sn_list) {
		this.req_sn_list = req_sn_list;
	}

	//交易信息
	public class Tx_info{
		//序列号
		private String req_sn;
		//处理的手机号码 
		private String mob_no;
		//产品编码
		private String prod_code;
		
		public String getReq_sn() {
			return req_sn;
		}
		public void setReq_sn(String req_sn) {
			this.req_sn = req_sn;
		}
		public String getMob_no() {
			return mob_no;
		}
		public void setMob_no(String mob_no) {
			this.mob_no = mob_no;
		}
		public String getProd_code() {
			return prod_code;
		}
		public void setProd_code(String prod_code) {
			this.prod_code = prod_code;
		}
	}
	
	//返回信息
	//返回编码
	private String code;
	//返回数据集合
	private List<WanLiuInterfaceBean> repDataList;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public int getRep_status() {
		return rep_status;
	}
	public void setRep_status(int rep_status) {
		this.rep_status = rep_status;
	}
	public String getReq_sn() {
		return req_sn;
	}
	public void setReq_sn(String req_sn) {
		this.req_sn = req_sn;
	}
	public String getRep_mob_no() {
		return rep_mob_no;
	}
	public void setRep_mob_no(String rep_mob_no) {
		this.rep_mob_no = rep_mob_no;
	}
	public String getRep_prod_code() {
		return rep_prod_code;
	}
	public void setRep_prod_code(String rep_prod_code) {
		this.rep_prod_code = rep_prod_code;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getOrder_stat() {
		return order_stat;
	}
	public void setOrder_stat(String order_stat) {
		this.order_stat = order_stat;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public List<WanLiuInterfaceBean> getRepDataList() {
		return repDataList;
	}
	public void setRepDataList(List<WanLiuInterfaceBean> repDataList) {
		this.repDataList = repDataList;
	}

	//=========================回调字段====================
	private String Updated;
	private String resultMsg;
	//其他字段复用
//	timestamp 是 Unix 时间戳 
//	req_sn 是 请求流水号 
//	order_sn 是 订单流水号 
//	prod_code 是 产品编码 
//	order_stat 是 订单状态，0：正在充值，1：充值失败，99：充值成功 
//	mob_no 是 处理的手机号码 
//	err_code 是 错误码：9999 表示成功，其它：表示失败 
//	err_msg 是 错误信息 
	public String getUpdated() {
		return Updated;
	}
	public void setUpdated(String updated) {
		Updated = updated;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	
	//======================余额查询接口====================
	private String time;
	private String balance;


	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
