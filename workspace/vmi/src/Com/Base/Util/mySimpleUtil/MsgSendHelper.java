package Com.Base.Util.mySimpleUtil;

import java.rmi.RemoteException;

import com.chinamobile.openmas.client.Sms;

/**
 * 短信工具类
 * 
 *
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-5-16 上午8:42:14
 */
public class MsgSendHelper {

	/**
	 * 短信服务器地址
	 */
	private static final String SMS_ADDRESS = "http://wz010.openmas.net:9080/OpenMasService";
	
	private static final String EXTEND_CODE = "0101";
	private static final String APPLICATION_ID = "8012001";
	private static final String PASSWORD = "aiQXNVSllhh7";
	
	
	private static MsgSendHelper msgSendHelper;
	
	
	
	private MsgSendHelper() {
		// TODO Auto-generated constructor stub
	}

	
	public static MsgSendHelper getMsgSendHelper() {
		
		if (msgSendHelper == null) {
			
			msgSendHelper = new MsgSendHelper();
			
		}
		
		
		return msgSendHelper;
	}

	
	/**
	 * 
	 * 短信发送
	 *
	 * @param msg，消息
	 * @param phones，电话s
	 * @throws RemoteException
	 *
	 * @date 2015-5-16 上午8:42:27  (ZJZ)
	 */
	public static void sendMsg(String msg, String... phones) throws RemoteException {
		// TODO Auto-generated method stub

		Sms sms = new Sms(SMS_ADDRESS);

		String GateWayid = sms.SendMessage(phones, msg,
				EXTEND_CODE, APPLICATION_ID, PASSWORD);
		
		System.out.println(GateWayid);
	}
	
	
	/**
	 *
	 * @param args
	 * @throws RemoteException 
	 *
	 * @date 2015-5-16 上午8:23:14  (ZJZ)
	 */
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub

		
//		sendMsg("today is sunday,今天是周末", "18225616019");
		
		sendMsg("today is sunday,今天是周末", new String[]{ "18225616019"});
		
	}

}
