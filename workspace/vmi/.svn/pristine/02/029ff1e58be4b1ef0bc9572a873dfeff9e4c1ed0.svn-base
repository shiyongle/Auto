package Com.Base.Util.mySimpleUtil.lvcsmsrel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 短信校验码帮助类
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-5-9 上午9:11:19
 */
public class VCLoginHelper {

	/**
	 * 单例
	 */
	private static VCLoginHelper vCLoginHelper;

	private Timer timer = new Timer();// 实例化Timer类

	/**
	 * 自动失效时间
	 */
	private int cleanInterval = 30 * 60 * 1000;

	private VCLoginHelper() {
		// TODO Auto-generated constructor stub

		cleanTheLoginVCs();

	}

	public static VCLoginHelper getvCLoginHelper() {

		if (vCLoginHelper == null) {

			vCLoginHelper = new VCLoginHelper();

		}

		return vCLoginHelper;
	}

	/**
	 * 
	 * 自动使得登录码池塘中的登录码失效，安全性问题
	 * 
	 * @date 2015-5-9 上午9:11:49 (ZJZ)
	 */
	private void cleanTheLoginVCs() {
		// TODO Auto-generated method stub

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				loginVCs.clear();

			}
		}, cleanInterval, cleanInterval);

	}

	/**
	 * 登录号码池塘，用户只有一个唯一码
	 */
	private static Map<String, Integer> loginVCs = new HashMap<>();

	/***
	 * 是正确的用户名和对于的校验码
	 *
	 * @param name
	 * @param lvc
	 * @return
	 *
	 * @date 2015-5-9 上午9:12:27  (ZJZ)
	 */
	public boolean isTheRightLVC(String name, int lvc) {

		if (loginVCs.containsKey(name) && loginVCs.get(name) == lvc) {

			return true;

		} else {

			return false;

		}

	}

	/**
	 * 产生对应的校验码
	 *
	 * @param name
	 * @return
	 *
	 * @date 2015-5-9 上午9:12:44  (ZJZ)
	 */
	public int generateVCLoginC(String name) {
		// TODO Auto-generated method stub

		int lVC = (int) ((Math.random() * 9 + 1) * 100000);

		loginVCs.put(name, lVC);

		return lVC;

	}

	/**
	 * 使用登录码的操作，要在号码池塘中剔除之
	 *
	 * @param name
	 * @param lVC
	 * @return
	 *
	 * @date 2015-5-9 上午9:12:55  (ZJZ)
	 */
	public boolean loginByLVC(String name, int lVC) {

		// TODO Auto-generated method stub

		if (isTheRightLVC(name, lVC)) {

			loginVCs.remove(name);

			return true;

		} else {

			return false;

		}
	}

	public static void main(String[] args) {

		System.out.println((int) ((Math.random() * 9 + 1) * 100000));

	}

}
