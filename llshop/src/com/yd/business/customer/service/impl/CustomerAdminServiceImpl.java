package com.yd.business.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.dao.ICustomerAdminDao;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
/**
 * 操作员管理接口实现类
 * @author Anlins
 *
 */
@Service("customerAdminService")
public class CustomerAdminServiceImpl extends BaseService implements
		ICustomerAdminService {
	public static final String _CURRENT_USER = "_CURRENT_USER";
	public static final String _CURRENT_USER_TYPE = "_CURRENT_USER_TYPE";
	@Resource
	private ICustomerAdminDao customerAdminDao;
	
	public ICustomerAdminDao getCustomerAdminDao() {
		return customerAdminDao;
	}

	public void setCustomerAdminDao(ICustomerAdminDao customerAdminDao) {
		this.customerAdminDao = customerAdminDao;
	}

	@Override
	public void insertCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		customerAdminDao.insertCustomerAdmin(bean);
	}

	@Override
	public void updateCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		customerAdminDao.updateCustomerAdmin(bean);
	}

	@Override
	public void deleteCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		customerAdminDao.deleteCustomerAdmin(bean);
	}

	@Override
	public CustomerAdminBean findCustomerAdminById(Integer id) {
		// TODO Auto-generated method stub
		return customerAdminDao.findCustomerAdminById(id);
	}

	@Override
	public List<CustomerAdminBean> listCustomerAdmin(CustomerAdminBean bean) {
		// TODO Auto-generated method stub
		return customerAdminDao.listCustomerAdmin(bean);
	}

	@Override
	public void batDelCustomerAdmin(List<CustomerAdminBean> list) {
		customerAdminDao.batDelCustomerAdmin(list);
	}

	@Override
	public CustomerAdminBean loginByCustomerAdmin(String username,
			String password) {
		// TODO Auto-generated method stub
		CustomerAdminBean bean = customerAdminDao.loginByCustomerAdmin(username, MD5.md5(password));
		if(bean!=null){
			bean.setPassword("");
			WebContext.getHttpSession().setAttribute(_CURRENT_USER, bean);
			WebContext.getHttpSession().setAttribute(_CURRENT_USER_TYPE, "2");
			bean.setSession_id(WebContext.getHttpSession().getId());
		}
		return bean;
	}

	@Override
	public void logoff() {
		// TODO Auto-generated method stub
		WebContext.getHttpSession().setAttribute(_CURRENT_USER, null);
	}

	@Override
	public CustomerAdminBean getUser() {
		// TODO Auto-generated method stub
		return (CustomerAdminBean)WebContext.getHttpSession().getAttribute(_CURRENT_USER);
	}

	@Override
	public String createToken() {
		// TODO Auto-generated method stub
		Object user = getUser();
		if (user != null) {
			String loginName = (String) getUserField("user_name");
			String password = (String) getUserField("password");
			
			return MD5.md5(loginName + password);
		}
		return null;
	}

	@Override
	public Integer getUserId() {
		// TODO Auto-generated method stub
		Integer id = null;
		Object o = WebContext.getHttpSession().getAttribute(_CURRENT_USER);
		id = ((CustomerAdminBean)o).getId();
		return id;
	}
	public Object getUserField(String fieldName) {
		Object user = getUser();
		return getUserField(fieldName, user);
	}

	private Object getUserField(String fieldName, Object user) {
		if (user != null) {
			return NumberUtil.toMap(user).get(fieldName);
		}
		return null;
	}
}
