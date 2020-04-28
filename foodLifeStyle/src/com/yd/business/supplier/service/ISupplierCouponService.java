package com.yd.business.supplier.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierCouponRuleBean;
import com.yd.business.user.bean.UserWechatBean;


public interface ISupplierCouponService {
	
	/**
	 * 领取优惠卷的时候查询可以展示的优惠卷信息
	 */
	public List<SupplierCouponConfigBean> querySureShowCoupon(UserWechatBean user);

	
	/**
	 * 根据优惠卷规则表中该优惠卷规则领取优惠卷,返回结果
	 */
	public String reveiveCouponResult(Integer coupon_id,UserWechatBean user);
	
	
	/**
	 * 根据优惠卷id到配置表中查询优惠卷基本信息,和到规则表中查询该优惠卷的规则
	 */
	public SupplierCouponConfigBean  findCouponInfoAndRuleNameByCouponid(Integer coupon_id); 


	/**
	 * 查询用户目前自己已经拥有的优惠卷
	 */	
	public List<SupplierCouponRecordBean> queryUserAllCoupon(Integer userid,Integer supplier_id);
	
	
	/**
	 * 根据优惠卷记录表中的id,在优惠卷记录表中更新订单
	 * 	 */
	public void updateOrderCodeCouponRecordById(Integer coupon_record_id,String out_no);
	
	
	/**
	 * 根据订单号改变优惠卷状态为使用状态
	 */
	public void updateCouponRecordStatusBecomeUsedByOrderCode(String order_code , String product_name);
	
	/**
	 * 查询目前配置可用的优惠卷
	 */
	public List<SupplierCouponConfigBean> queryAllEnableCouponInfo();
	/**
	 * 查询目前配置的优惠卷
	 */
	public List<SupplierCouponConfigBean> queryAllCouponInfo();

	
	/**
	 *用户获得优惠卷插入记录
	 */
	public void InsertGetCouponRecord(SupplierCouponRecordBean bean);
	
	
	/**
	 *	从优惠卷记录表中查询优惠信息
	 */
	public SupplierCouponRecordBean findCouponRecord(SupplierCouponRecordBean bean);
	
	
	
	/**
	 *	根据订单号到优惠卷记录表中查询优惠卷记录
	 */
	public SupplierCouponRecordBean findCouponRecordByOrderCode(String bean);
	
	
	/**
	 *查询自己拥有的优惠卷
	 */
	public List<SupplierCouponRecordBean> queryUserCanUseCoupon(Integer userid);
	
	
	
	/**
	 * 根据优惠卷规则表中该优惠卷规则使用优惠卷,返回结果
	 */
	public String useCouponResult(Integer coupon_id, Integer userid, String orderCode);
	
	
	
	/**
	 * 分页查询
	 */
	public PageinationData queryAdminConponConfigPage(SupplierCouponConfigBean bean);
	
	
	/**
	 * 分页查询
	 */
	public PageinationData queryAdminConponRecordPage(SupplierCouponRecordBean bean);
	
	
	/**
	 * 分页查询
	 */
	public PageinationData queryAdminConponRulePage(SupplierCouponRuleBean bean);
	
	
	/**
	 * 删除coupon_config表信息
	 */
	public void deteleCouponConfig(SupplierCouponConfigBean bean);
	
	
	/**
	 * 删除coupon_record表信息
	 */
	public void deteleCouponRecord(SupplierCouponRecordBean bean);
	
	
	/**
	 * 删除coupon_rule表信息
	 */
	public void deteleCouponRule(SupplierCouponRuleBean bean);
	
	
	
	/**
	 * 更改coupon_config表信息
	 */
	public void editCouponConfig(SupplierCouponConfigBean bean);
	
	
	/**
	 * 更改coupon_record表信息
	 */
	public void editCouponRecord(SupplierCouponRecordBean bean);
	
	
	/**
	 * 更改coupon_rule表信息
	 */
	public void editCouponRule(SupplierCouponRuleBean bean);
	
	
	
	
	
	/**
	 * 删除优惠卷目前展示的商品
	 */
	public String deleteShowProduct(SupplierCouponConfigBean bean);
	
	
	
	
	/**
	 * 增加优惠卷目前展示的商品
	 */
	public String addShowProduct(SupplierCouponConfigBean bean);
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询流量商城产品信息的
	 * */
	List<CustomerSupplierProductBean> queryCustomerProductByPhone(Integer customer_id, String phone,Integer coupon_id);
	
	/**
	 * 消费优惠卷时用户用的是哪个优惠卷
	 */
	public SupplierCouponConfigBean findCouponConfigInfo(SupplierCouponConfigBean bean);  
//
//	/**
//	 * 根据优惠卷id查询规则表信息
//	 * */
//	public SupplierCouponRuleBean queryCouponRuleById(Integer coupon_id);
	/**
	 *用户使用优惠卷，更新优惠卷记录表中拥有数据
	 */
	public void changeCouponRecodeUserd(Integer userid ,Integer coupon_id,Integer coupon_record_id);

	/**
	 * 检查该优惠卷是否支持这个商品,支持返回的为true,不支持返回的是false
	 */
	public boolean checkCouponProduct(Integer customer_id,String phone,Integer coupon_id);
	
	
	/**
	 * 查询这个用户优惠卷可以使用的总数是多少
	 * */
	public int  queryUserCouponCount(Integer coupon_record_id,Integer userid,Integer coupon_id);
	
	
	/**
	 * 根据优惠卷id查询单个优惠卷配置表中信息
	 * */
	public SupplierCouponConfigBean  findCouponInfoByCouponid(Integer coupon_id);

	/**
	 * 查询用户当前订单可用的优惠卷列表
	 * @param userid
	 * @param orderCode
	 * @return
	 */
	public List<SupplierCouponRecordBean> queryUserCanUseCouponByOrderCode(Integer userid, String orderCode);

	/**
	 * 通过订单号查询优惠卷记录
	 * @param orderCode
	 * @return
	 */
	List<SupplierCouponRecordBean> queryCouponRecordByOrderCode(String orderCode, int userid);




	



}
