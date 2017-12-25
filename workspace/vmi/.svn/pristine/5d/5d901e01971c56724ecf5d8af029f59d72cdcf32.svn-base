package Com.Base.Util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.poi.util.IOUtils;

import sun.awt.image.ImageFormatException;


public class ImageUtil {
  
	private static int IMGSIZE = 80;	// KB
	
	private static Pattern pattern = Pattern.compile("jpg|png|gif|bmp|JPG|PNG|GIF|BMP");
	
	public static boolean isImage(String fileType) {
		if(fileType.contains(".")){
			fileType = fileType.substring(fileType.lastIndexOf(".")+1);
		}
		Matcher matcher = pattern.matcher(fileType);
		return matcher.matches();
	}
	
    public static void resizeImage(InputStream is, OutputStream os,double percent, String format) throws IOException, ImageFormatException {
		try {
			BufferedImage prevImage = ImageIO.read(is);
			double width = prevImage.getWidth();  
			double height = prevImage.getHeight();  
			int newWidth = (int)(width * percent);  
			int newHeight = (int)(height * percent);  
			BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);  
			Graphics graphics = image.createGraphics();  
			graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
			ImageIO.write(image, format, os);
			os.flush();
		}finally{
			if(os instanceof FileOutputStream){
				os.close();
			}
			if(is instanceof FileInputStream){
				is.close();
			}
		}
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
			in.close();
			out.close();
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
		}
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



}  