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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.yd.business.isp.bean.DXInterfaceBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.YunZhangTongInterfaceBean;
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
 * 订购客户端4 =》 云掌通
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient4 extends OrderProductClient{
	@Resource
	private IConfigAttributeService configAttributeService;
	
	private Logger log = Logger.getLogger(OrderProductInstanceClient4.class);
	

	protected String appKey ;
	protected String appSecret ;
	protected String order_url;
	
	protected void initParams() {
		//这是云掌通的
		appKey = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_YUNZHANGTONG_APPKEY);
		appSecret = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_YUNZHANGTONG_APPSECRET);
		order_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_YUNZHANGTONG_ORDER_URL);
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
		System.out.println(2);
		Date date = new Date();
		String requestNo = phone + "_" +DateUtil.formatDateToPureSSS(date);
		
		String random = StringUtil.fixIntToString(RandomUtils.nextInt(999999999), 10) ;
		String sign = appKey +random + appSecret;
		sign = MD5.md5(sign);
		
		YunZhangTongInterfaceBean bean = new YunZhangTongInterfaceBean();
		bean.setAppKey(appKey);
		bean.setRandom(random);
		bean.setSign(sign);
		bean.setCpBatchId(orderCode);
		bean.setPhone(phone);
		bean.setMobilePackageSize(prod.getPackage_size());
		bean.setTelecomPackageSize(prod.getPackage_size());
		bean.setUnicomPackageSize(prod.getPackage_size());
		bean.setRequestNo(requestNo);
		bean.setType(ISPInterfaceBean.TYPE_THIRDPARTY_YUNZHANGTONG);
		bean.setProduct_id(prod.getId());
		bean.setCreate_time(DateUtil.formatDateSSS(date));
		
		String json = new JSONObject(bean).toString();
		
		
		try {
			String result = HttpUtil.post(order_url, json);
			log.debug("phone:"+ phone +" yunzhangtong request:" + json);
			log.debug("phone:"+ phone +" yunzhangtong result:" + result);

			JSONObject jso = new JSONObject(result);
			String result_code = jso.optString("code");
			String result_desc = jso.optString("msg");
			int success_num = jso.optInt("successNum",0);

			bean.setResCode(result_code);
			bean.setResMsg(result_desc);
			
			
			if(YunZhangTongInterfaceBean.RESULT_CODE_SUCCESS.equals(result_code) && success_num> 0){
				
				JSONArray successMsgs = jso.getJSONArray("successMsgs");
				String serialNo = successMsgs.getJSONObject(0).getString("serialNo");
				bean.setSerialNo(serialNo);
				
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
			}else{

				JSONArray failMsgs = jso.optJSONArray("failMsgs");
				if(failMsgs != null){
					String msg = failMsgs.getJSONObject(0).getString("msg");
					String code = failMsgs.getJSONObject(0).getString("code");
	
					bean.setResCode(code);
					bean.setResMsg(msg);
				}
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
	
	
	
	
	@Override
	public ISPInterfaceBean queryBalance() {
		return null;
	}


	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		return null;
	}


	public static void main(String[] args) {
		
		
	}
	
}
