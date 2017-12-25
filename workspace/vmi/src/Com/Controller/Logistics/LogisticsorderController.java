package Com.Controller.Logistics;

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
import Com.Dao.Logistics.ILogisticsorderDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustomer;
import Com.Entity.Logistics.Logisticsorder;

@Controller
public class LogisticsorderController {
	@Resource 
	ILogisticsorderDao LogisticsorderDao;
	@Resource 
	IBaseSysDao BaseSysDao;
	@Resource 
	ICustomer customerDao;
	@RequestMapping("getLogisticsOrderList")
	public void getLogisticsOrderList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "select l.fcreatetime,l.fdescription,l.fcargotype,l.fcartype,l.farrivetime,l.fconveyingstate,l.fcarnumber,l.fnumber,l.frecipientsaddress,l.fid,l.fcustomerid,l.flinkman,l.fphone,l.fcargoname,l.fcargoaddress,l.frecipientsname,l.frecipientsphone,l.fdriver,l.fdriverphone,l.farrivedate,l.frealityarrivetime,l.fcost,l.fstate from t_ord_logisticsorder l where 1=1"+LogisticsorderDao.QueryFilterByUser(request, "l.fcustomerid", null);;
			request.setAttribute("djsort", "l.fnumber desc");
			ListResult result = LogisticsorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"查询成功", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(true,e.getMessage(), "",""));
		}
	}
	@RequestMapping("delLogisticsOrderList")
	public void delLogisticsOrderList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fids = request.getParameter("fidcls");
			String sql = "delete from t_ord_logisticsorder where fid in('"+fids+"') and ifnull(fstate,0)=0";
			int size = LogisticsorderDao.ExecBySql(sql);
			if(size==fids.split(",").length){
				response.getWriter().write(JsonUtil.result(true,"删除成功", "",""));
			}else{
				throw new DJException("已经接收的不能删除！");
			}
			
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	@RequestMapping("saveOrUpdateLogisticsOrder")
	public void saveOrUpdateLogisticsOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			LogisticsorderDao.saveOrUpdateLogisticsOrder(request);
			response.getWriter().write(JsonUtil.result(true,"保存成功！", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	@RequestMapping("receiveLogisticsOrder")
	public void receiveLogisticsOrder(){
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@RequestMapping("completeLogisticsOrder")
	public void completeLogisticsOrder(){
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@RequestMapping("getLogisticsOrderInfo")
	public void getLogisticsOrderInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fid");
			String sql = "select l.fdriver,l.fdriverphone,l.fcost,l.fcarnumber,l.fconveyingstate,l.fcreatetime,l.fcreater,l.fcargotype,l.fcartype,l.fdescription,SUBSTR(l.farrivedate,1,10) farrivedate,l.farrivetime,l.fnumber,l.frecipientsaddress,l.fid,l.fcustomerid,l.flinkman,l.fphone,l.fcargoaddress,l.frecipientsname,l.frecipientsphone,l.fstate from t_ord_logisticsorder l where l.fid='"+fid+"'";
			List<HashMap<String,Object>> list = LogisticsorderDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"", "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	
	@RequestMapping("getLogisticsOrderManageList")
	public void getLogisticsOrderManageList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			request.setAttribute("djsort", "l.fnumber desc");
			String sql = "select c.fname cname,l.fcargotype,l.fcartype, l.farrivetime,l.fconveyingstate,l.fcarnumber,l.fnumber,l.frecipientsaddress,l.fid,l.fcustomerid,l.flinkman,l.fphone,l.fcargoaddress,l.frecipientsname,l.frecipientsphone,l.fdriver,l.fdriverphone,l.farrivedate,l.frealityarrivetime,l.fcost,l.fstate,l.fdescription,date_format(l.fcreatetime, '%Y-%m-%d %H:%i') fcreatetime,u.fname fcreater from t_ord_logisticsorder l  LEFT JOIN t_bd_customer c  ON c.fid = l.fcustomerid left join t_sys_user u on u.fid=l.fcreater where 1=1 ";
			ListResult result = LogisticsorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"查询成功", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	
	
	@RequestMapping("/cancellSendOrders")
	public String cancellSendOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
			try {
				if(StringUtils.isEmpty(fidcls)) throw new DJException("请选择操作记录");
				if(!LogisticsorderDao.QueryExistsBySql("select fid from t_ord_logisticsorder where ifnull(fstate,0)=1 and fid in "+fidcls)) throw new DJException("请选择已派送的记录");
				LogisticsorderDao.ExecBySql("update t_ord_logisticsorder set fdriver='',fdriverphone='',fcarnumber='',fcost=null,fstate=0  where ifnull(fstate,0)=1 and fid in "+fidcls);
				result = JsonUtil.result(true, "取消派车成功!", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false, e.getMessage(), "", "");
			}
		
		reponse.getWriter().write(result);
		return null;
	}
	
	
	@RequestMapping("/SendLogisticsOrders")
	public String SendLogisticsOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fid = request.getParameter("fid");
			try {
				if(StringUtils.isEmpty(fid)) throw new DJException("fid不能为空");
				Logisticsorder order=(Logisticsorder)LogisticsorderDao.Query(Logisticsorder.class, fid);
				if(order.getFstate()!=null&&order.getFstate()==1){throw new DJException("该订单已派车");}
				order.setFdriver(request.getParameter("fdriver"));
				order.setFdriverphone(request.getParameter("fdriverphone"));
				order.setFcarnumber(request.getParameter("fcarnumber"));
				String fcost=StringUtils.isEmpty(request.getParameter("fcost"))?"0":request.getParameter("fcost");
				order.setFcost(new Float(fcost));
				order.setFstate(1);
				LogisticsorderDao.saveOrUpdate(order);
				result = JsonUtil.result(true, "派车成功!", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false, e.getMessage(), "", "");
			}
		
		reponse.getWriter().write(result);
		return null;
	}
	@RequestMapping("/GetLogisticsCargoTypeList")
	public String GetLogisticsCargoTypeList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select DISTINCT fcargotype,fcartype,flasted from t_bd_logisticscarinfo where 1=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null)+"  GROUP BY fcargotype";
		 try {
			 List result= LogisticsorderDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "","", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping("/GetLogisticsCarTypeList")
	public String GetLogisticsCarTypeList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select  fcartype,fcargotype,flasted from t_bd_logisticscarinfo where 1=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null)+" group by fcartype";
		 String sqls =sql;
		 try {
			List result= LogisticsorderDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "","", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping("/GetLogisticsAddressFnameList")
	public String GetLogisticsAddressFnameList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=0 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 request.setAttribute("djgroup","fname");
			 request.setAttribute("nolimit",true);
			 ListResult result= LogisticsorderDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/GetLogisticsAddressFphoneList")
	public String GetLogisticsAddressFphoneList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=0 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 request.setAttribute("djgroup","fphone");
			 request.setAttribute("nolimit",true);
			 ListResult result= LogisticsorderDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/GetLogisticsAddressFlinkmanList")
	public String GetLogisticsAddressFlinkmanList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=0 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 request.setAttribute("djgroup","flinkman");
			 request.setAttribute("nolimit",true);
			 ListResult result= LogisticsorderDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/GetConsigneeAddressFnameList")
	public String GetConsigneeAddressFnameList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 request.setAttribute("djgroup","fname");
			 request.setAttribute("nolimit",true);
			 ListResult result= LogisticsorderDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping("/GetConsigneeAddressFphoneList")
	public String GetConsigneeAddressFphoneList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 request.setAttribute("djgroup","fphone");
			 request.setAttribute("nolimit",true);
			 ListResult result= LogisticsorderDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping("/GetConsigneeAddressFlinknameList")
	public String GetConsigneeAddressFlinknameList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 request.setAttribute("djgroup","flinkman");
			 request.setAttribute("nolimit",true);
			 ListResult result= LogisticsorderDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping("/getDefaultLogisticsInfo")
	public void getDefaultLogisticsInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String sql ="SELECT flinkman,fphone,fname,COUNT(1) c , 1 ftype FROM t_bd_logisticsaddress  WHERE ftype=1 AND flasted=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null)+" LIMIT 0,1 "+
							"UNION all"+
							 "(SELECT  flinkman,fphone,fname,COUNT(1) c ,0 ftype FROM t_bd_logisticsaddress WHERE ftype=0 AND flasted=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null)+" LIMIT 0,1) "+
							 "UNION "+
							 "(SELECT fcargotype AS flinkman,'' AS fphone,fcartype AS fname,COUNT(1) c , 2 ftype FROM `t_bd_logisticscarinfo` WHERE  flasted=1 "+LogisticsorderDao.QueryFilterByUser(request, "fcustomerid", null)+" LIMIT 0,1)"
								+" order by ftype asc";
			List list = LogisticsorderDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "","", list));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
}
