/**
 * 
 */
package com.yd.business.device.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.device.bean.ChangeMeterBean;
import com.yd.business.device.dao.IChangeMeterDao;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;

/**
 * @author ice
 *
 */
@Service("changeMeterService")
public class ChangeMeterServiceImpl extends BaseService implements IChangeMeterService {
	
	@Resource
	private IChangeMeterDao changeMeterDao;
	@Resource
	private IUserInfoService userInfoService;
	
	@Override
	public int createChangeMeter(long user_no ,BigDecimal cm_oldmetercode,int cm_type,long cm_operatorid,String cm_remark) {
		
		UserInfoBean user = userInfoService.findUserByNo(user_no);

		ChangeMeterBean bean = new ChangeMeterBean();
		bean.setCm_userid(user.getU_id());
		bean.setCm_oldmeterno(user_no);
		bean.setCm_oldmetercode(cm_oldmetercode);
		bean.setCm_operatorid(cm_operatorid);
		bean.setCm_type(cm_type);
		bean.setCm_remark(cm_remark);
		bean.setCm_happendate(new Date());
		bean.setCm_status(13);  // 不知意义
		
		bean.setCm_newmetercode(BigDecimal.ZERO);
		bean.setCm_newmeterno(0l);
		
		return changeMeterDao.insertChangeMeter(bean);
	}
	
}
