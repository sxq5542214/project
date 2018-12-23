/**
 * 
 */
package com.yd.business.lottery.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.lottery.bean.CqsscInfoBean;
import com.yd.business.lottery.dao.ILotterySSCDao;
import com.yd.util.BeanUtil;
import com.yd.util.JsonUtil;

/**
 * @author ice
 *
 */
@Repository("lotterySSCDao")
public class LotterySSCDaoImpl extends BaseDao implements ILotterySSCDao {
	private static final String NAMESPACE = "lotterySSC.";
	
	@Override
	public List<CqsscInfoBean> queryCqsscInfo(CqsscInfoBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE + "queryCqsscInfo", bean);
	}
	
	@Override
	public Integer createCqsscInfo(CqsscInfoBean bean){
		return sqlSessionTemplate.insert(NAMESPACE + "createCqsscInfo", bean);
	}
	
	
}
