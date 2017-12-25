package huawei.mini.dylogcps;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;

public class order extends UiAutomatorTestCase {
		public void testorder() throws UiObjectNotFoundException,RemoteException{
			UiDevice device = getUiDevice();
			device.wakeUp();
			/*========================协议订单开始,此处必须为协议用车6.8米的规则=========================================*/
			/*========================选择协议订单====================================================*/
			
			UiObject agreementorder = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(6));		//判断父对象是否存在
			
			UiObject agreementorder1 = agreementorder.getChild(new
								UiSelector().className("android.widget.LinearLayout").index(1));
			assertTrue("There has not agreementorder1",agreementorder1.exists());
			System.out.println("knowlages power");
			agreementorder1.click();									//点击协议下单
			
			sleep(1000);
			
			UiObject carload = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(4));
			assertTrue("There has not carload",carload.exists());
			System.out.println("knowlages power");
			carload.click();											//点击‘车厢6.8整车单’===此处索引为第一行（instance）
			
			/*========================选择时间模块====================================================*/		
			
			sleep(1000);
			
			UiObject trucktime = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(3));
			assertTrue("There has not trucktime",trucktime.exists());
			System.out.println("knowlages power");
			trucktime.click();											//点击装车时间
			
			UiObject trucktime1 = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(4));
			assertTrue("There has not trucktime1",trucktime1.exists());
			System.out.println("knowlages power");
			
			UiObject trucktime2 = trucktime1.getChild(new
								UiSelector().className("android.widget.TextView").index(2));
			assertTrue("There has not trucktime2",trucktime2.exists());
			System.out.println("knowlages power");
			trucktime2.click();										//选择明天
			
			UiObject trucktime3 = new UiObject(new
								UiSelector().className("android.widget.RelativeLayout").instance(1));
			assertTrue("There has not trucktime3",trucktime3.exists());
			System.out.println("knowlages power");
			
			UiObject trucktime4 = trucktime3.getChild(new
								UiSelector().className("android.widget.Button").index(1));
			assertTrue("There has not trucktime4",trucktime4.exists());
			System.out.println("knowlages power");
			trucktime4.click();										//确定时间
			
			/*========================提货地址选择====================================================*/
			
			UiObject pickadress = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(7));
			assertTrue("There has not pickadress",pickadress.exists());
			System.out.println("knowlages power");
			pickadress.click();										//点击提货地址
			
			UiObject pickadress1 = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(4));
			assertTrue("There has not pickadress1",pickadress1.exists());
			System.out.println("knowlages power");
			pickadress1.click();									//选择instance为4的地址（第一个）
			
			/*========================卸货地址选择====================================================*/
			
			UiObject unload = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(8));
			assertTrue("There has not unload",unload.exists());
			System.out.println("knowlages power");
			unload.click();
			
			UiObject unload1 = new UiObject(new
								UiSelector().className("android.widget.LinearLayout").instance(4));
			assertTrue("There has not unload1",unload1.exists());
			System.out.println("knowlages power");
			unload1.click();										//选择instance为4的地址（第一个）
			
			/*========================添加卸货地址选择====================================================*/
			UiObject addaddress = new UiObject(new
							UiSelector().className("android.widget.ScrollView").index(2));
			assertTrue("There has not addaddress",addaddress.exists());
			System.out.println("knowlages power");
			
			UiObject addaddress1 = addaddress.getChild(new
							UiSelector().className("android.widget.LinearLayout").index(4));
			assertTrue("There has not addaddress1",addaddress1.exists());
			System.out.println("knowlages power");
			
			addaddress1.click();									//点击‘添加地址’按钮 -->进入添加卸货地址页面
			
			UiObject unload3 = new UiObject(new
							UiSelector().className("android.widget.RelativeLayout").index(2));
			assertTrue("There has not unload3",unload3.exists());
			System.out.println("knowlages power");
			
			UiObject unload4 = unload3.getChild(new
							UiSelector().className("android.widget.FrameLayout").index(2));
			assertTrue("There has not unload4",unload4.exists());
			System.out.println("knowlages power");
			
			unload4.click();									//点击卸货地址列表第二个卸货地址-->返回下单页面
			
			/*========================选择所需车型====================================================*/
			UiObject kindcar = new UiObject(new
							UiSelector().className("android.widget.ScrollView").index(2));
			assertTrue("There has not kindcar",kindcar.exists());
			System.out.println("knowlages power");
			
			UiObject kindcar1 = kindcar.getChild(new
							UiSelector().className("android.widget.LinearLayout").instance(0));
			assertTrue("There has not kindcar1",kindcar1.exists());
			System.out.println("knowlages power");
			
			UiObject kindcar2 = kindcar1.getChild(new
							UiSelector().className("android.widget.LinearLayout").index(3));
			assertTrue("There has not kindcar2",kindcar2.exists());
			System.out.println("knowlages power");
		
			kindcar2.click();									//点击选择车型
			
			UiObject kindcar3 = new UiObject(new
							UiSelector().className("android.widget.RelativeLayout").index(0));
			assertTrue("There has not kindcar3",kindcar3.exists());
			System.out.println("knowlages power");
			
			UiObject kindcar4 = kindcar3.getChild(new
							UiSelector().className("android.widget.Button").index(2));
			assertTrue("There has not kindcar4",kindcar4.exists());
			System.out.println("knowlages power");
			
			kindcar4.click();
			
			/*========================选择货物类型====================================================*/
			
			
			UiObject kindcargo = new UiObject(new
							UiSelector().className("android.widget.LinearLayout").resourceId("com.olcps.dylogistics:id/btnGoTy"));
			assertTrue("There has not kindcargo",kindcargo.exists());
			System.out.println("This has kindcargo");
			kindcargo.click();								//通过resourceId判断，点击‘货物类型’
			
			UiObject kindcargo1 = new UiObject(new
							UiSelector().className("android.widget.LinearLayout").instance(2));
			assertTrue("There has not kindcargo1",kindcargo1.exists());
			System.out.println("This has kindcargo1");
			
			UiObject kindcargo2 = kindcargo1.getChild(new
							UiSelector().className("android.widget.TextView").resourceId("com.olcps.dylogistics:id/textValues"));
			assertTrue("There has not kindcargo2",kindcargo2.exists());
			System.out.println("This has kindcargo2");
			
			kindcargo2.click();								//点击‘纺织皮革’
			
			UiObject kindcargo3 = kindcargo1.getChild(new
							UiSelector().className("android.widget.LinearLayout").instance(1));
			assertTrue("There has not kindcargo3",kindcargo3.exists());
			System.out.println("This has kindcargo3");		//点击‘橡胶塑胶’
			
			kindcargo3.click();
			
			UiObject kindcargo4 = kindcargo1.getChild(new
							UiSelector().className("android.widget.LinearLayout").instance(2));
			assertTrue("There has not kindcargo4",kindcargo4.exists());
			System.out.println("This has kindcargo4");
			
			kindcargo4.click();								//点击‘玻璃陶瓷’
			
			UiObject kindcargo5 = kindcargo1.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(3));
			assertTrue("There has not kindcargo5",kindcargo5.exists());
			System.out.println("This has kindcargo5");
	
			kindcargo5.click();								//点击‘包装印刷’
			
			UiObject kindcargo6 = kindcargo1.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(4));
			assertTrue("There has not kindcargo6",kindcargo6.exists());
			System.out.println("This has kindcargo6");
	
			kindcargo6.click();								//点击‘金属配件’
			
			UiObject kindcargo7 = kindcargo1.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(5));
			assertTrue("There has not kindcargo7",kindcargo7.exists());
			System.out.println("This has kindcargo7");
	
			kindcargo7.click();								//点击‘电器通讯’
			
			UiObject kindcargo8 = kindcargo1.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(6));
			assertTrue("There has not kindcargo4",kindcargo8.exists());
			System.out.println("This has kindcargo4");
	
			kindcargo8.click();								//点击‘仪器设备’
			
			UiObject kindcargo9 = kindcargo1.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(7));
			assertTrue("There has not kindcargo9",kindcargo4.exists());
			System.out.println("This has kindcargo9");
	
			kindcargo9.click();								//点击‘快销设备’
			
			UiObject kindcargo10 = kindcargo1.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(8));
			assertTrue("There has not kindcargo10",kindcargo10.exists());
			System.out.println("This has kindcargo10");
	
			kindcargo10.click();							//点击‘家居建材’
		
			
			UiObject kindcargo11 = new UiObject(new
					UiSelector().className("android.widget.LinearLayout").instance(1));
			assertTrue("There has not kindcargo11",kindcargo11.exists());
			System.out.println("This has kindcargo11");
			
			UiObject kindcargo12 = kindcargo11.getChild(new
					UiSelector().className("android.widget.RelativeLayout").instance(0));
			assertTrue("There has not kindcargo12",kindcargo12.exists());
			System.out.println("This has kindcargo12");
			
			UiObject kindcargo13 = kindcargo12.getChild(new
					UiSelector().className("android.widget.Button").index(2));
			assertTrue("There has not kindcargo13",kindcargo13.exists());
			System.out.println("This has kindcargo13");
			
			kindcargo13.click();						//点击确定
			
			/*=================================下单==========================================*/
			
			UiObject  placeorder = new UiObject(new
					UiSelector().className("android.widget.ScrollView").index(2));
			assertTrue("There has not placeorder",placeorder.exists());
			System.out.print("This has placeorder");
			
			UiObject placeorder1 = placeorder.getChild(new
					UiSelector().className("android.widget.Button").resourceId("com.olcps.dylogistics:id/btnSubmit"));
			assertTrue("There has not placeorder1",placeorder1.exists());
			System.out.print("This has placeorder1");
			
			placeorder1.click();
		
			sleep(3000);
			
			/*=================================支付==========================================*/
			
			UiObject pay = new UiObject(new
					UiSelector().className("android.widget.ScrollView").index(2));
			assertTrue("There has not pay",pay.exists());
			System.out.println("This has pay");
			
			UiObject pay1 = pay.getChild(new
					UiSelector().className("android.widget.Button").resourceId("com.olcps.dylogistics:id/btnSubmit"));
			assertTrue("There has not pay1",pay1.exists());
			System.out.println("This has pay1");
			
			pay1.click();
			
			sleep(1000);										//页面至支付页面
			
			UiObject pay2 = new UiObject(new
					UiSelector().className("android.widget.ScrollView").index(2));
			assertTrue("There has not pay2",pay2.exists());
			System.out.println("This has pay2");
			
			UiObject pay3 = pay2.getChild(new
					UiSelector().className("android.widget.LinearLayout").instance(0));
			assertTrue("There has not pay3",pay3.exists());
			System.out.println("This has pay3");
			
			UiObject pay4 = pay3.getChild(new
					UiSelector().className("android.widget.Button").index(4));
			assertTrue("There has not pay4",pay4.exists());
			System.out.println("This has pay4");
			
			pay4.click();										//点击‘去支付’
			
			UiObject pwd = new UiObject(new
					UiSelector().className("android.widget.LinearLayout").instance(0));
			assertTrue("There has not pwd",pwd.exists());
			System.out.println("This has pwd");
			
			UiObject pwd1 = pwd.getChild(new
					UiSelector().className("android.widget.EditText").index(2));
			assertTrue("There has not pwd",pwd1.exists());
			System.out.println("This has pwd1");
			
			pwd1.setText("123456");							//输入支付密码‘123456’
			
			UiObject pwd2 = new UiObject(new
					UiSelector().className("android.widget.LinearLayout").index(5));
			assertTrue("There has not pwd2",pwd2.exists());
			System.out.println("This has pwd2");
			
			UiObject pwd3 = pwd2.getChild(new
					UiSelector().className("android.widget.Button").index(2));
			assertTrue("There has not pwd3",pwd3.exists());
			System.out.println("This has pwd3");
			pwd3.click();
			
			sleep(2000);
			
			UiObject success = new UiObject(new
					UiSelector().className("android.widget.Button").resourceId("com.olcps.dylogistics:id/confirm_button"));
			
			assertTrue("There has not success",success.exists());
			System.out.print("This has success");
			
			success.click();							//点击OK
			
			/*================================协议下单结束==================================================*/
					
		}

}
