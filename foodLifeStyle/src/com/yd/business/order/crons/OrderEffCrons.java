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

			}catch (Exception e) {
				log.error(e, e);
			}
		}
		log.info("orderEffCrons  end.....");

	}	
}
