package Com.Dao.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.org.mozilla.javascript.internal.Undefined;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Customer;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Useronline;
import Com.Entity.order.FTUProduct;
import Com.Entity.order.FTURelation;
import Com.Entity.order.FTUSaledeliver;
import Com.Entity.order.FTUSaledeliverEntry;
@Service("ftuDao")
public class FTUDao extends BaseDao implements IFTUDao {
	
	@Autowired
	private IBaseSysDao baseSysDao;
	
	@Override
	public List<HashMap<String, Object>> getFtuRelationName(HttpServletRequest request,int i) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		List<HashMap<String, Object>> custList=this.QueryBySql(sql);
		String supplierid = null;
		if(custList.size()>0)
		{
			HashMap<String,Object> map=custList.get(0);
			supplierid = (String)map.get("fsupplierid");
		}
		sql = "select fid,fname,ftel from t_ftu_relation where fsupplierid='"+supplierid+"' and ftype = "+i;
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		return list;
	}

	@Override
	public void saveFTUSaleDeliver(FTUSaledeliver saledeliver,
			List<Custproduct> productList) {
		String fparentid = saledeliver.getFid();
//		String fcustomerid = saledeliver.getFcustomerid();
		String fcustomerid = saledeliver.getFcustomer();
		String fbusNumber = saledeliver.getFbusNumber();
		FTUSaledeliverEntry entry;
		String fname;
		String fspec;
		BigDecimal fprice;
		StringBuffer  cid = new StringBuffer(" ");
		StringBuffer  pid = new StringBuffer(" ");
		int i = 0;
		baseSysDao.ExecBySql("delete from t_ftu_saledeliverentry where fparentid='"+fparentid+"'");//保存时先删除所有分录信息
		for(Custproduct product: productList){
//			if(StringUtils.isEmpty(product.getFname())){
//				throw new DJException("产品名称不能为空！");
//			}
//			if(StringUtils.isEmpty(product.getFspec())){
//				throw new DJException("产品规格不能为空！");
//			}
//			if(((Integer)product.getFamount())==null||product.getFamount()==0){
//				throw new DJException("数量不能为空或0！");
//			}
//			if(product.getFprice()==null){
//				throw new DJException("单价不能为空！");
//			}
			fname = product.getFname().trim();
			fspec = product.getFspec().trim();
			fprice = product.getFprice()==null?BigDecimal.ZERO:product.getFprice();
			
			if(fprice.intValue()<0){
				throw new DJException("产品单价不能小于0");
			}
			String sql = "select fid,fproductid from t_bd_custproduct where fcustomerid='"+fcustomerid+"' and  REPLACE( fname,'*','X')='"+fname+"' and REPLACE( fspec,'*','X')='"+fspec+"'";
			String fid = baseSysDao.getStringValue(sql, "fid");
			Productdef p = new Productdef();
			if(fid==null){
				product.setFid(this.CreateUUid());
				product.setFcreatetime(new Date());
				
				p.setFid(this.CreateUUid());
				p.setFcreatetime(new Date());
			}else{
				product.setFid(fid);
				if(!StringUtils.isEmpty(product.getFproductid())){
					p.setFid(baseSysDao.getStringValue(sql, "fproductid"));
					p.setFcreatetime(product.getFcreatetime());
				}else{
					p.setFid(this.CreateUUid());
					p.setFcreatetime(new Date());
				}
				
			}
			p.setFname(product.getFname());
			p.setFcharacter(product.getFspec());
			p.setFprice(product.getFprice());
			p.setFdescription(product.getFdescription());
			p.setFcustomerid(fcustomerid);
			p.setFsupplierid(saledeliver.getFsupplierid());
			p.setFeffect(1);
			p.setFeffected(1);
			product.setFcustomerid(fcustomerid);
			product.setFproductid(p.getFid());
			product.setFordercount(1);
			product.setFtype(1);
			product.setFeffect(1);
			if(cid.indexOf(product.getFid())<0){//相同客户产品ID不能保存
				cid.append(product.getFid()+",");
				if(pid.indexOf(product.getFproductid())<0){
					pid.append(product.getFproductid()+",");
					this.saveOrUpdate(p);//保存产品信息
				}
				this.saveOrUpdate(product);//保存客户产品信息
			}
			entry = new FTUSaledeliverEntry();
			entry.setFid(this.CreateUUid());
			entry.setFftuproductid(product.getFid());
			entry.setFamount(String.valueOf(product.getFamount()));
			entry.setFprice(String.valueOf(product.getFprices()));
			entry.setFdanjia(product.getFprice()==null?"0":String.valueOf(product.getFprice()));
			entry.setFparentid(fparentid);
			entry.setFdescription(product.getFdescription());
			entry.setFsequence(i++);
			this.saveOrUpdate(entry);
		}
		this.saveOrUpdate(saledeliver);
		String fcustomer = saledeliver.getFcustomer();
		String fdriver = saledeliver.getFdriver();
		String fclerk = saledeliver.getFclerk();
		String fsupplierid = saledeliver.getFsupplierid();
		FTURelation relation;
		//1-司机，2-开单员,3-车牌号码
		if(!StringUtils.isEmpty(fdriver)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fdriver+"' and ftype=1")){
			relation = new FTURelation();
			relation.setFid(this.CreateUUid());
			relation.setFsupplierid(fsupplierid);
			relation.setFname(fdriver);
			relation.setFtype((short)1);
			this.saveOrUpdate(relation);
		}
		if(!StringUtils.isEmpty(fclerk)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fclerk+"' and ftype=2")){
			relation = new FTURelation();
			relation.setFid(this.CreateUUid());
			relation.setFsupplierid(fsupplierid);
			relation.setFname(fclerk);
			relation.setFtype((short)2);
			this.saveOrUpdate(relation);
		}
		if(!StringUtils.isEmpty(fbusNumber)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fbusNumber+"' and ftype=3")){
			relation = new FTURelation();
			relation.setFid(this.CreateUUid());
			relation.setFsupplierid(fsupplierid);
			relation.setFname(fbusNumber);
			relation.setFtype((short)3);
			this.saveOrUpdate(relation);
		}
		
		
	}
	@Override
	public void saveFTUprint(String fid){
		String sql = String.format("update t_ftu_saledeliver set fprinttime=now(),fprintcount=ifnull(fprintcount,0)+1 where fid='%s'",fid);
		this.ExecBySql(sql);
	}
	@Override
	public void saveNewFTUSaleDeliver(FTUSaledeliver fs,
			List<FTUSaledeliverEntry> fse,String userid) {
		String sql;
		String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
//		String fsuppliername = baseSysDao.getStringValue("select fname from t_sys_supplier where fid ='"+fsupplierid+"'", "fname");
		String fsuppliername = baseSysDao.getStringValue("select fcustomername from t_sys_user where fid ='"+userid+"'", "fcustomername");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		if(fs!=null){
			FTUSaledeliver ftuSaledeliver  = (FTUSaledeliver)this.Query(FTUSaledeliver.class, fs.getFid());
			if(ftuSaledeliver!=null){//已经有送货凭证时，删产品、送货凭证分录、送货凭证
				sql = "select fftuproductid from t_ftu_saledeliverentry where fparentid='%s'";
				List<HashMap<String,Object>> list = this.QueryBySql(String.format(sql, ftuSaledeliver.getFid()));
//				for (HashMap<String, Object> hashMap : list) {//循环删除客户产品、产品信息
//					sql = "delete from t_pdt_productdef  where fid='%s'";
//					this.ExecBySql(String.format(sql,baseSysDao.getStringValue("select fproductid from t_bd_custproduct where fid='"+hashMap.get("fftuproductid")+"'", "fproductid")));
//					sql = "delete from t_bd_custproduct where fid='%s'";
//					this.ExecBySql(String.format(sql,hashMap.get("fftuproductid")));
//				}
				sql = "delete from t_ftu_saledeliverentry where fparentid='%s'";
				this.ExecBySql(String.format(sql, ftuSaledeliver.getFid()));
				sql = "delete from t_ftu_saledeliver where fid='%s'";
				this.ExecBySql(String.format(sql, ftuSaledeliver.getFid()));
			}
			
			Customer c = new Customer();
			if(StringUtils.isEmpty(fs.getFcustomername())){
				throw new DJException("客户不能为空");
			}
			sql = "select c.fid from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where c.fname='"+fs.getFcustomername()+"' and p.fsupplierid='"+fsupplierid+"'";
			List<HashMap<String,Object>>map = this.QueryBySql(sql);
			if(map.size()==0){//新增客户
				c.setFid(this.CreateUUid());
				c.setFname(fs.getFcustomername());
				c.setFphone(fs.getFphone());
				c.setFnumber(fs.getFcustomername());
				c.setFcreatetime(new Date());
				this.saveOrUpdate(c);
				
				Productreqallocationrules p = new Productreqallocationrules();
				p.setFid(this.CreateUUid());
				p.setFcreatetime(new Date());
				p.setFcreatorid(userid);
				p.setFcustomerid(c.getFid());
				p.setFsupplierid(fsupplierid);
				this.saveOrUpdate(p);
				
			}else{//有客户时，更新客户信息
				c.setFid((String)map.get(0).get("fid"));
				sql = "update t_bd_customer set fphone='"+fs.getFphone()+"' where fid='"+c.getFid()+"'";
				this.ExecBySql(sql);
			}
			if(!StringUtils.isEmpty(fs.getFcustAddress())){
				sql = "select fid,fname from t_bd_address where fname ='"+fs.getFcustAddress()+"'  and fcustomerid='"+c.getFid()+"'";
				map = this.QueryBySql(sql);
				Address address = new Address();
				if(map.size()==0){//客户地址
					address.setFid(this.CreateUUid());
					address.setFname(fs.getFcustAddress());
					address.setFcreatetime(new Date());
					address.setFcustomerid(c.getFid());
					address.setFdetailaddress(fs.getFcustAddress());
					this.saveOrUpdate(address);
					
					CustRelationAdress cd = new CustRelationAdress();
					cd.setFid(this.CreateUUid());
					cd.setFaddressid(address.getFid());
					cd.setFcustomerid(c.getFid());
					cd.setFeffect(1);
					this.saveOrUpdate(cd);
				}else{
					address.setFname((String)map.get(0).get("fname"));
				}
			}
			
			fs.setFid(this.CreateUUid());
			fs.setFsupplierid(fsupplierid);
			fs.setFsuppliername(fsuppliername);
			fs.setFcreator(userid);
			fs.setFcustomer(c.getFid());
			fs.setFphone(fs.getFphone());
		
			String fparentid = fs.getFid();
			String fcustomerid = fs.getFcustomer();
			int i = 0;
			Productdef p;
			Custproduct cp;
			StringBuffer  cid = new StringBuffer(" ");
			StringBuffer  pid = new StringBuffer(" ");
			baseSysDao.ExecBySql("delete from t_ftu_saledeliverentry where fparentid='"+fparentid+"'");//保存时先删除所有分录信息
			for (FTUSaledeliverEntry ftuSaledeliverEntry : fse) {
				sql = "select fid,fproductid from t_bd_custproduct where fcustomerid='"+fcustomerid+"' and  REPLACE( fname,'*','X')='"+ftuSaledeliverEntry.getFcusproductname()+"' and REPLACE( fspec,'*','X')='"+ftuSaledeliverEntry.getFspec()+"'";
				String fid = baseSysDao.getStringValue(sql, "fid");//客户产品ID
				p = new Productdef();
				cp = new Custproduct();
				if(fid==null){
					cp.setFid(this.CreateUUid());
					cp.setFcreatetime(new Date());
					
					p.setFid(this.CreateUUid());
					p.setFcreatetime(new Date());
				}else{
					cp.setFid(fid);
					if(!StringUtils.isEmpty(fid)){
						p.setFid(baseSysDao.getStringValue(sql, "fproductid"));
						p.setFcreatetime(cp.getFcreatetime());
					}else{
						p.setFid(this.CreateUUid());
						p.setFcreatetime(new Date());
					}
					
				}
				try {
					p.setFprice(BigDecimal.valueOf(ftuSaledeliverEntry.getFdanjia()==null?0:Double.valueOf(ftuSaledeliverEntry.getFdanjia())));
					cp.setFprice(BigDecimal.valueOf(ftuSaledeliverEntry.getFdanjia()==null?0:Double.valueOf(ftuSaledeliverEntry.getFdanjia())));
				} catch (Exception e) {
					// TODO: handle exception
				}
				p.setFname(ftuSaledeliverEntry.getFcusproductname());
				p.setFcharacter(ftuSaledeliverEntry.getFspec());
				p.setFdescription(ftuSaledeliverEntry.getFdescription());
				p.setFcustomerid(fcustomerid);
				p.setFsupplierid(fs.getFsupplierid());
				p.setFeffect(1);
				p.setFeffected(1);
				
				cp.setFcustomerid(fcustomerid);
				cp.setFname(ftuSaledeliverEntry.getFcusproductname());
				cp.setFspec(ftuSaledeliverEntry.getFspec());
				cp.setFproductid(p.getFid());
				cp.setFdescription(ftuSaledeliverEntry.getFdescription());
				cp.setFunit(ftuSaledeliverEntry.getFunit());
				cp.setFordercount(1);
				cp.setFtype(1);
				cp.setFeffect(1);
				if(cid.indexOf(cp.getFid())<0){//相同客户产品ID不能保存
					cid.append(cp.getFid()+",");
					if(pid.indexOf(cp.getFproductid())<0){
						pid.append(cp.getFproductid()+",");
						this.saveOrUpdate(p);//保存产品信息
					}
					this.saveOrUpdate(cp);//保存客户产品信息
				}
				ftuSaledeliverEntry.setFid(this.CreateUUid());
				ftuSaledeliverEntry.setFftuproductid(cp.getFid());
				ftuSaledeliverEntry.setFparentid(fparentid);
				ftuSaledeliverEntry.setFsequence(i++);
				this.saveOrUpdate(ftuSaledeliverEntry);//保存送货凭证分录信息
			}
			this.saveOrUpdate(fs);//保存送货凭证信息
			
			String fcustomer = fs.getFcustomer();
			String fdriver = fs.getFdriver();
			String fclerk = fs.getFclerk();
	//		String fsupplierid = saledeliver.getFsupplierid();
			FTURelation relation;
			//1-司机，2-开单员
			if(!StringUtils.isEmpty(fdriver)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fdriver+"' and ftype=1")){
				relation = new FTURelation();
				relation.setFid(this.CreateUUid());
				relation.setFsupplierid(fsupplierid);
				relation.setFname(fdriver);
				relation.setFtype((short)1);
				this.saveOrUpdate(relation);
			}
			if(!StringUtils.isEmpty(fclerk)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fclerk+"' and ftype=2")){
				relation = new FTURelation();
				relation.setFid(this.CreateUUid());
				relation.setFsupplierid(fsupplierid);
				relation.setFname(fclerk);
				relation.setFtype((short)2);
				this.saveOrUpdate(relation);
			}
//		if(!StringUtils.isEmpty(fbusNumber)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fbusNumber+"' and ftype=3")){
//			relation = new FTURelation();
//			relation.setFid(this.CreateUUid());
//			relation.setFsupplierid(fsupplierid);
//			relation.setFname(fbusNumber);
//			relation.setFtype((short)3);
//			this.saveOrUpdate(relation);
//		}
		}
	}
}
