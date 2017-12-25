package Com.Dao.order;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.stereotype.Service;

import com.dongjing.api.DongjingClient;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.ISimplemessageDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Address;
import Com.Entity.System.Productdef;
import Com.Entity.System.Supplier;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Firstproductdemand;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Productdemandfile;
import Com.Entity.order.Productstructure;
import Com.Entity.order.Saleorder;
import Com.Entity.order.SchemeDesignEntry;
import Com.Entity.order.Schemedesign;
//import Com.Entity.order.MSaleOrderFile;
@Service("SchemeDesignDao")
public class SchemeDesignDao extends BaseDao implements ISchemeDesignDao{
	
	public static final String GENERATE_DELIVERY_RETURN_TYPE_NO_CHARACTER = "不允许生成配送";

	public static final String GENERATE_DELIVERY_RETURN_TYPE_HAS_GENERATED = "已生成配送，不能重复生成";

	public static final String GENERATE_DELIVERY_RETURN_TYPE_SUCCESS = "生成成功";

	private static final String GENERATE_DELIVERY_RETURN_TYPE_HAS_NO_CONFIRMED = "该方案设计未确认，不能生成";
	
	private IStorebalanceDao storebalanceDao=(IStorebalanceDao) SpringContextUtils.getBean("StorebalanceDao");
	private ISimplemessageDao SimplemessageDao=(ISimplemessageDao) SpringContextUtils.getBean("simplemessageDao");
	private IFirstproductdemandDao firstproductdemandDao=(IFirstproductdemandDao) SpringContextUtils.getBean("FirstproductdemandDao");
	@Resource
	private IProductstructureDao ProductstructureDao;
//	= (IProductstructureDao)SpringContextUtils.getBean("ProductstructureDao");
	@Override
	public void saveschemedesignFile(List<FileItem> list,HashMap<String, String> map) {
		String fid = null;
		String fname = null;
		Productdemandfile obj = new Productdemandfile();
		String fpath = map.get("fpath");
		
		File file = null;
		String sql = null;
		for(FileItem item : list){
			if(!item.isFormField()){
				if(item.getSize()>10 * 1024 * 1024){
					throw new DJException("您上传的文件太大，请选择不超过10M的文件");
				}
				file = new File(fpath);
				if(!file.exists()){
					file.mkdirs();
				}
//				if(map.get("fname")==null || map.get("fname").equals("")){
					fname = item.getName();
//				}else{
//					fname = map.get("fname");
//				}
				
				fid = this.CreateUUid();
				fpath = fpath+"/" + fid+fname.substring(fname.lastIndexOf("."));
				try {
					item.write(new File(fpath));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
				obj.setFid(fid);
				obj.setFname(fname);
				obj.setFpath(fpath);
				obj.setFparentid(map.get("fparentid"));
				obj.setFtype(map.get("ftype"));
				obj.setFdescription(map.get("fdescription"));
				this.saveOrUpdate(obj);
			}
		}
		
	}

	@Override
	public void saveSchemeDesign(Schemedesign f,List<SchemeDesignEntry> entryList,List<Productstructure> productList) {//,List<Productdef> productList
		//删除和增加方案特性
		String sql = "delete from t_ord_schemedesignentry where fparentid = '"+f.getFid()+"'";
		this.ExecBySql(sql);
		int amount = 0;
		for(SchemeDesignEntry e : entryList){
			if(e.getFid()==null||"".equals(e.getFid())){
				e.setFid(this.CreateUUid());
			}
			e.setFparentid(f.getFid());
			this.saveOrUpdate(e);
			amount += e.getFentryamount();
		}
		//保存方案
		if(amount>0){
			f.setFamount(amount);
		}
		
		//方案保存成功新增推送消息记录;
		if(!this.QueryExistsBySql("select fid from t_ord_schemedesign where fid = '"+f.getFid()+"'")){
			sql = "SELECT f.fnumber,f.fname,ifnull(u.fname,'') fauditor,u.fid frecipientid FROM t_ord_firstproductdemand f left join t_sys_user u on u.fid=f.fauditorid where f.fid = '"+f.getFfirstproductid()+"'";
			HashMap<String, Object> fpdinfo = (HashMap<String, Object>)QueryBySql(sql).get(0);
			String fcontent = "尊敬的"+fpdinfo.get("fauditor")+"用户！您的'"+fpdinfo.get("fname")+"'需求已经添加方案。";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fcontent", fcontent);
			map.put("frecipientid", (String)fpdinfo.get("frecipientid"));
			map.put("ftype", "2");
			SimplemessageDao.MessageProjectEvaluation(map);
		}
		//群组功能暂用
//		//更新方案的groupid
//		if(StringUtils.isEmpty(f.getFgroupid()))
//		{
//			HashMap<String, Object>  fristinfo=(HashMap<String, Object>)this.QueryBySql("select fgroupid,freceiver from t_ord_firstproductdemand  where fid='"+f.getFfirstproductid()+"'").get(0);
//			if(fristinfo.get("fgroupid")!=null)
//			{
//				f.setFgroupid(""+fristinfo.get("fgroupid"));
//				String freceiver=""+fristinfo.get("freceiver");
//					if(!f.getFcreatorid().equals(freceiver))
//					{
//						if(!this.QueryExistsBySql("select fid from t_ord_schemedesign where ffirstproductid='"+f.getFfirstproductid()+"' and  fcreatorid ='"+f.getFcreatorid()+"'" ))
//						{
//							reciverDesignGroup(f);
//						}
//					}
//				
//			}
//		}
				
		this.saveOrUpdate(f);
		//删除原产品和客户产品
//		String productids,cusproductids;
//		sql = "select fid from t_pdt_productdef where schemedesignid='"+f.getFid()+"'";
//		List<HashMap<String, Object>> list = this.QueryBySql(sql);
//		if(list.size()>0){
//			String fid = list.get(0).get("fid").toString();
//			productids = "'"+fid+"'";
//			sql = "select group_concat(quote(fproductid)) products from t_pdt_productdefproducts where fparentid='"+fid+"'";
//			list = this.QueryBySql(sql);
//			if(list.get(0).get("products")!=null){
//				productids +=","+list.get(0).get("products"); //方案关联的所有产品id
//			}
//			//删除客户产品
//			sql = "select group_concat(quote(fcustproductid)) cusproductids from t_pdt_productrelationentry where fparentid in (select fid from t_pdt_productrelation where fproductid in("+productids+") )";
//			list = this.QueryBySql(sql);
//			cusproductids = list.get(0).get("cusproductids").toString();
//			sql = "delete from t_bd_custproduct where fid in ("+cusproductids+")";
//			this.ExecBySql(sql);
//			//删除产品和客户产品关联
//			sql = "delete from t_pdt_productrelationentry where fcustproductid in ("+cusproductids+")";
//			this.ExecBySql(sql);
//			sql = "delete from t_pdt_productrelation where fproductid in("+productids+")";
//			this.ExecBySql(sql);
//			//删除产品和子产品的关联
//			sql = "delete from t_pdt_productdefproducts where fparentid='"+fid+"'";
//			this.ExecBySql(sql);
//			//删除产品和子产品
//			sql = "delete from t_pdt_productdef where fid in ("+productids+")";
//			this.ExecBySql(sql);
//		}
		int count;
//		if((count=productList.size())>0){
//			//增加产品和客户产品以及它们的关联
//			Productdef product=null;
//			ProductdefProducts pp=null;
//			Custproduct cp = null;
//			Productrelation pr;
//			Productrelationentry pre;
//			String prid,cpid;
//			for(Productdef p : productList){
//				p.setFnewtype("3");
//				//若size=1，为普通产品；新增时，父产品和普通件存在schemedesignid,子产品没有
//				if(p.getSchemedesignid()!=null&&!"".equals(p.getSchemedesignid())&&count>1){//父产品
//					product = p;
//					p.setFnewtype("4");
//				}else if(count>1){ //子产品设置schemedesignid为1
//					p.setSchemedesignid("1");
//				}
//				if(p.getFid()==null||"".equals(p.getFid())){
//					p.setFid(this.CreateUUid());
//				}
//				this.saveOrUpdate(p);
//				cp = new Custproduct();
//				cpid = this.CreateUUid();
//				cp.setFid(cpid);
//				cp.setFname(p.getFname());
//				cp.setFnumber(p.getFnumber());
//				cp.setFcustomerid(p.getFcustomerid());
//				cp.setFrelationed(2);						//客户产品设置为2，便于在客户产品列表关联产品和修改时控制
//				this.saveOrUpdate(cp);
//				pr = new Productrelation();
//				prid = this.CreateUUid();
//				pr.setFid(prid);
//				pr.setFcustomerid(p.getFcustomerid());
//				pr.setFproductid(p.getFid());
//				this.saveOrUpdate(pr);
//				pre = new Productrelationentry();
//				pre.setFid(this.CreateUUid());
//				pre.setFamount(1);
//				pre.setFparentid(prid);
//				pre.setFcustproductid(cpid);
//				this.saveOrUpdate(pre);
//			}
//			if(count>1){
//				//增加产品和子产品关联
//				productList.remove(product);
//				for(Productdef p : productList){
//					pp = new ProductdefProducts();
//					pp.setFid(this.CreateUUid());
//					pp.setFparentid(product.getFid());
//					pp.setFproductid(p.getFid());
//					pp.setFamount(p.getSubProductAmount());
//					this.saveOrUpdate(pp);
//				}
//			}
//		}
//		if(productList.size()>0){
			ProductstructureDao.SaveOrUpdateProductstructure(productList,f.getFid());
//		}
		//改变产品需求状态
		sql = "update t_ord_firstproductdemand set fstate = '已设计' where fid = '"+f.getFfirstproductid()+"' and fstate = '已接收'";
		this.ExecBySql(sql);
	}
	
	@Override
	public void ExecSDOrder(HttpServletRequest request, Schemedesign sdinfo, ArrayList<SchemeDesignEntry> sdentry, Productdef productdefinfo) {
		// TODO Auto-generated method stub
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String fordernumber = ServerContext.getNumberHelper().getNumber("t_ord_saleorder", "Z",4,false);
		String productid = "769baec7-2d87-11e4-bdb9-00ff6b42e1e5";
		
//		if(productdefinfo!=null && productdefinfo.getFid()!=null && !productdefinfo.getFid().equals("")){
//			productid = productdefinfo.getFid();
//		}
		
		String supplierid = sdinfo.getFsupplierid();
		String schemedesignid = sdinfo.getFid();
		
		String fcustomerid = sdinfo.getFcustomerid();
		String fsaleorderid = CreateUUid();
		String fProductplanid = CreateUUid();
		String forderid = CreateUUid();
		
		Saleorder soinfo = new Saleorder();
		soinfo.setFid(fsaleorderid);
		soinfo.setForderid(forderid);
		soinfo.setFcreatorid(userid);
		soinfo.setFcreatetime(new Date());
		soinfo.setFnumber(fordernumber);
		soinfo.setFproductdefid(productid);
		soinfo.setFentryProductType(0);
		
		soinfo.setFtype(1);
		soinfo.setFlastupdatetime(new Date());
		soinfo.setFlastupdateuserid(userid);
		int amt = 0;
		SchemeDesignEntry sdentryinfo;
		Storebalance storebalance;
//		if(sdentry.size()>0){
			for(int i=0;i<sdentry.size();i++){
				sdentryinfo = sdentry.get(i);
				String sdentryid = sdentryinfo.getFid();
				amt = amt + (sdentryinfo.getFentryamount());
				
				//生成结存记录;
				storebalance = new Storebalance("");
				storebalance.setFcreatorid(userid);
				storebalance.setFproductId(productid);
				storebalance.setFcreatetime(new Date());

				String hql = " from  Supplier s where s.fid = '%s' ";
				hql = String.format(hql, supplierid);
				Supplier supplier = (Supplier) storebalanceDao.QueryByHql(hql).get(0);
				storebalance.setFproductplanId(fProductplanid);
				storebalance.setFcustomerid(fcustomerid);
				storebalance.setFsaleorderid(forderid);
				storebalance.setForderentryid(forderid);
				storebalance.setFtraitid(sdentryid);
				storebalance.setFwarehouseId(supplier.getFwarehouseid());
				storebalance.setFwarehouseSiteId(supplier.getFwarehousesiteid());
				storebalance.setFsupplierID(supplierid);
				storebalance.setFtype(1);
				storebalance.setFallotqty(0);
				storebalance.setFbalanceqty(0);
				storebalance.setFinqty(0);
				storebalance.setFoutqty(0);
				storebalanceDao.ExecSave(storebalance);
			}
//		}else{
//			Storebalance storebalance = new Storebalance("");
//			storebalance.setFcreatorid(userid);
//			storebalance.setFproductId(productid);
//			storebalance.setFcreatetime(new Date());
//
//			String hql = " from  Supplier s where s.fid = '%s' ";
//			hql = String.format(hql, supplierid);
//			Supplier supplier = (Supplier) storebalanceDao.QueryByHql(hql).get(0);
//			storebalance.setFproductplanId(fProductplanid);
//			storebalance.setFcustomerid(fcustomerid);
//			storebalance.setFsaleorderid(forderid);
//			storebalance.setForderentryid(forderid);
//			storebalance.setFtraitid("");
//			storebalance.setFwarehouseId(supplier.getFwarehouseid());
//			storebalance.setFwarehouseSiteId(supplier.getFwarehousesiteid());
//			storebalance.setFsupplierID(supplierid);
//			storebalance.setFtype(0);
//			storebalance.setFallotqty(0);
//			storebalance.setFbalanceqty(0);
//			storebalance.setFinqty(0);
//			storebalance.setFoutqty(0);
//			storebalanceDao.ExecSave(storebalance);
//		}
		
		if(amt == 0 ){
//			if(sdinfo.getFamount()!=null && sdinfo.getFamount()>0){
//				amt = sdinfo.getFamount();
//			}else{
				throw new DJException("特性的方案设计数量不能为空！");
//			}
		}
		
		soinfo.setFamount(amt);
		soinfo.setFcustomerid(fcustomerid);
		soinfo.setFcustproduct("4a1f4969-2d87-11e4-bdb9-00ff6b42e1e5");
		soinfo.setFarrivetime(sdinfo.getFoverdate());
		soinfo.setFbizdate(new Date());
		soinfo.setFaudited(1);
		soinfo.setFauditorid(userid);
		soinfo.setFaudittime(new Date());
		soinfo.setFamountrate(1);
		
		soinfo.setFassemble(0);
		soinfo.setFiscombinecrosssubs(0);
		
		soinfo.setFordertype(1);
		soinfo.setFseq(1);
		soinfo.setFimportEas(0);
		soinfo.setFallot(1);
		
		this.saveOrUpdate(soinfo);
		
		ProductPlan p = new ProductPlan();
		p.setFid(fProductplanid);
		p.setFdescription(sdinfo.getFdescription());
		p.setFschemedesignid(schemedesignid);
		p.setFboxlength(sdinfo.getFboxlength());
		p.setFboxwidth(sdinfo.getFboxwidth());
		p.setFboxheight(sdinfo.getFboxheight());
		p.setFtype(soinfo.getFtype());
		p.setPerpurchaseprice(new BigDecimal(0));
		p.setPurchaseprice(new BigDecimal(0));
		p.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_productplan", "P",4,false));
		p.setFparentorderid(fsaleorderid);// 设置父订单分录ID
		p.setFseq(soinfo.getFseq());// 设置父订单分录
		p.setFcreatorid(soinfo.getFcreatorid());
		p.setFcreatetime(soinfo.getFcreatetime());
		p.setFlastupdateuserid(soinfo.getFlastupdateuserid());
		p.setFlastupdatetime(soinfo.getFlastupdatetime());
		p.setFcustomerid(soinfo.getFcustomerid());
		p.setFcustproduct(soinfo.getFcustproduct());
		p.setFarrivetime(soinfo.getFarrivetime());
		p.setFbizdate(soinfo.getFbizdate());

//		p.setFamount((int) Math.round(soinfo.getFamount() * (cinfo.getFproportion() * 0.01)));// 设置生产数量
		p.setFamount(amt);// 设置生产数量
		
		p.setFaudited(1);
		p.setFauditorid(soinfo.getFcreatorid());
		p.setFaudittime(new Date());
		p.setFordertype(soinfo.getFordertype());
		p.setFsuitProductId(soinfo.getFsuitProductId());
		p.setFparentOrderEntryId(soinfo.getFparentOrderEntryId());
		p.setForderid(forderid);
		p.setFimportEas(soinfo.getFimportEas());
		p.setFproductdefid(soinfo.getFproductdefid());
		p.setFcustomerid(fcustomerid);
		p.setFsupplierid(supplierid);// 设置供应商
		p.setFentryProductType(soinfo.getFentryProductType());
		p.setFimportEastime(soinfo.getFimportEastime());
		p.setFamountrate(soinfo.getFamountrate());
		p.setFaffirmed(0);
		p.setFstockinqty(soinfo.getFstockinqty());
		p.setFstockoutqty(soinfo.getFstockoutqty());
		p.setFstoreqty(soinfo.getFstoreqty());
		p.setFassemble(soinfo.getFassemble());
		p.setFiscombinecrosssubs(soinfo.getFiscombinecrosssubs());
		p.setFeasorderid(soinfo.getFeasorderid());
		p.setFeasorderentryid(soinfo.getFeasorderentryid());
		p.setFcloseed(0);
		this.saveOrUpdate(p);
		//更改状态
		sdinfo.setFordered(1);
		this.saveOrUpdate(sdinfo);
	}
	@Override
	public void ExecCancelSchemeDesignOrder(String fid) {
		String hql = "from ProductPlan where fschemedesignid = '"+fid+"'";
		List<ProductPlan> list = this.QueryByHql(hql);
		if(list.size()==0){
			throw new DJException("方案ID不正确！");
		}
		ProductPlan productPlan = (ProductPlan) list.get(0);
		if(productPlan.getFaffirmed()==1){
			throw new DJException("此方案关联的制造商订单已确认，不能取消下单！");
		}
		String sql = "delete from t_inv_storebalance where fproductplanID ='"+productPlan.getFid()+"'";
		this.ExecBySql(sql);
		sql = "delete from t_ord_saleorder where fid = '"+productPlan.getFparentorderid()+"'";
		this.ExecBySql(sql);
		sql = "delete from t_ord_productplan where fid = '"+productPlan.getFid()+"'";
		this.ExecBySql(sql);
		sql = "update t_ord_schemedesign set fordered = 0 where fid = '"+fid+"' and fordered = 1";
		this.ExecBySql(sql);
	}

	@Override
	public void DelSchemeDesign(String fid) {
		String wh,custProducts,productid;
		List<HashMap<String, Object >> list;
		File file;
		if(this.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+fid+"'")){
			throw new DJException("删除失败，已确认的方案设计不能删除！");
		}
		String sql = "select 1 from t_ord_appraise where fschemedesignid ='"+fid+"'";
		list = this.QueryBySql(sql);
		if(list.size()>0){
			throw new DJException("有评价的方案设计不能删除！");
		}
//		String sql = "select fid from t_pdt_productdef where schemedesignid='"+fid+"'";
		sql = "SELECT fid FROM t_ord_productstructure where schemedesignid = '"+fid+"'";
		List<HashMap<String, Object>> productlist = this.QueryBySql(sql);
		if(productlist.size()!=0){
			sql = "SELECT fid FROM t_ord_productstructure  where 1=1 AND fparentid='"+productlist.get(0).get("fid")+"'";
			List<HashMap<String, Object>> subProducts = this.QueryBySql(sql);
			productlist.addAll(subProducts);
		}
		for(int j = 0;j<productlist.size();j++){
		
			this.ExecBySql("delete from t_ord_productstructure where fid ='"+productlist.get(j).get("fid")+"'");
		}
//		List<HashMap<String, Object >> products = this.QueryBySql(sql);
//		if(products.size()>0){
//			productid = products.get(0).get("fid").toString();
//			//查询产品有无关联子产品
//			sql = "select group_concat(quote(fproductid)) products from t_pdt_productdefproducts where fparentid = '"+productid+"'";
//			list = this.QueryBySql(sql);
//			if(list.get(0).get("products")==null){	//没有子产品
//				wh = " ='"+productid+"'";
//			}else{
//				wh = " in ("+list.get(0).get("products")+",'"+productid+"')";
//			}
//			sql = "select e.fcustproductid from t_pdt_productrelationentry e left join t_pdt_productrelation e1 on e.fparentid=e1.fid where e1.fproductid"+wh;
//			sql = "select group_concat(quote(fcustproductid)) cs from ("+sql+") u";
//			list = this.QueryBySql(sql);
//			custProducts = list.get(0).get("cs").toString();
//			sql = "select 1 from t_ord_deliverapply where fcusproductid in ("+custProducts+")";
//			if(this.QueryExistsBySql(sql)){
//				throw new DJException("此方案已有产品已在要货申请中使用，不能删除！");
//			}
//			sql = "select 1 from t_pdt_vmiproductparam where fproductid "+wh;
//			if(this.QueryExistsBySql(sql)){
//				throw new DJException("此方案已有产品在安全库存中存在，不能删除！");
//			}
//			//删除客户产品和产品的关联
//			sql = "delete from t_pdt_productrelationentry where fcustproductid in ("+custProducts+")";
//			this.ExecBySql(sql);
//			sql = "delete from t_pdt_productrelation where fproductid "+wh;
//			this.ExecBySql(sql);
//			//删除客户产品
//			sql = "delete from t_bd_custproduct where fid in ("+custProducts+")";
//			this.ExecBySql(sql);
//			//删除产品和产品间的关联
//			sql = "delete from t_pdt_productdefproducts where fparentid = '"+products.get(0).get("fid")+"'";
//			this.ExecBySql(sql);
//			sql = "delete from t_pdt_productdef where fid "+wh;
//			this.ExecBySql(sql);
//		}
		//删除方案特性
		sql = "delete from t_ord_schemedesignentry where fparentid = '"+fid+"'";
		this.ExecBySql(sql);
		//删除方案设计
		sql = "delete from t_ord_schemedesign where fid = '"+fid+"'";
		this.ExecBySql(sql);
		// 删除附件和记录
		sql = "select * from t_ord_productdemandfile where fparentid = '"+fid+"'";
		list = this.QueryBySql(sql);
		if(list.size()>0){
			sql = "delete from t_ord_productdemandfile where fparentid = '"+fid+"'";
			this.ExecBySql(sql);
			for(HashMap<String, Object> map : list){
				file = new File(map.get("fpath").toString());
				if(file.exists()){
					file.delete();
				}
			}
		}
	}

	@Override
	public void ExecUnReceiveProductdemand(String fidcls,int isExit) {
		// TODO Auto-generated method stub
	String sql="";
	if(isExit==1)
	{
		sql="select fid from t_ord_schemedesign where ffirstproductid in "+fidcls;
		List<HashMap<String, Object>> slist=this.QueryBySql(sql);
		for(int i=0;i<slist.size();i++)
		{
			this.DelSchemeDesign(slist.get(i).get("fid").toString());
		}
	}
	sql = "update t_ord_firstproductdemand set fstate='已分配',freceived=0,freceivetime= null,freceiver=null where (fstate='已接收' or fstate='已设计') and fid in "+fidcls;
	this.ExecBySql(sql);
	
	}
	@Override
public void ExecUnaffirm(String fid,List<HashMap<String,Object>> productlist,List<HashMap<String,Object>> entrylist){
	String sql = "select * from t_ord_schemedesign where fid ="+fid;
	List<HashMap<String,Object>> schemedesignlist = this.QueryBySql(sql);
	if(productlist.size()>0){
		
		sql = "select fid from t_pdt_productdef where schemedesignid in ("+fid+")";
		List<HashMap<String,Object>> list = this.QueryBySql(sql);//查产品信息
		if(list.size()>0){
			sql = "SELECT  fproductid as fid FROM t_pdt_productdefproducts  where fparentid in('"+list.get(0).get("fid")+"')";
			List<HashMap<String,Object>> subProducts = this.QueryBySql(sql);
			list.addAll(subProducts);
		}
		if(list.size()>0){
			String productid = "'";
			for(int j =0;j<list.size();j++){
				productid += list.get(j).get("fid").toString();
				if(j<list.size()-1){
					productid +="','";
				}
			}
			productid +="'"; 
			sql = "SELECT 1 FROM `t_ord_saleorder` WHERE  FPRODUCTDEFID in ("+productid+")";
			list = this.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException(schemedesignlist.get(0).get("fnumber")+"已生成生产订单,不能取消确认");
			}
//			
////			sql = "SELECT GROUP_CONCAT(QUOTE(r.fcustproductid)) cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid WHERE e.fproductid in ("+productid+")";
//			sql = "  SELECT GROUP_CONCAT(QUOTE(pt.fcustproductid)) cusproductids  FROM t_pdt_Productrelation p LEFT JOIN t_pdt_Productrelationentry pt ON p.fid=pt.fparentid "+
//					"WHERE p.fproductid in("+productid+")";//产品关联关系
//			list = this.QueryBySql(sql);
//			if(list.get(0).get("cusproductids")==null){
//				sql = "SELECT GROUP_CONCAT(QUOTE(r.fcustproductid)) cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid WHERE e.fproductid in("+productid+")";
//				list = this.QueryBySql(sql);
//			}
//			String cusproductids = list.get(0).get("cusproductids").toString();
			String cusproductids = productToCustproduct(productid);
			sql = "select 1 from t_ord_deliverapply where fcusproductid in("+cusproductids+")";
			list = this.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException(schemedesignlist.get(0).get("fnumber")+"已生成要货申请,不能取消确认");
			}
		}
	}
	if(entrylist.size()>0){
		sql = "SELECT fnumber FROM `t_ord_productplan` WHERE faudited=1 and fschemedesignid in ("+fid+")";
		List<HashMap<String,Object>> list = this.QueryBySql(sql);
		if(list.size()>0){
			throw new DJException(schemedesignlist.get(0).get("fnumber")+"的制造商订单已被审核,不能取消确认");
		}
		
		sql = "select fid from t_pdt_productdef where schemedesignid in ("+fid+")";
		list = this.QueryBySql(sql);
		if(list.size()>0){
			String productid = "'";
			for(int j =0;j<list.size();j++){
				productid += list.get(j).get("fid").toString();
				if(j<list.size()-1){
					productid +="','";
				}
			}
			productid +="'"; 
			//客户产品关联关系
//			sql = "SELECT GROUP_CONCAT(QUOTE(r.fcustproductid)) cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid WHERE e.fproductid in("+productid+")";
//			sql = "  SELECT pt.fcustproductid cusproductids  FROM t_pdt_Productrelation p LEFT JOIN t_pdt_Productrelationentry pt ON p.fid=pt.fparentid "+
//					"WHERE p.fproductid in("+productid+")";//产品关联关系
//			list = this.QueryBySql(sql);
//			String cusproductids = "";
//			for(int i = 0;i<list.size();i++){
//				cusproductids +=list.get(i).get("cusproductids");
//				if(i<list.size()-1){
//					cusproductids += ",";
//				}
//			}
//			if(list.get(0).get("cusproductids")==null){
//				cusproductids = "";
//				sql = "SELECT r.fcustproductid cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid WHERE e.fproductid in("+productid+")";
//				list = this.QueryBySql(sql);
//				for(int i = 0;i<list.size();i++){
//					cusproductids +=list.get(i).get("cusproductids");
//					if(i<list.size()-1){
//						cusproductids += ",";
//					}
//				}
//			}
//			cusproductids = "'"+cusproductids.replace(",", "','")+"'";
//			String cusproductids = list.get(0).get("cusproductids").toString();
			String cusproductids = productToCustproduct(productid);
			sql = "select 1 from t_ord_deliverapply where fcusproductid in("+cusproductids+")";
			list = this.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException(schemedesignlist.get(0).get("fnumber")+"已生成要货申请,不能取消确认");
			}
		}
	}
}
@Override
public String productToCustproduct(String productid){
	String sql = "";
	List<HashMap<String,Object>> list = null;
	sql = "  SELECT pt.fcustproductid cusproductids  FROM t_pdt_Productrelation p LEFT JOIN t_pdt_Productrelationentry pt ON p.fid=pt.fparentid "+
			"WHERE p.fproductid in("+productid+")";//产品关联关系
	list = this.QueryBySql(sql);
	String cusproductids = "";
	for(int i = 0;i<list.size();i++){
		cusproductids +=list.get(i).get("cusproductids");
		if(i<list.size()-1){
			cusproductids += ",";
		}
	}
	if(list.size()==0){
		cusproductids = "";
		sql = "SELECT r.fcustproductid cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid WHERE e.fproductid in("+productid+")";
		list = this.QueryBySql(sql);
		for(int i = 0;i<list.size();i++){
			cusproductids +=list.get(i).get("cusproductids");
			if(i<list.size()-1){
				cusproductids += ",";
			}
		}
	}
	cusproductids = "'"+cusproductids.replace(",", "','")+"'";
	return cusproductids;
}
	@Override
	public String ExecGenerateDelivery(HttpServletRequest request) {
		
		String fidString = request.getParameter("fids"); 
		List<HashMap<String,Object>> list;
		String[] fids = fidString.split(",");
		String sql  = "";
		for (String fid : fids) {
			sql = "select * from t_ord_schemedesign where fid ='"+fid+"'";
			list = this.QueryBySql(sql);
			if(firstproductdemandDao.closeProductDemand(list.get(0).get("ffirstproductid").toString(), this)){
				throw new DJException("已关闭的需求,不能操作！");
			}
			//逐个判断，
			generateDeliverySingle(request, fid);
			
		}
		
		//逐个生成
		
		
		for (String fid : fids) {
			
			String hql = " SELECT fid FROM SchemeDesignEntry where fparentid = '%s' ";
			
			hql = String.format(hql, fid);
			
			List<String> fidsT = QueryByHql(hql);
			
			
			generateDelivery(request, fid, fidsT);
			
		}
	
		

		return GENERATE_DELIVERY_RETURN_TYPE_SUCCESS;
	}

	private void generateDeliverySingle(HttpServletRequest request, String fid) {
		String hql = " SELECT fid FROM SchemeDesignEntry where fparentid = '%s' ";
		
		hql = String.format(hql, fid);
		
		List<String> fids = QueryByHql(hql);
		
		Schemedesign schemedesignT = (Schemedesign)Query(Schemedesign.class, fid);
		
		if (schemedesignT.getFconfirmed() != 1) {
			
			throw new DJException("方案：" + schemedesignT.getFnumber() + GENERATE_DELIVERY_RETURN_TYPE_HAS_NO_CONFIRMED);
			
		}
		
		//没有特性
		if (fids.size() == 0) {
			
			throw new DJException("方案：" + schemedesignT.getFnumber() + GENERATE_DELIVERY_RETURN_TYPE_NO_CHARACTER);
			
		}
		
		String sql = " select * from t_ord_schemedesignentry where fparentid = '%s' and fallot=1 ";
		sql = String.format(sql, schemedesignT.getFid());
		if(QueryBySql(sql).size()>1){
			throw new DJException("方案“" + schemedesignT.getFnumber() + "”特性已全部消耗完毕!");
		}
		//判断有没有对于要货申请
//		for (String id : fids) {
//			
//			String sql2 = " select * from t_ord_deliverapply where ftraitid = '%s' ";
//			
//			sql2 = String.format(sql2, id);
//			
//			SchemeDesignEntry schemeDesignEntryT = (SchemeDesignEntry) Query(SchemeDesignEntry.class, id);
//			
//			if (QueryBySql(sql2).size() != 0) {
//				
//				throw new DJException("方案“" + schemedesignT.getFnumber() + "”的特性“" + schemeDesignEntryT.getFcharacter()+"”" + GENERATE_DELIVERY_RETURN_TYPE_HAS_GENERATED);
//				
//			}
//			
//			
//		}
		
		
//		generateDelivery(request, fid, fids);
		
		
		
//		return GENERATE_DELIVERY_RETURN_TYPE_SINGLE_SUCCESS;
	}

	private void generateDelivery(HttpServletRequest request, String fid, List<String> fids) {
		Schemedesign schemeDesignT = (Schemedesign)Query(Schemedesign.class, fid);
		Firstproductdemand demandT = (Firstproductdemand) Query(Firstproductdemand.class, schemeDesignT.getFfirstproductid());
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		
		for (String id : fids) {
			
			SchemeDesignEntry schemeDesignEntryT = (SchemeDesignEntry) Query(SchemeDesignEntry.class, id);
			int famount = schemeDesignEntryT.getFentryamount()-schemeDesignEntryT.getFrealamount();
			if(famount==0){
				continue;
			}
			
			Deliverapply deliverapplyT = new Deliverapply();
			String sql = "SELECT ad.fid,ad.fname,ad.fdetailaddress,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"+demandT.getFcreatid()+"'";
			List<HashMap<String, Object>> sList = this.QueryBySql(sql);
			//List<Address> addresssT = (List<Address>) QueryByHql(String.format(" from Address where fcustomerid = '%s'", schemeDesignT.getFcustomerid()));
			
			//only 1 address,如果该客户关联的地址只有一个，者直接将地址赋值给要货申请，如果有多个者生成的要货申请中的地址不赋值；
			if (sList.size() == 1) {
				
				//Address addressT = addresssT.get(0);
				
				deliverapplyT.setFlinkman((String)sList.get(0).get("flinkman"));
				deliverapplyT.setFlinkphone((String)sList.get(0).get("fphone"));
				deliverapplyT.setFaddress((String)sList.get(0).get("fdetailaddress")); 
				deliverapplyT.setFaddressid((String)sList.get(0).get("fid"));
				
			}
			
			deliverapplyT.setFcreatorid(demandT.getFcreatid());
			deliverapplyT.setFcreatetime(new Date());
			deliverapplyT.setFupdateuserid(userid);
			deliverapplyT.setFupdatetime(new Date());
			deliverapplyT.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
			
			deliverapplyT.setFcustomerid(schemeDesignT.getFcustomerid());
			
			//没有对应客户产品时会出错 
			deliverapplyT.setFcusproductid((String)(QueryByHql(String.format(" select fid from Custproduct where fcharacterid = '%s' ", id)).get(0)));
			deliverapplyT.setFarrivetime(schemeDesignT.getFoverdate());
			
			deliverapplyT.setFamount(famount);
			
			deliverapplyT.setFdescription(schemeDesignT.getFdescription());
			deliverapplyT.setFordered(0);
			deliverapplyT.setFordermanid("");
			
			deliverapplyT.setFordertime(null);
			deliverapplyT.setFsaleorderid("");
			deliverapplyT.setFordernumber(schemeDesignEntryT.getFpurchasenumber());
			deliverapplyT.setForderentryid("");
			deliverapplyT.setFimportEas(0);
			
			deliverapplyT.setFimportEasuserid("");
			deliverapplyT.setFimportEastime(null);
			deliverapplyT.setFouted(0);
			deliverapplyT.setFoutorid("");
			deliverapplyT.setFouttime(null);
			
			
			deliverapplyT.setFeasdeliverid("");
			deliverapplyT.setFistoPlan(0);
			deliverapplyT.setFplanTime(null);
			deliverapplyT.setFplanNumber("");
			
			deliverapplyT.setFplanid("");
			deliverapplyT.setFalloted(0);
			deliverapplyT.setFiscreate(0);
			deliverapplyT.setFcusfid("");
			deliverapplyT.setFstate(0);
			
			deliverapplyT.setFtype("0");
			deliverapplyT.setFtraitid(schemeDesignEntryT.getFid());
			deliverapplyT.setFcharacter(schemeDesignEntryT.getFcharacter());
			
			//设置制造商为东经
			deliverapplyT.setFsupplierid("39gW7X9mRcWoSwsNJhU12TfGffw=");
			
			deliverapplyT.setFid(CreateUUid());
			
			saveOrUpdate(deliverapplyT);
			
			//修改分路状态
			String insertsql="update t_ord_schemedesignentry set frealamount=fentryamount,fallot=1 where fid='"+ id +"'";
			ExecBySql(insertsql);

		}
	}
	
	
	@Override
	public String ExecUndoGenerateDelivery(HttpServletRequest request) {
		// TODO Auto-generated method stub

		String result = null;

		String fidString = request.getParameter("fids");

		String[] fids = fidString.split(",");
		String sqls = "";
		List<HashMap<String,Object>> list;
		// 特性判断
		for (String id : fids) {
			
			sqls = "select * from t_ord_schemedesign where fid ='"+id+"'";
			list = this.QueryBySql(sqls);
			if(firstproductdemandDao.closeProductDemand(list.get(0).get("ffirstproductid").toString(), this)){
				throw new DJException("已关闭的需求,不能操作！");
			}
			List<String> idEs = QueryByHql(String
					.format(" select fid from SchemeDesignEntry where fparentid = '%s' ",
							id));

			if (idEs.size() == 0) {

				Schemedesign schemeDesignT = (Schemedesign) Query(
						Schemedesign.class, id);

				throw new DJException(String.format("方案：%s不能操作",
						schemeDesignT.getFnumber()));

			}

		}

		for (String id : fids) {

			List<String> idEs = QueryByHql(String
					.format(" select fid from SchemeDesignEntry where fparentid = '%s' ",
							id));
			// 判断
			for (String fid : idEs) {

				String sql = " select * from t_ord_deliverapply where ftraitid = '%s' ";

				SchemeDesignEntry schemeDesignEntryT = null;

				Schemedesign schemeDesignT = null;

				if (QueryBySql(String.format(sql, fid)).size() == 0) {
//
//					schemeDesignEntryT = (SchemeDesignEntry) Query(
//							SchemeDesignEntry.class, fid);
//
//					schemeDesignT = (Schemedesign) Query(Schemedesign.class, id);
//
//					throw new DJException(String.format(
//							"方案：%s特性：%s没有生成要货申请，不能取消",
//							schemeDesignT.getFnumber(),
//							schemeDesignEntryT.getFcharacter()));
//
					continue;
				}
				
				sql += " and fiscreate = 0 ";

				if (QueryBySql(String.format(sql, fid)).size() == 0) {

					schemeDesignEntryT = (SchemeDesignEntry) Query(
							SchemeDesignEntry.class, fid);

					schemeDesignT = (Schemedesign) Query(Schemedesign.class, id);

					throw new DJException(String.format(
							"方案：%s特性：%s已生成要货管理，不能取消",
							schemeDesignT.getFnumber(),
							schemeDesignEntryT.getFcharacter()));

				}
				
				if (QueryBySql(String.format(" SELECT * FROM t_ord_deliverorder where faudited = 1 and ftraitid = '%s'  ", fid)).size() > 0) {

					schemeDesignEntryT = (SchemeDesignEntry) Query(
							SchemeDesignEntry.class, fid);

					schemeDesignT = (Schemedesign) Query(Schemedesign.class, id);

					throw new DJException(String.format(
							"方案：%s特性：%s配送信息已审核，不能取消",
							schemeDesignT.getFnumber(),
							schemeDesignEntryT.getFcharacter())); 

				}

			}

		}

		// 执行删除

		String fidInString = fidString.replaceAll(",", "','");

		fidInString = "('" + fidInString + "')";

		List<String> idEs = QueryByHql(String.format(
				" select fid from SchemeDesignEntry where fparentid in %s ",
				fidInString));

		StringBuilder sbu = new StringBuilder("(");
		for (String id : idEs) {

			if (id.equals(idEs.get(0))) {

				sbu.append("'");

			} else {

				sbu.append(",'");

			}

			sbu.append(id);
			sbu.append("'");

		}
		sbu.append(")");

		String sql2 = " delete from t_ord_deliverapply where ftraitid in "
				+ sbu.toString();

		ExecBySql(sql2);

		//修改分路状态
		String insertsql="update t_ord_schemedesignentry set frealamount=0,fallot=0 where fid in "+ sbu.toString();
		ExecBySql(insertsql);
		
		return "已成功";
	}
	
	

	@Override
	public void ExecreceiveProductdemand(String userid, String ficls,String fsupplierid) {
		// TODO Auto-generated method stub
		String sql = "update t_ord_firstproductdemand set fstate='已接收',freceived=1,freceivetime= now(),freceiver='"+userid+"', fsupplierid='"+fsupplierid+"' where fid in "+ficls;
		this.ExecBySql(sql);
//		reciverGroup(ficls,userid); //创建群组 暂不用
	}


	public  void reciverGroup(String ficls,String userid){
		List<HashMap<String, Object>> flist=this.QueryBySql("select d.fid,d.fcreatid,u.fimid,d.fname,d.fnumber,d.fdescription from t_ord_firstproductdemand d left join t_sys_user u on d.fcreatid=u.fid  where d.fid in"+ficls);
		if(flist.size()==0){
			 throw new DJException("没有可接收的需求");
		}
//		List<HashMap<String, Object>> ulist=this.QueryBySql("select fimid from t_sys_user where fid='"+userid+"'");
		SysUser uinfo=(SysUser)this.Query(SysUser.class, userid);
		String sql="";
		DongjingClient c=new DongjingClient();
		c.setURL("http://202.107.222.99:8066/ichat/cgi");
		for(int i=0;i<flist.size();i++){
		HashMap cmap=flist.get(i);
		if(cmap.get("fimid").equals(uinfo.getFimid()))
		{
				throw new DJException("需求编号"+cmap.get("fnumber")+",需求创建者不能是接收人！");
		}
		c.setMethod("group_create");
		c.setRequestProperty("summary",(String)cmap.get("fdescription"));
		c.setRequestProperty("name", (String)cmap.get("fname"));
		c.setRequestProperty("founder", ""+cmap.get("fimid"));
			if(c.SubmitData())
			{
			  String r=	c.getResponse().getResultString();
			  JSONObject o= JSONObject.fromObject(r);
			  if((Integer)o.get("code")!=200){
				  throw new DJException("创建群组失败！");
			  }
			  String groupId=""+o.getJSONObject("data").get("groupId");
			  sql ="update t_ord_firstproductdemand set fgroupid='"+groupId+"' where fid='"+cmap.get("fid")+"'";
			  this.ExecBySql(sql);
			  	c.setMethod("groupMember_add");
				c.setRequestProperty("account",""+cmap.get("fimid"));
				c.setRequestProperty("groupId",  groupId);
				c.SubmitData();
				c.setMethod("groupMember_add");
				c.setRequestProperty("account",""+uinfo.getFimid());
				c.setRequestProperty("groupId",  groupId);
				c.SubmitData();
				
			}
		}
	}
	
	
	
	public  void reciverDesignGroup(Schemedesign sinfo){
			SysUser uinfo=(SysUser)this.Query(SysUser.class, sinfo.getFcreatorid());
				DongjingClient c=new DongjingClient();
				c.setURL("http://202.107.222.99:8066/ichat/cgi");
			  	c.setMethod("groupMember_add");
				c.setRequestProperty("account",""+uinfo.getFimid());
				c.setRequestProperty("groupId",  sinfo.getFgroupid());
				c.SubmitData();
	}

}
