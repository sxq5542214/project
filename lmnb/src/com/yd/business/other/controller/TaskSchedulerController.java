package com.yd.business.other.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.bean.TaskCronsBean;
import com.yd.business.other.service.IAdvertisingService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.other.service.ITaskSchedulerService;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * @author zxz
 *
 */
@Controller
public class TaskSchedulerController extends BaseController {
	@Resource
	private ITaskSchedulerService taskSchedulerService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IConfigCruxService configCruxService;
	
	/**
	 * 后台定时任务管理,查询功能
	 */
	@RequestMapping("/admin/other/taskSchedulerController/queryTaskCrons.do")
	public ModelAndView queryTaskCrons(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
			TaskCronsBean bean = new TaskCronsBean();
			String nowpage = request.getParameter("nowpage");
			String class_name = request.getParameter("query_crons_class_name");
			String cron_name = request.getParameter("query_crons_cron_name");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(class_name)){
				bean.setClass_name(class_name);
			}
			if(!StringUtil.isNull(cron_name)){
				bean.setCron_name(cron_name);
			}
			PageinationData pd =  taskSchedulerService.queryTaskCronsPage(bean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/taskScheduler/iframe_task_scheduler.jsp", map);
	}
	
	
	
	
	
	
	/**
	 * 轮播信息内容删除 	根据id删除advertising表信息
	 */
	@RequestMapping("/admin/other/taskSchedulerController/deleteTaskCron.do")
	public ModelAndView deleteTaskCron(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			TaskCronsBean bean = new TaskCronsBean();
			bean.setId(Integer.parseInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				taskSchedulerService.deleteTaskCron(bean);
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
	 *  增加和编辑轮播图片信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/other/taskSchedulerController/editTaskCron.do")
	public ModelAndView editTaskCron(HttpServletRequest request,HttpServletResponse response) throws IOException{
		TaskCronsBean bean =  new TaskCronsBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			bean.setCron_code(request.getParameter("code"));
			bean.setCron_name(request.getParameter("name"));
			bean.setCron_desc(request.getParameter("desc"));
			bean.setClass_name(request.getParameter("class_name"));
			bean.setExpression(request.getParameter("expression"));
			bean.setStatus(Integer.parseInt(request.getParameter("status")));
			bean.setDescription(request.getParameter("description"));
			bean.setEnable(Integer.parseInt(request.getParameter("enable")));
			taskSchedulerService.editTaskCron(bean);
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
