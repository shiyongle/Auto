package com.pc.util.pay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.pc.util.JSONUtil;

public class PayAccessTool {
	private static final String KEY_HMACSHA512 = "123"; // 签名 秘钥
	private static final String SERVER = "http://121.40.160.69:8081/DJBankPay"; // 云测试服务器
	//private static final String SERVER = "http://192.168.2.18/DJBankPay/Action_Pay_V3/DJPay/Pay/"; // 测试

	// private static final String SERVER = "http://wl.cpsol.net/DJBankV3/Action_Pay_V3/DJPay/Pay/"; // 正式服务器

	public static PayResult submit(String url,
			LinkedHashMap<String, Object> params) throws Exception {
	//	Map<String, Map<String, Object>> map = sign(params); // 签名
     System.out.println(1);
	//	String postdata = encryptBASE64(JSONUtil.getJson(map));

		String postUrl =url;
		String strData = params.get("PayInfo").toString();//"&data=" + postdata;
		// String strData = "&XDEBUG_SESSION_START=17402&data=" + postdata;//服务端断点调试时使用

		String ret = doAccessHTTPPost(postUrl, strData, "");

		JSONObject json = JSONObject.fromObject(ret);
		PayResult result = new PayResult();
		if (json.get("success").equals(true)
				|| json.get("success").equals("true")) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		String msg = json.get("msg").toString();
		System.out.println(msg);
		String data = json.get("data").toString();

		result.setMsg(msg == "null" ? null : msg);
		result.setData(data == "null" ? null : data);
		return result;
	}

	private static Map<String, Map<String, Object>> sign(
			LinkedHashMap<String, Object> data) throws Exception {
		String sign = hmacSHA512(JSONUtil.getJson(data), KEY_HMACSHA512);
		data.put("Sign", sign);
		Map<String, Map<String, Object>> map = new HashMap();
		map.put("Data", data);
		return map;
	}

	private static String encryptBASE64(byte[] data) throws Exception {
		return Base64.encodeBase64String(data).replace("\r\n", "");
	}

	private static String encryptBASE64(String data) throws Exception {
		if (data == null) {
			return null;
		}
		return encryptBASE64(data.getBytes("utf-8"));
	}

	/**
	 * hmacSHA512 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 */
	private static String hmacSHA512(String data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException {
		String result = "";
		byte[] bytesKey = key.getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(bytesKey,
				"HmacSHA512");

		Mac mac = Mac.getInstance("HmacSHA512");
		mac.init(secretKey);
		final byte[] macData = mac.doFinal(data.getBytes("utf-8"));
		byte[] hex = new Hex().encode(macData);
		result = new String(hex);

		return result;
	}

	/**
	 * <p>
	 * POST方法
	 * </p>
	 * 
	 * @param sendUrl
	 *            ：访问URL
	 * @param paramStr
	 *            ：参数串
	 * @param backEncodType
	 *            ：返回的编码
	 * @return
	 * @throws Exception
	 */
	private static String doAccessHTTPPost(String sendUrl, String sendParam,
			String backEncodType) throws Exception {
        
		StringBuffer receive = new StringBuffer();
		BufferedWriter wr = null;
		BufferedReader rd = null;
		try {
			if (backEncodType == null || backEncodType.equals("")) {
				backEncodType = "UTF-8";
			}

			URL url = new URL(sendUrl);
			HttpURLConnection URLConn = (HttpURLConnection) url
					.openConnection();

			URLConn.setDoOutput(true);
			URLConn.setDoInput(true);
			((HttpURLConnection) URLConn).setRequestMethod("POST");
			URLConn.setUseCaches(false);
			URLConn.setAllowUserInteraction(true);
			HttpURLConnection.setFollowRedirects(true);
			URLConn.setInstanceFollowRedirects(true);

			URLConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			URLConn.setRequestProperty("Content-Length",
					String.valueOf(sendParam.getBytes().length));
			URLConn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");   
			DataOutputStream dos = new DataOutputStream(
					URLConn.getOutputStream());
			dos.writeBytes(sendParam);
           System.out.println(URLConn.getResponseCode());
			if (URLConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				rd = new BufferedReader(new InputStreamReader(
						URLConn.getErrorStream(), backEncodType));
				String line;
				StringBuffer error = new StringBuffer();
				while ((line = rd.readLine()) != null) {
					error.append(line).append("\r\n");
				}
				throw new Exception(error.toString());
			} else {
				rd = new BufferedReader(new InputStreamReader(
						URLConn.getInputStream(), backEncodType));
				String line;
				while ((line = rd.readLine()) != null) {
					receive.append(line).append("\r\n");
				}
			}
/*		} catch(Exception e){
			//TODO 写日志
			 return null;
*/		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException ex) {
					throw ex;
				}
				wr = null;
			}
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException ex) {
					throw ex;
				}
				rd = null;
			}
		}

		return receive.toString();
	}

}