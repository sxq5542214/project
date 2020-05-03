/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.dao.ISupplierTopicDao;
import com.yd.business.supplier.dao.ISupplierUserDao;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierTopicService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("supplierUserService")
public class SupplierUserServiceImpl extends BaseService implements ISupplierUserService {
	
	@Resource
	private ISupplierUserDao supplierUserDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ISupplierService supplierService;
	
	@Override
	public void createSupplierUser(SupplierUserBean bean) {
		supplierUserDao.createSupplierUser(bean);
	}
	
	@Override
	public void updateSupplierUser(SupplierUserBean bean) {
		supplierUserDao.updateSupplierUser(bean);
	}
	
	@Override
	public List<SupplierUserBean> querySupplierUser(SupplierUserBean bean){
		return supplierUserDao.querySupplierUser(bean);
	}
	
	@Override
	public SupplierUserBean findSupplierUser(String openid,int sid) {
		if(StringUtil.isNotNull(openid)) {
			SupplierUserBean bean = new SupplierUserBean();
			bean.setOpenid(openid);
			bean.setSupplier_id(sid);
			List<SupplierUserBean> list = querySupplierUser(bean);
			if(list.size()>0) {
				return list.get(0);
			}
		}
		return null;
	}
	
	@Override
	public void createOrUpdateSupplierUser(String openid,Integer sid) {
		
		SupplierUserBean su = findSupplierUser(openid, sid);
		
		if(su == null) {
			//创建
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			su = new SupplierUserBean(user);
			su.setUser_id(user.getId());
			su.setSupplier_id(sid);
			su.setLast_access_time(DateUtil.getNowDateStr());
			su.setCreate_time(DateUtil.getNowDateStr());
			su.setPoints(0);
			su.setBalance(0);
			su.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
			
			createSupplierUser(su);
			
		}else {
			//修改
			su.setLast_access_time(DateUtil.getNowDateStr());
			updateSupplierUser(su);
		}
	}
	
	
	
}
