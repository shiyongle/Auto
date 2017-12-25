package Com.Controller.System;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.params;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.ISimplemessageDao;
import Com.Dao.System.IVmipdtParamDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Simplemessage;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;
import Com.Entity.System.VmipdtParam;
import Com.Entity.order.Saleorder;

@Controller
public class VmipdtParamController {
	private static final String VMI_PRO_CANT_BE_ALLOT_TO_DJ = "平台新建的产品不能分配给东力";
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IVmipdtParamDao VmipdtParamDao;
	@Resource
	private ISaleOrderDao saleOrderDao;
	@Resource
	private ISimplemessageDao simplemessageDao;
	@Resource
	private IStorebalanceDao storebalanceDao; 

	private boolean isrunning = false;

	@RequestMapping("/GetVmipdtParamList")
	public String GetVmipdtParamList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fcustomerid = request.getParameter("fcustomerid");
		String sql = "select v.fid,v.fcreatorid,u1.fname as fcreator,v.fcreatetime,v.flastupdateuserid,u2.fname as flastupdater,v.flastupdatetime,v.fcustomerid,c.fname as fcustname,v.fproductid,d.fname as fpdtname,v.fmaxstock,v.forderamount,v.fminstock,v.fbalanceqty,v.fproducedqty,ifnull(v.fdescription,'') fdescription,ifnull(v.fcontrolunitid,'') fcontrolunitid,v.fefected,ifnull(v.fsupplierId,'') fsupplierId,ifnull(sp.fname,'') fsupplier,ifnull(v.ftype,0) ftype from t_pdt_vmiproductparam v left join t_sys_supplier sp on sp.fid=v.fsupplierId left join t_sys_user u1 on  u1.fid=v.fcreatorid left join t_sys_user u2 on  u2.fid=v.flastupdateuserid left join t_bd_customer c on c.fid=v.fcustomerid left join t_pdt_productdef d on d.fid=v.fproductid ";
		if(!StringUtils.isEmpty(fcustomerid)){
			sql += " where v.fcustomerid ='"+fcustomerid+"'";
		}
		ListResult result;
		try {
			result = VmipdtParamDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			request.getSession().setAttribute("vmipdtParamListQuery", sql);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/SaveVmipdtParam")
	public String SaveVmipdtParam(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		VmipdtParam vinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			// 非空 fcustomerid fsupplierId fproductid fmaxstock forderamount
			// fminstock
			String[][] fieldsNameNamesArrays = { { "fcustomerid", "客户ID" },
					{ "fsupplierId", "制造商ID" }, { "fproductid", "产品名称" } };
			String[][] fFiledsTablesArrays = {
					{ "fcustomerid", "t_bd_customer", null, null },
					{ "fsupplierId", "t_sys_supplier", null, null },
					{ "fproductid", "t_pdt_productdef", "fcustomerid", null } };
			DataUtil.verifyNotNullAndDataAndPermissions(fieldsNameNamesArrays,
					fFiledsTablesArrays, request, VmipdtParamDao);
			// if(!DataUtil.positiveIntegerCheck(request.getParameter("fmaxstock"))){
			// throw new DJException("最大库存量不能为空，或大于0的正整数！");
			// }
			// if(!DataUtil.positiveIntegerCheck(request.getParameter("forderamount"))){
			// throw new DJException("下单批量不能为空，或大于0的正整数！");
			// }
			// if(!DataUtil.positiveIntegerCheck(request.getParameter("fminstock"))){
			// throw new DJException("最小库存量不能为空，或大于0的正整数！");
			// }

			// 安全库存类型一致性检测
			VmipdtParamDao.ExecGetVmiProductParam(
					request.getParameter("fproductid"),
					request.getParameter("ftype"),
					request.getParameter("fsupplierId"));

			// 增加产品与客户关联过滤;
			if (request.getParameter("fproductid") != null
					&& !request.getParameter("fproductid").equals("")
					&& request.getParameter("fcustomerid") != null
					&& !request.getParameter("fcustomerid").equals("")) {
				String sql = "select fid from t_pdt_productdef where fid = '"
						+ request.getParameter("fproductid")
						+ "' and fcustomerid = '"
						+ request.getParameter("fcustomerid") + "'";
				List<HashMap<String, Object>> productcyclecls = VmipdtParamDao
						.QueryBySql(sql);
				if (productcyclecls.size() <= 0) {
					throw new DJException("该产品不属于当前客户！");
				}
			}
			// 检查是否已经存在最小安全库存为0的记录fminstock
			if (request.getParameter("fminstock") != null
					&& request.getParameter("fminstock").equals("0")) {
				String sql = "select fid from t_pdt_vmiproductparam where FPRODUCTID = '"
						+ request.getParameter("fproductid")
						+ "' and fminstock=0 ";// "  and fid <> '"+request.getParameter("fsupplierId")+"'";
				if (fid != null && !StringHelper.isEmpty(fid)) {
					sql = sql + "  and fid <> '" + fid + "' ";
				}
				List<HashMap<String, Object>> productcyclecls = VmipdtParamDao
						.QueryBySql(sql);
				if (productcyclecls.size() > 0) {
					throw new DJException("该产品已经存在最小安全库存为0的记录");
				}
			}

			// 增加产品与制造商关联过滤;
			if (request.getParameter("fproductid") != null
					&& !request.getParameter("fproductid").equals("")
					&& request.getParameter("fsupplierId") != null
					&& !request.getParameter("fsupplierId").equals("")) {
				String sql = "select fid from t_bd_productcycle where FPRODUCTDEFID = '"
						+ request.getParameter("fproductid")
						+ "' and FSUPPLIERID = '"
						+ request.getParameter("fsupplierId") + "'";
				List<HashMap<String, Object>> productcyclecls = VmipdtParamDao
						.QueryBySql(sql);
				if (productcyclecls.size() <= 0) {
					throw new DJException("该产品不属于当前制造商生产！");
				}
			}

			if (fid != null && !"".equals(fid)) {
				vinfo = VmipdtParamDao.Query(fid);

				if ((request.getParameter("ftype") != null && !request
						.getParameter("ftype").equals(
								vinfo.getFtype().toString()))
						|| (request.getParameter("fproductid") != null && !request
								.getParameter("fproductid").equals(
										vinfo.getFproductid()))
						|| (request.getParameter("fsupplierId") != null && !request
								.getParameter("fsupplierId").equals(
										vinfo.getFsupplierId()))) {
					String sql = "select sum(fbalanceqty) fbalanceqty from t_inv_storebalance where fsupplierID = '"
							+ vinfo.getFsupplierId()
							+ "' and fproductid = '"
							+ vinfo.getFproductid() + "'";
					List<HashMap<String, Object>> balancecls = VmipdtParamDao
							.QueryBySql(sql);
					if (balancecls.size() > 0) {
						if (balancecls.get(0).get("fbalanceqty") != null) {
							if (Integer.parseInt(balancecls.get(0)
									.get("fbalanceqty").toString()) > 0) {
								throw new DJException(
										"原记录库存大于0不能修改产品、制造商、下单类型！");
							}
						}
					}

					sql = " select fid from t_ord_saleorder where fallot=0  and fproductdefid = '"
							+ vinfo.getFproductid() + "' ";
					List<HashMap<String, Object>> vmipcls = VmipdtParamDao
							.QueryBySql(sql);
					if (vmipcls.size() > 0) {
						throw new DJException("原记录存在未分配的生产订单，不能修改！");
					}

					sql = " select fid from t_ord_productplan where fcloseed=0 and fsupplierid = '"
							+ vinfo.getFsupplierId()
							+ "' and fproductdefid ='"
							+ vinfo.getFproductid() + "'";
					vmipcls = VmipdtParamDao.QueryBySql(sql);
					if (vmipcls.size() > 0) {
						throw new DJException("原记录存在未关闭的制造商订单，不能修改！");
					}

					sql = " select fid from t_ord_deliverorder where fouted = 0 and fsupplierId = '"
							+ vinfo.getFsupplierId()
							+ "' and fproductid = '"
							+ vinfo.getFproductid() + "' ";
					vmipcls = VmipdtParamDao.QueryBySql(sql);
					if (vmipcls.size() > 0) {
						throw new DJException("原记录存在未发货的配送信息，不能修改！");
					}
				}
			} else {
				// 增加产品、制造商、下单类型唯一性过滤;
				if (request.getParameter("ftype") != null
						&& !request.getParameter("ftype").equals("")
						&& request.getParameter("fproductid") != null
						&& !request.getParameter("fproductid").equals("")
						&& request.getParameter("fsupplierId") != null
						&& !request.getParameter("fsupplierId").equals("")) {
					String sql = "select fid from t_pdt_vmiproductparam where fproductid = '"
							+ request.getParameter("fproductid")
							+ "' and FSUPPLIERID = '"
							+ request.getParameter("fsupplierId") + "' ";
					List<HashMap<String, Object>> vmiproductparamcls = VmipdtParamDao
							.QueryBySql(sql);
					if (vmiproductparamcls.size() > 0) {
						throw new DJException("该制造商、产品安全库存已经存在！");
					}
				}

				vinfo = new VmipdtParam();
				vinfo.setFid(fid);
				vinfo.setFcreatorid(userid);
				vinfo.setFcreatetime(new Date());
			}

			vinfo.setFlastupdatetime(new Date());
			vinfo.setFlastupdateuserid(userid);

			if (request.getParameter("fcustomerid") == null
					|| request.getParameter("fcustomerid").equals("")) {
				throw new DJException("客户不能为空!");
			} else {
				vinfo.setFcustomerid(request.getParameter("fcustomerid"));
			}

			if (request.getParameter("fproductid") == null
					|| request.getParameter("fproductid").equals("")) {
				throw new DJException("产品不能为空！");
			} else {
				vinfo.setFproductid(request.getParameter("fproductid"));
			}

			if (request.getParameter("fsupplierId") == null
					|| request.getParameter("fsupplierId").equals("")) {
				throw new DJException("制造商不能为空！");
			} else {
				vinfo.setFsupplierId(request.getParameter("fsupplierId"));
			}

			if (request.getParameter("fmaxstock") != null
					&& !request.getParameter("fmaxstock").equals("")
					&& (new Integer(request.getParameter("fmaxstock")) > 0)) {
				vinfo.setFmaxstock(new Integer(request
						.getParameter("fmaxstock")));
			} else {
				throw new DJException("最大库存量不能为空且必须大于0！");
			}

			if (request.getParameter("ftype") == null
					|| request.getParameter("ftype").equals("")) {
				throw new DJException("下单类型不能为空！");
			} else {
				vinfo.setFtype(new Integer(request.getParameter("ftype")));
			}

			// 东经制造商下单类型必须为订单类型
//			if (vinfo.getFsupplierId().equals("39gW7X9mRcWoSwsNJhU12TfGffw=")) {
//				if (request.getParameter("ftype") != null
//						&& request.getParameter("ftype").equals("0")) {
//					throw new DJException("东经制造商下单类型必须为订单类型！");
//				}
//			}

			if (request.getParameter("forderamount") != null
					&& !request.getParameter("forderamount").equals("")) {
				vinfo.setForderamount(new Integer(request
						.getParameter("forderamount")));
			} else {
				throw new DJException("下单批量不能为空！");
			}

			if (request.getParameter("fminstock") != null
					&& !request.getParameter("fminstock").equals("")) {
				vinfo.setFminstock(new Integer(request
						.getParameter("fminstock")));
			} else {
				vinfo.setFminstock(0);
			}
			if (vinfo.getFminstock() == 0) {
				if (request.getParameter("ftype") != null
						&& request.getParameter("ftype").equals("0")) {
					throw new DJException("最小库存为0，下单类型必须为订单类型！");
				}
			}

			if (request.getParameter("fbalanceqty") != null
					&& !request.getParameter("fbalanceqty").equals("")) {
				vinfo.setFbalanceqty(new Integer(request
						.getParameter("fbalanceqty")));
			} else {
				vinfo.setFbalanceqty(0);
			}

			if (request.getParameter("fproducedqty") != null
					&& !request.getParameter("fproducedqty").equals("")) {
				vinfo.setFproducedqty(new Integer(request
						.getParameter("fproducedqty")));
			} else {
				vinfo.setFproducedqty(0);
			}

			vinfo.setFdescription(request.getParameter("fdescription"));
			vinfo.setFcontrolunitid(request.getParameter("fcontrolunitid"));
			// 检查新数据,如果原来没有安全库存默认走的是订单类型，如果新加的设置成通知类型就会出错 BY CC 20140308
			if (vinfo.getFtype() == 0) {

				String sql = "select sum(fbalanceqty) fbalanceqty from t_inv_storebalance where ftype =1 and fsupplierID = '"
						+ vinfo.getFsupplierId()
						+ "' and fproductid = '"
						+ vinfo.getFproductid() + "'";
				List<HashMap<String, Object>> balancecls = VmipdtParamDao
						.QueryBySql(sql);
				if (balancecls.size() > 0) {
					if (balancecls.get(0).get("fbalanceqty") != null) {
						if (Integer.parseInt(balancecls.get(0)
								.get("fbalanceqty").toString()) > 0) {
							throw new DJException("新记录订单类型库存大于0,下单类型必须为订单!");
						}
					}
				}

				sql = " select fid from t_ord_saleorder where fallot=0  and fproductdefid = '"
						+ vinfo.getFproductid() + "' ";
				List<HashMap<String, Object>> vmipcls = VmipdtParamDao
						.QueryBySql(sql);
				if (vmipcls.size() > 0) {
					throw new DJException("新记录存在未分配的生产订单，下单类型必须为订单！");
				}

				sql = " select fid from t_ord_productplan where fcloseed=0 and fsupplierid = '"
						+ vinfo.getFsupplierId()
						+ "' and fproductdefid ='"
						+ vinfo.getFproductid() + "'";
				vmipcls = VmipdtParamDao.QueryBySql(sql);
				if (vmipcls.size() > 0) {
					throw new DJException("新记录存在未关闭的制造商订单，下单类型必须为订单！");
				}

				sql = " select fid from t_ord_deliverorder where fouted = 0 and fsupplierId = '"
						+ vinfo.getFsupplierId()
						+ "' and fproductid = '"
						+ vinfo.getFproductid() + "' ";
				vmipcls = VmipdtParamDao.QueryBySql(sql);
				if (vmipcls.size() > 0) {
					throw new DJException("新记录存在未发货的配送信息，下单类型必须为订单！");
				}

			}

			// vmi新建产品不分给东力
			verifyProductCreatePlatform(request.getParameter("fproductid"),
					request.getParameter("fcustomerid"));

			/*
			 * 通知类型安全库存无库存记录，生成通知类型库存记录;
			 * 增加制造商仓库、库位空判断;
			 */
			String fsupplerID = vinfo.getFsupplierId();
			String hql = " from  Supplier s where s.fid = '%s' ";
			hql = String.format(hql, fsupplerID);
			Supplier supplierT = (Supplier) VmipdtParamDao.QueryByHql(hql).get(0);
			String fwarehouseId,fwarehouseSiteId;
			fwarehouseId = supplierT.getFwarehouseid();
			fwarehouseSiteId = supplierT.getFwarehousesiteid();
			if(fwarehouseId == null || fwarehouseId.equals("")){
				throw (new DJException("制造商仓库未设置！"));
			}
			if (fwarehouseSiteId == null || fwarehouseSiteId.equals("")) {
				throw (new DJException("制造商库位未设置！"));
			}
			
			HashMap<String, Object> params = VmipdtParamDao.ExecSave(vinfo);
			
			if(vinfo.getFtype()==0){
				String fproductID = vinfo.getFproductid();
				List<HashMap<String, Object>> productlists=VmipdtParamDao.QueryBySql("select fproductid from t_pdt_productdefproducts where fparentid='"+fproductID+"'");
				if(productlists.size()>0){
					for(int i=0;i<productlists.size();i++){
						fproductID=productlists.get(i).get("fproductid").toString();
						CreateStorebalance(fproductID, fwarehouseId, fwarehouseSiteId,fsupplerID,userid);
					}
				}else{
					CreateStorebalance(fproductID, fwarehouseId, fwarehouseSiteId,fsupplerID,userid);
				}
			}
			
			System.out.println(params);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true, "产品安全库存保存成功!", "", "");
			} else {
				result = JsonUtil.result(false, "产品安全库存保存失败!", "", "");
			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	private void CreateStorebalance(String fproductID, String fwarehouseId,
			String fwarehouseSiteId, String fsupplerID, String userid) {
		// TODO Auto-generated method stub
		String hql = " from Storebalance s where s.fproductId = '%s' and  s.ftype = 0 and s.fwarehouseId = '%s' and s.fwarehouseSiteId = '%s' and s.fsupplierID = '%s' ";
		hql = String.format(hql, fproductID, fwarehouseId, fwarehouseSiteId,fsupplerID); 
		List<Storebalance> storebalancesT = VmipdtParamDao.QueryByHql(hql);
		Storebalance storebalanceT;
		if (storebalancesT.size() == 0) {

			storebalanceT = new Storebalance("");

			storebalanceT.setFcreatorid(userid);

			storebalanceT.setFproductId(fproductID);

			storebalanceT.setFcreatetime(new Date());

			storebalanceT.setFwarehouseId(fwarehouseId);
			storebalanceT.setFwarehouseSiteId(fwarehouseSiteId);
			storebalanceT.setFsupplierID(fsupplerID);
			storebalanceT.setFtype(0);
			storebalanceT.setFinqty(0);
			storebalanceT.setFbalanceqty(0);
			storebalanceDao.ExecSave(storebalanceT);
		}
	}

	private void verifyProductCreatePlatform(String productID, String supplierID) {
		// TODO Auto-generated method stub

		String hql = "select pro.feasproductId from Productdef as pro where fid = '%s'";

		hql = String.format(hql, productID);

		List<String> listT = saleOrderDao.QueryByHql(hql);

		// 目标id已直接从数据库查出
		if (listT.get(0).length() == 36
				&& supplierID.equals("39gW7X9mRcWoSwsNJhU12TfGffw=")) {
			throw new DJException(VMI_PRO_CANT_BE_ALLOT_TO_DJ);
		}

	}

	@RequestMapping("/DelVmipdtParamList")
	public String DelVmipdtParamList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		try {
			String sql = "select d.fname,v.fproductid,v.fsupplierId from t_pdt_vmiproductparam v left join t_pdt_productdef d on d.fid=v.fproductid where v.fid in "
					+ fidcls;
			List<HashMap<String, Object>> vmicls = VmipdtParamDao
					.QueryBySql(sql);
			String fproductid = "";
			String fsupplierId = "";
			for (int i = 0; i < vmicls.size(); i++) {
				fproductid = vmicls.get(i).get("fproductid").toString();
				fsupplierId = vmicls.get(i).get("fsupplierId").toString();

				sql = "select sum(fbalanceqty) fbalanceqty from t_inv_storebalance where ftype = 0 and fsupplierID = '"
						+ fsupplierId
						+ "' and fproductid = '"
						+ fproductid
						+ "'";
				List<HashMap<String, Object>> balancecls = VmipdtParamDao
						.QueryBySql(sql);
				if (balancecls.size() > 0) {
					if (balancecls.get(0).get("fbalanceqty") != null) {
						if (Integer.parseInt(balancecls.get(0)
								.get("fbalanceqty").toString()) > 0) {
							throw new DJException("该安全库存产品’"
									+ vmicls.get(i).get("fname").toString()
									+ "‘有通知类型库存不能删除！");
						}
					}
				}

				sql = " select fid from t_ord_saleorder where fallot=0 and fproductdefid = '"
						+ fproductid + "' ";
				List<HashMap<String, Object>> vmipcls = VmipdtParamDao
						.QueryBySql(sql);
				if (vmipcls.size() > 0) {
					throw new DJException("该安全库存产品’"
							+ vmicls.get(i).get("fname").toString()
							+ "‘存在未分配的生产订单，不能删除！");
				}

				sql = " select fid from t_ord_productplan where fcloseed=0 and fproductdefid = '"
						+ fproductid
						+ "' and fsupplierid='"
						+ fsupplierId
						+ "'";
				vmipcls = VmipdtParamDao.QueryBySql(sql);
				if (vmipcls.size() > 0) {
					throw new DJException("该安全库存产品’"
							+ vmicls.get(i).get("fname").toString()
							+ "‘存在未关闭的制造商订单，不能删除！");
				}

				sql = " select fid from t_ord_deliverorder where fouted = 0 and fsupplierId='"
						+ fsupplierId
						+ "' and fproductid = '"
						+ fproductid
						+ "' ";
				vmipcls = VmipdtParamDao.QueryBySql(sql);
				if (vmipcls.size() > 0) {
					throw new DJException("该安全库存产品’"
							+ vmicls.get(i).get("fname").toString()
							+ "‘存在未发货的配送信息，不能删除！");
				}
			}

			String hql = "Delete FROM VmipdtParam where fid in " + fidcls;
			VmipdtParamDao.ExecByHql(hql);
			result = JsonUtil.result(true, "删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelVmipdtParamList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/getVmipdtParamInfo")
	public String getVmipdtParamInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select v.fid,v.fcreatorid,v.fcreatetime,v.flastupdateuserid,v.flastupdatetime,ifnull(v.fsupplierId,'') as fsupplierId_fid,ifnull(sp.fname,'') as fsupplierId_fname,v.fcustomerid as fcustomerid_fid,c.fname as fcustomerid_fname,v.fproductid as fproductid_fid,d.fname as fproductid_fname,v.fmaxstock,v.forderamount,v.fminstock,v.fbalanceqty,v.fproducedqty,v.fdescription,ifnull(v.fcontrolunitid,'') fcontrolunitid,ifnull(v.ftype,0) ftype from t_pdt_vmiproductparam v left join t_sys_supplier sp on sp.fid=v.fsupplierId left join t_pdt_productdef d on d.fid=v.fproductid left join t_bd_customer c on c.fid=v.fcustomerid where v.fid =:fid";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String, Object>> result = VmipdtParamDao.QueryBySql(
					sql, p);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	@RequestMapping("/AddVmipdtParam")
	public String AddVmipdtParam(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		return null;

	}

	@RequestMapping(value = "/GetProductlist")
	public String GetProductlist(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select fid,fname,fnumber,fversion,fcharacter,fboxmodelid,feffect,fnewtype,fishistory,flastupdateuserid,flastupdatetime,fcreatetime,fcreatorid FROM t_pdt_productdef ";
			// String fcustomerid = request.getParameter("fcustomerid");
			// if (fcustomerid !=null && !"".equals(fcustomerid) ) {
			// sql += " where fcustomerid='" + fcustomerid + "' ";
			// }
			ListResult result;

			result = VmipdtParamDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	// 模拟VMI下单
	@RequestMapping(value = "/prevmiplan")
	public String prevmiplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			// TODO Auto-generated method stub
			String sql = " select fnumber from t_ord_saleorder  where fallot=0 and faudited=1 and exists (select fid from t_pdt_vmiproductparam where t_pdt_vmiproductparam.fproductid=t_ord_saleorder.fproductdefid) ";
			List<HashMap<String, Object>> vmipcls = VmipdtParamDao
					.QueryBySql(sql);
			if (vmipcls.size() > 0) {
				throw new DJException("存在已审核但未分配的生产订单");
			}
			sql = " select ifnull(t_pdt_vmiproductparam.ftype,0) ftype,t_pdt_vmiproductparam.fsupplierId,t_pdt_vmiproductparam.fid,t_bd_customer.fid as fcustomerid,t_pdt_productdef.fassemble,t_pdt_productdef.feffect,t_pdt_productdef.fishistory,t_pdt_productdef.fiscombinecrosssubs,t_pdt_productdef.fid as fproductid,t_pdt_vmiproductparam.fminstock,t_pdt_vmiproductparam.forderamount,t_pdt_productdef.fnewtype from t_pdt_vmiproductparam,t_pdt_productdef,t_bd_customer where   t_bd_customer.fid=t_pdt_productdef.fcustomerid and t_pdt_productdef.fid=t_pdt_vmiproductparam.fproductid  "; // and
																																																																																																																																																		// t_pdt_vmiproductparam.ftype=0
			vmipcls = VmipdtParamDao.QueryBySql(sql);
			String msg = "";
			for (int i = 0; i < vmipcls.size(); i++) {
				HashMap<String, Object> info = vmipcls.get(i);
				int effect = (Integer) info.get("feffect") == null ? 0
						: (Integer) info.get("feffect");
				int ishistory = (Integer) info.get("fishistory") == null ? 0
						: (Integer) info.get("fishistory");
				if (ishistory == 1) {
					sql = " update t_pdt_vmiproductparam set fdescription='未下单，信息:历史版本产品不能下单！' where fid='"
							+ info.get("fid").toString() + "' ";
					VmipdtParamDao.ExecBySql(sql);
					continue;
				}
				if (effect == 0) {
					sql = " update t_pdt_vmiproductparam set fdescription='未下单，信息:禁用产品不能下单！' where fid='"
							+ info.get("fid").toString() + "' ";
					VmipdtParamDao.ExecBySql(sql);
					continue;
				}
				msg = "";
				String fsupplierId = info.get("fsupplierId").toString();
				int ftype = Integer.parseInt(info.get("ftype").toString());
				int fnewtype=Integer.parseInt(info.get("fnewtype").toString());//产品类型info.get("fnewtype")
				String productid = info.get("fproductid").toString();
				String customerid = info.get("fcustomerid").toString();
				int minstock = Integer.parseInt(info.get("fminstock")
						.toString());
				int orderamount = Integer.parseInt(info.get("forderamount")
						.toString());
				int balanceqty = 0;
				if(ftype==1||(ftype==0&&(fnewtype==1||fnewtype==3))){//除通知套装，计算库存
				sql = " select sum(ifnull(fbalanceqty,0)) fbalanceqty,sum(ifnull(fallotqty,0)) fallotqty from t_inv_storebalance b where b.FSUPPLIERID = '"
						+ fsupplierId
						+ "' and b.fproductid='"
						+ productid
						+ "' and ftype = '" + ftype + "'";
				List<HashMap<String, Object>> balancecls = VmipdtParamDao
						.QueryBySql(sql);
				if (balancecls.size() > 0) {
					if (balancecls.get(0).get("fbalanceqty") != null) {
						balanceqty = Integer.parseInt(balancecls.get(0)
								.get("fbalanceqty").toString());
						int fallotqty=(balancecls.get(0).get("fallotqty")==null?0:Integer.parseInt(balancecls.get(0).get("fallotqty").toString()));
						msg = msg + "库存数量：" + balanceqty + ";调拨在途数量："+fallotqty+";";
						balanceqty=balanceqty+fallotqty;
					} else {
						msg = msg + "库存数量：0;调拨在途数量：0;";
						}
				}
				}
				sql = "select sum(case when famount<fstockinqty then 0 else famount-ifnull(fstockinqty,0) end ) fmakingqty from t_ord_productplan where fsupplierid = '"
						+ fsupplierId
						+ "' and faudited=1 and fcloseed =0 and fproductdefid='"
						+ productid + "' ";
				List<HashMap<String, Object>> makingqtycls = VmipdtParamDao
						.QueryBySql(sql);
				if (makingqtycls.size() > 0) {
					if (makingqtycls.get(0).get("fmakingqty") != null
							&& Integer.parseInt(makingqtycls.get(0)
									.get("fmakingqty").toString()) > 0) {
						balanceqty = balanceqty
								+ Integer.parseInt(makingqtycls.get(0)
										.get("fmakingqty").toString());
						msg = msg
								+ "在生产数量："
								+ makingqtycls.get(0).get("fmakingqty")
										.toString() + ";";
					} else {
						msg = msg + "在生产数量：0;";
					}
				}
				// sql=" select sum(ifnull(t_ord_deliverorder.famount,0)) funsendqty from t_ord_deliverorder,t_sys_custrelationproduct where t_ord_deliverorder.fcusproductid=t_sys_custrelationproduct.fcustid and ifnull(fouted,0)=0 and t_sys_custrelationproduct.fproductdefid='"+productid+"' ";
				sql = " select sum(ifnull(t_ord_deliverorder.famount,0)-ifnull(t_ord_deliverorder.foutqty,0)) funsendqty from t_ord_deliverorder where fsupplierId = '"
						+ fsupplierId
						+ "' and ifnull(fouted,0)=0 and ifnull(ftype,0)<>1 and t_ord_deliverorder.fproductid='"
						+ productid + "' ";
				List<HashMap<String, Object>> unsendqtycls = VmipdtParamDao
						.QueryBySql(sql);
				if (unsendqtycls.size() > 0) {
					if (unsendqtycls.get(0).get("funsendqty") != null) {
						balanceqty = balanceqty
								- Integer.parseInt(unsendqtycls.get(0)
										.get("funsendqty").toString());
						msg = msg
								+ "未出库数量："
								+ unsendqtycls.get(0).get("funsendqty")
										.toString() + ";";
					} else {
						msg = msg + "未出库数量：0;";
					}
				}

				// 增加最小安全库存为0时，查询当前产品所有要货管理要货数量;
				int deliversAmt = 0;
				// String custproduct = "";
				// sql =
				// "select fid from t_bd_custproduct where fid in (select FCUSTID from t_sys_custrelationproduct where FPRODUCTDEFID='"+productid+"' )";
				// List<HashMap<String,Object>> data=
				// VmipdtParamDao.QueryBySql(sql);
				// if (data.size()>0)
				// {
				// HashMap o = data.get(0);
				// if(o.get("fid")!=null){
				// custproduct = o.get("fid").toString();
				// }
				// }
				sql = " select sum(ifnull(famount,0)) deliversAmt from t_ord_delivers where ifnull(falloted,0) = 0 and ifnull(ftype,0)<>1 and fproductid='"
						+ productid + "' ";
				List<HashMap<String, Object>> deliversinfo = VmipdtParamDao
						.QueryBySql(sql);
				if (deliversinfo.size() > 0) {
					if (deliversinfo.get(0).get("deliversAmt") != null) {
						deliversAmt = Integer.parseInt(deliversinfo.get(0)
								.get("deliversAmt").toString());
						if (deliversAmt > 0 && minstock == 0) {
							msg = msg + "未分配要货总数量:" + deliversAmt + ";";
						}
					}
				}
				
				
				if(ftype==0&&(fnewtype==2||fnewtype==4))//通知套装计算可用量
				{
					balanceqty=0;
					sql = "select p.fparentid,y.fsupplierid," +
						//"min(ifnull(s.fbalanceqty,0)/famount) fbalanceqtys,
						 " min((ifnull(s.fbalanceqty,0)+ifnull(s.fallotqty,0)-ifnull(s.lockamt,0))/famount) fbalanceqty ,min(ifnull(s.fallotqty,0)/famount) fallotqty "
						+" from  t_pdt_productdefproducts  p "
						+" left join t_bd_productcycle  y on y.fproductdefid=p.fproductid"
						+" left join usedbalanceqty_view s on s.fproductid=y.fproductdefid "
						+" and y.fsupplierid=s.fsupplierid"
						+" where y.fsupplierid='" +fsupplierId
						+"'and p.fparentid='" +productid
						+"' group by p.fparentid,y.fsupplierid ";
					List<HashMap<String, Object>> storecls = VmipdtParamDao
							.QueryBySql(sql);
					if (storecls.size() > 0) {
						if (storecls.get(0).get("fbalanceqty") != null) {
							balanceqty =((BigDecimal)storecls.get(0).get("fbalanceqty")).intValue();
							int fallotqty=(storecls.get(0).get("fallotqty")==null?0:((BigDecimal)storecls.get(0).get("fallotqty")).intValue());
						//	int fbalanceqtys=(storecls.get(0).get("fbalanceqtys")==null?0:((BigDecimal)storecls.get(0).get("fbalanceqtys")).intValue());

							msg = "可用数量：" + balanceqty + ";" +
									//"库存数量:"+fbalanceqtys+";
									"调拨在途数量："+fallotqty+";"+msg;
							balanceqty=balanceqty;
						} else {
							msg = "可用数量：0;调拨在途数量：0;"+msg;
						}
					}
				}

				if (ftype == 1) {// 下单类型为订单类型;
					if (minstock > balanceqty && minstock != 0) // 生成订单
					{
						sql = " update t_pdt_vmiproductparam set fdescription='会下单，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					} else if (minstock == 0 && deliversAmt > balanceqty
							&& deliversAmt > 0) {
						sql = " update t_pdt_vmiproductparam set fdescription='会下单，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					} else {
						sql = " update t_pdt_vmiproductparam set fdescription='不下单，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					}
				} else if (ftype == 0) {// 下单类型为通知类型;
					if (minstock > balanceqty && minstock != 0) // 生成订单
					{
						sql = " update t_pdt_vmiproductparam set fdescription='会发通知，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					} else if (minstock == 0 && deliversAmt > balanceqty
							&& deliversAmt > 0) {
						sql = " update t_pdt_vmiproductparam set fdescription='会发通知，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					} else {
						sql = " update t_pdt_vmiproductparam set fdescription='不用通知，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					}
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "下单成功", "", ""));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	// 原先的VMI自动下单,已废弃
	@RequestMapping("oldVmiPlan.do")
	public String vmiplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			if (isrunning) {
				throw new DJException("正在下单，请稍后再试！");
			}
			isrunning = true;
			ArrayList<Saleorder> solist=null;
			// TODO Auto-generated method stub
			// String
			// sql=" select t_pdt_vmiproductparam.fid,t_bd_customer.fid as fcustomerid,t_pdt_productdef.fassemble,t_pdt_productdef.fiscombinecrosssubs,t_pdt_productdef.fid as fproductid,t_pdt_vmiproductparam.fminstock,t_pdt_vmiproductparam.forderamount,t_pdt_productdef.fnewtype from t_pdt_vmiproductparam,t_pdt_productdef,t_bd_customer where t_pdt_productdef.feffect=1 and t_bd_customer.fid=t_pdt_productdef.fcustomerid and t_pdt_productdef.fid=t_pdt_vmiproductparam.fproductid ";
			String sql = " select fnumber from t_ord_saleorder  where fallot=0 and "
					+ "faudited=1 and exists (select fid from t_pdt_vmiproductparam "
					+ "where t_pdt_vmiproductparam.fproductid=t_ord_saleorder.fproductdefid) ";
			List<HashMap<String, Object>> vmipcls = VmipdtParamDao
					.QueryBySql(sql);
			if (vmipcls.size() > 0) {
				throw new DJException("存在已审核但未分配的生产订单");
			}
			
			sql = " select ifnull(t_pdt_vmiproductparam.ftype,0) ftype,t_pdt_vmiproductparam.fsupplierId,"
					+ "t_pdt_vmiproductparam.fid,t_bd_customer.fid as fcustomerid,"
					+ "t_pdt_productdef.fassemble,t_pdt_productdef.feffect,t_pdt_productdef.fishistory,"
					+ "t_pdt_productdef.fiscombinecrosssubs,t_pdt_productdef.fid as fproductid,"
					+ "t_pdt_vmiproductparam.fminstock,t_pdt_vmiproductparam.forderamount,"
					+ "t_pdt_productdef.fnewtype,t_pdt_productdef.fname as productname "
					+ "from t_pdt_vmiproductparam,t_pdt_productdef,t_bd_customer where   "
					+ "t_bd_customer.fid=t_pdt_productdef.fcustomerid "
					+ "and t_pdt_productdef.fid=t_pdt_vmiproductparam.fproductid ";
			vmipcls = VmipdtParamDao.QueryBySql(sql);

			// if (vmipcls.size()<=0)
			// {
			// reponse.getWriter().write(JsonUtil.result(false, "没有启用产品！", "",
			// ""));
			// return null;
			// }
			Calendar time = Calendar.getInstance();
			time.set(Calendar.MINUTE, 0);
			time.set(Calendar.SECOND, 0);
			time.set(Calendar.DATE, time.getTime().getDate() + 3);
			time.set(Calendar.HOUR_OF_DAY, 9);
			Date arriveDate = time.getTime();
			time.set(Calendar.HOUR_OF_DAY, 6);
			Date incomeTime = time.getTime();
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String msg = "";
			for (int i = 0; i < vmipcls.size(); i++) {
				HashMap<String, Object> info = vmipcls.get(i);
				int effect = (Integer) info.get("feffect") == null ? 0
						: (Integer) info.get("feffect");
				int ishistory = (Integer) info.get("fishistory") == null ? 0
						: (Integer) info.get("fishistory");
				if (ishistory == 1) {
					sql = " update t_pdt_vmiproductparam set fdescription='未下单，信息:历史版本产品不能下单！' where fid='"
							+ info.get("fid").toString() + "' ";
					VmipdtParamDao.ExecBySql(sql);
					continue;
				}
				if (effect == 0) {
					sql = " update t_pdt_vmiproductparam set fdescription='未下单，信息:禁用产品不能下单！' where fid='"
							+ info.get("fid").toString() + "' ";
					VmipdtParamDao.ExecBySql(sql);
					continue;
				}
				msg = "";
				String fsupplierId = info.get("fsupplierId").toString();
				int ftype = Integer.parseInt(info.get("ftype").toString());
				int fnewtype=Integer.parseInt(info.get("fnewtype").toString());//产品类型info.get("fnewtype")
				String productid = info.get("fproductid").toString();
				String customerid = info.get("fcustomerid").toString();
				int minstock = Integer.parseInt(info.get("fminstock")
						.toString());
				int orderamount = Integer.parseInt(info.get("forderamount")
						.toString());
				int balanceqty = 0;
				if(ftype==1||(ftype==0&&(fnewtype==1||fnewtype==3))){//除通知套装，计算库存
				sql = " select sum(ifnull(fbalanceqty,0)) fbalanceqty,sum(ifnull(fallotqty,0)) fallotqty  from t_inv_storebalance b where b.FSUPPLIERID = '"
						+ fsupplierId
						+ "' and b.fproductid='"
						+ productid
						+ "' and ftype = '" + ftype + "'";
				List<HashMap<String, Object>> balancecls = VmipdtParamDao
						.QueryBySql(sql);
				if (balancecls.size() > 0) {
					if (balancecls.get(0).get("fbalanceqty") != null) {
						balanceqty = Integer.parseInt(balancecls.get(0)
								.get("fbalanceqty").toString());
						int fallotqty=(balancecls.get(0).get("fallotqty")==null?0:Integer.parseInt(balancecls.get(0).get("fallotqty").toString()));
						msg = msg + "库存数量：" + balanceqty + ";调拨在途数量："+fallotqty+";";
						balanceqty=balanceqty+fallotqty;
					} else {
						msg = msg + "库存数量：0;调拨在途数量：0;";
					}
				}
				}
				sql = "select sum(case when famount<fstockinqty then 0 else famount-ifnull(fstockinqty,0) end ) fmakingqty from t_ord_productplan where fsupplierid = '"
						+ fsupplierId
						+ "' and faudited=1 and fcloseed =0 and fproductdefid='"
						+ productid + "' and fboxtype=0  ";
				List<HashMap<String, Object>> makingqtycls = VmipdtParamDao
						.QueryBySql(sql);
				if (makingqtycls.size() > 0) {
					if (makingqtycls.get(0).get("fmakingqty") != null
							&& Integer.parseInt(makingqtycls.get(0)
									.get("fmakingqty").toString()) > 0) {
						balanceqty = balanceqty
								+ Integer.parseInt(makingqtycls.get(0)
										.get("fmakingqty").toString());
						msg = msg
								+ "在生产数量："
								+ makingqtycls.get(0).get("fmakingqty")
										.toString() + ";";
					} else {
						msg = msg + "在生产数量：0;";
					}
				}
				// sql=" select sum(ifnull(t_ord_deliverorder.famount,0)) funsendqty from t_ord_deliverorder,t_sys_custrelationproduct where t_ord_deliverorder.fcusproductid=t_sys_custrelationproduct.fcustid and ifnull(fouted,0)=0 and t_sys_custrelationproduct.fproductdefid='"+productid+"' ";
//				sql = " select sum(ifnull(t_ord_deliverorder.famount,0)-ifnull(t_ord_deliverorder.foutqty,0)) funsendqty from t_ord_deliverorder where fsupplierId = '"
//						+ fsupplierId
//						+ "' and ifnull(fouted,0)=0 and ifnull(ftype,0)<>1 and t_ord_deliverorder.fproductid='"
//						+ productid + "' ";
				sql="select sum(fusedqty) funsendqty from t_inv_usedstorebalance u left join  t_ord_deliverorder  o  on u.fdeliverorderid=o.fid where  o.fsupplierId = '"+fsupplierId+"' and o.fproductid='"+productid+"' and u.fusedqty>0 and o.fboxtype=0 and u.ftype="+ftype;
				List<HashMap<String, Object>> unsendqtycls = VmipdtParamDao
						.QueryBySql(sql);
				if (unsendqtycls.size() > 0) {
					if (unsendqtycls.get(0).get("funsendqty") != null) {
						balanceqty = balanceqty
								- Integer.parseInt(unsendqtycls.get(0)
										.get("funsendqty").toString());
						msg = msg
								+ "待发货数量："
								+ unsendqtycls.get(0).get("funsendqty")
										.toString() + ";";
					} else {
						msg = msg + "待发货数量：0;";
					}
				}
				if(ftype==0&&(fnewtype==2||fnewtype==4))//通知套装计算可用量
				{
					balanceqty=0;
					sql = "select p.fparentid,y.fsupplierid," +
						//"min(ifnull(s.fbalanceqty,0)/famount) fbalanceqtys,
						 " min((ifnull(s.fbalanceqty,0)+ifnull(s.fallotqty,0)-ifnull(s.lockamt,0))/famount) fbalanceqty ,min(ifnull(s.fallotqty,0)/famount) fallotqty "
						+" from  t_pdt_productdefproducts  p "
						+" left join t_bd_productcycle  y on y.fproductdefid=p.fproductid"
						+" left join usedbalanceqty_view s on s.fproductid=y.fproductdefid "
						+" and y.fsupplierid=s.fsupplierid"
						+" where y.fsupplierid='" +fsupplierId
						+"'and p.fparentid='" +productid
						+"' group by p.fparentid,y.fsupplierid ";
					List<HashMap<String, Object>> storecls = VmipdtParamDao
							.QueryBySql(sql);
					if (storecls.size() > 0) {
						if (storecls.get(0).get("fbalanceqty") != null) {
							balanceqty =((BigDecimal)storecls.get(0).get("fbalanceqty")).intValue();
							int fallotqty=(storecls.get(0).get("fallotqty")==null?0:((BigDecimal)storecls.get(0).get("fallotqty")).intValue());
						//	int fbalanceqtys=(storecls.get(0).get("fbalanceqtys")==null?0:((BigDecimal)storecls.get(0).get("fbalanceqtys")).intValue());

							msg = "可用数量：" + balanceqty + ";" +
									//"库存数量:"+fbalanceqtys+";
									"调拨在途数量："+fallotqty+";"+msg;
							balanceqty=balanceqty;
						} else {
							msg = "可用数量：0;调拨在途数量：0;"+msg;
						}
					}
				}
				
				// 增加最小安全库存为0时，查询当前产品所有要货管理要货数量;
				int deliversAmt = 0;
				// String custproduct = "";
				// // sql =
				// "select fid from t_bd_custproduct where fid in (select FCUSTID from t_sys_custrelationproduct where FPRODUCTDEFID='"+productid+"' )";
				// sql =
				// "select fid from t_bd_custproduct where fid in (select fcustproductid from t_pdt_custrelation t left join t_pdt_custrelationentry tn on tn.fparentid=t.fid where fproductid='"+productid+"' )";
				// List<HashMap<String,Object>> data=
				// VmipdtParamDao.QueryBySql(sql);
				// if (data.size()>0)
				// {
				// HashMap o = data.get(0);
				// if(o.get("fid")!=null){
				// custproduct = o.get("fid").toString();
				// }
				// }
				sql = " select sum(ifnull(famount,0)) deliversAmt from t_ord_delivers where ifnull(falloted,0) = 0 and ifnull(ftype,0)<>1 and fproductid='"
						+ productid + "' ";
				List<HashMap<String, Object>> deliversinfo = VmipdtParamDao
						.QueryBySql(sql);
				if (deliversinfo.size() > 0) {
					if (deliversinfo.get(0).get("deliversAmt") != null) {
						deliversAmt = Integer.parseInt(deliversinfo.get(0)
								.get("deliversAmt").toString());
					}
				}

				if (ftype == 1) {// 下单类型为订单类型;
					 solist = new ArrayList<Saleorder>();
					String fordernumber = ServerContext.getNumberHelper()
							.getNumber("t_ord_saleorder", "Z", 4, false);

					if ("1".equals(info.get("fnewtype"))
							|| "3".equals(info.get("fnewtype"))) // 普通纸箱
					{
						if (minstock > balanceqty && minstock != 0) // 生成订单
						{
							Saleorder soinfo = new Saleorder();
							String orderEntryid = VmipdtParamDao.CreateUUid();
							soinfo.setFtype(1);
							soinfo.setFid(orderEntryid);
							soinfo.setForderid(VmipdtParamDao.CreateUUid());
							soinfo.setFcreatorid(userid);
							soinfo.setFcreatetime(new Date());
							soinfo.setFproductdefid(productid);
							soinfo.setFentryProductType(0);
							soinfo.setFlastupdatetime(new Date());
							soinfo.setFlastupdateuserid(userid);
							soinfo.setFamount(orderamount);
							soinfo.setFcustomerid(customerid);
							// soinfo.setFcustproduct(fcusproductid);
							soinfo.setFarrivetime(arriveDate);
							soinfo.setFbizdate(new Date());
							soinfo.setFaudited(1);
							soinfo.setFauditorid(userid);
							soinfo.setFaudittime(new Date());
							soinfo.setFamountrate(1);
							soinfo.setFordertype(1);
							soinfo.setFallot(0);
							soinfo.setFstockinqty(0);
							soinfo.setFstockoutqty(0);
							soinfo.setFstoreqty(0);

							if (info.get("FAssemble") != null
									&& info.get("FAssemble").toString()
											.equals("1")) {
								soinfo.setFassemble(1);
							} else {
								soinfo.setFassemble(0);
							}

							if (info.get("fiscombinecrosssubs") != null
									&& info.get("fiscombinecrosssubs")
											.toString().equals("1")) {
								soinfo.setFiscombinecrosssubs(1);
							} else {
								soinfo.setFiscombinecrosssubs(0);
							}

							soinfo.setFseq(1);
							soinfo.setFimportEas(0);
							soinfo.setFnumber(fordernumber);
							soinfo.setFsupplierid(fsupplierId);
							solist.add(soinfo);
							saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);//生成生产订单,（单个供应商则直接生成制造商订单与库存记录）
//							saleOrderDao.ExecSave(soinfo);
							sql = " update t_pdt_vmiproductparam set fdescription='已下单，信息："
									+ msg
									+ "' where fid='"
									+ info.get("fid").toString() + "' ";
							VmipdtParamDao.ExecBySql(sql);
						} else if (minstock == 0 && deliversAmt > balanceqty
								&& deliversAmt > 0) {

							Saleorder soinfo = new Saleorder();
							String orderEntryid = VmipdtParamDao.CreateUUid();
							soinfo.setFtype(1);
							soinfo.setFid(orderEntryid);
							soinfo.setForderid(VmipdtParamDao.CreateUUid());
							soinfo.setFcreatorid(userid);
							soinfo.setFcreatetime(new Date());
							soinfo.setFproductdefid(productid);
							soinfo.setFentryProductType(0);
							soinfo.setFlastupdatetime(new Date());
							soinfo.setFlastupdateuserid(userid);
							soinfo.setFamount(orderamount);
							soinfo.setFcustomerid(customerid);
							// soinfo.setFcustproduct(fcusproductid);
							soinfo.setFarrivetime(arriveDate);
							soinfo.setFbizdate(new Date());
							soinfo.setFaudited(1);
							soinfo.setFauditorid(userid);
							soinfo.setFaudittime(new Date());
							soinfo.setFamountrate(1);
							soinfo.setFordertype(1);
							soinfo.setFallot(0);
							soinfo.setFstockinqty(0);
							soinfo.setFstockoutqty(0);
							soinfo.setFstoreqty(0);

							if (info.get("FAssemble") != null
									&& info.get("FAssemble").toString()
											.equals("1")) {
								soinfo.setFassemble(1);
							} else {
								soinfo.setFassemble(0);
							}

							if (info.get("fiscombinecrosssubs") != null
									&& info.get("fiscombinecrosssubs")
											.toString().equals("1")) {
								soinfo.setFiscombinecrosssubs(1);
							} else {
								soinfo.setFiscombinecrosssubs(0);
							}

							soinfo.setFseq(1);
							soinfo.setFimportEas(0);
							soinfo.setFnumber(fordernumber);
							soinfo.setFsupplierid(fsupplierId);
							solist.add(soinfo);
							saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);//生成生产订单,（单个供应商则直接生成制造商订单与库存记录）
//							saleOrderDao.ExecSave(soinfo);
							sql = " update t_pdt_vmiproductparam set fdescription='已下单，信息："
									+ msg
									+ "' where fid='"
									+ info.get("fid").toString() + "' ";
							VmipdtParamDao.ExecBySql(sql);

						} else {
							sql = " update t_pdt_vmiproductparam set fdescription='未下单，信息："
									+ msg
									+ "' where fid='"
									+ info.get("fid").toString() + "' ";
							VmipdtParamDao.ExecBySql(sql);
						}

					} else if ("2".equals(info.get("fnewtype"))
							|| "4".equals(info.get("fnewtype"))) // 套装纸箱
					{
						String orderid = VmipdtParamDao.CreateUUid();
						if (minstock > balanceqty && minstock != 0) // 生成订单
						{
							// 套装
							// 一次性获取所有级次的：套装+子产品
							// 再按顺序加载即可

							List list = getAllProductSuit(productid);
							HashMap subInfo = null;

							for (int k = 0; k < list.size(); k++) {
								subInfo = (HashMap) list.get(k);

								Saleorder soinfo = new Saleorder();

								if (subInfo.get("FAssemble") != null
										&& subInfo.get("FAssemble").toString()
												.equals("1")) {
									soinfo.setFassemble(1);
								} else {
									soinfo.setFassemble(0);
								}

								if (subInfo.get("fiscombinecrosssubs") != null
										&& subInfo.get("fiscombinecrosssubs")
												.toString().equals("1")) {
									soinfo.setFiscombinecrosssubs(1);
								} else {
									soinfo.setFiscombinecrosssubs(0);
								}

								if (k == 0) {
									soinfo.setFamount(orderamount);
									soinfo.setFproductdefid(productid);
									soinfo.setFsuitProductId(productid);

								} else {

									soinfo.setFparentOrderEntryId(subInfo.get(
											"ParentOrderEntryId").toString());
									soinfo.setFamount(orderamount
											* new Integer(subInfo.get(
													"amountRate").toString()));
									soinfo.setFproductdefid(subInfo.get("fid")
											.toString());

								}
								soinfo.setFtype(1);
								soinfo.setFordertype(2);
								soinfo.setFentryProductType(new Integer(subInfo
										.get("entryProductType").toString()));
								soinfo.setFid(subInfo.get("orderEntryID")
										.toString());
								soinfo.setFseq((k + 1));
								soinfo.setFimportEas(0);
								soinfo.setFamountrate(new Integer(subInfo.get(
										"amountRate").toString()));
								soinfo.setForderid(orderid);
								soinfo.setFcreatorid(userid);
								soinfo.setFcreatetime(new Date());
								soinfo.setFlastupdatetime(new Date());
								soinfo.setFlastupdateuserid(userid);
								soinfo.setFnumber(fordernumber);
								soinfo.setFcustomerid(customerid);
								// soinfo.setFcustproduct(fcusproductid);
								soinfo.setFarrivetime(arriveDate);
								soinfo.setFbizdate(new Date());
								soinfo.setFaudited(1);
								soinfo.setFauditorid(userid);
								soinfo.setFaudittime(new Date());
								soinfo.setFallot(0);
								soinfo.setFsupplierid(fsupplierId);
								
								solist.add(soinfo);
//								HashMap<String, Object> params = saleOrderDao
//										.ExecSave(soinfo);
//								if (params.get("success") == Boolean.FALSE) {
//									throw new DJException("生成订单失败！");
//								}
							}
							saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);//生成生产订单,（单个供应商则直接生成制造商订单与库存记录）
							sql = " update t_pdt_vmiproductparam set fdescription='下单正常，库存数量为"
									+ balanceqty
									+ "' where fid='"
									+ info.get("fid").toString() + "' ";
							VmipdtParamDao.ExecBySql(sql);
						} else if (minstock == 0 && deliversAmt > balanceqty
								&& deliversAmt > 0) {
							// 套装
							// 一次性获取所有级次的：套装+子产品
							// 再按顺序加载即可

							List list = getAllProductSuit(productid);
							HashMap subInfo = null;

							for (int k = 0; k < list.size(); k++) {
								subInfo = (HashMap) list.get(k);

								Saleorder soinfo = new Saleorder();

								if (subInfo.get("FAssemble") != null
										&& subInfo.get("FAssemble").toString()
												.equals("1")) {
									soinfo.setFassemble(1);
								} else {
									soinfo.setFassemble(0);
								}

								if (subInfo.get("fiscombinecrosssubs") != null
										&& subInfo.get("fiscombinecrosssubs")
												.toString().equals("1")) {
									soinfo.setFiscombinecrosssubs(1);
								} else {
									soinfo.setFiscombinecrosssubs(0);
								}

								if (k == 0) {
									soinfo.setFamount(orderamount);
									soinfo.setFproductdefid(productid);
									soinfo.setFsuitProductId(productid);

								} else {

									soinfo.setFparentOrderEntryId(subInfo.get(
											"ParentOrderEntryId").toString());
									soinfo.setFamount(orderamount
											* new Integer(subInfo.get(
													"amountRate").toString()));
									soinfo.setFproductdefid(subInfo.get("fid")
											.toString());

								}
								soinfo.setFtype(1);
								soinfo.setFordertype(2);
								soinfo.setFentryProductType(new Integer(subInfo
										.get("entryProductType").toString()));
								soinfo.setFid(subInfo.get("orderEntryID")
										.toString());
								soinfo.setFseq((k + 1));
								soinfo.setFimportEas(0);
								soinfo.setFamountrate(new Integer(subInfo.get(
										"amountRate").toString()));
								soinfo.setForderid(orderid);
								soinfo.setFcreatorid(userid);
								soinfo.setFcreatetime(new Date());
								soinfo.setFlastupdatetime(new Date());
								soinfo.setFlastupdateuserid(userid);
								soinfo.setFnumber(fordernumber);
								soinfo.setFcustomerid(customerid);
								// soinfo.setFcustproduct(fcusproductid);
								soinfo.setFarrivetime(arriveDate);
								soinfo.setFbizdate(new Date());
								soinfo.setFaudited(1);
								soinfo.setFauditorid(userid);
								soinfo.setFaudittime(new Date());
								soinfo.setFallot(0);
								soinfo.setFsupplierid(fsupplierId);
								solist.add(soinfo);
//								HashMap<String, Object> params = saleOrderDao
//										.ExecSave(soinfo);
//								if (params.get("success") == Boolean.FALSE) {
//									throw new DJException("生成订单失败！");
//								}
							}
							saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);//生成生产订单,（单个供应商则直接生成制造商订单与库存记录）
							sql = " update t_pdt_vmiproductparam set fdescription='下单正常，库存数量为"
									+ balanceqty
									+ "' where fid='"
									+ info.get("fid").toString() + "' ";
							VmipdtParamDao.ExecBySql(sql);
						} else {
							sql = " update t_pdt_vmiproductparam set fdescription='库存大于最小值，不用下单' where fid='"
									+ info.get("fid").toString() + "' ";
							VmipdtParamDao.ExecBySql(sql);
						}
					}
					
				} else if (ftype == 0) {// 下单类型为通知类型;
					sql = " select FUSERID from t_bd_usersupplier where FSUPPLIERID='"
							+ fsupplierId + "' ";
					List<HashMap<String, Object>> usersupplier = VmipdtParamDao
							.QueryBySql(sql);

					if (minstock > balanceqty && minstock != 0) // 生成消息;
					{
						for (int j = 0; j < usersupplier.size(); j++) {
							String supplieruserid = "";
							if (usersupplier.get(j).get("FUSERID") != null) {
								supplieruserid = usersupplier.get(j)
										.get("FUSERID").toString();
							}
							Simplemessage sminfo = new Simplemessage();
							String SimplemessageID = VmipdtParamDao
									.CreateUUid();
							sminfo.setFid(SimplemessageID);
							sminfo.setFcontent("产品：" + info.get("productname")
									+ "库存不足，请及时入库; " + msg);
							sminfo.setFhaveReaded(0);
							// sminfo.setFtime(new Date());
							// sminfo.setFreceivingTime(new Date());
							sminfo.setFrecipient(supplieruserid);
							sminfo.setFremark(null);
							sminfo.setFsender(userid);
							sminfo.setFtime(new Date());
							simplemessageDao.ExecSave(sminfo);
						}
						sql = " update t_pdt_vmiproductparam set fdescription='已发通知，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					} else if (minstock == 0 && deliversAmt > balanceqty
							&& deliversAmt > 0) {

						for (int j = 0; j < usersupplier.size(); j++) {
							String supplieruserid = "";
							if (usersupplier.get(0).get("FUSERID") != null) {
								supplieruserid = usersupplier.get(0)
										.get("FUSERID").toString();
							}
							Simplemessage sminfo = new Simplemessage();
							String SimplemessageID = VmipdtParamDao
									.CreateUUid();
							sminfo.setFid(SimplemessageID);
							sminfo.setFcontent("最小库存量为0，当前产品所有要货数量大于实际库存，请及时备货; 当前产品所有要货数量:"
									+ deliversAmt + "," + msg);
							sminfo.setFhaveReaded(0);
							sminfo.setFreceivingTime(new Date());
							sminfo.setFrecipient(supplieruserid);
							sminfo.setFremark(null);
							sminfo.setFsender(userid);
							sminfo.setFtime(new Date());
							simplemessageDao.ExecSave(sminfo);
						}
						sql = " update t_pdt_vmiproductparam set fdescription='已发通知，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					} else {
						sql = " update t_pdt_vmiproductparam set fdescription='不用通知，信息："
								+ msg
								+ "' where fid='"
								+ info.get("fid").toString() + "' ";
						VmipdtParamDao.ExecBySql(sql);
					}
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "下单成功", "", ""));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		isrunning = false;
		return null;
	}

	/**
	 * 获取多级套装+子件，并且对“分录的产品类型”赋值
	 */
	public List getAllProductSuit(String fproductid){
		// ProductDefCollection productCols = new ProductDefCollection();
		List list = new ArrayList();
		// StringBuffer oql = new
		// StringBuffer("select *,Products.*,Products.product.* where id ='").append(id).append("'");
		// ProductDefInfo productInfo = getProductDefInfo(oql.toString());
		// stmt =
		// conn.prepareStatement("select fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'");
		// ResultSet productrs = stmt.executeQuery();
		String sql = "select fnewtype,FCombination,FAssemble from t_pdt_productdef where fid = '"
				+ fproductid + "'";
		List<HashMap<String, Object>> pdlist = VmipdtParamDao.QueryBySql(sql);
		if (pdlist.size() > 0) {
			HashMap productrs = pdlist.get(0);
			HashMap productInfo = new HashMap();
			productInfo.put("fid", fproductid);
			productInfo.put("amountRate", new Integer(1));
			productInfo.put("entryProductType", "1");
			productInfo.put("ParentOrderEntryId", null);
			if (productrs.get("fnewtype") != null) {
				productInfo.put("fnewtype", productrs.get("fnewtype")
						.toString());
			} else {
				productInfo.put("fnewtype", "0");
			}

			if (productrs.get("FCombination") != null) {
				productInfo.put("FCombination", productrs.get("FCombination")
						.toString());
			} else {
				productInfo.put("FCombination", "0");
			}

			productInfo.put("FAssemble", productrs.get("FAssemble").toString());
			productInfo.put("fiscombinecrosssubs", new Integer(0));
			getProductSuit(productInfo, list, null);
		}

		return list;
	}

	private void getProductSuit(HashMap productInfo, List list,
			String parentEntryId) {
		boolean isSuit = (productInfo.get("fnewtype").equals("2") || productInfo
				.get("fnewtype").equals("4"));
		String orderEntryID = saleOrderDao.CreateUUid();
		productInfo.put("orderEntryID", orderEntryID);
		if (!isSuit) {
			// 如果非套装，自己的分录ID，父分录Id取传入的参数，直接放入Collection
			// BOSUuid orderEntryID = BOSUuid.create(new
			// SaleOrderEntryInfo().getBOSType());
			productInfo.put("ParentOrderEntryId", parentEntryId);
			list.add(productInfo);
		} else {
			// 如果是套装，自己的分录Id，父分录Id，总套为null，非总套取参数，先把自己放入Collection，再循环递归调用自己的子件

			// 如果不是首次进入递归，套装的“分录的产品类型”== 非总套
			if (productInfo.get("entryProductType").toString().equals("1")) {
				// nothing
			} else {
				productInfo.put("entryProductType", "2");
				productInfo.put("ParentOrderEntryId", parentEntryId);
			}

			list.add(productInfo);

			// 子件“分录的产品类型”
			String subEntryProductType = "";
			boolean isassemble = false;
			if (productInfo.get("FCombination") != null
					&& productInfo.get("FCombination").toString().equals("1")) {
				// preSuitProductType = 1;
				subEntryProductType = "7"; // 合并下料子件
			} else if (productInfo.get("FAssemble") != null
					&& productInfo.get("FAssemble").toString().equals("1")) {
				// preSuitProductType = 2;
				subEntryProductType = "6"; // 组装套装子件
				isassemble = true;
			} else {
				// preSuitProductType = 0;
				subEntryProductType = "5"; // 普通套装子件
			}

			// ProductDefProductCollection subCols = productInfo.getProducts();
			// stmt =
			// conn.prepareStatement("select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'");
			// ResultSet productrs = stmt.executeQuery();
			String sql = "select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"
					+ productInfo.get("fid") + "'";
			List<HashMap<String, Object>> pdlist = VmipdtParamDao
					.QueryBySql(sql);
			for (int i = 0; i < pdlist.size(); i++) {
				HashMap productrs = pdlist.get(i);

				// stmt =
				// conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'");
				// ResultSet subproductrs = stmt.executeQuery();
				sql = "select fid,fnewtype,FCombination,FAssemble,fiscombinecrosssubs from t_pdt_productdef where fid = '"
						+ productrs.get("FProductID") + "'";
				List<HashMap<String, Object>> pdslist = VmipdtParamDao
						.QueryBySql(sql);
				if (pdslist.size() > 0) {
					HashMap subproductrs = pdslist.get(0);
					HashMap subInfo = new HashMap();
					subInfo.put("fid", subproductrs.get("fid"));
					subInfo.put("amountRate",
							new Integer(productrs.get("FAmount").toString())
									* new Integer(productInfo.get("amountRate")
											.toString()));
					if (subproductrs.get("fnewtype") != null) {
						subInfo.put("fnewtype", subproductrs.get("fnewtype")
								.toString());
					} else {
						subInfo.put("fnewtype", "0");
					}

					if (subproductrs.get("FCombination") != null) {
						subInfo.put("FCombination",
								subproductrs.get("FCombination").toString());
					} else {
						subInfo.put("FCombination", "0");
					}

					subInfo.put("FAssemble", subproductrs.get("FAssemble")
							.toString());
					subInfo.put("fiscombinecrosssubs",
							subproductrs.get("fiscombinecrosssubs").toString());
					// 子件的“分录的产品类型”
					subInfo.put("entryProductType", subEntryProductType);
					if (isassemble) {
						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
					} else {
						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
					}

					getProductSuit(subInfo, list, orderEntryID);
				}

			}
			// while (productrs.next())
			// {
			// stmt =
			// conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.getString("FProductID")+"'");
			// ResultSet subproductrs = stmt.executeQuery();
			// if (subproductrs.next())
			// {
			// HashMap subInfo = new HashMap();
			// subInfo.put("fid",subproductrs.getString("fid"));
			// subInfo.put("amountRate",productrs.getInt("FAmount") * new
			// Integer(productInfo.get("amountRate").toString()));
			// subInfo.put("fnewtype", subproductrs.getString("fnewtype"));
			// subInfo.put("ftype", subproductrs.getString("ftype"));
			// subInfo.put("FCombination",
			// subproductrs.getString("FCombination"));
			// subInfo.put("FAssemble", subproductrs.getString("FAssemble"));
			// //子件的“分录的产品类型”
			// subInfo.put("entryProductType",subEntryProductType);
			// if(isassemble){
			// subInfo.put("FisassembleSuitSub", Boolean.TRUE);
			// }else{
			// subInfo.put("FisassembleSuitSub", Boolean.FALSE);
			// }
			//
			// getProductSuit(conn,stmt,subInfo,list,orderEntryID);
			// }
			// }

		}
	}

	@RequestMapping("/EffectVmipdtparamList")
	public String EffectVmipdtparamList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		String effect = request.getParameter("feffect");
		try {
			if(effect==null||"".equals(effect)||!DataUtil.numberCheck(effect)){
				throw new DJException("参数传递有误！");
			}
			int feffect = Integer.parseInt(effect);
			String hql = "Update VmipdtParam set fefected=" + feffect
					+ " where fid in " + fidcls;
			VmipdtParamDao.ExecByHql(hql);
			result = JsonUtil.result(true,
					(feffect == 1 ? "启用" : "禁用") + "成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping(value = "/vmitoexcel")
	public String vmitoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			request.setAttribute("nolimit", 0);
			String sql = (String) request.getSession().getAttribute("vmipdtParamListQuery");
			ListResult result = VmipdtParamDao.QueryFilterList(sql, request);
			HashMap<String, String> map = new HashMap<>();
			map.put("fcreator", "创建人");
			map.put("fcreatetime", "创建时间");
			map.put("flastupdater", "最后修改人");
			map.put("flastupdatetime", "最后修改时间");
			map.put("fcustname", "客户名称");
			map.put("fpdtname", "产品名称");
			map.put("fmaxstock", "最大库存量");
			map.put("forderamount", "下单批量");
			map.put("fminstock", "最小库存量");
			map.put("fdescription", "描述");
			ExcelUtil.toexcel(reponse, result,map);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}



}