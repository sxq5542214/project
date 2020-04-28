package com.yd.business.order.crons;


import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;



/**
 * 给链接超时，余额不足的客户再次发送消息
 * @author zxz
 *
 */
@Component("getUserLastMonthOrderCrons")
public class GetUserLastMonthOrderCrons extends BaseCrons {
	@Resource
	private IOrderProductLogService orderProductLogService;
	
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	@Autowired
	private IUserWechatService userWechatService;
	
	@Override
	public void execJob(JobExecutionContext jobContext) {
		log.info("getUserLastMonthOrderCrons  start.....");
		//查询出上个月支付成功的订单
		OrderProductLogBean beanLastToday = new OrderProductLogBean();
		beanLastToday.setStatus(OrderProductLogBean.STATUS_SUCCESS);
		beanLastToday.setEvent_type(OrderProductLogBean.EVENT_TYPE_USER_ORDER);
		beanLastToday.setLastmonthbegin(DateUtil.getLastMonthDate() + " 00:00:00");
		beanLastToday.setLastmonthend(DateUtil.getLastMonthDate() + " 24:59:59");
		beanLastToday.setGroupby(" user_id having count(DISTINCT order_account) = 1 ");
		List<OrderProductLogBean> list = orderProductLogService.queryOrderProductLog(beanLastToday);
		OrderProductLogBean beanToday = new OrderProductLogBean();
		for(OrderProductLogBean  bean: list){
			try{
				beanToday.setUser_id(bean.getUser_id());
				beanToday.setLastmonthbegin(DateUtil.getLastMonthBeginDate());
				beanToday.setLastmonthend(DateUtil.getLastMonthEndDate());
				beanToday.setStatus(OrderProductLogBean.STATUS_SUCCESS);
				beanToday.setEvent_type(OrderProductLogBean.EVENT_TYPE_USER_ORDER);
				beanToday.setGroupby(" user_id having count(DISTINCT order_account) = 1 ");
				UserWechatBean user = userWechatService.findUserWechatById(bean.getUser_id());
				List<OrderProductLogBean> listToday = orderProductLogService.queryOrderProductLog(beanToday);
				if(listToday.isEmpty()){
				}else{
					msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_SYSTEM_ORDERBUY_MONTH, null, bean);
				}
		}catch(Exception e){
				log.error(e, e);
			}
		}

		log.info("getUserLastMonthOrderCrons  end.....");
	
	}
	
}
