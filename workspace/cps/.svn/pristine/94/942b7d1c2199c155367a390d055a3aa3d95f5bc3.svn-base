package com.action.myDelivery;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.myDelivery.MyDelivery;
import com.model.myDelivery.MyDeliveryQuery;
import com.opensymphony.xwork2.ModelDriven;
import com.service.myDelivery.MyDeliveryManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.SortListUtil;

public class MyDeliveryAction extends BaseAction implements ModelDriven<MyDelivery> {
	
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-9-1 下午1:25:43
	*/
	private static final long serialVersionUID = -3954724788679793223L;
	protected static final String LIST_JSP   = "/pages/mydelivery/list.jsp";
	@Autowired
	private MyDeliveryManager myDeliveryManager;
	private MyDelivery myDelivery=new MyDelivery();
	private PageModel<MyDelivery> pageModel;// 分页组件
	private MyDeliveryQuery mydeliveryQuery;
	public MyDelivery getMyDelivery() {
		return myDelivery;
	}

	public void setMyDelivery(MyDelivery myDelivery) {
		this.myDelivery = myDelivery;
	}
	
	public PageModel<MyDelivery> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<MyDelivery> pageModel) {
		this.pageModel = pageModel;
	}
	
	public MyDeliveryQuery getMydeliveryQuery() {
		return mydeliveryQuery;
	}

	public void setMydeliveryQuery(MyDeliveryQuery mydeliveryQuery) {
		this.mydeliveryQuery = mydeliveryQuery;
	}

	@Override
	public MyDelivery getModel() {
		return this.myDelivery;
	}
	
	public String list(){
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		String end = sdf1.format(new Date());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());
		rightNow.add(Calendar.MONTH,-3);//日期减3个月
		Date dt1=rightNow.getTime();
        String begin = sdf1.format(dt1);
		getRequest().setAttribute("begin", begin);
		getRequest().setAttribute("end", end);
		return LIST_JSP;
	}
	
	public String load(){
		String where1 =" where sa.fstate <> 1 and sa.fcustomer IN ( ? )";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		if(mydeliveryQuery !=null){
			if(!("").equals(mydeliveryQuery.getFarrivetimeBegin())){//下单开始
				where1 =where1 + " and date_format(sa.fprinttime,'%Y-%m-%d') >= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeBegin());
			}
			if(!("").equals(mydeliveryQuery.getFarrivetimeEnd())){//下单结束
				where1 =where1 + " and date_format(sa.fprinttime,'%Y-%m-%d') <= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeEnd());
			}
			if( !("").equals(mydeliveryQuery.getSearchKey())){//关键字
				where1 =where1 + " and (sa.fsuppliername like ?  or sa.fnumber like ? or p.fname like ? or p.fspec like ? or sa.fcustAddress like ? or sd.fdescription like ?)";
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
			}
		}
		String where2 =" where d.fcustomerid IN ( ? )";
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		if(mydeliveryQuery !=null){
			if(!("").equals(mydeliveryQuery.getFarrivetimeBegin())){//下单开始
				where2 =where2 + " and date_format(d.fouttime,'%Y-%m-%d') >= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeBegin());
			}
			if(!("").equals(mydeliveryQuery.getFarrivetimeEnd())){//下单结束
				where2 =where2 + " and date_format(d.fouttime,'%Y-%m-%d') <= ? ";
				ls.add(mydeliveryQuery.getFarrivetimeEnd());
			}
			if( !("").equals(mydeliveryQuery.getSearchKey())){//关键字
				where2 =where2 + " and (_suppliername like ?  or d.fnumber like ? or d.fpcmordernumber like ? or _custpdtname like ? or _spec like ? or _mspec like ? or d.faddress like ? or d.fdescription like ? )";
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
				ls.add("%" +mydeliveryQuery.getSearchKey() +"%");
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("farrivetime", "DESC");
		orderby.put("saleorderNumber", "DESC");
		pageModel = this.myDeliveryManager.findBySql(where1,where2, queryParams, orderby, pageNo, pageSize);
		HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("list", pageModel.getList());
			map.put("totalRecords", pageModel.getTotalRecords());//总条数
			map.put("pageNo", pageModel.getPageNo());//第几页
			map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/***最近7天收货变动曲线数据查询*/
	public String getLinear_x(){
		String currentCustomerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		Map<String,String> orderby =new HashMap<String,String>();
		orderby.put("farrivetime", "ASC");
		String where =" AND fcustomerid IN ( ? )";
		List<Object> ls = new ArrayList<Object>();
		ls.add(currentCustomerId);
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		List<MyDelivery> list = this.myDeliveryManager.getLine(where, queryParams, orderby);
		List<MyDelivery> afterl = this.get7Day(list);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", afterl);
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	public List<MyDelivery> get7Day(List<MyDelivery> list){
		List<MyDelivery> aftl =new ArrayList<MyDelivery>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c6 = Calendar.getInstance();
		Calendar c5 = Calendar.getInstance();
		Calendar c4 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c6.add(Calendar.DAY_OF_MONTH, -6);
		c5.add(Calendar.DAY_OF_MONTH, -5);
		c4.add(Calendar.DAY_OF_MONTH, -4);
		c3.add(Calendar.DAY_OF_MONTH, -3);
		c2.add(Calendar.DAY_OF_MONTH, -2);
		c1.add(Calendar.DAY_OF_MONTH, -1);
		List<String> ls =new ArrayList<String>();
		ls.add(sf.format(c6.getTime()));
		ls.add(sf.format(c5.getTime()));
		ls.add(sf.format(c4.getTime()));
		ls.add(sf.format(c3.getTime()));
		ls.add(sf.format(c2.getTime()));
		ls.add(sf.format(c1.getTime()));
		ls.add(sf.format(new Date()));
		for(int j = 0;j<list.size();j++){
			MyDelivery af =new MyDelivery();
			for(int i =0;i<ls.size();i++){
				if(list.get(j).getFarrivetime().contains(ls.get(i))){//包含
					af.setFarrivetime(list.get(j).getFarrivetime());
					af.setFamounts(list.get(j).getFamounts());
					aftl.add(af);
					ls.remove(i);
					break;
				}
			}
		}
		for(int m =0;m<ls.size();m++){
			MyDelivery af2 =new MyDelivery();
			af2.setFarrivetime(ls.get(m));
			af2.setFamounts(new BigDecimal(0));
			aftl.add(af2);
		}
		//调用排序通用类   
		SortListUtil<MyDelivery> sortList = new SortListUtil<MyDelivery>();   
		sortList.Sort(aftl, "getFarrivetime", "asc"); 
		return aftl;
	}

	public String loadNew(){
		String where ="";
		//2015-11-12 送货凭证据where条件
		String whereSHPZ="";
		//2015-11-12 发货单where条件
		String whereXSFH="";
		Object[] queryParams=null;
		List<Object> whereSHPZls = new ArrayList<Object>();
		List<Object> whereXSFHls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
		whereSHPZls.add(customerId);
		whereXSFHls.add(customerId);
		if(mydeliveryQuery!=null){
			if(!("").equals(mydeliveryQuery.getTimeQuantum())){
				String timeQuantum = mydeliveryQuery.getTimeQuantum();
				String beginTime =  "'"+timeQuantum.split(" 到 ")[0].trim()+"'";
				String endTime = "'"+timeQuantum.split(" 到 ")[1].trim()+" 23:59:59"+"'";
//				whereSHPZ += " and (sd.fprinttime between "+beginTime + " and "+endTime+" ) ";
				whereSHPZ += " and (sd.fprinttime between ? and ? ) ";
				whereSHPZls.add(beginTime);
				whereSHPZls.add(endTime);
//				whereXSFH += " and (d.fouttime between "+beginTime + " and "+endTime+" ) ";
				whereXSFH += " and (d.fouttime between ? and ? ) ";
				whereXSFHls.add(beginTime);
				whereXSFHls.add(endTime);
			}
			if( !("").equals(mydeliveryQuery.getSearchKey())){//关键字
				String searchKey = mydeliveryQuery.getSearchKey();
				String likeSearchKey = " like '%"+searchKey+"%' ";
//				whereSHPZ += " and (sd.fsuppliername "+likeSearchKey+" or sd.fnumber "+likeSearchKey+" or cpdt.fname "+likeSearchKey+" or cpdt.fspec "+likeSearchKey+" or sd.fcustAddress "+likeSearchKey+" or sde.fdescription "+likeSearchKey+" ) ";
				whereSHPZ += " and (sd.fsuppliername ? or sd.fnumber ? or cpdt.fname ? or cpdt.fspec ? or sd.fcustAddress ? or sde.fdescription ? ) ";
				for (int i = 0; i < 6; i++) {
					whereSHPZls.add(likeSearchKey);
				}
//				whereXSFH +=  " and (d._suppliername "+likeSearchKey+" or d.fnumber "+likeSearchKey+" or d._custpdtname "+likeSearchKey+" or d._spec "+likeSearchKey+" or d.faddress "+likeSearchKey+" or d.fdescription "+likeSearchKey+" ) ";
				whereXSFH +=  " and (d._suppliername ? or d.fnumber ? or d._custpdtname ? or d._spec ? or d.faddress ? or d.fdescription ? ) ";
				for (int i = 0; i < 6; i++) {
					whereXSFHls.add(likeSearchKey);
				}
			}
			//2015-11-12 用“龖”分开两个where条件
			if(!"".equals(whereSHPZ) || !"".equals(whereXSFH)){
				where = whereSHPZ+"龖"+whereXSFH;
			}
		}
		whereSHPZls.addAll(whereXSFHls);
		
		queryParams = (Object[])whereSHPZls.toArray(new Object[whereSHPZls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("t.farrivetime", "DESC");
		orderby.put("t.saleorderNumber ", "DESC");
		pageModel = this.myDeliveryManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
		List<MyDelivery> dlist = pageModel.getList();
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
	return writeAjaxResponse(JSONUtil.getJson(map));
	}
}




