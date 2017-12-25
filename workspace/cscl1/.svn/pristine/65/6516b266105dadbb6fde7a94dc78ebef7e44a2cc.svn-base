package com.pc.dao.rating;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Rating;

public interface IratingDao extends IBaseDao<CL_Rating, java.lang.Integer> {
 
	public Page<CL_Rating> findRatePage(PageRequest query);
	
  /**根据订单编号和评价类型查评价*/
	public CL_Rating getByOrderNumAndType(String orderNum,Integer ratingType);
}
