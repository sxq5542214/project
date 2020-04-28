/**
 * 
 */
package com.yd.business.lottery.dao;

import java.util.List;

import com.yd.business.lottery.bean.CqsscInfoBean;

/**
 * @author ice
 *
 */
public interface ILotterySSCDao {

	List<CqsscInfoBean> queryCqsscInfo(CqsscInfoBean bean);

	Integer createCqsscInfo(CqsscInfoBean bean);

}
