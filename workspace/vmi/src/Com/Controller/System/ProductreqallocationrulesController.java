package Com.Controller.System;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IProductreqallocationrulesDao;
import Com.Dao.order.IFirstproductdemandDao;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Useronline;

@Controller
public class ProductreqallocationrulesController {

	private static final String CUSTOMER_IS_NOT_UNIQ = "此客户、制造商已存在";

	private static final String BASE_SINGLER_SELECT = " select tpprr.fid fid, tpprr.fcustomerid fcustomerid_fid, tpprr.fsupplierid fsupplierid_fid,tpprr.fcreatorid fcreatorid,tpprr.fcreatetime fcreatetime, tbc.fname fcustomerid_fname, tss.fname fsupplierid_fname,tsu.fname fcreatorname ,tpprr.fisstock,tpprr.fbacthstock from t_pdt_productreqallocationRules tpprr left join t_bd_customer tbc on tpprr.fcustomerid = tbc.fid left join t_sys_supplier tss on tpprr.fsupplierid = tss.fid left join t_sys_user tsu on tpprr.fcreatorid = tsu.fid ";

	private static final String BASE_SELECT = " select tpprr.fid fid, tpprr.fcustomerid fcustomerid, tpprr.fsupplierid fsupplierid,tpprr.fcreatorid fcreatorid,tpprr.fcreatetime fcreatetime, tbc.fname fcustomername, tss.fname fsuppliername,tsu.fname fcreatorname ,tpprr.fisstock,tpprr.fbacthstock  from t_pdt_productreqallocationRules tpprr left join t_bd_customer tbc on tpprr.fcustomerid = tbc.fid left join t_sys_supplier tss on tpprr.fsupplierid = tss.fid left join t_sys_user tsu on tpprr.fcreatorid = tsu.fid ";

	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IFirstproductdemandDao firstproductdemandDao;
	@Resource
	private IProductreqallocationrulesDao productreqallocationrulesDao;


	@RequestMapping("/selectProductreqallocationruleses")
	public String selectProductreqallocationruleses(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String sql = BASE_SELECT;

		ListResult result;

		reponse.setCharacterEncoding("utf-8");

		try {
			result = productreqallocationrulesDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping("/selectProductreqallocationrulesById")
	public String selectProductreqallocationrulesById(
			HttpServletRequest request, HttpServletResponse reponse)
			throws IOException {

		reponse.setCharacterEncoding("utf-8");

		String fid = request.getParameter("fid");

		String sql = BASE_SINGLER_SELECT + " where tpprr.fid = '%s' ";

		sql = String.format(sql, fid);

		ListResult result;

		try {

			result = productreqallocationrulesDao.QueryFilterList(sql, request);

			reponse.getWriter().write(JsonUtil.result(true, "", result));

		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}

	@RequestMapping(value = "/saveProductreqallocationrules")
	public String saveProductreqallocationrules(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";

		Productreqallocationrules productreqallocationrules = null;

		try {

			// 有问题，获取的是null，用老的方式进行添加
			// productreqallocationrules = (Productreqallocationrules) request
			// .getAttribute("Productreqallocationrules");

			productreqallocationrules = getProductreqallocationrules(request);

			// 新增时加入时间和创建人
			if (productreqallocationrules.getFid() == null
					|| productreqallocationrules.getFid().equals("")) {

				productreqallocationrules.setFcreatetime(new Date());

				productreqallocationrules.setFcreatorid(((Useronline) request
						.getSession().getAttribute("Useronline")).getFuserid());

			}

			HashMap<String, Object> params = productreqallocationrulesDao
					.ExecSave(productreqallocationrules);

			System.out.println(params);
			if (params.get("success") == Boolean.TRUE) {
				result = "{success:true,msg:'保存成功!'}";
			} else {
				result = "{success:false,msg:'保存失败!'}";
			}
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	private Productreqallocationrules getProductreqallocationrules(
			HttpServletRequest request) {
		// TODO Auto-generated method stub

		Productreqallocationrules productreqallocationrules;

		String fcustomerid = request.getParameter("fcustomerid");

		String fsupplierid = request.getParameter("fsupplierid");
		
		int fisstock=StringUtils.isEmpty(request.getParameter("fisstock"))?1:new Integer(request.getParameter("fisstock"));
		int fbacthstock=StringUtils.isEmpty(request.getParameter("fbacthstock"))?0:new Integer(request.getParameter("fbacthstock"));

		String fid = request.getParameter("fid");

		if (fid.equals("")) {

			verifyCustomerUniq(fcustomerid,fsupplierid,"");

			productreqallocationrules = new Productreqallocationrules(fid);

		} else {

			productreqallocationrules = productreqallocationrulesDao.Query(fid);

			verifyCustomerUniqUpdate(fcustomerid, productreqallocationrules);

		}

		productreqallocationrules.setFcustomerid(fcustomerid);
		productreqallocationrules.setFsupplierid(fsupplierid);
		productreqallocationrules.setFisstock(fisstock);
		productreqallocationrules.setFbacthstock(fbacthstock);

		return productreqallocationrules;
	}

	private void verifyCustomerUniqUpdate(String fcustomerid,
			Productreqallocationrules productreqallocationrules) {
		// TODO Auto-generated method stub

		// 不同时才进行校验，
		if (!fcustomerid.equals(productreqallocationrules.getFcustomerid())) {

			verifyCustomerUniq(fcustomerid,productreqallocationrules.getFsupplierid(),productreqallocationrules.getFid());

		}

	}

	private void verifyCustomerUniq(String fcustomerid,String fsupplierid,String fid) {
		// TODO Auto-generated method stub

		String hql = " select count(*) from Productreqallocationrules p where p.fcustomerid = '%s' and p.fsupplierid = '%s' " +(fid.isEmpty()?"":(" and fid <>'"+fid+"'"));

		hql = String.format(hql, fcustomerid,fsupplierid);

		Long count = (Long) productreqallocationrulesDao.QueryByHql(hql).get(0);

		// 不唯一
		if (count != 0) {

			throw new DJException(CUSTOMER_IS_NOT_UNIQ);

		}

	}

	@RequestMapping("/deleteProductreqallocationrules")
	public String deleteProductreqallocationrules(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";

		String fidcls = request.getParameter("fidcls");

		fidcls = "('" + fidcls.replace(",", "','") + "')";

		try {
			String hql = "Delete from Productreqallocationrules where fid in "
					+ fidcls;

			productreqallocationrulesDao.ExecByHql(hql);

			result = "{success:true,msg:'删除成功!'}";

			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";
			log.error("DelUserList error", e);
		}

		reponse.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/allotruletoproductdemand")
	public String allotruletoproductdemand(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		try {
			String fidcls = request.getParameter("fidcls");
			String productDemandid[] = fidcls.split(",");
			for(String fid : productDemandid){
				if(firstproductdemandDao.closeProductDemand(fid, productreqallocationrulesDao)){
					throw new DJException("已关闭的需求不能分配！");
				}
			}
			fidcls="('"+fidcls.replace(",","','")+"')";
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			
			String sql="select fcustomerid from t_ord_firstproductdemand where ifnull(falloted,0)=0 and ifnull(fcustomerid,'')<>'' and fid in " +fidcls;
			List<HashMap<String,Object>> clist=productreqallocationrulesDao.QueryBySql(sql);
			if(clist.size()<=0)
			{
				throw new DJException("没有可以分配的需求,请检查是否所选的已经分配或客户为空");
			}
			for(int i=0;i<clist.size();i++)
			{
				productreqallocationrulesDao.ExecAllot(clist.get(i).get("fcustomerid").toString(),userid);
			}

			reponse.getWriter().write(JsonUtil.result(true, "分配成功！", "", ""));
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "分配失败," + e.getMessage(), "", ""));
		}

		reponse.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/GetSupplierByProductdemandList")
	public String GetSupplierByProductdemandList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql="select fcustomerid from t_ord_firstproductdemand where ifnull(falloted,0)=0 and ifnull(fcustomerid,'')<>'' and fid = '" +request.getParameter("productdemandid")+"'";
			List<HashMap<String,Object>> clist=productreqallocationrulesDao.QueryBySql(sql);
			if(clist.size()<=0)
			{
				throw new DJException("没有可以分配的需求,请检查是否所选的已经分配或客户为空");
			}
			String result = "";
			sql = "select fid,fname FROM t_sys_supplier " +
					"where fid in (select fsupplierid from t_pdt_productreqallocationrules where fcustomerid = '"+clist.get(0).get("fcustomerid").toString()+"') ";
			ListResult lresult;

			lresult = productreqallocationrulesDao.QueryFilterList(sql, request);
			List<HashMap<String, Object>> sList = lresult.getData();
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping("/manualAllotruletoproductdemand")
	public String manualAllotruletoproductdemand(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			String productdemandid = request.getParameter("productdemandid");
			String fsupplierid = request.getParameter("fsupplierid");;
			String productDemandid[] = productdemandid.split(",");
			for(String fid : productDemandid){
				if(firstproductdemandDao.closeProductDemand(fid, productreqallocationrulesDao)){
					throw new DJException("已关闭的需求不能分配！");
				}
			}
			String sql="select fcustomerid from t_ord_firstproductdemand where ifnull(falloted,0)=0 and ifnull(fcustomerid,'')<>'' and isfauditor=1 and fid = '" +productdemandid+"'";
			List<HashMap<String,Object>> clist=productreqallocationrulesDao.QueryBySql(sql);
			if(clist.size()<=0)
			{
				throw new DJException("没有可以分配的需求,请检查是否所选的已经分配或客户为空");
			}
			if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(fsupplierid))
			{
				productreqallocationrulesDao.ExecCreateMessageInfo(" select f.fid,f.fname,c.fname cname from t_ord_firstproductdemand f  inner join t_bd_customer c on c.fid=f.fcustomerid where f.fid='"+ productdemandid+"' ");
			}
			productreqallocationrulesDao.ExecBySql("update  t_ord_firstproductdemand  set falloted=1,fallotor='"+userid+"',fallottime=now(),fsupplierid='"+fsupplierid+"',fstate='已分配' where fid='"+ productdemandid+"' ");

			response.getWriter().write(JsonUtil.result(true, "分配成功！", "", ""));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, "分配失败," + e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/unallotruletoproductdemand")
	public String unallotruletoproductdemand(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
	
		try {
			String fidcls = request.getParameter("fidcls");
			String[] productDemandid = fidcls.split(",");
			fidcls="('"+fidcls.replace(",","','")+"')";
			for(String fid : productDemandid){
				if(firstproductdemandDao.closeProductDemand(fid, productreqallocationrulesDao)){
					throw new DJException("已经关闭的需求不能取消分配！");
				}
			}
			String sql="select fid from t_ord_firstproductdemand where ifnull(falloted,0)=1 and ifnull(freceived,0)=0 and fid in " +fidcls;
			List<HashMap<String,Object>> clist=productreqallocationrulesDao.QueryBySql(sql);
			if(clist.size()<=0)
			{
				throw new DJException("已经接收的不能取消分配！");
			}

			 sql = "update  t_ord_firstproductdemand  set falloted=0,fallotor='',fallottime=null,fstate='已发布' where ifnull(falloted,0)=1 and fid in "+ fidcls ;

			productreqallocationrulesDao.ExecBySql(sql);

			reponse.getWriter().write(JsonUtil.result(true, "取消分配成功！", "", ""));
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "取消分配失败," + e.getMessage(), "", ""));
		}

		reponse.getWriter().write(result);
		return null;
	}
	@RequestMapping("getproductdemandOfallotList")
	public void getproductdemandOfallotList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sql = "SELECT cus.fname custname ,f.fid,f.fname ,f.fnumber,u1.fname AS fcreatid,f.fcreatetime,u2.fname AS freceiver,f.freceivetime,u3.fname AS fauditorid,f.fauditortime,date_format(f.foverdate,'%Y-%m-%d %H:%i') foverdate,"
				+" f.fcostneed,f.fiszhiyang,f.fdescription ,f.isfauditor,f.freceived,f.falloted,f.fsupplierid,ifnull(s.fname,'')  fsuppliername FROM `t_ord_firstproductdemand` f LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid"
				+" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid LEFT JOIN t_sys_supplier s on s.fid=f.fsupplierid left join t_bd_customer cus on f.fcustomerid=cus.fid where f.isfauditor=1";
		try {
			ListResult list = productreqallocationrulesDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	
	@RequestMapping("getunallotTraitidList")
	public void getunallotTraitidList(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String sql = "select e.fid,e.fentryamount,e.fcharacter,e.frealamount,e.fallot,d.fname dname,f.fname fname from  t_ord_schemedesignentry e left join t_ord_schemedesign d on d.fid=e.fparentid inner join t_ord_firstproductdemand f on f.fid=d.ffirstproductid where e.fallot<>1 and fconfirmed=1 " + productreqallocationrulesDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");;
		String sql = "select e.fid,e.fentryamount,e.fcharacter,e.frealamount,e.fallot,d.fname dname,f.fname fname from  t_ord_schemedesignentry e left join t_ord_schemedesign d on d.fid=e.fparentid inner join t_ord_firstproductdemand f on f.fid=d.ffirstproductid where  fconfirmed=1 " + productreqallocationrulesDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");;

		try {
			ListResult list = productreqallocationrulesDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	
	
	@RequestMapping("/allotTraits")
	public String allotTraits(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		synchronized (this.getClass()){
		String fids = request.getParameter("fidcls");
		fids="('"+fids.replace(",","','")+"')";
		int ftype=Integer.parseInt(request.getParameter("ftype").toString());//0为批量 1为单个操作
		int realamount = new Integer(request.getParameter("frealamount"));
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			
			String sql = "select 0 as eftype, e.fid efid,e.fentryamount,e.fentryamount-e.frealamount frealamount,d.* from  t_ord_schemedesignentry e left join t_ord_schemedesign d on d.fid=e.fparentid where e.fallot<>1 and fconfirmed=1 and e.fid in "+fids;
			List<HashMap<String,Object>> list= productreqallocationrulesDao.QueryBySql(sql);
			if(list.size()>0)
			{
				for(int i=0;i<list.size();i++){
				if(ftype==1)
				{
					String farrivetime=request.getParameter("farrivetime");
					int amount=new Integer(list.get(0).get("frealamount").toString());
					if(realamount>amount)
					{
						throw new DJException("生成失败,数量大于可生成数量！");
					}
					else if(realamount<amount)
					{
						list.get(0).put("eftype", 1);
						list.get(0).put("frealamount", realamount);
					}
					list.get(0).put("foverdate",farrivetime);
				}
					sql=" select flinkman,fphone flinkphone,fname faddress,fid faddressid from t_bd_address  where fcustomerid='"+list.get(i).get("fcustomerid").toString()+"'  limit 1";
					List<HashMap<String,Object>> addlist=productreqallocationrulesDao.QueryBySql(sql);
					if(addlist.size()==0)
					{
						if(ftype==1)
						{
						throw new DJException("没有与该客户匹配的地址");
						}else
						{
							continue;
						}
					}
					
					sql="select p.fnumber,p.forderid,p.fid pid,s.fid sid,s.forderentryid from t_ord_productplan p inner join t_inv_storebalance  s on s.fproductplanid=p.fid where fschemedesignid='"+list.get(i).get("fid").toString()+"' and s.ftraitid='"+list.get(i).get("efid").toString()+"' limit 1";
					List<HashMap<String,Object>> slist=productreqallocationrulesDao.QueryBySql(sql);
					if(slist.size()==0)
					{
						if(ftype==1)
						{
						throw new DJException("没有对应的方案订单信息");
						}else
						{
							continue;
						}
					}
					productreqallocationrulesDao.ExecTraitAllot(list.get(i), userid,addlist.get(0),slist.get(0));
				}
				
			}else
			{
				throw new DJException("不存在需要分配的记录！");
			}
			result = JsonUtil.result(true,"分配成功!", "", "");

		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		}
		return null;

	}
	
	
	@RequestMapping("/unallotTraits")
	public String unallotTraits(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		synchronized (this.getClass()){
		String fids = request.getParameter("fidcls");
		fids="('"+fids.replace(",","','")+"')";
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			
//			String sql = "select fnumber,fid FROM t_ord_deliverorder where (fouted = 1  or fimporteas=1 or fassembleQty>0)  and fdeliversId in "+fids;
			String sql=" select o.fnumber,o.fid,d.fnumber dnumber FROM t_ord_deliverorder o left join t_ord_delivers d on  d.fid=o.fdeliversId where (o.fouted = 1  or o.fimporteas=1 or o.fassembleQty>0)  and o.fdeliversId in "+fids;
			List<HashMap<String,Object>> list= productreqallocationrulesDao.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException("编号"+list.get(0).get("dnumber")+"的配送单:"+list.get(0).get("fnumber")+"已发货或已部分提货或已汇入系统不能取消生成！");
			}
			
			sql=" select d.ftraitid,e.frealamount-sum(d.famount) frealamount,e.frealamount lastnum,n.ffirstproductid from t_ord_delivers d "
			+" inner join t_ord_schemedesignentry e on e.fid= d.ftraitid " +
			" inner join t_ord_schemedesign n on n.fid=e.fparentid where d.fid in "+fids;
			list= productreqallocationrulesDao.QueryBySql(sql);
			if(list.size()<0)
			{
				throw new DJException("特性数据或要货数据有误");

			}
			int frealamount=new Integer(list.get(0).get("frealamount").toString());
			int lastnum=new Integer(list.get(0).get("lastnum").toString());
			String ftraitid=list.get(0).get("ftraitid").toString();
			String ffirstproductid=list.get(0).get("ffirstproductid").toString();
			if(lastnum<=0){
				throw new DJException("生成数量有误");
			}
			if(frealamount<0)
			{
				throw new DJException("生成的要货数量大于特性实际数量,不能取消");
			}
			
			sql="select d.fid fdeliversid,r.fdeliverappid from t_ord_delivers  d"
				+" left join t_ord_deliverratio r on r.fdeliverid=d.fid"
				+" where d.fid in "+fids;
			list= productreqallocationrulesDao.QueryBySql(sql);
			
			productreqallocationrulesDao.ExecTraitunAllot(list,frealamount,ftraitid,ffirstproductid);
		
			result = JsonUtil.result(true,"取消生成成功!", "", "");

		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		}
		return null;

	}
	
}
