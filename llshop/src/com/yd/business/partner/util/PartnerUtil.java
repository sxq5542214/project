/**
 * 
 */
package com.yd.business.partner.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yd.business.isp.bean.InterfaceBean;
import com.yd.business.order.bean.PartnerOrderProductBean;
import com.yd.business.partner.bean.PartnerInterfaceBean;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
public class PartnerUtil {
	private static Logger log = Logger.getLogger(PartnerUtil.class);

	/**
	 * 转换类为XML格式
	 * @param bean
	 * @return
	 */
	public static String convertPartnerInterfaceBeanToXML(PartnerInterfaceBean bean){
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		
		Element root = doc.addElement("ROOT");
		
		Element status = root.addElement("STATUS");
		status.addCDATA(String.valueOf(bean.getStatus()));
		
		Element result_code = root.addElement("RESULT_CODE");
		result_code.addCDATA(StringUtil.convertNull(bean.getResult_code()));
		
		Element result_desc = root.addElement("RESULT_DESC");
		result_desc.addCDATA(StringUtil.convertNull(bean.getResult_desc()));
		
		Element partner_out_trade_no = root.addElement("PARTNER_OUT_TRADE_NO");
		partner_out_trade_no.addCDATA(StringUtil.convertNull(bean.getPartner_out_trade_no()));
		
		Element product_code = root.addElement("PRODUCT_CODE");
		product_code.addCDATA(StringUtil.convertNull(bean.getProduct_code()));
		
		Element order_account = root.addElement("ORDER_ACCOUNT");
		order_account.addCDATA(StringUtil.convertNull(bean.getOrder_account()));
		
		Element attach = root.addElement("ATTACH");
		attach.addCDATA(StringUtil.convertNull(bean.getAttach()));
		
		
		return doc.asXML();
	}
	
	

	/**
	 * 转换类为XML格式
	 * @param bean
	 * @return
	 */
	public static String convertPartnerInterfaceBeanToXML(PartnerOrderProductBean bean){
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		
		Element root = doc.addElement("ROOT");
		
		Element status = root.addElement("STATUS");
		status.addCDATA(String.valueOf(bean.getStatus()));
		
		Element result_code = root.addElement("RESULT_CODE");
		result_code.addCDATA(StringUtil.convertNull(bean.getResult_code()));
		
		Element result_desc = root.addElement("RESULT_DESC");
		result_desc.addCDATA(StringUtil.convertNull(bean.getResult_desc()));
		
		Element order_account = root.addElement("ORDER_ACCOUNT");
		order_account.addCDATA(StringUtil.convertNull(bean.getOrder_account()));
		
		Element attach = root.addElement("ATTACH");
		attach.addCDATA(StringUtil.convertNull(bean.getAttach()));
		
		
		return doc.asXML();
	}
	

	/**
	 * 转换类为XML格式
	 * @param bean
	 * @return
	 */
	public static String createFaildXML(String code, String desc){
		
		return createResponseXML(PartnerInterfaceBean.STATUS_FAILD, code, desc);
	}
	
	/**
	 * 转换类为XML格式
	 * @param bean
	 * @return
	 */
	public static String createResponseXML(int statusCode, String code, String desc){
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		
		Element root = doc.addElement("ROOT");
		
		Element status = root.addElement("STATUS");
		status.addCDATA(String.valueOf(statusCode));
		
		Element result_code = root.addElement("RESULT_CODE");
		result_code.addCDATA(code);
		
		Element result_desc = root.addElement("RESULT_DESC");
		result_desc.addCDATA(desc);
		
		Element partner_out_trade_no = root.addElement("PARTNER_OUT_TRADE_NO");
		
		Element product_code = root.addElement("PRODUCT_CODE");
		
		Element order_account = root.addElement("ORDER_ACCOUNT");
		
		Element attach = root.addElement("ATTACH");
		
		
		return doc.asXML();
	}
	
	/**
	 * 解析回调的内容
	 * @return
	 * @throws DocumentException 
	 */
	public static PartnerInterfaceBean parseNotifyResponse(String response) {
		PartnerInterfaceBean bean = new PartnerInterfaceBean();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(response);

			Element root = doc.getRootElement();

			String status = root.elementText("STATUS");
			String result_code = root.elementText("RESULT_CODE");
			String result_desc = root.elementText("RESULT_DESC");

			bean.setStatus(Integer.parseInt(status));
			bean.setResult_code(result_code);
			bean.setResult_desc(result_desc);
			
		} catch (DocumentException e) {
			log.error(e, e);
			bean.setStatus(PartnerInterfaceBean.STATUS_FAILD);
			bean.setResult_code("document_type_error");
			bean.setResult_desc("回调伙伴后返回的文档格式不正确！");
		}
		
		
		
		return bean;
	}
	
	
	

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if(value == null || "".equals(value.trim())){
            	continue;
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
	
	public static void main(String[] args) {
		PartnerInterfaceBean bean = new PartnerInterfaceBean();
		bean.setAttach("123123");
		bean.setOrder_account("15921841122");
		bean.setPartner_out_trade_no("15921841122_20160519112553285");
		bean.setResult_code("SUCCESS");
		bean.setResult_desc("订购成功");
		bean.setProduct_code("isp_lt_M50");
		
		
		System.out.println(convertPartnerInterfaceBeanToXML(bean));
	}
	
}
