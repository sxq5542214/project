package com.yd.business.log.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.log.bean.LogBean;
import com.yd.business.log.dao.ILogDao;
import com.yd.business.log.service.ILogService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.NumberUtil;
/**
 * 日志记录
 * @author Anlins
 *
 */
@Service("logService")
public class LogServiceImpl extends BaseService implements ILogService {

	private static final String ISOPENLOG = "ISOPENLOG";
	@Resource
	private ILogDao logDao;
	@Resource
	private IUserWechatService userWechatService;
	
	
	public ILogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}
	@Autowired
	private IConfigAttributeService configAttributeService;
	

	private void insertLog(LogBean bean) {
		// TODO Auto-generated method stub
		boolean isOpenLog = NumberUtil.toBool(configAttributeService.getAttributeByCode(ISOPENLOG));//这里判断是否需要记录日志
		if(isOpenLog) logDao.insertLog(bean);
	}

	@Override
	public void createAdminLog(Integer type, Integer action, String content,
			String function) {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			LogBean bean = new LogBean();
			bean.setUserid(0);
			bean.setType(type);
			bean.setDate(sdf.parse(sdf.format(new Date())));
			bean.setAction(action);
			bean.setContent(content);
			bean.setFunction(function);
			bean.setSupplier_id(0);
			insertLog(bean);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e, e);
		}
	}
	
	/**
	 * 记录用户日志
	 */
	@Override
	public void createUserOperationLog(String openid,String action_type,String action_value,Object action_param ){
	}
	
	
	@Override
	public int queryUserOperationLogCount(String openid,String action_type){
		return 0;
		
		
	}
	
}
