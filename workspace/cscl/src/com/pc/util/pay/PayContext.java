package com.pc.util.pay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class PayContext {
//	private static Logger logger = Logger.getLogger(PayContext.class);
//	// **pcWeb 2.0支付
//	public static String PaySum(String url,String payData) throws DJException {
//		// HttpClientPay.postData(map);
//		return  SubmitUrlData(url,payData);
//	}
//
//	public static String paySumGetCoding(String url,String payData) throws DJException {
//		return  SubmitUrlDataMsg(url,payData);
//	}
//
//	// **pcWeb 2.0支付
//	public static String BingBanK(String url) throws DJException {
//		DongjingClient PayClient = new DongjingClient();
//		return this.SubmitUrlData(url);
//	}
//
//	// ***校验 特殊
//	public static String PaySumCheck(HashMap<String, String> map) throws DJException {
//		DongjingClient PayClient = new DongjingClient();
//		PayClient.setRequestProperty("VerificationCode", map.get("PayInfo"));
//		// PayClient.SubmitUrlData(map.get("url"));
//		return this.SubmitUrlData(map.get("url"));
//	}

	/**
	 * 错误码 返回提示
	 * 
	 * @param code
	 * @return 返回提示
	 */
	public static String responeCode(int code) {
		String msg = "";
		switch (code) {
		case 1001:
			msg = "支付系统，支付方式相同";
			break;
		case 1002:
			msg = "支付系统，1002支付方式相同";
			break;
		case 1003:
			msg = "支付系统，1003锁未解开";
			break;
		case 1004:
			msg = "支付系统，1004数据已存在";
			break;
		case 1005:
			msg = "支付系统，第三方校验失败";
			break;
		case 1006:
			msg = "支付系统，1006稍后重试";
			break;
		case 1007:
			msg = "支付系统，1007请求超时";
			break;
		case 1009:
			msg = "支付系统，不支持该银行";
			break;
		case 1010:
			msg = "支付系统，系统正在处理中";
			break;
		case 1011:
			msg = "支付系统，签名错误";
			break;
		case 1012:
			msg = "支付系统，数据类型错误";
			break;
		case 1013:
			msg = "支付系统，数据不能为空";
			break;
		case 1014:
			msg = "支付系统，数据格式错误";
			break;
		case 1015:
			msg = "绑卡过于频繁，一分钟后重试";
			break;
		case 1016:
			msg = "支付系统，银行返回错误!";
			break;
		case 1017:
			msg = "该银行未绑定";
			break;
		default:
			msg = "未知错误代码";
			break;
		}		
		
		return msg;
	}

	/**
	 * 发送 数据到支付服务(服务端无特殊数据返回 )
	 * 
	 * @param url
	 * @return 返回空字符串为返回成功 其他字符为出错信息
	 */
	public static String SubmitUrlDataMsg(String url,String payData) {
		String respData = SubmitUrlData(url,payData);
		String msg = "";
		try {
			JSONObject jso = new JSONObject(respData);
			boolean success = (boolean) jso.get("success");
			if (!success)
				msg = jso.getString("msg");// responeCode(jso.getInt("code"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "支付服务数据返回错误！";
		}
		return msg;
	}

	/**
	 * 发送 数据到支付服务
	 * 
	 * @param url
	 *            请求url
	 * @return 直接返回请求结果
	 */
	public static String SubmitUrlData(String url,String payData) {
		try {
			// Post请求的url，与get不同的是不需要带参数
			URL serverUrl = new URL(url); // "logonAction.do"
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
			// 打开读写属性，默认均为false
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			// 配置连接的Content-type，配置为application/x-
			// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
			connection.addRequestProperty("Accept-Charset", "UTF-8");// GB2312,
//			connection.addRequestProperty("Cookie", cookie);
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			connection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String content = payData;

			// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
			byte[] keyByte = content.getBytes("UTF8");
			content = new String(keyByte, "ISO-8859-1");
			out.writeBytes(content);

			out.flush();

			out.close(); // flush and close
//			if (connection.getHeaderFields().get("Set-Cookie") != null) {
//				cookie = "";
//				for (String s : connection.getHeaderFields().get("Set-Cookie")) {
//					cookie += s;
//				}
//			}
//			logger.info("支付请求URL：" + url + " ，支付请求Data：" + payData);
//			System.out.println("支付请求URL：" + url + "，time：" + new Date() +" ，支付请求Data：" + payData);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String lines = "";
			StringBuffer bankXmlBuffer = new StringBuffer();
			while ((lines = reader.readLine()) != null) {
				bankXmlBuffer.append(lines);
			}
			reader.close();

			//这里需要将数据保存到库中
			
			return bankXmlBuffer.toString();

		} catch (Exception e) {
			// TODO: handle exception
			
			//这里需要将数据保存到库中
			return "";
		}
	}


}
