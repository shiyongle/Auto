package Com.Controller.System;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustomer;
import Com.Dao.System.IRegisterAuthenticationDao;
import Com.Dao.System.ISupCustomerInfoDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.IDeliversDao;
import Com.Entity.System.Customer;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.Supplier;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.System.Userpermission;
import Com.Entity.order.Productdemandfile;

@Controller
public class RegisterAuthernticationController {

	@Resource
	private IRegisterAuthenticationDao registerAuthenticationDao;
	
	@Resource
	private IBaseSysDao baseSysDao;
	
	@Resource
	private ICustomer customerDao;
	

	
	/**
	 * 根据用户对应的客户 返回客户是否认证  注册
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getIsAuthenticationByCustomer")
	public String getIsAuthenticationByCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = baseSysDao.getCurrentUserId(request);
		try {
		SysUser user=(SysUser)registerAuthenticationDao.Query(SysUser.class, userid);
		if(user==null) throw new DJException("用户不存在！");
		String fcustomerid=(user.getFtype()==0?(StringUtils.isEmpty(user.getFcustomerid())?"":user.getFcustomerid()):"");
//		String fcustomerid=baseSysDao.getCurrentCustomerid(userid);
		String sql =String.format("select fid from t_bd_customer where fid='%s' and ifnull(fisinternalcompany,0)=0 ",fcustomerid);
	
			response.getWriter().write(JsonUtil.result(true,fcustomerid,registerAuthenticationDao.QueryExistsBySql(sql)==true?"0":"1",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	/**
	 * 用户中心 -用户数据显示
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getUserManageUserinfo")
	public String getUserManageUserinfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = baseSysDao.getCurrentUserId(request);
		try {
			String sql =String.format( "select fid,fname,femail,ftel,fphone,ffax,fqq from t_sys_user where fid='%s'",userid);
			List<HashMap<String,Object>> result= registerAuthenticationDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	
	/**
	 * 用户中心 -保存用户数据
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveUserManageUser")
	public String saveUserManageUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String fid=request.getParameter("fid");
			String sql="";//是否执行修改密码
			SysUser userinfo=(SysUser)registerAuthenticationDao.Query(SysUser.class, fid);
			Customer cinfo=null;
			if(userinfo==null)
			{
				throw new DJException("用户不存在！");
			}
			String tel=request.getParameter("ftel");
			String fname=request.getParameter("fname");
			String femail=request.getParameter("femail");
			if(StringUtils.isEmpty(tel)&&StringUtils.isEmpty(fname)&&StringUtils.isEmpty(femail)){
				throw new DJException("手机、账户名称、邮箱其中一项不能为空！");
			}
			if(!StringUtils.isEmpty(tel)&&!userinfo.getFtel().equals(tel))
			{
				if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(tel, fid, registerAuthenticationDao)) throw new DJException("保存失败,该手机信息已被使用！");
			}
			if(!StringUtils.isEmpty(fname)&&!userinfo.getFname().equals(fname))
			{
				if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(fname, fid, registerAuthenticationDao))throw new DJException("保存失败,该账户名称已被使用！");
//			}else if(StringUtils.isEmpty(fname))
//			{
//				fname=tel;//保证账户不为空
			}
			if(!StringUtils.isEmpty(femail)&&!userinfo.getFemail().equals(femail))
			{
				if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(femail, fid, registerAuthenticationDao))throw new DJException("保存失败,该邮箱已被使用！");
			}
			userinfo.setFtel(tel);
			userinfo.setFname(fname);
			userinfo.setFemail(femail);
			userinfo.setFphone(request.getParameter("fphone"));
			userinfo.setFfax(request.getParameter("ffax"));
			userinfo.setFqq(request.getParameter("fqq"));
			if(!StringUtils.isEmpty(request.getParameter("password"))){
				sql=String.format("update t_sys_user set fpassword='%s' where fid='%s'",request.getParameter("password"),userinfo.getFid());//因为sysuser对象配置文件中默认不更新
			}
			//假如用户是客户类型且关联的客户不为空，则更新 客户的名称，下单手机，联系电话 
//			if(userinfo.getFtype()==0&&!StringUtils.isEmpty(userinfo.getFcustomerid())){
//				cinfo=(Customer)registerAuthenticationDao.Query(Customer.class, userinfo.getFcustomerid());
//				if(cinfo!=null)
//				{
//					if(cinfo.getFisinternalcompany()!=1){//认证后不修改
//						cinfo.setFname(userinfo.getFname());
//					}
//					cinfo.setFphone(userinfo.getFtel());
//					cinfo.setFartificialpersonphone(userinfo.getFphone());
//				}
//			}
			registerAuthenticationDao.saveUserManageUser(userinfo,cinfo,sql);//修改用户暂时不修改客户信息
			response.getWriter().write(JsonUtil.result(true,"保存成功！", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	
	/**
	 * 上传附件身份证或企业认证图片
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("uploadCustomerAuthenticationFile")
	public String uploadCustomerAuthenticationFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String ftype=request.getParameter("type");
		switch(ftype){
		case "0": ftype="身份证";break;
		case "1": ftype="营业执照";break;
		default: ftype="";break;
		}
		String result = "";
		HashMap map=new HashMap();
		map.put("ftype", ftype);
		map.put("fparentid", request.getParameter("fparentid"));
		map.put("fdescription", "");
		try {
			 List productfile=UploadFile.upload(request, map,2,false);
			result = "{success:true,msg:'附件上传成功!'}";
			response.getWriter().write(JsonUtil.result(true, "附件上传成功!", "1",productfile));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));	
		}
		
		return null;
	}
	
	
	
	
	/**
	 * 认证
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/sumbitAuthentication")
	public String sumbitAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String userid = baseSysDao.getCurrentUserId(request);
			int ftype=StringUtils.isEmpty(request.getParameter("type"))?0:new Integer(request.getParameter("type"));
			SysUser userinfo=(SysUser)registerAuthenticationDao.Query(SysUser.class, userid);
			Customer cinfo=null;
			//假如用户是客户类型且关联的客户不为空，则更新 客户的名称，下单手机，联系电话 
			if(userinfo.getFtype()==0&&!StringUtils.isEmpty(userinfo.getFcustomerid())){
				cinfo=(Customer)registerAuthenticationDao.Query(Customer.class, userinfo.getFcustomerid());
				if(cinfo!=null)
				{
					if(ftype==0){//个人认证
						if(StringUtils.isEmpty(request.getParameter("name"))) throw new DJException("姓名不能为空");
						if(MySimpleToolsZ.isTheRightCustomerByFid(request.getParameter("name"), cinfo.getFid(),baseSysDao))	throw new DJException("保存失败,该姓名已被其他人认证过！");
						if(StringUtils.isEmpty(request.getParameter("identitycard"))) throw new DJException("身份证号不能为空");
						if(!registerAuthenticationDao.QueryExistsBySql(String.format("select fid from t_ord_productdemandfile where fparentid='%s' and ftype='身份证'",cinfo.getFid()))) throw new DJException("请上传身份证图片"); 
						cinfo.setFname(request.getParameter("name"));//客户名称
						cinfo.setFartificialperson(request.getParameter("name"));//法人代表
						cinfo.setFbarcode(request.getParameter("identitycard"));//身份证号
						cinfo.setFisinternalcompany(1);//认证通过 
						registerAuthenticationDao.saveCustomerAndFsupplier(cinfo);
						response.getWriter().write(JsonUtil.result(true,"保存成功！", "", ""));
						return null;
					}//企业认证
					else
					{
						
						if(StringUtils.isEmpty(request.getParameter("companyname"))) throw new DJException("企业名称不能为空");
						if(MySimpleToolsZ.isTheRightCustomerByFid(request.getParameter("companyname"), cinfo.getFid(),baseSysDao))	throw new DJException("保存失败,该企业名称已使用！");
						if(StringUtils.isEmpty(request.getParameter("registrationno"))) throw new DJException("注册号不能为空");
						if(StringUtils.isEmpty(request.getParameter("fartificialperson"))) throw new DJException("法人代表不能为空");
						if(!registerAuthenticationDao.QueryExistsBySql(String.format("select fid from t_ord_productdemandfile where fparentid='%s' and ftype='营业执照' ",cinfo.getFid()))) throw new DJException("请上传证件图片"); 
						cinfo.setFname(request.getParameter("companyname"));//客户名称
						cinfo.setFartificialperson(request.getParameter("fartificialperson"));//法人代表
						cinfo.setFtxregisterno(request.getParameter("registrationno"));//注册号
						cinfo.setFartificialpersonphone(request.getParameter("personfphone"));//法人代表电话
						registerAuthenticationDao.saveCustomerAndFsupplier(cinfo);						
						response.getWriter().write(JsonUtil.result(true,"已成功提交，平台将在2小时内审核完成，请关注认证标识", "", ""));	
						return null;
					}
				
				}
			}
			response.getWriter().write(JsonUtil.result(false,"信息有误", "", ""));	

		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	//getFileByfid.do(fparentid) 窗口关闭删除附件
	
	/**
	 * 删除所有附件 认证时
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("deleteAuthenticationFileByfid")
	public void deleteAuthenticationFileByfid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fparentid = request.getParameter("fid");
		try {
			registerAuthenticationDao.ExecDelFileByFparentid(fparentid);

			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	/**
	 * 删除所有附件 客户产品新增时 如果没保存则删除附件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("deleteCustomerFileByAddfid")
	public void deleteCustomerFileByAddfid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fparentid = request.getParameter("fid");
		try {
			if(!registerAuthenticationDao.QueryExistsBySql(String.format("select fid from  t_bd_customer where fid='%s'",fparentid)))
			{
				registerAuthenticationDao.ExecDelFileByFparentid(fparentid);
			}

			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	

	/**
	 * 删除附件根据图片fid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("deleteCustomerFileByfilefid")
	public void deleteCustomerFileByfilefid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fparentid = request.getParameter("fid");
		try {
			
			registerAuthenticationDao.ExecDelAuthenticationFile(fparentid);
			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	
	
	/**
	 * 客户新增时 fid先赋值，用于附件上传
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getcustomeridWithAdd")
	public void getcustomeridWithAdd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			
			Map map = new HashMap();
			List list = new ArrayList();
			map.put("fid", registerAuthenticationDao.CreateUUid());
			map.put("fnumber", ServerContext.getNumberHelper().getNumber("t_bd_customer", "KH", 3, false));
			list.add(map);
			response.getWriter().write(JsonUtil.result(true, "", "1",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	
	/**
	 * 客户资料---获取附件图片信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getCustomerIdentityFileByfid")
	public String getCustomerIdentityFileByfid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fparentid = request.getParameter("fid");
		if(StringUtils.isEmpty(fparentid)) {response.getWriter().write(JsonUtil.result(true,"","",""));return null;}
		String ftype=request.getParameter("type");
		switch(ftype){
		case "0": ftype="身份证";break;
		case "1": ftype="营业执照";break;
		default: ftype="";break;
		}
		try {
			String sql=" SELECT d.fid,IFNULL(f.fpath,d.fpath) imgUrl FROM t_ord_productdemandfile   d  LEFT JOIN t_ord_productdemandfile f ON d.fid=f.fbid WHERE d.fparentid='"+fparentid+"' and d.ftype='"+ftype+"' ORDER BY d.fcreatetime desc";
			List<HashMap<String,Object>> result= registerAuthenticationDao.QueryBySql(sql);
			for (HashMap<String, Object> record : result) {
				String pathT = UploadFile.getFilePathByPath((String)record.get("imgUrl"));
				record.put("imgUrl", pathT);
			}
			response.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
/**
 * 客户基础资料。 客户信息的保存于修改
 * @param request
 * @param reponse
 * @return
 * @throws IOException
 */
	@RequestMapping(value="/SaveNewCustomer")
	public  String SaveNewCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String result = "";
	Customer cinfo=null;
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	String fid=request.getParameter("fid");
	cinfo=(Customer)registerAuthenticationDao.Query(Customer.class,fid);
	if(cinfo==null)
	{
		 cinfo=new Customer();
		 cinfo.setFid(fid);
		 cinfo.setFcreatorid(userid);
		 cinfo.setFcreatetime(new Date());
	}
	if(MySimpleToolsZ.isTheRightCustomerByFid(request.getParameter("fname"), cinfo.getFid(),baseSysDao))	throw new DJException("该客户名称已存在");
	cinfo.setFname(request.getParameter("fname"));
	cinfo.setFnumber(request.getParameter("fnumber"));
	cinfo.setFtxregisterno(request.getParameter("ftxregisterno"));//注册号
	cinfo.setFartificialperson(request.getParameter("fartificialperson"));
	cinfo.setFindustryid(request.getParameter("findustryid"));
	cinfo.setFbarcode(request.getParameter("fbarcode"));//身份证
	cinfo.setFaddress(request.getParameter("faddress"));
	cinfo.setFdescription(request.getParameter("fdescription")==null?"":request.getParameter("fdescription"));
	cinfo.setFschemedesign(request.getParameter("fschemedesign")==null?0:new Integer(request.getParameter("fschemedesign")));
	int fisinteralcomany=(request.getParameter("fisinternalcompany")==null?0:new Integer(request.getParameter("fisinternalcompany")));
	
	HashMap<String, Object> params =null;
//	if(cinfo.getFisinternalcompany()!=fisinteralcomany)//认证值改变  2015-08-19 现认证修改不分配角色
//	{
		cinfo.setFisinternalcompany(fisinteralcomany);//是否认证
//		params=registerAuthenticationDao.ExecSaveCustomerAndRole(cinfo);
//	}else
//	{
		params= customerDao.ExecSave(cinfo);
//	}
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
		result = "{success:false,msg:'"+e.getMessage()+ "'}";
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}

	/***
	 * 基础资料 客户管理的信息查询
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getNewCustomer")
	public String getNewCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
		String sql = "select fschemedesign,fid, fname,fnumber,fdescription,findustryid,fartificialperson,fisinternalcompany,ftxregisterno,fbarcode,faddress from t_bd_customer where fid = '"
				+ request.getParameter("fid") +"'";
		List<HashMap<String, Object>> sList = customerDao.QueryBySql(sql);
		reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
			
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	
	
	@RequestMapping("/GetUserCusMainmenu")
	public String GetUserCusMainmenu(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		Useronline useronlineinfo = (Useronline) request.getSession()
				.getAttribute("Useronline");
		try{
		String result = "{\"success\":true,\"menus\": ";
		String sql = "";
		reponse.setCharacterEncoding("utf-8");
		List<HashMap<String, Object>> childrenList=null;
		if (useronlineinfo.getFusername().equals("EAS")) {
			reponse.getWriter().write("{\"success\":false,\"msg\":\"该账号是管理员\"}");
			return null;
		} else {
			
//				sql = "select quote(froleid) userid from t_sys_userrole where fuserid='"+useronlineinfo.getFuserid()+"'";
//				List<HashMap<String, Object>> demandList = MainMenuDao.QueryBySql(sql);
//					StringBuilder userids=new StringBuilder("'"+useronlineinfo.getFuserid()+"'");
//					for(HashMap<String, Object> m : demandList){
//						userids.append(","+m.get("userid"));
//					}
			//自定义权限  送货凭证,产品档案，客户管理,地址管理
			StringBuilder menus=new StringBuilder("'c445c5dd-f316-11e4-bd7e-00ff6b42e1e5','75f8d45f-0f20-11e5-9395-00ff61c9f2e3','38961fcc-0dcf-11e5-9395-00ff61c9f2e3','8a5953bf-14ff-11e5-be46-00ff61c9f2e3'");
			sql="select concat(ifnull(fisinternalcompany,0),'') fisinternalcompany from t_bd_customer c inner join t_sys_user s on s.fcustomerid=c.fid inner join  t_sys_userrole r on r.fuserid=s.fid and froleid='030480f3-4645-11e5-8107-00ffa1e6e961' where s.fid='"+useronlineinfo.getFuserid()+"' ";
			List<HashMap<String, Object>> custlist=registerAuthenticationDao.QueryBySql(sql);
			if(custlist.size()==0) throw new DJException("不是注册账号！不能操作");//只有角色分配为“ 新注册用户”角色且用户的管理子账户有
			if("1".equals(custlist.get(0).get("fisinternalcompany")))//已认证
			{
				//我的业务,制造商-纸板订单，我的发货，客-纸板订单，暂存订单，我的收货，快速下单，订单监控
				menus.append(",'8382894c-0d74-11e5-9395-00ff61c9f2e3','048ae2d2-cbab-11e4-a8a2-00ff6b42e1e5','dffaef21-cbaa-11e4-a8a2-00ff6b42e1e5','6755469e-cbaa-11e4-a8a2-00ff6b42e1e5','ef9ee2a5-fe0e-11e4-9395-00ff61c9f2e3','7725a04b-4034-11e3-ad63-60a44c5bbef3','1728ba31-0d2a-11e5-9395-00ff61c9f2e3','2296fc76-1ace-11e5-be46-00ff61c9f2e3'");
			}			
			sql="SELECT   m.fid ,m.fpath,count(n.fid) fischeck FROM t_sys_Mainmenuitem   m left JOIN t_sys_userpermission n ON n.FRESOURCESID=m.FID and n.fuserid ='"+useronlineinfo.getFuserid()+"' WHERE   m.fid in ("+menus+") group by m.fid";
			List<HashMap<String, Object>> menuList=registerAuthenticationDao.QueryBySql(sql);
			 JSONArray bigArray = JSONArray.fromObject(menuList);
			 
			result = result + bigArray.toString() +",\"register\":"+custlist.get(0).get("fisinternalcompany")+"}";
			reponse.getWriter().write(result);

		}
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	
		return null;

	}
	
	
	// MainMenuTree
		@RequestMapping("/SaveUserCusMenu")
		public String SaveUserCusMenu(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException { // , Mainmenuitem
			Useronline useronlineinfo = (Useronline) request.getSession()
					.getAttribute("Useronline");												// command
			String result = "";
			try {
				reponse.setCharacterEncoding("utf-8");
				String fid=request.getParameter("fid");
				if(StringUtils.isEmpty(fid)) throw new DJException("标识不能为空");
				if(StringUtils.isEmpty(request.getParameter("fischeck"))) throw new DJException("数据有误");
				fid="('"+fid.replace(",","','")+"')";
				String fischeck=request.getParameter("fischeck");
				registerAuthenticationDao.ExecSaveUserpermission(fid,fischeck,useronlineinfo.getFuserid());
				reponse.getWriter().write(
							JsonUtil.result(true, "操作成功!", "", ""));
				

			} catch (Exception e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}

	
	
	
	
}
