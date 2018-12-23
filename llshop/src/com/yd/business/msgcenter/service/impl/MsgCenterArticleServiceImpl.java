/**
 * 
 */
package com.yd.business.msgcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.bean.MsgCenterUserSubscribeBean;
import com.yd.business.msgcenter.dao.IMsgCenterArticleDao;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;

/**
 * @author ice
 *
 */
@Service("msgCenterArticleService")
public class MsgCenterArticleServiceImpl extends BaseService implements IMsgCenterArticleService {
	
	@Resource
	private IMsgCenterArticleDao msgCenterArticleDao;
	@Resource
	private IWechatService wechatService;
	
	/**
	 * 通过ID查找文章
	 * @param id
	 * @return
	 */
	@Override
	public MsgCenterArticleBean findArticleById(int id){
		MsgCenterArticleBean bean = new MsgCenterArticleBean();
		bean.setId(id);
		bean = findArticle(bean);
		return bean;
	}
	
	/**
	 * 通过id得到文章信息，有素材分发记录的同时带出
	 * @param id
	 * @return
	 */
	@Override
	public MsgCenterArticleBean findArticleWithDeliveryInf(int id){
		MsgCenterArticleBean bean = new MsgCenterArticleBean();
		bean.setId(id);
		bean = findArticle(bean);
		if(bean.getSend_type().compareTo(MsgCenterArticleBean.SEND_TYPE_WECHAT_NEWS_ALL) == 0){
			//得到素材的分发记录
			WechatMaterialBean material = new WechatMaterialBean();
			material.setSucai_media_id(bean.getMaterial_code());
			WechatMaterialRelationBean relBean = new WechatMaterialRelationBean();
			List<WechatMaterialRelationBean> beanList = wechatService.queryWechatMaterialRelationByBean(material,relBean,null);
			List<String> delivery_original_names = new ArrayList<String>();
			for (WechatMaterialRelationBean wechatMaterialRelationBean : beanList) {
				WechatOriginalInfoBean original = wechatService.findWechatOriginalInfoByOriginalid(wechatMaterialRelationBean.getOriginalid());
				delivery_original_names.add(original.getOriginal_name());
			}
			if(delivery_original_names.size() > 0){
				bean.setDelivery_original_name(StringUtils.join(delivery_original_names.toArray(),"],["));
			}
		}
		return bean;
	}
	
	/**
	 * 查找article
	 */
	@Override
	public MsgCenterArticleBean findArticle(MsgCenterArticleBean bean){
		
		return msgCenterArticleDao.findArticle(bean);
	}
	
	/**
	 * 查询article
	 */
	@Override
	public List<MsgCenterArticleBean> queryArticle(MsgCenterArticleBean bean){
		
		return msgCenterArticleDao.queryArticle(bean);
	}
	
	/**
	 * 通过文章类型，查找出一条适合用户动作的文章(一个类型下可能会有多个文章)
	 * @param article_type_id
	 * @param action
	 * @return
	 */
	@Override
	public List<MsgCenterArticleBean> findArticleByType(int article_type_id,MsgCenterUserActionBean action){
		
		MsgCenterArticleTypeBean type = new MsgCenterArticleTypeBean();
		type.setId(article_type_id);
		type = msgCenterArticleDao.findArticleType(type );
		
		int limit = type.getNum();
		String orderby = type.getOrderby();
		int repeat = type.getRepeat();

		MsgCenterArticleConditionBean articleCondition = new MsgCenterArticleConditionBean();

		articleCondition.setStatus(MsgCenterArticleConditionBean.STATUS_ENABLE);
		articleCondition.setType(article_type_id);
		articleCondition.setUser_id(action.getUser_id());
		articleCondition.setOrderby(orderby);
		articleCondition.setPageSize(limit);
		articleCondition.setNow_time(DateUtil.getNowDateStr());

		articleCondition.setNot_repeat(true);
//		if(repeat == MsgCenterArticleTypeBean.REPEAT_NO){
//		}
		List<MsgCenterArticleBean> list = msgCenterArticleDao.queryArticle(articleCondition );
		
		//如果不重复的没有数据，且这个分类是可以重复的，那就再查询一次
		if(list.size() == 0 && repeat == MsgCenterArticleTypeBean.REPEAT_YES){
			articleCondition.setNot_repeat(null);
			articleCondition.setUser_id(null);
			articleCondition.setPageSize(Integer.MAX_VALUE);
			List<MsgCenterArticleBean> allList = msgCenterArticleDao.queryArticle(articleCondition );
			//随机取数据
			if(allList.size()>0){
				
				for(int i = 0 ; i < limit ; i++){
					int index = RandomUtil.nextInt(allList.size());
					list.add(allList.get(index));
				}
			}
			
		}
		
		return list;
		
	}
	
	@Override
	public List<MsgCenterArticleTypeBean> queryArticleType(MsgCenterArticleTypeBean bean){
		List<MsgCenterArticleTypeBean> articleList = msgCenterArticleDao.queryArticleType(bean);
		return articleList;
	}
	
	@Override
	public void saveOrUpdateArticle(MsgCenterArticleBean bean){
		
		MsgCenterArticleTypeBean type = new MsgCenterArticleTypeBean();
		type.setId(bean.getType());
		type = msgCenterArticleDao.findArticleType(type );
		
		bean.setType_name(type.getArcticle_type_name());
		if(bean.getId() == null){
			msgCenterArticleDao.createArticle(bean);
		}else{
			msgCenterArticleDao.updateArticle(bean);
		}
		
	}
	
	@Override
	public List<MsgCenterArticleBean> queryArticle(MsgCenterArticleConditionBean bean){
		return msgCenterArticleDao.queryArticle(bean);
	}
	
	@Override
	public void createOrUpdateUserSubscribeInfo(Integer user_id, String openid, 
			Integer status, String code, String type, Integer article_id){
		MsgCenterUserSubscribeBean bean = new MsgCenterUserSubscribeBean();
		bean.setArticle_id(article_id);
		bean.setCode(code);
		bean.setCreate_time(DateUtil.getNowDateStr());
		bean.setModify_time(DateUtil.getNowDateStr());
		bean.setOpenid(openid);
		bean.setType(type);
		bean.setUser_id(user_id);
		
		List<MsgCenterUserSubscribeBean> list = queryUserSubscribeInfo(bean);
		bean.setStatus(status);
		
		if(list.size() > 0){
			updateUserSubscribeInfo(bean);
		}else{
			createUserSubscribeInfo(bean);
		}
		
		
	}
	
	private void createUserSubscribeInfo(MsgCenterUserSubscribeBean bean){
		
		msgCenterArticleDao.createUserSubscribeInfo(bean);
	}
	
	private void updateUserSubscribeInfo(MsgCenterUserSubscribeBean bean){
		
		msgCenterArticleDao.updateUserSubscribeInfo(bean);
	}
	
	@Override
	public List<MsgCenterUserSubscribeBean> queryUserSubscribeInfo(MsgCenterUserSubscribeBean bean){
		return msgCenterArticleDao.queryUserSubscribeInfo(bean);
	}
	
	
}
