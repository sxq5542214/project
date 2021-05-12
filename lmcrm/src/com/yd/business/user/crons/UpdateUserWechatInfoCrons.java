/**
 * 
 */
package com.yd.business.user.crons;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.other.crons.BaseCrons;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatExtendBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.util.EmojiUtil;
import com.yd.business.wechat.service.IWechatService;

/**
 * 更新用户的微信信息
 * @author ice
 *
 */
@Component("updateUserWechatInfoCrons")
public class UpdateUserWechatInfoCrons extends BaseCrons {

	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	
	
	@Override
	public void execJob(JobExecutionContext jobContext) {
		log.info("updateUserWechatInfoCrons  start.....");
	    List<UserWechatBean> list = userWechatService.list(null);
	    
	    for(int i = 0 ; i < list.size();i++ ){
	    	
	    	
	    }
	    
		log.info("updateUserWechatInfoCrons  end.....");
	}
		
}






