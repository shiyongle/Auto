package com.pc.util;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
//import org.apache.http.entity.mime.MultipartEntityBuilder; 
//import org.apache.http.entity.mime.content.FileBody; 
//import org.apache.http.entity.mime.content.StringBody; 
//import org.junit.Test; 
import org.apache.http.util.EntityUtils;

public class HttpClientPay{
  public void jUnitTest() {
    get(); 
  } 
  /** 
   * HttpClient连接SSL 
   */
  public void ssl() { 
    CloseableHttpClient httpclient = null; 
    try { 
      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
      FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore")); 
      try {
        // 加载keyStore d:\\tomcat.keystore  
        trustStore.load(instream, "123456".toCharArray()); 
      } catch (CertificateException e) { 
        e.printStackTrace(); 
      } finally { 
        try { 
          instream.close(); 
        } catch (Exception ignore) { 
        } 
      } 
      // 相信自己的CA和所有自签名的证书 
      SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build(); 
      // 只允许使用TLSv1协议 
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, 
          SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); 
      httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build(); 
      // 创建http请求(get方式) 
      HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action"); 
      System.out.println("executing request" + httpget.getRequestLine()); 
      CloseableHttpResponse response = httpclient.execute(httpget); 
      try { 
        HttpEntity entity = response.getEntity(); 
        System.out.println("----------------------------------------"); 
        System.out.println(response.getStatusLine()); 
        if (entity != null) { 
          System.out.println("Response content length: " + entity.getContentLength()); 
          System.out.println(EntityUtils.toString(entity)); 
          EntityUtils.consume(entity); 
        } 
      } finally { 
        response.close(); 
      } 
    } catch (ParseException e) { 
      e.printStackTrace(); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    } catch (KeyManagementException e) { 
      e.printStackTrace(); 
    } catch (NoSuchAlgorithmException e) { 
      e.printStackTrace(); 
    } catch (KeyStoreException e) { 
      e.printStackTrace(); 
    } finally { 
      if (httpclient != null) { 
        try { 
          httpclient.close(); 
        } catch (IOException e) { 
          e.printStackTrace(); 
           
        } 
      } 
    } 
  } 
 
  /** 
   * post方式提交表单（模拟用户登录请求） 
   */
  public void postForm() { 
    // 创建默认的httpClient实例.  
    CloseableHttpClient httpclient = HttpClients.createDefault(); 
    // 创建httppost  
    HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action"); 
    // 创建参数队列  
    List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
    formparams.add(new BasicNameValuePair("username", "admin")); 
 
    formparams.add(new BasicNameValuePair("password", "123456")); 
 
    UrlEncodedFormEntity uefEntity;
 
    try { 
 
      uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
 
      httppost.setEntity(uefEntity); 
 
      System.out.println("executing request " + httppost.getURI()); 
 
      CloseableHttpResponse response = httpclient.execute(httppost); 
 
      try { 
 
        HttpEntity entity = response.getEntity(); 
 
        if (entity != null) { 
 
          System.out.println("--------------------------------------"); 
 
          System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8")); 
 
          System.out.println("--------------------------------------"); 
 
        } 
 
      } finally { 
 
        response.close(); 
 
      } 
 
    } catch (ClientProtocolException e) { 
 
      e.printStackTrace(); 
       
 
    } catch (UnsupportedEncodingException e1) { 
 
      e1.printStackTrace(); 
 
    } catch (IOException e) { 
 
      e.printStackTrace(); 
 
    } finally { 
 
      // 关闭连接,释放资源  
 
      try { 
 
        httpclient.close(); 
 
      } catch (IOException e) { 
 
        e.printStackTrace(); 
 
      } 
 
    } 
 
  } 
  /** 
   * 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
   */
  public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		HttpPost post = postForm(url, params);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();

		String charset = EntityUtils.getContentCharSet(entity);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpost;
	}
	
  public static String postData(HashMap<String, String> map) {

    // 创建默认的httpClient实例.  
 
    CloseableHttpClient httpclient = HttpClients.createDefault(); 
 
    // 创建httppost  
 
    HttpPost httppost = new HttpPost(map.get("url")); 
 
    // 创建参数队列  
 
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
 
    formparams.add(new BasicNameValuePair("data", map.get("PayInfo")));
 
    UrlEncodedFormEntity uefEntity;
 
    try {
 
      uefEntity = new UrlEncodedFormEntity(formparams);
      httppost.setEntity(uefEntity);
      
      httppost.setHeader("Content-Type", "application/json;charset=utf-8");
//      httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      System.out.println("executing request " + httppost.getURI());
      CloseableHttpResponse response = httpclient.execute(httppost);
      
      try { 
        HttpEntity entity = response.getEntity();
 
        if (entity != null) { 
        	
          System.out.println("--------------------------------------"); 
 
          System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8")); 
 
          System.out.println("--------------------------------------");
          
          JSONObject jsonobject = JSONObject.fromObject(entity);
          return jsonobject.toString();
 
        } 
 
      } finally { 
 
        response.close(); 
 
      } 
 
    } catch (ClientProtocolException e) { 
 
      e.printStackTrace(); 
 
    } catch (UnsupportedEncodingException e1) { 
 
      e1.printStackTrace(); 
 
    } catch (IOException e) { 
 
      e.printStackTrace(); 
 
    } finally { 
 
      // 关闭连接,释放资源  
 
      try {
 
        httpclient.close(); 
 
      } catch (IOException e) {
 
        e.printStackTrace(); 
 
      }
 
    }
	return null;
 
  } 
 
  /** 
 
   * 发送 get请求 
   */
 
  public void get() { 
 
    CloseableHttpClient httpclient = HttpClients.createDefault(); 
 
    try { 
 
      // 创建httpget.  
 
      HttpGet httpget = new HttpGet("http://www.baidu.com/"); 
 
      System.out.println("executing request " + httpget.getURI()); 
 
      // 执行get请求.  
 
      CloseableHttpResponse response = httpclient.execute(httpget); 
 
      try { 
 
        // 获取响应实体  
         
 
        HttpEntity entity = response.getEntity(); 
 
        System.out.println("--------------------------------------"); 
 
        // 打印响应状态  
 
        System.out.println(response.getStatusLine()); 
 
        if (entity != null) { 
 
          // 打印响应内容长度  
 
          System.out.println("Response content length: " + entity.getContentLength()); 
 
          // 打印响应内容  
 
          System.out.println("Response content: " + EntityUtils.toString(entity)); 
 
        } 
 
        System.out.println("------------------------------------"); 
 
      } finally { 
 
        response.close(); 
 
      } 
 
    } catch (ClientProtocolException e) { 
 
      e.printStackTrace(); 
 
    } catch (ParseException e) { 
 
      e.printStackTrace(); 
 
    } catch (IOException e) { 
 
      e.printStackTrace(); 
 
    } finally { 
 
      // 关闭连接,释放资源  
 
      try { 
 
        httpclient.close(); 
 
      } catch (IOException e) {
 
        e.printStackTrace(); 
 
      }
 
    }
 
  }
 
}