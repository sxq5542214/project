/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.user.bean.UserCommissionPointsBean;
import com.yd.business.user.dao.IUserCommissionPointsDao;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("userCommissionPointsService")
public class UserCommissionPointsServiceImpl extends BaseService implements IUserCommissionPointsService {
	@Resource
	private IUserCommissionPointsDao userCommissionPointsDao;
	
	/**
	 * 添加用户的福分
	 * @param wechatUserId
	 * @param point
	 */
	@Override
	public void createUserPointLog(Integer supplier_id,int userId,int point,String detail){
		
		if(point == 0){
			return;
		}
		
		//添加福分记录
		UserCommissionPointsBean bean = new UserCommissionPointsBean();
		bean.setCreate_date(DateUtil.formatDate(new Date()));
		bean.setDetailed(detail);
		bean.setSupplier_id(supplier_id);
		if(point > 0){
			bean.setPay("+"+point);
		}else{
			bean.setPay(String.valueOf(point));
		}
		
		bean.setUser_id(userId);
		userCommissionPointsDao.createUserCommissionPoint(bean);
	}
	
	@Override
	public List<UserCommissionPointsBean> queryUserCommissionPoints(Integer supplier_id, int userId){
		
		UserCommissionPointsBean bean = new UserCommissionPointsBean();
		bean.setUser_id(userId);
		bean.setSupplier_id(supplier_id);
		
		return userCommissionPointsDao.queryUserCommissionPoints(bean );
		
	} 
	
}
