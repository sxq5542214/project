/**
 * 
 */
package com.yd.business.isp.client;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelProductParamBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.JieTuoInterfaceBean;
import com.yd.business.isp.bean.NanJingShengShiInterfaceBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.wechat.util.SHA1Util;
import com.yd.util.AESUtils;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;
import com.yd.util.MD5Util;

/**
 * 订购客户端8 =》 杰拓
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient9 extends OrderProductClient {
	
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IChannelProductService channelProductService;
	
	private String partner_no ;
	private String contract_id ;
	private String order_url ;
	private String query_url;
	private String notify_url;
	private String key;
	private String vi;
	
	
	@Override
	protected void initParams() {
		partner_no = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO_PARTNER_NO);
		contract_id = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO_CONTRACT_ID);
		key = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO_KEY);
		vi = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO_VI);
		order_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO__ORDER_URL);
		query_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO__QUERY_URL);
		notify_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_JIETUO__NOTIFY_URL);
	}

	/* (non-Javadoc)
	 * @see com.yd.business.isp.client.OrderProductClient#accessOrderProduct(java.lang.String, java.lang.String, com.yd.business.product.bean.ProductBean)
	 */
	@Override
	public ISPInterfaceBean accessOrderProduct(String orderCode, String phone, ProductBean product) {
		// TODO Auto-generated method stub
		initParams();
		
		ChannelProductParamBean param = channelProductService.findChannelProductParam(product.getId(), ChannelBean.CODENUM_THIRDPARTY_JIETUO);
		

		JieTuoInterfaceBean bean = new JieTuoInterfaceBean();
		bean.setOrder_code(orderCode);
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setPhoneNo(phone);
		bean.setProduct_id(product.getId());
		bean.setProduct_name(product.getName());
		bean.setType(ISPInterfaceBean.TYPE_THIRDPARTY_JIETUO);
		
		//计算签名
		long timestamp = System.currentTimeMillis();
		try {

			String product_code = param.getParam_code();
			String code = encrypt(contract_id, orderCode, product_code, phone, timestamp +"", notify_url );
			
			String content = new JSONObject().put("partner_no", partner_no).put("code", code).toString();
			
			String response ;
			
			response = HttpUtil.post(order_url,content);
			
			JSONObject jso = new JSONObject(response);
			
			String status = jso.optString("order_status");
			bean.setResCode(jso.optString("result_code"));
			bean.setResMsg(jso.optString("result_des"));
			if(JieTuoInterfaceBean.RESCODE_WAIT.equals(status)){
				bean.setStatus(ISPInterfaceBean.STATUS_WAIT);
			}else if(JieTuoInterfaceBean.RESCODE_SUCCESS.equals(status)){
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			}
			
			
		} catch (Exception e) {
			log.error(e, e);
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResCode(ISPInterfaceBean.STATUS_FAILD+"");
			bean.setResMsg(e.getMessage());
		}
		
		
		return bean;
	}
	
	private String encrypt(String contract_id,String order_no,String plat_offer_id,
			String phone_id,String timestamp,String notify_url) throws Exception{
		
		
		JSONObject jso = new JSONObject();
		jso.put("contract_id", contract_id);
		jso.put("order_no", order_no);
		jso.put("plat_offer_id", plat_offer_id);
		jso.put("phone_id", phone_id);
		jso.put("timestamp", timestamp);
		jso.put("notify_url", notify_url);
		
		String str = jso.toString();
		
		String result = AESUtils.encrypt(str, key, vi);
		
		return result;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.yd.business.isp.client.OrderProductClient#queryBalance()
	 */
	@Override
	public ISPInterfaceBean queryBalance() {
		
		
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yd.business.isp.client.OrderProductClient#queryOrderStatus(java.util.List)
	 */
	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
		
		
		
	}
	

}
