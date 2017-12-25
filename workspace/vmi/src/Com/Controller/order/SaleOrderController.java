package Com.Controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.Inv.IproductindetailDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.ISupplierDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.order.IDeliversDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;

@Controller
public class SaleOrderController {
	Logger log = LoggerFactory.getLogger(SaleOrderController.class);
	@Resource
	private ISaleOrderDao saleOrderDao;
	@Resource
	private IDeliversDao DeliversDao;
	@Resource
	private IStorebalanceDao storebalanceDao;
	@Resource
	private IproductindetailDao productindetailDao;
	@Resource
	private IOutWarehouseDao outWarehouseDao;
	@Resource
	private IProductPlanDao productPlanDao;
	@Resource
	private ISupplierDao SupplierDao;
	@Resource
	private IProductdefDao productdefDao;
	@Resource
	private IDeliverapplyDao deliverapplyDao; 

	@RequestMapping(value = "/SaveSaleOrder")
	public String SaveSaleOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Saleorder pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			String amount = request.getParameter("famount");
			if(amount==null||"".equals(amount)){
				throw new DJException("数量不能为空！");
			}else if(!DataUtil.positiveIntegerCheck(amount)){
				throw new DJException("数量格式不正确！");
			}
			String arrivetime = request.getParameter("farrivetime");
			if(arrivetime==null||"".equals(arrivetime)){
				throw new DJException("交期不能为空！");
			}else if(!DataUtil.dateFormatCheck(arrivetime)){
				throw new DJException("请填写交期为正确的日期时间格式！");
			}
			String fproductid = request.getParameter("fproductdefid");
			String fcustomerid = request.getParameter("fcustomerid");
			if(!DataUtil.DataCheck(request, "t_bd_customer", null, null, saleOrderDao, fcustomerid)) {
				throw new DJException("数据校验有误，客户ID无效！");
			}
			if(!DataUtil.DataCheck(request, "t_pdt_productdef", "FCUSTOMERID", null, saleOrderDao, fproductid)) {
				throw new DJException("数据校验有误，请确认是否有客户相应权限，或产品资料ID无效！");
			}
			String fcusproductid = "";
			if(request.getParameter("fcustproduct")!=null){
				fcusproductid = request.getParameter("fcustproduct");
			}
			String fdescription = request.getParameter("fdescription");
			String sql = "SELECT 1 FROM `t_ord_schemedesignentry` WHERE fid=(SELECT fcharacterid FROM `t_pdt_productdef` WHERE fid ='"+fproductid+"') AND fallot=0";
			List<HashMap<String,Object>> list = saleOrderDao.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException("有特性产品不允许下单！");
			}
			int famount = new Integer(amount);
			int fnewtype = new Integer(productdefDao.Query(fproductid).getFnewtype());
			Date farrivetime = f.parse(arrivetime.replace("T"," "));
			String orderid = "";
			String fordernumber = "";
			
			if (fid != null && !"".equals(fid)) {
				pinfo = saleOrderDao.Query(fid);
				if (pinfo != null && pinfo.getFtype() == 1) {
					reponse.setCharacterEncoding("utf-8");
					reponse.getWriter().write(
							JsonUtil.result(false, "只能修改初始化类型订单!", "", ""));
					return null;
				}
				orderid = pinfo.getForderid();
				fordernumber = pinfo.getFnumber();
				if(DeliversDao.QueryExistsBySql("select 1 from t_ord_saleorder where fallot=1 and forderid='"+orderid+"'"))
				{
					reponse.setCharacterEncoding("utf-8");
					reponse.getWriter().write(
							JsonUtil.result(false, "已分配（子件已分配）不能修改!", "", ""));//已审核不能修改
					return null;
				}
				
				if(fproductid.equals(pinfo.getFproductdefid()) && 
						fcustomerid.equals(pinfo.getFcustomerid())){
					String ordersql = "update t_ord_saleorder set flastupdateuserid='"+userid+"',flastupdatetime='"+f.format(new Date())+
							"',fcustproduct='"+fcusproductid+"'," + "famount=famountrate*"+famount+",farrivetime='"+
							f.format(farrivetime)+"',fdescription='"+fdescription+"' where forderid = '" + orderid +"'";
					DeliversDao.ExecBySql(ordersql);
				}else{
					DeliversDao.ExecBySql("delete from t_ord_saleorder where forderid = '" + orderid +"'");
					createMergerOrder(orderid,fordernumber,fproductid,fnewtype,userid,fcustomerid,fcusproductid,famount,farrivetime,fdescription);
				}
			} else {
//				pinfo = new Saleorder();
//				pinfo.setFid(fid);
//				pinfo.setFcreatorid(userid);
//				pinfo.setFcreatetime(new Date());
//				pinfo.setFallot(0);
//				pinfo.setFtype(0);
//				pinfo.setFaudited(0);
//				pinfo.setFnumber(saleOrderDao.getFnumber("t_ord_saleorder",
//						"Z", 4));
				createMergerOrder(orderid,fordernumber,fproductid,fnewtype,userid,fcustomerid,fcusproductid,famount,farrivetime,fdescription);
			}
			result = JsonUtil.result(true,"保存成功!", "", "");
//			pinfo.setFlastupdatetime(new Date());
//			pinfo.setFlastupdateuserid(userid);
//			pinfo.setFamount(Integer.valueOf(request.getParameter("famount")));
//			pinfo.setFcustomerid(request.getParameter("fcustomerid"));
//			pinfo.setFcustproduct(request.getParameter("fcustproduct"));
//			pinfo.setFarrivetime("".equals(request.getParameter("farrivetime")) ? null
//					: f.parse(request.getParameter("farrivetime").replace("T",
//							" ")));
//			pinfo.setFbizdate("".equals(request.getParameter("fbizdate")) ? null
//					: f.parse(request.getParameter("fbizdate")
//							.replace("T", " ")));
//			pinfo.setFaudited(0);
//			String fproductdefid = request.getParameter("fproductdefid");
//			pinfo.setFproductdefid(fproductdefid);
//			if (fproductdefid != null && !"".equals(fproductdefid)) {
//				List list = saleOrderDao
//						.QueryBySql("select fsupplierid from t_bd_productcycle where fproductdefid='"
//								+ fproductdefid + "' order by fcreatetime asc");
//				if (list.size() > 0) {
//					pinfo.setFsupplierid(((HashMap) list.get(0)).get(
//							"fsupplierid").toString());
//				} else {
//					pinfo.setFsupplierid("");
//				}
//
//			}
//			HashMap<String, Object> params = saleOrderDao.ExecSave(pinfo);
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true, "保存成功!", "", "");
//			} else {
//				result = JsonUtil.result(false, "保存失败!", "", "");
//			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);

		return null;

	}

	private void createMergerOrder(String orderid,String fordernumber,String fproductid, int fnewtype,
			String userid, String fcustomerid, String fcusproductid, int famount,
			Date farrivetime,String fdescription) throws Exception {
		ArrayList<Saleorder> solist = new ArrayList<Saleorder>();
		// TODO Auto-generated method stub
		if(orderid.equals("")){
			orderid = saleOrderDao.CreateUUid();
		}
		
		if(fordernumber.equals("")){
			fordernumber = saleOrderDao.getFnumber("t_ord_saleorder", "Z",4);
		}
		
		if(fnewtype!=2 && fnewtype!=4){
			Saleorder soinfo = new Saleorder();
			String orderEntryid = saleOrderDao.CreateUUid();
			soinfo.setFid(orderEntryid);
			soinfo.setForderid(orderid);
			soinfo.setFcreatorid(userid);
			soinfo.setFcreatetime(new Date());
			soinfo.setFnumber(fordernumber);
			soinfo.setFproductdefid(fproductid);
			soinfo.setFentryProductType(0);
			
//			pinfo.setFordernumber(fordernumber+"-1");
//			pinfo.setForderentryid(orderEntryid);
			soinfo.setFtype(0);
			soinfo.setFlastupdatetime(new Date());
			soinfo.setFlastupdateuserid(userid);
			soinfo.setFamount(famount);
			soinfo.setFcustomerid(fcustomerid);
			soinfo.setFcustproduct(fcusproductid);
			soinfo.setFarrivetime(farrivetime);
			soinfo.setFbizdate(new Date());
			soinfo.setFaudited(1);
			soinfo.setFauditorid(userid);
			soinfo.setFaudittime(new Date());
			soinfo.setFamountrate(1);
			
			soinfo.setFassemble(0);
			soinfo.setFiscombinecrosssubs(0);
			
			soinfo.setFordertype(1);
			soinfo.setFseq(1);
			soinfo.setFimportEas(0);
			soinfo.setFallot(0);
			soinfo.setFdescription(fdescription);
			solist.add(soinfo);
//			HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
//			if (params.get("success") == Boolean.FALSE) {
//				throw new DJException("保存失败！");
//			}
			
		}else{
			//套装
			// 一次性获取所有级次的：套装+子产品
			// 再按顺序加载即可
			String productinfo = "select 1 from t_pdt_productdefproducts p where p.fparentid in (select fproductid from t_pdt_productdefproducts where fparentid = '"+fproductid+"')";
			if(saleOrderDao.QueryBySql(productinfo).size()>0){
				throw new DJException("保存失败,套装产品不能超过3级！");
			}
			List list = getAllProductSuit(fproductid);
			HashMap subInfo = null;
			String orderentryid = "";
			for (int k = 0; k < list.size(); k++)
			{
				subInfo = (HashMap) list.get(k);
				
				Saleorder soinfo = new Saleorder();
				
				if(subInfo.get("FAssemble")!=null && subInfo.get("FAssemble").toString().equals("1")){
					soinfo.setFassemble(1);
				}else{
					soinfo.setFassemble(0);
				}
				
				if(subInfo.get("fiscombinecrosssubs")!=null && subInfo.get("fiscombinecrosssubs").toString().equals("1")){
					soinfo.setFiscombinecrosssubs(1);
				}else{
					soinfo.setFiscombinecrosssubs(0);
				}
				
				if(k==0){
					soinfo.setFamount(famount);
					soinfo.setFproductdefid(fproductid);
					soinfo.setFsuitProductId(fproductid);
//					pinfo.setFordernumber(fordernumber+"-1");
//					pinfo.setForderentryid(subInfo.get("orderEntryID").toString());
					orderentryid = subInfo.get("orderEntryID").toString();
					
				}else{
					
					soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
					soinfo.setFamount(famount * new Integer(subInfo.get("amountRate").toString()));
					soinfo.setFproductdefid(subInfo.get("fid").toString());
					
				}
				soinfo.setFtype(0);
				soinfo.setFordertype(2);
				soinfo.setFentryProductType(new Integer(subInfo.get("entryProductType").toString()));
				soinfo.setFid(subInfo.get("orderEntryID").toString());
				soinfo.setFseq((k+1));
				soinfo.setFimportEas(0);
				soinfo.setFamountrate(new Integer(subInfo.get("amountRate").toString()));
				soinfo.setForderid(orderid);
				soinfo.setFcreatorid(userid);
				soinfo.setFcreatetime(new Date());
				soinfo.setFlastupdatetime(new Date());
				soinfo.setFlastupdateuserid(userid);
				soinfo.setFnumber(fordernumber);
				soinfo.setFcustomerid(fcustomerid);
				soinfo.setFcustproduct(fcusproductid);
				soinfo.setFarrivetime(farrivetime);
				soinfo.setFbizdate(new Date());
				soinfo.setFaudited(1);
				soinfo.setFauditorid(userid);
				soinfo.setFaudittime(new Date());
				soinfo.setFallot(0);
				soinfo.setFdescription(fdescription);
				solist.add(soinfo);
//				HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
//				if (params.get("success") == Boolean.FALSE) {
//					throw new DJException("保存失败！");
//				}
			}
		}
		saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);//生成生产订单,（单个制造商则直接生成制造商订单与库存记录）
	}

	/**
	 * 获取多级套装+子件，并且对“分录的产品类型”赋值
	 */
	protected List getAllProductSuit(String fproductid) throws Exception
	{
//		ProductDefCollection productCols = new ProductDefCollection();
		List list = new ArrayList();
//		StringBuffer oql = new StringBuffer("select *,Products.*,Products.product.* where id ='").append(id).append("'");
//		ProductDefInfo productInfo = getProductDefInfo(oql.toString());
//		stmt = conn.prepareStatement("select fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'");
//		ResultSet productrs = stmt.executeQuery();
		String sql = "select fnewtype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'";
		List<HashMap<String,Object>> pdlist= DeliversDao.QueryBySql(sql);
		if(pdlist.size()>0){
			HashMap productrs = pdlist.get(0);
			HashMap productInfo = new HashMap();
			productInfo.put("fid",fproductid);
			productInfo.put("amountRate",new Integer(1));
			productInfo.put("entryProductType","1");
			productInfo.put("ParentOrderEntryId",null);
			if(productrs.get("fnewtype")!=null){
				productInfo.put("fnewtype", productrs.get("fnewtype").toString());
			}else{
				productInfo.put("fnewtype", "0");
			}
			
			if(productrs.get("FCombination")!=null){
				productInfo.put("FCombination", productrs.get("FCombination").toString());
			}else{
				productInfo.put("FCombination", "0");
			}
			
			productInfo.put("FAssemble", productrs.get("FAssemble").toString());
			productInfo.put("fiscombinecrosssubs", new Integer(0));
			getProductSuit(productInfo,list,null);
		}
			
		return list;
	}
	
	private void getProductSuit(HashMap productInfo,List list,String parentEntryId) throws Exception
	{
		boolean isSuit = (productInfo.get("fnewtype").equals("2") || productInfo.get("fnewtype").equals("4"));
		String orderEntryID = saleOrderDao.CreateUUid();
		productInfo.put("orderEntryID",orderEntryID);
		if(!isSuit){
			//如果非套装，自己的分录ID，父分录Id取传入的参数，直接放入Collection
//			BOSUuid orderEntryID = BOSUuid.create(new SaleOrderEntryInfo().getBOSType());
			productInfo.put("ParentOrderEntryId",parentEntryId);
			list.add(productInfo);
		}
		else{
			//如果是套装，自己的分录Id，父分录Id，总套为null，非总套取参数，先把自己放入Collection，再循环递归调用自己的子件

			//如果不是首次进入递归，套装的“分录的产品类型”== 非总套
			if(productInfo.get("entryProductType").toString().equals("1")){
				//nothing
			}
			else{
				productInfo.put("entryProductType","2");
				productInfo.put("ParentOrderEntryId",parentEntryId);
			}
			
			list.add(productInfo);
			
			//子件“分录的产品类型”
			String subEntryProductType = "";
			boolean isassemble = false;
			if(productInfo.get("FCombination")!=null && productInfo.get("FCombination").toString().equals("1")){
				//preSuitProductType = 1;
				subEntryProductType = "7";	//合并下料子件
			}
			else if(productInfo.get("FAssemble")!=null && productInfo.get("FAssemble").toString().equals("1")){
				//preSuitProductType = 2;
				subEntryProductType = "6";	//组装套装子件
				isassemble = true;
			}
			else {
				//preSuitProductType = 0;
				subEntryProductType = "5";	//普通套装子件
			}
			
//			ProductDefProductCollection subCols =  productInfo.getProducts();
//			stmt = conn.prepareStatement("select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'");
//			ResultSet productrs = stmt.executeQuery();
			String sql = "select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'";
			List<HashMap<String,Object>> pdlist= DeliversDao.QueryBySql(sql);
			for(int i=0;i<pdlist.size();i++){
				HashMap productrs = pdlist.get(i);

//				stmt = conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'");
//				ResultSet subproductrs = stmt.executeQuery();
				sql = "select fid,fnewtype,FCombination,FAssemble,fiscombinecrosssubs from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'";
				List<HashMap<String,Object>> pdslist= DeliversDao.QueryBySql(sql);
				if(pdslist.size()>0){
					HashMap subproductrs = pdslist.get(0);
					HashMap subInfo = new HashMap();
					subInfo.put("fid",subproductrs.get("fid"));
					subInfo.put("amountRate",new Integer(productrs.get("FAmount").toString()) * new Integer(productInfo.get("amountRate").toString()));
					if(subproductrs.get("fnewtype")!=null){
						subInfo.put("fnewtype", subproductrs.get("fnewtype").toString());
					}else{
						subInfo.put("fnewtype", "0");
					}
					
					if(subproductrs.get("FCombination")!=null){
						subInfo.put("FCombination", subproductrs.get("FCombination").toString());
					}else{
						subInfo.put("FCombination", "0");
					}
					
					subInfo.put("FAssemble", subproductrs.get("FAssemble").toString());
					subInfo.put("fiscombinecrosssubs", subproductrs.get("fiscombinecrosssubs").toString());
					//子件的“分录的产品类型”
					subInfo.put("entryProductType",subEntryProductType);
					if(isassemble){
						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
					}else{
						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
					}
					
					getProductSuit(subInfo,list,orderEntryID);
				}
			
			}
//			while (productrs.next())
//			{
//				stmt = conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.getString("FProductID")+"'");
//				ResultSet subproductrs = stmt.executeQuery();
//				if (subproductrs.next())
//				{
//					HashMap subInfo = new HashMap();
//					subInfo.put("fid",subproductrs.getString("fid"));
//					subInfo.put("amountRate",productrs.getInt("FAmount") * new Integer(productInfo.get("amountRate").toString()));
//					subInfo.put("fnewtype", subproductrs.getString("fnewtype"));
//					subInfo.put("ftype", subproductrs.getString("ftype"));
//					subInfo.put("FCombination", subproductrs.getString("FCombination"));
//					subInfo.put("FAssemble", subproductrs.getString("FAssemble"));
//					//子件的“分录的产品类型”
//					subInfo.put("entryProductType",subEntryProductType);
//					if(isassemble){
//						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
//					}else{
//						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
//					}
//					
//					getProductSuit(conn,stmt,subInfo,list,orderEntryID);
//				}
//			}
			
		}
	}

	@RequestMapping(value = "/deleteSaleOrdersNew")
	public String deleteSaleOrdersNew(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			
			List affiredReuslt =null;
			String sql = "select  distinct QUOTE(forderid) forderid,fnumber from t_ord_saleorder where fid in "
					+ fidcls;
			List<HashMap<String, Object>> list = saleOrderDao.QueryBySql(sql);
			String forderidcls = "";
			for (int i = 0; i < list.size(); i++) {
				sql = " select fid from  t_ord_productplan where faffirmed=1 and forderid ="+list.get(i).get("forderid");
				 affiredReuslt = saleOrderDao.QueryBySql(sql);
				if (affiredReuslt.size() > 0) {
					throw new DJException("订单编号:"+list.get(i).get("fnumber")+"的生产计划，已经确认不能删除");
				}
				
				sql = "select 1 from t_ord_productplan where fschemedesignid<>'' and forderid ="+list.get(i).get("forderid");
				affiredReuslt = saleOrderDao.QueryBySql(sql);
				if(affiredReuslt.size()>0){
					throw new DJException("订单编号:"+list.get(i).get("fnumber")+"该制造商订单由方案设计下单生成，不能直接删除，请在方案设计取消下单！");
				}
				sql="select 1 from t_ord_deliverorder where fsaleorderid  ="+list.get(i).get("forderid");//订单类型会赋值
				affiredReuslt = saleOrderDao.QueryBySql(sql);
				if(affiredReuslt.size()>0){
					throw new DJException("订单编号:"+list.get(i).get("fnumber")+"对应的制造商订单已被分配到配送信息中，不能直接删除,请在要货管理中取消分配!");
				}
				if(i==0){ forderidcls=(String)list.get(i).get("forderid");}
				else
				{
					forderidcls+=","+(String)list.get(i).get("forderid");
				}
			}
			if (forderidcls.length() == 0) {
				forderidcls = "''";
				throw new DJException("请选择要删除的记录");
			}
//			 sql = " select fid from  t_ord_productplan where faffirmed=1 and forderid in ( "+ forderidcls + ")";
//			 affiredReuslt = saleOrderDao.QueryBySql(sql);
//			if (affiredReuslt.size() > 0) {
//				throw new DJException("该订单生产的生产计划，已经确认不能删除");
//			}
	
			saleOrderDao.ExecDeleteSaleOrdersNew(forderidcls);
			result = JsonUtil.result(true, "删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	
	@RequestMapping(value = "/deleteSaleOrders")
	public String deleteSaleOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String sql = "select forderid from t_ord_saleorder where fid in "
					+ fidcls;
			List<HashMap<String, Object>> list = saleOrderDao.QueryBySql(sql);
			String forderidcls = "";
			HashMap ordermap = new HashMap();
			for (int i = 0; i < list.size(); i++) {
				HashMap o = list.get(i);
				if (o.get("forderid") != null) {
					if (ordermap.size() == 0) {
						forderidcls = "'" + o.get("forderid").toString() + "'";
					} else if (ordermap.size() > 0
							&& !ordermap.containsKey(o.get("forderid")
									.toString())) {
						forderidcls += ",'" + o.get("forderid").toString()
								+ "'";
					}
					ordermap.put(o.get("forderid").toString(), null);
				}
			}
			if (forderidcls.length() == 0) {
				forderidcls = "''";
			}
			String hql = "Delete FROM Saleorder where faudited=0 and forderid in ("
					+ forderidcls + ")";
//			saleOrderDao.ExecByHql(hql);
			// 删除同时修改要货管理的下单状态为否;
//			sql = "SET SQL_SAFE_UPDATES=0 ";
//			saleOrderDao.ExecBySql(sql);
			sql = "UPDATE `t_ord_delivers` SET `fordered`='0',`fordermanid`=null,`fordertime`=null,`fsaleorderid`=null,`fordernumber`=null,`forderentryid`=null WHERE `fsaleorderid` in ("
					+ forderidcls + ")";
//			saleOrderDao.ExecBySql(sql);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("deletesaleorder", hql);
			params.put("updatedelivers", sql);
			params.put("delivers", "select fid from t_ord_delivers where fsaleorderid in (" + forderidcls + ")");
			params.put("mystock", "update mystock set fstate=0,fordered=0,fordernumber=null,fordertime=null,fordermanid =null,forderentryid=null,fsaleorderid=null where fsaleorderid in ("+forderidcls+")");
			saleOrderDao.ExecDeleteSaleOrders(params);
			
			result = JsonUtil.result(true, "删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping(value = "/GetSaleOrders")
	public String GetSaleOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select d.fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,fentryProductType,fstockoutqty,fstockinqty,fstoreqty,faffirmed,d.fassemble,d.fiscombinecrosssubs,d.fallot,ifnull(d.fallottime,'') fallottime,ifnull(u4.fname,'') as fallotor FROM (select * from t_ord_saleorder  order by fnumber ,fseq asc) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid "
					+ " left join t_sys_user u4 on u4.fid=d.fallotorid "
					+ "where 1=1 "
					+ saleOrderDao.QueryFilterByUser(request, "c.fid", "s.fid");
			ListResult result = saleOrderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/saletoexcel")
	public String producttoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select d.fid  ,d.fnumber as 订单编号 ,ifnull(c.fname,'') as  客户名称 ,p.fname as 客户产品名称,ifnull(f.fname,'') as 产品名称,ifnull(s.fname,'') as 制造商名称 ,p.fspec as 规格,d.farrivetime as 交期,d.fbizdate as 业务时间,d.famount  as 数量,u2.fname as  修改人, d.flastupdatetime as 修改时间 ,d.fcreatetime as 创建时间  ,u1.fname as 创建人,d.faudited 是否审核,ifnull(u3.fname,'') 审核人,ifnull(d.faudittime,'') 审核时间,  ifnull(d.fordertype,'') as 订单类型,ifnull(d.fseq,'') as 分录   FROM (select * from t_ord_saleorder  order by fnumber ,fseq asc) d  left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid where 1=1 "
					+ saleOrderDao.QueryFilterByUser(request, "c.fid", "s.fid");

			ListResult result = saleOrderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/GetSaleOrderInfo")
	public String GetProductInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fdescription,d.fid,d.fnumber,d.fcustomerid as fcustomerid_fid,c.fname as fcustomerid_fname,ifnull(d.fcustproduct,'') as fcustproduct_fid,ifnull(p.fname,'') as fcustproduct_fname, f.fid as fproductdefid_fid,ifnull(f.fname,'') as fproductdefid_fname,ifnull(s.fname,'') as fsuppliername,"
					+ " d.farrivetime,d.fbizdate,d.famount,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime FROM t_ord_saleorder d left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct "
					+ " left join t_sys_user u3 on u3.fid=d.fauditorid  left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid "
					+ " where d.fid='" + request.getParameter("fid") + "'";
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	private String getFnumber() {
		String fnumber;
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String nowString = df.format(nowdate);
		String sql = "select fnumber from t_ord_saleorder  where fnumber like :datenow"
				+ " order by fnumber desc limit 1";
		params p = new params();
		p.put("datenow", nowString + "%");
		List<HashMap<String, Object>> sList = saleOrderDao.QueryBySql(sql, p);
		if (sList.size() > 0) {

			String value = (String) sList.get(0).get("fnumber");
			int num = Integer.valueOf(value.substring(8)) + 1;
			String value2 = num + "";
			fnumber = nowString + "000".substring(value2.length()) + num;
			System.out.println(fnumber);

		} else {
			fnumber = nowString + "001";
		}
		return fnumber;
	}

	@RequestMapping(value = "/auditOrder")
	public String auditOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		// Saleorder pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String orderids = request.getParameter("fidcls");
			orderids="('"+orderids.replace(",","','")+"')";
			String sql = "select 1 from t_ord_saleorder where faudited=0 and forderid in "
					+ orderids;
			List<HashMap<String, Object>> data = saleOrderDao.QueryBySql(sql);
			if (data.size() > 0) {
				sql = "update t_ord_saleorder set faudited=1,fauditorid='"
						+ userid
						+ "',faudittime=now() where faudited=0 and forderid in "
						+ orderids;
				saleOrderDao.ExecBySql(sql);
			} else {
				throw new DJException("没有需要审核的订单,无需审核！");
			}
			// }else {
			// throw new DJException("未保存不能审核！");
			// }
			result = JsonUtil.result(true, "审核成功！", "", "");
			// HashMap<String, Object> params = saleOrderDao.ExecSave(pinfo);
			// if (params.get("success") == Boolean.TRUE) {
			// result = JsonUtil.result(true,"审核成功!", "", "");
			// } else {
			// result = JsonUtil.result(false,"审核失败!", "", "");
			// }
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping(value = "/unauditOrder")
	public String unauditOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		// Saleorder pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			// String fid = request.getParameter("fid");
			String orderids = request.getParameter("fidcls");
			orderids="('"+orderids.replace(",","','")+"')";
			String sql = "select 1 from t_ord_saleorder where fallot = 1 and forderid in "
					+ orderids;
			List<HashMap<String, Object>> data = saleOrderDao.QueryBySql(sql);
			if (data.size() > 0) {
				throw new DJException("不能选择已分配的订单反审核！");
			}
			// if (fid != null && !"".equals(fid)) {
			// pinfo = saleOrderDao.Query(fid);
			// if(pinfo.getFaudited()==0){
			// throw new DJException("未审核无需反审核！");
			// }else if(pinfo.getFallot()==1)
			// {
			// throw new DJException("已分配不能反审核！");
			// }else if(pinfo.getFimportEas()==1){
			// throw new DJException("已导入EAS的订单不能反审核！");
			// }
			else {
				sql = "update t_ord_saleorder set faudited=0,fauditorid=null"
						+ ",faudittime=null where faudited=1 and forderid in "
						+ orderids;
				saleOrderDao.ExecBySql(sql);
				// pinfo.setFid(fid);
				// pinfo.setFauditorid(null);
				// pinfo.setFaudited(0);
				// pinfo.setFaudittime(null);
			}
			// } else {
			// throw new DJException("未保存不能反审核！");
			// }

			result = JsonUtil.result(true, "反审核成功！", "", "");
			// HashMap<String, Object> params = saleOrderDao.ExecSave(pinfo);
			// if (params.get("success") == Boolean.TRUE) {
			// result = JsonUtil.result(true,"反审核成功!", "", "");
			// } else {
			// result = JsonUtil.result(false,"反审核失败!", "", "");
			// }
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	/**
	 * 分配 --根据产品ID 更新对应的制造商ID
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/AssageSupplier")
	public String AssageSupplier(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		reponse.setCharacterEncoding("utf-8");
		try {
			saleOrderDao.ExecAssageSupplier(request);
			reponse.getWriter()
			.write(JsonUtil.result(true, "执行成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	

	/**
	 * 
	 * 获取采购单价和和总的采购价
	 * @param famount
	 *            数量
	 * @param fproductdefid
	 *            产品id
	 * @param supplierid 
	 * @return
	 * 无效时返回null值
	 * 
	 * @date 2013-12-5 下午1:05:52 (ZJZ)
	 */
	private BigDecimal[] getPurchasePrices(Integer famount, String fproductdefid, String supplierid) {
		// TODO Auto-generated method stub

		

		BigDecimal perpurchasePrice = getTheBestPrice(famount, fproductdefid, supplierid);

		if (perpurchasePrice == null) {
			
			return null;
			
		}
		
		BigDecimal purchasePrice = perpurchasePrice.multiply(new BigDecimal(famount));

		return new BigDecimal[] { perpurchasePrice,
				purchasePrice};
	}

	/**
	 * 获得最合适的单价
	 * 
	 * @param famount
	 * @param fproductdefid
	 * @param supplierid 
	 * @return
	 * 
	 * @date 2013-12-5 下午2:25:28 (ZJZ)
	 */
	private BigDecimal getTheBestPrice(double famount, String fproductdefid, String supplierid) {
		// TODO Auto-generated method stub

		// 条件：有效，优先制造商，日期，数量限制；最多一条
		String sql = "SELECT pv.fid, pv.fprice as pvfprice FROM purchaseprice_view pv left join t_bal_purchaseprice tbp on pv.FPARENTID = tbp.fid where pv.FISEFFECT = 1 and (pv.FsupplierID IS null or pv.FsupplierID = '%s') and '%s' >= pv.FSTARTDATE and '%s' <= pv.FENDDATE and tbp.FPRODUCTID = '%s' and %f >= FLOWERLIMIT and %f <= FUPPERLIMIT  limit 1  ";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String time = sdf.format(new Date()); 

		float ft = (float)(famount);
		
		sql = String.format(sql, supplierid, time, time, fproductdefid, ft, ft);	

		List<HashMap<String, Object>> bestPrices = saleOrderDao.QueryBySql(sql);

		if (bestPrices.size() == 0) {

			return null;

		}

		// sql = " select * from t_bal_purchasesupplierprice ";
		//
		// if (saleOrderDao
		// .QueryBySql(sql).)
		// 
		BigDecimal t = (BigDecimal) bestPrices.get(0).get("pvfprice");
		// String id = (String) bestPrices.get(0).get("fid");
		//
		// sql = "  ";

		return t; 
	}

	/**
	 * delivers出库;
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/orderOut")
	public String orderOut(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			outWarehouseDao.saveOutWarehouse(request);
			reponse.getWriter().write(JsonUtil.result(true, "订单出库成功", "", ""));
		} catch (Exception e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, "出库失败：" + e.getMessage(), "", ""));
		}
		return null;
		// reponse.getWriter().write(JsonUtil.result(false,
		// "出库失败："+e.getMessage(), "", ""));
		// return null;
		// }
		// reponse.getWriter().write(JsonUtil.result(true, "订单出库成功", "", ""));

		// reponse.setCharacterEncoding("utf-8");
		// String result = "";
		// String userID = ((Useronline)
		// request.getSession().getAttribute("Useronline")).getFuserid();
		// String FWarehouseID = request.getParameter("FWarehouseID");
		// String FWarehouseSiteID = request.getParameter("FWarehouseSiteID");
		// String fproductplanid = request.getParameter("fplanid");
		// //String fproductid = request.getParameter("fproductid");
		// BigDecimal famount = new BigDecimal(request.getParameter("famount"));
		//
		// // int fentryProductType = new
		// Integer(request.getParameter("fentryProductType"));
		// int fentryProductType = 0;
		// if(request.getParameter("fentryProductType")!=null &&
		// !request.getParameter("fentryProductType").equals("")){
		// fentryProductType = new
		// Integer(request.getParameter("fentryProductType"));
		// }
		// int fassemble = 0;
		// if(request.getParameter("fassemble")!=null &&
		// !request.getParameter("fassemble").equals("") &&
		// !request.getParameter("fassemble").equals("null")){
		// fassemble = new Integer(request.getParameter("fassemble"));
		// }
		// int fiscombinecrosssubs = 0;
		// if(request.getParameter("fiscombinecrosssubs")!=null &&
		// !request.getParameter("fiscombinecrosssubs").equals("") &&
		// !request.getParameter("fiscombinecrosssubs").equals("null")){
		// fiscombinecrosssubs = new
		// Integer(request.getParameter("fiscombinecrosssubs"));
		// }
		// try{
		// ProductPlan pplaninfo = productPlanDao.Query(fproductplanid);
		// String fordertype = pplaninfo.getFordertype().toString();
		// String forderid = pplaninfo.getForderid();
		// String forderEntryid = pplaninfo.getFparentorderid();
		// String fproductid =pplaninfo.getFproductdefid();
		// if(pplaninfo.getFaffirmed()==null || pplaninfo.getFaffirmed()==0){
		// throw new DJException("制造商未确认不能出库！");
		// }
		// //套装订单可以出库的产品类型：主套,组装套,普通套子件,非组装且交叉合并套子件的合并套子件;
		// // if(((fordertype.equals("2") || fordertype.equals("4")) &&
		// fentryProductType==1) ||
		// // ((fordertype.equals("2") || fordertype.equals("4")) &&
		// fassemble==1) || fentryProductType==5 ||
		// // (fentryProductType==7 && fiscombinecrosssubs==0))
		// if(fentryProductType!=0)
		// {
		// String sql =
		// "SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and fproductplanID='"+fproductplanid+"'";
		// List<HashMap<String,Object>> balancelist=
		// DeliversDao.QueryBySql(sql);
		//
		// if (balancelist.size()<=0) {
		// throw new DJException("订单没有库存不能出库！");
		// }else{
		// HashMap balanceinfo = balancelist.get(0);
		// if(balanceinfo.get("fbalanceqty")!=null){
		// BigDecimal orderBalance = new
		// BigDecimal(balanceinfo.get("fbalanceqty").toString());
		// if(famount.compareTo(orderBalance)>0){
		// throw new DJException("订单库存不足，请订单库存盘存后再出库！");
		// }
		// }else{
		// throw new DJException("订单没有库存不能出库！");
		// }
		// }
		//
		// //主套出库则要去扣除每个子件数量;
		// if((fordertype.equals("2") || fordertype.equals("4")) &&
		// fentryProductType==1){
		// // if (balancelist.size()<=0) {
		// // throw new DJException("订单没有库存不能出库！");
		// // }else{
		// // HashMap balanceinfo = balancelist.get(0);
		// // if(balanceinfo.get("fbalanceqty")!=null){
		// // BigDecimal orderBalance = new
		// BigDecimal(balanceinfo.get("fbalanceqty").toString());
		// // if(famount.compareTo(orderBalance)>0){
		// // throw new DJException("订单库存不足，请订单库存盘存后再出库！");
		// // }else{
		// List<HashMap<String,Object>> orderlist=
		// DeliversDao.QueryBySql("SELECT fid,fproductdefid,famountrate,fentryProductType,fassemble,fiscombinecrosssubs,fordertype FROM t_ord_saleorder where forderid='"+forderid+"' order by fseq");
		// BigDecimal minQty = new BigDecimal(999999999); //套装库存;
		// String suitOrderentryID = ""; //套装主分录;
		// String suitPdtid = ""; //套装主套产品;
		// BigDecimal amoutrate = new BigDecimal(1); //套装分录系数;
		// BigDecimal orderQty = new BigDecimal(0);
		// for (int l=0;l<orderlist.size();l++) {
		// HashMap orderinfo = orderlist.get(l);
		// fentryProductType = new
		// Integer(orderinfo.get("fentryProductType").toString());
		// fassemble = new Integer(orderinfo.get("fassemble").toString());
		// fiscombinecrosssubs = new
		// Integer(orderinfo.get("fiscombinecrosssubs").toString());
		// fordertype = orderinfo.get("fordertype").toString();
		// int ordertype = new Integer(fordertype);
		//
		// orderQty = famount;
		// //出主套需扣除套装订单有库存产品类型：主套,组装套,普通套子件,非组装且交叉合并套子件的合并套子件;
		// if(((ordertype == 2 || ordertype == 4 ) && fentryProductType==1) ||
		// ((ordertype == 2 || ordertype == 4 ) && fassemble==1) ||
		// fentryProductType==5 || (fentryProductType==7 &&
		// fiscombinecrosssubs==0)){
		// fproductid = orderinfo.get("fproductdefid").toString();
		// forderEntryid = orderinfo.get("fid").toString();
		// amoutrate = new BigDecimal(orderinfo.get("famountrate").toString());
		// orderQty = orderQty.multiply(amoutrate);
		//
		// sql =
		// "SELECT fid FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and fproductplanID='"+fproductplanid+"'";
		// List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
		// outbalance(sblist,orderQty.intValue());
		// sql =
		// "update t_ord_saleorder set fstoreqty = fstoreqty - "+orderQty+",fstockoutqty = fstockoutqty + "+orderQty+" where fid='"+forderEntryid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// sql =
		// "update t_ord_productplan set fstoreqty = fstoreqty - "+orderQty+",fstockoutqty = fstockoutqty + "+orderQty+" where fid='"+fproductplanid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// }
		// }
		// // }
		// // }else{
		// // throw new DJException("订单没有库存不能出库！");
		// // }
		// // }
		// }else{
		// // if (balancelist.size()<=0) {
		// // throw new DJException("订单没有库存不能出库！");
		// // }else{
		// // HashMap balanceinfo = balancelist.get(0);
		// // if(balanceinfo.get("fbalanceqty")!=null){
		// // BigDecimal orderBalance = new
		// BigDecimal(balanceinfo.get("fbalanceqty").toString());
		// // if(famount.compareTo(orderBalance)>0){
		// // throw new DJException("订单库存不足，请订单库存盘存后再出库！");
		// // }else{
		// sql =
		// "SELECT fid FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and fproductplanID='"+fproductplanid+"'";
		// List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
		// outbalance(sblist,famount.intValue());
		// sql =
		// "update t_ord_saleorder set fstoreqty = fstoreqty - "+famount+",fstockoutqty = fstockoutqty + "+famount+" where fid='"+forderEntryid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// sql =
		// "update t_ord_productplan set fstoreqty = fstoreqty - "+famount+",fstockoutqty = fstockoutqty + "+famount+" where fid='"+fproductplanid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// // }
		// // }else{
		// // throw new DJException("订单没有库存不能出库！");
		// // }
		// // }
		// }
		//
		// //计算套装库存;
		// if(fentryProductType != 1){
		// calculateSuitQty(forderid,FWarehouseID,FWarehouseSiteID,userID);
		// }
		// // }else if(!fordertype.equals("2") && !fordertype.equals("4") &&
		// fentryProductType!=5 && fentryProductType!=6 &&
		// fentryProductType!=7){
		// }else{
		// //普通订单;
		// String sql =
		// "SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and fproductplanID='"+fproductplanid+"'";
		// List<HashMap<String,Object>> balancelist=
		// DeliversDao.QueryBySql(sql);
		// if (balancelist.size()<=0) {
		// throw new DJException("订单没有库存不能出库！");
		// }else{
		// HashMap balanceinfo = balancelist.get(0);
		// if(balanceinfo.get("fbalanceqty")!=null){
		// BigDecimal orderBalance = new
		// BigDecimal(balanceinfo.get("fbalanceqty").toString());
		// if(famount.compareTo(orderBalance)>0){
		// throw new DJException("订单库存不足，请订单库存盘存后再出库！");
		// }else{
		// sql =
		// "SELECT fid FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and fproductplanID='"+fproductplanid+"'";
		// List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
		// outbalance(sblist,famount.intValue());
		// sql =
		// "update t_ord_saleorder set fstoreqty = fstoreqty - "+famount+",fstockoutqty = ifnull(fstockoutqty,0) + "+famount+" where fid='"+forderEntryid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// sql =
		// "update t_ord_productplan set fstoreqty = fstoreqty - "+famount+",fstockoutqty = ifnull(fstockoutqty,0) + "+famount+" where fid='"+fproductplanid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// }
		// }else{
		// throw new DJException("订单没有库存不能出库！");
		// }
		// }
		// }
		// // else{
		// // throw new DJException("该订单分录不能出库！");
		// // }
		// //出库明细;
		// Outwarehouse Outwarehouseinfo = new Outwarehouse();
		// Outwarehouseinfo.setFnumber(outWarehouseDao.getFnumber("t_inv_outwarehouse",
		// "U",4));
		// Outwarehouseinfo.setForderentryid(forderEntryid);
		// Outwarehouseinfo.setFid(outWarehouseDao.CreateUUid());
		// Outwarehouseinfo.setFproductplanid(fproductplanid);
		// Outwarehouseinfo.setFcreatorid(userID);
		// Outwarehouseinfo.setFcreatetime(new Date());
		// Outwarehouseinfo.setFlastupdatetime(null);
		// Outwarehouseinfo.setFlastupdateuserid(null);
		// Outwarehouseinfo.setFoutqty(famount);
		// Outwarehouseinfo.setFwarehouseid(FWarehouseID);
		// Outwarehouseinfo.setFwarehousesiteid(FWarehouseSiteID);
		// Outwarehouseinfo.setFproductid(fproductid);
		// Outwarehouseinfo.setFremak(null);
		// Outwarehouseinfo.setFsaleorderid(forderid);
		// Outwarehouseinfo.setFaudited(1);
		// Outwarehouseinfo.setFauditorid(userID);
		// Outwarehouseinfo.setFaudittime(new Date());
		// outWarehouseDao.ExecSave(Outwarehouseinfo);
		// }catch(Exception e){
		// reponse.getWriter().write(JsonUtil.result(false,
		// "出库失败："+e.getMessage(), "", ""));
		// return null;
		// }
		// reponse.getWriter().write(JsonUtil.result(true, "订单出库成功", "", ""));
		// return null;
	}

	private void outbalance(List<HashMap<String, Object>> sblist, int famount) {
		// TODO Auto-generated method stub
		int bigRest = famount;
		int bigThisTotalQty = 0;
		for (int i = 0; i < sblist.size(); i++) {
			HashMap sbinfo = sblist.get(i);
			String sbId = sbinfo.get("fid").toString();
			Storebalance sbInfo = storebalanceDao.Query(sbId);

			int bigEachLocationQty = sbInfo.getFbalanceqty();
			if (bigEachLocationQty <= 0) {
				continue;// 库存余额为零，则不从该库位上出库。
			}
			bigRest = bigRest - bigEachLocationQty;
			if (bigRest < 0)// >=0。此时该库位已经可以满足出库要求。
			{
				bigThisTotalQty = bigThisTotalQty + famount;
				sbInfo.setFoutqty(sbInfo.getFoutqty()
						+ (bigEachLocationQty + bigRest));// 出库增加
				sbInfo.setFbalanceqty(bigRest * (-1));
				// isb.update(new ObjectUuidPK(sbId), sbInfo);
				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				// idao.updateBatch(new ObjectUuidPK(sbId), sbInfo);// 更新库存余额表
				// addQtyOfLocation(map,parentKey,locationId,bigIssueQty);
				break;
			} else
			// <0
			{
				bigThisTotalQty = bigThisTotalQty + bigEachLocationQty;
				// sbInfo.setActionType(StoreActionType.SA_OUT);
				sbInfo.setFoutqty(sbInfo.getFoutqty() + bigEachLocationQty);// 出库增加
				sbInfo.setFbalanceqty(0);// 该库位已经没有库存，要从下一个库位上继续出库。
				// isb.update(new ObjectUuidPK(sbId), sbInfo);
				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				// idao.updateBatch(new ObjectUuidPK(sbId), sbInfo);
				// addQtyOfLocation(map,parentKey,locationId,bigEachLocationQty);
			}
		}
	}

	/**
	 * delivers入库;
	 * 
	 * @throws IOException
	 * @throws DJException
	 */
	@RequestMapping(value = "/orderIn")
	public String orderIn(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			productindetailDao.saveproductindetail(request);
			reponse.getWriter().write(JsonUtil.result(true, "订单入库成功", "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "入库失败：" + e.getMessage(), "", ""));
			// throw new DJException(e.getMessage());
		}
		return null;

		//
		// reponse.setCharacterEncoding("utf-8");
		// String result = "";
		// String userID = ((Useronline)
		// request.getSession().getAttribute("Useronline")).getFuserid();
		// String FWarehouseID = request.getParameter("FWarehouseID");
		// String FWarehouseSiteID = request.getParameter("FWarehouseSiteID");
		// //String fproductid = request.getParameter("fproductid");
		//
		// //String forderEntryid = request.getParameter("forderEntryid");
		// String fproductplanid = request.getParameter("fplanid");
		// BigDecimal famount = new BigDecimal(request.getParameter("famount"));
		// int fentryProductType = 0;
		// if(request.getParameter("fentryProductType")!=null &&
		// !request.getParameter("fentryProductType").equals("")){
		// fentryProductType = new
		// Integer(request.getParameter("fentryProductType"));
		// }
		// int fassemble = 0;
		// if(request.getParameter("fassemble")!=null &&
		// !request.getParameter("fassemble").equals("") &&
		// !request.getParameter("fassemble").equals("null")){
		// fassemble = new Integer(request.getParameter("fassemble"));
		// }
		// int fiscombinecrosssubs = 0;
		// if(request.getParameter("fiscombinecrosssubs")!=null &&
		// !request.getParameter("fiscombinecrosssubs").equals("") &&
		// !request.getParameter("fiscombinecrosssubs").equals("null")){
		// fiscombinecrosssubs = new
		// Integer(request.getParameter("fiscombinecrosssubs"));
		// }
		//
		// try{
		// ProductPlan so = productPlanDao.Query(fproductplanid);
		// String forderid = so.getForderid();
		// String fproductid =so.getFproductdefid();
		// String forderEntryid =so.getFparentorderid();
		// if(so.getFaffirmed()==null || so.getFaffirmed()==0){
		// throw new DJException("制造商未确认不能入库！");
		// }
		// // if(famount.compareTo(new
		// BigDecimal(so.getFamount()-so.getFstockinqty()))>0){
		// // throw new DJException("入库数量不能大于订单数量！");
		// // }
		//
		// //套装订单需要入库的产品类型：组装套,普通套子件,非组装且交叉合并套子件的合并套子件;
		// // if(((fordertype.equals("2") || fordertype.equals("4")) &&
		// fassemble==1) || fentryProductType==5 ||
		// // (fentryProductType==7 && fiscombinecrosssubs==0))
		// // if(fentryProductType!=0)
		// // {
		// // Storebalance sbinfo;
		// // String sql =
		// "SELECT fid FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and w.fwarehouseSiteId='"+FWarehouseSiteID+"' and fproductplanID='"+fproductplanid+"'";
		// // List<HashMap<String,Object>> balancelist=
		// DeliversDao.QueryBySql(sql);
		// // if (balancelist.size()<=0) {
		// // sbinfo = new Storebalance();
		// // sbinfo.setFid(storebalanceDao.CreateUUid());
		// // sbinfo.setFproductplanId(fproductplanid);
		// // sbinfo.setFcreatorid(userID);
		// // sbinfo.setFcreatetime(new Date());
		// // sbinfo.setFupdatetime(null);
		// // sbinfo.setFupdateuserid(null);
		// // sbinfo.setFinqty(famount.intValue());
		// // sbinfo.setFoutqty(0);
		// // sbinfo.setFbalanceqty(famount.intValue());
		// // sbinfo.setFwarehouseId(FWarehouseID);
		// // sbinfo.setFwarehouseSiteId(FWarehouseSiteID);
		// // sbinfo.setFproductId(fproductid);
		// // sbinfo.setFdescription(null);
		// // sbinfo.setFsaleorderid(forderid);
		// // sbinfo.setForderentryid(forderEntryid);
		// // storebalanceDao.ExecSave(sbinfo);
		// // }else{
		// // HashMap balanceinfo = balancelist.get(0);
		// // String fid = balanceinfo.get("fid").toString();
		// //// sql =
		// "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty + "+famount+",w.finqty = w.finqty + "+famount+" fupdateuserid = '"+userID+"',fupdatetime = CURRENT_TIMESTAMP where w.fid='"+fid+"' ";
		// // sql =
		// "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty + "+famount+",w.finqty = w.finqty + "+famount+" where w.fid='"+fid+"' ";
		// // saleOrderDao.ExecBySql(sql);
		// // }
		// //
		// // List<HashMap<String,Object>> qtylist=
		// DeliversDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' ");
		// // BigDecimal fstockinqty = new BigDecimal(0);
		// // BigDecimal fstoreqty = new BigDecimal(0);
		// // if (qtylist.size()>0) {
		// // HashMap qtyinfo = qtylist.get(0);
		// // fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
		// // fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
		// // }
		// // sql =
		// "update t_ord_saleorder set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+forderEntryid+"' ";
		// // saleOrderDao.ExecBySql(sql);
		// //
		// // qtylist=
		// DeliversDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and fproductplanID='"+fproductplanid+"'");
		// // fstockinqty = new BigDecimal(0);
		// // fstoreqty = new BigDecimal(0);
		// // if (qtylist.size()>0) {
		// // HashMap qtyinfo = qtylist.get(0);
		// // fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
		// // fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
		// // }
		// // sql =
		// "update t_ord_productplan set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+fproductplanid+"' ";
		// // saleOrderDao.ExecBySql(sql);
		// //
		// // //计算套装库存;
		// // if(fentryProductType != 1){
		// // calculateSuitQty(forderid,FWarehouseID,FWarehouseSiteID,userID);
		// // }
		//
		// // }else if(!fordertype.equals("2") && !fordertype.equals("4") &&
		// fentryProductType!=5 && fentryProductType!=6 &&
		// fentryProductType!=7){
		// // }else{
		// //入库全都按普通订单方法入库;
		// //普通订单;
		// Storebalance sbinfo;
		// String sql =
		// "SELECT fid FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and w.fwarehouseSiteId='"+FWarehouseSiteID+"' and fproductplanID='"+fproductplanid+"'";
		// List<HashMap<String,Object>> balancelist=
		// DeliversDao.QueryBySql(sql);
		// if (balancelist.size()<=0) {
		// sbinfo = new Storebalance();
		// sbinfo.setFid(storebalanceDao.CreateUUid());
		// sbinfo.setFproductplanId(fproductplanid);
		// sbinfo.setFcreatorid(((Useronline)
		// request.getSession().getAttribute("Useronline")).getFuserid());
		// sbinfo.setFcreatetime(new Date());
		// sbinfo.setFupdatetime(null);
		// sbinfo.setFupdateuserid(null);
		// sbinfo.setFinqty(famount.intValue());
		// sbinfo.setFoutqty(0);
		// sbinfo.setFbalanceqty(famount.intValue());
		// sbinfo.setFwarehouseId(FWarehouseID);
		// sbinfo.setFwarehouseSiteId(FWarehouseSiteID);
		// sbinfo.setFproductId(fproductid);
		// sbinfo.setFdescription(null);
		// sbinfo.setFsaleorderid(forderid);
		// sbinfo.setForderentryid(forderEntryid);
		// storebalanceDao.ExecSave(sbinfo);
		// }else{
		// HashMap balanceinfo = balancelist.get(0);
		// String fid = balanceinfo.get("fid").toString();
		// sql =
		// "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty + "+famount+",w.finqty = w.finqty + "+famount+" where w.fid='"+fid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// }
		//
		// List<HashMap<String,Object>> qtylist=
		// DeliversDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' ");
		// BigDecimal fstockinqty = new BigDecimal(0);
		// BigDecimal fstoreqty = new BigDecimal(0);
		// if (qtylist.size()>0) {
		// HashMap qtyinfo = qtylist.get(0);
		// fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
		// fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
		// }
		// sql =
		// "update t_ord_saleorder set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+forderEntryid+"' ";
		// saleOrderDao.ExecBySql(sql);
		//
		// qtylist=
		// DeliversDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and fproductplanID='"+fproductplanid+"'");
		// fstockinqty = new BigDecimal(0);
		// fstoreqty = new BigDecimal(0);
		// if (qtylist.size()>0) {
		// HashMap qtyinfo = qtylist.get(0);
		// fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
		// fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
		// }
		// sql =
		// "update t_ord_productplan set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+",fcloseed=(case when famount>"+fstockinqty+" then 0 else 1 end)   where fid='"+fproductplanid+"' ";
		// saleOrderDao.ExecBySql(sql);
		// // }
		// // else{
		// // throw new DJException("该订单分录不能入库！");
		// // }
		//
		// //人库明细;
		// Productindetail pdtindetailinfo = new Productindetail();
		// pdtindetailinfo.setFnumber(productindetailDao.getFnumber("t_inv_productindetail",
		// "R",4));
		// pdtindetailinfo.setForderentryid(forderEntryid);
		// pdtindetailinfo.setFid(productindetailDao.CreateUUid());
		// pdtindetailinfo.setFproductplanid(fproductplanid);
		// pdtindetailinfo.setFcreatorid(userID);
		// pdtindetailinfo.setFcreatetime(new Date());
		// pdtindetailinfo.setFupdatetime(null);
		// pdtindetailinfo.setFupdateuserid(null);
		// pdtindetailinfo.setFinqty(famount);
		// pdtindetailinfo.setFwarehouseId(FWarehouseID);
		// pdtindetailinfo.setFwarehouseSiteId(FWarehouseSiteID);
		// pdtindetailinfo.setFproductId(fproductid);
		// pdtindetailinfo.setFdescription(null);
		// pdtindetailinfo.setFsaleOrderId(forderid);
		// pdtindetailinfo.setFtype(1);
		// pdtindetailinfo.setFaudited(1);
		// pdtindetailinfo.setFauditorid(userID);
		// pdtindetailinfo.setFaudittime(new Date());
		// productindetailDao.ExecSave(pdtindetailinfo);
		// }catch(Exception e){
		// reponse.getWriter().write(JsonUtil.result(false,
		// "入库失败："+e.getMessage(), "", ""));
		// return null;
		// }
		// reponse.getWriter().write(JsonUtil.result(true, "订单入库成功", "", ""));
		// return null;
	}

	// 计算套装库存;
	private void calculateSuitQty(String forderid, String FWarehouseID,
			String FWarehouseSiteID, String userID) {
		// TODO Auto-generated method stub
		String fproductid = "";
		String forderEntryid = "";
		BigDecimal inqty = new BigDecimal(0);
		String sql = "";
		Storebalance sbinfo;
		List<HashMap<String, Object>> orderlist = DeliversDao
				.QueryBySql("SELECT fid,fproductdefid,famountrate,fentryProductType,fassemble,fiscombinecrosssubs,fordertype FROM t_ord_saleorder where forderid='"
						+ forderid + "' order by fseq");
		BigDecimal minQty = new BigDecimal(999999999); // 套装库存;
		String suitOrderentryID = ""; // 套装主分录;
		String suitPdtid = ""; // 套装主套产品;
		BigDecimal amoutrate = new BigDecimal(1); // 套装分录系数;
		int fentryProductType;
		int fassemble;
		int fiscombinecrosssubs;
		int fordertype;

		for (int l = 0; l < orderlist.size(); l++) {
			HashMap orderinfo = orderlist.get(l);
			if (l == 0) {
				suitOrderentryID = orderinfo.get("fid").toString();
				suitPdtid = orderinfo.get("fproductdefid").toString();
				continue;
			}
			fentryProductType = new Integer(orderinfo.get("fentryProductType")
					.toString());
			fassemble = new Integer(orderinfo.get("fassemble").toString());
			fiscombinecrosssubs = new Integer(orderinfo.get(
					"fiscombinecrosssubs").toString());
			fordertype = new Integer(orderinfo.get("fordertype").toString());

			// 套装订单有入库的产品类型：组装套,普通套子件,非组装且交叉合并套子件的合并套子件;
			// if(((fordertype == 2 || fordertype == 4 ) && fassemble==1) ||
			// fentryProductType==5 || (fentryProductType==7 &&
			// fiscombinecrosssubs==0)){
			fproductid = orderinfo.get("fproductdefid").toString();
			forderEntryid = orderinfo.get("fid").toString();
			amoutrate = new BigDecimal(orderinfo.get("famountrate").toString());
			List<HashMap<String, Object>> fbalancelist = DeliversDao
					.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty FROM t_inv_storebalance w where w.FProductID='"
							+ fproductid
							+ "' and w.fsaleorderid='"
							+ forderid
							+ "' and w.forderentryid='"
							+ forderEntryid
							+ "' and w.fwarehouseId='"
							+ FWarehouseID
							+ "' and w.fwarehouseSiteId='"
							+ FWarehouseSiteID
							+ "' ");
			HashMap fbalanceinfo = fbalancelist.get(0);
			inqty = new BigDecimal(fbalanceinfo.get("fbalanceqty").toString())
					.divide(amoutrate, 0, BigDecimal.ROUND_DOWN);
			if (minQty.compareTo(inqty) > 0) {
				minQty = inqty;
			}
			// }
		}

		// 插入套装订单主分录结存表;
		sql = "SELECT fid FROM t_inv_storebalance w where w.FProductID='"
				+ suitPdtid + "' and w.fsaleorderid='" + forderid
				+ "' and w.forderentryid='" + suitOrderentryID
				+ "' and w.fwarehouseId='" + FWarehouseID
				+ "' and w.fwarehouseSiteId='" + FWarehouseSiteID + "' ";
		List<HashMap<String, Object>> suitbalancelist = DeliversDao
				.QueryBySql(sql);
		if (suitbalancelist.size() <= 0) {
			sbinfo = new Storebalance();
			sbinfo.setFid(storebalanceDao.CreateUUid());
			sbinfo.setFcreatorid(userID);
			sbinfo.setFcreatetime(new Date());
			sbinfo.setFupdatetime(null);
			sbinfo.setFupdateuserid(null);
			sbinfo.setFinqty(minQty.intValue());
			sbinfo.setFoutqty(0);
			sbinfo.setFbalanceqty(minQty.intValue());
			sbinfo.setFwarehouseId(FWarehouseID);
			sbinfo.setFwarehouseSiteId(FWarehouseSiteID);
			sbinfo.setFproductId(suitPdtid);
			sbinfo.setFdescription(null);
			sbinfo.setFsaleorderid(forderid);
			sbinfo.setForderentryid(suitOrderentryID);
			storebalanceDao.ExecSave(sbinfo);
		} else {
			HashMap suitbalanceinfo = suitbalancelist.get(0);
			String suitsbfid = suitbalanceinfo.get("fid").toString();
			sql = "update t_inv_storebalance w set w.fbalanceqty = " + minQty
					+ ",w.finqty =" + minQty + ",fupdateuserid = '" + userID
					+ "',fupdatetime = CURRENT_TIMESTAMP where w.fid='"
					+ suitsbfid + "' ";
			saleOrderDao.ExecBySql(sql);
		}

		List<HashMap<String, Object>> suitOrderlist = DeliversDao
				.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"
						+ suitPdtid
						+ "' and w.fsaleorderid='"
						+ forderid
						+ "' and w.forderentryid='" + suitOrderentryID + "' ");
		if (suitOrderlist.size() > 0) {
			HashMap suitOrderinfo = suitOrderlist.get(0);
			minQty = new BigDecimal(suitOrderinfo.get("fbalanceqty").toString());
			inqty = new BigDecimal(suitOrderinfo.get("finqty").toString());
		}
		sql = "update t_ord_saleorder set fstoreqty = " + minQty
				+ ",fstockinqty = " + inqty + " where fid='" + suitOrderentryID
				+ "' ";
		saleOrderDao.ExecBySql(sql);
	}

	/**
	 * 制造商确认;
	 */
	@RequestMapping(value = "/supplierAffirm")
	public String supplierAffirm(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		synchronized (this.getClass()){
		String result = "";
		String fidcls = request.getParameter("fidcls");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();

		try {
			if (fidcls == null || fidcls.equals("")) {
				throw new DJException("制造商已确认");
			}
			fidcls="'"+fidcls.replace(",","','")+"'";
			///服务端验证是否确认
			String sql = "select 1 from t_ord_productplan where fid in ("+fidcls+") and faffirmed=1";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("制造商已确认");
			}
			 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in("
					+ fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("已关闭订单不能确认！");
			}

 			saleOrderDao.ExecAffirmAndImportEAS(fidcls,userid);
			result = JsonUtil.result(true, "制造商确认成功", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;
		}

	}
	
	/**
	 * 纸板订单确认
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/cardboardSupplierAffirm")
	public String cardboardSupplierAffirm(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			fidcls="'"+fidcls.replace(",","','")+"'";
			///服务端验证是否确认
			/*String sql = "select 1 from t_ord_productplan where fid in ("+fidcls+") and faffirmed=1";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("制造商已确认");
			}*/
			String sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in("
					+ fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("已关闭订单不能确认！");
			}
			
			saleOrderDao.ExecCardboardSupplierAffirm(fidcls,userid);
			result = JsonUtil.result(true, "制造商确认成功!", "", "");
		} catch (DJException e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		response.getWriter().write(result);
		return null;
	}
	
	/**
	 * 制造商取消确认;
	 */
	@RequestMapping(value = "/supplierUnAffirm")
	public String supplierUnAffirm(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");

		try {
			if (fidcls == null || fidcls.equals("")) {
				throw new DJException("制造商未确认");
			}
			fidcls="'"+fidcls.replace(",","','")+"'";
			String sql = "select 1 from t_ord_productplan where fid in ("+fidcls+") and ifnull(faffirmed,0)=0";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("制造商未确认");
			}
			 sql = "SELECT 1 FROM t_ord_productplan where fimportEas=1 and fissync=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已导入接口不能取消确认，请先从接口退回！");
			}
			
			 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已关闭不能取消确认！");
			}
			
			sql = "SELECT fid FROM t_inv_productindetail where fproductplanid in("
					+ fidcls + ")";
			List balancelist = DeliversDao.QueryBySql(sql);
			if (balancelist.size() > 0) {
				throw new DJException("已入库订单不能取消确认！");
			}
			
			sql = "UPDATE t_ord_productplan SET faffirmed='0',fstate=0,faffirmtime=null,faffirmer='',fimportEas=0 WHERE fid in ("
					+ fidcls + ")";
			saleOrderDao.ExecBySql(sql);
			result = JsonUtil.result(true, "取消确认成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}
	
	/**
	 * 纸板订单取消确认
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/cardboardSupplierUnAffirm")
	public String cardboardSupplierUnAffirm(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		
		try {
			if (fidcls == null || fidcls.equals("")) {
				throw new DJException("制造商未确认");
			}
			fidcls="'"+fidcls.replace(",","','")+"'";

			String sql = "select 1 from t_ord_productplan where fid in ("+fidcls+") and ifnull(faffirmed,0)=0";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("制造商未确认");
			}
		    sql = "SELECT 1 FROM t_ord_productplan where fimportEas=1 and fissync=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已导入接口不能取消确认，请先从接口退回！");
			}
			
			 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已关闭不能取消确认！");
			}
			
			sql = "SELECT fid FROM t_inv_productindetail where fproductplanid in("
					+ fidcls + ")";
			List balancelist = DeliversDao.QueryBySql(sql);
			if (balancelist.size() > 0) {
				throw new DJException("已入库订单不能取消确认！");
			}
			saleOrderDao.ExecCardboardSupplierUnAffirm(fidcls);
			result = JsonUtil.result(true, "取消确认成功!", "", "");
		} catch (DJException e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		response.getWriter().write(result);
		return null;	
		
	}

	private String getFnumber(Connection conn, PreparedStatement stmt)
			throws SQLException {
		String fnumber = "";
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String nowString = "I" + df.format(nowdate);
		String sql = "";

		SimpleDateFormat dl = new SimpleDateFormat("yyMMdd");
		nowString = "VMI" + dl.format(nowdate);
		stmt = conn
				.prepareStatement("select max(fnumber) fnumber from t_ord_delivers where fcreatetime in (select max(fcreatetime) from t_ord_delivers ) and fnumber like '"
						+ nowString + "%'");
		ResultSet easDeliver = stmt.executeQuery();
		if (easDeliver.next()) {
			if (easDeliver.getString("fnumber") != null) {
				String value = easDeliver.getString("fnumber");
				int num = Integer.valueOf(value.substring(9)) + 1;
				String value2 = num + "";
				fnumber = nowString + "000".substring(value2.length()) + num;
			} else {
				fnumber = nowString + "001";
			}
		}

		return fnumber;
	}
	
	@RequestMapping(value = "/GetproductplanToIn")
	public String GetproductplanToIn(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		String fid=request.getParameter("fid");
		try {
			String sql=" select p.faffirmed,p.famount,s.fwarehouseid ,ifnull(w1.fname,'') wname,s.fwarehousesiteid,ifnull(w2.fname,'') wsfname ,p.fstockinqty,p.fstoreqty,p.fproductdefid,p.forderid,p.fparentorderid,p.fordertype,p.fentryProductType,p.fassemble,p.fiscombinecrosssubs,ifnull(p.fschemedesignid,'') fschemedesignid from t_ord_productplan  p "+
					" left join t_sys_supplier s on s.fid=p.fsupplierid "+
					" left join t_bd_warehouse w1 on w1.fid=s.fwarehouseid "+
					" left join t_bd_warehousesites w2 on w2.fid=s.fwarehousesiteid  where p.fid='"+fid+"'";
			List<HashMap<String, Object>> result = saleOrderDao.QueryBySql(sql);
		if((Integer)result.get(0).get("faffirmed")==0)
		{
			throw new DJException("制造商未确认不能相关操作");
		}
		if("".equals(result.get(0).get("wname"))||"".equals(result.get(0).get("wsfname")))
		{
			throw new DJException("该制造商没有设置仓库或者库位，请先设置！");
		}
			reponse.getWriter().write(JsonUtil.result(true, "", "",result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping("getFtraitidBySchemeDesignId")
	public String getFtraitidBySchemeDesignId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		if(StringUtils.isEmpty(fid)){
			throw new DJException("方案ID不存在！");
		}
		String sql = "select e.fid,e.fcharacter,e.fentryamount,e1.finqty from t_ord_schemedesignentry e left join t_inv_storebalance e1 on e.fid=e1.ftraitid where fparentid = :fid";
		params p = new params();
		p.put("fid", fid);
		try {
			List<HashMap<String, Object>> list = saleOrderDao.QueryBySql(sql, p);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	@RequestMapping("/getAssginSaleorderInfo")
	public String getAssginSaleorderInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql="select d.fid,d.fnumber,d.famount,d.FPRODUCTDEFID,d.fallot from t_ord_saleorder d where d.fid='"+fid+"'";
			List<HashMap<String,Object>> result= DeliversDao.QueryBySql(sql);
		
			if(result.get(0).get("fallot")!=null&&"1".equals(result.get(0).get("fallot")))
			{
				throw new DJException("分配失败！该要货信息已经分配");
			}
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/saveAssginSaleOrder")
	public String saveAssginSaleOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {

		HashMap<String, Object> params;

		String json = request.getReader().readLine();

		JSONArray jsonA = getJsonArrayByS(json);
		
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			Saleorder sinfo=null;
			String number="";
			int updatecycle=0;
//			HashMap temp = new HashMap();zky
				for (int i = 0; i < jsonA.size(); i++) {
					String fsupplierid=((JSONObject)jsonA.get(i)).getString("fsupplierid");
					if(sinfo==null)
					{
						String Saleorderid=((JSONObject)jsonA.get(i)).getString("Saleorderid");
						sinfo=saleOrderDao.Query(Saleorderid);
						if(sinfo==null){
							throw new DJException("该生产订单ID不存在！");
						}
						if( sinfo.getFallot()!=null && sinfo.getFallot()==1)
						{
							throw new DJException("该要货信息已经分配！");
						}
					}
					Supplier spinfo=SupplierDao.Query(fsupplierid);
					if(spinfo==null){
						throw new DJException("制造商ID不存在！");
					}
					if(!number.contains(spinfo.getFnumber()))
					{
						if(i>0)
						{
							number+=",";
						}
						number+=spinfo.getFnumber();
					}
					String amount = ((JSONObject)jsonA.get(i)).getString("famount");
					if(!DataUtil.positiveIntegerCheck(amount)){
						throw new DJException("制造商数量格式不正确！");
					}
					int famount=Integer.valueOf(amount);
//					createDeliverorder(sinfo, famount,spinfo, userid,spinfo.getFparentorderid(),spinfo.getFnumber());
					if (sinfo.getFaudited() == 0) {
						throw new DJException(sinfo.getFnumber() + "未审核不能分配！");
					}
					if (sinfo.getFallot() == 1) {
						throw new DJException(sinfo.getFnumber() + "已经分配！");
					}
					
					if (sinfo.getFentryProductType() == 1) {
						String sqll1 = "from Saleorder s  where s.fentryProductType!=1 and s.faudited=0 and s.forderid='"
								+ sinfo.getForderid() + "'";
						List auditResult = saleOrderDao.QueryByHql(sqll1);
						if (auditResult.size() > 0) {
							throw new DJException(sinfo.getFnumber()
									+ "分配失败！其子件未审核");
						}
						String sqll = "from Saleorder s  where s.fentryProductType!=1 and s.fallot=1 and s.forderid='"
								+ sinfo.getForderid() + "'";
						List result = saleOrderDao.QueryByHql(sqll);
						if (result.size() > 0) {
							throw new DJException(sinfo.getFnumber()
									+ "分配失败！该子件已经分配，请先取消子件的分配");
						}
						specifyCreateProductPlan(famount,sinfo, userid,sinfo.getFproductdefid(),spinfo, true);
						String executesql = "SET SQL_SAFE_UPDATES=0 ";
						saleOrderDao.ExecBySql(executesql);
						String updatesql = " update t_ord_saleorder set fallot=1 ,fallotorid=:fallotorid,fallottime=now() where fentryProductType!=1 and forderid=:forderid";
						params p = new params();
						p.put("fallotorid", userid);
						// p.put("fallottime", new Date());
						p.put("forderid", sinfo.getForderid());
						saleOrderDao.ExecBySql(updatesql, p);
					} else {
						specifyCreateProductPlan(famount,sinfo, userid,
								sinfo.getFproductdefid(),spinfo, true);
					}
					updatecycle++;
				}
				
				if (updatecycle > 0) {
					sinfo.setFallot(1);
					sinfo.setFallotorid(userid); 
					sinfo.setFallottime(new Date());
					HashMap<String, Object> paramss =saleOrderDao.ExecSave(sinfo);
					reponse.getWriter().write(
					JsonUtil.result(true, "保存成功", "", ""));
					if (paramss.get("success") == Boolean.FALSE) {
						throw new DJException(sinfo.getFnumber() + "分配失败！");
					}
				} else {
					throw new DJException(sinfo.getFnumber() + "分配失败！");
				}
				
				
		

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败!" + e.getMessage(), "", ""));
		}

		return null;
	}

	private void specifyCreateProductPlan(int famount, Saleorder sinfo, String userid,
			String fproductdefid, Supplier spinfo, boolean b) {
		// TODO Auto-generated method stub
		//在这里获得采购价，详见具体方法 
		BigDecimal[] purchaseprices = getPurchasePrices(famount,
				sinfo.getFproductdefid(), spinfo.getFid()); 

		if (purchaseprices == null) {
			//暂时不检查是否有价格   BY CC 2013-12-09
//			if (message == true) {
//				throw new DJException("未找到合适的采购价！");
//			} else {
//				continue;
//			}
			purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
		}

		ProductPlan p = new ProductPlan();
		
		p.setPerpurchaseprice(purchaseprices[0]);
		p.setPurchaseprice(purchaseprices[1]);
		
		p.setFid(saleOrderDao.CreateUUid());
		p.setFnumber(saleOrderDao.getFnumber("t_ord_productplan", "P",
				4));
		p.setFparentorderid(sinfo.getFid());// 设置父订单分录ID
		p.setFseq(sinfo.getFseq());// 设置父订单分录
		p.setFcreatorid(sinfo.getFcreatorid());
		p.setFcreatetime(sinfo.getFcreatetime());
		p.setFlastupdateuserid(sinfo.getFlastupdateuserid());
		p.setFlastupdatetime(sinfo.getFlastupdatetime());
		p.setFcustomerid(sinfo.getFcustomerid());
		p.setFcustproduct(sinfo.getFcustproduct());
		p.setFarrivetime(sinfo.getFarrivetime());
		p.setFbizdate(sinfo.getFbizdate());

		p.setFamount(famount);// 设置生产数量

		p.setFaudited(1);
		p.setFauditorid(sinfo.getFcreatorid());
		p.setFaudittime(new Date());
		p.setFordertype(sinfo.getFordertype());
		p.setFsuitProductId(sinfo.getFsuitProductId());
		p.setFparentOrderEntryId(sinfo.getFparentOrderEntryId());
		p.setForderid(sinfo.getForderid());
		p.setFimportEas(sinfo.getFimportEas());
		p.setFproductdefid(sinfo.getFproductdefid());
		p.setFsupplierid(spinfo.getFid());// 设置制造商
		p.setFentryProductType(sinfo.getFentryProductType());
		p.setFimportEastime(sinfo.getFimportEastime());
		p.setFamountrate(sinfo.getFamountrate());
		p.setFaffirmed(sinfo.getFaffirmed());
		p.setFstockinqty(sinfo.getFstockinqty());
		p.setFstockoutqty(sinfo.getFstockoutqty());
		p.setFstoreqty(sinfo.getFstoreqty());
		p.setFassemble(sinfo.getFassemble());
		p.setFiscombinecrosssubs(sinfo.getFiscombinecrosssubs());
		p.setFeasorderid(sinfo.getFeasorderid());
		p.setFeasorderentryid(sinfo.getFeasorderentryid());
		p.setFcloseed(0);
		p.setFdescription(sinfo.getFdescription());
		p.setFpcmordernumber(sinfo.getFpcmordernumber());
		HashMap<String, Object> params = productPlanDao.ExecSave(p);
		if (params.get("success") == Boolean.FALSE) {
			throw new DJException(sinfo.getFnumber() + "生成生产计划失败！");
		}
		
	}

	private JSONArray getJsonArrayByS(String s) {

		JSONObject jso = JSONObject.fromObject(s);
		
		String dataT = jso.getString("data");
		
		JSONArray jsa = null;

		if (dataT.charAt(0) == '{') {
			dataT = "[" + dataT + "]";
		}

		jsa = JSONArray.fromObject(dataT);

		return jsa;
	}

	/***
	 *  制造商生产报表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/GetSupplierProductNumsRpt")
	public String GetSupplierProductNumsRpt(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql =" select fproductdefid, fname,fsupplierid,lname,famounts,totalnum,CONCAT (round(rate*100,2),'%') rate "
			+" from ( select p.fproductdefid ,f.fname fname,p.fsupplierid,l.fname lname,sum(p.famount)famounts,s.totalnum,sum(p.famount)/s.totalnum rate "
			+" from t_ord_productplan  p "
			+" left join (select fproductdefid, sum(famount) totalnum from t_ord_productplan  where date_format(fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') group by fproductdefid ) s on p.fproductdefid=s.fproductdefid "
			+" left join  t_pdt_productdef f on f.fid=p.fproductdefid "
			+" left join  t_sys_supplier l on l.fid=p.fsupplierid "
			+" where date_format(p.fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') "
			+" group by p.fproductdefid,p.fsupplierid order by p.fproductdefid ) pp where 1=1" ;
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	/***
	 *  配送发货报表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/GetDeliverorderNumsRpt")
	public String GetDeliverorderNumsRpt(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
//			String sql =" select fproductid, fname,fsupplierid,lname,famounts,totalnum,CONCAT (round(rate*100,2),'%') rate,CONCAT (fproportion,'%') fproportion "
//			 +" from ( select p.fproductid,f.fname fname,p.fsupplierid,l.fname lname,sum(p.famount)famounts,s.totalnum,sum(p.famount)/s.totalnum rate,ifnull(cl.fproportion,0)  fproportion "
//			 + " from t_ord_deliverorder p "
//			 + " left join (select fproductid, sum(famount) totalnum  from t_ord_deliverorder where date_format(fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') and  fouted=1 group by fproductid ) s  on p.fproductid=s.fproductid "
//			 +" left join  t_pdt_productdef f on f.fid=p.fproductid "
//			 +" left join  t_sys_supplier l on l.fid=p.fsupplierid "
//			 +" left join   t_bd_productcycle cl on cl.fproductdefid=p.fproductid and cl.fsupplierid=p.fsupplierid "
//			 +" where date_format(p.fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') and  fouted=1 "
//			 +" group by p.fproductid,p.fsupplierid order by p.fproductid ) pp where 1=1 ";
			String sql="select fname,lname,truncate(famounts,2) famounts,truncate(totalnums,2) totalnums, rate,fwerkname from ( "+
					" select de.fproductid,f.fname fname,de.fsupplierid,l.fname lname,y.fwerkname,sum(de.famount)famounts,totalnums,CONCAT (round(sum(de.famount)/totalnums*100,2),'%') rate FROM t_tra_saledeliverentry de "+
					" left join t_tra_saledeliver d  on d.fid=de.fparentid "+
					" inner join t_ord_deliverorder p on p.fid=de.fdeliverorderid "+
					" left join  t_pdt_productdef f on f.fid=de.fproductid  "+
					" left join  t_sys_supplier l on l.fid=de.fsupplierid  "+
					" left join (select r.fid,y.fwerkname from t_ord_delivers r  "+
					" inner join t_ord_deliverratio o on o.fdeliverid=r.fid  "+
					" inner join t_ord_deliverapply y on y.fid=o.fdeliverappid )  y on y.fid=p.fdeliversid "+
					" left join (select de.fproductid,y.fwerkname,sum(de.famount) totalnums FROM t_tra_saledeliverentry de  "+
					" left join t_tra_saledeliver d  on d.fid=de.fparentid   "+
					" left join t_ord_deliverorder p on p.fid=de.fdeliverorderid   "+
					" left join (select r.fid,y.fwerkname from t_ord_delivers r "+
					" inner join t_ord_deliverratio o on o.fdeliverid=r.fid  "+
					" inner join t_ord_deliverapply y on y.fid=o.fdeliverappid ) y  on y.fid=p.fdeliversid  "+
					" where date_format(d.fauditdate,'%Y-%m')=date_format(now(),'%Y-%m') and  d.faudited=1 and ifnull(y.fwerkname,'')<>'' and p.ftype<>1 group by de.fproductid,y.fwerkname ) s  on s.fproductid= de.fproductid and  s.fwerkname=y.fwerkname  "+  
					" where date_format(d.fauditdate,'%Y-%m')=date_format(now(),'%Y-%m')  and  d.faudited=1 and ifnull(y.fwerkname,'')<>'' and p.ftype<>1  "+
					" group by de.fproductid,y.fwerkname,de.fsupplierid order by de.fproductid,y.fwerkname  "+
					" ) pp where 1=1   ";
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	
	
	
	@RequestMapping(value = "/SupplierProductNumsRpttoexect")
	public String SupplierProductNumsRpttoexect(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql =" select  fname 产品名称 ,lname 制造商名称,famounts 生产数量,totalnum 总生产合计,CONCAT (round(rate*100,2),'%') 占比 "
			+" from ( select p.fproductdefid ,f.fname fname,p.fsupplierid,l.fname lname,sum(p.famount)famounts,s.totalnum,sum(p.famount)/s.totalnum rate "
			+" from t_ord_productplan  p "
			+" left join (select fproductdefid, sum(famount) totalnum from t_ord_productplan  where date_format(fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') group by fproductdefid ) s on p.fproductdefid=s.fproductdefid "
			+" left join  t_pdt_productdef f on f.fid=p.fproductdefid "
			+" left join  t_sys_supplier l on l.fid=p.fsupplierid "
			+" where date_format(p.fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') "
			+" group by p.fproductdefid,p.fsupplierid order by p.fproductdefid ) pp where 1=1" ;
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, sList);

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping(value = "/DeliverorderNumsRpttoexect")
	public String DeliverorderNumsRpttoexect(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
//			String sql =" select  fname 产品名称 ,lname 制造商名称,famounts 生产数量,totalnum 总生产合计,CONCAT (round(rate*100,2),'%') 实际配额,CONCAT (fproportion,'%') 配额"
//			 +" from ( select p.fproductid,f.fname fname,p.fsupplierid,l.fname lname,sum(p.famount)famounts,s.totalnum,sum(p.famount)/s.totalnum rate,ifnull(cl.fproportion,0)  fproportion "
//			 + " from t_ord_deliverorder p "
//			 + " left join (select fproductid, sum(famount) totalnum  from t_ord_deliverorder where date_format(fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') and  fouted=1 group by fproductid ) s  on p.fproductid=s.fproductid "
//			 +" left join  t_pdt_productdef f on f.fid=p.fproductid "
//			 +" left join  t_sys_supplier l on l.fid=p.fsupplierid "
//			 +" left join   t_bd_productcycle cl on cl.fproductdefid=p.fproductid and cl.fsupplierid=p.fsupplierid "
//			 +" where date_format(p.fcreatetime,'%Y-%m')=date_format(now(),'%Y-%m') and  fouted=1 "
//			 +" group by p.fproductid,p.fsupplierid order by p.fproductid ) pp where 1=1 ";
			request.setAttribute("nolimit", 0);
			String sql="select fname 产品名称,lname 制造商名称,truncate(famounts,2)  发货数量 ,truncate(totalnums,2)  发货总合计, rate 配额,fwerkname 制造部名称  from ( "+
					" select de.fproductid,f.fname fname,de.fsupplierid,l.fname lname,y.fwerkname,sum(de.famount)famounts,totalnums,CONCAT (round(sum(de.famount)/totalnums*100,2),'%') rate FROM t_tra_saledeliverentry de "+
					" left join t_tra_saledeliver d  on d.fid=de.fparentid "+
					" inner join t_ord_deliverorder p on p.fid=de.fdeliverorderid "+
					" left join  t_pdt_productdef f on f.fid=de.fproductid  "+
					" left join  t_sys_supplier l on l.fid=de.fsupplierid  "+
					" left join (select r.fid,y.fwerkname from t_ord_delivers r  "+
					" inner join t_ord_deliverratio o on o.fdeliverid=r.fid  "+
					" inner join t_ord_deliverapply y on y.fid=o.fdeliverappid )  y on y.fid=p.fdeliversid "+
					" left join (select de.fproductid,y.fwerkname,sum(de.famount) totalnums FROM t_tra_saledeliverentry de  "+
					" left join t_tra_saledeliver d  on d.fid=de.fparentid   "+
					" left join t_ord_deliverorder p on p.fid=de.fdeliverorderid   "+
					" left join (select r.fid,y.fwerkname from t_ord_delivers r "+
					" inner join t_ord_deliverratio o on o.fdeliverid=r.fid  "+
					" inner join t_ord_deliverapply y on y.fid=o.fdeliverappid ) y  on y.fid=p.fdeliversid  "+
					" where date_format(d.fauditdate,'%Y-%m')=date_format(now(),'%Y-%m') and  d.faudited=1 and ifnull(y.fwerkname,'')<>''  and p.ftype<>1 group by de.fproductid,y.fwerkname ) s  on s.fproductid= de.fproductid and  s.fwerkname=y.fwerkname  "+  
					" where date_format(d.fauditdate,'%Y-%m')=date_format(now(),'%Y-%m')  and  d.faudited=1 and ifnull(y.fwerkname,'')<>''  and p.ftype<>1 "+
					" group by de.fproductid,y.fwerkname,de.fsupplierid order by de.fproductid,y.fwerkname  "+
					" ) pp where 1=1   ";
			
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, sList);

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	
	/***
	 *  配送发货报表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/GetMoreSupplierDeliverorderNumsRpt")
	public String GetMoreSupplierDeliverorderNumsRpt(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			request.setAttribute("nocount", "nocount");
			String sql ="select fproductid, fname,fsupplierid,lname,truncate(famounts,2) famounts,truncate(totalnum,2) totalnum,CONCAT (round(rate*100,2),'%') rate,CONCAT (fproportion,'%') fproportion from ( "
					   +" SELECT de.fsupplierid,de.fproductid,f.fname fname,l.fname lname,sum(de.famount) famounts,p.totalnum,sum(de.famount)/p.totalnum rate,ifnull(cl.fproportion,0)  fproportion "
					   +" FROM t_tra_saledeliverentry de "
					   +" left join t_tra_saledeliver d  on d.fid=de.fparentid  "
					   +" left join ( SELECT de.fproductid,sum(de.famount) totalnum FROM t_tra_saledeliverentry de "
					   +" left join t_tra_saledeliver d  on d.fid=de.fparentid "
					   +" left join t_ord_deliverorder p1 on DATEDIFF(NOW(),p1.fouttime)<40   AND   p1.fid=de.fdeliverorderid "
					   +" where EXTRACT(YEAR_MONTH FROM D.FAUDITDATE) = EXTRACT(YEAR_MONTH FROM NOW()) and d.faudited=1  and p1.ftype<>1 group by de.fproductid ) p on p.fproductid=de.fproductid "
					   +" left join  t_pdt_productdef f on f.fid=de.fproductid "
					   +" left join  t_sys_supplier l on l.fid=de.fsupplierid "
					   +" left join t_ord_deliverorder p1 on DATEDIFF(NOW(),p1.fouttime)<40   AND   p1.fid=de.fdeliverorderid "
					   +" inner join   t_bd_productcycle cl on cl.fproductdefid=de.fproductid and cl.fsupplierid=de.fsupplierid "
					   +" where EXTRACT(YEAR_MONTH FROM D.FAUDITDATE) = EXTRACT(YEAR_MONTH FROM NOW()) and ifnull(cl.fproportion,0)<>100  and d.faudited=1 and p1.ftype<>1 "
					   +" group by de.fproductid,de.fsupplierid order by de.fproductid ) pp where 1=1 ";
		
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			sList.setTotal(sList.getData().size()+"");
			reponse.getWriter().write(JsonUtil.result(true, "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	
	@RequestMapping(value = "/MoreSupplierDeliverorderNumsRpttoexect")
	public String MoreSupplierDeliverorderNumsRpttoexect(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setAttribute("nolimit", 0);
		try {
			String sql ="select  fname 产品名称 ,lname 制造商名称 ,truncate(famounts,2) 发货数量,truncate(totalnum,2) 发货总合计,CONCAT (round(rate*100,2),'%') 实际配额,CONCAT (fproportion,'%') 配额 from ( "
					   +" SELECT de.fsupplierid,de.fproductid,f.fname fname,l.fname lname,sum(de.famount) famounts,p.totalnum,sum(de.famount)/p.totalnum rate,ifnull(cl.fproportion,0)  fproportion "
					   +" FROM t_tra_saledeliverentry de "
					   +" left join t_tra_saledeliver d  on d.fid=de.fparentid  "
					   +" left join ( SELECT de.fproductid,sum(de.famount) totalnum FROM t_tra_saledeliverentry de "
					   +" left join t_tra_saledeliver d  on d.fid=de.fparentid "
					   +" left join t_ord_deliverorder p1 on DATEDIFF(NOW(),p1.fouttime)<40   AND p1.fid=de.fdeliverorderid  "
					   +" where EXTRACT(YEAR_MONTH FROM D.FAUDITDATE) = EXTRACT(YEAR_MONTH FROM NOW())   and d.faudited=1 and p1.ftype<>1 group by de.fproductid ) p on p.fproductid=de.fproductid "
					   +" left join  t_pdt_productdef f on f.fid=de.fproductid "
					   +" left join  t_sys_supplier l on l.fid=de.fsupplierid "
					   +" left join t_ord_deliverorder p1 on DATEDIFF(NOW(),p1.fouttime)<40   AND p1.fid=de.fdeliverorderid "
					   +" inner join   t_bd_productcycle cl on cl.fproductdefid=de.fproductid and cl.fsupplierid=de.fsupplierid "
					   +" where EXTRACT(YEAR_MONTH FROM D.FAUDITDATE) = EXTRACT(YEAR_MONTH FROM NOW())  and ifnull(cl.fproportion,0)<>100 and d.faudited=1 and p1.ftype<>1 "
					   +" group by de.fproductid,de.fsupplierid order by de.fproductid ) pp where 1=1 ";
		
			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, sList);

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
/**
 * 根据产品ID获取客户产品ID
 * @throws IOException 
 */
	@RequestMapping("getCustproductByProductid")
	public void getCustproductByProductid(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		String fproductid = request.getParameter("fproductid");
		try {
			String sql = "SELECT c.fid,c.fname FROM t_pdt_productrelation r LEFT JOIN t_pdt_productrelationentry e ON e.fparentid = r.fid  LEFT JOIN `t_bd_custproduct` c  ON c.fid = e.fcustproductid WHERE r.fproductid = '"+fproductid+"'"+saleOrderDao.QueryFilterByUser(request, "r.fcustomerid", null);
			List list = saleOrderDao.QueryBySql(sql);
			if(list.size()>0){
				if(list.size()==1){
					reponse.getWriter().write(
							JsonUtil.result(true, "", "", list));
				}else{
					reponse.getWriter().write(
							JsonUtil.result(false,"", "", ""));
				}
			}else{
				sql = "SELECT c.fid,c.fname FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid LEFT JOIN `t_bd_custproduct` c ON c.fid=r.`FCUSTPRODUCTID` WHERE e.fproductid='"+fproductid+"'"+saleOrderDao.QueryFilterByUser(request, "r.fcustomerid", null);
				list = saleOrderDao.QueryBySql(sql);
				if(list.size()==1){
					reponse.getWriter().write(
							JsonUtil.result(true, "", "", list));
				}else{
					reponse.getWriter().write(
							JsonUtil.result(false,"", "", ""));
				}
			}
			
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	/**
	 * 方案下单明细表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/GetOrderSchemeDetailList")
	public String GetOrderSchemeDetailList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select * from (select c.fname cname,f.fname,f.fauditortime,f.freceivetime,u1.fname sname,s.fcreatetime,s.fconfirmtime, "
					+ "case when s.fconfirmed=0 then '未确认' else if((ifnull(sd.count,0)!='0' and ifnull(dc.fname,0)!='0') or(ifnull(sd.count,0)='0' and ifnull(p.schemedesignid,0)!='0'),'已确认已下单','已确认未下单') end sstate, "
					+ "sp.fname fsupplier,f.flinkman,f.flinkphone FROM t_ord_schemedesign s "
					+ "left join t_ord_firstproductdemand f on s.ffirstproductid=f.fid "
					+ "left join t_bd_customer c ON f.fcustomerid = c.fid "
					+ "LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid "
					+ "LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid "
					+ "left join (select count(1) count,fparentid from t_ord_schemedesignentry group by fparentid) sd on s.fid=sd.fparentid "
					+ "left join(select cpdt.fname from t_ord_deliverapply d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid ) dc on u1.fname=dc.fname "
					+ "left join (select schemedesignid,p.fname from t_pdt_productdef p left join(select cpdt.fname from t_ord_deliverapply d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid ) dc on p.fname=dc.fname group by schemedesignid) p on s.fid=p.schemedesignid) a ";

			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/OrderSchemeDetailListRpttoexect")
	public String OrderSchemeDetailListRpttoexect(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setAttribute("nolimit", 0);
		try {
			String sql = "select * from (select c.fname 客户名称,f.fname 需求名称,f.fauditortime 需求发布时间,f.freceivetime 需求接收时间,u1.fname 方案名称,s.fcreatetime 方案创建时间,s.fconfirmtime 方案确定时间, "
					+ "case when s.fconfirmed=0 then '未确认' else if((ifnull(sd.count,0)!='0' and ifnull(dc.fname,0)!='0') or(ifnull(sd.count,0)='0' and ifnull(p.schemedesignid,0)!='0'),'已确认已下单','已确认未下单') end 方案状态, "
					+ "sp.fname 制造商,f.flinkman 设计师,f.flinkphone 设计师电话 "
					+ "FROM t_ord_schemedesign s left join t_ord_firstproductdemand f on s.ffirstproductid=f.fid "
					+ "left join t_bd_customer c ON f.fcustomerid = c.fid "
					+ "LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid "
					+ "LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid "
					+ "left join (select count(1) count,fparentid from t_ord_schemedesignentry group by fparentid) sd on s.fid=sd.fparentid "
					+ "left join(select cpdt.fname from t_ord_deliverapply d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid ) dc on u1.fname=dc.fname "
					+ "left join (select schemedesignid,p.fname from t_pdt_productdef p left join(select cpdt.fname from t_ord_deliverapply d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid ) dc on p.fname=dc.fname group by schemedesignid) p on s.fid=p.schemedesignid) a ";

			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
			ExcelUtil
					.toexcel(reponse, sList, "方案下单明细表");

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	


	/**
	 * 制造商接收;
	 */
	@RequestMapping(value = "/FinishedSupplierAffirm")
	public String FinishedSupplierAffirm(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		synchronized (this.getClass()){
		String result = "";
		String fidcls = request.getParameter("fidcls");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();

		try {
			if (fidcls == null || fidcls.equals("")) {
				throw new DJException("制造商已接收");
			}
			fidcls="'"+fidcls.replace(",","','")+"'";
			///服务端验证是否接收
			String sql = "select 1 from t_ord_productplan where fid in ("+fidcls+") and faffirmed=1";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("制造商已接收");
			}
			 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in("
					+ fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("已关闭订单不能接收！");
			}

 			saleOrderDao.ExecAffirmAndImportEAS(fidcls,userid);
			result = JsonUtil.result(true, "制造商接收成功", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;
		}

	}

	/**
	 * 制造商取消接收;
	 */
	@RequestMapping(value = "/FinishedSupplierUnAffirm")
	public String FinishedSupplierUnAffirm(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");

		try {
			if (fidcls == null || fidcls.equals("")) {
				throw new DJException("制造商未接收");
			}
			fidcls="'"+fidcls.replace(",","','")+"'";
			String sql = "select 1 from t_ord_productplan where fid in ("+fidcls+") and ifnull(faffirmed,0)=0";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("制造商未接收");
			}
			 sql = "SELECT 1 FROM t_ord_productplan where fimportEas=1 and fissync=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已导入接口不能取消接收，请先从接口退回！");
			}
			
			 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已关闭不能取消接收！");
			}
			
			sql = "SELECT 1 FROM t_ord_productplan where fcreateboard=1 and fid in(" + fidcls + ")";
			if(DeliversDao.QueryExistsBySql(sql)){
				throw new DJException("订单已生成纸板采购,不允许取消接收！");
			}
			
			sql = "SELECT fid FROM t_inv_productindetail where fproductplanid in("
					+ fidcls + ")";
			List balancelist = DeliversDao.QueryBySql(sql);
			if (balancelist.size() > 0) {
				throw new DJException("已入库订单不能取消接收！");
			}
			sql = "UPDATE t_ord_productplan SET faffirmed='0',fstate=0,faffirmtime=null,faffirmer='',fimportEas=0 WHERE fid in ("
					+ fidcls + ")";
			
			//增加直接取消快速下单;			
			String quicksql="SELECT QUOTE(fid) fid FROM t_ord_deliverapply WHERE IFNULL(fmaterialfid,'')<>'' and  fiscreate=1 and fplanid in ("+fidcls+")";
			List<HashMap<String, Object>> custList=saleOrderDao.QueryBySql(quicksql);
			String cids="";//符合记录fid
			StringBuilder custids=new StringBuilder();
			for(HashMap<String, Object> m : custList){
				custids.append(m.get("fid")+",");
			}
			cids = custids.toString();
			if(!cids.equals("")){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("productplanSql", sql);
				map.put("deliverapplySql", "update t_ord_deliverapply set fstate = 0 where fplanid in (" + fidcls + ")");
				cids="("+cids.substring(0, cids.length()-1)+")";
				map.put("fidcls", cids);
				map.put("userid", ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid());
				map.put("username", ((Useronline)request.getSession().getAttribute("Useronline")).getFusername());
				deliverapplyDao.ExecDeleteCreateDeliverapply(map);
			}else{
				saleOrderDao.ExecBySql(sql);
				saleOrderDao.ExecBySql("update t_ord_deliverapply set fstate = 0 where fplanid in (" + fidcls + ")");
			}
			
			result = JsonUtil.result(true, "取消接收成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}
	
}
