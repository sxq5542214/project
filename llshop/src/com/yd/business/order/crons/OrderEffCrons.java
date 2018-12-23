/**
 * 
 */
package com.yd.business.order.crons;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.supplier.service.ISupplierCardSecretService;
import com.yd.business.user.bean.UserSenceLog;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Component("orderEffCrons")
public class OrderEffCrons extends BaseCrons {
	@Resource
	private IOrderService orderService;
	@Resource
	private ISupplierCardSecretService supplierCardSecretService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IUserWechatService userWechatService;
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
		log.info("orderEffCrons  start.....");
		List<OrderProductEffBean> list = orderService.queryOrderProductEffByStatus(OrderProductEffBean.STATUS_WAIT);
		
		for(OrderProductEffBean bean : list){
			try{

				OrderProductLogBean orderLog = null;
				Integer id = bean.getEff_id();
				switch (bean.getType()) {
				case OrderProductEffBean.TYPE_CARD_SECRET:
					
					SupplierCardSecretBean scs = supplierCardSecretService.findSupplierCardSecret(id);
					orderLog = orderService.orderProductBySupplierCardSecret(scs, scs.getPhone());
					
					
					break;
	
				case OrderProductEffBean.TYPE_USER_EFF:
					orderLog = orderService.effUserOrder(id);
					break;
				}
				
				
				if(orderLog != null && orderLog.getStatus() == OrderProductLogBean.STATUS_SUCCESS){
					bean.setExecute_time(DateUtil.getNowDateStr());
					bean.setStatus(OrderProductEffBean.STATUS_SUCCESS);
					bean.setRemark(orderLog.getRemark());
				}else{
					bean.setExecute_time(DateUtil.getNowDateStr());
					bean.setStatus(OrderProductEffBean.STATUS_FAILD);
					bean.setRemark(orderLog.getRemark());
					
				}
				orderService.updateOrderProductEff(bean);
				
			}catch (Exception e) {
				log.error(e, e);
			}
		}
		log.info("orderEffCrons  end.....");

	}	
}
