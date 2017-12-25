package com.action.custproduct;

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
import com.model.address.Address;
import com.model.custproduct.TBdCustproduct;
import com.model.custproduct.TBdCustproductQuery;
import com.model.productdemandfile.Productdemandfile;
import com.model.supplier.Supplier;
import com.model.user.TSysUser;
import com.opensymphony.xwork2.ModelDriven;
import com.service.address.AddressManager;
import com.service.customer.CustomerManager;
import com.service.custproduct.TBdCustproductManager;
import com.service.firstproductdemand.TordFirstproductdemandManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.user.TSysUserManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.Params;
import com.util.StringUitl;

public class TBdCustproductAction extends BaseAction implements ModelDriven<TBdCustproduct>{

	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-7-20 下午4:00:49
	*/
	private static final long serialVersionUID = -3302488364686159595L;
	protected static final String LIST_JSP   = "/pages/custproduct/list.jsp";
	protected static final String NEW_LIST_JSP   = "/pages/custproduct/new_list.jsp";
	protected static final String CREATE_JSP = "/pages/custproduct/create.jsp";
	protected static final String EDIT_JSP = "/pages/custproduct/edit.jsp";
	protected static final String ORDER_JSP = "/pages/custproduct/orderDetail.jsp";
	protected static final String ADD_ADDRESS_JSP = "/pages/custproduct/addAddress.jsp";
	//protected static final String FILE_UPLOAD_JSP = "/pages/custproduct/fileUpload.jsp";
	protected static final String FILE_UPLOAD_JSP = "/pages/custproduct/imageUpload.jsp";
	private TBdCustproduct custproduct=new TBdCustproduct();
	private PageModel<TBdCustproduct> pageModel;// 分页组件
	private String[] ides;
	@Autowired
	private TBdCustproductManager custproductManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private ProductdemandfileManager productdemanManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private TSysUserManager userManager;
	@Autowired
	private TordFirstproductdemandManager tordFirstproductdemandManager;
	private TBdCustproductQuery custproductQuery;
	
	public TBdCustproduct getCustproduct() {
		return custproduct;
	}

	public void setCustproduct(TBdCustproduct custproduct) {
		this.custproduct = custproduct;
	}
	public PageModel<TBdCustproduct> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<TBdCustproduct> pageModel) {
		this.pageModel = pageModel;
	}

	public String[] getIdes() {
		return ides;
	}

	public void setIdes(String[] ides) {
		this.ides = ides;
	}
	private  String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public TBdCustproduct getModel() {
		return custproduct;
	}
	
	public TBdCustproductQuery getCustproductQuery() {
		return custproductQuery;
	}

	public void setCustproductQuery(TBdCustproductQuery custproductQuery) {
		this.custproductQuery = custproductQuery;
	}

	public String list() throws Exception{
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		if(keyword !=null && !("").equals(keyword)){
			java.net.URLDecoder.decode(keyword, "UTF-8");
			String keysword =java.net.URLDecoder.decode(keyword, "UTF-8");
			getRequest().setAttribute("keyword", keysword);
		}
		List<Supplier> sp= this.custproductManager.getBySupplier(userFid);
		getRequest().setAttribute("supplier", sp);
		return LIST_JSP;//返回后台订单列表
	}
	public String create(){
		return CREATE_JSP;
	}
	public String edit(){
		TBdCustproduct pt = this.custproductManager.get(id);
		getRequest().setAttribute("custproduct", pt);
		return EDIT_JSP;
	}
	/*** 加载列表数据*/
	public String load(){
		String where = "where tu.fid =? and pt.feffect =1  and pt.fcharacterid is null ";
		Object[] params = null;
		Map<String, String> orderby =new HashMap<String, String>();
		List<Object> paramls = new ArrayList<Object>();
			paramls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		if(custproductQuery !=null){
			if(!("").equals(custproductQuery.getSupplierId()) && !("0").equals(custproductQuery.getSupplierId())){//制造商
//				where = where +" and tp.fid = ?";
				where = where + "and (pf.fsupplierid =? or ifnull(pf.fsupplierid ,'')='' )";
				paramls.add(custproductQuery.getSupplierId());
			}
			if(custproductQuery.getFiscommon()==1){//常用商品
				where = where +" and pt.fiscommon = ?";
				paramls.add(1);
			}
			if(!("").equals(custproductQuery.getSearchKey())){
				where = where +" and (pt.FNAME like ? or pt.FSPEC like ? )";
				paramls.add("%"+custproductQuery.getSearchKey()+"%");
				paramls.add("%"+custproductQuery.getSearchKey()+"%");
			}
		}
		if(keyword !=null){
			where = where +" and (pt.FNAME like ? or pt.FSPEC like ? )";
			paramls.add("%"+keyword+"%");
			paramls.add("%"+keyword+"%");
		}
		params =(Object[])paramls.toArray(new Object[paramls.size()]);
		orderby.put("pt.fcreatetime", "desc");//按客户产品创建时间倒叙
		pageModel = this.custproductManager.findBySql(where, params, orderby, pageNo, pageSize);
		List<TBdCustproduct> ls = pageModel.getList();
/*
 * 改为通用的方法获取图片字节流;
		for(int i=0;i<ls.size();i++){
			ls.get(i).setPathImg(this.getPicture(ls.get(i).getFid()));
		}
*/		
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("list", ls);
			m.put("totalRecords", pageModel.getTotalRecords());//总条数
			m.put("pageNo", pageModel.getPageNo());//第几页
			m.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(m));
	}
	
	/*** 保存*/
	public String save(){
		custproduct.setFid(this.custproductManager.CreateUUid());
		custproduct.setFcreatetime(new Date());
		custproduct.setFcreatorid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		custproduct.setFcustomerid(getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString());
		this.custproductManager.save(custproduct);
		return writeAjaxResponse("success");
	}
	
	/***修改*/
	public String update(){
		TBdCustproduct u = this.custproductManager.get(custproduct.getFid());
		u.setFname(custproduct.getFname());
		u.setFspec(custproduct.getFspec());
		this.custproductManager.update(u);
		return writeAjaxResponse("success");
	}
	
	/*** 删除*/
	public String delete(){
		String[] s=getRequest().getParameterValues("ides");//字符串数组
		for(String id:s){
			this.custproductManager.deleteImpl(id);
		}
		return writeAjaxResponse("success");
	}
	
	/*** 下单*/
	public String placeOrder(){
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String cusproductId =getRequest().getParameter("fid");
		getRequest().setAttribute("fid", cusproductId);
		List<TBdCustproduct> product = this.custproductManager.getById(userId, cusproductId);
		getRequest().setAttribute("product", product.get(0));
		List<Address> address= new ArrayList<Address>();
		if(product.size()>0){
			address =this.addressManager.getByCustomerId(product.get(0).getFcustomerid());
		}
		getRequest().setAttribute("address", address);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		Calendar c4 = Calendar.getInstance();
		c1.add(Calendar.DAY_OF_MONTH, 5);
		c2.add(Calendar.DAY_OF_MONTH, 7);
		c3.add(Calendar.MONTH, 1);
		c4.add(Calendar.DAY_OF_MONTH, 2);
		getRequest().setAttribute("supplierId", product.get(0).getSupplierId());
		getRequest().setAttribute("address", address);
		getRequest().setAttribute("farriveTime", sf.format(c1.getTime()));//要货默认交期
		getRequest().setAttribute("ffinishtime", sf.format(c2.getTime()));//备货首次发货
		getRequest().setAttribute("fconsumetime", sf.format(c3.getTime()));//备货周期
		getRequest().setAttribute("currentTime", sf.format(c4.getTime()));//当前时间加2天
		return ORDER_JSP;
	}
	/***收货地址封装 */
	public String getAddress(){
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String cusproductId =getRequest().getParameter("fid");
	/*	List<TBdCustproduct> product = this.custproductManager.getById(userId, cusproductId);*/
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userId);
		List<Address> address= new ArrayList<Address>();
		if(!StringUitl.isNullOrEmpty(fcustomerid)){
			//address =this.addressManager.getByCustomerId(fcustomerid);
			address = this.addressManager.getByCustomerId(fcustomerid, userId);
		}
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("address", address);
		return writeAjaxResponse(JSONUtil.getJson(map));
		
	}
	
	/*** 新增收货地址*/
	public String add_address(){
		getRequest().setAttribute("fcustomerid", getRequest().getParameter("fcustomerid"));
		return ADD_ADDRESS_JSP;
	}
	
	public String fileUpload(){
		getRequest().setAttribute("fid", getRequest().getParameter("fid"));
		return FILE_UPLOAD_JSP;
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
	/*** 设为常用*/
	public String isCommon(){
		String[]  arrIds = getRequest().getParameter("fid").split(",");
		for(int i=0;i<arrIds.length;i++){
			TBdCustproduct product = this.custproductManager.get(arrIds[i]);
			if(!product.getFiscommon()){
				product.setFiscommon(true);
				this.custproductManager.update(product);
			}
		}
//		TBdCustproduct product = this.custproductManager.get(getRequest().getParameter("fid"));
//		product.setFiscommon(true);
//		this.custproductManager.update(product);
		return writeAjaxResponse("success");
	}
	/*** 取消常用*/
	public String cancelCommon(){
		String[]  arrIds = getRequest().getParameter("fid").split(",");
		for(int i=0;i<arrIds.length;i++){
			TBdCustproduct product = this.custproductManager.get(arrIds[i]);
				product.setFiscommon(false);
				this.custproductManager.update(product);
		}
		return writeAjaxResponse("success");
	}
	/*** 备货-首发日期*/
	public String setFirstTime(){
		int day=Integer.parseInt(getRequest().getParameter("day"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.DAY_OF_MONTH, day);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("date", sf.format(c2.getTime()));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/*** 备货-月周期*/
	public String setTime(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.MONTH, 1);
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("date", sf.format(c2.getTime()));
		return writeAjaxResponse(JSONUtil.getJson(map));
	}

	/**
	 * 根据需求ID查询所有客户产品列表及附件;
	 * @return
	 */
	public String getProductWithfp(){
		List<HashMap<String, HashMap<String, Object>>> list = custproductManager.getProductWithfp(id);
	    return writeAjaxResponse(JSONUtil.getJson(list));
	}

	/***************新版本代码*******************/
	
	public String new_list(){
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		List<Supplier> sp= this.custproductManager.getBySupplier(userFid);
		getRequest().setAttribute("supplier", sp);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 5);
		getRequest().getSession().setAttribute("foverdate", sf.format(c.getTime())+" 17:00:00");//要货默认交期
		getRequest().getSession().setAttribute("newday", sf.format(new Date())+" 17:00:00");//当月最后一天
		TSysUser currentUser = this.userManager.get(userFid);
		getRequest().getSession().setAttribute("currentUser", currentUser);//要货默认交期
		String fid = this.tordFirstproductdemandManager.CreateUUid();
		getRequest().getSession().setAttribute("mandPackageId", fid);
		return NEW_LIST_JSP;
	}
	
	//在线设计下单,加入购物车，批量加入购物车
	public String orderOnline(){
		String userFid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fcustproductid = getRequest().getParameter("fcustproductid");
		String fsupplierid = getRequest().getParameter("fsupplierid");
		String type = getRequest().getParameter("type");
		String fileparentid = "";
		TBdCustproduct cp = null;
		if(!"2".equals(type)){
			cp = this.custproductManager.get(fcustproductid);
			fileparentid = cp.getFid();
		}
		//附件
		List<Productdemandfile> listPfile = this.productdemanManager.getByParentId(fcustproductid);
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
		try {
			//库存
			/*List<Supplier> supplier = this.custproductManager.getBySupplier(userFid);*/
			List<HashMap<String,Object>> list = this.custproductManager.getCustproductStock(fcustproductid,fsupplierid);
			
			getRequest().setAttribute("stock", list);//客户产品库存
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//地址
		String fcustomerid = this.customerManager.getCustomerIdByUserid(userFid);
		List<Address> address= new ArrayList<Address>();
		if(!StringUitl.isNullOrEmpty(fcustomerid)){
			address =this.addressManager.getByCustomerId(fcustomerid,userFid);
		}
		
		String sql ="select 1 from t_pdt_productreqallocationrules where fbacthstock=1 and fcustomerid='"+fcustomerid+"' and fsupplierid='"+fsupplierid+"'";
		if(this.custproductManager.QueryExistsBySql(sql)){
			getRequest().setAttribute("fbacthstock", true);//是否批量备货
		}
		
		
		getRequest().setAttribute("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//当前时间
		//制造商的交期设置
		sql = "SELECT c.fdays,c.fdefaultdays,s.fisManageStock FROM t_sys_supplier s INNER JOIN t_sys_supplierdelivertime c ON s.fid=c.fsupplierid where s.fid ='"+fsupplierid+"'";	
		List<HashMap<String,Integer>> list = custproductManager.QueryBySql(sql, new Params());
		if(list.size()>0){
			getRequest().setAttribute("fdays",list.get(0).get("fdays"));
			getRequest().setAttribute("fdefaultdays", list.get(0).get("fdefaultdays"));	
			getRequest().setAttribute("fisManageStock", list.get(0).get("fisManageStock"));//制造商出入库管理
		}	
		getRequest().setAttribute("custproduct", cp);//客户产品信息
		getRequest().setAttribute("fsupplierid", fsupplierid);//制造商ID
		getRequest().setAttribute("productfile", url);//客户产品附件
		getRequest().setAttribute("address", address);//客户地址
		if(address.size()==0){
			getRequest().setAttribute("address", "");//客户地址
		}
		getRequest().setAttribute("type", type);//0"下单按钮" 1"加入购物车" 2"批量加入购物车"
		getRequest().setAttribute("fcustproducts", fcustproductid);//记录客户产品ID
		if(!"2".equals(type)){
			getRequest().setAttribute("picurl", this.getPicture(fileparentid));//放置默认大图片
		}
		return "/pages/deliverapply/orderOnline.jsp";
	}
}
