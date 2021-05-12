package com.yd.business.login.service.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.login.bean.LoginBean;
import com.yd.business.login.service.ILoginService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.operator.bean.OperatorBean;

@Service("loginService")
public class LoginServiceImpl extends BaseService implements ILoginService{
	
	@Autowired
	private IOperatorService operatorService;
	
	/**
	 * 生成随机验证码图片
	 */
	@Override
	public BufferedImage CheckCode(HttpSession session) {
		int width = 70;
		int height = 17;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		Random random = new Random();
		Font mFont = new Font(LoginBean.CHECKCODE_FONT_STYLE, Font.BOLD, 17);
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(mFont);
		g.setColor(getRandColor(180, 200));
		//画随机的线条
		for (int i = 0; i < 130; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(6) + 1;
			int y1 = random.nextInt(12) + 1;
			BasicStroke bs=new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
			Line2D line=new Line2D.Double(x,y,x+x1,y+y1);
			g2d.setStroke(bs);
			g2d.draw(line);
		}
		String sRand="";
		//输出随机的验证文字
		int itmp=0;
		for(int i=0;i<4;i++){
			if(random.nextInt(2)==1){
				itmp=random.nextInt(26)+65;	//生成A~Z的字母
			}else{
				itmp=random.nextInt(10)+48;	//生成0~9的数字
			}
			char ctmp=(char)itmp;
			sRand+=String.valueOf(ctmp);
			sRand = sRand.toUpperCase();
			Color color=new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110));
			g.setColor(color);
			/****随机缩放文字并将文字旋转指定角度**/
			//将文字旋转指定角度
			Graphics2D g2d_word=(Graphics2D)g;
			AffineTransform trans=new AffineTransform();
			trans.rotate(random.nextInt(45)*3.14/180,15*i+10,6);
			//缩放文字
			float scaleSize=random.nextFloat()+0.5f;
			if(scaleSize<0.8 || scaleSize>1.1f) scaleSize=1f;
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			/************************/
			g.drawString(String.valueOf(ctmp),15*i+10,14);

		}
		//将生成的验证码保存到Session中
		session.setAttribute(LoginBean.RANDCHECKCODE,encodeByMD5(sRand));//将加密后的验证码保存到Session
		g.dispose();
		return image;
		// TODO Auto-generated method stub

}
	
	
	//获取随机颜色
	private Color getRandColor(int fc, int bc) {
			Random random = new Random();
			if (fc > 255) fc = 255;
			if (bc > 255) bc = 255;
			int r = fc + random.nextInt(bc - fc);
			int g = fc + random.nextInt(bc - fc);
			int b = fc + random.nextInt(bc - fc);
			return new Color(r, g, b);
		}
		
		/**
		 * 进行MD5加密，连同下面的一起用encodeByMD5
		 */
		public String encode(String str, String algorithm) {
			if (str == null) {
				return null;
			}
			StringBuilder sb = new StringBuilder();								//声明并实例化StringBuilder类的对象
			try {
				MessageDigest code = MessageDigest.getInstance(algorithm);		//创建使用MD5加密算法的对象
				code.update(str.getBytes());									//将要加密信息中的所有字节提供给该对象
				byte[] bs = code.digest();										//调用digest方法完成消息摘要的计算，并以字节数组的形式返回消息摘要
				//将加密后的字节数组转换成十六进制的字符串，形成最终的密文
				for (int i = 0; i < bs.length; i++) {
					int v = bs[i] & 0xFF;
					if (v < 16) {
						sb.append(0);
					}
					sb.append(Integer.toHexString(v));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sb.toString().toUpperCase();									//将加密后的字符串中的英文字母转换为大写
		}
		
		
		/**
		 * 进行MD5加密,会调用上面encode方法进行具体处理
		 */
		@Override
		public String encodeByMD5(String str){
			return encode(str,"MD5");
		}
		
		@Override
		public OperatorBean login(String username,String password) {
			OperatorBean bean = operatorService.findOperatorByNameAndPass(username, password);
			if(bean != null) {
				// 登录用户写入session缓存
				WebContext.setObejctToSession(BaseContext.CURRENT_USER, bean);
			}
			
			return bean;
		}
		
		
}
