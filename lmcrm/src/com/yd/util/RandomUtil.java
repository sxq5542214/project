/**
 * 
 */
package com.yd.util;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * @author ice
 *
 */
public class RandomUtil {
	
	private static Logger log = Logger.getLogger(RandomUtil.class);
	private static Random random = new Random(System.currentTimeMillis());
	private static char[]  charArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
		'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	public static int nextInt(int num){
		return random.nextInt(num);
	}
	
	public static int nextInt(int max,int min){
		return random.nextInt(max) % (max - min + 1) + min;
	}
	public static void main(String []args){
		for(int i=0;i<200;i++){
			int num = nextInt(200,0);
			if(num == 12){
				System.out.println(num);
			}
		}
		
	}
	
	/**
	 * 随即字符串
	 * @param length 字符串长度
	 * @return
	 */
	public static String randomString(int length){
		StringBuilder sb = new StringBuilder("");
		for(int i = 0 ; i < length; i ++){
			int index = random.nextInt(charArray.length);
			sb.append(charArray[index]);
		}
		return sb.toString();
	}
	
}
