package Com.Base.Util.mySimpleUtil.lvcsmsrel;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.axis2.AxisFault;

import com.chinamobile.openmas.client.Sms;

import Com.Base.Dao.BaseDao;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Entity.System.SysUser;

/**
 * 短信登录码门面封装类
 * 
 *
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-5-9 上午9:19:20
 */
public class LVCSMSFacade {

	/**
	 * 短信服务器地址
	 */
	public static final String SMS_ADDRESS = "http://wz010.openmas.net:9080/OpenMasService";
	
	private IBaseDao baseDao;
	
	private static LVCSMSFacade lVCSMSFacade;
	
	/**
	 * 限制发送用户池塘，最长限制下面的时间，统一限制，便于操作
	 */
	private Set<String> theMsgSendLimiteUsers = new HashSet<>();

	private int theMsgSendLimiteUsersIime = 60 * 1000;
	
	private Timer timer = new Timer();// 实例化Timer类
	
	private LVCSMSFacade(IBaseDao baseDao) {
		// TODO Auto-generated constructor stub
		this.baseDao = baseDao;
		
		cleanMsgSentLimitUserSet();
	}
	

	public static LVCSMSFacade getLVCSMSFacade(IBaseDao baseDao) {
		
		if (lVCSMSFacade == null) {
			
			lVCSMSFacade = new LVCSMSFacade(baseDao);
			
		}
		
		return lVCSMSFacade;
	}
	
	
	private void cleanMsgSentLimitUserSet() {
		// TODO Auto-generated method stub
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				theMsgSendLimiteUsers.clear();

			}
		}, theMsgSendLimiteUsersIime, theMsgSendLimiteUsersIime);
	}
	
	
	
	/**
	 * 是对的校验码
	 *
	 * @param name
	 * @param lvc
	 * @return
	 *
	 * @date 2015-5-9 上午9:16:15  (ZJZ)
	 */
	public boolean isTheRightLVC(String name,String lvc) {
		// TODO Auto-generated method stub
		
		int lvcT = translateSToInt(lvc);
		
		return VCLoginHelper.getvCLoginHelper().isTheRightLVC(name,lvcT);
		
	}

	private int translateSToInt(String lvc) {
		if (lvc.length() != 6) {
			
			throw new DJException("登录码长度错误");
			
		}
		
		int lvcT;
		try {
			lvcT = Integer.parseInt(lvc);
		} catch (Exception e) {
			// TODO: handle exception
			throw new DJException("非法校验码");
		}
		
		
		
		return lvcT;
	}
	
	/**
	 * 使用校验码
	 *
	 * @param name
	 * @param lvc
	 * @return
	 *
	 * @date 2015-5-9 上午9:17:02  (ZJZ)
	 */
	public boolean loginByLVC(String name,String lvc) {
		
		int lvcT = translateSToInt(lvc);
		
		return VCLoginHelper.getvCLoginHelper().loginByLVC(name, lvcT);
		
	}
	
	/**
	 * 后期可以支持多个phone
	 *
	 * @param phone
	 * @throws RemoteException 
	 * @date 2015-5-7 下午1:46:25  (ZJZ)
	 */
	public void sendLVCToGoalPhone(String name, String phone)
			throws RemoteException {
		// TODO Auto-generated method stub
		
		if (theMsgSendLimiteUsers.contains(name)) {
			
			throw new DJException(String.format("在限定时间%s内无法重复发送",theMsgSendLimiteUsersIime/1000 + "秒"));
			
		}

		int lvc = VCLoginHelper.getvCLoginHelper().generateVCLoginC(name);

		String message = String.format("您好，您帐号 %s 对应的一次性校验码是:%s,请尽快使用，避免失效\n",
				name, lvc);

		Sms sms = new Sms(SMS_ADDRESS);

		String[] destinationAddresses = new String[] { phone };

		String extendCode = "0101";
		String ApplicationID = "8012001";
		String Password = "aiQXNVSllhh7";

		String GateWayid = sms.SendMessage(destinationAddresses, message,
				extendCode, ApplicationID, Password);
		System.out.println(GateWayid);
		
		theMsgSendLimiteUsers.add(name);

	}
	
	
	
	
	public static void sendMessage(String msg,String... phone)
			throws RemoteException {


		Sms sms = new Sms(SMS_ADDRESS);

		String[] destinationAddresses = phone;

		String extendCode = "0101";
		String ApplicationID = "8012001";
		String Password = "aiQXNVSllhh7";

		sms.SendMessage(destinationAddresses, msg,
				extendCode, ApplicationID, Password);
		
	}
	
	public static void main(String[] args) throws RemoteException {
		
		String message = String.format("您好，您的一次性登录码是:%s,请尽快使用，避免失效\nby CPS",
				1);
		
		Sms sms = new Sms(SMS_ADDRESS);
		
		String[] destinationAddresses = new String[]{"15058795602","18225616019","15988786747"};
		
		String extendCode = "0101"; 
		String ApplicationID= "8012001";
		String Password = "aiQXNVSllhh7";
		
		String GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
		System.out.println(GateWayid);
		
	}
	
	/**
	 * 获取对应用户名的手机号
	 *
	 * @param name
	 * @return
	 *
	 * @date 2015-5-9 上午9:17:44  (ZJZ)
	 */
	public String gainPhoneNumberByUserName(String name) {
		// TODO Auto-generated method stub

//		String id = MySimpleToolsZ.isTheRightUserName(name, baseDao);
		String id=MySimpleToolsZ.isTheRightUserNamePhoneEmail(name,baseDao);//先可根据邮箱、手机、用户名修改密码

		if (id.equals("-1")) {

			return "-1";

		}

		SysUser user = (SysUser) baseDao.Query(SysUser.class, id);

		return user.getFtel().isEmpty() ? "-2" : user.getFtel();

	}
	
	/**
	 * 获取验证码
	 *
	 * @param name
	 * @param lvc
	 * @throws RemoteException 
	 * @date 2015-5-7 下午2:25:00  (ZJZ)
	 */
	public void doGainLVCAction(String name) throws RemoteException {
		// TODO Auto-generated method stub

		String phone = gainPhone(name);
		
		sendLVCToGoalPhone(name,phone);
		
	}

	/**
	 * 是合法用户
	 *
	 * @param name
	 * @return
	 *
	 * @date 2015-5-9 上午9:18:19  (ZJZ)
	 */
	public boolean isTheLegalUserName(String name) {
		// TODO Auto-generated method stub

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
	
	/**
	 * 改变密码，一次操作，保证事务的正确性
	 *
	 * @param lvc
	 * @param userName
	 * @param passWord
	 * @return
	 *
	 * @date 2015-5-9 上午9:18:32  (ZJZ)
	 */
	public boolean changeThePassWord(String lvc, String userName,
			String passWord) {
		// TODO Auto-generated method stub

		if (loginByLVC(userName,lvc)) {

			String sql = String
					.format(" UPDATE `t_sys_user` SET `FPASSWORD`='%s' WHERE `fname`='%s'  ",
							passWord, userName);

			baseDao.ExecBySql(sql);

			return true;
		} else {

			return false;

		}

	}
	
	
	
}
