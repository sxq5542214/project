/**
 * 
 */
package com.yd.business.partner.runable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.yd.basic.framework.runable.BaseRunable;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.partner.bean.PartnerBean;
import com.yd.business.partner.bean.PartnerInterfaceBean;
import com.yd.business.partner.service.IPartnerService;
import com.yd.business.partner.util.PartnerUtil;
import com.yd.factory.ServiceFactory;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;
import com.yd.util.StringUtil;

/**
 * 给合作伙伴回调的线程类
 * @author ice
 *
 */
public class PartnerCallbackRunable extends BaseRunable {
	
	private PartnerOrderProductBean bean;
	@Resource
	private IPartnerService partnerService = ServiceFactory.getService(ServiceFactory.SERVICE_PARTNERSERVICE);
	@Resource
	private IOrderService orderService = ServiceFactory.getService(ServiceFactory.SERVICE_ORDERSERVICE);
	
	private IOrderProductLogService orderProductLogService = ServiceFactory.getService(ServiceFactory.SERVICE_ORDERPRODUCTLOGSERVICE);
	
	public PartnerCallbackRunable(PartnerOrderProductBean bean){
		this.bean = bean;
		
	}
	
	@Override
	public void runMethod() {
		String url = bean.getNotify_url();
		
		PartnerBean partner = partnerService.findPartnerById(bean.getPartner_id());
		
		//封装参数
		Map<String,String> map = new HashMap<String, String>();
		map.put("partner_code", partner.getPartner_code());
		map.put("customer_id", String.valueOf(partner.getCustomer_id()));
		map.put("partner_out_trade_no", bean.getPartner_out_trade_no());
		map.put("order_account", bean.getOrder_account());
		map.put("attach", bean.getAttach());
		map.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		map.put("status", String.valueOf(bean.getStatus()));
		map.put("result_code", bean.getResult_code());
		map.put("result_desc", bean.getResult_desc());
		
		String link = PartnerUtil.createLinkString(map);
		link = link + "&secret_key=" + partner.getSecret_key();
		String sign = MD5.md5(link);
		
		map.put("sign", sign);
		map.put("sign_type", "MD5");
		
		try {
			Thread.sleep(3 * 1000);
			if(bean.getId() == null){
				bean = orderProductLogService.findPartnerOrderProductByOrderCode(bean.getOut_trade_no());
			}
			log.debug("PartnerCallbackRunable:"+ map);
			//调用回调接口
			String result = HttpUtil.post(url, map);
			log.debug("PartnerCallbackRunable result:"+ result);
			
			//有返回数据
			if(StringUtil.isNotNull(result)){
				//解析结果
				PartnerInterfaceBean response = PartnerUtil.parseNotifyResponse(result);
				bean.setNotify_desc(response.getResult_code()+","+response.getResult_desc());
				
				//判断状态
				if(response.getStatus() == PartnerOrderProductBean.STATUS_SUCCESS){
					bean.setNotify_status(PartnerOrderProductBean.NOTIFY_STATUS_SUCCESS);
					
				}else{
					bean.setNotify_status(PartnerOrderProductBean.NOTIFY_STATUS_FAILD);
				}
			}else{
				//没有返回数据
				bean.setNotify_status(PartnerOrderProductBean.NOTIFY_STATUS_FAILD);
				bean.setNotify_desc("回调后伙伴未返回数据！");
			}
			
		} catch (Exception e) {
			log.error(e, e);
			bean.setNotify_status(PartnerOrderProductBean.NOTIFY_STATUS_FAILD);
			bean.setNotify_desc("系统回调发生错误："+e.getMessage());
		}
		//记录次数和时间
		Integer notify_count = bean.getNotify_count();
		bean.setNotify_count(notify_count == null? 1: ++notify_count );
		bean.setNotify_time(DateUtil.getNowDateStrSSS());
		
		//更新伙伴定单状态
		orderService.saveOrUpdatePartnerOrderProduct(bean);

	}

}
