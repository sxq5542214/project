/**
 * 
 */
package com.yd.business.channel.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelCustomerBean;
import com.yd.business.channel.bean.ChannelProductBean;
import com.yd.business.channel.bean.ChannelProductParamBean;
import com.yd.business.channel.bean.ChannelServerBean;
import com.yd.business.channel.dao.IChannelProductDao;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.JieTuoInterfaceBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.isp.service.IAccessISPOrderInterfaceService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.RandomUtil;

/**
 * @author ice
 *
 */
@Service("channelProductService")
public class ChannelProductServiceImpl extends BaseService implements IChannelProductService {
	
	@Resource
	private IChannelProductDao channelProductDao;
	@Resource
	private IChannelService channelService;
	@Resource
	private IAccessISPOrderInterfaceService accessISPOrderInterfaceService;
	@Resource
	private IProductService productService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
//	@Resource
//	private ThreadPoolTaskExecutor taskExecutor;
	
	
	/**
	 * 通道方式进行商品订购
	 * @param customer_id
	 * @param order_code
	 * @param phone
	 * @param param
	 * @param product_id
	 * @return
	 */
	@Override
	public ISPInterfaceBean orderProductByChannel(int customer_id,String order_code,String phone,String param,int product_id){
		ISPInterfaceBean isp = null;
		if("testServer".equals(BaseContext.getPopsValueByName(BaseContext.POPS_SERVER_NAME))){
			isp = new ISPInterfaceBean();
			isp.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
			isp.setResCode(ISPInterfaceBean.RESCODE_SUCCESS);
			isp.setResMsg("测试服务，订购成功");
			isp.setChannel_type(ChannelBean.TYPE_SYNC);
			return isp;
		}
		
		ChannelBean channel = channelService.findChannel(customer_id, phone, product_id);
		
		if(channel != null && channel.getStatus() == ChannelBean.STATUS_ENABLE){
			
			ChannelServerBean channelServer = channelService.getRandomChannelServer(channel.getId());
			
			String server_name = BaseContext.getPopsValueByName(BaseContext.POPS_SERVER_NAME);
			
			if(channelServer == null){

				isp = new ISPInterfaceBean();
				isp.setStatus(ISPInterfaceBean.STATUS_FAILD);
				isp.setResCode(ISPInterfaceBean.RESCODE_CHANNEL_SERVERNOTFOUND);
				isp.setResMsg("通道服务未找到");
				return isp;
			}
			
			if(channelServer.getServer_name().equalsIgnoreCase(server_name)){
				//如果就是本机，则直接调用方法
				
				isp = orderProductByChannel(channelServer.getChannel_id(), order_code, phone, param, product_id, server_name);
				
			}else{
				//如果不是本机，则启动http接口调用的方式
				isp = accessChannelServer(channelServer, phone, product_id,order_code);
				
			}
			isp.setChannel_id(channel.getId());
			isp.setChannel_type(channel.getChannel_type());
		}else if(channel.getStatus() != ChannelBean.STATUS_ENABLE){

			isp = new ISPInterfaceBean();
			isp.setStatus(ISPInterfaceBean.STATUS_FAILD);
			isp.setResCode(ISPInterfaceBean.RESCODE_CHANNEL_MAINTAIN);
			isp.setResMsg("通道维护");
		}else{
			isp = new ISPInterfaceBean();
			isp.setStatus(ISPInterfaceBean.STATUS_FAILD);
			isp.setResCode(ISPInterfaceBean.RESCODE_CHANNEL_NOTFOUND);
			isp.setResMsg("通道未找到");
		}
		
		return isp;
	}
	
	/**
	 * 访问通道服务
	 * @param server
	 * @param phone
	 * @param product_id
	 * @return
	 */
	private ISPInterfaceBean accessChannelServer(ChannelServerBean server,String phone,int product_id,String order_code){
		
		String url = server.getServer_url();
		String server_name = BaseContext.getPopsValueByName(BaseContext.POPS_SERVER_NAME);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("phone", phone);
		params.put("order_code", order_code);
		params.put("product_id", String.valueOf(product_id));
		params.put("channel_id", String.valueOf(server.getChannel_id()));
		params.put("server_name", server_name);
		params.put("param", "test");
		ISPInterfaceBean isp = new ISPInterfaceBean();
		isp.setStatus(-1);
		isp.setResMsg("返回错误");
		isp.setResCode(ISPInterfaceBean.RESCODE_CHANNEL_ACCESSERROR);
		
		 try {
			String result = HttpUtil.post(url, params);
			
			JSONObject json = new JSONObject(result);
			int status = json.getInt("status");
			String resMsg = json.optString("resMsg");
			String resCode = json.optString("resCode");
			
			isp.setStatus(status);
			isp.setResCode(resCode);
			isp.setResMsg(resMsg);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return isp;
	}
	
	/**
	 * 调用具体的订购方法
	 * @param channel_id
	 * @param order_code
	 * @param phone
	 * @param param
	 * @param product_id
	 * @param server_name
	 * @return
	 */
	@Override
	public ISPInterfaceBean orderProductByChannel(int channel_id,String order_code,String phone,String param,int product_id,String server_name){
		
		ISPInterfaceBean bean = accessISPOrderInterfaceService.accessISPOrderInterface(channel_id, order_code, phone, product_id, server_name);

		ChannelBean channel = channelService.findChannelById(channel_id);
		
		//同步的通道，成功才去扣减
		if(bean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS && channel.getChannel_type() == ChannelBean.TYPE_SYNC){
			//扣减余额
			minusChannelBalance(channel_id, product_id,order_code);
		}else if(bean.getStatus() == ISPInterfaceBean.STATUS_FAILD){
			checkChannelAlive(channel_id,product_id);
		}
		
		
		return bean ;
	}
	
	/**
	 * 扣减通道余额
	 * @param channel_id
	 * @param product_id
	 */
	@Override
	public void minusChannelBalance(int channel_id,int product_id,String order_code){
		ChannelBean channel;
		switch (channel_id) {
		case ChannelBean.CODENUM_YD_SUZHOU:
			ProductBean prod = productService.findProductById(product_id);
			channelService.updateChannelCalcBalance(channel_id, -prod.getProduct_price(),order_code);
			break;
		case ChannelBean.CODENUM_DX_HEFEI:
			
			break;
		case ChannelBean.CODENUM_DX_FUYANG:
			
			break;
		case ChannelBean.CODENUM_THIRDPARTY_YUNZHANGTONG:

			break;
		case ChannelBean.CODENUM_THIRDPARTY_DAHANSANTONG:

			break;
		case ChannelBean.CODENUM_THIRDPARTY_WANLIUKEJI:

			break;
		case ChannelBean.CODENUM_THIRDPARTY_ZHUOWEI:

			prod = productService.findProductById(product_id);
			ChannelProductParamBean param = findChannelProductParam(prod.getId(), channel_id);
			channelService.updateChannelCalcBalance(channel_id, -(prod.getProduct_price() * param.getDiscount())/1000 ,order_code);

			break;
		case ChannelBean.CODENUM_THIRDPARTY_NANJINGSHENGSHI:
			prod = productService.findProductById(product_id);
			channel = channelService.findChannelById(channel_id);
			channelService.updateChannelCalcBalance(channel_id, -(prod.getProduct_price() * channel.getDiscount())/100 ,order_code);

			break;
		case ChannelBean.CODENUM_THIRDPARTY_JIETUO:
			prod = productService.findProductById(product_id);
			param = findChannelProductParam(prod.getId(), channel_id);
			channelService.updateChannelCalcBalance(channel_id, -(prod.getProduct_price() * param.getDiscount())/1000 ,order_code);

			break;
		default:
			break;
		}
	}
	
	/**
	 * 检查通道是否正常
	 * @param channel_id
	 * @param product_id
	 */
	@Override
	public void checkChannelAlive(int channel_id,int product_id){

		int interval_time = configAttributeService.getIntValueByCode(AttributeConstant.CODE_CHANNEL_CHECK_INTERVAL_TIME);
		if(interval_time == 0) interval_time = 1000 * 60 * 10;
		
		MsgCenterUserActionBean action = new MsgCenterUserActionBean();
		action.setOpenid(UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY);
		action.setAction_type(MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_CHANNEL_FAILD);
		action.setOrderby(" and action_param like '%\"id\":"+channel_id+",%' and action_param not like '%\"discount\":"+product_id+"%' order by id desc limit 1");
		
		//查通道失败的情况
		List<MsgCenterUserActionBean> actionList = msgCenterActionService.queryUserAction(action );
		if(actionList.size() > 0){
			MsgCenterUserActionBean lastAction = actionList.get(0);
			Date date;
			try {
				//上一次发送时间与本次相比少于10分钟，就不发
				date = DateUtil.parseDateSSS(lastAction.getCreate_time());
				if( System.currentTimeMillis() - date.getTime() <= interval_time ){
					return ;
				}
			} catch (ParseException e) {
				log.error(e, e);
			}
		}
		//查产品失败的情况
		action.setOrderby(" and action_param like '%\"id\":"+channel_id+"%' and action_param like '%\"discount\":"+product_id+"%' order by id desc limit 1");
		actionList = msgCenterActionService.queryUserAction(action );
		if(actionList.size() > 0){
			MsgCenterUserActionBean lastAction = actionList.get(0);
			Date date;
			try {
				//上一次发送时间与本次相比少于10分钟，就不发
				date = DateUtil.parseDateSSS(lastAction.getCreate_time());
				if( System.currentTimeMillis() - date.getTime() <= interval_time ){
					return ;
				}
			} catch (ParseException e) {
				log.error(e, e);
			}
		}
		
		
		//连续的多少次失败
		int count = configAttributeService.getIntValueByCode(AttributeConstant.CODE_CHANNEL_CHECK_COUNT);
		if(count == 0) count = 10;
		
		ChannelBean channel = channelService.findChannelById(channel_id);
		ISPInterfaceBean bean = new ISPInterfaceBean();
		//先检查通道是不是所有产品都失败
		bean.setTable_name(channel.getTable_name());
		bean.setOrderby("and status != 0  order by id desc limit "+count);
		List<ISPInterfaceBean> list = accessISPOrderInterfaceService.queryInterfaceList(bean);
		
		boolean needAlarm = false;
		
		//只要有不是失败的，就认为通道整体没有问题
		for(int i = 0 ; i < list.size() ; i++){
			ISPInterfaceBean b = list.get(i);
			if(b.getStatus() != ISPInterfaceBean.STATUS_FAILD){
				needAlarm = false;
				break;
			}
			if(i == list.size() -1){
				needAlarm = true;
			}
		}
		
		//再检查这个通道对应的产品是不是都失败
		if(needAlarm == false){
			bean.setProduct_id(product_id);
			list = accessISPOrderInterfaceService.queryInterfaceList(bean);
			
			for(int i = 0 ; i < list.size() ; i++){
				ISPInterfaceBean b = list.get(i);
				if(b.getStatus() != ISPInterfaceBean.STATUS_FAILD){
					needAlarm = false;
					break;
				}
				if(i == list.size() -1){
					needAlarm = true;
				}
			}
		}
		
		//有上面的两种情况，发告警
		if(needAlarm){
			
			if(bean.getProduct_id() != null){
				ProductBean product = productService.findProductById(product_id);
				channel.setClass_name(product.getName() +" 连续"+ count +" 次失败，请注意！");
				channel.setDiscount(product_id);
			}else{
				channel.setClass_name(" 连续"+ count +" 次失败，请注意！");
			}
			
			msgCenterActionService.saveAndHandleUserAction(UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_CHANNEL_FAILD, null, channel);
		}
		
	}
	
	
	/**
	 * 根据条件返回随机的一条 产品通道，如果没有则返回空
	 */
	@Override
	public ChannelProductBean getRandomChannelProduct(String province,int product_id,int customer_id){
		
		//查询条件
		ChannelProductBean channelProduct = new ChannelProductBean();
		channelProduct.setProvince(province);
		channelProduct.setProduct_id(product_id);
		channelProduct.setCustomer_id(customer_id);
		channelProduct.setStatus(ChannelProductBean.STATUS_ENABLE);
		List<ChannelProductBean> channelProductList = queryChannelProduct(channelProduct);
		int weight = 0 ; 
		channelProduct = null; //重置为空
		for(int i = 0 ; i < channelProductList.size() ; i++){
			
			ChannelProductBean bean = channelProductList.get(i);
			if(bean .getTime_limit() == ChannelProductBean.TIME_LIMIT_TRUE){
				//检查是否有时间限制，如果有，则不记入到可用范围内
				boolean flag = channelService.checkTimeLimit();
				if(flag){
					channelProductList.remove(i);
					i--;
					continue;
				}
			}
			//计算权重总和 和范围
			if(bean.getWeight() != null && bean.getWeight() >0){
				
				bean.setWeight_min(weight);
				weight += bean.getWeight();
				
				bean.setWeight_max(weight);
			}
		}
		if(weight == 0) return null;
		
		//随机权重里的取值
		int index = RandomUtil.nextInt(weight);
		//判断取值是在哪个范围
		for(ChannelProductBean bean : channelProductList){
			
			if(bean.getWeight() != null && bean.getWeight() >0  && index< bean.getWeight_max() && index >= bean.getWeight_min()){
				
				channelProduct = bean ; 
			}
		}
		
		return channelProduct;
	}
	

	/**
	 * 根据条件返回随机的一条 产品通道，如果没有则返回空
	 */
	@Override
	public ChannelCustomerBean getRandomChannelCustomer(String province,Integer customer_id,int product_id){
		
		ProductBean product = productService.findProductById(product_id);
		//查询条件
		ChannelCustomerBean channelCustomer = new ChannelCustomerBean();
		channelCustomer.setProvince(province);
		channelCustomer.setCustomer_id(customer_id);
		channelCustomer.setProduct_brand(product.getProduct_brand());
		channelCustomer.setStatus(ChannelCustomerBean.STATUS_ENABLE);
		List<ChannelCustomerBean> channelCustomerList = queryChannelCustomer(channelCustomer);
		int weight = 0 ; 
		channelCustomer = null; //重置为空
		for(int i = 0 ; i < channelCustomerList.size() ; i++){
			
			ChannelCustomerBean bean = channelCustomerList.get(i);
			if(bean.getTime_limit() != null && bean.getTime_limit() == ChannelProductBean.TIME_LIMIT_TRUE){
				//检查是否有时间限制，如果有，则不记入到可用范围内
				boolean flag = channelService.checkTimeLimit();
				if(flag){
					channelCustomerList.remove(i);
					i--;
					continue;
				}
			}
			//计算权重总和 和范围
			if(bean.getWeight() != null && bean.getWeight() >0){
				
				bean.setWeight_min(weight);
				weight += bean.getWeight();
				
				bean.setWeight_max(weight);
			}
		}
		if(weight == 0) return null;
		
		//随机权重里的取值
		int index = RandomUtil.nextInt(weight);
		//判断取值是在哪个范围
		for(ChannelCustomerBean bean : channelCustomerList){
			
			if(bean.getWeight() != null && bean.getWeight() >0  && index< bean.getWeight_max() && index >= bean.getWeight_min()){
				
				channelCustomer = bean ; 
			}
		}
		
		return channelCustomer;
	}
	
	
	@Override
	public List<ChannelProductBean> queryChannelProduct(ChannelProductBean bean){
		return channelProductDao.queryChannelProduct(bean);
	}
	
	@Override
	public ChannelProductBean findChannelProductById(int id){
		ChannelProductBean bean = new ChannelProductBean();
		bean.setId(id);
		List<ChannelProductBean> list = queryChannelProduct(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<ChannelCustomerBean> queryChannelCustomer(ChannelCustomerBean bean){
		return channelProductDao.queryChannelCustomer(bean);
	}
	
	@Override
	public List<ChannelProductParamBean> queryChannelProductParam(ChannelProductParamBean bean){
		return channelProductDao.queryChannelProductParam(bean);
	}
	
	@Override
	public ChannelProductParamBean findChannelProductParam(int product_id,int channel_id){
		ChannelProductParamBean bean = new ChannelProductParamBean();
		bean.setChannel_id(channel_id);
		bean.setProduct_id(product_id);
		
		List<ChannelProductParamBean> list = queryChannelProductParam(bean);
		
		if(list.size() == 1 ){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void updateChannelProduct(ChannelProductBean bean){
		channelProductDao.updateChannelProduct(bean);
	}
	
	@Override
	public void updateChannelCustomer(ChannelCustomerBean bean){
		channelProductDao.updateChannelCustomer(bean);
	}
	
}
