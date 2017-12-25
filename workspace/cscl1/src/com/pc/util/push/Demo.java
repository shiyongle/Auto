package com.pc.util.push;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.pc.controller.BaseController;
import com.pc.model.CL_Message;
import com.pc.util.push.android.AndroidBroadcast;
import com.pc.util.push.android.AndroidCustomizedcast;
import com.pc.util.push.android.AndroidFilecast;
import com.pc.util.push.android.AndroidGroupcast;
import com.pc.util.push.android.AndroidUnicast;
import com.pc.util.push.ios.IOSBroadcast;
import com.pc.util.push.ios.IOSCustomizedcast;
import com.pc.util.push.ios.IOSFilecast;
import com.pc.util.push.ios.IOSGroupcast;
import com.pc.util.push.ios.IOSUnicast;
@Controller
public class Demo extends BaseController {
	private static String timestamp = "35503035fc443bada64d38dfdb0dec4a";

	/*** Android 友盟注册信息 ***/
	private static String android_appkey = "56711d9f67e58ead63003d84";
	private static String android_appMasterSecret = "3f1oaxghnbf144mpcgcpp2bpooewtnhq";

	/*** ios 友盟注册信息 ***/
	private static String ios_appkey = "566f8c7567e58e71ac0019b8";
	private static String ios_appMasterSecret = "csdvbuajmabybfgtjyrezzwmar3ba7ny";

	/*** ios企业 友盟注册信息 ***/
	private static String ios_ent_appkey = "56e2335b67e58e71f9002302";
	private static String ios_ent_appMasterSecret = "mkulqi25xa15hg5vhxvxw3w1izeg4q3h";

	private static PushClient client = new PushClient();

	/**
	 * 推送信息给用户 简写
	 * 
	 * @param deviceToken
	 * @param msg
	 * @param ftype
	 */
	public static void SendUmeng(String alias, String msg) {
		Demo.SendUmeng(alias,msg, 1);
	}

	/**
	 * 推送信息给用户
	 * 
	 * @param deviceToken
	 *            用户设备编号
	 * @param ticker
	 *            推送锁屏显示
	 * @param title
	 *            推送主标题
	 * @param msg
	 *            推送内容
	 * @param ftype
	 *            推送设备类型（1-Android 2-iOS 3-iOS 企业）
	 */
	public static void SendUmeng(String alias,String msg,int ftype) {
		// 根据用户id 获取
		try {
			if (ftype == 1) {
				// Android
				Demo.sendAndroidCustomizedcast(android_appkey, android_appMasterSecret, alias, msg);
			} else if (ftype == 2) {
				// iOS
				Demo.sendIOSCustomizedcast(ios_appkey, ios_appMasterSecret, alias, msg);
			} else if (ftype == 3) {
				// iOS企业
				Demo.sendIOSCustomizedcast(ios_ent_appkey, ios_ent_appMasterSecret, alias, msg);
//				Demo.sendIOSUnicast(ios_ent_appkey, ios_ent_appMasterSecret, "5a65a4089a25a7da3a6ee149efe930568d84e7223cc6e373e0f46e4b937235ec", msg);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 推送信息内容获取
	 * @param content  内容
	 * @param fuserid 用户id
	 * @param ftype 类型1-货主 2-司机
	 * @return
	 */
	public static CL_Message savePushMessage(String content, int fuserid,int ftype)
	{
		return Demo.savePushMessage("一路好运", content, fuserid, ftype);
	}
	/**
	 * 推送信息内容获取
	 * @param title 标题
	 * @param content  内容
	 * @param fuserid 用户id
	 * @param ftype 类型1-货主 2-司机
	 * @return
	 */
	public static CL_Message savePushMessage(String title,String content, int fuserid,int ftype)
	{
		CL_Message model=new CL_Message();
		model.setTitle(title);
		model.setContent(content);
		model.setReceiver(fuserid);
		model.setType(ftype);
		model.setCreator("0");
		model.setFisread(0);
		model.setCreateTime(new Date());
		return model;
	}
	
	/**
	 * 推送信息内容获取
	 * @param title 标题
	 * @param content  内容
	 * @param fuserid 用户id
	 * @param ftype 类型1-货主 2-司机
	 * @return
	 */
	public static CL_Message savePushMessage(String title,String content, int fuserid,int ftype,int businessType)
	{
		CL_Message model=new CL_Message();
		model.setTitle(title);
		model.setContent(content);
		model.setReceiver(fuserid);
		model.setType(ftype);
		model.setCreator("0");
		model.setFisread(0);
		model.setCreateTime(new Date());
		model.setFbusinessType(businessType);
		return model;
	}
	
	/**
	 * 推送信息内容获取
	 * @param title 标题
	 * @param content  内容
	 * @param userPhone 用户手机号
	 * @param ftype 类型1-货主 2-司机
	 * @return
	 */
	public static CL_Message savePushMessage(String title,String content, String userPhone,int ftype,int businessType)
	{
		CL_Message model=new CL_Message();
		model.setTitle(title);
		model.setContent(content);
		model.setReceiverPhone(userPhone);
		model.setType(ftype);
		model.setCreator("0");
		model.setFisread(0);
		model.setCreateTime(new Date());
		model.setFbusinessType(businessType);
		return model;
	}
	
	public static void sendIOSCustomizedcast(String appkey, String appMasterSecret,String alias, String msg) throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them
		// if there are multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(alias, "ylhy");
		customizedcast.setAlert(msg);//,\"title\":\"\",\"subtitle\":\"lala\"
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		customizedcast.setDescription("ylhy");
		customizedcast.setProductionMode();
//		customizedcast.setProductionMode(true);
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		client.send(customizedcast);
	}

	// 自定义播
	public static void sendAndroidCustomizedcast(String appkey, String appMasterSecret,String alias, String msg) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(alias, "ylhy");
//		customizedcast.setTicker("");
//		customizedcast.setTitle("");
		customizedcast.setTicker("您有新信息，请注意查收");
		customizedcast.setTitle("一路好运");
		customizedcast.setText(msg);
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	// Android单播
	public static void sendAndroidUnicast(String appkey, String appMasterSecret, String deviceToken, String ticker,
			String title, String msg) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setTicker(ticker);
		unicast.setTitle(title);
		unicast.setText(msg);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}

	// IOS单播
	public static void sendIOSUnicast(String appkey, String appMasterSecret, String deviceToken, String msg)
			throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(msg);
		unicast.setBadge(0);
		unicast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}

	// 广播
	public static void sendAndroidBroadcast(String appkey, String appMasterSecret) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
		broadcast.setTicker("Android broadcast ticker");
		broadcast.setTitle("中文的title");
		broadcast.setText("Android broadcast text");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}

	// 组播
	public static void sendAndroidGroupcast(String appkey, String appMasterSecret) throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"test"}, {"tag":"Test"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		groupcast.setFilter(filterJson);
		groupcast.setTicker("Android groupcast ticker");
		groupcast.setTitle("中文的title");
		groupcast.setText("Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}

	public static void sendAndroidCustomizedcastFile(String appkey, String appMasterSecret) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	// 文件播
	public static void sendAndroidFilecast(String appkey, String appMasterSecret) throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setTicker("Android filecast ticker");
		filecast.setTitle("中文的title");
		filecast.setText("Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}

	public static void sendIOSBroadcast(String appkey, String appMasterSecret) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey, appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge(0);
		broadcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}

	public static void sendIOSGroupcast(String appkey, String appMasterSecret) throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"iostest"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge(0);
		groupcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		groupcast.setTestMode();
		client.send(groupcast);
	}

	public static void sendIOSFilecast(String appkey, String appMasterSecret) throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge(0);
		filecast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		filecast.setTestMode();
		client.send(filecast);
	}

}
