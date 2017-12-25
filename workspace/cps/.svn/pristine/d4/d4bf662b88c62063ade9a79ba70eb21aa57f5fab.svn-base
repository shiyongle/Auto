package com.util;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.io.IOUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import sun.awt.image.ImageFormatException;


public class ImageUtil {
	private static int IMGSIZE = 80;	// KB

	public static void resizeImage(File file,InputStream is, OutputStream os,double percent, String format) throws IOException, ImageFormatException {
		try {
			BufferedImage prevImage=null;
			try{
				prevImage = ImageIO.read(is);
			}catch(IOException ee){				
				// //得到输入的编码器，将文件流进行jpg格式编码RGB
				try(InputStream fs=new FileInputStream(file)){
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fs);
					//得到编码后的图片对象
					prevImage = decoder.decodeAsBufferedImage();
				}
			}
			double width = prevImage.getWidth();  
			double height = prevImage.getHeight();
			//2016-04-14 lxx
			double ratio=width/height;
			if(height>500||ratio<0.5||ratio>2||width>500)
			{
				cutImage(new FileInputStream(file.getPath()), os, format, 0, 0, 350, 350);
			}
			else{
				double div_width =350;
				double div_height =350;
				int newWidth = 0;  
				int newHeight = 0;  
				if(width >height){
					newWidth = (int)(div_width);  
					newHeight = (int)((div_width*height)/width);
				}else if(width <height){
					newHeight=(int)div_height;
					newWidth=(int)((width*div_height)/height);
				}else{
					newWidth = (int)(width * percent);  
					newHeight = (int)(height * percent);
				}
				BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);  
				Graphics graphics = image.createGraphics();  
				graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
				ImageIO.write(image, format, os);
				os.flush();
			}
		}finally{
			if(os instanceof FileOutputStream){
				os.close();
			}
			if(is instanceof FileInputStream){
				is.close();
			}
		}
	}

	public static void saveDesignSmallImage(File file) throws IOException {
		String path = file.getPath();
		File newFile = new File(path.replace("vmifile", "smallvmifile"));
		reduceImage(file, newFile);
	}
	
	public static void saveSmallImage(File file) throws IOException {
		String path = file.getPath();
		File newFile = new File(path.replace("vmifile", "smallvmifile"));
		reduceImage(file, newFile);
	}

	private static void reduceImage(File source, File newFile) throws IOException {
		String path = source.getPath();
		String fileType = path.substring(path.lastIndexOf(".")+1);
		if(!isImage(fileType)){
			return;
		}
		File pFile = newFile.getParentFile();
		if(!pFile.exists()){
			pFile.mkdirs();
		}
		InputStream in = new FileInputStream(source);
		OutputStream out = new FileOutputStream(newFile);
		double percent = Math.sqrt(1024.0*IMGSIZE/in.available());
		if(percent>0.8){
			IOUtils.copy(in, out);
			out.flush();  
			IOUtils.closeQuietly(in);  
			IOUtils.closeQuietly(out);  
		}else{
			try {
				ImageUtil.resizeImage(source,in, out, percent, fileType);
			} catch (Exception e) {
				if("JPEG".equalsIgnoreCase(fileType) ||"JPG".equalsIgnoreCase(fileType)){
					try {
						CMYK.toRgb(source, newFile);
						//	ImageTool.convertToRGB(source, newFile);
					} catch (Exception e1) {
						throw new RuntimeException("图片转换失败！");
					}
				}else{
					throw new RuntimeException("图片转换失败！");
				}
			}
		}
	}

	public static boolean isImage(String fileType) {
		int index;
		if((index=fileType.lastIndexOf("."))!=-1){
			fileType = fileType.substring(index+1);
		}
		Pattern pattern = Pattern.compile("jpg|png|gif|JPG|PNG|GIF|bmp");
		Matcher matcher = pattern.matcher(fileType);
		return matcher.matches();
	}
	
	/**
	 * 用于拷贝平台文件
	 * @param root
	 * @throws IOException
	 */
	public static void copyFile(File root) throws IOException {
		File[] files = root.listFiles();
		String path;
		File newFile;
		File parentFile;
		for(File file: files){
			path = file.getPath();
			if(file.isDirectory()){
				copyFile(file);
			}else{
				newFile = new File(path.replace("d:\\vmifile", "d:\\smallvmifile"));
				parentFile = newFile.getParentFile();
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				reduceImage(file,newFile);
			}
		}
	}

	//2016-04-14 lxx 裁剪图片
	public static void cutImage(InputStream input, OutputStream out, String type,int x,  
			int y, int width, int height) throws IOException {  
		ImageInputStream imageStream = null;  
		try {  
			String imageType=(null==type||"".equals(type))?"jpg":type;  
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);  
			ImageReader reader = readers.next();  
			imageStream = ImageIO.createImageInputStream(input);  
			reader.setInput(imageStream, true);  
			ImageReadParam param = reader.getDefaultReadParam();  
			Rectangle rect = new Rectangle(x, y, width, height);  
			param.setSourceRegion(rect);  
			BufferedImage bi = reader.read(0, param);  
			ImageIO.write(bi, imageType, out);  
		} finally {  
			imageStream.close();  
		}  
	}  
}  