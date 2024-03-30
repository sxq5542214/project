package com.yd.business.client.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.client.QingSongInterfaceClient;
import com.yd.business.client.bean.QingSongInterfaceBean.MeterCMD;
import com.yd.business.client.service.IIOTInterfaceService;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.other.service.ICommandService;
import com.yd.util.HttpUtil;
import com.yd.util.JsonUtil;

@Controller
public class IOTInterfaceController extends BaseController {
	@Resource
	private IIOTInterfaceService iotInterfaceService;
	@Resource
	private ICommandService commandService;
	@Resource
	private IDeviceInfoService deviceInfoService;


	@RequestMapping("**/client/nbApi/buildStation")
	public ModelAndView qingsongBuildStation(HttpServletRequest request,HttpServletResponse response){

		try {
			String jsonStr = HttpUtil.readRequestString(request);
			JSONObject jso = new JSONObject(jsonStr);
			String stationcode = jso.getString("stationcode");
			
			iotInterfaceService.saveQingSongStationCode(stationcode);
			
			writeJson(response, "OK" );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("**/client/nbApi/postMeterCmd")
	public ModelAndView qingsongPostMeterCmd(HttpServletRequest request,HttpServletResponse response){

		try {
			
			String jsonStr = HttpUtil.readRequestString(request);
log.info("client/nbApi/postMeterCmd: "+ jsonStr);
			List<MeterCMD> list = QingSongInterfaceClient.convertPostMeterCMDResult(jsonStr);
			
			
			for(int i = 0 ; i < list.size() ; i ++) {
				MeterCMD cmd = list.get(i);
				Integer cmdid = Integer.parseInt(cmd.getOuterid());
				Byte state = Byte.parseByte(cmd.getState());
				
				//更新cmd 状态
				commandService.updateCmdStatus(cmdid, state, JsonUtil.convertObjectToJsonString(cmd));
				
				//更新水表的阀门状态
				MeterModelExtendsBean meter = deviceInfoService.findMeterByIspid(cmd.getIspid());
				meter.setValvestate(Byte.valueOf(cmd.getType()));
				meter.setRecentcmdtime(new Date());
				meter.setRecentcmdtype(Byte.valueOf(cmd.getType()));
				meter.setRecentcmdstate(Byte.valueOf(cmd.getState()));
				deviceInfoService.addOrUpdateMeter(meter);
			}
			
			
			writeJson(response, 0 );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("**/client/nbApi/postMeterReading")
	public ModelAndView qingsongPostMeterReading(HttpServletRequest request,HttpServletResponse response){

		try {
			String jsonStr = HttpUtil.readRequestString(request);

log.info("client/nbApi/postMeterReading: "+ jsonStr);
			int result = iotInterfaceService.handlerQingSongPostMeterReading(jsonStr);
			
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, -1 );
		}
		return null;
	}
}
