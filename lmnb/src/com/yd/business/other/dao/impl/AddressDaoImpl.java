/**
 * 
 */
package com.yd.business.other.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.other.bean.AddressBean;
import com.yd.business.other.dao.IAddressDao;

/**
 * @author ice
 *
 */
@Repository("addressDao")
public class AddressDaoImpl extends BaseDao implements IAddressDao {
	public static final String NAMESPACE = "address." ;
	
	@Override
	public List<AddressBean> queryAddress(AddressBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryAddress", bean);
	}
	
	
}
