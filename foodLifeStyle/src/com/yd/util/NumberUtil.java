package com.yd.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 常用工具类。
 * @author cj
 *
 */
public class NumberUtil {
	private static Random random = new Random(System.currentTimeMillis());
	private static Logger log = Logger.getLogger(NumberUtil.class);

	public static void main(String[] args) {
		
		String  s = fixIntToString(12, 5);
		System.out.println(s);
		
//		double d = distance(120.576821883333, 31.8042840333333, 120.5767826, 31.80425055);
//		System.out.println("D: " + d);
		
//		d = distance(120.1, 31.1, 120.2, 31.2);
//		System.out.println("1: " + d);
//		
//		d = distance(120.11, 31.11, 120.12, 31.12);
//		System.out.println("2: " + d);
//		
//		d = distance(120.111, 31.111, 120.112, 31.112);
//		System.out.println("3: " + d);
//		
//		d = distance(120.1111, 31.1111, 120.1112, 31.1112);
//		System.out.println("4: " + d);
//		
//		d = distance(120.11111, 31.11111, 120.11112, 31.11112);
//		System.out.println("5: " + d);
	}
	
	public static String toString(Object value) {
		if (value instanceof Date) return toString((Date) value);
		return value == null ? "" : value.toString();
	}
	
	/**
	 * 默认将日期转换为<pre>yyyy-MM-dd HH:mm:ss</pre>
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return date == null ? "" : toString(date, true);
	}
	
	public static int convertNull(Integer num) {
		if(num == null) {
			return 0;
		}
		return num;
	}
	
	/**
	 * <p>�?单格�?:yyyy-MM-dd
	 * <p>复杂格式:yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param detail
	 * @return
	 */
	public static String toString(Date date, boolean detail) {
		DateFormat fm = detail ? defaultDetailDateFormat : defaultDateFormat;
		return date == null ? "" : fm.format(date);
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
	
	public static int random(int end,Long seed){
		if(seed == null)
			return random.nextInt(end);
		else{
			random.setSeed(seed);
			return random.nextInt(end);
		}
	}
	
	public static int random(int end){
		return random(end, System.currentTimeMillis());
	}
	
	
	public static Integer toInt(Object obj) {
		if(StringUtil.isNull(obj)){
			return null;
		}
		if (obj instanceof Number) return ((Number)obj).intValue();
		try {
			String string = obj + "";
			return Integer.parseInt(string);
		} catch (Exception e) {
            log.error(e,e);
			return null;
		}
	}
	
	public static Integer toIntDefaultZero(Object obj) {
		
		if(obj == null){
			return 0;
		}
		
		if (obj instanceof Number) return ((Number)obj).intValue();
		try {
			String string = obj + "";
			return Integer.parseInt(string);
		} catch (Exception e) {
            log.error(e,e);
			return null;
		}
	}
	
	public static long toLong(Object value) {
		if (value instanceof Number) return ((Number)value).longValue();
		try {
			return Long.parseLong(value + "");
		} catch (Exception e) {
            log.error(e,e);
			return -1;
		}
	}	
	
	public static double toDouble(Object str) {
		return toDouble(str, -1);
	}
	
	public static double toDouble(Object str, double defaul) {
		if (str instanceof Number) return ((Number)str).doubleValue();
		try {
			return Double.parseDouble((str + "").trim());
		} catch (Exception e) {
            log.error(e,e);
			return defaul;
		}
	}
	
	/**
	 * 把所有可能的对象转成boolean。
	 * @param obj
	 * @return
	 */
	public static boolean toBool(Object obj) {
		if (obj == null) return false;
		if (obj instanceof Boolean) return ((Boolean)obj).booleanValue();
		String str = obj.toString();
		str = str.trim();
		if ("on".equalsIgnoreCase(str)) return true;
		if ("yes".equalsIgnoreCase(str)) return true;
		if ("y".equalsIgnoreCase(str)) return true;
		if ("是".equals(str)) return true;
		try {
			return new Boolean(str).booleanValue();
		} catch (Exception e) {
            log.error(e,e);
			return false;
		}
	}
	
	public static String[] keys(Map map) {
		if (map == null) return new String[0];
		ArrayList<String> ls = new ArrayList<String>();
		for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
			Object key = itr.next();
			if (key != null) ls.add(key.toString());
		}
		return (String[]) ls.toArray(new String[ls.size()]);
	}
	
	public static String[] concat(String[] s1, String[] s2) {
		String[] ss = new String[s1.length + s2.length];
		System.arraycopy(s1, 0, ss, 0, s1.length);
		System.arraycopy(s2, 0, ss, s1.length, s2.length);
		return ss;
	}
	
	public static File createTempFile(String path, String suffix, String filename) {
		//String realPath = getServletContext().getRealPath("/temp");
		File f = new File(path);
		f.mkdirs();
		if (filename == null) filename = System.currentTimeMillis() + "";
		if (suffix == null) suffix = ".tmp";
		File file = null;
		try {
			file = File.createTempFile(filename, suffix, f);
			file.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
            log.error(e,e);
		}
		
		return file;
	}

	protected static long getATermId(Map c) {
		Number a = (Number) c.get("ATERMID");
		if (a == null) {
			Map aterm = (Map) c.get("aterm");
			if (aterm == null) return -1;
			a = (Number) aterm.get("ID");
		}
		return a.longValue();
	}
	protected static long getZTermId(Map c) {
		Number a = (Number) c.get("ZTERMID");
		if (a == null) {
			Map aterm = (Map) c.get("zterm");
			if (aterm == null) return -1;
			a = (Number) aterm.get("ID");
		}
		return a.longValue();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 转换成int类型，失败则返回指定值�1�?7�?1�?7
	 * @param obj
	 * @param _default
	 * @return
	 */
	public static int toInt(Object obj, int _default) {
		
		if (obj instanceof Number) return ((Number)obj).intValue();
		try {
			if(StringUtil.isNull(obj)){
				return _default;
			}
			
			String string = obj + "";
			return Integer.parseInt(string);
		} catch (Exception e) {
            log.error(e,e);
			return _default;
		}
	}
	
	/**
	 * 数组转为字符串�1�?7�?1�?7
	 * @return
	 */
	public static String join(Object[] array, String sp, String quote, boolean trim) {
		sp = sp == null ? "," : sp;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (quote != null) sb.append(quote);
			Object o = array[i];
			if (trim && o != null) o = o.toString().trim();
			sb.append(o);
			if (quote != null) sb.append(quote);
			if (i != array.length - 1) sb.append(sp);
		}
		return sb.toString();
	}
	
	/**
	 * 参数是否为空判断。
	 * <ol>
	 * <li>字符串为null或长度为0返回true
	 * <li>数组为null或长度为0返回true
	 * <li>集合为null或长度为0返回true
	 * </ol>
	 * @param o
	 * @return
	 */
	public static boolean empty(Object o) {
		if (o == null) return true;
		if (o instanceof String) return empty((String)o);
		if (o instanceof Object[]) return empty((Object[])o);
		if (o instanceof Collection) return empty((Collection)o);
		return false;
	}
	
	/**
	 * 字符串为null或长度为0返回true
	 * @param str
	 * @return
	 */
	public static boolean empty(String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * 数组为null或长度为0返回true
	 * @param array
	 * @return
	 */
	public static boolean empty(Object[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 集合为null或长度为0返回true
	 * @param c
	 * @return
	 */
	public static boolean empty(Collection c) {
		return c == null || c.size() == 0;
	}

	/**
	 * 日期转换�?
	 * @param sd
	 * @return
	 */
	public static Date toDate(Object sd) {
		return toDate(sd, null, null);
	}
	
	/**
	 * @param sd
	 * @param detail
	 * @return
	 */
	public static Date toDate(Object sd, boolean detail) {
		DateFormat df = detail ? defaultDetailDateFormat : defaultDateFormat;
		return toDate(sd, df);
	}
	
	/**
	 * 日期转换�?
	 * @param sd
	 * @return
	 */
	public static Date toDate(Object sd, DateFormat format) {
		return toDate(sd, format, null);
	}

	/**
	 * 日期转换�?
	 * @param sd
	 * @return
	 */
	public static Date toDate(Object sd, DateFormat format, Date _default) {
		if (sd instanceof Date) return (Date) sd;
		if (format == null) format = defaultDateFormat;
		try {
			return parseDate(sd + "");
		} catch (Exception e) {
            log.error(e,e);
		}
		return _default;
	}
	
	private static DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
	private static DateFormat dateFormat2 = new SimpleDateFormat("yyyyMM");
	private static DateFormat defaultDetailDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date parseDate(String str){
        Date d = null;
        String oper = str;
        if(oper.indexOf('.')!=-1){
            int count = 0;
            while(oper.indexOf('.')!=-1){
                count++;
                oper = oper.substring(oper.indexOf('.')+1);
            }
            if(count ==1){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
                 try {
                     d = sdf.parse(str);
                } catch (ParseException e) {
                    log.error(e,e);
                }
            }
            if(count==2){
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	             try {
	                 d = sdf.parse(str);
	            } catch (ParseException e) {
	                log.error(e,e);
	            }
            }
        }
        else if(str.indexOf('-')!=-1){
            int count = 0;
            while(oper.indexOf('-')!=-1){
                count++;
                oper = oper.substring(oper.indexOf('-')+1);
            }
            if(count ==1){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                 try {
                     d = sdf.parse(str);
                } catch (ParseException e) {
                    log.error(e,e);
                }
            }
            if(count==2){
//	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             try {
	                 d = defaultDateFormat.parse(str);
	            } catch (ParseException e) {
	                log.error(e,e);
	            }
            }
        }
        else {
        	if(str.length() > 6){
        		try {
	                 d = dateFormat1.parse(str);
	            } catch (ParseException e) {
	                log.error(e,e);
	            }
        	} else {
        		try {
	                 d = dateFormat2.parse(str);
	            } catch (ParseException e) {
	                log.error(e,e);
	            }
        	}
        }
        
        return d;
    }

//	/**
//	 * 将对象转换为map�?
//	 * @param o
//	 * @return
//	 */
//	public static Map toMap(Object o) {
//		return toMap(o, 3);
//	}
//	
//	/**
//	 * 将对象转换为map�?
//	 * @param o
//	 * @return
//	 */
//	public static Object toMap(Object o, int loop) {
//		if (o == null) return null;
//		if (o.getClass().isPrimitive()) return o;
//		if (o instanceof String) return o;
//		if (o instanceof List) return o;
//		if (loop <= 0) return new HashMap();
//		Map n = new HashMap();
//		//遍历�?有属性，将所有属性�?�toMap置入
//		if (o instanceof Object) {
//			//反射
//			Class glass = o.getClass();
//	        Method[] methods = glass.getMethods();
//	        for (int i = 0; i < methods.length; i++) {
//				Method m = methods[i];
//				if (m.getParameterTypes().length != 0) continue;//只取属�?��??
//				if (!m.getName().startsWith("get") && !m.getName().startsWith("is")) continue;
//				int pos = m.getName().startsWith("is") ? 2 : 3;
//				String key = m.getName().substring(pos);
//				if (key.length() == 0) continue;
//				key = key.substring(0, 1).toLowerCase() + key.substring(1);
//				Object value = null;
//				try {
//					value = m.invoke(o, (Object[])null);
//				} 
//				catch (IllegalArgumentException e) {} 
//				catch (IllegalAccessException e) {} 
//				catch (InvocationTargetException e) {}
//				n.put(key, toMap(value, loop - 1));
//			}
//		}
//		return null;
//	}
	
	static double toRad(double deg) {
		return deg * Math.PI / 180;
	}
	
	/**
	 * wgs84下两点距�?(m).
	 * //var deg = dms[0].slice(0,3)/1 + dms[0].slice(3,5)/60 + dms[0].slice(5)/3600
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		  double a = 6378137, b = 6356752.3142,  f = 1/298.257223563;  // WGS-84 ellipsiod
		  double L = toRad(lon2-lon1);
		  double U1 = Math.atan((1-f) * Math.tan(toRad(lat1)));
		  double U2 = Math.atan((1-f) * Math.tan(toRad(lat2)));
		  double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
		  double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
		  
		  double lambda = L, lambdaP, iterLimit = 100;
		  double cosSqAlpha = 0;
		  double sinSigma = 0;
		  double cos2SigmaM = 0;
		  double sigma = 0;
		  double cosSigma = 0;
		  do {
			  double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
			  sinSigma = Math.sqrt((cosU2*sinLambda) * (cosU2*sinLambda) + 
					  (cosU1*sinU2-sinU1*cosU2*cosLambda) * (cosU1*sinU2-sinU1*cosU2*cosLambda));
			  if (sinSigma==0) return 0;  // co-incident points
			cosSigma = sinU1*sinU2 + cosU1*cosU2*cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha*sinAlpha;
			cos2SigmaM = cosSigma - 2*sinU1*sinU2/cosSqAlpha;
			if (cosSqAlpha == 0) cos2SigmaM = 0;  // equatorial line: cosSqAlpha=0 (§6)
			double C = f/16*cosSqAlpha*(4+f*(4-3*cosSqAlpha));
			lambdaP = lambda;
			lambda = L + (1-C) * f * sinAlpha *
			  (sigma + C*sinSigma*(cos2SigmaM+C*cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)));
		  } while (Math.abs(lambda-lambdaP) > 1e-12 && --iterLimit>0);

		  if (iterLimit==0) return -1;  // formula failed to converge

		  double uSq = cosSqAlpha * (a*a - b*b) / (b*b);
		  double A = 1 + uSq/16384*(4096+uSq*(-768+uSq*(320-175*uSq)));
		  double B = uSq/1024 * (256+uSq*(-128+uSq*(74-47*uSq)));
		  double deltaSigma = B*sinSigma*(cos2SigmaM+B/4*(cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)-
		    B/6*cos2SigmaM*(-3+4*sinSigma*sinSigma)*(-3+4*cos2SigmaM*cos2SigmaM)));
		  double s = b*A*(sigma-deltaSigma);
		  
		  //s = s.toFixed(3); // round to 1mm precision
		  return s;
		}

	/**
	 * 将传入参数构造成Map格式�?
	 * <pre>Map ps = Util.makeMap("key1", 1, "key2", 2);</pre>
	 * 
	 * @param os
	 * @return
	 */
	public static Map makeMap(Object... os) {
		Map ps = new HashMap();
		Object key = null;
		for (int i = 0; i < os.length; i++) {
			if (key != null) {
				ps.put(key, os[i]);
				key = null;
			} else key = os[i];
		}
		return ps;
	}
	
	public static Object getFirstMatch(Map map, Object... keys) {
		Object value = null;
		for (int i = 0; i < keys.length; i++) {
			value = map.get(keys[i]);
			if (value != null) return value;
		}
		return null;
	}
	
	public static Map<String, String> string2Map(String content, String rs, String ws) {
		if (content == null || content.length() == 0) return new HashMap();
		String[] ss = content.split(rs);
		Map ps = new HashMap();
		for(int i = 0; i < ss.length; i ++) {
			String[] st = ss[i].split(ws);
			if (st.length == 2) ps.put(st[0], st[1]);
		}
		return ps;
	}
	
	public static Map string2Map(String content) {
		return string2Map(content, ";", ":");
	}

	/**
	 * @param txt
	 * @return
	 */
	public static String makeNameForSearch(String txt) {
		txt = txt.replaceAll("\\*", "%");//处理通配�?*
		//名称中经常会含有括号，�?�括号经常搞错全角半角，稍加处理，用单字匹配符替�?
		txt = txt.replaceAll("[(（]", "_");
		txt = txt.replaceAll("[)）]", "_");
		return txt;
	}
	

	/**
	 * 将列表中的每个对象转换为map�?
	 * @param objs
	 * @return
	 */
	public static List<Map> toMap(List objs) {
		List<Map> maps = new ArrayList<Map>(objs.size());
		for (Object o : objs) {
			/*if (o instanceof BaseObj) maps.add(((BaseObj)o).toMap());
			else */
			maps.add(NumberUtil.toMap(o));
		}
		return maps;
	}
	
	public static interface IFetch<T> {
		Object getValue(T instance, String field);
	}
	
	/**
	
	/**
	 * 简单的JavaBean -> map，不处理List等复杂类型。
	 * @param o
	 * @return
	 */
	public static Map toMap(Object o) {
		if (o instanceof Map) return (Map) o;
		Map ret = new HashMap();
		Method[] ms = o.getClass().getMethods();
		for (int i = 0; i < ms.length; i++) {
			Method m = ms[i];
			Class[] ps = m.getParameterTypes();
			String name = m.getName();
			String key = getKeyFromMethodName(name);
			if (ps.length == 0 && key != null) {
				try {
					Object value = m.invoke(o, (Object[])null);
					if (value == null) continue;
					ret.put(key, value);
				} catch (Exception e) {
					System.err.println(name + " > " + o);
					e.printStackTrace();
		            log.error(e,e);
				}
			}
		}
		return ret;
	}
	private static String getKeyFromMethodName(String name) {
		if ("getClass".equals(name)) return null;
		String key = null;
		if (name.startsWith("get")) key = name.substring(3, name.length());
		else if (name.startsWith("is")) key = name.substring(2, name.length());
		
		if (NumberUtil.empty(key)) return null;
		if (key != null) key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
		return key;
	}
	
	/** 
	* @Description: 数据类型转换
	* 注：为了防止读取的是科学计数法的情况
	* @param @param numObject
	* @param @return  
	* @return Long   
	* @author wee
	* @date 2011-4-6
	*/
	public static Long numberFormat(Object numObject){
		if(numObject==null)
			return null;
		  if(numObject instanceof Number){			  
			  return ((Number)numObject).longValue();
		  }else{
			  return numberFormat(numObject.toString());
		  }

	}
	private static Long numberFormat(String numString){
		try
		{			
			if(numString.indexOf("E")>0)
			{
				String[] s = numString.split("E");
				double num1 = Double.parseDouble(s[0]);
				double num2 = Double.parseDouble(s[1]);
				return (long) (num1 * Math.pow(10l, num2));
			}
		  return Long.parseLong(numString.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
            log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 带符号的字符串转换为int  如 +15 转换为15    -15 转换为 -15
	 * @param symbolString
	 * @return
	 */
	public static Integer symbolStringToInt(String symbolString){
		Integer result = null;
		if(symbolString.startsWith("+")){
			String str = symbolString.substring(1);
			result = Integer.parseInt(str);
		}else{
			result = Integer.parseInt(symbolString);
		}
		
		return result;
	}
	
	
	
	/**
	 *double类型四舍五入方法
	 */
	public static double RoundingDouble(double num){
		BigDecimal   b   =   new   BigDecimal(num);  
		double   RoundingNum   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return RoundingNum; 
	}
	
	/**
	 * 数值的加法，自动过滤空值
	 * @param params
	 * @return
	 */
	public static Integer addtion(Integer... params){
		Integer total = 0;
		for(Integer i : params){
			if(i != null){
				total += i;
			}
		}
		return total;
	}
	
}
