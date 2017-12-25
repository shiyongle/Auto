package Com.Controller.System;

import java.io.IOException;
import java.util.Date;
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

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IAddressDao;
import Com.Dao.System.ICustRelationAdressDao;
import Com.Dao.quickOrder.IQuickOrderDao;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Useronline;
@Controller
public class AddressController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IAddressDao AddressDao;
	@Resource
	private ICustRelationAdressDao CustRelationAdressDao;
	@Resource
	private IQuickOrderDao QuickOrderDao;
	
	@RequestMapping("/GetAddressList")
	public String GetAddressList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql = "";
		// "select fid,fcreatorid,flastupdateuserid,fcontrolunitid,fdetailaddress,fnumber,fcountryid,fcityidid,femail,flinkman,fphone,fname,fprovinceid,fdistrictidid,fpostalcode,ffax,fcreatetime,flastupdatetime from t_bd_Address ";
		 try {
			 String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			List<HashMap<String, Object>> addressList = AddressDao.QueryBySql("select quote(faddressid) faddressid from t_bd_CustRelationAdress where 1=1 "+AddressDao.QueryFilterByUser(request,"fcustomerid",""));
			if(addressList.size()>0){
				StringBuilder addressIds = new StringBuilder("");
				for(HashMap<String, Object> map: addressList){
					addressIds.append(map.get("faddressid"));
					addressIds.append(",");
				} 
				sql = "select ifnull(c.fname,'') as customerName,tba.fcustomerid,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode,ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba left join t_bd_customer c on c.fid=tba.fcustomerid where tba.fid NOT IN "
				+"(SELECT faddress FROM t_bd_useraddress WHERE fuserid = '"+userid+"') and tba.fid in ("+addressIds.substring(0, addressIds.length()-1)+")";
			}else{
				sql = "select ifnull(c.fname,'') as customerName,tba.fcustomerid,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode,ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba left join t_bd_customer c on c.fid=tba.fcustomerid ";
				sql = sql + " where tba.fid NOT IN (SELECT faddress FROM t_bd_useraddress WHERE fuserid = '"+userid+"') " + AddressDao.QueryFilterByUser(request,"fcustomerid","");
			}
		
			ListResult result;
			reponse.setCharacterEncoding("utf-8");
		
			result = AddressDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/SaveAddress")
	public String SaveAddress(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Address ainfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				ainfo = AddressDao.Query(fid);
			} else {
				ainfo = new Address();
				ainfo.setFid(AddressDao.CreateUUid());
				ainfo.setFcreatorid(userid);
				ainfo.setFcreatetime(new Date());
				//2015-06-02 同时在客户地址表中新增一条记录
				CustRelationAdress cuToadInfo = new CustRelationAdress();
				cuToadInfo.setFid(CustRelationAdressDao.CreateUUid());
				cuToadInfo.setFcustomerid(ainfo.getFcustomerid());
				cuToadInfo.setFaddressid(ainfo.getFid());				
				CustRelationAdressDao.saveOrUpdate(cuToadInfo);
			}
			ainfo.setFlastupdatetime(new Date());
			ainfo.setFlastupdateuserid(userid);
			ainfo.setFname(request.getParameter("fname"));
			ainfo.setFnumber(request.getParameter("fnumber"));
			ainfo.setFpostalcode(request.getParameter("fpostalcode"));
			ainfo.setFemail(request.getParameter("femail"));
			ainfo.setFlinkman(request.getParameter("flinkman"));
			ainfo.setFphone(request.getParameter("fphone"));
			ainfo.setFfax(request.getParameter("ffax"));
			ainfo.setFprovinceid(request.getParameter("fprovinceid"));
			ainfo.setFcountryid(request.getParameter("fcountryid"));
			ainfo.setFdetailaddress(request.getParameter("fdetailaddress"));
			ainfo.setFcityidid(request.getParameter("fcityidid"));
			ainfo.setFdistrictidid(request.getParameter("fdistrictidid"));
			ainfo.setFcontrolunitid(request.getParameter("fcontrolunitid"));
			ainfo.setFcustomerid(request.getParameter("fcustomerid"));
			HashMap<String, Object> params = AddressDao.ExecSave(ainfo);
			System.out.println(params);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true, "保存成功!", "", "");
			} else {
				result = JsonUtil.result(false, "保存失败!", "", "");
			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	
	@RequestMapping("/DelAddressList")
	public String DelAddressList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		
		if(DataUtil.isforeignKeyConstraintLegal("t_ord_delivers", "faddress", AddressDao, fidcls)) {
			try {
				String hql = "Delete FROM Address where fid in " + fidcls;
				AddressDao.ExecByHql(hql);
				String sql = "Delete from t_bd_custrelationadress where faddressid in"+fidcls;
				AddressDao.ExecBySql(sql);
				result = JsonUtil.result(true, "删除成功!", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false, e.getMessage(), "", "");
				log.error("DelUserList error", e);
			}
		} else {
			result = "{success:false,msg:'" + "违法外键约束，本实体已被其他地方引用" + "'}";
		}
		
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/getAddressInfo")
	public String getAddressInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = " select c.fid fcustomerid_fid,ifnull(c.fname,'') fcustomerid_fname,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode,ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba left join t_bd_customer c on c.fid=tba.fcustomerid where tba.fid = '"
					+ request.getParameter("fid") + "'";
			List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	// @RequestMapping("/getcountryInfo")
	// public String getcountryInfo(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException{
	// reponse.setCharacterEncoding("utf-8");
	// try {
	// String sql =
	// " select fid,fname,fid as fcountryid_fid,fname as fcountryid_fname FROM t_bd_country where fdeletedstatus=1 ";
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
	//
	// } catch (Exception e) {
	// reponse.getWriter().write(JsonUtil.result(false, e.toString(), "", ""));
	// }
	// return null;
	// }
	//
	// @RequestMapping("/getprovinceInfo")
	// public String getprovinceInfo(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException{
	// reponse.setCharacterEncoding("utf-8");
	// try {
	// String sql =
	// "  select fid,fname,fid as fcountryid_fid,fname as fcountryid_fname FROM t_bd_province where fdeletedstatus=1 ";
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
	//
	// } catch (Exception e) {
	// reponse.getWriter().write(JsonUtil.result(false, e.toString(), "", ""));
	// }
	// return null;
	// }
	//
	// @RequestMapping("/getcityInfo")
	// public String getcityInfo(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException{
	// reponse.setCharacterEncoding("utf-8");
	// try {
	// String sql =
	// "  select fid,fname,fid as fcountryid_fid,fname as fcountryid_fname FROM t_bd_city where fdeletedstatus=1 ";
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
	//
	// } catch (Exception e) {
	// reponse.getWriter().write(JsonUtil.result(false, e.toString(), "", ""));
	// }
	// return null;
	// }
	//
	// @RequestMapping("/getdistrictInfo")
	// public String getdistrictInfo(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException{
	// reponse.setCharacterEncoding("utf-8");
	// try {
	// String sql =
	// "  select fid,fname,fid as fcountryid_fid,fname as fcountryid_fname FROM t_bd_region where fdeletedstatus=1 ";
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
	//
	// } catch (Exception e) {
	// reponse.getWriter().write(JsonUtil.result(false, e.toString(), "", ""));
	// }
	// return null;
	// }

	// @RequestMapping("/GetCountryAll")
	// public String GetCountryAll(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	//
	// String sql =
	// " select fid,fname FROM t_bd_country where fdeletedstatus=1 ";
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.setCharacterEncoding("utf-8");
	//
	// reponse.getWriter().write(JsonUtil.result(true,"",AddressDao.QueryCountBySql(sql),sList));
	// return null;
	//
	// }
	// @RequestMapping("/GetProvice")
	// public String GetProvice(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	// String sql =
	// "  select fid,fname FROM t_bd_province where fdeletedstatus=1 ";
	// String fcountryid=request.getParameter("fcountryid");
	// if(fcountryid!=null&&!"".equals(fcountryid))
	// {
	// sql += " and fcountryid='"+fcountryid+"'";
	// }
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.setCharacterEncoding("utf-8");
	// reponse.getWriter().write(JsonUtil.result(true,"","",sList));
	// return null;
	//
	// }
	//
	// @RequestMapping("/GetCity")
	// public String GetCity(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	// String sql = "  select fid,fname FROM t_bd_city where fdeletedstatus=1 ";
	// String fprovinceid=request.getParameter("fprovinceid");
	// if(fprovinceid!=null&&!"".equals(fprovinceid))
	// {
	// sql += " and fprovinceid='"+fprovinceid+"'";
	// }
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.setCharacterEncoding("utf-8");
	// reponse.getWriter().write(JsonUtil.result(true,"","",sList));
	// return null;
	//
	// }

	// @RequestMapping("/GetRegion")
	// public String GetRegion(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	// String sql =
	// "  select fid,fname FROM t_bd_region where fdeletedstatus=1 ";
	// String fcityid=request.getParameter("fcityid");
	// if(fcityid!=null&&!"".equals(fcityid))
	// {
	// sql += " and fcityid='"+fcityid+"'";
	// }
	// List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
	// reponse.setCharacterEncoding("utf-8");
	// reponse.getWriter().write(JsonUtil.result(true,"","",sList));
	// return null;
	//
	// }

	@RequestMapping("/AddAddress")
	public String AddAddress(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		return null;

	}

	@RequestMapping("/GetAddressTree")
	public String GetAddressTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "{children:[";
		String sql = "select fid id,fname text,1 isleaf  FROM t_bd_Address ";
		List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql);
		result += JsonUtil.List2Json(sList) + "]}";
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping("/SearchAddressNode")
	public String SearchAddressNode(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		params p = new params();

		String fname = request.getParameter("fname");
		String sql = "select fid id FROM t_bd_Address where fname like :fname order by fid desc";
		p.put("fname", "%" + fname + "%");
		List<HashMap<String, Object>> sList = AddressDao.QueryBySql(sql, p);
		reponse.setCharacterEncoding("utf-8");
		if (sList.size() < 1) {
			reponse.getWriter().write(
					JsonUtil.result(false, "不存在该客户", sList.size() + "", sList));
		} else {
			reponse.getWriter().write(
					JsonUtil.result(true, "", sList.size() + "", sList));
		}

		return null;

	}

	/**
	 * 用户优先作为过滤条件
	 */
	@RequestMapping("/gainAddressListByUserFirst")
	public String gainAddressListByUserFirst(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	
		String sql = "select ifnull(c.fname,'') as customerName,tba.fcustomerid,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode,ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba left join t_bd_customer c on c.fid=tba.fcustomerid ";
		sql = sql + " where 1=1 " + AddressDao.QueryFilterByUser(request,"fcustomerid","");
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		
		String sql2 = " select count(*) from t_bd_useraddress where fuserid = '%s' ";
		
		sql2 = String.format(sql2, MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request));
		
		
		try {
			

			if (!AddressDao.QueryCountBySql(sql2).equals("0")) {
				
				String hql2 = " select faddress from Useraddress where fuserid = '%s' ";
				
				hql2 = String.format(hql2, MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request));
				 
				List<String> addresss = AddressDao.QueryByHql(hql2);
				
				int sizeT = addresss.size();
				
				StringBuilder sb = new StringBuilder("('");
				
				for (int i = 0; i < sizeT; i++) {
					
					if (i != 0) {
						
						sb.append("','");
						
					}
					
					sb.append(addresss.get(i));
				}
				
				sb.append("')");
				
				sql += String.format(" or tba.fid in %s ", sb.toString()) ;
			
				result = AddressDao.QueryFilterList(sql, request);
				
				
			} else { 
				
				String fcustomerid = gainFcustomerid(request); 
				
				if (!fcustomerid.equals("-1")) {
					
					sql += String.format(" and tba.fcustomerid = '%s' ", fcustomerid);
					
				}
				
				
				result = AddressDao.QueryFilterList(sql, request);
				
			}
			
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	/**
	 * 查询用户的地址，先根据t_bd_useraddress查，没有，再根据客户查
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getUserAddress")
	public String getUserAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String sql = "select quote(faddress) faddressid from t_bd_useraddress where fuserid = '"+userid+"'";
			List<HashMap<String, Object>> addressList = AddressDao.QueryBySql(sql);
			if(addressList.size()>0){
				StringBuilder addressIds = new StringBuilder("");
				for(HashMap<String, Object> map: addressList){
					addressIds.append(map.get("faddressid"));
					addressIds.append(",");
				}
				sql = "select fid,fname,fnumber,flinkman,fphone from t_bd_address where fid in ("+addressIds.substring(0, addressIds.length()-1)+")";
			}else if(AddressDao.QueryExistsBySql("select fid from t_bd_CustRelationAdress where 1=1 "+AddressDao.QueryFilterByUser(request,"fcustomerid",""))){
				addressList = AddressDao.QueryBySql("select quote(faddressid) faddressid from t_bd_CustRelationAdress where 1=1 "+AddressDao.QueryFilterByUser(request,"fcustomerid",""));
				if(addressList.size()>0){
					StringBuilder addressIds = new StringBuilder("");
					for(HashMap<String, Object> map: addressList){
						addressIds.append(map.get("faddressid"));
						addressIds.append(",");
					}
					sql = "select fid,fname,fnumber,flinkman,fphone from t_bd_address where fid in ("+addressIds.substring(0, addressIds.length()-1)+")";
				}
			}else{
				sql = "select fid,fname,fnumber,flinkman,fphone from t_bd_address where 1=1 "+AddressDao.QueryFilterByUser(request,"fcustomerid","");
			}
			ListResult result = AddressDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	private String gainFcustomerid(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		 String defaultfilter =  request.getParameter("Defaultfilter");
		 
		 JSONArray jsa = JSONArray.fromObject(defaultfilter);
		 
		 JSONObject json = jsa.getJSONObject(1);
		 
		return  json.getString("value");
	}
	@RequestMapping("/GetAddressAndCustrelationadress")
	public String GetAddressAndCustrelationadress(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select * from (select ifnull(c.fname,'') as customerName,c.fid fcustomerid,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode,ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba left join t_bd_customer c on c.fid=tba.fcustomerid ";
		sql = sql + " UNION SELECT IFNULL(c.fname, '') AS customerName, cra.fcustomerid,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,IFNULL(tba.femail, '') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,IFNULL(tba.fpostalcode, '') fpostalcode,IFNULL(tba.ffax, '') ffax,"
				+" tba.fcreatetime,tba.flastupdatetime  FROM t_bd_custrelationadress cra LEFT JOIN t_bd_customer c ON cra.fcustomerid =c.`fid` LEFT JOIN t_bd_Address tba ON tba.`FID`=cra.faddressid) a where 1=1  " ;
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = AddressDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/SaveAddressMange")
	public String SaveAddressMange(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Address ainfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			Address address = (Address)request.getAttribute("Address");
			if (address.getFid() != null && !"".equals(address.getFid())) {
				ainfo = AddressDao.Query(address.getFid());
				ainfo.setFlastupdatetime(new Date());
				ainfo.setFlastupdateuserid(userid);
			} else {
				ainfo = new Address();
				ainfo.setFid(AddressDao.CreateUUid());
				ainfo.setFcreatorid(userid);
				ainfo.setFcreatetime(new Date());
				//2015-06-02 同时在客户地址表中新增一条记录
				CustRelationAdress cuToadInfo = new CustRelationAdress();
				cuToadInfo.setFid(CustRelationAdressDao.CreateUUid());
				cuToadInfo.setFcustomerid(address.getFcustomerid());
				cuToadInfo.setFaddressid(ainfo.getFid());				
				CustRelationAdressDao.saveOrUpdate(cuToadInfo);
			}
			ainfo.setFlinkman(address.getFlinkman());
			ainfo.setFphone(address.getFphone());
			ainfo.setFdetailaddress(address.getFdetailaddress());
			ainfo.setFname(address.getFdetailaddress());
			ainfo.setFcustomerid(address.getFcustomerid());
			HashMap<String, Object> params = AddressDao.ExecSave(ainfo);
			System.out.println(params);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true, "保存成功!", "", "");
			} else {
				result = JsonUtil.result(false, "保存失败!", "", "");
			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}
	
	//制造商地址管理列表查询;
	@RequestMapping("/GetsupplierscustAddressList")
	public String GetsupplierscustAddressList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql = "";
		 String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
	      ListResult result;
		 try {
		        String fsupplierid=QuickOrderDao.getCurrentSupplierid(userid);
//		        sql="SELECT fcustomerid FROM t_pdt_productreqallocationrules r LEFT JOIN t_bd_customer s ON s.fid=r.fcustomerid where r.fsupplierid ="+fsupplierid;
//				List<HashMap<String, Object>> addressList = AddressDao.QueryBySql("select quote(faddressid) faddressid from t_bd_CustRelationAdress where fcustomerid in ("+sql+") ");
//				if(addressList.size()>0){
//					StringBuilder addressIds = new StringBuilder("");
//					for(HashMap<String, Object> map: addressList){
//						addressIds.append(map.get("faddressid"));
//						addressIds.append(",");
//					} 
					sql = "select ifnull(c.fname,'') as customerName,tba.fcustomerid,tba.fid,tba.fcreatorid,tba.flastupdateuserid,tba.fcontrolunitid,tba.fdetailaddress,tba.fnumber,ifnull(tba.femail,'') femail,tba.flinkman,tba.fphone,tba.fname,tba.fcountryid,tba.fprovinceid,tba.fcityidid,tba.fdistrictidid,ifnull(tba.fpostalcode,'') fpostalcode,ifnull(tba.ffax,'') ffax,tba.fcreatetime,tba.flastupdatetime from t_bd_Address tba  LEFT JOIN t_bd_CustRelationAdress cu ON cu.`faddressid`= tba.`FID` LEFT JOIN t_pdt_productreqallocationrules r ON r.`fcustomerid`= cu.`fcustomerid`  left join t_bd_customer c on c.fid=r.fcustomerid where tba.fid NOT IN "
					+"(SELECT faddress FROM t_bd_useraddress WHERE fuserid = '"+userid+"') and r.fsupplierid="+fsupplierid;//tba.fid in ("+addressIds.substring(0, addressIds.length()-1)+")";
//				}
			
				reponse.setCharacterEncoding("utf-8");
			
				result = AddressDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
}
