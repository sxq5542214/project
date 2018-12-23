package com.yd.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	/**
	 * MD5加密
	 * @param value
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String md5(String value) {
		byte[] buffer = value.getBytes();
		String result = null;
		char hexDigist[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(buffer);
			byte[] datas = md.digest(); //16个字节的长整数
		    char[] str = new char[2*16];
		    int k = 0;
		    for(int i=0;i<16;i++){
		      byte b   = datas[i];
		      str[k++] = hexDigist[b>>>4 & 0xf];//高4位
		      str[k++] = hexDigist[b & 0xf];//低4位
		    }
		    result = new String(str);
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
	    return result;
	}
	public static void main(String[] args) {
		System.out.println(md5("customer_id=5050&nonce_str=eb084b8e6a6d4df294352f45171c4483&notify_url=http://120.55.82.118:8081/api/report/POSTRPTYUND&order_account=13666672546&partner_code=21f7f5ebcf354fc3894a2f55f5a2e8e2&partner_out_trade_no=a16ebb3fedfa451cb75c720ea9dc42a2&product_code=AnHYDGN10M26f7e554aa9e4623b3b176703bc91bf572b80abaca694c8abdcc9d631e554e0"));
	}
}
