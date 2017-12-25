package Com.Base.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import Com.Base.Util.SpringContextUtils;
import Com.Base.data.LogAction;
import Com.Dao.System.ISimplemessageDao;
import Com.Dao.order.ICusDeliversDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.IFirstproductdemandDao;
import Com.Dao.order.IOrderappraiseDao;

@Component
public class TaskTimer {
	@LogAction("定时任务：纸板需求自动接收,快速下单自动生成")
	@Scheduled(cron="0 0/5 *  * * ? ")   //每10分钟执行一次  
    void CardBoardTask()
	{

		/*
		 * 20150422 cd 纸板需求自动接收;
		 */
//		IDeliverorderDao Deliverorderdao  = (IDeliverorderDao) SpringContextUtils
//				.getBean("CusDeliversDao");
//		Deliverorderdao.ExecReceiveBoardOrders();
		IDeliverorderDao Deliverorderdao  = (IDeliverorderDao) SpringContextUtils
				.getBean("DeliverorderDao");
		Deliverorderdao.ExecReceiveBoardOrders();
		
		
		IDeliverapplyDao deliverapplyDao = (IDeliverapplyDao)SpringContextUtils.getBean("deliverapplyDao");
		deliverapplyDao.ExecAutoCreateDeliverapply();
		
	}
	
	@Scheduled(cron="0 0/10 *  * * ? ")   //每10分钟执行一次  
     void ZTExport()
	{
		try {
			
			ICusDeliversDao cusdeliverdao  = (ICusDeliversDao) SpringContextUtils
					.getBean("CusDeliversDao");
			cusdeliverdao.ExecExportZTcusdelivers();
			ISimplemessageDao SimplemessageDao  = (ISimplemessageDao) SpringContextUtils
					.getBean("simplemessageDao");
			SimplemessageDao.RequstReceiving();
			
			/*
			 * 自动评价;
			 */
			IOrderappraiseDao orderappraiseDao = (IOrderappraiseDao) SpringContextUtils
					.getBean("orderappraiseDao");
			orderappraiseDao.Execautoorderapparises();
			
			
//			IWarehouseDao cusdeliverdao  = (IWarehouseDao) SpringContextUtils
//			.getBean("WarehouseDao");
//			Warehouse info=new Warehouse();
//			info.setFid(cusdeliverdao.CreateUUid());
//			info.setFcreatetime(new Date());
//			info.setFnumber("adff");
//			info.setFname("fname");
//			cusdeliverdao.ExecSave(info);
//			System.out.print("开始执行任务"+(new Date()).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.print(e.getMessage());
			}
	}
	
	//见http://www.blogjava.net/hao446tian/archive/2012/02/13/369872.html
	@Scheduled(cron="0 0/15  08-17  * * ? ")   //每 十五分分钟执行一次（已分配）
    void ExecNewproductdemand()
	{
		try {
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("HH:mm");
			String time=format.format(date);
			String timeStrs = "08:30;10:30;13:30;15:30";
			IFirstproductdemandDao fpdmDao  = (IFirstproductdemandDao) SpringContextUtils
					.getBean("FirstproductdemandDao");	
			if(timeStrs.contains(time)){
				//已接收未设计
				fpdmDao.ExecRcvproductdemand();
			}	
			fpdmDao.ExecNewproductdemand();
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
	}
	
	@Scheduled(cron="0 0 10,15 * * ?")   //10点，15点执行一次（未确认）
    void ExecUnconfirmscheme()
	{
		try {			
			IFirstproductdemandDao fpdmDao  = (IFirstproductdemandDao) SpringContextUtils
					.getBean("FirstproductdemandDao");		
			fpdmDao.ExecUnconfirmscheme();
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
	}
}
