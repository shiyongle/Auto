package com.action.productdef;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.productdef.CommonMaterial;
import com.model.productdef.MaterialLimit;
import com.model.productdef.MyProductDefQuery;
import com.model.productdef.Productdef;
import com.model.supplier.Supplier;
import com.opensymphony.xwork2.ModelDriven;
import com.service.customer.CustomerManager;
import com.service.custproduct.TBdCustproductManager;
import com.service.productdef.ProductdefManager;
import com.service.saledeliver.SaledeliverManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.Params;
import com.util.StringUitl;

/*
 * CPS-VMI-wangc 材料、产品档案
 */

public class ProductdefAction extends BaseAction implements ModelDriven<Productdef>{
	
	private static final long serialVersionUID = 8895452689872402506L;
	protected static final String BOARD_COMMON_JSP   = "/pages/board/commonFmaterial.jsp";
	protected static final String DeliverTimeConfig_JSP = "/pages/productdef/MyDeliverTimeConfig.jsp";
	@Autowired
	private ProductdefManager productdefManager;	
	@Autowired
	private TBdCustproductManager custproductManager;
	@Autowired
	private CustomerManager customerManager;
	
	private Productdef productdef = new Productdef();
	private PageModel<Productdef> pageModel;
	private MyProductDefQuery myProductDefQuery;

	public PageModel<Productdef> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Productdef> pageModel) {
		this.pageModel = pageModel;
	}

	public MyProductDefQuery getMyProductDefQuery() {
		return myProductDefQuery;
	}

	public void setMyProductDefQuery(MyProductDefQuery myProductDefQuery) {
		this.myProductDefQuery = myProductDefQuery;
	}

	@Override
	public Productdef getModel() {
		return productdef;
	}

	public Productdef getProductdef() {
		return productdef;
	}

	public void setProductdef(Productdef productdef) {
		this.productdef = productdef;
	}
	
	public String getSupplierCardboardList(){
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
		String where = "";
		ls.add(customerId);
		if(myProductDefQuery!=null){
			if(!"".equals(myProductDefQuery.getSearchKey())){
				String queryKey = myProductDefQuery.getSearchKey();
//				where += " and (e.fqueryname like '%"+queryKey+"%') ";
				where += " and (e.fqueryname like ?) ";
				ls.add("%"+queryKey+"%");
			}
			if(!"".equals(myProductDefQuery.getSupplierid())){
//				where += " and e.fsupplierid = '"+myProductDefQuery.getSupplierid()+"' ";
				where += " and e.fsupplierid = ? ";
				ls.add(myProductDefQuery.getSupplierid());
			}
			queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		}
		pageModel = this.productdefManager.findSupplierCardboardList(pageNo, pageSize, where, queryParams);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	public String getCommonMaterialList(){
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
		String where =" and e1.fcustomerid  = ?";
		ls.add(customerId);
		if(myProductDefQuery!=null){
			if(!"".equals(myProductDefQuery.getSearchKey())){
//				String queryKey = myProductDefQuery.getSearchKey();
//				where += " and (e.fname like '%"+queryKey+"%' or e.flayer like '%"+queryKey+"%' or e.ftilemodelid like '%"+queryKey+"%') ";			
				String queryKey ="%"+ myProductDefQuery.getSearchKey()+"%";
				where += " and (e.fname like ? or e.flayer like ? or e.ftilemodelid like ?) ";	
				ls.add(queryKey);
				ls.add(queryKey);
				ls.add(queryKey);
			}
			if(!"".equals(myProductDefQuery.getSupplierid())){
//				where += " and e.fsupplierid = '"+myProductDefQuery.getSupplierid()+"' ";
				where += " and e.fsupplierid = ? ";
				ls.add(myProductDefQuery.getSupplierid());
			}	
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		pageModel = this.productdefManager.findCommonMaterialList(pageNo, pageSize, where, queryParams);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));		
	}
	
	/*** 设为常用*/
	public String setCommonMaterial(){
		String[] fids = getRequest().getParameter("fids").split(",");
		String fstate = getRequest().getParameter("fstate");
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		if(fstate.equals("1")){
			CommonMaterial obj;
			for(String materialid: fids){
				obj = new CommonMaterial();			
				obj.setFid(productdefManager.CreateUUid());
				obj.setFcustomerid(customerId);
				obj.setFsupplierid("39gW7X9mRcWoSwsNJhU12TfGffw=");
				obj.setFmaterialid(materialid);
				productdefManager.save(obj);
			}	
		}
		else{
			String sql = "delete from t_sys_commonmaterial where fid "+this.getCondition(getRequest().getParameter("fids"));
			productdefManager.ExecBySql(sql);
		}
		return writeAjaxResponse("success");
	}
	
	public String getCondition(String fidcls){
		String condition;
		if(fidcls.split(",").length==1){
			condition = " = '"+fidcls+"' ";
		}else{
			condition = " in ('"+fidcls.replace(",", "','")+"') ";
		}
		return condition;
	}
	
	//常用材料
	public String commonFmaterial(){
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		List<Supplier> sp= this.custproductManager.getBySupplier(userFid);
		getRequest().setAttribute("supplier", sp);
		return BOARD_COMMON_JSP;//返回后台订单列表
	}
	
	public String loadMaterial(){//下单时材料获取数据
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
		pageSize=getRequest().getParameter("pageSize")==null?50:new Integer(getRequest().getParameter("pageSize"));
		String term=getRequest().getParameter("term");
		String fsupplierid=getRequest().getParameter("fsupplierid");
		if(StringUitl.isNullOrEmpty(fsupplierid))//制造商为空不进行查询，直接返回空数据
		{
			HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("list", new ArrayList());
			map.put("totalRecords", 0);//总条数
			map.put("pageNo", pageNo);//第几页
			map.put("pageSize", pageSize);//每页显示多少条
			return writeAjaxResponse(JSONUtil.getJson(map));	
		}
		Params p=new Params();
//		String sql="select e.fid from t_pdt_productdef e inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e1.fcustomerid='%s' and  e.fsupplierid='%s'and  e.fboxtype = 1 and e.feffect=1 ";
		String sql="select e.fid from t_pdt_productdef e inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e1.fcustomerid=:fcid and  e.fsupplierid=:fsid and  e.fboxtype = 1 and e.feffect=1 ";
		p.put("fcid",customerId );
		p.put("fsid",fsupplierid );
		if(myProductDefQuery==null) myProductDefQuery=new  MyProductDefQuery();
		myProductDefQuery.setSupplierid(fsupplierid);
		if(!StringUitl.isNullOrEmpty(term))
		{
			myProductDefQuery.setSearchKey(term);
		}
		sql=String.format(sql, customerId,fsupplierid);
		if(productdefManager.QueryExistsBySql(sql,p))
		{
			return getCommonMaterialList();
		}
		return getSupplierCardboardList();
	}
	
	/****************************************************产品档案*********************************/
	
	@Autowired
	private SaledeliverManager saledeliverManager;
	protected static final String PRODUCTLIST   = "/pages/productdef/productRecord_list.jsp";
	protected static final String PRODUCTEXECLIN   = "/pages/productdef/productexcel.jsp";
	protected static final String PRODUCTEDIT   = "/pages/productdef/productRecord_addBox.jsp";
	protected static final String LIMITPAGE   = "/pages/board/MaterialLimitWin.jsp";

	public String productlist()
	{
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		List clist=saledeliverManager.getCustomerByfsupplier(fsupplierid);
		this.getRequest().setAttribute("customer", clist);
		return PRODUCTLIST;
	}
	/**
	 * 产品档案列表数据加载
	 * @return
	 */
	public String loadproduct()
	{
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("c.fiscommon", "desc");
		orderby.put("c.fcreatetime","desc");
		String where =" where  c.feffect = 1  AND IFNULL(c.fcharacterid, '') = '' and  f.fsupplierid=?";
		ls.add(fsupplierid);
		if(fsupplierid==null||StringUitl.isNullOrEmpty(getRequest().getParameter("myProductDefQuery.fcustomerid")))
		{
			PageModel<HashMap<String,Object>> emp=new PageModel<HashMap<String,Object>>();
			emp.setList(new ArrayList<HashMap<String,Object>>());
			emp.setPageSize(10);
			return writeAjaxResponse(JSONUtil.result(emp));
		}

			if(!StringUitl.isNullOrEmpty(myProductDefQuery.getFcustomerid())){
				where += " and c.fcustomerid = ? ";
				ls.add(myProductDefQuery.getFcustomerid());
			}	
			if(!StringUitl.isNullOrEmpty(myProductDefQuery.getSearchKey())){
				String queryKey = myProductDefQuery.getSearchKey();
				where += " and (c.fname like ?  ";	
				ls.add("%" +queryKey+"%");
				if(queryKey.matches("^\\d\\.?\\d*((\\*|X|x)?(\\d+\\.?\\d*)?){0,3}$")){
					where  += " or c.fspec like ? ";
					ls.add("%" +queryKey.replaceAll("\\*|X|x","_")+"%");	
				}
				where += ") ";	
			}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		PageModel<HashMap<String, Object>> pageModel = this.productdefManager.findProductlist(where, queryParams, orderby, pageNo, pageSize);
		return writeAjaxResponse(JSONUtil.getJson(pageModel));	
	}
	public String execlproduct()
	{
		String fcustomerid=getRequest().getParameter("fcustomerid");
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		List clist=saledeliverManager.getCustomerByfsupplier(fsupplierid);
		this.getRequest().setAttribute("customer", clist);
		this.getRequest().setAttribute("customerid", fcustomerid);
		return PRODUCTEXECLIN;
	}
	
	public String forbiddenproduct()
	{
		try{
		String fid=" in ('"+id.replaceAll(",", "','")+"') ";
		return writeAjaxResponse(JSONUtil.result(this.productdefManager.execforbiddenproduct(fid)>0?true:false,"","",""));
		}catch(Exception e){
			return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
		}
		
	}
	
	
	/**
	 * 客户产品导入模版下载
	 */
	public String downtempproduct(){
		String path = "D:\\tomcat\\客户产品模版.xls";
		String name = path.substring(path.lastIndexOf("\\")+1);
			InputStream in = null;
			try {
			in = new FileInputStream(path);
			this.getResponse().setContentType("application/x-msdownload");
			this.getResponse().addHeader("Content-Disposition", "attachment; filename=\"" + new String(name.getBytes("UTF-8"),"iso-8859-1")+ "\"");
			OutputStream out = this.getResponse().getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
			} catch (FileNotFoundException e) {
				return writeAjaxResponse("此附件文件不存在，无法下载！");
			}catch(Exception e)
			{
				return writeAjaxResponse("下载失败！"+e.getMessage());
			}
			return null;
	}
	
	public String DeliverTimeConfig(){
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String supplierid = null;
		supplierid= this.productdefManager.getCurrentSupplierid(userid);
		Params param=new Params();
		if(supplierid!=null)
		{
//			String sql = "SELECT fdays,fdefaultdays fdefaultdays FROM t_sys_supplierdelivertime where fsupplierid ='"+supplierid+"'";	
//			List<HashMap<String,Object>> list = productdefManager.QueryBySql(sql, new Params());
			String sql = "SELECT fdays,fdefaultdays fdefaultdays FROM t_sys_supplierdelivertime where fsupplierid =:fsid";	
			param.put("fsid", supplierid);
			List<HashMap<String,Object>> list = productdefManager.QueryBySql(sql, param);
			if(list.size()>0){			
				getRequest().setAttribute("fdays", list.get(0).get("fdays"));
				getRequest().setAttribute("fdefaultdays", list.get(0).get("fdefaultdays"));				
			}		
			getRequest().setAttribute("fsupplier", supplierid);
			return DeliverTimeConfig_JSP;//返回后台订单列表
		}
		else{
			return writeAjaxResponse("当前帐号关联的制造商异常！");
		}
	}
	
	//设置交期
	public String setDeliverTimeConfig(){	
		String fsupplier = getRequest().getParameter("fsupplier");
		String fdays = getRequest().getParameter("fdays");
		int days = Integer.parseInt(fdays);
		String fdefaultdays = getRequest().getParameter("fdefaultdays");
		int defaultdays = Integer.parseInt(fdefaultdays);
//		String sql = "SELECT fdays,fdefaultdays fdefaultdays FROM t_sys_supplierdelivertime where fsupplierid ='"+fsupplier+"'";	
//		List<HashMap<String,Object>> list = productdefManager.QueryBySql(sql, new Params());
		Params param=new Params();
		param.put("fsid", fsupplier);
		String sql = "SELECT fdays,fdefaultdays fdefaultdays FROM t_sys_supplierdelivertime where fsupplierid =:fsid";	
		List<HashMap<String,Object>> list = productdefManager.QueryBySql(sql,param);
		param.getData().clear();
		if(list.size()>0){
//			sql ="update t_sys_supplierdelivertime set fdays="+days+",fdefaultdays="+defaultdays+" where fsupplierid='"+fsupplier+"'  ";
			sql ="update t_sys_supplierdelivertime set fdays=:fdays,fdefaultdays=:fddays where fsupplierid=:fsid  ";
			param.put("fdays", days);
			param.put("fddays", defaultdays);
			param.put("fsid", fsupplier);
		}
		else{
//			sql = " insert t_sys_supplierdelivertime (fid,fsupplierid,fdays,fdefaultdays) values('"+this.productdefManager.CreateUUid()+"'";
//			sql +=",'"+fsupplier+"',"+days+","+defaultdays+") ";
			sql = " insert t_sys_supplierdelivertime (fid,fsupplierid,fdays,fdefaultdays) values(:uid";
			sql +=",:fsupp,:days,:ddays) ";
			param.put("uid", this.productdefManager.CreateUUid());
			param.put("fsupp", fsupplier);
			param.put("days", days);
			param.put("ddays", defaultdays);
		}
		productdefManager.ExecBySql(sql,param);
		return writeAjaxResponse("success");
	}
	
	//产品档案查看详情或修改
	public String productEdit(){
		HashMap<String,Object> orderInfo= productdefManager.getProductInfo(id);
		String customerId =(String) orderInfo.get("fcustomerid");
		MaterialLimit limit = productdefManager.getMaterialLmit(customerId);
		setRequestAttribute("orderInfo", JSONUtil.getJsonString(orderInfo));
		setRequestAttribute("fcustpdtid", orderInfo.get("fcustpdtid"));
		setRequestAttribute("fcustomerid", customerId);
		setRequestAttribute("customername", customerManager.get(customerId).getFname());
		setRequestAttribute("materiallimit", JSONUtil.getJsonString(limit));
		setRequestAttribute("view", getRequest().getParameter("view"));//View=true查看详情
		return PRODUCTEDIT;
	}
	
	public String productCreate(){
		String customerId =(String) getRequest().getParameter("customerid");
		if(StringUtils.isEmpty(customerId)){
			writeAjaxResponse(productdefManager.alertMsg("请先选择客户再新增产品！"));
		}
		MaterialLimit limit = productdefManager.getMaterialLmit(customerId);
		setRequestAttribute("fcustpdtid", productdefManager.CreateUUid());
		setRequestAttribute("fcustomerid", customerId);
		setRequestAttribute("customername", customerManager.get(customerId).getFname());
		setRequestAttribute("materiallimit", JSONUtil.getJsonString(limit));
		return PRODUCTEDIT;
	}


	public String saveProduct(){
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		productdef.setFsupplierid(fsupplierid);
		if(StringUtils.isEmpty(productdef.getFid())){
			productdef.setFcreatorid(userid);
			productdef.setFcreatetime(new Date());
		}
		String error = productdefManager.saveProduct(productdef);
		return writeAjaxResponse(error==null?"success":error);
	}
	
	//创建下料规格最值
	public String CreateCustLimit(){
		setRequestAttribute("fcustomerid", id);
		return LIMITPAGE;
	}
}
