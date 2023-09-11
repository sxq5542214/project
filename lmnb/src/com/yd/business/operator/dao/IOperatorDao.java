/**
 * 
 */
package com.yd.business.operator.dao;

import java.util.List;

import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;

/**
 * @author ice
 *
 */
public interface IOperatorDao {

	List<OperatorExtBean> queryOperatorList(OperatorBean bean);

	int insertOperator(OperatorBean bean);

	int updateOperator(OperatorBean bean);

	int deleteOperator(long id);

}
