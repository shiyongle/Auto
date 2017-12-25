package com.util;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinamobile.openmas.client.Sms;
import com.service.IBaseManager;

/*** 短信登录码门面封装类*/
public class LVCSMSFacade {
	/*** 短信服务器地址*/
	public static final String SMS_ADDRESS = "http://wz010.openmas.net:9080/OpenMasService";
	private SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	@Autowired
	private IBaseManager iBaseManager;
	
	private static LVCSMSFacade lVCSMSFacade;
	
	/*** 限制发送用户池塘，最长限制下面的时间，统一限制，便于操作*/
//	private Set<String> theMsgSendLimiteUsers = new HashSet<>();

	//2015-10-27 过期时间设为15分钟
//	private int theMsgSendLimiteUsersIime = 15*60 * 1000;
	/*** 实例化Timer类*/
//	private Timer timer = new Timer();
	
	
	private LVCSMSFacade(IBaseManager iBaseManager) {
		this.iBaseManager = iBaseManager;
//		cleanMsgSentLimitUserSet();
	}
	
	public static LVCSMSFacade getLVCSMSFacade(IBaseManager iBaseManager) {
		
		if (lVCSMSFacade == null) {
			lVCSMSFacade = new LVCSMSFacade(iBaseManager);
		}
		
		return lVCSMSFacade;
	}
	
//	private void cleanMsgSentLimitUserSet() {
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				theMsgSendLimiteUsers.clear();
//			}
//		}, theMsgSendLimiteUsersIime, theMsgSendLimiteUsersIime);
//	}
	
	
	/*** 是对的校验码*/
	public boolean isTheRightLVC(String name,String lvc,boolean isClean) {
		int lvcT = translateSToInt(lvc);
		return VCLoginHelper.getvCLoginHelper().isTheRightLVC(name,lvcT,isClean);
	}

	private int translateSToInt(String lvc) {
		if (lvc.length() != 6) {
			throw new DJException("登录码长度错误");
		}
		int lvcT;
		try {
			lvcT = Integer.parseInt(lvc);
		} catch (Exception e) {
			throw new DJException("非法校验码");
		}
		return lvcT;
	}
	
	/*** 使用校验码*/
	public boolean loginByLVC(String name,String lvc) {
		int lvcT = translateSToInt(lvc);
		return VCLoginHelper.getvCLoginHelper().loginByLVC(name, lvcT);
	}
	
	/*** 发送随机码*/
	public HashMap<String,Object> sendLVCToGoalPhone(String name, String phone) throws RemoteException {
		//限定时间发送
//		if (theMsgSendLimiteUsers.contains(name)) {
//			throw new DJException(String.format("在限定时间%s内无法重复发送",theMsgSendLimiteUsersIime/1000 + "秒"));
//		}
		//2016年5月30日  by les,一分钟内防止重复发送
				Map<String, String> loginVCs= VCLoginHelper.loginVCs;
				HashMap<String,Object> map=new HashMap<String,Object>();
				if(loginVCs.containsKey(name)){
					String timeStr = loginVCs.get(name).split(",")[1];
					Calendar nowcal =Calendar.getInstance();
					Calendar sendcal=Calendar.getInstance();  
					Date sendtime=null;
					try { 
						sendtime = sdf.parse(timeStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sendcal.setTime(sendtime); 
					sendcal.add(Calendar.MINUTE, -14);//已经加了15分钟了
					if(sendcal.compareTo(nowcal)>0){
						System.out.println("一分钟不允许重复发送");
						map.put("success", "false");
						map.put("msg","一分钟不允许重复发送!");
						return map;
					}
				}
				
		int lvc = VCLoginHelper.getvCLoginHelper().generateVCLoginC(name);
		String message = String.format("您好，您帐号 %s 对应的一次性校验码是:%s,请尽快使用，避免失效\n",name, lvc);
		sendMessageToMobile(phone,message);
		map.put("success", "true");
		map.put("msg","验证码发送成功");
		return map;
//			theMsgSendLimiteUsers.add(name);
	}
 
	/*** 发送随机码*/
	public void sendLVCToGoalPcPhone(String name, String phone) throws RemoteException {
		//限定时间发送
//		if (theMsgSendLimiteUsers.contains(name)) {
//			throw new DJException(String.format("在限定时间%s内无法重复发送",theMsgSendLimiteUsersIime/1000 + "秒"));
//		}
		//2016年5月30日  by les,一分钟内防止重复发送
				Map<String, String> loginVCs= VCLoginHelper.loginVCs;
				if(loginVCs.containsKey(name)){
					String timeStr = loginVCs.get(name).split(",")[1];
					Calendar nowcal =Calendar.getInstance();
					Calendar sendcal=Calendar.getInstance();  
					Date sendtime=null;
					try { 
						sendtime = sdf.parse(timeStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sendcal.setTime(sendtime); 
					sendcal.add(Calendar.MINUTE, -14);//已经加了15分钟了
					if(sendcal.compareTo(nowcal)>0){
						System.out.println("一分钟不允许重复发送");
						return;
					}
				}
				
		int lvc = VCLoginHelper.getvCLoginHelper().generateVCLoginC(name);
		String message = String.format("您好，您帐号 %s 对应的一次性校验码是:%s,请尽快使用，避免失效\n",name, lvc);
		sendMessageToMobile(phone,message);
//			theMsgSendLimiteUsers.add(name);
	}
	
	public static void sendMessageToMobile(String phone,String message) throws RemoteException{
//		String chinaMobile="134[0-8]\\d{7}$|^(?:13[5-9]|147|15[0-27-9]|1705|178|18[2-478])\\d{8}";//移动方面最新答复,1705虚拟运营商专属号段
//		String chinaUnion  = "(?:13[0-2]|145|15[56]|1709|176|18[56])\\d{8}"; //向联通微博确认并未回复，1709虚拟运营商专属号段
//		String chinaTelcom = "(?:133|153|1700|177|18[019])\\d{8}"; //1349卫星通讯号段 电信方面没给出答复，视作不存在，1700虚拟运营商专属号段
//		String[] destinationAddresses = new String[] { phone };
//		List<String> list=new ArrayList<>();
//		Pattern isCMCC=Pattern.compile(chinaMobile);
//		Pattern isCUCC=Pattern.compile(chinaUnion);
//		Pattern isCTCC=Pattern.compile(chinaTelcom);
//		for (String mobile : destinationAddresses) {
//			Matcher mMobile=isCMCC.matcher(mobile);
//			Matcher mUnion=isCUCC.matcher(mobile);
//			Matcher mTelcom=isCTCC.matcher(mobile);
//			if(mUnion.find()||mTelcom.find())//联通or电信
//			{
				String url = "http://121.52.212.233:7803/sms"; //网址
				String userid = "800884"; //企业ID
				String account = "800884"; //账号
				String password = "6MnFPf"; //密码
				String extno= "106902202131"; //扩展子号
				String content = message+"【东经CPS】"; //内容
				String send = SmsClientSend.sendSms(url,"send",userid,account,
						   password,phone,content,extno);
				
				try {
					System.out.println("联通电信:"+new String(send.getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
//			}
//			else if(mMobile.find())//移动
//			{
//				list.add(mobile);
//			}
//		}
//		if(list!=null&&list.size()!=0)
//		{
//			destinationAddresses=new String[list.size()];
////			destinationAddresses = (String[]) list.toArray();
//			for (int i = 0; i < list.size(); i++) {
//				destinationAddresses[i]=list.get(i);
//			}
//			Sms sms = new Sms(SMS_ADDRESS);
//			String extendCode = "0101";
//			String ApplicationID = "8012001";
//			String Password = "aiQXNVSllhh7";
//			String GateWayid = sms.SendMessage(destinationAddresses, message,extendCode, ApplicationID, Password);
//			System.out.println("移动："+GateWayid);
//		}
	}
	
	/*** 物流注册调用的发送随机码接口*/
	public void sendSecurityCode(String name, String phone,String fcode,String msgString) throws RemoteException {
		//限定时间发送
//		if (theMsgSendLimiteUsers.contains(name)) {
//			throw new DJException(String.format("在限定时间%s内无法重复发送",theMsgSendLimiteUsersIime/1000 + "秒"));
//		}
		String message = String.format(msgString,name, fcode);
		sendMessageToMobile(phone, message);
//		Sms sms = new Sms(SMS_ADDRESS);
//		String[] destinationAddresses = new String[] { phone };
//		String extendCode = "0101";
//		String ApplicationID = "8012001";
//		String Password = "aiQXNVSllhh7";
//		String GateWayid = sms.SendMessage(destinationAddresses, message,extendCode, ApplicationID, Password);
//		System.out.println(GateWayid);
//		theMsgSendLimiteUsers.add(name);
	}
	
	
	//only TSysUserAction lineDes(Line479) Use
	public static void sendMessage(String msg,String[] phone) throws RemoteException {
		Sms sms = new Sms(SMS_ADDRESS);
		String[] destinationAddresses = phone;
		String extendCode = "0101";
		String ApplicationID = "8012001";
		String Password = "aiQXNVSllhh7";
		sms.SendMessage(destinationAddresses, msg,extendCode, ApplicationID, Password);
	}

	public static void main(String[] args) {
		String message = String.format("您好，您的一次性登录码是:%s,请尽快使用，避免失效\nby CPS",1);
		Sms sms=null;
		try {
			sms = new Sms(SMS_ADDRESS);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] destinationAddresses = new String[]{"18565790984"};
		String extendCode = "0101"; 
		String ApplicationID= "8012001";
		String Password = "aiQXNVSllhh7";
		String GateWayid="";
		try {
			GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
		System.out.println(GateWayid);
	}
	
	/*** 获取对应用户名的手机号*/
	public String gainPhoneNumberByUserName(String name) {
		/*String id = MySimpleToolsZ.isTheRightUserName(name, baseDao);
		if (id.equals("-1")) {
			return "-1";
		}
		SysUser user = (SysUser) baseDao.Query(SysUser.class, id);
		return user.getFtel().isEmpty() ? "-2" : user.getFtel();*/
		return null;
	}
	
	/*** unused 忘记密码-通过用户名-获取验证码*/
//	public void doGainLVCAction(String name) throws RemoteException {
//		String phone = gainPhone(name);
//		sendLVCToGoalPhone(name,phone);
//	}

	/*** 是合法用户*/
	public boolean isTheLegalUserName(String name) {
		gainPhone(name);
		return true;
	}
	
	private String gainPhone(String name) {
		String phone = gainPhoneNumberByUserName(name);
		if (phone.equals("-1")) {
			throw new DJException("用户名错误");
		} else if (phone.equals("-2")){
			throw new DJException("没有电话号码，请联系管理员操作！");
		}
		return phone;
	}
	
	/*** 改变密码，一次操作，保证事务的正确性*/
	public boolean changeThePassWord(String lvc, String userName,String passWord) {
		if (loginByLVC(userName,lvc)) {
			String sql = String.format(" UPDATE `t_sys_user` SET `FPASSWORD`='%s' WHERE `fname`='%s'  ",passWord, userName);
			iBaseManager.ExecBySql(sql);
			return true;
		} else {
			return false;
		}
	}
}
