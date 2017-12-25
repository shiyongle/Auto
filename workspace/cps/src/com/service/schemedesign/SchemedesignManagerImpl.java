/*
 * CPS-VMI-wangc
 */

package com.service.schemedesign;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.IBaseDao;
import com.dao.firstproductdemand.TordFirstproductdemandDao;
import com.dao.schemedesign.SchemedesignDao;
import com.model.PageModel;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.schemedesign.Productstructure;
import com.model.schemedesign.SchemeDesignEntry;
import com.model.schemedesign.Schemedesign;
import com.service.IBaseManagerImpl;
import com.util.Params;
import com.util.StringUitl;

@SuppressWarnings("unchecked")
@Service("schemedesignManager")
@Transactional(rollbackFor = Exception.class)
public class SchemedesignManagerImpl extends IBaseManagerImpl<Schemedesign,java.lang.String> implements SchemedesignManager{
	@Autowired
	private SchemedesignDao schemedesignDao;
	@Autowired
	private TordFirstproductdemandDao tordFirstproductdemandManager;
	
	public IBaseDao<Schemedesign, java.lang.String> getEntityDao() {
		return this.schemedesignDao;
	}

	@Override
	public PageModel<Schemedesign> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult) {
		return this.schemedesignDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}

	/**
	 * 在线设计详情
	 * 方案列表查询;
	 */
	@Override
	public List<HashMap<String, HashMap<String, Object>>> getDetailWithfp(String id,String queryHistory) {
		//2016-3-23 lxx
		String t_name="t_ord_schemedesign";
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
			t_name+="_h";
		}
		String sql="select DATE_FORMAT(fconfirmtime,'%Y-%m-%d %H:%i') fconfirmtime ,fconfirmed Fconfirmed,fid schemdesignID,DATE_FORMAT(Fcreatetime,'%Y-%m-%d %H:%i') time from "+t_name+" where ffirstproductid = :fpid";
		Params param=new Params();
		param.put("fpid", id);
		List<HashMap<String, Object>> list=this.schemedesignDao.QueryBySql(sql, param);
		List<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
		List<HashMap<String, Object>> fileList;
		for (int i = 0; i < list.size(); i++) {
			HashMap hashMap=list.get(i);
			param.getData().clear();
			param.put("fpid", hashMap.get("schemdesignID"));
			sql = "select fid,fname,replace(SUBSTRING(fpath,LOCATE('/vmifile',fpath)),'vmifile','smallvmifile') fpath from t_ord_productdemandfile where fparentid=:fpid";
			fileList = this.schemedesignDao.QueryBySql(sql,param);
			hashMap.put("files", fileList);
			result.add(hashMap);
		}
		return result;
		
		
//		String where =" WHERE ( s.faudited = 1 OR ifnull(sc.fid, 1) = 1) AND s.fcustomerid =? AND s.ffirstproductid = ?";
//		String hql = "from Schemedesign where ffirstproductid = ?";
//		Object[] obj=new Object[]{id};
//		List<Schemedesign> list = this.schemedesignDao.findByHql(hql,obj);
//		List<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
//		List<HashMap<String, Object>> fileList;
//		HashMap map;
//		SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d H:m");
//		for(Schemedesign schemedesign:list){
//			map = new HashMap<>();
//			map.put("fconfirmtime", schemedesign.getFconfirmtime()!=null?sdf.format(schemedesign.getFconfirmtime()):"");
//			map.put("Fconfirmed", schemedesign.getFconfirmed());
//			map.put("schemdesignID", schemedesign.getFid());
//			map.put("time", sdf.format(schemedesign.getFcreatetime()));
//			hql = "select fid,fname,replace(SUBSTRING(fpath,LOCATE('/vmifile',fpath)),'vmifile','smallvmifile') fpath from t_ord_productdemandfile where fparentid=:fpid";
//			Params param=new Params();
//			param.put("fpid", schemedesign.getFid());
//			fileList = this.schemedesignDao.QueryBySql(hql,param);
//			map.put("files", fileList);
//			result.add(map);
//		}
//		return result;
	}

	/**
	 * 需求包详情
	 * 方案列表查询;
	 */
	@Override
	public List<HashMap<String, HashMap<String, Object>>> getDetailWithScheme(String id,String queryHistory) {

		//2016-3-24 lxx
		String t_name="t_ord_schemedesign";
		String t_name1="t_ord_schemedesignentry";
		if(queryHistory!=null&&"history".equals(queryHistory))
		{
			t_name+="_h";
			t_name1+="_h";
		}
		String sql="select fconfirmed Fconfirmed,fid schemdesignID,DATE_FORMAT(Fcreatetime,'%Y-%m-%d %H:%i') time from "+t_name+" where ffirstproductid = :fpid";
		Params param=new Params();
		param.put("fpid", id);
		List<HashMap<String, Object>> list=this.schemedesignDao.QueryBySql(sql, param);
		List<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
		List<HashMap<String, Object>> fileList;
		for (int i = 0; i < list.size(); i++) {
			HashMap hashMap=list.get(i);
			param.getData().clear();
			param.put("fpid", hashMap.get("schemdesignID"));
			sql = "select fid,fname,replace(SUBSTRING(fpath,LOCATE('/vmifile',fpath)),'vmifile','smallvmifile') fpath from t_ord_productdemandfile where fparentid=:fpid";
			fileList = this.schemedesignDao.QueryBySql(sql,param);
			hashMap.put("files", fileList);
			
			//方案产品查询;
			List<HashMap<String, HashMap<String, Object>>> productlist = getProductWithScheme(hashMap.get("schemdesignID").toString());
			//方案特性查询;
			if(productlist.size()<=0){
				sql = "select fid,fmaterial,fname,concat_ws('*',fboxlength,fboxwidth,fboxheight) fspec,DATE_FORMAT(foverdate,'%Y-%m-%d') foverdate,famount,ftemplateproduct,ftemplatenumber,ftilemodel from "+t_name+" where famount>0 and fid=:fpid";
				productlist = this.schemedesignDao.QueryBySql(sql, param);
				hashMap.put("schemeEntry", productlist);
				sql = "select fid,fdrawinfo,fentryamount,fcharacter,fparentid,frealamount,fallot,fpurchasenumber from "+t_name1+" where fparentid=:fpid";
				param.getData().clear();
				param.put("fpid", hashMap.get("schemdesignID"));
				List schemedesignentry = this.schemedesignDao.QueryBySql(sql,param);
				hashMap.put("schemedesignentry", schemedesignentry);
			}else{
				hashMap.put("productlist", productlist);
			}
			
			result.add(hashMap);
		}
		return result;
		
//		String where =" WHERE ( s.faudited = 1 OR ifnull(sc.fid, 1) = 1) AND s.fcustomerid =? AND s.ffirstproductid = ?";
//			String hql = "from Schemedesign where ffirstproductid = '"+id+"'";
//			List<Schemedesign> list = this.schemedesignDao.findByHql(hql);
//			List<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
//			List<HashMap<String, Object>> fileList;
//			HashMap map;
//			SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d H:m");
//			for(Schemedesign schemedesign:list){
//				map = new HashMap<>();
//				map.put("Fconfirmed", schemedesign.getFconfirmed());
//				map.put("schemdesignID", schemedesign.getFid());
//				map.put("time", sdf.format(schemedesign.getFcreatetime()));
//				hql = "select fid,fname,replace(SUBSTRING(fpath,LOCATE('/vmifile',fpath)),'vmifile','smallvmifile') fpath from t_ord_productdemandfile where fparentid='"+schemedesign.getFid()+"'";
//				fileList = this.schemedesignDao.QueryBySql(hql);
//				map.put("files", fileList);
//				
//				//方案产品查询;
//				List<HashMap<String, HashMap<String, Object>>> productlist = getProductWithScheme(schemedesign.getFid());
//				
//				//方案特性查询;
//				if(productlist.size()<=0){
//					hql = "select fid,fmaterial,fname,concat_ws('*',fboxlength,fboxwidth,fboxheight) fspec,DATE_FORMAT(foverdate,'%Y-%m-%d') foverdate,famount,ftemplateproduct,ftemplatenumber,ftilemodel from t_ord_schemedesign where famount>0 and fid='"+schemedesign.getFid()+"'";
//					productlist = this.schemedesignDao.QueryBySql(hql);
//					map.put("schemeEntry", productlist);
//					hql = "select fid,fdrawinfo,fentryamount,fcharacter,fparentid,frealamount,fallot,fpurchasenumber from t_ord_schemedesignentry where fparentid='"+schemedesign.getFid()+"'";
//					List schemedesignentry = this.schemedesignDao.QueryBySql(hql);
//					map.put("schemedesignentry", schemedesignentry);
//				}else{
//					map.put("productlist", productlist);
//				}
//				
//				result.add(map);
//			}
//			return result;
		}
	
	/*** 根据方案ID查询所有客户产品列表*/
	private List<HashMap<String, HashMap<String, Object>>> getProductWithScheme( String id) {
		List<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
		List<HashMap<String, Object>> productList;
		List<HashMap<String, Object>> fileList;
		
		HashMap map;
		String cpid = "";
		String sql = "select  fid,fname,fmaterialcodeid,concat_ws('*',fboxlength,fboxwidth,fboxheight) fspec,subProductAmount  from t_ord_productstructure where schemedesignid = '"+id+"'";
		productList = this.schemedesignDao.QueryBySql(sql);
			if(productList.size()>0){
				map = new HashMap<>();
				if(productList.get(0).get("fid")!=null && !productList.get(0).get("fid").equals("")){
					cpid = productList.get(0).get("fid").toString();
				}
				map.put("productfname",productList.get(0).get("fname"));
				map.put("fmaterialcode",productList.get(0).get("fmaterialcodeid"));
				map.put("fspec",productList.get(0).get("fspec"));
				map.put("subProductAmount",productList.get(0).get("subProductAmount"));
				result.add(map);
			}
			if(!cpid.equals("")){
				sql = "select  fid,fname,fmaterialcodeid,concat_ws('*',fboxlength,fboxwidth,fboxheight) fspec,subProductAmount  from t_ord_productstructure where fparentid = '"+cpid+"'";
				productList = this.schemedesignDao.QueryBySql(sql);
					for(int i=0;i<productList.size();i++){
						map = new HashMap<>();
						if(productList.get(i).get("fid")!=null && !productList.get(i).get("fid").equals("")){
							cpid = productList.get(i).get("fid").toString();
						}
						map.put("productfname",productList.get(i).get("fname"));
						map.put("fmaterialcode",productList.get(i).get("fmaterialcodeid"));
						map.put("fspec",productList.get(i).get("fspec"));
						map.put("subProductAmount",productList.get(i).get("subProductAmount"));
						result.add(map);
					}
			}

		return result;
	}
	
	/*********************************************************我的设计********************************************/

	@Override
	public int updateDesigner(String fids,String designer) {
		return this.schemedesignDao.updateDesigner(fids, designer);
	}

	@Override
	public List getDesinerList(String fsupplierid) {
		// TODO Auto-generated method stub
		String sql ="select u.fid,fname,femail,ftel,fqq from t_sys_user u inner join t_bd_usersupplier us on us.fuserid=u.fid where ftype = 3 and us.fsupplierid=:fsupplierid ";
		Params p=new Params();
		p.put("fsupplierid", fsupplierid);
		return this.schemedesignDao.QueryBySql(sql, p);
	}

	@Override
	public PageModel<HashMap<String, Object>> findByFirstdemand(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult,String...t_name) {
		// TODO Auto-generated method stub
		return this.schemedesignDao.findByFirstdemand(where, queryParams, orderby, pageNo, maxResult,t_name);
	}

	@Override
	public PageModel<HashMap<String, Object>> findBySchemedesign(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult,boolean...t_name) {
		// TODO Auto-generated method stub
		return this.schemedesignDao.findBySchemedesign(where, queryParams, orderby, pageNo, maxResult,t_name);
	}
	
	@Override
	public void execUnReceiveProductdemand(String fids) throws Exception {

			String sql="select fid from t_ord_schemedesign where ffirstproductid in ('%s')";
			List<HashMap<String, Object>> slist=this.schemedesignDao.QueryBySql(String.format(sql, fids));
			for(int i=0;i<slist.size();i++)
			{
				delSchemeDesign(slist.get(i).get("fid").toString());
			}

		sql = "update t_ord_firstproductdemand set fstate='已分配',freceived=0,freceivetime= null where (fstate='已接收' or fstate='已设计') and fid in ('%s')";
		this.ExecBySql(String.format(sql,fids));
	}
	
	@Override
	public void execReceiveProductdemand(String fids, String userId) throws Exception {
			String sql="select fid from t_ord_schemedesign where ffirstproductid in ('%s')";
			List<HashMap<String, Object>> slist=this.schemedesignDao.QueryBySql(String.format(sql, fids));
			for(int i=0;i<slist.size();i++)
			{
				delSchemeDesign(slist.get(i).get("fid").toString());
			}

		sql = "update t_ord_firstproductdemand set fstate='已接收',freceived=1,freceivetime= now(), freceiver='"+userId+"' where fstate  <>'已设计' and fstate  <>'已完成' and fstate  <>'确认方案' and fstate<>'已发布' and fstate<>'已关闭' and fid in ('%s')";
		this.ExecBySql(String.format(sql,fids));
	}

	public void delSchemeDesigns(String[] fid)throws Exception
	{
		for(String id: fid)
		{
			delSchemeDesign(id);
		}
	}
	@Override
	public void delSchemeDesign(String fid) throws Exception {
		List<HashMap<String, Object >> list;
		File file;
		if(this.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+fid+"'")){
			throw new Exception("删除失败，已确认的方案设计不能删除！");
		}
		String sql = "select 1 from t_ord_appraise where fschemedesignid ='"+fid+"'";
		list = this.schemedesignDao.QueryBySql(sql);
		if(list.size()>0){
			throw new Exception("有评价的方案设计不能删除！");
		}
		sql = "SELECT fid FROM t_ord_productstructure where schemedesignid = '"+fid+"'";
		List<HashMap<String, Object>> productlist = this.schemedesignDao.QueryBySql(sql);
		if(productlist.size()!=0){
			sql = "SELECT fid FROM t_ord_productstructure  where 1=1 AND fparentid='"+productlist.get(0).get("fid")+"'";
			List<HashMap<String, Object>> subProducts = this.schemedesignDao.QueryBySql(sql);
			productlist.addAll(subProducts);
		}
		for(int j = 0;j<productlist.size();j++){
		
			this.ExecBySql("delete from t_ord_productstructure where fid ='"+productlist.get(j).get("fid")+"'");
		}
		//删除方案特性
		sql = "delete from t_ord_schemedesignentry where fparentid = '"+fid+"'";
		this.ExecBySql(sql);
		//删除方案设计
		sql = "delete from t_ord_schemedesign where fid = '"+fid+"'";
		this.ExecBySql(sql);
		// 删除附件和记录
		sql = "select * from t_ord_productdemandfile where fparentid = '"+fid+"'";
		list = this.schemedesignDao.QueryBySql(sql);
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
	public void saveProductStructs(Firstproductdemand demand, List<Productstructure> productlist) {
		// TODO Auto-generated method stub
		//增加产品和客户产品以及它们的关联
				Productstructure p = null;
				String sql = "SELECT fid FROM t_ord_productstructure where schemedesignid = '"+demand.getFid()+"'";
				List<HashMap<String, Object>> list = this.schemedesignDao.QueryBySql(sql);
				if(list.size()!=0){
					sql = "SELECT fid FROM t_ord_productstructure  where 1=1 AND fparentid='"+list.get(0).get("fid")+"'";
					List<HashMap<String, Object>> subProducts = this.schemedesignDao.QueryBySql(sql);
					list.addAll(subProducts);
				}
				for(int j = 0;j<list.size();j++){
					this.ExecBySql("delete from t_ord_productstructure where fid ='"+list.get(j).get("fid")+"'");
				}
				if(productlist.size()==0){
					return;
				}
				String fuuid = StringUitl.isNullOrEmpty(productlist.get(0).getFid())?this.CreateUUid():productlist.get(0).getFid();
				for(int i = 0;i<productlist.size();i++){
					p = productlist.get(i);
					if(StringUitl.isNullOrEmpty(p.getFid())){
						if(i==0){
							p.setFid(fuuid);
						}else{
							p.setFid(this.CreateUUid());
						}
					}
					
					if(i>0&&StringUitl.isNullOrEmpty(p.getFparentid())){
						p.setFparentid(fuuid);
					}
				//	if(StringUitl.isNullOrEmpty(p.getFnumber())) p.setFnumber(p.getFname());
					p.setFcustomerid(demand.getFcustomerid());
					p.setFsupplierid(StringUitl.isNullOrEmpty(demand.getFsupplierid())?"39gW7X9mRcWoSwsNJhU12TfGffw=":demand.getFsupplierid());
					this.saveOrUpdate(p);
				}
		
	}

	@Override
	public List<HashMap<String, Object>> getFirstdemandproduct(String fid) {
		List<HashMap<String, Object>> productList;
		List<HashMap<String, Object>> productSubs;
		String sql = "select  fid,fname,fnumber,fmaterialcodeid,fboxlength,fboxwidth,fboxheight ,subProductAmount,fparentid,schemedesignid,fcustomerid,fsupplierid  from t_ord_productstructure where schemedesignid = '"+fid+"'";
		productList = this.schemedesignDao.QueryBySql(sql);
			if(productList.size()>0){
				productList.get(0).put("files", getfileInfo((String)productList.get(0).get("fid")));
				sql = "select  fid,fname,fnumber,fmaterialcodeid,fboxlength,fboxwidth,fboxheight ,subProductAmount,fparentid,schemedesignid  from t_ord_productstructure where fparentid = '"+productList.get(0).get("fid")+"'";
				productSubs = this.schemedesignDao.QueryBySql(sql);
					for(int i=0;i<productSubs.size();i++){
						productSubs.get(i).put("files", getfileInfo((String)productSubs.get(i).get("fid")));
					}
					productList.addAll(productSubs);
				}
			return productList;
	}
	
	public List<HashMap<String, Object>> getfileInfo(String fid) {
	String sql = "select fid,fname from t_ord_productdemandfile where fparentid='"+fid+"'";
	return this.schemedesignDao.QueryBySql(sql);
	}

	@Override
	public synchronized String affirmSchemeDesign(String userid,String fids) throws Exception {
		String sql;
		String[] fidArr = fids.split(",");
		sql = "select ffirstproductid from t_ord_schemedesign where fid in("+fids+")";
		List<HashMap<String,Object>> l = schemedesignDao.QueryBySql(sql);
		for(HashMap<String, Object> scmap : l){
			String fdid = scmap.get("ffirstproductid").toString();
			sql = "select 1 from t_ord_firstproductdemand where fid = '"+fdid+"' and fstate ='关闭'";
			if(tordFirstproductdemandManager.QueryExistsBySql(sql)){
				throw new Exception("你选择的方案对应的需求已关闭,不能操作");
			}
		}
		sql = "select 1 from t_ord_schemedesign where fconfirmed =1 and fid in ("+fids+")";
		if(schemedesignDao.QueryExistsBySql(sql)){
			throw new Exception("你选择的方案有已确认的,不能再次确认");
		}
		//2016-01-14 循环方案fid  
		for(int i=0;i<fidArr.length;i++){
			return schemedesignDao.affirmSchemeDesignByid(userid,fidArr[i]);
		}
		return "success";
	}

	@Override
	public String schemeSave(HashMap params) {
		String resStr = "success";
		Schemedesign scinfo = (Schemedesign)params.get("scinfo");
		List<SchemeDesignEntry> scentryList = (List<SchemeDesignEntry>) params.get("scentryList");
		List<Productstructure> pdtlist = (List<Productstructure>) params.get("pdtlist");
		String sql = "select fid from t_ord_firstproductdemand where fid = '"+scinfo.getFfirstproductid()+"' and fstate ='关闭' ";
		if(tordFirstproductdemandManager.QueryExistsBySql(sql)){
			resStr = "已关闭需求不能新增方案";
			return resStr;
		}
		sql = "select 1 from t_ord_firstproductdemand where ifnull(freceived,0)=0  and fid ='"+scinfo.getFfirstproductid()+"'";
		if(tordFirstproductdemandManager.QueryExistsBySql(sql)){
			resStr = "未接收不能新增";
			return resStr;
		}
		sql = "select 1 from t_ord_SchemeDesign where fname = '"+scinfo.getFname()+"' and fid != '"+scinfo.getFid()+"'";
		if(tordFirstproductdemandManager.QueryExistsBySql(sql)){
			resStr = "此方案名称已存在，请重新填写方案名称";
			return resStr;
		}
		sql = "select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+scinfo.getFid()+"'";
		if(tordFirstproductdemandManager.QueryExistsBySql(sql)){
			resStr = "已确认的方案设计不能修改";
			return resStr;
		}
		if((scentryList!=null && scentryList.size()>0) && (pdtlist!=null && pdtlist.size()>0)){
			resStr = "一个方案不能同时新增特性和产品";
			return resStr;
		}
		resStr = schemedesignDao.schemeSave(scinfo, scentryList, pdtlist);
		return resStr;
	}


	@Override
	public HashMap getScinfo(String fid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HashMap<String, Object>> getSchemeEntrys(String fparentid) {
		// TODO Auto-generated method stub
		Params p=new Params();
		p.put("fparentid", fparentid);
		String sql = "select fid,fdrawinfo,fpurchasenumber,fentryamount,fcharacter from t_ord_schemedesignentry where fparentid=:fparentid";
		return this.schemedesignDao.QueryBySql(sql, p);
	}

	@Override
	public void auditSchemedeginPackge(HashMap map) throws  Exception{
	
		Schemedesign scinfo = (Schemedesign)map.get("scinfo");
		scinfo.setFaudited(1);
		scinfo.setFauditorid((String)map.get("auditor"));
		scinfo.setFaudittime(new Date());
		String resStr=schemeSave(map);
		if(!"success".equals(resStr)) throw new Exception(resStr);
	
	}
//保存高端设计
	@Override
	public void saveGdSjcjnfo(List<Schemedesign> scList) throws  Exception {
		// TODO Auto-generated method stub
		Schemedesign desgin=null;
		String fdid = "";
		for(int i=0;i<scList.size();i++){
			desgin=scList.get(i);
			fdid = desgin.getFfirstproductid();
			String sql = "select 1 from t_ord_SchemeDesign where fname = '"+desgin.getFname()+"' and fid != '"+desgin.getFid()+"' and ffirstproductid = '"+fdid+"'";
			if(tordFirstproductdemandManager.QueryExistsBySql(sql)){
				 throw new Exception("此方案名称已存在，请重新填写方案名称");
			}
			if(desgin.getFcreatetime()==null)desgin.setFcreatetime(new Date());
			desgin.setFaudited(1);
			desgin.setFaudittime(new Date());
			desgin.setFauditorid("3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5");
			this.schemedesignDao.saveOrUpdate(desgin);
		}
		//修改需求状态
		String sql = "update t_ord_firstproductdemand set fstate = '已设计' where fid = '"+fdid+"' and fstate = '已接收'";
		this.ExecBySql(sql);
	}
	//保存需求包
	@Override
	public String saveXqbjnfo(List<HashMap> list) throws  Exception {
		String resStr = "";
		for(HashMap map:list)
		{
			resStr=schemeSave(map);
			 if(!resStr.equals("success")){
				 throw new Exception(resStr);
			 }
		}
		return resStr;
	}
	
	
	

	
}
