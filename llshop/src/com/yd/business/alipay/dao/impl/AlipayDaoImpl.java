/**
 * 
 */
package com.yd.business.alipay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.alipay.bean.AliPayLogBean;
import com.yd.business.alipay.dao.IAlipayDao;

/**
 * @author ice
 *
 */
@Repository("alipayDao")
public class AlipayDaoImpl extends BaseDao implements IAlipayDao {
	private static final String NAMESPACE = "alipay.";
	
	@Override
	public List<AliPayLogBean> queryAliPayLog(AliPayLogBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAlipayLog", bean);
	}
	
	@Override
	public AliPayLogBean findAliPayLog(AliPayLogBean bean){
		return sqlSessionTemplate.selectOne(NAMESPACE+ "queryAlipayLog",bean);
	}
	

	@Override
	public AliPayLogBean findAliPayLogByOutNo(String out_trade_no){
		AliPayLogBean bean = new AliPayLogBean();
		bean.setOut_trade_no(out_trade_no);
		
		return findAliPayLog(bean);
	}
	

	@Override
	public AliPayLogBean findAliPayLogByTradeNo(String trade_no,String status){
		AliPayLogBean bean = new AliPayLogBean();
		bean.setTrade_no(trade_no);
		bean.setStatus(status);
		return findAliPayLog(bean);
	}
	
	@Override
	public void createAliPayLog(AliPayLogBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createAliPayLog",bean);
	}
	
}
