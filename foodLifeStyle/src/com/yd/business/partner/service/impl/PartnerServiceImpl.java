/**
 * 
 */
package com.yd.business.partner.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.partner.bean.PartnerBean;
import com.yd.business.partner.dao.IPartnerDao;
import com.yd.business.partner.service.IPartnerService;

/**
 * @author ice
 *
 */
@Service("partnerService")
public class PartnerServiceImpl extends BaseService implements IPartnerService {
	
	@Resource
	private IPartnerDao partnerDao;
	
	@Override
	public PartnerBean getPartnerByCode(String partner_code){
		return partnerDao.getPartnerByCode(partner_code);
	}

	@Override
	public void insertPartner(PartnerBean bean) {
		// TODO Auto-generated method stub
		partnerDao.insertPartner(bean);
	}
	
	@Override
	public PartnerBean findPartnerById(int id){
		return partnerDao.findPartnerById(id);
	}
	
	@Override
	public List<PartnerBean> queryPartner(PartnerBean bean){
		return partnerDao.queryPartner(bean);
	}
	
	@Override
	public void updatePartnerStatusByCustomerId(int customer_id,int status){
		
		PartnerBean bean = new PartnerBean();
		bean.setCustomer_id(customer_id);
		//查找客户ID下的所有伙伴列表，一般情况是一对一
		List<PartnerBean> list = queryPartner(bean );
		
		//循环修改状态
		for(PartnerBean p : list){
			updatePartnerStatusByCustomerId(customer_id, status);
		}
		
	}
	
	
}
