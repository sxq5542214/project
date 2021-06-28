/**
 * 
 */
package com.yd.business.operator.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;
import com.yd.business.operator.dao.IOperatorDao;

/**
 * @author ice
 *
 */
@Repository("operatorDao")
public class OperatorDaoImpl extends BaseDao implements IOperatorDao {

    public static final  String NAMESPACE = "operator.";
    

    @Override
    public List<OperatorExtBean> queryOperatorList(OperatorBean bean){
    	return sqlSessionTemplate.selectList(NAMESPACE + "queryOperatorList", bean);
    }
    
    @Override
    public int insertOperator(OperatorBean bean) {
    	return sqlSessionTemplate.insert(NAMESPACE + "insertOperator", bean);
    }

    @Override
    public int updateOperator(OperatorBean bean) {
    	return sqlSessionTemplate.update(NAMESPACE +"updateOperator", bean);
    }

    @Override
    public int deleteOperator(long id) {
    	return sqlSessionTemplate.update(NAMESPACE +"deleteOperator", id);
    }
    
}
