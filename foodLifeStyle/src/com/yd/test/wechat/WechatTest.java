/**
 * 
 */
package com.yd.test.wechat;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

public class WechatTest {
	public static File createFileDir(BufferedImage image,String commentCode){
		Calendar calendar = Calendar.getInstance();
		//定义文件的名称
		String fileName = String.valueOf(calendar.getTimeInMillis());
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH)+1;
		Integer day = calendar.get(Calendar.DAY_OF_MONTH);
		String fileDir = "D:\\IMG\\"+year.toString()+"\\"+commentCode+"\\"+month.toString()+"\\"+day.toString();
		File file =new File(fileDir);    
		//如果文件夹不存在则创建    
		if (!file.exists() && !file.isDirectory()){       
		    file.mkdirs();    
		}
		File destFile = new File(fileDir+"\\"+fileName+".jpg");
		return destFile;
	}

	public static void main(String[] args) throws Exception {
//		int nums = 800;
//		// for (int i = 0; i < nums; i++) {
//		// new Thread(new Tt1()).run(); //单线程
//		// }
//		new Thread(new DoMutil(1, 1)).run(); // 并发
//		Thread.sleep(1000000);

		// 定义请求的配置信息
		// 获取下载地址
		// String downLoadUrl =
		// getDownLoadImglUrl(originalid)+"&media_id="+mediaId;
//		String downLoadUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=oLLdCXGYBmGfQHFNbsfXeiVjH5vM4ba3_e6DysTWGMtR-AEG3LVDs-ybbyZPHPb_haQEh0h_x2N2Y_NeEd8howEq9UEDv2YY-INtCaDxnPkDptEMCiDl1q8tmzJj9HdFSQXgAEACAH&media_id=VIWxZdzn7oO4QRWjRxoIT5_b5ppbr6HbnsgT9o7wtuQRQzzstWU1dCHZnEDA1Yqh";
//		String downLoadUrl = "http://ww1.sinaimg.cn/large/7d3337d8gw1f9jgpy9xbuj21hc0xc7wh.jpg";
		//图片的http全路径      
        String imgurl = "http://localhost:8080/llshop/page/pc/img/IMG_20161112_154640.jpg";   
        URL url = new URL(imgurl);   
//        BufferedInputStream   in = new BufferedInputStream(url.openStream());      
//        //保存的图片文件名      
//        File img = new File("d:/img.jpg");      
//        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(img));                     
//        byte[] buf = new byte[2048];      
//        int l = 0;  
//        int temp = 0;  
//        while( (l = in.read(buf)) != -1){    
//            temp =0;  
//            temp += l;  
//            out.write(buf);  
//               
//         }  
//         in.close();      
//         out.close(); 
		// 下载图片
//		CloseableHttpClient httpClient = HttpClients.createDefault();
			// get请求缩略图信息
//			HttpGet httpGet = new HttpGet(downLoadUrl); // 构造请求方式及内容
//			httpGet.setConfig(requestConfig);
//			CloseableHttpResponse response = httpClient.execute(httpGet);
			InputStream input = null;
					Image img = ImageIO.read(url);
					int height = img.getHeight(null);
					int width = img.getWidth(null);
					int maxWidth = 1366;
					int maxHeight = 1000;
					if(height > maxHeight){
						width = width*maxHeight/height;
						height = maxHeight;
					}else if(width > maxWidth){
						height = height*maxWidth/width;
						width = maxWidth;
					}
					BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
					image.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
					
					File destFile = createFileDir(image,"usersign");
					FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
					// 可以正常实现bmp、png、gif转jpg
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(image); // JPEG编码
					out.close();
	}
}

class DoMutil implements Runnable {

	int threadNum = 1;
	int nums = 1;

	public DoMutil(int threadNum, int nums) {
		this.threadNum = threadNum;
		this.nums = nums;
	}

	@Override
	public void run() {
		ExecutorService executorService = Executors
				.newFixedThreadPool(threadNum);
		for (int i = 0; i < nums; i++) {
			executorService.execute(new Tt1());
		}
	}

}

class Tt1 implements Runnable {
	static String xx = "d:/x/xxx.png";

	public void run() {
		try {
			long time = Long.valueOf(DateUtil.formatDateToPureSSS(new Date()));
			// 定义请求的配置信息
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(1000 * 20).setConnectTimeout(1000 * 60)
					.build();
			// 获取下载地址
			// String downLoadUrl =
			// getDownLoadImglUrl(originalid)+"&media_id="+mediaId;
//			String downLoadUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=oLLdCXGYBmGfQHFNbsfXeiVjH5vM4ba3_e6DysTWGMtR-AEG3LVDs-ybbyZPHPb_haQEh0h_x2N2Y_NeEd8howEq9UEDv2YY-INtCaDxnPkDptEMCiDl1q8tmzJj9HdFSQXgAEACAH&media_id=VIWxZdzn7oO4QRWjRxoIT5_b5ppbr6HbnsgT9o7wtuQRQzzstWU1dCHZnEDA1Yqh";
//			String downLoadUrl = "http://ww1.sinaimg.cn/large/7d3337d8gw1f9jgpy9xbuj21hc0xc7wh.jpg";
			//图片的http全路径      
            String imgurl = "http://localhost:8080/llshop/page/pc/img/IMG_20161112_154640.jpg";   
            URL url = new URL(imgurl);   
//            BufferedInputStream   in = new BufferedInputStream(url.openStream());      
//            //保存的图片文件名      
//            File img = new File("d:/img.jpg");      
//            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(img));                     
//            byte[] buf = new byte[2048];      
//            int l = 0;  
//            int temp = 0;  
//            while( (l = in.read(buf)) != -1){    
//                temp =0;  
//                temp += l;  
//                out.write(buf);  
//                   
//             }  
//             in.close();      
//             out.close(); 
			// 下载图片
//			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				// get请求缩略图信息
//				HttpGet httpGet = new HttpGet(downLoadUrl); // 构造请求方式及内容
//				httpGet.setConfig(requestConfig);
//				CloseableHttpResponse response = httpClient.execute(httpGet);
				InputStream input = null;
				try {
//					HttpEntity entity = response.getEntity();
//					if (response.getStatusLine().getStatusCode() >= 400) {
//						throw new IOException("Got bad response, error code = "
//								+ response.getStatusLine().getStatusCode()
//								+ " downLoadUrl: " + downLoadUrl);
//					}
					
					if (true) {
//						input = entity.getContent();
						long time3 = Long.valueOf(DateUtil.formatDateToPureSSS(new Date()));
						System.out.println(time3 - time);
						Image img = ImageIO.read(url);
						long time4 = Long.valueOf(DateUtil.formatDateToPureSSS(new Date()));
						System.out.println(time4 - time);
						BufferedImage image = new BufferedImage(400, 400,BufferedImage.TYPE_INT_RGB);
						image.getGraphics().drawImage(img, 0, 0, 400, 400, null); // 绘制缩小后的图
						
						File destFile = createFileDir(image,"usersign");
						FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
						// 可以正常实现bmp、png、gif转jpg
						JPEGImageEncoder encoder = JPEGCodec
								.createJPEGEncoder(out);
						encoder.encode(image); // JPEG编码
						out.close();
						long time2 = Long.valueOf(DateUtil.formatDateToPureSSS(new Date()));
						System.out.println(time2 - time);
						Thread.sleep(100);
					}
				} finally {
					if (!StringUtil.isNull(input)) {
						input.close();
					}
//					response.close();
				}
			} catch (Exception e1) {
			} finally {
//				httpClient.close();
			}

		} catch (IOException e1) {
		}
	}
	
	public static File createFileDir(BufferedImage image,String commentCode){
		//定义文件的名称
		String fileName = DateUtil.getNowDateStrSSS();
		Date nowDate = new Date();
		Integer year = nowDate.getYear();
		Integer month = nowDate.getMonth()+1;
		Integer day = nowDate.getDay();
		String fileDir = year.toString()+"\\"+commentCode+"\\"+month.toString()+"\\"+day.toString();//+"\\"+fileName+".jpg";
		File file =new File(fileDir);    
		//如果文件夹不存在则创建    
		if (!file .exists() && !file .isDirectory()){       
		    file .mkdir();    
		}
		File destFile = new File(fileDir+"\\"+fileName+".jpg");
		return destFile;
	}
	

}
