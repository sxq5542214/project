/**
 * 
 */
package com.yd.business.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author zxz
 *
 */
@Controller
public class UserWechatController extends BaseController {
	
	@Autowired
	private IUserWechatService userWechatService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	/**
	 * 用户管理,查询功能
	 */
	@RequestMapping("/admin/user/userWechatController/queryAdmimWechatUser.do")
	public ModelAndView queryAdmimWechatUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			UserWechatBean bean = new UserWechatBean();
			String table_wechatuser_nick_name =  request.getParameter("query_wechatuser_nick_name");	//微信名称
			String query_wechatuser_city =  request.getParameter("query_wechatuser_city");				//城市
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(table_wechatuser_nick_name)){
				bean.setNick_name(table_wechatuser_nick_name);
			}
			if(!StringUtil.isNull(query_wechatuser_city)){
				bean.setCity(query_wechatuser_city);
			}
			PageinationData pd =  userWechatService.queryWechatUserPage(bean);
			WechatOriginalInfoBean wechatOriginalInfoBean = new WechatOriginalInfoBean();
			List<WechatOriginalInfoBean> wechatOriginalInfoList =   wechatOriginalInfoService.queryWechatOriginalInfo(wechatOriginalInfoBean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("pd", pd);
			map.put("wechatOriginalInfoList", wechatOriginalInfoList);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/userWechat/iframe_user_wechat.jsp", map);
	}
	
	
	
	/**
	 * 用户管理删除 	根据id删除ll_wechat_user表信息
	 */
	@RequestMapping("/admin/user/userWechatController/deleteWechatUser.do")
	public ModelAndView deleteWechatUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			UserWechatBean bean = new UserWechatBean();
			bean.setId(Integer.parseInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				userWechatService.deteleWechatUser (bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}

	
	/**
	 *  增加和编辑字典值表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/user/userWechatController/editWechatUser.do")
	public ModelAndView editWechatUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		UserWechatBean bean =  new UserWechatBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			bean.setOpenid(request.getParameter("openid"));
			bean.setNick_name(request.getParameter("nick_name"));
			String phone = request.getParameter("phone");
			if(!StringUtil.isNull(phone)){
				bean.setPhone(phone);
			}
			bean.setSex((request.getParameter("sex")));
			bean.setProvince(request.getParameter("province"));
			bean.setCity(request.getParameter("city"));
			bean.setStatus(Integer.parseInt(request.getParameter("status")));
			String parentid = request.getParameter("parentid");
			if(!StringUtil.isNull(parentid)){
				bean.setParentid(Integer.parseInt(parentid));
			}
			String level = request.getParameter("level");
			if(!StringUtil.isNull(level)){
				bean.setLevel(Integer.parseInt(level));
			}
			bean.setOffline_num(Integer.parseInt(request.getParameter("offline_num")));
			bean.setHead_img(request.getParameter("head_img"));
			bean.setPoints(Integer.parseInt(request.getParameter("points")));
			bean.setBalance(Integer.parseInt(request.getParameter("balance")));
			String share_type = request.getParameter("share_type");
			if(!StringUtil.isNull(share_type)){
				bean.setShare_type(Integer.parseInt(share_type));
			}
			bean.setOriginalid( request.getParameter("originalid"));
			String type = request.getParameter("type");
			if(!StringUtil.isNull(type)){
				bean.setType(Integer.parseInt(type));
			}
			userWechatService.editWechatUser(bean);
			bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
}
