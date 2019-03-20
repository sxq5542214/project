package com.yd.business.supplier.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.domain.Area;
import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.log.service.ILogService;
import com.yd.business.order.bean.AreaData;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.DateUtil;
import com.yd.util.FileUploadUtil;
import com.yd.util.NumberUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author sxq
 *
 */
@Controller
public class SupplierEventController extends BaseController{
	public static final String PAGE_USER_SEE_EVENT_ACTIVITY_PAGE = "/page/user/activity/signActivity/supplierEventActivityList.jsp";
	@Autowired
	private ISupplierEventService supplierEventService;
	@Autowired
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private ILogService logService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	@Override
	protected void initServletContext(ServletContext servletContext) {
		// 保存文件的目录
		PATH_FOLDER = servletContext.getRealPath("/images/upload");
		// 存放临时文件的目录,存放xxx.tmp文件的目录
		TEMP_FOLDER = servletContext.getRealPath("/upload");
		// TODO Auto-generated method stub
		super.initServletContext(servletContext);
	}
	
	/**
	 * 文章查看
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("**/supplierEvent/readEvent.do")
	public ModelAndView readEvent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			Integer eventId = NumberUtil.toInt(request.getParameter("eventId"));
			String openid = request.getParameter("fromOpenid");
			String share_time_ms = request.getParameter("share_time_ms");
			Map<String, Object> map = new HashMap<String, Object>();
			
			SupplierEventBean bean = supplierEventService.queryByid(eventId);
			if(bean!=null){//当前查看一次，则将阅读次数+1
//				bean.setRead_num(NumberUtil.toInt(bean.getRead_num(), 0)+1);
				supplierEventService.updateEventReadNum(bean.getId(), 1);
			}
			if(openid != null){
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				
				userWechatService.readUserSenceLog(openid, null, share_time_ms);
				
				map.put("user", user);
				UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(openid, WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, eventId);
				map.put("ticket", ticket);
			}
			//writeJson(response, bean);
			map.put("bean", bean);
			return new ModelAndView("/page/pc/supplier-event-preview.jsp",map);
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
	@RequestMapping("/supplierEvent/find.do")
	public ModelAndView find(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer id = NumberUtil.toInt(request.getParameter("id"));
		SupplierArticleBean bean = supplierEventService.queryByid(id);
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
	@RequestMapping("**/admin/supplierEvent/list.do")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Integer status = NumberUtil.empty(request.getParameter("status"))?null:NumberUtil.toInt(request.getParameter("status"));
		Integer nowpage = NumberUtil.toInt(request.getParameter("nowpage"),1); 
		SupplierEventBean bean = new SupplierEventBean();
		bean.setTitle(title);
		bean.setContent(content);
		bean.setSupplier_id(1);
		bean.setStatus(status);
		bean.setOrderby(" order by id desc ");
		List<SupplierEventBean> list = supplierEventService.list(bean);
		
		Map<String,Object> map = new HashMap<String, Object>();
		PageinationData pd = new PageinationData().fenye(list, nowpage, Integer.MAX_VALUE);
		map.put("PageinationData", pd);
//		writeJson(response, pd);
		return new ModelAndView("/page/pc/supplier-event.jsp",map);
	}
	
	@RequestMapping("**/supplierEvent/queryCode.do")
	public ModelAndView queryCode(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String eventId = request.getParameter("eventId");
		String code = request.getParameter("code");
		SupplierEventCodeBean codeBean = supplierEventService.queryCode(Integer.parseInt(eventId), Integer.parseInt(code));
		if(codeBean != null)
		{
			writeJson(response, codeBean);
		}
		return null;
	}
	
	
	@RequestMapping("**/admin/supplierEvent/toUpdatePage.do")
	public ModelAndView toUpdatePage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id = request.getParameter("id");
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotNull(id)){
			SupplierEventBean bean = supplierEventService.queryByid(Integer.parseInt(id));
			map.put("bean", bean);
		}else{
			SupplierEventBean bean = new SupplierEventBean();
			bean.setJoinCount(0);
			bean.setEnd_time(DateUtil.getNowDateStr());
			bean.setLotteryDate(DateUtil.getNowDateStr());
			map.put("bean", bean);
		}
		return new ModelAndView("/page/pc/supplier-event-edit.jsp",map);
	}

	@RequestMapping("**/supplierEvent/userRead/toMyEventPage.do")
	public ModelAndView toMyEventPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		try{
			String eventId = request.getParameter("eventId");
			String openid = request.getParameter("openid");
			String share_time_ms = StringUtil.convertNull(request.getParameter("share_time_ms"));
			
			Integer eId = Integer.valueOf(eventId);
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			//公众号信息
			WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
//			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(openid, WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, eId);
			
			
//			int count = logService.queryUserOperationLogCount(user.getOpenid(), "/wechat/user/supplierEvent/userRead/toMyEventPage.do");
//			user.setTotalcount(count);
			
//			//查询活动码
//			List<SupplierEventCodeBean> codeList = supplierEventService.queryEventCode(eId, user.getId(),null);
//			
//			if(codeList.size() == 0 && user.getParentid() == null ){
//				//判断用户是好人还是坏人（有父用户就是好人，没有的话，如果是安徽的也是好人）,如果是坏人，直接送它一个号
//				supplierEventService.createEventCode(eId, user.getId(), user.getOpenid());
//				codeList = supplierEventService.queryEventCode(eId, user.getId(),null);
//			}
			
//			SupplierEventBean event = supplierEventService.queryByid(eId);
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("codeList", codeList);
//			map.put("user", user);
//			map.put("ticket", ticket);
//			map.put("event", event);
//			map.put("original", original);
			
			//转跳到另一个域名下
			String url = original.getServer_url()+"/"+ RandomUtil.randomString(3) +"supplierEvent"+ RandomUtil.randomString(3) +"/"+ RandomUtil.randomString(3) +"userRead"+ RandomUtil.randomString(3) +"/"+ RandomUtil.randomString(3) +"toMyEventPageWildcard"+ RandomUtil.randomString(3) +".do?eventId="+eventId+"&openid="+openid+"&share_time_ms="+share_time_ms;
			
			return new ModelAndView("redirect:" + url);
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "<script>alert('活动页面打开发生错误，请重新打开！');</script>");
		}
		return null;
	}
	
	/**
	 * 上面方法的另一种通配方法名
	 */
	@RequestMapping("**/**supplierEvent**/**userRead**/**toMyEventPageWildcard**.do")
	public ModelAndView toMyEventPageWildcard(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		try{
			String eventId = request.getParameter("eventId");
			String openid = request.getParameter("openid");
			String share_time_ms = request.getParameter("share_time_ms");
			
			Integer eId = Integer.valueOf(eventId);
			
			Map<String,Object> map = new HashMap<String, Object>();
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(openid, WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, eId);
			
			//公众号信息
			WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
			
//			int count = logService.queryUserOperationLogCount(user.getOpenid(), "/wechat/user/supplierEvent/userRead/toMyEventPage.do");
//			user.setTotalcount(count);
			
			//查询活动码
			List<SupplierEventCodeBean> codeList = supplierEventService.queryEventCode(eId, user.getId(),null);
			
			if(codeList.size() == 0 && user.getParentid() == null ){
				//判断用户是好人还是坏人（有父用户就是好人,如果是坏人，直接送它一个号
				supplierEventService.createEventCode(eId, user.getId(), user.getOpenid());
				codeList = supplierEventService.queryEventCode(eId, user.getId(),null);
			}
			
			
			
	//		int count = supplierEventService.queryEventCodeCount(eId);
			SupplierEventBean event = supplierEventService.queryByid(eId);
	//		event.setJoinCount(count);
			
			map.put("codeList", codeList);
			map.put("user", user);
			map.put("ticket", ticket);
			map.put("event", event);
			map.put("original", original);
			
			return new ModelAndView("/page/user/supplierEvent/mySupplierEventInfo.jsp",map);
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "<script>alert('活动页面打开发生错误，请重新打开！');</script>");
		}
		return null;
	}
	
	@RequestMapping("**/supplierEvent/userRead/toEventShare.do")
	public ModelAndView toEventShare(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String eventId = request.getParameter("eventId");
		String openId = request.getParameter("openId");
		SupplierEventBean event = supplierEventService.queryByid(Integer.parseInt(eventId));
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
		
		Integer userSenceLogId = userWechatService.createUserSenceLog(openId, WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, event.getId());
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("eventId", eventId);
		map.put("openId", openId);
		map.put("event", event);
		map.put("user", user);
		map.put("userSenceLogId", userSenceLogId);
		return new ModelAndView("/page/hongbao/supplierEventShare.jsp",map);
	}
	
	/**
	 * 新增和修改文章
	 */
	@RequestMapping(value="**/supplierEvent/update.do",method=RequestMethod.POST)
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
//		url = NumberUtil.empty(url)? serverUrl + "supplierEvent/readEvent.do?eventId="+id:url;
		//String img_url= request.getParameter("img_url");
		String media_id = request.getParameter("media_id");
		String wechat_server_id = request.getParameter("wechat_server_id");
		Integer status = NumberUtil.toInt(request.getParameter("status"));
		String lotteryInfo = request.getParameter("lotteryInfo");
		String lotteryDate = request.getParameter("lotteryDate");
		String lotteryNumber = request.getParameter("lotteryNumber");
		String lotteryPlace = request.getParameter("lotteryPlace");
		String joinCount = request.getParameter("joinCount");
		String searchName = request.getParameter("searchName");
		String end_time = request.getParameter("end_time");
		String title_attached = request.getParameter("title_attached");
		String prize_content = request.getParameter("prize_content");
		
		String createDate = DateUtil.getNowDateStr();
		SupplierEventBean bean = new SupplierEventBean();
		bean.setTitle(title);
		bean.setContent(content);
		bean.setSupplier_id(1);
		bean.setEnd_time(end_time);
		bean.setTitle_attached(title_attached);
		bean.setPrize_content(prize_content);
//		bean.setUrl(url);
		if(FileUploadUtil.isExists(request))bean.setImg_url(serverUrl + getImgPath("thumb_img", imageName));
		bean.setMedia_id(media_id);
		bean.setWechat_server_id(wechat_server_id);
		bean.setStatus(status);
		bean.setLotteryInfo(lotteryInfo);
		bean.setLotteryDate(lotteryDate);
		bean.setLotteryNumber(lotteryNumber);
		bean.setLotteryPlace(lotteryPlace);
		bean.setJoinCount(Integer.parseInt(joinCount));
		bean.setModify_time(createDate);
		bean.setSearchName(searchName);
		try{
			if(StringUtil.isNull(id)){
				bean.setCreate_time(createDate);
				bean.setRead_num(0);
				supplierEventService.insert(bean);
				url = StringUtil.isNull(url)?serverUrl + "supplierEvent/readEvent.do?eventId="+bean.getId():url;
				bean.setUrl(url);
			}else{
				bean.setId(Integer.parseInt(id));
			}
			supplierEventService.update(bean);
			FileUploadUtil.imgFileUpload(request, response,path,appendPath(PATH_FOLDER, "thumb_img"));
			//writeJson(response, "success");
			response.sendRedirect("admin/supplierEvent/list.do");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
//			writeJson(response, "error");
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
	@RequestMapping("/supplierEvent/fileUpload.do")
	public ModelAndView fileUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String img_url= getImgName();
		Integer id = NumberUtil.toInt(request.getParameter("id"));
		try {
			String path = getImgPath("thumb_img", img_url);
			SupplierEventBean bean = new SupplierEventBean();
			bean.setId(id);
			bean.setImg_url(path);
			supplierEventService.update(bean);
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
	@RequestMapping("/supplierEvent/delete.do")
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
		SupplierArticleBean bean = new SupplierArticleBean();
		bean.setId(id);
		supplierEventService.delete(bean);
	}
	/**
	 * 批量删除文章
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/supplierEvent/batDel.do")
	public ModelAndView batchDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] ids = request.getParameter("ids").split(",");
		List<SupplierArticleBean> list = new ArrayList<SupplierArticleBean>();
		for(int i=0;i<ids.length;i++){
			SupplierArticleBean bean = new SupplierArticleBean();
			bean.setId(NumberUtil.toInt(ids[i]));
			list.add(bean);
		}
		try{
			supplierEventService.batchDelete(list);
			writeJson(response, "1");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "0");
		}
		return null;
	}
	
	
	@RequestMapping("supplierEvent/ajax/querySupplierEventByAjax.do")
	public ModelAndView querySupplierEventByAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			
			List<SupplierEventBean> list = supplierEventService.queryList(SupplierEventBean.STATUS_ENABLE, 99999);
			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/supplierEvent/readLotteryEvent.do")
	public ModelAndView readLotteryEvent(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String fromOpenid = request.getParameter("fromOpenid");
//			String lotteryid = request.getParameter("lotteryid");
			String productid = request.getParameter("productid");
			String param = request.getParameter("param");
			String share_time_ms = request.getParameter("share_time_ms");
			

			UserWechatBean user = userWechatService.findUserWechatByOpenId(fromOpenid);
			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, WechatConstant.TICKET_SENCE_CODE_LOTTERY, Integer.parseInt(productid));
			WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(ticket.getOriginalid());
			
			userWechatService.readUserSenceLog(fromOpenid, null, share_time_ms);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("param", param);
			model.put("ticket", ticket);
			model.put("user", user);
			model.put("original", original);
			
			return new ModelAndView("/page/hongbao/displayLotteryWin.jsp", model );
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}
	
	@RequestMapping("**/supplierEvent/toSupplierEventActivityListPage.do")
	public ModelAndView toSupplierEventActivityListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			
			SupplierEventBean condition = new SupplierEventBean();
			condition.setType(SupplierEventBean.TYPE_USER_SEE_EVENT);
			condition.setStatus(SupplierEventBean.STATUS_ENABLE);
			condition.setOrderby(" order by media_id ");
			List<SupplierEventBean> nowEventList = supplierEventService.queryBeforEndTimeSupplierEvent(condition );
//			List<SupplierEventBean> futureEventList = supplierEventService.list(condition );
			List<SupplierEventBean> futureEventList = Collections.emptyList();
			condition.setOrderby(" order by id desc ");
			List<SupplierEventBean> historyEventList = supplierEventService.queryAfterEndTimeSupplierEvent(condition );
			
			WechatOriginalInfoBean originalinfo = BaseContext.getWechatOriginalInfo(user.getOriginalid());
			
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("originalinfo", originalinfo);
			model.put("nowEventList", nowEventList);
			model.put("futureEventList", futureEventList);
			model.put("historyEventList", historyEventList);
			
			
			return new ModelAndView(PAGE_USER_SEE_EVENT_ACTIVITY_PAGE, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
}
