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
import com.yd.business.system.bean.SystemRoleAdminRelationBean;
import com.yd.business.system.bean.SystemRoleBean;
import com.yd.business.system.service.ISystemManagerService;
import com.yd.iotbusiness.mapper.dao.LmOperatorModelMapper;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModelExample;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("operatorService")
public class OperatorServiceImpl extends BaseService implements IOperatorService {
	@Resource
	private IOperatorDao operatorDao;
	@Resource
	private LmOperatorModelMapper operatorModelMapper;
	@Resource
	private ISystemManagerService systemManagerService;
	
	@Override
	public OperatorBean findOperatorById(Long id) {
		OperatorBean bean = new OperatorBean();
		bean.setO_id(id);
		List<OperatorExtBean> list = operatorDao.queryOperatorList(bean);
		return null;
	}
	
	@Override
	public LmOperatorModel findOperatorById(Integer id) {
		return operatorModelMapper.selectByPrimaryKey(id);
	}
	
	
	@Override
	public OperatorExtBean findOperatorByNameAndPass(String username,String password) {
		OperatorBean bean = new OperatorBean();
		bean.setO_name(username);
		bean.setO_password2(password);
		List<OperatorExtBean> list = operatorDao.queryOperatorList(bean);
		
		return list.size() >0 ? list.get(0): null;
	}
	@Override
	public LmOperatorModel findLmOperatorByNameAndPass(String username,String password) {
		
		LmOperatorModelExample bean = new LmOperatorModelExample();
		LmOperatorModelExample.Criteria criteria = bean.createCriteria();
		criteria.andLoginnameEqualTo(username);
		criteria.andPassEqualTo(password);
		List<LmOperatorModel> list = operatorModelMapper.selectByExample(bean );
		
		return list.size() >0 ? list.get(0): null;
	}
	
	@Override
	public LmOperatorModel findOperatorByOpenid(String openid) {
		
		LmOperatorModelExample bean = new LmOperatorModelExample();
		LmOperatorModelExample.Criteria criteria = bean.createCriteria();
		criteria.andOpenidEqualTo(openid);
		List<LmOperatorModel> list = operatorModelMapper.selectByExample(bean );
		
		return list.size() >0 ? list.get(0): null;
	}
	
	@Override
	public List<OperatorExtBean> queryOperatorList(OperatorBean bean){

//		OperatorBean bean = new OperatorBean(); 
//		bean.setO_companyid(company_id);
		List<OperatorExtBean> list = operatorDao.queryOperatorList(bean);
		return list;
	}
	
	
	@Override
	public int addOrUpdateOperator(OperatorBean bean) {
		
		int i = 0 ;
		
		if(bean.getO_id() == null) {
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
			
			bean.setO_createdate(new Date());
			bean.setO_updatedate(bean.getO_createdate());
			bean.setO_password("313331303634"); //对应默认密码：123321
			i = operatorDao.insertOperator(bean);
			
		}else {
			bean.setO_updatedate(new Date());
			i = operatorDao.updateOperator(bean);
		}
		
		return i;
	}
	
	/**
	 * 
	 * 修改员工的角色权限
	 */
	@Override
	public int updateOperatorRole(Long opid , String[] roleids , OperatorBean loginOperator ) {
		int result = 0 ; 
		
		OperatorBean target = findOperatorById(opid);
		// 先判断登录用户是否有权限修改此员工的权限
		List<SystemRoleBean> loginRoleList = systemManagerService.querySystemRoleByOperator(loginOperator.getO_id());
		
		for(String roleid : roleids) {
			int id = Integer.parseInt(roleid);
			boolean hasRole = false;
			for(SystemRoleBean role : loginRoleList) {
				if(role.getId().intValue() == id) {
					hasRole = true;
					break;
				}
			}
			if(!hasRole) { // 如果当前登录用户没有这个权限，则直接结束
				return 0;
			}
		}
		// 同一公司才可修改  或者超级管理员 
		if(target.getO_companyid().intValue() == loginOperator.getO_companyid()  || loginOperator.getO_kind() == OperatorBean.KIND_SUPPERUSER) {
			
			SystemRoleAdminRelationBean bean = new SystemRoleAdminRelationBean();
			bean.setAdmin_id(target.getO_id().intValue());
			bean.setAdmin_name(target.getO_name());
			// 先删除原有角色权限
			systemManagerService.deleteSystemRoleAdminRelation(bean );
			// 插入已选择的角色权限
			int num = 0 ; //插入成功的记录
			for(String roleid : roleids) {
				SystemRoleBean role = systemManagerService.findSystemRoleById(Integer.parseInt(roleid));
				bean.setCreate_time(DateUtil.getNowDateStr());
				bean.setRole_id(role.getId());
				bean.setRole_name(role.getName());
				bean.setStatus(SystemRoleAdminRelationBean.STATUS_ENABLE);
				systemManagerService.createSystemRoleAdminRelation(bean);
				num++;
			}
			
			return num;
		}
		
		return result;
	}
	
	
}
