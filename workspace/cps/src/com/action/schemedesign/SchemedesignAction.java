package com.action.schemedesign;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.action.BaseAction;
import com.model.PageModel;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.schemedesign.Productstructure;
import com.model.schemedesign.SchemeDesignEntry;
import com.model.schemedesign.Schemedesign;
import com.model.schemedesign.SchemedesignQuery;
import com.model.user.TSysUser;
import com.opensymphony.xwork2.ModelDriven;
import com.service.designschemes.DesignSchemesManager;
import com.service.firstproductdemand.TordFirstproductdemandManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.saledeliver.SaledeliverManager;
import com.service.schemedesign.SchemedesignManager;
import com.service.user.TSysUserManager;
import com.util.Constant;
import com.util.DateMorpherEx;
import com.util.JSONUtil;
import com.util.Params;
import com.util.ServerContext;
import com.util.StringUitl;

//我的设计

@SuppressWarnings("unchecked")
public class SchemedesignAction extends BaseAction implements ModelDriven<Schemedesign>{
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-9-21 下午4:31:19
	*/
	private static final long serialVersionUID = -7523479884007975579L;
	protected static final String DETAIL_JSP = "/pages/schemeDesign/design_detail.jsp";
	protected static final String PACKDETAIL_JSP = "/pages/schemeDesign/package_detail.jsp";
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	protected static final String DESIGN_ADD_GDSJ = "/pages/schemeDesign/schemeDesign_add_gdsj.jsp";
	protected static final String DESIGN_ADD_XQB = "/pages/schemeDesign/schemeDesign_add_xqb.jsp";
	@Autowired
	private SchemedesignManager schemedesignManager;
	@Autowired
	private TSysUserManager tSysUserManager;
	@Autowired
	private DesignSchemesManager designSchemesManager;
	private SchemedesignQuery schemedesignQuery;
	private Schemedesign schemedesign =new Schemedesign();
	private List<Productstructure> products =new ArrayList<Productstructure>();
	private PageModel<Schemedesign> pageModel;// 分页组件
	public Schemedesign getModel() {
		return schemedesign;
	}

	public PageModel<Schemedesign> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Schemedesign> pageModel) {
		this.pageModel = pageModel;
	}
	
	public SchemedesignQuery getSchemedesignQuery() {
		return schemedesignQuery;
	}

	public void setSchemedesignQuery(SchemedesignQuery schemedesignQuery) {
		this.schemedesignQuery = schemedesignQuery;
	}
	
	
	public List<Productstructure> getProducts() {
		return products;
	}

	public void setProducts(List<Productstructure> products) {
		this.products = products;
	}

	public String loadDetail(){
		String where =" WHERE ( s.faudited = 1 OR ifnull(sc.fid, 1) = 1) AND s.fcustomerid =? AND s.ffirstproductid = ?";
		if(schemedesignQuery !=null){
			if(!("").equals(schemedesignQuery.getSearchKey())){
				where = where + " and (u1.fname like '%"+schemedesignQuery.getSearchKey()+"%' "+
									   "or s.fname like '%"+schemedesignQuery.getSearchKey()+"%' "+
							           "or s.fnumber like '%"+schemedesignQuery.getSearchKey()+"%' "+
							           "or s.fdescription  like '%"+schemedesignQuery.getSearchKey()+"%' "+
							           "or c.fname  like '%"+schemedesignQuery.getSearchKey()+"%' "+
							           "or sp.fname like '%"+schemedesignQuery.getSearchKey()+"%' "+
							           "or u2.fname  like '%"+schemedesignQuery.getSearchKey()+"%' "+
						             ")";
			}
		}
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
			ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
			ls.add(getRequest().getParameter("fid"));
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		pageModel = this.schemedesignManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
	    return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/**
	 * 根据需求ID查询所有方案详情页
	 * @return
	 */
	public String loadDetailWithfp(){
		String queryHistory=this.getRequest().getParameter("queryHistory");
		List<HashMap<String, HashMap<String, Object>>> list = schemedesignManager.getDetailWithfp(id,queryHistory);
		return writeAjaxResponse(JSONUtil.getJson(list));
	}
	
	/**
	 * 需求包详情
	 * 根据需求ID查询所有方案详情页
	 * @return
	 */
	public String loadDetailWithScheme(){
		List<HashMap<String, HashMap<String, Object>>> list = schemedesignManager.getDetailWithScheme(id,this.getRequest().getParameter("queryHistory"));
	    return writeAjaxResponse(JSONUtil.getJson(list));
	}
	
	/*********************************************************我的设计********************************************/
	protected static final String SCHEMEDSIGN_DESIGNER = "/pages/schemeDesign/schemeDesigner.jsp"; 
	protected static final String SCHEMEDSIGN_DEMANDPRODUCT = "/pages/schemeDesign/schemeDesign_product.jsp"; 
	protected static final String SCHEMEDSIGN_EDIT = "/pages/schemeDesign/schemeDesign_edit_gdsj.jsp"; 
	protected static final String SCHEMEDSIGN_PACKAGEEDIT = "/pages/schemeDesign/schemeDesign_edit_xqb.jsp";
	@Autowired
	private TordFirstproductdemandManager tordFirstproductdemandManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private SaledeliverManager saledeliverManager;
	/***
	 * 指定设计师
	 * @return
	 */
	public String saveDesigner()
	{
		try{
		if(id.length()<1)throw new Exception("请选择记录！");
		String fdesigner=this.getRequest().getParameter("fdesigner");
		if(StringUitl.isNullOrEmpty(fdesigner)) throw new Exception("请选择设计师");
		if(!this.schemedesignManager.QueryExistsBySql(String.format("select 1 from t_ord_firstproductdemand where fid  = '%s' and fstate  <>'已设计' and fstate  <>'已完成' and fstate  <>'确认方案' and fstate<>'已发布' and fstate<>'已关闭' ",id)))	throw new Exception("只能指定已分配、已接收的需求！");
		this.schemedesignManager.updateDesigner(id, fdesigner);
		return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
		}catch(Exception e)
		{
			return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
		}
	}
	/**
	 * 根据制造商过滤设计师
	 * @return
	 */
	public String loadDesigner()
	{
		Firstproductdemand demand=tordFirstproductdemandManager.get(id);
		if("已完成".equals(demand.getFstate())) return writeAjaxResponse("已完成的方案不能再指定！");
		if(StringUitl.isNullOrEmpty(demand.getFdesignproviderid())) 	return writeAjaxResponse("未指定设计商");
		
		List<HashMap<String,Object>> desinerlist=this.schemedesignManager.getDesinerList(demand.getFdesignproviderid());
		this.getRequest().setAttribute("designer", desinerlist);
		this.getRequest().setAttribute("firstdemandid", demand.getFid());
		if(!StringUitl.isNullOrEmpty(demand.getFreceiver())) this.getRequest().setAttribute("selectedDesigner", demand.getFreceiver());
		return SCHEMEDSIGN_DESIGNER;
	}
	
	/**需求列表**/
	public String loaddemand()
	{
		pageSize = 30;
		PageModel<HashMap<String, Object>> pageModel=null;
		TSysUser user = (TSysUser)getRequest().getSession().getAttribute("cps_user");
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("f.fauditortime", "desc");
		String where =" where  f.falloted=1";
		//2016-3-22 lxx
		String t_name=null;
		if(user.getFisfilter()==0)
		{
			String fsupplierid=saledeliverManager.getFsupplieridByUser(user.getFid());
//			fsupplierid="39gW7X9mRcWoSwsNJhU12TfGffw=";  //测试用
			if(fsupplierid==null)
			{
				pageModel=new PageModel<HashMap<String,Object>>();
				pageModel.setList(new ArrayList<HashMap<String,Object>>());
				pageModel.setPageSize(pageSize);
				return writeAjaxResponse(JSONUtil.result(pageModel));
			}
//			where +=String.format(" and (f.fsupplierid in ('%s') or f.fdesignproviderid in('%s'))",fsupplierid,fsupplierid);
			//2016-3-19 lxx
			where +=" and (f.fsupplierid in (?) or f.fdesignproviderid in(?))";
			ls.add(fsupplierid);
			ls.add(fsupplierid);
			//2016-4-2  子账号只查看 自己接收的需求（东经除外）
			if(!fsupplierid.equals("39gW7X9mRcWoSwsNJhU12TfGffw=") && user.getFisreadonly()==1){
				where += QueryFilterByUserofuser("f.freceiver","and");				
			}
		}
		if(schemedesignQuery!=null){
		if(!StringUitl.isNullOrEmpty(schemedesignQuery.getFcustomerid())){
				where += " and f.fcustomerid = ? ";
				ls.add(schemedesignQuery.getFcustomerid());
		}
		if(!StringUitl.isNullOrEmpty(schemedesignQuery.getFstate())){
			//2016-3-22 lxx
			if("三月前数据".equals(schemedesignQuery.getFstate()))
			{
				t_name="t_ord_firstproductdemand_h";
			}else if(!"全部".equals(schemedesignQuery.getFstate())){
				where += " and f.fstate = ? ";
				ls.add(schemedesignQuery.getFstate());
			}
		}
		if(schemedesignQuery.getFtype()==null||schemedesignQuery.getFtype()>=0){
			where += " and ifnull(f.fisdemandpackage,0) = ? ";
			ls.add(schemedesignQuery.getFtype());
		}
		if(!StringUitl.isNullOrEmpty(schemedesignQuery.getSearchKey())){
				String queryKey = schemedesignQuery.getSearchKey();
				where += " and (f.fnumber like ?  or f.fname like ? or ifnull(u2.fname,'设计咨询')  like ?)";	
				ls.add("%" +queryKey+"%");
				ls.add("%" +queryKey+"%");
				ls.add("%" +queryKey+"%");
		}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		pageModel = this.schemedesignManager.findByFirstdemand(where, queryParams, orderby, pageNo, pageSize,t_name);
		return writeAjaxResponse(JSONUtil.getJson(pageModel));	
	}
	
	/**方案列表**/
	public String loadSchemedesign()
	{
		String queryHistory=this.getRequest().getParameter("queryHistory");
		boolean t_name=false;
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
			t_name=true;
		}
		PageModel<HashMap<String, Object>> pageModel=null;
		TSysUser user = (TSysUser)getRequest().getSession().getAttribute("cps_user");
		String ffirstproductid=getRequest().getParameter("ffirstproductid");
		String searchKey=getRequest().getParameter("schemesearchKey");
		Object[] queryParams=null;
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("s.fcreatetime", "asc");
		List<Object> ls = new ArrayList<Object>();
		String where =" where 1=1 ";
		if(user.getFisfilter()==0){
			String	fsupplierid=saledeliverManager.getFsupplieridByUser(user.getFid());
			if(fsupplierid==null)//没有关联制造商不可看
			{
				pageModel=new PageModel<HashMap<String,Object>>();
				pageModel.setList(new ArrayList<HashMap<String,Object>>());
				pageModel.setPageSize(pageSize);
				return writeAjaxResponse(JSONUtil.result(pageModel));
			}
			where +=String.format(" and (s.fsupplierid in ('%s') or f.fdesignproviderid in('%s'))",fsupplierid,fsupplierid);
			//2016-4-2  子账号只查看 自己接收的需求（东经除外）
			if(!fsupplierid.equals("39gW7X9mRcWoSwsNJhU12TfGffw=") && user.getFisreadonly()==1){
				where += QueryFilterByUserofuser("f.freceiver","and");				
			}

		}
		if(!StringUitl.isNullOrEmpty(ffirstproductid))
		{
			where += " and  s.ffirstproductid=?";
			ls.add(ffirstproductid);
		}
		
		if(!StringUitl.isNullOrEmpty(searchKey)){
				where += " and (s.fname like  ? or s.fnumber like ?) ";
				ls.add("%" +searchKey+"%");
				ls.add("%" +searchKey+"%");
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		pageModel = this.schemedesignManager.findBySchemedesign(where, queryParams, orderby, pageNo, pageSize,t_name);
		return writeAjaxResponse(JSONUtil.getJson(pageModel));	
	}
	public String list(){
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		List list=this.saledeliverManager.getCustomerByfsupplier(fsupplierid);
		getRequest().setAttribute("customer", list);
		TSysUser user = tSysUserManager.get(userid);
		getRequest().setAttribute("userType", user.getFtype());
		return "/pages/schemeDesign/schemeDesign_list.jsp";
	}
	
	//高端设计详情
	public  String detail(){
		System.out.println(id);
		String queryHistory=this.getRequest().getParameter("queryHistory");
		String t_name=null;
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
			//历史数据
			t_name="t_ord_firstproductdemand_h";
			getRequest().setAttribute("queryHistory",queryHistory);
		}
		String where =" WHERE f.fid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(id);
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		List<Firstproductdemand> mand = this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby,t_name);
		String schemeId=mand.get(0).getFresourceschemeid();
		if(schemeId!=null&&schemeId!="")
		{
			//通过需求表的来源id，即作品id找到发布的作品名称
			getRequest().setAttribute("schemename", designSchemesManager.get(schemeId).getFname());
		}
		getRequest().setAttribute("mand", mand.get(0));
		return DETAIL_JSP;
	}
	//需求包详情
	public  String packageDetail(){
		System.out.println(id);
		String queryHistory=this.getRequest().getParameter("queryHistory");
		String t_name=null;
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
			//历史数据
			t_name="t_ord_firstproductdemand_h";
			getRequest().setAttribute("queryHistory",queryHistory);
		}
		String where =" WHERE f.fid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(id);
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		List<Firstproductdemand> mand = this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby,t_name);
		getRequest().setAttribute("mand", mand.get(0));
		return PACKDETAIL_JSP;
	}
	
	
	//需求退回
		public String  unReceiveProductdemand()
		{
			try{
			if(StringUitl.isNullOrEmpty(id))  throw new Exception("请选择记录操作");
			String fids=id.replace(",","','");
			if(this.schemedesignManager.QueryExistsBySql(String.format("select 1 from t_ord_firstproductdemand where fid  in ('%s') and fstate ='关闭'",fids)))	throw new Exception("需求已关闭,不能退回！");
			if(this.schemedesignManager.QueryExistsBySql(String.format("select 1 from t_ord_schemedesign where ifnull(fconfirmed,0)=1 and ffirstproductid in ('%s')",fids))) throw new Exception("存在已确认的方案设计，不能退回需求！");
			this.schemedesignManager.execUnReceiveProductdemand(fids);
			return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
			}catch(Exception e)
			{
					return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
			}
		}
	//接收需求
	public String receiveProductdemand()
	{
		try{
		if(StringUitl.isNullOrEmpty(id))  throw new Exception("请选择记录操作");
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user = tSysUserManager.get(userId);
			if(user != null && user.getFtype() == 3){
				String fids=id.replace(",","','");
				if(!this.schemedesignManager.QueryExistsBySql(String.format("select 1 from t_ord_firstproductdemand where fid  in ('%s') and fstate  <>'已设计' and fstate  <>'已完成' and fstate  <>'确认方案' and fstate<>'已发布' and fstate<>'已关闭' ",fids)))	throw new Exception("只能接收已分配、已接收的需求！");
				
				this.schemedesignManager.execReceiveProductdemand(fids, userId);
				return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
			} else {
				throw new Exception("只有设计师才能接收需求！");
			}
		}catch(Exception e)
		{
				return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
		}
	}
		//删除方案
		public String delSchemeDesign()
		{
			try{
				if(StringUitl.isNullOrEmpty(id))  throw new Exception("请选择记录操作");
				String fids=id.replace(",","','");
				if(schemedesignManager.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid in ('%s') ")) throw new Exception("已确认的方案设计不能删除！");
				this.schemedesignManager.delSchemeDesigns(id.split(","));
				return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
				}catch(Exception e)
				{
					return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
				}
		}
		
		public String demandProduct()
		{
			try{
			if(StringUitl.isNullOrEmpty(id)) throw new Exception("请选择记录操作");
			//方案未确认 不可以新增
			if(!schemedesignManager.QueryExistsBySql(String.format("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and ffirstproductid= '%s' ",id))) throw new Exception("确认的方案设计才能编辑！");
			 Firstproductdemand demand=(Firstproductdemand)tordFirstproductdemandManager.get(id);
			 if(demand.getFisdemandpackage()!=null&&demand.getFisdemandpackage()==1) throw new Exception("需求包的产品，请在新增方案时添加！");
			 if("已完成".equals(demand.getFstate()))throw new Exception("已完成的需求，不能新增产品！");
			}catch(Exception e)
			{
				return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
			}
			return writeAjaxResponse(JSONUtil.result(true,"","",""));
		}
		
		public String demandProductAdd()
		{
			try{
			Params p=new Params();
			p.put("fid", id);
			List<HashMap<String,Object>> demand=this.schemedesignManager.QueryBySql("select d.fid,d.fcustomerid,c.fname customer,s.fid fsupplierid,s.fname supplier from t_ord_firstproductdemand d left join t_bd_customer c on c.fid=d.fcustomerid left join t_sys_supplier s  ON s.fid=IF(IFNULL(d.fsupplierid,'')='','39gW7X9mRcWoSwsNJhU12TfGffw=',d.fsupplierid)  where d.fid=:fid",p);
			if(demand.size()==0) throw new Exception("该需求记录不存在");
			List<HashMap<String, Object>> products=schemedesignManager.getFirstdemandproduct(id);
			this.setRequestAttribute("products", products);
			this.setRequestAttribute("demand", demand.get(0));
			}catch(Exception e)
			{
				return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
			}
			return SCHEMEDSIGN_DEMANDPRODUCT;
		}
		
		public String getProductSubId()
		{
			return writeAjaxResponse(JSONUtil.getJson(this.schemedesignManager.CreateUUid()));
		}

		public String SaveFirstdemandProduct()
		{
			try{
			Firstproductdemand demand=(Firstproductdemand)tordFirstproductdemandManager.get(id);
			if(demand==null) throw new Exception("该需求记录不存在");
			if(!schemedesignManager.QueryExistsBySql(String.format("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and ffirstproductid= '%s' ",id))) throw new Exception("方案设计须为确认");
			if("已完成".equals(demand.getFstate()))throw new Exception("需求已完成！");
			if("已关闭".equals(demand.getFstate()))throw new Exception("需求已关闭！");
			this.schemedesignManager.saveProductStructs(demand, products);
			}catch(Exception e)
			{
				return writeAjaxResponse(JSONUtil.result(false, "保存失败,"+e.getMessage(), "", ""));
			}
			return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
		}
		
		public String delfileByfparent()
		{
			try{
				String fparentid=this.getRequest().getParameter("fparentid");
				Firstproductdemand firstinfo=(Firstproductdemand)tordFirstproductdemandManager.get(id);
				if(!StringUitl.isNullOrEmpty(firstinfo.getFstate())&&("已关闭".equals(firstinfo.getFstate())||"已完成".equals(firstinfo.getFstate())))
				{
					 throw new Exception("需求已关闭或已完成，不能操作");
				}
				productdemandfileManager.delFileByParentId(fparentid);
			}catch(Exception e)
			{
				return writeAjaxResponse(JSONUtil.result(false, "保存失败,"+e.getMessage(), "", ""));
			}
			return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
		}
		
		//2016-01-11 打开新增方案界面
				public String designCreate(){
					String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
					String fdid =(String) getRequest().getParameter("fdid");
					String fid = schemedesignManager.CreateUUid();
					String fnumber1 = ServerContext.getNumberHelper().getNumber(schemedesignManager,"t_ord_SchemeDesign", "SD", 4, false);
					String fnumber2 = ServerContext.getNumberHelper().getNumber(schemedesignManager,"t_ord_SchemeDesign", "SD", 4, false);
					Firstproductdemand fdinfo = tordFirstproductdemandManager.get(fdid);
					String fcustomerid = fdinfo.getFcustomerid();
					String fsupplierid = fdinfo.getFsupplierid();
					this.getRequest().setAttribute("fid", fid);
					//一次性产品和循环产品 默认需要两个
					this.getRequest().setAttribute("scfid",schemedesignManager.CreateUUid());
					//一次性产品方案编码和循环方案编码分别赋值fnumber
					this.getRequest().setAttribute("fnumber1",fnumber1);
					this.getRequest().setAttribute("fnumber2",fnumber2);
					this.getRequest().setAttribute("fname",fdinfo.getFname());
					this.getRequest().setAttribute("fcustomerid", fcustomerid);
					this.getRequest().setAttribute("fsupplierid",fsupplierid);
					this.getRequest().setAttribute("ffirstproductid",fdid);
					this.getRequest().setAttribute("fcreatorid",userid);
					if(fdinfo.getFisdemandpackage()==0){
						return DESIGN_ADD_GDSJ;
					}
					//需求包方案默认需要 （父）产品 id提供上传使用
					this.getRequest().setAttribute("ppdtid",schemedesignManager.CreateUUid());
					this.getRequest().setAttribute("pdtid",schemedesignManager.CreateUUid());
					return DESIGN_ADD_XQB;
				}
				
				public String getDesignID(){
					String result;
					String fid = schemedesignManager.CreateUUid();
					//提供循环订单的产品使用
					String ppdtid = schemedesignManager.CreateUUid();
					String pdtid = schemedesignManager.CreateUUid();
					String fnumber = ServerContext.getNumberHelper().getNumber(schemedesignManager,"t_ord_SchemeDesign", "SD", 4, false);
					result = JSONUtil.result(true,"", "","{\"fid\":\""+fid+ "\",\"fnumber\":\""+fnumber+ "\",\"ppdtid\":\""+ppdtid+ "\",\"pdtid\":\""+pdtid+ "\"}");		
					return writeAjaxResponse(result);
				}
				
				//高端设计方案保存
				public String saveXqbjnfo(){						
			        //data : {eachMain:eachMain,eachInfo:eachInfo},
					String resStr = "";
					try{
					JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(new String[] {"yyyy-MM-dd HH:mm:ss.0", "yyyy-MM-dd HH:mm:ss" }, (Date) null));
					String scInfoArr = getRequest().getParameter("scInfoArr");
					 JSONArray eachjo = JSONArray.fromObject(scInfoArr);
					 List<HashMap> schemelist=new ArrayList<HashMap>();
					 
					 DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 for(int i=0;i<eachjo.size();i++){
						 List<SchemeDesignEntry> scentryList = null;
						 List<Productstructure> pdtlist = null;
						 JSONObject mainjo = eachjo.getJSONObject(i);		
						 JSONObject scInfoJo =  (JSONObject) mainjo.get("scInfo");
						 Date foverdate = null;
						 if(scInfoJo.containsKey("foverdate")){
						 String str = scInfoJo.getString("foverdate");
						 if(str.indexOf(".") >-1){
							 str = str.split("\\.")[0];
						 }
						 try{
							 foverdate = f.parse(str);
						 }catch( Exception e){
						 }
						 }
						 
						 Schemedesign scinfo = (Schemedesign) JSONObject.toBean(scInfoJo, Schemedesign.class);
						 if(foverdate != null){
							 scinfo.setFoverdate(foverdate);
						 }
						 if(mainjo.containsKey("scentrys")){
							 JSONArray scentrysJA = JSONArray.fromObject(mainjo.get("scentrys"));
							 scentryList =(ArrayList<SchemeDesignEntry>)JSONArray.toList(scentrysJA, SchemeDesignEntry.class);
						 }
						 if(mainjo.containsKey("pdtentys")){
							 JSONArray scpdinfosJA = JSONArray.fromObject(mainjo.get("pdtentys")); 						 
							 pdtlist =(ArrayList<Productstructure>)JSONArray.toList(scpdinfosJA, Productstructure.class); 
						 }
						 HashMap params = new HashMap();
						 params.put("scinfo", scinfo);
					     //产品暂时为空
						 params.put("pdtlist", pdtlist);
						 params.put("scentryList", scentryList);
						 schemelist.add(params);
					 }
					 resStr= schemedesignManager.saveXqbjnfo(schemelist);
					}catch(Exception e){
						 return writeAjaxResponse(e.getMessage());
					}
					 return writeAjaxResponse(resStr);
				}
				
				//高端设计方案保存
				public String saveGdSjcjnfo(){		
			        //data : {eachMain:eachMain,eachInfo:eachInfo},
					String resStr = "success";
					DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
					String eachMain = getRequest().getParameter("eachMain");
					JSONObject mainjo = JSONObject.fromObject(eachMain);			
					String eachInfo = getRequest().getParameter("eachInfo");
				    JSONArray eachjo = JSONArray.fromObject(eachInfo);
				    List<Schemedesign> scList=(ArrayList<Schemedesign>)JSONArray.toList(eachjo, Schemedesign.class);				    
					for(int i=0;i<scList.size();i++){
						Schemedesign  scinfo = scList.get(i);
						scinfo.setFcustomerid(mainjo.getString("fcustomerid"));
						scinfo.setFsupplierid(mainjo.getString("fsupplierid"));
						scinfo.setFfirstproductid(mainjo.getString("ffirstproductid"));
						scinfo.setFcreatorid(mainjo.getString("fcreatorid"));
						try {
							scinfo.setFcreatetime(mainjo.containsKey("fcreatetime")?f.parse(mainjo.getString("fcreatetime")):new Date());
						} catch (ParseException e) {
							scinfo.setFcreatetime(new Date());
						}
					}
					try{
						if(scList.size()>0&&tordFirstproductdemandManager.QueryExistsBySql(String.format("select 1 from t_ord_firstproductdemand where ifnull(freceived,0)=0 and fid ='%s'",scList.get(0).getFfirstproductid()))){
							 throw new Exception("未接收不能新增方案");
						}
						schemedesignManager.saveGdSjcjnfo(scList);
					}catch(Exception e){
						return writeAjaxResponse(e.getMessage());
					}
					return writeAjaxResponse(resStr);
				}
				
				//2016-01-11 打开新增方案界面
				public String saveCreate(){
					String resStr = "";
					String jsDate = getRequest().getParameter("data");		
					JSONObject jo = JSONObject.fromObject(jsDate);
					//构建方案
					JSONObject joschemedesign = (JSONObject) jo.get("schemedesign");
					Schemedesign scinfo = (Schemedesign)JSONObject.toBean(joschemedesign, Schemedesign.class);
					//构建产品list
					JSONArray pdtjoArr = jo.getJSONArray("pdtlist");
					List<Productstructure> pdtlist = new ArrayList<Productstructure>();
					for(int i=0;i<pdtjoArr.size();i++){
						Productstructure  ptinfo = new Productstructure();
						ptinfo = (Productstructure) JSONObject.toBean((JSONObject)pdtjoArr.get(i), Productstructure.class);		
						pdtlist.add(ptinfo);
					}
					//构建方案特性
					List<SchemeDesignEntry> scentryList = new ArrayList<SchemeDesignEntry>();
					JSONArray scentryjoArr = jo.getJSONArray("scentrylist");
					for(int i=0;i<scentryjoArr.size();i++){
						SchemeDesignEntry  scentryInfo = new SchemeDesignEntry();
						scentryInfo = (SchemeDesignEntry) JSONObject.toBean((JSONObject)scentryjoArr.get(i), SchemeDesignEntry.class);		
						scentryList.add(scentryInfo);
					}
					if(!schemedesignManager.QueryExistsBySql("select fid from t_ord_SchemeDesign where fid = '"+scinfo.getFid()+"'")){
						scinfo.setFcreatorid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
						scinfo.setFcreatetime(new Date());
					}		
					HashMap params = new HashMap();
					params.put("scinfo", scinfo);
					params.put("pdtlist", pdtlist);
					params.put("scentryList", scentryList);
					resStr = schemedesignManager.schemeSave(params);
					return writeAjaxResponse(resStr);
				}
				
				//2016-01-12获取方案信息
				public String getScinfo(){
					String fid = getRequest().getParameter("fid");		
					HashMap map = schemedesignManager.getScinfo(fid);
					return writeAjaxResponse(JSONUtil.getJson(map));
				}

				public String affirmSchemeDesign(){		
					String fids = getRequest().getParameter("fids");
					try{
						schemedesignManager.affirmSchemeDesign(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString(),fids);
					}
					catch(Exception e){
						return writeAjaxResponse(e.getMessage());
					}
					return writeAjaxResponse("success");
				}
				
				public String unaffirmSchemeDesign(){		
					String fids = getRequest().getParameter("fids");
					try{
						tordFirstproductdemandManager.UnAffirmSchemedesign(fids);
					}
					catch(Exception e){
						return writeAjaxResponse(e.getMessage());
					}
					return writeAjaxResponse("success");
				}
		
				public String schemeDetail()
				{
					String ftype=this.getRequest().getParameter("type");//修改、查看状态
					String queryHistory=this.getRequest().getParameter("queryHistory");
					try
					{
						String tablename="t_ord_schemedesign";
						String tablename1="t_ord_firstproductdemand";
						if(queryHistory!=null&&"history".equals(queryHistory))
						{
							tablename+="_h";
							tablename1+="_h";
						}
//						String sql="SELECT fid,fcreatorid,fcreatetime,fname,fnumber,fdescription,ffirstproductid,fcustomerid,fsupplierid,fconfirmed,fconfirmer,fconfirmtime,foverdate,fboxlength,fboxwidth,fboxheight,fmaterial,ftilemodel,famount,fordered,forderperson,fordertime,ftemplateproduct,ftemplatenumber,fgroupid,faudited,fauditorid,faudittime FROM "+tablename+" WHERE fid  =:fid";
						String sql="SELECT * FROM "+tablename+" WHERE fid  =:fid";
						Params param=new Params();
						param.put("fid", id);
						List<HashMap<String, Object>> list=this.schemedesignManager.QueryBySql(sql,param);
						Schemedesign design=new Schemedesign();
						JSONUtil.mapToObject(list.get(0), design);
						List<HashMap<String,Object>> file=schemedesignManager.getfileInfo(id);
//						String sql1="SELECT fid,fnumber,fdescription,fboxlength,fboxwidth,fboxheight,fboardlength,fboardwidth,fcreatid,fcreatetime,fupdateuserid,fupdatetime,fauditorid,fauditortime,isfauditor,fcustomerid,fsupplierid,falloted,fallotor,fallottime,freceived,freceiver,freceivetime,fname,ftype,fcostneed,fiszhiyang,famount,foverdate,farrivetime,fboxpileid,fmaterial,fcorrugated,fprintcolor,fprintbarcode,funitestyle,fprintstyle,fsurfacetreatment,fpackstyle,fpackdescription,fisclean,fispackage,fpackagedescription,fislettering,fletteringescription,flinkphone,flinkman,fisaccessory,fstate,fclosetime,fcloseuserid,fcloseprevstate,fpreproductdemandid,fgroupid,fpurordernumber,fisdemandpackage,fdesignproviderid FROM "+tablename1+" WHERE fid  =:fid";
						String sql1="SELECT * FROM "+tablename1+" WHERE fid  =:fid";
						param.getData().clear();
						param.put("fid", design.getFfirstproductid());
						List<HashMap<String, Object>> listsdm=this.tordFirstproductdemandManager.QueryBySql(sql1,param);
						Firstproductdemand demand=new Firstproductdemand();
						JSONUtil.mapToObject(listsdm.get(0), demand);
						
//						Firstproductdemand demand=tordFirstproductdemandManager.get(design.getFfirstproductid());
						this.setRequestAttribute("type", ftype);
						this.setRequestAttribute("desgin", design);
						this.setRequestAttribute("file", file);
						if(demand.getFisdemandpackage()!=null&&demand.getFisdemandpackage()==1)
						{
							List<HashMap<String, Object>> entrys=schemedesignManager.getSchemeEntrys(id);
							if (entrys.size()>0) this.setRequestAttribute("entrys", entrys);
							else{
								List<HashMap<String, Object>> products=schemedesignManager.getFirstdemandproduct(id);
								this.setRequestAttribute("products", products);
							}
							return SCHEMEDSIGN_PACKAGEEDIT;
						}
					}
					catch(Exception e)
					{
						System.out.print(e.toString());
					}
					return SCHEMEDSIGN_EDIT;
				}
				
				public String execlSchemeDesignlist(){
					
					PageModel<HashMap<String, Object>> pageModel=null;
					TSysUser user = (TSysUser)getRequest().getSession().getAttribute("cps_user");
					Object[] queryParams=null;
					List<Object> ls = new ArrayList<Object>();
					Map<String, String> orderby = new LinkedHashMap<String,String>();
					orderby.put("f.fauditortime", "desc");
					String where =" where  f.freceived=1";
					
					if(user.getFisfilter()==0)
					{
						String fsupplierid=saledeliverManager.getFsupplieridByUser(user.getFid());
						if(fsupplierid==null)
						{
							pageModel=new PageModel<HashMap<String,Object>>();
							pageModel.setList(new ArrayList<HashMap<String,Object>>());
							pageModel.setPageSize(pageSize);
							return writeAjaxResponse(JSONUtil.result(pageModel));
						}
						where +=String.format(" and (f.fsupplierid in ('%s') or f.fdesignproviderid in('%s'))",fsupplierid,fsupplierid);

					}
					if(schemedesignQuery!=null){
					if(!StringUitl.isNullOrEmpty(schemedesignQuery.getFcustomerid())){
							where += " and f.fcustomerid = ? ";
							ls.add(schemedesignQuery.getFcustomerid());
					}
					if(!StringUitl.isNullOrEmpty(schemedesignQuery.getFstate())){
						where += " and f.fstate = ? ";
						ls.add(schemedesignQuery.getFstate());
					}
					if(schemedesignQuery.getFtype()==null||schemedesignQuery.getFtype()>=0){
						where += " and ifnull(f.fisdemandpackage,0) = ? ";
						ls.add(schemedesignQuery.getFtype());
					}
					if(!StringUitl.isNullOrEmpty(schemedesignQuery.getSearchKey())){
							String queryKey = schemedesignQuery.getSearchKey();
							where += " and (f.fnumber like ?  or f.fname like ? or ifnull(u2.fname,'设计咨询')  like ?)";	
							ls.add("%" +queryKey+"%");
							ls.add("%" +queryKey+"%");
							ls.add("%" +queryKey+"%");
					}
					}
					String[] fids=getRequest().getParameterValues("fid");
					if(fids !=null ){
						String fid ="";
						for(int i=0 ;i<fids.length;i++){
							if(i ==0){
								fid = "'"+fids[i]+"'";
							}else{
								fid = fid + ",'"+fids[i]+"'"; 
							}
						}
						where =where + " and f.fid in ("+fid+")";
					}
					queryParams = (Object[])ls.toArray(new Object[ls.size()]);
					pageModel = this.schemedesignManager.findByFirstdemand(where, queryParams, orderby, pageNo, -1);
					
				
						
					WritableWorkbook book = null;
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

					String fileName = format.format(new Date());
					try {
						getResponse().setContentType("multipart/form-data");
						getResponse().setHeader("Content-Disposition",
								"attachment;fileName=" + fileName+ ".xls");
						book = Workbook.createWorkbook(getResponse().getOutputStream());
						WritableSheet wsheet = book.createSheet("我的设计", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
						SheetSettings ss = wsheet.getSettings();
			            ss.setVerticalFreeze(2);  // 设置行冻结前2行
			            WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
			            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
			            WritableCellFormat wcf = new WritableCellFormat(font1);	  //设置样式，字体
				            wcf.setAlignment(Alignment.CENTRE);                   //平行居中
				            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   //垂直居中
			            WritableCellFormat wcf2 = new WritableCellFormat(font2);  //设置样式，字体
				           // wcf2.setBackground(Colour.LIGHT_ORANGE);
				            wcf2.setAlignment(Alignment.CENTRE);                  //平行居中
				            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
				            wcf2.setWrap(true);  
			            wsheet.mergeCells( 0 , 0 , 10 , 0 ); // 合并单元格  
			            Label titleLabel = new Label( 0 , 0 , " 我的设计",wcf);
			            wsheet.addCell(titleLabel);
			            wsheet.setRowView(0, 1000); // 设置第一行的高度
			            int[] headerArrHight = {10,20,20,20,30,20,20,20,20,20};
			            String headerArr[] = {"序号","需求编号","客户名称","需求名称","需求描述","发布时间","客户电话","设计师","类型","需求状态"};            
			            for (int i = 0; i < headerArr.length; i++) {
			            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
			            	wsheet.setColumnView(i, headerArrHight[i]);
			            }
			    		List<HashMap<String,Object>> demandlist =pageModel.getList();
			    		int count = 2;
			    		for(int i=0;i<demandlist.size();i++){
			    			wsheet.addCell(new Label( 0 , count ,String.valueOf(i+1),wcf2));
			    			wsheet.addCell(new Label( 1 , count ,(String)demandlist.get(i).get("fnumber"),wcf2));
			    			wsheet.addCell(new Label(2, count,(String)demandlist.get(i).get("cname"),wcf2));
			    			wsheet.addCell(new Label(3, count,(String)demandlist.get(i).get("fname"),wcf2));
			    			wsheet.addCell(new Label(4, count,(String)demandlist.get(i).get("fdescription"),wcf2));
			    			wsheet.addCell(new Label(5, count,(String)demandlist.get(i).get("fauditortime"),wcf2));
			    			wsheet.addCell(new Label(6, count,(String)demandlist.get(i).get("flinkphone"),wcf2));
			    			wsheet.addCell(new Label(7, count,StringUitl.isNullOrEmpty(demandlist.get(i).get("freceiver"))?"设计咨询":(String)demandlist.get(i).get("freceiver"),wcf2));
			    			wsheet.addCell(new Label(8, count,"1".equals(demandlist.get(i).get("fisdemandpackage").toString())?"需求包":"高端设计",wcf2));
			    			wsheet.addCell(new Label(9, count,(String)demandlist.get(i).get("fstate"),wcf2));
			    			count++;
			    		}
			    		book.write();
					} catch (IOException | WriteException e) {
						return writeAjaxResponse("导出失败!");
					} finally {
						try {
							book.close();
						} catch (Exception e) {
							return writeAjaxResponse("导出失败!"+e.getMessage());
						}
					}
					return null;
				}
				
				public String auditSchemedeginPackge()
				{
					try{
					String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
					String resStr = "";
					 DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 Date foverdate = null;
					String scInfoArr = getRequest().getParameter("scInfoArr");
					 JSONArray eachjo = JSONArray.fromObject(scInfoArr);
					 if(eachjo.size()!=1)  throw new Exception("请选择一条记录审核");
						 List<SchemeDesignEntry> scentryList = null;
						 JSONObject mainjo = eachjo.getJSONObject(0);		
						 JSONObject scInfoJo =  (JSONObject) mainjo.get("scInfo");
						 Schemedesign scinfo = (Schemedesign) JSONObject.toBean(scInfoJo, Schemedesign.class);
				
						 if(scInfoJo.containsKey("foverdate")){
						 String str = scInfoJo.getString("foverdate");
						 if(str.indexOf(".") >-1){
							 str = str.split("\\.")[0];
						 }
						 try{
							 foverdate = f.parse(str);
							 if(foverdate!=null) scinfo.setFoverdate(foverdate);
						 }catch( Exception e){
						 }
						 }
						 
						 if(mainjo.containsKey("scentrys")){//特性分录
							 JSONArray scentrysJA = JSONArray.fromObject(mainjo.get("scentrys"));
							 scentryList =(ArrayList<SchemeDesignEntry>)JSONArray.toList(scentrysJA, SchemeDesignEntry.class);
						 }else
						 {
							 throw new Exception("无需审核");
						 }
						 if(mainjo.containsKey("pdtentys")){
							 throw new Exception("循环产品无需审核");
						 }
						 HashMap params = new HashMap();
						 params.put("scinfo", scinfo);
						 params.put("scentryList", scentryList);
						 params.put("auditor", userid);
						 schemedesignManager.auditSchemedeginPackge(params);
					}catch(Exception e)
					{
						return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
					}
					 return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
				}
}