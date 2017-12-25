package Com.Base.Util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
//	protected static final Logger LOG = LoggerFactory
//			.getLogger(JPushUtil.class);

	// demo App defined in resources/jpush-api.conf
	private static final String appKey = "1f800afd92b83a35d47c9c35";
	private static final String masterSecret = "ded797f5cd29aa2092534044";
	private static final String appKeyApp = "59501933fdcecdea22c1ffa2";//苹果
	private static final String masterSecretApp = "f9f9a9e5c8905b0987341174";//苹果
	
	public static final String TITLE = "Test from API example";
	public static final String ALERT = "Test from API Example - alert";
	public static final String MSG_CONTENT = "Test from API Example - msgContent";
	public static final String REGISTRATION_ID = "0900e8d85ef";
	public static final String TAG = "tag_api";

//	public static void main(String[] args) {
//		SendPushToAll("aaa", "收到货卡号的卡号");
//	}

	/**
	 *  向ios、android推送
	 * @param user
	 * @param content
	 */
	public static void SendPushToAll(String user, String content) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

		PushPayload payload = buildPushObject_android_and_ios(user, content);

		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println("Got result - " + result);

		} catch (APIConnectionException e) {
			System.out.println("Connection error. Should retry later. "+ e);

		} catch (APIRequestException e) {
			System.out.println(
					"Error response from JPush server. Should review and fix it. "+
					e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}

	
	/**
	 *  向ios、推送(另外appKey)
	 * @param user
	 * @param content
	 */
	public static void SendPushToIOS(String user, String content) {
		JPushClient jpushClient = new JPushClient(masterSecretApp, appKeyApp, 3);

		PushPayload payload = buildPushObjectWithios(user, content);

		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println("Got result - " + result);

		} catch (APIConnectionException e) {
			System.out.println("Connection error. Should retry later. "+ e);

		} catch (APIRequestException e) {
			System.out.println(
					"Error response from JPush server. Should review and fix it. "+
					e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}
	
	/**
	 * 广播所有
	 * @param content
	 */
	public static void broadcast(String content) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

		PushPayload payload = buildPushObject_all_all_alert(content);

		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println("Got result - " + result);

		} catch (APIConnectionException e) {
			System.out.println("Connection error. Should retry later. "+ e);

		} catch (APIRequestException e) {
			System.out.println(
					"Error response from JPush server. Should review and fix it. "+
					e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}

	// 推送所有
	public static PushPayload buildPushObject_all_all_alert(String content) {
		return PushPayload.alertAll(content);
	}

	// 别名推送
	public static PushPayload buildPushObject_all_alias_alert() {
		return PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(Audience.alias("aaa"))
				.setNotification(Notification.alert(ALERT)).build();
	}

	// 安卓 标签推送
	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.tag("tag1"))
				.setNotification(Notification.android(ALERT, TITLE, null))
				.build();
	}

	// android、ios标签推送
	public static PushPayload buildPushObject_android_and_ios(String user,
			String content) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.tag(user))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(content)
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.setTitle("CPS智能服务平台").build())
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setContentAvailable(true)
												.incrBadge(1)
												.addExtra("from", "JPush")
												.build()).build())
								.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}
	
	
	//ios标签推送
	public static PushPayload buildPushObjectWithios(String user,
			String content) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.tag(user))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(content)
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setContentAvailable(true)
												.incrBadge(1).setSound("happy.caf")
												.addExtra("from", "JPush")
												.build()).build())
												.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}

	// ios标签推送
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.tag_and("tag1", "tag_all"))
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setAlert(ALERT).setBadge(5)
												.setSound("happy")
												.addExtra("from", "JPush")
												.build()).build())
				.setMessage(Message.content(MSG_CONTENT))
				.setOptions(
						Options.newBuilder().setApnsProduction(true).build())
				.build();
	}

	// android、ios标签加别名推送
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(
										AudienceTarget.tag("tag1", "tag2"))
								.addAudienceTarget(
										AudienceTarget
												.alias("alias1", "alias2"))
								.build())
				.setMessage(
						Message.newBuilder().setMsgContent(MSG_CONTENT)
								.addExtra("from", "JPush").build()).build();
	}

}
