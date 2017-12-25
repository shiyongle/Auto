package Com.Controller.SDK;

import java.io.IOException;
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
import Com.Base.Util.JsonUtil;
import Com.Dao.order.ICusDeliversDao;
import Com.Entity.System.UserCustomer;
import Com.Entity.System.Useronline;


@Controller
public class CusDeliversSDKController {
	Logger log = LoggerFactory.getLogger(CusDeliversSDKController.class);
	@Resource
	private ICusDeliversDao CusDeliversDao;

	@RequestMapping(value = "/ImpCusDeliversSDK")
	public String ImpCusDeliversSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			CusDeliversDao.ExecImpCusDeliversSDK(request);
			result = "{success:true,msg:'成功'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
	@RequestMapping(value = "/getSaleDeliversSDK")
	public String getSaleDeliversSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			List<UserCustomer> cuscls=CusDeliversDao.QueryByHql(" from UserCustomer where fuserid='"+userid+"'");
			if(cuscls.size()!=1)
			{
				throw new DJException("客户信息有误");
			}
			String sql = " SELECT SD.fid fbillid,SDE.FID fvmifid,SD.FNUMBER billno,sde.fseq,sup.fnumber supno,sup.fname supname,sup.faddress supaddress,sd.ftruckid,dap.fcusfid,sde.famount / da.fdelivernum * da.fdeliverapplynum as famount FROM t_tra_saledeliverentry sde left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid  ";
			sql=sql+" left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid left join t_sys_supplier sup on sup.fid = sd.FSUPPLIER  ";
			sql=sql+" WHERE SD.fisexport=0 and SD.faudited=1 AND  SD.fcustomerid='"+cuscls.get(0).getFcustomerid()+"' order by SD.FNUMBER,SDE.FSEQ ";
//			request.setAttribute("nolimit",true);
//			request.setAttribute("djsort"," SD.FNUMBER,SDE.FSEQ ");//djsort
//			ListResult result=CusDeliversDao.QueryFilterList(sql, request);
			List<HashMap<String,Object>> resultcls=CusDeliversDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",resultcls));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	更新发货表FISEXPORT=1
	 * @throws IOException 
	 */
	@RequestMapping(value = "/ImportedSaleDeliverSDK")
	public void ImportedSaleDeliverSDK(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		List<UserCustomer> cuscls=CusDeliversDao.QueryByHql(" from UserCustomer where fuserid='"+userid+"'");
		if(cuscls.size()!=1)
		{
			throw new DJException("客户信息有误");
		}
		String result = "";
		String fid = request.getParameter("fid");
		String sql="";
		try {
			sql = "UPDATE t_tra_saledeliver SET FISEXPORT=1 where fid='"+fid+"' and fcustomerid='"+cuscls.get(0).getFcustomerid()+"'";
			CusDeliversDao.ExecBySql(sql);
			result = "{success:true,msg:'成功'}";
		} catch (DJException e) { 
			// TODO: handle exception
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		response.getWriter().write(result);
		
	}
}
