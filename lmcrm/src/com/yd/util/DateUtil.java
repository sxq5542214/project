/**
 * 
 */
package com.yd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;


/**
 * @author ice
 *
 */
public class DateUtil {
	private static Logger log = Logger.getLogger(DateUtil.class);
	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sf_month = new SimpleDateFormat("yyyy-MM");
	private static final SimpleDateFormat sf_sss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final SimpleDateFormat sf_date = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sf_time = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat sf_date_sss_pure = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final SimpleDateFormat sf_date_pure = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat sf_only_date_pure = new SimpleDateFormat("yyyyMMdd");
	
	
	/**
	 * JAVA与PHP的时间转换
	 * @param date
	 * @return
	 */
	public static int java2phpDate(Date date){
		return java2phpDate(date.getTime());
	}
	
	/**
	 * php与JAVA的时间转换
	 * @param dateValue
	 * @return
	 */
	public static Date php2javaDate(int dateValue){
		return new Date(php2javaDateValue(dateValue));
	}
	
	/**
	 * php与JAVA的时间转换
	 * @param dateValue
	 * @return
	 */
	public static String php2javaDateStr(int dateValue){
		return sf.format(new Date(php2javaDateValue(dateValue)));
	}
	
	/**
	 * php与JAVA的时间转换
	 * @param dateValue
	 * @return
	 */
	public static Date php2javaDate(long dateValue){
		return new Date(php2javaDateValue((int)dateValue));
	}
	
	public static int java2phpDate(long dateValue){
		String str = String.valueOf(dateValue);
		String newstr = str.substring(0, str.length() -3);
		return Integer.parseInt(newstr);
	}
	
	public static long php2javaDateValue(int dateValue){
		String str = String.valueOf(dateValue);
		String newstr = str+"000";
		return Long.parseLong(newstr);
	}
	
	public static boolean moreThanJava(int dateValue,Date date){
		int javaValue = java2phpDate(date);
		if(dateValue >= javaValue){
			return true;
		}
		return false;
	}
	
	public static boolean moreThanJava(int phpValue,long javaValue){
		String str = String.valueOf(javaValue);
		String newstr = str.substring(0, str.length() -3);
		javaValue = Integer.parseInt(newstr);
		
		if(phpValue >= javaValue)
			return true;
		
		return false;
	}
	/**
	 * 计算两个时间的时间差(小时)
	 * @param start
	 * @param end
	 */
	public static int timeDiff(String start,String end){
		int hour = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			Date startDate = sdf.parse("2015-08-28 21:44:35");
			Date endDate = sdf.parse(end);
			long diff = startDate.getTime() - endDate.getTime();
			long h = diff/(1000*60*60);
			hour = NumberUtil.toInt(h);
		}catch(Exception e){
            log.error(e,e);
		}
		return hour;
	}
	
	public static String getNowDateStr(){
		return formatDate(new Date());
	}
	public static String getNowDateStrSSS(){
		return formatDateSSS(new Date());
	}
	public static String getNowMonthStr(){
		return formatMonth(new Date());
	}
	
	public static String getNowOnlyDateStr(){
		return formatDateOnlyDate(new Date());
	}
	
	/**
	 * yyyyMMddHHmmssSSS
	 * @param date
	 * @return
	 */
	public static String formatDateToPureSSS(Date date){
		return sf_date_sss_pure.format(date);
	}
	
	/**
	 * yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String formatDatePure(Date date){
		return sf_date_pure.format(date);
	}
	
	/**
	 * yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String formatOnlyDatePure(Date date){
		return sf_only_date_pure.format(date);
	}
	
	
	public static String formatDate(Date date){
		if(date == null) return "";
		return sf.format(date);
	}
	
	public static String formatMonth(Date date){
		if(date == null) return "";
		return sf_month.format(date);
	}
	
	public static String formatDateSSS(Date date){
		if(date == null) return "";
		return sf_sss.format(date);
	}

	public static String formatDateOnlyDate(Date date){
		return sf_date.format(date);
	}
	
	public static String formatDateOnlyDate(long time){
		return sf_date.format(new Date(time));
	}
	
	public static String formatDateOnlyTime(Date date){
		return sf_time.format(date);
	}
	
	public static String formatDateOnlyTime(long date){
		return sf_time.format(new Date(date));
	}
	
	public static String formatDate(long date){
		return sf.format(new Date(date));
	}
	
	public static Date parseDate(String str) throws ParseException{
		return sf.parse(str);
	}
	
	public static Date parseDateSSS(String str) throws ParseException{
		return sf_sss.parse(str);
	}
	
	public static Date parseDateDetial(String str) throws ParseException{
		return sf_sss.parse(str);
	}
	
	public static Date parseDateOnlyDate(String str) throws ParseException{
		return sf_date.parse(str);
	}
	
	public static String formatDateOnlyDate(String date){
		if(date.split(" ").length >0)
		{
			return date.split(" ")[0];
		}
		return null;
	}
	
	public static int getNowDayOfMonth(){
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取下几个月的月份
	 * @param nextNum  下几个月
	 * @return
	 */
	public static int getNextMonth(Integer nextNum){
		if(nextNum == null)
			nextNum = 0;
		
		Calendar c = Calendar.getInstance();
//		c.add(Calendar.MONTH, nextNum);
		
		int month = c.get(Calendar.MONTH) + 1 + nextNum;
		
		if(month > 12){
			month = month - 12;
		}
		
		return month;
	}
	
	
	/**
	 * 将一个时间增加多少天
	 * @param date
	 * @param plusDate 增加天数
	 * @return
	 * @throws ParseException 
	 */
	public static String plusDate(String time,int plusDate){
		return plusDate(time, plusDate, true);
	}
	
	/**
	 * 将一个时间增加多少天
	 * @param date
	 * @param plusDate 增加天数
	 * @return
	 * @throws ParseException 
	 */
	public static String plusDate(String date,int plusDate,boolean hasTime){
		String result = "";
		try {
			SimpleDateFormat format = hasTime ? sf : sf_date;
			Date d = format.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_YEAR, plusDate);
			Date resultDate = c.getTime();
			result = format.format(resultDate);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e, e);
		}
		return result;
	}
	
	/**
	 * 将一个时间增加多少天
	 * @param date
	 * @param plusDate 增加天数
	 * @return
	 * @throws ParseException 
	 */
	public static String plusDate(long time,int plusDate,boolean hasTime){
		String result = "";
		try {
			Date d = new Date(time);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_YEAR, plusDate);
			Date resultDate = c.getTime();
			if(hasTime) {
				result = sf.format(resultDate);
			}else {
				result = sf_date.format(resultDate);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e, e);
		}
		return result;
	}
	/**
	 * 获取日期的月份
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static int getMonthByDate(String date,String formatStr){
		int month = 0;
		if(StringUtil.isNull(date)){
			return month;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			Date dateTime = format.parse(date);
			month = dateTime.getMonth()+1;
		} catch (ParseException e) {
			log.error(e, e);
		}
		return month;
	}

	
    /**  
     * 计算两个日期之间相差的小时数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        smdate=sf_sss.parse(sf_sss.format(smdate));  
        bdate=sf_sss.parse(sf_sss.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }  
	
	
    /**
	 * 获取上个月的今天日期
	 * @param date
	 * @param sf_date
	 * @return
	 */
	public static String getLastMonthDate(){			
		Calendar  calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		calendar.add(Calendar.MONTH, -1);
		return 	 sf_date.format(calendar.getTime());
	}

    /**
	 * 获取上个月第一天日期
	 * @param date
	 * @param sf_date
	 * @return
	 */ 
	public static String getLastMonthBeginDate(){			
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return 	 sf_date.format(calendar.getTime());
	}
	
    /**
	 * 获取上个月最后一天日期
	 * @param date
	 * @param sf_date
	 * @return
	 */ 
	public static String getLastMonthEndDate(){			
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		return 	 sf_date.format(calendar.getTime());
	}
	
	/**
	 * 获取时间的开始时间
	 * current : 时间毫秒数
	 * @return
	 */
	public static String getNowDateBegin(long current){
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();
		return  formatDateSSS(new Date(zero));
	}
	/**
	 * 获取时间的结束时间
	 * current : 时间毫秒数
	 * @return
	 */
	public static String getNowDateEnd(long current){
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数  
		return  formatDateSSS(new Date(twelve));
	}


	public static void main(String[] args) throws ParseException {
		
//		System.out.println(getNextMonth(1));
		String ddd = "2017-01-01 16:01:04.111";
		long current = parseDateSSS(ddd).getTime();
//		System.out.println("天"+getMonthBeginDate(current,0));
//		System.out.println("天"+getMonthEndDate(current,0));
//		System.out.println("周"+getMonthBeginDate(current,1));
//		System.out.println("周"+getMonthEndDate(current,1));
//		System.out.println("月"+getMonthBeginDate(current,2));
//		System.out.println("月"+getMonthEndDate(current,2));
//		System.out.println("年"+getMonthBeginDate(current,3));
//		System.out.println("年"+getMonthEndDate(current,3));
		Calendar c = Calendar.getInstance();
		int mo = c.get(Calendar.MONTH);
//		System.out.println(DateUtil.formatDate(c.getTime()));
		
//		System.out.println(daysBetween(DateUtil.parseDateDetial("2016-08-14 16:01:04.111"),new Date()));
		
		
	}
}
