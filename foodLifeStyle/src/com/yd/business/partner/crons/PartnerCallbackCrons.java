/**
 * 
 */
package com.yd.business.partner.crons;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.partner.runable.PartnerCallbackRunable;
import com.yd.business.partner.service.IPartnerInterfaceService;

/**
 * 
 * 给合作伙伴回调的定时任务，需要每5分钟执行一次
 * @author ice
 *
 */
@Component("partnerCallbackCrons")
public class PartnerCallbackCrons extends BaseCrons {
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IOrderProductLogService orderProductLogService;
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
		
		int maxNotifyCount = 3;
		int sleepTime = 20 * 1000;
		
		PartnerOrderProductBean condition = new PartnerOrderProductBean();
//		condition.setStatus(PartnerOrderProductBean.STATUS_SUCCESS);
		condition.setNotify_status(PartnerOrderProductBean.STATUS_FAILD);
		condition.setNotify_count(maxNotifyCount);
		
		List<PartnerOrderProductBean> list = orderProductLogService.queryPartnerOrderProduct(condition );
		int i = 0;
		//循环发送 合作伙伴未返回成功的信息
		for(PartnerOrderProductBean bean : list){
			try {
				if(i % 20 ==0){
						Thread.sleep(sleepTime);
				}
				//启线程去调用
				taskExecutor.execute(new PartnerCallbackRunable(bean));
				i++;
			
			} catch (InterruptedException e) {
				log.error(e, e);
			}
		}
		
		
	}

}
