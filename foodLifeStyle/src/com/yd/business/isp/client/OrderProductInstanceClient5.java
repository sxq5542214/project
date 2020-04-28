/**
 * 
 */
package com.yd.business.isp.client;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.yd.business.isp.bean.DaHanSanTongInterfaceBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;

/**
 * 订购客户端5 =》 大汉三通
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient5 extends OrderProductClient{
	@Resource
	private IConfigAttributeService configAttributeService;
	
	private Logger log = Logger.getLogger(OrderProductInstanceClient5.class);
	

	protected String account ;
	protected String password ;
	protected String order_url;
	
	protected void initParams() {
		//这是大汉三通的
		account = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_DAHANSANTONG_ACCOUNT);
		password = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_DAHANSANTONG_PWD);
		order_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_DAHANSANTONG_ORDER_URL);
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
		String sign = account + MD5.md5(password) + time + phone;
		sign = MD5.md5(sign);
		
		DaHanSanTongInterfaceBean bean = new DaHanSanTongInterfaceBean();
		
		Date date = new Date();
		bean.setTimestamp(String.valueOf(time));
		bean.setAccount(account);
		bean.setMobiles(phone);
		bean.setSign(sign);
		bean.setPackageSize(String.valueOf(prod.getPackage_size_simple()));
		bean.setCreate_time(DateUtil.formatDateSSS(date));
		bean.setOrder_code(orderCode);
		
		String clientOrderId = phone + DateUtil.formatDateToPureSSS(date);
		bean.setClientOrderId(clientOrderId );
		
		String json = new JSONObject(bean).toString();
		
		
		try {
			String result = HttpUtil.post(order_url, json);
			log.debug("phone:"+ phone +" dahansantong request:" + json);
			log.debug("phone:"+ phone +" dahansantong result:" + result);

			JSONObject jso = new JSONObject(result);
			String result_code = jso.optString("resultCode");
			String result_desc = jso.optString("resultMsg");

			bean.setResCode(result_code);
			bean.setResMsg(result_desc);
			bean.setType(ISPInterfaceBean.TYPE_THIRDPARTY_DAHANSANTONG);
			bean.setProduct_id(prod.getId());
			
			
			if(DaHanSanTongInterfaceBean.RESULT_CODE_SUCCESS.equals(result_code)  ){
				
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
	
	
	
	
	@Override
	public ISPInterfaceBean queryBalance() {
		return null;
	}


	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		return null;
	}


	public static void main(String[] args) {
		String order_url = "http://if.dahanbank.cn/FCOrderServlet";
		String account = "admin";
		String password = "dirasd";
		String phone = "15955107747";
		String orderCode = "UserBalance_test_20160622010122947";
		ProductBean prod = new ProductBean();
		prod.setPackage_size_simple(10);
		
		long time = System.currentTimeMillis();
		String sign = account + MD5.md5(password) + time + phone;
		sign = MD5.md5(sign);
		
		DaHanSanTongInterfaceBean bean = new DaHanSanTongInterfaceBean();
		
		bean.setTimestamp(String.valueOf(time));
		bean.setAccount(account);
		bean.setMobiles(phone);
		bean.setSign(sign);
		bean.setPackageSize(String.valueOf(prod.getPackage_size_simple()));
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		String clientOrderId = phone + DateUtil.formatDateToPureSSS(new Date());
		bean.setClientOrderId(clientOrderId );
		
		
		String json = new JSONObject(bean).toString();
		
		
		try {
			String result = HttpUtil.post(order_url, json);
			System.out.println("phone:"+ phone +" dahansantong request:" + json);
			System.out.println("phone:"+ phone +" dahansantong result:" + result);

			JSONObject jso = new JSONObject(result);
			String result_code = jso.optString("resultCode");
			String result_desc = jso.optString("resultMsg");

			bean.setResCode(result_code);
			bean.setResMsg(result_desc);
			bean.setType(ISPInterfaceBean.TYPE_THIRDPARTY_DAHANSANTONG);
			bean.setProduct_id(prod.getId());
			
			
			if(DaHanSanTongInterfaceBean.RESULT_CODE_SUCCESS.equals(result_code)  ){
				
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
