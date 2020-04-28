/**
 * 
 */
package com.yd.business.isp.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean.Tx_info;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;

/**
 * 订购客户端6 =》 弯流科技
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient6 extends OrderProductClient{
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IChannelService channelService;
	
	private Logger log = Logger.getLogger(OrderProductInstanceClient6.class);
	

	protected String account ;
	protected String password ;
	protected String order_url;
	protected String api_key;
	protected String querybalance_url;
	
	protected void initParams() {
		//弯流科技的相关参数
		api_key = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_WANLIUKEJI_APIKEY);
		account = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_WANLIUKEJI_ACCOUNT);
		password = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_WANLIUKEJI_PWD);
		order_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_WANLIUKEJI_ORDER_URL);
		querybalance_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_WANLIUKEJI_QUERY_BALANCE_URL);
	}
	
	
	/**
	 * 访问订购接口
	 * @param phone
	 * @param code
	 * @return
	 */
	@Override
	public ISPInterfaceBean accessOrderProduct(String orderCode,String phone,ProductBean prod){
		
		initParams();
		
		long time = System.currentTimeMillis();
		String sign = MD5.md5(api_key+String.valueOf(time));
		
		WanLiuInterfaceBean bean = new WanLiuInterfaceBean();
		bean.setCreatedate(DateUtil.getNowDateStrSSS());
		bean.setApikey(api_key);
		bean.setTimestamp(String.valueOf(time));
		Tx_info tx_info = bean.new Tx_info() ;
		tx_info.setMob_no(phone);
		tx_info.setProd_code(prod.getWanliu_code());
		tx_info.setReq_sn(orderCode);
		List<Tx_info> infoList = new ArrayList<Tx_info>();
		infoList.add(tx_info);
		bean.setTx_info(infoList);
		bean.setSign(sign);
		
		String json = new JSONObject(bean).toString();
		log.debug("phone:"+ phone +" wanliukeji request:" + json);
		
		try {
			String result = HttpUtil.post(order_url, json);
			log.debug("phone:"+ phone +" wanliukeji result:" + result);

			JSONObject jso = new JSONObject(result);
			boolean code = jso.getBoolean("code");
			bean.setCode(String.valueOf(code));
			Object dataList = jso.get("data");
			if(code){
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				JSONArray reData = (JSONArray) dataList;
				List<WanLiuInterfaceBean> repDataList = new ArrayList<WanLiuInterfaceBean>();
				for (int i = 0; i < reData.length(); i++) {
					JSONObject resultMap = (JSONObject) reData.get(i);
					WanLiuInterfaceBean repData = new WanLiuInterfaceBean();
					repData.setCreated(resultMap.get("created").toString());
					repData.setReq_sn(resultMap.get("req_sn").toString());
					repData.setRep_mob_no(resultMap.get("mob_no").toString());
					repData.setOrder_sn(resultMap.get("order_sn").toString());
					repData.setOrder_stat(resultMap.get("order_stat").toString());
					repData.setRep_prod_code(resultMap.get("prod_code").toString());
					repData.setErr_code(resultMap.get("err_code").toString());
					repData.setErr_msg(resultMap.get("err_msg").toString());
					if (WanLiuInterfaceBean.RESULT_CODE_SUCCESS.equals(repData.getErr_code())) {
						repData.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
					} else {
						repData.setStatus(ISPInterfaceBean.STATUS_FAILD);
					}
					repDataList.add(repData);
				}
				bean.setRepDataList(repDataList);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
				JSONObject dataInfo = (JSONObject) jso.get("data");
				bean.setErr_code(dataInfo.getString("err_code"));
				bean.setErr_msg(dataInfo.getString("err_msg"));
			}
				
			
			bean.setType(ISPInterfaceBean.TYPE_THIRDPARTY_WANLIUKEJI);
		} catch (Exception e) {
			log.error(e, e);
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResCode(ISPInterfaceBean.RESCODE_INTERNAL_SERVER_ERROR);
			bean.setResMsg("内部服务出错，请联系管理员");
		}
		return bean;
	}
	
	
	
	
	
	/**
	 * 弯流科技，余额查询接口
	 */
	@Override
	public WanLiuInterfaceBean queryBalance() {
		//初始化参数
		initParams();
		long time = System.currentTimeMillis();
		String sign = MD5.md5(api_key+String.valueOf(time));
		WanLiuInterfaceBean bean = new WanLiuInterfaceBean();
		bean.setCreatedate(DateUtil.getNowDateStrSSS());
		bean.setApikey(api_key);
		bean.setTimestamp(String.valueOf(time));
		bean.setSign(sign);
		String json = new JSONObject(bean).toString();
		log.debug("wanliukeji queryBalance request:" + json);
		try {
			String result = HttpUtil.post(order_url, json);
			log.debug("wanliukeji queryBalance result:" + result);
			JSONObject jso = new JSONObject(result);
			boolean code = jso.getBoolean("code");
			bean.setCode(String.valueOf(code));
			
			if(code){
				JSONObject dataInfo = (JSONObject) jso.get("data");
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				bean.setTime(dataInfo.getString("time"));
				bean.setBalance(dataInfo.getString("balance"));
				//获取数据成功，保存余额
				channelService.updateChannelBalance(ChannelBean.CODENUM_THIRDPARTY_WANLIUKEJI,bean.getBalance());
			}else{
				JSONObject dataInfo = (JSONObject) jso.get("errmsg");
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
				bean.setErr_code(dataInfo.getString("err_code"));
				bean.setErr_msg(dataInfo.getString("err_msg"));
			}
			
		} catch (IOException e) {
			log.error(e, e);
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResCode(ISPInterfaceBean.RESCODE_INTERNAL_SERVER_ERROR);
			bean.setResMsg("内部服务出错，请联系管理员");
		}
		return bean;
	}


	/**
	 * 查询订单状态
	 */
	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		//初始化参数
		initParams();
		long time = System.currentTimeMillis();
		String sign = MD5.md5(api_key+String.valueOf(time));
		WanLiuInterfaceBean bean = new WanLiuInterfaceBean();
		bean.setCreatedate(DateUtil.getNowDateStrSSS());
		bean.setApikey(api_key);
		bean.setTimestamp(String.valueOf(time));
		bean.setSign(sign);
		bean.setReq_sn_list(orderCode);
		String json = new JSONObject(bean).toString();
		System.out.println(json);
		return null;
	}


	public static void main(String[] args) {
		String order_url = "http://if.dahanbank.cn/FCOrderServlet";
		String account = "admin";
		String api = "e33444a8-6ed9-44a3-bf3c-26be6c8df2b4";
		String password = "dirasd";
		String phone = "15955107747";
		String orderCode = "UserBalance_test_20160622010122947";
		ProductBean prod = new ProductBean();
		prod.setPackage_size_simple(10);
		
		long time = System.currentTimeMillis();
		String sign = MD5.md5(api+String.valueOf(time));
		
		WanLiuInterfaceBean bean = new WanLiuInterfaceBean();
		
		bean.setApikey(api);
		bean.setTimestamp(String.valueOf(time));
		bean.setSign(sign);
		List<String> req_sn_list = new ArrayList<String>();
		req_sn_list.add("UserBalance_2_20160822104901137");
		req_sn_list.add("UserBalance_2_20160822105048436");
		bean.setReq_sn_list(req_sn_list);
		String json = new JSONObject(bean).toString();
		json = json.replace("req_sn_list", "req_sn");
		System.out.println(json);
		try {
			String result = HttpUtil.post("http://api.oa45.com/tra/prodtx/ordrqry", json);
			System.out.println(result);
			JSONObject jso = new JSONObject(result);
			boolean code = jso.getBoolean("code");
			bean.setCode(String.valueOf(code));
			if(code){
				JSONObject dataInfo = (JSONObject) jso.get("data");
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				bean.setTime(dataInfo.getString("time"));
				bean.setBalance(dataInfo.getString("balance"));
			}else{
				JSONObject dataInfo = (JSONObject) jso.get("errmsg");
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
				bean.setErr_code(dataInfo.getString("err_code"));
				bean.setErr_msg(dataInfo.getString("err_msg"));
			}
		} catch (IOException e) {
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResCode(ISPInterfaceBean.RESCODE_INTERNAL_SERVER_ERROR);
			bean.setResMsg("内部服务出错，请联系管理员");
		}
	}
}
