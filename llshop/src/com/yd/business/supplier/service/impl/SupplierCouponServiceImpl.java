package com.yd.business.supplier.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.order.bean.AreaData;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.product.bean.SupplierProductAttachBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.dao.ISupplierProductDao;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierCouponRuleBean;
import com.yd.business.supplier.dao.ISupplierCouponDao;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

@Service("supplierCouponService")
public class SupplierCouponServiceImpl extends BaseService implements
ISupplierCouponService {
	@Resource
	private ISupplierCouponDao supplierCouponDao;
	@Resource
	private IOrderService orderService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private ISupplierProductDao supplierProductDao;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IChannelProductService channelProductService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IConfigAttributeService configAttributeService;
	
	//存放进行中的定单号，线程同步的，避免同一时刻多次重复定购
		private static ConcurrentHashMap<String,Object> runningCacheMap = new ConcurrentHashMap<String, Object>();
		
		
		
		/**
		 * 领取优惠卷的时候查询可以展示的优惠卷信息
		 */
		@Override
		public List<SupplierCouponConfigBean> querySureShowCoupon(UserWechatBean user) {
			// TODO Auto-generated method stub
			try{
				List<SupplierCouponConfigBean> returnlist = new ArrayList<SupplierCouponConfigBean>();
				List<SupplierCouponConfigBean>  list = queryCouponInfo();			//查询目前配置的所有可用的优惠卷信息
				for(SupplierCouponConfigBean bean : list){
					//根据优惠卷id查询优惠卷展示规则
					List<SupplierCouponRuleBean> ruleSqlList = QueryShowCouponRuleByCouponid(bean.getId());	
					//从优惠卷规则表中把优惠卷的规则名称给拼接上
					bean = spliceCouponRuleName(bean);
					//给优惠卷的时间进行截取把时分秒都给截取吊
					bean.setBegin_time(interceptTime(bean.getBegin_time()));
					bean.setEnd_time(interceptTime(bean.getEnd_time()));
					//判断优惠卷展示规则是否复合条件
					if(StringUtil.isNull(ruleSqlList) || ruleSqlList.size() == SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO ){
						returnlist.add(bean);
					}else{
						boolean whetherCanShow = judgeShowCouponRuleSQL(ruleSqlList,user);
							if(whetherCanShow){
								returnlist.add(bean);
							}
				}
				}
				return returnlist;
			}
			catch(Exception e){
				log.error(e, e);
			}
		return null;
		}

		//给优惠卷的时间中的时分秒给截取掉,只留下年月日.	如:2016-01-01 00:00: 截取为:2016-01-01
		public String  interceptTime(String time){
			String interceptTime =  time.substring(SupplierCouponConfigBean.SUBSTRING_ZEOR,SupplierCouponConfigBean.SUBSTRING_TEN);
			return interceptTime ;
		}
		
	/**
	 * 查询目前配置所有的可以使用的优惠卷
	 */
	@Override
	public List<SupplierCouponConfigBean> queryCouponInfo() {
		SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
		bean.setStatus(SupplierCouponConfigBean.USE_STATUS);
		bean.setBegin_time(DateUtil.getNowDateStr());
		bean.setEnd_time(DateUtil.getNowDateStr());
		return supplierCouponDao.queryCouponInfo(bean);
	}
		
		
	
	
	/**
	 * 根据优惠卷表中的id查询优惠卷规则表中展示优惠卷的所有sql
	 * */
	private List<SupplierCouponRuleBean> QueryShowCouponRuleByCouponid(Integer coupon_id){
		SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
		bean.setCoupon_id(coupon_id);
		bean.setStatus(SupplierCouponRuleBean.USE_STATUS);
		bean.setType(SupplierCouponRuleBean.SHOW_COUPON_RULE_TYPE);
		bean.setBegin_time(DateUtil.getNowDateStr());
		bean.setEnd_time(DateUtil.getNowDateStr());
		return supplierCouponDao.queryCouponRuleSQLByCouponId(bean);
	}
		
	
	
	/**
	 * 拼接优惠卷规则中规则名称
	 * */
	public  SupplierCouponConfigBean  spliceCouponRuleName(SupplierCouponConfigBean bean) {
		List<SupplierCouponRuleBean> ruleList = queryReceiveCouponRuleSQLById(bean.getId(),SupplierCouponRuleBean.SHOW_COUPON_RULE_TYPE);	
		if(!StringUtil.isNull(ruleList) && ruleList.size() != SupplierCouponConfigBean.COUPON_RULE_LIST_IS_ZERO){
			if(ruleList.size() == SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ONE){
				bean.setRule_name(ruleList.get(SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO).getRule_name());
			}
			else{
				bean.setRule_name(ruleList.get(SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO).getRule_name());
				for(int i = SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ONE; i<ruleList.size();i++){
					bean.setRule_name(ruleList.get(i).getRule_name() +";"+ bean.getRule_name());
				}
			}
		}
		return bean;
	}
	
	
	
	/**
	 * 根据优惠卷表中的id领取优惠卷规则表中展示优惠卷的sql
	 * */
	private List<SupplierCouponRuleBean> queryReceiveCouponRuleSQLById(Integer coupon_id,Integer type){
		SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
		bean.setCoupon_id(coupon_id);
		bean.setStatus(SupplierCouponRuleBean.USE_STATUS);
		bean.setType(type);
		bean.setBegin_time(DateUtil.getNowDateStr());
		bean.setEnd_time(DateUtil.getNowDateStr());
		return supplierCouponDao.queryCouponRuleSQLByCouponId(bean);
	}
	
	
	
	/**
	 * 提取展示优惠卷的规则出来判断此优惠卷是否可以展示
	 */
	private boolean judgeShowCouponRuleSQL(List<SupplierCouponRuleBean> ruleSqlList,UserWechatBean user){
		boolean whetherCanShow = true;
		try{
			//替换sql中的占位符
				for(SupplierCouponRuleBean ruleBean : ruleSqlList){
					ruleBean.setSQL(ruleBean.getSQL().replace("#userid#", String.valueOf(user.getId())));
					ruleBean.setSQL(ruleBean.getSQL().replace("#openid#", String.valueOf(user.getOpenid())));
					int count = couponRuleSQLCounValue(ruleBean.getSQL());						//执行该优惠卷对应规则表中sql判断是否有返回数据
					if( count == SupplierCouponRuleBean.RULE_SQL_COUNT_MIN_VALUE){
						whetherCanShow = false ; 
						break;
					}
				}
		}catch(Exception e){
			log.error(e, e);
		}
	
	return whetherCanShow;

	}
	
	
	
	
	
	
	
	
	/**
	 * 根据优惠卷规则表中该优惠卷规则，返回领取结果
	 */	
	@Override
	public String reveiveCouponResult(Integer coupon_id, UserWechatBean user) {
		// TODO Auto-generated method stub
		//设置"网络原因领取失败,请稍后在试"返回结果,如果后面方法报错,界面将会弹出此结果
		String reveiveResult = configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.RECEIVE_COUPON_INTERNET_BAD);		

		try{
		List<SupplierCouponRuleBean> ruleSqlList = queryReceiveCouponRuleSQLById(coupon_id,SupplierCouponRuleBean.RECEIVE_COUPON_RULE_TYPE);		//把所有的sql条件通过list方式给查出来
		if(StringUtil.isNull(ruleSqlList) || ruleSqlList.size() == SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO ){				//如果从优惠卷规则表中查询领取优惠卷的规则为空
			reveiveResult = receiveCoupon(coupon_id,user);	
			return reveiveResult;
		}else{
			boolean whetherReceiveCoupon = judgeReceiveCouponRuleSQL(ruleSqlList,user);			//如果有领取规则判断领取规则的条件是否成立
			if(whetherReceiveCoupon){															//满足领取规则
				reveiveResult = receiveCoupon(coupon_id,user);	
				return reveiveResult;
			}else{
				//根据优惠卷id到规则表中查询该优惠卷的规则信息
				reveiveResult = findCouponRuleName(coupon_id,SupplierCouponRuleBean.SHOW_COUPON_RULE_TYPE);
				return reveiveResult;
			}
		}
			}catch(Exception e){
			log.error(e, e);
			return reveiveResult;														//如果前面报错,返回网络原因的错误信息s
		}
	}
	
	/**
	 * 根据优惠卷id和type到优惠卷规则表中查询该优惠卷的规则名称
	 * */
	public String findCouponRuleName(Integer coupon_id, Integer type){
		SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
		bean.setCoupon_id(coupon_id);
		bean.setType(type);
		bean = supplierCouponDao.findCouponRule(bean);
		return bean.getRule_name();
	}
	
	
	/**
	 * 领取优惠卷,返回领取领取优惠卷结果
	 * */
	private String receiveCoupon(Integer coupon_id, UserWechatBean user){
		String reveiveResult = configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.RECEIVE_COUPON_INTERNET_BAD);		
		try{
			SupplierCouponConfigBean conponInfo = findCouponInfoByCouponid(coupon_id);
			SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
			bean.setUserid(user.getId());								//设置用户id
			bean.setCoupon_id(coupon_id);								//设置优惠卷id
			bean.setUse_time(conponInfo.getBegin_time());				//设置生效时间
			bean.setExpire_time(conponInfo.getEnd_time());				//设置失效时间
			boolean UserReceiveLimit = UserReveiceCouponLimit(coupon_id,user.getId());		//判断改用户领取这个优惠卷的上线是多少
			if(UserReceiveLimit){											//达到领取条件
				int count = reduceCouponNum(coupon_id);						//减少此优惠卷数量
				if(count > SupplierCouponRuleBean.UPDATE_COUPON_MIN_NUMBER){						//判断表中是否还有剩余的优惠卷
					InsertGetCouponRecord(bean);			//领取优惠卷,在优惠卷记录表中加一条记录
					return  configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.RECEIVE_COUPON_SUCCESS_RESULT);
				}else{
					return  configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.COUPON_ZERO);

				}
			}else{
				return  configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.USER_RECEIVE_COUPON_LIMIT);
			}
		}catch(Exception e){
			log.error(e, e);
		}
		return reveiveResult;
	}
	
	
	
	

	//根据优惠卷id从优惠卷配置表中查询优惠卷信息
			public SupplierCouponConfigBean findCouponInfoAndRuleNameByCouponid(Integer coupon_id){
				SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
				bean.setId(coupon_id);
				bean.setStatus(SupplierCouponConfigBean.USE_STATUS);
				bean.setBegin_time(DateUtil.getNowDateStr());
				bean.setEnd_time(DateUtil.getNowDateStr());
				bean = supplierCouponDao.findCouponConfigInfo(bean);
				bean = spliceCouponRuleName(bean);
				return bean;
			}
	
	
	
			/**
			 * 	 根据优惠卷id从优惠卷配置表中查询优惠卷信息 
			 * */
			public SupplierCouponConfigBean findCouponInfoByCouponid(Integer coupon_id){
				SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
				bean.setId(coupon_id);
				bean.setStatus(SupplierCouponConfigBean.USE_STATUS);
				bean.setBegin_time(DateUtil.getNowDateStr());
				bean.setEnd_time(DateUtil.getNowDateStr());
				return supplierCouponDao.findCouponConfigInfo(bean);
			}
	
	
	
	

	/**
	 *用户获得优惠卷插入记录
	 */
	@Override
	public void InsertGetCouponRecord(SupplierCouponRecordBean bean) {
			bean.setStatus(SupplierCouponRecordBean.USE_STATUS);							//设置状态
			bean.setStatus_description(SupplierCouponRecordBean.USER_STATUS_DESCRIPTION);	//设置状态描述
			bean.setCreate_time(DateUtil.getNowDateStr());									//设置当前系统日期
			supplierCouponDao.InsertGetCouponRecord(bean);
	}

	
	
	
	/**
	 *  查询用户目前自己已经拥有的优惠卷
	 * */
	@Override
	public List<SupplierCouponRecordBean> queryMyAllCoupon(Integer userid,Integer supplier_id) {
		// TODO Auto-generated method stub
		List<SupplierCouponRecordBean> myCouponList =  new ArrayList<SupplierCouponRecordBean>();
		try{
			SupplierCouponRecordBean ruleBean = new SupplierCouponRecordBean();
			ruleBean.setUserid(userid);
			List<SupplierCouponRecordBean> recordList = queryMycoupon(ruleBean); 		//通过userid,到优惠卷记录表查询我的优惠卷记录
			for(SupplierCouponRecordBean recordBeanNew : recordList){
				//从前台传过来的产品id,和自己的优惠卷进行比较,如果没有匹配上就给这个状态改为99.
				recordBeanNew = matchingCouponUseProduct(supplier_id,recordBeanNew);
				
				//判断如果匹配到了,该优惠卷可以使用此产品
				if(recordBeanNew.getStatus() == SupplierCouponConfigBean.MATCH_TO_PRODUCT ){
				//查询此优惠卷的规则是什么做出判断
					 List<SupplierCouponRuleBean> couponRuleList = QueryUseCouponRuleByCouponid(recordBeanNew.getCoupon_id());
					 if(StringUtil.isNull(couponRuleList) || couponRuleList.size() == SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO ){				//如果从优惠卷规则表中查询领取优惠卷的规则为空
						 //如果此优惠卷既可以使用此
						 recordBeanNew.setRule_name(configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.COUPON_NO_USE_RULE));
						 

					 }else{
						if(couponRuleList.size() == SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ONE){
							recordBeanNew.setRule_name(couponRuleList.get(SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO).getRule_name());
						}
						else{
							recordBeanNew.setRule_name(couponRuleList.get(SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO).getRule_name());
							for(int i = SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ONE; i<couponRuleList.size();i++){
								recordBeanNew.setRule_name(couponRuleList.get(i).getRule_name() +";"+ recordBeanNew.getRule_name());
							}
						}
					 }
				}else{
					//如果该优惠卷没有匹配此产品,那么给优惠卷规则名称设置为该优惠卷不可展示字段
					recordBeanNew.setRule_name(configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.NO_USE_COUPON_SHOW));
				}
				//根据优惠卷id还有优惠卷的type确定查询优惠卷的规则名称
				myCouponList.add(recordBeanNew);
			}
		}catch(Exception e){
			log.error(e, e);
		}
		return myCouponList;
	}
	
	
	
	
	
	/**
	 * 根据优惠卷表中的id查询优惠卷规则表中使用优惠卷的所有sql
	 * */
	private List<SupplierCouponRuleBean> QueryUseCouponRuleByCouponid(Integer coupon_id){
		SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
		bean.setCoupon_id(coupon_id);
		bean.setStatus(SupplierCouponRuleBean.USE_STATUS);
		bean.setType(SupplierCouponRuleBean.USE_COUPON_RULE_TYPE);
		bean.setBegin_time(DateUtil.getNowDateStr());
		bean.setEnd_time(DateUtil.getNowDateStr());
		return supplierCouponDao.queryCouponRuleSQLByCouponId(bean);
	}
		
	
	
	
	/**
	 * 根据优惠卷记录表中的id,在优惠卷记录表中更新订单
	 */
	@Override
	public void updateOrderCodeCouponRecordById(Integer coupon_record_id,String out_no) {
		// TODO Auto-generated method stub
		SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
		bean.setId(coupon_record_id);					//设置记录表中的id
		bean.setOrder_code(out_no);						//设置订单编号
		supplierCouponDao.updateOrderCodeCouponRecordById(bean);
		
	}
	
	
	

	@Override
	public void updateStatusBecomeUserByOrderCode(String order_code , String product_name) {
		// TODO Auto-generated method stub
		int count = countCouponRecordByOrderCode(order_code);				//根据订单号在优惠卷记录表中查询是否有这个优惠卷正在使用
		if(count > SupplierCouponRecordBean.COUNT_NUMBER_ZERO){				//如果返回count的结果大于优惠卷总数
			updateStatusyByOrderCode(order_code,product_name);							//根据订单号更改优惠卷记录表中的状态,把状态更改为已经使用状态
		}
		
		
		
	}
	
	
	
	/**
	 * 查询该用户已经领取几个该优惠卷
	 */
	private int countCouponRecordByOrderCode(String order_code){
		SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
		bean.setOrder_code(order_code);
		return 	supplierCouponDao.findRecordCountByOrderCode(bean);
	}
	
	/**
	 * 根据优惠卷订单号,更新优惠卷状态为使用状态
	 */
	public void updateStatusyByOrderCode(String order_code,String product_name) {
		// TODO Auto-generated method stub
		SupplierCouponRecordBean bean = new SupplierCouponRecordBean();	
		bean.setOrder_code(order_code);													//设置订单编号
		bean.setProduct_name(product_name);												//设置产品名称
		bean.setStatus(SupplierCouponRecordBean.USERED_STATUS);							//设置使用状态
		bean.setStatus_description(SupplierCouponRecordBean.USERED_STATUS_DESCRIPTION);//设置状态描述
		bean.setModify_time(DateUtil.getNowDateStr());									//设置修改时间为当前时间
		supplierCouponDao.updateStatusyByOrderCode(bean);
		
	}
	
	
	/**
	 *	从优惠卷记录表中查询优惠信息
	 */
	@Override
	public SupplierCouponRecordBean findCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return supplierCouponDao.findCouponRecord(bean);
	}

	
	/**
	 *	根据订单号到优惠卷记录表中查询优惠卷记录
	 */
	@Override
	public SupplierCouponRecordBean findCouponRecordByOrderCode(String order_code) {
		// TODO Auto-generated method stub
		SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
		bean.setOrder_code(order_code);
		return supplierCouponDao.findCouponRecordByOrderCode(bean);
	}
	
	
	/**
	 *查询自己拥有的优惠卷 , 优惠卷配置表和优惠卷记录表关联查询
	 */
	@Override
	public List<SupplierCouponRecordBean> queryMycoupon(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		bean.setStatus(SupplierCouponRecordBean.USE_STATUS);
	//	bean.setUse_time(DateUtil.getNowOnlyDateStr());
	//  bean.setExpire_time(DateUtil.getNowOnlyDateStr());
		List<SupplierCouponRecordBean> list = supplierCouponDao.queryMycoupon(bean);
		List<SupplierCouponRecordBean> listNew = new ArrayList<SupplierCouponRecordBean>(); 
		try{
			for(SupplierCouponRecordBean beanNew : list){
				beanNew.setUse_time(beanNew.getUse_time().substring(SupplierCouponRecordBean.SUBTRING_NUMBER_ZERO,SupplierCouponRecordBean.SUBTRING_NUMBER_TEN));
				beanNew.setExpire_time(beanNew.getExpire_time().substring(SupplierCouponRecordBean.SUBTRING_NUMBER_ZERO,SupplierCouponRecordBean.SUBTRING_NUMBER_TEN));
				listNew.add(beanNew);
			}
			
		}catch(Exception e){
			log.error(e, e);
		}
		return listNew;
	}
	
	
	
	
	/**
	 * 根据优惠卷规则表的规则使用优惠卷
	 */
	@Override
	public String useCouponResult(Integer coupon_id, Integer userid) {
		// TODO Auto-generated method stub
		//设置"网络原因领取失败,请稍后在试"返回结果,如果后面方法报错,界面将会弹出此结果
		String	reveiveResult=	configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.USE_COUPON_INTERNET_BAD);
		try{
		List<SupplierCouponRuleBean> ruleSqlList = queryReceiveCouponRuleSQLById(coupon_id,SupplierCouponRuleBean.USE_COUPON_RULE_TYPE);		//把所有的sql条件通过list方式给查出来
		if(StringUtil.isNull(ruleSqlList) || ruleSqlList.size() == SupplierCouponRuleBean.COUPON_RULE_LIST_IS_ZERO ){				//如果从优惠卷规则表中查询领取优惠卷的规则为空
			reveiveResult=	configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.COUPON_CAN_USE);
			return reveiveResult;
		}else{
			boolean whetherReceiveCoupon = judgeUseCouponRuleSQL(ruleSqlList,userid);			//如果有使用规则判断领取规则的条件是否成立
			if(whetherReceiveCoupon){															//满足领取规则
				reveiveResult=	configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.COUPON_CAN_USE);
				return reveiveResult;
			}else{  
				//根据优惠卷id到规则表中查询该优惠卷的规则信息  
				reveiveResult=	configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.COUPON_POP_TYPE,SupplierCouponRuleBean.COUPON_NO_CAN_USE);
				return reveiveResult;
			}
		}
			}catch(Exception e){
			log.error(e, e);
			return reveiveResult;														//如果前面报错,返回网络原因的错误信息s
		}
		
	}


	
	
	/**
	 * 判断使用优惠卷的规则是否满足
	 * */
		private boolean judgeUseCouponRuleSQL(List<SupplierCouponRuleBean> ruleSqlList,Integer user){
			boolean whetherReceiveCoupon = true ;
			try{
				for(SupplierCouponRuleBean bean : ruleSqlList){
					bean.setSQL(bean.getSQL().replace("#userid#", String.valueOf(user)));
					int count = couponRuleSQLCounValue(bean.getSQL());						//执行该优惠卷对应规则表中sql判断是否有返回数据
					if(count ==  SupplierCouponRuleBean.RULE_SQL_COUNT_MIN_VALUE ){
						whetherReceiveCoupon = false ;
						break;
					}
				}
				
			}catch(Exception e){
				log.error(e, e);
			}
			
			return whetherReceiveCoupon;
		}
	
	
	
	
	
	/**
	 * 查询优惠卷记录表中记录
	 */
	public List<SupplierCouponRecordBean> queryCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		bean.setStatus(SupplierCouponRecordBean.USE_STATUS);
		return supplierCouponDao.queryCouponRecord(bean);
	}
	
	
	
	/**
	 * 查询优惠卷配置表单个优惠卷信息
	 */
	public SupplierCouponConfigBean  findCouponConfigInfo(SupplierCouponConfigBean bean){
		bean.setStatus(SupplierCouponConfigBean.USE_STATUS);
		return  supplierCouponDao.findCouponConfigInfo(bean); 
	}
	
	
	
	/**
	 * 匹配该产品与用户拥有的优惠卷是否可用
	 */
	public SupplierCouponRecordBean  matchingCouponUseProduct(Integer supplier_id,SupplierCouponRecordBean myCouponbean ){
		if(StringUtil.isNull(myCouponbean.getCouponshow_product())){
			myCouponbean.setStatus(SupplierCouponConfigBean.MATCH_TO_PRODUCT);
		}else{
		List<String> list = Arrays.asList(myCouponbean.getCouponshow_product().split(","));
		for(String string : list){
			String supplier_id_String =supplier_id.toString();
			Integer c = string.compareTo(supplier_id_String);
			if(c == SupplierCouponConfigBean.COUPON_SHOW_PRODUCT_ZERO){
				myCouponbean.setStatus(SupplierCouponConfigBean.MATCH_TO_PRODUCT);
			}
		}
		}
		return myCouponbean;
	}
	
	
	
	
	@Override
	public PageinationData queryAdminConponConfigPage(SupplierCouponConfigBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<SupplierCouponConfigBean> showList = supplierCouponDao.queryCouponConfigPage(bean);
		    int count = supplierCouponDao.couponConfigCount(bean);
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
	public PageinationData queryAdminConponRecordPage(SupplierCouponRecordBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<SupplierCouponRecordBean> showList = supplierCouponDao.queryCouponRecordPage(bean);
		    int count = supplierCouponDao.couponRecordCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;	}

	@Override
	public PageinationData queryAdminConponRulePage(SupplierCouponRuleBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<SupplierCouponRuleBean> showList = supplierCouponDao.queryCouponRulePage(bean);
		    int count = supplierCouponDao.conponRuleCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;	
		}

	/**
	 * 删除优惠卷目前展示的商品
	 */
	@Override
	public String deleteShowProduct(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		SupplierCouponConfigBean couponConfigBean = supplierCouponDao.findCouponConfigInfo(bean);
		List<String> coupon_show_product_list = new  ArrayList<String>();
		if(!StringUtil.isNull(couponConfigBean.getCouponshow_product())){
			StringTokenizer st = new StringTokenizer(couponConfigBean.getCouponshow_product(),",");
		    while(st.hasMoreTokens() ){
		    String aa = String.valueOf(st.nextToken());
		    if(!aa.equals(bean.getCouponshow_product())){
		    	coupon_show_product_list.add(aa);
		    }
//		    System.out.println(aa+"你好");
		    }
		    for(int i = SupplierCouponConfigBean.INT_ZERO;i<coupon_show_product_list.size();i++){
		    	if(i == SupplierCouponConfigBean.INT_ZERO){
		    		bean.setCouponshow_product(coupon_show_product_list.get(i));
		    	}else{
		    		bean.setCouponshow_product(bean.getCouponshow_product()+","+coupon_show_product_list.get(i));
		    	}
		    }
		supplierCouponDao.updateCouponConfig(bean);
		}
		
		return configCruxService.getValueByTypeAndKey(SupplierCouponConfigBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponConfigBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS);
	}
	
	
	
	
	/**
	 * 增加优惠卷目前展示的商品
	 */
	@Override
	public String addShowProduct(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		SupplierCouponConfigBean couponConfigBean = supplierCouponDao.findCouponConfigInfo(bean);
		List<String> coupon_show_product_list = new  ArrayList<String>();
		if(!StringUtil.isNull(couponConfigBean.getCouponshow_product())){
			StringTokenizer st = new StringTokenizer(bean.getCouponshow_product(),",");
			while(st.hasMoreTokens() ){
			    String aa = String.valueOf(st.nextToken());
			    coupon_show_product_list.add(aa);
			 }
			
			StringTokenizer stb = new StringTokenizer(couponConfigBean.getCouponshow_product(),",");
			while(stb.hasMoreTokens() ){
				 String bb = String.valueOf(stb.nextToken());
				 coupon_show_product_list.add(bb);
//				 System.out.println(bb);
			}
			HashSet h = new HashSet(coupon_show_product_list);   
			coupon_show_product_list.clear();   
			coupon_show_product_list.addAll(h);
			
			 for(int i = SupplierCouponConfigBean.INT_ZERO;i<coupon_show_product_list.size();i++){
			    	if(i == SupplierCouponConfigBean.INT_ZERO){
			    		bean.setCouponshow_product(coupon_show_product_list.get(i));
			    	}else{
			    		bean.setCouponshow_product(bean.getCouponshow_product()+","+coupon_show_product_list.get(i));
			    	}
			    }
			supplierCouponDao.updateCouponConfig(bean);
		}else{
			supplierCouponDao.updateCouponConfig(bean);
		}
		return configCruxService.getValueByTypeAndKey(SupplierCouponConfigBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponConfigBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通过手机号查询商户客户下可用的商品
	 */
	@Override
	public List<CustomerSupplierProductBean> queryCustomerProductByPhone(Integer customer_id,String phone,Integer coupon_id){
		
		AreaData areaData = orderService.getAreaDataByPhone(phone);
		if(areaData == null) return null;
		SupplierProductBean condition = new SupplierProductBean();
		condition.setCustomer_id(customer_id);
		condition.setStatus(SupplierProductBean.STATUS_UP);
		condition.setNow_time(DateUtil.getNowDateStr());
		
		List<CustomerSupplierProductBean> listCsp = new ArrayList<CustomerSupplierProductBean>();
		
		//查询客户下有多少商户
		List<SupplierBean> suppliers = supplierService.querySupplierByCustomerId(customer_id);
		for(SupplierBean sb : suppliers){
			condition.setSupplier_id(sb.getId());
			//如果不可以销售，则必须有库存
			if(sb.getIssale() == SupplierBean.ISSALE_FALSE){
				condition.setStore_num(1);
			}else{
				condition.setStore_num(null);
			}
			
			//有没有区域数据
			if(areaData != null){
				condition.setProduct_brand_name(areaData.getBrand());
				condition.setProduct_province(areaData.getProvince());
			}
//第一种写法，目前此代码已经作废			
//			//目前是测试方法，产品通过sql查询出来
//			SupplierCouponRuleBean  couponRuleBean = queryRuleSql(1);	//查询规则表中sql
//			List<SupplierProductBean> csp =queryProductByCouponRuleSQL(couponRuleBean.getSQL());

			
			
			//查询每个商户下的商品
			List<SupplierProductBean> csp = supplierProductDao.querySupplierProduct(condition);
			List<SupplierProductBean> cspnew = findCouponProduct(coupon_id,csp);
			if(cspnew.size() >0){
				listCsp.add(new CustomerSupplierProductBean(areaData,sb,cspnew));
			}
			
			//如果查出的数据不是全国的，那么界面上再展示出来全国的
			if(areaData == null || !AreaData.PROVINCE_QG.equals(areaData.getProvince())){
				//查询全国的商品
				condition.setProduct_province(AreaData.PROVINCE_QG);
				cspnew = supplierProductDao.querySupplierProduct(condition);
				if(cspnew.size() >0){
					listCsp.add(new CustomerSupplierProductBean(areaData,sb,cspnew));
				}
			}
		}
		return listCsp;
	}
	
	//根据优惠卷id查询规则表一条数据
//	public SupplierCouponRuleBean queryCouponRuleById(Integer coupon_id){
//		SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
//		bean.setCoupon_id(coupon_id);
//		bean.setStatus(SupplierCouponRuleBean.USE_STATUS);
//		bean.setBegin_time(DateUtil.getNowDateStr());
//		bean.setEnd_time(DateUtil.getNowDateStr());
//		return supplierCouponDao.queryCouponRuleById(bean);
//	}
	
	//把规则表中的sql把一些变量带入在重新计算一次	
	private List<SupplierProductBean> queryProductByCouponRuleSQL(String couponRuleSql){
		return supplierCouponDao.queryProductByCouponRuleSQL(couponRuleSql);
	}
	

	/**
	 * 根据优惠卷表中的定义判断此优惠卷是否可以展示
	 */
	public List<SupplierProductBean> findCouponProduct(Integer id ,List<SupplierProductBean> csp){
		List<SupplierProductBean> cspnew = new ArrayList<SupplierProductBean>();
		try{
			SupplierCouponConfigBean beanIn = new SupplierCouponConfigBean();
			beanIn.setId(id);
			SupplierCouponConfigBean beanOut =  findCouponConfigInfo(beanIn); 
			List<String> list = Arrays.asList(beanOut.getCouponshow_product().split(","));
			for(SupplierProductBean bean : csp){
			for(String string : list){
				int judgeSame =string.compareTo(bean.getId().toString());
				if(judgeSame == SupplierProductBean.NO_MATCHING_PRODUCT){				//如果没有匹配到商品
					bean.setCoupon_discount_product(SupplierProductBean.COUPON_SHOW_PRODUCT);
				}
			}
			cspnew.add(bean);
			}
		}catch(Exception e){
			log.error(e, e);
		}
		return cspnew;
	}

	/**
	 * 
	 */
	@Override
	public void changeCouponRecodeUserd(Integer userid, Integer coupon_id,Integer coupon_record_id) {
		// TODO Auto-generated method stub
		SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
		bean.setId(coupon_record_id);
		bean.setUserid(userid);
		bean.setCoupon_id(coupon_id);
		bean.setStatus(SupplierCouponRecordBean.USERED_STATUS);
		bean.setStatus_description(SupplierCouponRecordBean.USERED_STATUS_DESCRIPTION);
		supplierCouponDao.changeCouponRecodeUserd(bean);
	}
	
	
	
	/**
	 * 处理用户的订购商品逻辑
	 */
	@Override
	public OrderProductLogBean orderProductByUser(String out_trade_code,String param,Integer coupon_id,Integer coupon_record_id){

		//订购日志信息
		OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
//		ChannelBean channel = null;
//		if(orderLog != null ){
//			channel = channelService.findChannelById(orderLog.getChannel_id());
//		}
		if(runningCacheMap.get(out_trade_code) == null) { //只有不在runningCacheMap  中的定单号才去定购
			runningCacheMap.put(out_trade_code, "running");
			//只有是同步的，才需要在支付成功后，再次调用时，去订购
			if(orderLog == null ||  orderLog.getStatus() == OrderProductLogBean.STATUS_PAYSUCCESS
			|| orderLog.getStatus() == OrderProductLogBean.STATUS_NEED_AGAIN_ORDER ){
				UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(out_trade_code);
				
				if(orderLog == null){
					orderLog = new OrderProductLogBean();
					orderLog.setOrder_account(consumeInfo.getPhone());
					orderLog.setCreate_time(DateUtil.getNowDateStrSSS());
					orderLog.setOrder_code(out_trade_code);
					orderLog.setStatus(OrderProductLogBean.STATUS_WAIT);
					orderLog.setUser_id(consumeInfo.getUser_id());
					orderLog.setSupplier_product_id(consumeInfo.getSupplier_product_id());
					orderLog.setEvent_type(consumeInfo.getEvent_type());
				}
				
				
				//支付已经成功
				if(consumeInfo.getStatus() == UserConsumeInfoBean.STATUS_SUCCESS){
					
					SupplierProductBean sp = supplierProductService.findSupplierProductById(consumeInfo.getSupplier_product_id());
					UserWechatBean user = userWechatService.findUserWechatById(consumeInfo.getUser_id());
					
					int price = (int) (sp.getProduct_price() * sp.getDiscount()/100d);
					int userBalance = user.getBalance() + user.getPoints();
					
					//用户的钱+积分够付，才能订购(或者是无需校验金额的订单，打包商品的订单)
					if(userBalance >= price || orderLog.getEvent_type() == OrderProductLogBean.EVENT_TYPE_USER_ORDER_EFF){
						
//						ispBean.setOrder_code(out_trade_code);
						if(orderLog.getEvent_type() != OrderProductLogBean.EVENT_TYPE_USER_ORDER_EFF){
							//扣减用户的余额和积分
							int costBalance = user.getBalance() - (price - orderLog.getCost_points());
							
							user.setBalance( costBalance );
							user.setPoints(user.getPoints() - orderLog.getCost_points());
						}
						
						
						//访问订购商品的具体接口
//						ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(out_trade_code, consumeInfo.getPhone(), sp.getProduct_id());
						ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(sp.getCustomer_id(), out_trade_code, consumeInfo.getPhone(), param, sp.getProduct_id());
					
						
						if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
							//判断是否有附加商品，如果有的话，一起订购
							List<SupplierProductAttachBean> attachs = supplierProductService.querySupplierAttachProductBySpid(sp.getId());
							for(int i = 0;i < attachs.size() ; i++){
								SupplierProductAttachBean attach = attachs.get(i);
								if(attach.getAttach_supplier_product_id() != null){
									SupplierProductBean attachSp = supplierProductService.findSupplierProductById(attach.getAttach_supplier_product_id());
									if(attachSp != null){
//										accessISPOrderInterfaceService.accessISPOrderInterface(out_trade_code+"G"+i, consumeInfo.getPhone(), attachSp.getProduct_id());
										channelProductService.orderProductByChannel(attachSp.getCustomer_id(), out_trade_code+"G"+i, consumeInfo.getPhone(), param, attachSp.getProduct_id());
									}
								}
							}
						}
						
//						orderLog.setCost_points(temp);
//						orderLog.setCost_money(0);
//						orderLog.setCost_balance(costBalance);
//						orderLog.setCost_price(price);
						orderLog.setSupplier_id(sp.getSupplier_id());
						orderLog.setProduct_name(sp.getProduct_name());
						orderLog.setProduct_price(sp.getProduct_price());

						orderLog.setChannel_id(ispBean.getChannel_id());
						if(ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
							switch (ispBean.getChannel_type()) {
							//同步的通道
							case ChannelBean.TYPE_SYNC:
								orderLog.setStatus(OrderProductLogBean.STATUS_SUCCESS);
								orderLog.setRemark("充值成功");
								
								
								handlerOrderProductByUserResult(user, orderLog, sp, orderLog.getStatus(), coupon_id,coupon_record_id);
								
//								下面已不用，封装到上面的方法里了
//								user.setLast_order_time(DateUtil.getNowDateStr());
//								//生成扣减积分记录
//								userCommissionPointsService.createUserPointLog(user.getId(), -orderLog.getCost_points(), "订购商品【"+sp.getProduct_name()+"】支付积分");
//								//订购成功，扣减用户信息
//								userWechatService.update(user);
//								//订购成功，自己和上级添加积分
//								userWechatService.updateUserBalanceByOrderProduct(sp.getProduct_id(), user);
//								//订购成功，给用户发消息
//								sendMessageByOrderSuccess(user, out_trade_code ,orderLog);
								break;
							
							//异步的通道
							case ChannelBean.TYPE_ASYNC:

								orderLog.setStatus(OrderProductLogBean.STATUS_PAYSUCCESS);
								orderLog.setRemark("下单成功，待充值");
								

								//保存并处理用户动作
								msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_ASYNC , null, orderLog);
								
//								//下单成功，给用户发消息
//								sendMessageByOrderASyncSuccess(user, out_trade_code, orderLog);
								break;
							default:
								break;
							}
							
						}else{
							if(!orderLog.getStatus().equals(OrderProductLogBean.STATUS_NEED_AGAIN_ORDER)){
								orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
							}
							orderLog.setReason(ispBean.getResMsg());
							orderLog.setRemark(ispBean.getResMsg()+",由于运营商系统原因，造成本次流量充值失败。我们已将您本次支付的金额自动存入您的现金账户，您可以选择提现或再次消费。");
							boolean MatchingSuccessed = matchingErrorOrder(ispBean.getResMsg(),out_trade_code);
							//如何匹配到错误
							if(MatchingSuccessed){
								//   订购失败，给用户发消息
								handlerOrderProductByUserResult(user, orderLog, sp, orderLog.getStatus(), coupon_id,coupon_record_id);

							}else{
								if(orderLog.getStatus() == OrderProductLogBean.STATUS_NEED_AGAIN_ORDER)
								{
									orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
									handlerOrderProductByUserResult(user, orderLog, sp, orderLog.getStatus(), coupon_id,coupon_record_id);
								}else{
									orderLog.setStatus(OrderProductLogBean.STATUS_NEED_AGAIN_ORDER);
								}
							}
						}
						
						//订购成功，添加积分，这里的已废弃不用了
//						ProductBean product = productService.findProductById(sp.getProduct_id());
//						int givePoints = product.getGive_points();
//						user.setPoints(user.getPoints() + givePoints);
//						userCommissionPointsService.createUserPointLog(user.getId(), givePoints, "订购商品【"+sp.getProduct_name()+"】赠送积分");
						
					}else{
						
						orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
						orderLog.setRemark("充值失败，账户总额不足以支付！");
						
					}
					
				}else{
					orderLog.setStatus(OrderProductLogBean.STATUS_FAILD);
					orderLog.setRemark("原订单支付未完成，充值失败！");
				}
				
				//创建订购日志
				orderProductLogService.createOrUpdateOrderProductLog(orderLog);
			}
			//移除缓存
			runningCacheMap.remove(out_trade_code);
		}
		log.debug("orderProductByUser,runningCacheMap:" + runningCacheMap);
		return orderLog;
		
	}
	
	

	/**
	 * 处理用户订购结果
	 * @param user
	 * @param orderLog
	 * @param sp
	 * @param status
	 */
	private void handlerOrderProductByUserResult(UserWechatBean user, OrderProductLogBean orderLog, SupplierProductBean sp,int status,Integer coupon_id,Integer coupon_record_id){
		
		if(status == OrderProductLogBean.STATUS_SUCCESS)
		{
			user.setLast_order_time(DateUtil.getNowDateStr());
			//扣除用户优惠卷
			supplierCouponService.changeCouponRecodeUserd(user.getId(),coupon_id,coupon_record_id);
			
			//生成扣减积分记录
			userCommissionPointsService.createUserPointLog(user.getId(), -orderLog.getCost_points(), "充值【"+sp.getProduct_name()+"】支付积分");
			//订购成功，扣减用户信息
			userWechatService.update(user);
			//订购成功，自己和上级添加积分
			userWechatService.updateUserBalanceByOrderProduct(sp.getProduct_id(), user);
			
//			//订购成功，给用户发微信消息
			sendMessageByOrderSuccess(user, orderLog.getOrder_code() ,orderLog);
//			//订购成功，给用户发送短信
//			smsService.sendSuccessMsgMyPhoneNumAndContent(orderLog.getOrder_account(), user.getNick_name(), orderLog.getProduct_name(), sp.getCustomer_id());
		}else{
			//订购失败，判断用户有没有领过红包，有领过红包将红包金额剔除
			takeOffBalanceByOrderFailed(orderLog,user);
			

			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_FAILD , null, orderLog);
			
//			//订购失败，给用户发消息
//			sendMessageByOrderFaild(user, orderLog.getOrder_code() ,orderLog);
//			//订购失败，给用户发送短信
//			smsService.sendFaildMsgByOrder(orderLog.getOrder_account(), orderLog.getProduct_name() , orderLog.getRemark(), sp.getCustomer_id());

		}
	}
	
	/**
	 * 调用接口返回结果模糊匹配,匹配链接超时余额不足的情况，
	 */
		private   boolean matchingErrorOrder(String text,String out_trade_code){	
    	ConfigCruxBean beanIn = new ConfigCruxBean();
    	beanIn.setStatus(ConfigCruxBean.STATUS_USE);
    	beanIn.setType(ConfigCruxBean.TYPE_ORDER_ERROR);
    	List<ConfigCruxBean> list = configCruxService.queryConfigCruxInfo(beanIn);
		for(ConfigCruxBean beanOut : list){
	        Pattern pattern = Pattern.compile("("+beanOut.getKey()+")");
	        Matcher matcher = pattern.matcher(text);
	        if(matcher.find()){
	            return true;
	        }
		}
		return false;
		
}
		
		/**
		 * 给定购成功的用户发消息
		 * @param user
		 * @param order_code
		 */
		private void sendMessageByOrderSuccess(UserWechatBean user,String order_code,OrderProductLogBean orderLog){
			
//			//先给自己发
//			String content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_SUCCESS);
//			content = content.replaceAll("#product_name#", orderLog.getProduct_name());
//			content = content.replaceAll("#money#", String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
//			TextBean text = new TextBean();
//			text.setToUserName(user.getOpenid());
//			text.setContent(content);
//			text.setNotifyType(BaseMessage.NOTIFYTYPE_ORDER_SUCCESS);
//			wechatService.sendMessageToUser(text);
			orderLog.setNick_name(user.getNick_name());
			orderLog.setMoney( String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
			
			//保存并处理用户动作
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS , null, orderLog);
			
			
			List<UserWechatBean> listUser = null;
			UserWechatBean condition = new UserWechatBean();
			//再发给父用户
			if(user.getParentid() != null){//查询父用户
				condition.setId(user.getParentid());
				listUser = userWechatService.list(condition);
			}
			if(listUser != null && listUser.size() > 0){
//				content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_SUCCESS_PARENT);
//				text = new TextBean();
//				for(UserWechatBean u : listUser){
//					
//					String str = content.replaceAll("#nick_name#", user.getNick_name());
//					str = str.replaceAll("#product_name#", orderLog.getProduct_name());
//					str = str.replaceAll("#money#", String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
//					text.setToUserName(u.getOpenid());
//					text.setContent(str);
//					text.setNotifyType(BaseMessage.NOTIFYTYPE_PARENT);
//					wechatService.sendMessageToUser(text);
//				}
				
				//保存并处理用户动作
				msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS_NOTIFY_PARENT , null, orderLog);
				
			}
			
			//再给子用户发
			condition.setId(null);
			condition.setParentid(user.getId());
			listUser = userWechatService.list(condition);
			if(listUser != null && listUser.size() > 0){
//				content = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_USER_ORDER_SUCCESS_CHILD);
//				text = new TextBean();
//				for(UserWechatBean u : listUser){
	//
//					String str = content.replaceAll("#nick_name#", user.getNick_name());
//					str = str.replaceAll("#product_name#", orderLog.getProduct_name());
//					str = str.replaceAll("#money#", String.valueOf( (orderLog.getCost_money() + orderLog.getCost_balance())/100d));
//					text.setToUserName(u.getOpenid());
//					text.setContent(str);
//					text.setNotifyType(BaseMessage.NOTIFYTYPE_CHILDREN);
//					wechatService.sendMessageToUser(text);
//				}
				//保存并处理用户动作
				msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_ORDER_SUCCESS_NOTIFY_CHILD , null, orderLog);
				
			}
			
		}
	
		/**
		 * 订购失败，将用户所获得的红包金额从余额里扣除
		 * @param orderLog
		 * @param user
		 */
		private void takeOffBalanceByOrderFailed(OrderProductLogBean orderLog,UserWechatBean user){
			//红包是否发送
			if(!StringUtil.isNull(orderLog.getIs_sended()) && (orderLog.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_SUCCESS_HONBAO || orderLog.getIs_sended().intValue() == OrderProductLogBean.IS_SENDED_SUCCESS_BALANCE)){
				if(!StringUtil.isNull(orderLog.getLucky_money())){
					//将红包的钱扣除
					int money =  user.getBalance() - orderLog.getLucky_money();
					user.setBalance(money);
					userWechatService.update(user);
					
				}
			}
			//更新订单表的是否发送红包(发送失败)
			orderLog.setIs_sended(OrderProductLogBean.IS_SENDED_FAILD);
			orderProductLogService.createOrUpdateOrderProductLog(orderLog);
		}

		

		
	
		
	
		//根据规则表中的sql在数据中在此执行，看返回的count值是多少
		private  int  couponRuleSQLCounValue(String CouponRuleSQl){
			return supplierCouponDao.couponRuleSQLCounValue(CouponRuleSQl);
		}

		
		
		
		
	/**
	 * 判断领取优惠卷的规则是否满足
	 * */
		private boolean judgeReceiveCouponRuleSQL(List<SupplierCouponRuleBean> ruleSqlList,UserWechatBean user){
			boolean whetherReceiveCoupon = true ;
			try{
				for(SupplierCouponRuleBean bean : ruleSqlList){
					bean.setSQL(bean.getSQL().replace("#userid#", String.valueOf(user.getId())));
					bean.setSQL(bean.getSQL().replace("#openid#", String.valueOf(user.getOpenid())));
					int count = couponRuleSQLCounValue(bean.getSQL());						//执行该优惠卷对应规则表中sql判断是否有返回数据
					if(count ==  SupplierCouponRuleBean.RULE_SQL_COUNT_MIN_VALUE ){
						whetherReceiveCoupon = false ;
						break;
					}
				}
				
			}catch(Exception e){
				log.error(e, e);
			}
			
			return whetherReceiveCoupon;
		}
		
		/**
		 * 用户根据优惠卷id使优惠卷的数量自减一,当优惠卷的数量已经变成0的时候,将不会在减少
		 * 没执行一次更新语句都会有返回数据返回更新几条
		 * */
		public int reduceCouponNum(Integer coupon_id ){
			SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
			bean.setId(coupon_id);
			bean.setStatus(SupplierCouponConfigBean.USE_STATUS);
		    return supplierCouponDao.UpdateCouponNumReduceOne(bean);
		}
		
		
		
		/**
		 * 用户领取数量做出判断
		 */
		private boolean UserReveiceCouponLimit(Integer coupon_id,Integer userid){
			//查询该优惠卷每个用户限制领取多少
			SupplierCouponConfigBean couponConfigbeanIn = new  SupplierCouponConfigBean();
			couponConfigbeanIn.setId(coupon_id);
			SupplierCouponConfigBean couponConfigbeanOut =supplierCouponService.findCouponConfigInfo(couponConfigbeanIn);
			int userReceiveCouponCount =  UserReceiveCouponCount(coupon_id,userid);	//用户领取优惠卷总数
			if(userReceiveCouponCount < couponConfigbeanOut.getReceive_limit_num()){//如何过用户领取优惠卷小于限制领取优惠卷数目返回true
				return true;
			}
			return false;
			
		}
		
		
		/**
		 * 查询该用户已经领取几个该优惠卷
		 */
		private int UserReceiveCouponCount(Integer coupon_id, Integer userid){
			SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
			bean.setCoupon_id(coupon_id);
			bean.setUserid(userid);
			bean.setStatus(SupplierCouponRecordBean.USE_STATUS);
			return 	supplierCouponDao.UserReceiveCouponCount(bean);
		}
		
		


		
		
		
		public boolean checkCouponProduct(Integer customer_id,String phone,Integer coupon_id){
			//通过producrt_id到产品表中查询该产品的信息
			AreaData areaData = orderService.getAreaDataByPhone(phone);
			if(areaData == null) return false;
			SupplierProductBean condition = new SupplierProductBean();
			condition.setCustomer_id(customer_id);
			condition.setStatus(SupplierProductBean.STATUS_UP);
			condition.setNow_time(DateUtil.getNowDateStr());
			
			List<CustomerSupplierProductBean> listCsp = new ArrayList<CustomerSupplierProductBean>();
			
			//查询客户下有多少商户
			List<SupplierBean> suppliers = supplierService.querySupplierByCustomerId(customer_id);
			for(SupplierBean sb : suppliers){
				condition.setSupplier_id(sb.getId());
				//如果不可以销售，则必须有库存
				if(sb.getIssale() == SupplierBean.ISSALE_FALSE){
					condition.setStore_num(1);
				}else{
					condition.setStore_num(null);
				}
				
				//有没有区域数据
				if(areaData != null){
					condition.setProduct_brand_name(areaData.getBrand());
					condition.setProduct_province(areaData.getProvince());
				}
				//查询每个商户下的商品
				List<SupplierProductBean> csp = supplierProductDao.querySupplierProduct(condition);
				List<SupplierProductBean> couponProduct = findCouponProduct(coupon_id ,csp);
				if(couponProduct.size() == 0){
					return false;
				}		
			}
			return true ;
		}

		/**
		 * 查询这个用户优惠卷可以使用的总数是多少
		 * */
		@Override
		public int queryUserCouponCount(Integer coupon_record_id,Integer userid,Integer coupon_id) {
			// TODO Auto-generated method stub
			SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
			bean.setId(coupon_record_id);
			bean.setUserid(userid);
			bean.setCoupon_id(coupon_id);
			bean.setStatus(SupplierCouponRecordBean.USE_STATUS);
			return supplierCouponDao.UserReceiveCouponCount(bean);
		}

		@Override
		public void deteleCouponConfig(SupplierCouponConfigBean bean) {
			supplierCouponDao.deteleCouponConfig(bean);
		}

		@Override
		public void deteleCouponRecord(SupplierCouponRecordBean bean) {
			supplierCouponDao.deteleCouponRecord(bean);
		}

		@Override
		public void deteleCouponRule(SupplierCouponRuleBean bean) {
			supplierCouponDao.deteleCouponRule(bean);
		}

		@Override
		public void editCouponConfig(SupplierCouponConfigBean bean) {
			try{
				if(StringUtil.isNull(bean.getId())){
					supplierCouponDao.insertCouponConfig(bean);
				}else{
					supplierCouponDao.updateCouponConfig(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);			
			}
		}

		@Override
		public void editCouponRecord(SupplierCouponRecordBean bean) {
			try{
				if(StringUtil.isNull(bean.getId())){
					supplierCouponDao.insertCouponRecord(bean);
				}else{
					supplierCouponDao.updateCouponRecord(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);			
			}
		}

		@Override
		public void editCouponRule(SupplierCouponRuleBean bean) {
			try{
				if(StringUtil.isNull(bean.getId())){
					supplierCouponDao.insertCouponRule(bean);
				}else{
					supplierCouponDao.updateCouponRule(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);			
			}
		}

	

		
		
	











		

}	
