/**
 * 
 */
package com.yd.business.operator.dao;

import java.util.List;

import com.yd.business.operator.bean.OperatorBean;

/**
 * @author ice
 *
 */
public interface IOperatorDao {

	List<OperatorBean> queryOperatorList(OperatorBean bean);

	int insertOperator(OperatorBean bean);

	int updateOperator(OperatorBean bean);

	int deleteOperator(long id);

}
