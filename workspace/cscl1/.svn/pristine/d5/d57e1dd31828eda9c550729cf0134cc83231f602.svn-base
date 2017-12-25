package com.pc.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @date 2016年8月2日
 * 
 * @author 叶千阁
 *
 */
public class FastDFSUtilParent {
//	private static FastDFSBean fastClient ;
	private static int length = 1;
//	private static String IPADDRESS = "http://192.168.10.159";

	// 获取TrackServer
//	protected static TrackerServer getTrackServer() throws IOException {
//		FastDFSBean fastClient = new FastDFSBean();
//		return fastClient.getTrackerServer();
//	}
//	protected static void closeServer() throws IOException{
//		fastClient.close();
//	}

	// 获取StorageClient
//	protected static StorageClient getStorageClient() throws IOException {
//		fastClient = new FastDFSBean();
//		return fastClient.getStorageClient();
//	}

	// 通过返回的数组得到url
	protected static String getUrl(String[] strings) throws IOException {
		return strings[0] + "/" + strings[1];
	}
	

	// 文件转byte数组
	protected static byte[] readFileToByteArray(File file) throws IOException {
		return FileUtils.readFileToByteArray(file);
	}

	// 通过文件来获取后缀名
	protected static String readFileGetLastName(File file) {
		return FilenameUtils.getExtension(file.getName());
	}
	
	public static String getIpAddress() {
		return "";
	}
	
	public static String getTrackIpAddress() {
		ClassPathResource cpr = new ClassPathResource("client.conf");
		File file = null ;
		String fileString = null;
		try {
			file = cpr.getFile();
			fileString = FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result ="http://"+ fileString.substring(fileString.lastIndexOf("tracker_server=")+"tracker_server=".length(), fileString.indexOf(":22122"));
		return result;
	}

	protected static String[] UrlToStringArry(String url) {
		return url.split("/", 2);
	}

	protected static String getAbsolutePath() {
		File file = new File("");
		return file.getAbsolutePath();
	}

	// 获取工作目录的路劲
	public static String getPath() {
		String[] strings = getAbsolutePath().split("\\\\");
		String path = strings[0];
		length = strings.length - 1;
		for (int i = 1; i < length; i++) {
			path = path + "/" + strings[i];
		}
		return path;
	}

	// 生成zip文件的路劲
	protected static String createZipPath(String zipName) {
		return getPath() + "/" + zipName + ".zip";
	}

	// 生成zip文件
	protected static File createFileZip(String zipName) {
		String path = createZipPath(zipName);
		return new File(path);
	}

	// 根据文件产生 ZipOutputStream
	protected static ZipOutputStream createZipOut(File filezip) throws FileNotFoundException {
		FileOutputStream fous = new FileOutputStream(filezip);
		return new ZipOutputStream(fous);
	}
	
	public static String getLastName(String url){
		return url.substring(url.lastIndexOf(".")+1);
	}

}
