/**
 * 
 */
package com.yd.business.wechat.crons;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.other.crons.BaseCrons;
import com.yd.business.wechat.service.IWechatService;

/**
 * @author ice
 *
 */
@Component("sendMessageToUserCrons")
public class SendMessageToUserCrons extends BaseCrons {
	@Resource
	private IWechatService wechatService;
	
	@Override
	public void execJob(JobExecutionContext jobContext) {
		//目前暂时没有用到
//		long thisTime = System.currentTimeMillis();
//		if(thisTime - lastTime >= sleepTime)
//		{
			wechatService.execSendMessageToUserList();
//		}

	}

}
