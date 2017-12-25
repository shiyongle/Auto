package Com.Controller.System;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.Excel.ExcelUtils;
import Com.Dao.System.IAddressDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustRelationAdressDao;
import Com.Dao.System.ICustomer;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Customer;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;



@Controller
public class CustomerController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private ICustomer customerDao;
	
	@Resource
	private ICustRelationAdressDao CustRelationAdressDao;
	@Resource
	private IBaseSysDao baseSysDao;
	@Resource
	private IAddressDao AddressDao;
	@RequestMapping("/GetCustomerList")
	public String GetCustomerList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select fschemedesign,fid,fname,fnumber,fmnemoniccode,findustryid,faddress,fisinternalcompany, ftxregisterno,fbizregisterno,fartificialperson,fusedstatus,DATE_FORMAT(fcreatetime,'%Y-%m-%d %H:%i') fcreatetime FROM t_bd_customer where 1=1 "+customerDao.QueryFilterByUser(request, "fid", null);
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = customerDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;

	}
	
	@RequestMapping("/GetCustomerListByUserId")
	public String GetCustomerListByUserId(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql;
		if(!baseSysDao.isAdministrator(userid)){
			sql = "select c.fid,c.fname,c.fnumber,c.fmnemoniccode,c.findustryid,c.faddress,c.fisinternalcompany,c.ftxregisterno,c.fbizregisterno,c.fartificialperson,c.fusedstatus,c.fcreatetime FROM t_bd_customer c ,t_bd_usercustomer u where u.FCUSTOMERID = c.FID and u.FUSERID = '"+userid+"'";
		}else{
			sql = "select c.fid,c.fname,c.fnumber,c.fmnemoniccode,c.findustryid,c.faddress,c.fisinternalcompany,c.ftxregisterno,c.fbizregisterno,c.fartificialperson,c.fusedstatus,c.fcreatetime FROM t_bd_customer c";
		}
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = customerDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;

	}
	
	@RequestMapping(value="/SaveCustomer")
	public  String SaveCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String result = "";
	Customer cinfo=null;
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	String fid=request.getParameter("fid");
	if(fid!=null&&!"".equals(fid))
	{
		cinfo=customerDao.Query(fid);
		cinfo.setFlastupdatetime(new Date());
		cinfo.setFlastupdateuserid(userid);
		
	}else
	{
		 cinfo=new Customer();
		 cinfo.setFid(fid);
			cinfo.setFcreatorid(userid);
			cinfo.setFcreatetime(new Date());
			
		
	}
	cinfo.setFname(request.getParameter("fname"));
	cinfo.setFnumber(request.getParameter("fnumber"));
	cinfo.setFsimplename(request.getParameter("fsimplename"));
	cinfo.setFforeignname(request.getParameter("fforeignname"));
	String fmnemoniccode=request.getParameter("fmnemoniccode");
	cinfo.setFmnemoniccode(request.getParameter("fmnemoniccode"));
	cinfo.setFbizregisterno(request.getParameter("fbizregisterno"));
	cinfo.setFbusilicence(request.getParameter("fbusilicence"));
	cinfo.setFbusiexequatur(request.getParameter("fbusiexequatur"));
	cinfo.setFgspauthentication(request.getParameter("fgspauthentication"));
	cinfo.setFtxregisterno(request.getParameter("ftxregisterno"));
	cinfo.setFartificialperson(request.getParameter("fartificialperson"));
	cinfo.setFindustryid(request.getParameter("findustryid"));
	cinfo.setFbarcode(request.getParameter("fbarcode"));
	cinfo.setFcountryid(request.getParameter("fcountryid"));
	cinfo.setFprovince(request.getParameter("fprovince"));
	cinfo.setFcityid(request.getParameter("fcityid"));
	cinfo.setFregionid(request.getParameter("fregionid"));
	cinfo.setFaddress(request.getParameter("faddress"));
	String fisinternalcompany=request.getParameter("fisinternalcompany");
	String fschemedesign = request.getParameter("fschemedesign");
	cinfo.setFdescription(request.getParameter("fdescription")==null?"":request.getParameter("fdescription"));
	if(fschemedesign!=null&&!"true".equals(fschemedesign))
	{
		cinfo.setFschemedesign(1);
	}else
	{
		cinfo.setFschemedesign(0);
	}
	if(fisinternalcompany!=null&&!"true".equals(fisinternalcompany))
	{
		cinfo.setFisinternalcompany(1);
	}else
	{
		cinfo.setFisinternalcompany(0);
	}
	cinfo.setFusedstatus(0);
	
	HashMap<String, Object> params = customerDao.ExecSave(cinfo);
	System.out.println(params);
	if (params.get("success") == Boolean.TRUE) {
		result = "{success:true,msg:'客户保存成功!'}";
		if(customerDao.QueryExistsBySql("select 1 from t_sys_supplier where fid = '"+fid+"'")){
//			setSupplier(cinfo);//修改客户时 已经导入到制造商资料的要同步
			Customer cu = cinfo;
			String sql = String.format("update t_sys_supplier set fid='%s',fnumber='%s',fname='%s',fartificialperson='%s',ftaxregisterno='%s',fmnemoniccode='%s'," +
					"faddress='%s',fsimplename='%s',fforeignname='%s',fbizregisterno='%s',fbusilicence='%s',fbusiexequatur='%s',fgspauthentication='%s'," +
					"fbarcode='%s',fcountry='%s',fusedstatus=%s where fid ='%s'",cu.getFid(),cu.getFnumber(),cu.getFname(),cu.getFartificialperson(),cu.getFtxregisterno(),
					cu.getFmnemoniccode(),cu.getFaddress(),cu.getFsimplename(),cu.getFforeignname(),cu.getFbizregisterno(),cu.getFbusilicence(),cu.getFbusiexequatur(),
					cu.getFgspauthentication(),cu.getFbarcode(),cu.getFcountryid(),cu.getFusedstatus()==null?0:cu.getFusedstatus(),cu.getFid());
			customerDao.ExecBySql(sql);
		}
	}else
	{
	result="{success:false,msg:'客户保存失败!'}";
	}
	}
	catch(Exception e)
	{
		result = "{success:false,msg:'"+ e.toString() + "'}";
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}

	
	@RequestMapping("/DelCustomerList")
	public String DelCustomerList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		
		if(DataUtil.isforeignKeyConstraintLegal("t_ord_delivers", "fcustomerid", customerDao, fidcls)) {
			try {
				String hql = "Delete FROM Customer where fid in " + fidcls;
				customerDao.ExecByHql(hql);
					result = "{success:true,msg:'删除成功!'}";
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = "{success:false,msg:'" + e.toString().replaceAll("'", "")+ "'}";
				log.error("DelUserList error", e);
			}
		} else {
			result = "{success:false,msg:'" + "违法外键约束，本实体已被其他地方引用" + "'}";
		}
		
			
			
		reponse.getWriter().write(result);
		return null;
	}
	@RequestMapping("/getCustomer")
	public String getCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
		String sql = "select fschemedesign,fid, fname,fnumber,fdescription, fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcountryid,fcityid, fprovince,fregionid,findustryid,fartificialperson,fbizregisterno,fisinternalcompany,ftxregisterno,fbarcode,fmnemoniccode,fbusilicence,fbusiexequatur,fgspauthentication,fforeignname,faddress,fusedstatus,fsimplename from t_bd_customer where fid = '"
				+ request.getParameter("fid") +"'";
		List<HashMap<String, Object>> sList = customerDao.QueryBySql(sql);
		reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
			
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	@RequestMapping("/GetCountryAll")
	public String GetCountryAll(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String sql  = " select fid,fname FROM t_bd_country where fdeletedstatus=1 ";
		 List<HashMap<String, Object>> sList = customerDao.QueryBySql(sql);
		reponse.setCharacterEncoding("utf-8");

		reponse.getWriter().write(JsonUtil.result(true,"",customerDao.QueryCountBySql(sql),sList));
		return null;

	}
	@RequestMapping("/GetProvice")
	public String GetProvice(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "  select fid,fname FROM t_bd_province where fdeletedstatus=1 ";
		String fcountryid=request.getParameter("fcountryid");
		if(fcountryid!=null&&!"".equals(fcountryid))
		{
			sql += " and fcountryid='"+fcountryid+"'";
		}
		List<HashMap<String, Object>> sList = customerDao.QueryBySql(sql);
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true,"","",sList));
		return null;

	}
	
	@RequestMapping("/GetCity")
	public String GetCity(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "  select fid,fname FROM t_bd_city where fdeletedstatus=1 ";
		String fprovinceid=request.getParameter("fprovinceid");
		if(fprovinceid!=null&&!"".equals(fprovinceid))
		{
			sql += " and fprovinceid='"+fprovinceid+"'";
		}
		List<HashMap<String, Object>> sList = customerDao.QueryBySql(sql);
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true,"","",sList));
		return null;

	}
	
	@RequestMapping("/GetRegion")
	public String GetRegion(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "  select fid,fname FROM t_bd_region where fdeletedstatus=1 ";
		String fcityid=request.getParameter("fcityid");
		if(fcityid!=null&&!"".equals(fcityid))
		{
			sql += " and fcityid='"+fcityid+"'";
		}
		List<HashMap<String, Object>> sList = customerDao.QueryBySql(sql);
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true,"","",sList));
		return null;

	}
	

	@RequestMapping("/AddCustomer")
	public String AddCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true,"","",""));
		return null;

	}
	
	@RequestMapping("/GetCustomerTree")
	public String GetCustomerTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fname = request.getParameter("fname");
		if(!StringUtils.isEmpty(fname)){
			fname = JsonUtil.decodeUnicode(fname);
		}
		String result="{children:[";
		String sql = "select fid id,fname text,1 isleaf  FROM t_bd_customer where "+(StringUtils.isEmpty(fname)?"1=1 ":"fname like '%"+fname+"%' ")+customerDao.QueryFilterByUser(request, "fid", null) + " limit 0,30";
		List<HashMap<String, Object>>  sList = customerDao.QueryBySql(sql);
		result+=JsonUtil.List2Json(sList)+"]}";
		reponse.getWriter().write(result);

		return null;

	}
	@RequestMapping("/SearchCustomerNode")
	public String SearchCustomerNode(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		params p=new params();
		
		String fname=request.getParameter("fname");
		String sql = "select fid id FROM t_bd_customer where fname like :fname "+customerDao.QueryFilterByUser(request, "fid", null)+" order by fid desc ";
		p.put("fname", "%"+fname+"%");
		List<HashMap<String, Object>>  sList = customerDao.QueryBySql(sql,p);
		reponse.setCharacterEncoding("utf-8");
		if(sList.size()<1)
		{
			reponse.getWriter().write(JsonUtil.result(false,"不存在该客户",sList.size()+"",sList));
		}
		else{
		reponse.getWriter().write(JsonUtil.result(true,"",sList.size()+"",sList));
		}

		return null;

	}
	
	
	
	@RequestMapping(value = "/customertoexcel")
	public String customertoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select fid,fname 名称 ,fnumber 编码 ,fmnemoniccode 助记码,findustryid 行业 ,faddress 地址,fisinternalcompany 是否内部公司, ftxregisterno 税务登记号,fbizregisterno 工商注册号,fartificialperson 法人代表 ,fusedstatus 状态,fcreatetime 创建时间 FROM t_bd_customer where 1=1 "+customerDao.QueryFilterByUser(request, "fid", null);
			ListResult result = customerDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/AddCustRelationAdress")
	public void AddCustRelationAdress(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		try {
			
			String fid = request.getParameter("fidcls");
			fid = "('"+fid.replace(",", "','")+"')";
			String[] fids = DataUtil.InStrToArray(fid);
			String id = request.getParameter("myobjectid");
			CustRelationAdressDao.ExecAddCustRelationAdress(fids,id);
			response.getWriter().write(JsonUtil.result(true,"关联成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	@RequestMapping(value = "/DelCustRelationAdress")
	public void DelCustRelationAdress(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fidcls");
			fid = "('"+fid.replace(",", "','")+"')";
			String id = request.getParameter("myobjectid");
			String sql = "delete from t_bd_custrelationadress where fcustomerid='%s' and faddressid in %s";
			sql = String.format(sql, id,fid);
			CustRelationAdressDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"取消成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	@RequestMapping(value = "/getCustRelationAdressList")
	public void getCustRelationAdressList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT IFNULL(c.fname,'') AS customerName,tba.fcustomerid,tba.fid,tba.fcreatorid,"
					+" tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,"
					+" IFNULL(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,"
					+" IFNULL(tba.fpostalcode,'') fpostalcode,"
					+" IFNULL(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime FROM t_bd_custrelationadress ca LEFT JOIN t_bd_Address tba "
					+" ON tba.fid=ca.faddressid  LEFT JOIN t_bd_customer c ON c.fid=ca.fcustomerid where 1=1";
			ListResult list = CustRelationAdressDao.QueryFilterList(sql,request);
			response.getWriter().write(JsonUtil.result(true,"",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
	}
	@RequestMapping("/GetAddressLists")
	public String GetAddressLists(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fcustomerid");
		// String sql =
		// "select fid,fcreatorid,flastupdateuserid,fcontrolunitid,fdetailaddress,fnumber,fcountryid,fcityidid,femail,flinkman,fphone,fname,fprovinceid,fdistrictidid,fpostalcode,ffax,fcreatetime,flastupdatetime from t_bd_Address ";
		String sql = "select DISTINCT ifnull(c.fname,'') as customerName,tba.fcustomerid,tba.fid,tba.fcreatorid," +
				"tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber," +
				"ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid," +
				"tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode," +
				"ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba " +
				"left join t_bd_customer c on c.fid=tba.fcustomerid where tba.fid NOT IN "
				+"(SELECT faddressid FROM t_bd_custrelationadress ca WHERE ca.fcustomerid = '"+fid+"')" ;
    
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = AddressDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "",result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	/*
	 * 获取当前用户对应的客户是否确认方案
	 */
	@RequestMapping("/getCustomerSchemedesign")
	public void getCustomerSchemedesign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String result = JsonUtil.result(false, "", "", "");
		try {
			String sql = " select c.fid,c.fschemedesign  from t_bd_usercustomer u left join t_bd_customer c on c.fid=u.fcustomerid where u.fuserid='"+userid+"'"+
						" union SELECT cu.fid,cu.fschemedesign  FROM t_bd_customer cu LEFT JOIN t_bd_rolecustomer c ON cu.fid=c.fcustomerid  LEFT JOIN t_sys_userrole r ON r.froleid = c.froleid  where r.fuserid='"+userid+"'";
			List<HashMap<String,Object>> list = customerDao.QueryBySql(sql);
			for(HashMap<String,Object> customer : list){
				if(customer.get("fschemedesign")==null||customer.get("fschemedesign")==""){
					continue;
				}
				if(1==(Integer)customer.get("fschemedesign")){
					result = JsonUtil.result(true, "", "", "");
				}
			}
			response.getWriter().write(result);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	@RequestMapping("/getCustomerDescription")
	public void getCustomerDescription(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = JsonUtil.result(false, "", "", "");
		try {
			String sql = " select fdescription from t_bd_customer where 1=1 "+customerDao.QueryFilterByUser(request,"fid",null);
			List<HashMap<String,Object>> list = customerDao.QueryBySql(sql);
			result = JsonUtil.result(true, "", ""+list.size(), list);

			response.getWriter().write(result);
		} catch (DJException e) {
			response.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	@RequestMapping("setCustomerDescription")
	public void setCustomerDescription(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String customerid = baseSysDao.getCurrentCustomerid(request);
			String description = request.getParameter("description");
			String sql = "update t_bd_customer set fdescription = :description where fid = :fid";
			params p = new params();
			p.put("fid", customerid);
			p.put("description",description);
			customerDao.ExecBySql(sql, p);
			String result = JsonUtil.result(true, "操作成功！", "","");
			response.getWriter().write(result);
		} catch (DJException e) {
			response.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	@RequestMapping("setCustomerToSupplier")
	public void setCustomerToSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		try {
			fids = "'"+fids.replace(",", "','")+"'";
			String sql = "select 1 from t_sys_supplier where fid in("+fids+")";
			if(customerDao.QueryExistsBySql(sql)){
				throw new DJException("客户信息已经导入，不能重复导入！");
			}
			String hql = "from Customer where fid in ("+fids+")";
			List<Customer> list = customerDao.QueryByHql(hql);
			for(Customer customer : list){
				
				setSupplier(customer);
			}
			response.getWriter().write(JsonUtil.result(true, "导入成功！", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	public void setSupplier(Customer cu){
		try {
			Supplier su = new Supplier();
			su.setFid(cu.getFid());
			su.setFnumber(cu.getFnumber());//编码
			su.setFname(cu.getFname());//名称
			su.setFartificialperson(cu.getFartificialperson());//法人代表
			su.setFtaxregisterno(cu.getFtxregisterno());//税务登记号
			su.setFmnemoniccode(cu.getFmnemoniccode());//助记码
			su.setFaddress(cu.getFaddress());//地址
			su.setFsimplename(cu.getFsimplename());//简称
			su.setFforeignname(cu.getFforeignname());//外文名称
			su.setFbizregisterno(cu.getFbizregisterno());//工商注册号
			su.setFbusilicence(cu.getFbusilicence());//营业执照
			su.setFbusiexequatur(cu.getFbusiexequatur());//经营许可证
			su.setFgspauthentication(cu.getFgspauthentication());//GSP认证
			su.setFbarcode(cu.getFbarcode());//条形码
			su.setFcountry(cu.getFcountryid());//国家
			su.setFcreatetime(cu.getFcreatetime());
			su.setFlastupdatetime(cu.getFlastupdatetime());
			su.setFusedstatus(cu.getFusedstatus()==null?0:cu.getFusedstatus());//状态
			customerDao.saveOrUpdate(su);
		} catch (DJException e) {
			// TODO: handle exception
		}
	}
	@RequestMapping("IsSupplier")
	public void IsSupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fids = request.getParameter("fids");
			fids = "'"+fids.replace(",", "','")+"'";
			String sql = "select 1 from t_sys_supplier where fid in("+fids+")";
			if(customerDao.QueryExistsBySql(sql)){
				throw new DJException("客户信息已经导入，不能重复导入！");
			}
			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	@RequestMapping("getCustomerBysupplier")
	public void getCustomerBysupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "select c.fid,c.fname,c.fnumber,c.findustryid,c.faddress,c.fisinternalcompany from t_bd_customer c left join t_pdt_productreqallocationrules p on p.fcustomerid=c.fid where 1=1"+customerDao.QueryFilterByUser(request, null, "p.fsupplierid");
			ListResult result = customerDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	/**
	 * 客户导入模版下载
	 */
	@RequestMapping("downloadCustomerExcel")
	public void downloadCustomerExcel(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String path = "D:\\tomcat\\客户导入模版.xls";
		String name = path.substring(path.lastIndexOf("\\")+1);
			InputStream in = null;
			try {
				in = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				throw new DJException("此附件文件不存在，无法下载！");
			}
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(name.getBytes("UTF-8"),"iso-8859-1")+ "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
	}
	
	//客户导入
	@RequestMapping("saveUploadCustomerExcelData")
	public String saveUploadCustomerExcelData(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		try {
			int len = customerDao.saveCustomerExcel(request);
			response.getWriter().write(JsonUtil.result(true, "操作成功，共导入"+len+"条数据！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	//我的客户
	@RequestMapping("getMyCustomersList")
	public String getMyCustomersList(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		try {
			String sql = "SELECT c.ffax,c.fid,c.fname,c.flinkman,c.fphone,c.fartificialpersonphone,c.fcreatetime,c.fisinvited,c.fdescription,ad.fname addressname FROM `t_bd_customer` c LEFT JOIN (SELECT fid,fcustomerid,faddressid FROM `t_bd_custrelationadress`  cu  GROUP BY cu.`fcustomerid` HAVING COUNT(cu.fid)<=1 ) cust ON c.fid=cust.fcustomerid LEFT JOIN t_pdt_productreqallocationrules sup ON sup.fcustomerid=c.fid left join t_bd_address ad on ad.fid= cust.faddressid where 1=1 "+baseSysDao.QueryFilterByUser(request, null, "sup.fsupplierid");
			request.setAttribute("djsort", "c.fcreatetime desc");
			ListResult result = customerDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	
	//查找是否存在客户
	@RequestMapping("queryMyCustomerByName")
	public String queryMyCustomerByName(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		try {
			String fname = request.getParameter("cname");
			String result = "";
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
			String sql = "SELECT c.fid,pc.`fsupplierid` FROM `t_bd_customer` c LEFT JOIN t_pdt_productreqallocationrules pc ON pc.`fcustomerid`=c.`fid` WHERE c.fname='"+fname+"'";
			List<HashMap<String,Object>> list= customerDao.QueryBySql(sql);
			if(list.size()>0){
				for(int i = 0;i<list.size();i++){
					if(fsupplierid.equals(list.get(i).get("fsupplierid"))){
						throw new DJException("该客户已存在!");
					}
				}
				throw new DJException("系统已存在该客户，请联系平台处理！<br/>(联系电话：0577-85391777)");
			}
			response.getWriter().write(JsonUtil.result(true, "成功","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	//我的客户保存
	@RequestMapping("saveOrUpdateCustomer")
	public void saveOrUpdateCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			customerDao.saveOrUpdateMyCustomer(request);
			response.getWriter().write(JsonUtil.result(true, "保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	//我的地址保存
	@RequestMapping("saveOrUpdateAddress")
	public void saveOrUpdateAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String customerid = request.getParameter("customerid");
			customerDao.saveOrUpdateAddress(request,customerid);
			response.getWriter().write(JsonUtil.result(true, "保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	//获取上传的文件信息
	@RequestMapping("getMyCustomerFile")
	public void getMyCustomerFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select fid,fname,fpath,fparentid from t_ord_productdemandfile where fparentid ='"+userid+"'";
			List list = customerDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	//我的客户上传文件
	@RequestMapping("MyCustomerUploadFile")
	public void MyCustomerUploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			UploadFile.upload(request, userid);
			response.getWriter().write(JsonUtil.result(true, "上传成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	@RequestMapping("DelMyCustomer")
	public void DelMyCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String customerid = request.getParameter("fidcls");
			customerDao.DelMyCustomer(request, customerid);
			response.getWriter().write(JsonUtil.result(true, "删除成功！","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	//我的客户导入
	@RequestMapping("saveUploadMyCustomerExcelData")
	public String saveUploadMyCustomerExcelData(HttpServletRequest request,
			HttpServletResponse response)throws IOException, BiffException{
		try {
			String result = customerDao.saveMyCustomerExcel(request);
			response.getWriter().write(JsonUtil.result(true, result, "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	
	/**
	 * 客户导入模版下载
	 */
	@RequestMapping("downloadMyCustomerExcel")
	public void downloadMyCustomerExcel(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String path = "D:\\tomcat\\我的客户导入模版.xls";
		String name = path.substring(path.lastIndexOf("\\")+1);
			InputStream in = null;
			try {
				in = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				throw new DJException("此附件文件不存在，无法下载！");
			}
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(name.getBytes("UTF-8"),"iso-8859-1")+ "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
	}
}
