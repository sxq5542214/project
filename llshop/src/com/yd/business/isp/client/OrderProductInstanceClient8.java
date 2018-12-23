/**
 * 
 */
package com.yd.business.isp.client;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelProductParamBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.NanJingShengShiInterfaceBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.wechat.util.SHA1Util;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;
import com.yd.util.MD5Util;

/**
 * 订购客户端8 =》 南京盛世
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient8 extends OrderProductClient {
	
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IChannelProductService channelProductService;
	
	private String auth_code ;
	private String auth_key ;
	private String order_url ;
	private String query_url;
	
	private SHA1Util sha = new SHA1Util();
	
	
	@Override
	protected void initParams() {
		auth_code = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_NANJINGSHENGSHI_AUTH_CODE);
		auth_key = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_NANJINGSHENGSHI_AUTH_KEY);
		order_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_NANJINGSHENGSHI_ORDER_URL);
		query_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_NANJINGSHENGSHI_QUERY_URL);
	}

	/* (non-Javadoc)
	 * @see com.yd.business.isp.client.OrderProductClient#accessOrderProduct(java.lang.String, java.lang.String, com.yd.business.product.bean.ProductBean)
	 */
	@Override
	public ISPInterfaceBean accessOrderProduct(String orderCode, String phone, ProductBean product) {
		// TODO Auto-generated method stub
		initParams();
		
		ChannelProductParamBean param = channelProductService.findChannelProductParam(product.getId(), ChannelBean.CODENUM_THIRDPARTY_NANJINGSHENGSHI);
		
		String product_code = param.getParam_code();
		
		//计算签名
		long timestamp = System.currentTimeMillis();
		String signStr = auth_code + phone + product_code + timestamp + orderCode + auth_key ;
		
		String sign = sha.getDigestOfString(signStr.getBytes());
		
		
		NanJingShengShiInterfaceBean bean = new NanJingShengShiInterfaceBean();
		bean.setOrder_code(orderCode);
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setPhoneNo(phone);
		bean.setProduct_id(product.getId());
		bean.setProduct_name(product.getName());
		bean.setType(ISPInterfaceBean.TYPE_THIRDPARTY_NANJINGSHENGSHI);
		
		String url = order_url +"?auth_code="+auth_code +"&phone="+phone +"&plan=" + product_code +"&timestamp="+timestamp+"&req_no="+orderCode+"&sign=" + sign;
		String response ;
		try {
			response = HttpUtil.get(url);
			
			JSONObject jso = new JSONObject(response);
			
			bean.setResCode(String.valueOf(jso.optInt("code")));
			bean.setResMsg(URLDecoder.decode(jso.optString("text"), "utf8"));
			if(NanJingShengShiInterfaceBean.RESCODE_WAIT.equals(bean.getResCode())){
				bean.setStatus(ISPInterfaceBean.STATUS_WAIT);
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
		
//		String auth_code ="531287753a975e76a01bc2bd9fd2cb40fd4b804b";
//		String auth_key = "e92027042cf626e75c7c54583495fca3";
//		String phone = "15955107747";
//		String plan = "10000010";
//		String timestamp = ""+System.currentTimeMillis();
//		String orderCode = "Partner_test15955107747";
//		
//		//计算签名
//		String signStr = auth_code + phone + plan + timestamp + orderCode + auth_key ;
//		
//		String sign = new SHA1Util().getDigestOfString(signStr.getBytes());
//		
//		
//		String url = "http://121.40.68.199:10032/rechargeController/recharge.do?auth_code="+auth_code +"&phone="+phone +"&plan=" + plan +"&timestamp="+timestamp+"&req_no="+orderCode+"&sign=" + sign;
//		System.out.println(url);
//		String response ;
//		try {
//			response = HttpUtil.get(url);
//			
//			System.out.println(response);
//			JSONObject jso = new JSONObject(response);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//			
			
			
//		查询
//		String url = "http://121.40.68.199:10032/rechargeController/queryBalance.do";
		String url = "http://120.24.62.58:10032/rechargeController/queryBalance.do";
		String auth_code ="531287753a975e76a01bc2bd9fd2cb40fd4b804b";
		String auth_key = "e92027042cf626e75c7c54583495fca3";
		String timestamp = ""+System.currentTimeMillis();
		String signStr = auth_code + timestamp + auth_key;
		String sign = new SHA1Util().getDigestOfString(signStr.getBytes());
		String response ;
		try {
			url = url+"?auth_code="+auth_code +"&timestamp="+timestamp+"&sign=" + sign;
			response = HttpUtil.get(url);
			System.out.println(URLDecoder.decode(response,"utf8"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	

}
