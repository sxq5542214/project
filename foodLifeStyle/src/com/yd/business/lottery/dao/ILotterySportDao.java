/**
 * 
 */
package com.yd.business.lottery.dao;

import java.util.List;

import com.yd.business.lottery.bean.ExpertBean;
import com.yd.business.lottery.bean.ExpertResultAndFlowBean;
import com.yd.business.lottery.bean.ExpertResultBean;
import com.yd.business.lottery.bean.ExpertResultFlowBean;

/**
 * @author ice
 *
 */
public interface ILotterySportDao {

	List<ExpertResultBean> queryExpertResultInfo(ExpertResultBean bean);

	Integer updateExpertResultInfo(ExpertResultBean bean);

	Integer createExpertResultInfo(ExpertResultBean bean);

	List<ExpertBean> queryExpertInfo(ExpertBean bean);

	ExpertBean findExpertById(int id);

	List<ExpertResultFlowBean> queryExpertResultFlow(ExpertResultFlowBean bean);

	Integer createResultFlow(ExpertResultFlowBean bean);

	ExpertResultFlowBean findLastExpertResultFlowByExpertId(int expertid);

	ExpertResultFlowBean findExpertResultFlowById(int id);

	ExpertResultBean findLastJoinExpertResultByExpertId(int expertId);

	ExpertResultFlowBean findExpertResultFlowByResultId(int resultId);

	ExpertResultBean findExpertResultById(int id);

	Integer updateExpertResultFlow(ExpertResultFlowBean bean);

	List<ExpertResultAndFlowBean> queryExpertResultAndFlow(ExpertResultBean bean);

	Integer queryCashPool();

	void updateCashPool(int money);

}
