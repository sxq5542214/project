/**
 * 
 */
package com.yd.business.bill.crons;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.bill.service.IBillService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *	月账单定时任务
 */
@Component("billMonthCronsCrons")
public class BillMonthCrons extends BaseCrons {

	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IBillService billService;
	
	
	
	/* (non-Javadoc)
	 * @see com.ym.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {

		log.debug(" BillMonthCrons begin.....");
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String billMonth = DateUtil.formatMonthPure(calendar.getTime());
		
		//无支付、无读表数据的账单扣减
		billService.initNoPayUserBills(billMonth);
		
		//查询当月读表未达最低消费的账单进行扣减
		billService.updateBillByDeductionMinconsumamout(billMonth);
		
		//更新账单支付总额
		billService.updateBillCyclebuyamount(billMonth);
		
		
		log.debug(" BillMonthCrons end.....");
	}
	
	
	
	
	
	public static void main(String[] args) {
	
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String billMonth = DateUtil.formatMonthPure(calendar.getTime());
		System.out.println(billMonth);
	}
}
