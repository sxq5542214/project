package com.yd.business.area.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.bean.AreaExtBean;
import com.yd.business.area.dao.IAreaDao;
/**
 * 
 * @author ice
 *
 */
@Repository("areaDao")
public class AreaDaoImpl extends BaseDao implements IAreaDao {
	 
	private final static String NAMESPACE = "area.";

	@Override
	public List<AreaExtBean> queryAreaList(AreaBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAreaList", bean);
	}

	@Override
	public int insertArea(AreaBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(NAMESPACE+"insertArea", bean);
	}

	@Override
	public int updateArea(AreaBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(NAMESPACE+"updateArea", bean);
	}

	@Override
	public int deleteArea(long id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(NAMESPACE+"deleteArea", id);
	}
	
	@Override
	public List<AddressBean> queryAddressList(AddressBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryAddressList", bean);
	}

	@Override
	public int insertAddress(AddressBean bean) {
		return sqlSessionTemplate.insert(NAMESPACE+"insertAddress", bean);
	}
	@Override
	public int updateAddress(AddressBean bean) {
		return sqlSessionTemplate.insert(NAMESPACE+"updateAddress", bean);
	}
	@Override
	public AddressBean findAddressById(int id) {
		AddressBean bean = new AddressBean();
		bean.setId(id);
		List<AddressBean> list = queryAddressList(bean );
		return list.size() == 0 ? null : list.get(0);
	}
	
	@Override
	public int deleteAddress(int id , int companyId) {
		AddressBean bean = new AddressBean();
		bean.setId(id);
		bean.setCompany_id(companyId);

		return sqlSessionTemplate.delete(NAMESPACE+"deleteAddress", bean);
	}
	
}
