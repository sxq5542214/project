/**
 * 
 */
package com.yd.business.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.dao.IUserConsumeInfoDao;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;

/**
 * @author ice
 *
 */
@Service("userConsumeInfoService")
public class UserConsumeInfoServiceImpl extends BaseService implements IUserConsumeInfoService {
	@Resource
	private IUserConsumeInfoDao userConsumeInfoDao ;
	
	@Override
	public UserConsumeInfoBean createConsumeInfo(String phone,Integer money,Integer spid,Integer user_id, String transactionId,String out_trade_no,String interface_type,Integer event_type){
		
		return createConsumeInfo(phone, money, spid, user_id, transactionId, out_trade_no, interface_type, 0,event_type);
	}
	
	@Override
	public UserConsumeInfoBean createConsumeInfo(String phone,Integer money,Integer spid,Integer user_id, String transactionId,String out_trade_no,String interface_type,Integer eff_num,Integer event_type){
		
		UserConsumeInfoBean bean = new UserConsumeInfoBean();
		bean.setMoney(money);
		bean.setPhone(phone);
		bean.setSupplier_product_id(spid);
		bean.setUser_id(user_id);
		bean.setInterface_type(interface_type);
		bean.setOut_trade_code(out_trade_no);
		bean.setStatus(UserConsumeInfoBean.STATUS_WAIT);
		bean.setTransaction_id(transactionId);
		bean.setCreate_date(DateUtil.getNowDateStr());
		bean.setEff_num(eff_num);
		bean.setEvent_type(event_type);
		
		userConsumeInfoDao.createConsumeInfo(bean);
		return bean;
	}
	
	@Override
	public void deleteConsumeInfo(String out_trade_code){
		
		UserConsumeInfoBean bean = userConsumeInfoDao.findUserConsumeInfoBean(out_trade_code);
		bean.setStatus(UserConsumeInfoBean.STATUS_CANCEL);
		userConsumeInfoDao.updateUserConsumeInfoBean(bean);
	}
	
	@Override
	public UserConsumeInfoBean findUserConsumeInfo(String out_trade_code){
		
		return userConsumeInfoDao.findUserConsumeInfoBean(out_trade_code);
	}
	
	@Override
	public List<UserConsumeInfoBean> queryUserConsumeInfo(UserConsumeInfoBean bean){
		return userConsumeInfoDao.queryUserConsumeInfoBean(bean);
	}
	
	@Override
	public void updateUserConsumeInfo(UserConsumeInfoBean bean){
		userConsumeInfoDao.updateUserConsumeInfoBean(bean);
	}
	
	@Override
	public void updateUserConsumeInfoStatus(int status,String out_trade_no){
		userConsumeInfoDao.updateUserConsumeInfoStatus(status,out_trade_no);
	} 
	
	
	/**
	 * 创建定单号
	 * @return
	 */
	@Override
	public String createOutTradeNo(String type,Integer userId){
		
		return createOutTradeNo(type, userId.toString(), new Date());
	}
	@Override
	public String createOutTradeNo(String type,String userId,Date date){
		return createOutTradeNo(type, userId, date, false);
	}
	@Override
	public String createOutTradeNo(String type,String userId,Date date,boolean addRandom){

		String no = type + "_" + userId +"_"+DateUtil.formatDateToPureSSS(date) ;
		if(addRandom){
			int random = RandomUtil.nextInt(999);
			no = no + random;
		}
		
		return no;
	}
	/**
	 * 用户预约订单生效时间
	 */
	@Override
	public void updateUserOrderEffDate(UserConsumeInfoBean bea) {
		updateUserConsumeInfo(bea);
	}
	
}
