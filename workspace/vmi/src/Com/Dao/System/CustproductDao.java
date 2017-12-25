package Com.Dao.System;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.file.MyMultiFileUploadHelper;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Entity.System.CustRelationProduct;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Custrelation;
import Com.Entity.System.Custrelationentry;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.Productdef;
import Com.Entity.System.Useronline;
import Com.Entity.System.Userrelationcustp;
import Com.Entity.order.Productdemandfile;

@Service("CustproductDao")
public class CustproductDao extends BaseDao implements ICustproductDao {

	public static final String CUSTPRODUCT_IMG = "file/schemedesign";

	/**
	 * 函数说明：获得一条的信息CustproductD 返回值：对象
	 */
	@Override
	public Custproduct Query(String fid) {
		return (Custproduct) this.getHibernateTemplate().get(Custproduct.class,
				fid);
	}

	/**
	 * 函数说明：查询的所有信息 参数说明： 集合 返回值：
	 */
	@Override
	public List QuerySysUsercls(String hql) {
		// String sql = " FROM Products where  " + fieldname + "  like '% " +
		// value + " %' " + " ORDER BY gameNameCn " ;
		return this.getHibernateTemplate().find(hql);

	}

	@Override
	public HashMap<String, Object> ExecSave(Custproduct info) {
		HashMap<String, Object> params = new HashMap<>();
		if (info.getFid().isEmpty()) {
			info.setFid(this.CreateUUid());
			// info.setFeffect(0);
			info.setFcreatetime(new Date());
			this.saveOrUpdate(info);
		} else {
			// info.setFeffect(0);
			this.saveOrUpdate(info);
		}
		params.put("success", true);
		return params;
	}

	@Override
	public Useronline ExecCheckLogin(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Query q = getSession().createQuery(
				" FROM SysUser where fname=:fname and fpassword=:fpassword ");
		q.setString("fname", username);
		q.setString("fpassword", password);
		List<Custproduct> slist = q.list();
		if (slist.size() > 0) {
			Useronline info = new Useronline();
			info.setFid(this.CreateUUid());
			info.setFuserid(slist.get(0).getFid());
			info.setFlogintime(new Date());
			info.setFlastoperatetime(new Date());
			info.setFusername(slist.get(0).getFname());
			info.setFsessionid(request.getSession().getId());
			this.saveOrUpdate(info);
			return info;
		}
		return null;
	}

	@Override
	public boolean ExecCustrelation(JSONArray jsa) {
		// TODO Auto-generated method stub
		String fparentid = "";
		String fsql = "";
		String sql = "";
		for (int i = 0; i < jsa.size(); i++) {

			int count = ((JSONObject) jsa.get(i)).getInt("mcount");
			String productid = ((JSONObject) jsa.get(i)).getString("productid");
			String custproductid = ((JSONObject) jsa.get(i))
					.getString("custproductid");

			Custproduct custpdtinfo = Query(custproductid);
			if(custpdtinfo.getFeffect()==0){
				throw new DJException("该客户产品已禁用,不能关联产品！");
			}
			sql = "INSERT INTO t_pdt_custrelation (FID, FCUSTOMERID, FCUSTPRODUCTID) VALUES (\'%s\', \'%s\', \'%s\') ";

			List<HashMap<String, Object>> list = QueryBySql("select r.fid ,e.fid ffid from t_pdt_custrelation r  left join  t_pdt_custrelationentry e  on r.fid=e.fparentid where r.FCUSTPRODUCTID='"
					+ custproductid + "'");
			if (list.size() == 0) {
				fparentid = CreateUUid();
				fsql = String.format(sql, fparentid,
						custpdtinfo.getFcustomerid(), custproductid);
				ExecBySql(fsql);
			} else if (list.size() == 1 && list.get(0).get("ffid") == null) {
				fparentid = (String) list.get(0).get("fid");
			} else {
				return false;
			}

			// 添加单引号，原生sql的要求，但尽量避免，原生sql操作是有重大安全漏洞的
			sql = "INSERT INTO t_pdt_custrelationentry (FID, FPRODUCTID, FPARENTID, FAMOUNT) VALUES (\'%s\', \'%s\', \'%s\', %d) ";
			fsql = String
					.format(sql, CreateUUid(), productid, fparentid, count);
			ExecBySql(fsql);

			sql = "update t_bd_custproduct set Frelationed = 1,fproductid='"+productid+"' where fid = '"
					+ custproductid + "'";
			ExecBySql(sql);

		}
		return true;
	}

	@Override
	public void ExecdeteleString(String entrysql, String sql, String fparentsql) {
		// TODO Auto-generated method stub
		this.ExecBySql(entrysql);
		this.ExecBySql(sql);
		if (fparentsql.length() > 0) {
			this.ExecBySql(fparentsql);
		}
	}

	@Override
	public void ExecAddUserRelationCust(String userid, String[] tArrayofFid) {
		// TODO Auto-generated method stub
		String sql = "";
		for (int i = 0; i < tArrayofFid.length; i++) {
			sql = "SELECT fid FROM t_bd_userrelationcustp where fcustproductid = :fcustproductid and fuserid = :fuserid ;";// SELECT
																															// *

			params paramsT1 = new params();
			paramsT1.setString("fuserid", userid);
			paramsT1.setString("fcustproductid", tArrayofFid[i]);

			if (this.QueryBySql(sql, paramsT1).size() == 0) {
				Userrelationcustp info = new Userrelationcustp();
				info.setFid(this.CreateUUid());
				info.setFuserid(userid);
				info.setFcustproductid(tArrayofFid[i]);
				saveOrUpdate(info);
			}
		}
	}

	@Override
	public void ExecDeleteUserRelationCust(String userid, String fidcls) {
		// TODO Auto-generated method stub
		String sql = "delete from t_bd_userrelationcustp where  fuserid = :fuserid   and fcustproductid in ";
		sql = sql + fidcls;
		params paramsT = new params();
		paramsT.setString("fuserid", userid);
		ExecBySql(sql, paramsT);
	}

	@Override
	public String ExecBuildCusProductChilrenJson(HttpServletRequest request)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

		String fcustomerid = request.getParameter("fcustomerid");
		
		String textF = request.getParameter("value");
		if(textF!=null){
			textF = new String(textF.getBytes("iso-8859-1"),"utf-8");
		}
		
		String eastText = request.getParameter("cname");
		if(eastText!=null){
			eastText = new String(eastText.getBytes("iso-8859-1"),"utf-8");
			if(eastText.equals("所有客户")){
				eastText = "";
			}
		}
		String parentId = request.getParameter("id");

		String pageNo = request.getParameter("pageNo");

		String sql = " SELECT * FROM cusproduct_treegrid_view where 1 = 1 and feffect=1";

		if (parentId != null) {
			if (parentId.equals("-2")) {

				sql += " and parentId = '-1' ";

			} else {

				sql += " and parentId = '%s' ";

				sql = String.format(sql, parentId);

			}
		}
		if(textF !=null){
			sql +=" and cname like '%"+eastText+"%' and (fname like '%"+textF+"%' or fnumber like '%"+textF+"%')";
		}

		if (fcustomerid == null || fcustomerid.equals("")) {
			sql += QueryFilterByUser(request, "fcustomerid", null);
		} else {

			if (!fcustomerid.equals("-1")) {
				sql += " and fcustomerid='" + fcustomerid + "' ";
			} else {

				sql += QueryFilterByUser(request, "fcustomerid", null);

			}

		}

		sql = MySimpleToolsZ.getMySimpleToolsZ().buildBaseSql(request, sql,
				"fcreatetime");

		// sql += QueryFilterByUser(request, "fcustomerid", null);

		// 模糊查询
		if (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request) == MySimpleToolsZ.COMMON_SEARCH) {

			String sql2 = " SELECT * FROM cusproduct_treegrid_view where 1 = 1 and id in ( select distinct parentid FROM cusproduct_treegrid_view where 1 = 1 "
					+ MySimpleToolsZ.getMySimpleToolsZ()
							.buildMySearchBoxSQLFragment(request) + " ) ";

			sql = sql + " union " + sql2
					+ QueryFilterByUser(request, "fcustomerid", null);

		} else if (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request) == MySimpleToolsZ.TIME_SEARCH) {

			String sql2 = " SELECT * FROM cusproduct_treegrid_view where 1 = 1 and id in ( select distinct parentid FROM cusproduct_treegrid_view where 1 = 1 "
					+ MySimpleToolsZ.getMySimpleToolsZ()
							.buildDateBetweenSQLFragment(request) + " ) ";

			sql = sql + " union " + sql2
					+ QueryFilterByUser(request, "fcustomerid", null);

		}

		sql += " order by fcreatetime desc, flastupdatetime desc";
		
		// 搜索就不分页
		if (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request) == MySimpleToolsZ.NO_SEARCH) {

			int pageT = 0;

			if (pageNo != null) {

				pageT = Integer.parseInt(pageNo);

			}

			sql += String.format(" limit %s,50 ", pageT * 50 + "");

		}

		List<HashMap<String, Object>> params = QueryBySql(sql);

		String json = "{children:[" + JsonUtil.transformListToJson(params)
				+ "]}";

		return json;
	}

	@Override
	public String ExecSelectCreateAndUpdateCountForColumn(
			HttpServletRequest request) {
		// TODO Auto-generated method stub

		String sql = " SELECT * FROM t_bd_custproduct where fcreatetime BETWEEN DATE_SUB(NOW(), INTERVAL 1 Month) and NOW()  " + QueryFilterByUser(request, "fcustomerid", null);

		int createcount = QueryBySql(sql).size();

		sql = " SELECT * FROM t_bd_custproduct where flastupdatetime BETWEEN DATE_SUB(NOW(), INTERVAL 1 Month) and NOW() " + QueryFilterByUser(request, "fcustomerid", null);

		int updatecount = QueryBySql(sql).size();

		List<HashMap<String, Object>> result = new ArrayList<>();

		HashMap<String, Object> mapT = new HashMap<>();

		mapT.put("createcount", createcount);
		mapT.put("updatecount", updatecount);
		mapT.put("xaxisTip", "变化量");

		result.add(mapT);

		return JsonUtil.result(true, "", "" + result.size(), result);
	}

	@Override
	public void ExecNumberofSQL(List list) {
		// TODO Auto-generated method stub
		for (int j = 0; j < list.size(); j++) {
			String sql = list.get(j).toString();
			this.ExecBySql(sql);
		}

	}

	@Override
	public void ExecCreateCorrespondingProductRel(HttpServletRequest request,
			Custproduct custproduct) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		//新建标识为1
		if(request.getParameter("isCreate").equals("1")){
			
			if (request.getParameter("isFromBasePlatforms").equals("1")) {
				
				custproduct.setFid(CreateUUid());
				
			}
			
			// 新建产品
			Productdef product = saveProduct(custproduct);
			
			// 新建对应关系
			Custrelation custrelation = new Custrelation();
			
			custrelation.setFid(CreateUUid());
			custrelation.setFcustomerid(custproduct.getFcustomerid());
			custrelation.setFcustproductid(custproduct.getFid());
			
			saveOrUpdate(custrelation);
			
			Custrelationentry custrelationentry = new Custrelationentry();
			
			custrelationentry.setFid(CreateUUid());
			custrelationentry.setFparentid(custrelation.getFid());
			custrelationentry.setFproductid(product.getFid());
			
			custrelationentry.setFamount(1);
			
			saveOrUpdate(custrelationentry);
			
			// 反写客户产品关联状态
			custproduct.setFrelationed(1);
			custproduct.setFproductid(product.getFid());
		}
		
		ExecSave(custproduct);

	}

	private Productdef saveProduct(Custproduct custproduct) {
		Productdef product = new Productdef();

		product.setFnumber(custproduct.getFnumber());
		product.setFname(custproduct.getFname());
		product.setFcharacter(custproduct.getFspec());
		product.setForderunitid(custproduct.getForderunit());
		product.setFcharacter(custproduct.getFspec());
		product.setFcharacterid(custproduct.getFcharacterid());
		product.setFtilemodelid(custproduct.getFtilemodel());
		product.setFmaterialcode(custproduct.getFmaterial());

		product.setFdescription(custproduct.getFdescription());
		product.setFcustomerid(custproduct.getFcustomerid());
		product.setFid(CreateUUid());
		product.setFcreatetime(custproduct.getFcreatetime());
		product.setFlastupdatetime(custproduct.getFlastupdatetime());

		product.setFcreatorid(custproduct.getFcreatorid());

		saveOrUpdate(product);
		return product;
	}

	@Override
	public void DelCustProduct(String fidcls) {
		String sql = "delete from t_bd_custproduct where fid in " + fidcls;
		this.ExecBySql(sql);
		sql = "select group_concat(quote(fid)) fids from t_pdt_custrelation where fcustproductid in "+fidcls;
		HashMap<String, Object> map = (HashMap<String, Object>)this.QueryBySql(sql).get(0);
		if(map.get("fids")!=null){
			fidcls = "("+map.get("fids")+")";
			sql = "delete from t_pdt_custrelation where fid in "+fidcls;
			this.ExecBySql(sql);
			sql = "delete from t_pdt_custrelationentry where fparentid in "+fidcls;
			this.ExecBySql(sql);
		}
	}

	@Override
	public void ExecUploadCustProductImg(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		String fid = request.getParameter("fid");
		
//		MySimpleToolsZ.getMySimpleToolsZ().saveFileAndRecordItForMultiFileUpload(request, this, CUSTPRODUCT_IMG, fid);
		UploadFile.upload(request, fid);
	}

	@Override
	public void ExecDeleteCustProductAccessorys(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		
		String fids = request.getParameter("fidcls");
		
		String delConditon = "('" +  fids.replaceAll(",", "\',\'") + "')";
		
		String hql = " select fpath from Productdemandfile where fid in %s ";
		
		List<String> aPathS = QueryByHql(String.format(hql, delConditon));
		
		//delete files
		for (String aPath : aPathS) {
			
			File fT = new File(aPath);
			
			fT.deleteOnExit();
			
		}
		
		String hql2 = " delete from Productdemandfile where fid in %s  ";
		
		//删除记录
		ExecByHql(String.format(hql2, delConditon));
		
	}
	/**
	 * 启用 
	 */
	@Override
	public void ExecCustpEnabled(String fids){
		String sql;
		List<HashMap<String,Object>> list;
		for (String id : fids.split(",")) {
			sql = "select feffect,fcustomerid from t_bd_custproduct where  fid = '"
					+ id + "'";
			list = this.QueryBySql(sql);
			if((Integer)list.get(0).get("feffect")==1){
				throw new DJException("该产品已启用！");
			}
			sql = "select 1 from t_bd_custproduct where feffect =1 and fnumber = (select fnumber from t_bd_custproduct where fid ='"
					+ id + "') and fcustomerid ='"+list.get(0).get("fcustomerid")+"'";
			if (this.QueryExistsBySql(sql)) {
				throw new DJException("该产品的同一编码产品已启用！");
			}
			sql = "update t_bd_custproduct set feffect=1 where feffect = 0 and fid = '"
					+ id + "'";
			this.ExecBySql(sql);
		}
	
	}
	@Override
	public void ExecCustpDisable(String fids){
		String sql;
		List<HashMap<String,Object>> list;
		for (String id : fids.split(",")) {
			sql = "select feffect,fcustomerid from t_bd_custproduct where fid='"+id+"'";
			list = this.QueryBySql(sql);
			if((Integer)list.get(0).get("feffect")==0){
				throw new DJException("该产品已经禁用！");
			}
			sql = "select 1 from mystock where fcustproductid='"+id+"' and fcustomerid='"+list.get(0).get("fcustomerid")+"'";
			if(this.QueryExistsBySql(sql)){
				throw new DJException("已经备货的不允许禁用！");
			}
//			sql = "select 1 from t_ord_deliverapply where  fcusproductid='"+id+"' and fcustomerid='"+list.get(0).get("fcustomerid")+"'";
//			if(this.QueryExistsBySql(sql)){
//				throw new DJException("已生成要货申请的不允许禁用！");
//			}
			sql = " select c.fname cname,t.fid,t.fname,t.fnumber,t.fcustomerid,ifnull((case when fstoreqty<0 then 0 else fstoreqty end),0) fbalanceqty,ifnull(fusedqty,0) fusedqty,fmakingqty,n.fproductid "
					+ "from t_bd_custproduct t left join t_bd_customer c on t.fcustomerid=c.fid "
					+ "left join ( "
					+ "select cp.fid,(case when ifnull(cr.radio,0)>0 then b.amount/cr.radio else b.amount*ifnull(pr.radio,1) end-fusedqty) fstoreqty,fusedqty,ifnull(fmakingqty,0) fmakingqty,ifnull(cr.fproductid,pr.fproductid)  fproductid "
					+ "FROM t_bd_custproduct cp  "
					+ "left join (select r.fcustproductid,rn.fproductid fproductid,rn.famount radio from t_pdt_custrelationentry rn left join t_pdt_custrelation r on rn.fparentid = r.fid "
					+ "group by r.fcustproductid having count(1)=1) cr on cr.fcustproductid=cp.fid "
					+ "left join (select pn.fcustproductid,p.fproductid ,pn.famount radio from t_pdt_productrelationentry pn left join t_pdt_productrelation p on pn.fparentid=p.fid  "
					+ "group by pn.fcustproductid having count(1)=1) pr on pr.fcustproductid=cp.fid "
					+ "left join (select fproductid,sum(fbalanceqty) amount from t_inv_storebalance group by fproductid  "
					+ ") b on b.fproductid=ifnull(cr.fproductid,pr.fproductid) "
					+ "left join (select sum(case when fusedqty<0 then 0 else fusedqty end) fusedqty,fproductid from t_inv_usedstorebalance group by fproductid ) us on us.fproductid=ifnull(cr.fproductid,pr.fproductid) "
					+ "left join (select sum(case when famount < ifnull(fstockinqty,0)  then 0 else famount - ifnull(fstockinqty, 0) end ) fmakingqty,fproductdefid from t_ord_productplan where faudited = 1 and fcloseed = 0 group by fproductdefid) p on p.fproductdefid=ifnull(cr.fproductid,pr.fproductid) "
					+ ") n on n.fid=t.fid "
					+ "where (ifnull(fstoreqty,0)>0 or ifnull(fusedqty,0)>0 or ifnull(fmakingqty,0)>0) "
					+ " and t.fid = '"+id+"'"
					+" and t.fcustomerid='"+list.get(0).get("fcustomerid")+"'";
			if(this.QueryExistsBySql(sql)){
//				throw new DJException("我的库存中有该产品的不能禁用！");
				throw new DJException("存在待发货或在生产或有库存的产品不能禁用！");
			}
			sql = "update t_bd_custproduct set feffect=0 where feffect = 1 and fid = '"
					+ id + "'";
			this.ExecBySql(sql);
		}
	}

	@Override
	public String ExecDelFile(String ids) {
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

}
