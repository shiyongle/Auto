package com.action.productplan;

import java.io.IOException;
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
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import com.action.BaseAction;
import com.model.PageModel;
import com.model.productdef.Productdef;
import com.model.productdemandfile.Productdemandfile;
import com.model.productplan.OrderInOutBean;
import com.model.productplan.ProductPlan;
import com.model.productplan.ProductPlanQuery;
import com.model.user.TSysUser;
import com.opensymphony.xwork2.ModelDriven;
import com.service.productdef.ProductdefManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.productplan.ProductplanManager;
import com.service.saledeliver.SaledeliverManager;
import com.service.user.TSysUserManager;
import com.util.Constant;
import com.util.JSONUtil;
import com.util.Params;
import com.util.StringUitl;

//纸箱接单
@SuppressWarnings("serial")
public class ProductplanAction extends BaseAction implements ModelDriven<ProductPlan>{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 

	//forward paths
	protected static final String LIST_JSP= "/pages/productplan/cardOrder.jsp";
	protected static final String SHOW_JSP = "/pages/productplan/cardDetail.jsp";
	protected static final String ORDERINOUT_JSP = "/pages/productplan/stock_operate_win.jsp";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "!/page/TordProductplan/list.do";
	@Autowired
	private ProductplanManager productplanManager;
	@Autowired
	private SaledeliverManager saledeliverManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private ProductdefManager productdefManager;
	@Autowired
	private TSysUserManager userManager;

	private ProductPlan Productplan;
	private OrderInOutBean orderInOutBean;
	private ProductPlanQuery productplanQuery;

	private PageModel<HashMap<String,Object>> pageModel;// 分页组件


	public PageModel<HashMap<String,Object>> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<HashMap<String,Object>> pageModel) {
		this.pageModel = pageModel;
	}

	public OrderInOutBean getOrderInOutBean() {
		return orderInOutBean;
	}

	public void setOrderInOutBean(OrderInOutBean orderInOutBean) {
		this.orderInOutBean = orderInOutBean;
	}

	public ProductPlan getProductplan() {
		return Productplan;
	}

	public void setProductplan(ProductPlan productplan) {
		Productplan = productplan;
	}


	public ProductPlanQuery getProductplanQuery() {
		return productplanQuery;
	}

	public void setProductplanQuery(ProductPlanQuery productplanQuery) {
		this.productplanQuery = productplanQuery;
	}

	public ProductPlan getModel() {
		return Productplan;
	}

	/** 执行搜索 */
	public String list() {
		return LIST_JSP;
	}


	/** 执行搜索 */
	public String loadlist() {
		String t_name=null;
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		String where =" where d.fsupplierid=?  and d.fboxtype=0 and   ifnull(d.faudited,0)=1";
		List<Object> ls = new ArrayList<Object>();
//		fsupplierid="0c0fc615-7d27-11e4-bdb9-00ff6b42e1e5";  //测试用
		ls.add(fsupplierid);
		if(fsupplierid==null)
		{
			pageModel=new PageModel<HashMap<String,Object>>();
			pageModel.setList(new ArrayList<HashMap<String,Object>>());
			pageModel.setPageSize(10);
			return writeAjaxResponse(JSONUtil.result(pageModel));
		}
		Object[] queryParams=null;
		Map<String, String> orderby = new LinkedHashMap<String,String>();//多个顺序排序
		orderby.put("d.farrivetime", "DESC");
		//orderby.put("d.fcreatetime ", "DESC");//按交期+编号排序；原：按 创建时间+编号
		orderby.put("d.fnumber","ASC");
		if(productplanQuery!=null){
			if(!StringUitl.isNullOrEmpty(productplanQuery.getFstate()))
			{
				//三个月前数据2016-3-21 lxx
				if("100".equals(productplanQuery.getFstate()))
				{
					t_name="t_ord_productplan_h";
				}else{
					//				where+=String.format(" and d.fstate in (%s)",productplanQuery.getFstate());
					where+=" and d.fstate in ("+productplanQuery.getFstate().replaceAll("\\d+","?")+")";
					String fstates[]=productplanQuery.getFstate().split(",");
					if(fstates.length>0){
					for(String fstate:fstates){
						ls.add(fstate);
					}
					}
				}
			}
			if(!StringUitl.isNullOrEmpty(productplanQuery.getTimeQuantum())&&!StringUitl.isNullOrEmpty(productplanQuery.getFarrivetimeBegin()))
			{
				where +=" and d.fcreatetime between  ? and  ? ";
				ls.add(productplanQuery.getFarrivetimeBegin());
				ls.add(productplanQuery.getFarrivetimeEnd());
			}
			if(!StringUitl.isNullOrEmpty(productplanQuery.getSearchKey())){//关键字
				String searchKey="%" +productplanQuery.getSearchKey() +"%";
				where  += " and (c.fname like ? or da.fordernumber like ? or d.fnumber like ? or f.fname like ?  ";
				ls.add(searchKey);
				ls.add(searchKey);
				ls.add(searchKey);
				ls.add(searchKey);
				if(productplanQuery.getSearchKey().matches("^\\d\\.?\\d*((\\*|X|x)?(\\d+\\.?\\d*)?){0,3}$")){
					where  += "  or p.fspec like ?";
					ls.add("%" +productplanQuery.getSearchKey().replaceAll("\\*|X|x","_") +"%");
				}
				where +=")";
			}
		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);
		pageModel = productplanManager.findBySql(where, queryParams, orderby, pageNo, pageSize,t_name);
		return writeAjaxResponse(JSONUtil.result(pageModel));
	}

	/** 查看详情*/
	public String detail() {
		Object[] param=null;
		List<Object> p=new ArrayList<Object>();
		//		String where=" where d.fid='%s'";
		//		where=String.format(where, id);
		String queryHistory=this.getRequest().getParameter("queryHistory");
		String where=" where d.fid=?";
		p.add(id);
		param=(Object[])p.toArray(new Object[p.size()]);
		boolean history=false;
		if("100".equals(queryHistory))
		{
			history=true;
		}
		List<ProductPlan> list=productplanManager.getProductplanList(where , param, null,history);
		if(list.size()>0){
			Productdef dinfo=this.productdefManager.get(list.get(0).getFproductdefid());
			List<Productdemandfile> ls = this.productdemandfileManager.getByParentId(list.get(0).getFcustproduct());
			this.getRequest().setAttribute("productplan", list.get(0));
			this.getRequest().setAttribute("filelist", ls);
			this.getRequest().setAttribute("productdef", dinfo);
		}
		return SHOW_JSP;

	}

	/** 接收*/
	public String receiveplan() {
		synchronized (this.getClass()){
			String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();

			Params param=new Params();
			try {
				String fid="'"+id+"'";
				///服务端验证是否确认
				//				String sql = "select 1 from t_ord_productplan where fid in (%s) and faffirmed=1";
				String sql = "select 1 from t_ord_productplan where fid in (:fid) and faffirmed=1";
				param.put("fid", fid);
				if(productplanManager.QueryExistsBySql(sql,param)) throw new Exception("订单已接收！");
				//				if(productplanManager.QueryExistsBySql(String.format(sql,fid))) throw new Exception("订单已接收！");
				//				 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(%s)";
				sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(:fid)";
				//				if(productplanManager.QueryExistsBySql(String.format(sql,fid))) throw new Exception("已关闭订单不能接收！");
				if(productplanManager.QueryExistsBySql(sql,param)) throw new Exception("已关闭订单不能接收！");
				productplanManager.updateReceiveState(fid, userid);
				return writeAjaxResponse(JSONUtil.result(true, "", "", ""));

			} catch (Exception e) {
				return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
			}
		}
	}
	/**
	 * 取消接收
	 * @return
	 */
	public String unreceiveplan()
	{
		synchronized (this.getClass()){
			try {
				if(StringUitl.isNullOrEmpty(id))
				{
					throw new Exception("请选择记录操作");
				}
//					String fid="'"+id+"'";
				TSysUser user=(TSysUser)getRequest().getSession().getAttribute("cps_user");
				productplanManager.updateUnReceiveState(id, user);
				return writeAjaxResponse(JSONUtil.result(true, "", "", ""));
			} catch (Exception e) {
				return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
			}
		}
	}
	/** 出入库 */
	public String createOrderInOut() {
		setRequestAttribute("id", id);
		return ORDERINOUT_JSP;
	}

	public String execplan(){
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String fsupplierid=saledeliverManager.getFsupplieridByUser(userid);
		String where =" where d.fsupplierid=?  and d.fboxtype=0 and   ifnull(d.faudited,0)=1";
		List<Object> ls = new ArrayList<Object>();
		ls.add(fsupplierid);
		if(fsupplierid==null)
		{
			pageModel=new PageModel<HashMap<String,Object>>();
			pageModel.setList(new ArrayList<HashMap<String,Object>>());
			pageModel.setPageSize(10);
			return writeAjaxResponse(JSONUtil.result(pageModel));
		}
		Object[] queryParams=null;
		Map<String, String> orderby = new LinkedHashMap<String,String>();//多个顺序排序
		orderby.put("d.fcreatetime ", "DESC");
		orderby.put("d.fnumber","ASC");
		if(productplanQuery!=null){
			if(!StringUitl.isNullOrEmpty(productplanQuery.getFstate()))
			{
				//				where+=String.format(" and d.fstate in (%s)",productplanQuery.getFstate());
				where+=" and d.fstate in (?)";
				ls.add(productplanQuery.getFstate());
			}
			if(!StringUitl.isNullOrEmpty(productplanQuery.getTimeQuantum())&&!StringUitl.isNullOrEmpty(productplanQuery.getFarrivetimeBegin()))
			{
				where +=" and d.fcreatetime between  ? and  ? ";
				ls.add(productplanQuery.getFarrivetimeBegin());
				ls.add(productplanQuery.getFarrivetimeEnd());
			}
			if(!StringUitl.isNullOrEmpty(productplanQuery.getSearchKey())){//关键字
				where  += " and (c.fname like ? or da.fordernumber like ? or d.fnumber like ? or f.fname like ?  ";
				ls.add("%" +productplanQuery.getSearchKey() +"%");
				ls.add("%" +productplanQuery.getSearchKey() +"%");
				ls.add("%" +productplanQuery.getSearchKey() +"%");
				ls.add("%" +productplanQuery.getSearchKey() +"%");
				if(productplanQuery.getSearchKey().matches("^\\d\\.?\\d*((\\*|X|x)?(\\d+\\.?\\d*)?){0,3}$")){
					where  += "  or p.fspec like ?";
					ls.add("%" +productplanQuery.getSearchKey().replaceAll("\\*|X|x","_") +"%");
				}
				where +=")";
			}
		}
		String[] fids=getRequest().getParameterValues("fid");
		if(fids !=null ){
			String fid ="";
			for(int i=0 ;i<fids.length;i++){
				if(i ==0){
					fid = "?";
				}else{
					fid = fid + ",?"; 
				}
				ls.add(fids[i]);
			}
			where =where + " and d.fid in (" + fid + ")";

		}
		queryParams = (Object[])ls.toArray(new Object[ls.size()]);			
		WritableWorkbook book = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");

		String fileName ="纸箱接单"+format.format(new Date());
		try {
			getResponse().setContentType("multipart/form-data");
			getResponse().setHeader("Content-Disposition",
					"attachment;fileName=" + fileName+ ".xls");
			book = Workbook.createWorkbook(getResponse().getOutputStream());
			WritableSheet wsheet = book.createSheet("纸箱接单", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
			SheetSettings ss = wsheet.getSettings();
			ss.setVerticalFreeze(2);  // 设置行冻结前2行
			WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
			WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
			WritableCellFormat wcf = new WritableCellFormat(font1);	  //设置样式，字体
			wcf.setAlignment(Alignment.CENTRE);                   //平行居中
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   //垂直居中
			WritableCellFormat wcf2 = new WritableCellFormat(font2);  //设置样式，字体
			//   wcf2.setBackground(Colour.LIGHT_ORANGE);
			wcf2.setAlignment(Alignment.CENTRE);                  //平行居中
			wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
			wcf2.setWrap(true);  
			wsheet.mergeCells( 0 , 0 , 10 , 0 ); // 合并单元格  
			Label titleLabel = new Label( 0 , 0 , " 纸箱接单",wcf);//TODO
			wsheet.addCell(titleLabel);
			wsheet.setRowView(0, 1000); // 设置第一行的高度
			int[] headerArrHight = {10,20,20,20,10,30,30,20,30,10,10,10,20};
			String headerArr[] = {"序号","客户名称","采购订单号","制造订单号","订单状态","产品名称","规格","交期","地址","入库","出库","数量","备注"};      
			String fstatevalue[]={"未接收","已接收","已入库","已入库"};//状态

			for (int i = 0; i < headerArr.length; i++) {
				wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
				wsheet.setColumnView(i, headerArrHight[i]);
			}
			List<ProductPlan> plist = this.productplanManager.getProductplanList(where, queryParams, orderby);
			int conut = 2;
			for(int i=0;i<plist.size();i++){
				wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
				wsheet.addCell(new Label( 1 , conut ,plist.get(i).getCname(),wcf2));
				wsheet.addCell(new Label(2, conut,plist.get(i).getFpcmordernumber(),wcf2));
				wsheet.addCell(new Label(3, conut,plist.get(i).getFnumber(),wcf2));
				wsheet.addCell(new Label(4,conut,(plist.get(i).getFstate()>fstatevalue.length?"":fstatevalue[plist.get(i).getFstate()]),wcf2));
				wsheet.addCell(new Label(5, conut,null,wcf2));//产品名称
				wsheet.addCell(new Label(6, conut,plist.get(i).getFspec(),wcf2));
				wsheet.addCell(new Label(7, conut,plist.get(i).getFarrivetimeString(),wcf2));
				wsheet.addCell(new Label(8, conut,plist.get(i).getFaddress(),wcf2));
				wsheet.addCell(new jxl.write.Number(9, conut,StringUitl.isNullOrEmpty(plist.get(i).getFstockinqty())?0:Integer.valueOf(plist.get(i).getFstockinqty()),wcf2));
				wsheet.addCell(new jxl.write.Number(10, conut,StringUitl.isNullOrEmpty(plist.get(i).getFstockoutqty())?0:Integer.valueOf(plist.get(i).getFstockoutqty()),wcf2));
				wsheet.addCell(new jxl.write.Number(11, conut,plist.get(i).getFamount(),wcf2));
				wsheet.addCell(new Label(12, conut,plist.get(i).getFdescription(),wcf2));
				conut++;
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

	public void validateOrderInOut(){
		String error = null;
		int stockIn = orderInOutBean.getStockIn();
		int stockOut = orderInOutBean.getStockOut();
		boolean in;
		if(stockIn<0 || stockOut<0 || (stockIn==0 && stockOut==0)|| (stockIn>0 && stockOut>0)){
			error = "出入库数量不正确，不能提交！";
		}else{
			Productplan = productplanManager.get(orderInOutBean.getProductPlanId());
			if(Productplan == null){
				error = "传参错误,制造商Id不正确！";
			}else if(!StringUtils.isEmpty(Productplan.getFschemedesignid())){
				error = "暂不支持有特性产品出入库，请在老平台操作！";
			}else if(Productplan.getFaffirmed() == null || Productplan.getFaffirmed() == 0){
				error = "供应商未接收不能出入库！";
			}else{
				orderInOutBean.setInStock(in = stockIn>0?true:false);
				if(in && Productplan.getFstate()==3){
					error = "订单已完成不能入库！";
				}else if(!in){
					//					String sql = "select 1 from t_pdt_vmiproductparam where fproductid = '"+Productplan.getFproductdefid()+"' and FSUPPLIERID = '"+Productplan.getFsupplierid()+"' and FTYPE = 0 ";
					//					if(productdefManager.QueryExistsBySql(sql)){
					Params param=new Params();
					String sql = "select 1 from t_pdt_vmiproductparam where fproductid = :fpid and FSUPPLIERID = :fsid and FTYPE = 0 ";
					param.put("fpid", Productplan.getFproductdefid());
					param.put("fsid", Productplan.getFsupplierid());
					if(productdefManager.QueryExistsBySql(sql,param)){
						error = "该产品、制造商、下单类型为通知！";
					}
				}
			}
		}
		setRequestAttribute("error", error);
	}

	public String orderInOut(){
		String error = (String) getRequestAttribute("error");
		if(error!=null){
			return writeAjaxResponse(error);
		}
		orderInOutBean.setUserid(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		error = productplanManager.orderInOut(Productplan,orderInOutBean);
		return writeAjaxResponse(error==null?"success":error);
	}
}
