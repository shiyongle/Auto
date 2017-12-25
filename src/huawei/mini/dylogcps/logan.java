package huawei.mini.dylogcps;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;


public class logan extends UiAutomatorTestCase {
	public void test1logan() throws UiObjectNotFoundException, RemoteException{
		
	} 
	public void testlogan() throws UiObjectNotFoundException,RemoteException{
		UiDevice device = getUiDevice();
		device.pressHome();
		device.wakeUp();
		
		UiObject index = new UiObject(new 
						UiSelector().className("android.view.View").instance(2));
		assertTrue("There has not index found",index.exists());
		System.out.println("knowlages power");							//判断父对象是否存在
		
		UiObject index1 = index.getChild(new
						UiSelector().className("android.widget.TextView").index(5));
		assertTrue("There has not index1 found",index1.exists());
		System.out.println("knowlages power");							
		index1.click();													//点击APP图标
		
		sleep(3000);
/*-----------------------------启动APP-------------------------------------------------*/
		UiObject username = new UiObject(new 
						UiSelector().className("android.widget.LinearLayout").instance(4));
		assertTrue("There has not username found",username.exists());
		System.out.println("knowlages power");
		
		UiObject username1 = username.getChild(new
						UiSelector().className("android.widget.EditText").index(1));
		System.out.println("knowlages power");
		username1.setText("18268005370");								//输入账号
		
		sleep(1000);
		
		UiObject password = new UiObject(new
						UiSelector().className("android.widget.LinearLayout").instance(5));
		assertTrue("There has not password found",password.exists());
		System.out.println("knowlages power");							//判断父对象是否存在
		
		
		UiObject password1 = password.getChild(new
						UiSelector().className("android.widget.EditText").index(1));
		assertTrue("There has not password found",password1.exists());
		System.out.println("knowlages power");
		password1.setText("dj123456");								//输入密码
		
		UiObject button = new UiObject(new
						UiSelector().className("android.widget.LinearLayout").instance(3));
		assertTrue("There has not button found",button.exists());		//判断父对象是否存在
		System.out.println("knowlages power");
		
		UiObject button1 = button.getChild(new
						UiSelector().className("android.widget.Button").index(5));
		assertTrue("There has not button found",button1.exists());
		System.out.println("knowlages power");
		button1.click();											//点击登录
		
/*==============================登录成功======================================================*/
	}
}
