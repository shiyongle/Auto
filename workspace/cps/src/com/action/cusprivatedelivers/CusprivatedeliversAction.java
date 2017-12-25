package com.action.cusprivatedelivers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.StringFormulaCell;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.cusprivatedelivers.CusPrivateDelivers;
import com.model.deliverapply.Deliverapply;
import com.model.deliverapply.DeliverapplyQuery;
import com.model.productdemandfile.Productdemandfile;
import com.model.supplier.Supplier;
import com.model.userCustomer.UserCustomer;
import com.opensymphony.xwork2.ModelDriven;
import com.service.cusprivatedelivers.CusprivatedeliversManager;
import com.service.customer.CustomerManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.userCustomer.UserCustomerManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.Params;


public class CusprivatedeliversAction extends BaseAction implements ModelDriven<CusPrivateDelivers>{
	private static final long serialVersionUID = 6911175071199142322L;
	protected static final String SHOPONLINE_JSP   = "/pages/deliverapply/shopOnline.jsp";
	@Autowired
	private CusprivatedeliversManager cusprivatedeliversManager;

	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private UserCustomerManager userCustomerManager;
	@Autowired
	private CustomerManager customerManager;
	private CusPrivateDelivers cusPrivateDelivers=new CusPrivateDelivers();
	
	private PageModel<CusPrivateDelivers> pageModel;// 分页组件
	@Override
	public CusPrivateDelivers getModel() {
		return cusPrivateDelivers;
	}
	
	public CusPrivateDelivers getCusPrivateDelivers() {
		return cusPrivateDelivers;
	}

	public void setCusPrivateDelivers(CusPrivateDelivers cusPrivateDelivers) {
		this.cusPrivateDelivers = cusPrivateDelivers;
	}
	
	public String shoppingDetail()
	{
		getRequest().setAttribute("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//当前时间
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userFid);
		String sql = "select 1 from t_pdt_productreqallocationrules where fbacthstock=1 and fcustomerid='%s'";
		if(this.cusprivatedeliversManager.QueryExistsBySql(String.format(sql, fcustomerid))){
			getRequest().setAttribute("fbacthstock", true);//是否批量备货
		}
		return SHOPONLINE_JSP;
	}
	
	/**
	 * APP接口 获取购物车记录getShopCarInfos.do 
	 */
	/*** 加载列表数据*/
	public String loadShopCar(){
		String ftype=getRequest().getParameter("ftype");
		String keyword=getRequest().getParameter("keyword");
		String currentUserId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		List<Object> paramls = new ArrayList<Object>();
//		String where = " where  c.fcreatorid = '"+currentUserId+"' and  c.ftype='"+ftype+"'";//+currentUserId+"'";
		String where = " where  c.fcreatorid = ? and  c.ftype=?";//+currentUserId+"'";
		paramls.add(currentUserId);
		paramls.add(ftype);
		Object[] params = null;
		Map<String, String> orderby =new HashMap<String, String>();
		orderby.put("c.fsupplierid","ASC");
//			paramls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		if(keyword !=null){
			where = where +" and (p.fname like ? or p.FSPEC like ? )";
			paramls.add("%"+keyword+"%");
			paramls.add("%"+keyword+"%");
		}
		params =(Object[])paramls.toArray(new Object[paramls.size()]);
		pageModel = this.cusprivatedeliversManager.findBySql(where, params, orderby, pageNo, pageSize);
		List<CusPrivateDelivers> ls = pageModel.getList();
		List<Supplier> supplierilist=new ArrayList<Supplier>();
		String supplierid="";
		Supplier csupplier=null;
//		String configsql="SELECT c.fdays,s.fisManageStock FROM t_sys_supplier s INNER JOIN t_sys_supplierdelivertime c ON s.fid=c.fsupplierid where s.fid ='%s'";
		String configsql="SELECT c.fdays,s.fisManageStock FROM t_sys_supplier s INNER JOIN t_sys_supplierdelivertime c ON s.fid=c.fsupplierid where s.fid =:fid";
		List<HashMap<String,Integer>> list=null;//存制造商交期设置
		for(int i=0;i<ls.size();i++){
			if("".equals(supplierid)||!supplierid.equals(ls.get(i).getFsupplierid()))
			{
				supplierid=ls.get(i).getFsupplierid(); 
				csupplier=new Supplier(ls.get(i).getFsupplierid(),ls.get(i).getFsuppliername());
				//csupplier.setCusprivate(new ArrayList<CusPrivateDelivers>());
				supplierilist.add(csupplier);
				if("0".equals(ftype)){//为要货时才查询最小交期
				Params prm=new Params();
				prm.put("fid", supplierid);
//				list=cusprivatedeliversManager.QueryBySql(String.format(configsql, supplierid), new Params());
				list=cusprivatedeliversManager.QueryBySql(configsql,prm);
				if(list.size()>0)
				{
					csupplier.setFusedstatus(list.get(0).get("fdays"));//暂用字段表示最小交期 VC
					csupplier.setFisManageStock(list.get(0).get("fisManageStock"));//是否做出入库管理
				}
				}
			}
			csupplier.getCusprivate().add(ls.get(i));
			/*
			 * 改为通用的方法获取图片字节流;
			 * ls.get(i).setPathImg(this.getPicture(ls.get(i).getFcusproduct()));*/
		}
	
		
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("list",supplierilist);
			m.put("totalRecords", pageModel.getTotalRecords());//总条数
			m.put("pageNo", pageModel.getPageNo());//第几页
			m.put("pageSize", pageModel.getPageSize());//每页显示多少条
		
			JSONObject.fromObject(m);
			
		return writeAjaxResponse(JSONObject.fromObject(m).toString());
	}
	
	/*** 通过客户产品主键获取图片路径*/
	public String getPicture(String fid){
		String path = null;
		List<Productdemandfile> productfile = this.productdemandfileManager.getByParentId(fid);
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
			if(productfile.size()>0){
				path = (productfile.get(0).getFpath()).replace("vmifile", "smallvmifile");
				path =basePath + path.split("webapps")[1];
			}else{
				path = basePath + "/vmifile/defaultpic.png";
			}
		return path;
	}
	
	
	/**
	 * APP接口 删除购物车记录deleteShopCarRecords.do 
	 */
	/*** 删除*/
	public String delete(){
		String[] s=getRequest().getParameterValues("ides");//字符串数组

		this.cusprivatedeliversManager.deleteImpl(s);

		return writeAjaxResponse("success");
	}
	
	/**
	 *  购物车数据更新，  APP接口updateShopFamount.do  (只更新数量值)
	 */
	public String update() throws Exception{
		String updatejson=getRequest().getParameter("cusprivatedelivers");
		  JSONArray json = JSONArray.fromObject(updatejson);

		  List<CusPrivateDelivers>	list=(ArrayList<CusPrivateDelivers>)JSONArray.toList(json,  CusPrivateDelivers.class);
		 try{
		 this.cusprivatedeliversManager.updateImpl(list);
		  } catch(Exception e){
			  return writeAjaxResponse("failure");
		 }
		return writeAjaxResponse("success");
	}
	/**
	 *  购物车下单  APP接口SaveShopDeliverapply.do 
	 */
	public String convertorders()
	{
		String currentUserId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();

		String[] s=getRequest().getParameterValues("ides");//字符串数组

		String error = this.cusprivatedeliversManager.createDeliverapply(s,currentUserId);

		return writeAjaxResponse(error ==null ? "success" : error);
	}
	
	//要货、备货保存购物车
	/**
	 * APP接口：saveCusprivatedelivers.do 要货加入购物车 (逻辑一致)
	 */
		public String saveCusprivatedelivers(){
			String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			UserCustomer uc =this.userCustomerManager.getByUserId(userId);
			String fids = cusPrivateDelivers.getFcusproduct();
		
			this.cusprivatedeliversManager.saveCusprivatedelivers(fids, cusPrivateDelivers,userId,uc.getFcustomerid());
			return writeAjaxResponse("success");
		}
}
