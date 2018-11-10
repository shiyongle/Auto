package dylogcps;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;

public class login extends UiAutomatorTestCase {
	public void testlogin() throws UiObjectNotFoundException,RemoteException{
		UiDevice device = getUiDevice();
		device.pressHome();
		device.wakeUp();
		
		UiObject cps = new UiObject(new
					UiSelector().className("android.view.View").instance(2));
		assertTrue("There has not be found",cps.exists());
		
		UiObject login = cps.getChild(new 
					UiSelector(
							
							).className("android.widget.TextView").instance(2));
		assertTrue("There has not be found",login.exists());
		System.out.println("knowlages power");
		login.click();						//����
		
		sleep(5000);						//�ȴ�
		
		UiObject view = new UiObject(new
					UiSelector().className("android.widget.LinearLayout").index(0));
		assertTrue("There has not be found",view.exists());
		System.out.println("knowlages power");             //�жϸ��������Ƿ����
		
		UiObject edit = view.getChild(new 
					UiSelector().className("android.widget.EditText").index(1));		
		edit.setText("18268005370");
		
		UiObject view1 = new UiObject(new
					UiSelector().className("android.widget.LinearLayout").index(2));
		assertTrue("There has not be found",view1.exists());	//�жϸ��������Ƿ����
		
		UiObject edit1 = view1.getChild(new
					UiSelector().className("android.widget.EditText").index(1));
		edit1.setText("123456");
		
		UiSelector button = new UiSelector().className("android.widget.Button").index(5);
		UiObject lg = new UiObject(button);
		lg.click();							//��¼�ɹ�������ҳ
		
		sleep(5000);			
		
		UiObject menu = new UiObject(new
					UiSelector().className("android.widget.TabHost").index(2));
		assertTrue("There has not found",menu.exists());
		System.out.print("Knowledges powers");		//�жϸ��������Ƿ����
		
		UiObject purse = menu.getChild(new
					UiSelector().className("android.widget.LinearLayout").index(1));
		assertTrue("There has not found",purse.exists());
		System.out.print("Knowledges powers");				
		purse.click();							//���Ǯ��
		
		sleep(2000);
		
		UiObject record = menu.getChild(new
					UiSelector().className("android.widget.LinearLayout").index(2));
		assertTrue("There has not found",record.exists());
		System.out.print("Knowledges powers");
		record.click();								//�����¼
		
		sleep(2000);
		
		UiObject me = menu.getChild(new
					UiSelector().className("android.widget.LinearLayout").index(3));
		System.out.print("Knowledges powers");
		me.click();									//����ҵ�
		
		sleep(2000);
		
		UiObject home = menu.getChild(new
					UiSelector().className("android.widget.LinearLayout").index(0));
		System.out.print("Knowledges powers");
		home.click();								//�����ҳ
		
		sleep(2000);
		
		UiObject order = new UiObject(new
					UiSelector().className("android.widget.FrameLayout").index(1));
		assertTrue("There has not be found",order.exists());
		System.out.println("knowldges powers");			//�жϸ������Ƿ���ڣ���������
		
		UiObject order1 = order.getChild(new
					UiSelector().className("android.widget.LinearLayout").index(1)); 
		assertTrue("There has not be found",order1.exists());
		System.out.println("knowldges powers");
		order1.click();									//������롮Э���µ���
		
		sleep(5000);
		
		UiObject select = new UiObject(new
					UiSelector().className("android.widget.RelativeLayout").index(2));
		assertTrue("There has not be found",select.exists()); 		//�жϸ������Ƿ����
		
		UiObject select1 = select.getChild(new
					UiSelector().className("android.widget.LinearLayout").index(1));
		assertTrue("There has not be found",select1.exists());
		System.out.println("knowldges powers");
		select1.click();								//����µ� ��4.2�װ��쵥��
		
		sleep(3000);
		
		/*=========================ѡ��ʱ���===============================================*/
		
		UiObject data = new UiObject(new
					UiSelector().className("android.widget.ScrollView").index(2));
		assertTrue("There has not be found",data.exists());			//�жϸ������Ƿ����
		
		UiObject data1 = data.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(1));
		assertTrue("There has not be found",data1.exists());
		
		UiObject data2 = data1.getChild(new
					UiSelector().className("android.widget.ImageView").index(3));
		assertTrue("There has not be found",data2.exists());
		System.out.println("knowldges powers");
		data2.click();												//������롮ʱ��ѡ���
		
		sleep(3000);
		
		UiObject hour = new UiObject(new
					UiSelector().className("android.widget.RelativeLayout").index(0));
		assertTrue("There has not be found",hour.exists());
		
		UiObject hour1 = hour.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(3));
		assertTrue("There has not be found",hour1.exists());
		System.out.println("knowldges powers");
		
		UiObject hour2 = hour1.getChild(new
					UiSelector().className("android.widget.TextView").index(2));
		assertTrue("There has not be found",hour2.exists());
		System.out.println("knowldges powers");
		hour2.click();										//ѡ��ʱ��
	/*-----------------------------------------------------------------*/
		UiObject affirm = new UiObject(new
					UiSelector().className("android.widget.RelativeLayout").instance(1));
		assertTrue("There has not be found",affirm.exists());
		
		UiObject affirm1 = affirm.getChild(new
					UiSelector().className("android.widget.Button").index(1));
		assertTrue("There has not be found",affirm1.exists());
		affirm1.click();									//ȷ��ʱ��
	/*============================ѡ���ַ��=========================================*/	
		
		UiObject address = new UiObject(new
					UiSelector().className("android.widget.ScrollView").index(2));
		assertTrue("There has not be found",address.exists());
		
		UiObject address1 = address.getChild(new
				UiSelector().className("android.widget.LinearLayout").instance(2));
		assertTrue("There has not be found",address1.exists());
		System.out.println("knowldges powers");
		address1.click();								//�����ַ
		
		//注释
		}
}
			
				
				
				

