package com.pc.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pc.dao.financeBill.IFinanceBillDao;
import com.pc.dao.order.IorderDao;
import com.pc.model.CL_FinanceBill;
import com.pc.model.CL_Order;

public class CsclTimerTask {
	@Resource
	private IFinanceBillDao financeBillDao;
	@Resource
	private IorderDao orderDao;
	//2016-5-6 by lu  每月1号0点自动生成上月账单
	
	void getLastMonthBill() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		List<Map<String,Object>> resList = financeBillDao.getLastMonthBill("getLastMonthBill");
		CL_FinanceBill fbinfo;
		if(resList.size()>0){			
			for(Map<String,Object> map : resList){
				fbinfo = new CL_FinanceBill();
				fbinfo.setFbillDate(sdf.parse(map.get("fbillDate").toString()));
				fbinfo.setFcustId(Integer.parseInt(map.get("fcustId").toString()));
				fbinfo.setFcustName(map.get("fcustName").toString());
				fbinfo.setFbillAmount(new BigDecimal(map.get("fbillAmount").toString()));
				fbinfo.setFunPayAmount(BigDecimal.ZERO);
				fbinfo.setFverification(false);
				financeBillDao.save(fbinfo);
			}
		}
	}
	
	//定时更新订单状态
	void updateOrderStatus() throws Exception{
		Integer userId;
		Date date=new Date();
	    	List<CL_Order> orders =orderDao.getByCreator();
	    	if(orders.size()>0){
		    	for(CL_Order order:orders){
		    		long ss=date.getTime()-order.getCreateTime().getTime();
		    		long time=ss-15*60000;
		    		if(time>=0){
						this.orderDao.updateStatusByOrderId(order.getId(), 6);
		    		}
		    	}
	    	}
	}
}
