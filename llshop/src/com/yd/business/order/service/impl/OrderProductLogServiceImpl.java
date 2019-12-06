/**
 * 
 */
package com.yd.business.order.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.order.bean.OrderLogConditionBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.dao.IOrderProductLogDao;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatPayInfoBean;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("orderProductLogService")
public class OrderProductLogServiceImpl extends BaseService implements IOrderProductLogService {

	@Resource
	private IOrderProductLogDao orderProductLogDao;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatPayService wechatPayService;
	
	
	@Override
	public List<OrderProductLogBean> queryOrderProductLogByUserId(int userId){
		
		OrderProductLogBean bean = new OrderProductLogBean();
		bean.setUser_id(userId);
		bean.setNotInStatus(""+ OrderProductLogBean.STATUS_CANCEL);
		
		return orderProductLogDao.queryOrderProductLog(bean);
	}
	
	@Override
	public List<OrderProductLogBean> queryOrderProductLogByUserId(int userId,int status){
		
		OrderProductLogBean bean = new OrderProductLogBean();
		bean.setUser_id(userId);
		bean.setStatus(status);
		
		return orderProductLogDao.queryOrderProductLog(bean);
	}
	
	/**
	 * 查询一定时间之前的未付款的定单
	 * @param minTime
	 * @return
	 */
	@Override
	public List<OrderProductLogBean> queryOrderProductLogByNoPay(String minTime){

		return orderProductLogDao.queryOrderProductLogByNoPay(minTime);
	}
	
	@Override
	public OrderProductLogBean findOrderProductLogById(int id){
		return orderProductLogDao.findOrderProductLogById(id);
	}

	@Override
	public OrderProductLogBean findOrderProductLogByCode(String out_trade_code) {
		return orderProductLogDao.findOrderProductLogByCode(out_trade_code);
	}

	@Override
	public void createOrUpdateOrderProductLog(OrderProductLogBean orderLog) {

		orderLog.setModify_time(DateUtil.getNowDateStrSSS());
		if(orderLog.getId() == null){
			orderProductLogDao.createOrderProductLog(orderLog);
		}else{
			orderProductLogDao.updateOrderProductLog(orderLog);
		}
	}
	
	@Override
	public OrderProductLogBean createOrderProductLogByUserConsumeInfo(UserConsumeInfoBean consume,int cost_points,int cost_money,Integer admin_id) {
		
		return createOrderProductLogByUserConsumeInfo(consume, cost_points, cost_money,0, admin_id);
	}
	
	@Override
	public OrderProductLogBean createOrderProductLogByUserConsumeInfo(UserConsumeInfoBean consume,int cost_points,int cost_money,int cost_balance,Integer admin_id) {
		
		OrderProductLogBean orderLog = new OrderProductLogBean();
		SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(consume.getSupplier_product_id());
		
		orderLog.setAdmin_id(admin_id);
		orderLog.setUser_id(consume.getUser_id());
		orderLog.setCost_money(cost_money);
		orderLog.setCost_balance(cost_balance);
		orderLog.setCost_points(cost_points);
		orderLog.setCost_price(cost_money + cost_points + cost_balance);
		orderLog.setCreate_time(DateUtil.getNowDateStrSSS());
		orderLog.setOrder_account(consume.getPhone());
		orderLog.setOrder_code(consume.getOut_trade_code());
		orderLog.setProduct_name(sp.getProduct_name());
		orderLog.setProduct_price(sp.getProduct_price());
		orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
		orderLog.setSupplier_id(sp.getSupplier_id());
		orderLog.setSupplier_name(sp.getSupplier_name());
		orderLog.setSupplier_product_id(consume.getSupplier_product_id());
		orderLog.setEvent_type(consume.getEvent_type());
		
		createOrUpdateOrderProductLog(orderLog );
		return orderLog;
	}
	
	@Override
	public void updateOrderProductLogStatusToCancel(String out_code){
		
		orderProductLogDao.updateOrderProductLogStatus(OrderProductLogBean.STATUS_CANCEL, out_code);
		
	}
	@Override
	public void updateOrderProductLogStatusToPaySuccess(String out_code){
		
		orderProductLogDao.updateOrderProductLogStatus(OrderProductLogBean.STATUS_PAYSUCCESS, out_code);
		
	}
	

	@Override
	public PartnerOrderProductBean findPartnerOrderProductByOrderCode(String orderCode){
		return orderProductLogDao.findPartnerOrderProductByOrderCode(orderCode);
	}

	@Override
	public PartnerOrderProductBean findPartnerOrderProductByPartnerOrderCode(String partner_out_trade_no){
		return orderProductLogDao.findPartnerOrderProductByPartnerOrderCode(partner_out_trade_no);
	}
	
	@Override
	public List<PartnerOrderProductBean> queryPartnerOrderProduct(PartnerOrderProductBean bean){
		return orderProductLogDao.queryPartnerOrderProduct(bean);
	}
	
	/**
	 *根据userid查询这个人有没有历史记录手机号码
	 * @param request
   	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@Override
	public OrderProductLogBean findOrderProductLogByUseridDesc(int userId){
		
		OrderProductLogBean bean = new OrderProductLogBean();
		OrderProductLogBean beanout = null;
		bean.setUser_id(userId);
		bean.setPageSize(1);
		List<OrderProductLogBean> listbean = orderProductLogDao.findOrderProductLogByUseridDesc(bean);
	    if(listbean.size()!=0){
	    	beanout	= listbean.get(0);
	    }
	    return beanout;
	}
	/**
	 * 根据产品为订单获得红包金额，已有红包金额的直接返回，没有则重新生成
	 * @param bean
   	 * @param luckyMoney
	 * @return
	 * @throws IOException 
	 */
	@Override
	public OrderProductLogBean dealOrderProductLuckyMoney(OrderProductLogBean bean) {
		//订单类型是1，3（订购成功，支付成功待订购）&&event_type=1
		if((!StringUtil.isNull(bean.getEvent_type()) && bean.getEvent_type().intValue() == UserConsumeInfoBean.EVENT_TYPE_USER_ORDER) && (bean.getStatus().intValue() == OrderProductLogBean.STATUS_SUCCESS || bean.getStatus().intValue() == OrderProductLogBean.STATUS_PAYSUCCESS)){
			OrderProductLogBean orderProductBean = this.findOrderProductLogById(bean.getId());
			if(!StringUtil.isNull(orderProductBean.getLucky_money())){
				return orderProductBean;
			}else{
				//为订单更新红包金额
				//获取该订单的随机红包
				int luckymoney = supplierProductService.returnProductLuckMoneyByOrder(bean.getSupplier_product_id());
				orderProductBean.setLucky_money(luckymoney);
				orderProductBean.setIs_sended(OrderProductLogBean.IS_SENDED_WAIT);
				this.createOrUpdateOrderProductLog(orderProductBean);
				return orderProductBean;
			}
		}else{
			bean.setLucky_money(0);
			return bean;
		}
	}
	
	@Override
	public OrderProductLogBean dealSharedOrderAndSendLuckMoney(String orderId, String openId,String share_type,String ip) {
		String remark = "";
		//获取订单
		OrderProductLogBean  orderBean = this.findOrderProductLogById(Integer.valueOf(orderId));
		// 更新订单的分享类型
		orderBean.setShare_type(Integer.valueOf(share_type));
		// 更新分享时间
		orderBean.setShare_time(DateUtil.getNowDateStrSSS());
		//获取用户
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
		//用户与订单的校验(用户ID与订单的用户ID相等)
		if(orderBean.getUser_id().intValue() == user.getId().intValue()){
			//订单是否已经发送红包
			if(!StringUtil.isNull(orderBean.getIs_sended()) && (orderBean.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_SUCCESS_HONBAO || orderBean.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_SUCCESS_BALANCE)){
				orderBean.setRemark("红包已发送！请在公众号首页查收!");
				return orderBean;
			}
			//不发送红包工单
			if(!StringUtil.isNull(orderBean.getIs_sended()) && orderBean.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_NO){
				orderBean.setRemark("订单不符合奖励条件！");
				return orderBean;
			}
			//event_type=1
			if((!StringUtil.isNull(orderBean.getEvent_type()) && orderBean.getEvent_type().intValue() == UserConsumeInfoBean.EVENT_TYPE_USER_ORDER)){
				// 订单类型是1，3（订购成功，支付成功待订购）
				if(orderBean.getStatus().intValue() != OrderProductLogBean.STATUS_SUCCESS && orderBean.getStatus().intValue() != OrderProductLogBean.STATUS_PAYSUCCESS){
					orderBean.setIs_sended(OrderProductLogBean.IS_SENDED_NO);
					this.createOrUpdateOrderProductLog(orderBean);
					orderBean.setRemark("订单类型不再活动范围，获取奖励失败！");
					return orderBean;
				}
				// 判断红包是否过期（24小时）订单超过24小时，无法获得分享红包
				if(hoursByTwoDays(orderBean.getCreate_time(),new Date()) > 24){
					orderBean.setIs_sended(OrderProductLogBean.IS_SENDED_NO);
					this.createOrUpdateOrderProductLog(orderBean);
					orderBean.setRemark("订单时间超过24小时，获取奖励失败！");
					return orderBean;
				}
				//发送用户红包
				sendLuckyMoney(openId,orderBean,ip);
				return orderBean;
			}else{
				remark = "订单类型不再活动范围，获取奖励失败！";
				orderBean.setIs_sended(OrderProductLogBean.IS_SENDED_NO);
				this.createOrUpdateOrderProductLog(orderBean);
				orderBean.setRemark(remark);
			}
		}else{
			remark = "关键信息校验失败，获取奖励失败！";
			orderBean.setIs_sended(OrderProductLogBean.IS_SENDED_NO);
			this.createOrUpdateOrderProductLog(orderBean);
			orderBean.setRemark(remark);
		}
		return orderBean;
	}
	
	/**
	 * 发送红红包给用户
	 * @param openId
	 * @param orderBean
	 * @return
	 */
	private OrderProductLogBean sendLuckyMoney(String openId,OrderProductLogBean orderBean,String ip){
		String remark = orderBean.getRemark();
		boolean isPaied = true;
		// 先将红包添加至用户余额，在从余额扣除金额提现到微信红包
		if (orderBean.getLucky_money() > WechatPayInfoBean.LUCKY_MONEY_MIN_DEFAULT_MIN) {
			// Go 余额账户
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			//红包金额小于100且大于0 生成发放到余额的流水记录，红包的流水是在发送红包方法里面生成
			if(orderBean.getLucky_money() < WechatPayInfoBean.LUCKY_MONEY_MIN_WECHAT){
				// 生成流水单,商户订单号
				String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_SHAREBONUS, orderBean.getUser_id());
				userConsumeInfoService.createConsumeInfo(orderBean.getOrder_account(), orderBean.getLucky_money(), orderBean.getSupplier_product_id(), orderBean.getUser_id(), null, out_no, UserConsumeInfoBean.INTERFACETYPE_SHARE_LUCKYMONEY, UserConsumeInfoBean.EVENT_TYPE_USER_BALANCE_INCOME);
				// 更新流水为支付成功
				UserConsumeInfoBean customerInfoBean = userConsumeInfoService.findUserConsumeInfo(out_no);
				customerInfoBean.setStatus(UserConsumeInfoBean.STATUS_SUCCESS);
				userConsumeInfoService.updateUserConsumeInfo(customerInfoBean);
			}
			//更新用户余额
			int money =  user.getBalance() + orderBean.getLucky_money();
			user.setBalance(money);
			userWechatService.update(user);
			remark = "由于微信限制，红包金额已入现金账户余额，注意查收！";
			int is_sended = OrderProductLogBean.IS_SENDED_SUCCESS_BALANCE;
			//当红包金额大于1元时，提现至微信红包；判断是否发微信红包
			if (orderBean.getLucky_money() >= WechatPayInfoBean.LUCKY_MONEY_MIN_WECHAT) {
				// Go 微信
				log.info("GO Send user luckymoney start;openId:"+openId+",orderBean.getLucky_money():"+orderBean.getLucky_money()+",ip:"+ip);
				isPaied = wechatPayService.payBonusLimit200(openId,orderBean.getLucky_money(), ip,"购后返现金红包",1);
				log.info("GO Send user luckymoney end;isPaied:"+isPaied);
				if (isPaied) {
					is_sended = OrderProductLogBean.IS_SENDED_SUCCESS_HONBAO;
					remark = "红包发送成功，请在公众号首页查收！";
				} else {
					is_sended = OrderProductLogBean.IS_SENDED_FAILD;
					money =  user.getBalance() - orderBean.getLucky_money();
					user.setBalance(money);
					userWechatService.update(user);
					remark = "红包发送失败，请联系管理员！";
				}
			}
			orderBean.setIs_sended(is_sended);
		}
		// 更新红包发送状态；
		this.createOrUpdateOrderProductLog(orderBean);
		orderBean.setRemark(remark);
		return orderBean;
	}
	
	private int hoursByTwoDays(String createdate,Date nowTime){
		Date createTime;
		try {
			createTime = DateUtil.parseDateDetial(createdate);
			return DateUtil.daysBetween(createTime,nowTime);
		} catch (ParseException e) {
			log.error(e,e);
			e.printStackTrace();
			return 100;
		}
	}
	

	/**
	 * 查询用户记录号码
	 * @param userId
	 * @return
	 */
	@Override
	public List<OrderProductLogBean> queryOrderLogLastTen(int userId){

		OrderProductLogBean userRecodePhone = new OrderProductLogBean();
		userRecodePhone.setUser_id(userId);
		userRecodePhone.setPageSize(10);
		List<OrderProductLogBean> listUserRecodePhone = orderProductLogDao.queryOrderLogByDesc(userRecodePhone);
	    return listUserRecodePhone;
		
	}
	
	/**
	 * 查询已经付款的用户但是未领取红包
	 * @return
	 */
	@Override
	public List<OrderLogConditionBean> queryLucykIsNullUser(){
		OrderLogConditionBean bean = new OrderLogConditionBean();
		List<Integer> list =  new ArrayList<Integer>();
		list.add(OrderLogConditionBean.STATUS_SUCCESS);
		list.add(OrderLogConditionBean.STATUS_PAYSUCCESS);
		bean.setLucky_money(WechatPayInfoBean.LUCKY_MONEY_MIN_DEFAULT_MIN);
		bean.setRemark("您身边的在线加油站");
		bean.setEvent_type(UserConsumeInfoBean.EVENT_TYPE_USER_ORDER);
		bean.setIs_sended(OrderLogConditionBean.IS_SENDED_WAIT);
		bean.setStatusarr(list);
		return orderProductLogDao.queryLucykIsNullUser(bean);
	}
	
	

	/**
	 * 给余额不够，调用接口超时超时的订单，状态改变
	 */
	@Override
	public void updateOrderProductLogStatusOrder(String out_code,int status){
		
		orderProductLogDao.updateOrderProductLogStatus(status, out_code);
		
	}
	

	/**
	 * 查询订单表,返回多个订单
	 */
    public 	List<OrderProductLogBean> queryOrderProductLog(OrderProductLogBean bean){
		return orderProductLogDao.queryOrderProductLog(bean);
    }

	@Override
	public PageinationData queryOrderProductLogPage(OrderProductLogBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<OrderProductLogBean> showList = orderProductLogDao.queryOrderProductLogPage(bean);
		    int count = orderProductLogDao.countOrderProductLog(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;
	}

	@Override
	public void deleteOrderProductLog(OrderProductLogBean bean) {
		// TODO Auto-generated method stub
		orderProductLogDao.deleteOrderProductLog(bean);
	}

	@Override
	public void editOrderProductLog(OrderProductLogBean bean) {
		try{
			if(StringUtil.isNull(bean.getId())){
				bean.setCreate_time(DateUtil.getNowDateStr());
				orderProductLogDao.insertOrderProductLogAdmin(bean);
			}else{
				bean.setModify_time(DateUtil.getNowDateStr());
				orderProductLogDao.updateOrderProductLogAdmin(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
	}
	
}




