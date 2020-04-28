/**
 * 
 */
package com.yd.business.partner.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.partner.bean.PartnerBean;
import com.yd.business.partner.dao.IPartnerDao;

/**
 * @author ice
 *
 */
@Repository("partnerDao")
public class PartnerDaoImpl extends BaseDao implements IPartnerDao {
	private static final String NAMESPACE = "partner.";
	
	@Override
	public PartnerBean getPartnerByCode(String partner_code){
		
		return sqlSessionTemplate.selectOne(NAMESPACE+"getPartnerByCode", partner_code);
		
	}

	@Override
	public void insertPartner(PartnerBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertPartner", bean);
	}
	
	@Override
	public int getPartnerCallOfDayCount(int partner_id,String dayStr){
		
		Map<String, Object > map = new HashMap<String, Object>();
		map.put("partner_id", partner_id);
		map.put("dayStr", dayStr);
		
		Integer count = sqlSessionTemplate.selectOne(NAMESPACE+"getPartnerCallOfDayCount", map);
		return count == null ? 0 : count;
	}
	
	@Override
	public PartnerBean findPartnerById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findPartnerById", id);
	}
	
	@Override
	public List<PartnerBean> queryPartner(PartnerBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryPartner", bean);
	}
	
	@Override
	public void updatePartner(PartnerBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updatePartner", bean);
	}
	
}
