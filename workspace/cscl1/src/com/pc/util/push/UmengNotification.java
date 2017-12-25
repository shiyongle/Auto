package com.pc.util.push;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class UmengNotification {
	// This JSONObject is used for constructing the whole request string.
	protected final JSONObject rootJson = new JSONObject();
	
	
	// The app master secret
	protected String appMasterSecret;
	
	// Keys can be set in the root level
	protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"appkey", "timestamp", "type", "device_tokens", "alias", "alias_type", "file_id", 
			"filter", "production_mode", "feedback", "description", "thirdparty_id"}));
	
	// Keys can be set in the policy level
	protected static final HashSet<String> POLICY_KEYS = new HashSet<String>(Arrays.asList(new String[]{
			"start_time", "expire_time", "max_send_num"
	}));
	
	// Set predefined keys in the rootJson, for extra keys(Android) or customized keys(IOS) please 
	// refer to corresponding methods in the subclass.
	public abstract boolean setPredefinedKeyValue(String key, Object value) throws Exception;
	public void setAppMasterSecret(String secret) {
		appMasterSecret = secret;
	}
	
	public String getPostBody(){
		JSONObject obj=new JSONObject();
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			obj.put("expire_time", dateFormat.format(new Date(System.currentTimeMillis()+30000)));
			rootJson.put("policy", obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		"policy": {
//        "expire_time": "2017-01-08 22:44:04"
//    },
//		
		
		return rootJson.toString();
	}
	
	protected final String getAppMasterSecret(){
		return appMasterSecret;
	}
	
	protected void setProductionMode(Boolean prod) throws Exception {
    	setPredefinedKeyValue("production_mode", prod.toString());
    }

	///正式模式
    public void setProductionMode() throws Exception {
    	setProductionMode(true);
    }

    ///测试模式
    public void setTestMode() throws Exception {
    	setProductionMode(false);
    }

    ///发送消息描述，建议填写。
    public void setDescription(String description) throws Exception {
    	setPredefinedKeyValue("description", description);
    }

    ///定时发送时间，若不填写表示立即发送。格式: "YYYY-MM-DD hh:mm:ss"。
    public void setStartTime(String startTime) throws Exception {
    	setPredefinedKeyValue("start_time", startTime);
    }
    ///消息过期时间,格式: "YYYY-MM-DD hh:mm:ss"。
    public void setExpireTime(String expireTime) throws Exception {
    	setPredefinedKeyValue("expire_time", expireTime);
    }
    ///发送限速，每秒发送的最大条数。
    public void setMaxSendNum(Integer num) throws Exception {
    	setPredefinedKeyValue("max_send_num", num);
    }

}
