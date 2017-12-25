package Com.Base.Util;

import Com.Base.Util.push.Push;


public class JPushUtilNew {

	/**
	 *  向ios、android推送
	 * @param user
	 * @param content
	 */
	public static void SendPushToAll(String user, String content) {
		Push android = new Push("5624a5f2e0f55a8384000207", "tuobqdouridd4flizqnrjgmq4tpcyobn");
		Push ios = new Push("5624a65267e58e0e3a001e1a", "z1g0bizscazvcqhuk8qlhfxd7pdq389v");
		try {
			android.sendAndroidGroupcast(user,"CPS智能服务平台",content);
			ios.sendIOSGroupcast(user,content);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	/**
	 *  向ios、推送(另外appKey)
	 * @param user
	 * @param content
	 */
	public static void SendPushToIOS(String user, String content) {
		Push ios = new Push("5624a65267e58e0e3a001e1a", "z1g0bizscazvcqhuk8qlhfxd7pdq389v");
		try {
			ios.sendIOSGroupcastTest(user,content);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//JPushUtil.SendPushToAll("三级厂", "伲豪，倭是测试！");
		JPushUtilNew.SendPushToIOS("三级厂", "伲豪，倭是测试！");
	}
	
}
	