/**
 * 
 */
package com.yd.business.user.util;

import org.apache.log4j.Logger;

import com.yd.util.StringUtil;

/**
 * @author ice
 * 
 */
public class EmojiUtil {
	private static Logger log = Logger.getLogger(EmojiUtil.class);
	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtil.isNull(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (!isNotEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}

	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
	

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {

//		if (!containsEmoji(source)) {
//			return source;// 如果不包含，直接返回
//		}
		
		if(StringUtil.isNull(source)){
			return "微信用户";
		}
		
		StringBuilder buf = new StringBuilder();
		
		boolean flag = false;
		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			} else {
				flag = true;
				log.debug("EmojiUtil find...  changeto space  char:"+ codePoint);
				buf.append(' ');
			}
		}
		
		//如果有，就返回新字符串，没有就返回原来的
		if (flag) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
			return buf.toString();
		} else {
			return source;
		}

	}
	
	public static void main(String[] args) {
		String str = "尐蘭岚? 123123";
		System.out.println(filterEmoji(str));
		
		
	}
}
