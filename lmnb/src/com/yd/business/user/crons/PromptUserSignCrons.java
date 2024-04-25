package com.yd.business.user.crons;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.basic.framework.cron.BaseCrons;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;


/**
 * 给48小时访问过的用户发一段话,提醒签到
 * @author zxz
 *
 */
@Component("promptUserSignCrons")
public class PromptUserSignCrons extends BaseCrons {
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IConfigAttributeService configAttributeService;

	@Override
	public void execJob(JobExecutionContext jobContext) {
		log.info("PromptUserSignCrons  start.....");
		
		try {
			String date = DateUtil.getNowDateStr();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("date", date);
			map.put("link_url", "#server_url#user/toUserSignAndTopicPage.do?openid=#action_openid#");
			
			
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		log.info("PromptUserSignCrons  end.....");
	}
	
}