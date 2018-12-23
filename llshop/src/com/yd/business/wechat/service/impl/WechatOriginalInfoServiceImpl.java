/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.dao.IWechatOriginalInfoDAO;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("wechatOriginalInfoService")
public class WechatOriginalInfoServiceImpl extends BaseService implements IWechatOriginalInfoService {
	
	@Autowired
	private IWechatOriginalInfoDAO wechatOriginalInfoDAO;

	
	/**
	 * 分页查询
	 */
	@Override
	public PageinationData queryWechatOriginalInfoPage(WechatOriginalInfoBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<WechatOriginalInfoBean> showList = wechatOriginalInfoDAO.queryWechatOriginalInfoPage(bean);
		    int count = wechatOriginalInfoDAO.wechatOriginalInfoCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;
		
	}

	/**
	 * 删除WechatOriginalInfo表信息
	 */
	@Override
	public void deteleWechatOriginalInfo(WechatOriginalInfoBean bean) {
		wechatOriginalInfoDAO.deteleWechatOriginalInfo(bean);
	}
	
	/**
	 * 编辑和插入WechatOriginalInfo表信息
	 */
	@Override
	public void editWechatOriginalInfo(WechatOriginalInfoBean bean) {
		try{
			if(StringUtil.isNull(bean.getId())){
				wechatOriginalInfoDAO.insertWechatOriginalInfo(bean);
			}else{
				bean.setModify_time(DateUtil.getNowDateStr());
				wechatOriginalInfoDAO.updateWechatOriginalInfo(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
	}

	@Override
	public List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean) {
		
		return wechatOriginalInfoDAO.queryWechatOriginalInfo(bean);
	}
			

}
