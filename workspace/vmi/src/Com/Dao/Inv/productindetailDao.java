package Com.Dao.Inv;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.System.ISupplierDao;
import Com.Dao.order.IProductPlanDao;
import Com.Entity.Inv.Productindetail;
import Com.Entity.Inv.Supplierindetail;
import Com.Entity.System.Useronline;
import Com.Entity.order.ProductPlan;
@Service("ProductindetailDao")
public class productindetailDao extends BaseDao implements IproductindetailDao {
	private IProductPlanDao ProductPlanDao=(IProductPlanDao) SpringContextUtils.getBean("productPlanDao");
	private ISupplierDao SupplierDao=(ISupplierDao) SpringContextUtils.getBean("SupplierDao");
	private IStorebalanceDao storebalanceDao=(IStorebalanceDao) SpringContextUtils.getBean("StorebalanceDao");
	@Override
	public HashMap<String, Object> ExecSave(Productindetail cyc) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cyc.getFid().isEmpty()) {
			cyc.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cyc);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Productindetail Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Productindetail.class, fid);
	}

	@Override
	public void saveproductindetail(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String userID = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String FWarehouseID = request.getParameter("FWarehouseID");
		String FWarehouseSiteID = request.getParameter("FWarehouseSiteID");
		String fproductplanid = request.getParameter("fplanid");
		String ftraitid=request.getParameter("ftraitid")==null?"":request.getParameter("ftraitid");
		BigDecimal famount = new BigDecimal(request.getParameter("famount"));

		
		ProductPlan so = ProductPlanDao.Query(fproductplanid);
		String forderid = so.getForderid();
		String fproductid = so.getFproductdefid();
		String forderEntryid = so.getFparentorderid();
		
		String supplierid = so.getFsupplierid();
		if(so.getFstate()==3){
			throw new DJException("订单已完成不能入库！");
		}
		if (so.getFaffirmed() == null || so.getFaffirmed() == 0) {
			throw new DJException("供应商未确认不能入库！");
		}
		
		storebalanceDao.ExecInStore(fproductplanid, famount.intValue(),
				FWarehouseID, FWarehouseSiteID, userID, fproductid, forderid,
				forderEntryid,supplierid,ftraitid);
		
		Productindetail pdtindetailinfo = new Productindetail();
		
		pdtindetailinfo.setFnumber(getFnumber("t_inv_productindetail", "R", 4));
		pdtindetailinfo.setForderentryid(forderEntryid);
		pdtindetailinfo.setFid(CreateUUid());
		pdtindetailinfo.setFproductplanid(fproductplanid);
		pdtindetailinfo.setFcreatorid(userID);
		pdtindetailinfo.setFcreatetime(new Date());
		pdtindetailinfo.setFupdatetime(null);
		pdtindetailinfo.setFupdateuserid(null);
		pdtindetailinfo.setFinqty(famount);
		pdtindetailinfo.setFwarehouseId(FWarehouseID);
		pdtindetailinfo.setFwarehouseSiteId(FWarehouseSiteID);
		pdtindetailinfo.setFproductId(fproductid);
		pdtindetailinfo.setFdescription(null);
		pdtindetailinfo.setFsaleOrderId(forderid);
		pdtindetailinfo.setFtype(1);
		pdtindetailinfo.setFaudited(1);
		pdtindetailinfo.setFauditorid(userID);
		pdtindetailinfo.setFaudittime(new Date());
		pdtindetailinfo.setFsupplierid(supplierid);
		pdtindetailinfo.setFtraitid(ftraitid);
		ExecSave(pdtindetailinfo);
		
		if(so.getFcustomerid().equals("04d8efb5-5f3a-11e4-bdb9-00ff6b42e1e5")){//客户为东经包装的制造商订单才添加接口入库明细表
			saveSupplierIndetail(Integer.valueOf(request.getParameter("famount")),so);//制造商接口入库明细表
		}
		
	}
	public void saveSupplierIndetail(int famount,ProductPlan p){
		Supplierindetail su = new Supplierindetail();
		su.setFid(this.CreateUUid());
		su.setFamount(p.getFamount());
		su.setFinqty(famount);
		su.setFintime(new Date());
		su.setFpcmordernumber(p.getFpcmordernumber());
		su.setFstate(0);
		su.setFsupplierid(SupplierDao.Query(p.getFsupplierid()).getFname());
		this.saveOrUpdate(su);
	}
	@Override
	public void saveproductindetail(String fproductplanid,int famount,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid,String forderid,String forderEntryid,String fsupplierid,String ftraitid,boolean audited) {
		// TODO Auto-generated method stub		
		
		String sql = "select fid from t_pdt_vmiproductparam where fproductid = '"+fproductid+"' and FSUPPLIERID = '"+fsupplierid+"' and FTYPE = 0 ";
		List<HashMap<String, Object>> vmiproductparamcls=QueryBySql(sql);
		if (vmiproductparamcls.size()>0)
		{
			throw new DJException("该产品、制造商、下单类型为通知！");
		}
		if(audited)
		{
		storebalanceDao.ExecInStore(fproductplanid, famount,
				FWarehouseID, FWarehouseSiteID, userid, fproductid, forderid,
				forderEntryid,fsupplierid,ftraitid);
		}
		if(userid.contains("普通调拨"))
		{
			userid=userid.split(",")[0];
		}
		Productindetail pdtindetailinfo = new Productindetail();
		
		pdtindetailinfo.setFnumber(getFnumber("t_inv_productindetail", "R", 4));
		pdtindetailinfo.setForderentryid(forderEntryid);
		pdtindetailinfo.setFid(CreateUUid());
		pdtindetailinfo.setFproductplanid(fproductplanid);
		pdtindetailinfo.setFcreatorid(userid);
		pdtindetailinfo.setFcreatetime(new Date());
		pdtindetailinfo.setFupdatetime(null);
		pdtindetailinfo.setFupdateuserid(null);
		pdtindetailinfo.setFinqty(new BigDecimal(famount));
		pdtindetailinfo.setFwarehouseId(FWarehouseID);
		pdtindetailinfo.setFwarehouseSiteId(FWarehouseSiteID);
		pdtindetailinfo.setFproductId(fproductid);
		pdtindetailinfo.setFdescription(null);
		pdtindetailinfo.setFsaleOrderId(forderid);
		pdtindetailinfo.setFtype(1);
		pdtindetailinfo.setFaudited(audited?1:0);
		pdtindetailinfo.setFauditorid(audited?userid:"");
		pdtindetailinfo.setFaudittime(audited?new Date():null);
		pdtindetailinfo.setFsupplierid(fsupplierid);
		pdtindetailinfo.setFtraitid(ftraitid);
		ExecSave(pdtindetailinfo);
	}
}
