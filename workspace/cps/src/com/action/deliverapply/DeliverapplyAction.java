package com.action.deliverapply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.address.Address;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.deliverapply.DeliverapplyQuery;
import com.model.mystock.Mystock;
import com.model.orderState.OrderState;
import com.model.productdemandfile.Productdemandfile;
import com.model.supplier.Supplier;
import com.model.userCustomer.UserCustomer;
import com.opensymphony.xwork2.ModelDriven;
import com.service.address.AddressManager;
import com.service.cusprivatedelivers.CusprivatedeliversManager;
import com.service.customer.CustomerManager;
import com.service.custproduct.TBdCustproductManager;
import com.service.deliverapply.DeliverapplyManager;
import com.service.mystock.MystockManager;
import com.service.orderState.OrderStateManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.supplier.SupplierManager;
import com.service.userCustomer.UserCustomerManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.Params;
import com.util.StringUitl;

public class DeliverapplyAction extends BaseAction implements ModelDriven<Deliverapply>{
	
	private static final long serialVersionUID = 1L;
	
	protected static final String LIST_JSP   = "/pages/deliverapply/list.jsp";
	protected static final String EDIT_JSP   = "/pages/deliverapply/edit.jsp";
	protected static final String DETAIL_JSP   = "/pages/deliverapply/orderDetail.jsp";

	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private OrderStateManager orderStateManager;
	@Autowired
	private DeliverapplyManager deliverapplyManager;
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private TBdCustproductManager custproductManager;
	@Autowired
	private UserCustomerManager userCustomerManager;
	@Autowired
	private MystockManager mystockManager;
	@Autowired
	private SupplierManager supplierManage;
	@Autowired
	private CusprivatedeliversManager cusprivatedeliversManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductdemandfileManager productdemanManager;
	private Deliverapply deliverapply=new Deliverapply();
	private Mystock myctock=new Mystock();
	private DeliverapplyQuery deliverapplyQuery;
	private PageModel<Deliverapply> pageModel;// 分页组件
	
	public Deliverapply getDeliverapply() {
		return deliverapply;
	}
	public void setDeliverapply(Deliverapply deliverapply) {
		this.deliverapply = deliverapply;
	}
	public Mystock getMystock() {
		return myctock;
	}
	public void setMystock(Mystock myctock) {
		this.myctock = myctock;
	}
	
	public DeliverapplyQuery getDeliverapplyQuery() {
		return deliverapplyQuery;
	}
	public void setDeliverapplyQuery(DeliverapplyQuery deliverapplyQuery) {
		this.deliverapplyQuery = deliverapplyQuery;
	}
	
	public PageModel<Deliverapply> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<Deliverapply> pageModel) {
		this.pageModel = pageModel;
	}
	@Override
	public Deliverapply getModel() {
		return this.deliverapply;
	}
	/*** 下单
	 * @throws ParseException */
	public String save() throws ParseException{
		String currentUserId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String currentCustomerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		this.deliverapplyManager.saveImpl(deliverapply,currentUserId,currentCustomerId);
		return writeAjaxResponse("success");
	}
	
	public String list(){
		return LIST_JSP;
	}
	
	public String load(){
		String t_name=null;
		//2016-4-2 by les 统一改成根据客户-只查看自己过滤
		//String where =" where tsy.FID  = ?";
		String where =" where mv.fcustomerid = ?";
		Object[] queryParams=null;
		Map<String, String> orderby = new HashMap<String,String>();
		List<Object> ls = new ArrayList<Object>();
//		ls.add("5de35a65-54dc-11e4-bdb9-00ff6b42e1e5");  //测试用
		ls.add(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		if(deliverapplyQuery !=null){
			/*if(!("").equals(deliverapplyQuery.getFordertimeBegin())){//下单开始
				where =where + " and date_format(mv.fcreatetime,'%Y-%m-%d') >= ? ";
				ls.add(deliverapplyQuery.getFordertimeBegin());
			}
			if(!("").equals(deliverapplyQuery.getFordertimeEnd())){//下单结束
				where =where + " and date_format(mv.fcreatetime,'%Y-%m-%d') <= ? ";
				ls.add(deliverapplyQuery.getFordertimeEnd());
			}
			if(!("").equals(deliverapplyQuery.getFconsumetimeBegin())){//周期开始
				where =where + " and date_format(mv.farrivetime,'%Y-%m-%d') >= ? ";
				ls.add(deliverapplyQuery.getFconsumetimeBegin());
			}
			if(!("").equals(deliverapplyQuery.getFconsumetimeEnd())){//周期结束
				where =where + " and date_format(mv.farrivetime,'%Y-%m-%d') <= ? ";
				ls.add(deliverapplyQuery.getFconsumetimeEnd());
			}*/
			if( !("").equals(deliverapplyQuery.getSearchKey())){//关键字
				where =where + " and (mv._custpdtname like ? or mv._spec like ? or mv.faddress like ? or mv._suppliername like ? or mv.fnumber like ? or mv.fdescription like ? )";
				for (int i = 0; i < 6; i++) {
					ls.add("%" +deliverapplyQuery.getSearchKey() +"%");
				}
			}
			if(!("").equals(deliverapplyQuery.getFstate()) && deliverapplyQuery.getFstate()!=null){
				if(deliverapplyQuery.getFstate()==0){//未接收
					where =where + " and mv.fstate = 0";
				}else if(deliverapplyQuery.getFstate()==1){//已接收
					where =where + " and (mv.fstate = 1 or mv.fstate = 2 or mv.fstate = 3 )";
				}else if(deliverapplyQuery.getFstate()==4){//已入库
					where =where + " and mv.fstate = 4";
				}else if(deliverapplyQuery.getFstate()==5){//部分发货
					where =where + " and mv.fstate = 5";
				}else if(deliverapplyQuery.getFstate()==6){//部分发货
					where =where + " and mv.fstate = 6";
				}else
				{
					//三个月前数据  2016-3-21 lxx
					t_name="t_ord_deliverapply_card_mv_h";
				}
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		orderby.put("mv.fcreatetime", "desc");
		//pageModel = this.deliverapplyManager.findBySql(where, queryParams, orderby, pageNo, pageSize);
		pageModel = this.deliverapplyManager.findBySql(where, queryParams, orderby, pageNo, pageSize,t_name);
		List<Deliverapply> dlist = pageModel.getList();
	/*	
	 * 改为通用的方法获取图片字节流;
	 * for(int i=0;i<dlist.size();i++){
			dlist.get(i).setLatestpathImg(this.getPicture(dlist.get(i).getFcusproductid()));
		}*/
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userFid);
		String sql = "select 1 from t_pdt_productreqallocationrules where fbacthstock=1 and fcustomerid='%s'";
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(this.cusprivatedeliversManager.QueryExistsBySql(String.format(sql, fcustomerid))){
			map.put("fbacthstock", true);//是否批量备货
		}
			map.put("list", pageModel.getList());
			map.put("totalRecords", pageModel.getTotalRecords());//总条数
			map.put("pageNo", pageModel.getPageNo());//第几页
			map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	/*** 修改*/
	public String edit(){
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		Deliverapply dy =this.deliverapplyManager.get(id);
		Date d =dy.getFarrivetime();
		int hours = d.getHours();
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		String date = sdf1.format(dy.getFarrivetime());
		List<Address> address= new ArrayList<Address>();
		if(dy!=null){
			address =this.addressManager.getByCustomerId(dy.getFcustomerid());
		}
		getRequest().setAttribute("address", address);
		getRequest().setAttribute("hours", hours);
		getRequest().setAttribute("date", date);
		getRequest().setAttribute("deliverapply", dy);
		return EDIT_JSP;
	}
	
	public String update() throws ParseException{
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		SimpleDateFormat sdf2 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Deliverapply dly = this.deliverapplyManager.get(deliverapply.getFid());
		dly.setFupdatetime(new Date());
		dly.setFupdateuserid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		if(("am").equals(deliverapply.getHours())){
			String date =sdf1.format(deliverapply.getFarrivetime());
			date =date +" 09:00:00";
			dly.setFarrivetime(sdf2.parse(date));
		}else if(("pm").equals(deliverapply.getHours())){
			String date =sdf1.format(deliverapply.getFarrivetime());
			date =date +" 14:00:00";
			dly.setFarrivetime(sdf2.parse(date));
		}
		dly.setFamount(deliverapply.getFamount());
		dly.setFaddressid(deliverapply.getFaddressid());
		Address ad =this.addressManager.get(deliverapply.getFaddressid());
		dly.setFaddress(ad.getFdetailaddress());
		this.deliverapplyManager.update(dly);
		return writeAjaxResponse("success");
	}
	
	public String delete(){
		String result="";
		
		try{
		if(StringUitl.isNullOrEmpty(id))
		{
			throw new Exception("请选择记录删除");
		}
//		 String fid ="'"+id+"'";
		 this.deliverapplyManager.deleteboxImpl(id);
		result = JSONUtil.result(true,"", "", "");
		}catch(Exception e)
		{
			result = JSONUtil.result(false,e.getMessage(), "", "");
		}
		return writeAjaxResponse(result);
	}
	
	public  String detail(){
		String where =" where mv.fid  = '"+id+"'";
		//2016-3-24 lxx
		String queryHistory=this.getRequest().getParameter("queryHistory");//取得页面传来的参数
		String t_name=null;
		if("100".equals(queryHistory))//判断是否为三月前数据
		{
			t_name="t_ord_deliverapply_card_mv_h";
		}
		List<Deliverapply> list =this.deliverapplyManager.getDeliverapplyInfo(where, null, null,t_name);
		if(list.size()!=0)
		{
			Deliverapply deliverapply = list.get(0);
			List<Productdemandfile> ls = this.productdemandfileManager.getByParentId(deliverapply.getFcusproductid());
			List<OrderState> newstatelist=new ArrayList<OrderState>();//订单状态
			List<OrderState> statelist=orderStateManager.getOrderStateInfo(deliverapply.getFid());
			getRequest().setAttribute("deliverapplyinfo", deliverapply);
			getRequest().setAttribute("custproductfile", ls);
			String[] states=new String[]{"提交需求","已接收","已入库","部分发货","全部发货"};
			newstatelist.add(0,new OrderState(0,deliverapply.getFcreatetime(),"提交需求"));
			for(int i=1;i<5;i++)
			{
				if(statelist.size()>0&&statelist.get(0).getFstate()==(i+2)){
					statelist.get(0).setFstateValue(states[i]);
					newstatelist.add(statelist.get(0));
					statelist.remove(0);continue;
				}
				else{
				newstatelist.add(new OrderState(i+2,null,states[i]));
				}
			}
			getRequest().setAttribute("deliverapplystate", newstatelist);
		}
		
		return DETAIL_JSP;
	}
	//在线下单 要货保存
	public String saveOrderOnlineByDeliverapply(){
		try{
		Deliverapply d = (Deliverapply)getRequest().getAttribute("deliverapply");
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		Address address = this.addressManager.get(d.getFaddressid());
		UserCustomer uc =this.userCustomerManager.getByUserId(userid);
		if(StringUitl.isNullOrEmpty(d.getFid())){
			d.setFid(this.deliverapplyManager.CreateUUid());
			d.setFnumber(this.deliverapplyManager.getNumber("t_ord_deliverapply", "N", 4, true));
		}else
		{
			if(custproductManager.QueryExistsBySql(String.format("SELECT p.fid FROM t_ord_deliverapply d INNER JOIN  t_ord_productplan  p  ON d.fplanid=p.fid WHERE ifnull(p.faffirmed,0)=0 and  d.fid='%s'",d.getFid())))
			{
				throw new Exception("制造商正在处理该订单,不能修改！请返回");
			}
	
			if(custproductManager.QueryExistsBySql(String.format("SELECT fid FROM t_ord_deliverapply WHERE fiscreate=1 and  fid='%s'",d.getFid())))
			{
				throw new Exception("订单已接收,不能修改！请返回");
			}

		}
		d.setFcreatetime(new Date());
		d.setFcreatorid(userid);
		d.setFcustomerid(uc.getFcustomerid());
		d.setFaddress(address.getFdetailaddress());
		d.setFlinkman(address.getFlinkman());
		d.setFlinkphone(address.getFphone());
		d.setFboxmodel(0);
		
		//反写客户产品信息的最后下单时间和最后下单数量
		TBdCustproduct custproduct = this.custproductManager.get(d.getFcusproductid());
		custproduct.setFlastordertime(new Date());
		custproduct.setFlastorderfamount(d.getFamount());
		d.setFmaterialfid(custproduct.getFproductid());//非东经走快速生成订单，东经按是否快速下单？决定是否快速生成订单。

		//根据分配规则里的是否快速下单确定是否走快速下单
		Params p = new Params();
		if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(d.getFsupplierid()))//东经按是否快速下单？决定是否快速生成订单。
		{
		String sql = "select ifnull(fisstock,0) fisstock from t_pdt_productreqallocationrules where fsupplierid='%s' and fcustomerid='%s' order by fisstock desc";
		List<HashMap<String,Object>> list = this.custproductManager.QueryBySql(String.format(sql,d.getFsupplierid(),d.getFcustomerid()), p);
	     if(list.size()==0||Integer.parseInt(list.get(0).get("fisstock").toString())==0)
	     {
	       d.setFmaterialfid(null);
	     }
		}
	     this.deliverapplyManager.saveOrderOnlineByDeliverapply(d,custproduct);
		}catch(Exception e)
	     {
	    	 return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
	     }
		return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
	}
	//在线下单 备货保存
	public String saveOrderOnlineByMystock(){
		try{
		String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		Mystock mystock = (Mystock)getRequest().getAttribute("mystock");
		UserCustomer uc =this.userCustomerManager.getByUserId(userid);
		if(StringUitl.isNullOrEmpty(mystock.getFid())){
			mystock.setFid(this.mystockManager.CreateUUid());
		}else
		{
			if(mystockManager.QueryExistsBySql(String.format("select fid from mystock where fid='%s' and (fordered=1 or fstate>0)",mystock.getFid())))
			{
				throw new Exception("已接收的订单,不能修改！请返回！");
			}
		}
		mystock.setFnumber(this.mystockManager.getNumber("mystock", "B", 4, true));
		mystock.setFcreatetime(new Date());
		mystock.setFcreateid(userid);
		mystock.setFcustomerid(uc.getFcustomerid());
		mystock.setFstate(0);
		mystock.setFremark(mystock.getFdescription());
		mystock.setFnumber(this.mystockManager.getNumber("mystock","B", 4,true));
		this.mystockManager.saveOrUpdate(mystock);
		}catch(Exception e)
		{
			return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
		}
		return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
	}
	public String getStockDetail(){
		String fid = getRequest().getParameter("fid");
		Mystock m = this.mystockManager.get(fid);
		TBdCustproduct c = this.custproductManager.get(m.getFcustproductid());
		Supplier s = this.supplierManage.get(m.getFsupplierid());
		List<Productdemandfile> ls = this.productdemandfileManager.getByParentId(m.getFcustproductid());
		getRequest().setAttribute("mystock", m);
		getRequest().setAttribute("custproduct", c);
		getRequest().setAttribute("supplier", s);
		getRequest().setAttribute("productdemandfile", ls);
		return "/pages/deliverapply/stockDetail.jsp";
	}
	
	/*** 通过方案ID获取图片路径*/
	public String getPicture(String pid){
		String path = null;
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
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
	public String editDeliverOrder(){
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fid = getRequest().getParameter("fid");
		Deliverapply d =this.deliverapplyManager.get(fid);
		try {
			//库存
			/*List<Supplier> supplier = this.custproductManager.getBySupplier(userFid);*/
			List<HashMap<String,Object>> list = this.custproductManager.getCustproductStock(d.getFcusproductid(),d.getFsupplierid());
			getRequest().setAttribute("stock", list);//客户产品库存
		} catch (Exception e) {
			// TODO: handle exception
		}
		//地址
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userFid);
		List<Address> address= new ArrayList<Address>();
		if(!StringUitl.isNullOrEmpty(fcustomerid)){
			address =this.addressManager.getByCustomerId(fcustomerid);
		}
		//交期设置
		getRequest().setAttribute("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//当前时间
		//制造商的交期设置
//		String sql = "SELECT c.fdays,s.fisManageStock FROM t_sys_supplier s INNER JOIN t_sys_supplierdelivertime c ON s.fid=c.fsupplierid where s.fid ='"+d.getFsupplierid()+"'";	
//		List<HashMap<String,Integer>> list = custproductManager.QueryBySql(sql, new Params());
		String sql = "SELECT c.fdays,s.fisManageStock FROM t_sys_supplier s INNER JOIN t_sys_supplierdelivertime c ON s.fid=c.fsupplierid where s.fid =:fid ";	
		Params param=new Params();
		param.put("fid",d.getFsupplierid() );
		List<HashMap<String,Integer>> list = custproductManager.QueryBySql(sql, param);
		if(list.size()>0){
			getRequest().setAttribute("fdays",list.get(0).get("fdays"));
			getRequest().setAttribute("fisManageStock", list.get(0).get("fisManageStock"));//制造商出入库管理
		}	
		//附件
		List<Productdemandfile> listPfile = this.productdemanManager.getByParentId(d.getFcusproductid());
		List<String> url =new ArrayList<String>();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
		if(listPfile.size() >0){
			String path = "";
			for(int i=0;i<listPfile.size();i++){
				if(i>=5){//前端最多只能显示5张图片
					break;
				}
				path = (listPfile.get(i).getFpath());
				path =basePath + path.split("webapps")[1];
				url.add(path);
			}
		}
		//客户产品信息
		TBdCustproduct c = this.custproductManager.get(d.getFcusproductid());
		
		getRequest().setAttribute("custproduct", c);//客户产品信息
		getRequest().setAttribute("fsupplierid", d.getFsupplierid());//制造商ID
		getRequest().setAttribute("productfile", url);//客户产品附件
		getRequest().setAttribute("address", address);//客户地址
		getRequest().setAttribute("type", 0);//0"下单按钮" 1"加入购物车" 2"批量加入购物车"
		getRequest().setAttribute("fcustproducts", d.getFcusproductid());//记录客户产品ID
		getRequest().setAttribute("deliverapply", d);
		Address ad = this.addressManager.get(d.getFaddressid());
		getRequest().setAttribute("addressname", ad.getFname()+" 联系人:"+ad.getFlinkman()+"联系电话 :"+ad.getFphone());
		getRequest().setAttribute("picurl", this.getPicture(c.getFid()));//放置默认大图片
		return "/pages/deliverapply/orderOnline.jsp";
	}
	
	
	
}
