/**
 * 
 */
package com.yd.business.order.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.dao.IShopOrderDao;
import com.yd.business.order.service.IOrderService;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.user.bean.UserAddressBean;
import com.yd.business.user.bean.UserCartBean;
import com.yd.business.user.bean.UserCartBean.CartInfo;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserAddressService;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.service.impl.UserConsumeInfoServiceImpl;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("shopOrderService")
public class ShopOrderServiceImpl extends BaseService implements IShopOrderService {
	
	@Resource
	private IShopOrderDao shopOrderDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IOrderService orderService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IUserAddressService userAddressService;
	
	
	@Override
	public void createShopOrderInfo(ShopOrderInfoBean bean){
		shopOrderDao.createShopOrderInfo(bean);
	}

	@Override
	public void updateShopOrderInfo(ShopOrderInfoBean bean){
		shopOrderDao.updateShopOrderInfo(bean);
	}

	@Override
	public List<ShopOrderInfoBean> queryShopOrderInfo(ShopOrderInfoBean bean){
		return shopOrderDao.queryShopOrderInfo(bean);
	}
	
	@Override
	public void updateShopOrderExpressInfo(int order_id,String mode,String code,Integer price){
		
		ShopOrderInfoBean order = findShopOrderInfoById(order_id);

		if(price != null){
			order.setExpress_price(price);
		}
		if(StringUtil.isNotNull(mode)){
			order.setExpress_mode(mode);
		}
		if(StringUtil.isNotNull(code)){
			order.setExpress_order_code(code);
			order.setExpress_date(DateUtil.getNowDateStr());
			order.setStatus(ShopOrderInfoBean.STATUS_ALREADY_DELIVERY);
		}
		
		updateShopOrderInfo(order);
		
	}

	@Override
	public ShopOrderInfoBean findShopOrderInfoByCode(String order_code) {
		ShopOrderInfoBean bean = new ShopOrderInfoBean();
		bean.setOrder_code(order_code);
		List<ShopOrderInfoBean> list = queryShopOrderInfo(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	

	@Override
	public ShopOrderInfoBean findShopOrderInfoById(int id) {
		ShopOrderInfoBean bean = new ShopOrderInfoBean();
		bean.setId(id);
		List<ShopOrderInfoBean> list = queryShopOrderInfo(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	

	@Override
	public void createShopOrderProduct(ShopOrderProductBean bean){
		shopOrderDao.createShopOrderProduct(bean);
	}
	
	@Override
	public List<ShopOrderProductBean> queryShopOrderProduct(ShopOrderProductBean bean){
		return shopOrderDao.queryShopOrderProduct(bean);
	}
	
	

	/**
	 * 从cookie的值中查询商品列表
	 * 入参格式为：{"productInfos":[{"spid":29,"num":1},{"spid":30,"num":2}]}
	 * @param productJson
	 * @return
	 */
	@Override
	public ShopOrderInfoBean createOrderLogByUserCartList(String openid,String productJson){

		ShopOrderInfoBean order = new ShopOrderInfoBean();
		List<ShopOrderProductBean> productList = new ArrayList<ShopOrderProductBean>();
		order.setProductList(productList);
		order.setCost_points(0);
		order.setCost_price(0);
		try{
			if(StringUtil.isNull(productJson)){
				return order;
			}
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				return order;
			}
			
			//入参是界面编码过的，要解码
			productJson = URLDecoder.decode(productJson, "utf-8");
			
			JSONObject jso = new JSONObject(productJson);
			long time = jso.getLong("time");
			Date date = new Date(time);
			String order_code = userConsumeInfoService.createOutTradeNo(UserConsumeInfoServiceImpl.OUTTRADE_TYPE_SHOP, user.getId().toString(),date);

			order.setOrder_code(order_code);
			List<ShopOrderInfoBean> list = queryShopOrderInfo(order);
			if(list.size() > 0){
				order = list.get(0);
				
				ShopOrderProductBean condition = new ShopOrderProductBean();
				condition.setOrder_code(order_code);
				productList = queryShopOrderProduct(condition );
				order.setProductList(productList);
				
			}else{
				order.setUser_id(user.getId());
				order.setNick_name(user.getNick_name());
				order.setCreate_time(DateUtil.getNowDateStr());
				order.setStatus(ShopOrderInfoBean.STATUS_WAIT);
				order.setEvent_type(ShopOrderInfoBean.EVENT_TYPE_USER_ORDER_SHOP);
				order.setOrder_code(order_code);
				//创建定单
				createShopOrderInfo(order);
			
				JSONArray array = jso.getJSONArray("productInfos");
				List<Integer> ids = new ArrayList<Integer>();
				
				for(int i = 0 ; i < array.length(); i++){
					
					JSONObject prodJson = array.getJSONObject(i);
					ids.add(prodJson.getInt("spid"));
				}
				
				if(ids.size() > 0) {
					String order_name = "";
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
								//技算折扣和积分抵扣等
								int sp_total_price = (int)(num * sp.getProduct_price() * sp.getDiscount() /100d);
								int offset_points = sp.getProduct_offset_points() * num;
								//抵扣积分 
								int userPoints = user.getPoints();
								if(offset_points > userPoints){
									offset_points = userPoints;
								}
								
								sp_total_price = sp_total_price - offset_points;
								if(order_name.length() == 0){
									order_name = sp.getProduct_name();
									order.setOrder_img(sp.getHead_img());
									if(spList.size() > 1){
										order_name += "等"+spList.size()+"件商品";
									}
								}
								//生成订单商品信息
								ShopOrderProductBean spb = new ShopOrderProductBean();
								spb.setHead_img(sp.getHead_img());
								spb.setOrder_info_id(order.getId());
								spb.setOrder_code(order_code);
								spb.setUser_id(user.getId());
								spb.setNick_name(user.getNick_name());
								spb.setSupplier_id(sp.getSupplier_id());
								spb.setSupplier_name(sp.getSupplier_name());
								spb.setSupplier_product_id(sp.getId());
								spb.setSupplier_product_name(sp.getProduct_name());
								spb.setOriginal_price(sp.getProduct_price());
								spb.setDiscount(sp.getDiscount());
								spb.setNum(num);
								spb.setCost_points(offset_points);
								spb.setReal_price(sp_total_price);
								spb.setCreate_time(DateUtil.getNowDateStrSSS());
								
								createShopOrderProduct(spb);
								productList.add(spb);
								
								order.setCost_price(order.getCost_price() + sp_total_price);
								order.setCost_points(order.getCost_points() + offset_points);
								order.setOrder_name(order_name);
								updateShopOrderInfo(order);
	//							int give_points = sp.getGive_points() * num;   // 用户还没有付款呢
	//							if(offset_points >0){
	//								user.setPoints(user.getPoints() - offset_points);
	//								userCommissionPointsService.createUserPointLog(user.getId(), -offset_points, "购买商品"+ sp.getProduct_name()+" *"+num+" 扣减积分" );
	//							}
								
							}
						}
					}
				}
			}
		}catch(Exception e) {
			log.error(e, e);
		}
		
		return order;
	}
	
	
	@Override
	public void updateShopOrderStatus(String order_code,int status) {
		ShopOrderInfoBean order = new ShopOrderInfoBean();
		order.setOrder_code(order_code);
		order.setStatus(status);
		updateShopOrderInfo(order);
	}
	@Override
	public void delteShopOrderById(int id){
		shopOrderDao.delteShopOrderById(id);
	}
	
	@Override
	public void updateShopOrderStatusToDelete(String order_code){
		updateShopOrderStatus(order_code, ShopOrderInfoBean.STATUS_USER_DELETE);
	}
	
	
	@Override
	public void setupOrderAddress(String order_code,int userAddrId){
		
		ShopOrderInfoBean order = findShopOrderInfoByCode(order_code);
		UserAddressBean userAddr = userAddressService.findUserAddressById(userAddrId);
		
		order.setContact_address(userAddr.getContact_address());
		order.setContact_name(userAddr.getContact_name());
		order.setContact_phone(userAddr.getContact_phone());
		
		updateShopOrderInfo(order);
		
	}
	
	
	
}
