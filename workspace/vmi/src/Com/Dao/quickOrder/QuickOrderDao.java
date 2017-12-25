package Com.Dao.quickOrder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.UploadFile;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.order.CusPrivateDelivers;
import Com.Entity.order.Cusdelivers;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;
import Com.Entity.order.SchemeDesignEntry;

@Service("QuickOrderDao")
public class QuickOrderDao extends BaseDao implements IQuickOrderDao{
	@Override
	public void ExecCustproductCommon(String fidcls,String fstate){
	String sql = "update t_bd_custproduct set fiscommon="+fstate+" where fid in "+fidcls;
	this.ExecBySql(sql);
	}
	
	/**
	 * 删除客户产品附件
	 */
	@Override
	public String ExecDelFileQuick(String ids) {
		List<HashMap<String, Object>> list = null;
		String message = null;
		for(String id : ids.split(",")){
			String sql = "SELECT 1 FROM t_ord_productdemandfile pdf INNER JOIN t_ord_schemedesign sd ON sd.fid = pdf.fparentid WHERE pdf.fpath = (SELECT fpath FROM t_ord_productdemandfile WHERE fid = '"+id+"')";
			list = this.QueryBySql(sql);
			if(list.size()>0){
				message = "方案设计生成的附件,不能删除！";
				continue;
			}else{
				UploadFile.delFile(id);
				sql = "select fid from t_ord_productdemandfile where fbid = '"+id+"'";
				list = this.QueryBySql(sql);
				if(list.size()>0){
					UploadFile.delFile(list.get(0).get("fid")+"");
				}
			}
		}
		return message;
	}
	
	
	/**
	 * 查询当前登录用户关联的客户,返回客户id集合
	 */
	@Override
	public String getCurrentCustomerids(String userid){
		String cids="";
		String sql="select quote(fcustomerid) fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
				" union select c.fcustomerid from  t_sys_userrole r "+
				" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
		List<HashMap<String, Object>> custList=this.QueryBySql(sql);
		if(custList.size()!=0){
			StringBuilder custids=new StringBuilder();
			for(HashMap<String, Object> m : custList){
				custids.append(m.get("fcustomerid")+",");
			}
			cids = custids.toString();
			cids=cids.substring(0, cids.length()-1);
		}
		return cids;
	}
	
	
	@Override
	public void ExecUploadQuickCustProductImg(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String fid = request.getParameter("fid");
		UploadFile.upload(request, fid);
	}

	@Override
	public void ExecSaveQuickDeliverapply(Deliverapply dinfo)
			throws Exception {
		
		saveOrUpdate(dinfo);
		ExecBySql("UPDATE t_bd_custproduct SET fordercount=IFNULL(fordercount,0)+1,forderamount=IFNULL(forderamount,0)+"+dinfo.getFamount()+" WHERE fid='"+dinfo.getFcusproductid()+"'");
	}

	@Override
	public void ExecSaveMystockOrDeliverapply(Deliverapply dinfo, Mystock sinfo)
			throws Exception {
		// TODO Auto-generated method stub
		
		if(dinfo!=null){
			saveOrUpdate(dinfo);
			this.ExecBySql("update t_bd_custproduct set flastordertime=now(),flastorderfamount="+dinfo.getFamount()+",flastordertype='要货',fisupdate=0 where fid='"+dinfo.getFcusproductid()+"'");
		}
		if(sinfo!=null){
			saveOrUpdate(sinfo);
			this.ExecBySql("update t_bd_custproduct set flastordertime=now(),flastorderfamount="+sinfo.getFplanamount()+",flastordertype='备货',fisupdate=0 where fid='"+sinfo.getFcustproductid()+"'");

		}
		
		
	}

	@Override
	public void ExecSaveDeliverapplyOrCusdelivers(List<Deliverapply> dlist,List<Mystock> myStockList,
			List<Cusdelivers> clist,String userid) throws Exception {
		for (int i = 0; i < dlist.size(); i++) {
			Deliverapply order = dlist.get(i);
			this.ExecBySql("delete from  t_ord_cusprivatedelivers  where fid='"+order.getFid()+"'");
			order.setFid(this.CreateUUid());
			this.saveOrUpdate(order);
			this.ExecBySql("update t_bd_custproduct set flastordertime=now(),flastorderfamount="+order.getFamount()+",flastordertype='要货',fisupdate=0  where fid='"+order.getFcusproductid()+"'");
		}
		for(Mystock mystock: myStockList){
			this.ExecBySql("delete from  t_ord_cusprivatedelivers  where fid='"+mystock.getFid()+"'");
			mystock.setFid(this.CreateUUid());
			this.save(mystock);
			this.ExecBySql("update t_bd_custproduct set flastordertime=now(),flastorderfamount="+mystock.getFplanamount()+",flastordertype='要货',fisupdate=0  where fid='"+mystock.getFcustproductid()+"'");
		}
//		this.ExecBySql("delete from  t_ord_cusprivatedelivers  where fcreatorid='"+userid+"'");
		if(clist!=null){
			for(Cusdelivers entry : clist){
				this.saveOrUpdate(entry);
			}
		}
	}

	@Override
	public void ExecSaveQuickCustproduct(Custproduct c, Productdef f)
			throws Exception {
		// TODO Auto-generated method stub
		saveOrUpdate(c);
		saveOrUpdate(f);
		
	}
	
	
	
	/**
	 * 查询当前登录用户关联的制造商,返回制造商id集合
	 */
	@Override
	public String getCurrentSupplierfids(String userid){
		String cids="";
		String sql="select quote(fsupplierid) fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		List<HashMap<String, Object>> custList=this.QueryBySql(sql);
		if(custList.size()!=0){
			StringBuilder custids=new StringBuilder();
			for(HashMap<String, Object> m : custList){
				custids.append(m.get("fsupplierid")+",");
			}
			cids = custids.toString();
			cids=cids.substring(0, cids.length()-1);
		}
		return cids;
	}

	

	/**
	 * 查询当前登录用户关联的制造商,只能关联一个
	 */
	@Override
	public String getCurrentSupplierid(String userid){
		String fsupplierid="";
		String sql="select quote(fsupplierid) fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		List<HashMap<String, Object>> custList=this.QueryBySql(sql);
		if(custList.size()==1)
		{
			HashMap<String,Object> map=custList.get(0);
			fsupplierid =map.get("fsupplierid").toString();
		}else if(custList.size()==0){
			 throw new DJException("该账号没有关联制造商，请联系客服进行设置");
		}else{
			throw new DJException("当前帐号存在多个制造商,不能执行操作！");
		}
		return fsupplierid;
	}

	@Override
	public void ExecSaveCusPrivateDeliversForHolder(
			List<CusPrivateDelivers> dlist) {
		// TODO Auto-generated method stub
		for(CusPrivateDelivers cs:dlist)
		{
			this.saveOrUpdate(cs);
		}
		
	}

	
}