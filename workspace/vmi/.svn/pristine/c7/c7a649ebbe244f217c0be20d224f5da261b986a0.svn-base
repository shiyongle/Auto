package Com.Dao.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.SpringContextUtils;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Custproductproducts;
import Com.Entity.System.Custrelation;
import Com.Entity.System.Custrelationentry;
import Com.Entity.System.Productdef;
import Com.Entity.System.ProductdefProducts;
import Com.Entity.System.Productrelation;
import Com.Entity.System.Productrelationentry;
import Com.Entity.order.Productdemandfile;
import Com.Entity.order.Productstructure;
import Com.Entity.order.SchemeDesignEntry;
import Com.Entity.order.Schemedesign;

@Service("ProductstructureDao")
public class ProductstructureDao extends BaseDao implements IProductstructureDao {
	private ISchemeDesignDao SchemeDesignDao = (ISchemeDesignDao)SpringContextUtils.getBean("SchemeDesignDao");
	@Override
	public void SaveOrUpdateProductstructure(List<Productstructure> productList,String fid){
		//增加产品和客户产品以及它们的关联
		Productstructure p = null;
		String sql = "SELECT fid FROM t_ord_productstructure where schemedesignid = '"+fid+"'";
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		if(list.size()!=0){
			sql = "SELECT fid FROM t_ord_productstructure  where 1=1 AND fparentid='"+list.get(0).get("fid")+"'";
			List<HashMap<String, Object>> subProducts = this.QueryBySql(sql);
			list.addAll(subProducts);
		}
		for(int j = 0;j<list.size();j++){
			this.ExecBySql("delete from t_ord_productstructure where fid ='"+list.get(j).get("fid")+"'");
		}
		if(productList.size()==0){
			return;
		}
		String fuuid = "".equals(productList.get(0).getFid())?this.CreateUUid():productList.get(0).getFid();
		for(int i = 0;i<productList.size();i++){
			p = productList.get(i);
			if(p.getFid()==null||"".equals(p.getFid())){
				if(i==0){
					p.setFid(fuuid);
				}else{
					p.setFid(this.CreateUUid());
				}
			}
			
			if(i>0){
				p.setFparentid(fuuid);
			}
			this.saveOrUpdate(p);
		}
	}
	@Override
	public void ExecaffirmSchemeDesign(HttpServletRequest request,String fids,String userid,List<HashMap<String,Object>> productlist,List<HashMap<String,Object>> entrylist,List<HashMap<String,Object>> filelist){
//		sql = "select * from t_ord_schemedesignentry where fparentid in "+fids;
//		List<HashMap<String,Object>> schemedesignlist = SchemeDesignDao.QueryBySql(sql);//查特性信息
		synchronized (this.getClass()){
		Productdef p = null;
		Productdemandfile pdf = null;
		ProductdefProducts pp = null;
		Custproductproducts cpp = null;
		Custproduct cp = null;
		Productrelation pr;
		Productrelationentry pre;
//		Custrelation cust;
//		Custrelationentry custr;
		String prid,//Productrelation主键
		cpid;//Productrelationentry主键
		String fid = this.CreateUUid();//套装ID
		String custfid = null;
		try {
			if(productlist.size()>0){
//				无特性的增加产品和客户产品以及它们的关联
				for(int i =0;i<productlist.size();i++){
					p = new Productdef();
					p.setFnewtype("3");
					p.setSchemedesignid(productlist.get(i).get("schemedesignid").toString());
					//若size=1，为普通产品；新增时，父产品和普通件存在schemedesignid,子产品没有
					if(productlist.get(i).get("schemedesignid")!=null&&!"".equals(productlist.get(i).get("schemedesignid"))&&productlist.size()>1){//父产品
						p.setFnewtype("4");
					}else if(i>0){ //子产品设置schemedesignid为1
						p.setSchemedesignid("1");
					}
					p.setFid(this.CreateUUid());//待测试
					if(i==0){
						fid = p.getFid();
					}
					p.setFnumber(productlist.get(i).get("fnumber").toString());
					p.setFcustomerid(productlist.get(i).get("fcustomerid").toString());
					p.setFname(productlist.get(i).get("fname").toString());
					p.setFversion(productlist.get(i).get("fversion").toString());
					p.setFboxlength(new BigDecimal(productlist.get(i).get("fboxlength").toString()));
					p.setFboxheight(new BigDecimal(productlist.get(i).get("fboxheight").toString()));
					p.setFboxwidth(new BigDecimal(productlist.get(i).get("fboxwidth").toString()));
					p.setFmaterialcodeid(productlist.get(i).get("fmaterialcodeid").toString());
					p.setFcreatetime(new Date());
					p.setFlastupdatetime(new Date());
					p.setFlastupdateuserid(userid);
					p.setFcreatorid(userid);
					p.setSubProductAmount(new Integer(productlist.get(i).get("subProductAmount").toString()));
					p.setFissynctoson(0);//FREJECT FISSEVENLAYER FISCOMBINECROSS FCHROMATICPRECISION
					p.setFreject(0);
					p.setFissevenlayer(0);
					p.setFiscombinecross(0);
					p.setFchromaticprecision(new BigDecimal(5.0000000000));
					this.saveOrUpdate(p);//新增产品信息
					
					if(i==0){
						for(int j = 0;j<filelist.size();j++){
							pdf = new Productdemandfile();
							pdf.setFid(this.CreateUUid());
							pdf.setFname(filelist.get(j).get("fname").toString());
							pdf.setFpath(filelist.get(j).get("fpath").toString());
							pdf.setFparentid(p.getFid());
							this.saveOrUpdate(pdf);
						}
					}
					
					cp = new Custproduct();
					cpid = this.CreateUUid();
					cp.setFtype(1);
					if(i==0){
						custfid = cpid;
						if(productlist.size()>1){
							cp.setFtype(0);
						}
					}
					cp.setFid(cpid);
					cp.setFcreatetime(new Date());
					cp.setFlastupdatetime(new Date());
					cp.setFlastupdateuserid(p.getFcustomerid());
					String spec = productlist.get(i).get("fboxlength")+"*"+productlist.get(i).get("fboxwidth")+"*"+productlist.get(i).get("fboxheight");
					cp.setFspec(spec);
					cp.setFname(p.getFname());
					cp.setFnumber(p.getFnumber());
					cp.setFcustomerid(p.getFcustomerid());
					cp.setFrelationed(2);						//客户产品设置为2，便于在客户产品列表关联产品和修改时控制
					cp.setFproductid(p.getFid());
					this.saveOrUpdate(cp);//新增客户产品信息
					if(i==0){
						for(int j = 0;j<filelist.size();j++){
							pdf = new Productdemandfile();
							pdf.setFid(this.CreateUUid());
							pdf.setFname(filelist.get(j).get("fname").toString());
							pdf.setFpath(filelist.get(j).get("fpath").toString());
							pdf.setFparentid(cp.getFid());
							this.saveOrUpdate(pdf);
						}
					}
					pr = new Productrelation();
					prid = this.CreateUUid();
					pr.setFid(prid);
					pr.setFcustomerid(p.getFcustomerid());
					pr.setFproductid(p.getFid());
					this.saveOrUpdate(pr);
					
					pre = new Productrelationentry();
					pre.setFid(this.CreateUUid());
					pre.setFamount(1);
					pre.setFparentid(prid);
					pre.setFcustproductid(cpid);
					this.saveOrUpdate(pre);
//					cust = new Custrelation();
//					prid = this.CreateUUid();
//					cust.setFid(prid);
//					cust.setFcustomerid(p.getFcustomerid());
//					cust.setFcustproductid(cpid);
//					this.saveOrUpdate(cust);
//					
//					custr = new Custrelationentry();
//					custr.setFid(this.CreateUUid());
//					custr.setFamount(1);
//					custr.setFparentid(prid);
//					custr.setFproductid(p.getFid());
//					this.saveOrUpdate(custr);
					if(i>0){
						//增加产品和子产品关联
//						productlist.remove(p);
							pp = new ProductdefProducts();
							pp.setFid(this.CreateUUid());
							pp.setFparentid(fid);//套装ID
							pp.setFproductid(p.getFid());//存产品ID
							pp.setFamount(new Integer(productlist.get(i).get("subProductAmount").toString()));
							this.saveOrUpdate(pp);
							
							cpp = new Custproductproducts();
							cpp.setFamount(new Integer(productlist.get(i).get("subProductAmount").toString()));
							cpp.setFcustproductid(cpid);
							cpp.setFid(this.CreateUUid());
							cpp.setFparentid(custfid);
							this.saveOrUpdate(cpp);
					}
				}
			}
			if(entrylist.size()>0){//有特性的
				String sql = "select * from t_ord_schemedesign where fid ='"+entrylist.get(0).get("fparentid")+"'";
				List<HashMap<String,Object>> schemedesignlist = this.QueryBySql(sql);
				for(int i =0;i<entrylist.size();i++){
					p = new Productdef();
					p.setFnewtype("3");
					p.setSchemedesignid(entrylist.get(i).get("fparentid").toString());
					p.setFid(this.CreateUUid());
//					p.setFnumber(schemedesignlist.get(0).get("fnumber").toString());
					p.setFcustomerid(schemedesignlist.get(0).get("fcustomerid").toString());
					p.setFname(schemedesignlist.get(0).get("fname").toString());
//					p.setFversion(schemedesignlist.get(i).get("fversion").toString());
					p.setFboxlength(new BigDecimal(schemedesignlist.get(0).get("fboxlength").toString()));
					p.setFboxheight(new BigDecimal(schemedesignlist.get(0).get("fboxheight").toString()));
					p.setFboxwidth(new BigDecimal(schemedesignlist.get(0).get("fboxwidth").toString()));
					p.setFmaterialcodeid(schemedesignlist.get(0).get("fmaterial").toString());
					p.setFtilemodelid(schemedesignlist.get(0).get("ftilemodel").toString());
					p.setFcreatetime(new Date());
					p.setFcreatorid(userid);
//					p.setFcharacter(entrylist.get(i).get("fcharacter").toString());
					p.setFcharacterid(entrylist.get(i).get("fid").toString());
					p.setFcharactername(entrylist.get(i).get("fcharacter").toString());
//					p.setSubProductAmount(new Integer(schemedesignlist.get(i).get("subProductAmount").toString()));
					p.setFissynctoson(0);//FREJECT FISSEVENLAYER FISCOMBINECROSS FCHROMATICPRECISION
					p.setFreject(0);
					p.setFissevenlayer(0);
					p.setFiscombinecross(0);
					p.setFnumber("");
					p.setFchromaticprecision(new BigDecimal(5.0000000000));
					this.saveOrUpdate(p);//新增产品信息
					
					for(int j = 0;j<filelist.size();j++){
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(p.getFid());
						this.saveOrUpdate(pdf);
					}
					String spec = schemedesignlist.get(0).get("fboxlength")+"*"+schemedesignlist.get(0).get("fboxwidth")+"*"+schemedesignlist.get(0).get("fboxheight");
					cp = new Custproduct();
					cp.setFid(this.CreateUUid());
					cp.setFcreatetime(new Date());
					cp.setFtype(1);
					cp.setFspec(spec);
					cp.setFname(p.getFname());
					cp.setFnumber("");
					cp.setFcustomerid(p.getFcustomerid());
					cp.setFcharacterid(entrylist.get(i).get("fid").toString());
					cp.setFcharactername(entrylist.get(i).get("fcharacter").toString());
					cp.setFrelationed(2);//客户产品设置为2，便于在客户产品列表关联产品和修改时控制
					cp.setFproductid(p.getFid());
					this.saveOrUpdate(cp);//新增客户产品信息
					
					for(int j = 0;j<filelist.size();j++){
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(cp.getFid());
						this.saveOrUpdate(pdf);
					}
					
					pr = new Productrelation();
//					prid = this.CreateUUid();
					pr.setFid(this.CreateUUid());
					pr.setFcustomerid(p.getFcustomerid());
					pr.setFproductid(p.getFid());
					this.saveOrUpdate(pr);
					
					pre = new Productrelationentry();
					pre.setFid(this.CreateUUid());
					pre.setFamount(1);
					pre.setFparentid(pr.getFid());
					pre.setFcustproductid(cp.getFid());
					this.saveOrUpdate(pre);
					
//					cust = new Custrelation();
//					prid = this.CreateUUid();
//					cust.setFid(prid);
//					cust.setFcustomerid(p.getFcustomerid());
//					cust.setFcustproductid(cp.getFid());
//					this.saveOrUpdate(cust);
//					
//					custr = new Custrelationentry();
//					custr.setFid(this.CreateUUid());
//					custr.setFamount(1);
//					custr.setFparentid(prid);
//					custr.setFproductid(p.getFid());
//					this.saveOrUpdate(custr);
				}
			}
			
			if(entrylist.size()>0){//有特性 需要下单
				Schemedesign sdinfo = (Schemedesign) SchemeDesignDao.QueryByHql("from Schemedesign where fid = "+ fids).get(0);
//				if(sdinfo.getFconfirmed()==0){
//					throw new DJException("该方案未确认，不能下单！");
//				}
				ArrayList<SchemeDesignEntry> sdentry = (ArrayList<SchemeDesignEntry>) SchemeDesignDao.QueryByHql("from SchemeDesignEntry where fparentid = "+ fids);
				ArrayList<Productdef> productdefCol = (ArrayList<Productdef>)SchemeDesignDao.QueryByHql("from Productdef where schemedesignid = "+fids);
				Productdef productdefinfo = null;
				if(productdefCol.size()>0){
					productdefinfo = productdefCol.get(0);
				}
				SchemeDesignDao.ExecSDOrder(request,sdinfo,sdentry,productdefinfo);
			}
		
			String sql = "update t_ord_schemedesign set fconfirmed=1,fconfirmer ='"+userid+"',fconfirmtime = now() where fid in("+fids+")";//更改方案设计为以确定
			this.ExecBySql(sql);
		} catch (DJException e) {
			// TODO: handle exception
		}
		
		}
	}
	@Override
	public void Delproduct(List<HashMap<String,Object>> productlist,String fids){
		String sql = "select fid from t_pdt_productdef where schemedesignid="+fids;
		if(productlist==null){
			sql = "update t_ord_schemedesign set fconfirmed=0,fconfirmer =null,fconfirmtime = null where fid ="+fids;//更改方案设计为以确定
			this.ExecBySql(sql);
			return;
		}
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		String productids = null;
		if(list.size()>0){
			String fid = "'";
			for(int i =0;i<list.size();i++){
				fid += list.get(i).get("fid").toString();
				if(i<list.size()-1){
					fid +="','";
				}
			}
			fid +="'"; 
			productids =fid;
			sql = "select group_concat(quote(fproductid)) products from t_pdt_productdefproducts where fparentid in ("+fid+")";
			list = this.QueryBySql(sql);
			if(list.get(0).get("products")!=null){
				productids +=","+list.get(0).get("products"); //方案关联的所有产品id
			}
////			sql = "SELECT GROUP_CONCAT(QUOTE(r.fcustproductid)) cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid where e.fproductid in ("+productids+")";
//			sql = "  SELECT GROUP_CONCAT(QUOTE(pt.fcustproductid)) cusproductids  FROM t_pdt_Productrelation p LEFT JOIN t_pdt_Productrelationentry pt ON p.fid=pt.fparentid "+
//					"WHERE p.fproductid in("+productids+")";//产品关联关系
//			list = this.QueryBySql(sql);
//			if(list.get(0).get("cusproductids")==null){
//				sql = "SELECT GROUP_CONCAT(QUOTE(r.fcustproductid)) cusproductids FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid WHERE e.fproductid in("+productids+")";
//				list = this.QueryBySql(sql);
//			}
//			String cusproductids = list.get(0).get("cusproductids").toString();
			String cusproductids = SchemeDesignDao.productToCustproduct(productids);
			sql = "delete from t_ord_productdemandfile where fparentid in("+cusproductids+")";
			this.ExecBySql(sql);//删除客户产品关联附件
			sql = "delete from t_ord_productdemandfile where fparentid in("+productids+")";
			this.ExecBySql(sql);//删除产品关联附件
			sql = "delete from t_bd_custproduct where fid in ("+cusproductids+")";
			this.ExecBySql(sql);
			sql = "delete from t_bd_custproductproducts where fparentid in ("+cusproductids+")";
			this.ExecBySql(sql);//删除客户产品关联
			
			//删除客户产品和产品关联
//			sql = "delete from t_pdt_productrelationentry where fcustproductid in ("+cusproductids+")";
//			this.ExecBySql(sql);
//			
//			sql = "delete from t_pdt_productrelation where fproductid in("+productids+")";
//			this.ExecBySql(sql);
			sql = "delete from t_pdt_Productrelation where FPRODUCTID in ("+productids+")";
			this.ExecBySql(sql);
			
			sql = "delete from t_pdt_Productrelationentry where FCUSTPRODUCTID in("+cusproductids+")";
			this.ExecBySql(sql);
			//删除产品和子产品的关联
			sql = "delete from t_pdt_productdefproducts where fparentid in ("+fid+")";
			this.ExecBySql(sql);
			//删除产品和子产品
			sql = "delete from t_pdt_productdef where fid in ("+productids+")";
			this.ExecBySql(sql);
			sql = "SELECT fid,fparentorderid FROM `t_ord_productplan` WHERE fschemedesignid ="+fids;
			list = this.QueryBySql(sql);
			if(list.size()>0){
				sql = "delete from t_ord_saleorder where fid ='"+list.get(0).get("fparentorderid")+"'";
				this.ExecBySql(sql);//删除生产订单信息
				sql = "delete from t_inv_storebalance where fproductplanid ='"+list.get(0).get("fid")+"'";
				this.ExecBySql(sql);//删除库存记录
				sql = "delete from t_ord_productplan where fschemedesignid ="+fids;
				this.ExecBySql(sql);//删除制造商订单
			}
			
		}
			sql = "update t_ord_schemedesign set fconfirmed=0,fconfirmer =null,fconfirmtime = null where fid ="+fids;//更改方案设计为以确定
			this.ExecBySql(sql);
		
	}
}

