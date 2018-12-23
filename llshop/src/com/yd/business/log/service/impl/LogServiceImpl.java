package com.yd.business.log.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.log.bean.LogBean;
import com.yd.business.log.dao.ILogDao;
import com.yd.business.log.service.ILogService;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.bean.UserOperationLogBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
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
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
		if(user == null && StringUtil.isNotNull(openid)){
			user = new UserWechatBean();
			user.setOpenid(openid);
		}
		createUserOperationLog(user, action_type, action_value, action_param);
	}
	
	public UserOperationLogBean createUserOperationLog(UserWechatBean user,String action_type,String action_value,Object action_param ){
		String action_param_json = null;
		if(action_param != null){
			JSONObject jso = null;
			
			if(action_param instanceof Map ){
				jso = new JSONObject((Map) action_param);
			}else{
				jso = new JSONObject(action_param);
			}
			
			
			action_param_json = jso.toString();
		}
		
//		MsgCenterActionDefineBean condition = new MsgCenterActionDefineBean();
//		condition.setAction_type(action_type);
//		msgCenterActionDao.queryActionDefine(condition );
		
		MsgCenterUserActionBean bean = new MsgCenterUserActionBean();
		bean.setAction_type(action_type);
		bean.setAction_param(action_param_json);
		bean.setAction_param_class(action_param.getClass().getName());
		bean.setAction_value(action_value);
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setNick_name(user.getNick_name());
		bean.setOpenid(user.getOpenid());
		bean.setUser_id(user.getId());
		bean.setStatus(MsgCenterUserActionBean.STATUS_ENABLE);
		
		logDao.createUserOperationLog(bean);
		
		return bean;
	}
	
	@Override
	public int queryUserOperationLogCount(String openid,String action_type){
		
		UserOperationLogBean bean = new UserOperationLogBean();
		bean.setOpenid(openid);
		bean.setAction_type(action_type);
		
		return logDao.queryUserOperationLogCount(bean);
		
	}
	
}
