/**
 * 
 */
package com.yd.business.channel.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.channel.bean.ChannelBalanceLogBean;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.bean.ChannelCustomerBean;
import com.yd.business.channel.bean.ChannelProductBean;
import com.yd.business.channel.bean.ChannelServerBean;
import com.yd.business.channel.bean.TimeLimitBean;
import com.yd.business.channel.dao.IChannelDao;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;

/**
 * @author ice
 *
 */
@Service("channelService")
public class ChannelServiceImpl extends BaseService implements IChannelService {
	
	@Resource
	private IChannelDao channelDao;
	@Resource
	private IOrderService orderService;
	@Resource
	private IChannelProductService channelProductService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	@Override
	public ChannelBean findChannelById(Integer id){
		if(id == null){
			return null;
		}
		return channelDao.findChannelById(id);
	}
	
	/**
	 * 根据当前时间，客户ID，手机号，商品ID，去查询通道
	 * @param customer_id
	 * @param phone
	 * @return
	 */
	@Override
	public ChannelBean findChannel(int customer_id,String phone,int product_id){
		ChannelBean channel = null;
		Integer channel_id = null;
		//先查商品通道表，如果没有，再查客户通道表
		//取手机号码的省份
		AreaDataBean ad = orderService.getAreaDataByPhone(phone);
		String province = ad.getProvince();
		
		ChannelProductBean channelProduct = channelProductService.getRandomChannelProduct(province, product_id, customer_id);
		//如果有产品通道，则直接用
		if(channelProduct != null){
			channel_id = channelProduct.getChannel_id();
		}else{
			//如果产品通道没有，则查询客户通道 
			ChannelCustomerBean channelCustomer = channelProductService.getRandomChannelCustomer(province, customer_id,product_id);
			if(channelCustomer != null){
				channel_id = channelCustomer.getChannel_id();
			}else{
				//如果指定客户的通道也没有。。。 走默认的。。。
				channelCustomer = channelProductService.getRandomChannelCustomer(province, CustomerBean.ID_DEFAULT ,product_id);
				if (channelCustomer != null) {
					channel_id = channelCustomer.getChannel_id();
				}
			}
		}
		if(channel_id != null){
			channel = channelDao.findChannelById(channel_id);
		}
		
		return channel;
	}
	
	
	 
	/**
	 * 检查当前时间是否在限制时间内
	 * @return true在限制时间内，false 不在限制时间内
	 */
	@Override
	public boolean checkTimeLimit(){
		
		Calendar c = Calendar.getInstance();
		TimeLimitBean bean = new TimeLimitBean();
		
		bean.setYear(c.get(Calendar.YEAR));
		bean.setMonth(c.get(Calendar.MONTH) + 1);
		bean.setDay(c.get(Calendar.DAY_OF_MONTH));
		
		
		String current_time = DateUtil.formatDateOnlyTime(c.getTime());
		bean.setCurrent_time(current_time );
		List<TimeLimitBean> list = channelDao.queryTimeLimit(bean);
		if(list.size() > 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 从多个Channelserver中随机取一个
	 * @param channel_id
	 * @return	
	 */
	@Override
	public ChannelServerBean getRandomChannelServer(int channel_id){
		ChannelServerBean channelServer = new ChannelServerBean();
		channelServer.setChannel_id(channel_id);
		List<ChannelServerBean> list = channelDao.queryChannelServer(channelServer);

		channelServer = null;
		int weight = 0;
		for(int i = 0 ; i < list.size() ; i++){
			
			ChannelServerBean bean = list.get(i);
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
		for(ChannelServerBean bean : list){
			
			if(bean.getWeight() != null && bean.getWeight() >0  && index< bean.getWeight_max() && index >= bean.getWeight_min()){
				
				channelServer = bean ; 
			}
		}
		
		return channelServer;
	}

	/**
	 * 更新第三方余额
	 */
	@Deprecated
	@Override
	public void updateChannelBalance(int channel_id,String balance) {
		ChannelBean channel = channelDao.findChannelById(channel_id);
		channel.setBalance(balance);
		channelDao.updateChannelBalance(channel);
	}
	
	@Override
	public void updateChannelCalcBalance(int channel_id,int price,String remark){
		
		//判断是否需要发送告警
		ChannelBean channel = findChannelById(channel_id);
		//以万为单位
		int beforPrice = channel.getCalc_balance() / 1000000;
		int after = (channel.getCalc_balance() + price) / 1000000;
		
		if(beforPrice != after ){
			//万元为单位的钱  不一样，就发送消息告警
			//触发消息中心的告警动作
			channel.setCalc_balance(channel.getCalc_balance() + price);
			msgCenterActionService.saveAndHandleUserAction(UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_CHANNEL_PRICE_ALARM  , null, channel);
		}
		
		channelDao.updateChannelCalcBalance(channel_id, price);
		
		//通道余额日志
		ChannelBalanceLogBean bean = new ChannelBalanceLogBean();
		bean.setChannel_id(channel_id);
		bean.setChannel_name(channel.getName());
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setGet_balance(price);
		bean.setRemark(remark);
//		这里这样记多线程并发下会导致脏读的问题，改为数据库直接查
//		bean.setTotal_balance(channel.getCalc_balance());
		createChannelBalanceLog(bean);
	}
	
	@Override
	public List<ChannelBean> queryChannel(ChannelBean bean){
		return channelDao.queryChannel(bean);
	}
	
	@Override
	public void createChannelBalanceLog(ChannelBalanceLogBean bean){
		channelDao.createChannelBalanceLog(bean);
	}
	
}
