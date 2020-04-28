/**
 * 
 */
package com.yd.business.lottery.service;

import java.util.List;

import com.yd.business.lottery.bean.ExpertBean;
import com.yd.business.lottery.bean.ExpertResultAndFlowBean;
import com.yd.business.lottery.bean.ExpertResultBean;

/**
 * @author ice
 *
 */
public interface ILotterySportService {

	List<ExpertResultAndFlowBean> queryExpertResultInfo(ExpertResultBean bean);

	Integer createExpertResultInfo(ExpertResultBean bean);

	Integer updateExpertResultInfo(ExpertResultBean bean);

	List<ExpertBean> queryExpertInfo(ExpertBean bean);

	void handleExpertResult(ExpertResultBean bean);

	Integer calcCurrentJoinMoney(int expertId, int odds);

}
