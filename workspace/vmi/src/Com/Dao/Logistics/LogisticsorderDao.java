package Com.Dao.Logistics;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ServerContext;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.Logistics.LogisticsAddress;
import Com.Entity.Logistics.LogisticsCarinfo;
import Com.Entity.Logistics.Logisticsorder;
import Com.Entity.System.Customer;
import Com.Entity.System.Useronline;

@Service("LogisticsorderDao")
public class LogisticsorderDao extends BaseDao implements ILogisticsorderDao{
	@Resource 
	IBaseSysDao BaseSysDao;
	
	@Override
	public void saveOrUpdateLogisticsOrder(HttpServletRequest request){
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String fcustomerid = BaseSysDao.getCurrentCustomerid(userid);
		Customer c = (Customer) this.Query(Customer.class, fcustomerid);
		Logisticsorder l = (Logisticsorder)request.getAttribute("Logisticsorder");
		LogisticsAddress la = null;
		if(!StringUtils.isEmpty(l)){
			if(StringUtils.isEmpty(l.getFid())){
				l.setFid(this.CreateUUid());
				l.setFcreater(userid);
				l.setFcreatetime(new Date());
				l.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_logisticsorder", "WL", 4, false));
				l.setFcustomerid(c.getFid());
				l.setFstate(0);
			}
			String sql = "update  t_bd_logisticsaddress set flasted=0 where flasted=1 and fcustomerid='"+l.getFcustomerid()+"'";
			this.ExecBySql(sql);
			sql = "select 1 from t_bd_logisticsaddress where ftype=0 and flinkman='"+l.getFlinkman()+"' and fphone='"+l.getFphone()+"' and fname='"+l.getFcargoaddress()+"'and fcustomerid='"+l.getFcustomerid()+"'";
			if(!this.QueryExistsBySql(sql)){
				la = new LogisticsAddress();
				la.setFid(this.CreateUUid());
				la.setFcreatetime(new Date());
				la.setFcreatorid(userid);
				la.setFcustomerid(fcustomerid);
				la.setFlasted(1);
				la.setFlinkman(l.getFlinkman());
				la.setFphone(l.getFphone());
				la.setFname(l.getFcargoaddress());
				la.setFtype(0);
				this.saveOrUpdate(la);
			}else{
				sql = "update  t_bd_logisticsaddress set flasted=1 where ftype=0 and flinkman='"+l.getFlinkman()+"' and fphone='"+l.getFphone()+"' and fname='"+l.getFcargoaddress()+"' and fcustomerid='"+l.getFcustomerid()+"'";
				this.ExecBySql(sql);
			}
			sql = "select 1 from t_bd_logisticsaddress where ftype=1 and flinkman='"+l.getFrecipientsname()+"' and fphone='"+l.getFrecipientsphone()+"' and fname='"+l.getFrecipientsaddress()+"' and fcustomerid='"+l.getFcustomerid()+"'";
			if(!this.QueryExistsBySql(sql)){
				la = new LogisticsAddress();
				la.setFid(this.CreateUUid());
				la.setFcreatetime(new Date());
				la.setFcreatorid(userid);
				la.setFcustomerid(fcustomerid);
				la.setFlasted(1);
				la.setFlinkman(l.getFrecipientsname());
				la.setFphone(l.getFrecipientsphone());
				la.setFname(l.getFrecipientsaddress());
				la.setFtype(1);
				this.saveOrUpdate(la);
			}else{
				sql = "update  t_bd_logisticsaddress set flasted=1 where  ftype=1 and flinkman='"+l.getFrecipientsname()+"' and fphone='"+l.getFrecipientsphone()+"' and fname='"+l.getFrecipientsaddress()+"' and fcustomerid='"+l.getFcustomerid()+"'";
				this.ExecBySql(sql);
			}
			sql = "update t_bd_logisticscarinfo set flasted=0 where flasted=1 and fcustomerid='"+l.getFcustomerid()+"'";
			this.ExecBySql(sql);
			sql = "select 1 from t_bd_logisticscarinfo where fcargotype='"+l.getFcargotype()+"' and fcartype='"+l.getFcartype()+"' and fcustomerid='"+l.getFcustomerid()+"'";
			if(!this.QueryExistsBySql(sql)){
				LogisticsCarinfo lc = new LogisticsCarinfo();
				lc.setFid(this.CreateUUid());
				lc.setFcargotype(l.getFcargotype());
				lc.setFcartype(l.getFcartype());
				lc.setFcreatetime(new Date());
				lc.setFcreatorid(userid);
				lc.setFcustomerid(fcustomerid);
				lc.setFlasted(1);
				this.saveOrUpdate(lc);
			}else{
				sql = "update t_bd_logisticscarinfo set flasted=1 where fcargotype='"+l.getFcargotype()+"' and fcartype='"+l.getFcartype()+"' and fcustomerid='"+l.getFcustomerid()+"'";
				this.ExecBySql(sql);
			}
			this.saveOrUpdate(l);
			
		}
	}
}
