package com.service.deliverapply;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.IBaseDao;
import com.dao.deliverapply.DeliverapplyDao;
import com.dao.mystock.MystockDao;
import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.mystock.Mystock;
import com.model.produceplan.ProducePlan;
import com.model.productdef.MaterialLimit;
import com.model.productdef.Productdef;
import com.service.IBaseManagerImpl;
import com.util.Params;
import com.util.StringUitl;
@Service("deliverapplyManager")
@SuppressWarnings("unchecked")
@Transactional(rollbackFor = Exception.class)
public class DeliverapplyManagerImpl extends IBaseManagerImpl<Deliverapply, java.lang.Integer> implements DeliverapplyManager {
	@Autowired
	private DeliverapplyDao deliverapplyDao;
	@Autowired
	private MystockDao mystockDao;

	@Override
	protected IBaseDao<Deliverapply, Integer> getEntityDao() {
		return this.deliverapplyDao;
	}
	
	@Override
	public PageModel<Deliverapply> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult,String...t_name) {
		return this.deliverapplyDao.findBySql(where, queryParams, orderby, pageNo, maxResult,t_name);
	}
	
	@Override
	public List<Deliverapply> ExecBySql( String where, Object[] queryParams,  Map<String, String> orderby) {
		return this.deliverapplyDao.ExecBySql(where, queryParams, orderby);
	}
	
	@Override
	public List<Deliverapply> getDeliverapplyInfo( String where, Object[] queryParams,Map<String, String> orderby,String...t_name) {
		return this.deliverapplyDao.getDeliverapplyInfo(where, queryParams, orderby,t_name);
	}
	
	@Override
	public void saveImpl(Deliverapply deliverapply,String currentUserId,String currentCustomerId) throws ParseException {
		 SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		 SimpleDateFormat sdf2 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		/*********insert*要货信息**************/
		if(deliverapply.getFamount()>0){
				deliverapply.setFid(deliverapplyDao.CreateUUid());
				deliverapply.setFcreatorid(currentUserId);
				deliverapply.setFcreatetime(new Date());
				deliverapply.setFcustomerid(currentCustomerId);
				if(("am").equals(deliverapply.getHours())){
					String date =sdf1.format(deliverapply.getFarrivetime());
					date =date +" 09:00:00";
					deliverapply.setFarrivetime(sdf2.parse(date));
				}else if(("pm").equals(deliverapply.getHours())){
					String date =sdf1.format(deliverapply.getFarrivetime());
					date =date +" 14:00:00";
					deliverapply.setFarrivetime(sdf2.parse(date));
				}
				this.deliverapplyDao.save(deliverapply);
		}
		/*********insert*备货信息****************/
		if(deliverapply.getFplanamount()>0){
			Mystock ms =new Mystock();
				ms.setFid(mystockDao.CreateUUid());
				ms.setFcreateid(currentUserId);
				ms.setFcreatetime(new Date());
				ms.setFcustomerid(currentCustomerId);
				ms.setFcustproductid(deliverapply.getFcusproductid());
				ms.setFplanamount(deliverapply.getFplanamount());
				ms.setFaveragefamount(deliverapply.getFaveragefamount());
				ms.setFfinishtime(deliverapply.getFfinishtime());
				ms.setFconsumetime(deliverapply.getFconsumetime());
				ms.setFremark(deliverapply.getFremark());
				ms.setFsupplierid(deliverapply.getFsupplierid());
			this.mystockDao.save(ms);
		}
		
		
	}

	@Override
	public void saveOrderOnlineByDeliverapply(Deliverapply apply,TBdCustproduct custproduct) {
		this.saveOrUpdate(apply);
		if(custproduct!=null)	this.saveOrUpdate(custproduct);
	}

	
	@Override
	public PageModel<Deliverapply> findBoardBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult,String...t_name) {
		return this.deliverapplyDao.findBoardBySql(where, queryParams, orderby, pageNo, maxResult,t_name);
	}

	@Override
	public List<Deliverapply> getDeliverapplyboards(String where,
			Object[] queryParams, Map<String, String> orderby,String...t_name) {
	return	deliverapplyDao.getDeliverapplyBoards(where, queryParams, orderby,t_name);
	}

	/**
	 * 删除纸板
	 */
	@Override
//	public void deleteBoardsImpl(String fidcls,Params param) {	
		public void deleteBoardsImpl(String fidcls) {	
		deliverapplyDao.ExecBySql("delete from t_ord_deliverapply where fboxtype=1 and (fstate=0 or fstate=7) and fid "+fidcls);
		if(deliverapplyDao.QueryExistsBySql("select 1 from t_ord_productplan where fdeliversboardid "+fidcls)){//我的业务生成的纸板订单删除时需要，更改我的业务状态
			deliverapplyDao.ExecBySql("update  t_ord_productplan set fcreateboard=0 where fdeliversboardid "+fidcls);
		}
//		deliverapplyDao.ExecBySql("delete from t_ord_deliverapply where fboxtype=1 and (fstate=0 or fstate=7) and fid "+fidcls,param);
//		if(deliverapplyDao.QueryExistsBySql("select 1 from t_ord_productplan where fdeliversboardid "+fidcls,param)){//我的业务生成的纸板订单删除时需要，更改我的业务状态
//			deliverapplyDao.ExecBySql("update  t_ord_productplan set fcreateboard=0 where fdeliversboardid "+fidcls,param);
//		}
	}
	@Override
	public void deleteboxImpl(String fidcls) throws Exception{
//		String hql = "from Deliverapply where fid in "+fidcls;
//		List<Deliverapply> list = deliverapplyDao.QueryByHql(hql);
		String hql = "from Deliverapply where fid = ? ";
		Object[] param=new Object[]{fidcls};
		List<Deliverapply> list = deliverapplyDao.QueryByHql(hql, param);
		String sql=null;
		String deliverorderid;
		for(Deliverapply obj : list){
			if(obj.getFiscreate()!=0){//有特性的已生成管理的要货申请
				if(!StringUitl.isNullOrEmpty(obj.getFtraitid())){
					sql = "select 1 from t_ord_deliverorder where fdeliversid=(select fid from t_ord_delivers where fapplayid=:faid) and faudited=1";
					Params params=new Params();
					params.put("faid", obj.getFid());
					if(this.QueryExistsBySql(sql,params)){
						throw new Exception("此唛稿产品的配送信息已审核，无法删除！");
					}
//					sql = "select group_concat(quote(fid)) fids from t_ord_deliverorder where fdeliversid=(select fid from t_ord_delivers where fapplayid='"+obj.getFid()+"')";
//					deliverorderid = "("+((HashMap<String, Object>)deliverapplyDao.QueryBySql(sql).get(0)).get("fids")+")";
					sql = "select group_concat(quote(fid)) fids from t_ord_deliverorder where fdeliversid=(select fid from t_ord_delivers where fapplayid=:faid)";
					deliverorderid = "("+((HashMap<String, Object>)deliverapplyDao.QueryBySql(sql,params).get(0)).get("fids")+")";
					sql = "delete from t_ord_delivers where fapplayid=:faid";
					this.ExecBySql(sql,params);
					sql = "delete from t_ord_deliverratio where fdeliverappid=:faid";
					this.ExecBySql(sql,params);					
//					sql = "delete from t_ord_delivers where fapplayid='"+obj.getFid()+"'";
//					this.ExecBySql(sql);
//					sql = "delete from t_ord_deliverratio where fdeliverappid='"+obj.getFid()+"'";
//					this.ExecBySql(sql);
//					sql = "delete from t_inv_usedstorebalance where fdeliverorderid in "+deliverorderid;
//					this.ExecBySql(sql);
//					sql = "delete from t_ord_deliverorder where fid in "+deliverorderid;
//					this.ExecBySql(sql);
					sql = "delete from t_inv_usedstorebalance where fdeliverorderid in :deliverorderid";
					params=new Params();
					params.put("deliverorderid", deliverorderid);
					this.ExecBySql(sql,params );
					sql = "delete from t_ord_deliverorder where fid in :deliverorderid";
					this.ExecBySql(sql,params);
				
				}else if(!StringUitl.isNullOrEmpty(obj.getFmaterialfid())&&this.QueryExistsBySql(String.format("select fid from t_ord_productplan where fid = '%s' and ifnull(faffirmed,0)=0",obj.getFplanid()))){//快速生成订单的逻辑， 状态为未接收，
					 throw new Exception("制造商正在处理该订单;</br>如需删除,请联系制造商取消订单,再删除");
				}
				else{
					throw new Exception("订单已接收，无法删除！");
				}
			}
			if(!StringUitl.isNullOrEmpty(obj.getFtraitid())){//有特性的要货申请
				sql = "select 1 from t_ord_schemedesignentry where fallot=1 and fid='"+obj.getFtraitid()+"'";
				List<HashMap<String,Object>> list1 = deliverapplyDao.QueryBySql(sql);
				if(list1.size()>0){
					sql = "select 1 from mystock where fcustproductid ='"+obj.getFcusproductid()+"'";
					list1 = deliverapplyDao.QueryBySql(sql);
					if(list1.size()>0){
						throw new Exception("该特性产品在备货中已存在，无法删除！");
					}
					sql = "select 1 from t_ord_Deliverapply where (ftraitid = '' OR ftraitid=NULL) and fcusproductid='"+obj.getFcusproductid()+"' and fid not in"+fidcls;
					list1 = deliverapplyDao.QueryBySql(sql);
					if(list1.size()>0){
						throw new Exception("该特性产品在要货申请中已存在，无法删除！");
					}
				}
				sql = "update t_ord_schemedesignentry set fallot=0,frealamount=frealamount-"+obj.getFamount()+" where fid='"+obj.getFtraitid()+"'";
				this.ExecBySql(sql);
			}
			sql="delete from t_ord_deliverapply where fid ='"+obj.getFid()+"'";
			this.ExecBySql(sql);
		}
		
	}
	@Override
	public void ExecUpdateBoardStateToCreate(String fidcls) throws Exception{
		 deliverapplyDao.ExecUpdateBoardStateToCreate(fidcls);
	}

	@Override
	public void saveBoardSignleDeliverapply(Deliverapply deliverapply) throws Exception{
		deliverapplyDao.saveBoardSignleDeliverapply(deliverapply);
		deliverapplyDao.saveCustBoardFormula(deliverapply);
		deliverapplyDao.saveCustBoardLabel(deliverapply);
	}

	@Override
	public String getfirstDateofMaterial(String fmaterialfid,String fsupplierid ) throws Exception {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Productdef pd=(Productdef)deliverapplyDao.Query(Productdef.class, fmaterialfid);
		if(pd==null) throw new Exception("材料有误");
		List<ProducePlan> plans=deliverapplyDao.QueryByHql(" from ProducePlan p where p.fproductid='"+ fmaterialfid+"' and p.fsupplierid='"+fsupplierid+"'");
		ProducePlan pinfo=null;
		if(plans!=null&&plans.size()>0) pinfo=plans.get(0);
		Calendar cday=deliverapplyDao.getFirstArrivetimeDate(pinfo,pd);//计算排产期 白班就取最近排产期
		if(new Integer(pd.getFnewtype())==2)//裱胶类型 默认+2
		{
			cday.add(Calendar.DAY_OF_MONTH, 2);
		}else if(pd.getFeffected()==1){//夜班 
			cday.add(Calendar.DAY_OF_MONTH, 1);//普默认+1
		}
		//通过修改属性文件  临时设置不排产日
		ArrayList nodaylist = new ArrayList();
		Properties prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/boardsth.properties");
		//加载属性列表
		prop.load(in);
		Iterator<String> it=prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if(key.equals("notproductDate")){
				nodaylist = new ArrayList<String>(Arrays.asList(prop.getProperty(key).split(",")));
			}			
		}
		in.close();
		while(nodaylist.contains(f.format(cday.getTime())))//交期在不排产日，交期延后+1
		{
			cday.add(Calendar.DAY_OF_MONTH, 1);
		}
		return f.format(cday.getTime());		
	}

	@Override
	public int deleteCustomerlabel(String customer, String fname)
	 {
		Params p=new Params();
		p.put("fcid", customer);
		p.put("fname", fname);
		return 	deliverapplyDao.ExecBySql("delete from t_ord_custboardlabel where fcustomerid=:fcid and fname=:fname ",p);
//		return 	deliverapplyDao.ExecBySql(String.format("delete from t_ord_custboardlabel where fcustomerid='%s' and fname='%s' ",customer,fname));
	}

	@Override
	public int updateCustomerDescription(String customerid, String description) {
		// TODO Auto-generated method stub
		Params p=new Params();
		p.put("fdes", description);
		p.put("fid", customerid);
 		return 	deliverapplyDao.ExecBySql("update t_bd_customer set fdescription = :fdes where fid =:fid ",p);
// 		return 	deliverapplyDao.ExecBySql(String.format("update t_bd_customer set fdescription = '%s' where fid ='%s' ",description,customerid));

	}
	
	
	//下料规格最值设置
	@Override
	public void saveMaterialLimit(MaterialLimit m) {
		// TODO Auto-generated method stub
		Params param=new Params();		
		String sql = "delete from t_sys_material_limit where fcustomerid = :fcid";
		param.put("fcid", m.getFcustomerid());
//		String sql = "delete from t_sys_material_limit where fcustomerid = '"+m.getFcustomerid()+"'";
		this.ExecBySql(sql,param);
		m.setFid(this.CreateUUid());
		deliverapplyDao.saveOrUpdate(m);
	}

	@Override
	public HashMap<String, Object> getDeliverapplyById(String id,String...t_name) {
		try
		{
			String tablename="t_ord_deliverapply";
			if(t_name.length!=0&t_name[0]!=null)
			{
				tablename=t_name[0];
			}
//			String sql="select * from "+tablename+" where fid=:fid";
			String sql="select fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,fEASdeliverid,fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fiscreate,fcusfid,fwerkname,fstate,ftype,ftraitid,fcharacter,foutQty,fsupplierid,fmaterialfid,fboxmodel,fboxlength,fboxwidth,fboxheight,fmateriallength,fmaterialwidth,fhformula,fvformula,fstavetype,famountpiece,fboxtype,fseries,frecipient,freceiptTime,fhline,fvline,fhformula1,fdefine1,fdefine2,fdefine3,fvstaveexp,fhstaveexp,fiscommonorder,fcommonBoardOrder,fordesource,flabel from "+tablename+" where fid=:fid";
			Params param=new Params();
			param.put("fid", id);
			List<HashMap<String, Object>> list=this.deliverapplyDao.QueryBySql(sql, param);
			if(list!=null)
			{
				return list.get(0);
			}
		}
		catch(Exception e)
		{
			System.out.print(e.toString());
		}
		return null;
	}
	

	
}
