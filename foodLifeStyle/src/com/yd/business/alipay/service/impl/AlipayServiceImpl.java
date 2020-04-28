/**
 * 
 */
package com.yd.business.alipay.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.alipay.bean.AliPayLogBean;
import com.yd.business.alipay.bean.AlipayConfig;
import com.yd.business.alipay.dao.IAlipayDao;
import com.yd.business.alipay.service.IAlipayService;
import com.yd.business.alipay.util.AlipayNotify;
import com.yd.business.alipay.util.AlipaySubmit;
import com.yd.business.customer.bean.CustomerAdminBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.bean.CustomerConsumeInfoBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.customer.service.ICustomerConsumeInfoService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("alipayService")
public class AlipayServiceImpl extends BaseService implements IAlipayService {
	public static final int TYPE_USER = 1;
	public static final int TYPE_CUSTOMER = 2;
	
	
	@Resource
	private IAlipayDao alipayDao;

	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	private ICustomerConsumeInfoService customerConsumeInfoService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ICustomerAdminService customerAdminService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private IOrderService orderService;
	@Resource
	private TaskExecutor taskExecutor;
	
	@Override
	public String createOrderProductUnifiedOrder(Integer adminid,String phone,Integer spid){
		
		CustomerAdminBean admin = customerAdminService.findCustomerAdminById(adminid);
		SupplierProductBean sp = supplierProductService.findSupplierProductById(spid);
		//商户订单号
		String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_ALIPAY, admin.getCustomer_id());
		//保存充值记录
		userConsumeInfoService.createConsumeInfo(phone,sp.getProduct_price(), sp.getId(), admin.getId(), null, out_no,UserConsumeInfoBean.INTERFACETYPE_ALIPAY,UserConsumeInfoBean.EVENT_TYPE_SUPPLIER_ORDER);
		
		
		return out_no;
	}
	
	@Override
	public String createCustomerRechargeUnifiedOrder(int customer_id,int moeny){
		
		//商户订单号
		String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_ALICHARGE, customer_id );
		//保存充值记录
		customerConsumeInfoService.createCustomerConsumeInfo(moeny, customer_id, out_no, CustomerConsumeInfoBean.INTERFACETYPE_ALIPAY,UserConsumeInfoBean.EVENT_TYPE_SUPPLIER_CHARGE);
		
		return out_no;
	}
	
	@Override
	public String toAliPayPage(String out_no,int type){
		String product_name = "";
		String out_name = "";
		Integer price = 0;
		
		switch (type) {
		case TYPE_USER:
			//取用户充值信息表
			UserConsumeInfoBean consume = userConsumeInfoService.findUserConsumeInfo(out_no);
			Integer spid = consume.getSupplier_product_id();
			SupplierProductBean sp = supplierProductService.findSupplierProductById(spid);
			out_name = consume.getPhone()+"订购【"+sp.getProduct_name()+"】";
			price = consume.getMoney();
			product_name = sp.getProduct_name();
			
			break;
		case TYPE_CUSTOMER:
			//取客户充值信息表
			
			CustomerConsumeInfoBean cconsume = customerConsumeInfoService.findCustomerConsumeInfo(out_no);
			CustomerBean customer = customerService.findCustomerById(cconsume.getCustomer_id());

			price = cconsume.getMoney();
			product_name = customer.getName()+"充值";
			out_name = "为"+customer.getName() + "充值" + price/100 +"元";
		
		break;

		}
		
		String product_url = "";
		

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = out_no;
        //订单名称，必填
        String subject = out_name;
        //付款金额，必填
        String total_fee = String.valueOf(price/100d);
        //收银台页面上，商品展示的超链接，必填
        String show_url = product_url;
        //商品描述，可空
        String body = product_name;
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("body", body);
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
        //如sParaTemp.put("参数名","参数值");

		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
		
		return sHtmlText;
	}
	
	
	/**
	 * 处理支付宝发来的支付完成通知消息
	 * @param requestParams
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@Override
	public AliPayLogBean handleServerNotify(Map<String, String[]> requestParams,String method) throws Exception{
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		log.info("handleAlipayServerNotify:"+params);
		
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = params.get("out_trade_no");

		//支付宝交易号
		String trade_no = params.get("trade_no");
		
		//交易状态
		String trade_status = params.get("trade_status");

		//先查询是否已经处理过
		AliPayLogBean logBean = new AliPayLogBean();; 
		AutoInvokeGetSetMethod.autoInvoke(params, logBean);
		logBean.setCreate_time(DateUtil.getNowDateStrSSS());
		logBean.setSign(null);//不保存签名数据
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理

				AliPayLogBean oldlog = alipayDao.findAliPayLogByTradeNo(trade_no,AliPayLogBean.STATUS_SUCCESS);
				if(oldlog == null ){

					String partnerId = configAttributeService.getValueByCode(AttributeConstant.CODE_ALIPAY_PARTNER_ID);
					//判断业务数据是否一致
					CustomerConsumeInfoBean cconsume = customerConsumeInfoService.findCustomerConsumeInfo(out_trade_no);
					if(cconsume != null){
						//客户充值的
						if(cconsume.getMoney()/100d == Double.valueOf(logBean.getTotal_fee()) && partnerId.equals(logBean.getSeller_id()) ){
							
							CustomerBean customer = customerService.findCustomerById(cconsume.getCustomer_id());
							//修改商户的账户余额，支付状态
							customer.setBalance( customer.getBalance() + cconsume.getMoney() );
							
							cconsume.setFinish_date(DateUtil.getNowDateStrSSS());
							cconsume.setStatus(CustomerConsumeInfoBean.STATUS_SUCCESS);
							logBean.setStatus(AliPayLogBean.STATUS_SUCCESS);
							
							customerService.addCustomerBalance(out_trade_no, customer.getId(), cconsume.getMoney(), "充值成功");
							customerConsumeInfoService.updateUserConsumeInfo(cconsume);
						}
					}else{
						//订购充值的
						UserConsumeInfoBean consume = userConsumeInfoService.findUserConsumeInfo(out_trade_no);
						
						if(consume.getMoney()/100d == Double.valueOf(logBean.getTotal_fee()) && partnerId.equals(logBean.getSeller_id()) ){
							
							Integer spid = consume.getSupplier_product_id();
							SupplierProductBean sp = supplierProductService.findSupplierProductById(spid);
							SupplierBean supplier = supplierService.findSupplierById(sp.getSupplier_id());
							//修改商户的账户余额，支付状态
							supplier.setBalance( supplier.getBalance() + consume.getMoney() );
							
							consume.setStatus(UserConsumeInfoBean.STATUS_SUCCESS);
							logBean.setStatus(AliPayLogBean.STATUS_SUCCESS);
							
							supplierService.updateSupplier(supplier);
							userConsumeInfoService.updateUserConsumeInfo(consume);
							
							//起线程去调用业务订购,  如果是商户订购，userID里存的是adminID
							startThreadOrderProduct(consume.getOut_trade_code(), consume.getUser_id());
						}
					}
				}
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
		}
		//记录日志
		alipayDao.createAliPayLog(logBean);
		
		return logBean;
	}
	
	
	/**
	 * 起线程去订购
	 * @param out_trade_code
	 * @param adminId
	 */
	private void startThreadOrderProduct(final String out_trade_code,final Integer adminId){
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//先休眠0.1秒
				try {
					Thread.sleep(100);
					//定购业务
					orderService.orderProductBySupplierBalance(out_trade_code, adminId);
					
				} catch (Exception e) {
					log.error(e, e);
				}
			}
		});
	}
	
}
