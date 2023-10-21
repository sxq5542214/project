/**
 * 
 */
package com.yd.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;


import net.coobird.thumbnailator.Thumbnails;

public class ImageUtils {
	private static Logger log = Logger.getLogger(ImageUtils.class);

	public static int DEFAULT_IMG_WIDTH = 1024;

	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	private static String TEMP_FOLDER = "/temp";

	private static DiskFileItemFactory factory = null;
	private static ServletFileUpload upload = null;
	
	public final static void scale(String srcImageFile, String result, int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = width * scale;
				height = height * scale;
			} else {// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			log.error(e, e);
		}
	}
	
	/**
	 * 用给定的宽高生成图片
	 * @param fi
	 * @param targetSrc
	 * @param width
	 * @param height
	 * @return
	 */
	public final static boolean compressPic(FileItem fi,String targetSrc,int width,int height) {
		try {
			Thumbnails.of(fi.getInputStream()).size(width, height).toFile(targetSrc);
			return true;
		} catch (IOException e) {
			log.error(e,e);
			return false;
		}
	}

	/**
	 * 上传文件，并按指定的宽高存储
	 * @param request
	 * @param targetSrc
	 * @param width
	 * @param height
	 * @return	写入文件的具体目录
	 * @throws Exception
	 */
	public final static List<String> uploadFileByRequest(HttpServletRequest request,String targetDir,int width,int height) throws Exception {

		List<String> strList = new ArrayList<String>();
		boolean flag = false;
		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
//			List<FileItem> list = upload.parseRequest(request);
			MultiValueMap<String, MultipartFile> map = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
			List<CommonsMultipartFile> mfList = null;
			FileItem item = null;
			for(String key : map.keySet()) {
				CommonsMultipartFile cmf = (CommonsMultipartFile) map.get(key).get(0);
				if(cmf != null) {
					item = cmf.getFileItem();

					String str = uploadFileByRequest(item, targetDir, width, height);
					strList.add(str);
				}
			}
			// 获取上传的文件
		}catch (Exception e) {
			log.error(e,e);
			throw new Exception("图片上传失败！");
		}
		return strList;
	}

	/**
	 * 上传文件，并按指定的宽高存储
	 * @param request
	 * @param targetSrc
	 * @param width
	 * @param height
	 * @return	写入文件的具体目录
	 * @throws Exception
	 */
	public final static String uploadFileByRequest(FileItem item,String targetDir,int width,int height) throws Exception {
		File file = new File(targetDir);
		if (!file.exists())
			file.mkdirs();
		// 获得磁盘文件条目工厂
		if(factory == null) {

			// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
			// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
			/**
			 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
			 * 格式的 然后再将其真正写到 对应目录的硬盘上
			 */
			factory = new DiskFileItemFactory();
			factory.setRepository(new File(TEMP_FOLDER ));
			// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
			factory.setSizeThreshold(1024 * 1024);

			// 高水平的API文件上传处理
			upload = new ServletFileUpload(factory);
		}

		boolean flag = false;
		try {
			// 获取上传的文件
			if(item != null && StringUtil.isNotNull(item.getName())) {
				
				String fileName = item.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String targetFileSrc = targetDir + DateUtil.formatDatePure(new Date()) + "." + fileExt;
				
				System.out.println("存放目录:" + file.getAbsolutePath());
				System.out.println("文件名:" + file.getName());
				System.out.println("文件大小:" + item.getSize());
				System.out.println("文件上传名称:"+item.getName().substring(item.getName().indexOf(".")+1));
				
				// 真正写到磁盘上
				flag = compressPic(item, targetFileSrc, width, height);
				
				if(flag) {
					return targetFileSrc;
				}

			}
		}catch (Exception e) {
			log.error(e,e);
			throw new Exception("图片上传失败！");
		}
		return null;
	}
	
	private static FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}
	
	public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			log.error(e, e);
		}
	}

	public final static void cut(String srcImageFile, String result, int x, int y, int width, int height) {
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static void cut2(String srcImageFile, String descDir, int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20)
				rows = 2; // 切片行数
			if (cols <= 0 || cols > 20)
				cols = 2; // 切片列数
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static void cut3(String srcImageFile, String descDir, int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static void convert(String srcImageFile, String formatName, String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			log.error(e, e);
		}
	}

	public final static void pressText(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static void pressText2(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y,
			float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao,
					null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return 宽度
	 */
	public static int[] getImgWidth(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int result[] = { 0, 0 };
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			result[0] = src.getWidth(null); // 得到源图宽
			result[1] = src.getHeight(null); // 得到源图高
			is.close();
		} catch (Exception e) {
			log.error(e, e);
		}
		return result;
	}

	/**
	 * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
	 * 
	 * @param imgsrc
	 *            源图片地址
	 * @param imgdist
	 *            目标图片地址
	 * @param widthdist
	 *            压缩后图片宽度（当rate==null时，必传）
	 * @param heightdist
	 *            压缩后图片高度（当rate==null时，必传）
	 * @param rate
	 *            压缩比例
	 */
	public static void reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist, Float rate) {
		try {
			File srcfile = new File(imgsrc);
			// 检查文件是否存在
			if (!srcfile.exists()) {
				return;
			}
			// 如果rate不为空说明是按比例压缩
			if (rate != null && rate > 0) {
				// 获取文件高度和宽度
				int[] results = getImgWidth(srcfile);
				if (results == null || results[0] == 0 || results[1] == 0) {
					return;
				} else {
					widthdist = (int) (results[0] * rate);
					heightdist = (int) (results[1] * rate);
				}
			}
			// 开始读取文件并进行压缩
			Image src = javax.imageio.ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream out = new FileOutputStream(imgdist);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(tag);
			out.close();

		} catch (IOException e) {
			log.error(e, e);
		}
	}

	/**
	 * 按图片质量进行压缩
	 * 
	 * @param srcFilePath
	 *            源文件地址
	 * @param descFilePath
	 *            生成的文件地址
	 * @param quality
	 *            质量 0到1
	 * @return 成功或失败
	 * @throws IOException
	 */
	@Deprecated
	public static boolean compressPic(String srcFilePath, String descFilePath, float quality) {

		return compressPic(srcFilePath, descFilePath, ImageParam.getInstance(), quality);

		// File file = null;
		// BufferedImage src = null;
		// FileOutputStream out = null;
		// ImageWriter imgWrier;
		// ImageWriteParam imgWriteParams;
		//
		// // 指定写图片的方式为 jpg
		// imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		// imgWriteParams = new
		// javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
		// // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		// imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// // 这里指定压缩的程度，参数qality是取值0~1范围内，
		// imgWriteParams.setCompressionQuality(quality);
		// imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		//
		// try {
		// if (isBlank(srcFilePath)) {
		// return false;
		// } else {
		// file = new File(srcFilePath);
		// src = ImageIO.read(file);
		//
		// ColorModel colorModel = src.getColorModel();//
		// ColorModel.getRGBdefault();
		// // 指定压缩时使用的色彩模式
		// imgWriteParams.setDestinationType(new
		// javax.imageio.ImageTypeSpecifier(colorModel, colorModel
		// .createCompatibleSampleModel(16, 16)));
		//
		// makedirs(descFilePath);
		// out = new FileOutputStream(descFilePath);
		//
		// imgWrier.reset();
		// // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
		// // OutputStream构造
		// imgWrier.setOutput(ImageIO.createImageOutputStream(out));
		// // 调用write方法，就可以向输入流写图片
		// imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
		// out.flush();
		// out.close();
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
		// return true;
	}

	public static boolean isBlank(String string) {
		if (string == null || string.length() == 0 || string.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 压缩图片， 可以指定宽高 和 压缩质量
	 * 
	 * @param srcFile		源文件，可以是网络的
	 * @param descFile		保存文件
	 * @param newWidth		指定宽度
	 * @param newHeight		指定高度
	 * @param quality		质量 0-1
	 * @return
	 * @throws Exception
	 */
	public static boolean compressPic(String srcFile, String descFile, ImageParam imgParam,
			float quality) {

		try {
			File imgFile;
			BufferedImage image;
			// 判断是否是网络图片
			if (srcFile.startsWith("http") || srcFile.startsWith("ftp") || srcFile.startsWith("file")) {
				// imgFile = new File(new URI(srcFile));
				image = javax.imageio.ImageIO.read(new URL(srcFile));
			} else {
				imgFile = new File(srcFile);
				image = javax.imageio.ImageIO.read(imgFile);
			}
			BufferedImage buffer;
			ImageWriter writer = null;

			Integer newWidth = imgParam.getWidth();
			Integer newHeight = imgParam.getHeight();
			// 是否有指定宽高
			if (newWidth == null || newHeight == null) {// 指定生成图宽高
				if (image.getWidth(null) > DEFAULT_IMG_WIDTH) { // 这里可以自己指定缩放的条件
					double rate = (double) image.getWidth(null) / DEFAULT_IMG_WIDTH;
					newWidth = DEFAULT_IMG_WIDTH;
					newHeight = (int) (image.getHeight(null) / rate);
				} else {
					newWidth = image.getWidth(null);
					newHeight = image.getHeight(null);
				}
			}

			imgParam.setWidth(newWidth);
			imgParam.setHeight(newHeight);

			buffer = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
			// BufferedImage通过缩放进行压缩 压缩策略为Image.SCALE_FAST 速度优先
			buffer.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST), 0, 0, null);
			// 获得一个ImageWriter输出流
			ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(buffer);

			Iterator<ImageWriter> iter = ImageIO.getImageWriters(type, "jpg");
			if (iter.hasNext()) {
				writer = iter.next();
			}
			if (writer == null) {
				return false;
			}

			IIOImage iioImage = new IIOImage(buffer, null, null);
			// 指定压缩方式 压缩程度 色彩模式
			ImageWriteParam param = writer.getDefaultWriteParam();
			// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality); // 这里可以指定压缩的质量程度 0-1.0
			ColorModel colorModel = ColorModel.getRGBdefault();

			param.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel
					.createCompatibleSampleModel(16, 16)));

			makedirs(descFile);
			FileOutputStream out = new FileOutputStream(descFile);

			writer.reset();
			// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
			// OutputStream构造
			ImageOutputStream imgOut = ImageIO.createImageOutputStream(out);
			writer.setOutput(imgOut);
			// 调用write方法，就可以向输入流写图片
			writer.write(null, iioImage, param);
			out.flush();
			out.close();
			imgOut.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
			return false;
		}
	}

	public static boolean makedirs(String path) {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			return file.getParentFile().mkdirs();
		}
		return false;
	}

	public static void main(String[] args) {

		System.out.println("E:\\Program Files\\apache\\tomcat-8.5.37\\webapps\\foodLifeStyle\\/images/upload/thumb/6/20200501222817.jpg".lastIndexOf("/"+1));
		
		
//		String srcFilePath = "D:\\Documents\\Pictures\\16a8e4bda0e5a5bde982b1e69e97e5928cd71b.jpg";
		
//		String srcFilePath = "https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=1497772711,3850772681&fm=85&s=D6391BC74A1268D46A29C0A90300A002";
//		String descFilePath = "D:\\Documents\\Pictures\\newfload\\8k4k_1.jpg";
//		float quality = 0.8f;
//		try {
//			long time = System.currentTimeMillis();
//			boolean flag = compressPic(srcFilePath, descFilePath, quality);
//
//			System.out.println(flag + ",cost:" + (System.currentTimeMillis() - time));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// // 1-缩放图像：
		// // 方法一：按比例缩放
		// ImageUtils.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);// 测试OK
		// // 方法二：按高度和宽度缩放
		// ImageUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300,
		// true);// 测试OK
		//
		// // 2-切割图像：
		// // 方法一：按指定起点坐标和宽高切割
		// ImageUtils.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400);//
		// 测试OK
		// // 方法二：指定切片的行数和列数
		// ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2);// 测试OK
		// // 方法三：指定切片的宽度和高度
		// ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300);// 测试OK
		//
		// // 3-图像类型转换：
		// ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");// 测试OK
		//
		// // 4-彩色转黑白：
		// ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");// 测试OK
		//
		// // 5-给图片添加文字水印：
		// // 方法一：
		// ImageUtils.pressText("我是水印文字", "e:/abc.jpg", "e:/abc_pressText.jpg",
		// "宋体", Font.BOLD, Color.white, 80, 0, 0,
		// 0.5f);// 测试OK
		// // 方法二：
		// ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg",
		// "e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//
		// 测试OK
		//
		// // 6-给图片添加图片水印：
		// ImageUtils.pressImage("e:/abc2.jpg", "e:/abc.jpg",
		// "e:/abc_pressImage.jpg", 0, 0, 0.5f);// 测试OK
	}
	public static class ImageParam{
		
		public ImageParam(){}
		
		public ImageParam(int width,int height){
			this.width= width;
			this.height = height;
		}
		
		private Integer width;
		private Integer height;
		public Integer getWidth() {
			return width;
		}
		public void setWidth(Integer width) {
			this.width = width;
		}
		public Integer getHeight() {
			return height;
		}
		public void setHeight(Integer height) {
			this.height = height;
		}
		
		public static ImageParam getInstance(){
			return new ImageParam();
		}
	}
}
