package com.pc.util;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pc.util.file.FastDFSUtil;



public class ImageUtil {
  
	private static int IMGSIZE = 80;	// KB
	
    public static void resizeImage(InputStream is, OutputStream os,double percent, String format) throws IOException {
		try {
			BufferedImage prevImage = ImageIO.read(is);
			double width = prevImage.getWidth();  
			double height = prevImage.getHeight();
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
		}finally{
				os.close();
				is.close();
		}
	}
    
//	public static void saveSmallImage(File file) throws IOException {
//		String path = file.getPath();
//		File newFile = new File(path.replace("csclfile", "smallcsclfile"));
//		reduceImage(file, newFile);
//	}
	
	public static File saveSmallImage(MultipartFile multifile,String filename) throws IOException{
		File smallfile = new File(FastDFSUtil.getPath()+"/"+filename);
//		multifile.
		reduceImage(multifile, smallfile,filename);
		
		return smallfile;
		
	}
	
	public static File saveSmallAbImage(MultipartFile multifile,String filename) throws IOException {
		File smallfile = new File(FastDFSUtil.getPath()+"/"+filename);
//		File newFile = new File(path.replace("csclabnormityfile", "smallcsclfile"));
		reduceImage(multifile, smallfile,filename);
		return smallfile;
	}
 
	
	
//	public static void saveSmallAbImage(File file) throws IOException {
//		String path = file.getPath();
//		File newFile = new File(path.replace("csclabnormityfile", "smallcsclfile"));
//		reduceImage(file, newFile);
//	}
 
	private static void reduceImage(MultipartFile multifile, File newFile,String fname) throws IOException {
		String fileType = fname.substring(fname.lastIndexOf(".")+1);
		if(!isImage(fileType)){
			return;
		}
		File pFile = newFile.getParentFile();
		if(!pFile.exists()){
			pFile.mkdirs();
		}
		InputStream in = multifile.getInputStream();
		OutputStream out = new FileOutputStream(newFile);
		double percent = Math.sqrt(1024.0*IMGSIZE/in.available());
		if(percent>0.8){
			IOUtils.copy(in, out);
		}else{
			try {
				ImageUtil.resizeImage(in, out, percent, fileType);
			} catch (Exception e) {
				if("JPEG".equalsIgnoreCase(fileType) ||"JPG".equalsIgnoreCase(fileType)){
					try {
						CMYK.toRgb(multifile, newFile);
					} catch (Exception e1) {
						throw new RuntimeException("图片转换失败！");
					}
				}else{
					throw new RuntimeException("图片转换失败！");
				}
			}
			finally{
				in.close();
				out.flush();
			}
		}
	}
	
	/*
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
		}else{
			try {
				ImageUtil.resizeImage(in, out, percent, fileType);
			} catch (Exception e) {
				if("JPEG".equalsIgnoreCase(fileType) ||"JPG".equalsIgnoreCase(fileType)){
					try {
						CMYK.toRgb(source, newFile);
					} catch (Exception e1) {
						throw new RuntimeException("图片转换失败！");
					}
				}else{
					throw new RuntimeException("图片转换失败！");
				}
			}
			finally{
				in.close();
				out.flush();
			}
		}
	}
	*/
	
	
	
	
	
	

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
//	public static void copyFile(File root) throws IOException {
//		File[] files = root.listFiles();
//		String path;
//		File newFile;
//		File parentFile;
//		for(File file: files){
//			path = file.getPath();
//			if(file.isDirectory()){
//				copyFile(file);
//			}else{
//				newFile = new File(path.replace("d:\\csclfile", "d:\\smallcsclfile"));
//				parentFile = newFile.getParentFile();
//				if(!parentFile.exists()){
//					parentFile.mkdirs();
//				}
//				reduceImage(file,newFile);
//				
//			}
//		}
//	}



}  