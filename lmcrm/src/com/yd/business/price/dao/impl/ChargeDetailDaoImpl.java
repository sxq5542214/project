/**
 * 
 */
package com.yd.business.price.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.dao.IChargeDetailDao;

/**
 * @author ice
 *
 */
@Repository("chargeDetailDao")
public class ChargeDetailDaoImpl extends BaseDao implements IChargeDetailDao {
	public static final String NAMESPACE = "chargeDetail." ;

	@Override
	public List<ChargeDetailBean> queryChargeDetailList(ChargeDetailBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryChargeDetailList", bean);
	}
	
	@Override
	public int insertChargeDetail(ChargeDetailBean bean) {
		return sqlSessionTemplate.insert(NAMESPACE + "insertChargeDetail", bean);
	}
	@Override
	public int updateChargeDetail(ChargeDetailBean bean) {
		return sqlSessionTemplate.update(NAMESPACE + "updateChargeDetail", bean);
	}

	@Override
	public List<ChargeDetailBean> queryChargeListByUserId(Long u_id) {
		return sqlSessionTemplate.selectList(NAMESPACE +"queryChargeListByUserId", u_id);
	}
	
}
