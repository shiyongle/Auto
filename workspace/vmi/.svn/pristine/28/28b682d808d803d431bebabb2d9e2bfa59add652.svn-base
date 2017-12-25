package Com.Controller.Inv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.Inv.IProductcheckDao;
import Com.Dao.Inv.IProductcheckitemDao;
import Com.Entity.Inv.Productcheck;


@Controller
public class ProductcheckController {
	Logger log = LoggerFactory.getLogger(ProductcheckController.class);
	@Resource
	private IProductcheckDao productcheckDao;
	@Resource
	private IProductcheckitemDao productcheckitemDao;
	@RequestMapping(value = "/gainProductchecks")
	public String gainProductchecks(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			
			request.setAttribute("djsort","tip.fupdatetime desc");
			
			String sql = " SELECT tip.fid, DATE_FORMAT(tip.fcreatetime, '%Y-%m-%d %H:%i')fcreatetime, DATE_FORMAT(tip.fupdatetime, '%Y-%m-%d %H:%i')fupdatetime, tip.fuserid, tip.fstate, tip.fremark, tip.fnumber, tsy.fname username FROM t_inv_productcheck tip left join t_sys_user tsy on tip.fuserid = tsy.fid "
					+ String.format("  where tip.fuserid =  '%s' ",
							MySimpleToolsZ.getMySimpleToolsZ()
									.getCurrentUserId(request));
			
			ListResult result = productcheckDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping(value = "/updateProductcheckFremark")
	public String updateProductcheckFremark(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try{
		String fid = request.getParameter("fid");
		Date dateT = new Date();
		Productcheck productcheck = productcheckDao.Query(fid);
		
		String fremark = request.getParameter("fremark");
		productcheck.setFupdatetime(dateT);
		productcheck.setFremark(fremark);
		productcheckDao.ExecSave(productcheck);
		reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping(value = "/saveProductcheck")
	public String saveProductcheck(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			Productcheck productcheck = buildProductcheck(request, productcheckDao);
			
			productcheckDao.ExecSave(productcheck);
			
			String fid = request.getParameter("fid");
			String sql = "DELETE FROM t_inv_productcheckitem WHERE fproductcheckid='" +fid+"'";
			productcheckDao.ExecBySql(sql);
			productcheckitemDao.ExecSaveProductcheckitem(request);
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	private Productcheck buildProductcheck(HttpServletRequest request, IBaseDao dao) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String fid = request.getParameter("fid");
		
		String action = request.getParameter("action");
		
		Productcheck productcheck = null;
		
		String timeValue = request.getParameter("timeValue");
		
		String fremark = request.getParameter("fremark");
		
		Date dateT = new Date();
		
//		if (action.equals("update")) {
//			
//			productcheck = productcheckDao.Query(fid);
//			
//			//there is only update state can be set remark.
//			productcheck.setFremark(request.getParameter("fremark"));
//			
//		} else if(action.equals("create")) {
		
			productcheck = new Productcheck(fid);
			try {
			productcheck.setFnumber(dao.getFnumber("t_inv_productcheck", "", 3));
			if(timeValue.equals("")){
				
					productcheck.setFcreatetime(sdf.parse(sdf.format(dateT)));
					
			}else{
				
					Date date=sdf.parse(timeValue);
					productcheck.setFcreatetime(date);
				
				
			}
			
				productcheck.setFupdatetime(sdf.parse(sdf.format(dateT)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			productcheck.setFremark(fremark);
			productcheck.setFuserid(MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request));
//		} 
		
//		productcheck.setFupdatetime(dateT);
		
		//avoid unexpectedly fstateS change
		String fstateS = (request.getParameter("fstate"));
	
		if (fstateS != null) {
			
			productcheck.setFstate(Byte.valueOf(fstateS));
			
		}
		
		return productcheck;
	}
	
	@RequestMapping(value = "/productchecktoexcel")
	public String productchecktoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql= "  SELECT tip.fnumber '单据编号',tip.fcreatetime '盘点日期', tip.fupdatetime '修改日期',  tsy.fname '操作人', tip.fstate '状态', tip.fremark '备注' FROM t_inv_productcheck tip left join t_sys_user tsy on tip.fuserid = tsy.fid  ";
			
			ListResult result = productcheckDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/calibrationTheInventory")
	public String calibrationTheInventory(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			
			productcheckDao.ExecCalibrationTheInventory(request);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/DeleteProductcheck")
	public String DeleteProductcheck(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fidcls");
		
		String fidsS = "('" + fids.replace(",", "','") + "')";

		try {
			
			String sql = " DELETE FROM t_inv_productcheck WHERE fid in %s ";
			String sql2 = "DELETE FROM t_inv_productcheckitem WHERE fproductcheckid in %s ";
			
			sql = String.format(sql, fidsS);
			sql2 = String.format(sql2, fidsS);
			
			productcheckDao.ExecBySql(sql);
			productcheckDao.ExecBySql(sql2);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}

	@RequestMapping(value = "/saveProductTime")
	public String saveProductTime(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			String fid = request.getParameter("fid");
			String fcreatetime = request.getParameter("fcreatetime");
			Productcheck productcheck = null;
			productcheck = productcheckDao.Query(fid);
			Date date=sdf.parse(fcreatetime);
			productcheck.setFcreatetime(date);	
			productcheck.setFupdatetime(new Date());
			
			//avoid unexpectedly fstateS change
			String fstateS = (request.getParameter("fstate"));
		
			if (fstateS != null) {
				
				productcheck.setFstate(Byte.valueOf(fstateS));
				
			}
			
			productcheckDao.ExecSave(productcheck);
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}