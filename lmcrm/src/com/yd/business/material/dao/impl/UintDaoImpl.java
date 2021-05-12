/**
 * 
 */
package com.yd.business.material.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.material.bean.UnitBean;
import com.yd.business.material.dao.IUnitDao;

/**
 * @author ice
 *
 */
@Repository("unitDao")
public class UintDaoImpl extends BaseDao implements IUnitDao {
    public static final  String NAMESPACE = "unit.";
    

    @Override
    public List<UnitBean> queryUnitList(UnitBean bean){
    	return sqlSessionTemplate.selectList(NAMESPACE + "queryUnitList", bean);
    }
    
    @Override
    public int insertUnit(UnitBean bean) {
    	return sqlSessionTemplate.insert(NAMESPACE + "insertUnit", bean);
    }

    @Override
    public int updateUnit(UnitBean bean) {
    	return sqlSessionTemplate.update(NAMESPACE +"updateUnit", bean);
    }

    @Override
    public int deleteUnit(long id) {
    	return sqlSessionTemplate.update(NAMESPACE +"deleteUnit", id);
    }
    
    
    
}
