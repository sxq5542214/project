package com.yd.business.isp.test;

import java.io.IOException;
import java.math.BigDecimal;

import org.json.JSONObject;

import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.product.bean.ProductBean;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;
import com.yd.factory.ServiceFactory;

public class CallISP extends Thread {
	public void run(){
		String order_url = "http://api.oa45.com/tra/prodtx/balqry";
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
		
		String json = new JSONObject(bean).toString();
		System.out.println(json);
		try {
			String result = HttpUtil.post(order_url, json);
			System.out.println(result);
			JSONObject jso = new JSONObject(result);
			boolean code = jso.getBoolean("code");
			bean.setCode(String.valueOf(code));
			if(code){
				JSONObject dataInfo = (JSONObject) jso.get("data");
				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
				bean.setTime(dataInfo.getString("time"));
				bean.setBalance(dataInfo.getString("balance"));
				IChannelService channelService = ServiceFactory.getService(ServiceFactory.SERVICE_CHANNELSERVICE);
				channelService.updateChannelBalance(ChannelBean.CODENUM_THIRDPARTY_WANLIUKEJI,bean.getBalance());
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
	 public static double roundForNumber(double v,int scale){
	        if(scale<0){
	            throw new IllegalArgumentException("The scale must be a positive integer or zero");
	        }
	        BigDecimal b = new BigDecimal(Double.toString(v));
	        BigDecimal one = new BigDecimal("1");
	        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
}
