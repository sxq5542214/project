/**
 * 
 */
package com.yd.business.supplier.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.comment.service.ICommentInfoService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierTopicBean;
import com.yd.business.supplier.service.ISupplierTopicService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.DateUtil;
import com.yd.util.FileUploadUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class SupplierTopicController extends BaseController {
	public static final String PAGE_USER_SEE_TOPIC_ACTIVITY_PAGE = "/page/user/activity/signActivity/supplierTopicActivityList.jsp";

	@Resource
	private ISupplierTopicService supplierTopicService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ICommentInfoService commentInfoService;
	
	
	/**
	 * 文章查看
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("**/supplierTopic/readTopic.do")
	public ModelAndView readTopic(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			Integer topicId = NumberUtil.toInt(request.getParameter("topicId"));
			String openid = request.getParameter("openid");
			String fromOpenid = request.getParameter("fromOpenid");
			if(StringUtil.isNull(openid)){
				openid = fromOpenid;
			}
			String share_time_ms = request.getParameter("share_time_ms");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fromOpenid", fromOpenid);
			SupplierTopicBean bean = supplierTopicService.findSupplierTopicById(topicId);
			if(bean!=null){//当前查看一次，则将阅读次数+1
//				bean.setRead_num(NumberUtil.toInt(bean.getRead_num(), 0)+1);
				supplierTopicService.updateEventReadNum(bean.getId(), 1);
			}
			if(StringUtil.isNotNull(openid)){
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				
				userWechatService.readUserSenceLog(openid, null, share_time_ms);
				
				map.put("user", user);
				UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(openid, WechatConstant.TICKET_SENCE_CODE_SUPPLIERTOPIC, topicId);
				map.put("ticket", ticket);
			}
			//writeJson(response, bean);
			map.put("bean", bean);
			return new ModelAndView("/page/pc/supplier-topic-preview.jsp",map);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 文章查看
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/supplierTopic/find.do")
	public ModelAndView find(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer id = NumberUtil.toInt(request.getParameter("id"));
		SupplierArticleBean bean = supplierTopicService.findSupplierTopicById(id);
		writeJson(response, bean);
		return null;
	}
	
	
	/**
	 * 获取商户广告列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("**/admin/supplierTopic/list.do")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Integer status = NumberUtil.empty(request.getParameter("status"))?null:NumberUtil.toInt(request.getParameter("status"));
		Integer nowpage = NumberUtil.toInt(request.getParameter("nowpage"),1); 
		SupplierTopicBean bean = new SupplierTopicBean();
		bean.setTitle(title);
		bean.setContent(content);
		bean.setSupplier_id(1);
		bean.setStatus(status);
		bean.setOrderby(" order by id desc");
		List<SupplierTopicBean> list = supplierTopicService.querySupplierTopic(bean);
		
		Map<String,Object> map = new HashMap<String, Object>();
		PageinationData pd = new PageinationData().fenye(list, nowpage, Integer.MAX_VALUE);
		map.put("PageinationData", pd);
//		writeJson(response, pd);
		return new ModelAndView("/page/pc/supplier-topic.jsp",map);
	}
	

	@RequestMapping("**/admin/supplierTopic/toUpdatePage.do")
	public ModelAndView toUpdatePage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id = request.getParameter("id");
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotNull(id)){
			SupplierTopicBean bean = supplierTopicService.findSupplierTopicById(Integer.parseInt(id));
			map.put("bean", bean);
		}else{
			SupplierTopicBean bean = new SupplierTopicBean();
			bean.setJoinCount(0);
			
			String date = DateUtil.getNowDateStr();
			bean.setCreate_time(date);
			bean.setEnd_time(date);
			map.put("bean", bean);
		}
		return new ModelAndView("/page/pc/supplier-topic-edit.jsp",map);
	}
	

	/**
	 * 新增和修改文章
	 */
	@RequestMapping(value="**/supplierTopic/update.do",method=RequestMethod.POST)
	public ModelAndView update(HttpServletRequest req,HttpServletResponse response,ModelMap model) throws Exception{
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) req;
		
		String serverUrl = BaseContext.getServerUrl();
		
		String imageName = getImgName();
		String path = appendPath(PATH_FOLDER, "thumb_img")+"\\"+imageName;
		//imgFileUpload(multipartHttp, response);
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String url = request.getParameter("url");
		String descrip = request.getParameter("descrip");
//		url = NumberUtil.empty(url)? serverUrl + "supplierEvent/readEvent.do?eventId="+id:url;
		//String img_url= request.getParameter("img_url");
		Integer status = NumberUtil.toInt(request.getParameter("status"));
		String joinCount = request.getParameter("joinCount");
		String searchName = request.getParameter("searchName");
		String end_time = request.getParameter("end_time");
		String create_time = request.getParameter("create_time");
		String title_attached = request.getParameter("title_attached");
		String prize_content = request.getParameter("prize_content");
		
		
		String createDate = DateUtil.getNowDateStr();
		SupplierTopicBean bean = new SupplierTopicBean();
		bean.setTitle(title);
		bean.setContent(content);
		bean.setSupplier_id(CustomerBean.ID_PLATFROM);
		bean.setEnd_time(end_time);
		bean.setTitle_attached(title_attached);
		bean.setPrize_content(prize_content);
		bean.setCreate_time(create_time);
		bean.setDescrip(descrip);
//		bean.setUrl(url);
		if(FileUploadUtil.isExists(request))bean.setImg_url(serverUrl + getImgPath("thumb_img", imageName));
		bean.setStatus(status);
		bean.setJoinCount(Integer.parseInt(joinCount));
		bean.setModify_time(createDate);
		bean.setSearchName(searchName);
		try{
			if(StringUtil.isNull(id)){
				bean.setCreate_time(createDate);
				bean.setRead_num(0);
				String comment_code = "topic_" + create_time;
				bean.setComment_code(comment_code );
				supplierTopicService.createSupplierTopic(bean);
				url = StringUtil.isNull(url)? "#server_url#supplierTopic/readTopic.do?topicId="+bean.getId()+"&openid=#action_openid#":url;
				bean.setUrl(url);
				commentInfoService.initCommentConfig(comment_code, bean.getCreate_time(), bean.getEnd_time(),bean.getUrl() ,bean.getTitle());
				
			}else{
				bean.setId(Integer.parseInt(id));
			}
			supplierTopicService.updateSupplierTopic(bean);
			FileUploadUtil.imgFileUpload(request, response,path,appendPath(PATH_FOLDER, "thumb_img"));
			//writeJson(response, "success");
			response.sendRedirect("admin/supplierTopic/list.do");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
//			writeJson(response, "error");
		}
		return null;
	}
	
	
	@RequestMapping("supplierTopic/ajax/querySupplierTopicByAjax.do")
	public ModelAndView querySupplierEventByAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			
			SupplierTopicBean bean = new SupplierTopicBean();
			bean.setStatus(SupplierTopicBean.STATUS_ENABLE);
			bean.setOrderby(" order by id desc ");
			List<SupplierTopicBean> list = supplierTopicService.querySupplierTopic(bean );
			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/supplierTopic/fileUpload.do")
	public ModelAndView fileUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String img_url= getImgName();
		Integer id = NumberUtil.toInt(request.getParameter("id"));
		try {
			String path = getImgPath("thumb_img", img_url);
			SupplierTopicBean bean = new SupplierTopicBean();
			bean.setId(id);
			bean.setImg_url(path);
			supplierTopicService.updateSupplierTopic(bean);
			upload(request, response, appendPath(PATH_FOLDER, "thumb_img"), img_url);
			//writeJson(response, path);
			response.getWriter().write(path);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "error");
		}
		return null;
	}
	/**
	 * 删除文章
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/supplierTopic/delete.do")
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer id = NumberUtil.toInt(request.getParameter("id"));
		try{
			delete(id);
			writeJson(response, "1");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "0");
		}
		return null;
	}
	//删除文章
	public void delete(int id){
		SupplierTopicBean bean = new SupplierTopicBean();
		bean.setId(id);
		supplierTopicService.deleteSupplierTopic(bean);
	}
	

	@RequestMapping("**/supplierTopic/toSupplierTopicActivityListPage.do")
	public ModelAndView toSupplierTopicActivityListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			
			SupplierTopicBean condition = new SupplierTopicBean();
			condition.setStatus(SupplierEventBean.STATUS_ENABLE);
			condition.setOrderby(" order by read_num ");
			condition.setEnd_time(DateUtil.getNowDateStr());
			List<SupplierTopicBean> nowTopicList = supplierTopicService.queryBeforEndTimeSupplierTopic(condition );
			
			condition.setEnd_time(null);
			condition.setCreate_time(DateUtil.getNowDateStr());
			List<SupplierTopicBean> futureTopicList = supplierTopicService.queryBeforEndTimeSupplierTopic(condition );
//			List<SupplierTopicBean> futureTopicList = Collections.emptyList();
			condition.setOrderby(" order by id desc ");
			

			condition.setEnd_time(DateUtil.getNowDateStr());
			condition.setCreate_time(null);
			List<SupplierTopicBean> historyTopicList = supplierTopicService.queryAfterEndTimeSupplierTopic(condition );
			
			WechatOriginalInfoBean originalinfo = BaseContext.getWechatOriginalInfo(user.getOriginalid());
			
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("originalinfo", originalinfo);
			model.put("nowTopicList", nowTopicList);
			model.put("futureTopicList", futureTopicList);
			model.put("historyTopicList", historyTopicList);
			
			
			return new ModelAndView(PAGE_USER_SEE_TOPIC_ACTIVITY_PAGE, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	
	
	
}
