package com.yd.business.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierCouponRuleBean;
import com.yd.business.supplier.dao.ISupplierCouponDao;

@Repository("supplierCouponDao")
public class SupplierCouponDaoImpl  extends BaseDao implements ISupplierCouponDao{

	private static final String NAMESPACE = "supplierCoupon.";

	/**
	 * 查询目前配置的优惠卷
	 */
	@Override
	public List<SupplierCouponConfigBean> queryCouponInfo(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCouponInfo", bean);
	}


	@Override
	public void InsertGetCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"InsertGetCouponRecord", bean);		
	}

	@Override
	public List<SupplierCouponRecordBean> queryMycoupon(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryMycoupon", bean);

	}

	@Override
	public List<SupplierCouponRuleBean> queryCouponRuleSQLByCouponId(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCouponRuleSQLByCouponId", bean);
	}
	
	@Override
	public SupplierCouponConfigBean findCouponConfigInfo(SupplierCouponConfigBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryCouponInfo",bean);
	}

	@Override
	public List<SupplierCouponRecordBean> queryCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCouponRecord", bean);

	}
	
	
	@Override
	public void changeCouponRecodeUserd(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"changeCouponRecodeUserd", bean);
	}

	/**
	 * 根据规则表中的sql在数据中在此执行，看返回的count值是多少
	 * */
	@Override
	public int couponRuleSQLCounValue(String CouponRuleSQl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"couponRuleSQLCounValue", CouponRuleSQl);
	}

	
	/**
	 * 根据优惠卷记录表中的id,在优惠卷记录表中更新订单
	 */	
	@Override
	public void updateOrderCodeCouponRecordById(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateOrderCodeCouponRecordById", bean);
	}
	
	
	/**
	 * 从优惠卷记录表中查询优惠信息
	 */	
	@Override
	public SupplierCouponRecordBean findCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"findCouponRecord", bean);
	}
	
	
	/**
	 * 根据订单号到优惠卷记录表中查询优惠卷记录
	 */	
	@Override
	public SupplierCouponRecordBean findCouponRecordByOrderCode(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"findCouponRecordByOrderCode", bean);
	}
	
	
	
	/**
	 * 根据优惠卷订单号,更新优惠卷状态为使用状态
	 */	
	@Override
	public void updateStatusyByOrderCode(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateStatusyByOrderCode", bean);
	}
	
	/**
	 * 查询该用户已经领取几个该优惠卷
	 */
	@Override
	public int findRecordCountByOrderCode(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"findRecordCountByOrderCode", bean);
	}
	
	/**
	 * 查询该用户已经领取几个该优惠卷
	 */
	@Override
	public int UserReceiveCouponCount(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"UserReceiveCouponCount", bean);
	}

	/**
	 * 没领取一张优惠卷,这个优惠卷数量减一
	 * */
	@Override
	public int UpdateCouponNumReduceOne(SupplierCouponConfigBean bean) {
		return sqlSessionTemplate.update(NAMESPACE+"UpdateCouponNumReduceOne", bean); 
	}


	@Override
	public SupplierCouponRuleBean findCouponRule(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"findCouponRule", bean);
	}


	@Override
	public List<SupplierCouponConfigBean> queryCouponConfigPage(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCouponConfigPage", bean,rowBound(bean));
	}


	@Override
	public List<SupplierCouponRecordBean> queryCouponRecordPage(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCouponRecordPage", bean,rowBound(bean));
	}


	@Override
	public List<SupplierCouponRuleBean> queryCouponRulePage(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryCouponRulePage", bean,rowBound(bean));
	}


	@Override
	public int couponConfigCount(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"couponConfigCount",bean);
	}


	@Override
	public int couponRecordCount(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"couponRecordCount",bean);
	}


	@Override
	public int conponRuleCount(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"conponRuleCount",bean);
	}


	@Override
	public void deteleCouponConfig(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deteleCouponConfig", bean);
	}


	@Override
	public void deteleCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deteleCouponRecord", bean);
	}


	@Override
	public void deteleCouponRule(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deteleCouponRule", bean);
	}


	@Override
	public void insertCouponConfig(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCouponConfig", bean);
	}


	@Override
	public void insertCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCouponRecord", bean);

	}


	@Override
	public void insertCouponRule(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertCouponRule", bean);
	}


	@Override
	public void updateCouponConfig(SupplierCouponConfigBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCouponConfig", bean);

	}


	@Override
	public void updateCouponRecord(SupplierCouponRecordBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCouponRecord", bean);
	}
	@Override
	public void updateCouponRecordStatusExpiredBySysdate(int userid) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCouponRecordStatusExpiredBySysdate", userid);
	}


	@Override
	public void updateCouponRule(SupplierCouponRuleBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateCouponRule", bean);
	}







}
