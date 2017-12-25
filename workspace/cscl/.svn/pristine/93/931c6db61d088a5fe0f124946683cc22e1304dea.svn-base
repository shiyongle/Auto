package com.pc.util;

import java.util.HashMap;

import com.pc.appInterface.api.DongjingClient;
import com.pc.appInterface.api.NewDongjingClient;
import com.pc.model.Util_UserOnline;

public class ServerContext{

//	private static OracleHelper OracleHelper;
//	private static NumberHelper NumberHelper;
	private static String[] Specialstr;
//	private static final String BaseUrl=UrlConf.DJPAY_URL;
	private static String msg;
//	private static final String HswUrl=UrlConf.HSWURL;//花生网测试
	
	private static HashMap<String, Util_UserOnline> map = new HashMap<String, Util_UserOnline>();
	public static String getMsg() {
		return msg;
	}
	
	//获取支付链接
	public static String getBaseurl() {
		return UrlConf.DJPAY_URL;
	}

	//获取花生壳链接
	public static String getHswurl() {
		return UrlConf.HSWURL;
	}
	
	public static void setMsg(String msg) {
		ServerContext.msg = msg;
	}

	private static HashMap<String, String[]> driverPosition = new HashMap<String, String[]>();
	
	public static HashMap<String, Util_UserOnline> getUseronline() throws DJException {
		return map;
	}
	
	public static void setUseronline(String sessionId,Util_UserOnline useronline) throws DJException {
//		if(!map.containsKey(sessionId)){
			map.put(sessionId, useronline);
//		}
	}
	
	public static void delUseronline(String sessionId) throws DJException {
		if(map.containsKey(sessionId)){
			map.remove(sessionId);
		}
	}
	
	public static void delAllUseronline() throws DJException {
		map.clear();
	}
	
	public static void ServerContext() throws DJException {
		Specialstr=new String[]{"--","%","*","'"};
		
	}
	public static String[] getSpecialstr() throws DJException {
		return Specialstr;
	}
	
	
	public static DongjingClient createVmiClient() throws DJException {
		DongjingClient vmiClient=new DongjingClient();
		vmiClient.setURL(UrlConf.DJLOGIN_URL);
		vmiClient.setUsername(UrlConf.DJLOGIN_AN);
		vmiClient.setPassword(UrlConf.DJLOGIN_PS);
		vmiClient.connect();
		return vmiClient;
	}
	public static NewDongjingClient createInnerUserClient() throws DJException {
		NewDongjingClient vmiClient=new NewDongjingClient();
		vmiClient.setURL(UrlConf.DJLOGIN_URL);
		vmiClient.setUsername(UrlConf.DJLOGIN_AN);
		vmiClient.setPassword(UrlConf.DJLOGIN_PS);
		vmiClient.connect();
		return vmiClient;
	}

	public static NewDongjingClient createSendMsgClient() throws DJException {
		NewDongjingClient vmiClient=new NewDongjingClient();
		vmiClient.setURL(UrlConf.DJMSGPUSH);
		vmiClient.setUsername(UrlConf.DJLOGIN_AN);
		vmiClient.setPassword(UrlConf.DJLOGIN_PS);
		vmiClient.connect();
		return vmiClient;
	}

	
	
	//增加服务端校验付款状态，防止直接调用接口修改付款状态   
//	public static boolean checkPayStatus(String data) throws DJException {
//		 DongjingClient PayClient=new DongjingClient();
//		 PayClient.setRequestProperty("data", data);
//		 PayClient.SubmitUrlData("http://192.168.0.89/DJBankPay/Action_Pay/DJPay/Pay/API/Name/UserOrder/Index/UserOrderPayOver?&XDEBUG_SESSION_START=17402");
//		return  PayClient.issuccess();
//	}
	/*//将发送退款请求放到服务端，确保支付退款与业务系统状态一致 
	public static boolean cancelOrderPay(String url,String PayInfo) throws DJException {
		 DongjingClient PayClient=new DongjingClient();
		 PayClient.setRequestProperty("data", PayInfo);
		 PayClient.SubmitUrlData(url);
		return  PayClient.issuccess();
	}
	
	//将带好运卷的支付
	public static boolean cancelCouponsPay(String url,String PayInfo,String Attached) throws DJException {
		 DongjingClient PayClient=new DongjingClient();
		 PayClient.setRequestProperty("data", PayInfo);
		 PayClient.setRequestProperty("Attached", Attached);
		 PayClient.SubmitUrlData(url);
		return  PayClient.issuccess();
	}
	
	//提现扣除余额结果返回   
		public static boolean checkWithdrawStatus(String data) throws DJException {
			 DongjingClient PayClient=new DongjingClient();
			 PayClient.setRequestProperty("data", data);
			 PayClient.SubmitUrlData(ServerContext.getBaseurl()+"/Action_Pay/DJPay/PaySys/UseMoney?&XDEBUG_SESSION_START=17402");
			return  PayClient.issuccess();
		}
		
		public static boolean UserMoneyTwo(String data) throws DJException {
			DongjingClient PayClient=new DongjingClient();
			PayClient.setRequestProperty("data", data);
			PayClient.SubmitUrlData(ServerContext.getBaseurl()+"/Action_Pay/DJPay/PaySys/UserMoneyTwo?&XDEBUG_SESSION_START=17402");
			return  PayClient.issuccess();
		}
		
		public static boolean UserMoneyOne(String data) throws DJException {
			DongjingClient PayClient=new DongjingClient();
			PayClient.setRequestProperty("data", data);
			PayClient.SubmitUrlData(ServerContext.getBaseurl()+"/Action_Pay/DJPay/PaySys/UserMoneyOne?&XDEBUG_SESSION_START=17402");
			return  PayClient.issuccess();
		}*/

//	//将发送退款请求放到服务端，确保支付退款与业务系统状态一致 
//	public static boolean cancelOrderPay1(String url) throws DJException {
//		 DongjingClient PayClient=new DongjingClient();
//		 PayClient.SubmitUrlData(url);
//		return  PayClient.issuccess();
//	}

	/**
	 * @return the driverPosition
	 */
	public static HashMap<String, String[]> getDriverPosition() {
		return driverPosition;
	}

	/**
	 * @param driverPosition the driverPosition to set
	 */
	public static void setDriverPosition(HashMap<String, String[]> driverPosition) {
		ServerContext.driverPosition = driverPosition;
	}
	
	/*//当前用户余额
	public static String UsePayBalance(String data) throws DJException {
		DongjingClient PayClient=new DongjingClient();
		PayClient.setRequestProperty("data", data);		
		PayClient.SubmitUrlData(ServerContext.getBaseurl()+"/Action_Pay/DJPay/Pay/API/Name/UserControl/Index/UsePayBalance");
		return  PayClient.getResponse().getResultString();
	}*/
	
}