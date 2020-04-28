/**
 * 
 */
package com.yd.business.partner.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelProductService;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.isp.bean.ISPInterfaceBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.partner.bean.PartnerBean;
import com.yd.business.partner.bean.PartnerDiscountInstanceBean;
import com.yd.business.partner.bean.PartnerInterfaceBean;
import com.yd.business.partner.dao.IPartnerDao;
import com.yd.business.partner.runable.PartnerCallbackRunable;
import com.yd.business.partner.service.IPartnerDiscountInstanceService;
import com.yd.business.partner.service.IPartnerInterfaceService;
import com.yd.business.partner.service.IPartnerService;
import com.yd.business.partner.util.PartnerUtil;
import com.yd.business.product.bean.PartnerProductBean;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IPartnerProductService;
import com.yd.business.product.service.IProductService;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.MD5;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("partnerInterfaceService")
public class PartnerInterfaceServiceImpl extends BaseService implements IPartnerInterfaceService {
	
	@Resource
	private IPartnerDao partnerDao;
	@Resource
	private IPartnerService partnerService;
	@Resource
	private IPartnerDiscountInstanceService partnerDiscountInstanceService;
	@Resource 
	private ICustomerService customerService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private IProductService productService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IPartnerProductService partnerProductService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IConfigAttributeService configAttributeService ;
	@Resource
	private IOrderService orderService;
	@Resource
	private IChannelProductService channelProductService;
	
	//存放进行中的合作伙伴定单号，线程同步的，避免同一时刻多次重复定购
	private static ConcurrentHashMap<String,Object> runningCacheMap = new ConcurrentHashMap<String, Object>();
	
	
	@Override
	public PartnerInterfaceBean handlePartnerInterface(Map<String, String> params,String method){
		System.out.println(runningCacheMap);
		PartnerInterfaceBean interfaceBean = new PartnerInterfaceBean();
		
		//合作伙伴编码
		String partner_code = params.get("partner_code");
		PartnerBean partner = partnerService.getPartnerByCode(partner_code);

//		//客户ID
		String customer_id = params.get("customer_id");
		
		//数据校验，在校验的时候加入到缓存中,合作伙伴的定单号
		boolean flag = validatePartnerInfo(interfaceBean, partner, params);
//		boolean flag = true;  //测试用的
		String partner_order_code = params.get("partner_out_trade_no");
		String cache_order_code = customer_id + partner_order_code;
		
		//校验成功，执行业务
		if(flag){
			
			PartnerOrderProductBean orderBean = orderService.findPartnerOrderProductByPartnerOrderCode(partner_order_code);
			
			//已经成功订购过的
			if(orderBean != null && orderBean.getStatus() == PartnerOrderProductBean.STATUS_SUCCESS){
				interfaceBean.setStatus(PartnerInterfaceBean.STATUS_SUCCESS);
				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
				interfaceBean.setResult_desc("订购成功");
				
			}else{
				try {
					//如果此单号是首次，则新建
					if(orderBean == null){
						orderBean = new PartnerOrderProductBean();
						orderBean.setCreate_time(DateUtil.getNowDateStrSSS());
						
						//创建定单号
						String out_trade_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_PARTNER, partner.getId().toString(),new Date(), true);
						orderBean.setOut_trade_no(out_trade_no);
						AutoInvokeGetSetMethod.autoInvoke(params, orderBean);
					}
					orderBean.setModify_time(DateUtil.getNowDateStrSSS());
			
					ProductBean product = productService.findProductByCode(params.get("product_code"));

					
					orderBean.setProduct_id(product.getId());
					orderBean.setProduct_name(product.getName());
					orderBean.setProduct_price(product.getProduct_price());
					orderBean.setPartner_id(partner.getId());
					//访问具体的订购通道
//					ISPInterfaceBean ispBean = accessISPOrderInterfaceService.accessISPOrderInterface(orderBean.getOut_trade_no(), orderBean.getOrder_account(), orderBean.getProduct_id());
					ISPInterfaceBean ispBean = channelProductService.orderProductByChannel(Integer.parseInt(customer_id), orderBean.getOut_trade_no(), orderBean.getOrder_account(), "",  orderBean.getProduct_id());
					
					//接口访问得成功才算成功
					if( (ispBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS && ispBean.getChannel_type() == ChannelBean.TYPE_SYNC )
							|| (ispBean.getStatus() == ISPInterfaceBean.STATUS_WAIT && ispBean.getChannel_type() == ChannelBean.TYPE_ASYNC )){
						orderBean.setChannel_id(ispBean.getChannel_id());
						switch (ispBean.getChannel_type()) {
						//同步的通道
						case ChannelBean.TYPE_SYNC:
							
							orderBean.setStatus(PartnerOrderProductBean.STATUS_SUCCESS);
							orderBean.setNotify_status(PartnerOrderProductBean.NOTIFY_STATUS_WAIT);
							interfaceBean.setStatus(PartnerInterfaceBean.STATUS_SUCCESS);
							interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
							interfaceBean.setResult_desc("下单成功，待订购");

							orderBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
							orderBean.setResult_desc("订购成功");
							Integer balance = calcPartnerDiscountPrice(product, partner);
							orderBean.setDiscount_price(balance); //记录折后价
							//扣余额
							customerService.addCustomerBalance(orderBean.getOut_trade_no(), Integer.parseInt(customer_id), balance, "为"+orderBean.getOrder_account()+"订购【"+product.getName()+"】商品");
							
							if(StringUtil.isNotNull(orderBean.getNotify_url())){
								//通道是同步的，但接口里有传异步通知的URL，则启线程去给伙伴异步通知
								taskExecutor.execute(new PartnerCallbackRunable(orderBean), 30 * 1000);
							}
							
							break;
						
						//异步的通道
						case ChannelBean.TYPE_ASYNC:
							
							//异步的返回待订购，isp给我们回调时，我们再给伙伴回调
							orderBean.setStatus(OrderProductLogBean.STATUS_WAIT);
							orderBean.setResult_desc("下单成功，待订购");
							orderBean.setNotify_status(PartnerOrderProductBean.NOTIFY_STATUS_WAIT);
							interfaceBean.setStatus(PartnerInterfaceBean.STATUS_SUCCESS);
							interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
							interfaceBean.setResult_desc("下单成功");
							break;
						default:
							break;
						}
					}else{
						orderBean.setStatus(PartnerOrderProductBean.STATUS_FAILD);
						interfaceBean.setStatus(PartnerInterfaceBean.STATUS_FAILD);
						interfaceBean.setResult_code(ispBean.getResCode());
						interfaceBean.setResult_desc(ispBean.getResMsg());
						orderBean.setChannel_id(ispBean.getChannel_id());
						orderBean.setResult_code(ispBean.getResCode());
						orderBean.setResult_desc(ispBean.getResMsg());
					}
					
					//保存订购记录
					orderService.saveOrUpdatePartnerOrderProduct(orderBean);
				} catch (Exception e) {
					log.error(e, e);
					interfaceBean.setStatus(PartnerInterfaceBean.STATUS_FAILD);
					interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_SYSTEM_INTERNAL_ERROR);
					interfaceBean.setResult_desc("系统内部错误，请联系管理员");
					
				}
			}
			

			//附加上原有的部分业务信息
			interfaceBean.setAttach(params.get("attach"));
			interfaceBean.setProduct_code(params.get("product_code"));
			interfaceBean.setPartner_out_trade_no(params.get("partner_out_trade_no"));
			interfaceBean.setOrder_account(params.get("order_account"));
			
			
			
		}else{
			interfaceBean.setStatus(PartnerInterfaceBean.STATUS_FAILD);
		}
		
		//只要不是返回进行中，就移除缓存
		if(!PartnerInterfaceBean.RESULT_CODE_PARTNER_ORDER_RUNNING.equals(interfaceBean.getResult_code())){
			//业务执行完毕，移除缓存
			runningCacheMap.remove(cache_order_code);
		}
		return interfaceBean;
	}
	
	/**
	 * 处理运营商的回调，并给合作伙伴发送回调
	 * @param orderCode
	 * @return
	 */
	@Override
	public PartnerInterfaceBean handlerISPInterfaceCallBack(String orderCode,int status,String remark){

		PartnerOrderProductBean orderBean = orderProductLogService.findPartnerOrderProductByOrderCode(orderCode);

		PartnerInterfaceBean interfaceBean = new PartnerInterfaceBean(); 
		if(orderBean != null ){
			
			if(orderBean.getStatus() == PartnerOrderProductBean.STATUS_WAIT){
				
				if(status == PartnerInterfaceBean.STATUS_SUCCESS){
					orderBean.setModify_time(DateUtil.getNowDateStrSSS());
					orderBean.setStatus(PartnerOrderProductBean.STATUS_SUCCESS);
					orderBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
					orderBean.setResult_desc("订购成功");
					
					interfaceBean.setStatus(PartnerInterfaceBean.STATUS_SUCCESS);
					interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
					interfaceBean.setResult_desc("订购成功");
					
					ProductBean product = productService.findProductById(orderBean.getProduct_id());
					PartnerBean partner = partnerDao.findPartnerById(orderBean.getPartner_id());
					
					Integer balance = calcPartnerDiscountPrice(product, partner);
					orderBean.setDiscount_price(balance); //记录折后价
					//扣余额
					customerService.addCustomerBalance(orderBean.getOut_trade_no(), partner.getCustomer_id(), balance, "为"+orderBean.getOrder_account()+"订购【"+product.getName()+"】商品");
		
				}else{
					interfaceBean.setStatus(ISPInterfaceBean.STATUS_FAILD);
					orderBean.setStatus(ISPInterfaceBean.STATUS_FAILD);
					orderBean.setResult_code(status+"");
					orderBean.setResult_desc(remark);
					//检查通道情况
					channelProductService.checkChannelAlive(orderBean.getChannel_id(), orderBean.getProduct_id());
				}

				//保存订购记录
				orderService.saveOrUpdatePartnerOrderProduct(orderBean);
				
				//启线程去给伙伴异步通知
				taskExecutor.execute(new PartnerCallbackRunable(orderBean));
				

				//异步订购成功后，扣减通道余额
				if(orderBean.getStatus() == ISPInterfaceBean.STATUS_SUCCESS){
					channelProductService.minusChannelBalance(orderBean.getChannel_id(), orderBean.getProduct_id(),orderCode);
				}
				
			}else{
				interfaceBean.setStatus(orderBean.getStatus());
			}
		}else{

			interfaceBean.setStatus(PartnerInterfaceBean.STATUS_FAILD);
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_ORDERCODE_ERROR);
			interfaceBean.setResult_desc("订单号未找到！" + orderCode);
		}
		
		
		return interfaceBean;
	}
	
	
	
	/**
	 * 计算伙伴的折扣额
	 * @param product
	 * @param partner
	 * @return
	 */
	private Integer calcPartnerDiscountPrice(ProductBean product, PartnerBean partner){
		Integer balance = -product.getProduct_price();
		//先通过产品的id查询相应的折扣
		PartnerDiscountInstanceBean pdi = partnerDiscountInstanceService.findPartnerDiscountInstance(partner.getCustomer_id(), partner.getLast_month_money(),product.getProduct_brand(),product.getProduct_type(),product.getId());
		if(pdi != null && pdi.getDiscount() != null){
			balance = (balance * pdi.getDiscount()) / 100;  // discount 是百比分,所以要除以100
		}
		//单个商品没有折扣，走通用流程
		if(pdi == null){
			//计算实际价格,如果伙伴有折扣实例，先取个性化的实例， 没有再去取统一的折扣
			pdi = partnerDiscountInstanceService.findPartnerDiscountInstance(partner.getCustomer_id(), partner.getLast_month_money(),product.getProduct_brand(),product.getProduct_type(),null);
			if(pdi != null && pdi.getDiscount() != null){
				balance = (balance * pdi.getDiscount()) / 100;  // discount 是百比分,所以要除以100
			}
		}
		//还是没有的话，取统一的折扣
		if(pdi == null){
			CustomerDiscountBean groupDiscount = partnerDiscountInstanceService.findPartnerDiscountByGroup(partner.getCustomer_id(), partner.getLast_month_money(),product.getProduct_brand(),product.getProduct_type());
			if(groupDiscount != null && groupDiscount.getDiscount() != null){
				balance = (balance * groupDiscount.getDiscount()) / 100;  // discount 是百比分,所以要除以100
			}
		}

		
		return balance;
	}
	
	
	/**
	 * 校验合作伙伴信息
	 * @param params
	 * @return
	 */
	private boolean validatePartnerInfo(PartnerInterfaceBean interfaceBean,PartnerBean partner,Map<String,String> params){
		boolean flag = true;

		//客户ID
		String customer_id = params.get("customer_id");
		String order_account = params.get("order_account");
		String sign = params.get("sign");
		//定单编码
		String order_code = params.get("partner_out_trade_no");
		String cache_order_code = customer_id + order_code;
		// 判断缓存，多线程不能重复订购
		if(runningCacheMap.get(cache_order_code) != null){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_ORDER_RUNNING);
			interfaceBean.setResult_desc("该定单号正在订购进行中，不能重复订购");
			flag = false;
		}else if(StringUtil.isNotNull(cache_order_code)){
			runningCacheMap.put(cache_order_code, "running");
		}
		
		
		//产品编码
		String product_code = params.get("product_code");
		//访问IP
		String spbill_create_ip = params.get("spbill_create_ip");
		
		ProductBean product = productService.findProductByCode(product_code);
		CustomerBean customer = null;
		if(StringUtil.isNotNull(customer_id)){
			customer = customerService.findCustomerById(Integer.parseInt(customer_id));
		}
		//验证签名
		if(partner == null){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_ERROR);
			interfaceBean.setResult_desc("合作伙伴编码错误，请检查请求数据");
			flag = false;
		}else if(partner.getStatus() != PartnerBean.STATUS_ENABLE){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_STATUS_ERROR);
			interfaceBean.setResult_desc("合作伙伴状态为未启用或已到期，请重新签约后再使用");
			flag = false;
		}else if(partner.getDff_date() == null || DateUtil.getNowDateStr().compareTo(partner.getDff_date()) >0 ){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_STATUS_DFF);
			interfaceBean.setResult_desc("合作伙伴签约时间已到期，请重新签约后再使用");
			flag = false;
		}else if(StringUtil.isNull(order_account) || order_account.length() != 11 ){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_ORDER_ACCOUNT_ERROR);
			interfaceBean.setResult_desc("合作伙伴订购号码有误，请检查请求数据");
			flag = false;
		}else if(customer_id == null || customer == null){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_CUSTOMER_ERROR);
			interfaceBean.setResult_desc("客户信息错误，请检查请求数据");
			flag = false;
		}else if(customer.getStatus() != CustomerBean.STATUS_YES){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_CUSTOMER_ERROR);
			interfaceBean.setResult_desc("当前客户状态不可用，请联系平台侧进行启用后再试");
			flag = false;
		}else if(order_code == null || order_code.length() > 32){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_ORDERCODE_ERROR);
			interfaceBean.setResult_desc("合作伙伴订单号为空或大于32位，请检查请求数据");
			flag = false;
		}else if(partner.getCustomer_id() != Integer.parseInt(customer_id) ){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_CUSTOMER_ERROR);
			interfaceBean.setResult_desc("合作伙伴信息与客户信息不匹配，请检查请求数据");
			flag = false;
		}else if(product == null ){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_PRODUCT_ERROR);
			interfaceBean.setResult_desc("没有该产品，请检查请求数据");
			flag = false;
		}else if(StringUtil.isNull(sign) ){
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_SIGN_ERROR);
			interfaceBean.setResult_desc("合作伙伴签名字段不能为空，请检查请求数据");
			flag = false;
		}
		//数据都非空，判断权限和余额
		if(flag){
			
			PartnerProductBean ppd = partnerProductService.findPartnerProduct(partner.getId(), product.getCode());
			if(ppd == null){
				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_PRODUCT_ERROR);
				interfaceBean.setResult_desc("合作伙伴信息与产品编码不匹配，请检查请求数据");
				flag = false;
			}else if(ppd.getStatus() == null || ppd.getStatus() != PartnerProductBean.STATUS_ENABLE){

				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_PRODUCT_ERROR);
				interfaceBean.setResult_desc("该产品状态不可用，维护中");
				flag = false;
			}else if(ppd.getDff_date() == null || DateUtil.getNowDateStr().compareTo(ppd.getDff_date()) >0){

				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_PRODUCT_DFF);
				interfaceBean.setResult_desc("合作伙伴该订购商品已过期或未生效，请重新签约商品或延长有效期");
				flag = false;
			}
			CustomerBean cust = customerService.findCustomerById(Integer.parseInt(customer_id));
			if(cust.getBalance() < product.getProduct_price()){
				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_BALANCE_ERROR);
				interfaceBean.setResult_desc("合作伙伴账户余额不足，请先进行账户充值");
				flag = false;
			}
			
			if(partner.getAllow_ips() != null &&  partner.getAllow_ips().indexOf(spbill_create_ip) < 0){
				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_IP_ERROR);
				interfaceBean.setResult_desc("合作伙伴IP限制，此IP【"+ spbill_create_ip +"】不可访问接口");
				flag = false;
			}
			//校验签名
			if(!verify(params, partner)){
				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_SIGN_ERROR);
				interfaceBean.setResult_desc("合作伙伴签名加密数据不匹配，请检查请求数据");
				flag = false;
			}
			
			//校验接口调用限额
			int callCount = partnerDao.getPartnerCallOfDayCount(partner.getId(), DateUtil.formatDateOnlyDate(new Date()));
			if(partner.getDay_limit() != null  && callCount > partner.getDay_limit()){
				interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_DAYCALL_LIMIT);
				interfaceBean.setResult_desc("合作伙伴日调用接口达到限额，若需要提高限额请重新签约");
				flag = false;
			}
			
			boolean hasInvalidDay = configAttributeService.getBooleanValueByCode(AttributeConstant.CODE_PARTNER_INTERFACE_HASINVALIDDAY);
			if(hasInvalidDay){
				// 每月第一天和月末最后两天，不可以调用接口
				Calendar c = Calendar.getInstance();
				int day = c.get(Calendar.DAY_OF_MONTH);
				int month = c.get(Calendar.MONTH);
				c.add(Calendar.DATE, 2);
				int newMonth = c.get(Calendar.MONTH);
				
				if(day == 1 || month != newMonth){
					interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_INVALID_DAY);
					interfaceBean.setResult_desc("合作伙伴调用接口月初第一天和月末两天暂不开放，请避开后再试");
					flag = false;
				}
			}
		}
		
		return flag;
	}
	
	 /**
     * 验证消息是否是此客户发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    private static boolean verify(Map<String, String> params,PartnerBean partner) {
    	
    	String secret_key = partner.getSecret_key();
	    String sign = params.get("sign");
	    String sign_type = params.get("sign_type");
//	    params.put("secret_key", secret_key);
	    //移除签名
	    params.remove("sign");
	    params.remove("sign_type");
	    //IP是从request中取的，不是对方传的，所以签名时要去除，验证完签名再加上
	    String spbill_create_ip = params.remove("spbill_create_ip");
	    
	    String link = PartnerUtil.createLinkString(params);
	    //密钥放在最后拼
	    link = link + "&secret_key=" + secret_key;
	  //获得签名验证结果
        boolean isSign = false;
        if("MD5".equals(sign_type) ) {
        	
        	String new_sign = MD5.md5(link);
        	
        	if(new_sign.equals(sign)){
        		isSign = true;
        	}
        }
        params.put("spbill_create_ip",spbill_create_ip);
        return isSign;
    }
	
    /**
     * 查询客户余额
     */
    @Override
    public PartnerInterfaceBean queryCustomerBalance(int customer_id,String partnerCode){

		PartnerInterfaceBean interfaceBean = new PartnerInterfaceBean();
		
    	PartnerBean partner = partnerService.getPartnerByCode(partnerCode);
    	
    	if(customer_id == partner.getCustomer_id()){
    		
    		CustomerBean cus = customerService.findCustomerById(customer_id);
    		
    		Integer balance = cus.getBalance();
    		
    		interfaceBean.setStatus(PartnerInterfaceBean.STATUS_SUCCESS);
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
			interfaceBean.setResult_desc("您的余额为："+ balance / 100d );
    	}else{

    		interfaceBean.setStatus(PartnerInterfaceBean.STATUS_FAILD);
			interfaceBean.setResult_code(PartnerInterfaceBean.RESULT_CODE_CUSTOMER_ERROR);
			interfaceBean.setResult_desc("匹配客户信息出错，请检查入参数据！ ");
    	}
    	
    	
    	return interfaceBean;
    }
    
    /**
     * 查询合作伙伴定单情况
     */
    @Override
    public PartnerOrderProductBean queryPartnerOrder(int customer_id,String partnerCode,String partner_out_trade_no){

    	PartnerOrderProductBean order = new PartnerOrderProductBean();
		
    	PartnerBean partner = partnerService.getPartnerByCode(partnerCode);
    	
    	if(customer_id == partner.getCustomer_id()){
    		
    		order = orderProductLogService.findPartnerOrderProductByPartnerOrderCode(partner_out_trade_no);
    		
    		if(order != null){
    			if(!StringUtil.isNull(order.getStatus())){
    				if(order.getStatus() == PartnerInterfaceBean.STATUS_SUCCESS){
    					order.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_SUCCESS);
    	        		order.setResult_desc("订购成功");
    				}else if(order.getStatus() == PartnerInterfaceBean.STATUS_FAILD){
    					order.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_FAILD);
    	        		order.setResult_desc("订购失败");
    				}else if(order.getStatus() == OrderProductLogBean.STATUS_WAIT){
    					order.setResult_code(PartnerInterfaceBean.RESULT_CODE_ORDER_WAIT);
    	        		order.setResult_desc("订购中");
    				}
    			}else{
    				order.setStatus(PartnerInterfaceBean.STATUS_FAILD);
            		order.setResult_code(PartnerInterfaceBean.RESULT_CODE_SYSTEM_INTERNAL_ERROR);
            		order.setResult_desc("系统内部错误");
    			}
    		}else{
    			order = new PartnerOrderProductBean();
        		order.setStatus(PartnerInterfaceBean.STATUS_FAILD);
        		order.setResult_code(PartnerInterfaceBean.RESULT_CODE_PARTNER_ORDERCODE_ERROR);
        		order.setResult_desc("定单号未找到！请检查合作伙伴订单号");
    		}
    	}else{

    		order.setStatus(PartnerInterfaceBean.STATUS_FAILD);
    		order.setResult_code(PartnerInterfaceBean.RESULT_CODE_CUSTOMER_ERROR);
    		order.setResult_desc("匹配客户信息出错，请检查入参数据！ ");
    	}
    	
    	
    	return order;
    }
    
    
    public static void main(String[] args) {
		Map<String,String> map = new HashMap<String, String>();
//		map.put("attach", "13739271082_201700000001");
		map.put("order_account", "13865017100");
		map.put("product_code", "AnHYDGN2G");
//		map.put("product_code", "AHDX10M");
		map.put("scope", "AnHYDGN2G");
		map.put("partner_out_trade_no", "20170201235401532546");
		map.put("partner_code", "6db187a463f5426cb0ed5bde906ea2c5");
		map.put("customer_id", "5059");
		map.put("nonce_str", "d67e89e1-0b71-41a3-8a88-7dac7ce91c58");
		map.put("notify_url", "http://120.76.72.110:17058/");
//		map.put("spbill_create_ip", "120.76.72.110");
		map.put("sign", "583d091cef218543872657d00191a305");
		PartnerBean p  = new PartnerBean();
		p.setSecret_key("26f7e554aa9e4623b3b176703bc91bf572b80abaca694c8abdcc9d631e554e0");
		
		 String link = PartnerUtil.createLinkString(map);
		    //密钥放在最后拼
		 link = link + "&secret_key=" + p.getSecret_key();
		 String sign = MD5.md5(link);
		map.put("sign_type", "MD5");
//		map.put("sign", sign);
		System.out.println(sign);
		System.out.println(link);
		
		
		try {
			String result = HttpUtil.post("http://www.91liuliang.cc/llshop/p/intf/handlePartnerAsyncOrder.htm", map);
		
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean f = verify(map, p);
		System.err.println(f);
		
	}
}
