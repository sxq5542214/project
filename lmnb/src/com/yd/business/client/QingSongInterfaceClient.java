/**
 *
 */
package com.yd.business.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yd.basic.framework.client.BaseCMDClient;
import com.yd.basic.framework.context.BaseContext;
import com.yd.business.client.bean.QingSongInterfaceBean;
import com.yd.business.client.bean.QingSongInterfaceBean.Conter;
import com.yd.business.client.bean.QingSongInterfaceBean.DeviceDto;
import com.yd.business.client.bean.QingSongInterfaceBean.MeterCMD;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.util.HttpUtil;
import com.yd.util.MD5Util;

/**
 *
 */
public class QingSongInterfaceClient extends BaseCMDClient{
	static Logger log = Logger.getLogger(QingSongInterfaceClient.class);
	public static String STATIONCODE = "538391"; // 测试
	public static String JOIN_FLAG = "QING_SONG"; // 测试
	public static String QINGSONG_URL_PREFIX = "http://nb.sdqsbj.com:80/"; //测试
	public static String URL_SUFIX_SENDCMD = "api/third/open/thirdFactory/v1/sendCmd";
	public static String URL_SUFIX_GETCMD_RESULT = "api/third/open/thirdFactory/v1/getCmd";
	public static String URL_SUFIX_CHECKDEVICEINFO = "api/third/open/thirdFactory/v1/checkDeviceInfo";
//	public static IConfigAttributeService configAttributeService =(IConfigAttributeService)BaseContext.getBeanOfType(IConfigAttributeService.class);

	private static String getURLPrefix() {
//		return configAttributeService.getValueByCode(AttributeConstant.CODE_IOT_CLIENT_QINGSONG_URLPREFIX);
		return QINGSONG_URL_PREFIX;
	}
	public static QingSongInterfaceBean sendCmd(QingSongInterfaceBean bean ) {
		QingSongInterfaceBean ret = new QingSongInterfaceBean();
		try {
			ArrayList<QingSongInterfaceBean> listBean = new ArrayList<QingSongInterfaceBean>();
			String url = getURLPrefix() + URL_SUFIX_SENDCMD;

			List<Header> list = initPostHeader();


			bean.setJoinFlag(null);
			bean.setStationcode(STATIONCODE);
			listBean.add(bean);
			JSONArray jso = new JSONArray(listBean);
			String result = HttpUtil.postJSON(url, jso.toString(), list);
			
			JSONObject resJson = new JSONObject(result);

			int code = resJson.getInt("code");
			ret.setCode(String.valueOf(code));
			if(code == QingSongInterfaceBean.code_success) {
				ret.setMsg("操作提交成功");
				return ret;
			}else {
				ret.setMsg(resJson.getString("msg"));
				log.error(resJson.getString("msg"));
				return ret;
			}

		} catch (IOException e) {
			log.error(e, e);
			ret.setCode("-1");
			ret.setMsg(e.getMessage());
			return ret;
		}

	}



	public static DeviceDto checkDeviceInfo(String code, String stationCode) {

		try {
			ArrayList<QingSongInterfaceBean> listBean = new ArrayList<QingSongInterfaceBean>();
			QingSongInterfaceBean bean = new QingSongInterfaceBean();
			bean.setCode(code);
			bean.setStationcode(STATIONCODE);
			
			listBean.add(bean);
			JSONArray jso = new JSONArray(listBean);

			String url = getURLPrefix() + URL_SUFIX_CHECKDEVICEINFO;
			List<Header> headers = initPostHeader();
			String result = HttpUtil.postJSON(url, jso.toString(), headers);

			JSONObject resJson = new JSONObject(result);
			log.info(result);

			int resCode = resJson.getInt("code");
			if(resCode == QingSongInterfaceBean.code_success) {

				JSONArray resDataList = resJson.getJSONArray("data");
				if(resDataList.length() == 0) {
					return null;
				}
				JSONObject resData = resDataList.getJSONObject(0);
				DeviceDto dev = bean.new DeviceDto();
				dev.setBattery(resData.getString("battery"));
				dev.setCode(resData.getString("code"));
				dev.setIsp(resData.getString("isp"));
				dev.setIspid(resData.getString("ispid"));
				dev.setValvestate(resData.getString("valvestate"));
				dev.setSignalstrength(resData.getString("signalstrength"));
				dev.setReadperiod(resData.getString("readperiod"));
//				dev.setReversenum(resData.getString("reversenum"));
				dev.setImei(resData.getString("imei"));
				dev.setSim(resData.getString("sim"));
				dev.setCurnum(resData.getString("curnum"));
				dev.setSensor(resData.getString("sensor"));
				
				return dev;
			}else {
				log.error(resJson.getString("msg"));
				return null;
			}

		} catch (IOException e) {
			log.error(e, e);
			return null;
		}

	}

	private static List<Header> initPostHeader(){

		String curTimes = String.valueOf(System.currentTimeMillis());
//		String curTimes = "1693895555477" ;
		Header times = new BasicHeader("times", curTimes);
		Header stationcode = new BasicHeader("stationcode", STATIONCODE);
		String secretStr =  curTimes+ JOIN_FLAG;
		String md5secret = MD5Util.md5_32(secretStr);
		Header secret = new BasicHeader("secret", md5secret.toLowerCase());

//		System.out.println("times:" + curTimes);
//		System.out.println("stationcode:" + STATIONCODE);
//		System.out.println("joinFlag:" + JOIN_FLAG);
//		System.out.println("secretPreMd5:" + secretStr);
//		System.out.println("secretMd5:" + md5secret);
		
		List<Header> list = new ArrayList<>();
		list.add(secret);
		list.add(stationcode);
		list.add(times);
		return list;
	}

	public static List<QingSongInterfaceBean> convertPostMeterReadingResult(String str) {
		List<QingSongInterfaceBean> list = new ArrayList<QingSongInterfaceBean>();
		JSONArray array = new JSONArray(str);
		
		for(int i = 0 ; i < array.length() ; i ++) {
			QingSongInterfaceBean bean = new QingSongInterfaceBean();
			
			JSONObject jso = array.getJSONObject(i);
			bean.setIspid(jso.getString("ispid"));
			bean.setCurtime(jso.getString("curtime"));
//			bean.setStationcode(jso.getString("stationcode"));

			Conter conter = bean.new Conter();
			JSONObject con = jso.getJSONObject("conter");
			conter.setCurnum(con.getString("curnum"));
			conter.setValvestate(con.getString("valvestate"));
			conter.setBattery(con.getString("battery"));
			conter.setSensor(con.getString("sensor"));
			conter.setSignalstrength(con.getString("signal"));
			conter.setReadperiod(con.getString("readperiod"));
			conter.setReadstate(con.getString("readstate"));
//			conter.setReversenum(con.getString("reversenum"));

			bean.setConter(conter);
			list.add(bean);
		}
		return list;
	}


	public static List<MeterCMD> convertPostMeterCMDResult(String str) {
		List<MeterCMD> list = new ArrayList<MeterCMD>();
		JSONArray array = new JSONArray(str);
		
		for(int i = 0 ; i < array.length() ; i ++) {
			MeterCMD bean = new MeterCMD();
			
			JSONObject jso = array.getJSONObject(i);
			bean.setIspid(jso.getString("ispid"));
			bean.setType(jso.getString("type"));
			bean.setId(jso.getString("id"));
			bean.setOuterid(jso.getString("outerid"));
			bean.setFinishtime(jso.getString("finishtime"));
			bean.setState(jso.getString("state"));
			bean.setCreatetime(jso.getString("createtime"));
			bean.setSendtime(jso.getString("sendtime"));

			list.add(bean);
		}
		return list;
	}


	public MeterCMD getCMDResult(String cmdid,String ispid,String createTime, String stationCode) {

		try {

			QingSongInterfaceBean bean = new QingSongInterfaceBean();
			bean.setIspid(ispid);
			bean.setCreatetime(createTime);
			bean.setId(cmdid);

			String url = getURLPrefix() + URL_SUFIX_GETCMD_RESULT;

			List<Header> list = initPostHeader();

			JSONObject jso = new JSONObject(bean);
			String result = HttpUtil.postJSON(url, jso.toString(), list);

			JSONObject resJson = new JSONObject(result);
			System.out.println(resJson);

			int resCode = resJson.getInt("code");
			if(resCode == QingSongInterfaceBean.code_success) {

				jso = resJson.getJSONObject("data");
				MeterCMD cmd = new MeterCMD();
				cmd.setId(jso.getString("id"));
				cmd.setState(jso.getString("state"));
				cmd.setSendtime(jso.getString("sendtime"));
				cmd.setFinishtime(jso.getString("finishtime"));

				return cmd;
			}else {
				log.error(resJson.getString("msg"));
				return null;
			}

		} catch (IOException e) {
			log.error(e, e);
			return null;
		}

	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String code = "200223267508"	;   //200223090143    200223267508
//		DeviceDto res = checkDeviceInfo(code, STATIONCODE);
//		System.out.println(res);
		
		
		testMeterReading();
	}
	private static void testMeterReading() {
		String str = "[\r\n"
				+ "	{\r\n"
				+ "		\"conter\": {\r\n"
				+ "			\"alarmNum\": \"null\",\r\n"
				+ "			\"alarmTime\": \"null\",\r\n"
				+ "			\"battery\": \"0\",\r\n"
				+ "			\"curnum\": \"2.00\",\r\n"
				+ "			\"readperiod\": \"48\",\r\n"
				+ "			\"readstate\": \"null\",\r\n"
				+ "			\"sensor\": \"0\",\r\n"
				+ "			\"signal\": \"12\",\r\n"
				+ "			\"valvestate\": \"00\"\r\n"
				+ "		},\r\n"
				+ "		\"curtime\": \"2023-09-14 15:14:10\",\r\n"
				+ "		\"ispid\": \"c32f1c3bcfc24a25aabdbc6ed824235b\"\r\n"
				+ "	},\r\n"
				+ "	{\r\n"
				+ "		\"conter\": {\r\n"
				+ "			\"alarmNum\": \"null\",\r\n"
				+ "			\"alarmTime\": \"null\",\r\n"
				+ "			\"battery\": \"0\",\r\n"
				+ "			\"curnum\": \"0.20\",\r\n"
				+ "			\"readperiod\": \"48\",\r\n"
				+ "			\"readstate\": \"null\",\r\n"
				+ "			\"sensor\": \"0\",\r\n"
				+ "			\"signal\": \"15\",\r\n"
				+ "			\"valvestate\": \"00\"\r\n"
				+ "		},\r\n"
				+ "		\"curtime\": \"2023-09-13 21:59:49\",\r\n"
				+ "		\"ispid\": \"1046694471\"\r\n"
				+ "	}\r\n"
				+ "]";
		try {
			String url = "http://localhost:8080/lmnb/client/nbApi/postMeterReading";
			HttpUtil.post(url, str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
