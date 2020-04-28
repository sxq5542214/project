/**
 * 
 */
package com.yd.business.isp.client;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelProductParamBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.ZhuoweiInterfaceBean;
import com.yd.business.isp.util.ISPInterfaceUtil;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;

/**
 * 订购客户端7 =》 卓威流量
 * @author ice
 *
 */
@Component
public class OrderProductInstanceClient7 extends OrderProductClient {
	
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IChannelProductService channelProductService;
	
	private String ec_code ;
	private String key ;
	private String order_url ;
	private String query_url;
	
	
	@Override
	protected void initParams() {
		ec_code = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_ZHUOWEI_EC_CODE);
		key = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_ZHUOWEI_KEY);
		order_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_ZHUOWEI_ORDER_URL);
		query_url = configAttributeService.getValueByCode(AttributeConstant.CODE_THIRDPARTY_ZHUOWEI_ORDER_URL);
	}

	/* (non-Javadoc)
	 * @see com.yd.business.isp.client.OrderProductClient#accessOrderProduct(java.lang.String, java.lang.String, com.yd.business.product.bean.ProductBean)
	 */
	@Override
	public ISPInterfaceBean accessOrderProduct(String orderCode, String phone, ProductBean product) {
		// TODO Auto-generated method stub
		initParams();
		
		ChannelProductParamBean param = channelProductService.findChannelProductParam(product.getId(), ChannelBean.CODENUM_THIRDPARTY_ZHUOWEI);
		String product_code = param.getParam_code();
		//计算签名
		String sign = getSignString(ec_code, orderCode ,phone,product_code,key  );
		
		String url = order_url +"?ec_code="+ec_code +"&order_number="+orderCode +"&mobile=" + phone +"&product_code="+product_code+"&sign="+sign;
		System.out.println(url);
		String response ;
		ZhuoweiInterfaceBean bean = null;
		try {
			response = HttpUtil.get(url);
			
			bean = ISPInterfaceUtil.parseZhuoWeiResult(response);
			
			if(ZhuoweiInterfaceBean.RESCODE_WAIT.equals(bean.getResCode())){
				bean.setStatus(ISPInterfaceBean.STATUS_WAIT);
			}else{
				bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			}
			
		} catch (Exception e) {
			log.error(e, e);
			bean = new ZhuoweiInterfaceBean();
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResMsg(e.getMessage());
		}
		
		
		return bean;
	}

	private String getSignString(String ec_code2, String orderCode, String phone, String productCode, String key2) {
		String sign = MD5.md5(ec_code2 + orderCode + phone +productCode + key2);
		return sign.toUpperCase();
	}
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.isp.client.OrderProductClient#queryBalance()
	 */
	@Override
	public ISPInterfaceBean queryBalance() {
		// TODO Auto-generated method stub
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

}
