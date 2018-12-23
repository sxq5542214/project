/**
 * 
 */
package com.yd.business.isp.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import com.yd.business.isp.bean.DaHanSanTongInterfaceBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.isp.bean.YunZhangTongInterfaceBean;
import com.yd.business.isp.bean.ZhuoweiInterfaceBean;

/**
 * @author ice
 *
 */
public class ISPInterfaceUtil {
	private static Logger log = Logger.getLogger(ISPInterfaceUtil.class);
	
	/**
	 * 转换云掌通的json 为bean对象
	 * @param json
	 * @return
	 */
	public static YunZhangTongInterfaceBean convertJsonToYunZhangTongBean(String json){
		YunZhangTongInterfaceBean bean = new YunZhangTongInterfaceBean();
		
		JSONObject jso = new JSONObject(json);
		String appKey = jso.getString("appKey");
		String appSecret = jso.getString("appSecret");
		String phone = jso.getString("phone");
		String serialNo = jso.getString("serialNo");
		Integer status = jso.getInt("status");
		String cpBatchId = jso.getString("cpBatchId");
		
		bean.setAppKey(appKey);
		bean.setAppSecret(appSecret);
		bean.setPhone(phone);
		bean.setRequestNo(serialNo);
		if(status == YunZhangTongInterfaceBean.STATUS_CALLBACK_SUCCESS){
			bean.setStatus(1);
			bean.setRemark("订购成功");
		}else{
			bean.setStatus(-1);
			bean.setRemark("异步订购返回失败");
		}
		bean.setCpBatchId(cpBatchId);
		
		return bean;
	}
	
	/**
	 * 转换云掌通bean为 返回的json字符串
	 * @param bean
	 * @return
	 */
	public static String convertYunZhangTongBeanToResult(YunZhangTongInterfaceBean bean){
		
		if(bean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS)
		{
			bean.setCode(YunZhangTongInterfaceBean.RESULT_CODE_SUCCESS);
			bean.setMsg(bean.getRemark());
		}else{
			bean.setCode(String.valueOf(bean.getStatus()));
			bean.setMsg(bean.getRemark());
		}
		JSONObject jso = new JSONObject(bean);
		return jso.toString();
	}
	
	

	/**
	 * 转换大汉三通的json 为bean对象
	 * @param json
	 * @return
	 */
	public static DaHanSanTongInterfaceBean convertJsonToDaHanSanTongBean(String json){
		DaHanSanTongInterfaceBean bean = new DaHanSanTongInterfaceBean();
		
		JSONObject jso = new JSONObject(json);
		String clientOrderId = jso.getString("clientOrderId");
		String mobile = jso.getString("mobile");
		String reportTime = jso.getString("reportTime");
		String callBackTime = jso.getString("callBackTime");
		String clientSubmitTime = jso.getString("clientSubmitTime");
		Integer status = jso.getInt("status");
		String errorCode = jso.getString("errorCode");
		String errorDesc = jso.getString("errorDesc");
		String discount = jso.getString("discount");
		String costMoney = jso.getString("costMoney");
		
		bean.setClientOrderId(clientOrderId);
		bean.setMobiles(mobile);
		bean.setReportTime(reportTime);
		bean.setCallBackTime(callBackTime);
		bean.setClientSubmitTime(clientSubmitTime);
		bean.setStatus(status);
		bean.setResCode(errorCode);
		bean.setResMsg(errorDesc);
		bean.setDiscount(discount);
		bean.setCostMoney(costMoney);
		bean.setOrder_code(clientOrderId);
		
		if(status == DaHanSanTongInterfaceBean.STATUS_CALLBACK_SUCCESS){
			bean.setStatus(1);
			bean.setRemark("订购成功");
		}else{
			bean.setStatus(-1);
			bean.setRemark("异步订购返回失败");
		}
		
		return bean;
	}
	
	/**
	 * 转换云掌通bean为 返回的json字符串
	 * @param bean
	 * @return
	 */
	public static String convertDaHanSanTongBeanToResult(DaHanSanTongInterfaceBean bean){
		
		if(bean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS)
		{
			bean.setResultCode(DaHanSanTongInterfaceBean.RESULTCODE_CALLBACK_SUCCESS);
			bean.setResultMsg(bean.getRemark());
		}else{
			bean.setResultCode(DaHanSanTongInterfaceBean.RESULTCODE_CALLBACK_FAILD);
			bean.setResultMsg(bean.getRemark());
		}
		JSONObject jso = new JSONObject(bean);
		return jso.toString();
	}
	
	/**
	 * 转换弯流科技的json 为bean对象
	 * @param json
	 * @return
	 */
	public static WanLiuInterfaceBean convertJsonToWanLiuInterBean(String json){
		WanLiuInterfaceBean bean = new WanLiuInterfaceBean();
		JSONObject jso = new JSONObject(json);
		bean.setUpdated(jso.getString("updated"));
		bean.setTimestamp(jso.get("timestamp").toString());
//		req_sn 是 请求流水号 
//		order_sn 是 订单流水号 
//		prod_code 是 产品编码 
//		order_stat 是 订单状态，0：正在充值，1：充值失败，99：充值成功 
//		mob_no 是 处理的手机号码 
//		err_code 是 错误码：9999 表示成功，其它：表示失败 
//		err_msg 是 错误信息 
		bean.setReq_sn(jso.getString("req_sn"));
		bean.setOrder_sn(jso.getString("order_sn"));
		bean.setRep_prod_code(jso.getString("prod_code"));
		bean.setOrder_stat(jso.get("order_stat").toString());
		bean.setRep_mob_no(jso.getString("mob_no"));
		bean.setErr_code(jso.getString("err_code"));
		bean.setErr_msg(jso.getString("err_msg"));
		if(WanLiuInterfaceBean.RESULTCODE_CALLBACK_SUCCESS.equals(bean.getErr_code())){
			bean.setStatus(WanLiuInterfaceBean.STATUS_SUCCESS);
			bean.setRemark("订购成功");
		}else{
			bean.setStatus(WanLiuInterfaceBean.STATUS_FAILD);
			bean.setRemark("异步订购返回失败");
		}
		return bean;
	} 
	/**
	 * 转换弯流科技bean为 返回的json字符串
	 * @param bean
	 * @return
	 */
	public static String convertWanLiuKeJiBeanToResult(WanLiuInterfaceBean bean){
		
		if(bean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS)
		{
			bean.setCode(WanLiuInterfaceBean.RETURNCODE_CALLBACK_SUCCESS);
			bean.setResultMsg(bean.getRemark());
		}else{
			bean.setCode(WanLiuInterfaceBean.RETURNCODE_CALLBACK_FAIL);
			bean.setResultMsg(bean.getRemark());
		}
		JSONObject jso = new JSONObject(bean);
		return jso.toString();
	}
	

	public static ZhuoweiInterfaceBean parseZhuoWeiResult(String result) throws DocumentException{
		ZhuoweiInterfaceBean bean = new ZhuoweiInterfaceBean();
		
		Document doc = DocumentHelper.parseText(result);
		Element root = doc.getRootElement();
		
		for(Element e : (List<Element>)root.elements()){

			if(e.getName().equalsIgnoreCase("action")){
				bean.setAction(e.getText());
			}
			if(e.getName().equalsIgnoreCase("ret_code")){
				bean.setResCode(e.getText());
			}
			if(e.getName().equalsIgnoreCase("ret_msg")){
				bean.setResMsg(e.getText());
			}
			if(e.getName().equalsIgnoreCase("req_order_number")){
				bean.setIsp_order_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("ec_code")){
				bean.setEc_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("oder_number")){
				bean.setOrder_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("mobile")){
				bean.setPhone(e.getText());
			}
			if(e.getName().equalsIgnoreCase("sign")){
				bean.setSign(e.getText());
			}
		}
		
		return bean;
	}
	
}
