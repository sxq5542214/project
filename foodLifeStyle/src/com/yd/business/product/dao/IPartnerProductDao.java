/**
 * 
 */
package com.yd.business.product.dao;

import java.util.List;

import com.yd.business.product.bean.PartnerProductBean;

/**
 * @author ice
 *
 */
public interface IPartnerProductDao {

	PartnerProductBean findPartnerProduct(PartnerProductBean bean);

	List<PartnerProductBean> queryPartnerProduct(PartnerProductBean bean);

	public void insertPartnerProduct(PartnerProductBean bean);
}
