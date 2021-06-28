/**
 * 
 */
package com.yd.business.operator.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;
import com.yd.business.operator.dao.IOperatorDao;
import com.yd.business.operator.service.IOperatorService;

/**
 * @author ice
 *
 */
@Service("operatorService")
public class OperatorServiceImpl extends BaseService implements IOperatorService {
	@Resource
	private IOperatorDao operatorDao;
	@Override
	public OperatorBean findOperatorById(Long id) {
		OperatorBean bean = new OperatorBean();
		bean.setO_id(id);
		List<OperatorExtBean> list = operatorDao.queryOperatorList(bean);
		return list.size() >0 ? list.get(0): null;
	}
	
	@Override
	public OperatorBean findOperatorByNameAndPass(String username,String password) {
		OperatorBean bean = new OperatorBean();
		bean.setO_name(username);
		bean.setO_password2(password);
		List<OperatorExtBean> list = operatorDao.queryOperatorList(bean);
		return list.size() >0 ? list.get(0): null;
	}
	
	@Override
	public List<OperatorExtBean> queryOperatorList(Long company_id){

		OperatorBean bean = new OperatorBean();
		bean.setO_companyid(company_id);
		List<OperatorExtBean> list = operatorDao.queryOperatorList(bean);
		return list;
	}
	
	
	@Override
	public int addOrUpdateOperator(OperatorBean bean) {
		
		int i = 0 ;
		bean.setO_rank1("FFFF");
		bean.setO_rank2("FFFF");
		bean.setO_rank3("FFFF");
		bean.setO_rank4("FFFF");
		bean.setO_rank5("FFFF");
		bean.setO_rank6("FFFF");
		bean.setO_rank7("FFFF");
		bean.setO_rank8("FFFF");
		bean.setO_rank9("FFFF");
		bean.setO_rank99("FFFF");
		
		if(bean.getO_id() == null) {
			
			bean.setO_createdate(new Date());
			bean.setO_updatedate(bean.getO_createdate());
			i = operatorDao.insertOperator(bean);
			
		}else {
			bean.setO_updatedate(new Date());
			i = operatorDao.updateOperator(bean);
		}
		
		return i;
	}
	
	
}
