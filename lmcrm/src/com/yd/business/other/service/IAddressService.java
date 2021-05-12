/**
 * 
 */
package com.yd.business.other.service;

import java.util.List;

import com.yd.business.other.bean.AddressBean;

/**
 * @author ice
 *
 */
public interface IAddressService {

	List<AddressBean> queryAddress(AddressBean bean);

	AddressBean findAddressById(int id);

}
