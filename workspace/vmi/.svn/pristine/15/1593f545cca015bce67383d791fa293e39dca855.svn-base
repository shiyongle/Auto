package Com.Controller.SDK;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Dao.order.IFTUDao;
import Com.Entity.System.Useronline;

@Controller
public class FtuSDKController {
	@Resource
	private IFTUDao ftuDao;
	
	@RequestMapping("FTUdetail")
	public void FTUdetail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "SELECT IFNULL(SUM(CAST(sd.fprice AS SIGNED)),0) fprice,COUNT(sa.fid) ftucount,IFNULL(SUM(CAST(sd.famount AS SIGNED)),0) famount,COUNT(DISTINCT sa.fcustomer) custcount FROM t_ftu_saledeliver sa LEFT JOIN t_ftu_saledeliverentry sd ON sa.fid=sd.fparentid where sa.fstate<>1 "+ftuDao.QueryFilterByUser(request, null,"sa.fsupplierid");
			List<HashMap<String,Object>> monthList = ftuDao.QueryBySql(sql+"and DATE_FORMAT(sa.fcreatetime,'%Y-%m')=DATE_FORMAT(now(),'%Y-%m')");//查询一个月的总金额
			List<HashMap<String,Object>> nowList = ftuDao.QueryBySql(sql +"and DATE_FORMAT(sa.fcreatetime,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d')");//查询今天的统计数据
			List<HashMap<String,Object>> yesterdayList = ftuDao.QueryBySql(sql+"and DATE_FORMAT(sa.fcreatetime,'%Y-%m-%d')=DATE_SUB(DATE_FORMAT(now(),'%Y-%m-%d'),INTERVAL 1 DAY)");//查询昨天的统计数据
			String nowResult = JsonUtil.result(true,"","", nowList);
			String yesterdayResult = JsonUtil.result(true,"","", yesterdayList);
			response.getWriter().write("{\"success\":" + true+ ",\"data\":[{\"montPrices\":\""+monthList.get(0).get("fprice")+"\",\"nowList\":"+nowResult.substring(nowResult.indexOf("["),nowResult.lastIndexOf("]")+1)+"" +
					",\"yesterdayList\":"+yesterdayResult.substring(yesterdayResult.indexOf("["),yesterdayResult.lastIndexOf("]")+1)+"}]}");
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	@RequestMapping("getSupplierSDK")
	public void getSupplierSDK(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			int ftype = 0;//1是制造商 2是客户 3既是制造商又是客户
			String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
					" union select c.fsupplierid from  t_sys_userrole r "+
					" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
			List<HashMap<String,Object>> list=ftuDao.QueryBySql(sql);
			if(list.size()>0){
				ftype = 1;
			}
			sql = "select distinct e1.fid,e1.fname,e1.fnumber from t_pdt_productreqallocationrules e"
					+" left join t_sys_supplier e1 on e.fsupplierid=e1.fid where 1=1 "+ftuDao.QueryFilterByUser(request, "e.fcustomerid", null);
			if(ftuDao.QueryExistsBySql(sql)){
				if(1==ftype){
					ftype = 3;
				}else{
					ftype = 2;
				}
			}
			response.getWriter().write("{\"success\":" + true+ ",\"data\":[{\"ftype\":\""+ftype+"\"}]");
		}
		catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
}
