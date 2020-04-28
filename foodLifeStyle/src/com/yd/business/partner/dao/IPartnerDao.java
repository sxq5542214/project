/**
 * 
 */
package com.yd.business.partner.dao;

import java.util.List;

import com.yd.business.partner.bean.PartnerBean;

/**
 * @author ice
 *
 */
public interface IPartnerDao {

	PartnerBean getPartnerByCode(String partner_code);
	public void insertPartner(PartnerBean bean);
	int getPartnerCallOfDayCount(int partnerId, String dayStr);
	PartnerBean findPartnerById(int id);
	List<PartnerBean> queryPartner(PartnerBean bean);
	void updatePartner(PartnerBean bean);
}
