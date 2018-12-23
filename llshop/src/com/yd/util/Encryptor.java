package com.yd.util;


public class Encryptor {
	public Encryptor()
	{
	}

	public static String encrypt(String s)
	{
		StringBuffer stringbuffer = new StringBuffer();
		int i = 0;
		int j = s.length();
		int k = ( (j + 2) / 3) * 4;
		int l = k + 2 * (k / 60 + 1);
		k = 0;
		while (j > 0)
		{
			char c = s.charAt(i++);
			char c3 = v.charAt(c >> 2);
			char c1;
			if (--j > 0)
			{
				c1 = s.charAt(i++);
			}
			else
			{
				c1 = '\0';
			}
			char c4 = v.charAt( (c << 4) + (c1 >> 4) & 0x3f);
			char c2;
			char c5;
			if (j > 0)
			{
				if (--j > 0)
				{
					c2 = s.charAt(i++);
				}
				else
				{
					c2 = '\0';
				}
				c5 = v.charAt( (c1 << 2) + (c2 >> 6) & 0x3f);
			}
			else
			{
				c2 = '\0';
				c5 = '=';
			}
			char c6;
			if (j > 0)
			{
				c6 = v.charAt(c2 & 0x3f);
			}
			else
			{
				c6 = '=';
			}
			stringbuffer.append(c3).append(c4).append(c5).append(c6);
			if (j > 0)
			{
				j--;
			}
			if (++k == 15)
			{
				k = 0;
				stringbuffer.append("\r\n");
			}
		}
//		stringbuffer.append("\r\n");
		if (stringbuffer.length() != l-2)
		{
			return null;
		}
		else
		{
			return stringbuffer.toString();
		}
	}
	
	public static String uncrypt(String password){
		String v_password ;
		Integer passLength ;
		Integer passLength2 ;
		Integer passLength3 ;
		String pass ;
		String c1 ;
		String c2 ;
		String c3 ;
		String c4 ;
		char p1;
		char p2;
		char p3;
		Integer i1;
		Integer i2;
		Integer i3;
		Integer i4;
		String getResult ="";

		   passLength = password.length();
		   passLength3 = passLength % 4 ;
		   
		   if(passLength3 == 0){
			   v_password = password;
		   }else{
			   v_password = password.substring(0, password.length()-2);
		   }

		   pass = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

		   passLength = v_password.length();
		   passLength2 = 0;
		   
		   while(passLength > passLength2){
			   c1 = v_password.substring(passLength2, passLength2 + 1);
			   i1 = pass.indexOf(c1);

			   passLength2 = passLength2 + 1;
			   
			   c2 = v_password.substring(passLength2, passLength2 + 1);
			   i2 = pass.indexOf(c2) ;
			   passLength2 = passLength2 + 1;
			   
			   c3 = v_password.substring(passLength2, passLength2 + 1);
			   i3 = pass.indexOf(c3) ;
			   passLength2 = passLength2 + 1;
			   
			   c4 = v_password.substring(passLength2, passLength2 + 1);
			   i4 = pass.indexOf(c4) ;
			   passLength2 = passLength2 + 1;
			   
			   p1 = (char) (i1*4 + (int)Math.floor(i2/16));
			   
			   if(c3.equals("=")){
				   p2 = ' ';
			   }else{
				   p2 = (char)((i2 % 16) * 16 + Math.floor(i3/4));
			   }
			   
			   if(c4.equals("=")){
				   p3 = ' ';
			   }else{
				   p3 = (char)( (i3%4) * 64 + i4);
			   }
			   getResult += String.valueOf(p1) + String.valueOf(p2) + String.valueOf(p3);
		   }
		   
		   return getResult.trim();
	}

	private static final String v = new String(
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
	
	
	public static void main(String[] args)
	{
//		long tim = System.currentTimeMillis();
//		System.out.println(tim);
		String str = encrypt("122,5050,1,99985");
		System.out.println(str.length()+","+str);
//		
		System.out.println(uncrypt(str));
	}
}

//OTg1LDUwNTAsMSw5OTk4NA==
//,MTIyLDUwNTAsMSw5OTk4NQ==
