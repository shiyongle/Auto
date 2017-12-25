package com.action.deliverapply;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.PageModel;
import com.model.address.Address;
import com.model.customer.Customer;
import com.model.deliverapply.Deliverapply;
import com.model.deliverapply.DeliverapplyQuery;
import com.model.orderState.OrderState;
import com.model.productdef.CustBoardFormula;
import com.model.productdef.MaterialLimit;
import com.model.productdef.Productdef;
import com.model.supplier.Supplier;
import com.opensymphony.xwork2.ModelDriven;
import com.service.address.AddressManager;
import com.service.customer.CustomerManager;
import com.service.deliverapply.DeliverapplyManager;
import com.service.productdef.ProductdefManager;
import com.service.supplier.SupplierManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.Params;
import com.util.StringUitl;
//纸板要货申请
public class BoardDeliverapplyAction extends BaseAction implements ModelDriven<Deliverapply>{
	
	private static final long serialVersionUID = 1L;
	protected static final String LIST_JSP   = "/pages/board/board_list.jsp";//纸板详情页面	
	protected static final String BOARDDETAIL_JSP   = "/pages/board/boardDetail.jsp";//纸板详情页面	
	protected static final String BOARDCREATE_JSP   = "/pages/board/boardOrder.jsp";//纸板新增页面

	@Autowired
	private DeliverapplyManager deliverapplyManager;
	@Autowired
	private ProductdefManager productdefManager;
	@Autowired
	private AddressManager addressManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private SupplierManager supplierManager;
	
	
	private Deliverapply deliverapply=new Deliverapply();
	private DeliverapplyQuery deliverapplyQuery;
	private PageModel<Deliverapply> pageModel;// 分页组件
	
	public Deliverapply getDeliverapply() {
		return deliverapply;
	}
	public void setDeliverapply(Deliverapply deliverapply) {
		this.deliverapply = deliverapply;
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
	//纸板订单列表
	public String list(){
		return LIST_JSP;
	}

	//加载纸板订单列表
	public String loadboard(){
		String t_name=null;
		Object[] queryParams=null;
		Map<String, String> orderby = new HashMap<String,String>();
		List<Object> ls = new ArrayList<Object>();
		//2016-4-2 by les 统一改成根据客户-只查看自己过滤
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		String[] fstate=null;
		String where =" where  mv.fcustomerid  = ? and  mv.fstate!=7 ";
//		userid="35af4765-e19e-11e4-a8a2-00ff6b42e1e5"; //测试用
		ls.add(userid);
		if(deliverapplyQuery !=null){
			where = doSearchKey(ls, where);
			if(!("").equals(deliverapplyQuery.getFstate()) && deliverapplyQuery.getFstate()!=null){
				fstate=getRequest().getParameter("deliverapplyQuery.fstate").split(",");
				//三个月前数据 2016-3-21 lxx
				if("100".equals(fstate[0]))
				{
					t_name="t_ord_deliverapply_board_mv_h";
				}else{
					where+= "and (1=0";
					for(String state:fstate)
					{
						where =where + " or mv.fstate = ?";
						ls.add(state);
					}
					where+= ")";
				}
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		orderby.put("mv.fcreatetime", "desc");
		pageModel = this.deliverapplyManager.findBoardBySql(where, queryParams, orderby, pageNo, pageSize,t_name);
		HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("list", pageModel.getList());
			map.put("totalRecords", pageModel.getTotalRecords());//总条数
			map.put("pageNo", pageModel.getPageNo());//第几页
			map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	//查看纸板订单详细信息
	public String boarddetails(){
		Object[] queryParam=null;
		String queryHistory=this.getRequest().getParameter("queryHistory");
		String t_name=null;
		if("100".equals(queryHistory))
		{
			t_name="t_ord_deliverapply_board_mv_h";
		}
		List<Object> ls=new ArrayList<Object>();
		String where =" where mv.fid  = ?";//mv.fid='a80cec67-18bc-11e5-bc30-00ff6b42e1e5' ";
		ls.add(id);
		queryParam=(Object[])ls.toArray(new Object[ls.size()]);
		List<Deliverapply> list =this.deliverapplyManager.getDeliverapplyboards(where, queryParam, null,t_name);
		if(list.size()!=0)
		{
		Deliverapply deliverapply = list.get(0);
		List<OrderState> newstatelist=new ArrayList<OrderState>();//订单状态
		getRequest().setAttribute("boardinfo", deliverapply);
		String[] states=new String[]{"提交订单","未接收","未入库","未发货"};
		if(deliverapply.getFstate()!=7){
		newstatelist.add(0,new OrderState(0,deliverapply.getFcreatetime(),"提交订单"));
		String sql="";
		Params p = new Params();
		if(deliverapply.getFstate()>1){
			newstatelist.add(1,new OrderState(2,deliverapply.getFreceiptTime(),"已接收"));
		}
		if(deliverapply.getFstate()>3)
		{
//		sql="select fcreatetime  from t_inv_productindetail where fproductplanid='%s' ORDER BY fcreatetime LIMIT 1"; 
//		List<HashMap<String,Date>> intime=deliverapplyManager.QueryBySql(sql.format(sql, deliverapply.getFplanid()),p);
		sql="select fcreatetime  from t_inv_productindetail where fproductplanid=:fppid ORDER BY fcreatetime LIMIT 1"; 
		p.put("fppid", deliverapply.getFplanid());
		List<HashMap<String,Date>> intime=deliverapplyManager.QueryBySql(sql,p);
		newstatelist.add(new OrderState(4,intime.size()>0?intime.get(0).get("fcreatetime"):null,"已入库"));
		}
		if(deliverapply.getFstate()>4){
		 newstatelist.add(new OrderState(5,deliverapply.getFouttime(),"已发货"));
		}
		
		int j=newstatelist.size();
		for(int i=j;i<states.length;i++)
		{
			newstatelist.add(new OrderState(999999,null,states[i]));
		}
		}
		getRequest().setAttribute("boardstate", newstatelist);
		}
		return BOARDDETAIL_JSP;
	}
	
	//删除纸板
	public String deleteboard()
	{
		String result = "";
		String fids =getRequest().getParameter("fids");
		
		StringBuilder custids=new StringBuilder();
		String condition="";
		try {
			if(fids==null)
			{
				throw new Exception("请选择记录删除");
			}
			fids = fids.replaceAll(",", "','");
			condition = " in ('"+fids+"') ";
			if(deliverapplyManager.QueryExistsBySql("select fid from t_ord_deliverapply where fstate<>0 and fstate<>7 and fboxtype=1 and fid "+condition))
			{
				throw new Exception("已生成订单，不允许删除");
			}
			deliverapplyManager.deleteBoardsImpl(condition);
			result = JSONUtil.result(true,"", "", "");
			return writeAjaxResponse(result);
		} catch (Exception e) {
			result = JSONUtil.result(false,e.getMessage(), "", "");
			return writeAjaxResponse(result);
		}
		
	}
	
	
	
	public String loadboardShop(){
		Object[] queryParams=null;
		Map<String, String> orderby = new HashMap<String,String>();
		List<Object> ls = new ArrayList<Object>();
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fcustomerid=getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		String[] fstate=null;
		String where =" where  mv.fcustomerid=? and mv.fcreatorid  = ? and  mv.fstate=7 ";
		ls.add(fcustomerid);
		ls.add(userid);
		if(deliverapplyQuery !=null){
			where = doSearchKey(ls, where);
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		orderby.put("mv.fcreatetime", "desc");
		pageModel = this.deliverapplyManager.findBoardBySql(where, queryParams, orderby, pageNo, pageSize);
		HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("list", pageModel.getList());
			map.put("totalRecords", pageModel.getTotalRecords());//总条数
			map.put("pageNo", pageModel.getPageNo());//第几页
			map.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	private String doSearchKey(List<Object> ls, String where) {
		String searchKey = deliverapplyQuery.getSearchKey();
		if(!StringUtils.isEmpty(searchKey)){//关键字
			if(searchKey.matches("^\\d+\\.?\\d*((\\*|X|x)(\\d+\\.?\\d*)){1,2}$")){
				String[] values = searchKey.split("[xX*]");
				if(values.length == 2){
					where += "and (mv.fmateriallength= ? and mv.fmaterialwidth= ?)";
					ls.add(Float.valueOf(values[0])+"");
					ls.add(Float.valueOf(values[1])+"");
				}else if(values.length == 3){
					where  += "and (mv.fboxlength= ? and mv.fboxwidth= ? and mv.fboxheight= ?)";
					ls.add(Float.valueOf(values[0])+"");
					ls.add(Float.valueOf(values[1])+"");
					ls.add(Float.valueOf(values[2])+"");
				}
			}else{
				where += " and (mv._materialname like ? or mv._suppliername like ? or mv.fnumber like ? or mv.flabel like ? or mv.fdescription like ? )";
				ls.add("%" +searchKey +"%");
				ls.add("%" +searchKey +"%");
				ls.add("%" +searchKey +"%");
				ls.add("%" +searchKey +"%");
				ls.add("%" +searchKey +"%");
			}
		}
		return where;
	}
	
	public String shopOrder(){
		String fids = getRequest().getParameter("fids");
		String result = "";
		try {
			deliverapplyManager.ExecUpdateBoardStateToCreate(fids);
			result = JSONUtil.result(true,"", "", "");
			return writeAjaxResponse(result);
		} catch (Exception e) {
			// TODO: handle exception
			result = JSONUtil.result(false,e.getMessage(), "", "");
			return writeAjaxResponse(result);
		}
	}
	
	public String create(){
		String fcustomerid=getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID)==null?"":getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			MaterialLimit mlimit=null;
			Address address=null;
			Customer c=null;
			List<CustBoardFormula> formula=null;
			if(!StringUitl.isNullOrEmpty(fcustomerid)){
				mlimit=productdefManager.getMaterialLmit(fcustomerid);//获取最值
				List<Address> list=addressManager.getByCustomerId(fcustomerid,userid);//获取默认地址
				address =list.size()>0?list.get(0):null;//获取默认地址
				c=customerManager.get(fcustomerid);//获取客户备注
				formula=productdefManager.getCustBoardFormula(fcustomerid);// 查询客户公式的前一次下单的值
			}
			getRequest().setAttribute("address", address);
			getRequest().setAttribute("materiallimit", JSONUtil.getJsonString(mlimit));
			getRequest().setAttribute("custformula", JSONUtil.getJsonString(formula));
			getRequest().setAttribute("descrition", c!=null?c.getFdescription():"");
		return BOARDCREATE_JSP;
	}
	
	public String getCustBoardFormula(){
		String fcustomerid=(String) getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID);
		if(StringUtils.isEmpty(fcustomerid)){
			return writeAjaxResponse("{}");
		}
		String data = JSONArray.fromObject(productdefManager.getCustBoardFormula(fcustomerid)).toString();
		return writeAjaxResponse(data!=null?data:"{}");
	}
	
	public String edit(){
		String queryHistory = getRequest().getParameter("queryHistory");
		String action = getRequest().getParameter("action");
		//判断是否历史数据，历史数据Deliverapply_h
		String t_name="t_ord_deliverapply";
		if("100".equals(queryHistory))
		{
			t_name+="_h";
		}
		HashMap<String, Object> map =this.deliverapplyManager.getDeliverapplyById(id,t_name);
		if(map==null){
			productdefManager.alertMsg("订单不存在，请刷新重试！");
		}
		Deliverapply dy=new Deliverapply();
		JSONUtil.mapToObject(map, dy);
		MaterialLimit mlimit=productdefManager.getMaterialLmit(dy.getFcustomerid());//获取最值
		Address address=addressManager.get(dy.getFaddressid());
		getRequest().setAttribute("materiallimit", JSONUtil.getJsonString(mlimit));
		getRequest().setAttribute("address", address);
		Productdef productdef = (Productdef)productdefManager.get(dy.getFmaterialfid());
		dy.setLayer(String.valueOf(productdef.getFlayer()));
		getRequest().setAttribute("materialname", productdef.getFqueryname());
		getRequest().setAttribute("materialid", dy.getFmaterialfid());
		JSONObject jsonObject = JSONObject.fromObject(dy);
		if("add".equals(action)){
			jsonObject.remove("fid");
			jsonObject.remove("famount");
			jsonObject.remove("famountpiece");
		}else{
			jsonObject.put("materialname", productdef.getFqueryname());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		jsonObject.put("farrivetime", sdf.format(dy.getFarrivetime()));
		jsonObject.put("fcreatetime", sdf.format(dy.getFcreatetime()));
		getRequest().setAttribute("orderInfo", jsonObject.toString());
		getRequest().setAttribute("supplierid", dy.getFsupplierid());
		getRequest().setAttribute("suppliername", ((Supplier)supplierManager.get(dy.getFsupplierid())).getFname());
		return BOARDCREATE_JSP;
	}
	
//		//账户余额提示
//		public String getCustbalanceInfo(){
//			try {
//				TSysUser userinfo=(TSysUser)getRequest().getSession().getAttribute("cps_user");
//				String fcustomerid=getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID)==null?"":getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();				
//				Params p = new Params();
//				if(!StringUitl.isNullOrEmpty(fcustomerid)){
//					p.put("fcustomerid", fcustomerid);
//					List list=deliverapplyManager.QueryBySql("select fid from t_bd_custaccountbalance where fisread=0 and fcustomerid=:fcustomerid",p);
//					if(list.size()>0){
//						
//						deliverapplyManager.ExecBySql("update t_bd_custaccountbalance set fisread=1 where fid='"+((HashMap)list.get(0)).get("fid")+"'");
//						return writeAjaxResponse(JSONUtil.result(true,userinfo.getFname()+"，您的账户余额不足！","",""));
//					}
//				}
//				return writeAjaxResponse(JSONUtil.result(false,"","",""));
//				
//			} catch (Exception e) {
//				return writeAjaxResponse(JSONUtil.result(false,"","",""));
//			}
//		}
		
		//分页的制造商
		public String getSupplierAppList() {
			Object[] queryParams=null;
			List<Object> ls = new ArrayList<Object>();
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String where =" where  s.fid = ? ";
			ls.add(userid);
			String term=getRequest().getParameter("term");
//			int pageCurrent=getRequest().getParameter("pageNo")==null?1:new Integer(getRequest().getParameter("offset"));
			int defaultpage=getRequest().getParameter("pageSize")==null?50:new Integer(getRequest().getParameter("pageSize"));

			if(!StringUitl.isNullOrEmpty(term)){
					where =where + " and p.FNAME like ? ";
					ls.add("%" +term +"%");
			}
			queryParams = (Object[])ls.toArray(new Object[ls.size()]);
			PageModel<Supplier> spageModel = this.productdefManager.getSupplierListByPage(pageNo, defaultpage, where, queryParams);
			HashMap<String,Object> map =new HashMap<String,Object>();
				map.put("list", spageModel.getList());
				map.put("totalRecords", spageModel.getTotalRecords());//总条数
				map.put("pageNo", spageModel.getPageNo());//第几页
				map.put("pageSize", spageModel.getPageSize());//每页显示多少条
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	
		public String saveBoardSignleDeliverapply(){
			String result = "";
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String custid=getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
			try{
				if(StringUitl.isNullOrEmpty(deliverapply.getFid())){
					deliverapply.setFcreatorid(userid);
				}
				deliverapply.setFcustomerid(custid);
				deliverapply.setFupdateuserid(userid);
				deliverapplyManager.saveBoardSignleDeliverapply(deliverapply);
				result = JSONUtil.result(true,"", "", "");
			} catch (Exception e) {
				result = JSONUtil.result(false,e.getMessage(), "", "");
			}
			return writeAjaxResponse(result);
		}
		//材料选择后，获取最早配送时间
		public String getfirstDateofMaterial(){
			String result="";
			String fmaterialfid=getRequest().getParameter("fmaterialfid");
			String fsupplierid =getRequest().getParameter("fsupplierid");
			try {
				result=JSONUtil.result(true, deliverapplyManager.getfirstDateofMaterial(fmaterialfid, fsupplierid), "","");
				
			} catch (Exception e) {
				result=JSONUtil.result(false, e.getMessage(), "","");
			}
			return writeAjaxResponse(result);
		}
		
		
		public  String getCustomerLabelList(){
		
				String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
				String label =getRequest().getParameter("labelName");
				String queryCon ="";
				Params p=new Params();
				 p.put("fcustomerid",customerId );
				if(!StringUitl.isNullOrEmpty(label)){
					//label=new String(label.getBytes("ISO-8859-1"),"UTF-8");
					 queryCon = " and fname like :fname ";
					 p.put("fname", "%"+label.trim()+"%");
				}
				String sql = "select fid,fname from t_ord_custboardlabel where fcustomerid =:fcustomerid "+queryCon+" ORDER BY CONVERT( fname USING gbk ),fcreatetime desc  limit 0,50";
				List<HashMap<String, Object>> data = deliverapplyManager.QueryBySql(sql,p);
				return writeAjaxResponse(JSONUtil.result(data, data.size(), 1, 50));
		}
		//删除客户标签
		public  String delCustomerLabelInfo() throws IOException {
		
				String labelName = getRequest().getParameter("labelName");
				String msg = "";
				if(StringUitl.isNullOrEmpty(labelName)){
					return writeAjaxResponse("success");
				}
				String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
				deliverapplyManager.deleteCustomerlabel(customerId, labelName);
				return writeAjaxResponse("success");
			}
		//备注设置为客户默认的描述
		public String setCustomerDescription() {
				String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
				String description = getRequest().getParameter("description");
				deliverapplyManager.updateCustomerDescription(customerId, description);
				return writeAjaxResponse("success");
		}
		
		public String saveMaterialLimit(){
			String cid =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
			String customerid = getRequest().getParameter("customerid");
			if(StringUitl.isNullOrEmpty(customerid)){	//是纸板下单，而不是产品档案编辑时保存
				customerid=cid;
			}
			if(customerid == null){
				return writeAjaxResponse("您未关联客户，请联系平台处理！");
			}
			MaterialLimit materialLimit = new MaterialLimit();
			materialLimit.setFmaxlength(Double.valueOf(getRequest().getParameter("fmaxlength")));
			materialLimit.setFmaxwidth(Double.valueOf(getRequest().getParameter("fmaxwidth")));
			materialLimit.setFminlength(Double.valueOf(getRequest().getParameter("fminlength")));
			materialLimit.setFminwidth(Double.valueOf(getRequest().getParameter("fminwidth")));
			materialLimit.setFcustomerid(customerid);
			deliverapplyManager.saveMaterialLimit(materialLimit);
			return writeAjaxResponse("success");
		}
		public  String checkBdDeliverapply()  {
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			try {
				String customerId =getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();	    
				Deliverapply deliverapply = this.deliverapply;
				deliverapply.setFcustomerid(customerId);
				String fid = deliverapply.getFid();
				//2015-08-19  新增的订单此处加上连个控制   1：同规格，此材料不同于历史材料提示    2：同规格，数量，材料已经下过提示
				if(StringUitl.isNullOrEmpty(fid)){
					String checkSql = "";
					String mtid = deliverapply.getFmaterialfid();
					Params param=new Params();
					 //1：同规格，此材料不同于历史材料提示
					if(!"不压线".equals(deliverapply.getFstavetype())){
//						checkSql = "select DISTINCT t._materialname,t.fmaterialfid from  t_ord_deliverapply_board_mv t where t.fboxlength ="+deliverapply.getFboxlength()+" ";
//						checkSql += " and t.fboxwidth = "+deliverapply.getFboxwidth()+" and  t.fboxheight =  "+deliverapply.getFboxheight()+" ";
//						checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"' ";
						checkSql = "select DISTINCT t._materialname,t.fmaterialfid from  t_ord_deliverapply_board_mv t where t.fboxlength =:fbln ";
						checkSql += " and t.fboxwidth = :fbwid and  t.fboxheight =  :fbhgt ";
						checkSql += " and t.fcustomerid = :fcid ";
						param.put("fbln", deliverapply.getFboxlength());
						param.put("fbwid", deliverapply.getFboxwidth());
						param.put("fbhgt", deliverapply.getFboxheight());
						param.put("fcid", deliverapply.getFcustomerid());
					}
					else{
//						checkSql = "select DISTINCT t._materialname,t.fmaterialfid from  t_ord_deliverapply_board_mv t where ";
//						checkSql += "  t.fmateriallength = "+deliverapply.getFmateriallength()+" and  t.fmaterialwidth =  "+deliverapply.getFmaterialwidth()+" ";
//						checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"'  and t.fstavetype = '不压线' ";
						checkSql = "select DISTINCT t._materialname,t.fmaterialfid from  t_ord_deliverapply_board_mv t where ";
						checkSql += "  t.fmateriallength = :fmln and  t.fmaterialwidth =  :fmwid ";
						checkSql += " and t.fcustomerid = :fcid  and t.fstavetype = '不压线' ";
						param.put("fmln", deliverapply.getFmateriallength());
						param.put("fmwid", deliverapply.getFmaterialwidth());
						param.put("fcid", deliverapply.getFcustomerid());
					
					}
//					List<HashMap<String, Object>> list = this.deliverapplyManager.QueryBySql(checkSql,new Params());
					List<HashMap<String, Object>> list = this.deliverapplyManager.QueryBySql(checkSql,param);
					param.getData().clear();
					if(list.size() == 1 && !list.get(0).get("fmaterialfid").equals(mtid)){
						throw new Exception("该规格只做过"+list.get(0).get("_materialname")+"，是否继续！");
					}
					//同规格做个多个材料，判断此单材料
					if(list.size() > 1){
						Boolean flag = true;
						for(int i=0;i<list.size();i++){
							if(list.get(i).get("fmaterialfid").equals(mtid)){
								flag = false;
							}
						}
						if(flag){
							throw new Exception("该规格没做过此种材料，是否继续！");
						}
					}
//					else if(list.size()>1){
//						throw new DJException("该规格做过不同的材料，是否继续！");
//					}
					 //2：同规格，数量，材料已经下过提示
					if(!"不压线".equals(deliverapply.getFstavetype())){
//						checkSql = "select t.fid from  t_ord_deliverapply_board_mv t where t.fboxlength ="+deliverapply.getFboxlength()+" ";
//						checkSql += " and t.fboxwidth = "+deliverapply.getFboxwidth()+" and  t.fboxheight =  "+deliverapply.getFboxheight()+" ";
//						checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"' and t.fmaterialfid= '"+deliverapply.getFmaterialfid()+"' ";
//						checkSql += " and t.famount = "+deliverapply.getFamount()+" and t.fcreatetime >=CURRENT_DATE() ";
						checkSql = "select t.fid from  t_ord_deliverapply_board_mv t where t.fboxlength =:fbln ";
						checkSql += " and t.fboxwidth = :fbwid and  t.fboxheight =  :fbhgt ";
						checkSql += " and t.fcustomerid = :fcid and t.fmaterialfid= :fmid ";
						checkSql += " and t.famount = :famount and t.fcreatetime >=CURRENT_DATE() ";
						param.put("fbln",deliverapply.getFboxlength() );
						param.put("fbwid",deliverapply.getFboxwidth() );
						param.put("fbhgt",deliverapply.getFboxheight() );
						param.put("fcid",deliverapply.getFcustomerid() );
						param.put("fmid",deliverapply.getFmaterialfid() );
						param.put("famount",deliverapply.getFamount() );
					}
					else{
//						checkSql = "select t.fid from  t_ord_deliverapply_board_mv t where  ";
//						checkSql += "  t.fmateriallength = "+deliverapply.getFmateriallength()+" and  t.fmaterialwidth =  "+deliverapply.getFmaterialwidth()+" ";
//						checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"' and t.fmaterialfid= '"+deliverapply.getFmaterialfid()+"' ";
//						checkSql += " and t.famount = "+deliverapply.getFamount()+" and t.fstavetype = '不压线'  and t.fcreatetime >=CURRENT_DATE() ";
						checkSql = "select t.fid from  t_ord_deliverapply_board_mv t where  ";
						checkSql += "  t.fmateriallength = :fmln and  t.fmaterialwidth =  :fmwid ";
						checkSql += " and t.fcustomerid = :fcid and t.fmaterialfid= :fmid ";
						checkSql += " and t.famount = :famount and t.fstavetype = '不压线'  and t.fcreatetime >=CURRENT_DATE() ";
						param.put("fmln", deliverapply.getFmateriallength());
						param.put("fmwid", deliverapply.getFmaterialwidth());
						param.put("fcid", deliverapply.getFcustomerid());
						param.put("fmid",deliverapply.getFmaterialfid() );
						param.put("famount",deliverapply.getFamount() );
					}	
					List<HashMap<String, Object>> slist = deliverapplyManager.QueryBySql(checkSql,param);
//					List<HashMap<String, Object>> slist = deliverapplyManager.QueryBySql(checkSql,new Params());
					if(slist.size()>0){
						throw new Exception("此规格今天已有同样的订单，是否继续！");
					}
				}
				//2015-08-19  新增的订单此处加上连个控制   1：同规格，此材料不同于历史材料提示    2：同规格，数量，材料已经下过
			}
			catch (Exception e) {
				return writeAjaxResponse(JSONUtil.result(true, e.getMessage(), "", "[{\"flag\":1,\"msg\": \""+e.getMessage()+"\"}]"));
			}
			return writeAjaxResponse(JSONUtil.result(true, "", "", "[{\"flag\":0}]"));
		}
		
		
		
}
