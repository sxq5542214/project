package com.yd.business.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.bean.CustomerBalanceLogBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.dao.ICustomerDao;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.util.DateUtil;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * 客户信息
 * @author Anlins
 *
 */
@Service("customerService")
public class CustomerServiceImpl extends BaseService implements
		ICustomerService {
	public static final String _CURRENT_USER = "_CURRENT_USER";
	public static final String _CURRENT_USER_TYPE = "_CURRENT_USER_TYPE";
	@Resource
	private ICustomerDao customerDao;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	@Override
	public CustomerBean login(String username, String password) {
		// TODO Auto-generated method stub
		CustomerBean bean = customerDao.login(username, MD5.md5(password));
		if(bean!=null){
			bean.setPassword(password);
			WebContext.getHttpSession().setAttribute(_CURRENT_USER, bean);
			WebContext.getHttpSession().setAttribute(_CURRENT_USER_TYPE, "1");
			bean.setSession_id(WebContext.getHttpSession().getId());
		}
		return bean;
	}
	@Override
	public void setSession(CustomerBean bean){
		WebContext.getHttpSession().setAttribute(_CURRENT_USER, bean);
	}
	
	@Override
	public void logoff() {
		// TODO Auto-generated method stub
		WebContext.getHttpSession().setAttribute(_CURRENT_USER, null);
	}

	@Override
	public CustomerBean getUser() {
		// TODO Auto-generated method stub
		return (CustomerBean)WebContext.getHttpSession().getAttribute(_CURRENT_USER);
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
		int type = NumberUtil.toInt(WebContext.getHttpSession().getAttribute(_CURRENT_USER_TYPE));
		Object o = WebContext.getHttpSession().getAttribute(_CURRENT_USER);
		id = type==1?((CustomerBean)o).getId():((CustomerAdminBean)o).getCustomer_id();
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
	@Override
	public int insertCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		return customerDao.insertCustomer(bean);
	}

	@Override
	public void updateCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		customerDao.updateCustomer(bean);
	}

	@Override
	public void deleteCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		customerDao.deleteCustomer(bean);
	}

	@Override
	public CustomerBean findCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return customerDao.findCustomerById(id);
	}

	@Override
	public List<CustomerBean> listCustomer(CustomerBean bean) {
		// TODO Auto-generated method stub
		return customerDao.listCustomer(bean);
	}

	@Override
	public CustomerBean findCustomerByPhone(String phone) {
		// TODO Auto-generated method stub
		CustomerBean bean = new CustomerBean();
		bean.setContacts_phone(phone);
		return customerDao.findCustomerByPhone(bean);
	}

	@Override
	public List<CustomerBean> listCustomerBySupplier(List<SupplierBean> list) {
		// TODO Auto-generated method stub
		return customerDao.listCustomerBySupplier(list);
	}
	
	/**
	 * 添加客户余额
	 * @param bean
	 * @param balance
	 * @param remark
	 */
	@Override
	public void addCustomerBalance(CustomerBean bean,String out_trade_no,Integer balance,String remark){
		
//		此方式在多线程密集并发时会脏读，不再使用
//		Integer oldBalance = bean.getBalance();
//		if(oldBalance == null) oldBalance = 0;
//		bean.setBalance(oldBalance + balance);
//		customerDao.updateCustomer(bean);
		
		customerDao.updateCustomerBalance(bean.getId(), balance);
		
		
		//保存客户余额日志
		CustomerBalanceLogBean logBean = new CustomerBalanceLogBean();
		logBean.setCreate_time(DateUtil.getNowDateStrSSS());
		logBean.setCustomer_id(bean.getId());
		logBean.setCustomer_name(bean.getName());
		logBean.setGet_balance(balance);
		logBean.setRemark(remark);
		logBean.setOut_trade_no(out_trade_no);
//		logBean.setTotal_balance(bean.getBalance());  这里直接sql里取数据库的值 ，不然会脏读
		customerDao.addCustomerBalanceLog(logBean);
		
		
	}
	

	/**
	 * 添加客户余额
	 * @param bean
	 * @param balance
	 * @param remark
	 */
	@Override
	public synchronized void addCustomerBalance(String out_trade_no,Integer customer_id,Integer balance,String remark){
		
		CustomerBean bean = customerDao.findCustomerById(customer_id);
		
		checkCustomerBalanceAlarm(bean, balance);
		
		addCustomerBalance(bean,out_trade_no, balance, remark);
		
		
	}
	
	/**
	 * 检查客户余额及告警
	 * @param bean
	 * @param changeBalance
	 */
	private void checkCustomerBalanceAlarm(CustomerBean bean,int changeBalance){
		int alarm_balance = 1000000; //告警金额为1W元
		int balance = bean.getBalance();
		
		if(changeBalance > 0){
			bean.setSession_id(changeBalance / 100d +"元");
			//客户添加余额的消息通知
			msgCenterActionService.saveAndHandleUserAction(UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_CUSTOMER_PRICE_ADD, null, bean);
			
		}else if(balance >= alarm_balance &&  balance + changeBalance < alarm_balance){
			//告警
			bean.setSession_id(balance / 100 +"元");
			//客户余额不足1W的消息通知
			msgCenterActionService.saveAndHandleUserAction(UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_CUSTOMER_PRICE_ALARM, null, bean);
		}
		
	}


	@Override
	public CustomerBean queryAdminCustomer() {
		// TODO Auto-generated method stub
		return customerDao.queryAdminCustomer();
	}


	@Override
	public void addCustomerBalanceLog(CustomerBalanceLogBean bean) {
		// TODO Auto-generated method stub
		customerDao.addCustomerBalanceLog(bean);
	}


	@Override
	public void updateCustomerBalance(Integer id, int balance) {
		// TODO Auto-generated method stub
		customerDao.updateCustomerBalance(id, balance);
	}
	
	/**
	 * 如果号码已存在，则使用已有的客户
	 */
	@Override
	public void createCustomer(String custName,String phoneNo,String openid) {
		CustomerBean bean ;
		bean = findCustomerByPhone(phoneNo);
		if(bean == null) {
			//没有客户才新建
			bean = new CustomerBean();
			bean.setOpenid(openid);
			bean.setName(custName);
			bean.setUsername(phoneNo);
			bean.setContacts_name(custName);
			bean.setContacts_phone(phoneNo);
			bean.setBalance(0);
			bean.setRecharge_balance(0);
			bean.setPoints(0);
			bean.setType(CustomerBean.TYPE_REG);
			bean.setCreate_time(DateUtil.getNowDateStr());
			insertCustomer(bean);
		}
		
	}
	
}
