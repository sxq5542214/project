/**
 * 
 */
package com.yd.business.wechat.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
			List<WechatOriginalInfoBean> showList = wechatOriginalInfoDAO.queryWechatOriginalInfo(bean);
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
	
	/**
	 * 查询第一条记录
	 * @param id
	 * @return
	 */
	@Override
	public WechatOriginalInfoBean queryFirstWechatOriginalInfo(){
		List<WechatOriginalInfoBean> list = queryWechatOriginalInfo(null);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	@Override
	public WechatOriginalInfoBean findWechatOriginalInfoByOriginalid(String originalid){
		WechatOriginalInfoBean bean = new WechatOriginalInfoBean();
		bean.setOriginalid(originalid);
		List<WechatOriginalInfoBean> list = queryWechatOriginalInfo(bean);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public WechatOriginalInfoBean findWechatOriginalInfoByServer(String server_domain){
		WechatOriginalInfoBean bean = new WechatOriginalInfoBean();
		bean.setServer_domain(server_domain);
		List<WechatOriginalInfoBean> list = queryWechatOriginalInfo(bean);
		if(list.size()>0){
			return list.get(0);
		}else{
			//如果没有查到，再按serverurl去查
			bean.setServer_domain(null);
			bean.setServer_url(server_domain);

			list = queryWechatOriginalInfo(bean);
			if(list.size()>0){
				return list.get(0);
			}
			
		}
		return null;
	}
	
	

	/**
	 * 通过服务域名换取公众号的原始ID
	 * @param request
	 * @return
	 */
	@Override
	public String getOriginalidByServerDomain(HttpServletRequest request) {
		String serverName = request.getServerName();
		System.out.println("serverName:"+serverName);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByServer(serverName);
		if(originalInfo == null){
			queryFirstWechatOriginalInfo();
		}
		
		return originalInfo.getOriginalid();
	}
	/**
	 * 通过服务域名换取公众号的原始ID
	 * @param request
	 * @return
	 */
	@Override
	public WechatOriginalInfoBean getOriginalInfoByServerDomain(HttpServletRequest request) {
		String serverName = request.getServerName();
		System.out.println("serverName:"+serverName);
		WechatOriginalInfoBean originalInfo = findWechatOriginalInfoByServer(serverName);
		return originalInfo;
	}
	
}
