/**
 * 
 */
package com.yd.business.user.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.user.bean.UserCartBean;
import com.yd.business.user.bean.UserCartBean.CartInfo;
import com.yd.business.user.service.IUserCartService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("userCartService")
public class UserCartServiceImpl extends BaseService implements IUserCartService {
	
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IProductService productService;
	@Resource
	private IUserWechatService userWechatService;
	
	/**
	 * 从cookie的值中查询商品列表
	 * 入参格式为：{"productInfos":[{"spid":29,"num":1},{"spid":30,"num":2}]}
	 * @param productJson
	 * @return
	 */
	@Override
	public UserCartBean queryUserCartListByCookieJson(String productJson){

		UserCartBean bean = new UserCartBean();
		bean.setCartInfoList(new ArrayList<UserCartBean.CartInfo>());
		try{
			if(StringUtil.isNull(productJson)){
				return bean;
			}
			
			//入参是界面编码过的，要解码
			productJson = URLDecoder.decode(productJson, "utf-8");
			
			JSONObject jso = new JSONObject(productJson);
			
			JSONArray array = jso.getJSONArray("productInfos");
			List<Integer> ids = new ArrayList<Integer>();
			
			for(int i = 0 ; i < array.length(); i++){
				
				JSONObject prodJson = array.getJSONObject(i);
				ids.add(prodJson.getInt("spid"));
			}
			
			if(ids.size() > 0) {
			
				List<SupplierProductBean> spList = supplierProductService.querySupplierProductByIds(ids);
				//查出数据后，要对比是否有大于库存的，并封装成对象
				for(SupplierProductBean sp : spList){
					
					for(int i = 0 ; i < array.length(); i++){
						
						JSONObject prodJson = array.getJSONObject(i);
						int id = prodJson.getInt("spid");
						int num = prodJson.getInt("num");
						if(sp.getId() == id && sp.getStore_num() > 0 ){
							
							if(sp.getStore_num() < num){
								num = sp.getStore_num();
							}
							
							UserCartBean.CartInfo ci = bean.new CartInfo();
							ci.setNum(num);
							ci.setSupplierProduct(sp);
							ci.setTotal_price( (int)(num * sp.getProduct_price() * sp.getDiscount() /100d));
							bean.getCartInfoList().add(ci);
						}
					}
				}
			}
		}catch(Exception e) {
			log.error(e, e);
		}
		
		return bean;
	}
	

	
}
