package Com.Controller.System;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import Com.Dao.System.ISupplierDao;
import Com.Entity.System.Customer;
import Com.Entity.System.Supplier;

@Controller
public class SupplierController {
	Logger log = LoggerFactory.getLogger(SupplierController.class);
	@Resource
	private ISupplierDao SupplierDao;

	@RequestMapping("/SaveSupplier")
	public String SaveSupplier(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String result = "";
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userid = "";
		// String userid =
		// ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			request.setCharacterEncoding("utf-8");
			reponse.setCharacterEncoding("utf-8");
			Supplier info = new Supplier();
			info.setFaddress(request.getParameter("faddress"));
			info.setFid(request.getParameter("fid"));
			
			//制造商仓库、库位修改时增加检测库存，控制有库存的仓库和库位不能修改；
			if(request.getParameter("fid")!=null && !request.getParameter("fid").equals("")){
				info = SupplierDao.Query(request.getParameter("fid"));
				if(request.getParameter("fwarehouseid") == null || request.getParameter("fwarehouseid").equals("")){
					throw new DJException("仓库不能为空！");
				}else{
					if(!request.getParameter("fwarehouseid").equals(info.getFwarehouseid())){
						String sql=" select sum(ifnull(fbalanceqty,0)) fbalanceqty from t_inv_storebalance b where b.FSUPPLIERID = '"+request.getParameter("fid")+"' and b.FWarehouseID='"+info.getFwarehouseid()+"'";
						List<HashMap<String, Object>> balancecls=SupplierDao.QueryBySql(sql);
						if (balancecls.size()>0)
						{
							if(balancecls.get(0).get("fbalanceqty")!=null)
							{
								if(Integer.parseInt(balancecls.get(0).get("fbalanceqty").toString())>0){
									throw new DJException("制造商该仓库有库存不能修改仓库！");
								}
							}
						}
					}
				}
				
				if(request.getParameter("fwarehousesiteid") == null || request.getParameter("fwarehousesiteid").equals("")){
					throw new DJException("库位不能为空！");
				}else{
					if(!request.getParameter("fwarehousesiteid").equals(info.getFwarehousesiteid())){
						String sql=" select sum(ifnull(fbalanceqty,0)) fbalanceqty from t_inv_storebalance b where b.FSUPPLIERID = '"+request.getParameter("fid")+"' and b.FWarehouseID='"+info.getFwarehouseid()+"' and FWarehouseSiteID = '"+request.getParameter("fwarehousesiteid")+"'";
						List<HashMap<String, Object>> balancecls=SupplierDao.QueryBySql(sql);
						if (balancecls.size()>0)
						{
							if(balancecls.get(0).get("fbalanceqty")!=null)
							{
								if(Integer.parseInt(balancecls.get(0).get("fbalanceqty").toString())>0){
									throw new DJException("制造商该库位有库存不能修改库位！");
								}
							}
						}
					}
				}
			}
			
			info.setFname(request.getParameter("fname"));
			if(StringUtils.isEmpty(info.getFnumber())){
			info.setFnumber(ServerContext.getNumberHelper().getNumber("t_sys_supplier", "ZH", 2, false));
			}
			else
			{
				info.setFnumber(request.getParameter("fnumber"));
			}
			info.setFcountry(request.getParameter("fcountry"));
			info.setFcity(request.getParameter("fcity"));
			info.setFartificialperson(request.getParameter("fartificialperson"));
			info.setFbarcode(request.getParameter("fbarcode"));
			info.setFbizregisterno(request.getParameter("fbizregisterno"));
			info.setFbusiexequatur(request.getParameter("fbusiexequatur"));
			info.setFbusilicence(request.getParameter("fbusilicence"));
			info.setFcreatorid(request.getParameter("fcreatorid").isEmpty() ? userid
					: request.getParameter("fcreatorid"));
			info.setFdescription(request.getParameter("fdescription"));
			info.setFemail(request.getParameter("femail"));
			info.setFforeignname(request.getParameter("fforeignname"));
			info.setFgspauthentication(request
					.getParameter("fgspauthentication"));
			info.setFlastupdatetime(new Date());
			info.setFlastupdateuserid(userid);
			info.setFmnemoniccode(request.getParameter("fmnemoniccode"));
			info.setFsimplename(request.getParameter("fsimplename"));
			info.setFtaxregisterno(request.getParameter("ftaxregisterno"));
			info.setFtel(request.getParameter("ftel"));
			info.setFwarehouseid(request.getParameter("fwarehouseid"));
			info.setFwarehousesiteid(request.getParameter("fwarehousesiteid"));
			info.setFcreatetime(!request.getParameter("fcreatetime").isEmpty() ? f
					.parse(request.getParameter("fcreatetime")) : new Date());
			info.setFaddress(request.getParameter("faddress"));
			info.setFisManageStock(new Integer((StringUtils.isEmpty(request.getParameter("fisManageStock"))?"0":request.getParameter("fisManageStock"))));
			HashMap<String, Object> params = SupplierDao.ExecSave(info);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"保存成功!", "", "");
				if(SupplierDao.QueryExistsBySql("select 1 from t_bd_customer where fid = '"+request.getParameter("fid")+"'")){
//					setCustomer(info);
					Supplier su = info;
					String sql = String.format("update t_bd_customer set fid='%s',fnumber='%s',fname='%s',fartificialperson='%s',ftxregisterno='%s',fmnemoniccode='%s'," +
							"faddress='%s',fsimplename='%s',fforeignname='%s',fbizregisterno='%s',fbusilicence='%s',fbusiexequatur='%s',fgspauthentication='%s'," +
							"fbarcode='%s',fcountryid='%s',fusedstatus=%s where fid ='%s'",su.getFid(),su.getFnumber(),su.getFname(),su.getFartificialperson(),su.getFtaxregisterno(),
							su.getFmnemoniccode(),su.getFaddress(),su.getFsimplename(),su.getFforeignname(),su.getFbizregisterno(),su.getFbusilicence(),su.getFbusiexequatur(),
							su.getFgspauthentication(),su.getFbarcode(),su.getFcountry(),su.getFusedstatus()==null?0:su.getFusedstatus(),su.getFid());
					SupplierDao.ExecBySql(sql);
				}
			} else {
				result = JsonUtil.result(false,"保存失败!", "", "");
			}

		} catch (Exception e) {

			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/GetSupplierList")
	public String GetSupplierList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String result = "";
			String sql = "select fisManageStock,fid,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fname,fnumber,fdescription,fsimplename,fartificialperson,fbarcode,fbusiexequatur,fbizregisterno,fbusilicence,fgspauthentication,ftaxregisterno,fusedstatus,fmnemoniccode,fforeignname,faddress,ftel,femail,fcountry,fcity FROM t_sys_supplier where 1=1 "+SupplierDao.QueryFilterByUser(request, null, "fid");
			ListResult lresult;

			lresult = SupplierDao.QueryFilterList(sql, request);
			List<HashMap<String, Object>> sList = lresult.getData();
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/GetSupplierInfo")
	public String GetSupplierInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String sql = "select s.fisManageStock,s.fid,s.fcreatorid,s.fwarehouseid fwarehouseid_fid, ifnull(w1.fname,'') fwarehouseid_fname,s.fwarehousesiteid fwarehousesiteid_fid,ifnull(w2.fname,'') fwarehousesiteid_fname, s.fcreatetime,s.flastupdateuserid,s.flastupdatetime,s.fname,s.fnumber,s.fdescription,s.fsimplename,s.fartificialperson,s.fbarcode,s.fbusiexequatur,s.fbizregisterno,s.fbusilicence,s.fgspauthentication,s.ftaxregisterno,s.fusedstatus,s.fmnemoniccode,s.fforeignname,s.faddress,s.ftel,s.femail,s.fcountry,s.fcity FROM t_sys_supplier s" +
				" left join t_bd_warehouse w1 on w1.fid=s.fwarehouseid left join t_bd_warehousesites w2 on w2.fid=s.fwarehousesiteid" +
				" where s.fid = '"
				+ request.getParameter("fid") + "'";
		List<HashMap<String, Object>> sList = SupplierDao.QueryBySql(sql);
		reponse.setCharacterEncoding("utf-8");
		// reponse.getWriter().write(JsonUtil.result(true,"",SupplierDao.QueryCountBySql(sql),sList));
		reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		return null;

	}

	@RequestMapping("/EffectSupplierList")
	public String EffectSupplierList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		int feffect = Integer.parseInt(request.getParameter("feffect"));
		try {
			String hql = "Update Supplier set fusedstatus=" + feffect
					+ " where fid in " + fidcls;
			SupplierDao.ExecByHql(hql);
			result = JsonUtil.result(true,(feffect == 1 ? "启用" : "禁用")+ "成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping("/DelSupplierList")
	public String DelSupplierList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		
		if(DataUtil.isforeignKeyConstraintLegal("t_ord_productplan", "FSUPPLIERID", SupplierDao, fidcls)) {
			try {
				String hql = " FROM Supplier where fusedstatus=1 and fid in "
						+ fidcls;
				List<Supplier> sList = SupplierDao.QuerySysUsercls(hql);
				if (sList.size() > 0) {
					result = JsonUtil.result(false,"不能删除已经启用的用户!", "", "");
				} else {
					hql = "Delete FROM Supplier where fid in " + fidcls;
					SupplierDao.ExecByHql(hql);
					result = JsonUtil.result(true,"删除成功!", "", "");
				}
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
				log.error("DelSupplierList error", e);
			}
			
		} else {
			result = "{success:false,msg:'" + "违法外键约束，本实体已被其他地方引用" + "'}";
		}

		reponse.getWriter().write(result);

		return null;

	}

	private String GetJSON(List<Supplier> sList) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "";
		int index = 0;
		for (Supplier info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:'" + info.getFid() + "',fname:'"
					+ info.getFname() + "',fnumber:'" + info.getFnumber()
					+ "',feffect:'" + (info.getFusedstatus() == 1 ? "是" : "否")
					+ "',fcountry:'" + info.getFcountry() + "',femail:'"
					+ info.getFemail() + "',ftel:'" + info.getFtel()
					+ "',fcreatetime:'" + f.format(info.getFcreatetime())
					+ "'}";
			index++;
		}
		return result;

	}
	
	
	@RequestMapping(value = "/suppliertoexcel")
	public String suppliertoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select fid,fcreatetime '创建时间',flastupdatetime '修改时间',fname '供应商名称',fnumber '编码',fdescription '描述' ,fsimplename '简称',fartificialperson '法人代表',fbarcode '条形码',fbusiexequatur '经营许可证' ,fbizregisterno '工商注册号',fbusilicence '营业执照',fgspauthentication 'GSP认证',ftaxregisterno '税务登记号',fusedstatus '状态',fmnemoniccode '助记码' ,fforeignname '外文名称',faddress '地址',ftel '手机',femail '邮箱' FROM t_sys_supplier where 1=1 "+SupplierDao.QueryFilterByUser(request, null, "fid");
			ListResult result = SupplierDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/setSupplierToCustomer")
public void setSupplierToCustomer(HttpServletRequest request,
		HttpServletResponse reponse) throws IOException{
	String fids = request.getParameter("fids");
	try {
		fids = "'"+fids.replace(",", "','")+"'";
		String sql = "select 1 from t_bd_customer where fid in("+fids+")";
		if(SupplierDao.QueryExistsBySql(sql)){
			throw new DJException("制造商信息已经导入，不能重复导入！");
		}
		sql = "from Supplier where fid in ("+fids+")";
		List<Supplier> list = SupplierDao.QueryByHql(sql);
		for(Supplier su : list){
			setCustomer(su);
		}
		reponse.getWriter().write(JsonUtil.result(true, "导入成功！", "",""));
	} catch (DJException e) {
		// TODO: handle exception
		reponse.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
	}
}
	public void setCustomer(Supplier su){
		try {
			Customer cu = new Customer();
			cu.setFid(su.getFid());
			cu.setFnumber(su.getFnumber());//编码
			cu.setFname(su.getFname());//名称
			cu.setFartificialperson(su.getFartificialperson());//法人代表
			cu.setFtxregisterno(su.getFtaxregisterno());//税务登记号
			cu.setFmnemoniccode(su.getFmnemoniccode());//助记码
			cu.setFaddress(su.getFaddress());//地址
			cu.setFsimplename(su.getFsimplename());//简称
			cu.setFforeignname(su.getFforeignname());//外文名称
			cu.setFbizregisterno(su.getFbizregisterno());//工商注册号
			cu.setFbusilicence(su.getFbusilicence());//营业执照
			cu.setFbusiexequatur(su.getFbusiexequatur());//经营许可证
			cu.setFgspauthentication(su.getFgspauthentication());//GSP认证
			cu.setFbarcode(su.getFbarcode());//条形码
			cu.setFcountryid(su.getFcountry());//国家
			cu.setFcreatetime(su.getFcreatetime());
			cu.setFlastupdatetime(su.getFlastupdatetime());
			cu.setFusedstatus(su.getFusedstatus()==null?0:su.getFusedstatus());//状态
			SupplierDao.saveOrUpdate(cu);
		} catch (DJException e) {
			// TODO: handle exception
		}
	}
	@RequestMapping("IsCustomer")
	public void IsCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fids = request.getParameter("fids");
			fids = "'"+fids.replace(",", "','")+"'";
			String sql = "select 1 from t_bd_customer where fid in("+fids+")";
			if(SupplierDao.QueryExistsBySql(sql)){
				throw new DJException("制造商信息已经导入，不能重复导入！");
			}
			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
}
