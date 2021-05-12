/**
 * 
 */
package com.yd.business.other.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.AddressBean;
import com.yd.business.other.dao.IAddressDao;
import com.yd.business.other.service.IAddressService;

/**
 * @author ice
 *
 */
@Service("addressService")
public class AddressServiceImpl extends BaseService implements IAddressService {
	
	@Resource
	private IAddressDao addressDao;
	
	private List<AddressBean> level1 ;
	private List<AddressBean> level2 ;
	private List<AddressBean> level3 ;
	
	@Override
	public List<AddressBean> queryAddress(AddressBean bean){
		return addressDao.queryAddress(bean);
	}
	
	@Override
	public AddressBean findAddressById(int id){
		
		AddressBean bean = new AddressBean();
		bean.setId(id);
		List<AddressBean> list = queryAddress(bean );
		if(list.size() > 0 ) {
			return list.get(0);
		}
		return null;
	}
	
	private void initAddressCache(){
		
		AddressBean condition = new AddressBean();
		condition.setLevel(AddressBean.LEVEL_PROVINCE);
		level1 = queryAddress(condition);
		
		condition.setLevel(AddressBean.LEVEL_CITY);
		level2 = queryAddress(condition);
		
		condition.setLevel(AddressBean.LEVEL_DISTRICT);
		level3 = queryAddress(condition);
	}
	
	
	
}
