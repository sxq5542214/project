/**
 * 
 */
package com.yd.business.isp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.isp.bean.DXInterfaceBean;
import com.yd.business.isp.bean.DaHanSanTongInterfaceBean;
import com.yd.business.isp.bean.ISPCallbackLogBean;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.isp.bean.InterfaceBean;
import com.yd.business.isp.bean.JieTuoInterfaceBean;
import com.yd.business.isp.bean.NanJingShengShiInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean;
import com.yd.business.isp.bean.ZhuoweiInterfaceBean;
import com.yd.business.isp.bean.WanLiuInterfaceBean.Tx_info;
import com.yd.business.isp.bean.YDInterfaceBean;
import com.yd.business.isp.bean.YunZhangTongInterfaceBean;
import com.yd.business.isp.client.OrderDXProductClient;
import com.yd.business.isp.client.OrderProductInstanceClient1;
import com.yd.business.isp.client.OrderProductInstanceClient2;
import com.yd.business.isp.client.OrderProductInstanceClient3;
import com.yd.business.isp.client.OrderProductInstanceClient4;
import com.yd.business.isp.client.OrderProductInstanceClient5;
import com.yd.business.isp.client.OrderProductInstanceClient6;
import com.yd.business.isp.client.OrderProductInstanceClient7;
import com.yd.business.isp.client.OrderProductInstanceClient8;
import com.yd.business.isp.client.OrderProductInstanceClient9;
import com.yd.business.isp.client.OrderYDProductClient;
import com.yd.business.order.bean.AreaData;
import com.yd.business.isp.dao.IISPInterfaceLogDao;
import com.yd.business.order.exception.ISPException;
import com.yd.business.isp.service.IAccessISPOrderInterfaceService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.partner.bean.PartnerInterfaceBean;
import com.yd.business.partner.runable.PartnerCallbackRunable;
import com.yd.business.partner.service.IPartnerInterfaceService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("accessISPOrderInterfaceService")
public class AccessISPOrderInterfaceServiceImpl extends BaseService implements IAccessISPOrderInterfaceService {
	
	@Resource
	private IOrderService orderService;
	@Resource
	private IProductService productService;
	@Resource
	private IISPInterfaceLogDao ispInterfaceLogDao;
	@Resource
	private IPartnerInterfaceService partnerInterfaceService;
	@Resource
	private IChannelProductService channelProductService;
	@Resource
	private IChannelService channelService;

	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private OrderYDProductClient orderYDProductClient;
	@Resource
	private OrderDXProductClient orderDXProductClient;
	@Resource
	private OrderProductInstanceClient1 orderProductInstanceClient1;
	@Resource
	private OrderProductInstanceClient2 orderProductInstanceClient2;
	@Resource
	private OrderProductInstanceClient3 orderProductInstanceClient3;
	@Resource
	private OrderProductInstanceClient4 orderProductInstanceClient4;
	@Resource
	private OrderProductInstanceClient5 orderProductInstanceClient5;
	@Resource
	private OrderProductInstanceClient6 orderProductInstanceClient6;
	@Resource
	private OrderProductInstanceClient7 orderProductInstanceClient7;
	@Resource
	private OrderProductInstanceClient8 orderProductInstanceClient8;
	@Resource
	private OrderProductInstanceClient9 orderProductInstanceClient9;
	
	
	/**
	 * 访问订购的具体接口，已废弃
	 */
	@Deprecated
	@Override
	public ISPInterfaceBean accessISPOrderInterface(String order_code,String phone, Integer productId) {
		
		AreaData ad = orderService.getAreaDataByPhone(phone);

		ISPInterfaceBean bean = null;
		if(ad == null){
			bean = new ISPInterfaceBean();
			bean.setStatus(ISPInterfaceBean.STATUS_FAILD);
			bean.setResCode(ISPInterfaceBean.RESCODE_PHONE_UNSUPPORT);
			bean.setResMsg("不支持的手机号码！");
		}else{
			
			ProductBean product = productService.findProductById(productId);
			if(AreaData.BRAND_YD.equals(ad.getBrand())){
				
				bean = accessYDOrderInterface(order_code,phone, product);
//				bean = new YDInterfaceBean();
//				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
//				bean.setOrder_code(order_code);
				bean.setType(ISPInterfaceBean.TYPE_AHYD_SZ);
			}else if(AreaData.BRAND_DX.equals(ad.getBrand())){
				
				bean = accessDXOrderInterface(order_code,phone, product);
//				bean = new DXInterfaceBean();
//				bean.setStatus(ISPInterfaceBean.STATUS_SUCCESS);
//				bean.setOrder_code(order_code);
				bean.setType(ISPInterfaceBean.TYPE_AHDX_HF);
			}else if(AreaData.BRAND_LT.equals(ad.getBrand())){
	
				bean = accessLTOrderInterface(phone, product.getCode());
//				bean.setType(ISPInterfaceBean.TYPE_AHLT);
				
			}
		}
		bean.setOrder_code(order_code);
		//保存日志
		createInterfaceLog(bean);
		return bean;
	}
	
	private ISPInterfaceBean findSuccessInterface(String order_code,String table_name){
		//检查订单是否存在成功过,如果有成功记录，直接返回之前成功的
		List<ISPInterfaceBean> list = ispInterfaceLogDao.queryInterfaceLog(order_code, ISPInterfaceBean.STATUS_SUCCESS,table_name);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 访问运营商或第三方订购接口
	 */
	@Override
	public ISPInterfaceBean accessISPOrderInterface(int channel_id,String order_code,String phone, Integer productId,String server_name) {
		
		
		ProductBean product = productService.findProductById(productId);
		ChannelBean channel = channelService.findChannelById(channel_id);
		ISPInterfaceBean result = findSuccessInterface(order_code, channel.getTable_name());
		if(result != null){
			return result;
		}
		
		
		switch (channel_id) {
		case ChannelBean.CODENUM_YD_SUZHOU:
			
			result = accessClientByCodeNum1(order_code, phone, product);
			break;
		case ChannelBean.CODENUM_DX_HEFEI:
			result = accessClientByCodeNum2(order_code, phone, product);
			break;
		case ChannelBean.CODENUM_DX_FUYANG:
			result = accessClientByCodeNum3(order_code, phone, product);
			break;
		case ChannelBean.CODENUM_THIRDPARTY_YUNZHANGTONG:

			result = accessClientByCodeNum4(order_code, phone, product);
			break;
		case ChannelBean.CODENUM_THIRDPARTY_DAHANSANTONG:

			result = accessClientByCodeNum5(order_code, phone, product);
			break;
		case ChannelBean.CODENUM_THIRDPARTY_WANLIUKEJI:

			result = accessClientByCodeNum6(order_code, phone, product);
			break;

		case ChannelBean.CODENUM_THIRDPARTY_ZHUOWEI:

			result = accessClientByCodeNum7(order_code, phone, product);
			break;

		case ChannelBean.CODENUM_THIRDPARTY_NANJINGSHENGSHI:

			result = accessClientByCodeNum8(order_code, phone, product);
			break;
		case ChannelBean.CODENUM_THIRDPARTY_JIETUO:

			result = accessClientByCodeNum9(order_code, phone, product);
			break;
			
		default:
			break;
		}
		
		Integer discount = channel.getDiscount() == null ? 100:channel.getDiscount() ;
		result.setOrder_code(order_code);
		result.setServer_name(server_name);
		result.setProduct_id(productId);
		result.setDiscount(String.valueOf(discount));
		result.setCostMoney( String.valueOf((discount * product.getProduct_price()) / 100));
		result.setCreate_time(DateUtil.getNowDateStrSSS());
		result.setChannel_id(channel_id);
		//保存日志
		createInterfaceLog(result);
		return result;
	}
	


	/**
	 * 调用编号为1的通道客户端  宿州移动
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	public ISPInterfaceBean accessClientByCodeNum1(String orderCode,String phone,ProductBean product){
		return orderProductInstanceClient1.accessOrderProduct(orderCode, phone, product);
	}

	/**
	 * 调用编号为2的通道客户端 合肥电信
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	public ISPInterfaceBean accessClientByCodeNum2(String orderCode,String phone,ProductBean product){
		return orderProductInstanceClient2.accessOrderProduct(orderCode, phone, product);
	}

	/**
	 * 调用编号为3的通道客户端  阜阳电信
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	public ISPInterfaceBean accessClientByCodeNum3(String orderCode,String phone,ProductBean product){
		return orderProductInstanceClient3.accessOrderProduct(orderCode, phone, product);
	}

	/**
	 * 调用编号为4的通道客户端   云掌通
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	public ISPInterfaceBean accessClientByCodeNum4(String orderCode,String phone,ProductBean product){
		return orderProductInstanceClient4.accessOrderProduct(orderCode, phone, product);
	}

	/**
	 * 调用编号为5的通道客户端  大汉三通
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	public ISPInterfaceBean accessClientByCodeNum5(String orderCode,String phone,ProductBean product){
		return orderProductInstanceClient5.accessOrderProduct(orderCode, phone, product);
	}
	
	/**
	 * 调用编号为6的通道客户端  弯流科技
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	public ISPInterfaceBean accessClientByCodeNum6(String orderCode,String phone,ProductBean product){
		return orderProductInstanceClient6.accessOrderProduct(orderCode, phone, product);
	}


	/**
	 * 调用编号为8的通道客户端  南京盛世
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	private ISPInterfaceBean accessClientByCodeNum8(String order_code, String phone, ProductBean product) {
		
		return orderProductInstanceClient8.accessOrderProduct(order_code, phone, product);
	}

	/**
	 * 调用编号为9的通道客户端  杰拓通信
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	private ISPInterfaceBean accessClientByCodeNum9(String order_code, String phone, ProductBean product) {
		
		return orderProductInstanceClient9.accessOrderProduct(order_code, phone, product);
	}

	/**
	 * 调用编号为7的通道客户端  卓威
	 * @param orderCode
	 * @param phone
	 * @param product
	 * @return
	 */
	private ISPInterfaceBean accessClientByCodeNum7(String order_code, String phone, ProductBean product) {
		
		return orderProductInstanceClient7.accessOrderProduct(order_code, phone, product);
	}
	
	/**
	 * 访问移动产品订购
	 * @param phone
	 * @param productCode
	 * @return
	 */
	@Deprecated
	protected  ISPInterfaceBean accessYDOrderInterface(String orderCode,String phone, ProductBean product){
		
		return orderYDProductClient.accessOrderProduct(orderCode,phone, product);
	}
	
	/**
	 * 访问电信产品订购
	 * @param phone
	 * @param productCode
	 * @return
	 */
	@Deprecated
	protected  ISPInterfaceBean accessDXOrderInterface(String orderCode,String phone,  ProductBean product){
		
		return orderDXProductClient.accessOrderProduct(orderCode, phone, product);
	}
	/**
	 * 访问联通产品订购
	 * @param phone
	 * @param productCode
	 * @return
	 */
	@Deprecated
	protected  ISPInterfaceBean accessLTOrderInterface(String phone, String productCode){
		
		return null;
	}
	
	/**
	 * 创建接口日志
	 * @param bean
	 * @throws ISPException 
	 */
	@Override
	public void createInterfaceLog(ISPInterfaceBean bean) throws ISPException{
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		switch (bean.getChannel_id()) {
		case ISPInterfaceBean.TYPE_AHYD_SZ:
			createYDInterfaceLog((YDInterfaceBean)bean);
			break;
		case ISPInterfaceBean.TYPE_AHDX_HF:
			createDXInterfaceLog((DXInterfaceBean)bean);
			break;
		case ISPInterfaceBean.TYPE_AHDX_FY:
			createDXInterfaceLog((DXInterfaceBean)bean);
			break;

		case ISPInterfaceBean.TYPE_THIRDPARTY_YUNZHANGTONG:
			createYunZhangTongInterfaceLog((YunZhangTongInterfaceBean)bean);
			break;

		case ISPInterfaceBean.TYPE_THIRDPARTY_DAHANSANTONG:
			createDaHanSanTongInterfaceLog((DaHanSanTongInterfaceBean)bean);
			break;

		case ISPInterfaceBean.TYPE_THIRDPARTY_WANLIUKEJI:
			createWanLiuKeJiInterfaceLog((WanLiuInterfaceBean)bean);
			break;

		case ISPInterfaceBean.TYPE_THIRDPARTY_ZHUOWEI:
			createZhuoWeiInterfaceLog((ZhuoweiInterfaceBean)bean);
			break;

		case ISPInterfaceBean.TYPE_THIRDPARTY_NANJINGSHENGSHI:
			createNanJingShengShiInterfaceLog((NanJingShengShiInterfaceBean)bean);
			break;
		case ISPInterfaceBean.TYPE_THIRDPARTY_JIETUO:
			createJieTuoInterfaceLog((JieTuoInterfaceBean)bean);
			break;

		default:
			break;
		}
		
	}

	private void createZhuoWeiInterfaceLog(ZhuoweiInterfaceBean bean) {
		ispInterfaceLogDao.createZhuoWeiInterfaceLog(bean);
	}

	private void createNanJingShengShiInterfaceLog(NanJingShengShiInterfaceBean bean) {
		ispInterfaceLogDao.createNanJingShengShiInterfaceLog(bean);
	}
	
	private void createJieTuoInterfaceLog(JieTuoInterfaceBean bean) {
		ispInterfaceLogDao.createJieTuoInterfaceLog(bean);
	}

	/**
	 * 保存日志
	 * @param bean
	 */
	private void createLTInterfaceLog(YDInterfaceBean bean) {
		
	}
	/**
	 * 保存日志
	 * @param bean
	 */
	private void createDXInterfaceLog(DXInterfaceBean bean) {


		switch (bean.getType()) {
		case ISPInterfaceBean.TYPE_AHDX_HF:

			ispInterfaceLogDao.createDXInterfaceLog(bean);
			break;
		case ISPInterfaceBean.TYPE_AHDX_FY:

			ispInterfaceLogDao.createDX_FuYangInterfaceLog(bean);
			break;

		default:
			throw new ISPException("unsupported isp type! isp type:"+bean.getType());
		}
	}

	/**
	 * 保存日志
	 * @param bean
	 * @throws Exception 
	 */
	private void createYDInterfaceLog(YDInterfaceBean bean) throws ISPException{
		
		switch (bean.getType()) {
		case ISPInterfaceBean.TYPE_AHYD_SZ:
			
			ispInterfaceLogDao.createYDInterfaceLog(bean);
			break;

		default:
			throw new ISPException("unsupported isp type! isp type:"+bean.getType());
		}
	}
	

	/**
	 * 保存日志
	 * @param bean
	 */
	private void createYunZhangTongInterfaceLog(YunZhangTongInterfaceBean bean) {

		ispInterfaceLogDao.createYunZhangTongInterfaceLog(bean);
	}
	
	/**
	 * 保存日志
	 * @param bean
	 */
	private void createDaHanSanTongInterfaceLog(DaHanSanTongInterfaceBean bean) {

		ispInterfaceLogDao.createDaHanSanTongInterfaceLog(bean);
	}
	
	/**
	 * 保存日志
	 * @param bean
	 */
	private void createWanLiuKeJiInterfaceLog(WanLiuInterfaceBean bean) {
		List<WanLiuInterfaceBean> repList = bean.getRepDataList();
		List<Tx_info> tx_infol = bean.getTx_info();
		if (repList.size() > 0) {
			for (WanLiuInterfaceBean responseData : repList) {
				for (Tx_info tx_info : tx_infol) {
					if(tx_info.getReq_sn().equals(responseData.getReq_sn())){
						bean.setInfo(tx_info);
						break;
					}
					bean.setTx_info(null);
				}
				bean.setCreated(responseData.getCreated());
				bean.setReq_sn(responseData.getReq_sn());
				bean.setRep_mob_no(responseData.getRep_mob_no());
				bean.setOrder_sn(responseData.getOrder_sn());
				bean.setOrder_stat(responseData.getOrder_stat());
				bean.setRep_prod_code(responseData.getRep_prod_code());
				bean.setErr_code(responseData.getErr_code());
				bean.setErr_msg(responseData.getErr_msg());
				ispInterfaceLogDao.createWanLiuKeJiInterfaceLog(bean);
			}
		} else {
			ispInterfaceLogDao.createWanLiuKeJiInterfaceLog(bean);
		}
	}

	/**
	 * 处理云掌通的回调
	 * @param bean
	 */
	@Override
	public YunZhangTongInterfaceBean handleYunZhangTongCallBack(YunZhangTongInterfaceBean bean){
		
//		YunZhangTongInterfaceBean condition = new YunZhangTongInterfaceBean();
//		condition.setSerialNo(bean.getSerialNo());
//		
//		List<YunZhangTongInterfaceBean> list = ispInterfaceLogDao.queryYunZhangTongInterfaceLog(condition);
		String orderCode = bean.getCpBatchId();
		
		InterfaceBean result = dispatchOrderCode(orderCode,bean.getStatus(),"");
		
		bean.setStatus(result.getStatus());
		bean.setRemark(result.getRemark());

		updateThirdPartyCallBackInfo(bean);
		return bean;
	}
	

	/**
	 * 处理云掌通的回调
	 * @param bean
	 */
	@Override
	public DaHanSanTongInterfaceBean handleDaHanSanTongCallBack(DaHanSanTongInterfaceBean bean){
		
//		YunZhangTongInterfaceBean condition = new YunZhangTongInterfaceBean();
//		condition.setSerialNo(bean.getSerialNo());
//		
//		List<YunZhangTongInterfaceBean> list = ispInterfaceLogDao.queryYunZhangTongInterfaceLog(condition);
		String clientOrderId = bean.getClientOrderId();
		DaHanSanTongInterfaceBean old = ispInterfaceLogDao.findDaHanSanTongInterfaceByClientOrderId(clientOrderId);
		
		if(old != null){
			InterfaceBean result = dispatchOrderCode(old.getOrder_code(),bean.getStatus(),"");
			bean.setStatus(result.getStatus());
			bean.setRemark(result.getRemark());
		}else{
			bean.setStatus(-1);
			bean.setRemark("未找到之前的下单记录");
		}
		updateThirdPartyCallBackInfo(bean);
		
		return bean;
	}

	/**
	 * 处理弯流科技的回调
	 * @param bean
	 */
	@Override
	public WanLiuInterfaceBean handleWanLiuKeJiCallBack(WanLiuInterfaceBean bean){
		
		String req_sn = bean.getReq_sn();
		List<WanLiuInterfaceBean> oldList = ispInterfaceLogDao.findWanLiuKeJiInterfaceByReq_sn(req_sn);
		if(oldList.size() > 0){
			for (WanLiuInterfaceBean wanLiuInterfaceBean : oldList) {
				InterfaceBean result = dispatchOrderCode(wanLiuInterfaceBean.getReq_sn(),bean.getStatus(),bean.getRemark());
				bean.setStatus(result.getStatus());
				bean.setRemark(result.getRemark());
			}
		}		
		updateThirdPartyCallBackInfo(bean);
		return bean;
	}

	/**
	 * 处理南京盛世的回调
	 * @param bean
	 */
	@Override
	public NanJingShengShiInterfaceBean handleNanJingShengshiCallBack(NanJingShengShiInterfaceBean bean){
		
		String order_code = bean.getOrder_code();
		InterfaceBean result = dispatchOrderCode(order_code,bean.getStatus(),bean.getRemark());
		bean.setStatus(result.getStatus());
		bean.setRemark(result.getRemark());
		
		String date = DateUtil.getNowDateStrSSS();
		bean.setCallBack_time(date);
		updateThirdPartyCallBackInfo(bean);
		
		return bean;
	}

	/**
	 * 处理南京盛世的回调
	 * @param bean
	 */
	@Override
	public ZhuoweiInterfaceBean handleZhuoWeiCallBack(ZhuoweiInterfaceBean bean){
		
		String order_code = bean.getOrder_code();
		InterfaceBean result = dispatchOrderCode(order_code,bean.getStatus(),bean.getRemark());
		bean.setStatus(result.getStatus());
		bean.setRemark(result.getRemark());
		
		String date = DateUtil.getNowDateStrSSS();
		bean.setCallBack_time(date);
		updateThirdPartyCallBackInfo(bean);
		return bean;
	}
	
	/**
	 * 更新第三方调用接口信息
	 * @param bean
	 */
	private void updateThirdPartyCallBackInfo(ISPInterfaceBean bean){

		if(bean.getTable_name() == null){
			log.warn(" updateThirdPartyCallBackInfo table_name is null ! order_code :" + bean.getOrder_code());
			return ;
		}
		ispInterfaceLogDao.updateThirdPartyCallBackInfo(bean);
	}
	
	/**
	 * 处理南京盛世的回调
	 * @param bean
	 */
	@Override
	public JieTuoInterfaceBean handleJieTuoCallBack(JieTuoInterfaceBean bean){
		
		String order_code = bean.getOrder_code();
		InterfaceBean result = dispatchOrderCode(order_code,bean.getStatus(),bean.getRemark());
		bean.setStatus(result.getStatus());
		bean.setRemark(result.getRemark());
		
		String date = DateUtil.getNowDateStrSSS();
		bean.setCallBack_time(date);
		updateThirdPartyCallBackInfo(bean);
		
		return bean;
	}
	
	/**
	 * 根据订单号进行分发调度
	 * @param orderCode
	 */
	public InterfaceBean dispatchOrderCode(String orderCode,int status,String remark){
		InterfaceBean bean = null;
		
		//还在订购中的商品不处理
		if(status == ISPInterfaceBean.STATUS_WAIT){
			bean = new ISPInterfaceBean();
			bean.setStatus(ISPInterfaceBean.STATUS_WAIT);
			bean.setRemark("订购中");
			return bean;
		}
		
		if(StringUtil.isNotNull(orderCode)){
			//合作伙伴的定单
			if(orderCode.startsWith(IUserConsumeInfoService.OUTTRADE_TYPE_PARTNER)){
				
				bean = partnerInterfaceService.handlerISPInterfaceCallBack(orderCode,status,remark);

			}else{
				//处理普通用户的订单
				bean = orderService.handlerOrderProductCallBack(orderCode,status,remark);
			}
		}
		
		return bean;
	}
	
	@Override
	public void createISPCallbackLog(String phone,String order_code,String method_name,Integer status,String remark,String request_content,String response_content){
		
		ISPCallbackLogBean bean = new ISPCallbackLogBean();
		bean.setPhone(phone);
		bean.setOrder_code(order_code);
		bean.setMethod_name(method_name);
		bean.setStatus(status);
		bean.setRemark(remark);
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setRequest_content(request_content);
		bean.setResponse_content(response_content);
		
		try{
			ispInterfaceLogDao.createISPCallbackLog(bean);
		}catch (Exception e) {
			log.error(e, e);
		}
	}
	
	@Override
	public List<ISPInterfaceBean> queryInterfaceList(ISPInterfaceBean bean){
		return ispInterfaceLogDao.queryInterfaceList(bean);
	}
	
}
