package Com.Controller.Finance;

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
import Com.Base.Util.ListResult;
import Com.Dao.Finance.IGatheringDao;
import Com.Entity.System.Useronline;

@Controller
public class GatheringController {
	Logger log = LoggerFactory.getLogger(GatheringController.class);
	@Resource
	private IGatheringDao GatheringDao;
//
//	@Resource
//	private IDeliverapplyDao deliverapplyDao; 
//
//	
	@RequestMapping("/GetGatheringList")
	public String GetGatheringList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT u1.fname creator,s.fnumber saledelivernum,d.fnumber deliverordernum,p.fnumber productplannum,ds.fnumber deliversnum,a.fnumber deliverapplynum,cp.fname custproductname,g.FID,g.FNUMBER,g.FBIZDATE,g.FCREATORID,g.FCREATETIME,g.FDELIVERAPPLYID,g.FSALEDELIVERID,g.FSALEDELIVERENTRYID,g.FCUSTPRODUCTID,g.FAMOUNT,g.FPRICE,g.FSUM,g.FDELIVERORDERID,g.FDELIVERSID,g.FPRODUCTPLANID FROM t_bal_gathering g left join t_ord_deliverapply a on a.fid=g.FDELIVERAPPLYID left join t_tra_saledeliver s on s.fid=g.FSALEDELIVERID left join t_ord_deliverorder d on d.fid=g.FDELIVERORDERID left join t_ord_productplan p on p.fid=g.FPRODUCTPLANID left join t_ord_delivers ds on ds.fid=g.FDELIVERSID left join t_bd_custproduct cp on cp.fid=g.fcustproductid left join t_sys_user u1 on u1.fid=g.fcreatorid ";
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = GatheringDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping(value="/SaveGathering")
	public  String SaveBalanceprice(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
	String result="";
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//	Gathering bprice=(Gathering)request.getAttribute("Balanceprice");
//	List<Balancepriceprices> prices=(ArrayList<Balancepriceprices>)request.getAttribute("Balancepriceprices");
//	List<Balancepricecustprices> custprices=(ArrayList<Balancepricecustprices>)request.getAttribute("Balancepricecustprices");
//	if(prices.size()==0)
//	{
//		result = JsonUtil.result(false,"请至少设置一条结算价格", "", "");
//	}else
//	{
//		GatheringDao.ExecSaveBalanceprice(bprice, prices, custprices);
	
		result = "{success:true,msg:'帐户支出保存成功!'}";
//	}

	}
	catch(Exception e)
	{
		result = JsonUtil.result(false,e.getMessage(), "", "");
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}

	
	@RequestMapping("/getGatheringInfo")
	public String getBalancepriceInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String username=((Useronline)request.getSession().getAttribute("Useronline")).getFusername();
			String fid = request.getParameter("fid");
			String sql ="SELECT u1.fname creator,s.fnumber saledelivernum,d.fnumber deliverordernum,p.fnumber productplannum,ds.fnumber deliversnum,g.FID,g.FNUMBER,g.FBIZDATE,g.FCREATORID,g.FCREATETIME,g.FDELIVERAPPLYID,g.FSALEDELIVERID,g.FSALEDELIVERENTRYID,g.FCUSTPRODUCTID,g.FAMOUNT,g.FPRICE,g.FSUM,g.FDELIVERORDERID,g.FDELIVERSID,g.FPRODUCTPLANID FROM t_bal_gathering g left join t_ord_deliverapply a on a.fid=g.FDELIVERAPPLYID left join t_tra_saledeliver s on s.fid=g.FSALEDELIVERID left join t_ord_deliverorder d on d.fid=g.FDELIVERORDERID left join t_ord_productplan p on p.fid=g.FPRODUCTPLANID left join t_ord_delivers ds on ds.fid=g.FDELIVERSID left join t_sys_user u1 on u1.fid=g.fcreatorid " +
					" where g.fid='"+fid+"'";
			List<HashMap<String,Object>> result= GatheringDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}


}