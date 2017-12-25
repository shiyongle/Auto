package Com.Controller.Inv;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.System.Useronline;

@Controller
public class StorebalanceController {
	private static final String BASE_SQL = " select pp.fproductid,pp.fname,pp.fbalanceqty,pp.forderunitid,pp.fnumber,pp.fcustomerid,tiu.fusedqty,top.fmakingqty fmakingqty from(select p.fproductid fproductid,f.fname fname,sum(fbalanceqty)fbalanceqty,f.fcustomerid fcustomerid,p.ftraitid,f.forderunitid forderunitid,f.fnumber fnumber from t_inv_storebalance p left join t_pdt_productdef f ON f.fid=p.fproductid and p.ftraitid is null group by p.fproductid)pp left join(select sum(case when famount<fstockinqty then 0 else famount-ifnull(fstockinqty,0)end)fmakingqty,fproductdefid from t_ord_productplan where faudited=1 and fcloseed=0 group by fproductdefid)top ON top.fproductdefid=pp.fproductid left join(SELECT fproductid,sum(fusedqty)fusedqty from t_inv_usedstorebalance group by fproductid)tiu ON tiu.fproductid=pp.fproductid where 1=1 and pp.fbalanceqty<>0 union select pp.fproductid,pd.fcharacter fname,pp.fbalanceqty,''forderunitid,''fnumber,pp.fcustomerid,tiu.fusedqty,case when pd.fentryamount<pp.finqty then 0 else pd.fentryamount-ifnull(pp.finqty,0)end fmakingqty from t_inv_storebalance pp left join t_ord_schemedesignentry pd ON pp.ftraitid=pd.fid left join(SELECT fstorebalanceid,sum(fusedqty)fusedqty from t_inv_usedstorebalance group by fstorebalanceid)tiu ON tiu.fstorebalanceid=pp.fid where 1=1 and pp.fbalanceqty<>0 and pp.ftraitid is not null ";
	Logger log = LoggerFactory.getLogger(productindetailController.class);
	@Resource
	private IStorebalanceDao storebalanceDao;
	@Resource
	private IBaseSysDao baseSysDao;

	@RequestMapping(value = "/GetStorebalanceList")
	public String GetStorebalanceList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT pp.fnumber productplannum,w.fid,w.FWarehouseID,ifnull(wh.fname,'') as whname,w.FWarehouseSiteID,ifnull(whs.fname,'') as whsname,w.FProductID, ifnull(d.fname,'') as pdtname,ifnull(w.fdescription,'') fdescription,w.fcreatorid, ifnull(u1.fname,'') as cfname,ifnull(u2.fname,'') as lfname ,ifnull(w.fcreatetime,'') fcreatetime,w.fupdateuserid,ifnull(w.fupdatetime,'') fupdatetime,w.finqty,w.foutqty,w.fbalanceqty FROM t_inv_storebalance w left join t_pdt_productdef d on d.fid=w.FProductID left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user u2 on u2.fid= w.fupdateuserid left join t_bd_warehouse wh on wh.fid=w.FWarehouseID left join t_bd_warehousesites whs on whs.fid=w.FWarehouseSiteID left join t_ord_productplan pp on pp.fid=w.fproductplanId  where 1=1 "
					+ storebalanceDao.QueryFilterByUser(request, null,
							"pp.fsupplierid");
			ListResult result = storebalanceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/selectSuitsHavingTheSameSon")
	public String selectSuitsHavingTheSameSon(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		request.setCharacterEncoding("utf-8");
		reponse.setCharacterEncoding("utf-8");

		try {
			
//			String id = request.getParameter("");

			String sql = " SELECT t1.dbId as dbId, t1.name as name, t1.balanceqty as balanceqty FROM t_inv_storebalance_tree_grid t1,t_inv_storebalance_tree_grid t2 where t1.id = t2.parentid ";
					
			ListResult result = storebalanceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));

		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/Storebalancetoexcel")
	public String Storebalancetoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			request.setAttribute("nolimit", 0);
			String sql = "SELECT w.fid,w.FWarehouseID 仓库ID,ifnull(wh.fname,'') as 仓库名称,w.FWarehouseSiteID 库位ID,ifnull(whs.fname,'') as 库位名称,w.FProductID 产品ID,ifnull(d.fname,'') as 产品名称,w.fdescription 描述,u1.fname as 创建人,u2.fname as 修改人 ,w.fcreatetime 创建时间,w.fupdatetime 修改时间,w.finqty 入库数量,w.foutqty 出库数量,w.fbalanceqty 库存数量 FROM t_inv_storebalance w left join t_pdt_productdef d on d.fid=w.FProductID left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user u2 on u2.fid= w.fupdateuserid left join t_bd_warehouse wh on wh.fid=w.FWarehouseID left join t_bd_warehousesites whs on whs.fid=w.FWarehouseSiteID ";
			ListResult result = storebalanceDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/GetStorebalanceFilterByUserList")
	public String GetStorebalanceFilterByUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql=" select fproductid,pp.fname,foutqty,finqty,fbalanceqty,ifnull(pd.fcharacter,'') fcharacter from (select p.fproductid ,f.fname fname,sum(foutqty) foutqty,sum(finqty) finqty,sum(fbalanceqty) fbalanceqty,f.fcustomerid,p.ftraitid from t_inv_storebalance  p left join  t_pdt_productdef f on f.fid=p.fproductid group by p.fproductid ) pp left join t_ord_schemedesignentry pd on pp.ftraitid=pd.fid where 1=1 "+storebalanceDao.QueryFilterByUser(request, "fcustomerid", null);
			ListResult result = storebalanceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/GetStorebalancebyTypeList")
	public String GetStorebalancebyTypeList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			String 	sql="select fparentid from t_pdt_productdefproducts where 1=1  ";
			ListResult productsresult = storebalanceDao.QueryFilterList(sql, request);
			JSONArray jj = JSONArray.fromObject(request.getParameter("Defaultfilter"));
			JSONObject o = jj.getJSONObject(0);
			String productdefid=o.getString("value");
			if("0".equals(productsresult.getTotal()))//普通产品，不是套装产品
			{
				sql = "select favailablenum,fallotqty,productplannum,pfid,fid,fproductid,pdtname,finqty,foutqty,fbalanceqty,sfname,fsupplierid,fsaleorderid,forderentryid,ftype,fissuit,fparentid from ( "
						+ " SELECT w.fbalanceqty+w.fallotqty-ifnull(r.famount,0) favailablenum,w.fallotqty,ifnull(pp.fnumber,'') productplannum,ifnull(pp.fid,'') pfid,w.fid,w.fproductid, ifnull(d.fname,'') as pdtname,w.finqty,w.foutqty,w.fbalanceqty,ifnull(s.fname,'') as sfname,w.fsupplierid ,ifnull(w.fsaleorderid,'') fsaleorderid,ifnull(w.forderentryid,'') forderentryid,w.ftype as ftype,'0'as fissuit,w.fproductid as fparentid "
						+ " FROM t_inv_storebalance w  "
						+ " left join t_pdt_productdef d on d.fid=w.fproductid "
						+ " left join t_ord_productplan pp on pp.fid=w.fproductplanId  "
						+ " left join t_sys_supplier s on s.fid=w.fsupplierid  "
						+ " left join (select ifnull(sum(fusedqty),0) famount ,fstorebalanceid from t_inv_usedstorebalance "
						+ "	where  fusedqty>0  and ftype=1 and fproductid = '"+productdefid+"' group by fstorebalanceid "
						+" ) r ON   w.fid=r.fstorebalanceid  "
						+ "	where ( w.fbalanceqty+w.fallotqty>0 or( w.fbalanceqty=0 and pp.fcloseed=0 )) and w.fproductid='"
						+ productdefid
						+ "' and  w.ftype=1 "
						+ " union SELECT w.fbalanceqty+w.fallotqty-ifnull(r.famount,0) favailablenum,w.fallotqty,'' productplannum,'' pfid,w.fid,w.fproductid, "
						+ " ifnull(d.fname,'') as pdtname,w.finqty,w.foutqty,w.fbalanceqty,ifnull(s.fname,'') as sfname,"
						+ " w.fsupplierid ,'' fsaleorderid,'' forderentryid,w.ftype as ftype,'0'as fissuit ,w.fproductid as fparentid"
						+ "	 FROM t_inv_storebalance w  "
						+ "	 left join t_pdt_productdef d on d.fid=w.fproductid "
						+ "	 left join t_ord_productplan pp on pp.fid=w.fproductplanId  "
						+ "	 left join t_sys_supplier s on s.fid=w.fsupplierid  "
						+ " left join  t_pdt_vmiproductparam pr on pr.fproductid=w.fproductid and pr.fsupplierid=w.fsupplierid and pr.ftype=0 "
						+ "	left join ("
						+ "	select  sum(ifnull(fusedqty,0)) famount,fstorebalanceid "
						+ "	from t_inv_usedstorebalance   where fusedqty>0 and  ftype=0  and fproductid='"
						+ productdefid
						+ "' group by fstorebalanceid"
						+ "	) r on r.fstorebalanceid= w.fid where   "
//						+ "	 w.fbalanceqty>0 and "
						+ " w.ftype=0 and w.fproductid='"+productdefid+"' and ifnull(pr.fid,'')<>'' ) tt  where 1=1";
			}else//套装产品
			{
			
				sql = "select favailablenum,fallotqty,productplannum,pfid,fid,fproductid,pdtname,finqty,foutqty,fbalanceqty,sfname,fsupplierid,fsaleorderid,forderentryid,ftype,fissuit, fparentid from (  "
						+ " select fbalanceqtys AS favailablenum,fallotqty,'' productplannum,'' pfid,'00000000-0000-0000-0000-000000000000' AS fid,fparentid AS fproductid, "
						+ " ifnull(d.fname,'') as pdtname,'-'finqty,'-'foutqty,'-'fbalanceqty,ifnull(s.fname,'') as sfname,bq.fsupplierid,"
						+ " ''fsaleorderid,''forderentryid ,0 as ftype,'1'as fissuit,fparentid"
						+ "  from (	select p.fparentid,y.fsupplierid,min((ifnull(s.fbalanceqty,0)+ifnull(s.fallotqty,0)-ifnull(u.lockamt,0))/famount) fbalanceqtys,min(ifnull(s.fallotqty,0)/famount) fallotqty "
						+ " from  t_pdt_productdefproducts  p "
						+ " left join t_bd_productcycle  y on y.fproductdefid = p.fparentid "
					    +" left join (select s.fid,s.fproductid,FSUPPLIERID,fbalanceqty,fallotqty from  t_inv_storebalance  s "
					    +" left join t_pdt_productdefproducts p on p.fproductid=s.fproductid where  p.fparentid = '"+productdefid+"'  and ftype=0 ) s ON s.FSUPPLIERID = y.fsupplierid and   s.fproductid = p.fproductid "
					    +"left join (select  sum(ifnull(fusedqty, 0)) lockamt, fstorebalanceid,fproductid  from t_inv_usedstorebalance where fusedqty > 0 and ftype = 0 group by fstorebalanceid ) u on u.fstorebalanceid=s.fid  "
						+ "	where  p.fparentid='"
						+ productdefid
						+ "' and y.fsupplierid in( select fsupplierid from t_bd_productcycle where fproductdefid='"+productdefid+"' )"
						+ " group by p.fparentid,y.fsupplierid  ) bq "
						+ " left join  t_pdt_vmiproductparam pr on pr.fproductid=bq.fparentid and pr.fsupplierid=bq.fsupplierid "
						+ "	left join t_pdt_productdef d on d.fid=bq.fparentid "
						+ "	left join t_sys_supplier s on s.fid=bq.fsupplierid"
						+ " where pr.ftype=0 and ifnull(pr.fid,'')<>''"
						+ " union SELECT w.fbalanceqty+w.fallotqty-ifnull(r.famount,0) favailablenum,w.fallotqty,ifnull(pp.fnumber,'') productplannum,ifnull(pp.fid,'') pfid,w.fid,w.fproductid, ifnull(d.fname,'') as pdtname,w.finqty,w.foutqty,w.fbalanceqty,ifnull(s.fname,'') as sfname,w.fsupplierid ,ifnull(w.fsaleorderid,'') fsaleorderid,ifnull(w.forderentryid,'') forderentryid,w.ftype as ftype,'1'as fissuit, w.fproductid as fparentid "
						+ "	 FROM t_inv_storebalance w  "
						+ "	 left join t_pdt_productdef d on d.fid=w.fproductid "
						+ "	 left join t_ord_productplan pp on pp.fid=w.fproductplanId  "
						+ "	 left join t_sys_supplier s on s.fid=w.fsupplierid  "
						+ " left join (select ifnull(sum(fusedqty),0) famount ,fstorebalanceid from t_inv_usedstorebalance "
						+ "	where  fusedqty>0  and ftype=1 and fproductid = '"+productdefid+"' group by fstorebalanceid "
						+"	 ) r ON   w.fid=r.fstorebalanceid  "
						+ "	 where ( w.fbalanceqty+w.fallotqty>0 or( w.fbalanceqty=0 and pp.fcloseed=0 )) and w.fproductid='"
						+ productdefid + "'  and  w.ftype=1 ) tt  where 1=1 ";
				
			}
			List result = storebalanceDao.QueryBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true, "", ""+result.size(),result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	
	@RequestMapping(value = "/selectProductHistoryQty")
	public String selectProductHistoryQty(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		reponse.setCharacterEncoding("utf-8"); 
		request.setCharacterEncoding("utf-8");
		
		try {
			String sql = MySimpleToolsZ.getMySimpleToolsZ().buildLineSql("t_ord_productplan", "fstoreqty", "fcreatetime", storebalanceDao.QueryFilterByUser(request, "fcustomerid", null), false);
			
			ListResult result = new ListResult(); 
			
			List<HashMap<String, Object>> listObjs = storebalanceDao.QueryBySql(sql); 
			
			MySimpleToolsZ.getMySimpleToolsZ().sortListByDateDesc(listObjs, "fcreatetime");
			
			
			result.setData(listObjs);
			result.setTotal(listObjs.size() + "");
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	/**
	 * 查询产品库存，暂不用
	 * 逻辑有错误，ftraitid is null 不能放left join on 后
	 */
	@RequestMapping(value = "/selectStorebalances")
	public String selectStorebalances(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String userid =
				 ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		
		try {
			String sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
			List<HashMap<String,Object>> list = storebalanceDao.QueryBySql(sqls);//是否有过了产品
			String fparoductid = "('";//接收 客户产品转化成产品的ID
			if(list.size()>0){
				sqls = "SELECT pf.fid FROM `t_pdt_productrelationentry` pty LEFT JOIN `t_pdt_productrelation` pt ON pty.fparentid = pt.fid LEFT JOIN `t_pdt_productdef` pf ON pt.fproductid=pf.fid  WHERE pty.fcustproductid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
				list = storebalanceDao.QueryBySql(sqls);
				if(list.size()>0){
					for(int i =0;i<list.size();i++){
						fparoductid +=list.get(i).get("fid");
						if(i<list.size()-1){
							fparoductid += "','";
						}
					}
					fparoductid += "')";
					sqls = " and pp.fproductid IN"+fparoductid;
				}else{
					 sqls=" select d.fid from  t_pdt_custrelation  r "+
								" left join t_pdt_custrelationentry e on e.fparentid=r.fid left join  t_pdt_productdef  d on d.fid=e.fproductid "+
								" where r.fcustproductid in (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
					 list= storebalanceDao.QueryBySql(sqls);
					 if(list.size()>0){
						 for(int i =0;i<list.size();i++){
								fparoductid +=list.get(i).get("fid");
								if(i<list.size()-1){
									fparoductid += "','";
								}
							}
							fparoductid += "')";
							sqls = " and pp.fproductid IN"+fparoductid;
					 }
				}
				
			}else{
				sqls = "";
			}
			//执行远程查询  
//			if (request.getParameter("condictionValue") != null) {
//				
//				return selectStorebalancesByCondition(request, reponse,sqls);
//			}
			String sql = " select pp.fproductid,pp.fname,pp.fbalanceqty,pp.forderunitid,pp.fnumber,pp.fcustomerid,tiu.fusedqty,top.fmakingqty fmakingqty from(select p.fproductid fproductid,f.fname fname,sum(fbalanceqty)fbalanceqty,f.fcustomerid fcustomerid,p.ftraitid,f.forderunitid forderunitid,f.fnumber fnumber from t_inv_storebalance p left join t_pdt_productdef f ON f.fid=p.fproductid and p.ftraitid is null group by p.fproductid)pp left join(select sum(case when famount<fstockinqty then 0 else famount-ifnull(fstockinqty,0)end)fmakingqty,fproductdefid from t_ord_productplan where faudited=1 and fcloseed=0 group by fproductdefid)top ON top.fproductdefid=pp.fproductid left join(SELECT fproductid,sum(fusedqty)fusedqty from t_inv_usedstorebalance group by fproductid)tiu ON tiu.fproductid=pp.fproductid where 1=1 and pp.fbalanceqty<>0  %s union select pp.fproductid,pd.fcharacter fname,pp.fbalanceqty,''forderunitid,''fnumber,pp.fcustomerid,tiu.fusedqty,case when pd.fentryamount<pp.finqty then 0 else pd.fentryamount-ifnull(pp.finqty,0)end fmakingqty from t_inv_storebalance pp left join t_ord_schemedesignentry pd ON pp.ftraitid=pd.fid left join(SELECT fstorebalanceid,sum(fusedqty)fusedqty from t_inv_usedstorebalance group by fstorebalanceid)tiu ON tiu.fstorebalanceid=pp.fid where 1=1 and pp.fbalanceqty<>0 and pp.ftraitid is not null %s ";
			
			
			
			sql = String.format(sql,  sqls+storebalanceDao.QueryFilterByUser(request, "pp.fcustomerid", 
					null), sqls+storebalanceDao.QueryFilterByUser(request, "pp.fcustomerid", 
					null));
			sql = "select e3.fcustproductid fproductid,e3.fname,floor(e.fbalanceqty/e3.radio) fbalanceqty,e.forderunitid,e3.fnumber,e.fcustomerid,floor(e.fusedqty/e3.radio) fusedqty,floor(e.fmakingqty/e3.radio) fmakingqty from ("+sql+") e "
					+"left join (select e1.fproductid,e2.fcustproductid,e2.fcustomerid,e4.fname fname,e4.fnumber fnumber,e1.famount radio from t_pdt_custrelationentry e1 left join t_pdt_custrelation e2 on e1.fparentid =e2.fid left join t_bd_custproduct e4 on e2.fcustproductid=e4.fid) e3 "
					+" on e.fproductid=e3.fproductid and e.fcustomerid=e3.fcustomerid";
			List result = storebalanceDao.QueryBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result.size() + "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", "")); 
		}
		return null;
	}
	
	@RequestMapping(value = "/selectStorebalancesByCondition")
	public String selectStorebalancesByCondition(HttpServletRequest request,
			HttpServletResponse reponse,String sqls) throws IOException {
		String field = "pp." + request.getParameter("condictionField");
		String value =request.getParameter("condictionValue");
		value =  new String(value.toString().getBytes("ISO-8859-1"), "UTF-8");
		
		try {
			String sql1 = "  select pp.fproductid,pp.fname,pp.fbalanceqty,pp.forderunitid,pp.fnumber,pp.fcustomerid,tiu.fusedqty,top.fmakingqty fmakingqty from(select p.fproductid fproductid,f.fname fname,sum(fbalanceqty)fbalanceqty,f.fcustomerid fcustomerid,p.ftraitid,f.forderunitid forderunitid,f.fnumber fnumber from t_inv_storebalance p left join t_pdt_productdef f ON f.fid=p.fproductid and p.ftraitid is null group by p.fproductid)pp left join(select sum(case when famount<fstockinqty then 0 else famount-ifnull(fstockinqty,0)end)fmakingqty,fproductdefid from t_ord_productplan where faudited=1 and fcloseed=0 group by fproductdefid)top ON top.fproductdefid=pp.fproductid left join(SELECT fproductid,sum(fusedqty)fusedqty from t_inv_usedstorebalance group by fproductid)tiu ON tiu.fproductid=pp.fproductid where 1=1 and pp.fbalanceqty<>0  " + " and " + field + " like '%" + value + "%' "
					+ storebalanceDao.QueryFilterByUser(request, "pp.fcustomerid",
							null);
			
			String sql2 = null;
			
			if (field.equals("pp.fname")) {
				
				field = "pd.fcharacter";
				
				sql2 = " union select pp.fproductid,pd.fcharacter fname,pp.fbalanceqty,''forderunitid,''fnumber,pp.fcustomerid,tiu.fusedqty,case when pd.fentryamount<pp.finqty then 0 else pd.fentryamount-ifnull(pp.finqty,0)end fmakingqty from t_inv_storebalance pp left join t_ord_schemedesignentry pd ON pp.ftraitid=pd.fid left join(SELECT fstorebalanceid,sum(fusedqty)fusedqty from t_inv_usedstorebalance group by fstorebalanceid)tiu ON tiu.fstorebalanceid=pp.fid where 1=1 and pp.fbalanceqty<>0 and pp.ftraitid is not null   " + " and " + field + " like '%" + value + "%' "
						+ storebalanceDao.QueryFilterByUser(request, "pp.fcustomerid",
								null);
				
			} else if (field.equals("pp.fnumber")) {
				
				sql2 = "";
				
			}
			sql1 = sql1+sqls;
			sql2 =sqls;
			List result = storebalanceDao.QueryBySql(sql1 + sql2);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result.size() + "", result));
			
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));  
		}
		return null;
	}

//	@RequestMapping(value = "/queryStoreBalances")
//	public String queryStoreBalances(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		String userid = baseSysDao.getCurrentUserId(request);
//		String sql;
//		try {
//			if (storebalanceDao
//					.QueryExistsBySql("SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"
//							+ userid + "'")) {
//				sql = " select c.fname cname,t.fid,t.fname,t.fnumber,t.fcustomerid,ifnull((case when fstoreqty<0 then 0 else fstoreqty end),0) fbalanceqty,ifnull(fusedqty,0) fusedqty,fmakingqty,fproductid "
//								+ "from t_bd_userrelationcustp e left join t_bd_custproduct t on e.fcustproductid=t.fid left join t_bd_customer c on t.fcustomerid=c.fid "
//								+ "left join ( "
//								+ "select cp.fid,(case when ifnull(cr.radio,0)>0 then b.amount/cr.radio else b.amount*ifnull(pr.radio,1) end-fusedqty) fstoreqty,fusedqty,ifnull(fmakingqty,0) fmakingqty,ifnull(cr.fproductid,pr.fproductid)  fproductid "
//								+ "FROM t_bd_custproduct cp  "
//								+ "left join (select r.fcustproductid,rn.fproductid fproductid,rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation r on rn.fparentid = r.fid "
//								+ "group by r.fcustproductid having count(1)=1) cr on cr.fcustproductid=cp.fid "
//								+ "left join (select pn.fcustproductid,p.fproductid ,pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation p on pn.fparentid=p.fid  "
//								+ "group by pn.fcustproductid having count(1)=1) pr on pr.fcustproductid=cp.fid "
//								+ "left join (select fproductid,sum(fbalanceqty) amount from t_inv_storebalance group by fproductid  "
//								+ ") b on b.fproductid=ifnull(cr.fproductid,pr.fproductid) "
//								+ "left join (select sum(case when fusedqty<0 then 0 else fusedqty end) fusedqty,fproductid from t_inv_usedstorebalance group by fproductid ) us on us.fproductid=ifnull(cr.fproductid,pr.fproductid) "
//								+ "left join (select sum(case when famount < ifnull(fstockinqty,0)  then 0 else famount - ifnull(fstockinqty, 0) end ) fmakingqty,fproductdefid from t_ord_productplan where faudited = 1 and fcloseed = 0 group by fproductdefid) p on p.fproductdefid=ifnull(cr.fproductid,pr.fproductid) "
//								+ ") n on n.fid=t.fid "
//								+ "where (ifnull(fstoreqty,0)>0 or ifnull(fusedqty,0)>0 or ifnull(fmakingqty,0)>0) " + 
//						"and e.fuserid = '"
//						+ userid + "' and t.feffect=1";
//			} else {
//				sql = " select c.fname cname,t.fid,t.fname,t.fnumber,t.fcustomerid,ifnull((case when fstoreqty<0 then 0 else fstoreqty end),0) fbalanceqty,ifnull(fusedqty,0) fusedqty,fmakingqty,fproductid "
//						+ "from t_bd_custproduct t left join t_bd_customer c on t.fcustomerid=c.fid "
//						+ "left join ( "
//						+ "select cp.fid,(case when ifnull(cr.radio,0)>0 then b.amount/cr.radio else b.amount*ifnull(pr.radio,1) end-fusedqty) fstoreqty,fusedqty,ifnull(fmakingqty,0) fmakingqty,ifnull(cr.fproductid,pr.fproductid)  fproductid "
//						+ "FROM t_bd_custproduct cp  "
//						+ "left join (select r.fcustproductid,rn.fproductid fproductid,rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation r on rn.fparentid = r.fid "
//						+ "group by r.fcustproductid having count(1)=1) cr on cr.fcustproductid=cp.fid "
//						+ "left join (select pn.fcustproductid,p.fproductid ,pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation p on pn.fparentid=p.fid  "
//						+ "group by pn.fcustproductid having count(1)=1) pr on pr.fcustproductid=cp.fid "
//						+ "left join (select fproductid,sum(fbalanceqty) amount from t_inv_storebalance group by fproductid  "
//						+ ") b on b.fproductid=ifnull(cr.fproductid,pr.fproductid) "
//						+ "left join (select sum(case when fusedqty<0 then 0 else fusedqty end) fusedqty,fproductid from t_inv_usedstorebalance group by fproductid ) us on us.fproductid=ifnull(cr.fproductid,pr.fproductid) "
//						+ "left join (select sum(case when famount < ifnull(fstockinqty,0)  then 0 else famount - ifnull(fstockinqty, 0) end ) fmakingqty,fproductdefid from t_ord_productplan where faudited = 1 and fcloseed = 0 group by fproductdefid) p on p.fproductdefid=ifnull(cr.fproductid,pr.fproductid) "
//						+ ") n on n.fid=t.fid "
//						+ "where (ifnull(fstoreqty,0)>0 or ifnull(fusedqty,0)>0 or ifnull(fmakingqty,0)>0) and t.feffect=1"
//						+ storebalanceDao.QueryFilterByUser(request,
//								"fcustomerid", null);
//			}
//			
//			ListResult result = storebalanceDao.QueryFilterList(sql, request);
//			reponse.getWriter().write(JsonUtil.result(true, "", result));
//			
//		} catch (DJException e) {
//			reponse.getWriter().write(
//					JsonUtil.result(false, e.getMessage(), "", ""));
//		}
//		return null;
//	}
//	
	
	@RequestMapping(value = "/queryStoreBalancesOld")
	public String queryStoreBalancesOld(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = baseSysDao.getCurrentUserId(request);
		String sql,ftypesql="";
		try {
		String customersql=storebalanceDao.QueryFilterByUser(request,"cp.fcustomerid", null);
		String ftype=request.getParameter("ftype");
		if(ftype!=null&&"1".equals(ftype))//1为按产品统计，0为按供应商统计
		{
			ftypesql = "select cp.fid,cp.fcustomerid,cp.fname,cp.fproductid,cp.fnumber,s.fbalanceqty * cp.radio fbalanceqty,fusedqty* cp.radio fusedqty,fmakingqty*cp.radio fmakingqty,'-' suppliername"
					+ " from ("
					+ " 	select  cp.fid,cp.feffect,cp.fcustomerid,cp.fname,cp.fnumber,"
					+ "	ifnull(pr.fproductid, cr.fproductid) fproductid,ifnull(pr.radio,1/cr.radio)  radio"
					+ "	from t_bd_custproduct cp"
					+ "	left join (select cp.fcustproductid, rn.fproductid fproductid, rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation cp ON rn.fparentid = cp.fid LEFT JOIN t_pdt_productdef f ON f.fid=rn.fproductid WHERE 1 = 1 AND IFNULL(f.fishistory, 0) = 0 AND IFNULL(f.feffect, 0) = 1 "+customersql+"group by cp.fcustproductid having count(1) = 1) cr ON cr.fcustproductid = cp.fid"
					+ "	left join (select fcustproductid, fproductid,radio from( select  pn.fcustproductid, cp.fproductid, pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation cp ON pn.fparentid = cp.fid INNER JOIN (SELECT * FROM t_pdt_productdef WHERE  IFNULL(fishistory, 0) = 0 AND IFNULL(feffect, 0) = 1 ) pd ON pd.fid=cp.fproductid where 1=1"+customersql+" group by cp.fproductid having count(1) = 1) pp group by fcustproductid having count(1) = 1) pr ON pr.fcustproductid = cp.fid  "
					+ "	where ifnull(ifnull(pr.radio,cr.radio),0 )<>0 and cp.feffect=1 "+customersql
					+ " ) cp"
//					+" left join (select s.fproductid,s.fsupplierid, sum(case when amount - ifnull(fusedqty,0) < 0 then 0 else amount - ifnull(fusedqty,0)end )fbalanceqty,sum(ifnull(fusedqty,0))fusedqty,sum(ifnull(fmakingqty,0)) fmakingqty "
					+" left join (select s.fproductid,s.fsupplierid, sum(amount) fbalanceqty,sum(ifnull(fusedqty,0))fusedqty,sum(ifnull(fmakingqty,0)) fmakingqty "
					+" from "
					+" (select fproductid,fsupplierid, sum(fbalanceqty) amount from t_inv_storebalance group by fproductid,fsupplierid) s "
					+" left  join (select sum(fusedqty)fusedqty,s.fproductid,o.fsupplierid from t_inv_usedstorebalance s left join t_ord_deliverorder  o on o.fid=s.fdeliverorderid where fusedqty>0 group by s.fproductid,o.fsupplierid)  u on s.fproductid=u.fproductid and s.fsupplierid=u.fsupplierid"
					+" left join (select  sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty, fproductdefid,fsupplierid from t_ord_productplan where faudited = 1 and fcloseed = 0 group by fproductdefid,fsupplierid ) p on  s.fproductid=p.fproductdefid and s.fsupplierid=p.fsupplierid"
					+" where  ( s.amount > 0 or ifnull(fmakingqty, 0) > 0 or ifnull(fusedqty, 0) > 0) group by fproductid )s on s.fproductid=cp.fproductid"
					+ " where ifnull(s.fsupplierid,'')<>''  ";
			}else
			{
//				ftypesql = "select cp.fid,cp.fcustomerid,cp.fname,cp.fproductid,cp.fnumber,s.amount * cp.radio fstoreqty,ifnull(fusedqty* cp.radio,0) fusedqty,ifnull(fmakingqty*cp.radio,0) fmakingqty,r.fname suppliername,s.fsupplierid"
//						+ " from ("
//						+ " 	select  cp.fid,cp.feffect,cp.fcustomerid,cp.fname,cp.fnumber,"
//						+ "	ifnull(pr.fproductid, cr.fproductid) fproductid,ifnull(pr.radio,1/cr.radio)  radio"
//						+ "	from t_bd_custproduct cp"
//						+ "	left join (select r.fcustproductid, rn.fproductid fproductid, rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation r ON rn.fparentid = r.fid group by r.fcustproductid having count(1) = 1) cr ON cr.fcustproductid = cp.fid"
//						+ "	left join (select fcustproductid, fproductid,radio from( select  pn.fcustproductid, p.fproductid, pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation p ON pn.fparentid = p.fid group by p.fproductid having count(1) = 1) pp group by fcustproductid having count(1) = 1) pr ON pr.fcustproductid = cp.fid  "
//						+ "	where ifnull(ifnull(pr.radio,cr.radio),0 )<>0 and cp.feffect=1"
//						+ " ) cp"
//						+ " left join  (select fproductid,fsupplierid, sum(fbalanceqty) amount from t_inv_storebalance group by fproductid,fsupplierid) s on s.fproductid = cp.fproductid"
//						+ " left  join (select sum(fusedqty)fusedqty,s.fproductid,o.fsupplierid from t_inv_usedstorebalance s left join t_ord_deliverorder  o on o.fid=s.fdeliverorderid where fusedqty>0 group by s.fproductid,o.fsupplierid)  u on s.fproductid=u.fproductid and s.fsupplierid=u.fsupplierid"
//						+ " left join (select  sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty, fproductdefid,fsupplierid from t_ord_productplan where faudited = 1 and fcloseed = 0 group by fproductdefid,fsupplierid ) p on  s.fproductid=p.fproductdefid and s.fsupplierid=p.fsupplierid"
//						+ " left join t_sys_supplier r on r.fid=s.fsupplierid "
//						+ " where ifnull(s.fsupplierid,'')<>'' and(s.amount>0 or ifnull(fmakingqty,0)>0 or ifnull(fusedqty,0)>0)";
				ftypesql = "select cp.fid,cp.fcustomerid,cp.fname,cp.fproductid,cp.fnumber,s.fbalanceqty * cp.radio fbalanceqty,fusedqty* cp.radio fusedqty,fmakingqty*cp.radio fmakingqty,r.fname suppliername,s.fsupplierid"
						+ " from ("
						+ " 	select  cp.fid,cp.feffect,cp.fcustomerid,cp.fname,cp.fnumber,"
						+ "	ifnull(pr.fproductid, cr.fproductid) fproductid,ifnull(pr.radio,1/cr.radio)  radio"
						+ "	from t_bd_custproduct cp"
						+ "	left join (select cp.fcustproductid, rn.fproductid fproductid, rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation cp ON rn.fparentid = cp.fid LEFT JOIN t_pdt_productdef f ON f.fid=rn.fproductid where 1=1  AND IFNULL(f.fishistory, 0) = 0 AND IFNULL(f.feffect, 0) = 1 "+customersql+"group by cp.fcustproductid having count(1) = 1) cr ON cr.fcustproductid = cp.fid"
						+ "	left join (select fcustproductid, fproductid,radio from( select  pn.fcustproductid, cp.fproductid, pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation cp ON pn.fparentid = cp.fid INNER JOIN (SELECT * FROM t_pdt_productdef WHERE   IFNULL(fishistory, 0) = 0 AND IFNULL(feffect, 0) = 1) pd ON pd.fid=cp.fproductid where 1=1"+customersql+" group by cp.fproductid having count(1) = 1) pp group by fcustproductid having count(1) = 1) pr ON pr.fcustproductid = cp.fid  "
						+ "	where ifnull(ifnull(pr.radio,cr.radio),0 )<>0 and cp.feffect=1 "+customersql
						+ " ) cp"
//						+" left join (select s.fproductid,s.fsupplierid, case when amount - ifnull(fusedqty,0) < 0 then 0 else amount - ifnull(fusedqty,0)end fbalanceqty,ifnull(fusedqty,0)fusedqty,ifnull(fmakingqty,0) fmakingqty "
						+" left join (select s.fproductid,s.fsupplierid, amount fbalanceqty,ifnull(fusedqty,0)fusedqty,ifnull(fmakingqty,0) fmakingqty "
						+" from "
						+" (select fproductid,fsupplierid, sum(fbalanceqty) amount from t_inv_storebalance group by fproductid,fsupplierid) s "
						+" left  join (select sum(fusedqty)fusedqty,s.fproductid,o.fsupplierid from t_inv_usedstorebalance s left join t_ord_deliverorder  o on o.fid=s.fdeliverorderid where fusedqty>0 group by s.fproductid,o.fsupplierid)  u on s.fproductid=u.fproductid and s.fsupplierid=u.fsupplierid"
						+" left join (select  sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty, fproductdefid,fsupplierid from t_ord_productplan where faudited = 1 and fcloseed = 0 group by fproductdefid,fsupplierid ) p on  s.fproductid=p.fproductdefid and s.fsupplierid=p.fsupplierid"
						+" where  ( s.amount > 0 or ifnull(fmakingqty, 0) > 0 or ifnull(fusedqty, 0) > 0) )s on s.fproductid=cp.fproductid"
						+ " left join t_sys_supplier r on r.fid=s.fsupplierid "
						+ " where ifnull(s.fsupplierid,'')<>''  ";


			}
			if (storebalanceDao
					.QueryExistsBySql("SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"
							+ userid + "'")) {

				sql = " select c.fname cname,suppliername,t.fid,t.fname,t.fnumber,t.fcustomerid,fbalanceqty,fusedqty,fmakingqty,fproductid "
						+ " from t_bd_userrelationcustp e "
						+ " left join ( "
						+ ftypesql
						+ " ) t on e.fcustproductid=t.fid "
						+ " left join t_bd_customer c on t.fcustomerid=c.fid  where "
						+ " e.fuserid = '" + userid + "' and ifnull(t.fid,'')<>''";
			} else {
				sql = " select c.fname cname,suppliername,t.fid,t.fname,t.fnumber,t.fcustomerid, fbalanceqty,fusedqty,fmakingqty,fproductid "
						+ "from ("
						+ ftypesql
						+ " ) t "
						+ "left join t_bd_customer c on t.fcustomerid=c.fid where 1=1 ";
//						+ storebalanceDao.QueryFilterByUser(request,"t.fcustomerid", null);
			}
			
			ListResult result = storebalanceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	@RequestMapping(value = "/queryStoreBalances")
	public String queryStoreBalances(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = baseSysDao.getCurrentUserId(request);
		String sql,ftypesql="";
		try {
		String customersql=storebalanceDao.QueryFilterByUser(request,"cp.fcustomerid", null);		
		//正泰按原先查询；其他客户按一对一查询
		String ftype=request.getParameter("ftype");
		if(customersql.length()>0&&!customersql.contains("KNG83wEeEADgDmm4wKgCZ78MBA4="))
		{			
			customersql=customersql.replace("cp.", "");
			if(ftype!=null&&"1".equals(ftype))//1为按产品统计，0为按供应商统计   库存表根据客户过滤...（通知类型的产品 新盟、临港库存信息中 fcustomerid为空）目前只有正泰使用 ，以后通用 去掉客户过滤即可
			{
				
				 sql="  SELECT t.fid, t.fname,t.fnumber,t.fcustomerid,t.fproductid,c.fname cname,'-'suppliername,IFNULL(amount,0) fbalanceqty ,IFNULL(fusedqty,0) fusedqty ,IFNULL(fmakingqty,0) fmakingqty FROM  t_bd_custproduct  t"
						   + "  INNER JOIN t_pdt_productdef f ON t.fproductid=f.fid   AND IFNULL(f.fishistory,0)=0  AND f.feffect= 1 "
						   + "  left JOIN  (SELECT  b.fproductid,SUM(fbalanceqty) amount FROM t_inv_storebalance b  WHERE 1=1 "+customersql+" GROUP BY b.fproductid ) s  ON s.fproductid=t.fproductid"
						   +"   LEFT JOIN (SELECT SUM(fusedqty) fusedqty,s.fproductid  FROM t_inv_usedstorebalance s   WHERE fusedqty > 0  GROUP BY s.fproductid ) u  ON s.fproductid = u.fproductid "
						   +"   LEFT JOIN (SELECT  SUM(  CASE WHEN famount < IFNULL(fstockinqty, 0)  THEN 0  ELSE famount - IFNULL(fstockinqty, 0) END ) fmakingqty, fproductdefid  FROM t_ord_productplan WHERE faudited = 1 AND fcloseed = 0  "+customersql+" GROUP BY fproductdefid) p ON s.fproductid = p.fproductdefid  "
						   +"   LEFT JOIN t_bd_customer c ON c.fid=t.fcustomerid";
			}else{
				 sql="  SELECT  t.fid,t.fname,t.fnumber,c.fname cname,t.fcustomerid,t.fproductid,r.fname suppliername,IFNULL(amount,0) fbalanceqty ,IFNULL(fusedqty,0) fusedqty ,IFNULL(fmakingqty,0) fmakingqty FROM  t_bd_custproduct  t"
						   + "  INNER JOIN t_pdt_productdef f ON t.fproductid=f.fid   AND IFNULL(f.fishistory,0)=0  AND f.feffect= 1 "
						   + "  left JOIN  (SELECT  b.fproductid,b.fsupplierid,SUM(fbalanceqty) amount FROM t_inv_storebalance b  WHERE 1=1 "+customersql+" GROUP BY b.fproductid,b.fsupplierid ) s  ON s.fproductid=t.fproductid"
						   +"   LEFT JOIN (SELECT SUM(fusedqty) fusedqty,s.fproductid, o.fsupplierid  FROM t_inv_usedstorebalance s   LEFT JOIN t_ord_deliverorder o ON o.fid = s.fdeliverorderid    WHERE fusedqty > 0 "+customersql+" GROUP BY s.fproductid, o.fsupplierid ) u  ON s.fproductid = u.fproductid  AND s.fsupplierid = u.fsupplierid "
						   +"   LEFT JOIN (SELECT  SUM(  CASE WHEN famount < IFNULL(fstockinqty, 0)  THEN 0  ELSE famount - IFNULL(fstockinqty, 0) END ) fmakingqty, fproductdefid, fsupplierid  FROM t_ord_productplan WHERE faudited = 1 AND fcloseed = 0  "+customersql+" GROUP BY fproductdefid, fsupplierid) p ON s.fproductid = p.fproductdefid  AND s.fsupplierid = p.fsupplierid "
						   +"   inner JOIN t_sys_supplier r  ON r.fid = s.fsupplierid "
						   +"   LEFT JOIN t_bd_customer c ON c.fid=t.fcustomerid";
			}
			
			if (storebalanceDao
					.QueryExistsBySql("SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"
							+ userid + "'")) {

				
				sql+=" INNER JOIN t_bd_userrelationcustp e ON e.fcustproductid=t.fid  and e.fuserid='" + userid + "'";

			}
			sql+="   where t.feffect=1 "+customersql.replace("fcustomerid", "t.fcustomerid")+" AND (s.amount > 0 OR IFNULL(fmakingqty, 0) > 0  OR IFNULL(fusedqty, 0) > 0) ";
		}
		else{
			if(ftype!=null&&"1".equals(ftype))//1为按产品统计，0为按供应商统计
			{
			ftypesql = "select cp.fid,cp.fcustomerid,cp.fname,cp.fproductid,cp.fnumber,s.fbalanceqty * cp.radio fbalanceqty,fusedqty* cp.radio fusedqty,fmakingqty*cp.radio fmakingqty,'-' suppliername"
					+ " from ("
					+ " 	select  cp.fid,cp.feffect,cp.fcustomerid,cp.fname,cp.fnumber,"
					+ "	ifnull(pr.fproductid, cr.fproductid) fproductid,ifnull(pr.radio,1/cr.radio)  radio"
					+ "	from t_bd_custproduct cp"
					+ "	left join (select cp.fcustproductid, rn.fproductid fproductid, rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation cp ON rn.fparentid = cp.fid LEFT JOIN t_pdt_productdef f ON f.fid=rn.fproductid WHERE 1 = 1 AND IFNULL(f.fishistory, 0) = 0 AND IFNULL(f.feffect, 0) = 1 "+customersql+"group by cp.fcustproductid having count(1) = 1) cr ON cr.fcustproductid = cp.fid"
					+ "	left join (select fcustproductid, fproductid,radio from( select  pn.fcustproductid, cp.fproductid, pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation cp ON pn.fparentid = cp.fid INNER JOIN (SELECT * FROM t_pdt_productdef WHERE  IFNULL(fishistory, 0) = 0 AND IFNULL(feffect, 0) = 1 ) pd ON pd.fid=cp.fproductid where 1=1"+customersql+" group by cp.fproductid having count(1) = 1) pp group by fcustproductid having count(1) = 1) pr ON pr.fcustproductid = cp.fid  "
					+ "	where ifnull(ifnull(pr.radio,cr.radio),0 )<>0 and cp.feffect=1 "+customersql
					+ " ) cp"
//					+" left join (select s.fproductid,s.fsupplierid, sum(case when amount - ifnull(fusedqty,0) < 0 then 0 else amount - ifnull(fusedqty,0)end )fbalanceqty,sum(ifnull(fusedqty,0))fusedqty,sum(ifnull(fmakingqty,0)) fmakingqty "
					+" left join (select s.fproductid,s.fsupplierid, sum(amount) fbalanceqty,sum(ifnull(fusedqty,0))fusedqty,sum(ifnull(fmakingqty,0)) fmakingqty "
					+" from "
					//+" (select fproductid,fsupplierid, sum(fbalanceqty) amount from t_inv_storebalance where 1=1 "+customersql.replace("cp.", "") +" group by fproductid,fsupplierid) s "
				    +" (select fproductid,fsupplierid, sum(fbalanceqty) amount from t_inv_storebalance   group by fproductid,fsupplierid) s "
					+" left  join (select sum(fusedqty)fusedqty,s.fproductid,o.fsupplierid from t_inv_usedstorebalance s left join t_ord_deliverorder  o on o.fid=s.fdeliverorderid where fusedqty>0 "+customersql.replace("cp.", "") +" group by s.fproductid,o.fsupplierid)  u on s.fproductid=u.fproductid and s.fsupplierid=u.fsupplierid"
					+" left join (select  sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty, fproductdefid,fsupplierid from t_ord_productplan where faudited = 1 and fcloseed = 0 "+customersql.replace("cp.", "") +" group by fproductdefid,fsupplierid ) p on  s.fproductid=p.fproductdefid and s.fsupplierid=p.fsupplierid"
					+" where  ( s.amount > 0 or ifnull(fmakingqty, 0) > 0 or ifnull(fusedqty, 0) > 0) group by fproductid )s on s.fproductid=cp.fproductid"
					+ " where ifnull(s.fsupplierid,'')<>''  ";
			}else
			{
				ftypesql = "select cp.fid,cp.fcustomerid,cp.fname,cp.fproductid,cp.fnumber,s.fbalanceqty * cp.radio fbalanceqty,fusedqty* cp.radio fusedqty,fmakingqty*cp.radio fmakingqty,r.fname suppliername,s.fsupplierid"
						+ " from ("
						+ " 	select  cp.fid,cp.feffect,cp.fcustomerid,cp.fname,cp.fnumber,"
						+ "	ifnull(pr.fproductid, cr.fproductid) fproductid,ifnull(pr.radio,1/cr.radio)  radio"
						+ "	from t_bd_custproduct cp"
						+ "	left join (select cp.fcustproductid, rn.fproductid fproductid, rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation cp ON rn.fparentid = cp.fid LEFT JOIN t_pdt_productdef f ON f.fid=rn.fproductid where 1=1  AND IFNULL(f.fishistory, 0) = 0 AND IFNULL(f.feffect, 0) = 1 "+customersql+"group by cp.fcustproductid having count(1) = 1) cr ON cr.fcustproductid = cp.fid"
						+ "	left join (select fcustproductid, fproductid,radio from( select  pn.fcustproductid, cp.fproductid, pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation cp ON pn.fparentid = cp.fid INNER JOIN (SELECT * FROM t_pdt_productdef WHERE   IFNULL(fishistory, 0) = 0 AND IFNULL(feffect, 0) = 1) pd ON pd.fid=cp.fproductid where 1=1"+customersql+" group by cp.fproductid having count(1) = 1) pp group by fcustproductid having count(1) = 1) pr ON pr.fcustproductid = cp.fid  "
						+ "	where ifnull(ifnull(pr.radio,cr.radio),0 )<>0 and cp.feffect=1 "+customersql
						+ " ) cp"
//						+" left join (select s.fproductid,s.fsupplierid, case when amount - ifnull(fusedqty,0) < 0 then 0 else amount - ifnull(fusedqty,0)end fbalanceqty,ifnull(fusedqty,0)fusedqty,ifnull(fmakingqty,0) fmakingqty "
						+" left join (select s.fproductid,s.fsupplierid, amount fbalanceqty,ifnull(fusedqty,0)fusedqty,ifnull(fmakingqty,0) fmakingqty "
						+" from "
						+" (select fproductid,fsupplierid, sum(fbalanceqty) amount from t_inv_storebalance  group by fproductid,fsupplierid) s "
						+" left  join (select sum(fusedqty)fusedqty,s.fproductid,o.fsupplierid from t_inv_usedstorebalance s left join t_ord_deliverorder  o on o.fid=s.fdeliverorderid where fusedqty>0 "+customersql.replace("cp.", "") +" group by s.fproductid,o.fsupplierid)  u on s.fproductid=u.fproductid and s.fsupplierid=u.fsupplierid"
						+" left join (select  sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty, fproductdefid,fsupplierid from t_ord_productplan where faudited = 1 and fcloseed = 0 "+customersql.replace("cp.", "") +" group by fproductdefid,fsupplierid ) p on  s.fproductid=p.fproductdefid and s.fsupplierid=p.fsupplierid"
						+" where  ( s.amount > 0 or ifnull(fmakingqty, 0) > 0 or ifnull(fusedqty, 0) > 0) )s on s.fproductid=cp.fproductid"
						+ " left join t_sys_supplier r on r.fid=s.fsupplierid "
						+ " where ifnull(s.fsupplierid,'')<>''  ";


			}
			if (storebalanceDao
					.QueryExistsBySql("SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"
							+ userid + "'")) {

				sql = " select c.fname cname,suppliername,t.fid,t.fname,t.fnumber,t.fcustomerid,fbalanceqty,fusedqty,fmakingqty,fproductid "
						+ " from t_bd_userrelationcustp e "
						+ " left join ( "
						+ ftypesql
						+ " ) t on e.fcustproductid=t.fid "
						+ " left join t_bd_customer c on t.fcustomerid=c.fid  where "
						+ " e.fuserid = '" + userid + "' and ifnull(t.fid,'')<>''";
			} else {
				sql = " select c.fname cname,suppliername,t.fid,t.fname,t.fnumber,t.fcustomerid, fbalanceqty,fusedqty,fmakingqty,fproductid "
						+ "from ("
						+ ftypesql
						+ " ) t "
						+ "left join t_bd_customer c on t.fcustomerid=c.fid where 1=1 ";
//						+ storebalanceDao.QueryFilterByUser(request,"t.fcustomerid", null);
			}
		}
			
			ListResult result = storebalanceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
}