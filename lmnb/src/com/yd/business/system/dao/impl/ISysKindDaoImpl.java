/**
 * 
 */
package com.yd.business.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.system.bean.SysKindBean;
import com.yd.business.system.dao.ISysKindDao;

/**
 * @author ice
 *
 */
@Repository("sysKindDao")
public class ISysKindDaoImpl extends BaseDao implements ISysKindDao {
	private static final String NAMESPACE = "sysKind.";
	
	

    @Override
    public List<SysKindBean> querySysKindList(SysKindBean bean){
    	return sqlSessionTemplate.selectList(NAMESPACE + "querySysKindList", bean);
    }
    
    @Override
    public int insertSysKind(SysKindBean bean) {
    	return sqlSessionTemplate.insert(NAMESPACE + "insertSysKind", bean);
    }

    @Override
    public int updateSysKind(SysKindBean bean) {
    	return sqlSessionTemplate.update(NAMESPACE +"updateSysKind", bean);
    }

    @Override
    public int deleteSysKind(long id) {
    	return sqlSessionTemplate.update(NAMESPACE +"deleteSysKind", id);
    }
    
    
}
