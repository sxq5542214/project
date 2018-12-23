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
	      try {
	        UserWechatBean bean = list.get(i);
	        //去微信服务端取数据
	        UserWechatExtendBean newInfo = wechatService.getWechatUserInfo(bean.getOpenid(),bean.getOriginalid());
	        //取数据成功
	        if(newInfo.getSubscribe() == UserWechatExtendBean.SUBSCRIBE_YES)
	        {

	          String filterName = EmojiUtil.filterEmoji(newInfo.getNick_name());
	          newInfo.setNick_name(filterName);
	          newInfo.setId(bean.getId());
	          newInfo.setBalance(null);
	          newInfo.setPoints(null);
	          newInfo.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
	          //更新表数据
	          userWechatService.updateUserWechat(newInfo);
	        }else{
	          bean.setStatus(UserWechatBean.STATUS_UNSUBSCRIBE);
	          userWechatService.update(bean);
	        }
	      } catch (Exception e) {
	        log.error("updateUserWechatInfo error!"+e.getMessage(), e);
	      }
	    }
	    
		log.info("updateUserWechatInfoCrons  end.....");
	}
		
}






