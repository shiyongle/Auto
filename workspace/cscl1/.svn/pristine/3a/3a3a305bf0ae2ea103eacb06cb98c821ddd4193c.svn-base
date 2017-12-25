package com.pc.controller.trigger;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
public class DelTriggerController extends QuartzJobBean{
	private ICouponsDao couponsDao;
	private ICouponsDetailDao couponsDetailDao;
	
	public void setCouponsDao(ICouponsDao couponsDao) {
		this.couponsDao = couponsDao;
	}

	public void setCouponsDetailDao(ICouponsDetailDao couponsDetailDao) {
		this.couponsDetailDao = couponsDetailDao;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {
		System.out.println("清除数据！");
		List<Integer> ids1 = this.couponsDao.getBeOverdueCoupons();
		if(ids1.size()>0){
			Integer[] ides1 = ids1.toArray(new Integer[ids1.size()]);
			this.couponsDao.updateCoupons(ides1);
		}
		
		List<Integer> ids = this.couponsDetailDao.getBeOverdueCouponsDetail();
		if(ids.size()>0){
			Integer[] ides = ids.toArray(new Integer[ids.size()]);
			this.couponsDetailDao.updateCouponsDetail(ides);
		}
		
	}
}
