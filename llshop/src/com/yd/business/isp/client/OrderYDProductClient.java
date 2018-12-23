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

/**
 * @author ice
 *
 */
@Component
public class OrderYDProductClient extends OrderProductClient{
	@Resource
	private IConfigAttributeService configAttributeService;
	
	private Logger log = Logger.getLogger(OrderYDProductClient.class);
	public static final String API_METHOD_DG = "s4000Cfm";
	
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
	public ISPInterfaceBean accessOrderProduct(String orderCode,String phone,ProductBean product){
		
		ApiReqInfo info=new ApiReqInfo();
		info.setApiMethod(API_METHOD_DG);
		
		String appKey = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_APPKEY);
		String login_no = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_LOGIN_NO);
		String userName = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_USER_NAME);
		String baseURL = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_BASEURL);
		String version = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_VERSION);
		String environment = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_ENVIRONMENT);
		String privateKey = configAttributeService.getValueByCode(AttributeConstant.CODE_YD_PRIVATEKEY);
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
			log.debug("phone:"+ phone +" productcode:"+product.getIntf_code()+" OrderYDProductClient result:" + result);
			
			bean = convertResult(result);
			bean.setType(ISPInterfaceBean.TYPE_AHYD_SZ);
			bean.setService_no(phone);
			bean.setProd_prcid(product.getIntf_code());
			bean.setOrder_code(orderCode);
			
		} catch (UnsupportedEncodingException e) {
			log.error(e, e);
			bean.setResCode("systemError");
		}
		
		
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
	
	
	
	
	@Override
	public ISPInterfaceBean queryBalance() {
		return null;
	}

	@Override
	public ISPInterfaceBean queryOrderStatus(List<String> orderCode) {
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		

		long time = System.currentTimeMillis();
		ApiReqInfo info=new ApiReqInfo();
		info.setApiMethod(API_METHOD_DG);

		info.setPrivateKeyString("");
		info.setTimeStamp(DateUtil.formatDatePure(new Date()));
		info.setApiVersion("1.0");
		info.setAppKey("10001142");
		info.setBaseUrl("http://120.209.204.229:81/rest/");
		info.setUserName("yundou");
		info.setStatus("1");//1正式 2沙箱
		
		YDInterfaceBean bean = new YDInterfaceBean();
		bean.setLogin_no("L0ZZZZ161");
		bean.setService_no("15955107747");
		bean.setProd_prcid("PIBP2800");
		bean.setGroup_id(10015611539l);
		bean.setExpDate(DateUtil.formatDatePure(getLastMonthTime()));
		
		Map<String,String> paramMap=new HashMap<String,String>();
		
		JSONObject jso = new JSONObject(bean);
		
		//需要大写
		paramMap.put("body", URLEncoder.encode(jso.toString().toUpperCase(), "utf-8") );
		
		//通过jar访问接口
		info.setParamMap(paramMap);
		String result = ApiClient.callByGet(info);
		
		
		System.out.println(result);
		System.out.println("cost:" + (System.currentTimeMillis() - time ));
		
//		String str ="{\"resCode\": \"0000000\",\"resMsg\": \"ok!\",\"resource_list\": {\"EFFEXP_MODE\":\"LJSX\",\"SP_FLAG\":\"N\",\"LOGIN_ACCEPT\":10020050876559,\"EFF_DATE\":\"20160603000625\",\"EXP_DATE\":\"20991231235959\",\"IMSI_NO\":\"460023551159687\",\"BRAND_ID\":\"001\",\"USERPRC_FLAG\":\"N\"}}";
//		JSONObject jso = new JSONObject(str);
//
//		JSONObject resList = jso.getJSONObject("resource_list");
//		
//		System.out.println(resList.getLong("LOGIN_ACCEPT"));
		
		
		
		
		
	}
}
