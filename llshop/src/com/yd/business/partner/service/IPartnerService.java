/**
 * 
 */
package com.yd.business.partner.service;

import java.util.List;

import com.yd.business.partner.bean.PartnerBean;

/**
 * @author ice
 *
 */
public interface IPartnerService {

	PartnerBean getPartnerByCode(String partner_code);
	public void insertPartner(PartnerBean bean);
	PartnerBean findPartnerById(int id);
	List<PartnerBean> queryPartner(PartnerBean bean);
	void updatePartnerStatusByCustomerId(int customer_id, int status);
}
