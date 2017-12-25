package Com.Controller.System;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ISupCustomerInfoDao;
import Com.Entity.System.Customer;
import Com.Entity.System.SysUser;

@Controller
public class SupCustomerInfoController {

	@Resource
	private ISupCustomerInfoDao supCustomerInfoDao;
	
	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping("/getCustomerListBySupplier")
	public String getCustomerListBySupplier(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "SELECT u.fname flastupdateusername,e.fpacktotal,e.fpackbuyamount,e.fid,e.fname fcustname,e.fphone,e.findustryid,e.ftxregisterno,e.fbizregisterno,e.fartificialperson,e.faddress,e.fcreatetime,e.flastupdateuserid,e.flastupdatetime,e.fdescription,e.fisinvited FROM t_bd_customer e inner JOIN t_pdt_productreqallocationrules e1 ON e.fid=e1.fcustomerid left join t_sys_user u on e.flastupdateuserid=u.fid where 1=1 "
		+ baseSysDao.QueryFilterByUser(request, null, "e1.fsupplierid");
		try {
			request.setAttribute("djsort","e.fcreatetime desc");
			ListResult rs = supCustomerInfoDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", rs));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("/saveCustomerInfoBySupplier")
	public String saveCustomerInfoBySupplier(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Customer customer = (Customer) request.getAttribute("Customer");
		String userid = baseSysDao.getCurrentUserId(request);
		try {
			supCustomerInfoDao.saveCustomerInfo(customer,userid);
			response.getWriter().write(JsonUtil.result(true,"保存成功！", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("/getCustomerInfoOfSupplier")
	public String getCustomerInfoOfSupplier(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		try {
			String sql = "select e.fartificialpersonphone,e.flinkman,e.fpacktotal,e.fpackbuyamount,e.fid,e.fname,e.fphone,e.findustryid,e.ftxregisterno,e.fbizregisterno,e.fartificialperson,e.faddress,e.fcreatetime,e.flastupdateuserid,e.flastupdatetime,e.fdescription,e.fcreatorid,e.fisinvited from t_bd_customer e where e.fid =:fid";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String,Object>> result= supCustomerInfoDao.QueryBySql(sql,p);
			response.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/sendMessageForCustomers")
	public String sendMessageForCustomers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidcls = request.getParameter("fidcls");
		String userid = baseSysDao.getCurrentUserId(request);
		String way = request.getParameter("way");
		try {
			String info = supCustomerInfoDao.ExecSendMessageForCustomers(fidcls,userid,way);	// 邀请信息，user存在
			if(!StringUtils.isEmpty(way)){
				info = "操作成功！";
			}
			response.getWriter().write(JsonUtil.result(true, info.isEmpty()?"":info, "",""));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	@RequestMapping("/deleteForCustomersBysupplier")
	public String deleteForCustomersBysupplier(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidcls = request.getParameter("fidcls");
		String userid = baseSysDao.getCurrentUserId(request);
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String sql="select fid from t_ftu_saledeliver where fstate!=1 and fcustomer in "+fidcls;
			if(supCustomerInfoDao.QueryExistsBySql(sql)){
				throw new DJException("该客户已经生成送货凭证，不能删除");
			}
//			if(supCustomerInfoDao.QueryExistsBySql("select fid from t_bd_custproduct where fcustomerid in "+fidcls))
//			{
//				throw new DJException("该客户已有产品，不能删除");
//			}
			if(supCustomerInfoDao.QueryExistsBySql("select fid from t_ord_deliverapply where fcustomerid in "+fidcls))
			{
				throw new DJException("该客户已经要货，不能删除");
			}
			
			supCustomerInfoDao.ExecdeleteForCustomers(fidcls,userid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	
}
