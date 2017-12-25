package com.pc.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.carRanking.IcarRankingDao;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsActivity.ICouponsActivityDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
//import com.pc.dao.userVoucher.IuserVoucherDao;
import com.pc.util.pay.OrderMsg;


@Component
public class CsclTimer {
	
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private IorderDao orderDao;
	@Resource
 	private IcarRankingDao carRankingDao;
	@Resource
	private ICouponsActivityDao activityDao;
	@Resource
	private IaddtoDao addtoDao;
	@Resource 
	private IFinanceStatementDao financeStatementDao;
	
//  @Resource
// 	private IuserVoucherDao iuserVoucherDao;
	@Scheduled(cron="0 0 0  * * ? ")   
//	@Scheduled(cron="0/5 * *  * * ? ")//测试每5秒执行一次 
	protected void executeInternal(){
//		System.out.println("清除数据！");
		List<Integer> ids = this.couponsDetailDao.getBeOverdueCouponsDetail();
		if(ids.size()>0){
			Integer[] ides = ids.toArray(new Integer[ids.size()]);
			this.couponsDetailDao.updateCouponsDetail(ides);
		}
		List<Integer> ids1 = this.activityDao.getBeOverdueCouponsActivity();
		if(ids1.size()>0){
			Integer[] ides1 = ids1.toArray(new Integer[ids1.size()]);
			this.activityDao.updateCoupons(ides1);
		}
		
		
	}
	
	@Scheduled(cron="0 0 */1 * * ? ")   
//	@Scheduled(cron="0 0/5 * * * ? ")   
	//定时更新订单状态,一小时
	void updateOrderStatus() throws Exception {
		HashMap<String, Object> param = new HashMap<>();
		Date date = new Date();
		List<CL_Order> orders = orderDao.getByCreator();
		if (orders.size() > 0) {
			for (CL_Order order : orders) {
				long ll = new Date().getTime() - order.getLoadedTime().getTime();
				if (ll - 120 * 60000 > 0) {
					if (order.getStatus() == 9) {
						order.setStatus(6);
					} else {
						long l = financeStatementDao.findFrelatedIdList(
								order.getId(), 1, order.getCreator(), -1);
						if (l == 0) {
							Integer couponsId1 = order.getCouponsDetailId();//查询订单上是否有增值服务优惠券
							Integer couponsId2 = order.getCouponsId();//查询订单上是否有订单优惠券
							if(couponsId1 != null && couponsId2 == null){
								CL_CouponsDetail detail = couponsDetailDao.getById(couponsId1);
								detail.setIsUse(0);
								couponsDetailDao.update(detail);
								order.setStatus(6);
								order.setCouponsDetailId(null);
							} else if (couponsId1 == null && couponsId2 != null) {
								CL_CouponsDetail detail = couponsDetailDao.getById(couponsId2);
								detail.setIsUse(0);
								couponsDetailDao.update(detail);
								order.setStatus(6);
								order.setCouponsId(null);
							} else if (couponsId1 != null && couponsId2 != null) {
								CL_CouponsDetail detail1 = couponsDetailDao.getById(couponsId1);
								CL_CouponsDetail detail2 = couponsDetailDao.getById(couponsId2);
								detail1.setIsUse(0);
								detail2.setIsUse(0);
								couponsDetailDao.update(detail1);
								couponsDetailDao.update(detail2);
								order.setStatus(6);
								order.setCouponsDetailId(null);
								order.setCouponsId(null);
							}else {
								order.setStatus(6);
							}
						}
					}
					orderDao.update(order);
				}
			}
		}
		List<CL_FinanceStatement> finances = financeStatementDao.getByWait();
		if (finances.size() > 0) {
			for (CL_FinanceStatement finance : finances) {
				long ss = date.getTime() - finance.getFcreateTime().getTime();
				long time = ss - 120 * 60000;
				if (time > 0) {
					OrderMsg orderMsg = new OrderMsg();
					orderMsg.setOrder(finance.getNumber());
					orderMsg.setUserId(-1);// 判断是否是定时器
					if (finance.getFbusinessType() == 2) {
						CL_Addto addto = addtoDao.getById(Integer
								.parseInt(finance.getFrelatedId()));
						param.put("addto", addto);
					}
					financeStatementDao.updateBusinessType(orderMsg, param);
				}
			}
		}
		/*
		 * List<CL_Order> orders =orderDao.getByCreator(); if(orders.size()>0){
		 * for(CL_Order order:orders){ long
		 * ss=date.getTime()-order.getCreateTime().getTime(); long
		 * time=ss-5*60000; if(time>=0){ HashMap<String,Object> m =
		 * orderDao.autoPayOrderFreight(order);
		 * //订单每5分钟自动取消,先调支付的订单校验接口失败后再执行更改取消状态; if(!(m.get("payed")!=null &&
		 * m.get("payed").equals("true"))){
		 * this.orderDao.updateStatusByOrderIdAndTask(order.getId(), 6); }
		 * 
		 * } } }
		 * 
		 * //追加运费订单每5分钟自动取消前,先调支付的订单校验接口; List<CL_Addto> addtolist =
		 * addtoDao.getNonPayment(); if(addtolist.size()>0){ for(CL_Addto
		 * addto:addtolist){ long
		 * ss=date.getTime()-addto.getFcreatTime().getTime(); long
		 * time=ss-5*60000; if(time>=0){ HashMap<String,Object> m =
		 * addtoDao.autoPayAddtoFreight(addto);
		 * //追加运费订单每5分钟自动取消,先调支付的订单校验接口失败后再执行更改取消状态; if(!(m.get("payed")!=null
		 * && m.get("payed").equals("true"))){
		 * this.addtoDao.updateStatusByFidAndTask(addto.getFid(), 6); } } } }
		 */
	}
	
	/**
	@Scheduled(cron="0 0/5 * * * ? ")   
	//定时更新订单状态
	void checkPayStatus() throws Exception{
		System.out.println("支付校验定时器启动 %%(&**()(*)(!");
		List<CL_UserVoucher> vous=iuserVoucherDao.getByStatusInTast();
		List<Integer> fstatus=new  ArrayList<>();
		List<Integer> fisover=new ArrayList<>();
		Date date=new Date();
		String BankName="";
		String re="";
		JSONObject o=new JSONObject(); 
		for(CL_UserVoucher vou:vous){
			if(vou.getFtype()==1){
				BankName=PcWebPayUtil.PAY_ALIPAY_QR;
			}else if(vou.getFtype()==2){
				BankName=PcWebPayUtil.PAY_WECHAT_QR;
			}else if(vou.getFtype()==3){
				BankName=vou.getFbankName();
			}else{
				continue;
			}
			
			long ss=date.getTime()-vou.getFcreatorTime().getTime();
    		long time=ss-8*60000;
    		if(time>=0){
    			fisover.add(vou.getFid());
  		    }
    		re=PcWebPayUtil.CheckPayStatus(vou.getNumber(),vou.getVmiUserPhone(),BankName) ;
    		o=JSONObject.fromObject(re);
    		if(o.get("success").equals("true")){
    			fstatus.add(vou.getFid());
		    }
		}
		Integer[] fiso=fisover.toArray(new Integer[fisover.size()]);
		Integer[] fsta=fstatus.toArray(new Integer[fstatus.size()]);
		if(fiso.length>0){
			iuserVoucherDao.updateVouCherDetail(fiso);
		}
		if(fsta.length>0){
		  iuserVoucherDao.updateVouCherStatusDetail(fsta);
		}
		
	}
*/
	
	 
 	

	//定时更新前三名司机信息 
//	@Scheduled(cron="0 0 0  * * ? ")   
//	   void updateCarRanking() throws ParseException{
//		 List<CL_CarRanking> rankingList=carRankingDao.getAll();
// 		 System.out.println("定时更新前三名司机信息");
//	 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//			Calendar c = Calendar.getInstance();  
//	       c.add(Calendar.DATE, - 7);  
//	       Date date=new Date();
//			try {
//				date = sdf.parse((sdf.format(c.getTime())));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//	   	 List<CL_FinanceStatement> fas=iFinanceStatementDao.getByUserIdAndCar(date);
//	   	 if(fas.size()>0){
//		    	 if(rankingList.size()>0){
//		            	  for(int i=0;i<3;i++){
//		            		  CL_CarRanking cad=new CL_CarRanking();
//		            		  cad.setFbalance(fas.get(i).getSumFamount().setScale(0,BigDecimal.ROUND_HALF_UP));
//		            		  cad.setFname(fas.get(i).getDriverName());
//		            		  cad.setFnumber(fas.get(i).getCarNum());
//		            		  cad.setFid(i+1);
//		            		  carRankingDao.update(cad);
//		            	  }
//		          }  
//			      else{
//			    	  for(int i=0;i<3;i++){
//		    		  CL_CarRanking cad=new CL_CarRanking();
//            		  cad.setFbalance(fas.get(i).getSumFamount().setScale(0,BigDecimal.ROUND_HALF_UP));
//            		  cad.setFname(fas.get(i).getDriverName());
//            		  cad.setFnumber(fas.get(i).getCarNum());
//	           		  carRankingDao.save(cad);
//	           	  }
//			 }
//	   	 }
//  } 
}
