/**
 * 
 */
package com.yd.business.lottery.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.lottery.bean.ExpertBean;
import com.yd.business.lottery.bean.ExpertResultAndFlowBean;
import com.yd.business.lottery.bean.ExpertResultBean;
import com.yd.business.lottery.bean.ExpertResultFlowBean;
import com.yd.business.lottery.dao.ILotterySportDao;

/**
 * @author ice
 *
 */
@Repository("lotterySportDao")
public class LotterySportDaoImpl extends BaseDao implements ILotterySportDao {
	private static final String NAMESPACE = "lotterySport.";
	
	@Override
	public List<ExpertResultBean> queryExpertResultInfo(ExpertResultBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryExpertResultInfo", bean);
	}
	
	@Override
	public List<ExpertResultAndFlowBean> queryExpertResultAndFlow(ExpertResultBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryExpertResultAndFlow", bean);
	}
	
	@Override
	public Integer updateExpertResultInfo(ExpertResultBean bean){
		return sqlSessionTemplate.update(NAMESPACE +"updateExpertResultInfo", bean);
	}
	@Override
	public Integer createExpertResultInfo(ExpertResultBean bean){
		return sqlSessionTemplate.insert(NAMESPACE +"createExpertResultInfo", bean);
	}
	
	@Override
	public Integer createResultFlow(ExpertResultFlowBean bean){
		return sqlSessionTemplate.insert(NAMESPACE + "createResultFlow", bean);
	}
	
	@Override
	public List<ExpertBean> queryExpertInfo(ExpertBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryExpertInfo", bean);
	}
	
	@Override
	public ExpertBean findExpertById(int id){
		ExpertBean bean = new ExpertBean();
		bean.setId(id);
		List<ExpertBean> list = queryExpertInfo(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<ExpertResultFlowBean> queryExpertResultFlow(ExpertResultFlowBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"queryExpertResultFlow", bean);
	}

	@Override
	public ExpertResultFlowBean findExpertResultFlowById(int id){
		ExpertResultFlowBean bean = new ExpertResultFlowBean();
		bean.setId(id);
		List<ExpertResultFlowBean> list = queryExpertResultFlow(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public ExpertResultBean findExpertResultById(int id){
		ExpertResultBean bean = new ExpertResultBean();
		bean.setId(id);
		List<ExpertResultBean> list = queryExpertResultInfo(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public ExpertResultFlowBean findExpertResultFlowByResultId(int resultId){
		ExpertResultFlowBean bean = new ExpertResultFlowBean();
		bean.setExpert_result_id(resultId);
		List<ExpertResultFlowBean> list = queryExpertResultFlow(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public ExpertResultFlowBean findLastExpertResultFlowByExpertId(int expertid){
		ExpertResultFlowBean bean = new ExpertResultFlowBean();
		bean.setExpert_id(expertid);
		bean.setOrderby(" order by id desc limit 1");
		List<ExpertResultFlowBean> list = queryExpertResultFlow(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public ExpertResultBean findLastJoinExpertResultByExpertId(int expertId){
		ExpertResultBean bean = new ExpertResultBean();
		bean.setExpert_id(expertId);
		bean.setLottery_result("'中奖','未中'");
		bean.setOrderby(" order by id desc limit 1");
		List<ExpertResultBean> list = queryExpertResultInfo(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public Integer updateExpertResultFlow(ExpertResultFlowBean bean){
		return sqlSessionTemplate.update(NAMESPACE +"updateExpertResultFlow", bean);
	}
	
	@Override
	public Integer queryCashPool(){
		return (Integer)sqlSessionTemplate.selectOne(NAMESPACE +"queryCashPool");
	}
	
	@Override
	public void updateCashPool(int money){
		sqlSessionTemplate.update(NAMESPACE +"updateCashPool", money);
	}
	
	
}
