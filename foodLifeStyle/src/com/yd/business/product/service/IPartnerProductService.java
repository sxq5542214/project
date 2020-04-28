/**
 * 
 */
package com.yd.business.product.service;

import java.util.List;

import com.yd.business.product.bean.PartnerProductBean;

/**
 * @author ice
 *
 */
public interface IPartnerProductService {

	PartnerProductBean findPartnerProduct(PartnerProductBean bean);

	List<PartnerProductBean> queryPartnerProduct(PartnerProductBean bean);

	PartnerProductBean findPartnerProduct(Integer partnerId, String productCode);

	public void insertPartnerProduct(PartnerProductBean bean);

	PartnerProductBean findPartnerProduct(Integer partnerId, String productCode, Integer status);
}
