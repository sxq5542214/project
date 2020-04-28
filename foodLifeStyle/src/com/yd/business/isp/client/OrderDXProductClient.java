/**
 * 
 */
package com.yd.business.isp.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.yd.business.isp.bean.DXInterfaceBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.order.util.ConnectionUrlUtil;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;
import com.yd.util.MD5Util;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Component
public class OrderDXProductClient extends OrderProductClient{
	@Resource
	private IConfigAttributeService configAttributeService;
	
	private Logger log = Logger.getLogger(OrderDXProductClient.class);
	public static final String API_METHOD_JYB = "jybs4000cfm_open";
	
	@Override
	protected void initParams() {
		
	}

	/**
	 * 访问订购接口
	 * @param phone
	 * @param code
	 * @return
	 */
	@Override
	public ISPInterfaceBean accessOrderProduct(String orderCode,String phone,ProductBean prod){
		

		String url = configAttributeService.getValueByCode(AttributeConstant.CODE_DX_AH_ORDER_URL);
		String qdid = configAttributeService.getValueByCode(AttributeConstant.CODE_DX_AH_QDID);
		String qdkey = configAttributeService.getValueByCode(AttributeConstant.CODE_DX_AH_QDKEY);
//		产品ID
//		String sqnid = configAttributeService.getValueByCode(AttributeConstant.CODE_DX_AH_SQNID);
		
		String time = DateUtil.formatDatePure(new Date());
		String random = StringUtil.fixIntToString(RandomUtils.nextInt(999999), 6) ;
		String transactionId = qdid + time + random;
		StringBuffer sb=new StringBuffer();
		sb.append("userid="+phone);
		sb.append("&qdid="+qdid);
		sb.append("&sqnid="+prod.getIntf_code());
		sb.append("&sign="+MD5.md5((phone+qdid+qdkey).toUpperCase()));
		sb.append("&transactionId="+transactionId);

		DXInterfaceBean bean = new DXInterfaceBean();
		bean.setPhone(phone);
		bean.setProduct_code(prod.getIntf_code());
		bean.setTransactionId(transactionId);
		bean.setOrder_code(orderCode);
		bean.setType(ISPInterfaceBean.TYPE_AHDX_HF);
		
		try {
			String result = ConnectionUrlUtil.sendPost(url, sb.toString(),"UTF-8");
			log.debug("phone:"+ phone +" productcode:"+prod.getIntf_code()+" OrderDXProductClient result:" + result);

			JSONObject jso = new JSONObject(result);
			String result_code = jso.optString("result");
			String result_desc = jso.optString("message");

			bean.setResCode(result_code);
			bean.setResMsg(result_desc);
			
			if(DXInterfaceBean.RESULT_CODE_SUCCESS.equals(result_code)){
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			}
			
			
		} catch (Exception e) {
			log.error(e, e);
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResCode(ISPInterfaceBean.RESCODE_INTERNAL_SERVER_ERROR);
			bean.setResMsg("内部服务出错，请联系管理员");
		}
		
		return bean;
	}
	
	public static void main(String[] args) {

		String qdkey = "";
		long tim = System.currentTimeMillis();
		String url = "http://61.191.47.139/flowdd";
//		String url = "http://112.74.215.147:8880/flowdd";
		String qdid = "-100022";
		String phone = "18019985420";
		
		String time = DateUtil.formatDatePure(new Date());
		String random = StringUtil.fixIntToString(RandomUtils.nextInt(999999), 6) ;
		String transactionId = qdid + time + random;
		StringBuffer sb=new StringBuffer();
		sb.append("userid="+phone);
		sb.append("&qdid="+qdid);
		sb.append("&sqnid="+"51");
		sb.append("&sign="+MD5.md5((phone+qdid+qdkey).toUpperCase()));
		sb.append("&transactionId="+transactionId);

		System.out.println(sb.toString());
		try {
			String result = ConnectionUrlUtil.sendPost(url, sb.toString(),"UTF-8");

			System.out.println(result);
//			JSONObject jso = new JSONObject(result);
//			String result_code = jso.optString("result");
//			String result_desc = jso.optString("message");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("cost："+ (System.currentTimeMillis() - tim));

	}

	@Override
	public ISPInterfaceBean queryBalance() {
		return null;
	}

	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		return null;
	}
	
}
