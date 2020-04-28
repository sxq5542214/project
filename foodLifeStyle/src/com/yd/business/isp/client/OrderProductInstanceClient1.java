/**
 * 
 */
package com.yd.business.isp.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.sitech.op.sdk.apiclient.ApiClient;
import com.sitech.op.sdk.entity.ApiReqInfo;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.YDInterfaceBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;

/**
 * 订购客户端1 =》 宿州移动
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient1 extends OrderProductClient{
	@Resource
	private IConfigAttributeService configAttributeService;
	
	private Logger log = Logger.getLogger(OrderProductInstanceClient1.class);
	public static final String API_METHOD_DG = "s4000Cfm";
	

	protected String appKey ;
	protected String login_no ;
	protected String userName ;
	protected String baseURL ;
	protected String version ;
	protected String environment ;
	protected String privateKey ;
	
	protected void initParams(){
		//这些默认是宿州的
		appKey = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_APPKEY);
		login_no = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_LOGIN_NO);
		userName = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_USER_NAME);
		baseURL = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_BASEURL);
		version = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_VERSION);
		environment = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_ENVIRONMENT);
		privateKey = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_PRIVATEKEY);
	}
	
	
	/**
	 * 访问订购接口
	 * @param phone
	 * @param code
	 * @return
	 */
	@Override
	public ISPInterfaceBean accessOrderProduct(String orderCode,String phone,ProductBean product){
		
		ApiReqInfo info=new ApiReqInfo();
		info.setApiMethod(API_METHOD_DG);
		
		initParams();
		
		String time = DateUtil.formatDatePure(new Date());
		
		info.setTimeStamp(time);
		info.setApiVersion(version);
		info.setAppKey(appKey);
		info.setBaseUrl(baseURL);
		info.setPrivateKeyString(privateKey);
		info.setStatus(environment);
		info.setUserName(userName);
		
		YDInterfaceBean bean = new YDInterfaceBean();
		bean.setLogin_no(login_no);
		bean.setService_no(phone);
		bean.setProd_prcid(product.getIntf_code());
		bean.setGroup_id(Long.valueOf(product.getIntf_param()));
		//设置失效时间为月底
		bean.setExpDate(DateUtil.formatDatePure(getLastMonthTime()));
		
		Map<String,String> paramMap=new HashMap<String,String>();

		JSONObject jso = new JSONObject(bean);
		//需要大写
		try {
			paramMap.put("body",URLEncoder.encode(jso.toString().toUpperCase(), "utf-8") );//通过jar访问接口
			info.setParamMap(paramMap);
			String result = ApiClient.callByGet(info);
			
			//超时啥的，可能返回失败，先查询是否已订购成功，如果没成功则再次调用
			if(StringUtil.isNull(result)){
				result = ApiClient.callByGet(info);
//				queryOrderStatus(orderCode);
				
			}
			
			log.debug("phone:"+ phone +" productcode:"+product.getIntf_code()+" OrderYDProductClient result:" + result);
			
			bean = convertResult(result);
			bean.setType(ISPInterfaceBean.TYPE_AHYD_SZ);
			bean.setService_no(phone);
			bean.setProd_prcid(product.getIntf_code());
			
		} catch (Exception e) {
			log.error(e, e);
			bean.setResCode("systemError");
			bean.setResMsg(e.getMessage());
		}
		bean.setOrder_code(orderCode);
		
		
		if(YDInterfaceBean.RESCODE_SUCCESS.equals(bean.getResCode())){
			bean.setStatus(YDInterfaceBean.STATUS_SUCCESS);
		}else{
			bean.setStatus(YDInterfaceBean.STATUS_FAILD);
		}
		
		
		return bean;
	}
	
	/**
	 * 获取本月最后一天的最后一秒
	 * @return
	 */
	private static Date getLastMonthTime(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) +1;
		int day = 30;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;

		case 2:
			if(c.get(Calendar.YEAR) % 4 == 0)
				day = 29;
			else {day = 28;}
			break;
		}

		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		
		return c.getTime();
	}
	
	/**
	 * 转换接口返回的数据
	 * @param result
	 * @return
	 */
	private YDInterfaceBean convertResult(String result){
		
		YDInterfaceBean bean = new YDInterfaceBean();
		if(StringUtil.isNull(result)){

			bean.setResCode("-9999");
			bean.setResMsg("运营商未返回数据,请稍后重试");
			return bean;
		}
		
		
		JSONObject jso = null;
		try{
			jso = new JSONObject(result);
		}catch (Exception e) {
			log.error(e, e);
			jso = new JSONObject();
			jso.append("resCode","-99999");
			jso.append("resMsg","接口返回异常："+result);
		}
		bean.setResCode(jso.getString("resCode"));
		bean.setResMsg(jso.getString("resMsg"));
		
		if(bean.getResCode().equals(YDInterfaceBean.RESCODE_SUCCESS))
		{
			JSONObject resList = jso.getJSONObject("resource_list");
			
			bean.setLoginAccept(String.valueOf(resList.optLong("LOGIN_ACCEPT")));
			bean.setEffDate(resList.optString("EFF_DATE"));
			bean.setExpDate(resList.optString("EXP_DATE"));
			bean.setBrandId(resList.optString("BRAND_ID"));
			bean.setSpFlag(resList.optString("SP_FLAG"));
			bean.setEffexpMode(resList.optString("EFFEXP_MODE"));
			bean.setImsiNo(resList.optString("IMSI_NO"));
			bean.setUserprcFlag(resList.optString("USERPRC_FLAG"));
		}
		return bean;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
//		System.out.println("cost:" + (System.currentTimeMillis() - time ));
//		String str ="{\"resCode\": \"0000000\",\"resMsg\": \"ok!\",\"resource_list\": {\"EFFEXP_MODE\":\"LJSX\",\"SP_FLAG\":\"N\",\"LOGIN_ACCEPT\":10020050876559,\"EFF_DATE\":\"20160603000625\",\"EXP_DATE\":\"20991231235959\",\"IMSI_NO\":\"460023551159687\",\"BRAND_ID\":\"001\",\"USERPRC_FLAG\":\"N\"}}";
//		JSONObject jso = new JSONObject(str);
//
//		JSONObject resList = jso.getJSONObject("resource_list");
//		
//		System.out.println(resList.getLong("LOGIN_ACCEPT"));
		
		
		query5();
//		delete();
		
		
	}
	
	private static void delete() throws UnsupportedEncodingException{
		

//		删除的
		long time = System.currentTimeMillis();
		ApiReqInfo info=new ApiReqInfo();
		info.setApiMethod(API_METHOD_DG);

		info.setPrivateKeyString("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMqDXLzNanLYEXOI2zOeu+hthno2uLWDwjAhSGxq8mn8kfsIdJrXThsaqNDu+ZyfgjGOLL6TFt6zNGgRFUrNuzYv6Ne9JnYmuzi4vWUI5arMD7rS+UvDlfA3cOiB+dLHsnYDD5r2c463kRqXCo7oaeyIZypZ75E7acjfRf87H1cjAgMBAAECgYBY1gk/dWjl2izFntpxqYdKrYkZYZnjXlo4HNGVZdDTd41eLtZf+mTz9NzrUnnDICHPtUuhigq1RK2PnTbW6qrdkI/98CHZZp6iQqW0TJtjn85g2Pm7oE8BsD+ravf96wktnKGx05YoecLsTVl0hMyHoMCRn4WxBwCn6jI/g2TOmQJBAPx1onrehx3mO+5L/xYWAJi63wkx742Udc7IZfG4Vz07MUOQgwnrJNanetUvcTJrwGVAwdzdwbZm1RUM3U6h2ncCQQDNWmm7orP7yU49O7Qg7Bqw+eaKuD5e/0dbLGQWCSTRc/WK9MzPh9EBDgslHkj58+kS5bQdcI0hEYerv/8DBme1AkBnxQqGKpWrSolzx7UQJPcV0CoBI49tyIor9t9AXh8twfjb5riWCRtZR/oLSrbinLu2YsnMuTH4gYgwYNtOiOS9AkAZX8yk1MvslslGI7R0RPhfWeG0zmNWlbCZgXi0iFi2UN/MVcdg9WVL/hI+eemGiS8JmLySzq+BoIrlHApEs2vpAkBGiyYZ2xHBq2Oz4f3C/GGvKSr6dvpudgxRPisILdx6FnpFO/Zav5u3jgBtrGMHQpkXFzH5/gIMfZcpDExeL9Lm");
		info.setTimeStamp(DateUtil.formatDatePure(new Date()));
		info.setApiVersion("1.0");
		info.setAppKey("10001142");
		info.setBaseUrl("http://120.209.204.229:81/rest/");
		info.setUserName("yundou");
		info.setStatus("1");//1正式 2沙箱
		
		YDInterfaceBean bean = new YDInterfaceBean();
		bean.setLogin_no("L0ZZZZ161");
		bean.setService_no("18326602779");
		bean.setProd_prcid("PIBP2812");
		bean.setGroup_id(10015612915l);
		bean.getBusi_info().setOperate_type("D"); //删除
		
		bean.setEffDate("20160603000408");
		bean.setExpDate("20170228235900");
//		bean.setExpDate(DateUtil.formatDatePure(getLastMonthTime()));
		
		Map<String,String> paramMap=new HashMap<String,String>();
		
		JSONObject jso = new JSONObject(bean);
		
		//需要大写
		paramMap.put("body", URLEncoder.encode(jso.toString().toUpperCase(), "utf-8") );
		
		//通过jar访问接口
		info.setParamMap(paramMap);
		String result = ApiClient.callByGet(info);
		
		System.out.println(result);
		
	}

	
	private static void query(){

//		查询的
		try{
			long time = System.currentTimeMillis();
			ApiReqInfo info=new ApiReqInfo();
			info.setApiMethod("sQUsrMbr");
	
			info.setPrivateKeyString("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMqDXLzNanLYEXOI2zOeu+hthno2uLWDwjAhSGxq8mn8kfsIdJrXThsaqNDu+ZyfgjGOLL6TFt6zNGgRFUrNuzYv6Ne9JnYmuzi4vWUI5arMD7rS+UvDlfA3cOiB+dLHsnYDD5r2c463kRqXCo7oaeyIZypZ75E7acjfRf87H1cjAgMBAAECgYBY1gk/dWjl2izFntpxqYdKrYkZYZnjXlo4HNGVZdDTd41eLtZf+mTz9NzrUnnDICHPtUuhigq1RK2PnTbW6qrdkI/98CHZZp6iQqW0TJtjn85g2Pm7oE8BsD+ravf96wktnKGx05YoecLsTVl0hMyHoMCRn4WxBwCn6jI/g2TOmQJBAPx1onrehx3mO+5L/xYWAJi63wkx742Udc7IZfG4Vz07MUOQgwnrJNanetUvcTJrwGVAwdzdwbZm1RUM3U6h2ncCQQDNWmm7orP7yU49O7Qg7Bqw+eaKuD5e/0dbLGQWCSTRc/WK9MzPh9EBDgslHkj58+kS5bQdcI0hEYerv/8DBme1AkBnxQqGKpWrSolzx7UQJPcV0CoBI49tyIor9t9AXh8twfjb5riWCRtZR/oLSrbinLu2YsnMuTH4gYgwYNtOiOS9AkAZX8yk1MvslslGI7R0RPhfWeG0zmNWlbCZgXi0iFi2UN/MVcdg9WVL/hI+eemGiS8JmLySzq+BoIrlHApEs2vpAkBGiyYZ2xHBq2Oz4f3C/GGvKSr6dvpudgxRPisILdx6FnpFO/Zav5u3jgBtrGMHQpkXFzH5/gIMfZcpDExeL9Lm");
			info.setTimeStamp(DateUtil.formatDatePure(new Date()));
			info.setApiVersion("1.0");
			info.setAppKey("10001142");
			info.setBaseUrl("http://120.209.204.229:81/rest/");
			info.setUserName("yundou");
			info.setLoginNo("L0ZZZZ161");
			info.setStatus("1");//1正式 2沙箱
			
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("PHONE_NO", "15056199266");
			paramMap.put("GROUP_ID", "10015611627");
			paramMap.put("TEAM_ID", "0");
			paramMap.put("PAGE_NUM", "1");
			paramMap.put("PAGE_AMOUNT", "10");
			paramMap.put("OPR_INFO",  URLEncoder.encode("{\"LOGIN_NO\":\"L0ZZZZ161\"}", "utf-8"));
			
			//需要大写
	//		paramMap.put("body", URLEncoder.encode(jso.toString().toUpperCase(), "utf-8") );
			
			//通过jar访问接口
			info.setParamMap(paramMap);
			String result = ApiClient.callByGet(info);
			
			System.out.println(result);
			System.out.println("cost:" + (System.currentTimeMillis() - time ));

		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	private static void query5(){

//		查询的
		try{
			long time = System.currentTimeMillis();
			ApiReqInfo info=new ApiReqInfo();
			info.setApiMethod("sUserOrdInfoQry");
	
			info.setPrivateKeyString("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMqDXLzNanLYEXOI2zOeu+hthno2uLWDwjAhSGxq8mn8kfsIdJrXThsaqNDu+ZyfgjGOLL6TFt6zNGgRFUrNuzYv6Ne9JnYmuzi4vWUI5arMD7rS+UvDlfA3cOiB+dLHsnYDD5r2c463kRqXCo7oaeyIZypZ75E7acjfRf87H1cjAgMBAAECgYBY1gk/dWjl2izFntpxqYdKrYkZYZnjXlo4HNGVZdDTd41eLtZf+mTz9NzrUnnDICHPtUuhigq1RK2PnTbW6qrdkI/98CHZZp6iQqW0TJtjn85g2Pm7oE8BsD+ravf96wktnKGx05YoecLsTVl0hMyHoMCRn4WxBwCn6jI/g2TOmQJBAPx1onrehx3mO+5L/xYWAJi63wkx742Udc7IZfG4Vz07MUOQgwnrJNanetUvcTJrwGVAwdzdwbZm1RUM3U6h2ncCQQDNWmm7orP7yU49O7Qg7Bqw+eaKuD5e/0dbLGQWCSTRc/WK9MzPh9EBDgslHkj58+kS5bQdcI0hEYerv/8DBme1AkBnxQqGKpWrSolzx7UQJPcV0CoBI49tyIor9t9AXh8twfjb5riWCRtZR/oLSrbinLu2YsnMuTH4gYgwYNtOiOS9AkAZX8yk1MvslslGI7R0RPhfWeG0zmNWlbCZgXi0iFi2UN/MVcdg9WVL/hI+eemGiS8JmLySzq+BoIrlHApEs2vpAkBGiyYZ2xHBq2Oz4f3C/GGvKSr6dvpudgxRPisILdx6FnpFO/Zav5u3jgBtrGMHQpkXFzH5/gIMfZcpDExeL9Lm");
			info.setTimeStamp(DateUtil.formatDatePure(new Date()));
			info.setApiVersion("1.0");
			info.setAppKey("10001142");
			info.setBaseUrl("http://120.209.204.229:81/rest/");
			info.setUserName("yundou");
			info.setLoginNo("L0ZZZZ161");
			info.setStatus("1");//1正式 2沙箱
			
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("serviceNo", "15955107747");
			
			//需要大写
	//		paramMap.put("body", URLEncoder.encode(jso.toString().toUpperCase(), "utf-8") );
			
			//通过jar访问接口
			info.setParamMap(paramMap);
			String result = ApiClient.callByGet(info);
			
			System.out.println(result);
			System.out.println("cost:" + (System.currentTimeMillis() - time ));

		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public ISPInterfaceBean queryBalance() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		

		long time = System.currentTimeMillis();
		ApiReqInfo info=new ApiReqInfo();
		info.setApiMethod(API_METHOD_DG);

		info.setTimeStamp(DateUtil.formatDatePure(new Date()));
		info.setApiVersion(version);
		info.setAppKey(appKey);
		info.setBaseUrl(baseURL);
		info.setPrivateKeyString(privateKey);
		info.setStatus(environment);
		info.setUserName(userName);
		
		
		YDInterfaceBean bean = new YDInterfaceBean();
		bean.setLogin_no(login_no);
		bean.setService_no("15955622069");
		bean.setProd_prcid("PIBP2807");
		bean.setGroup_id(10015612829l);
		bean.getBusi_info().setOperate_type("D"); //删除
		
		bean.setEffDate("20170101000000");
		bean.setExpDate("20991231235959");
//		bean.setExpDate(DateUtil.formatDatePure(getLastMonthTime()));
		
		Map<String,String> paramMap=new HashMap<String,String>();
		
		JSONObject jso = new JSONObject(bean);
		
		//需要大写
		try {
			paramMap.put("body", URLEncoder.encode(jso.toString().toUpperCase(), "utf-8") );
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
		}
		
		//通过jar访问接口
		info.setParamMap(paramMap);
		String result = ApiClient.callByGet(info);
		
		
		System.out.println(result);
		System.out.println("cost:" + (System.currentTimeMillis() - time ));
		
		
		return null;
	}
	
	
}
