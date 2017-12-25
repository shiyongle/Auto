package Com.Controller.Inv;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Dao.Inv.IWarehouseDao;
import Com.Dao.Inv.IWarehouseSitesDao;
import Com.Entity.Inv.Warehouse;
import Com.Entity.Inv.Warehousesites;
import Com.Entity.System.Useronline;

@Controller
public class WarehouseController {
	Logger log = LoggerFactory.getLogger(WarehouseController.class);
	@Resource
	private IWarehouseDao warehouseDao;
	@Resource
	private IWarehouseSitesDao warehouseSitesDao;

	@RequestMapping(value = "/SaveWarehouse")
	public String SaveWarehouse(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Warehouse pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = warehouseDao.Query(fid);
			} else {
				pinfo = new Warehouse();
				pinfo.setFid(fid);
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
				pinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_bd_warehouse", "CK", 2, false));
			}
			pinfo.setFlastupdatetime(new Date());
			pinfo.setFlastupdateuserid(userid);
			pinfo.setFname(request.getParameter("fname"));
//			pinfo.setFnumber(request.getParameter("fnumber"));
			pinfo.setFsimplename(request.getParameter("fsimplename"));
			pinfo.setFdescription(request.getParameter("fdescription"));
			pinfo.setFcontrollerid(request.getParameter("fcontrollerid"));
			pinfo.setCfaddresid(request.getParameter("cfaddresid"));
			pinfo.setFoutstorage(new BigDecimal(request
					.getParameter("foutstorage")));
			pinfo.setFinstorage(new BigDecimal(request
					.getParameter("finstorage")));
			pinfo.setCffreightprice(new BigDecimal(request
					.getParameter("cffreightprice")));
			pinfo.setFwarehousetype(Integer.valueOf(request
					.getParameter("fwarehousetype")));
			HashMap<String, Object> params = warehouseDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = "{success:true,msg:'保存成功!'}";
			} else {
				result = "{success:false,msg:'保存失败!'}";
			}
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/GetWarehouses")
	public String GetWarehouses(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT w.fid, w.fnumber,w.fname,w.fsimplename,w.fcontrollerid,ifnull(u.fname,'') as ufname,w.cfaddresid, ifnull(d.fdetailaddress,'') as dfname,w.fdescription,w.fcreatorid, u1.fname as cfname,u2.fname as lfname ,w.fcreatetime,w.flastupdateuserid,w.flastupdatetime,w.foutstorage,w.finstorage,w.fwarehousetype,w.cffreightprice FROM t_bd_warehouse w left join  t_sys_user u on u.fid= w.fcontrollerid left join t_bd_address d on d.fid=w.cfaddresid left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user  u2 on u2.fid= w.flastupdateuserid ";
			ListResult result = warehouseDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/GetWarehouseInfo")
	public String GetWarehouseInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT w.fid,w.fnumber,w.fname,w.fsimplename,w.fcontrollerid as fcontrollerid_fid,ifnull(u.fname,'') as fcontrollerid_fname,w.cfaddresid as cfaddresid_fid, ifnull(d.fname,'') as cfaddresid_fname,w.fdescription,w.foutstorage,w.finstorage,w.fwarehousetype,w.cffreightprice FROM t_bd_warehouse w left join  t_sys_user u on u.fid= w.fcontrollerid left join t_bd_address d on d.fid=w.cfaddresid ";
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				sql += " where w.fid='" + fid + "'";
			}
			List<HashMap<String, Object>> sList = warehouseDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/DeleteWarehouse")
	public String DeleteWarehouse(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM Warehouse where fid in " + fidcls;
			warehouseDao.ExecByHql(hql);
			result = "{success:true,msg:'删除成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping(value = "/warehousetoexcel")
	public String warehousetoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "SELECT w.fid, w.fnumber '编码',w.fname '名称',w.fsimplename '简称',w.fcontrollerid '管理员ID',ifnull(u.fname,'') as '管理员名称',w.cfaddresid '地址ID', ifnull(d.fdetailaddress,'') as '详细地址',w.fdescription '描述', u1.fname as '创建人',u2.fname as '修改人' ,w.fcreatetime '创建时间',w.flastupdatetime '修改时间',w.foutstorage '出库计件(m2)',w.finstorage '入库计件(m2)',w.fwarehousetype '仓库类型 ',w.cffreightprice '运费单价'  FROM t_bd_warehouse w left join  t_sys_user u on u.fid= w.fcontrollerid left join t_bd_address d on d.fid=w.cfaddresid left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user  u2 on u2.fid= w.flastupdateuserid ";
			ListResult result = warehouseDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/SaveWarehouseSite")
	public String SaveWarehouseSite(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Warehousesites pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = warehouseSitesDao.Query(fid);
			} else {
				pinfo = new Warehousesites();
				pinfo.setFid(fid);
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
				pinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_bd_warehousesites", "KW", 2, false));
			}
			pinfo.setFlastupdatetime(new Date());
			pinfo.setFlastupdateuserid(userid);
			pinfo.setFname(request.getParameter("fname"));
//			pinfo.setFnumber(request.getParameter("fnumber"));

			pinfo.setFremark(request.getParameter("fremark"));
			pinfo.setFparentid(request.getParameter("fparentid"));

			pinfo.setFoutstoreprice(new BigDecimal(request
					.getParameter("foutstoreprice")));
			pinfo.setFinstoreprice(new BigDecimal(request
					.getParameter("finstoreprice")));
			pinfo.setFarea(new BigDecimal(request.getParameter("farea")));
			pinfo.setFaddress(request.getParameter("faddress"));
			HashMap<String, Object> params = warehouseSitesDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = "{success:true,msg:'保存成功!'}";
			} else {
				result = "{success:false,msg:'保存失败!'}";
			}
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/DeleteWarehouseSites")
	public String DeleteWarehouseSites(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM Warehousesites where fid in " + fidcls;
			warehouseDao.ExecByHql(hql);
			result = "{success:true,msg:'删除成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/GetWarehouseSites")
	public String GetWarehouseSites(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT s.fid,s.fname,s.fnumber,s.fcreatetime,s.fcreatorid,u1.fname as cfname,s.flastupdateuserid,u2.fname as lfname,s.flastupdatetime,s.faddress,"
					+ " fparentid,ifnull(w.fname,'') as wfname,fremark,finstoreprice,foutstoreprice,farea FROM t_bd_warehousesites  s "
					+ " left join t_bd_warehouse w on w.fid=s.fparentid"
					+ " left join t_sys_user u1 on u1.fid=s.fcreatorid"
					+ " left join t_sys_user u2 on u2.fid=s.flastupdateuserid ";
			ListResult result = warehouseDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping(value = "/GetWarehouseSiteInfo")
	public String GetWarehouseSiteInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT s.fid,s.fname,s.fnumber,s.faddress, fparentid as fparentid_fid,ifnull(w.fname,'') as fparentid_fname,fremark,finstoreprice,foutstoreprice,farea " +
					"FROM t_bd_warehousesites  s left join t_bd_warehouse w on w.fid=s.fparentid";
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				sql += " where s.fid='" + fid + "'";
			}
			List<HashMap<String, Object>> sList = warehouseDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/warehouseSitetoexcel")
	public String warehouseSitetoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "SELECT s.fid,s.fname '名称',s.fnumber '编码',s.fcreatetime  '创建时间',u1.fname as '创建人',u2.fname as '修改人',s.flastupdatetime '修改时间' ,s.faddress '地址',"
					+ " fparentid '仓库ID',ifnull(w.fname,'') as '所属仓库名称',fremark '备注',finstoreprice '入库计件(m2)',foutstoreprice '出库计件(m2)',farea '容量(m2)' FROM t_bd_warehousesites  s "
					+ " left join t_bd_warehouse w on w.fid=s.fparentid"
					+ " left join t_sys_user u1 on u1.fid=s.fcreatorid"
					+ " left join t_sys_user u2 on u2.fid=s.flastupdateuserid ";
			ListResult result = warehouseDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
}