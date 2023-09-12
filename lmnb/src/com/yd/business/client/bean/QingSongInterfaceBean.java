/**
 * 
 */
package com.yd.business.client.bean;

/**
 * 
 */
public class QingSongInterfaceBean {
	public static final int code_success = 0;
	public static final int code_error = -1;
	public static final String type_valve_open = "1";
	public static final String type_valve_close = "2";
	
	private String urlPrefix;
	private String joinFlag;
	
	private String ispid;
	private String stationcode;
	private String outerid;
	private String type;
	
	private String curtime;
	
	private String createtime;
	private String id;
	
	private String code;
	private String msg;
	private Object data;
	
	private Conter conter;
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCurtime() {
		return curtime;
	}
	public void setCurtime(String curtime) {
		this.curtime = curtime;
	}
	public Conter getConter() {
		return conter;
	}
	public void setConter(Conter conter) {
		this.conter = conter;
	}
	public String getJoinFlag() {
		return joinFlag;
	}
	public void setJoinFlag(String joinFlag) {
		this.joinFlag = joinFlag;
	}
	public String getUrlPrefix() {
		return urlPrefix;
	}
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getIspid() {
		return ispid;
	}
	public void setIspid(String ispid) {
		this.ispid = ispid;
	}
	public String getStationcode() {
		return stationcode;
	}
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	public String getOuterid() {
		return outerid;
	}
	public void setOuterid(String outerid) {
		this.outerid = outerid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public class DeviceDto{
		private String battery;
		private String code;//表号
		private String isp;
		private String ispid;//设备通讯码，运营商平台生成的
		private String valvestate; ///阀门状态 02未知 00开 01关 10无阀 11
		private String signalstrength;///信号
		private String readperiod;//周期半小时一个单位，48为24小时
		private String reversenum;//反向读数
		private String imei;
		private String sim;
		private String curnum;
		private String sensor;
		
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public String getSim() {
			return sim;
		}
		public void setSim(String sim) {
			this.sim = sim;
		}
		public String getCurnum() {
			return curnum;
		}
		public void setCurnum(String curnum) {
			this.curnum = curnum;
		}
		public String getSensor() {
			return sensor;
		}
		public void setSensor(String sensor) {
			this.sensor = sensor;
		}
		public String getBattery() {
			return battery;
		}
		public void setBattery(String battery) {
			this.battery = battery;
		}
		public String getIsp() {
			return isp;
		}
		public void setIsp(String isp) {
			this.isp = isp;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getIspid() {
			return ispid;
		}
		public void setIspid(String ispid) {
			this.ispid = ispid;
		}
		public String getValvestate() {
			return valvestate;
		}
		public void setValvestate(String valvestate) {
			this.valvestate = valvestate;
		}
		public String getSignalstrength() {
			return signalstrength;
		}
		public void setSignalstrength(String signalstrength) {
			this.signalstrength = signalstrength;
		}
		public String getReadperiod() {
			return readperiod;
		}
		public void setReadperiod(String readperiod) {
			this.readperiod = readperiod;
		}
		public String getReversenum() {
			return reversenum;
		}
		public void setReversenum(String reversenum) {
			this.reversenum = reversenum;
		}
	}
	public static class MeterCMD{
		public static byte STATE_NOSEND = 0;
		public static byte STATE_WAITEXECUTE = 1;
		public static byte STATE_SUCCESS = 2;
		public static byte STATE_FAILD = 3;
		public static byte STATE_OFFLINE = 4;
		public static byte STATE_OVERWIRTE = 5;
		public static byte STATE_CANCLE = 6;
		
		
		private String id;
		private String type;
		private String ispid;
		private String outerid;
		private String finishtime;
		private String state;
		private String createtime;
		private String sendtime;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getIspid() {
			return ispid;
		}
		public void setIspid(String ispid) {
			this.ispid = ispid;
		}
		public String getOuterid() {
			return outerid;
		}
		public void setOuterid(String outerid) {
			this.outerid = outerid;
		}
		public String getFinishtime() {
			return finishtime;
		}
		public void setFinishtime(String finishtime) {
			this.finishtime = finishtime;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getSendtime() {
			return sendtime;
		}
		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}
	}
	public class Conter{
		private String curnum;
		private String valvestate;
		private String battery;
		private String sensor;
		private String signalstrength;
		private String readperiod;
		private String readstate;
		private String reversenum;
		public String getCurnum() {
			return curnum;
		}
		public void setCurnum(String curnum) {
			this.curnum = curnum;
		}
		public String getValvestate() {
			return valvestate;
		}
		public void setValvestate(String valvestate) {
			this.valvestate = valvestate;
		}
		public String getBattery() {
			return battery;
		}
		public void setBattery(String battery) {
			this.battery = battery;
		}
		public String getSensor() {
			return sensor;
		}
		public void setSensor(String sensor) {
			this.sensor = sensor;
		}
		public String getSignalstrength() {
			return signalstrength;
		}
		public void setSignalstrength(String signalstrength) {
			this.signalstrength = signalstrength;
		}
		public String getReadperiod() {
			return readperiod;
		}
		public void setReadperiod(String readperiod) {
			this.readperiod = readperiod;
		}
		public String getReadstate() {
			return readstate;
		}
		public void setReadstate(String readstate) {
			this.readstate = readstate;
		}
		public String getReversenum() {
			return reversenum;
		}
		public void setReversenum(String reversenum) {
			this.reversenum = reversenum;
		}
	}
}
