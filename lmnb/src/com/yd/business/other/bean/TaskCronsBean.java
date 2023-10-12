/**
 * 
 */
package com.yd.business.other.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 
 顺便贴一下cronExpression表达式备忘：

字段 允许值 允许的特殊字符

秒 0-59 , – * /

分 0-59 , – * /

小时 0-23 , – * /

日期 1-31 , – * ? / L W C

月份 1-12 或者 JAN-DEC , – * /

星期 1-7 或者 SUN-SAT , – * ? / L C #

年（可选） 留空, 1970-2099 , – * /

表达式意义

"0 0 12 * * ?" 每天中午12点触发

"0 15 10 ? * *" 每天上午10:15触发

"0 15 10 * * ?" 每天上午10:15触发

"0 15 10 * * ? *" 每天上午10:15触发

"0 15 10 * * ? 2005" 2005年的每天上午10:15触发

"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发

"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发

"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发

"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发

"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发

"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发

"0 15 10 15 * ?" 每月15日上午10:15触发

"0 15 10 L * ?" 每月最后一日的上午10:15触发

"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发

"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发

"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发

每天早上6点

0 6 * * *

每两个小时

0 *\2(这个\反了)  * * *

晚上11点到早上8点之间每两个小时，早上八点

0 23-7/2，8 * * *

每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点

0 11 4 * 1-3

1月1日早上4点

0 4 1 1 * 

 * @author ice
 *
 */
@Alias("task")
public class TaskCronsBean extends BaseBean {
	public static final int ENABLE_TRUE = 1;
	public static final int ENABLE_FALSE= 0;
	
	public static final int STATUS_RUNING = 1;
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_ERROR = -1;
	
	public static final int RUNONCE_TRUE = 1;
	public static final int RUNONCE_FALSE = 0;

	
	
	private Integer id;
	private String cron_code;
	private String cron_name;
	private String cron_desc;
	private Integer cron_order;
	private String cron_config;
	private String class_name;
	private String method_name;
	private String param_value;
	private Date thistime;
	private Date nextime;
	private String expression;
	private Integer day;
	private Integer week;
	private Integer hour;
	private Integer minute;
	private Integer enable;
	private Integer run_once;
	private String allow_ip;
	private String alow_files;
	private Integer status;
	private String description;
	
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getCron_code() {
		return cron_code;
	}
	public void setCron_code(String cron_code) {
		this.cron_code = cron_code;
	}
	public String getCron_name() {
		return cron_name;
	}
	public void setCron_name(String cron_name) {
		this.cron_name = cron_name;
	}
	public String getCron_desc() {
		return cron_desc;
	}
	public void setCron_desc(String cron_desc) {
		this.cron_desc = cron_desc;
	}
	public Integer getCron_order() {
		return cron_order;
	}
	public void setCron_order(Integer cron_order) {
		this.cron_order = cron_order;
	}
	public String getCron_config() {
		return cron_config;
	}
	public void setCron_config(String cron_config) {
		this.cron_config = cron_config;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getParam_value() {
		return param_value;
	}
	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}
	public Date getThistime() {
		return thistime;
	}
	public void setThistime(Date thistime) {
		this.thistime = thistime;
	}
	public Date getNextime() {
		return nextime;
	}
	public void setNextime(Date nextime) {
		this.nextime = nextime;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getRun_once() {
		return run_once;
	}
	public void setRun_once(Integer run_once) {
		this.run_once = run_once;
	}
	public String getAllow_ip() {
		return allow_ip;
	}
	public void setAllow_ip(String allow_ip) {
		this.allow_ip = allow_ip;
	}
	public String getAlow_files() {
		return alow_files;
	}
	public void setAlow_files(String alow_files) {
		this.alow_files = alow_files;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
