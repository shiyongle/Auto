package com.pc.util.file;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @date 2016年8月2日
 * 
 * @author 叶千阁
 *
 */
public class FastDFSBean {
	private String clientName = "conf/spring/client.conf";
	private TrackerClient trackerClient = null;
	private StorageServer storageServer = null;
	private TrackerServer trackerServer = null;
	private StorageClient storageClient = null;
//	private static FastDFSBean fastDFSBean = new FastDFSBean();
	
//	public static FastDFSBean getFastDFSBean(){
//		return fastDFSBean;
//	}
	
	public FastDFSBean() {
		try {
			ClassPathResource cpr = new ClassPathResource(clientName);
			ClientGlobal.init(cpr.getClassLoader().getResource(clientName).getPath());
			this.trackerClient = new TrackerClient();
			this.trackerServer = trackerClient.getConnection();
			this.storageClient = new StorageClient(trackerServer, storageServer);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public void close() throws IOException{
		if (storageServer!=null) {
			this.storageServer.close();
		}
		if (trackerServer!=null) {
			
			this.trackerServer.close();
		}
		
	}

	public TrackerClient getTrackerClient() {
		return trackerClient;
	}

	public StorageServer getStorageServer() {
		return storageServer;
	}

	public TrackerServer getTrackerServer() {
		return trackerServer;
	}

	public StorageClient getStorageClient() {
		return storageClient;
	}

}
