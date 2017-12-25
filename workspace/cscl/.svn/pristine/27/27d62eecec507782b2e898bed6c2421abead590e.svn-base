package com.pc.appInterface.api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

import com.pc.util.Base64;
public class NewDongjingClient {

private String Url;
private String cookie = "";
private boolean success = false;
private boolean connected = false;
private String msgstr;
private DongjingResponse Response;
private String mbsn;
private String username;
private String password;
private HttpURLConnection connection;
private String Charset;
private String Method;
private HashMap<String, String> RequestProperty;
private String recvice;

public NewDongjingClient() {
	RequestProperty = new HashMap<String, String>();
	Response = new DongjingResponse();
	Charset = "UTF-8";
	// mbsn=getCPUSerial()+getMotherboardSN()+getHardDiskSN("c");
	mbsn = "Andorid";
	// System.out.print(mbsn);
}

public void setRequestProperty(String pk, String pv) {
	RequestProperty.put(pk, pv);
}

public void clearRequestProperty() {
	RequestProperty.clear();
}

public String getURL() {
	return Url;
}

public void setURL(String uRL) {
	if (uRL.endsWith("/")) {
		Url = uRL;
	} else {
		Url = uRL + "/";
	}

}

public String getMethod() {
	return Method;
}

public String getRecvice() {
	return recvice;
}

public void setRecvice(String recvice) {
	this.recvice = recvice;
}

public void setMethod(String method) {
	Method = method;
	clearRequestProperty();
}

private String GetContent() throws UnsupportedEncodingException {
	String result = "AppType=dongjingSDK&";
	if (RequestProperty.size() > 0) {
		String key;
		Set<String> set = RequestProperty.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			if (!result.equals("AppType=dongjingSDK&")) {
				result = result + "&";
			}
			key = it.next();
			result = result + key + "=" + URLEncoder.encode(RequestProperty.get(key).toString(), "utf-8");
		}
	}
	return result;
}



public DongjingResponse getResponse() {
	return Response;
}

public String getCharset() {
	return Charset;
}

public void setCharset(String charset) {
	Charset = charset;
}

public String getMsgstr() {
	return msgstr;
}

public boolean issuccess() {
	return success;
}

public boolean isconnected() {
	return connected;
}

public void setUsername(String username) {
	this.username = username;
}

public void setPassword(String password) {
	this.password = password;
}

// private String getCPUSerial() {
// String result = "";
// String errs = "";
// try {
// String line;
// File file = File.createTempFile("realhowto", ".vbs");
// file.deleteOnExit();
// if (!(file.exists())) {
// file.createNewFile();
// }
// FileWriter fw = new FileWriter(file);
// String vbs = "Set objWMIService =
// GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems =
// objWMIService.ExecQuery _ \n (\"Select * from Win32_Processor\") \nFor
// Each objItem in colItems \n Wscript.Echo objItem.ProcessorId \n exit for
// ' do the first cpu only! \nNext \n";
//
// fw.write(vbs);
// fw.close();
// Process p = Runtime.getRuntime().exec(
// "cscript //NoLogo " + file.getPath());
// BufferedReader input = new BufferedReader(new InputStreamReader(
// p.getInputStream()));
//
// while ((line = input.readLine()) != null)
// result = result + line;
//
// input.close();
// file.delete();
// } catch (Exception e) {
// errs = e.getMessage();
// e.fillInStackTrace();
// }
// if ((result.trim().length() < 1) || (result == null))
// {
// result = "获取C错误:"+ errs;
// msgstr=result;
// }
// return result.trim();
// }
// private String getHardDiskSN(String drive) {
// String result = "";
// try {
// String line;
// File file = File.createTempFile("realhowto", ".vbs");
// drive=file.getPath().substring(0,1);
// file.deleteOnExit();
// FileWriter fw = new FileWriter(file);
//
// String vbs = "Set objFSO =
// CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives =
// objFSO.Drives\nSet objDrive = colDrives.item(\""
// + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
// fw.write(vbs);
// fw.close();
// Process p = Runtime.getRuntime().exec(
// "cscript //NoLogo " + file.getPath());
// BufferedReader input = new BufferedReader(new InputStreamReader(
// p.getInputStream()));
//
// while ((line = input.readLine()) != null)
// result = result + line;
//
// input.close();
// } catch (Exception e) {
// e.printStackTrace();
// msgstr="获取H错误:"+e.getMessage();
// }
// return result.trim();
// }
// private String getMotherboardSN() {
// String result = "";
// try {
// String line;
// File file = File.createTempFile("realhowto", ".vbs");
// file.deleteOnExit();
// FileWriter fw = new FileWriter(file);
//
// String vbs = "Set objWMIService =
// GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems =
// objWMIService.ExecQuery _ \n (\"Select * from Win32_BaseBoard\") \nFor
// Each objItem in colItems \n Wscript.Echo objItem.SerialNumber \n exit for
// ' do the first cpu only! \nNext \n";
//
// fw.write(vbs);
// fw.close();
// Process p = Runtime.getRuntime().exec(
// "cscript //NoLogo " + file.getPath());
// BufferedReader input = new BufferedReader(new InputStreamReader(
// p.getInputStream()));
//
// while ((line = input.readLine()) != null)
// result = result + line;
//
// input.close();
// } catch (Exception e) {
// e.printStackTrace();
// msgstr="获取M错误:"+e.getMessage();
// }
// return result.trim();
// }
private static String getNowDate() {
	Date currentTime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String dateString = formatter.format(currentTime);
	return dateString;
}

// 加密
private static String encrypt(String sSrc) throws Exception {
	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	// byte[] raw = sKey.getBytes();
	byte[] raw = GetSkey().getBytes();
	SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	// IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//
	// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
	IvParameterSpec iv = new IvParameterSpec(GetSkey().getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
	cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
	return new Base64().encode(encrypted);// 此处使用BASE64做转码。
}

private static String GetSkey() {
	return String.valueOf(Math.PI).substring(0, 16);
}

private void GetURL(String Method) {

	try {
		URL getUrl;
		Response = new DongjingResponse();
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String content = GetContent();
		if (content.equals("")) {
			getUrl = new URL(Url + Method + ".do");// "GetUserList.do"
		} else {
			getUrl = new URL(Url + Method + ".do?" + content);// "GetUserList.do"
		}

		// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
		// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection

		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.addRequestProperty("Cookie", cookie);
		connection.addRequestProperty("Accept-Charset", Charset);// GB2312,

		// 建立与服务器的连接，并未发送数据

		connection.connect();

		// 发送数据到服务器并使用Reader读取返回的数据

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		if (connection.getHeaderFields().get("Set-Cookie") != null) {
			cookie = "";
			for (String s : connection.getHeaderFields().get("Set-Cookie")) {
				cookie += s;
			}
		}
		// while ((Resultdata = reader.readLine()) != null) {
		//
		// System.out.println(Resultdata);
		//
		// }
		String lines = "";
		while ((lines = reader.readLine()) != null) {
			Response.setResultdata(lines);
			// System.out.println(lines);
		}
		if (!Response.getResultString().startsWith("{")) {
			this.success = false;
			this.msgstr = "返回信息异常,请重新连接登录";
		}
		reader.close();

		// 断开连接

		// connection.disconnect();
	} catch (Exception e) {
		// TODO: handle exception
		// System.out.println(e.toString());
		success = false;
		msgstr = e.getMessage().substring(0,
				e.getMessage().indexOf("URL") == -1 ? e.getMessage().length() : e.getMessage().indexOf("URL"));
		// throw e;
	}

}

private void PostURL(String Method) {

	try {
		Response = new DongjingResponse();
		// Post请求的url，与get不同的是不需要带参数

		URL serverUrl = new URL(Url + Method + ".do"); // "logonAction.do"

		// 打开连接
		connection = (HttpURLConnection) serverUrl.openConnection();

		// 打开读写属性，默认均为false

		connection.setDoOutput(true);

		connection.setDoInput(true);
		connection.setConnectTimeout(65000);

		// 设置请求方式，默认为GET

		connection.setRequestMethod("POST");

		// Post 请求不能使用缓存

		connection.setUseCaches(false);

		// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。

		// connection.setFollowRedirects(true);

		// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数

		connection.setInstanceFollowRedirects(true);

		// 配置连接的Content-type，配置为application/x-
		// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
		connection.addRequestProperty("Accept-Charset", Charset);// GB2312,
		connection.addRequestProperty("Cookie", cookie);
		connection.setRequestProperty(" Content-Type ",
				
//				" application/x-www-form-urlencoded ");
				" application/json ");
		connection.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，

		// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略

		// connection.connect();

		DataOutputStream out = new DataOutputStream(connection

				.getOutputStream());

		// 正文内容其实跟get的URL中'?'后的参数字符串一致

		String content = GetContent();// "password="+MD5helper.MD5(password)+"&username="+username+"&L="+encrypt(getNowDate()
										// + "," + mbsn);

		// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面

		out.writeBytes(content);

		out.flush();

		out.close(); // flush and close
		if (connection.getHeaderFields().get("Set-Cookie") != null) {
			cookie = "";
			for (String s : connection.getHeaderFields().get("Set-Cookie")) {
				cookie += s;
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String lines = "";
		while ((lines = reader.readLine()) != null) {
			Response.setResultdata(lines);
			// System.out.println(lines);
		}
		reader.close();
		this.success = this.Response.isSuccess();
		this.msgstr = this.Response.getMsgstr();

	} catch (Exception e) {
		// TODO: handle exception
		// System.out.println(e.toString());
		success = false;
		msgstr = e.getMessage().substring(0,
				e.getMessage().indexOf("URL") == -1 ? e.getMessage().length() : e.getMessage().indexOf("URL"));
		// throw e;
	}

}

public boolean connect() {
	try {
		msgstr = "";
		// "password="+MD5helper.MD5(password)+"&username="+username+"&L="+encrypt(getNowDate()
		// + "," + mbsn);
		/*setRequestProperty("fpassword", MD5helper.MD5(password));
		setRequestProperty("fname", username);
		setRequestProperty("L", encrypt(getNowDate() + "," + mbsn));
		if (!msgstr.equals("")) {
			connected = false;
			return false;
		}
		PostURL("csclLogon");*/
		connected = true;
	} catch (Exception e) {
		// TODO: handle exception
		msgstr = e.toString();
		connected = false;
	}

	return connected;
}

public boolean GetData() {
	if (connected) {
		GetURL(this.Method);
		this.success = Response.isSuccess();
	}
	return success;
}

public boolean SubmitData() {
	if (connected) {
		PostURL(this.Method);
		this.success = Response.isSuccess();
	}
	return success;
}


//		// 直接POST发送URL，以后再抽类 BY CC 2016-03-03 end
//
//		public String SubmitUrlDataToString(String url) {
//
//			StringBuffer receive = new StringBuffer();
//			try {
//				Response = new DongjingResponse();
//				// Post请求的url，与get不同的是不需要带参数
//
//				URL serverUrl = new URL(url); // "logonAction.do"
//
//				// 打开连接
//				connection = (HttpURLConnection) serverUrl.openConnection();
//
//				// 打开读写属性，默认均为false
//
//				connection.setDoOutput(true);
//
//				connection.setDoInput(true);
//
//				// 设置请求方式，默认为GET
//
//				connection.setRequestMethod("POST");
//
//				// Post 请求不能使用缓存
//
//				connection.setUseCaches(false);
//
//				// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。
//
//				// connection.setFollowRedirects(true);
//
//				// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数
//
//				connection.setInstanceFollowRedirects(true);
//
//				// 配置连接的Content-type，配置为application/x-
//				// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
//				connection.addRequestProperty("Accept-Charset", Charset);// GB2312,
//				connection.addRequestProperty("Cookie", cookie);
//				connection.setRequestProperty("ContentType",
//
//						"application/x-www-form-urlencoded;charset=utf-8"); //
//				// connection.setRequestProperty("Content-Type",
//				//
//				// "application/x-www-form-urlencoded"); //
//
//				connection.addRequestProperty("User-Agent",
//						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
//				// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
//
//				// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
//
//				// connection.connect();
//
//				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//				String content = GetContent();// "password="+MD5helper.MD5(password)+"&username="+username+"&L="+encrypt(getNowDate()
//												// + "," + mbsn);
//
//				// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
//
//				out.writeBytes(content);
//
//				out.flush();
//
//				out.close(); // flush and close
//				if (connection.getHeaderFields().get("Set-Cookie") != null) {
//					cookie = "";
//					for (String s : connection.getHeaderFields().get("Set-Cookie")) {
//						cookie += s;
//					}
//
//				}
//
//				return inputStreamToString(connection.getInputStream());
//			} catch (Exception e) {
//				// TODO: handle exception
//				return "{\"success\":\"false\"}";
//			}
//			// return success;
//		}

//		private String inputStreamToString(InputStream in) {
//			StringBuffer out = new StringBuffer();
//			BufferedReader input = null;
//			try {
//
//				input = new BufferedReader(new InputStreamReader(in, "utf-8"));
//				String s = "";
//				while ((s = input.readLine()) != null) {
//					out.append(s);
//				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				return "";
//			} catch (IOException e) {
//				e.printStackTrace();
//				return "";
//			}
//			return out.toString();
//		}

// public static void main(String[] args) throws Exception
// {
// DongjingClient c=new DongjingClient();
// c.setURL("http://192.168.0.89:8080/vmi");
// c.setUsername("冯卉卉");//冯卉卉
// c.setPassword("123");
// if(!c.connect())
// {
// System.out.print(c.getMsgstr());
// }
// System.out.print(c.getMsgstr());
// c.setMethod("GetUserInfo");
// c.setRequestProperty("fid","05bd3268-638e-11e3-b2ee-60a44c5bbef3");
// if(c.GetData())
// {
// System.out.print(c.getResponse().getResultString());
// //c.getResponse().getResultitems().get(0).getBoolean("fisfilter");
// }
// else
// {
// System.out.print(c.getMsgstr());
// }
// //c.getuser();
// c.setMethod("EffectUserList");
// c.setRequestProperty("fidcls","05bd3268-638e-11e3-b2ee-60a44c5bbef3");//feffect
// c.setRequestProperty("feffect","1");//feffect
// if(c.SubmitData())//EffectUserList
// {
// System.out.print(c.getResponse().getResultString());
// }
// else
// {
// System.out.print(c.getMsgstr());
// }
//
// }
//

//
//
// public void getuser() throws IOException
// {
// try {
// // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
// URL getUrl = new URL(Url+"GetUserList.do");
//
// // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
//
// HttpURLConnection connection = (HttpURLConnection)
// getUrl.openConnection();
// connection.addRequestProperty("Cookie", cookie);
// connection.addRequestProperty("Accept-Charset", "UTF-8");// GB2312,
//
// // 建立与服务器的连接，并未发送数据
//
// connection.connect();
//
// // 发送数据到服务器并使用Reader读取返回的数据
//
// BufferedReader reader = new BufferedReader(new
// InputStreamReader(connection.getInputStream(),"UTF-8"));
//
// System.out.println(" ============================= ");
//
// System.out.println(" Contents of get request ");
//
// System.out.println(" ============================= ");
//
// String lines;
//
// while ((lines = reader.readLine()) != null) {
//
// System.out.println(lines);
//
// }
//
// reader.close();
//
// // 断开连接
//
// //connection.disconnect();
//
// System.out.println(" ============================= ");
//
// System.out.println(" Contents of get request ends ");
//
// System.out.println(" ============================= ");
// } catch (Exception e) {
// // TODO: handle exception
// System.out.println(e.toString());
// }
//
// }
// public boolean login() throws IOException{
// try {
// // Post请求的url，与get不同的是不需要带参数
//
// URL serverUrl=new URL(Url+"logonAction.do");
//
// // 打开连接
// connection =(HttpURLConnection) serverUrl.openConnection();
//
// //打开读写属性，默认均为false
//
// connection.setDoOutput(true);
//
// connection.setDoInput(true);
//
// // 设置请求方式，默认为GET
//
// connection.setRequestMethod("POST");
//
// // Post 请求不能使用缓存
//
// connection.setUseCaches(false);
//
// // URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。
//
// // connection.setFollowRedirects(true);
//
// //URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数
//
// connection.setInstanceFollowRedirects(true);
//
// // 配置连接的Content-type，配置为application/x-
// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
// connection.addRequestProperty("Accept-Charset", "UTF-8;");// GB2312,
// connection.setRequestProperty(" Content-Type ",
//
// " application/x-www-form-urlencoded ");
// connection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U;
// Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
// // 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
//
// // 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略
//
// //connection.connect();
//
// DataOutputStream out = new DataOutputStream(connection
//
// .getOutputStream());
//
// //正文内容其实跟get的URL中'?'后的参数字符串一致
//
// String content =
// "password="+MD5helper.MD5(password)+"&username="+username+"&L="+encrypt(getNowDate()
// + "," + mbsn);
//
// // DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
//
// out.writeBytes(content);
//
// out.flush();
//
// out.close(); // flush and close
// if (connection.getHeaderFields().get("Set-Cookie") != null) {
// cookie="";
// for (String s : connection.getHeaderFields().get("Set-Cookie")) {
// cookie += s;
// }
// }
//
//
// BufferedReader reader = new BufferedReader(new
// InputStreamReader(connection.getInputStream(),"UTF-8"));
//
// String line;
//
// System.out.println(" ============================= ");
//
// System.out.println(" Contents of post request ");
//
// System.out.println(" ============================= ");
//
// while ((line = reader.readLine()) != null) {
// System.out.println(line);
//
// }
//
// System.out.println(" ============================= ");
//
// System.out.println(" Contents of post request ends ");
//
// System.out.println(" ============================= ");
//
// reader.close();
// } catch (Exception e) {
// // TODO: handle exception
// System.out.println(e.toString());
// }
//
//
//
// // connection.disconnect();
//
// return true;
// }
	

}
