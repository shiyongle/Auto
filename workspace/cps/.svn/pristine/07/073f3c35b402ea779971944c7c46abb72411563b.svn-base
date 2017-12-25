/*
 * CPS-VMI-wangc
 */

package com.service.firstproductdemand;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.productdef.Custproductproducts;
import com.model.productdef.ProductdefProducts;
import com.model.productdef.Productrelation;
import com.model.productdef.Productrelationentry;
import com.dao.IBaseDao;
import com.dao.deliverapply.DeliverapplyDao;
import com.dao.firstproductdemand.TordFirstproductdemandDao;
import com.dao.productdemandfile.ProductdemandfileDao;
import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.productdef.Productdef;
import com.model.productdemandfile.Productdemandfile;
import com.service.IBaseManagerImpl;
import com.util.Constant;
import com.util.Params;
import com.util.StringUitl;

@Service("tordFirstproductdemandManager")
@Transactional(rollbackFor = Exception.class)
public class TordFirstproductdemandManagerImpl extends IBaseManagerImpl<Firstproductdemand,java.lang.String> implements TordFirstproductdemandManager{
	@Autowired
	private TordFirstproductdemandDao tordFirstproductdemandDao;
	@Autowired
	private ProductdemandfileDao productdemandfileDao;
	@Autowired
	private DeliverapplyDao deliverapplyDao;
	public IBaseDao<Firstproductdemand, java.lang.String> getEntityDao() {
		return this.tordFirstproductdemandDao;
	}

	@Override
	public PageModel<Firstproductdemand> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult,String...t_name) {
		return this.tordFirstproductdemandDao.findBySql(where, queryParams, orderby, pageNo, maxResult,t_name);
	}

	public List<Firstproductdemand> execlBySql(String where, Object[] queryParams,Map<String, String> orderby,String...t_name){
		return this.tordFirstproductdemandDao.execlBySql(where, queryParams, orderby,t_name);
	}

	@Override
	public void deleteImpl(String[] ids) {
		for(String fid:ids){
			this.tordFirstproductdemandDao.delete("t_ord_firstproductdemand", fid);
//			String sql = "select * from t_ord_productdemandfile where fparentid in ('"+fid+"' )";
			Params params=new Params();
			String sql = "select * from t_ord_productdemandfile where fparentid in (:fid)";
			params.put("fid", fid);
			List<HashMap<String,Object>> list = this.productdemandfileDao.QueryBySql(sql,params);
//			List<HashMap<String,Object>> list = this.productdemandfileDao.QueryBySql(sql);
			if(list.size()>0){
				for(HashMap<String, Object> map : list){
					File file = new File(map.get("fpath").toString());
					if(file!=null&&!"".equals(file)){
						file.delete();
					}
					sql = "delete from  t_ord_productdemandfile where fparentid  in (:fid )";
					this.productdemandfileDao.ExecBySql(sql,params);
				}
			}
		}
	}

	/**
	 * 方案取消确认
	 * 制造商未上cps-vmi项目，暂不使用;
	 */
	@Override
	public void UnAffirmSchemedesign(String fids) throws Exception {
			String sql = "select ffirstproductid from t_ord_schemedesign where fid in("+fids+")";
			List<HashMap<String,Object>> l = tordFirstproductdemandDao.QueryBySql(sql);
			for(HashMap<String, Object> productid : l){
				sql = "select 1 from t_ord_firstproductdemand where fid ='" + productid.get("ffirstproductid").toString()
						+ "' and fstate ='关闭'";
				List list = tordFirstproductdemandDao.QueryBySql(sql);
				if (list.size() > 0) {
					throw new Exception("你选择的方案对应的需求已关闭,不能操作！");
				} 
			}
			String[] fid = fids.split(",");
			for(int i =0;i<fid.length;i++){
				
				sql = "select * from t_ord_productstructure where schemedesignid = "+fid[i];
				List<HashMap<String,Object>> list = tordFirstproductdemandDao.QueryBySql(sql);//查产品信息
				if(list.size()>0){
					sql = "SELECT  * FROM t_ord_productstructure  where 1=1 AND fparentid='"+list.get(0).get("fid")+"'";
					List<HashMap<String,Object>> subProducts = tordFirstproductdemandDao.QueryBySql(sql);
					list.addAll(subProducts);
				}
				sql = "select * from t_ord_schemedesignentry where fparentid = "+fid[i];
				List<HashMap<String,Object>> list1 = tordFirstproductdemandDao.QueryBySql(sql);//查特性信息
				ExecUnaffirm(fid[i],list,list1);//判断制造商订单是否审核 是否生成要货申请
				if(list.size()>0){
					Delproduct(list,fid[i]);//删除产品结构数据
				}else if(list1.size()>0){			
					Delproduct(list1,fid[i]);//删除特性所有数据
					//要货申请id list
					List daidsList = new ArrayList();
					for(int j=0;j<list1.size();j++){
						ArrayList<Deliverapply> applylist=(ArrayList<Deliverapply>)deliverapplyDao.findByHql(" from Deliverapply where ftraitid = '"+list1.get(j).get("fid")+"' ");
					//	Deliverapply dainfo = ((ArrayList<Deliverapply>)deliverapplyDao.findByHql(" from Deliverapply where ftraitid = '"+list1.get(i).get("fid")+"' ")).get(0);
						for(int z=0;z<applylist.size();z++){
							if( applylist.get(z).getFiscreate()!=0){
								throw new Exception("特性方案已经生成要货管理,不能取消确认！");
							}
						daidsList.add(applylist.get(z).getFid());
					}
					}
					//有特性方案确认后会生成要货，取消确认需要删除要货
					String daids = StringUitl.buildIdStringFromList(daidsList);
					if(!"".equals(daids)){
						String delSql = " delete from t_ord_deliverapply where fid in "+daids+"";
						this.ExecBySql(delSql);
					}
				}else 
				{
					Delproduct(null,fid[i]);//没有特性，没有产品的方案 只反审核确认状态
				}
			}
			//2016-01-25   取消确认，如果需求对应的所有方案 都是未确认状态，修改需求的fstate 为已设计
			for(HashMap<String, Object> productid : l){
				sql = "SELECT 1 FROM t_ord_schemedesign WHERE fconfirmed = 1 AND ffirstproductid = '"+productid.get("ffirstproductid").toString()+"'";
				if(!tordFirstproductdemandDao.QueryExistsBySql(sql)){
					sql = "update t_ord_firstproductdemand set fstate = '已设计' where fid = '"+productid.get("ffirstproductid").toString()+"' ";
					this.ExecBySql(sql);
				}
			}
	}
	
	private void ExecUnaffirm(String fid,List<HashMap<String,Object>> productlist,List<HashMap<String,Object>> entrylist) throws Exception{
		String sql = "select * from t_ord_schemedesign where fid ="+fid;
		List<HashMap<String,Object>> schemedesignlist = tordFirstproductdemandDao.QueryBySql(sql);
		List daidsList = new ArrayList();
		if(productlist.size()>0){
			
			sql = "select fid from t_pdt_productdef where schemedesignid in ("+fid+")";
			List<HashMap<String,Object>> list = tordFirstproductdemandDao.QueryBySql(sql);//查产品信息
			if(list.size()>0){
				sql = "SELECT  fproductid as fid FROM t_pdt_productdefproducts  where fparentid in('"+list.get(0).get("fid")+"')";
				List<HashMap<String,Object>> subProducts = tordFirstproductdemandDao.QueryBySql(sql);
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
				list = tordFirstproductdemandDao.QueryBySql(sql);
				if(list.size()>0){
					throw new Exception(schemedesignlist.get(0).get("fnumber")+"已生成生产订单,不能取消确认");
				}
			}
		}
		if(entrylist.size()>0){
			sql = "SELECT fnumber FROM `t_ord_productplan` WHERE faffirmed=1 and fschemedesignid in ("+fid+")";
			List<HashMap<String,Object>> list = tordFirstproductdemandDao.QueryBySql(sql);
			if(list.size()>0){
				throw new Exception(schemedesignlist.get(0).get("fnumber")+"的制造商订单已确认,不能取消确认");
			}
			
			sql = "select fid from t_pdt_productdef where schemedesignid in ("+fid+")";
			list = tordFirstproductdemandDao.QueryBySql(sql);
			if(list.size()>0){
				String productid = "'";
				for(int j =0;j<list.size();j++){
					productid += list.get(j).get("fid").toString();
					if(j<list.size()-1){
						productid +="','";
					}
				}
			}
		}
	}
	
	/**
	*删除产品结构数据
	*删除特性所有数据
	*没有特性，没有产品的方案 只反审核确认状态
	 */
	private void Delproduct(List<HashMap<String,Object>> productlist,String fids){
		String sql = "select fid from t_pdt_productdef where schemedesignid="+fids;
		if(productlist==null){
			sql = "update t_ord_schemedesign set fconfirmed=0,fconfirmer =null,fconfirmtime = null where fid ="+fids;//更改方案设计为以确定
			this.ExecBySql(sql);
			return;
		}
		List<HashMap<String, Object>> list = tordFirstproductdemandDao.QueryBySql(sql);
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
			list = tordFirstproductdemandDao.QueryBySql(sql);
			if(list.get(0).get("products")!=null){
				productids +=","+list.get(0).get("products"); //方案关联的所有产品id
			}
			String cusproductids = productToCustproduct(productids);
			sql = "delete from t_ord_productdemandfile where fparentid='"+cusproductids+"'";
			this.ExecBySql(sql);//删除客户产品关联附件
			sql = "delete from t_ord_productdemandfile where fparentid in("+productids+")";
			this.ExecBySql(sql);//删除产品关联附件
			sql = "delete from t_bd_custproduct where fid ='"+cusproductids+"'";
			this.ExecBySql(sql);
			sql = "delete from t_bd_custproductproducts where fparentid ='"+cusproductids+"'";
			this.ExecBySql(sql);//删除客户产品关联
			
			sql = "delete from t_pdt_Productrelation where FPRODUCTID in ("+productids+")";
			this.ExecBySql(sql);
			
			sql = "delete from t_pdt_Productrelationentry where FCUSTPRODUCTID ='"+cusproductids+"'";
			this.ExecBySql(sql);
			//删除产品和子产品的关联
			sql = "delete from t_pdt_productdefproducts where fparentid in ("+fid+")";
			this.ExecBySql(sql);
			//删除产品和子产品
			sql = "delete from t_pdt_productdef where fid in ("+productids+")";
			this.ExecBySql(sql);
			sql = "SELECT fid,fparentorderid FROM `t_ord_productplan` WHERE fschemedesignid ="+fids;
			list = tordFirstproductdemandDao.QueryBySql(sql);
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

	private String productToCustproduct(String productid) {
		// TODO Auto-generated method stub
		String cpid = "";
		List<HashMap<String,Object>> list = tordFirstproductdemandDao.QueryBySql("select fid from t_bd_custproduct where fproductid = '"+productid.replace("'","")+"'");
		if(list.size()>0){
			cpid = list.get(0).get("fid").toString();
		}
		return cpid;
	}

	@Override
	public void AffirmSchemedesign(String ffirstproductid,String fids,String userid) {
//		productdemandfileDao.ExecBySql("update t_ord_firstproductdemand set fstate='确认方案' where fid ='"+ffirstproductid+"' and fstate='已设计'");
//		productdemandfileDao.ExecBySql("update t_ord_schemedesign set fconfirmed =1,fconfirmer='"+userid+"',fconfirmtime=now() where fid in ("+fids+")");
		Params param=new Params();
		param.put("fpid", ffirstproductid);
		String fpdSql="update t_ord_firstproductdemand set fstate='确认方案' where fid =:fpid and fstate='已设计'";
		productdemandfileDao.ExecBySql(fpdSql, param);
		param.getData().clear();
		String sdSql="update t_ord_schemedesign set fconfirmed =1,fconfirmer=:fcid,fconfirmtime=now() where fid in (:fid)";
		param.put("fcid", userid);
		param.put("fid", fids.split(","));
		productdemandfileDao.ExecBySql(sdSql, param);
	}

	@Override
	public void closeFp(String fids) {
		// TODO Auto-generated method stub
		productdemandfileDao.ExecBySql("update t_ord_firstproductdemand set fstate =? where fid in ("+fids+")");
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String finishFp(String[] param) {
		synchronized (this.getClass()){
		String sql = null;
		String fid = param[0];
		String userid = param[1];
		Params params=null;
		try {
//			sql = "select * from t_ord_productstructure where schemedesignid = '"+fid+"' ";
			sql = "select * from t_ord_productstructure where schemedesignid = :fid ";
			params=new Params();
			params.put("fid", fid);
//			List<HashMap<String,Object>> productlist = productdemandfileDao.QueryBySql(sql);//查产品信息
			List<HashMap<String,Object>> productlist = productdemandfileDao.QueryBySql(sql,params);//查产品信息
			if(productlist.size()==0){
				return "需求未建产品不能完成需求！";
			}
			
		    if(productlist.size()!=0){
//		     sql = "SELECT  * FROM t_ord_productstructure  where fparentid='"+productlist.get(0).get("fid")+"'";
		     sql = "SELECT  * FROM t_ord_productstructure  where fparentid=:fpid";
		     params=new Params();
		     params.put("fpid", productlist.get(0).get("fid"));
		     List<HashMap<String,Object>> subProducts = productdemandfileDao.QueryBySql(sql,params);
		     productlist.addAll(subProducts);
		    }
		    
			sql = "select 1 from t_ord_firstproductdemand where fstate in ('已完成','关闭') and fid = :fid";
			params=new Params();
			params.put("fid", fid);
			List list = tordFirstproductdemandDao.QueryBySql(sql,params);
			if(list.size()>0){
				return "你选择的需求已完成或已关闭,不能操作！";
			}
			
			if(productlist.size()>0){
				//生成成产品和客户产品,产品关联表等  并生成生成订单,制造商订单,库存信息
				return ExecaffirmSchemeDesign(fid,userid,productlist);
			}

//			response.getWriter().write(JsonUtil.result(true,"确认成功!", ischaracter, ""));
		} catch (Exception e) {
			return "操作失败:"+e.getMessage();
		}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	private String ExecaffirmSchemeDesign(String fpid,String userid,
			List<HashMap<String, Object>> productlist) {
		synchronized (this.getClass()){
		ProductdefProducts pp = null;
		Custproductproducts cpp = null;
		Productrelation pr;
		Productrelationentry pre;
		Productdef p = null;
		Productdemandfile pdf = null;
		TBdCustproduct cp = null;
		String prid,//Productrelation主键
		cpid;//Productrelationentry主键
		String fid = this.CreateUUid();//套装ID
		String custfid = null;
		String fsupplierid = "";
		List<HashMap<String,Object>> filelist = null ;
		String productdefproducts= "";
		String custproductproducts= "";
		try {
				
//				无特性的增加产品和客户产品以及它们的关联
				String sql = "";
				for(int i =0;i<productlist.size();i++){
					p = new Productdef();
					p.setFnewtype("3");
					p.setSchemedesignid(fpid);
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
					p.setFnumber((String)productlist.get(i).get("fnumber"));
					p.setFcustomerid(productlist.get(i).get("fcustomerid").toString());
					p.setFname(productlist.get(i).get("fname").toString());
					p.setFversion((String)productlist.get(i).get("fversion"));
					p.setFboxlength(productlist.get(i).get("fboxlength")==null?null:new BigDecimal(productlist.get(i).get("fboxlength").toString()));
					p.setFboxheight(productlist.get(i).get("fboxheight")==null?null:new BigDecimal(productlist.get(i).get("fboxheight").toString()));
					p.setFboxwidth(productlist.get(i).get("fboxwidth")==null?null:new BigDecimal(productlist.get(i).get("fboxwidth").toString()));
					p.setFmaterialcodeid(productlist.get(i).get("fmaterialcodeid").toString());
					p.setFcreatetime(new Date());
					p.setFlastupdatetime(new Date());
					p.setFlastupdateuserid(userid);
					p.setFcreatorid(userid);
					p.setSubProductAmount(new Integer(productlist.get(i).get("subProductAmount").toString()));
					p.setFissynctoson(0);//FREJECT FISSEVENLAYER FISCOMBINECROSS FCHROMATICPRECISION
					p.setFreject(0);
					p.setFeffect(1);
					p.setFissevenlayer(0);
					p.setFiscombinecross(0);
					p.setFchromaticprecision(new BigDecimal(5.0000000000));
					if(productlist.get(i).get("fsupplierid")!=null){
						fsupplierid = productlist.get(i).get("fsupplierid").toString();
					}
					p.setFsupplierid(fsupplierid);
					this.saveOrUpdate(p);//新增产品信息
					
//					sql = "select * from t_ord_productdemandfile where fparentid = '" + productlist.get(i).get("fid").toString()+"' ";
					sql = "select * from t_ord_productdemandfile where fparentid =:fpid ";
					Params params=new Params();
					params.put("fpid",productlist.get(i).get("fid").toString() );
					filelist = tordFirstproductdemandDao.QueryBySql(sql,params);
					for(int j = 0;j<filelist.size();j++){
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(p.getFid());
						this.saveOrUpdate(pdf);
					}
					
					cp = new TBdCustproduct();
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
					cp.setFcreatorid(userid);
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
					
					for(int j = 0;j<filelist.size();j++){
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(cp.getFid());
						this.saveOrUpdate(pdf);
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
					
					if(i>0){
						//增加产品和子产品关联
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
			String sqlUpd="update t_ord_firstproductdemand set fstate='已完成' where fid = :fid";
			Params params=new Params();
			params.put("fid", fpid);
//			this.ExecBySql("update t_ord_firstproductdemand set fstate='已完成' where fid = '"+fpid+"'");//更改需求状态为已完成;
			this.ExecBySql(sqlUpd,params);//更改需求状态为已完成;
		} catch (Exception e) {
			return e.getMessage()==null?"null":e.getMessage();
		}
		
		}
		return null;
	}

	@Override
	public List<Productdemandfile> getFilesbyParentid(String pid){
		String hql = " from Productdemandfile where fparentid=? ";
		Object[] queryParams=new Object[]{pid};
		List<Productdemandfile> listfile = productdemandfileDao.findByHql(hql, queryParams);
//		String hql = " from Productdemandfile where fparentid='"+pid+"' ";
//		List<Productdemandfile> listfile = productdemandfileDao.findByHql(hql);
		return listfile;
	}

	@Override
	public void affirmPay(String id) {
//		productdemandfileDao.ExecBySql("update t_ord_firstproductdemand set fstate='已完成' where fid ='"+id+"' and fstate='确认方案'");
		Params params=new Params();
		String sql="update t_ord_firstproductdemand set fstate='已完成' where fid =:id and fstate='确认方案'";
		params.put("id", id);
		productdemandfileDao.ExecBySql(sql,params);
	}
}
