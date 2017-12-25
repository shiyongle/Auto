package com.util;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 短信校验码帮助类
 * @author ZJZ 
 */
public class VCLoginHelper {
	/*** 单例*/
	private static VCLoginHelper vCLoginHelper;
	/*** 实例化Timer类*/
	private Timer timer = new Timer();
	/*** 登录号码池塘，用户只有一个唯一码*/
	public static Map<String, String> loginVCs = new HashMap<>();
	/*** 自动失效时间*/
	private int cleanInterval = 15 * 60 * 1000;

	
	private VCLoginHelper() {
		cleanTheLoginVCs();
	}

	public static VCLoginHelper getvCLoginHelper() {
		if (vCLoginHelper == null) {
			vCLoginHelper = new VCLoginHelper();
		}
		return vCLoginHelper;
	}

	/*** 自动使得登录码池塘中的登录码失效，安全性问题*/
	private void cleanTheLoginVCs() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Iterator<String> it = loginVCs.keySet().iterator();
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				while(it.hasNext()){
					String key = it.next();
					String date = loginVCs.get(key).split(",")[1];
					ParsePosition pos = new ParsePosition(0);   
					Date oldtime = sdf.parse(date, pos);
					Date time = new Date();
					if(time.after(oldtime)){
						loginVCs.remove(key);
					}
					
				}
			}
		}, cleanInterval, cleanInterval);

	}

	/*** 是正确的用户名和对应的校验码*/
	public boolean isTheRightLVC(String name, int lvc,boolean isClean) {
		if (loginVCs.containsKey(name)){
			int a = Integer.parseInt(loginVCs.get(name).split(",")[0]);
			if( a == lvc){
				if(isClean){
					loginVCs.remove(name);
				}
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	/*** 产生对应的校验码*/
	public int generateVCLoginC(String name) {
		int random;
		String lVC = "";
		Date time = new Date();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		time.setMinutes(time.getMinutes()+15);
		lVC = loginVCs.get(name);
		if(lVC==null || lVC.equals("")){
			random = (int) ((Math.random() * 9 + 1) * 100000);
			lVC = random + "," + sdf.format(time);
			loginVCs.put(name, lVC);
		}else{
			random = Integer.parseInt(lVC.split(",")[0]);
			lVC = random + "," + sdf.format(time);
			loginVCs.put(name, lVC);
		}
		System.out.println("************************向map添加元素"+loginVCs.get(name)+"*****************************");
		return random;
	}

	/*** 使用登录码的操作，要在号码池塘中剔除之*/
	public boolean loginByLVC(String name, int lVC) {
		if (isTheRightLVC(name, lVC,true)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println((int) ((Math.random() * 9 + 1) * 100000));
	}

}
