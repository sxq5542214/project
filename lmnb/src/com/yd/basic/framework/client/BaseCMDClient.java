package com.yd.basic.framework.client;

import org.apache.log4j.Logger;

public abstract class BaseCMDClient {
	protected Logger log = Logger.getLogger(getClass());
	
	//0 未发送 1待执行 2成功 3失败 4离线 5覆盖 6取消
	public static final byte CMD_STATE_NOTSEND = 0;
	public static final byte CMD_STATE_WAIT = 1;
	public static final byte CMD_STATE_SUCCESS = 2;
	public static final byte CMD_STATE_FAILD = 3;
	public static final byte CMD_STATE_OFFLINE = 4;
	public static final byte CMD_STATE_OVERWRITE = 5;
	public static final byte CMD_STATE_CANCLE = 6;
	
	
	
}
