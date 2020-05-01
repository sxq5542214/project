/**
 * 
 */
package com.yd.business.user.service;

import java.util.List;

import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.user.bean.UserCartBean;

/**
 * @author ice
 *
 */
public interface IUserCartService {

	UserCartBean queryUserCartListByCookieJson(String productJson);

}
