/**
 * 
 */
package com.yd.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ʢТǿ
 *
 * 2012-2-1 ����02:03:14
 */
public class StringUtil
{
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String convertNull(String str){
		if(str == null || "".equals(str.trim()))
		{
			return "";
		}else
		{
			return str;
		}
	}
	public static String convertNull(String str,String optStr){
		if(isNull(str)) {
			return optStr;
		}
		return convertNull(str);
	}

	public static String convertNull(Integer str)
	{
		if(str == null )
		{
			return "";
		}else
		{
			return str.toString();
		}
	}

	public static String convertNull(Integer str,String opt)
	{
		if(str == null )
		{
			return opt;
		}else
		{
			return str.toString();
		}
	}
	

	public static String convertNull(Integer str,Integer opt)
	{
		return convertNull(str,String.valueOf(opt));
	}
	
	public static boolean isNull(Object o){
		if(o == null){
			return true;
		}
		if(o instanceof String){
			return isNull((String)o);
		}
		
		return false;
	}
	
	public static boolean isNull(String str)
	{
		if(null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str))
		{
			return true;
		}else
			return false;
	}
	
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
	
	
	public static String addLike(String str)
	{
		if(str == null || str.trim().equals(""))
		{
			return "%%";
		}
		return "%"+str+"%";
	}
	
	public static String addLikeReturnNull(String str)
	{
		if(str == null )
		{
			return null;
		}
		return "%"+str+"%";
	}
	
	public static String toString(Object value) {
		if (value instanceof Date) return toString((Date) value);
		return value == null ? "" : value.toString();
	}
	
	public static String toString(Date value) {
		return value == null ? "" : df.format(value);
	}
	

	/**
	 * 补全数字为字符串  如 1  补全为 01  或 补全为 001
	 * @param value
	 * @param length
	 * @return
	 */
	public static String fixIntToString(int value,int length){
		StringBuffer str = new StringBuffer(value+"");
		if(str.length() < length){
			int x = length - str.length();
			
			for(int i =0; i < x ; i++){
				str = str.insert(0, "0");
			}
		}
		return str.toString();
	}
	/**
	 * 补全数字为字符串  如 1  补全为 01  或 补全为 001
	 * @param value
	 * @param length
	 * @return
	 */
	public static String fixString(String value,int length){
		StringBuffer str = new StringBuffer(value);
		if(str.length() < length){
			int x = length - str.length();
			
			for(int i =0; i < x ; i++){
				str = str.insert(0, "0");
			}
		}
		return str.toString();
	}
	
	
	public static String dealHttpUrl(String url,Map<String,String> map){
		//是否直接在后面添加参数
		if(isNotNull(url)){
			if(url.contains("?")){
				for (Map.Entry<String, String> entry : map.entrySet()) {  
					url = url + "&"+ entry.getKey()+"="+entry.getValue();
				}  
			}else{
				url = url + "?";
				int i = 0;
				for (Map.Entry<String, String> entry : map.entrySet()) {  
					if(i == 0){
						url = url + entry.getKey()+"="+entry.getValue();
						i++;
					}else{
						url = url + "&"+ entry.getKey()+"="+entry.getValue();
					}
				}
			}
			if(url.startsWith("/")){
				url = url.replaceFirst("/", "");
			}
		}
		return url;
	}
	
	/**
	 * 取截取字符串的第几个元素
	 * @param dateStr
	 * @param number
	 * @param strFlag
	 * @return
	 */
	public static String cutDateString(String dateStr,int number,String strFlag){
		if(dateStr.contains(strFlag)){
			List<String> dateList = Arrays.asList(dateStr.split(strFlag));
			if(dateList.size() >= number){
				return dateList.get(number-1);
			}
		}
		return "_";
	}
	
	public static String guoHtml(String s) {
		if (isNotNull(s)) {
			String str = s.replaceAll("<[.[^<]]*>", "");
			return str;
		} else {
			return s;
		}
	}

/**
  * 字符串转换unicode
  */
 public static String string2Unicode(String string) {
  
     StringBuffer unicode = new StringBuffer();
  
     for (int i = 0; i < string.length(); i++) {
  
         // 取出每一个字符
         char c = string.charAt(i);
  
         // 转换为unicode
         unicode.append("\\u" + Integer.toHexString(c));
     }
  
     return unicode.toString();
 }
 

/**
  * unicode 转字符串
  */
 public static String unicode2String(String unicode) {
  
     StringBuffer string = new StringBuffer();
  
     String[] hex = unicode.split("\\\\u");
  
     for (int i = 1; i < hex.length; i++) {
  
         // 转换出每一个代码点
         int data = Integer.parseInt(hex[i], 16);
  
         // 追加成string
         string.append((char) data);
     }
  
     return string.toString();
 }

}
