package com.yd.business.supplier.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardCostlogBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.dao.ISupplierEventDao;
import com.yd.business.supplier.dao.ISupplierStoreDao;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierStoreService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author ice
 *
 */
@Service("supplierStoreService")
public class SupplierStoreServiceImpl extends BaseService implements
		ISupplierStoreService {

	@Resource
	private ISupplierEventDao supplierEventDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatPayService wechatPayService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private ISupplierStoreDao supplierStoreDao;
	@Resource 
	private ISupplierUserService supplierUserService;
	@Resource
	private ISupplierService supplierService;
	
	
	@Override
	public void createStoreBalanceCard(SupplierStoreBalanceCardBean bean) {
		
		bean.setCreate_time(DateUtil.getNowDateStr());
		
		supplierStoreDao.createStoreBalanceCard(bean);
	}
	@Override
	public void createStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean) {
		supplierStoreDao.createStoreBalanceCardRecord(bean);
		
	}
	@Override
	public void createStoreBalanceCardCostlog(SupplierStoreBalanceCardCostlogBean bean) {
		// TODO Auto-generated method stub
		supplierStoreDao.createStoreBalanceCardCostlog(bean);
	}
	@Override
	public int updateStoreBalanceCard(SupplierStoreBalanceCardBean bean) {
		// TODO Auto-generated method stub
		return supplierStoreDao.updateStoreBalanceCard(bean);
	}
	@Override
	public int updateStoreBalanceCardRecord(SupplierStoreBalanceCardRecordBean bean) {
		// TODO Auto-generated method stub
		return supplierStoreDao.updateStoreBalanceCardRecord(bean);
	}
	
	@Override
	public SupplierStoreBalanceCardBean findStoreBalanceCardById(Integer id,Integer sid) {
		if(sid == null) {
			return null;
		}
		SupplierStoreBalanceCardBean bean = new SupplierStoreBalanceCardBean();
		bean.setId(id);
		bean.setSupplier_id(sid);
		List<SupplierStoreBalanceCardBean> list = queryStoreBalanceCard(bean );
		if(list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public SupplierStoreBalanceCardBean findStoreBalanceCardById(Integer id) {
		SupplierStoreBalanceCardBean bean = new SupplierStoreBalanceCardBean();
		bean.setId(id);
		List<SupplierStoreBalanceCardBean> list = queryStoreBalanceCard(bean );
		if(list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<SupplierStoreBalanceCardBean> queryStoreBalanceCard(SupplierStoreBalanceCardBean bean) {
		// TODO Auto-generated method stub
		return supplierStoreDao.queryStoreBalanceCard(bean);
	}
	@Override
	public List<SupplierStoreBalanceCardRecordBean> queryStoreBalanceCardRecord(
			SupplierStoreBalanceCardRecordBean bean) {
		// TODO Auto-generated method stub
		return supplierStoreDao.queryStoreBalanceCardRecord(bean);
	}
	@Override
	public List<SupplierStoreBalanceCardCostlogBean> queryStoreBalanceCardCostlog(
			SupplierStoreBalanceCardCostlogBean bean) {
		// TODO Auto-generated method stub
		return supplierStoreDao.queryStoreBalanceCardCostlog(bean);
	}

	@Override
	public List<SupplierStoreBalanceCardRecordBean> queryUserCanuseStoreBalanceCardRecord(
			String openid ,Integer sid  ) {
		
		SupplierStoreBalanceCardRecordBean bean = new SupplierStoreBalanceCardRecordBean();
		bean.setStatus(SupplierStoreBalanceCardRecordBean.STATUS_UP);
		bean.setOpenid(openid);
		bean.setSupplier_id(sid);
		bean.setBalance(1); //余额大于等于1才行
		bean.setDff_time(DateUtil.getNowDateStr());
		// TODO Auto-generated method stub
		return supplierStoreDao.queryStoreBalanceCardRecord(bean);
	}
	
	@Override
	public int updateStoreCardRecordBalance(String openid,Integer id, Integer addBalance,Integer sid,String orderCode,String remark) {
		int i = 0;
		
		if(sid ==null) {
			return i;
		}
		
		SupplierStoreBalanceCardRecordBean record = null;
		SupplierStoreBalanceCardRecordBean bean = new SupplierStoreBalanceCardRecordBean();
		bean.setSupplier_id(sid);
		bean.setOpenid(openid);
		bean.setId(id);
		List<SupplierStoreBalanceCardRecordBean> recordList = this.queryStoreBalanceCardRecord(bean);
		if(recordList.size() > 0) {
			record = recordList.get(0);
		}
		
		if(addBalance != null && addBalance != 0) {
			i = supplierStoreDao.updateStoreCardRecordBalance(record.getId(), addBalance);
			record.setBalance(record.getBalance() + addBalance);
		}
		SupplierStoreBalanceCardBean card = findStoreBalanceCardById(record.getCard_id());
		card.setAddBalance(NumberUtil.divideHave100(addBalance));
		record.setName(card.getName());
		record.setRemark( NumberUtil.divideHave100(card.getDiscount()) );
		record.setBalanceStr(NumberUtil.divideHave100(record.getBalance()));
		record.setRemark(remark);
		msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_SUPPLIER_STORE_CARD_UPDATE_RECORD, null, record);
		
		
		SupplierStoreBalanceCardCostlogBean costlog = new SupplierStoreBalanceCardCostlogBean();
		costlog.setRecord_id(record.getId());
		costlog.setCard_name(card.getName());
		costlog.setSupplier_id(record.getSupplier_id());
		costlog.setUser_id(record.getUser_id());
		costlog.setOpenid(record.getOpenid());
		costlog.setCost(addBalance);
		costlog.setSurplus(record.getBalance());
		costlog.setOrder_code(orderCode);
		costlog.setCreate_time(DateUtil.getNowDateStr());
		costlog.setRemark(remark);
		
		this.createStoreBalanceCardCostlog(costlog );
		
		
		return i;
	}
	@Override
	public int createStoreBalanceCardRecord(Integer userId, Integer cardId, Integer supplier_id) {
		SupplierUserBean user = supplierUserService.findSupplierUser(userId, supplier_id);
		SupplierBean supplier = supplierService.findSupplierById(supplier_id);
		SupplierStoreBalanceCardBean card = findStoreBalanceCardById(cardId, supplier_id);
		
		SupplierStoreBalanceCardRecordBean record = new SupplierStoreBalanceCardRecordBean();
		String eff_time = DateUtil.getNowDateStr();
		String dff_time = "2099-12-31 23:59:59";
		Integer useful_lift = card.getUseful_lift();
		if(useful_lift != null && useful_lift > 0) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, useful_lift);
			dff_time = DateUtil.formatDate(c.getTime());
		}
		record.setEff_time(eff_time);
		record.setDff_time(dff_time);
		
		record.setSupplier_id(supplier_id);
		record.setSupplier_name(supplier.getName());
		record.setCard_id(card.getId());
		record.setOpenid(user.getOpenid());
		record.setUser_id(user.getUser_id());
		record.setBalance(card.getInit_balance());
		record.setInit_balance(card.getInit_balance());
		record.setStatus(SupplierStoreBalanceCardRecordBean.STATUS_UP);
		record.setCreate_time(eff_time);
		
		createStoreBalanceCardRecord(record);
		record.setAddBalance(NumberUtil.divideHave100(record.getBalance()));
		record.setBalanceStr(record.getAddBalance());
		record.setName(card.getName());
		record.setRemark("新增储值卡/折扣卡");
		msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_SUPPLIER_STORE_CARD_ASSIGN, null, record);
		
		return record.getId();
	}
	
	@Override
	public SupplierStoreBalanceCardRecordBean findStoreBalanceCardRecordById(Integer id) {

		SupplierStoreBalanceCardRecordBean bean = new SupplierStoreBalanceCardRecordBean();
		bean.setId(id);
		List<SupplierStoreBalanceCardRecordBean> list = queryStoreBalanceCardRecord(bean );
		if(list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}


}
