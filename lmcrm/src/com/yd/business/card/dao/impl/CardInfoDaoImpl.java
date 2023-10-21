package com.yd.business.card.dao.impl;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.card.bean.CardInfoBean;
import com.yd.business.card.dao.ICardInfoDao;

@Repository("cardInfoDao")
public class CardInfoDaoImpl extends BaseDao implements ICardInfoDao {

	private final static String NAMESPACE = "card.";

	@Override
	public int insertWriteCardLogs(CardInfoBean bean) {
		
		return sqlSessionTemplate.insert(NAMESPACE + "insertWriteCardLogs", bean);
	}

	
	
}
