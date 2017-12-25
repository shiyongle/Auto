package com.pc.controller.userRole;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.UUID.impl.UuidDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.refuse.IrefuseDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.dao.userDiary.impl.UserDiaryDao;
import com.pc.model.CL_Address;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_Umeng_Push;
import com.pc.model.CL_UserDiary;
import com.pc.model.CL_UserRole;
import com.pc.model.CL_Uuid;
import com.pc.model.Util_Option;
import com.pc.model.Util_UserOnline;
import com.pc.query.address.AddressQuery;
import com.pc.query.financeStatement.FinanceStatementQuery;
import com.pc.query.order.OrderQuery;
import com.pc.query.userDiary.UserDiaryQuery;
import com.pc.query.userRole.UserRoleQuery;
import com.pc.util.JSONUtil;
import com.pc.util.RedisUtil;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.model.CL_Refuse;
import com.pc.query.refuse.RefuseQuery;

@Controller
public class UserRoleController extends BaseController {
	protected static final String LIST_JSP = "/pages/pc/user/user_list.jsp";
	protected static final String INDEX_JSP = "/index.jsp";
	protected static final String EDIT_JSP = "/pages/pc/user/user_edit.jsp";
	protected static final String USER_DIARY = "/pages/pc/user/user_diary.jsp";
	
	protected static final String REFUSE_LIST_JSP = "/pages/pc/system/refuseList.jsp";
	protected static final String REFUSE_CREATE_JSP = "/pages/pc/system/refuseCreate.jsp";
	protected static final String REFUSE_EDIT_JSP = "/pages/pc/system/refuseEdit.jsp";

	@Resource
	private UserRoleDao userRoleDao;

	@Resource
	private UserDiaryDao userDiaryDao;

	@Resource
	private UuidDao uuidDao;
	
	@Resource
	private IFinanceStatementDao financeStatementDao;
	
	@Resource
	private IUMengPushDao iumeng;

	@Resource
	private IrefuseDao refuseDao;
	// private FinanceStatementQuery finStatementQuery;
	//
	// public FinanceStatementQuery getFinStatementQuery() {
	// return finStatementQuery;
	// }
	//
	// public void setFinStatementQuery(FinanceStatementQuery ff) {
	// this.finStatementQuery = ff;
	// }
	// private UserRoleQuery userRoleQuery;

	// public UserRoleQuery getUserRoleQuery() {
	// return userRoleQuery;
	// }
	// public void setUserRoleQuery(UserRoleQuery userRoleQuery) {
	// this.userRoleQuery = userRoleQuery;
	// }

	// 列表界面
	@RequestMapping("/user/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return LIST_JSP;
	}

	@RequestMapping("/user/logList")
	public String logList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return USER_DIARY;
	}

	// 加载列表数据
	@RequestMapping("/user/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute UserRoleQuery userRoleQuery) throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String vmiUserName = request.getParameter("vmiUserName");

		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		if (pageNum != null) {
			userRoleQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			userRoleQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if (vmiUserName != null) {
			userRoleQuery.setVmiUserName(vmiUserName);
		}
		// 调度平台用户,只查询运营角色
		// userRoleQuery.setRoleId(3);
		Page<CL_UserRole> page = userRoleDao.findPage(userRoleQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	// 校验输入的用户和密码是否存在
	@RequestMapping("/user/checkuser")
	public String checkuser(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		Integer userId = null;
		String fname = request.getParameter("fname");
		System.out.println(fname);
		String fpassword = request.getParameter("fpassword");
		System.out.println(fpassword);
		DongjingClient a = ServerContext.createVmiClient();
		a.setMethod("csclwebLogon");
		a.setRequestProperty("fname", fname);
		a.setRequestProperty("fpassword", fpassword);
		a.SubmitData();
		JSONObject jbsc = net.sf.json.JSONObject.fromObject(a.getResponse().getResultString());
		if (("false").equals(jbsc.get("success"))) {
			return writeAjaxResponse(reponse, JSONUtil.getJson("failure"));
		} else {
			HashMap<String, Object> m = new HashMap<String, Object>();
			String fid = JSONObject.fromObject(JSONArray.fromObject(jbsc.get("userlist")).get(0)).get("fid").toString();
			String fvmiName = JSONObject.fromObject(JSONArray.fromObject(jbsc.get("userlist")).get(0)).get("fname")
					.toString();
			String fvmiPhone = JSONObject.fromObject(JSONArray.fromObject(jbsc.get("userlist")).get(0)).get("ftel")
					.toString();
			CL_UserRole userRole = userRoleDao.getByVmiUserFidAndRoleId(fid, 3);
			if (userRole == null) {
				CL_UserRole userRole1 = new CL_UserRole();
				userRole1.setCreateTime(new Date());
				userRole1.setVmiUserFid(fid);
				userRole1.setVmiUserName(fvmiName);
				userRole1.setRoleId(3);
				userRole1.setVmiUserPhone(fvmiPhone);
				userRoleDao.save(userRole1);
			}
			if (userRole.getVmiUserName() == null || "".equals(userRole.getVmiUserName())) {
				userRole.setVmiUserName(fvmiName);
				userRole.setVmiUserPhone(fvmiPhone);
				userRoleDao.update(userRole);
			}
			// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY
			// CC 2016-02-24 START
			userId = userRoleDao.getByVmiUserFidAndRoleId(fid, 3).getId();
			Util_UserOnline useronline = new Util_UserOnline();
			useronline.setFsessionid(request.getSession().getId().toString());
			useronline.setFuserid(fid);
			useronline.setFusername(fname);
			useronline.setFlogintime(new Date());
			useronline.setFuserId(userId);
			
			//服务器维护判断开始
			/*if(RedisUtil.get("UpdateStartTime")!=null && RedisUtil.get("UpdateEndTime")!=null ){
				SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date now = new Date();// 当前时间
				if (!(RedisUtil.get("allowPerson").contains(userId+""))) {
					if (formatTemp.parse(RedisUtil.get("UpdateStartTime")).before(now) && formatTemp.parse(RedisUtil.get("UpdateEndTime")).after(now)) {
						m.put("success", "false");
						m.put("msg", RedisUtil.get("UpdateStartTime") + "至" + RedisUtil.get("UpdateEndTime") + RedisUtil.get("contentkey"));
						return writeAjaxResponse(reponse, JSONUtil.getJson(m));
					}
				}
			}*/
			HashMap<String, Object> tm = null;
			tm = RedisUtil.IsSystemDefend(1,userId);
			if(tm != null){
				return writeAjaxResponse(reponse, JSONUtil.getJson(tm));
			}
			//服务器维护判断结束
			
			ServerContext.setUseronline(request.getSession().getId().toString(), useronline);
			// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY
			// CC 2016-02-24 END
			
			m.put("success", "success");
			m.put("fid", fid);
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		}
	}

	@RequestMapping("/index")
	public String login(@ModelAttribute("user") CL_UserRole user, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		if (user.getFname() != null) {
			String username = user.getFname();
			// username=new String(username.getBytes("ISO-8859-1"),"utf-8");
			session.setAttribute("username", username);
			List<CL_UserRole> ls = this.userRoleDao.getByVmiUserNameAccurate(username);
			// if(ls.size()>0){
			// session.setAttribute("userId", ls.get(0).getVmiUserFid());//用户ID
			// session.setAttribute("userRoleId",
			// ls.get(0).getId());//当前登录用户对应的角色ID
			// }
			// modify by les 2016-4-23 修正userRoleId 为运营角色
			if (ls.size() > 0) {
				session.setAttribute("userId", ls.get(0).getVmiUserFid());// 用户ID
				for (CL_UserRole ur : ls) {
					if (ur.getRoleId() == 3) {
						session.setAttribute("userRoleId", ur.getId());// 当前登录用户对应的角色ID
						break;
					}
				}
			}
		}
		return INDEX_JSP;
	}

	@RequestMapping("/user/logout")
	public void logout(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		HttpSession session = request.getSession();
		// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY CC
		// 2016-02-24 START
		ServerContext.delUseronline(session.getId().toString());
		// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY CC
		// 2016-02-24 end
		if (session.getAttribute("username") != null) {
			session.invalidate();
		}
		PrintWriter out = reponse.getWriter();
		out.write("<script type='text/javascript'>window.parent.frames.location.href = '../login.jsp';</script>");
	}

	@RequestMapping("/user/queryCarUser")
	public String queryCarUser(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute UserRoleQuery userRoleQuery) {
		// 只查询是车主的用户
		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		userRoleQuery.setRoleId(2);
		if (request.getParameter("roleid") != null && "3".equals(request.getParameter("roleid"))) {
			userRoleQuery.setRoleId(3);
		}
		List<CL_UserRole> ls = this.userRoleDao.find(userRoleQuery);
		List<Util_Option> options = new ArrayList<Util_Option>();
		if (ls.size() > 0) {

			// 直接取CL_UserRole表后加的字段vmi_user_name为name
			/*
			 * String fids =""; for(CL_UserRole user:ls){
			 * fids=fids+"'"+user.getVmiUserFid()+"',"; } DongjingClient a
			 * =ServerContext.createVmiClient(); a.setMethod("csclGetUserlist");
			 * a.setRequestProperty("fids", "WHERE fid IN ("
			 * +fids.substring(0,fids.length()-1)+")"); a.SubmitData();
			 * JSONObject jbsc
			 * =net.sf.json.JSONObject.fromObject(a.getResponse().
			 * getResultString()); JSONArray array =
			 * JSONArray.fromObject(jbsc.get("userlist")); for (int i
			 * =0;i<array.size();i++) {
			 * ls.get(i).setFname(JSONObject.fromObject(array.get(i)).get(
			 * "fname").toString());
			 * ls.get(i).setVmiUserName(JSONObject.fromObject(array.get(i)).get(
			 * "fname").toString());
			 * ls.get(i).setVmiUserPhone(JSONObject.fromObject(array.get(i)).get
			 * ("ftel").toString()); }
			 */
			for (CL_UserRole userrole : ls) {
				Util_Option o1 = new Util_Option();
				o1.setOptionId(userrole.getId());
				o1.setOptionName(userrole.getFname());
				options.add(o1);
			}
			Util_Option o = new Util_Option();
			o.setOptionName("请选择");
			o.setOptionId(-1);
			o.setOptionVal("请选择");
			options.add(0, o);
			return writeAjaxResponse(reponse, JSONUtil.getJson(options));
		} else {
			return writeAjaxResponse(reponse, JSONUtil.getJson(options));
		}
	}

	@RequestMapping("/user/queryHuoUser")
	public String queryHuoUser(HttpServletResponse reponse, @ModelAttribute UserRoleQuery userRoleQuery) {
		// 只查询是货主的用户
		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		userRoleQuery.setRoleId(1);
		List<CL_UserRole> ls = this.userRoleDao.find(userRoleQuery);
		List<Util_Option> options = new ArrayList<Util_Option>();
		if (ls.size() > 0) {
			String fids = "";
			for (CL_UserRole user : ls) {
				fids = fids + "'" + user.getVmiUserFid() + "',";
			}
			DongjingClient a = ServerContext.createVmiClient();
			a.setMethod("csclGetUserlist");
			a.setRequestProperty("fids", "WHERE fid IN (" + fids.substring(0, fids.length() - 1) + ")");
			a.SubmitData();
			JSONObject jbsc = net.sf.json.JSONObject.fromObject(a.getResponse().getResultString());
			JSONArray array = JSONArray.fromObject(jbsc.get("userlist"));
			for (int i = 0; i < array.size(); i++) {
				ls.get(i).setFname(JSONObject.fromObject(array.get(i)).get("fname").toString());
				ls.get(i).setVmiUserName(JSONObject.fromObject(array.get(i)).get("fname").toString());
				ls.get(i).setVmiUserPhone(JSONObject.fromObject(array.get(i)).get("ftel").toString());
			}
			for (CL_UserRole userrole : ls) {
				Util_Option o1 = new Util_Option();
				o1.setOptionId(userrole.getId());
				o1.setOptionName(userrole.getFname());
				options.add(o1);
			}
			Util_Option o = new Util_Option();
			o.setOptionName("请选择");
			o.setOptionId(-1);
			o.setOptionVal("请选择");
			options.add(0, o);
			return writeAjaxResponse(reponse, JSONUtil.getJson(options));
		} else {
			return writeAjaxResponse(reponse, JSONUtil.getJson(options));
		}
	}

	@RequestMapping("/user/editAccount")
	public String editAccount(HttpServletRequest request, HttpServletResponse reponse, Integer id) {
		CL_UserRole role = this.userRoleDao.getById(id);
		request.setAttribute("role", role);
		return EDIT_JSP;
	}

	@RequestMapping("/user/updateAccount")
	public String updateAccount(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute CL_UserRole role) {
		this.userRoleDao.updateAcount(role);
		return writeAjaxResponse(reponse, "success");
	}

	/**
	 * 转介绍注册接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/inviteReg")
	public String inviteReg(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String ftel = "", fcode = "", password = "", repassword = "", fuserroleid = "", fuserid = "";
		if (request.getParameter("ftel") == null || "".equals(request.getParameter("ftel"))) {
			map.put("success", "false");
			map.put("msg", "手机号不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			ftel = request.getParameter("ftel");
		}
		if (request.getParameter("fuserroleid") == null || "".equals(request.getParameter("fuserroleid"))) {
			map.put("success", "false");
			map.put("msg", "用户角色ID不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			fuserroleid = request.getParameter("fuserroleid");
			CL_UserRole role = userRoleDao.getById(Integer.parseInt(fuserroleid));
			if (role != null) {
				fuserid = role.getVmiUserFid();
			} else {
				fuserid = "0";
			}
		}

		if (request.getParameter("fcode") == null || "".equals(request.getParameter("fcode"))) {
			map.put("success", "false");
			map.put("msg", "手机验证码不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			fcode = request.getParameter("fcode");
		}

		if (request.getParameter("password") == null || "".equals(request.getParameter("password"))) {
			map.put("success", "false");
			map.put("msg", "用户密码不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			password = request.getParameter("password");
		}

		if (request.getParameter("repassword") == null || "".equals(request.getParameter("repassword"))
				|| !request.getParameter("repassword").equals(password)) {
			map.put("success", "false");
			map.put("msg", "两次密码输入不一致！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			repassword = request.getParameter("repassword");
		}
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("inviteReg");
		djcn.setRequestProperty("fpassword", password);
		djcn.setRequestProperty("fuserroleid", fuserroleid);
		djcn.setRequestProperty("frepassword", repassword);
		djcn.setRequestProperty("ftel", ftel);
		djcn.setRequestProperty("fuserid", fuserid);
		djcn.setRequestProperty("fcode", fcode);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		if ("true".equals(jo.get("success"))) {
			// CL_UserRole csclUser = new CL_UserRole();
			// JSONArray array = JSONArray.fromObject(jo.get("data"));
			// csclUser.setRoleId(0);
			// csclUser.setVmiUserFid(JSONObject.fromObject(array.get(0)).get("fid").toString());
			// csclUser.setVmiUserName(JSONObject.fromObject(array.get(0)).get("fname").toString());
			// csclUser.setVmiUserPhone(JSONObject.fromObject(array.get(0)).get("ftel").toString());
			// csclUser.setCreateTime(new Date());
			// this.userRoleDao.save(csclUser);
			map.put("success", "true");
			map.put("msg", "注册成功！");
		} // 根据 vmi方法 reg 返回的msg 来判断是什么原因导致注册失败
		else if ("验证码校验失败！".equals(jo.get("msg"))) {
			map.put("success", "false");
			map.put("msg", "验证码校验失败validefail！");
		} else {
			map.put("success", "false");
			map.put("msg", jo.get("msg"));
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	// 获取或者下拉列表数据源
	@RequestMapping("/user/combogrid")
	public String getComboxGridData(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String searchKey = "";
		if (request.getParameter("q") != null) {
			searchKey = request.getParameter("q");
			searchKey = new String(searchKey.getBytes("ISO-8859-1"), "utf-8");
		}
		List<Map<String, Object>> resList = this.userRoleDao.getComboxGridData(searchKey);
		return writeAjaxResponse(response, JSONUtil.getJson(resList));
	}

	// 设置帐号月结
	@RequestMapping("/user/updateUserProtocol")
	public String editMonthPay(HttpServletRequest request, HttpServletResponse reponse, Integer id) {
		CL_UserRole role = this.userRoleDao.getById(id);
		if (role.isProtocol()) {
			role.setProtocol(false);
		} else {
			role.setProtocol(true);
		}
		userRoleDao.updateProtocol(role);
		return writeAjaxResponse(reponse, "success");
	}

	// 用户登录日志
	@RequestMapping("/user/loadLog")
	public String loadLog(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute UserDiaryQuery userDiaryQuery) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (userDiaryQuery == null) {
			userDiaryQuery = newQuery(UserDiaryQuery.class, null);
		}
		if (pageNum != null) {
			userDiaryQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			userDiaryQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_UserDiary> page = userDiaryDao.findPage(userDiaryQuery);
		map.put("rows", page.getResult());
		map.put("total", page.getTotalCount());
		System.out.println(page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	// 扫码需要的UUID
	@RequestMapping("/user/uuid")
	public String uuid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		UUID uuid = UUID.randomUUID();
		CL_Uuid entity = new CL_Uuid();
		entity.setUuid(uuid.toString());
		uuidDao.save(entity);
		List<CL_Uuid> list = uuidDao.getByUUID(uuid.toString());
		map.put("fid", list.get(0).getFid());
		map.put("UUID", list.get(0).getUuid());
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	// 扫码登录
	@RequestMapping("/user/QRLogin")
	public String QRLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String UUID = request.getParameter("UUID");
		String FID = request.getParameter("fid");
		List<CL_Uuid> list = uuidDao.getByUUIDAndFid(UUID, Integer.parseInt(FID));
		CL_Uuid uu = list.get(0);
		if (uu.getFname() != null) {
			map.put("success", "success");
			map.put("Uname", uu.getFname());
			map.put("UPWD", uu.getFpassword());
			// 登录成功后，该条数据就并没有什么卵用了，删了吧
			uuidDao.deleteById(uu.getFid());
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			map.put("success", "false");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}

	/** 获取余额 **/
	@RequestMapping("/app/user/getUserBalance")
	public String getAppUserBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取用户id
		Integer userroleid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		CL_UserRole role = userRoleDao.getById(userroleid);
		if (role == null)
			this.poClient(response, false, "未获取到用户");

		return this.poClient(response, true, role.getFbalance().toString());
	}

	/** 获取余额 **/
	@RequestMapping("/user/getUserBalance")
	public String getUserBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取用户id
		return this.getAppUserBalance(request, response);
	}

	/*** 获取对账单 ***/
	@RequestMapping("/app/user/getUserBill")
	public String getUserBill(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*String pageNum = request.getParameter("pageNum");
		;
		String pageSize = request.getParameter("pageSize");

		// 空判断
		String msg = String_Custom.parametersEmpty(new String[] { pageNum, pageSize });
		if (msg.length() != 0)
			return this.poClient(response, false, msg);

		// 合理性判断
		if (String_Custom.noNumber(pageSize))
			return this.poClient(response, false, "参数错误");
		if (String_Custom.noNumber(pageNum))
			return this.poClient(response, false, "参数错误");

		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		int start=(Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize)+1;
		int end=Integer.parseInt(pageNum)*Integer.parseInt(pageSize);
				
		
		
		Page<CL_FinanceStatement> page = financeStatementDao.findPageInfo(fcusid,start,end);
		return this.poClient(response, true, JSONUtil.getJson(page.getResult()), page.getTotalCount());*/
		
		FinanceStatementQuery statementQuery = newQuery(FinanceStatementQuery.class, null);
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer pageNum, pageSize;
		if (request.getParameter("pageNum") == null || "".equals(request.getParameter("pageNum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if (request.getParameter("pageSize") == null || "".equals(request.getParameter("pageSize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (statementQuery == null) {
			statementQuery = newQuery(FinanceStatementQuery.class, null);
		}
		if (pageNum != null) {
			statementQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			statementQuery.setPageSize(pageSize);
		}

		if (request.getSession() == null || request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			map.put("success", "false");
			map.put("msg", "请重新登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		Integer userId = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		statementQuery.setFuserroleId(userId);
		statementQuery.setSortColumns("bt.fcreate_time desc");
		Page<CL_FinanceStatement> page = financeStatementDao.findPageInfo(statementQuery);
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	
	}

	
	/****用户推送注册****/
	@RequestMapping("/app/user/umengPush")
	public String umengPush(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bundleID = request.getParameter("bundleID");   //设备唯一标示(苹果传bundle_id Android传android)
		
		String msg = String_Custom.parametersEmpty(new String[] { bundleID});
		if (msg.length() != 0)
			return this.poClient(response, false, msg);
		
		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_UserRole role = userRoleDao.getById(fcusid);
		if (role == null)
			this.poClient(response, false, "未获取到用户");
		
		int fdeviceType=1;
		if(bundleID.equals("android"))
		{
			fdeviceType=1;
		}else if(bundleID.equals("com.djkg.ylhy"))
		{
			fdeviceType=2;	
		}else if(bundleID.equals("com.djkg.hy"))
		{
			fdeviceType=3;
		}else
		{
			fdeviceType=4;//未知
		}
		
		List<CL_Umeng_Push> umengList=iumeng.getUserUmengRegistration(fcusid,fdeviceType);
		
		if(umengList.size()==0)
		{
			CL_Umeng_Push model=new CL_Umeng_Push();
			model.setFuserid(fcusid);
			model.setFuserPhone(role.getVmiUserPhone());
			model.setFcreateTime(new Date());  //创建时间
			model.setFlastTime(new Date());  //当前时间
			model.setFdevice(role.getVmiUserFid());
			model.setFuserType(role.getRoleId());
			model.setFdeviceType(fdeviceType);
			iumeng.save(model);
			umengList.add(model);
		}else
		{
			CL_Umeng_Push model=umengList.get(0);
			model.setFlastTime(new Date());
			model.setFuserPhone(role.getVmiUserPhone());
			iumeng.update(model);
		}
		
		
		
		return this.poClient(response, true);
//		
//		String pageSize = request.getParameter("pageSize");
//
//		// 空判断
//		String msg = String_Custom.parametersEmpty(new String[] { pageNum, pageSize });
//		if (msg.length() != 0)
//			return this.poClient(response, false, msg);
//
//		// 合理性判断
//		if (String_Custom.noNumber(pageSize))
//			return this.poClient(response, false, "参数错误");
//		if (String_Custom.noNumber(pageNum))
//			return this.poClient(response, false, "参数错误");
//
//		
//
//		int start=(Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize)+1;
//		int end=Integer.parseInt(pageNum)*Integer.parseInt(pageSize);
//				
//		
//		Page<CL_FinanceStatement> page = financeStatementDao.findPageInfo(fcusid,start,end);
//		return this.poClient(response, true, JSONUtil.getJson(page.getResult()), page.getTotalCount());
	}
	
	/**
	 * 用户导出功能
	 * @param request
	 * @param response
	 * @param userRoleQuery
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("app/user/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		
		return null;
	}
	
	@RequestMapping("/system/refuseList")
	public String refuseList(HttpServletRequest request,HttpServletResponse response){
		return REFUSE_LIST_JSP;
	}
	
	@RequestMapping("/system/refuseCreate")
	public String refuseCreate(HttpServletRequest request,HttpServletResponse response){
		return REFUSE_CREATE_JSP;
	}
	
	@RequestMapping("/system/refuseEdit")
	public String refuseEdit(HttpServletRequest request,HttpServletResponse response,Integer id){
		CL_Refuse refuse= refuseDao.getById(id);
		request.setAttribute("refuse", refuse);
		return REFUSE_EDIT_JSP;
	}
	
	@RequestMapping("/system/refuseLoad")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute RefuseQuery refuseQuery){
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (refuseQuery == null) {
			refuseQuery = newQuery(RefuseQuery.class, null);
		}
		if (pageNum != null) {
			refuseQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			refuseQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_Refuse> page = refuseDao.findPage(refuseQuery);
		for(int i=0;i<page.getResult().size();i++){
			String fvalues = page.getResult().get(i).getFvalues();
			CL_Refuse refuseinfo = refuseDao.getusername(fvalues.split(","));
			page.getResult().get(i).setFvaluesName(refuseinfo.getFvalues());
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 系统控制功能新增接口
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sys/refuseSave")
	public String refuseSave(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Refuse refuse,String StartTime, String EndTime) throws Exception{
		HashMap<String, Object> m = new HashMap<>();
		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId());
		if(useronline == null ){
			m.put("success", false);
			m.put("msg", "登录超时,请重新登录！");
		}
		CL_Refuse refuseinfo = refuseDao.IsExistIdByType(refuse.getFtype());
		if (refuseinfo != null && !refuseinfo.equals("")) {
			m.put("success", false);
			m.put("msg", "保存失败，该控制类型已存在!");
		}else{
			DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StartTime != null && !"".equals(StartTime)){
				refuse.setFstart_time(fmtDateTime.parse(StartTime));
			}
			if(EndTime != null && !"".equals(EndTime)){
				refuse.setFend_time(fmtDateTime.parse(EndTime));
			}
			
			refuse.setFcreator(useronline.getFuserId());
			refuse.setFcreate_time(new Date());
			refuseDao.save(refuse);
			updateOnlineDate(refuse.getFtype());
			
			m.put("success", true);
			m.put("msg", "保存成功!");
		}
		
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 系统控制功能修改接口
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sys/refuseUpdate")
	public String refuseUpdate(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Refuse refuse,String StartTime, String EndTime) throws Exception{
		HashMap<String, Object> m = new HashMap<>();
		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId());
		if(useronline == null ){
			m.put("success", "false");
			m.put("msg", "登录超时,请重新登录！");
		}
		
		DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StartTime != null && !"".equals(StartTime)){
			refuse.setFstart_time(fmtDateTime.parse(StartTime));
		}
		if(EndTime != null && !"".equals(EndTime)){
			refuse.setFend_time(fmtDateTime.parse(EndTime));
		}
		
		refuse.setFupdate_time(new Date());
		refuseDao.update(refuse);
		updateOnlineDate(refuse.getFtype());
		
		m.put("success", true);
		m.put("msg", "修改成功!");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 系统控制功能删除接口
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sys/refuseDel")
	public String refuseDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer fid , success ,ftype;
		HashMap<String, Object> m = new HashMap<String, Object>();
		
		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId());
		if(useronline == null ){
			m.put("success", "false");
			m.put("msg", "登录超时,请重新登录！");
		}
		
		if (request.getParameter("id") == null || "".equals(request.getParameter("id"))) {
			m.put("success", "false");
			m.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			fid = Integer.parseInt(request.getParameter("id"));
		}

		if (request.getParameter("ftype") == null || "".equals(request.getParameter("ftype"))) {
			m.put("success", "false");
			m.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			ftype = Integer.parseInt(request.getParameter("ftype"));
		}
		
		success = refuseDao.deleteById(fid);
		updateOnlineDate(ftype);
		
		if(success==0){
			m.put("success", false);
			m.put("msg", "删除失败!");
		}else{
			m.put("success", true);
			m.put("msg", "删除成功!");
		}
		
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/**
	 * 2017-05-22 cd 增加往redis缓存存储上线停止时间
	 * **/
	public boolean updateOnlineDate(Integer ftype) throws Exception{
		try {
			SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			CL_Refuse refuse = refuseDao.IsExistIdByType(ftype);
			
			if(refuse==null){
				
				if(ftype == 0){
					RedisUtil.set("NoOrderStartTime", "");			//停止接单开始时间
					RedisUtil.set("NoOrderEndTime", "");			//停止接单结束时间
					RedisUtil.set("NoOrderAllowPerson", "");		//停止接单允许人员
					RedisUtil.set("NoOrderContentkey", "");			//停止接单提示内容
				}else{
					RedisUtil.set("OnlineTestStartTime", "");		//上线测试开始时间
					RedisUtil.set("OnlineTestEndTime", "");			//上线测试结束时间
					RedisUtil.set("OnlineTestAllowPerson", "");		//上线测试允许人员
					RedisUtil.set("OnlineTestContentkey", "");		//上线测试提示内容
				}
				
			}else{
				if(ftype == 0){
					RedisUtil.set("NoOrderStartTime", formatTemp.format(refuse.getFstart_time()));		//停止接单开始时间
					RedisUtil.set("NoOrderEndTime", formatTemp.format(refuse.getFend_time()));			//停止接单结束时间
					RedisUtil.set("NoOrderAllowPerson", refuse.getFvalues());							//停止接单允许人员
					RedisUtil.set("NoOrderContentkey", refuse.getFkey());								//停止接单提示内容
				}else{
					RedisUtil.set("OnlineTestStartTime", formatTemp.format(refuse.getFstart_time()));	//上线测试开始时间
					RedisUtil.set("OnlineTestEndTime", formatTemp.format(refuse.getFend_time()));		//上线测试结束时间
					RedisUtil.set("OnlineTestAllowPerson", refuse.getFvalues());						//上线测试允许人员
					RedisUtil.set("OnlineTestContentkey", refuse.getFkey());							//上线测试提示内容
				}
				
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	/**
	 * 2017-05-22 cd 手动更新redis缓存存储上线停止时间
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sys/updateOnlineDateL")
	public String updateOnlineDateL(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer success ,ftype;
		HashMap<String, Object> m = new HashMap<String, Object>();
		
		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId());
		if(useronline == null ){
			m.put("success", "false");
			m.put("msg", "登录超时,请重新登录！");
		}

		if (request.getParameter("ftype") == null || "".equals(request.getParameter("ftype"))) {
			m.put("success", "false");
			m.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			ftype = Integer.parseInt(request.getParameter("ftype"));
		}
		
		try {
			SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			CL_Refuse refuse = refuseDao.IsExistIdByType(ftype);
			
			if(refuse==null){
				
				if(ftype == 0){
					RedisUtil.set("NoOrderStartTime", "");			//停止接单开始时间
					RedisUtil.set("NoOrderEndTime", "");			//停止接单结束时间
					RedisUtil.set("NoOrderAllowPerson", "");		//停止接单允许人员
					RedisUtil.set("NoOrderContentkey", "");			//停止接单提示内容
				}else{
					RedisUtil.set("OnlineTestStartTime", "");		//上线测试开始时间
					RedisUtil.set("OnlineTestEndTime", "");			//上线测试结束时间
					RedisUtil.set("OnlineTestAllowPerson", "");		//上线测试允许人员
					RedisUtil.set("OnlineTestContentkey", "");		//上线测试提示内容
				}
				
			}else{
				if(ftype == 0){
					RedisUtil.set("NoOrderStartTime", formatTemp.format(refuse.getFstart_time()));		//停止接单开始时间
					RedisUtil.set("NoOrderEndTime", formatTemp.format(refuse.getFend_time()));			//停止接单结束时间
					RedisUtil.set("NoOrderAllowPerson", refuse.getFvalues());							//停止接单允许人员
					RedisUtil.set("NoOrderContentkey", refuse.getFkey());								//停止接单提示内容
				}else{
					RedisUtil.set("OnlineTestStartTime", formatTemp.format(refuse.getFstart_time()));	//上线测试开始时间
					RedisUtil.set("OnlineTestEndTime", formatTemp.format(refuse.getFend_time()));		//上线测试结束时间
					RedisUtil.set("OnlineTestAllowPerson", refuse.getFvalues());						//上线测试允许人员
					RedisUtil.set("OnlineTestContentkey", refuse.getFkey());							//上线测试提示内容
				}
				
			}
			success = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			success = 0;
		}
		
		if(success==0){
			m.put("success", false);
			m.put("msg", "缓存更新失败!");
		}else{
			m.put("success", true);
			m.put("msg", "缓存更新成功!");
		}
		
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
}