package com.yd.business.supplier.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.dao.ISupplierEventDao;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
/**
 * 
 * @author Anlins
 *
 */
@Service("supplierEventService")
public class SupplierEventServiceImpl extends BaseService implements
		ISupplierEventService {

	@Resource
	private ISupplierEventDao supplierEventDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatPayService wechatPayService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	

	@Override
	public String insert(SupplierEventBean bean) {
		String str = supplierEventDao.insert(bean);
		if("511".equals(str)|| bean.getId() >= 511){
			//由于微信的二维码扫描参数位数限制，这里ID不能大于511
			throw new RuntimeException("supplierEvent id >=511 !  must be <511!");
		}
		return str;
	}

	@Override
	public void update(SupplierEventBean bean) {
		supplierEventDao.update(bean);
	}
	
	@Override
	public void updateEventReadNum(int id,int num){
		supplierEventDao.addEventReadNum(id,num);
	}

	@Override
	public void delete(SupplierArticleBean bean) {
		supplierEventDao.delete(bean);
	}

	@Override
	public SupplierEventBean queryByid(Integer id) {
		return supplierEventDao.queryByid(id);
	}

	@Override
	public List<SupplierEventBean> list(SupplierEventBean bean) {
		return supplierEventDao.list(bean);
	}
	
	@Override
	public List<SupplierEventBean> queryBeforEndTimeSupplierEvent(SupplierEventBean bean) {
		bean.setEnd_time(DateUtil.getNowDateStr());
		return supplierEventDao.queryBeforEndTimeSupplierEvent(bean);
	}
	
	@Override
	public List<SupplierEventBean> queryAfterEndTimeSupplierEvent(SupplierEventBean bean) {
		bean.setEnd_time(DateUtil.getNowDateStr());
		return supplierEventDao.queryAfterEndTimeSupplierEvent(bean);
	}
	@Override
	public List<SupplierEventBean> queryList(int status,int limit){
		
		SupplierEventBean bean = new SupplierEventBean();
		bean.setStatus(status);
		bean.setPageSize(limit);
		return supplierEventDao.listByLimit(bean);
	}
	
	
	
	/**
	 * 保存文章发送历史
	 */
	@Override
	public SupplierEventCodeBean queryCode(Integer eventId,Integer code){
		
		SupplierEventCodeBean bean = new SupplierEventCodeBean();
		bean.setCode(code);
		bean.setEventId(eventId);
		List<SupplierEventCodeBean> list = supplierEventDao.queryEventCode(bean );
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询用户的活动参与码
	 * @param fromUserId 
	 */
	@Override
	public List<SupplierEventCodeBean> queryEventCode(int eventId,int userId, Integer fromUserId){
		
		SupplierEventCodeBean bean = new SupplierEventCodeBean();
		bean.setEventId(eventId);
		bean.setUserId(userId);
		bean.setFromUserId(fromUserId);
		return supplierEventDao.queryEventCode(bean );
	}
	
	/**
	 * 查询用户的活动参与码，如果一个都没有，则生成一个
	 * @param fromOpenId
	 */
	@Override
	public List<SupplierEventCodeBean> queryAndInitEventCode(int eventId,int userId, String fromOpenId){
		
		List<SupplierEventCodeBean> list = queryEventCode(eventId, userId,userId);
		if(list.size() == 0){//如果自己没有，则生成一个
			createEventCode(eventId, userId, fromOpenId);
			
			//给用户发红包
			sendBonusByEvent(eventId, userId);
			
		}
		list = queryEventCode(eventId, userId,null);
		
		return list;
	}
	
	/**
	 * 活动里定义了给用户发红包的话，就直接给用户发
	 * @param eventId
	 * @param userId
	 */
	private void sendBonusByEvent(int eventId,int userId){
		SupplierEventBean event = supplierEventDao.queryByid(eventId);
		if(event.getBonusMaxUserId() != null && event.getBonusNum() != null && event.getBonusNum() >0){
			if(userId <= event.getBonusMaxUserId()){
				UserWechatBean user = userWechatService.findUserWechatById(userId);
				user.setBalance(user.getBalance() + event.getBonusNum());
				userWechatService.updateUserWechat(user);
				
				wechatPayService.payBonusLimit200(user.getOpenid(), event.getBonusNum(), null);
			}
		}
	}
	
	
	/**
	 * 查询用户的活动参与码
	 */
	@Override
	public int queryEventCodeCount(int eventId){
		
		SupplierEventCodeBean bean = new SupplierEventCodeBean();
		bean.setEventId(eventId);
		return supplierEventDao.queryEventCodeCount(bean);
	}
	
	/**
	 * 创建用户活动参与码
	 * @param eventId
	 * @param openId
	 */
	@Override
	public void createEventCode(int eventId,int userId,String fromOpenId){
		
		SupplierEventBean event = supplierEventDao.queryByid(eventId);
		//如果当前时间大于活动时间，则结束
		if(event.getLotteryDate() == null || event.getEnd_time().compareTo(DateUtil.getNowDateStr()) < 0 )
		{
			return;
		}
		Integer maxCode = supplierEventDao.queryMaxEventCode(eventId);
		UserWechatBean fromUser = userWechatService.findUserWechatByOpenId(fromOpenId);

		SupplierEventCodeBean bean = new SupplierEventCodeBean();
		bean.setEventId(eventId);
		bean.setFromUserId(fromUser.getId());
		int count = supplierEventDao.queryEventCodeCount(bean);
		bean.setTotalcount(count+1);//记录当前有多少用户助力了
		
		bean.setUserId(userId);
		count = supplierEventDao.queryEventCodeCount(bean);
		//如果已经助力过，直接返回了
		if(count > 0)return;
		
		if(maxCode == null) maxCode = 0;
		if(maxCode <= event.getJoinCount()) maxCode =  event.getJoinCount();
//		//添加扰码，随机跳过一些数字
//		int interfere = NumberUtil.random(4);
//		if(interfere != 0){
//			int jumpNum = maxCode % interfere;
//			maxCode += jumpNum;
//		}
		
		
		maxCode++;
		bean.setCode(maxCode);
		bean.setCreateDate(DateUtil.getNowDateStr());
		bean.setEventId(eventId);
		bean.setUserId(userId);
		bean.setFromUserId(fromUser.getId());
		bean.setFromUserName(fromUser.getNick_name());
		try {
			
			supplierEventDao.createSupplierEventCode(bean);

			event = supplierEventDao.queryByid(eventId);
			//活动人数加1
			event.setJoinCount(maxCode);
			supplierEventDao.update(event);
			
			UserWechatBean user = userWechatService.findUserWechatById(userId);
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_SUPPLIER_GET_CODE, null , bean);
			
		} catch (Exception e) {
			log.error(e, e); //并发量大时，code可能会重复，导致报错
			createEventCode(eventId, userId, fromOpenId);
		}
	}

	@Override
	public void batchDelete(List<SupplierArticleBean> list) {
		// TODO Auto-generated method stub
		supplierEventDao.batchDelete(list);
	}

}
