package Com.Base.Util.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * 多文件上传配套助手单例类
 * 
 * 一般只需调用upload即可，有几个文件就调用几次（没有分块上传的情况下）
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2014-8-28 上午10:59:45
 */
public class MyMultiFileUploadHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int BUFFER_SIZE = 2 * 1024;

	private static MyMultiFileUploadHelper myMultiFileUploadHelper;

	private String name;
	private String uploadFileName;
	// 文件类型
	private String uploadContentType;
	// 大文件上传 分块chul

	private String relativePath;

	private HttpServletRequest httpServletRequest;

	private MyMultiFileUploadHelper() {
		// TODO Auto-generated constructor stub
	}

	private void copy(File dst, HttpServletRequest httpServletRequest) {
		InputStream in = null;
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true),
						BUFFER_SIZE);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
			}

			in = httpServletRequest.getInputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 英文用这个
	 * @param relativePath
	 *            ,相对路径，用“\\”隔开
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 * 
	 * @date 2014-8-28 上午11:01:04 (ZJZ)
	 */
	public String upload(String relativePath,
			HttpServletRequest httpServletRequest) throws Exception {

		return upload(relativePath, httpServletRequest.getParameter("name"),
				httpServletRequest);
	}

	/**
	 * 
	 * 中文用这个，自己传入文件名
	 * 
	 * @param relativePath
	 *            ,相对路径，用“\\”隔开
	 * @param name
	 *            ,文件名
	 * @param httpServletRequest
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @date 2014-8-28 上午11:01:04 (ZJZ)
	 */
	public String upload(String relativePath, String name,
			HttpServletRequest httpServletRequest) throws Exception {

		String dstPath = httpServletRequest.getServletContext().getRealPath(
				relativePath)
				+ "\\" + name;

		// 赋值到实例属性
		this.relativePath = relativePath;
		this.httpServletRequest = httpServletRequest;

		File dstFile = new File(dstPath);

		// 获得父目录
		File parent = dstFile.getParentFile();

		// 不存在就创建父目录
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}

		// int chunk =
		// Integer.parseInt(httpServletRequest.getParameter("chunk"));
		// int chunks = Integer
		// .parseInt(httpServletRequest.getParameter("chunks"));
		//
		// // 文件已存在（上传了同名的文件）
		// if (chunk == 0 && dstFile.exists()) {
		// dstFile.delete();
		// dstFile = new File(dstPath);
		// }

		copy(dstFile, httpServletRequest);

		// System.out.println("上传文件:" + uploadFileName + " 临时文件名："
		// + uploadContentType + " " + chunk + " " + chunks);
		// if (chunk == chunks - 1) {
		// // 完成一整个文件;
		// }

		return "success";
	}

	/**
	 * 获取结果
	 * 
	 * @return
	 * 
	 * @date 2014-8-28 上午11:01:25 (ZJZ)
	 */
	public String queryResult() {
		String filePath = httpServletRequest.getServletContext().getRealPath(
				relativePath);
		System.out.println("保存文件路径： " + filePath);

		HttpServletRequest request = httpServletRequest;

		String result = "";

		int count = Integer.parseInt(request.getParameter("uploader_count"));
		for (int i = 0; i < count; i++) {
			uploadFileName = request.getParameter("uploader_" + i + "_name");
			name = request.getParameter("uploader_" + i + "_tmpname");
			System.out.println(uploadFileName + " " + name);
			try {
				// do something with file;
				result += uploadFileName + "导入完成. <br />";
			} catch (Exception e) {
				result += uploadFileName + "导入失败:" + e.getMessage()
						+ ". <br />";
				e.printStackTrace();
			}
		}
		return result;
	}

	public static MyMultiFileUploadHelper getMyMultiFileUploadHelper() {

		if (myMultiFileUploadHelper == null) {

			myMultiFileUploadHelper = new MyMultiFileUploadHelper();

		}

		return myMultiFileUploadHelper;
	}

}
