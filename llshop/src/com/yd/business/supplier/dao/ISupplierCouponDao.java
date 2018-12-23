package com.yd.business.supplier.dao;

import java.util.List;

import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierCouponRuleBean;



public interface ISupplierCouponDao {
	
	/**
	 * 查询目前配置的优惠卷
	 */
	public List<SupplierCouponConfigBean> queryCouponInfo(SupplierCouponConfigBean bean);
	

	/**
	 * 用户获得优惠卷插入记录
	 */
	public void InsertGetCouponRecord(SupplierCouponRecordBean bean);

	/**
	 * 查询用户自己的折扣卷 
	 */
	public List<SupplierCouponRecordBean> queryMycoupon(SupplierCouponRecordBean bean);


	/**
	 *查询规则表中sql
	 */
	public List<SupplierCouponRuleBean> queryCouponRuleSQLByCouponId(SupplierCouponRuleBean bean);
	
	/**
	 * 把规则表中的sql把一些变量带入在重新计算一次	
	 */
	public List<SupplierProductBean> queryProductByCouponRuleSQL(String couponRuleSql);
	
	/**
	 * 查询单个优惠卷信息
	 */
	public SupplierCouponConfigBean findCouponConfigInfo(SupplierCouponConfigBean bean);
	
	/**
	 * 消费优惠卷后给状态更新
	 * */
	public void changeCouponRecodeUserd(SupplierCouponRecordBean bean);
	
	/**
	 * 根据规则表中的sql在数据中在此执行，看返回的count值是多少
	 * */
	public int couponRuleSQLCounValue(String CouponRuleSQl);
	
	/**
	 * 查询自己优惠卷记录 
	 */
	public List<SupplierCouponRecordBean> queryCouponRecord(SupplierCouponRecordBean bean);

	
	/**
	 * 根据优惠卷记录表中的id,在优惠卷记录表中更新订单
	 */
	public void updateOrderCodeCouponRecordById(SupplierCouponRecordBean bean);
	
	
	/**
	 * 根据优惠卷订单号,更新优惠卷状态为使用状态
	 */
	public void updateStatusyByOrderCode(SupplierCouponRecordBean bean);
	
	
	/**
	 * 从优惠卷记录表中查询优惠信息
	 */
	public SupplierCouponRecordBean findCouponRecord(SupplierCouponRecordBean bean);
	
	
	/**
	 * 根据订单号到优惠卷记录表中查询优惠卷记录
	 */
	public SupplierCouponRecordBean findCouponRecordByOrderCode(SupplierCouponRecordBean bean);
	
	/**
	 * 查询该用户已经领取几个该优惠卷
	 */
	public int findRecordCountByOrderCode(SupplierCouponRecordBean bean);
	
	/**
	 * 查询该用户已经领取几个该优惠卷
	 */
	public int UserReceiveCouponCount(SupplierCouponRecordBean bean);
	
	/**
	 * 没领取一张优惠卷,这个优惠卷数量减一
	 * */
	public int UpdateCouponNumReduceOne(SupplierCouponConfigBean bean);
	
	/**
	 * 从优惠卷规则表中查询一条数据
	 * */
	public SupplierCouponRuleBean  findCouponRule(SupplierCouponRuleBean  bean);
	
	
	

	/**
	 * 分页查询ll_conpon_config表信息
	 */
	public List<SupplierCouponConfigBean> queryCouponConfigPage(SupplierCouponConfigBean bean);
	
	
	/**
	 * 分页查询ll_conpon_record表信息
	 */
	public List<SupplierCouponRecordBean> queryCouponRecordPage(SupplierCouponRecordBean bean);
	
	
	/**
	 * 分页查询ll_conpon_rule表信息
	 */
	public List<SupplierCouponRuleBean> queryCouponRulePage(SupplierCouponRuleBean bean);
	
	/**
	 * 查询ll_conpon_config表总数
	 */
	public int couponConfigCount(SupplierCouponConfigBean bean);
	
	
	/**
	 * 查询ll_conpon_record表总数
	 */
	public int couponRecordCount(SupplierCouponRecordBean bean);
	
	
	/**
	 * 查询ll_conpon_rule表总数
	 */
	public int conponRuleCount(SupplierCouponRuleBean bean);
	
	
	/**
	 * 删除ll_conpon_config表信息
	 */
	public void deteleCouponConfig(SupplierCouponConfigBean bean);
	
	
	/**
	 * 删除ll_conpon_record表信息
	 */
	public void deteleCouponRecord(SupplierCouponRecordBean bean);
	/**
	 * 删除ll_conpon_rule表信息
	 */
	public void deteleCouponRule(SupplierCouponRuleBean bean);
	
	
	
	/**
	 * 插入ll_coupon_config表数据
	 */
	public void insertCouponConfig(SupplierCouponConfigBean bean);
	
	
	/**
	 * 插入ll_coupon_record表数据
	 */
	public void insertCouponRecord(SupplierCouponRecordBean bean);
	
	/**
	 * 后台更新ll_coupon_rule表数据
	 */
	public void insertCouponRule(SupplierCouponRuleBean bean);
	
	/**
	 * 后台更新ll_coupon_config表数据
	 */
	public void updateCouponConfig(SupplierCouponConfigBean bean);
	
	
	/**
	 * 后台更新ll_coupon_record表数据
	 */
	public void updateCouponRecord(SupplierCouponRecordBean bean);
	
	/**
	 * 后台更新ll_coupon_rule表数据
	 */
	public void updateCouponRule(SupplierCouponRuleBean bean);
	
}
