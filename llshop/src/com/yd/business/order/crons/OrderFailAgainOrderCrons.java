package com.yd.business.order.crons;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.crons.BaseCrons;



/**
 * 给链接超时，余额不足的客户再次发送消息
 * @author zxz
 *
 */
@Component("orderFailAgainOrderCrons")
public class OrderFailAgainOrderCrons extends BaseCrons {
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IOrderService orderService;
	
	@Override
	public void execJob(JobExecutionContext jobContext) {
		log.info("orderFailAgainOrderCrons  start.....");
		OrderProductLogBean beanIn = new OrderProductLogBean();
		beanIn.setStatus(OrderProductLogBean.STATUS_NEED_AGAIN_ORDER);
//		查询出余额不足,链接超时等特殊状态下的订单
		List<OrderProductLogBean> list = orderProductLogService.queryOrderProductLog(beanIn);

			try{
				for(OrderProductLogBean bean : list){
				orderService.orderProductByUser(bean.getOrder_code(),null);
				}
//				List<OrderProductLogBean> listAgain = orderProductLogService.queryOrderProductLog(beanIn);
//				for(OrderProductLogBean beanAgain : listAgain){
//				if(beanAgain.getStatus() == OrderProductLogBean.STATUS_NEED_AGAIN_ORDER ){
//					orderProductLogService.updateOrderProductLogStatusOrder(beanAgain.getOrder_code(),OrderProductLogBean.STATUS_FAILD);
//				}
//				}
				
			}catch(Exception e){
				log.error(e, e);
			}

		log.info("orderFailAgainOrderCrons  end.....");
	
	}
	
}
