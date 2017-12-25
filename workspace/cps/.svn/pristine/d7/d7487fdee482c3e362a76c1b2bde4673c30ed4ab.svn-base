package com.action.firstproductdemand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.action.BaseAction;
import com.model.PageModel;
import com.model.designschemes.Designschemes;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.firstproductdemand.FirstproductdemandQuery;
import com.model.productdemandfile.Productdemandfile;
import com.model.supplier.Supplier;
import com.model.user.TSysUser;
import com.opensymphony.xwork2.ModelDriven;
import com.service.custproduct.TBdCustproductManager;
import com.service.designschemes.DesignSchemesManager;
import com.service.firstproductdemand.TordFirstproductdemandManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.supplier.SupplierManager;
import com.service.user.TSysUserManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.StringUitl;

/*
 * CPS-VMI-wangc
 */

public class FirstproductdemandAction extends BaseAction implements ModelDriven<Firstproductdemand>{
	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-9-21 下午4:31:19
	*/
	private static final long serialVersionUID = -7523479884007975579L;

	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	protected static final String LIST_JSP= "/pages/firstproductdemand/list.jsp";
	protected static final String NEW_LIST_JSP ="/pages/onLineDesgin/list.jsp";
	protected static final String NEW_EDIT_JSP ="/pages/onLineDesgin/edit.jsp";
	protected static final String LINE_DESGIN_JSP = "/pages/onLineDesgin/line_desgin.jsp";
	protected static final String DETAIL_JSP = "/pages/onLineDesgin/design_detail.jsp";
	protected static final String PACKDETAIL_JSP = "/pages/onLineDesgin/package_detail.jsp";
	
	protected static final String CREATE_JSP = "/pages/firstproductdemand/create.jsp";
	protected static final String EDIT_JSP = "/pages/firstproductdemand/edit.jsp";
	protected static final String SHOW_JSP = "/pages/firstproductdemand/show.jsp";
	protected static final String PRINT_JSP = "/pages/firstproductdemand/print.jsp";
	protected static final String MAND_EDIT_JSP = "/pages/firstproductdemand/mand_edit.jsp";
	 
	@Autowired
	private DesignSchemesManager designSchemesManager;
	@Autowired
	private TordFirstproductdemandManager tordFirstproductdemandManager;
	@Autowired
	private TSysUserManager userManager;
	@Autowired
	private TBdCustproductManager custproductManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private SupplierManager supplierManager;
	private FirstproductdemandQuery  firstproductdemandQuery;
	private Firstproductdemand firstproductdemand =new Firstproductdemand();
	private PageModel<Firstproductdemand> pageModel;// 分页组件
	public Firstproductdemand getModel() {
		return firstproductdemand;
	}
	public FirstproductdemandQuery getFirstproductdemandQuery() {
		return firstproductdemandQuery;
	}
	public void setFirstproductdemandQuery(
			FirstproductdemandQuery firstproductdemandQuery) {
		this.firstproductdemandQuery = firstproductdemandQuery;
	}
	public PageModel<Firstproductdemand> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Firstproductdemand> pageModel) {
		this.pageModel = pageModel;
	}

	/** 执行搜索 */
	public String list() {
		return LIST_JSP;
	}
	
	/***在线设计列表*/
	public String new_list(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		Calendar  cale = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = sf.format(cale.getTime());
		System.out.println(firstday);
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = sf.format(cale.getTime());
		getRequest().getSession().setAttribute("foverdate", sf.format(c.getTime())+" 17:00:00");//要货默认交期
		getRequest().getSession().setAttribute("firstday", firstday+" 17:00:00");//当月第一天
		getRequest().getSession().setAttribute("lastday", lastday+" 17:00:00");//当月最后一天
		getRequest().getSession().setAttribute("mandPackageId", this.tordFirstproductdemandManager.CreateUUid());
		return NEW_LIST_JSP;
	}
	
	/***2015-10-26 在线设计编辑列表*/
	public String _edit(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Firstproductdemand demand = this.tordFirstproductdemandManager.get(id);		
		List<Productdemandfile> listfile = tordFirstproductdemandManager.getFilesbyParentid(demand.getFid());
		getRequest().setAttribute("demand", demand);
		//发货日期
		if(session.get("user")!=null){
			 getRequest().setAttribute("isdenglu", true);//已登录
		}else{
			getRequest().setAttribute("isdenglu", false);//未登录
		}
        getRequest().setAttribute("newday", sf.format(new Date()));//当前时间
        getRequest().setAttribute("filelist", listfile);
		return NEW_EDIT_JSP;

	}
	
	//在线设计-需求详情
	public  String detail(){
		System.out.println(id);
		String where =" WHERE f.fid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		ls.add(id);
		//2016-3-22 lxx 历史数据分离
		String t_name=null;
		String queryHistory=getRequest().getParameter("queryHistory");
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
			t_name="t_ord_firstproductdemand_h";
			//给jsp页面传递查看历史数据的信息
			getRequest().setAttribute("queryHistory", queryHistory);
		};
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		List<Firstproductdemand> mand = this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby,t_name);
		getRequest().setAttribute("mand", mand.get(0));
		getRequest().setAttribute("confirm", getRequest().getParameter("confirm"));
		return DETAIL_JSP;
	}
	
	//需求包详情
	public  String packageDetail(){
		System.out.println(id);
		//2016-3-24 lxx
		String queryHistory=this.getRequest().getParameter("queryHistory");
		String t_name=null;
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
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
	
	/** 执行搜索 */
	public String create() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		Calendar  cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = sf.format(cale.getTime());
		System.out.println(firstday);
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = sf.format(cale.getTime());
		getRequest().setAttribute("foverdate", sf.format(new Date())+" 17:00:00");//要货默认交期
		getRequest().setAttribute("firstday", firstday+" 17:00:00");//当月第一天
		getRequest().setAttribute("lastday", lastday+" 17:00:00");//当月最后一天
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user = this.userManager.get(userId);
		getRequest().setAttribute("flinkman", user.getFname());
		getRequest().setAttribute("flinkphone",user.getFtel());
		List<Supplier> sp= this.custproductManager.getBySupplier(userId);
		getRequest().setAttribute("supplier", sp);
		getRequest().setAttribute("fid", this.tordFirstproductdemandManager.CreateUUid());
		System.out.println(lastday);
		return CREATE_JSP;
	}
	
	
	
	public String _create(){
		//2016-4-9 by les 快速在线设计增加 来源明星产品记录		
		if(getRequest().getParameter("fsourceid")!=null){
			String fsourceid =getRequest().getParameter("fsourceid");
			Designschemes sourcedsinfo = designSchemesManager.get(fsourceid);
			getRequest().setAttribute("sourcedsinfo", sourcedsinfo);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String firstday;
		Calendar  cale = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 2);
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = sf.format(cale.getTime());
		System.out.println(firstday);
		if(session.get("user")!=null){
			 getRequest().setAttribute("isdenglu", true);//已登录
		}else{
			getRequest().setAttribute("isdenglu", false);//未登录
		}
		getRequest().setAttribute("foverdate", sf.format(c.getTime())+" 17:00:00");//要货默认交期
		getRequest().setAttribute("newday", sf.format(new Date()));//当前时间
		getRequest().setAttribute("fid", this.tordFirstproductdemandManager.CreateUUid());
		return LINE_DESGIN_JSP;
	}
	
	 /**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    private static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        System.out.println(df.format(theDate));
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first+" 17:00:00");
        map.put("last",   day_last+" 17:00:00");
        return map;
    }
	
	
	public String edit() throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String where =" WHERE f.fcustomerid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		ls.add(customerId);
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		where =where + " and f.fid  in ('"+id+"')";
		Map<String, String> orderby = new HashMap<String,String>();
		List<Firstproductdemand> list =this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby);
		Firstproductdemand demand = list.get(0);
		System.out.println(demand.getFoverdateString());
		System.out.println(demand.getFarrivetimeString());
		getRequest().setAttribute("demand", demand);
		//发货日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(demand.getFarrivetimeString());
        Map<String, String> map = getFirstday_Lastday_Month(date);
        getRequest().setAttribute("farrivetime_first", map.get("first"));
        getRequest().setAttribute("farrivetime_last",  map.get("last"));
        //入库日期
        Date date2 = df.parse(demand.getFoverdateString());
        Map<String, String> map2 = getFirstday_Lastday_Month(date2);
        getRequest().setAttribute("foverdate_first", map2.get("first"));
        getRequest().setAttribute("foverdate_last",  map2.get("last"));
        getRequest().setAttribute("newday", sf.format(new Date()));//当前时间
        String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
        List<Supplier> sp= this.custproductManager.getBySupplier(userId);
		getRequest().setAttribute("supplier", sp);
		return EDIT_JSP;
	}
	
	public String show(){
		String where =" WHERE f.fcustomerid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		ls.add(customerId);
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		where =where + " and f.fid  in ('"+id+"')";
		Map<String, String> orderby = new HashMap<String,String>();
		List<Firstproductdemand> list =this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby);
		Firstproductdemand productdemand = list.get(0);
		getRequest().setAttribute("productdemand", productdemand);
		return SHOW_JSP;
	}
	
	public String print(){
		String where =" WHERE f.fcustomerid  = ? ";
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		ls.add(customerId);
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		where =where + " and f.fid  in ('"+id+"')";
		Map<String, String> orderby = new HashMap<String,String>();
		List<Firstproductdemand> list =this.tordFirstproductdemandManager.execlBySql(where, queryParams, orderby);
		Firstproductdemand productdemand = list.get(0);
		getRequest().setAttribute("productdemand", productdemand);
		return PRINT_JSP;
	}
	
	public String load(){
		String where =" WHERE f.fcustomerid  = ? and f.fisdemandpackage =0" + QueryFilterByUserofuser("fcreatid","and")  ;
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =(String)getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID);	    
		ls.add(customerId);
		String t_name=null;
		if(firstproductdemandQuery !=null){
			if(("全部").equals(firstproductdemandQuery.getFstate())){
				where =where + " and f.fstate  in ('已发布','已分配','已接收','已设计','确认方案','已完成')";
			}else if(("未接收").equals(firstproductdemandQuery.getFstate())){
				where =where + " and f.fstate  in ('已发布','已分配')";
			}else if("已设计".equals(firstproductdemandQuery.getFstate())){
				where =where + " and f.fstate  in ('已设计','确认方案')";
			}
			//三个月前数据  2016-3-21 lxx
			else if("三月前数据".equals(firstproductdemandQuery.getFstate()))
			{
				t_name="t_ord_firstproductdemand_h";
			}
			else{
				where =where + " and f.fstate  in (?)";
				ls.add(firstproductdemandQuery.getFstate());
			}
			if(!("").equals(firstproductdemandQuery.getSearchKey()) && firstproductdemandQuery.getSearchKey() !=null){
				String searchKey="%"+firstproductdemandQuery.getSearchKey()+"%";
				where = where + " and (c.fname like ? or f.fname like ? or f.fnumber like ? or u1.fname  like ? or ifnull(u2.fname,'设计咨询')  like ? or ifnull(u2.ftel,'0577-59395101') like ? or u3.fname  like ? or sp.fname  like ? or f.fdescription like ? or f.flinkman  like ? or f.flinkphone  like ? )";
				for (int i = 0; i < 11; i++) {
					ls.add(searchKey);
				}
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("f.isfauditor", "DESC");
		orderby.put("f.fauditortime", "DESC");
		pageModel = this.tordFirstproductdemandManager.findBySql(where, queryParams, orderby, pageNo, pageSize,t_name);
		for (Firstproductdemand demand : pageModel.getList()) {
			if(demand.getFdesignproviderid()!=null)
			{
				Supplier supinfo = supplierManager.get(demand.getFdesignproviderid());
				demand.setFdesignproviderid(supinfo.getFname());
			}
		}
		List<Firstproductdemand> dlist = pageModel.getList();
		/*
		 * 改为通用的方法获取图片字节流;
		 * for(int i=0;i<dlist.size();i++){
			dlist.get(i).setLatestpathImg(this.getPicture(dlist.get(i).getFid()));
		}*/
//		String sql = " where fstate='存草稿' and fisdemandpackage=0 and fcustomerid ='" + customerId + "'" + QueryFilterByUserofuser("fcreatid","and") ;
		String sql = " where fstate='存草稿' and fisdemandpackage=0 and fcustomerid = ? "+ QueryFilterByUserofuser("fcreatid","and")  ;
		queryParams=new Object[]{customerId};
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		map.put("countCcg", this.tordFirstproductdemandManager.getCount(sql,queryParams));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/***需求包-全部*/
	public String loadpackage(){
		String t_name=null;
		String where =" WHERE f.fcustomerid  = ? and f.fisdemandpackage =1" + QueryFilterByUserofuser("fcreatid","and");
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
		ls.add(customerId);
		if(firstproductdemandQuery !=null){
			//三个月前数据  2016-3-21 lxx
			if(("三月前数据").equals(firstproductdemandQuery.getFstate())){
				t_name="t_ord_firstproductdemand_h";
			}else if(("未接收").equals(firstproductdemandQuery.getFstate())){
				where =where + " and f.fstate  in ('已发布','已分配')";
			}else if(("已接收").equals(firstproductdemandQuery.getFstate())){
				where =where + " and f.fstate  in ('已设计','已接收','关闭','已完成','已生成要货')";
			}
			else{
				//全部
//				where =where + " and f.fstate  in ('已发布','已接收')";
				where =where + " and f.fstate  not in ('存草稿')";
			}
			if(!("").equals(firstproductdemandQuery.getSearchKey()) && firstproductdemandQuery.getSearchKey() !=null){
				String searchKey="%"+firstproductdemandQuery.getSearchKey()+"%";
				where = where + " and (c.fname like ? or f.fname like ? or f.fnumber like ? or u1.fname  like ? or ifnull(u2.fname,'设计咨询')  like ? or ifnull(u2.ftel,'0577-59395101') like ? or u3.fname  like ? or sp.fname  like ? or f.fdescription like ? or f.flinkman  like ? or f.flinkphone  like ?)";
				for (int i = 0; i < 11; i++) {
					ls.add(searchKey);
				}
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
//		orderby.put("f.isfauditor", "DESC");
//		orderby.put("f.fauditortime", "DESC");
		orderby.put("f.fcreatetime", "DESC");
		pageModel = this.tordFirstproductdemandManager.findBySql(where, queryParams, orderby, pageNo, pageSize,t_name);
		List<Firstproductdemand> dlist = pageModel.getList();
		/*
		 * 改为通用的方法获取图片字节流;
		 * for(int i=0;i<dlist.size();i++){
			dlist.get(i).setLatestpathImg(this.getPicture(dlist.get(i).getFid()));
		}*/
		String sql = " where fstate='存草稿' and fisdemandpackage=1 and fcustomerid =?" + QueryFilterByUserofuser("fcreatid","and") ;
		queryParams=new Object[]{customerId};
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		map.put("countCcg", this.tordFirstproductdemandManager.getCount(sql,queryParams));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	/***在线设计-存草稿列表*/
	public String loadccg(){
		String where =" WHERE f.fcustomerid  = ? and fisdemandpackage=0" + QueryFilterByUserofuser("fcreatid","and") ;
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		ls.add(customerId);
		if(firstproductdemandQuery !=null){
			where =where + " and f.fstate  in ('存草稿')";
			if(!("").equals(firstproductdemandQuery.getSearchKey()) && firstproductdemandQuery.getSearchKey() !=null){
				String searchKey="%"+firstproductdemandQuery.getSearchKey()+"%";
				where = where + " and (c.fname like ? or f.fname like ? or f.fnumber like ? or u1.fname  like ? or ifnull(u2.fname,'设计咨询')  like ? or ifnull(u2.ftel,'0577-59395101') like ? or u3.fname  like ? or sp.fname  like ? or f.fdescription like ? or f.flinkman  like ? or f.flinkphone  like ?)";
				for (int i = 0; i < 11; i++) {
					ls.add(searchKey);
				}
//				System.out.println("FirstproductdemandAction.loadpackageccg()模糊查询:"+searchKey);
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("f.isfauditor", "DESC");
		orderby.put("f.fauditortime", "DESC");
		pageModel = this.tordFirstproductdemandManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
		for (Firstproductdemand demand : pageModel.getList()) {
			if(demand.getFdesignproviderid()!=null)
			{
				Supplier supinfo = supplierManager.get(demand.getFdesignproviderid());
				demand.setFdesignproviderid(supinfo.getFname());
			}
		}
		List<Firstproductdemand> dlist = pageModel.getList();
		/*
		 * 改为通用的方法获取图片字节流;
		 * for(int i=0;i<dlist.size();i++){
			dlist.get(i).setLatestpathImg(this.getPicture(dlist.get(i).getFid()));
		}*/
		String sql = " where fstate='存草稿' and fisdemandpackage=0 and fcustomerid =? " + QueryFilterByUserofuser("fcreatid","and") ;
		queryParams=new Object[]{customerId};
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		map.put("countCcg", this.tordFirstproductdemandManager.getCount(sql,queryParams));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/***需求包-草稿箱*/
	public String loadpackageccg(){
		String where =" WHERE f.fcustomerid  = ? and fisdemandpackage=1" + QueryFilterByUserofuser("fcreatid","and") ;
		Object[] queryParams=null;
		List<Object> ls = new ArrayList<Object>();
		String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		ls.add(customerId);
		if(firstproductdemandQuery !=null){
			where =where + " and f.fstate  in ('存草稿')";
			if(!("").equals(firstproductdemandQuery.getSearchKey()) && firstproductdemandQuery.getSearchKey() !=null){
				where = where + " and (c.fname like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
									   "or f.fname like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.fnumber like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or u1.fname  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or ifnull(u2.fname,'设计咨询')  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or ifnull(u2.ftel,'0577-59395101') like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or u3.fname  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or sp.fname  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.fdescription like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.flinkman  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
							           "or f.flinkphone  like '%"+firstproductdemandQuery.getSearchKey()+"%' "+
						             ")";
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		Map<String, String> orderby = new HashMap<String,String>();
		orderby.put("f.isfauditor", "DESC");
		orderby.put("f.fauditortime", "DESC");
		pageModel = this.tordFirstproductdemandManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
		List<Firstproductdemand> dlist = pageModel.getList();
		/*
		 * 改为通用的方法获取图片字节流;
		 * for(int i=0;i<dlist.size();i++){
			dlist.get(i).setLatestpathImg(this.getPicture(dlist.get(i).getFid()));
		}*/
		String sql = " where fstate='存草稿' and fisdemandpackage=1 and fcustomerid = ? " + QueryFilterByUserofuser("fcreatid","and") ;
		queryParams=new Object[]{customerId};
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("list", pageModel.getList());
		map.put("totalRecords", pageModel.getTotalRecords());//总条数
		map.put("pageNo", pageModel.getPageNo());//第几页
		map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		map.put("countCcg", this.tordFirstproductdemandManager.getCount(sql,queryParams));
	return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	
	public String save(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String isfb =getRequest().getParameter("isfb");
			firstproductdemand.setFcreatid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
			firstproductdemand.setFcreatetime(new Date());
			firstproductdemand.setFauditorid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
			firstproductdemand.setFauditortime(new Date());
			if(("1").equals(isfb)){
				firstproductdemand.setIsfauditor(true);
				firstproductdemand.setFstate("已发布");
			}else{
				firstproductdemand.setIsfauditor(false);
				firstproductdemand.setFstate("存草稿");
			}
			firstproductdemand.setFnumber(this.tordFirstproductdemandManager.getNumber("t_ord_firstproductdemand", "F", 4, false));
			firstproductdemand.setFcustomerid(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
			
			if(StringUitl.isNullOrEmpty(firstproductdemand.getFsupplierid()))//新增需求制造商，设计商默认为东经
			{
				firstproductdemand.setFsupplierid("39gW7X9mRcWoSwsNJhU12TfGffw=");
				firstproductdemand.setFdesignproviderid("39gW7X9mRcWoSwsNJhU12TfGffw=");
			}
			else{
				firstproductdemand.setFdesignproviderid(firstproductdemand.getFsupplierid());
			}
			
			//自动接收标志位"1"的情况下，设定状态"已分配",设计商设为制造商
			if( !"存草稿".equals(firstproductdemand.getFstate())) {
				Supplier supplier =  this.supplierManager.get(firstproductdemand.getFsupplierid());
				if(supplier != null && supplier.getFisautoreceive() == 1 ){
					firstproductdemand.setFdesignproviderid(firstproductdemand.getFsupplierid());
					firstproductdemand.setFstate("已分配");
					firstproductdemand.setFalloted(true);
					firstproductdemand.setFallottime(new Date());
				}
			}
		this.tordFirstproductdemandManager.save(firstproductdemand);
		map.put("success", "success");
		map.put("mandPackageId", this.tordFirstproductdemandManager.CreateUUid());
		return writeAjaxResponse(JSONUtil.getJson(map));
	}

	/***2015-10-26   在线设计保存*/
	public String _update(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		Firstproductdemand upt =this.tordFirstproductdemandManager.get(firstproductdemand.getFid());
		String isfb =getRequest().getParameter("isfb");
		if(upt.getFstate().equals("存草稿") || upt.getFstate().equals("已发布")||upt.getFstate().equals("已分配") ){
			if(("1").equals(isfb)){
				if(upt.getFstate().equals("存草稿")){
					upt.setFnumber(this.tordFirstproductdemandManager.getNumber("t_ord_firstproductdemand", "F", 4, false));
					upt.setFauditortime(new Date());
				}
				upt.setIsfauditor(true);
				upt.setFstate("已发布");
			}else{
				upt.setIsfauditor(false);
				upt.setFstate("存草稿");
			}
			if(("0").equals(firstproductdemand.getFsupplierid())){
				upt.setFsupplierid(null);
			}else{
				if(upt.getFisdemandpackage()==1) upt.setFsupplierid(firstproductdemand.getFsupplierid());//在线设计没有修改制造商
			}
			upt.setFname(firstproductdemand.getFname());
			upt.setFlinkphone(firstproductdemand.getFlinkphone());
			upt.setFlinkman(firstproductdemand.getFlinkman());
			upt.setFarrivetime(firstproductdemand.getFarrivetime());
			upt.setFdescription(firstproductdemand.getFdescription());
			upt.setFupdateuserid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
			upt.setFupdatetime(new Date());
			upt.setFiszhiyang(firstproductdemand.getFiszhiyang());
			
			//自动接收标志位"1"的情况下，设定状态"已分配",设计商设为制造商
			if( !"存草稿".equals(upt.getFstate())) {
				Supplier supplier =  this.supplierManager.get(upt.getFsupplierid());
				if(supplier != null && supplier.getFisautoreceive() == 1 ){
					if(firstproductdemand.getFsupplierid() != null){
						upt.setFdesignproviderid(firstproductdemand.getFsupplierid());
					}
					upt.setFstate("已分配");
					upt.setFalloted(true);
					upt.setFallottime(new Date());
				}
			}
			this.tordFirstproductdemandManager.update(upt);
			//upt.setFcreatetime(new Date());
			map.put("success", "success");
			return writeAjaxResponse(JSONUtil.getJson(map));	
		}
		else{
			map.put("success", "方案已设计，不能修改！");
			return writeAjaxResponse(JSONUtil.getJson(map));	
		}
		
	}
	
	public String update(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		Firstproductdemand upt =this.tordFirstproductdemandManager.get(firstproductdemand.getFid());
		String isfb =getRequest().getParameter("isfb");
		if(upt.getFstate().equals("存草稿") || upt.getFstate().equals("已发布")||upt.getFstate().equals("已分配") ){
			if(("1").equals(isfb)){
				if(upt.getFstate().equals("存草稿")){
					upt.setFnumber(this.tordFirstproductdemandManager.getNumber("t_ord_firstproductdemand", "F", 4, false));
					upt.setFauditortime(new Date());
				}
				upt.setIsfauditor(true);
				upt.setFstate("已发布");
			}else{
				upt.setIsfauditor(false);
				upt.setFstate("存草稿");
			}
			if(("0").equals(firstproductdemand.getFsupplierid())){
				upt.setFsupplierid(null);
			}else{
				upt.setFsupplierid(firstproductdemand.getFsupplierid());
			}
			upt.setFname(firstproductdemand.getFname());
			upt.setFamount(firstproductdemand.getFamount());
			upt.setFarrivetime(firstproductdemand.getFarrivetime());
			upt.setFreceivetime(firstproductdemand.getFreceivetime());
			upt.setFdescription(firstproductdemand.getFdescription());
			upt.setFupdateuserid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
			upt.setFupdatetime(new Date());
			
			//自动接收标志位"1"的情况下，设定状态"已分配",设计商设为制造商
			if( !"存草稿".equals(upt.getFstate())) {
				Supplier supplier =  this.supplierManager.get(upt.getFsupplierid());
				if(supplier != null && supplier.getFisautoreceive() == 1 ){
					if(firstproductdemand.getFsupplierid() != null){
						upt.setFdesignproviderid(firstproductdemand.getFsupplierid());
					}
					upt.setFstate("已分配");
					upt.setFalloted(true);
					upt.setFallottime(new Date());
				}
			}
			this.tordFirstproductdemandManager.update(upt);
		map.put("success", "success");
		return writeAjaxResponse(JSONUtil.getJson(map));
		}
		else{
			map.put("success",(upt.getFisdemandpackage()==1?"需求已接收":"需求状态为"+upt.getFstate())+",不能修改！");
			return writeAjaxResponse(JSONUtil.getJson(map));	
		}
	}
	
	//取消发布
	public String  cancel_fb(){
		String fid =getRequest().getParameter("fid");
		Firstproductdemand demand = this.tordFirstproductdemandManager.get(fid);
			demand.setFstate("存草稿");
		this.tordFirstproductdemandManager.update(demand);
		return writeAjaxResponse("success");
	}
	//发布
	public String  sure_fb(){
		String fid =getRequest().getParameter("fid");
		Firstproductdemand demand = this.tordFirstproductdemandManager.get(fid);
			demand.setFstate("已发布");
		this.tordFirstproductdemandManager.update(demand);
		return writeAjaxResponse("success");
	}
	//删除
	public String delete(){
		String[] s=getRequest().getParameterValues("fids");//字符串数组
		/*if(s!=null){
		StringBuffer ids=new StringBuffer();
		for(String fid:s)
		{
			ids.append("'"+fid+"',");
		}
		String fids=ids.substring(0, ids.length()-1);
		if(this.tordFirstproductdemandManager.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fid in ("+fids+") and  freceived=1")) return writeAjaxResponse("需求已接收,不能删除！");
		if(this.tordFirstproductdemandManager.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fid in ("+fids+") and  fstate='关闭' ")) return writeAjaxResponse("需求已关闭，不能删除！");
		}*/
		this.tordFirstproductdemandManager.deleteImpl(s);
		return writeAjaxResponse("success");
	}
	
	/**
	 * 方案取消确认
	 * 制造商未上cps-vmi项目，暂不使用;
	 * 逻辑已改，添加确认方案和确认付款状态，如果要用，请改代码
	 */
	public String backEdit() throws Exception{
		String param =id;
		this.tordFirstproductdemandManager.UnAffirmSchemedesign(param);
		
		return writeAjaxResponse("success");
	}
	
	/**
	 * 方案确认
	 * 方案确认属于老的业务逻辑，这里只需要改确认标记;
	 */
	public String affirm() throws Exception{
		if("".equals(id)){
			return writeAjaxResponse("success");
		}
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String ffirstproductid = getRequest().getParameter("ffirstproductid");
		this.tordFirstproductdemandManager.AffirmSchemedesign(ffirstproductid,id,userid);
		return writeAjaxResponse("success");
	}
	/*public String affirmPay(){
		if(!StringUtils.isEmpty(id)){
			tordFirstproductdemandManager.affirmPay(id);
			return writeAjaxResponse("success");
		}else{
			return writeAjaxResponse("传参错误！");
		}
	}*/
	/**
	 * * 关闭需求
	 * 只修改需求状态为关闭;
	 * */
	public String closeFp() throws Exception{
		String param =id;
		this.tordFirstproductdemandManager.closeFp(param);
		return writeAjaxResponse("success");
	}
	
	/**
	 * * 完成需求
	 * */
	public String finishFp() throws Exception{
		String  fids = id;
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String[] param = {fids,userid};
		String error = this.tordFirstproductdemandManager.finishFp(param);
		return writeAjaxResponse(error==null?"success":error);
	}
	
	/*** 通过方案ID获取图片路径*/
	public String getPicture(String pid){
		String path = null;
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
//		List<Productdemandfile> productfile = tordFirstproductdemandManager.getFilesbyParentid(pid);
//		//2015-10-29 附件有图片类型则显示小图片否则显示默认图片
//		for(int i=0;i<productfile.size();i++){
//			String filename = productfile.get(i).getFname();
//			int lastIndx =filename.lastIndexOf(".");
//			if(lastIndx> -1){
//				filename  = filename.substring(lastIndx);
//				if(filename.matches("\\.(jpg|png|gif|JPG|PNG|GIF)")){
//					path = (productfile.get(i).getFpath()).replace("vmifile", "smallvmifile");
//					path =basePath + path.split("webapps")[1];			
//					break;
//				}
//			}
//		}
		
		//2015-10-30 调用原来方法
		List<Productdemandfile> listfile = productdemandfileManager.getImagesByParentId(pid);
		if(listfile.size()>0){
			path = (listfile.get(0).getFpath()).replace("vmifile", "smallvmifile");
			path =basePath + path.split("webapps")[1];	
		}
		if(path==null){
			path = basePath + "/vmifile/defaultpic.png";
		}
		return path;
	}
	/***需求包修改*/
	public String mand_edit(){
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		Firstproductdemand demand = this.tordFirstproductdemandManager.get(id);
		List<Supplier> sp= this.custproductManager.getBySupplier(userFid);
		getRequest().setAttribute("supplier", sp);
		getRequest().getSession().setAttribute("firstproductdemand", demand);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		getRequest().setAttribute("newday", sf.format(new Date())+" 17:00:00");//当前时间
		return MAND_EDIT_JSP;
	}
}
