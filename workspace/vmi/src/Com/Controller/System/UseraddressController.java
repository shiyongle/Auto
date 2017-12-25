package Com.Controller.System;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.BaseSysDao;
import Com.Dao.System.IAddressDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustRelationAdressDao;
import Com.Dao.System.IUseraddressDao;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useraddress;
import Com.Entity.System.Useronline;

@Controller
public class UseraddressController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IUseraddressDao useraddressDao;
	@Resource
	private IAddressDao addressDao;
	@Resource
	private ICustRelationAdressDao custRelationAdressDao;
	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping("/addUserAddressItem")
	public String addUserAddressItem(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			useraddressDao.ExecAddUserAddressItem(request);

			reponse.getWriter().write(JsonUtil.result(true, "成功", "", ""));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	@RequestMapping("/delUserAddressItem")
	public String delUserAddressItem(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			useraddressDao.ExecDelUserCustomer(request);

			reponse.getWriter().write(JsonUtil.result(true, "成功", "", ""));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/gainUserAddresss")
	public String gainUserAddresss(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		String sql = " SELECT tbu.fid, tda.fname, tda.fnumber,tbu.fuserid userid  FROM t_bd_useraddress tbu left join t_bd_address tda on tda.fid = tbu.faddress ";
		ListResult result;
		
		reponse.setCharacterEncoding("utf-8");
		try {
			result = useraddressDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;

	}
	
	//2015-04-23 by lu  客户地址列表
	@RequestMapping("/GetUserAddressList")
	public String getUserAddressList(HttpServletRequest request,HttpServletResponse response) throws IOException {									
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		//2015-06-02 根据用户管理中“只查看自己帐号” 过滤地址
		SysUser user  = (SysUser)baseSysDao.Query(SysUser.class, userid);
		//1.只查看自己帐号,则取用户关联地址中的地址信息
		//2.`feffect`标志设为2，这种情况下需要隐藏启用、禁用按钮，因为这个地址不一定在本用户关联客户对应的客户关联地址表中【可能引用了其他客户的地址】
		//因为用户管理中关联的地址是不会往客户关联地址表中写入记录的
		StringBuffer suf = new StringBuffer("");
		if(user.getFisreadonly()==1){ 
			suf.append("SELECT ad.`fid` fid,ud.fid cdid,cu.`FNAME` custname,ad.`FDETAILADDRESS` dtaddress,ad.`FLINKMAN` linkman,ad.`FPHONE` phone,2 AS `feffect`,UD.`fdefault` fdefault  ");
			suf.append("FROM t_bd_useraddress ud JOIN `t_bd_address` ad  ON ad.`FID` = ud.`faddress`  ");
			suf.append("JOIN `t_bd_usercustomer`  uc ON uc.`FUSERID` = ud.`fuserid`  ");
			suf.append("JOIN `t_bd_customer` cu ON cu.`fid` = uc.`FCUSTOMERID`  ");
			suf.append("JOIN `t_sys_user` u ON u.`FID` = ud.`fuserid` ");
			suf.append("where  u.`FID`='"+userid+"' ");	
		}
		else{
			suf.append("SELECT ad.`fid` fid,cd.fid cdid,cu.`FNAME` custname,ad.`FDETAILADDRESS` dtaddress,ad.`FLINKMAN` linkman,ad.`FPHONE` phone,cd.`feffect`, ");
			suf.append("CASE  WHEN IFNULL(ud.fdefault,0) = 1 THEN 1 ELSE 0 END fdefault ");
			suf.append("FROM `t_bd_custrelationadress` cd ");
			suf.append("JOIN `t_bd_address` ad  ON ad.`FID` = cd.`faddressid` ");
			suf.append("JOIN `t_bd_usercustomer`  uc ON uc.`FCUSTOMERID` = cd.`fcustomerid` ");
			suf.append("JOIN `t_bd_customer` cu ON cu.`fid` = uc.`FCUSTOMERID` ");
			suf.append("JOIN `t_sys_user` u ON u.`FID` = uc.`FUSERID` ");
			suf.append("LEFT JOIN t_bd_useraddress ud ON ud.`faddress`= cd.`faddressid` and ud.`fuserid` = u.`FID` ");
			suf.append("where u.`FID`='"+userid+"' ");	
		}
		try {
			ListResult result = useraddressDao.QueryFilterList(suf.toString(), request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	//2015-04-23 by lu 判断用户是否关联多个客户
	@RequestMapping(value = "/getCountCustInfo")
	public String getCountCustInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String sql="select fcustomerid fid,cust.`FNAME` fname from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s "+
					" left join t_bd_customer cust ON cust.fid = s.fcustomerid where fcustomerid is not null " ;
			List<HashMap<String, Object>> data=useraddressDao.QueryBySql(sql);
			if(data.size()== 0)
			{
				throw new DJException("当前帐号没有关联客户,不能执行操作！");				
			}else{
				response.getWriter().write(JsonUtil.result(true, "", data.size()+"",data));
			}
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	//2015-04-23 by lu 保存地址
	@RequestMapping(value = "/saveUserAddress")
	public String saveUserAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		Address ainfo;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			ainfo = (Address)request.getAttribute("Address");
			ainfo.setFname(ainfo.getFdetailaddress());
			//修改地址的时候,不用保存默认地址和客户地址
			if(!"".equals(ainfo.getFid())){
				HashMap<String, Object> params = addressDao.ExecSave(ainfo);
				if (params.get("success") == Boolean.TRUE) {
					result = JsonUtil.result(true, "地址保存成功!", "", "");
					response.getWriter().write(result);
					return null;
				}
			}
			else{
				ainfo.setFcreatorid(userid);
				ainfo.setFcreatetime(new Date());
				ainfo.setFlastupdatetime(new Date());
				ainfo.setFlastupdateuserid(userid);
				HashMap map = new HashMap();
				map.put("userid", userid);
				map.put("Address", ainfo);	
				HashMap<String, Object> params = useraddressDao.ExecSaveMyAddress(map);
				if (params.get("success") == Boolean.TRUE) {
					result = JsonUtil.result(true, "地址保存成功!", "", "");
				} else {
					result = JsonUtil.result(false, "地址保存失败!", "", "");
				}
			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		response.getWriter().write(result);
		return null;
	}
	
	//2015-04-24 by lu 设为默认地址
	@RequestMapping(value = "/setUserAddressDft")
	public String setUserAddressDft(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String fadid = request.getParameter("fadid");
		String fcdid = request.getParameter("fcdid");
		HashMap map = new HashMap();
		map.put("fadid", fadid);
		map.put("fcdid", fcdid);
		map.put("userid", userid);		
		try {
			useraddressDao.ExecUserAddressDft(map);
			response.getWriter().write(JsonUtil.result(true, "默认地址成功！", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "默认地址失败！", "", ""));
		}
		return null;
	}
	
	//2015-04-24 by 禁用-启用客户地址
	@RequestMapping(value = "/setCustAdEffect")
	public String setCustAdEffect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		String fidcls = request.getParameter("fidcls");
		int feffect = Integer.parseInt(request.getParameter("feffect"));
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			useraddressDao.ExecBySql("update t_bd_custrelationadress set feffect= "+feffect+" where fid in "+fidcls);		
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "操作失败！", "", ""));
		}
		return null;
	}
	
	//2015-04-24 by lu 判断地址是否被多个客户关联
	@RequestMapping(value = "/checkUserAddress")
	public String checkUserAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String adid = request.getParameter("fid");
		try {
			String sql="select fid from t_bd_custrelationadress where faddressid = '"+adid+"'" ;
			List<HashMap<String, Object>> data=custRelationAdressDao.QueryBySql(sql);
			if(data.size()>1)
			{
				throw new DJException("当前地址关联多个客户,不能修改！");				
			}
			response.getWriter().write(JsonUtil.result(true, "","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	//2015-04-24 by lu 删除客户地址
	@RequestMapping(value = "/delUserToAddress")
	public String delUserToAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String adid = request.getParameter("fadid");
		String cdid = request.getParameter("fcdid");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		HashMap map = new HashMap();
		map.put("fadid", adid);
		map.put("fcdid", cdid);
		map.put("userid", userid);		
		try {
			String strsql="select fid from t_bd_custrelationadress where faddressid = '"+adid+"'" ;
			List<HashMap<String, Object>> data=custRelationAdressDao.QueryBySql(strsql);
			if(data.size()>1)
			{
				throw new DJException("当前地址关联多个客户,不能删除！");				
			}
			strsql = useraddressDao.QueryFilterByUser(request,"fcustomerid","");			
			map.put("strsql", strsql);
			useraddressDao.DelUserToAddress(map);
			result = JsonUtil.result(true,"删除成功!", "", "");
			response.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		response.getWriter().write(result);
		return null;
	}
	
	//2015-04-25 by lu  用户地址列表
	@RequestMapping("/getUserDefaultAddress")
	public String getUserDefaultAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		ListResult result;
		//2015-06-03 根据用户管理中“只查看自己帐号” 过滤默认地址
		SysUser user  = (SysUser)baseSysDao.Query(SysUser.class, userid);
		String sql = "";
		try {
			if(user.getFisreadonly()==1){ 
				sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"+userid+"' and ud.`fdefault` = 1";
				List list  = useraddressDao.QueryBySql(sql);
				if(list.size()==0){
					String customerid = baseSysDao.getCurrentCustomerid(userid);
					sql = " SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad where ad.fcustomerid = '"+customerid+"'  "+
							" and ad.fid IN (SELECT faddressid FROM t_bd_custrelationadress where feffect = 1) ";
					List nlist  = useraddressDao.QueryBySql(sql);
					response.getWriter().write(JsonUtil.result(true, "", nlist.size()+"",nlist));
					return null;
				}
				response.getWriter().write(JsonUtil.result(true, "", list.size()+"",list));
			}
			else{
				//正常情况下 应该用户地址表中只有唯一地址,也就是默认地址
				sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"+userid+"'";
				result = useraddressDao.QueryFilterList(sql, request);	
				//2015年8月11日  如果没设置默认，则取地址簿里面最新地址做为默认
				if(result.getTotal().equals("0")){
					String customerid = baseSysDao.getCurrentCustomerid(userid);
					sql = " SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad where ad.fcustomerid = '"+customerid+"'  "+
							" and ad.fid IN (SELECT faddressid FROM t_bd_custrelationadress where feffect = 1) ";
					List nlist  = useraddressDao.QueryBySql(sql);
					response.getWriter().write(JsonUtil.result(true, "", nlist.size()+"",nlist));
					return null;
				}
				response.getWriter().write(JsonUtil.result(true, "", result));	
			}
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	//2015-04-25 by lu  用户关联客户地址列表
	@RequestMapping("/getUserToCustAddress")
	public String getUserToCustAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		ListResult result ;
		//2015-06-03 根据用户管理中“只查看自己帐号” 过滤地址
		SysUser user  = (SysUser)baseSysDao.Query(SysUser.class, userid);
		String sql = "";
		try {		
			if(user.getFisreadonly()==1){ 
				sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone,ad.fdetailaddress FROM t_bd_address ad JOIN t_bd_useraddress cd  ON ad.`FID` = cd.`faddress` where cd.`fuserid` = '"+userid+"'";
				List list  = useraddressDao.QueryBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "", list.size()+"",list));
			}
			else{
				String fcustomerid = baseSysDao.getCurrentCustomerid(userid);	
				sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone,ad.fdetailaddress FROM t_bd_address ad JOIN t_bd_CustRelationAdress cd ON ad.`FID` = cd.`faddressid` where cd.`fcustomerid` ='"+fcustomerid+"' and cd.feffect=1";
				result = useraddressDao.QueryFilterList(sql, request);	
				response.getWriter().write(JsonUtil.result(true, "", result));
			}				
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	//2015-05-20 by lu  销售模块不是根据用户过滤地址,而是根据界面所选择的客户过滤
	@RequestMapping("/getDjCustAddress")
	public String getDjCustAddress(HttpServletRequest request,HttpServletResponse response) throws IOException {		
		try {		
			String sql = "";
			sql = "SELECT ad.fid,ad.fname,ad.fnumber,ad.flinkman,ad.fphone,ad.fdetailaddress FROM t_bd_address ad JOIN t_bd_CustRelationAdress cd ON ad.`FID` = cd.`faddressid` where cd.feffect=1";
			ListResult result = useraddressDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));				 
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
}
